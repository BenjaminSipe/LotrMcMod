package lotr.common.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Iterator;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.Direction.Plane;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap.Type;
import net.minecraft.world.gen.feature.Feature;

public class MordorBasaltFeature extends Feature {
   public MordorBasaltFeature(Codec codec) {
      super(codec);
   }

   public boolean generate(ISeedReader world, ChunkGenerator generator, Random rand, BlockPos pos, MordorBasaltFeatureConfig config) {
      int radius = config.radius.func_242259_a(rand);
      float density = MathHelper.func_151240_a(rand, config.minDensity, config.maxDensity);
      float prominence = MathHelper.func_151240_a(rand, config.minProminence, config.maxProminence);
      boolean lava = rand.nextFloat() < config.lavaChance;
      int numTries = (int)((double)((float)(radius * radius)) * 3.141592653589793D * (double)density);
      Mutable movingPos = (new Mutable()).func_189533_g(pos);

      label76:
      for(int l = 0; l < numTries; ++l) {
         int moveX = rand.nextFloat() < density ? 1 : 2;
         int moveZ = rand.nextFloat() < density ? 1 : 2;
         movingPos.func_196234_d(MathHelper.func_76136_a(rand, -moveX, moveX), 0, MathHelper.func_76136_a(rand, -moveZ, moveZ));
         int dx = movingPos.func_177958_n() - pos.func_177958_n();
         int dz = movingPos.func_177952_p() - pos.func_177952_p();
         float rSq = (float)(dx * dx + dz * dz) / (float)(radius * radius);
         if (rSq > 1.0F) {
            movingPos.func_189533_g(pos);
         }

         int topY = world.func_201676_a(Type.OCEAN_FLOOR_WG, movingPos.func_177958_n(), movingPos.func_177952_p()) - 1;
         movingPos.func_185336_p(topY);
         boolean lavaHere = lava && rSq <= 0.25F && rand.nextInt(4) == 0 && this.canPlaceLavaHere(world, movingPos);
         int randDepth = config.depth.func_242259_a(rand);

         for(int d = 0; d < randDepth; ++d) {
            BlockState state = world.func_180495_p(movingPos);
            if (!config.surfaceBlocks.contains(state.func_177230_c())) {
               if (d == 0) {
                  continue label76;
               }
               break;
            }

            if (lavaHere && d == 0) {
               this.func_230367_a_(world, movingPos, Blocks.field_150353_l.func_176223_P());
            } else {
               this.func_230367_a_(world, movingPos, Blocks.field_235337_cO_.func_176223_P());
            }

            movingPos.func_189536_c(Direction.DOWN);
         }

         if (!lavaHere && rand.nextFloat() < prominence) {
            BlockPos abovePos = new BlockPos(movingPos.func_177958_n(), topY + 1, movingPos.func_177952_p());
            if (world.func_175623_d(abovePos)) {
               this.func_230367_a_(world, abovePos, Blocks.field_235337_cO_.func_176223_P());
            }
         }
      }

      return true;
   }

   private boolean canPlaceLavaHere(IWorld world, BlockPos pos) {
      if (world.func_204610_c(pos.func_177984_a()).func_206888_e()) {
         Iterator var3 = Plane.HORIZONTAL.iterator();

         Direction dir;
         do {
            if (!var3.hasNext()) {
               return true;
            }

            dir = (Direction)var3.next();
         } while(world.func_180495_p(pos.func_177972_a(dir)).func_200132_m());

         return false;
      } else {
         return false;
      }
   }
}
