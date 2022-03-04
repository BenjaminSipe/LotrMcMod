package lotr.common.world.gen.tree;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Random;
import java.util.Set;
import net.minecraft.util.Direction;
import net.minecraft.util.Direction.Plane;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.FeatureSpread;
import net.minecraft.world.gen.feature.TreeFeature;
import net.minecraft.world.gen.foliageplacer.FoliagePlacer;
import net.minecraft.world.gen.foliageplacer.FoliagePlacerType;
import net.minecraft.world.gen.foliageplacer.FoliagePlacer.Foliage;

public class AspenFoliagePlacer extends FoliagePlacer {
   public static final Codec CODEC = RecordCodecBuilder.create((instance) -> {
      return func_242830_b(instance).and(FeatureSpread.func_242254_a(0, 16, 8).fieldOf("trunk_height").forGetter((foliage) -> {
         return foliage.trunkHeightSpread;
      })).apply(instance, AspenFoliagePlacer::new);
   });
   private final FeatureSpread trunkHeightSpread;

   public AspenFoliagePlacer(FeatureSpread radius, FeatureSpread offset, FeatureSpread trunkHeightSpread) {
      super(radius, offset);
      this.trunkHeightSpread = trunkHeightSpread;
   }

   protected FoliagePlacerType func_230371_a_() {
      return LOTRFoliagePlacers.ASPEN_FOLIAGE;
   }

   protected void func_230372_a_(IWorldGenerationReader world, Random rand, BaseTreeFeatureConfig config, int p_230372_4_, Foliage foliage, int foliageHeight, int foliageMaxWidth, Set leaves, int foliageOffset, MutableBoundingBox bb) {
      int leafTop = foliageOffset;
      int leafBottom = foliageOffset - foliageHeight;

      for(int y = foliageOffset; y >= leafBottom; --y) {
         int baseLayerWidth = foliageMaxWidth;
         if (y >= leafTop - 1) {
            baseLayerWidth = foliageMaxWidth - 2;
         } else if (y >= leafTop - 3 || y <= leafBottom + 1 || rand.nextInt(4) == 0) {
            baseLayerWidth = foliageMaxWidth - 1;
         }

         int layerWidth = baseLayerWidth + foliage.func_236764_b_();
         int branches = 4 + rand.nextInt(5);

         for(int b = 0; b < branches; ++b) {
            Mutable movingPos = (new Mutable()).func_239621_a_(foliage.func_236763_a_(), 0, y, 0);
            int origX = movingPos.func_177958_n();
            int origZ = movingPos.func_177952_p();
            int length = 4 + rand.nextInt(8);

            for(int l = 0; l < length && Math.abs(origX - movingPos.func_177958_n()) <= layerWidth && Math.abs(origZ - movingPos.func_177952_p()) <= layerWidth; ++l) {
               this.doPlaceLeafBlock(world, rand, config, movingPos, leaves, bb);
               Direction randDir = Plane.HORIZONTAL.func_179518_a(rand);
               movingPos.func_189536_c(randDir);
            }
         }
      }

   }

   private void doPlaceLeafBlock(IWorldGenerationReader world, Random rand, BaseTreeFeatureConfig config, Mutable movingPos, Set leaves, MutableBoundingBox bb) {
      if (TreeFeature.func_236404_a_(world, movingPos)) {
         world.func_180501_a(movingPos, config.field_227369_n_.func_225574_a_(rand, movingPos), 19);
         bb.func_78888_b(new MutableBoundingBox(movingPos, movingPos));
         leaves.add(movingPos.func_185334_h());
      }

   }

   public int func_230374_a_(Random rand, int trunkHeight, BaseTreeFeatureConfig config) {
      return Math.max(4, trunkHeight - this.trunkHeightSpread.func_242259_a(rand));
   }

   public int func_230376_a_(Random rand, int trunkHeight) {
      return super.func_230376_a_(rand, trunkHeight);
   }

   protected boolean func_230373_a_(Random rand, int absX, int layerY, int absZ, int layerWidth, boolean bool6) {
      return false;
   }
}
