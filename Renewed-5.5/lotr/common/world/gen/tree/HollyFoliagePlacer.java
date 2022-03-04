package lotr.common.world.gen.tree;

import com.google.common.math.IntMath;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Random;
import java.util.Set;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.FeatureSpread;
import net.minecraft.world.gen.foliageplacer.FoliagePlacer;
import net.minecraft.world.gen.foliageplacer.FoliagePlacerType;
import net.minecraft.world.gen.foliageplacer.FoliagePlacer.Foliage;

public class HollyFoliagePlacer extends FoliagePlacer {
   public static final Codec CODEC = RecordCodecBuilder.create((instance) -> {
      return func_242830_b(instance).and(FeatureSpread.func_242254_a(0, 16, 8).fieldOf("trunk_height").forGetter((foliage) -> {
         return foliage.trunkHeightSpread;
      })).apply(instance, HollyFoliagePlacer::new);
   });
   private final FeatureSpread trunkHeightSpread;

   public HollyFoliagePlacer(FeatureSpread radius, FeatureSpread offset, FeatureSpread trunkHeightSpread) {
      super(radius, offset);
      this.trunkHeightSpread = trunkHeightSpread;
   }

   protected FoliagePlacerType func_230371_a_() {
      return LOTRFoliagePlacers.HOLLY_FOLIAGE;
   }

   protected void func_230372_a_(IWorldGenerationReader world, Random rand, BaseTreeFeatureConfig config, int p_230372_4_, Foliage foliage, int foliageHeight, int foliageMaxWidth, Set leaves, int foliageOffset, MutableBoundingBox bb) {
      int layerWidth = false;

      for(int y = foliageOffset; y >= foliageOffset - foliageHeight; --y) {
         int layerWidth;
         if (y == foliageOffset) {
            layerWidth = 0;
         } else if (y < foliageOffset - 2 && y != foliageOffset - foliageHeight) {
            layerWidth = foliageMaxWidth;
         } else {
            layerWidth = 1;
         }

         this.func_236753_a_(world, rand, config, foliage.func_236763_a_(), layerWidth + foliage.func_236764_b_(), leaves, y, foliage.func_236765_c_(), bb);
      }

   }

   public int func_230374_a_(Random rand, int trunkHeight, BaseTreeFeatureConfig config) {
      return Math.max(4, trunkHeight - this.trunkHeightSpread.func_242259_a(rand));
   }

   public int func_230376_a_(Random rand, int trunkHeight) {
      return super.func_230376_a_(rand, trunkHeight);
   }

   protected boolean func_230373_a_(Random rand, int absX, int layerY, int absZ, int layerWidth, boolean bool6) {
      if (layerWidth > 0 && IntMath.mod(layerY, 2) == 1) {
         return absX == layerWidth && absZ == layerWidth;
      } else {
         return false;
      }
   }
}
