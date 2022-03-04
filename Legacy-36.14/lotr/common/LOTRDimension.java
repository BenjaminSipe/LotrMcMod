package lotr.common;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import lotr.common.world.LOTRWorldProvider;
import lotr.common.world.LOTRWorldProviderMiddleEarth;
import lotr.common.world.LOTRWorldProviderUtumno;
import lotr.common.world.biome.LOTRBiome;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.config.Configuration;

public enum LOTRDimension {
   MIDDLE_EARTH("MiddleEarth", 100, LOTRWorldProviderMiddleEarth.class, true, 100, EnumSet.of(LOTRDimension.DimensionRegion.WEST, LOTRDimension.DimensionRegion.EAST, LOTRDimension.DimensionRegion.SOUTH)),
   UTUMNO("Utumno", 101, LOTRWorldProviderUtumno.class, false, 500, EnumSet.of(LOTRDimension.DimensionRegion.REG_UTUMNO));

   public String dimensionName;
   private int defaultID;
   public int dimensionID;
   private Class providerClass;
   private boolean loadSpawn;
   public LOTRBiome[] biomeList = new LOTRBiome[256];
   public Map colorsToBiomeIDs = new HashMap();
   public List majorBiomes = new ArrayList();
   public List achievementCategories = new ArrayList();
   public List allAchievements = new ArrayList();
   public List factionList = new ArrayList();
   public List dimensionRegions = new ArrayList();
   public int spawnCap;

   private LOTRDimension(String s, int i, Class c, boolean flag, int spawns, EnumSet regions) {
      this.dimensionName = s;
      this.defaultID = i;
      this.providerClass = c;
      this.loadSpawn = flag;
      this.spawnCap = spawns;
      this.dimensionRegions.addAll(regions);
      Iterator var9 = this.dimensionRegions.iterator();

      while(var9.hasNext()) {
         LOTRDimension.DimensionRegion r = (LOTRDimension.DimensionRegion)var9.next();
         r.setDimension(this);
      }

   }

   public String getUntranslatedDimensionName() {
      return "lotr.dimension." + this.dimensionName;
   }

   public String getDimensionName() {
      return StatCollector.func_74838_a(this.getUntranslatedDimensionName());
   }

   public static void configureDimensions(Configuration config, String category) {
      LOTRDimension[] var2 = values();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         LOTRDimension dim = var2[var4];
         dim.dimensionID = config.get(category, "Dimension ID: " + dim.dimensionName, dim.defaultID).getInt();
      }

   }

   public static void registerDimensions() {
      LOTRDimension[] var0 = values();
      int var1 = var0.length;

      for(int var2 = 0; var2 < var1; ++var2) {
         LOTRDimension dim = var0[var2];
         DimensionManager.registerProviderType(dim.dimensionID, dim.providerClass, dim.loadSpawn);
         DimensionManager.registerDimension(dim.dimensionID, dim.dimensionID);
      }

   }

   public static LOTRDimension getCurrentDimension(World world) {
      if (world != null) {
         WorldProvider provider = world.field_73011_w;
         if (provider instanceof LOTRWorldProvider) {
            return ((LOTRWorldProvider)provider).getLOTRDimension();
         }
      }

      return null;
   }

   public static LOTRDimension getCurrentDimensionWithFallback(World world) {
      LOTRDimension dim = getCurrentDimension(world);
      return dim == null ? MIDDLE_EARTH : dim;
   }

   public static LOTRDimension forName(String s) {
      LOTRDimension[] var1 = values();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         LOTRDimension dim = var1[var3];
         if (dim.dimensionName.equals(s)) {
            return dim;
         }
      }

      return null;
   }

   public static enum DimensionRegion {
      WEST("west"),
      EAST("east"),
      SOUTH("south"),
      REG_UTUMNO("utumno");

      private String regionName;
      private LOTRDimension dimension;
      public List factionList = new ArrayList();

      private DimensionRegion(String s) {
         this.regionName = s;
      }

      public void setDimension(LOTRDimension dim) {
         this.dimension = dim;
      }

      public LOTRDimension getDimension() {
         return this.dimension;
      }

      public String codeName() {
         return this.regionName;
      }

      public String getRegionName() {
         return StatCollector.func_74838_a("lotr.dimension." + this.dimension.dimensionName + "." + this.codeName());
      }

      public static LOTRDimension.DimensionRegion forName(String regionName) {
         LOTRDimension.DimensionRegion[] var1 = values();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            LOTRDimension.DimensionRegion r = var1[var3];
            if (r.codeName().equals(regionName)) {
               return r;
            }
         }

         return null;
      }

      public static LOTRDimension.DimensionRegion forID(int ID) {
         LOTRDimension.DimensionRegion[] var1 = values();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            LOTRDimension.DimensionRegion r = var1[var3];
            if (r.ordinal() == ID) {
               return r;
            }
         }

         return null;
      }
   }
}
