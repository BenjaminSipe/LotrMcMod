package lotr.common.world.gen.tree;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import net.minecraft.block.BlockState;
import net.minecraft.block.AbstractBlock.AbstractBlockState;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.Direction.Axis;
import net.minecraft.util.Direction.Plane;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.foliageplacer.FoliagePlacer.Foliage;
import net.minecraft.world.gen.trunkplacer.TrunkPlacerType;

public class PartyTrunkPlacer extends ExtendedTrunkPlacer {
   protected static final Codec CODEC = RecordCodecBuilder.create((instance) -> {
      return baseCodecWithWood(instance).apply(instance, PartyTrunkPlacer::new);
   });

   protected PartyTrunkPlacer(int baseHeight, int heightRandA, int heightRandB, Optional woodProvider, Optional strippedLogProvider, Optional branchProvider) {
      super(baseHeight, heightRandA, heightRandB, woodProvider, strippedLogProvider, branchProvider);
   }

   public PartyTrunkPlacer(int baseHeight, int heightRandA, int heightRandB, BlockState wood, BlockState branch) {
      this(baseHeight, heightRandA, heightRandB, Optional.of(new SimpleBlockStateProvider(wood)), Optional.empty(), Optional.of(new SimpleBlockStateProvider(branch)));
   }

   protected TrunkPlacerType func_230381_a_() {
      return LOTRTrunkPlacers.PARTY_TRUNK_PLACER;
   }

