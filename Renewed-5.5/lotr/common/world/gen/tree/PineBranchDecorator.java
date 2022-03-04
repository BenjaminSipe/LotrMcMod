package lotr.common.world.gen.tree;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.Random;
import java.util.Set;
import lotr.common.block.AxialSlabBlock;
import lotr.common.block.LOTRBlockStates;
import net.minecraft.block.BlockState;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.state.properties.SlabType;
import net.minecraft.util.Direction;
import net.minecraft.util.Direction.AxisDirection;
import net.minecraft.util.Direction.Plane;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.blockstateprovider.BlockStateProvider;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.TreeFeature;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;

public class PineBranchDecorator extends TreeDecorator {
   public static final Codec CODEC = RecordCodecBuilder.create((instance) -> {
      return instance.group(BlockStateProvider.field_236796_a_.fieldOf("wood_provider").forGetter((deco) -> {
         return deco.woodProvider;
      }), Codec.floatRange(0.0F, 1.0F).fieldOf("probability").forGetter((deco) -> {
         return deco.prob;
      })).apply(instance, PineBranchDecorator::new);
   });
   private final BlockStateProvider woodProvider;
   private final float prob;

   public PineBranchDecorator(BlockStateProvider log, float f) {
      this.woodProvider = log;
      this.prob = f;
   }

   public PineBranchDecorator(BlockState log, float f) {
      this((BlockStateProvider)(new SimpleBlockStateProvider(log)), f);
   }

   protected TreeDecoratorType func_230380_a_() {
      return LOTRTreeDecorators.PINE_BRANCH;
   }

   public void func_225576_a_(ISeedReader world, Random rand, List trunk, List leaves, Set decoSet, MutableBoundingBox bb) {
      int baseY = ((BlockPos)trunk.get(0)).func_177956_o();
      int trunkHeight = trunk.size();
      Direction[] lastDir = new Direction[1];
      trunk.stream().filter((pos) -> {
         int diff = pos.func_177956_o() - baseY;
         return diff >= 3 && diff < trunkHeight - 3;
      }).forEach((pos) -> {
         if (rand.nextFloat() < this.prob) {
            Direction dir = Plane.HORIZONTAL.func_179518_a(rand);
            if (dir != lastDir[0]) {
               lastDir[0] = dir;
               BlockPos branchPos = pos.func_177982_a(dir.func_82601_c(), 0, dir.func_82599_e());
               if (TreeFeature.func_236297_b_(world, branchPos)) {
                  BlockState blockstate = this.woodProvider.func_225574_a_(rand, branchPos);
                  if (blockstate.func_177230_c() instanceof RotatedPillarBlock) {
                     blockstate = (BlockState)blockstate.func_206870_a(RotatedPillarBlock.field_176298_M, dir.func_176740_k());
                  } else if (blockstate.func_177230_c() instanceof AxialSlabBlock && blockstate.func_235901_b_(LOTRBlockStates.SLAB_AXIS)) {
                     blockstate = (BlockState)((BlockState)blockstate.func_206870_a(LOTRBlockStates.SLAB_AXIS, dir.func_176740_k())).func_206870_a(SlabBlock.field_196505_a, dir.func_176743_c() == AxisDirection.NEGATIVE ? SlabType.TOP : SlabType.BOTTOM);
                  }

                  this.func_227423_a_(world, branchPos, blockstate, decoSet, bb);
               }
            }
         }

      });
   }
}
