package lotr.common.world.biome;

import java.util.Random;
import lotr.common.LOTRAchievement;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.feature.LOTRWorldGenDoubleFlower;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.world.spawning.LOTRInvasions;
import lotr.common.world.structure2.LOTRWorldGenRottenHouse;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRBiomeGenGladdenFields extends LOTRBiomeGenAnduin {
   public LOTRBiomeGenGladdenFields(int i, boolean major) {
      super(i, major);
      this.clearBiomeVariants();
      this.variantChance = 1.0F;
      this.addBiomeVariantSet(LOTRBiomeVariant.SET_SWAMP);
      this.decorator.sandPerChunk = 0;
      this.decorator.quagmirePerChunk = 1;
      this.decorator.treesPerChunk = 0;
      this.decorator.willowPerChunk = 1;
      this.decorator.flowersPerChunk = 2;
      this.decorator.doubleFlowersPerChunk = 10;
      this.decorator.grassPerChunk = 8;
      this.decorator.doubleGrassPerChunk = 8;
      this.decorator.waterlilyPerChunk = 4;
      this.decorator.canePerChunk = 10;
      this.decorator.reedPerChunk = 3;
      this.decorator.addTree(LOTRTreeType.OAK_SWAMP, 1000);
      this.registerSwampFlowers();
      this.decorator.addRandomStructure(new LOTRWorldGenRottenHouse(false), 400);
      this.setBanditChance(LOTREventSpawner.EventChance.BANDIT_UNCOMMON);
      this.invasionSpawns.clearInvasions();
      this.invasionSpawns.addInvasion(LOTRInvasions.GUNDABAD, LOTREventSpawner.EventChance.UNCOMMON);
      this.invasionSpawns.addInvasion(LOTRInvasions.GUNDABAD_WARG, LOTREventSpawner.EventChance.UNCOMMON);
   }

   public LOTRAchievement getBiomeAchievement() {
      return LOTRAchievement.enterGladdenFields;
   }

   public LOTRMusicRegion.Sub getBiomeMusic() {
      return LOTRMusicRegion.RHOVANION.getSubregion("gladden");
   }

   public WorldGenerator getRandomWorldGenForDoubleFlower(Random random) {
      if (random.nextInt(3) != 0) {
         LOTRWorldGenDoubleFlower doubleFlowerGen = new LOTRWorldGenDoubleFlower();
         doubleFlowerGen.setFlowerType(1);
         return doubleFlowerGen;
      } else {
         return super.getRandomWorldGenForDoubleFlower(random);
      }
   }

   public int spawnCountMultiplier() {
      return 2;
   }
}
