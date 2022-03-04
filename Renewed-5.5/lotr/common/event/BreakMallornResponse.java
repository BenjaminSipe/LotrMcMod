package lotr.common.event;

import java.util.Random;
import lotr.common.data.LOTRLevelData;
import lotr.common.data.LOTRPlayerData;
import lotr.common.entity.npc.GaladhrimWarriorEntity;
import lotr.common.fac.FactionPointer;
import lotr.common.fac.FactionPointers;
import lotr.common.init.LOTRBiomes;
import lotr.common.init.LOTREntities;
import lotr.common.init.LOTRTags;
import lotr.common.speech.EventSpeechbanks;
import lotr.common.world.biome.LOTRBiomeWrapper;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.Heightmap.Type;
import net.minecraft.world.server.ServerWorld;

public class BreakMallornResponse {
   private static final FactionPointer FACTION;

   public void handleBlockBreak(World world, PlayerEntity player, BlockPos pos, BlockState state) {
      if (!world.field_72995_K && state.func_235714_a_(LOTRTags.Blocks.BREAK_MALLORN_RESPONSES) && !player.field_71075_bZ.field_75098_d) {
         ServerWorld sWorld = (ServerWorld)world;
         Random rand = world.field_73012_v;
         LOTRBiomeWrapper biomeWrapper = LOTRBiomes.getWrapperFor(world.func_226691_t_(pos), world);
         LOTRPlayerData pd = LOTRLevelData.getSidedData(player);
         if (rand.nextInt(3) == 0 && biomeWrapper.hasBreakMallornResponse() && pd.getAlignmentData().getAlignment(FACTION) < 0.0F) {
            int elves = 2 + world.field_73012_v.nextInt(4);
            boolean sentMessage = false;

            for(int l = 0; l < elves; ++l) {
               GaladhrimWarriorEntity warrior = this.spawnWarrior(sWorld, rand, player, pos);
               if (warrior != null && !sentMessage) {
                  warrior.sendSpeechTo((ServerPlayerEntity)player, EventSpeechbanks.GALADHRIM_WARRIOR_DEFEND_MALLORN);
                  sentMessage = true;
               }
            }
         }
      }

   }

   private GaladhrimWarriorEntity spawnWarrior(ServerWorld world, Random rand, PlayerEntity player, BlockPos pos) {
      GaladhrimWarriorEntity warrior = (GaladhrimWarriorEntity)((EntityType)LOTREntities.GALADHRIM_WARRIOR.get()).func_200721_a(world);
      int range = 6;
      int x = MathHelper.func_76128_c(player.func_226277_ct_()) + MathHelper.func_76136_a(rand, -range, range);
      int z = MathHelper.func_76128_c(player.func_226281_cx_()) + MathHelper.func_76136_a(rand, -range, range);
      int y = world.func_201676_a(Type.MOTION_BLOCKING_NO_LEAVES, x, z);
      BlockPos topPos = new BlockPos(x, y, z);
      if (world.func_180495_p(topPos.func_177977_b()).func_224755_d(world, topPos.func_177977_b(), Direction.UP) && !world.func_180495_p(topPos).func_215686_e(world, topPos) && !world.func_180495_p(topPos.func_177984_a()).func_215686_e(world, topPos.func_177984_a())) {
         warrior.func_70012_b((double)x + 0.5D, (double)y, (double)z + 0.5D, rand.nextFloat() * 360.0F, 0.0F);
         if (warrior.func_213380_a(world, SpawnReason.EVENT)) {
            warrior.spawnRidingHorse = false;
            warrior.func_213386_a(world, world.func_175649_E(warrior.func_233580_cy_()), SpawnReason.EVENT, (ILivingEntityData)null, (CompoundNBT)null);
            world.func_242417_l(warrior);
            warrior.setDefendingTree();
            warrior.func_70624_b(player);
            return warrior;
         }
      }

      return null;
   }

   static {
      FACTION = FactionPointers.LOTHLORIEN;
   }
}
