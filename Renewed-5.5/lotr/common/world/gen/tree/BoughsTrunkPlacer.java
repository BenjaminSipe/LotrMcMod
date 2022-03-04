package lotr.common.world.gen.tree;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import net.minecraft.block.BlockState;
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
import net.minecraft.world.gen.feature.TreeFeature;
import net.minecraft.world.gen.foliageplacer.FoliagePlacer.Foliage;
import net.minecraft.world.gen.trunkplacer.TrunkPlacerType;

public class BoughsTrunkPlacer extends ExtendedTrunkPlacer {
   protected static final Codec CODEC = RecordCodecBuilder.create((instance) -> {
      return baseCodecWithWood(instance).apply(instance, BoughsTrunkPlacer::new);
   });

   protected BoughsTrunkPlacer(int baseHeight, int heightRandA, int heightRandB, Optional woodProvider, Optional strippedLogProvider, Optional branchProvider) {
      super(baseHeight, heightRandA, heightRandB, woodProvider, strippedLogProvider, branchProvider);
   }

   public BoughsTrunkPlacer(int baseHeight, int heightRandA, int heightRandB, BlockState wood, BlockState branch) {
      this(baseHeight, heightRandA, heightRandB, Optional.of(new SimpleBlockStateProvider(wood)), Optional.empty(), Optional.of(new SimpleBlockStateProvider(branch)));
   }

   protected TrunkPlacerType func_230381_a_() {
      return LOTRTrunkPlacers.BOUGHS_TRUNK_PLACER;
   }

   public List func_230382_a_(IWorldGenerationReader world, Random rand, int trunkHeight, BlockPos basePos, Set trunk, MutableBoundingBox bb, BaseTreeFeatureConfig config) {
      func_236909_a_(world, basePos.func_177977_b());

      int branchMinHeight;
      for(branchMinHeight = 0; branchMinHeight < trunkHeight; ++branchMinHeight) {
         func_236911_a_(world, rand, basePos.func_177981_b(branchMinHeight), trunk, bb, config);
      }

      branchMinHeight = (int)((float)trunkHeight * 0.6F);
      int branchMaxHeight = trunkHeight - 1;
      this.placeWood(world, rand, basePos.func_177981_b(branchMaxHeight), trunk, bb, config, Axis.Y);
      List foliage = new ArrayList();
      int deg = 0;

      int rootUp;
      for(int y = branchMaxHeight; y >= branchMinHeight; --y) {
         int branches = 1 + rand.nextInt(2);

         for(rootUp = 0; rootUp < branches; ++rootUp) {
            deg += 50 + rand.nextInt(70);
            float angle = (float)Math.toRadians((double)deg);
            float cos = MathHelper.func_76134_b(angle);
            float sin = MathHelper.func_76126_a(angle);
            float angleY = rand.nextFloat() * (float)Math.toRadians(40.0D);
            float cosY = MathHelper.func_76134_b(angleY);
            float sinY = MathHelper.func_76126_a(angleY);
            int length = 4 + rand.nextInt(6);
            Mutable branchPos = (new Mutable()).func_239621_a_(basePos, 0, y, 0);
            Axis branchAxis = Direction.func_176733_a((double)(deg + 90)).func_176740_k();

            for(int l = 0; l < length; ++l) {
               if (Math.floor((double)(cos * (float)l)) != Math.floor((double)(cos * (float)(l - 1)))) {
                  branchPos.func_196234_d((int)Math.signum(cos), 0, 0);
               }

               if (Math.floor((double)(sin * (float)l)) != Math.floor((double)(sin * (float)(l - 1)))) {
                  branchPos.func_196234_d(0, 0, (int)Math.signum(sin));
               }

               if (Math.floor((double)(sinY * (float)l)) != Math.floor((double)(sinY * (float)(l - 1)))) {
                  branchPos.func_196234_d(0, (int)Math.signum(sinY), 0);
               }

               if (branchPos.func_177958_n() != basePos.func_177958_n() || branchPos.func_177952_p() != basePos.func_177952_p() || branchPos.func_177956_o() > basePos.func_177956_o() + branchMaxHeight) {
                  if (!TreeFeature.func_236404_a_(world, branchPos)) {
                     break;
                  }

                  this.placeWood(world, rand, branchPos, trunk, bb, config, branchAxis);
               }
            }

            foliage.add(new Foliage(branchPos.func_177981_b(2), rand.nextInt(2), false));
         }
      }

      Iterator var25 = Plane.HORIZONTAL.iterator();

      while(var25.hasNext()) {
         Direction dir = (Direction)var25.next();
         rootUp = rand.nextInt(3);
         int rootLength = 3 + rootUp + rand.nextInt(3);
         int maxOut = 1;
         if (rootUp >= 2 && rand.nextBoolean()) {
            ++maxOut;
         }

         Mutable rootPos = (new Mutable()).func_239621_a_(basePos, dir.func_82601_c(), rootUp, dir.func_82599_e());
         this.growRootsDownAndThenOut(world, rand, rootPos, rootLength, dir, maxOut, trunk, bb, config);
      }

      return foliage;
   }
}
