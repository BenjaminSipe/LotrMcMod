package lotr.client.render.entity;

import lotr.client.model.LOTRModelBalrog;
import lotr.common.entity.npc.LOTREntityBalrog;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class LOTRRenderBalrog extends RenderLiving {
   private static LOTRRandomSkins balrogSkins;
   private static LOTRRandomSkins balrogSkinsBright;
   private static final ResourceLocation fireTexture = new ResourceLocation("lotr:mob/balrog/fire.png");
   private LOTRModelBalrog balrogModel;
   private LOTRModelBalrog balrogModelBright;
   private LOTRModelBalrog fireModel;

   public LOTRRenderBalrog() {
      super(new LOTRModelBalrog(), 0.5F);
      this.balrogModel = (LOTRModelBalrog)this.field_77045_g;
      this.balrogModelBright = new LOTRModelBalrog(0.05F);
      this.fireModel = new LOTRModelBalrog(0.0F);
      this.fireModel.setFireModel();
      balrogSkins = LOTRRandomSkins.loadSkinsList("lotr:mob/balrog/balrog");
      balrogSkinsBright = LOTRRandomSkins.loadSkinsList("lotr:mob/balrog/balrog_bright");
   }

   protected ResourceLocation func_110775_a(Entity entity) {
      return balrogSkins.getRandomSkin((LOTREntityBalrog)entity);
   }

   public void func_76986_a(Entity entity, double d, double d1, double d2, float f, float f1) {
      LOTREntityBalrog balrog = (LOTREntityBalrog)entity;
      ItemStack heldItem = balrog.func_70694_bm();
      this.balrogModel.heldItemRight = this.fireModel.heldItemRight = heldItem == null ? 0 : 2;
      super.func_76986_a(balrog, d, d1, d2, f, f1);
   }

   protected void func_77041_b(EntityLivingBase entity, float f) {
      LOTREntityBalrog balrog = (LOTREntityBalrog)entity;
      float scale = 2.0F;
      GL11.glScalef(scale, scale, scale);
      if (balrog.isBalrogCharging()) {
         float lean = balrog.getInterpolatedChargeLean(f);
         lean *= 35.0F;
         GL11.glRotatef(lean, 1.0F, 0.0F, 0.0F);
      }

   }

   private void setupFullBright() {
      int light = 15728880;
      int lx = light % 65536;
      int ly = light / 65536;
      OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, (float)lx / 1.0F, (float)ly / 1.0F);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
   }

   protected int func_77032_a(EntityLivingBase entity, int pass, float f) {
      LOTREntityBalrog balrog = (LOTREntityBalrog)entity;
      if (balrog.isWreathedInFlame()) {
         if (pass == 1) {
            OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, 240.0F, 240.0F);
            GL11.glMatrixMode(5890);
            GL11.glLoadIdentity();
            float f1 = (float)balrog.field_70173_aa + f;
            float f2 = f1 * 0.01F;
            float f3 = f1 * 0.01F;
            GL11.glTranslatef(f2, f3, 0.0F);
            GL11.glMatrixMode(5888);
            GL11.glAlphaFunc(516, 0.01F);
            GL11.glEnable(3042);
            GL11.glBlendFunc(1, 1);
            float alpha;
            if (balrog.isBalrogCharging()) {
               alpha = 0.6F + MathHelper.func_76126_a(f1 * 0.1F) * 0.15F;
            } else {
               alpha = 0.3F + MathHelper.func_76126_a(f1 * 0.05F) * 0.15F;
            }

            GL11.glColor4f(alpha, alpha, alpha, 1.0F);
            GL11.glDisable(2896);
            GL11.glDepthMask(false);
            this.func_77042_a(this.fireModel);
            this.func_110776_a(fireTexture);
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
            GL11.glDisable(2896);
            this.setupFullBright();
            this.func_77042_a(this.balrogModelBright);
            this.func_110776_a(balrogSkinsBright.getRandomSkin(balrog));
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            return 1;
         }

         if (pass == 3) {
            GL11.glEnable(2896);
            GL11.glDisable(3042);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         }
      }

      return -1;
   }

   protected void func_77029_c(EntityLivingBase entity, float f) {
      GL11.glColor3f(1.0F, 1.0F, 1.0F);
      ItemStack heldItem = entity.func_70694_bm();
      if (heldItem != null) {
         GL11.glPushMatrix();
         this.balrogModel.body.func_78794_c(0.0625F);
         this.balrogModel.rightArm.func_78794_c(0.0625F);
         GL11.glTranslatef(-0.25F, 1.5F, -0.125F);
         float scale = 1.25F;
         GL11.glScalef(scale, -scale, scale);
         GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
         GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
         this.field_76990_c.field_78721_f.func_78443_a(entity, heldItem, 0);
         if (heldItem.func_77973_b().func_77623_v()) {
            for(int x = 1; x < heldItem.func_77973_b().getRenderPasses(heldItem.func_77960_j()); ++x) {
               this.field_76990_c.field_78721_f.func_78443_a(entity, heldItem, x);
            }
         }

         GL11.glPopMatrix();
      }

   }
}
