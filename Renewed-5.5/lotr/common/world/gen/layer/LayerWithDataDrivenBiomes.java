package lotr.common.world.gen.layer;

import lotr.common.LOTRLog;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.Util;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeRegistry;
import net.minecraft.world.gen.area.IAreaFactory;
import net.minecraft.world.gen.area.LazyArea;

public class LayerWithDataDrivenBiomes {
   private final LazyArea genLayers;

   public LayerWithDataDrivenBiomes(IAreaFactory areaFactory) {
      this.genLayers = (LazyArea)areaFactory.make();
   }

   public Biome getLayerBiome(Registry biomeReg, int x, int z) {
      int biomeId = this.genLayers.func_202678_a(x, z);
      Biome biome = (Biome)biomeReg.func_148745_a(biomeId);
      if (biome == null) {
         if (SharedConstants.field_206244_b) {
            throw (IllegalStateException)Util.func_229757_c_(new IllegalStateException("Unknown biome id: " + biomeId));
         } else {
            LOTRLog.warn("Unknown biome id: %d", biomeId);
            return (Biome)biomeReg.func_230516_a_(BiomeRegistry.func_244203_a(0));
         }
      } else {
         return biome;
      }
   }
}
