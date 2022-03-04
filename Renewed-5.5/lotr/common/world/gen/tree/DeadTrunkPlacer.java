package lotr.common.world.gen.tree;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import net.minecraft.block.BlockState;
import net.minecraft.block.AbstractBlock.AbstractBlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.Direction.Axis;
import net.minecraft.util.Direction.Plane;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.trunkplacer.TrunkPlacerType;

public class DeadTrunkPlacer extends ExtendedTrunkPlacer {
   protected static final Codec CODEC = RecordCodecBuilder.create((instance) -> {
      return baseCodecWithWood(instance).apply(instance, DeadTrunkPlacer::new);
   });

   protected DeadTrunkPlacer(int baseHeight, int heightRandA, int heightRandB, Optional woodProvider, Optional strippedLogProvider, Optional branchProvider) {
      super(baseHeight, heightRandA, heightRandB, woodProvider, strippedLogProvider, branchProvider);
   }

   public DeadTrunkPlacer(int baseHeight, int heightRandA, int heightRandB, BlockState wood, BlockState branch) {
      this(baseHeight, heightRandA, heightRandB, Optional.of(new SimpleBlockStateProvider(wood)), Optional.empty(), Optional.of(new SimpleBlockStateProvider(branch)));
   }

   protected TrunkPlacerType func_230381_a_() {
      return LOTRTrunkPlacers.DEAD_TRUNK_PLACER;
   }

   public List func_230382_a_(IWorldGenerationReader world, Random rand, int trunkHeight, BlockPos basePos, Set trunk, MutableBoundingBox bb, BaseTreeFeatureConfig config) {
      func_236909_a_(world, basePos.func_177977_b());

      for(int y = 0; y < trunkHeight; ++y) {
         BlockPos trunkPos = basePos.func_177981_b(y);
         func_236911_a_(world, rand, trunkPos, trunk, bb, config);
         if (rand.nextInt(6) == 0) {
            this.placeRandomSurroundingBranch(world, rand, trunkPos, trunk, bb, config);
         }
      }

      if (trunkHeight >= 3) {
         Mutable branchPos = new Mutable();
         Iterator var16 = Plane.HORIZONTAL.iterator();

         while(var16.hasNext()) {
            Direction dir = (Direction)var16.next();
            int branchLength = 2 + rand.nextInt(4);
            int branchOut = 0;
            int branchUp = trunkHeight - rand.nextInt(3);

            for(int l = 0; l < branchLength; ++l) {
               if (rand.nextInt(4) == 0) {
                  ++branchOut;
               }

               if (l > 0 && rand.nextInt(3) != 0) {
                  ++branchUp;
               }

               if (branchOut > 2 && branchUp == 0) {
                  ++branchUp;
               }

               branchPos.func_189533_g(basePos.func_177981_b(branchUp).func_177967_a(dir, branchOut));
               this.placeWood(world, rand, branchPos, trunk, bb, config, Axis.Y);
               if (rand.nextInt(8) == 0 && world.func_217375_a(branchPos, AbstractBlockState::func_196958_f)) {
                  this.placeRandomSurroundingBranch(world, rand, branchPos, trunk, bb, config);
               }
            }
         }
      }

      return ImmutableList.of();
   }

   private boolean placeRandomSurroundingBranch(IWorldGenerationReader world, Random rand, BlockPos pos, Set trunk, MutableBoundingBox bb, BaseTreeFeatureConfig config) {
      return this.placeBranch(world, rand, pos.func_177972_a(Plane.HORIZONTAL.func_179518_a(rand)), trunk, bb, config);
   }
}
