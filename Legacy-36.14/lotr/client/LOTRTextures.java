package lotr.client;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import lotr.client.gui.LOTRGuiMap;
import lotr.client.render.LOTRBufferedImageIcon;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRConfig;
import lotr.common.LOTRDimension;
import lotr.common.LOTRLevelData;
import lotr.common.util.LOTRColorUtil;
import lotr.common.util.LOTRCommonIcons;
import lotr.common.util.LOTRLog;
import lotr.common.world.biome.LOTRBiome;
import lotr.common.world.biome.LOTRBiomeGenMordor;
import lotr.common.world.genlayer.LOTRGenLayerWorld;
import lotr.common.world.map.LOTRWaypoint;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent.Pre;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.opengl.GL11;

public class LOTRTextures implements IResourceManagerReloadListener {
   private static Minecraft mc = Minecraft.func_71410_x();
   public static ResourceLocation missingTexture;
   private static ResourceLocation mapTexture;
   private static ResourceLocation sepiaMapTexture;
   public static ResourceLocation overlayTexture;
   public static ResourceLocation mapTerrain;
   public static final ResourceLocation osrsTexture;
   public static final int OSRS_WATER = 6453158;
   public static final int OSRS_GRASS = 5468426;
   public static final int OSRS_BEACH = 9279778;
   public static final int OSRS_HILL = 6575407;
   public static final int OSRS_MOUNTAIN = 14736861;
   public static final int OSRS_MOUNTAIN_EDGE = 9005125;
   public static final int OSRS_SNOW = 14215139;
   public static final int OSRS_TUNDRA = 9470587;
   public static final int OSRS_SAND = 13548147;
   public static final int OSRS_TREE = 2775058;
   public static final int OSRS_WILD = 3290677;
   public static final int OSRS_PATH = 6575407;
   public static final int OSRS_KINGDOM_COLOR = 16755200;
   private static ResourceLocation particleTextures;
   private static ResourceLocation newWaterParticles;
   private static int newWaterU;
   private static int newWaterV;
   private static int newWaterWidth;
   private static int newWaterHeight;
   private static Map eyesTextures;
   private static Map averagedPageColors;

   public static void load() {
      IResourceManager resMgr = mc.func_110442_L();
      TextureManager texMgr = mc.func_110434_K();
      LOTRTextures textures = new LOTRTextures();
      textures.func_110549_a(resMgr);
      ((IReloadableResourceManager)resMgr).func_110542_a(textures);
      MinecraftForge.EVENT_BUS.register(textures);
      TextureMap texMapBlocks = (TextureMap)texMgr.func_110581_b(TextureMap.field_110575_b);
      TextureMap texMapItems = (TextureMap)texMgr.func_110581_b(TextureMap.field_110576_c);
      textures.preTextureStitch(new Pre(texMapBlocks));
      textures.preTextureStitch(new Pre(texMapItems));
   }

   public void func_110549_a(IResourceManager resourceManager) {
      loadMapTextures();
      replaceWaterParticles();
      eyesTextures.clear();
      averagedPageColors.clear();
   }

   @SubscribeEvent
   public void preTextureStitch(Pre event) {
      TextureMap map = event.map;
      if (map.func_130086_a() == 0) {
         LOTRCommonIcons.iconEmptyBlock = generateIconEmpty(map);
         LOTRCommonIcons.iconStoneSnow = map.func_94245_a("stone_snow");
      }

      if (map.func_130086_a() == 1) {
         LOTRCommonIcons.iconEmptyItem = generateIconEmpty(map);
         LOTRCommonIcons.iconMeleeWeapon = map.func_94245_a("lotr:slotMelee");
         LOTRCommonIcons.iconBomb = map.func_94245_a("lotr:slotBomb");
      }

   }

   public static void drawMap(EntityPlayer entityplayer, double x0, double x1, double y0, double y1, double z, double minU, double maxU, double minV, double maxV) {
      drawMap(entityplayer, LOTRConfig.enableSepiaMap, x0, x1, y0, y1, z, minU, maxU, minV, maxV, 1.0F);
   }

