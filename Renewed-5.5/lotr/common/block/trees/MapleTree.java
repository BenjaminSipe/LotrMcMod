package lotr.common.block.trees;

import java.util.Random;
import lotr.common.world.biome.LOTRBiomeFeatures;
import net.minecraft.world.gen.feature.ConfiguredFeature;

public class MapleTree extends PartyableTree {
   protected ConfiguredFeature func_225546_b_(Random rand, boolean bees) {
      if (rand.nextInt(10) == 0) {
         return bees ? LOTRBiomeFeatures.mapleFancyBees() : LOTRBiomeFeatures.mapleFancy();
      } else {
         return bees ? LOTRBiomeFeatures.mapleBees() : LOTRBiomeFeatures.maple();
      }
   }

   protected ConfiguredFeature getPartyTreeFeature(Random rand) {
      return LOTRBiomeFeatures.mapleParty();
   }
}
