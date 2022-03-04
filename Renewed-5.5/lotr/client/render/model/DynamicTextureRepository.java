package lotr.client.render.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.Map.Entry;
import lotr.client.render.model.connectedtex.ConnectedTextureElement;
import lotr.client.render.model.connectedtex.TextureConnectionProperties;
import lotr.common.LOTRLog;
import lotr.common.item.VesselType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.MissingTextureSprite;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.resources.IResourceManager;
import net.minecraft.resources.ResourcePackType;
import net.minecraft.resources.SimpleReloadableResourceManager;
import net.minecraft.util.ResourceLocation;

public class DynamicTextureRepository {
   public static final DynamicTextureRepository INSTANCE = new DynamicTextureRepository();
   private final Map virtualPacks = new HashMap();
   private final Map cachedConnectedLocations = new HashMap();
   private final Map cachedVesselLocations = new HashMap();
   private static final int VESSEL_FILL_COLOR = 16711935;

   private DynamicTextureRepository() {
   }

   public ResourceLocation getConnectedTexture(TextureConnectionProperties textureProperties, Set elements) {
      ResourceLocation cacheKey = textureProperties.getCanonicalCacheKey();
      return !this.cachedConnectedLocations.containsKey(cacheKey) ? MissingTextureSprite.func_195675_b() : getConnectedTextureLocation(textureProperties.getBaseTextureName(), elements);
   }

   private static ResourceLocation getConnectedTextureLocation(ResourceLocation base, Set elements) {
      int key = ConnectedTextureElement.getIconSetKey(elements);
      return new ResourceLocation(base.func_110624_b(), base.func_110623_a() + "_" + key);
   }

   public List generateAllConnectedTextures(TextureConnectionProperties textureProperties) {
      Minecraft mc = Minecraft.func_71410_x();
      IResourceManager resMgr = mc.func_195551_G();
      ResourceLocation base = textureProperties.getBaseTextureName();
      boolean includeBase = textureProperties.includeBaseElement();
      String namespace = base.func_110624_b();
      ResourceLocation cacheKeyLocation = textureProperties.getCanonicalCacheKey();
      if (this.cachedConnectedLocations.containsKey(cacheKeyLocation)) {
         boolean checkResourceExists = resMgr.func_219533_b(DynamicTextureResourcePack.createDynamicTextureSetIsLoadedMarker(cacheKeyLocation));
         if (checkResourceExists) {
            return (List)this.cachedConnectedLocations.get(cacheKeyLocation);
         }
      }

      ArrayList allList = new ArrayList();

      try {
         ResourceLocation baseTextureFullPath = convertTextureFullPath(base);
         NativeImage baseImage = NativeImage.func_195713_a(resMgr.func_199002_a(baseTextureFullPath).func_199027_b());
         int iconWidth = baseImage.func_195702_a();
         int iconHeight = baseImage.func_195714_b();
         Map elementImages = new HashMap();
         ConnectedTextureElement[] var14 = ConnectedTextureElement.values();
         int var15 = var14.length;

         NativeImage connectedImage;
         for(int var16 = 0; var16 < var15; ++var16) {
            ConnectedTextureElement elem = var14[var16];
            if (elem == ConnectedTextureElement.BASE) {
               if (includeBase) {
                  elementImages.put(elem, baseImage);
               }
            } else if (textureProperties.makeFromSingleIcon()) {
               elementImages.put(elem, this.makePartFromSingleIcon(baseImage, elem));
            } else {
               Optional optElementPath = textureProperties.getElementIconPath(elem);
               if (optElementPath.isPresent()) {
                  ResourceLocation elementPath = convertTextureFullPath((ResourceLocation)optElementPath.get());
                  connectedImage = NativeImage.func_195713_a(resMgr.func_199002_a(elementPath).func_199027_b());
                  if (connectedImage.func_195702_a() == iconWidth && connectedImage.func_195714_b() == iconHeight) {
                     elementImages.put(elem, connectedImage);
                  } else {
                     LOTRLog.error("All connected texture icons for %s must have the same dimensions!", base);
                     LOTRLog.error("%s: base icon is %dx%d, but %s icon is %dx%d", base, iconWidth, iconHeight, elem.elementName, connectedImage.func_195702_a(), connectedImage.func_195714_b());
                     elementImages.put(elem, createErroredImage(iconWidth, iconHeight));
                  }
               }
            }
         }

         Map permutationSet = includeBase ? ConnectedTextureElement.ALL_COMBINATIONS_WITH_BASE : ConnectedTextureElement.ALL_COMBINATIONS_WITHOUT_BASE;
         Iterator var31 = permutationSet.entrySet().iterator();

         label84:
         while(var31.hasNext()) {
            Entry entry = (Entry)var31.next();
            int key = (Integer)entry.getKey();
            Set elementSet = (Set)entry.getValue();
            List sortedList = ConnectedTextureElement.sortIconSet(elementSet);
            connectedImage = new NativeImage(baseImage.func_211678_c(), iconWidth, iconHeight, true);
            if (includeBase) {
               connectedImage.func_195703_a(baseImage);
            }

            Iterator var21 = sortedList.iterator();

            while(true) {
               NativeImage elementImage;
               do {
                  ConnectedTextureElement elem;
                  do {
                     if (!var21.hasNext()) {
                        DynamicTexture dynamic = new DynamicTexture(connectedImage);
                        ResourceLocation connectedRes = getConnectedTextureLocation(base, elementSet);
                        ResourceLocation connectedFullPath = convertTextureFullPath(connectedRes);
                        mc.func_110434_K().func_229263_a_(connectedFullPath, dynamic);
                        this.getVirtualPack(namespace).addDynamicTexture(cacheKeyLocation, connectedFullPath, dynamic);
                        allList.add(connectedRes);
                        continue label84;
                     }

                     elem = (ConnectedTextureElement)var21.next();
                  } while(elem == ConnectedTextureElement.BASE);

                  elementImage = (NativeImage)elementImages.get(elem);
               } while(elementImage == null);

               for(int x = 0; x < connectedImage.func_195702_a(); ++x) {
                  for(int y = 0; y < connectedImage.func_195714_b(); ++y) {
                     int rgba = elementImage.func_195709_a(x, y);
                     int alpha = rgba >> 24 & 255;
                     if (alpha != 0) {
                        connectedImage.func_195700_a(x, y, rgba);
                     }
                  }
               }
            }
         }
      } catch (IOException var28) {
         LOTRLog.error("Error generating connected textures for %s", cacheKeyLocation);
         var28.printStackTrace();
      }

      this.cachedConnectedLocations.put(cacheKeyLocation, allList);
      return allList;
   }

