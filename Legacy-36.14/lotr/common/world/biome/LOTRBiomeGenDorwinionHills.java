package lotr.common.world.biome;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRMod;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.structure2.LOTRWorldGenStoneRuin;

public class LOTRBiomeGenDorwinionHills extends LOTRBiomeGenDorwinion {
   public LOTRBiomeGenDorwinionHills(int i, boolean major) {
      super(i, major);
      this.field_76753_B = LOTRMod.rock;
      this.fillerBlockMeta = 5;
      this.biomeTerrain.setXZScale(50.0D);
      this.clearBiomeVariants();
      this.addBiomeVariant(LOTRBiomeVariant.FOREST_LIGHT);
      this.decorator.flowersPerChunk = 3;
      this.decorator.grassPerChunk = 10;
      this.decorator.doubleGrassPerChunk = 5;
      this.decorator.clearRandomStructures();
      this.decorator.addRandomStructure(new LOTRWorldGenStoneRuin.DORWINION(1, 4), 800);
   }

   public LOTRAchievement getBiomeAchievement() {
      return LOTRAchievement.enterDorwinionHills;
   }

   public boolean hasDomesticAnimals() {
      return false;
   }

   public float getTreeIncreaseChance() {
      return 0.25F;
   }

   public int spawnCountMultiplier() {
      return super.spawnCountMultiplier() * 3;
   }
}
