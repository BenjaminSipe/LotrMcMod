package lotr.common.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import lotr.common.world.map.RoadPointCache;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap.Type;
import net.minecraft.world.gen.feature.Feature;

public class BoulderFeature extends Feature {
   public BoulderFeature(Codec codec) {
      super(codec);
   }

   public boolean generate(ISeedReader world, ChunkGenerator generator, Random rand, BlockPos pos, BoulderFeatureConfig config) {
      world.func_226691_t_(pos);
      int boulderWidth = MathHelper.func_76136_a(rand, config.minWidth, config.maxWidth);
      if (!RoadPointCache.checkNotGeneratingWithinRangeOfRoad(world, pos, boulderWidth)) {
         return false;
      } else {
         int highestHeight = pos.func_177956_o();
         int lowestHeight = highestHeight;

         int spheres;
         int heightValue;
         for(int i = -boulderWidth; i <= boulderWidth; ++i) {
            for(spheres = -boulderWidth; spheres <= boulderWidth; ++spheres) {
               BlockPos heightPos = world.func_205770_a(Type.WORLD_SURFACE_WG, pos.func_177982_a(i, 0, spheres));
               if (!this.isSurfaceBlock(world, heightPos.func_177977_b())) {
                  return false;
               }

               heightValue = heightPos.func_177956_o();
               if (heightValue > highestHeight) {
                  highestHeight = heightValue;
               }

               if (heightValue < lowestHeight) {
                  lowestHeight = heightValue;
               }
            }
         }

         if (highestHeight - lowestHeight > config.heightCheck) {
            return false;
         } else {
            Mutable movingPos = new Mutable();
            spheres = MathHelper.func_76136_a(rand, 1, Math.max(1, boulderWidth));

            for(int l = 0; l < spheres; ++l) {
               heightValue = MathHelper.func_76136_a(rand, -boulderWidth, boulderWidth);
               int zOffset = MathHelper.func_76136_a(rand, -boulderWidth, boulderWidth);
               BlockPos boulderPos = world.func_205770_a(Type.WORLD_SURFACE_WG, pos.func_177982_a(heightValue, 0, zOffset));
               int sphereWidth = MathHelper.func_76136_a(rand, config.minWidth, config.maxWidth);

               for(int i = -sphereWidth; i <= sphereWidth; ++i) {
                  for(int j = -sphereWidth; j <= sphereWidth; ++j) {
                     for(int k = -sphereWidth; k <= sphereWidth; ++k) {
                        int dist = i * i + j * j + k * k;
                        if (dist < sphereWidth * sphereWidth || dist < (sphereWidth + 1) * (sphereWidth + 1) && rand.nextInt(3) == 0) {
                           movingPos.func_189533_g(boulderPos.func_177982_a(i, j, k));

                           for(BlockPos below = movingPos.func_177977_b(); movingPos.func_177956_o() >= 0 && !world.func_180495_p(below).func_200015_d(world, below); below = below.func_177977_b()) {
                              movingPos.func_189533_g(below);
                           }

                           world.func_180501_a(movingPos, config.stateProvider.func_225574_a_(rand, movingPos), 3);
                           LOTRFeatures.setGrassToDirtBelow(world, movingPos);
                        }
                     }
                  }
               }
            }

            return true;
         }
      }
   }

   private boolean isSurfaceBlock(IWorld world, BlockPos pos) {
      return LOTRFeatures.isSurfaceBlock(world, pos);
   }
}
