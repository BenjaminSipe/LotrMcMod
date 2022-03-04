package lotr.client.render.entity;

import lotr.client.render.LOTRRenderBlocks;
import lotr.common.entity.projectile.LOTREntityPlate;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class LOTRRenderPlate extends Render {
   private RenderBlocks blockRenderer = new RenderBlocks();

   protected ResourceLocation func_110775_a(Entity entity) {
      return TextureMap.field_110575_b;
   }

   public void func_76986_a(Entity entity, double d, double d1, double d2, float f, float f1) {
      LOTREntityPlate plate = (LOTREntityPlate)entity;
      GL11.glEnable(32826);
      GL11.glPushMatrix();
      GL11.glTranslatef((float)d, (float)d1, (float)d2);
      GL11.glRotatef(-f, 0.0F, 1.0F, 0.0F);
      int i = entity.func_70070_b(f1);
      int j = i % 65536;
      int k = i / 65536;
      OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, (float)j / 1.0F, (float)k / 1.0F);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.func_110777_b(entity);
      LOTRRenderBlocks.renderEntityPlate(entity.field_70170_p, 0, 0, 0, plate.getPlateBlock(), this.blockRenderer);
      GL11.glPopMatrix();
      GL11.glDisable(32826);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
   }
}
