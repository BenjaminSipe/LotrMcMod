package lotr.common.world.gen.layer;

import lotr.common.init.LOTRBiomes;
import lotr.common.init.PreRegisteredLOTRBiome;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.IExtendedNoiseRandom;
import net.minecraft.world.gen.area.IArea;
import net.minecraft.world.gen.layer.traits.IAreaTransformer1;

public class MEAddIslandsLayer implements IAreaTransformer1 {
   public static final MEAddIslandsLayer DEFAULT_ADD_ISLANDS = new MEAddIslandsLayer(400);
   private final int islandChance;

   public MEAddIslandsLayer(int chance) {
      this.islandChance = chance;
   }

   public int func_215728_a(IExtendedNoiseRandom noiseRand, IArea biomeLayer, int x, int z) {
      IWorld world = LOTRBiomes.getServerBiomeContextWorld();
      int biome = biomeLayer.func_202678_a(x, z);
      if (biome == LOTRBiomes.getBiomeID((PreRegisteredLOTRBiome)LOTRBiomes.SEA, world) && biomeLayer.func_202678_a(x - 1, z) == biome && biomeLayer.func_202678_a(x + 1, z) == biome && biomeLayer.func_202678_a(x, z - 1) == biome && biomeLayer.func_202678_a(x, z + 1) == biome && noiseRand.func_202696_a(this.islandChance) == 0) {
         biome = LOTRBiomes.getBiomeID((PreRegisteredLOTRBiome)LOTRBiomes.ISLAND, world);
      }

      return biome;
   }

   public int func_215721_a(int x) {
      return x;
   }

   public int func_215722_b(int z) {
      return z;
   }
}
