package lotr.common.world;

import java.util.Optional;
import java.util.function.Function;
import lotr.common.data.LOTRLevelData;
import lotr.common.dim.MiddleEarthDimensionType;
import lotr.common.entity.item.RingPortalEntity;
import lotr.common.init.LOTRDimensions;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.ITeleporter;

public class RingPortalTeleporter implements ITeleporter {
   private RegistryKey targetDim;
   private boolean makeRingPortalIfNotMade;

   public RingPortalTeleporter(RegistryKey dim, boolean make) {
      this.targetDim = dim;
      this.makeRingPortalIfNotMade = make;
   }

   public static void transferEntity(ServerWorld world, Entity entity, Optional portal, boolean makePortal) {
      portal.ifPresent(RingPortalEntity::onTransferEntity);
      RegistryKey targetDim = LOTRDimensions.MIDDLE_EARTH_WORLD_KEY;
      if (LOTRDimensions.isDimension((World)world, targetDim)) {
         targetDim = World.field_234918_g_;
      }

      ServerWorld targetWorld = world.func_73046_m().func_71218_a(targetDim);
      entity.changeDimension(targetWorld, new RingPortalTeleporter(targetDim, makePortal));
   }

   public Entity placeEntity(Entity entity, ServerWorld currentWorld, ServerWorld destWorld, float yaw, Function repositionEntity) {
      LOTRLevelData levelData = LOTRLevelData.serverInstance();
      BlockPos target;
      if (this.targetDim.equals(LOTRDimensions.MIDDLE_EARTH_WORLD_KEY)) {
         MiddleEarthDimensionType meDim = (MiddleEarthDimensionType)destWorld.func_73046_m().func_71218_a(this.targetDim).func_230315_m_();
         if (levelData.madeMiddleEarthPortal()) {
            target = meDim.getSpawnCoordinate(destWorld);
         } else {
            target = meDim.getDefaultPortalCoordinate(destWorld);
         }
      } else {
         target = levelData.getOverworldPortalLocation();
      }

      while(destWorld.func_180495_p(target).func_200132_m()) {
         target = target.func_177984_a();
      }

      while(!destWorld.func_180495_p(target.func_177977_b()).func_200132_m()) {
         target = target.func_177977_b();
      }

      Vector3d prevMotion = entity.func_213322_ci();
      if (entity instanceof ServerPlayerEntity) {
         ServerPlayerEntity player = (ServerPlayerEntity)entity;
         player.func_70012_b((double)target.func_177958_n() + 0.5D, (double)target.func_177956_o(), (double)target.func_177952_p() + 0.5D, player.field_70177_z, 0.0F);
         player.func_70029_a(destWorld);
         destWorld.func_217447_b(player);
         RegistryKey prevDimKey = currentWorld.func_234923_W_();
         RegistryKey newDimKey = player.field_70170_p.func_234923_W_();
         CriteriaTriggers.field_193134_u.func_233551_a_(player, prevDimKey, newDimKey);
         player.field_71135_a.func_147364_a(player.func_226277_ct_(), player.func_226278_cu_(), player.func_226281_cx_(), player.field_70177_z, player.field_70125_A);
      } else {
         Entity entityNew = entity.func_200600_R().func_200721_a(destWorld);
         if (entityNew != null) {
            entityNew.func_180432_n(entity);
            entityNew.func_174828_a(target, entityNew.field_70177_z, 0.0F);
            entityNew.func_213317_d(prevMotion);
            destWorld.func_217460_e(entityNew);
         }

         entity = entityNew;
      }

      if (entity != null) {
         entity.func_242279_ag();
      }

      if (this.targetDim.equals(LOTRDimensions.MIDDLE_EARTH_WORLD_KEY) && !levelData.madeMiddleEarthPortal()) {
         if (this.makeRingPortalIfNotMade) {
            RingPortalEntity portal = new RingPortalEntity(destWorld);
            portal.func_70012_b((double)target.func_177958_n() + 0.5D, (double)target.func_177956_o() + 3.5D, (double)target.func_177952_p() + 0.5D, 0.0F, 0.0F);
            destWorld.func_217376_c(portal);
         }

         levelData.setMadeMiddleEarthPortal(true);
         levelData.markMiddleEarthPortalLocation(destWorld, target);
      }

      return entity;
   }
}
