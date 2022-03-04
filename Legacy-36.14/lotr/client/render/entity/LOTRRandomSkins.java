package lotr.client.render.entity;

import cpw.mods.fml.common.FMLLog;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.imageio.ImageIO;
import lotr.client.LOTRTextures;
import lotr.common.entity.LOTRRandomSkinEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class LOTRRandomSkins implements IResourceManagerReloadListener {
   private static Random rand = new Random();
   private static Minecraft mc = Minecraft.func_71410_x();
   private static Map allRandomSkins = new HashMap();
   protected String skinPath;
   protected List skins;

   public static LOTRRandomSkins loadSkinsList(String path) {
      LOTRRandomSkins skins = (LOTRRandomSkins)allRandomSkins.get(path);
      if (skins == null) {
         skins = new LOTRRandomSkins(path, true, new Object[0]);
         allRandomSkins.put(path, skins);
      }

      return skins;
   }

   private LOTRRandomSkins(String path, boolean register, Object... args) {
      this.skinPath = path;
      this.handleExtraArgs(args);
      if (register) {
         ((IReloadableResourceManager)mc.func_110442_L()).func_110542_a(this);
      } else {
         this.loadAllRandomSkins();
      }

   }

   protected void handleExtraArgs(Object... args) {
   }

   protected void loadAllRandomSkins() {
      this.skins = new ArrayList();
      int skinCount = 0;
      int skips = 0;
      int maxSkips = 10;
      boolean foundAfterSkip = false;

      while(true) {
         ResourceLocation skin = new ResourceLocation(this.skinPath + "/" + skinCount + ".png");
         boolean noFile = false;

         try {
            if (mc.func_110442_L().func_110536_a(skin) == null) {
               noFile = true;
            }
         } catch (Exception var8) {
            noFile = true;
         }

         if (noFile) {
            ++skips;
            if (skips >= maxSkips) {
               if (this.skins.isEmpty()) {
                  FMLLog.warning("LOTR: No random skins for %s", new Object[]{this.skinPath});
               }

               if (foundAfterSkip) {
                  FMLLog.warning("LOTR: Random skins %s skipped a number. This is not good - please number your skins from 0 and upwards, with no gaps!", new Object[]{this.skinPath});
               }

               return;
            }

            ++skinCount;
         } else {
            this.skins.add(skin);
            ++skinCount;
            if (skips > 0) {
               foundAfterSkip = true;
            }
         }
      }
   }

   public ResourceLocation getRandomSkin(LOTRRandomSkinEntity rsEntity) {
      if (this.skins != null && !this.skins.isEmpty()) {
         Entity entity = (Entity)rsEntity;
         long l = entity.func_110124_au().getLeastSignificantBits();
         long hash = (long)this.skinPath.hashCode();
         l = l * 39603773L ^ l * 6583690632L ^ hash;
         l = l * hash * 2906920L + l * 65936063L;
         rand.setSeed(l);
         int i = rand.nextInt(this.skins.size());
         return (ResourceLocation)this.skins.get(i);
      } else {
         return LOTRTextures.missingTexture;
      }
   }

   public ResourceLocation getRandomSkin() {
      int i = rand.nextInt(this.skins.size());
      return (ResourceLocation)this.skins.get(i);
   }

   public static int nextInt(LOTRRandomSkinEntity rsEntity, int n) {
      Entity entity = (Entity)rsEntity;
      long l = entity.func_110124_au().getLeastSignificantBits();
      l = l * 29506206L * (l ^ 105028696L) + 25859L;
      l = l * l * 426430295004L + 25925025L * l;
      rand.setSeed(l);
      return rand.nextInt(n);
   }

   public List getAllSkins() {
      return this.skins;
   }

   public void func_110549_a(IResourceManager resourcemanager) {
      this.loadAllRandomSkins();
   }

   public static LOTRRandomSkins getCombinatorialSkins(String path, String... layers) {
      String combinedPath = path;
      String[] var3 = layers;
      int var4 = layers.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         String s = var3[var5];
         combinedPath = combinedPath + "_" + s;
      }

      LOTRRandomSkins skins = (LOTRRandomSkins)allRandomSkins.get(combinedPath);
      if (skins == null) {
         skins = new LOTRRandomSkins.LOTRRandomSkinsCombinatorial(path, layers);
         allRandomSkins.put(combinedPath, skins);
      }

      return (LOTRRandomSkins)skins;
   }

   // $FF: synthetic method
   LOTRRandomSkins(String x0, boolean x1, Object[] x2, Object x3) {
      this(x0, x1, x2);
   }

   private static class LOTRRandomSkinsCombinatorial extends LOTRRandomSkins {
      private String[] skinLayers;

      private LOTRRandomSkinsCombinatorial(String path, String... layers) {
         super(path, true, (Object[])layers, null);
      }

      protected void handleExtraArgs(Object... args) {
         this.skinLayers = (String[])((String[])args);
      }

      protected void loadAllRandomSkins() {
         this.skins = new ArrayList();
         List layeredImages = new ArrayList();
         List tempLayered = new ArrayList();
         String[] var3 = this.skinLayers;
         int var4 = var3.length;

         label70:
         for(int var5 = 0; var5 < var4; ++var5) {
            String layer = var3[var5];
            String layerPath = this.skinPath + "/" + layer;
            LOTRRandomSkins layerSkins = new LOTRRandomSkins(layerPath, false, new Object[0]);
            tempLayered.clear();
            Iterator var9 = layerSkins.getAllSkins().iterator();

            while(var9.hasNext()) {
               ResourceLocation overlay = (ResourceLocation)var9.next();

               try {
                  BufferedImage overlayImage = ImageIO.read(LOTRRandomSkins.mc.func_110442_L().func_110536_a(overlay).func_110527_b());
                  if (layeredImages.isEmpty()) {
                     tempLayered.add(overlayImage);
                  } else {
                     Iterator var12 = layeredImages.iterator();

                     while(var12.hasNext()) {
                        BufferedImage baseImage = (BufferedImage)var12.next();
                        int skinWidth = baseImage.getWidth();
                        int skinHeight = baseImage.getHeight();
                        BufferedImage newImage = new BufferedImage(skinWidth, skinHeight, 2);

                        for(int i = 0; i < skinWidth; ++i) {
                           for(int j = 0; j < skinHeight; ++j) {
                              int baseRGB = baseImage.getRGB(i, j);
                              int overlayRGB = overlayImage.getRGB(i, j);
                              int opaqueTest = -16777216;
                              if ((overlayRGB & opaqueTest) == opaqueTest) {
                                 newImage.setRGB(i, j, overlayRGB);
                              } else {
                                 newImage.setRGB(i, j, baseRGB);
                              }
                           }
                        }

                        tempLayered.add(newImage);
                     }
                  }
               } catch (IOException var22) {
                  FMLLog.severe("LOTR: Error combining skins " + this.skinPath + " on layer " + layer + "!", new Object[0]);
                  var22.printStackTrace();
                  break label70;
               }
            }

            layeredImages.clear();
            layeredImages.addAll(tempLayered);
         }

         int skinCount = 0;

         for(Iterator var24 = layeredImages.iterator(); var24.hasNext(); ++skinCount) {
            BufferedImage image = (BufferedImage)var24.next();
            ResourceLocation skin = LOTRRandomSkins.mc.field_71446_o.func_110578_a(this.skinPath + "_layered/" + skinCount + ".png", new DynamicTexture(image));
            this.skins.add(skin);
         }

         FMLLog.info("LOTR: Assembled combinatorial skins for " + this.skinPath + ": " + this.skins.size() + " skins", new Object[0]);
      }

      // $FF: synthetic method
      LOTRRandomSkinsCombinatorial(String x0, String[] x1, Object x2) {
         this(x0, x1);
      }
   }
}
