package lotr.common.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import lotr.common.block.CrystalBlock;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;

public class CrystalFeature extends Feature {
   public CrystalFeature(Codec codec) {
      super(codec);
   }

   public boolean generate(ISeedReader world, ChunkGenerator generator, Random rand, BlockPos pos, CrystalFeatureConfig config) {
      Mutable movingPos = new Mutable();

      for(int l = 0; l < config.tries; ++l) {
         int x = pos.func_177958_n() - rand.nextInt(config.xspread) + rand.nextInt(config.xspread);
         int y = pos.func_177956_o() - rand.nextInt(config.yspread) + rand.nextInt(config.yspread);
         int z = pos.func_177952_p() - rand.nextInt(config.zspread) + rand.nextInt(config.zspread);
         movingPos.func_181079_c(x, y, z);
         if (world.func_175623_d(movingPos)) {
            BlockState baseState = config.blockProvider.func_225574_a_(rand, movingPos);
            List dirs = Arrays.asList(Direction.values());
            Collections.shuffle(dirs, rand);
            Iterator var13 = dirs.iterator();

            while(var13.hasNext()) {
               Direction dir = (Direction)var13.next();
               BlockState placeState = (BlockState)baseState.func_206870_a(CrystalBlock.CRYSTAL_FACING, dir);
               if (placeState.func_196955_c(world, movingPos)) {
                  world.func_180501_a(movingPos, placeState, 2);
                  break;
               }
            }
         }
      }

      return true;
   }
}
