package lotr.common.world.genlayer;

import com.google.common.math.IntMath;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.ModContainer;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import javax.imageio.ImageIO;
import lotr.common.LOTRDimension;
import lotr.common.LOTRMod;
import lotr.common.world.biome.LOTRBiome;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import org.apache.logging.log4j.Level;

public class LOTRGenLayerWorld extends LOTRGenLayer {
   public static final int scalePower = 7;
   private static byte[] biomeImageData;
   public static final int originX = 810;
   public static final int originZ = 730;
   public static final int scale = IntMath.pow(2, 7);
   public static int imageWidth;
   public static int imageHeight;

   public LOTRGenLayerWorld() {
      super(0L);
      if (!loadedBiomeImage()) {
         try {
            BufferedImage biomeImage = null;
            String imageName = "assets/lotr/map/map.png";
            ModContainer mc = LOTRMod.getModContainer();
            if (!mc.getSource().isFile()) {
               File file = new File(LOTRMod.class.getResource("/" + imageName).toURI());
               biomeImage = ImageIO.read(new FileInputStream(file));
            } else {
               ZipFile zip = new ZipFile(mc.getSource());
               Enumeration entries = zip.entries();

               while(entries.hasMoreElements()) {
                  ZipEntry entry = (ZipEntry)entries.nextElement();
                  if (entry.getName().equals(imageName)) {
                     biomeImage = ImageIO.read(zip.getInputStream(entry));
                  }
               }

               zip.close();
            }

            if (biomeImage == null) {
               throw new RuntimeException("Could not load LOTR biome map image");
            }

            imageWidth = biomeImage.getWidth();
            imageHeight = biomeImage.getHeight();
            int[] colors = biomeImage.getRGB(0, 0, imageWidth, imageHeight, (int[])null, 0, imageWidth);
            biomeImageData = new byte[imageWidth * imageHeight];

            for(int i = 0; i < colors.length; ++i) {
               int color = colors[i];
               Integer biomeID = (Integer)LOTRDimension.MIDDLE_EARTH.colorsToBiomeIDs.get(color);
               if (biomeID != null) {
                  biomeImageData[i] = (byte)biomeID;
               } else {
                  FMLLog.log(Level.ERROR, "Found unknown biome on map " + Integer.toHexString(color), new Object[0]);
                  biomeImageData[i] = (byte)LOTRBiome.ocean.field_76756_M;
               }
            }
         } catch (Exception var8) {
            var8.printStackTrace();
         }
      }

   }

   public int[] getInts(World world, int i, int k, int xSize, int zSize) {
      int[] intArray = LOTRIntCache.get(world).getIntArray(xSize * zSize);

      for(int k1 = 0; k1 < zSize; ++k1) {
         for(int i1 = 0; i1 < xSize; ++i1) {
            int i2 = i + i1 + 810;
            int k2 = k + k1 + 730;
            if (i2 >= 0 && i2 < imageWidth && k2 >= 0 && k2 < imageHeight) {
               intArray[i1 + k1 * xSize] = getBiomeImageID(i2, k2);
            } else {
               intArray[i1 + k1 * xSize] = LOTRBiome.ocean.field_76756_M;
            }
         }
      }

      return intArray;
   }

   public static int getBiomeImageID(int x, int z) {
      int index = z * imageWidth + x;
      return biomeImageData[index] & 255;
   }

   public static LOTRBiome getBiomeOrOcean(int mapX, int mapZ) {
      int biomeID;
      if (mapX >= 0 && mapX < imageWidth && mapZ >= 0 && mapZ < imageHeight) {
         biomeID = getBiomeImageID(mapX, mapZ);
      } else {
         biomeID = LOTRBiome.ocean.field_76756_M;
      }

      return LOTRDimension.MIDDLE_EARTH.biomeList[biomeID];
   }

   public static boolean loadedBiomeImage() {
      return biomeImageData != null;
   }

