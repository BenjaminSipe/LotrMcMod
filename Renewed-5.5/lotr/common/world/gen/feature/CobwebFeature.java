package lotr.common.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;

public class CobwebFeature extends Feature {
   public CobwebFeature(Codec codec) {
      super(codec);
   }

   public boolean generate(ISeedReader world, ChunkGenerator generator, Random rand, BlockPos pos, CobwebFeatureConfig config) {
      Mutable movingPos = new Mutable();
      Mutable adjPos = new Mutable();

      for(int l = 0; l < config.tries; ++l) {
         int x = pos.func_177958_n() - rand.nextInt(config.xspread) + rand.nextInt(config.xspread);
         int y = pos.func_177956_o() - rand.nextInt(config.yspread) + rand.nextInt(config.yspread);
         int z = pos.func_177952_p() - rand.nextInt(config.zspread) + rand.nextInt(config.zspread);
         movingPos.func_181079_c(x, y, z);
         if (world.func_175623_d(movingPos)) {
            boolean anyStoneAdj = false;
            Direction[] var13 = Direction.values();
            int var14 = var13.length;

            for(int var15 = 0; var15 < var14; ++var15) {
               Direction dir = var13[var15];
               adjPos.func_189533_g(movingPos.func_177967_a(dir, 1));
               BlockState adjState = world.func_180495_p(adjPos);
               if (adjState.func_200015_d(world, adjPos) && adjState.func_185904_a() == Material.field_151576_e) {
                  anyStoneAdj = true;
                  break;
               }
            }

            if (anyStoneAdj) {
               world.func_180501_a(movingPos, config.blockProvider.func_225574_a_(rand, movingPos), 2);
            }
         }
      }

      return true;
   }
}
