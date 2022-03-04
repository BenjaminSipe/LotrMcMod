package lotr.client.render.entity;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import lotr.client.model.LOTRModelCamel;
import lotr.common.entity.animal.LOTREntityCamel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class LOTRRenderCamel extends RenderLiving {
   private static ResourceLocation camelSkin = new ResourceLocation("lotr:mob/camel/camel.png");
   private static ResourceLocation saddleTexture = new ResourceLocation("lotr:mob/camel/saddle.png");
   private static ResourceLocation carpetBase = new ResourceLocation("lotr:mob/camel/carpet_base.png");
   private static ResourceLocation carpetOverlay = new ResourceLocation("lotr:mob/camel/carpet_overlay.png");
   private LOTRModelCamel modelSaddle = new LOTRModelCamel(0.5F);
   private LOTRModelCamel modelCarpet = new LOTRModelCamel(0.55F);
   private static Map coloredCarpetTextures = new HashMap();

   public LOTRRenderCamel() {
      super(new LOTRModelCamel(), 0.5F);
      this.func_77042_a(this.modelSaddle);
   }

   protected ResourceLocation func_110775_a(Entity entity) {
      LOTREntityCamel camel = (LOTREntityCamel)entity;
      return LOTRRenderHorse.getLayeredMountTexture(camel, camelSkin);
   }

   protected int func_77032_a(EntityLivingBase entity, int pass, float f) {
      LOTREntityCamel camel = (LOTREntityCamel)entity;
      if (pass == 0 && camel.isMountSaddled()) {
         this.func_77042_a(this.modelSaddle);
         this.func_110776_a(saddleTexture);
         return 1;
      } else if (pass == 1 && camel.isCamelWearingCarpet()) {
         this.func_77042_a(this.modelCarpet);
         int color = camel.getCamelCarpetColor();
         ResourceLocation carpet = getColoredCarpetTexture(color);
         this.func_110776_a(carpet);
         return 1;
      } else {
         return super.func_77032_a(entity, pass, f);
      }
   }

   protected void func_77041_b(EntityLivingBase entity, float f) {
      GL11.glScalef(1.25F, 1.25F, 1.25F);
   }

   public static ResourceLocation getColoredCarpetTexture(int carpetRGB) {
      String path = "lotr:camel_carpet_0x" + Integer.toHexString(carpetRGB);
      ResourceLocation res = (ResourceLocation)coloredCarpetTextures.get(path);
      if (res == null) {
         try {
            Minecraft mc = Minecraft.func_71410_x();
            InputStream isBase = mc.func_110442_L().func_110536_a(carpetBase).func_110527_b();
            BufferedImage imgBase = ImageIO.read(isBase);
            InputStream isOverlay = mc.func_110442_L().func_110536_a(carpetOverlay).func_110527_b();
            BufferedImage imgOverlay = ImageIO.read(isOverlay);
            BufferedImage imgDyed = new BufferedImage(imgBase.getWidth(), imgBase.getHeight(), 2);
            int carpetR = carpetRGB >> 16 & 255;
            int carpetG = carpetRGB >> 8 & 255;
            int carpetB = carpetRGB & 255;

            for(int i = 0; i < imgDyed.getWidth(); ++i) {
               for(int j = 0; j < imgDyed.getHeight(); ++j) {
                  int argbOverlay = imgOverlay.getRGB(i, j);
                  int aOverlay = argbOverlay >> 24 & 255;
                  if (aOverlay > 0) {
                     imgDyed.setRGB(i, j, argbOverlay);
                  } else {
                     int argb = imgBase.getRGB(i, j);
                     int a = argb >> 24 & 255;
                     int r = argb >> 16 & 255;
                     int g = argb >> 8 & 255;
                     int b = argb & 255;
                     r = r * carpetR / 255;
                     g = g * carpetG / 255;
                     b = b * carpetB / 255;
                     int dyed = a << 24 | r << 16 | g << 8 | b;
                     imgDyed.setRGB(i, j, dyed);
                  }
               }
            }

            res = mc.field_71446_o.func_110578_a(path, new DynamicTexture(imgDyed));
         } catch (IOException var22) {
            System.out.println("LOTR: Error generating coloured camel carpet texture");
            var22.printStackTrace();
            res = carpetBase;
         }

         coloredCarpetTextures.put(path, res);
      }

      return res;
   }
}