   public static LOTRGenLayer[] createWorld(LOTRDimension dim, WorldType worldType) {
      if (dim == LOTRDimension.UTUMNO) {
         LOTRGenLayer biomes = new LOTRGenLayerBiome(LOTRBiome.utumno);
         LOTRGenLayer variants = new LOTRGenLayerBiomeVariants(300L);
         return new LOTRGenLayer[]{biomes, variants, variants, variants, variants};
      } else {
         LOTRGenLayer rivers = new LOTRGenLayerRiverInit(100L);
         LOTRGenLayer rivers = LOTRGenLayerZoom.magnify(1000L, rivers, 10);
         LOTRGenLayer rivers = new LOTRGenLayerRiver(1L, rivers);
         LOTRGenLayer rivers = new LOTRGenLayerSmooth(1000L, rivers);
         rivers = LOTRGenLayerZoom.magnify(1000L, rivers, 1);
         LOTRGenLayer biomeSubtypes = new LOTRGenLayerBiomeSubtypesInit(3000L);
         LOTRGenLayer biomeSubtypes = LOTRGenLayerZoom.magnify(3000L, biomeSubtypes, 2);
         LOTRGenLayer biomes = new LOTRGenLayerWorld();
         LOTRGenLayer mapRivers;
         if (worldType == LOTRMod.worldTypeMiddleEarthClassic) {
            LOTRGenLayer oceans = new LOTRGenLayerClassicOcean(2012L);
            mapRivers = LOTRGenLayerZoom.magnify(200L, oceans, 3);
            LOTRGenLayer oceans = new LOTRGenLayerClassicRemoveOcean(400L, mapRivers);
            LOTRGenLayer biomes = new LOTRGenLayerClassicBiomes(2013L, oceans, dim);
            biomes = LOTRGenLayerZoom.magnify(300L, biomes, 2);
         }

         LOTRGenLayer mapRivers = new LOTRGenLayerExtractMapRivers(5000L, (LOTRGenLayer)biomes);
         LOTRGenLayer biomes = new LOTRGenLayerRemoveMapRivers(1000L, (LOTRGenLayer)biomes, dim);
         LOTRGenLayer biomes = new LOTRGenLayerBiomeSubtypes(1000L, biomes, biomeSubtypes);
         LOTRGenLayer biomes = new LOTRGenLayerNearHaradRiverbanks(200L, biomes, (LOTRGenLayer)mapRivers, dim);
         LOTRGenLayer biomes = new LOTRGenLayerNearHaradOasis(500L, biomes, dim);
         LOTRGenLayer biomes = LOTRGenLayerZoom.magnify(1000L, biomes, 1);
         LOTRGenLayer biomes = new LOTRGenLayerBeach(1000L, biomes, dim, LOTRBiome.ocean);
         biomes = LOTRGenLayerZoom.magnify(1000L, biomes, 2);
         LOTRGenLayer biomes = new LOTRGenLayerOasisLake(600L, biomes, dim);
         biomes = LOTRGenLayerZoom.magnify(1000L, biomes, 2);
         LOTRGenLayer biomes = new LOTRGenLayerSmooth(1000L, biomes);
         LOTRGenLayer variants = new LOTRGenLayerBiomeVariants(200L);
         LOTRGenLayer variants = LOTRGenLayerZoom.magnify(200L, variants, 8);
         LOTRGenLayer variantsSmall = new LOTRGenLayerBiomeVariants(300L);
         LOTRGenLayer variantsSmall = LOTRGenLayerZoom.magnify(300L, variantsSmall, 6);
         LOTRGenLayer lakes = (new LOTRGenLayerBiomeVariantsLake(100L, (LOTRGenLayer)null, 0)).setLakeFlags(1);

         int i;
         for(i = 1; i <= 5; ++i) {
            lakes = new LOTRGenLayerZoom(200L + (long)i, (LOTRGenLayer)lakes);
            if (i <= 2) {
               lakes = (new LOTRGenLayerBiomeVariantsLake(300L + (long)i, (LOTRGenLayer)lakes, i)).setLakeFlags(1);
            }

            if (i == 3) {
               lakes = (new LOTRGenLayerBiomeVariantsLake(500L, (LOTRGenLayer)lakes, i)).setLakeFlags(2, 4);
            }
         }

         for(i = 0; i < 4; ++i) {
            mapRivers = new LOTRGenLayerMapRiverZoom(4000L + (long)i, (LOTRGenLayer)mapRivers);
         }

         LOTRGenLayer mapRivers = new LOTRGenLayerNarrowRivers(3000L, (LOTRGenLayer)mapRivers, 6);
         mapRivers = LOTRGenLayerZoom.magnify(4000L, mapRivers, 1);
         LOTRGenLayer rivers = new LOTRGenLayerIncludeMapRivers(5000L, rivers, mapRivers);
         return new LOTRGenLayer[]{biomes, variants, variantsSmall, (LOTRGenLayer)lakes, rivers};
      }
   }

   public static void printRiverlessMap(World world, File file) {
      if (!file.exists()) {
         try {
            file.createNewFile();
         } catch (IOException var11) {
            var11.printStackTrace();
         }
      }

      LOTRDimension dim = LOTRDimension.MIDDLE_EARTH;
      LOTRGenLayer biomeSubtypes = new LOTRGenLayerBiomeSubtypesInit(3000L);
      LOTRGenLayer biomeSubtypes = LOTRGenLayerZoom.magnify(3000L, biomeSubtypes, 2);
      LOTRGenLayer biomes = new LOTRGenLayerWorld();
      LOTRGenLayer biomes = new LOTRGenLayerRemoveMapRivers(1000L, biomes, dim);
      LOTRGenLayer biomes = new LOTRGenLayerBiomeSubtypes(1000L, biomes, biomeSubtypes);
      BufferedImage buf = new BufferedImage(imageWidth, imageHeight, 2);

      for(int i = 0; i < imageWidth; ++i) {
         for(int k = 0; k < imageHeight; ++k) {
            int[] b = biomes.getInts(world, i - 810, k - 730, 1, 1);
            LOTRBiome biome = dim.biomeList[b[0]];
            buf.setRGB(i, k, biome.field_76790_z | -16777216);
            LOTRIntCache.get(world).resetIntCache();
         }
      }

      try {
         ImageIO.write(buf, "png", file);
      } catch (IOException var10) {
         var10.printStackTrace();
      }

   }
}
