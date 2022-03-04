package lotr.common.block.trees;

import java.util.Random;
import lotr.common.world.biome.LOTRBiomeFeatures;
import net.minecraft.world.gen.feature.ConfiguredFeature;

public class LebethronTree extends PartyableTree {
   protected ConfiguredFeature func_225546_b_(Random rand, boolean bees) {
      if (rand.nextInt(10) == 0) {
         return bees ? LOTRBiomeFeatures.lebethronFancyBees() : LOTRBiomeFeatures.lebethronFancy();
      } else {
         return bees ? LOTRBiomeFeatures.lebethronBees() : LOTRBiomeFeatures.lebethron();
      }
   }

   protected ConfiguredFeature getPartyTreeFeature(Random rand) {
      return LOTRBiomeFeatures.lebethronParty();
   }
}
