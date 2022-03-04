package lotr.common.world.gen.tree;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.function.Predicate;
import net.minecraft.block.BlockState;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.Direction.Axis;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.blockstateprovider.BlockStateProvider;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.foliageplacer.FoliagePlacer.Foliage;
import net.minecraft.world.gen.trunkplacer.TrunkPlacerType;

public class FangornTrunkPlacer extends ExtendedTrunkPlacer {
   protected static final Codec CODEC = RecordCodecBuilder.create((instance) -> {
      return baseCodecWithWood(instance).apply(instance, FangornTrunkPlacer::new);
   });
   private final boolean generateLeaves;

   protected FangornTrunkPlacer(int baseHeight, int heightRandA, int heightRandB, Optional woodProvider, Optional strippedLogProvider, Optional branchProvider) {
      super(baseHeight, heightRandA, heightRandB, woodProvider, strippedLogProvider, branchProvider);
      this.generateLeaves = true;
   }

   public FangornTrunkPlacer(int baseHeight, int heightRandA, int heightRandB, BlockState wood, BlockState strippedLog) {
      this(baseHeight, heightRandA, heightRandB, Optional.of(new SimpleBlockStateProvider(wood)), Optional.of(new SimpleBlockStateProvider(strippedLog)), Optional.empty());
   }

   protected TrunkPlacerType func_230381_a_() {
      return LOTRTrunkPlacers.FANGORN_TRUNK_PLACER;
   }

