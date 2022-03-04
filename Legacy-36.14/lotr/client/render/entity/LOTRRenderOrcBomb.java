package lotr.client.render.entity;

import lotr.common.LOTRMod;
import lotr.common.entity.item.LOTREntityOrcBomb;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class LOTRRenderOrcBomb extends Render {
   private RenderBlocks blockRenderer = new RenderBlocks();

   public LOTRRenderOrcBomb() {
      this.field_76989_e = 0.5F;
   }

   protected ResourceLocation func_110775_a(Entity entity) {
      return TextureMap.field_110575_b;
   }

   public void func_76986_a(Entity entity, double d, double d1, double d2, float f, float f1) {
      LOTREntityOrcBomb bomb = (LOTREntityOrcBomb)entity;
      this.renderBomb(bomb, d, d1, d2, f1, bomb.orcBombFuse, bomb.getBombStrengthLevel(), 1.0F, entity.func_70013_c(f1));
   }

   public void renderBomb(Entity entity, double d, double d1, double d2, float ticks, int fuse, int strengthLevel, float bombScale, float brightness) {
      GL11.glPushMatrix();
      GL11.glTranslatef((float)d, (float)d1, (float)d2);
      GL11.glScalef(bombScale, bombScale, bombScale);
      float f2;
      if ((float)fuse - ticks + 1.0F < 10.0F) {
         f2 = 1.0F - ((float)fuse - ticks + 1.0F) / 10.0F;
         if (f2 < 0.0F) {
            f2 = 0.0F;
         }

         if (f2 > 1.0F) {
            f2 = 1.0F;
         }

         f2 *= f2;
         f2 *= f2;
         float scale = 1.0F + f2 * 0.3F;
         GL11.glScalef(scale, scale, scale);
      }

      f2 = (1.0F - ((float)fuse - ticks + 1.0F) / 100.0F) * 0.8F;
      this.func_110777_b(entity);
      this.blockRenderer.func_147800_a(LOTRMod.orcBomb, strengthLevel, brightness);
      if (fuse / 5 % 2 == 0) {
         GL11.glDisable(3553);
         GL11.glDisable(2896);
         GL11.glEnable(3042);
         GL11.glBlendFunc(770, 772);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, f2);
         this.blockRenderer.func_147800_a(LOTRMod.orcBomb, strengthLevel, 1.0F);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         GL11.glDisable(3042);
         GL11.glEnable(2896);
         GL11.glEnable(3553);
      }

      GL11.glPopMatrix();
   }
}
