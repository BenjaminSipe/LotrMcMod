package lotr.common.block.trees;

import java.util.Random;
import lotr.common.world.biome.LOTRBiomeFeatures;
import net.minecraft.world.gen.feature.ConfiguredFeature;

public class MallornTree extends PartyableCrossBigTree {
   protected ConfiguredFeature func_225546_b_(Random rand, boolean bees) {
      return bees ? LOTRBiomeFeatures.mallornBees() : LOTRBiomeFeatures.mallorn();
   }

   protected ConfiguredFeature getCrossTreeFeature(Random rand) {
      return LOTRBiomeFeatures.mallornBoughs();
   }

   protected ConfiguredFeature getPartyTreeFeature(Random rand) {
      return LOTRBiomeFeatures.mallornParty();
   }
}
