package lotr.client.render.entity;

import lotr.client.LOTRClientProxy;
import lotr.client.model.LOTRModelSmokeShip;
import lotr.common.entity.projectile.LOTREntitySmokeRing;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class LOTRRenderSmokeRing extends Render {
   private ModelBase magicShipModel = new LOTRModelSmokeShip();

   protected ResourceLocation func_110775_a(Entity entity) {
      return LOTRClientProxy.particlesTexture;
   }

   public void func_76986_a(Entity entity, double d, double d1, double d2, float f, float f1) {
      Tessellator tessellator = Tessellator.field_78398_a;
      GL11.glPushMatrix();
      GL11.glTranslatef((float)d, (float)d1, (float)d2);
      GL11.glEnable(32826);
      GL11.glEnable(2977);
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      LOTREntitySmokeRing smokeRing = (LOTREntitySmokeRing)entity;
      float age = smokeRing.getRenderSmokeAge(f1);
      float opacity = 1.0F - age;
      int colour = smokeRing.getSmokeColour();
      if (colour == 16) {
         GL11.glDisable(3553);
         GL11.glDisable(2884);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, opacity * 0.75F);
         GL11.glScalef(0.3F, -0.3F, 0.3F);
         GL11.glRotatef(entity.field_70126_B + (entity.field_70177_z - entity.field_70126_B) * f1 - 90.0F, 0.0F, 1.0F, 0.0F);
         GL11.glRotatef(entity.field_70127_C + (entity.field_70125_A - entity.field_70127_C) * f1, 0.0F, 0.0F, -1.0F);
         GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
         this.magicShipModel.func_78088_a((Entity)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
         GL11.glEnable(2884);
         GL11.glEnable(3553);
      } else {
         float[] colours = EntitySheep.field_70898_d[colour];
         GL11.glColor4f(colours[0], colours[1], colours[2], opacity);
         this.func_110777_b(entity);
         float smokeSize = 0.1F + 0.9F * age;
         GL11.glScalef(smokeSize, smokeSize, smokeSize);
         this.drawSprite(tessellator, 0);
      }

      GL11.glDisable(3042);
      GL11.glDisable(32826);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glPopMatrix();
   }

   private void drawSprite(Tessellator tessellator, int index) {
      float var3 = (float)(index % 16 * 16 + 0) / 128.0F;
      float var4 = (float)(index % 16 * 16 + 16) / 128.0F;
      float var5 = (float)(index / 16 * 16 + 0) / 128.0F;
      float var6 = (float)(index / 16 * 16 + 16) / 128.0F;
      float var7 = 1.0F;
      float var8 = 0.5F;
      float var9 = 0.25F;
      GL11.glRotatef(180.0F - this.field_76990_c.field_78735_i, 0.0F, 1.0F, 0.0F);
      GL11.glRotatef(-this.field_76990_c.field_78732_j, 1.0F, 0.0F, 0.0F);
      tessellator.func_78382_b();
      tessellator.func_78375_b(0.0F, 1.0F, 0.0F);
      tessellator.func_78374_a((double)(0.0F - var8), (double)(0.0F - var9), 0.0D, (double)var3, (double)var6);
      tessellator.func_78374_a((double)(var7 - var8), (double)(0.0F - var9), 0.0D, (double)var4, (double)var6);
      tessellator.func_78374_a((double)(var7 - var8), (double)(var7 - var9), 0.0D, (double)var4, (double)var5);
      tessellator.func_78374_a((double)(0.0F - var8), (double)(var7 - var9), 0.0D, (double)var3, (double)var5);
      tessellator.func_78381_a();
   }
}
