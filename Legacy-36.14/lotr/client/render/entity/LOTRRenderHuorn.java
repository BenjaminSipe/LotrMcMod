package lotr.client.render.entity;

import lotr.client.model.LOTRModelHuorn;
import lotr.common.entity.npc.LOTREntityHuornBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderHuorn extends RenderLiving {
   private static LOTRRandomSkins faceSkins;

   public LOTRRenderHuorn() {
      super(new LOTRModelHuorn(), 0.0F);
      faceSkins = LOTRRandomSkins.loadSkinsList("lotr:mob/huorn/face");
   }

   protected ResourceLocation func_110775_a(Entity entity) {
      LOTREntityHuornBase huorn = (LOTREntityHuornBase)entity;
      return faceSkins.getRandomSkin(huorn);
   }

   public void func_76986_a(EntityLiving entity, double d, double d1, double d2, float f, float f1) {
      LOTREntityHuornBase huorn = (LOTREntityHuornBase)entity;
      if (huorn.ignoringFrustumForRender) {
         huorn.ignoringFrustumForRender = false;
         huorn.field_70158_ak = false;
      }

      super.func_76986_a(entity, d, d1, d2, f, f1);
      if (Minecraft.func_71382_s() && huorn.hiredNPCInfo.getHiringPlayer() == this.field_76990_c.field_78734_h) {
         LOTRNPCRendering.renderHiredIcon(entity, d, d1 + 3.5D, d2);
         LOTRNPCRendering.renderNPCHealthBar(entity, d, d1 + 3.5D, d2);
      }

   }

   protected void func_77039_a(EntityLivingBase entity, double d, double d1, double d2) {
      LOTREntityHuornBase huorn = (LOTREntityHuornBase)entity;
      if (!huorn.isHuornActive()) {
         int i = MathHelper.func_76128_c(huorn.field_70165_t);
         int j = MathHelper.func_76128_c(huorn.field_70163_u);
         int k = MathHelper.func_76128_c(huorn.field_70161_v);
         double var10000 = (double)i + 0.5D;
         RenderManager var10001 = this.field_76990_c;
         d = var10000 - RenderManager.field_78725_b;
         var10000 = (double)j;
         var10001 = this.field_76990_c;
         d1 = var10000 - RenderManager.field_78726_c;
         var10000 = (double)k + 0.5D;
         var10001 = this.field_76990_c;
         d2 = var10000 - RenderManager.field_78723_d;
      }

      d1 -= 0.0078125D;
      super.func_77039_a(entity, d, d1, d2);
      huorn.field_70737_aN = 0;
   }

   protected void func_77043_a(EntityLivingBase entity, float f, float f1, float f2) {
      LOTREntityHuornBase huorn = (LOTREntityHuornBase)entity;
      if (!huorn.isHuornActive()) {
         f1 = 0.0F;
      }

      super.func_77043_a(entity, f, f1, f2);
   }

   protected float func_77044_a(EntityLivingBase entity, float f) {
      return f;
   }
}
