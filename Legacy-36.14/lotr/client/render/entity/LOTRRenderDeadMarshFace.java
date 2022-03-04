package lotr.client.render.entity;

import lotr.client.fx.LOTREntityDeadMarshFace;
import lotr.client.model.LOTRModelMarshWraith;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class LOTRRenderDeadMarshFace extends Render {
   private static ResourceLocation skin = new ResourceLocation("lotr:mob/wraith/marshWraith.png");
   private ModelBase model = new LOTRModelMarshWraith();

   protected ResourceLocation func_110775_a(Entity entity) {
      return skin;
   }

   public void func_76986_a(Entity entity, double d, double d1, double d2, float f, float f1) {
      LOTREntityDeadMarshFace face = (LOTREntityDeadMarshFace)entity;
      GL11.glPushMatrix();
      GL11.glTranslatef((float)d, (float)d1, (float)d2);
      GL11.glEnable(32826);
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, face.faceAlpha);
      GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
      GL11.glRotatef(face.field_70177_z, 0.0F, 0.0F, 1.0F);
      GL11.glRotatef(face.field_70125_A, 0.0F, 1.0F, 0.0F);
      this.func_110777_b(face);
      this.model.func_78088_a((Entity)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
      GL11.glDisable(3042);
      GL11.glDisable(32826);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glPopMatrix();
   }
}
