package lotr.common.world.biome;

import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.LOTRTreeType;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.world.biome.BiomeGenBase.SpawnListEntry;

public class LOTRBiomeGenRohanWoodlands extends LOTRBiomeGenRohan {
   public LOTRBiomeGenRohanWoodlands(int i, boolean major) {
      super(i, major);
      this.field_76762_K.add(new SpawnListEntry(EntityWolf.class, 8, 4, 8));
      this.clearBiomeVariants();
      this.addBiomeVariantSet(LOTRBiomeVariant.SET_FOREST);
      this.decorator.treesPerChunk = 5;
      this.decorator.flowersPerChunk = 3;
      this.decorator.doubleFlowersPerChunk = 1;
      this.decorator.grassPerChunk = 10;
      this.decorator.doubleGrassPerChunk = 3;
      this.decorator.addTree(LOTRTreeType.BIRCH, 50);
      this.registerForestFlowers();
      this.decorator.clearVillages();
   }

   public float getChanceToSpawnAnimals() {
      return 0.75F;
   }

   public int spawnCountMultiplier() {
      return super.spawnCountMultiplier() * 2;
   }
}
