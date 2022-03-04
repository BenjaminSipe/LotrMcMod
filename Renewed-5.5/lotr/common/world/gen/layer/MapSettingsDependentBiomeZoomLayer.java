package lotr.common.world.gen.layer;

import java.util.function.LongFunction;
import lotr.common.world.gen.MiddleEarthBiomeGenSettings;
import net.minecraft.world.gen.IExtendedNoiseRandom;
import net.minecraft.world.gen.area.IArea;
import net.minecraft.world.gen.area.IAreaFactory;
import net.minecraft.world.gen.layer.ZoomLayer;
import net.minecraft.world.gen.layer.traits.IAreaTransformer1;

public class MapSettingsDependentBiomeZoomLayer implements IAreaTransformer1 {
   private final MiddleEarthBiomeGenSettings biomeGenSettings;
   private final boolean classicBiomes;
   private final LongFunction longFunc;

   public MapSettingsDependentBiomeZoomLayer(MiddleEarthBiomeGenSettings settings, boolean classic, LongFunction lFunc) {
      this.biomeGenSettings = settings;
      this.classicBiomes = classic;
      this.longFunc = lFunc;
   }

   public int func_215728_a(IExtendedNoiseRandom noiseRand, IArea baseBiomeLayer, int x, int z) {
      int mapScale = MiddleEarthWorldLayers.getActiveMapSettings().getScalePower();
      int classicBiomeScale = this.biomeGenSettings.getClassicBiomeSize();
      int curZoom = 0;
      int numZooms = this.classicBiomes ? classicBiomeScale : mapScale - 2;
      IAreaFactory zoomedBiomeLayer = () -> {
         return baseBiomeLayer;
      };

      while(true) {
         if (curZoom == Math.max(0, numZooms - 5)) {
         }

         if (curZoom == Math.max(0, numZooms - 4)) {
            zoomedBiomeLayer = MEAddIslandsLayer.DEFAULT_ADD_ISLANDS.func_202713_a((IExtendedNoiseRandom)this.longFunc.apply(300L), zoomedBiomeLayer);
            zoomedBiomeLayer = MEShoreLayers.ForMainland.INSTANCE.func_202713_a((IExtendedNoiseRandom)this.longFunc.apply(1000L), zoomedBiomeLayer);
         }

         if (curZoom == Math.max(0, numZooms - 3)) {
            zoomedBiomeLayer = MEShoreLayers.ForIsland.INSTANCE.func_202713_a((IExtendedNoiseRandom)this.longFunc.apply(900L), zoomedBiomeLayer);
         }

         if (curZoom == Math.max(0, numZooms - 2)) {
         }

         if (curZoom >= numZooms) {
            return zoomedBiomeLayer.make().func_202678_a(x, z);
         }

         zoomedBiomeLayer = ZoomLayer.NORMAL.func_202713_a((IExtendedNoiseRandom)this.longFunc.apply(1000L + (long)curZoom), zoomedBiomeLayer);
         ++curZoom;
      }
   }

   public int func_215721_a(int x) {
      return x;
   }

   public int func_215722_b(int z) {
      return z;
   }
}
