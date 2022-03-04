package lotr.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.RenderTypeBuffers;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.item.ItemFrameEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.common.ForgeMod;

public class LOTRGameRenderer extends GameRenderer {
   private final Minecraft mc;

   public LOTRGameRenderer(Minecraft mc, IResourceManager resMgr, RenderTypeBuffers buffers) {
      super(mc, resMgr, buffers);
      this.mc = mc;
   }

   public void func_78473_a(float partialTicks) {
      Entity entity = this.mc.func_175606_aa();
      if (entity != null && this.mc.field_71441_e != null) {
         this.mc.func_213239_aq().func_76320_a("pick");
         this.mc.field_147125_j = null;
         double blockReachDistance = (double)this.mc.field_71442_b.func_78757_d();
         this.mc.field_71476_x = entity.func_213324_a(blockReachDistance, partialTicks, false);
         Vector3d eyePos = entity.func_174824_e(partialTicks);
         boolean useSurvivalReachLimit = false;
         double survivalReachLimit = this.mc.field_71439_g.func_110148_a((Attribute)ForgeMod.REACH_DISTANCE.get()).func_111126_e() - 2.0D;
         double entityReachDistance = blockReachDistance;
         if (this.mc.field_71442_b.func_78749_i()) {
            entityReachDistance = blockReachDistance + 1.0D;
            blockReachDistance = entityReachDistance;
         } else if (blockReachDistance > survivalReachLimit) {
            useSurvivalReachLimit = true;
         }

         double entityReachDistanceSq = entityReachDistance * entityReachDistance;
         if (this.mc.field_71476_x != null) {
            entityReachDistanceSq = this.mc.field_71476_x.func_216347_e().func_72436_e(eyePos);
         }

         Vector3d lookVec = entity.func_70676_i(1.0F);
         Vector3d fullReachPos = eyePos.func_72441_c(lookVec.field_72450_a * blockReachDistance, lookVec.field_72448_b * blockReachDistance, lookVec.field_72449_c * blockReachDistance);
         float f = 1.0F;
         AxisAlignedBB fullReachBoundingBox = entity.func_174813_aQ().func_216361_a(lookVec.func_186678_a(blockReachDistance)).func_72314_b((double)f, (double)f, (double)f);
         EntityRayTraceResult entityRayTraceResult = ProjectileHelper.func_221273_a(entity, eyePos, fullReachPos, fullReachBoundingBox, (e) -> {
            return !e.func_175149_v() && e.func_70067_L();
         }, entityReachDistanceSq);
         if (entityRayTraceResult != null) {
            Entity targetEntity = entityRayTraceResult.func_216348_a();
            Vector3d targetEntityPos = entityRayTraceResult.func_216347_e();
            double dSqToTargetEntity = eyePos.func_72436_e(targetEntityPos);
            if (useSurvivalReachLimit && dSqToTargetEntity > survivalReachLimit * survivalReachLimit) {
               this.mc.field_71476_x = BlockRayTraceResult.func_216352_a(targetEntityPos, Direction.func_210769_a(lookVec.field_72450_a, lookVec.field_72448_b, lookVec.field_72449_c), new BlockPos(targetEntityPos));
            } else if (dSqToTargetEntity < entityReachDistanceSq || this.mc.field_71476_x == null) {
               this.mc.field_71476_x = entityRayTraceResult;
               if (targetEntity instanceof LivingEntity || targetEntity instanceof ItemFrameEntity) {
                  this.mc.field_147125_j = targetEntity;
               }
            }
         }

         this.mc.func_213239_aq().func_76319_b();
      }

   }
}
