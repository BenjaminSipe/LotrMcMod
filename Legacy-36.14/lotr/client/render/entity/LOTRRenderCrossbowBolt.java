package lotr.client.render.entity;

import lotr.common.entity.projectile.LOTREntityCrossbowBolt;
import lotr.common.item.LOTRItemCrossbowBolt;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class LOTRRenderCrossbowBolt extends Render {
   private static ResourceLocation boltTexture = new ResourceLocation("lotr:item/crossbowBolt.png");

   protected ResourceLocation func_110775_a(Entity entity) {
      return boltTexture;
   }

   public void func_76986_a(Entity entity, double d, double d1, double d2, float f, float f1) {
      LOTREntityCrossbowBolt bolt = (LOTREntityCrossbowBolt)entity;
      ItemStack boltItem = bolt.getProjectileItem();
      this.func_110777_b(bolt);
      GL11.glPushMatrix();
      GL11.glTranslatef((float)d, (float)d1, (float)d2);
      GL11.glRotatef(bolt.field_70126_B + (bolt.field_70177_z - bolt.field_70126_B) * f1 - 90.0F, 0.0F, 1.0F, 0.0F);
      GL11.glRotatef(bolt.field_70127_C + (bolt.field_70125_A - bolt.field_70127_C) * f1, 0.0F, 0.0F, 1.0F);
      Tessellator tessellator = Tessellator.field_78398_a;
      int yOffset = 0;
      if (boltItem != null && boltItem.func_77973_b() instanceof LOTRItemCrossbowBolt && ((LOTRItemCrossbowBolt)boltItem.func_77973_b()).isPoisoned) {
         yOffset = 1;
      }

      float f2 = 0.0F;
      float f3 = 0.5F;
      float f4 = (float)(0 + yOffset * 10) / 32.0F;
      float f5 = (float)(5 + yOffset * 10) / 32.0F;
      float f6 = 0.0F;
      float f7 = 0.15625F;
      float f8 = (float)(5 + yOffset * 10) / 32.0F;
      float f9 = (float)(10 + yOffset * 10) / 32.0F;
      float f10 = 0.05625F;
      GL11.glEnable(32826);
      float f11 = (float)bolt.shake - f1;
      if (f11 > 0.0F) {
         float f12 = -MathHelper.func_76126_a(f11 * 3.0F) * f11;
         GL11.glRotatef(f12, 0.0F, 0.0F, 1.0F);
      }

      GL11.glRotatef(45.0F, 1.0F, 0.0F, 0.0F);
      GL11.glScalef(f10, f10, f10);
      GL11.glTranslatef(-4.0F, 0.0F, 0.0F);
      GL11.glNormal3f(f10, 0.0F, 0.0F);
      tessellator.func_78382_b();
      tessellator.func_78374_a(-7.0D, -2.0D, -2.0D, (double)f6, (double)f8);
      tessellator.func_78374_a(-7.0D, -2.0D, 2.0D, (double)f7, (double)f8);
      tessellator.func_78374_a(-7.0D, 2.0D, 2.0D, (double)f7, (double)f9);
      tessellator.func_78374_a(-7.0D, 2.0D, -2.0D, (double)f6, (double)f9);
      tessellator.func_78381_a();
      GL11.glNormal3f(-f10, 0.0F, 0.0F);
      tessellator.func_78382_b();
      tessellator.func_78374_a(-7.0D, 2.0D, -2.0D, (double)f6, (double)f8);
      tessellator.func_78374_a(-7.0D, 2.0D, 2.0D, (double)f7, (double)f8);
      tessellator.func_78374_a(-7.0D, -2.0D, 2.0D, (double)f7, (double)f9);
      tessellator.func_78374_a(-7.0D, -2.0D, -2.0D, (double)f6, (double)f9);
      tessellator.func_78381_a();

      for(int i = 0; i < 4; ++i) {
         GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
         GL11.glNormal3f(0.0F, 0.0F, f10);
         tessellator.func_78382_b();
         tessellator.func_78374_a(-8.0D, -2.0D, 0.0D, (double)f2, (double)f4);
         tessellator.func_78374_a(8.0D, -2.0D, 0.0D, (double)f3, (double)f4);
         tessellator.func_78374_a(8.0D, 2.0D, 0.0D, (double)f3, (double)f5);
         tessellator.func_78374_a(-8.0D, 2.0D, 0.0D, (double)f2, (double)f5);
         tessellator.func_78381_a();
      }

      GL11.glDisable(32826);
      GL11.glPopMatrix();
   }
}
