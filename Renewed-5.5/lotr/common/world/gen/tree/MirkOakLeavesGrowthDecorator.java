package lotr.common.world.gen.tree;

import com.mojang.serialization.Codec;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import lotr.common.block.MirkOakLeavesBlock;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.feature.TreeFeature;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;

public class MirkOakLeavesGrowthDecorator extends TreeDecorator {
   public static final Codec CODEC = Codec.unit(MirkOakLeavesGrowthDecorator::new);

   protected TreeDecoratorType func_230380_a_() {
      return LOTRTreeDecorators.MIRK_OAK_LEAVES_GROWTH;
   }

   public void func_225576_a_(ISeedReader world, Random rand, List trunk, List leaves, Set decoSet, MutableBoundingBox bb) {
      Set addedLeavesPositions = new HashSet();
      Iterator var8 = leaves.iterator();

      while(true) {
         BlockPos leavesPos;
         int leavesBelow;
         do {
            if (!var8.hasNext()) {
               leaves.addAll(addedLeavesPositions);
               decoSet.addAll(addedLeavesPositions);
               var8 = leaves.iterator();

               while(var8.hasNext()) {
                  leavesPos = (BlockPos)var8.next();
                  BlockState leafState = world.func_180495_p(leavesPos);
                  if (leafState.func_177230_c() instanceof MirkOakLeavesBlock) {
                     BlockPos belowLeavesPos = leavesPos.func_177977_b();
                     leafState = leafState.func_196956_a(Direction.DOWN, world.func_180495_p(belowLeavesPos), world, leavesPos, belowLeavesPos);
                     world.func_180501_a(leavesPos, leafState, 3);
                  }
               }

               return;
            }

            leavesPos = (BlockPos)var8.next();
            leavesBelow = 0;
            if (world.func_175623_d(leavesPos.func_177977_b()) && rand.nextInt(3) == 0) {
               ++leavesBelow;
            }
         } while(leavesBelow <= 0);

         BlockState leafState = world.func_180495_p(leavesPos);
         Mutable hangingPos = (new Mutable()).func_189533_g(leavesPos);

         for(int i = 0; i < leavesBelow; ++i) {
            hangingPos.func_189536_c(Direction.DOWN);
            if (TreeFeature.func_236404_a_(world, hangingPos)) {
               this.func_227423_a_(world, hangingPos.func_185334_h(), leafState, addedLeavesPositions, bb);
            }
         }
      }
   }
}
