package lotr.common.world.gen.layer;

import java.util.function.LongFunction;
import lotr.common.world.gen.MiddleEarthBiomeGenSettings;
import lotr.common.world.map.MapSettings;
import lotr.common.world.map.MapSettingsManager;
import net.minecraft.world.gen.IExtendedNoiseRandom;
import net.minecraft.world.gen.LazyAreaLayerContext;
import net.minecraft.world.gen.area.IAreaFactory;
import net.minecraft.world.gen.layer.LayerUtil;
import net.minecraft.world.gen.layer.SmoothLayer;
import net.minecraft.world.gen.layer.ZoomLayer;

public class MiddleEarthWorldLayers {
   public static final int MIN_SCALE_POWER = 2;
   public static final int MAX_SCALE_POWER = 10;

   public static LayerWithDataDrivenBiomes create(long worldSeed, boolean classicBiomes, MiddleEarthBiomeGenSettings genSettings) {
      int maxCacheSize = 25;
      IAreaFactory areaFactory = createLayers(classicBiomes, genSettings, (seedModifier) -> {
         return new LazyAreaLayerContext(maxCacheSize, worldSeed, seedModifier);
      });
      return new LayerWithDataDrivenBiomes(areaFactory);
   }

   public static IAreaFactory createLayers(boolean classicBiomes, MiddleEarthBiomeGenSettings genSettings, LongFunction longFunc) {
      int riverScale = genSettings.getRiverSize();
      IAreaFactory riverLayer = MESeedRiversLayer.INSTANCE.func_202823_a((IExtendedNoiseRandom)longFunc.apply(100L));
      riverLayer = LayerUtil.func_202829_a(1000L, ZoomLayer.NORMAL, riverLayer, 2 + riverScale, longFunc);
      riverLayer = MERiverLayer.INSTANCE.func_202713_a((IExtendedNoiseRandom)longFunc.apply(1L), riverLayer);
      riverLayer = SmoothLayer.INSTANCE.func_202713_a((IExtendedNoiseRandom)longFunc.apply(1000L), riverLayer);
      IAreaFactory subBiomesLayer = SeedBiomeSubtypesLayer.INSTANCE.func_202823_a((IExtendedNoiseRandom)longFunc.apply(3000L));
      subBiomesLayer = LayerUtil.func_202829_a(3000L, ZoomLayer.NORMAL, subBiomesLayer, 2, longFunc);
      IAreaFactory biomeLayer = null;
      if (classicBiomes) {
         IAreaFactory seaLayer = ClassicSeedSeasLayer.INSTANCE.func_202823_a((IExtendedNoiseRandom)longFunc.apply(2012L));
         seaLayer = LayerUtil.func_202829_a(200L, ZoomLayer.NORMAL, seaLayer, 3, longFunc);
         seaLayer = ClassicRemoveSeaAtOriginLayer.INSTANCE.func_202713_a((IExtendedNoiseRandom)longFunc.apply(300L), seaLayer);
         biomeLayer = (new ClassicBiomeLayer(genSettings)).func_202713_a((IExtendedNoiseRandom)longFunc.apply(2013L), seaLayer);
         biomeLayer = LayerUtil.func_202829_a(300L, ZoomLayer.NORMAL, biomeLayer, 2, longFunc);
      } else {
         biomeLayer = MiddleEarthMapLayer.INSTANCE.func_202823_a((IExtendedNoiseRandom)longFunc.apply(1954L));
      }

      biomeLayer = BiomeSubtypesLayer.INSTANCE.func_202707_a((IExtendedNoiseRandom)longFunc.apply(1000L), biomeLayer, subBiomesLayer);
      biomeLayer = (new MEAddIslandsLayer(400)).func_202713_a((IExtendedNoiseRandom)longFunc.apply(400L), biomeLayer);
      biomeLayer = (new MapSettingsDependentBiomeZoomLayer(genSettings, classicBiomes, longFunc)).func_202713_a((IExtendedNoiseRandom)longFunc.apply(0L), biomeLayer);
      biomeLayer = SmoothLayer.INSTANCE.func_202713_a((IExtendedNoiseRandom)longFunc.apply(1000L), biomeLayer);
      biomeLayer = MEAddRiversLayer.INSTANCE.func_202707_a((IExtendedNoiseRandom)longFunc.apply(100L), biomeLayer, riverLayer);
      return biomeLayer;
   }

   public static MapSettings getActiveMapSettings() {
      return MapSettingsManager.serverInstance().getCurrentLoadedMap();
   }
}
