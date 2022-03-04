package lotr.common.world.biome;

import lotr.common.LOTRMod;
import lotr.common.world.map.LOTRRoadType;
import net.minecraft.world.gen.feature.WorldGenMinable;

public class LOTRBiomeGenEasternDesolation extends LOTRBiomeGenMordor {
   public LOTRBiomeGenEasternDesolation(int i, boolean major) {
      super(i, major);
      this.field_76752_A = LOTRMod.mordorDirt;
      this.field_76753_B = LOTRMod.mordorDirt;
      this.decorator.addSoil(new WorldGenMinable(LOTRMod.mordorGravel, 0, 60, LOTRMod.mordorDirt), 6.0F, 60, 100);
      this.decorator.grassPerChunk = 3;
      this.biomeColors.setSky(9538431);
      this.biomeColors.resetClouds();
      this.biomeColors.resetFog();
   }

   public LOTRRoadType getRoadBlock() {
      return LOTRRoadType.DIRT;
   }

   public LOTRMusicRegion.Sub getBiomeMusic() {
      return LOTRMusicRegion.MORDOR.getSubregion("east");
   }

   public float getTreeIncreaseChance() {
      return 0.5F;
   }

   public int spawnCountMultiplier() {
      return super.spawnCountMultiplier() * 2;
   }
}
