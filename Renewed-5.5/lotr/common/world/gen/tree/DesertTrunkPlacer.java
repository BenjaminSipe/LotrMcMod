package lotr.common.world.gen.tree;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.Direction.Plane;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.foliageplacer.FoliagePlacer.Foliage;
import net.minecraft.world.gen.trunkplacer.TrunkPlacerType;

public class DesertTrunkPlacer extends ExtendedTrunkPlacer {
   protected static final Codec CODEC = RecordCodecBuilder.create((instance) -> {
      return baseCodecWithWood(instance).apply(instance, DesertTrunkPlacer::new);
   });

   protected DesertTrunkPlacer(int baseHeight, int heightRandA, int heightRandB, Optional woodProvider, Optional strippedLogProvider, Optional branchProvider) {
      super(baseHeight, heightRandA, heightRandB, woodProvider, strippedLogProvider, branchProvider);
   }

   public DesertTrunkPlacer(int baseHeight, int heightRandA, int heightRandB, BlockState wood) {
      this(baseHeight, heightRandA, heightRandB, Optional.of(new SimpleBlockStateProvider(wood)), Optional.empty(), Optional.empty());
   }

   protected TrunkPlacerType func_230381_a_() {
      return LOTRTrunkPlacers.DESERT_TRUNK_PLACER;
   }

   public List func_230382_a_(IWorldGenerationReader world, Random rand, int trunkHeight, BlockPos basePos, Set trunk, MutableBoundingBox bb, BaseTreeFeatureConfig config) {
      func_236909_a_(world, basePos.func_177977_b());

      for(int y = 0; y < trunkHeight; ++y) {
         func_236911_a_(world, rand, basePos.func_177981_b(y), trunk, bb, config);
      }

      List foliage = new ArrayList();
      int trunkTopOffset = 0;
      int branches = true;

      Mutable branchPos;
      for(Iterator var11 = Plane.HORIZONTAL.iterator(); var11.hasNext(); foliage.add(new Foliage(branchPos.func_177984_a(), 0, false))) {
         Direction branchDir = (Direction)var11.next();
         int branchLength = 1 + rand.nextInt(3);
         int branchHeight = trunkHeight - trunkTopOffset - 1 - rand.nextInt(2);
         branchPos = (new Mutable()).func_239621_a_(basePos, 0, branchHeight, 0);

         for(int l = 0; l < branchLength; ++l) {
            if (rand.nextInt(3) != 0) {
               branchPos.func_189536_c(Direction.UP);
            }

            if (rand.nextInt(3) != 0) {
               branchPos.func_189536_c(branchDir);
            }

            if (!func_236911_a_(world, rand, branchPos, trunk, bb, config)) {
               break;
            }
         }
      }

      return foliage;
   }
}
