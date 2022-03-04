package lotr.common.event;

import java.util.Optional;
import lotr.common.LOTRGameRules;
import lotr.common.init.LOTRDimensions;
import lotr.common.util.LOTRUtil;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class MiddleEarthRespawning {
   private static final int BED_RESPAWN_WITHIN = 5000;
   private static final int WORLDSPAWN_RESPAWN_WITHIN = 2000;
   private static final int MIN_RESPAWN_RANGE = 500;
   private static final int MAX_RESPAWN_RANGE = 1500;

   public static ServerWorld getDefaultRespawnWorld(ServerWorld defaultRespawnWorld, ServerPlayerEntity player) {
      return isLOTRDimension(player) && LOTRDimensions.isDimension((World)defaultRespawnWorld, World.field_234918_g_) ? player.func_71121_q() : defaultRespawnWorld;
   }

   private static boolean isLOTRDimension(ServerPlayerEntity player) {
      return LOTRDimensions.isModDimension(player.func_71121_q());
   }

   public static BlockPos getCheckedBedRespawnPosition(BlockPos bedRespawnPosition, ServerPlayerEntity player) {
      ServerWorld bedRespawnWorld = player.func_184102_h().func_71218_a(player.func_241141_L_());
      return isLOTRDimension(player) && bedRespawnPosition != null && bedRespawnWorld != null && !LOTRDimensions.isModDimension(bedRespawnWorld) ? null : bedRespawnPosition;
   }

   public static void relocatePlayerIfNeeded(Optional optBedRespawnPosition, ServerPlayerEntity newPlayer, ServerPlayerEntity deadPlayer) {
      if (isLOTRDimension(deadPlayer)) {
         ServerWorld world = deadPlayer.func_71121_q();
         Vector3d deathPoint = deadPlayer.func_213303_ch();
         if (optBedRespawnPosition.isPresent()) {
            Vector3d bedRespawnPos = (Vector3d)optBedRespawnPosition.get();
            if (!deathPoint.func_237488_a_(bedRespawnPos, 5000.0D) && isGameruleEnabled(world)) {
               relocateRandomlyForDistantRespawn(newPlayer, deadPlayer, true);
            }
         } else {
            BlockPos worldSpawn = LOTRDimensions.getDimensionSpawnPoint(world);
            if (!deathPoint.func_237488_a_(Vector3d.func_237489_a_(worldSpawn), 2000.0D) && isGameruleEnabled(world)) {
               relocateRandomlyForDistantRespawn(newPlayer, deadPlayer, false);
            } else {
               int x = worldSpawn.func_177958_n();
               int z = worldSpawn.func_177952_p();
               int y = LOTRUtil.forceLoadChunkAndGetTopBlock(world, x, z);
               newPlayer.func_70012_b((double)x + 0.5D, (double)y, (double)z + 0.5D, newPlayer.field_70177_z, newPlayer.field_70125_A);
               raisePlayerIfObstructed(newPlayer, world);
            }
         }
      }

   }

   private static boolean isGameruleEnabled(ServerWorld world) {
      return world.func_82736_K().func_223586_b(LOTRGameRules.MIDDLE_EARTH_RESPAWNING);
   }

   private static void relocateRandomlyForDistantRespawn(ServerPlayerEntity newPlayer, ServerPlayerEntity deadPlayer, boolean hasBed) {
      if (isLOTRDimension(deadPlayer)) {
         ServerWorld world = newPlayer.func_71121_q();
         BlockPos deathPos = deadPlayer.func_233580_cy_();
         double randomDistance = (double)MathHelper.func_76136_a(world.field_73012_v, 500, 1500);
         float angle = world.field_73012_v.nextFloat() * 3.1415927F * 2.0F;
         int x = deathPos.func_177958_n() + (int)(randomDistance * (double)MathHelper.func_76126_a(angle));
         int z = deathPos.func_177952_p() + (int)(randomDistance * (double)MathHelper.func_76134_b(angle));
         int y = LOTRUtil.forceLoadChunkAndGetTopBlock(world, x, z);
         newPlayer.func_70012_b((double)x + 0.5D, (double)y, (double)z + 0.5D, newPlayer.field_70177_z, newPlayer.field_70125_A);
         raisePlayerIfObstructed(newPlayer, world);
         if (hasBed) {
            LOTRUtil.sendMessage(newPlayer, new TranslationTextComponent("chat.lotr.respawn.farFromBed"));
         } else {
            LOTRUtil.sendMessage(newPlayer, new TranslationTextComponent("chat.lotr.respawn.farFromWorldSpawn"));
         }
      }

   }

   private static void raisePlayerIfObstructed(ServerPlayerEntity newPlayer, ServerWorld world) {
      while(!world.func_226669_j_(newPlayer) && newPlayer.func_226278_cu_() < (double)world.func_217301_I()) {
         newPlayer.func_70107_b(newPlayer.func_226277_ct_(), newPlayer.func_226278_cu_() + 1.0D, newPlayer.func_226281_cx_());
      }

   }
}
