package lotr.client.render.entity;

import lotr.common.entity.projectile.LOTREntityMarshWraithBall;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class LOTRRenderWraithBall extends Render {
   private static ResourceLocation texture = new ResourceLocation("lotr:mob/wraith/marshWraith_ball.png");

   protected ResourceLocation func_110775_a(Entity entity) {
      return texture;
   }

   public void func_76986_a(Entity entity, double d, double d1, double d2, float f, float f1) {
      GL11.glPushMatrix();
      GL11.glTranslatef((float)d, (float)d1, (float)d2);
      GL11.glEnable(32826);
      this.func_110777_b(entity);
      Tessellator tessellator = Tessellator.field_78398_a;
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.75F);
      this.drawSprite(tessellator, ((LOTREntityMarshWraithBall)entity).animationTick);
      GL11.glDisable(3042);
      GL11.glDisable(32826);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glPopMatrix();
   }

   private void drawSprite(Tessellator tessellator, int index) {
      float var3 = (float)(index % 4 * 16 + 0) / 64.0F;
      float var4 = (float)(index % 4 * 16 + 16) / 64.0F;
      float var5 = (float)(index / 4 * 16 + 0) / 64.0F;
      float var6 = (float)(index / 4 * 16 + 16) / 64.0F;
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
