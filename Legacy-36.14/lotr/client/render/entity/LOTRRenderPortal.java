package lotr.client.render.entity;

import lotr.client.model.LOTRModelPortal;
import lotr.common.entity.item.LOTREntityPortal;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class LOTRRenderPortal extends Render {
   public static ResourceLocation ringTexture = new ResourceLocation("lotr:misc/portal.png");
   public static ResourceLocation writingTexture = new ResourceLocation("lotr:misc/portal_writing.png");
   public static ModelBase ringModel = new LOTRModelPortal(false);
   public static ModelBase writingModelOuter = new LOTRModelPortal(true);
   public static ModelBase writingModelInner = new LOTRModelPortal(true);

   protected ResourceLocation func_110775_a(Entity entity) {
      return ringTexture;
   }

   public void func_76986_a(Entity entity, double d, double d1, double d2, float f, float f1) {
      LOTREntityPortal portal = (LOTREntityPortal)entity;
      GL11.glPushMatrix();
      GL11.glDisable(2884);
      GL11.glTranslatef((float)d, (float)d1 + 0.75F, (float)d2);
      GL11.glNormal3f(0.0F, 0.0F, 0.0F);
      GL11.glEnable(32826);
      GL11.glScalef(1.0F, -1.0F, 1.0F);
      float portalScale = (float)portal.getScale();
      if (portalScale < (float)LOTREntityPortal.MAX_SCALE) {
         portalScale += f1;
         portalScale /= (float)LOTREntityPortal.MAX_SCALE;
         GL11.glScalef(portalScale, portalScale, portalScale);
      }

      GL11.glRotatef(portal.getPortalRotation(f1), 0.0F, 1.0F, 0.0F);
      GL11.glRotatef(10.0F, 1.0F, 0.0F, 0.0F);
      this.func_110776_a(this.func_110775_a(portal));
      float scale = 0.0625F;
      ringModel.func_78088_a((Entity)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, scale);
      GL11.glDisable(2896);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      Tessellator.field_78398_a.func_78380_c(15728880);
      this.func_110776_a(writingTexture);
      writingModelOuter.func_78088_a((Entity)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, scale * 1.05F);
      writingModelInner.func_78088_a((Entity)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, scale * 0.85F);
      GL11.glEnable(2896);
      GL11.glDisable(32826);
      GL11.glEnable(2884);
      GL11.glPopMatrix();
   }
}
