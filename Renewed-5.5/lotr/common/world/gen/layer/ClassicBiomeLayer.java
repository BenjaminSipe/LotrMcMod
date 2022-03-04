package lotr.common.world.gen.layer;

import java.util.List;
import lotr.common.init.LOTRBiomes;
import lotr.common.init.PreRegisteredLOTRBiome;
import lotr.common.world.gen.MiddleEarthBiomeGenSettings;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.IExtendedNoiseRandom;
import net.minecraft.world.gen.area.IArea;
import net.minecraft.world.gen.layer.traits.IAreaTransformer1;

public class ClassicBiomeLayer implements IAreaTransformer1 {
   private final List classicBiomeNames = LOTRBiomes.listBiomeNamesForClassicGen();

   public ClassicBiomeLayer(MiddleEarthBiomeGenSettings genSettings) {
   }

   public int func_215728_a(IExtendedNoiseRandom noiseRand, IArea seaLayer, int x, int z) {
      IWorld world = LOTRBiomes.getServerBiomeContextWorld();
      int sea = seaLayer.func_202678_a(x, z);
      if (sea > 0) {
         return LOTRBiomes.getBiomeID((PreRegisteredLOTRBiome)LOTRBiomes.SEA, world);
      } else {
         ResourceLocation biomeName = (ResourceLocation)this.classicBiomeNames.get(noiseRand.func_202696_a(this.classicBiomeNames.size()));
         return LOTRBiomes.getBiomeIDByRegistryName(biomeName, world);
      }
   }

   public int func_215721_a(int x) {
      return x;
   }

   public int func_215722_b(int z) {
      return z;
   }
}
