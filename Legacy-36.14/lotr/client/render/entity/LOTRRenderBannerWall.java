package lotr.client.render.entity;

import lotr.client.model.LOTRModelBannerWall;
import lotr.common.entity.item.LOTREntityBannerWall;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class LOTRRenderBannerWall extends Render {
   private static LOTRModelBannerWall model = new LOTRModelBannerWall();

   protected ResourceLocation func_110775_a(Entity entity) {
      LOTREntityBannerWall banner = (LOTREntityBannerWall)entity;
      return LOTRRenderBanner.getStandTexture(banner.getBannerType());
   }

   public void func_76986_a(Entity entity, double d, double d1, double d2, float f, float f1) {
      LOTREntityBannerWall banner = (LOTREntityBannerWall)entity;
      GL11.glPushMatrix();
      GL11.glDisable(2884);
      GL11.glTranslatef((float)d, (float)d1, (float)d2);
      GL11.glScalef(-1.0F, -1.0F, 1.0F);
      GL11.glRotatef(f, 0.0F, 1.0F, 0.0F);
      this.func_110776_a(LOTRRenderBanner.getStandTexture(banner.getBannerType()));
      model.renderPost(0.0625F);
      this.func_110776_a(LOTRRenderBanner.getBannerTexture(banner.getBannerType()));
      model.renderBanner(0.0625F);
      GL11.glEnable(2884);
      GL11.glPopMatrix();
      if (RenderManager.field_85095_o) {
         GL11.glPushMatrix();
         GL11.glDepthMask(false);
         GL11.glDisable(3553);
         GL11.glDisable(2896);
         GL11.glDisable(2884);
         GL11.glDisable(3042);
         float width = entity.field_70130_N / 2.0F;
         AxisAlignedBB aabb = banner.field_70121_D.func_72329_c().func_72317_d(-RenderManager.field_78725_b, -RenderManager.field_78726_c, -RenderManager.field_78723_d);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         RenderGlobal.func_147590_a(aabb, 16777215);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         GL11.glEnable(3553);
         GL11.glEnable(2896);
         GL11.glEnable(2884);
         GL11.glDisable(3042);
         GL11.glDepthMask(true);
         GL11.glPopMatrix();
      }

   }
}