   public static void drawMap(EntityPlayer entityplayer, boolean sepia, double x0, double x1, double y0, double y1, double z, double minU, double maxU, double minV, double maxV, float alpha) {
      Tessellator tessellator = Tessellator.field_78398_a;
      mc.func_110434_K().func_110577_a(getMapTexture(entityplayer, sepia));
      GL11.glColor4f(1.0F, 1.0F, 1.0F, alpha);
      tessellator.func_78382_b();
      tessellator.func_78374_a(x0, y1, z, minU, maxV);
      tessellator.func_78374_a(x1, y1, z, maxU, maxV);
      tessellator.func_78374_a(x1, y0, z, maxU, minV);
      tessellator.func_78374_a(x0, y0, z, minU, minV);
      tessellator.func_78381_a();
      boolean meneltarma = entityplayer != null && LOTRLevelData.getData(entityplayer).hasAchievement(LOTRAchievement.enterMeneltarma);
      if (!meneltarma) {
         double mtX = LOTRWaypoint.MENELTARMA_SUMMIT.getX();
         double mtY = LOTRWaypoint.MENELTARMA_SUMMIT.getY();
         int mtW = 20;
         double mtMinU = (mtX - (double)mtW) / (double)LOTRGenLayerWorld.imageWidth;
         double mtMaxU = (mtX + (double)mtW) / (double)LOTRGenLayerWorld.imageWidth;
         double mtMinV = (mtY - (double)mtW) / (double)LOTRGenLayerWorld.imageHeight;
         double mtMaxV = (mtY + (double)mtW) / (double)LOTRGenLayerWorld.imageHeight;
         if (minU <= mtMaxU && maxU >= mtMinU && minV <= mtMaxV && maxV >= mtMinV) {
            GL11.glDisable(3553);
            int oceanColor = getMapOceanColor(sepia);
            mtMinU = Math.max(mtMinU, minU);
            mtMaxU = Math.min(mtMaxU, maxU);
            mtMinV = Math.max(mtMinV, minV);
            mtMaxV = Math.min(mtMaxV, maxV);
            double ratioX = (x1 - x0) / (maxU - minU);
            double ratioY = (y1 - y0) / (maxV - minV);
            double mtX0 = x0 + (mtMinU - minU) * ratioX;
            double mtX1 = x0 + (mtMaxU - minU) * ratioX;
            double mtY0 = y0 + (mtMinV - minV) * ratioY;
            double mtY1 = y0 + (mtMaxV - minV) * ratioY;
            tessellator.func_78382_b();
            tessellator.func_78378_d(oceanColor);
            tessellator.func_78374_a(mtX0, mtY1, z, mtMinU, mtMaxV);
            tessellator.func_78374_a(mtX1, mtY1, z, mtMaxU, mtMaxV);
            tessellator.func_78374_a(mtX1, mtY0, z, mtMaxU, mtMinV);
            tessellator.func_78374_a(mtX0, mtY0, z, mtMinU, mtMinV);
            tessellator.func_78381_a();
            GL11.glEnable(3553);
         }
      }

   }

