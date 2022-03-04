package lotr.client.model;

import lotr.client.render.entity.LOTRRenderPortal;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class LOTRModelCompass extends ModelBase {
   public static LOTRModelCompass compassModel = new LOTRModelCompass();
   private static ResourceLocation compassTexture = new ResourceLocation("lotr:misc/compass.png");
   private ModelRenderer compass;
   private ModelBase ringModel = new LOTRModelPortal(false);
   private ModelBase writingModelOuter = new LOTRModelPortal(true);
   private ModelBase writingModelInner = new LOTRModelPortal(true);

   private LOTRModelCompass() {
      this.field_78090_t = 32;
      this.field_78089_u = 32;
      this.compass = new ModelRenderer(this, 0, 0);
      this.compass.func_78793_a(0.0F, 0.0F, 0.0F);
      this.compass.func_78789_a(-16.0F, 0.0F, -16.0F, 32, 0, 32);
   }

   public void render(float scale, float rotation) {
      TextureManager texturemanager = Minecraft.func_71410_x().func_110434_K();
      GL11.glPushMatrix();
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glDisable(2884);
      GL11.glNormal3f(0.0F, 0.0F, 0.0F);
      GL11.glEnable(32826);
      GL11.glScalef(1.0F, 1.0F, -1.0F);
      GL11.glRotatef(40.0F, 1.0F, 0.0F, 0.0F);
      GL11.glRotatef(rotation, 0.0F, 1.0F, 0.0F);
      texturemanager.func_110577_a(compassTexture);
      this.compass.func_78785_a(scale * 2.0F);
      texturemanager.func_110577_a(LOTRRenderPortal.ringTexture);
      this.ringModel.func_78088_a((Entity)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, scale);
      texturemanager.func_110577_a(LOTRRenderPortal.writingTexture);
      this.writingModelOuter.func_78088_a((Entity)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, scale * 1.05F);
      texturemanager.func_110577_a(LOTRRenderPortal.writingTexture);
      this.writingModelInner.func_78088_a((Entity)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, scale * 0.85F);
      GL11.glDisable(32826);
      GL11.glEnable(2884);
      GL11.glPopMatrix();
   }
}
