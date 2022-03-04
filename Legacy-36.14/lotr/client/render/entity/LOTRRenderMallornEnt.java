package lotr.client.render.entity;

import lotr.client.model.LOTRModelEnt;
import lotr.common.entity.npc.LOTREntityMallornEnt;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class LOTRRenderMallornEnt extends LOTRRenderEnt {
   private static ResourceLocation mallornEntSkin = new ResourceLocation("lotr:mob/ent/mallorn.png");
   private static ResourceLocation shieldSkin = new ResourceLocation("lotr:mob/ent/mallornEnt_shield.png");
   private LOTRModelEnt shieldModel = new LOTRModelEnt();

   public void func_76986_a(Entity entity, double d, double d1, double d2, float f, float f1) {
      super.func_76986_a(entity, d, d1, d2, f, f1);
      LOTREntityMallornEnt ent = (LOTREntityMallornEnt)entity;
      if (ent.field_70175_ag) {
         BossStatus.func_82824_a(ent, false);
      }

   }

   protected ResourceLocation func_110775_a(Entity entity) {
      return mallornEntSkin;
   }

   protected void func_77041_b(EntityLivingBase entity, float f) {
      LOTREntityMallornEnt ent = (LOTREntityMallornEnt)entity;
      float scale = LOTREntityMallornEnt.BOSS_SCALE;
      GL11.glScalef(scale, scale, scale);
      GL11.glTranslatef(0.0F, -ent.getSpawningOffset(f), 0.0F);
      if (ent.isWeaponShieldActive()) {
         OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, 240.0F, 240.0F);
      }

   }

   protected int func_77032_a(EntityLivingBase entity, int pass, float f) {
      LOTREntityMallornEnt ent = (LOTREntityMallornEnt)entity;
      if (ent.isWeaponShieldActive()) {
         if (pass == 1) {
            OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, 240.0F, 240.0F);
            GL11.glMatrixMode(5890);
            GL11.glLoadIdentity();
            float f1 = (float)ent.field_70173_aa + f;
            float f2 = f1 * 0.01F;
            float f3 = f1 * 0.01F;
            GL11.glTranslatef(f2, f3, 0.0F);
            GL11.glMatrixMode(5888);
            GL11.glAlphaFunc(516, 0.01F);
            GL11.glEnable(3042);
            GL11.glBlendFunc(1, 1);
            float alpha = 0.3F + MathHelper.func_76126_a(f1 * 0.05F) * 0.15F;
            GL11.glColor4f(alpha, alpha, alpha, 1.0F);
            GL11.glDisable(2896);
            GL11.glDepthMask(false);
            this.func_77042_a(this.shieldModel);
            this.func_110776_a(shieldSkin);
            return 1;
         }

         if (pass == 2) {
            GL11.glMatrixMode(5890);
            GL11.glLoadIdentity();
            GL11.glMatrixMode(5888);
            GL11.glAlphaFunc(516, 0.1F);
            GL11.glDisable(3042);
            GL11.glEnable(2896);
            GL11.glDepthMask(true);
         }
      }

      return -1;
   }
}
