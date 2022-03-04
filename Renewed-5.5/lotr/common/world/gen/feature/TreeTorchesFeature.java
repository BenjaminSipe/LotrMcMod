package lotr.common.world.gen.feature;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.block.WallTorchBlock;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.Direction.Plane;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.BlockStateProvidingFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

public class TreeTorchesFeature extends Feature {
   public TreeTorchesFeature(Codec codec) {
      super(codec);
   }

   public boolean generate(ISeedReader world, ChunkGenerator generator, Random rand, BlockPos pos, BlockStateProvidingFeatureConfig config) {
      if (world.func_175623_d(pos.func_177977_b()) && world.func_175623_d(pos) && world.func_175623_d(pos.func_177984_a())) {
         BlockState torch = config.field_227268_a_.func_225574_a_(rand, pos);
         List shuffledDirections = Lists.newArrayList(Plane.HORIZONTAL);
         Collections.shuffle(shuffledDirections, rand);
         Iterator var8 = shuffledDirections.iterator();

         while(var8.hasNext()) {
            Direction dir = (Direction)var8.next();
            BlockPos offsetPos = pos.func_177972_a(dir);
            BlockState offsetState = world.func_180495_p(offsetPos);
            if (offsetState.func_235714_a_(BlockTags.field_200031_h)) {
               BlockPos adjPos1 = pos.func_177972_a(dir.func_176746_e());
               BlockPos adjPos2 = pos.func_177972_a(dir.func_176735_f());
               if (world.func_175623_d(adjPos1) && world.func_175623_d(adjPos2)) {
                  world.func_180501_a(pos, (BlockState)torch.func_206870_a(WallTorchBlock.field_196532_a, dir.func_176734_d()), 2);
                  return true;
               }
            }
         }

         return false;
      } else {
         return false;
      }
   }
}
