package lotr.common.world.gen.tree;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.Direction.Axis;
import net.minecraft.util.Direction.Plane;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.foliageplacer.FoliagePlacer.Foliage;
import net.minecraft.world.gen.trunkplacer.TrunkPlacerType;

public class MirkOakTrunkPlacer extends ExtendedTrunkPlacer {
   protected static final Codec CODEC = RecordCodecBuilder.create((instance) -> {
      return baseCodecWithWood(instance).apply(instance, MirkOakTrunkPlacer::new);
   });
   private final boolean isDead;
   private final int trunkWidth;
   private final boolean hasRoots;

   protected MirkOakTrunkPlacer(int baseHeight, int heightRandA, int heightRandB, Optional woodProvider, Optional strippedLogProvider, Optional branchProvider) {
      super(baseHeight, heightRandA, heightRandB, woodProvider, strippedLogProvider, branchProvider);
      this.isDead = false;
      this.trunkWidth = 0;
      this.hasRoots = true;
   }

   public MirkOakTrunkPlacer(int baseHeight, int heightRandA, int heightRandB, BlockState wood, BlockState branch) {
      this(baseHeight, heightRandA, heightRandB, Optional.of(new SimpleBlockStateProvider(wood)), Optional.empty(), Optional.of(new SimpleBlockStateProvider(branch)));
   }

   protected TrunkPlacerType func_230381_a_() {
      return LOTRTrunkPlacers.MIRK_OAK_TRUNK_PLACER;
   }

   public List func_230382_a_(IWorldGenerationReader world, Random rand, int trunkHeight, BlockPos basePos, Set trunk, MutableBoundingBox bb, BaseTreeFeatureConfig config) {
      func_236909_a_(world, basePos.func_177977_b());

      for(int y = 0; y < trunkHeight; ++y) {
         func_236911_a_(world, rand, basePos.func_177981_b(y), trunk, bb, config);
      }

      List foliage = new ArrayList();
      this.addLeafCanopy(world, rand, basePos.func_177981_b(trunkHeight - 1), trunk, foliage, bb, config);
      int roots = 4 + rand.nextInt(1);

      for(int l = 0; l < roots; ++l) {
         Mutable rootPos = (new Mutable()).func_189533_g(basePos).func_189534_c(Direction.UP, 1 + rand.nextInt(1));
         int rootLength = 4 + rand.nextInt(4);
         Direction rootDir = Plane.HORIZONTAL.func_179518_a(rand);
         rootPos.func_189534_c(rootDir, 1);
         this.growRootsDownBranchingOut(world, rand, rootPos, rootLength, rootDir, 3, trunk, bb, config);
      }

      return foliage;
   }

   private void addLeafCanopy(IWorldGenerationReader world, Random rand, BlockPos pos, Set trunk, List foliage, MutableBoundingBox bb, BaseTreeFeatureConfig config) {
      int leafStart = 2;
      int leafTop = leafStart + 3;
      int baseMaxRange = 3;
      int addMaxRange = rand.nextInt(2);
      int var10000 = baseMaxRange + addMaxRange;
      foliage.add(new Foliage(pos.func_177981_b(leafTop), addMaxRange, false));
      List woodPositions = new ArrayList();

      for(int l = 0; l <= leafStart; ++l) {
         BlockPos layerPos = pos.func_177981_b(l);
         woodPositions.add(layerPos);
         addLateralOffsets(woodPositions, layerPos, l + 1);
         if (l > 0) {
            addDiagonalOffsets(woodPositions, layerPos, l);
            if (l == leafStart && addMaxRange > 0) {
               addLateralOffsets(woodPositions, layerPos, l + 2);
               addDiagonalOffsets(woodPositions, layerPos, l + 1);
            }
         }
      }

      woodPositions.forEach((woodPos) -> {
         this.placeWood(world, rand, woodPos, trunk, bb, config, Axis.Y);
      });
   }

   private static void addLateralOffsets(List list, BlockPos midPos, int offset) {
      Plane.HORIZONTAL.forEach((dir) -> {
         list.add(midPos.func_177967_a(dir, offset));
      });
   }

   private static void addDiagonalOffsets(List list, BlockPos midPos, int offset) {
      list.add(midPos.func_177982_a(-offset, 0, -offset));
      list.add(midPos.func_177982_a(-offset, 0, offset));
      list.add(midPos.func_177982_a(offset, 0, -offset));
      list.add(midPos.func_177982_a(offset, 0, offset));
   }
}