   public static void drawMapOverlay(EntityPlayer entityplayer, double x0, double x1, double y0, double y1, double z, double minU, double maxU, double minV, double maxV) {
      Tessellator tessellator = Tessellator.field_78398_a;
      mc.func_110434_K().func_110577_a(overlayTexture);
      GL11.glEnable(3042);
      OpenGlHelper.func_148821_a(770, 771, 1, 0);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.2F);
      tessellator.func_78382_b();
      tessellator.func_78374_a(x0, y1, z, 0.0D, 1.0D);
      tessellator.func_78374_a(x1, y1, z, 1.0D, 1.0D);
      tessellator.func_78374_a(x1, y0, z, 1.0D, 0.0D);
      tessellator.func_78374_a(x0, y0, z, 0.0D, 0.0D);
      tessellator.func_78381_a();
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glDisable(3042);
   }

   public static void drawMapCompassBottomLeft(double x, double y, double z, double scale) {
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      mc.func_110434_K().func_110577_a(LOTRGuiMap.mapIconsTexture);
      int width = 32;
      int height = 32;
      double x1 = x + (double)width * scale;
      double y0 = y - (double)height * scale;
      int texU = 224;
      int texV = 200;
      float u0 = (float)texU / 256.0F;
      float u1 = (float)(texU + width) / 256.0F;
      float v0 = (float)texV / 256.0F;
      float v1 = (float)(texV + height) / 256.0F;
      Tessellator tessellator = Tessellator.field_78398_a;
      tessellator.func_78382_b();
      tessellator.func_78374_a(x, y, z, (double)u0, (double)v1);
      tessellator.func_78374_a(x1, y, z, (double)u1, (double)v1);
      tessellator.func_78374_a(x1, y0, z, (double)u1, (double)v0);
      tessellator.func_78374_a(x, y0, z, (double)u0, (double)v0);
      tessellator.func_78381_a();
   }

   private static ResourceLocation getMapTexture(EntityPlayer entityplayer, boolean sepia) {
      return !LOTRConfig.osrsMap && !sepia ? mapTexture : sepiaMapTexture;
   }

   public static int getMapOceanColor(boolean sepia) {
      if (LOTRConfig.osrsMap) {
         return -10324058;
      } else {
         int ocean = LOTRBiome.ocean.field_76790_z;
         if (sepia) {
            ocean = getSepia(ocean);
         }

         return ocean;
      }
   }

   public static void loadMapTextures() {
      mapTexture = new ResourceLocation("lotr:map/map.png");

      try {
         BufferedImage mapImage = ImageIO.read(mc.func_110442_L().func_110536_a(mapTexture).func_110527_b());
         sepiaMapTexture = convertToSepia(mapImage, new ResourceLocation("lotr:map_sepia"));
      } catch (IOException var1) {
         FMLLog.severe("Failed to generate LOTR sepia map", new Object[0]);
         var1.printStackTrace();
         sepiaMapTexture = mapTexture;
      }

   }

   private static ResourceLocation convertToSepia(BufferedImage srcImage, ResourceLocation resourceLocation) {
      int mapWidth = srcImage.getWidth();
      int mapHeight = srcImage.getHeight();
      int[] colors = srcImage.getRGB(0, 0, mapWidth, mapHeight, (int[])null, 0, mapWidth);

      for(int i = 0; i < colors.length; ++i) {
         int color = colors[i];
         if (LOTRConfig.osrsMap) {
            Integer biomeID = (Integer)LOTRDimension.MIDDLE_EARTH.colorsToBiomeIDs.get(color);
            if (biomeID == null) {
               color = getMapOceanColor(true);
            } else {
               LOTRBiome biome = LOTRDimension.MIDDLE_EARTH.biomeList[biomeID];
               if (biome.heightBaseParameter < 0.0F) {
                  color = 6453158;
               } else if (biome.heightBaseParameter > 0.8F) {
                  color = 14736861;
               } else if (biome.heightBaseParameter > 0.4F) {
                  color = 6575407;
               } else if (biome instanceof LOTRBiomeGenMordor) {
                  color = 3290677;
               } else if (biome.decorator.treesPerChunk > 1) {
                  color = 2775058;
               } else if (biome.field_76750_F < 0.3F) {
                  if (biome.field_76750_F < 0.2F) {
                     color = 14215139;
                  } else {
                     color = 9470587;
                  }
               } else if (biome.field_76751_G < 0.2F) {
                  color = 13548147;
               } else {
                  color = 5468426;
               }
            }
         } else {
            color = getSepia(color);
         }

         colors[i] = color;
      }

      BufferedImage newMapImage = new BufferedImage(mapWidth, mapHeight, 2);
      newMapImage.setRGB(0, 0, mapWidth, mapHeight, colors, 0, mapWidth);
      if (LOTRConfig.osrsMap) {
         BufferedImage temp = newMapImage;
         newMapImage = new BufferedImage(mapWidth, mapHeight, 2);

         for(int i = 0; i < mapWidth; ++i) {
            for(int j = 0; j < mapHeight; ++j) {
               int rgb = temp.getRGB(i, j);
               byte range;
               int edge;
               int total;
               int x;
               int y;
               int x1;
               int y1;
               int rgb1;
               float ratio;
               if (rgb == 5468426) {
                  range = 8;
                  edge = 0;
                  total = 0;
                  x = -range;

                  while(true) {
                     if (x >= range) {
                        if (edge > 0) {
                           ratio = (float)edge / (float)total;
                           rgb = LOTRColorUtil.lerpColors_I(5468426, 9279778, ratio * 2.0F);
                        }
                        break;
                     }

                     for(y = -range; y < range; ++y) {
                        x1 = i + x;
                        y1 = y + j;
                        if (x1 >= 0 && x1 < mapWidth && y1 >= 0 && y1 < mapHeight) {
                           rgb1 = temp.getRGB(x1, y1);
                           if (rgb1 == 6453158) {
                              ++edge;
                           }

                           ++total;
                        }
                     }

                     ++x;
                  }
               } else if (rgb == 14736861) {
                  range = 8;
                  edge = 0;
                  total = 0;
                  x = -range;

                  while(true) {
                     if (x >= range) {
                        if (edge > 0) {
                           ratio = (float)edge / (float)total;
                           rgb = LOTRColorUtil.lerpColors_I(14736861, 9005125, ratio * 1.5F);
                        }
                        break;
                     }

                     for(y = -range; y < range; ++y) {
                        x1 = i + x;
                        y1 = y + j;
                        if (x1 >= 0 && x1 < mapWidth && y1 >= 0 && y1 < mapHeight) {
                           rgb1 = temp.getRGB(x1, y1);
                           if (rgb1 != 14736861) {
                              ++edge;
                           }

                           ++total;
                        }
                     }

                     ++x;
                  }
               }

               newMapImage.setRGB(i, j, rgb | -16777216);
            }
         }
      }

      mc.field_71446_o.func_110579_a(resourceLocation, new DynamicTexture(newMapImage));
      return resourceLocation;
   }

   private static int getSepia(int rgb) {
      Color color = new Color(rgb);
      int alpha = rgb >> 24 & 255;
      float[] colors = color.getColorComponents((float[])null);
      float r = colors[0];
      float g = colors[1];
      float b = colors[2];
      float newR = r * 0.79F + g * 0.39F + b * 0.26F;
      float newG = r * 0.52F + g * 0.35F + b * 0.19F;
      float newB = r * 0.35F + g * 0.26F + b * 0.15F;
      newR = Math.min(Math.max(0.0F, newR), 1.0F);
      newG = Math.min(Math.max(0.0F, newG), 1.0F);
      newB = Math.min(Math.max(0.0F, newB), 1.0F);
      int sepia = (new Color(newR, newG, newB)).getRGB();
      sepia |= alpha << 24;
      return sepia;
   }

   public static void replaceWaterParticles() {
      try {
         IResourceManager resMgr = mc.func_110442_L();
         IResource loadedParticles = resMgr.func_110536_a(particleTextures);
         BufferedImage particles = ImageIO.read(loadedParticles.func_110527_b());
         BufferedImage waterParticles = ImageIO.read(resMgr.func_110536_a(newWaterParticles).func_110527_b());
         int[] rgb = waterParticles.getRGB(0, 0, waterParticles.getWidth(), waterParticles.getHeight(), (int[])null, 0, waterParticles.getWidth());
         particles.setRGB(newWaterU, newWaterV, newWaterWidth, newWaterHeight, rgb, 0, newWaterWidth);
         TextureManager textureManager = mc.func_110434_K();
         textureManager.func_110577_a(particleTextures);
         AbstractTexture texture = (AbstractTexture)textureManager.func_110581_b(particleTextures);
         TextureUtil.func_110989_a(texture.func_110552_b(), particles, false, false);
      } catch (IOException var7) {
         FMLLog.severe("Failed to replace rain particles", new Object[0]);
         var7.printStackTrace();
      }

   }

   public static ResourceLocation getEyesTexture(ResourceLocation skin, int[][] eyesCoords, int eyeWidth, int eyeHeight) {
      ResourceLocation eyes = (ResourceLocation)eyesTextures.get(skin);
      if (eyes == null) {
         try {
            BufferedImage skinImage = ImageIO.read(mc.func_110442_L().func_110536_a(skin).func_110527_b());
            int skinWidth = skinImage.getWidth();
            int skinHeight = skinImage.getHeight();
            BufferedImage eyesImage = new BufferedImage(skinWidth, skinHeight, 2);
            int[][] var9 = eyesCoords;
            int var10 = eyesCoords.length;

            for(int var11 = 0; var11 < var10; ++var11) {
               int[] eyePos = var9[var11];
               int eyeX = eyePos[0];
               int eyeY = eyePos[1];

               for(int i = eyeX; i < eyeX + eyeWidth; ++i) {
                  for(int j = eyeY; j < eyeY + eyeHeight; ++j) {
                     int rgb = skinImage.getRGB(i, j);
                     eyesImage.setRGB(i, j, rgb);
                  }
               }
            }

            eyes = mc.field_71446_o.func_110578_a(skin.toString() + "_eyes_" + eyeWidth + "_" + eyeHeight, new DynamicTexture(eyesImage));
         } catch (IOException var18) {
            LOTRLog.logger.error("Failed to generate eyes skin");
            var18.printStackTrace();
            eyes = missingTexture;
         }

         eyesTextures.put(skin, eyes);
      }

      return eyes;
   }

   private static IIcon generateIconEmpty(TextureMap textureMap) {
      String iconName = "textures/blocks/LOTR_EMPTY_ICON";
      int size = 16;
      BufferedImage iconImage = new BufferedImage(size, size, 2);

      for(int i = 0; i < iconImage.getWidth(); ++i) {
         for(int j = 0; j < iconImage.getHeight(); ++j) {
            int rgb = 0;
            int alpha = 0;
            int rgb = rgb | alpha;
            iconImage.setRGB(i, j, rgb);
         }
      }

      LOTRBufferedImageIcon icon = new LOTRBufferedImageIcon(iconName, iconImage);
      icon.func_110966_b(iconImage.getWidth());
      icon.func_110969_c(iconImage.getHeight());
      textureMap.setTextureEntry(iconName, icon);
      return icon;
   }

   public static int computeAverageFactionPageColor(ResourceLocation texture, int u0, int v0, int u1, int v1) {
      if (!averagedPageColors.containsKey(texture)) {
         boolean var5 = false;

         int avgColor;
         try {
            BufferedImage pageImage = ImageIO.read(mc.func_110442_L().func_110536_a(texture).func_110527_b());
            long totalR = 0L;
            long totalG = 0L;
            long totalB = 0L;
            long totalA = 0L;
            int count = 0;

            int u;
            int v;
            int rgb;
            for(u = u0; u < u1; ++u) {
               for(v = v0; v < v1; ++v) {
                  rgb = pageImage.getRGB(u, v);
                  Color color = new Color(rgb);
                  totalR += (long)color.getRed();
                  totalG += (long)color.getGreen();
                  totalB += (long)color.getBlue();
                  totalA += (long)color.getAlpha();
                  ++count;
               }
            }

            u = (int)(totalR / (long)count);
            v = (int)(totalG / (long)count);
            rgb = (int)(totalB / (long)count);
            int avgA = (int)(totalA / (long)count);
            avgColor = (new Color(u, v, rgb, avgA)).getRGB();
         } catch (IOException var20) {
            FMLLog.severe("LOTR: Failed to generate average page colour", new Object[0]);
            var20.printStackTrace();
            avgColor = 0;
         }

         averagedPageColors.put(texture, avgColor);
         return avgColor;
      } else {
         return (Integer)averagedPageColors.get(texture);
      }
   }

   public static int findContrastingColor(int text, int bg) {
      Color cText = new Color(text);
      Color cBg = new Color(bg);
      float[] hsbText = Color.RGBtoHSB(cText.getRed(), cText.getGreen(), cText.getBlue(), (float[])null);
      float[] hsbBg = Color.RGBtoHSB(cBg.getRed(), cBg.getGreen(), cBg.getBlue(), (float[])null);
      float bText = hsbText[2];
      float bBg = hsbBg[2];
      float limit = 0.4F;
      if (Math.abs(bText - bBg) < limit) {
         if (bBg > 0.66F) {
            bText = bBg - limit;
         } else {
            bText = bBg + limit;
         }
      }

      return Color.HSBtoRGB(hsbText[0], hsbText[1], bText);
   }

   static {
      missingTexture = mc.func_110434_K().func_110578_a("lotr.missingSkin", TextureUtil.field_111001_a);
      overlayTexture = new ResourceLocation("lotr:map/mapOverlay.png");
      mapTerrain = new ResourceLocation("lotr:map/terrain.png");
      osrsTexture = new ResourceLocation("lotr:map/osrs.png");
      particleTextures = new ResourceLocation("textures/particle/particles.png");
      newWaterParticles = new ResourceLocation("lotr:misc/waterParticles.png");
      newWaterU = 0;
      newWaterV = 8;
      newWaterWidth = 64;
      newWaterHeight = 8;
      eyesTextures = new HashMap();
      averagedPageColors = new HashMap();
   }
}
