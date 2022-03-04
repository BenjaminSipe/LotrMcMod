package lotr.common.world.biome;

import lotr.common.entity.animal.LOTREntityBird;
import lotr.common.entity.animal.LOTREntityButterfly;
import lotr.common.entity.animal.LOTREntityFlamingo;
import lotr.common.entity.animal.LOTREntityJungleScorpion;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.LOTRTreeType;
import net.minecraft.world.biome.BiomeGenBase.SpawnListEntry;

public class LOTRBiomeGenFarHaradCloudForest extends LOTRBiomeGenFarHarad {
   public LOTRBiomeGenFarHaradCloudForest(int i, boolean major) {
      super(i, major);
      this.field_76762_K.clear();
      this.field_76762_K.add(new SpawnListEntry(LOTREntityFlamingo.class, 10, 4, 4));
      this.spawnableLOTRAmbientList.clear();
      this.spawnableLOTRAmbientList.add(new SpawnListEntry(LOTREntityBird.class, 10, 4, 4));
      this.spawnableLOTRAmbientList.add(new SpawnListEntry(LOTREntityButterfly.class, 10, 4, 4));
      this.field_76761_J.add(new SpawnListEntry(LOTREntityJungleScorpion.class, 30, 4, 4));
      this.addBiomeVariantSet(LOTRBiomeVariant.SET_FOREST);
      this.decorator.treesPerChunk = 10;
      this.decorator.vinesPerChunk = 50;
      this.decorator.flowersPerChunk = 4;
      this.decorator.doubleFlowersPerChunk = 4;
      this.decorator.grassPerChunk = 15;
      this.decorator.doubleGrassPerChunk = 10;
      this.decorator.enableFern = true;
      this.decorator.melonPerChunk = 0.1F;
      this.decorator.clearTrees();
      this.decorator.addTree(LOTRTreeType.JUNGLE_CLOUD, 4000);
      this.decorator.addTree(LOTRTreeType.JUNGLE, 500);
      this.decorator.addTree(LOTRTreeType.JUNGLE_SHRUB, 1000);
      this.decorator.addTree(LOTRTreeType.MANGO, 20);
      this.registerJungleFlowers();
      this.biomeColors.setGrass(2007124);
      this.biomeColors.setFoliage(428338);
      this.biomeColors.setSky(11452859);
      this.biomeColors.setFoggy(true);
   }

   public LOTRMusicRegion.Sub getBiomeMusic() {
      return LOTRMusicRegion.FAR_HARAD_JUNGLE.getSubregion("cloudForest");
   }

   public float getChanceToSpawnAnimals() {
      return super.getChanceToSpawnAnimals() * 0.5F;
   }
}
