package lotr.common.block.trees;

import java.util.Random;
import lotr.common.world.biome.LOTRBiomeFeatures;
import net.minecraft.block.trees.Tree;
import net.minecraft.world.gen.feature.ConfiguredFeature;

public class HollyTree extends Tree {
   protected ConfiguredFeature func_225546_b_(Random rand, boolean bees) {
      return bees ? LOTRBiomeFeatures.hollyBees() : LOTRBiomeFeatures.holly();
   }
}
