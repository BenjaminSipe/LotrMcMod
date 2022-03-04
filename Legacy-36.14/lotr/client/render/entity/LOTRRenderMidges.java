package lotr.client.render.entity;

import lotr.client.model.LOTRModelMidge;
import lotr.common.entity.animal.LOTREntityMidges;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class LOTRRenderMidges extends RenderLiving {
   private static ResourceLocation midgeTexture = new ResourceLocation("lotr:mob/midge.png");
   private float renderTick;

   public LOTRRenderMidges() {
      super(new LOTRModelMidge(), 0.0F);
   }

   public void func_76986_a(Entity entity, double d, double d1, double d2, float f, float f1) {
      this.renderTick = f1;
      super.func_76986_a(entity, d, d1, d2, f, f1);
   }

   protected void func_77036_a(EntityLivingBase entity, float f, float f1, float f2, float f3, float f4, float f5) {
      this.func_110777_b(entity);
      this.field_77045_g.func_78087_a(f, f1, f2, f3, f4, f5, entity);
      LOTREntityMidges midges = (LOTREntityMidges)entity;

      for(int l = 0; l < midges.midges.length; ++l) {
         LOTREntityMidges.Midge midge = midges.midges[l];
         GL11.glPushMatrix();
         GL11.glTranslatef(midge.midge_prevPosX + (midge.midge_posX - midge.midge_prevPosX) * this.renderTick, midge.midge_prevPosY + (midge.midge_posY - midge.midge_prevPosY) * this.renderTick, midge.midge_prevPosZ + (midge.midge_posZ - midge.midge_prevPosZ) * this.renderTick);
         GL11.glRotatef(midge.midge_rotation, 0.0F, 1.0F, 0.0F);
         GL11.glScalef(0.2F, 0.2F, 0.2F);
         this.field_77045_g.func_78088_a(entity, f, f1, f2, f3, f4, f5);
         GL11.glPopMatrix();
      }

   }

   protected ResourceLocation func_110775_a(Entity entity) {
      return midgeTexture;
   }
}
