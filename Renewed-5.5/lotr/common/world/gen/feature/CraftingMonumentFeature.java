package lotr.common.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import lotr.common.block.DoubleTorchBlock;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;

public class CraftingMonumentFeature extends Feature {
   public CraftingMonumentFeature(Codec codec) {
      super(codec);
   }

   public boolean generate(ISeedReader world, ChunkGenerator generator, Random rand, BlockPos pos, CraftingMonumentFeatureConfig config) {
      if (!LOTRFeatures.isSurfaceBlock(world, pos.func_177977_b())) {
         return false;
      } else {
         int up = 2 + rand.nextInt(3);
         BlockPos tablePos = pos.func_177981_b(up);
         world.func_180501_a(tablePos, config.craftingTable, 2);
         Mutable movingPos = new Mutable();
         Mutable brickPos = new Mutable();
         int maxSteps = 8;
         int decoLayer = 2;

         for(int l = 1; l <= maxSteps; ++l) {
            boolean setAnyThisLayer = false;
            int y = -l;

            for(int x = -l; x <= l; ++x) {
               for(int z = -l; z <= l; ++z) {
                  movingPos.func_189533_g(tablePos).func_196234_d(x, y, z);
                  int maxY = movingPos.func_177956_o();
                  int minY = maxY;
                  if (l == maxSteps) {
                     minY = 0;
                  }

                  brickPos.func_189533_g(movingPos);

                  BlockState post;
                  for(int y1 = maxY; y1 >= minY; --y1) {
                     brickPos.func_185336_p(y1);
                     post = world.func_180495_p(brickPos);
                     if (post.func_200015_d(world, brickPos)) {
                        break;
                     }

                     BlockState newState = LOTRFeatures.getBlockStateInContext(config.baseBlockProvider.func_225574_a_(rand, brickPos), world, brickPos);
                     world.func_180501_a(brickPos, newState, 2);
                     LOTRFeatures.setGrassToDirtBelow(world, brickPos);
                     setAnyThisLayer = true;
                  }

                  if (l == decoLayer && Math.abs(x) == decoLayer && Math.abs(z) == decoLayer) {
                     BlockPos postPos = movingPos.func_177984_a();
                     post = config.postProvider.func_225574_a_(rand, postPos);
                     world.func_180501_a(postPos, post, 2);
                     world.func_180501_a(postPos.func_177984_a(), post, 2);
                     BlockPos torchPos = postPos.func_177981_b(2);
                     BlockState torch = config.torchProvider.func_225574_a_(rand, torchPos);
                     if (torch.func_177230_c() instanceof DoubleTorchBlock) {
                        ((DoubleTorchBlock)torch.func_177230_c()).placeTorchAt(world, torchPos, 2);
                     } else {
                        world.func_180501_a(torchPos, torch, 2);
                     }
                  }
               }
            }

            if (!setAnyThisLayer && l >= decoLayer) {
               break;
            }
         }

         return true;
      }
   }
}
