package lotr.client.render.entity;

import lotr.client.LOTRClientProxy;
import lotr.common.entity.projectile.LOTREntityGandalfFireball;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class LOTRRenderGandalfFireball extends Render {
   protected ResourceLocation func_110775_a(Entity entity) {
      return LOTRClientProxy.particlesTexture;
   }

   public void func_76986_a(Entity entity, double d, double d1, double d2, float f, float f1) {
      GL11.glPushMatrix();
      GL11.glTranslatef((float)d, (float)d1, (float)d2);
      GL11.glEnable(32826);
      this.func_110777_b(entity);
      Tessellator tessellator = Tessellator.field_78398_a;
      this.drawSprite(tessellator, 24 + ((LOTREntityGandalfFireball)entity).animationTick);
      GL11.glDisable(32826);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glPopMatrix();
   }

   private void drawSprite(Tessellator tessellator, int index) {
      float f = (float)(index % 8 * 16 + 0) / 128.0F;
      float f1 = (float)(index % 8 * 16 + 16) / 128.0F;
      float f2 = (float)(index / 8 * 16 + 0) / 128.0F;
      float f3 = (float)(index / 8 * 16 + 16) / 128.0F;
      float f4 = 1.0F;
      float f5 = 0.5F;
      float f6 = 0.25F;
      GL11.glRotatef(180.0F - this.field_76990_c.field_78735_i, 0.0F, 1.0F, 0.0F);
      GL11.glRotatef(-this.field_76990_c.field_78732_j, 1.0F, 0.0F, 0.0F);
      tessellator.func_78382_b();
      tessellator.func_78375_b(0.0F, 1.0F, 0.0F);
      tessellator.func_78380_c(15728880);
      tessellator.func_78374_a((double)(0.0F - f5), (double)(0.0F - f6), 0.0D, (double)f, (double)f3);
      tessellator.func_78374_a((double)(f4 - f5), (double)(0.0F - f6), 0.0D, (double)f1, (double)f3);
      tessellator.func_78374_a((double)(f4 - f5), (double)(f4 - f6), 0.0D, (double)f1, (double)f2);
      tessellator.func_78374_a((double)(0.0F - f5), (double)(f4 - f6), 0.0D, (double)f, (double)f2);
      tessellator.func_78381_a();
   }
}