   public List func_230382_a_(IWorldGenerationReader world, Random rand, int trunkHeight, BlockPos basePos, Set trunk, MutableBoundingBox bb, BaseTreeFeatureConfig config) {
      List foliage = new ArrayList();
      int trunkWidth = 1;
      boolean flag = true;
      float trunkPitch = (float)Math.toRadians((double)MathHelper.func_151240_a(rand, 65.0F, 90.0F));
      float trunkYaw = (float)Math.toRadians((double)(rand.nextFloat() * 360.0F));
      Mutable movingPos = new Mutable();

      int angle;
      int x;
      int rootUpY;
      Mutable rootPos;
      int woodBelow;
      int maxWoodBelow;
      for(angle = 0; angle < trunkHeight; ++angle) {
         BlockPos offsetCentrePos = this.getOffsetCentrePos(basePos, angle, trunkPitch, trunkYaw);

         for(x = -trunkWidth; x <= trunkWidth; ++x) {
            for(rootUpY = -trunkWidth; rootUpY <= trunkWidth; ++rootUpY) {
               movingPos.func_239621_a_(offsetCentrePos, x, 0, rootUpY);
               this.placeWood(world, rand, movingPos, trunk, bb, config, Axis.Y);
               if (angle == 0) {
                  LOTRTrunkPlacers.setGrassToDirt(world, movingPos.func_177977_b());
                  rootPos = (new Mutable()).func_189533_g(movingPos.func_177977_b());
                  woodBelow = 0;
                  maxWoodBelow = 6 + rand.nextInt(3);

                  while(rootPos.func_177956_o() >= 0 && this.placeWood(world, rand, rootPos, trunk, bb, config, Axis.Y)) {
                     LOTRTrunkPlacers.setGrassToDirt(world, rootPos.func_177977_b());
                     rootPos.func_189536_c(Direction.DOWN);
                     ++woodBelow;
                     if (woodBelow > maxWoodBelow) {
                        break;
                     }
                  }
               }
            }
         }
      }

      angle = 0;

      int l1;
      int woodDropped;
      while(angle < 360) {
         angle += 30 + rand.nextInt(35);
         float angleR = (float)Math.toRadians((double)angle);
         float sin = MathHelper.func_76126_a(angleR);
         float cos = MathHelper.func_76134_b(angleR);
         int boughLength = 6 + rand.nextInt(6);
         woodBelow = Math.round((float)boughLength * 0.05F);
         maxWoodBelow = MathHelper.func_76141_d(MathHelper.func_151240_a(rand, 0.65F, 0.95F) * (float)trunkHeight);
         l1 = 2 + rand.nextInt(4);
         BlockPos offsetCentrePos = this.getOffsetCentrePos(basePos, maxWoodBelow, trunkPitch, trunkYaw);

         for(woodDropped = 0; woodDropped < boughLength; ++woodDropped) {
            int x = Math.round(cos * (float)woodDropped);
            int z = Math.round(sin * (float)woodDropped);
            int y = Math.round((float)woodDropped / (float)boughLength * (float)l1);
            int range = woodBelow - Math.round((float)woodDropped / (float)boughLength * (float)woodBelow * 0.5F);

            int branch_angle;
            for(branch_angle = -range; branch_angle <= range; ++branch_angle) {
               for(int y1 = -range; y1 <= range; ++y1) {
                  for(int z1 = -range; z1 <= range; ++z1) {
                     movingPos.func_189533_g(offsetCentrePos).func_196234_d(x + branch_angle, y + y1, z + z1);
                     this.placeWood(world, rand, movingPos, trunk, bb, config, Axis.Y);
                  }
               }
            }

            branch_angle = angle + MathHelper.func_76136_a(rand, -60, 60);
            float branch_angleR = (float)Math.toRadians((double)branch_angle);
            float branch_sin = MathHelper.func_76126_a(branch_angleR);
            float branch_cos = MathHelper.func_76134_b(branch_angleR);
            int branchLength = 5 + rand.nextInt(4);
            int branchHeight = rand.nextInt(4);

            for(int l1 = 0; l1 < branchLength; ++l1) {
               int x1 = x + Math.round(branch_cos * (float)l1);
               int z1 = z + Math.round(branch_sin * (float)l1);
               int y1 = y + Math.round((float)l1 / (float)branchLength * (float)branchHeight);

               int size;
               for(size = 0; size >= 0; --size) {
                  movingPos.func_189533_g(offsetCentrePos).func_196234_d(x1, y1 + size, z1);
                  this.placeLogWithAxis(world, rand, movingPos, trunk, bb, config, Direction.func_176733_a((double)branch_angle).func_176746_e().func_176740_k());
               }

               if (l1 == branchLength - 1 || rand.nextInt(6) == 0) {
                  size = rand.nextInt(6) == 0 ? 1 : 0;
                  foliage.add(new Foliage(offsetCentrePos.func_185334_h().func_177982_a(x1, y1 + 1, z1), size, false));
               }
            }
         }
      }

      foliage.add(new Foliage(this.getOffsetCentrePos(basePos, trunkHeight, trunkPitch, trunkYaw), 1, false));
      int roots = 5 + rand.nextInt(5);

      for(x = 0; x < roots; ++x) {
         rootUpY = 0 + rand.nextInt(4);
         rootPos = (new Mutable()).func_189533_g(this.getOffsetCentrePos(basePos, rootUpY, trunkPitch, trunkYaw));
         woodBelow = 2 + rand.nextInt(4);
         Direction rootDir = Plane.HORIZONTAL.func_179518_a(rand);
         rootPos.func_189534_c(rootDir, trunkWidth + 1);
         rootPos.func_189534_c(rootDir.func_176746_e(), MathHelper.func_76136_a(rand, -trunkWidth, trunkWidth));

         for(l1 = 0; l1 < woodBelow; ++l1) {
            Mutable dropDownPos = (new Mutable()).func_189533_g(rootPos);
            woodDropped = 0;
            boolean var46 = true;

            while(dropDownPos.func_177956_o() >= 0) {
               BlockPos checkAbovePos = dropDownPos.func_177984_a();
               boolean branch = woodDropped <= 1 && !world.func_217375_a(checkAbovePos, AbstractBlockState::func_200132_m);
               boolean placedBlock = branch ? this.placeBranch(world, rand, dropDownPos, trunk, bb, config) : this.placeWood(world, rand, dropDownPos, trunk, bb, config, Axis.Y);
               if (!placedBlock) {
                  break;
               }

               if (!branch) {
                  LOTRTrunkPlacers.setGrassToDirt(world, dropDownPos.func_177977_b());
               }

               dropDownPos.func_189536_c(Direction.DOWN);
               ++woodDropped;
               if (woodDropped > 5) {
                  break;
               }
            }

            rootPos.func_189536_c(Direction.DOWN);
            if (rand.nextBoolean()) {
               rootPos.func_189536_c(rootDir);
            }
         }
      }

      return foliage;
   }

   private BlockPos getOffsetCentrePos(BlockPos basePos, int trunkHeightHere, float trunkPitch, float trunkYaw) {
      float upFrac = MathHelper.func_76126_a(trunkPitch) * (float)trunkHeightHere;
      float outFrac = MathHelper.func_76134_b(trunkPitch) * (float)trunkHeightHere;
      int offX = Math.round(outFrac * MathHelper.func_76134_b(trunkYaw));
      int offZ = Math.round(outFrac * MathHelper.func_76126_a(trunkYaw));
      return basePos.func_177982_a(offX, Math.round(upFrac), offZ);
   }

   private boolean isSolidWood(BlockState state) {
      return state.func_200132_m() && state.func_235714_a_(BlockTags.field_200031_h);
   }
}
