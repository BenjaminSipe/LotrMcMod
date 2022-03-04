package lotr.common.world.gen.tree;

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

public class LairelosseFoliagePlacer extends FoliagePlacer {
   public static final Codec CODEC = RecordCodecBuilder.create((instance) -> {
      return func_242830_b(instance).and(FeatureSpread.func_242254_a(0, 16, 8).fieldOf("trunk_height").forGetter((foliage) -> {
         return foliage.trunkHeightSpread;
      })).apply(instance, LairelosseFoliagePlacer::new);
   });
   private final FeatureSpread trunkHeightSpread;

   public LairelosseFoliagePlacer(FeatureSpread radius, FeatureSpread offset, FeatureSpread trunkHeightSpread) {
      super(radius, offset);
      this.trunkHeightSpread = trunkHeightSpread;
   }

   protected FoliagePlacerType func_230371_a_() {
      return LOTRFoliagePlacers.LAIRELOSSE_FOLIAGE;
   }

   protected void func_230372_a_(IWorldGenerationReader world, Random rand, BaseTreeFeatureConfig config, int p_230372_4_, Foliage foliage, int foliageHeight, int foliageMaxWidth, Set leaves, int foliageOffset, MutableBoundingBox bb) {
      int layerWidth = 0;

      for(int y = foliageOffset; y >= foliageOffset - foliageHeight; --y) {
         if (y >= foliageOffset - 1) {
            layerWidth = 0;
         } else {
            ++layerWidth;
            if (layerWidth > foliageMaxWidth) {
               layerWidth = 1;
            }
         }

         this.func_236753_a_(world, rand, config, foliage.func_236763_a_(), layerWidth + foliage.func_236764_b_(), leaves, y, foliage.func_236765_c_(), bb);
      }

   }

   public int func_230374_a_(Random rand, int trunkHeight, BaseTreeFeatureConfig config) {
      return Math.max(3, trunkHeight - this.trunkHeightSpread.func_242259_a(rand));
   }

   public int func_230376_a_(Random rand, int trunkHeight) {
      return super.func_230376_a_(rand, trunkHeight);
   }

   protected boolean func_230373_a_(Random rand, int absX, int layerY, int absZ, int layerWidth, boolean bool6) {
      return absX + absZ > layerWidth;
   }
}
