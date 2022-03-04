package lotr.client;

import java.util.List;
import lotr.common.item.LOTRWeaponStats;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

public class LOTREntityRenderer extends EntityRenderer {
   private Minecraft theMC;
   private Entity thePointedEntity;

   public LOTREntityRenderer(Minecraft mc, IResourceManager irm) {
      super(mc, irm);
      this.theMC = mc;
   }

   public void func_78473_a(float partialTick) {
      if (this.theMC.field_71451_h != null && this.theMC.field_71441_e != null) {
         this.theMC.field_147125_j = null;
         this.thePointedEntity = null;
         double blockReach = (double)this.theMC.field_71442_b.func_78757_d();
         float meleeReachFactor = LOTRWeaponStats.getMeleeReachFactor(this.theMC.field_71439_g.func_70694_bm());
         blockReach *= (double)meleeReachFactor;
         this.theMC.field_71476_x = this.theMC.field_71451_h.func_70614_a(blockReach, partialTick);
         double reach = (double)LOTRWeaponStats.getMeleeReachDistance(this.theMC.field_71439_g);
         double maxDist = reach;
         Vec3 posVec = this.theMC.field_71451_h.func_70666_h(partialTick);
         if (this.theMC.field_71476_x != null) {
            maxDist = this.theMC.field_71476_x.field_72307_f.func_72438_d(posVec);
         }

         Vec3 lookVec = this.theMC.field_71451_h.func_70676_i(partialTick);
         Vec3 sightVec = posVec.func_72441_c(lookVec.field_72450_a * reach, lookVec.field_72448_b * reach, lookVec.field_72449_c * reach);
         Vec3 targetVec = null;
         float lookWidth = LOTRWeaponStats.getMeleeExtraLookWidth();
         List entities = this.theMC.field_71441_e.func_72839_b(this.theMC.field_71451_h, this.theMC.field_71451_h.field_70121_D.func_72321_a(lookVec.field_72450_a * reach, lookVec.field_72448_b * reach, lookVec.field_72449_c * reach).func_72314_b((double)lookWidth, (double)lookWidth, (double)lookWidth));
         double leastDist = maxDist;

         for(int i = 0; i < entities.size(); ++i) {
            Entity entity = (Entity)entities.get(i);
            if (entity.func_70067_L()) {
               float f = entity.func_70111_Y();
               AxisAlignedBB entityBB = entity.field_70121_D.func_72314_b((double)f, (double)f, (double)f);
               MovingObjectPosition movingobjectposition = entityBB.func_72327_a(posVec, sightVec);
               if (entityBB.func_72318_a(posVec)) {
                  if (0.0D < leastDist || leastDist == 0.0D) {
                     this.thePointedEntity = entity;
                     targetVec = movingobjectposition == null ? posVec : movingobjectposition.field_72307_f;
                     leastDist = 0.0D;
                  }
               } else if (movingobjectposition != null) {
                  double entityDist = posVec.func_72438_d(movingobjectposition.field_72307_f);
                  if (entityDist < leastDist || leastDist == 0.0D) {
                     if (entity == this.theMC.field_71451_h.field_70154_o && !entity.canRiderInteract()) {
                        if (leastDist == 0.0D) {
                           this.thePointedEntity = entity;
                           targetVec = movingobjectposition.field_72307_f;
                        }
                     } else {
                        this.thePointedEntity = entity;
                        targetVec = movingobjectposition.field_72307_f;
                        leastDist = entityDist;
                     }
                  }
               }
            }
         }

         if (this.thePointedEntity != null && (leastDist < maxDist || this.theMC.field_71476_x == null)) {
            this.theMC.field_71476_x = new MovingObjectPosition(this.thePointedEntity, targetVec);
            if (this.thePointedEntity instanceof EntityLivingBase || this.thePointedEntity instanceof EntityItemFrame) {
               this.theMC.field_147125_j = this.thePointedEntity;
            }
         }
      }

   }

   public void func_78464_a() {
      super.func_78464_a();
      if (Minecraft.func_71382_s()) {
         float wight = LOTRClientProxy.tickHandler.getWightLookFactor();
         float hand = LOTRReflectionClient.getHandFOV(this);
         LOTRReflectionClient.setHandFOV(this, hand + wight * 0.3F);
      }

   }
}
