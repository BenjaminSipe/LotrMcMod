package lotr.common.world.gen.tree;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.foliageplacer.FoliagePlacer.Foliage;
import net.minecraft.world.gen.trunkplacer.TrunkPlacerType;

public class CedarTrunkPlacer extends ExtendedTrunkPlacer {
   protected static final Codec CODEC = RecordCodecBuilder.create((instance) -> {
      return baseCodecWithWood(instance).apply(instance, CedarTrunkPlacer::new);
   });

   protected CedarTrunkPlacer(int baseHeight, int heightRandA, int heightRandB, Optional woodProvider, Optional strippedLogProvider, Optional branchProvider) {
      super(baseHeight, heightRandA, heightRandB, woodProvider, strippedLogProvider, branchProvider);
   }

   public CedarTrunkPlacer(int baseHeight, int heightRandA, int heightRandB, BlockState branch) {
      this(baseHeight, heightRandA, heightRandB, Optional.empty(), Optional.empty(), Optional.of(new SimpleBlockStateProvider(branch)));
   }

   protected TrunkPlacerType func_230381_a_() {
      return LOTRTrunkPlacers.CEDAR_TRUNK_PLACER;
   }

   public List func_230382_a_(IWorldGenerationReader world, Random rand, int trunkHeight, BlockPos basePos, Set trunk, MutableBoundingBox bb, BaseTreeFeatureConfig config) {
      func_236909_a_(world, basePos.func_177977_b());

      for(int y = 0; y < trunkHeight; ++y) {
         func_236911_a_(world, rand, basePos.func_177981_b(y), trunk, bb, config);
      }

      List foliage = new ArrayList();
      foliage.add(new Foliage(basePos.func_177981_b(trunkHeight), 0, false));

      int x;
      int z;
      for(x = trunkHeight - 1; x > trunkHeight / 2; x -= 1 + rand.nextInt(3)) {
         z = 1 + rand.nextInt(3);

         label89:
         for(int b = 0; b < z; ++b) {
            float angle = rand.nextFloat() * 3.1415927F * 2.0F;
            int length = MathHelper.func_76136_a(rand, 4, 7);
            int leafLayerLessWidth = 1;
            Mutable branchPos = new Mutable();

            for(int l = 0; l < length; ++l) {
               int branchX = Math.round(0.5F + MathHelper.func_76134_b(angle) * (float)(l + 1));
               int branchZ = Math.round(0.5F + MathHelper.func_76126_a(angle) * (float)(l + 1));
               int branchY = x - 3 + l / 2;
               BlockPos prevBranchPos = branchPos.func_185334_h();
               branchPos.func_239621_a_(basePos, branchX, branchY, branchZ);
               if (!branchPos.equals(prevBranchPos)) {
                  if (!func_236911_a_(world, rand, branchPos, trunk, bb, config)) {
                     continue label89;
                  }

                  if (l == length - 1 && leafLayerLessWidth <= 1) {
                     Mutable woodPos = new Mutable();

                     for(int x = -1; x <= 1; ++x) {
                        for(int z = -1; z <= 1; ++z) {
                           if ((x == 0 || z == 0) && x != z) {
                              woodPos.func_239621_a_(branchPos, x, 0, z);
                              func_236911_a_(world, rand, woodPos, trunk, bb, config);
                           }
                        }
                     }
                  }
               }
            }

            foliage.add(new Foliage(branchPos.func_177984_a(), -leafLayerLessWidth, false));
         }
      }

      for(x = -1; x <= 1; ++x) {
         for(z = -1; z <= 1; ++z) {
            if (Math.abs(x) != Math.abs(z)) {
               Mutable rootPos = (new Mutable()).func_239621_a_(basePos, x, rand.nextInt(2), z);
               int rootLength = 4 + rand.nextInt(3);
               this.growRootsDown(world, rand, rootPos, rootLength, trunk, bb, config);
            }
         }
      }

      return foliage;
   }
}