   public List func_230382_a_(IWorldGenerationReader world, Random rand, int trunkHeight, BlockPos basePos, Set trunk, MutableBoundingBox bb, BaseTreeFeatureConfig config) {
      int trunkRadiusMin = (int)((float)trunkHeight * 0.125F);
      int trunkRadiusMax = trunkRadiusMin + 4;
      int xSlope = MathHelper.func_76136_a(rand, 4, 10) * (rand.nextBoolean() ? -1 : 1);
      int zSlope = MathHelper.func_76136_a(rand, 4, 10) * (rand.nextBoolean() ? -1 : 1);
      List foliage = new ArrayList();
      Mutable offsetCentrePos = (new Mutable()).func_189533_g(basePos);
      Set strippedLogTrunkPositions = new HashSet();
      Mutable movingPos = new Mutable();

      int z;
      int woodBelow;
      int maxWoodBelow;
      for(int y = 0; y < trunkHeight; ++y) {
         float heightF = (float)y / (float)trunkHeight;
         int width = trunkRadiusMax - (int)(heightF * (float)(trunkRadiusMax - trunkRadiusMin));

         for(int x = -width; x <= width; ++x) {
            for(z = -width; z <= width; ++z) {
               movingPos.func_189533_g(offsetCentrePos).func_196234_d(x, y, z);
               if (x * x + z * z < width * width) {
                  if (this.placeStrippedLog(world, rand, movingPos, trunk, bb, config, Axis.Y)) {
                     strippedLogTrunkPositions.add(movingPos.func_185334_h());
                  }

                  if (y == 0) {
                     LOTRTrunkPlacers.setGrassToDirt(world, movingPos.func_177977_b());
                     Mutable woodBelowPos = (new Mutable()).func_189533_g(movingPos.func_177977_b());
                     woodBelow = 0;
                     maxWoodBelow = 6 + rand.nextInt(5);

                     while(woodBelowPos.func_177956_o() >= 0 && this.placeStrippedLog(world, rand, woodBelowPos, trunk, bb, config, Axis.Y)) {
                        strippedLogTrunkPositions.add(woodBelowPos.func_185334_h());
                        LOTRTrunkPlacers.setGrassToDirt(world, woodBelowPos.func_177977_b());
                        woodBelowPos.func_189536_c(Direction.DOWN);
                        ++woodBelow;
                        if (woodBelow > maxWoodBelow) {
                           break;
                        }
                     }
                  }
               }
            }
         }

         if (y % xSlope == 0) {
            if (xSlope > 0) {
               offsetCentrePos.func_189536_c(Direction.EAST);
            } else if (xSlope < 0) {
               offsetCentrePos.func_189536_c(Direction.WEST);
            }
         }

         if (y % zSlope == 0) {
            if (zSlope > 0) {
               offsetCentrePos.func_189536_c(Direction.SOUTH);
            } else if (zSlope < 0) {
               offsetCentrePos.func_189536_c(Direction.NORTH);
            }
         }
      }

      Predicate notWood = (state) -> {
         return !state.func_235714_a_(BlockTags.field_200031_h);
      };
      Iterator var42 = strippedLogTrunkPositions.iterator();

      while(true) {
         int boughLength;
         while(var42.hasNext()) {
            BlockPos strippedPos = (BlockPos)var42.next();
            Direction[] var46 = Direction.values();
            z = var46.length;

            for(boughLength = 0; boughLength < z; ++boughLength) {
               Direction checkDir = var46[boughLength];
               if (world.func_217375_a(strippedPos.func_177972_a(checkDir), notWood)) {
                  world.func_180501_a(strippedPos, ((BlockStateProvider)this.woodProvider.get()).func_225574_a_(rand, strippedPos), 19);
                  break;
               }
            }
         }

         int angle = 0;

         while(angle < 360) {
            angle += 10 + rand.nextInt(20);
            float angleR = (float)Math.toRadians((double)angle);
            float sin = MathHelper.func_76126_a(angleR);
            float cos = MathHelper.func_76134_b(angleR);
            boughLength = 12 + rand.nextInt(10);
            woodBelow = Math.round((float)boughLength / 25.0F * 1.5F);
            maxWoodBelow = MathHelper.func_76141_d((float)trunkHeight * (0.9F + rand.nextFloat() * 0.1F));
            int boughHeight = 3 + rand.nextInt(4);

            for(int l = 0; l < boughLength; ++l) {
               int x = Math.round(cos * (float)l);
               int z = Math.round(sin * (float)l);
               int y = maxWoodBelow + Math.round((float)l / (float)boughLength * (float)boughHeight);
               int range = woodBelow - Math.round((float)l / (float)boughLength * (float)woodBelow * 0.5F);

               int x1;
               for(x1 = -range; x1 <= range; ++x1) {
                  for(int y1 = -range; y1 <= range; ++y1) {
                     for(int z1 = -range; z1 <= range; ++z1) {
                        movingPos.func_189533_g(offsetCentrePos).func_196234_d(x + x1, y + y1, z + z1);
                        this.placeWood(world, rand, movingPos, trunk, bb, config, Axis.Y);
                     }
                  }
               }

               x1 = angle + rand.nextInt(360);
               float branch_angleR = (float)Math.toRadians((double)x1);
               float branch_sin = MathHelper.func_76126_a(branch_angleR);
               float branch_cos = MathHelper.func_76134_b(branch_angleR);
               int branchLength = 7 + rand.nextInt(6);
               int branchHeight = rand.nextInt(6);

               for(int l1 = 0; l1 < branchLength; ++l1) {
                  int x1 = x + Math.round(branch_cos * (float)l1);
                  int z1 = z + Math.round(branch_sin * (float)l1);
                  int y1 = y + Math.round((float)l1 / (float)branchLength * (float)branchHeight);

                  for(int y2 = 0; y2 >= -1; --y2) {
                     movingPos.func_189533_g(offsetCentrePos).func_196234_d(x1, y1 + y2, z1);
                     this.placeWood(world, rand, movingPos, trunk, bb, config, Axis.Y);
                  }

                  if (l1 == branchLength - 1) {
                     BlockPos foliagePos = offsetCentrePos.func_185334_h().func_177982_a(x1, y1, z1);
                     foliage.add(new Foliage(foliagePos, 0, false));
                  }
               }
            }
         }

         return foliage;
      }
   }
}
