package lotr.common.world.gen.layer;

import lotr.common.init.LOTRBiomes;
import lotr.common.init.PreRegisteredLOTRBiome;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.area.IArea;
import net.minecraft.world.gen.layer.traits.IAreaTransformer2;

public enum BiomeSubtypesLayer implements IAreaTransformer2 {
   INSTANCE;

   public int func_215723_a(INoiseRandom context, IArea biomeLayer, IArea subtypeInitLayer, int x, int z) {
      int biomeID = biomeLayer.func_202678_a(x, z);
      int subtypeSeed = subtypeInitLayer.func_202678_a(x, z);
      float subtypeF = SeedBiomeSubtypesLayer.randFloat(subtypeSeed);
      IWorld world = LOTRBiomes.getServerBiomeContextWorld();
      int newBiomeID = biomeID;
      if (biomeID == LOTRBiomes.getBiomeID((PreRegisteredLOTRBiome)LOTRBiomes.SHIRE, world)) {
         if (subtypeF < 0.15F) {
            newBiomeID = LOTRBiomes.getBiomeID((PreRegisteredLOTRBiome)LOTRBiomes.SHIRE_WOODLANDS, world);
         }
      } else if (biomeID == LOTRBiomes.getBiomeID((PreRegisteredLOTRBiome)LOTRBiomes.NORTHLANDS, world)) {
         if (subtypeF < 0.1F) {
            newBiomeID = LOTRBiomes.getBiomeID((PreRegisteredLOTRBiome)LOTRBiomes.NORTHLANDS_FOREST, world);
         } else if (subtypeF < 0.15F) {
            newBiomeID = LOTRBiomes.getBiomeID((PreRegisteredLOTRBiome)LOTRBiomes.DENSE_NORTHLANDS_FOREST, world);
         }
      } else if (biomeID == LOTRBiomes.getBiomeID((PreRegisteredLOTRBiome)LOTRBiomes.SNOWY_NORTHLANDS, world)) {
         if (subtypeF < 0.1F) {
            newBiomeID = LOTRBiomes.getBiomeID((PreRegisteredLOTRBiome)LOTRBiomes.SNOWY_NORTHLANDS_FOREST, world);
         } else if (subtypeF < 0.15F) {
            newBiomeID = LOTRBiomes.getBiomeID((PreRegisteredLOTRBiome)LOTRBiomes.DENSE_SNOWY_NORTHLANDS_FOREST, world);
         }
      } else if (biomeID == LOTRBiomes.getBiomeID((PreRegisteredLOTRBiome)LOTRBiomes.NORTHLANDS_FOREST, world)) {
         if (subtypeF < 0.33F) {
            newBiomeID = LOTRBiomes.getBiomeID((PreRegisteredLOTRBiome)LOTRBiomes.DENSE_NORTHLANDS_FOREST, world);
         }
      } else if (biomeID == LOTRBiomes.getBiomeID((PreRegisteredLOTRBiome)LOTRBiomes.SNOWY_NORTHLANDS_FOREST, world)) {
         if (subtypeF < 0.33F) {
            newBiomeID = LOTRBiomes.getBiomeID((PreRegisteredLOTRBiome)LOTRBiomes.DENSE_SNOWY_NORTHLANDS_FOREST, world);
         }
      } else if (biomeID == LOTRBiomes.getBiomeID((PreRegisteredLOTRBiome)LOTRBiomes.SEA, world) && biomeLayer.func_202678_a(x - 1, z) == biomeID && biomeLayer.func_202678_a(x + 1, z) == biomeID && biomeLayer.func_202678_a(x, z - 1) == biomeID && biomeLayer.func_202678_a(x, z + 1) == biomeID && subtypeF < 0.02F) {
         newBiomeID = LOTRBiomes.getBiomeID((PreRegisteredLOTRBiome)LOTRBiomes.ISLAND, world);
      }

      return newBiomeID;
   }

   public int func_215721_a(int x) {
      return x;
   }

   public int func_215722_b(int z) {
      return z;
   }
}
