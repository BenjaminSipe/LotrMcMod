package lotr.client.render.entity;

import lotr.common.LOTRMod;
import lotr.common.entity.projectile.LOTREntityThrownRock;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class LOTRRenderThrownRock extends Render {
   private RenderBlocks blockRenderer = new RenderBlocks();

   protected ResourceLocation func_110775_a(Entity entity) {
      return TextureMap.field_110575_b;
   }

   public void func_76986_a(Entity entity, double d, double d1, double d2, float f, float f1) {
      GL11.glEnable(32826);
      GL11.glDisable(2884);
      GL11.glPushMatrix();
      GL11.glTranslatef((float)d, (float)d1, (float)d2);
      GL11.glRotatef(f, 0.0F, 1.0F, 0.0F);
      GL11.glRotatef(entity.field_70127_C + (entity.field_70125_A - entity.field_70127_C) * f1, 1.0F, 0.0F, 0.0F);
      int i = entity.func_70070_b(f1);
      int j = i % 65536;
      int k = i / 65536;
      OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, (float)j / 1.0F, (float)k / 1.0F);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.func_110777_b(entity);
      if (((LOTREntityThrownRock)entity).getSpawnsTroll()) {
         this.blockRenderer.func_147800_a(LOTRMod.trollTotem, 0, 1.0F);
      } else {
         this.blockRenderer.func_147800_a(Blocks.field_150348_b, 0, 1.0F);
      }

      GL11.glPopMatrix();
      GL11.glEnable(2884);
      GL11.glDisable(32826);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
   }
}