   private NativeImage makePartFromSingleIcon(NativeImage baseImage, ConnectedTextureElement elem) {
      int sideWidth = true;
      int sideHeight = true;
      int fullWidth = true;
      int fullHeight = true;
      switch(elem) {
      case SIDE_LEFT:
         return this.copyAreaFromIcon(baseImage, 0.0D, 0.0D, 3.0D, 16.0D);
      case SIDE_RIGHT:
         return this.copyAreaFromIcon(baseImage, 13.0D, 0.0D, 16.0D, 16.0D);
      case SIDE_TOP:
         return this.copyAreaFromIcon(baseImage, 0.0D, 0.0D, 16.0D, 3.0D);
      case SIDE_BOTTOM:
         return this.copyAreaFromIcon(baseImage, 0.0D, 13.0D, 16.0D, 16.0D);
      case CORNER_TOPLEFT:
      case INVCORNER_TOPLEFT:
         return this.copyAreaFromIcon(baseImage, 0.0D, 0.0D, 3.0D, 3.0D);
      case CORNER_TOPRIGHT:
      case INVCORNER_TOPRIGHT:
         return this.copyAreaFromIcon(baseImage, 13.0D, 0.0D, 16.0D, 3.0D);
      case CORNER_BOTTOMLEFT:
      case INVCORNER_BOTTOMLEFT:
         return this.copyAreaFromIcon(baseImage, 0.0D, 13.0D, 3.0D, 16.0D);
      case CORNER_BOTTOMRIGHT:
      case INVCORNER_BOTTOMRIGHT:
         return this.copyAreaFromIcon(baseImage, 13.0D, 13.0D, 3.0D, 16.0D);
      default:
         throw new IllegalArgumentException("Unknown connected texture element " + elem.toString() + "!");
      }
   }

   private NativeImage copyAreaFromIcon(NativeImage baseImage, double x0, double y0, double x1, double y1) {
      int iconWidth = baseImage.func_195702_a();
      int iconHeight = baseImage.func_195714_b();
      NativeImage elementImage = new NativeImage(baseImage.func_211678_c(), iconWidth, iconHeight, true);
      int x0I = (int)Math.round(x0 / 16.0D * (double)iconWidth);
      int x1I = (int)Math.round(x1 / 16.0D * (double)iconWidth);
      int y0I = (int)Math.round(y0 / 16.0D * (double)iconHeight);
      int y1I = (int)Math.round(y1 / 16.0D * (double)iconHeight);

      for(int y = y0I; y < y1I; ++y) {
         for(int x = x0I; x < x1I; ++x) {
            elementImage.func_195700_a(x, y, baseImage.func_195709_a(x, y));
         }
      }

      return elementImage;
   }

   public ResourceLocation getFilledVesselTexture(ResourceLocation liquidTex, VesselType vessel) {
      return !this.cachedVesselLocations.containsKey(liquidTex) ? MissingTextureSprite.func_195675_b() : getFilledVesselLocation(liquidTex, vessel);
   }

