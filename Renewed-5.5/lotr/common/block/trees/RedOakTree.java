package lotr.common.block.trees;

import java.util.Random;
import lotr.common.world.biome.LOTRBiomeFeatures;
import net.minecraft.world.gen.feature.ConfiguredFeature;

public class RedOakTree extends PartyableTree {
   protected ConfiguredFeature func_225546_b_(Random rand, boolean bees) {
      return bees ? LOTRBiomeFeatures.redOakBees() : LOTRBiomeFeatures.redOak();
   }

   protected ConfiguredFeature getPartyTreeFeature(Random rand) {
      return LOTRBiomeFeatures.redOakParty();
   }
}
