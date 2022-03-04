package lotr.common.block.trees;

import java.util.Random;
import lotr.common.world.biome.LOTRBiomeFeatures;
import net.minecraft.world.gen.feature.ConfiguredFeature;

public class MirkOakTree extends PartyableTree {
   protected ConfiguredFeature func_225546_b_(Random rand, boolean bees) {
      return LOTRBiomeFeatures.mirkOak();
   }

   protected ConfiguredFeature getPartyTreeFeature(Random rand) {
      return LOTRBiomeFeatures.mirkOakParty();
   }
}
