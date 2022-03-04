package lotr.common.block.trees;

import java.util.Random;
import lotr.common.world.biome.LOTRBiomeFeatures;
import lotr.common.world.gen.feature.WeightedRandomFeatureConfig;
import net.minecraft.block.trees.Tree;
import net.minecraft.world.gen.feature.ConfiguredFeature;

public class BirchTreeAlt extends Tree {
   protected ConfiguredFeature func_225546_b_(Random rand, boolean bees) {
      return bees ? ((WeightedRandomFeatureConfig)LOTRBiomeFeatures.birchBees().field_222738_b).getRandomFeature(rand) : ((WeightedRandomFeatureConfig)LOTRBiomeFeatures.birch().field_222738_b).getRandomFeature(rand);
   }
}
