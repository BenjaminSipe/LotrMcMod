package lotr.client.render.entity;

import java.util.HashMap;
import java.util.Map;
import lotr.client.model.LOTRModelBanner;
import lotr.common.LOTRConfig;
import lotr.common.entity.item.LOTREntityBanner;
import lotr.common.item.LOTRItemBanner;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.culling.Frustrum;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class LOTRRenderBanner extends Render {
   private static Map bannerTextures = new HashMap();
   private static ResourceLocation standTexture = new ResourceLocation("lotr:item/banner/stand.png");
   private static LOTRModelBanner model = new LOTRModelBanner();
   private static Frustrum bannerFrustum = new Frustrum();

   protected ResourceLocation func_110775_a(Entity entity) {
      return this.getStandTexture(entity);
   }

   public static ResourceLocation getStandTexture(LOTRItemBanner.BannerType type) {
      return standTexture;
   }

   private ResourceLocation getStandTexture(Entity entity) {
      LOTREntityBanner banner = (LOTREntityBanner)entity;
      return getStandTexture(banner.getBannerType());
   }

   public static ResourceLocation getBannerTexture(LOTRItemBanner.BannerType type) {
      ResourceLocation r = (ResourceLocation)bannerTextures.get(type);
      if (r == null) {
         r = new ResourceLocation("lotr:item/banner/banner_" + type.bannerName + ".png");
         bannerTextures.put(type, r);
      }

      return r;
   }

   private ResourceLocation getBannerTexture(Entity entity) {
      LOTREntityBanner banner = (LOTREntityBanner)entity;
      return getBannerTexture(banner.getBannerType());
   }

   public void func_76986_a(Entity entity, double d, double d1, double d2, float f, float f1) {
      LOTREntityBanner banner = (LOTREntityBanner)entity;
      Minecraft mc = Minecraft.func_71410_x();
      boolean debug = mc.field_71474_y.field_74330_P;
      boolean protecting = banner.isProtectingTerritory();
      boolean renderBox = debug && protecting;
      boolean seeThroughWalls = renderBox && LOTRConfig.showPermittedBannerSilhouettes && (mc.field_71439_g.field_71075_bZ.field_75098_d || banner.clientside_playerHasPermissionInSurvival());
      int protectColor = '\uff00';
      bannerFrustum.func_78547_a(d + RenderManager.field_78725_b, d1 + RenderManager.field_78726_c, d2 + RenderManager.field_78723_d);
      int light;
      int lx;
      int ly;
      if (bannerFrustum.func_78546_a(banner.field_70121_D)) {
         GL11.glPushMatrix();
         GL11.glDisable(2884);
         GL11.glTranslatef((float)d, (float)d1 + 1.5F, (float)d2);
         GL11.glScalef(-1.0F, -1.0F, 1.0F);
         GL11.glRotatef(180.0F - entity.field_70177_z, 0.0F, 1.0F, 0.0F);
         GL11.glTranslatef(0.0F, 0.01F, 0.0F);
         if (seeThroughWalls) {
            GL11.glDisable(2929);
            GL11.glDisable(3553);
            GL11.glDisable(2896);
            light = 15728880;
            lx = light % 65536;
            ly = light / 65536;
            OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, (float)lx / 1.0F, (float)ly / 1.0F);
            GL11.glColor4f((float)(protectColor >> 16 & 255) / 255.0F, (float)(protectColor >> 8 & 255) / 255.0F, (float)(protectColor >> 0 & 255) / 255.0F, 1.0F);
         }

         this.func_110776_a(this.getStandTexture(entity));
         model.renderStand(0.0625F);
         model.renderPost(0.0625F);
         this.func_110776_a(this.getBannerTexture(entity));
         model.renderBanner(0.0625F);
         if (seeThroughWalls) {
            GL11.glEnable(2929);
            GL11.glEnable(3553);
            GL11.glEnable(2896);
         }

         GL11.glEnable(2884);
         GL11.glPopMatrix();
      }

      if (renderBox) {
         GL11.glPushMatrix();
         GL11.glDepthMask(false);
         GL11.glDisable(3553);
         GL11.glDisable(2884);
         GL11.glDisable(3042);
         light = 15728880;
         lx = light % 65536;
         ly = light / 65536;
         OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, (float)lx / 1.0F, (float)ly / 1.0F);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         GL11.glDisable(2896);
         float width = entity.field_70130_N / 2.0F;
         AxisAlignedBB aabb = banner.createProtectionCube().func_72317_d(-RenderManager.field_78725_b, -RenderManager.field_78726_c, -RenderManager.field_78723_d);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         RenderGlobal.func_147590_a(aabb, protectColor);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         GL11.glEnable(2896);
         GL11.glEnable(3553);
         GL11.glEnable(2884);
         GL11.glDisable(3042);
         GL11.glDepthMask(true);
         GL11.glPopMatrix();
      }

   }
}