   public Map generateVesselDrinkTextures(ResourceLocation liquidTex) {
      Minecraft mc = Minecraft.func_71410_x();
      IResourceManager resMgr = mc.func_195551_G();
      String namespace = liquidTex.func_110624_b();
      if (this.cachedVesselLocations.containsKey(liquidTex)) {
         boolean checkResourceExists = resMgr.func_219533_b(DynamicTextureResourcePack.createDynamicTextureSetIsLoadedMarker(liquidTex));
         if (checkResourceExists) {
            return (Map)this.cachedVesselLocations.get(liquidTex);
         }
      }

      HashMap allMap = new HashMap();

      try {
         ResourceLocation liquidTexFullPath = convertTextureFullPath(liquidTex);
         NativeImage liquidImage = NativeImage.func_195713_a(resMgr.func_199002_a(liquidTexFullPath).func_199027_b());
         int iconWidth = liquidImage.func_195702_a();
         int iconHeight = liquidImage.func_195714_b();
         VesselType[] var10 = VesselType.values();
         int var11 = var10.length;

         for(int var12 = 0; var12 < var11; ++var12) {
            VesselType ves = var10[var12];
            ResourceLocation vesPath = convertTextureFullPath(ves.getEmptySpritePath());
            NativeImage vesImage = NativeImage.func_195713_a(resMgr.func_199002_a(vesPath).func_199027_b());
            if (iconWidth < vesImage.func_195702_a() || iconHeight < vesImage.func_195714_b()) {
               LOTRLog.error("The loaded drink liquid icon %s is too small! Must be at least the size of loaded vessel icons, and ideally 2x2x", liquidTex);
               LOTRLog.error("%s: liquid icon is %dx%d, but %s icon is %dx%d", liquidTex, iconWidth, iconHeight, ves.getEmptyIconName(), vesImage.func_195702_a(), vesImage.func_195714_b());
               vesImage = createErroredImage(iconWidth, iconHeight);
            }

            NativeImage filledDrinkImage = new NativeImage(vesImage.func_211678_c(), vesImage.func_195702_a(), vesImage.func_195714_b(), true);
            filledDrinkImage.func_195703_a(vesImage);

            for(int x = 0; x < filledDrinkImage.func_195702_a(); ++x) {
               for(int y = 0; y < filledDrinkImage.func_195714_b(); ++y) {
                  int rgb = filledDrinkImage.func_195709_a(x, y) & 16777215;
                  if (rgb == 16711935) {
                     int liquidRgba = liquidImage.func_195709_a(x, y);
                     filledDrinkImage.func_195700_a(x, y, liquidRgba);
                  }
               }
            }

            DynamicTexture dynamic = new DynamicTexture(filledDrinkImage);
            ResourceLocation filledRes = getFilledVesselLocation(liquidTex, ves);
            ResourceLocation filledFullPath = convertTextureFullPath(filledRes);
            mc.func_110434_K().func_229263_a_(filledFullPath, dynamic);
            this.getVirtualPack(namespace).addDynamicTexture(liquidTex, filledFullPath, dynamic);
            allMap.put(ves, filledRes);
         }
      } catch (IOException var21) {
         LOTRLog.error("Error generating filled vessel textures for %s", liquidTex);
         var21.printStackTrace();
      }

      this.cachedVesselLocations.put(liquidTex, allMap);
      return allMap;
   }

   private static ResourceLocation getFilledVesselLocation(ResourceLocation liquidTex, VesselType vessel) {
      return new ResourceLocation(liquidTex.func_110624_b(), liquidTex.func_110623_a() + "_" + vessel.getCodeName());
   }

   private static ResourceLocation convertTextureFullPath(ResourceLocation texture) {
      return new ResourceLocation(texture.func_110624_b(), String.format("textures/%s.png", texture.func_110623_a()));
   }

   private static NativeImage createErroredImage(int width, int height) {
      NativeImage errored = new NativeImage(width, height, true);

      for(int x = 0; x < errored.func_195702_a(); ++x) {
         for(int y = 0; y < errored.func_195714_b(); ++y) {
            int rgb = false;
            int rgb;
            if ((x + y) % 2 == 0) {
               rgb = 16711680;
            } else {
               rgb = 0;
            }

            errored.func_195700_a(x, y, -16777216 | rgb);
         }
      }

      return errored;
   }

   private DynamicTextureResourcePack getVirtualPack(String namespace) {
      Minecraft mc = Minecraft.func_71410_x();
      SimpleReloadableResourceManager resMgr = (SimpleReloadableResourceManager)mc.func_195551_G();
      DynamicTextureResourcePack pack = (DynamicTextureResourcePack)this.virtualPacks.get(namespace);
      if (pack == null || pack != null && !resMgr.func_219533_b(pack.packIsLoadedMarkerResource)) {
         pack = new DynamicTextureResourcePack(ResourcePackType.CLIENT_RESOURCES, namespace);
         this.virtualPacks.put(namespace, pack);
         resMgr.func_199021_a(pack);
      }

      return pack;
   }
}
