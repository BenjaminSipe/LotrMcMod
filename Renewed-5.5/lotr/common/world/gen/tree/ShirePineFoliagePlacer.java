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

public class ShirePineFoliagePlacer extends FoliagePlacer {
   public static final Codec CODEC = RecordCodecBuilder.create((instance) -> {
      return func_242830_b(instance).and(FeatureSpread.func_242254_a(0, 16, 8).fieldOf("foliage_height").forGetter((foliage) -> {
         return foliage.heightSpread;
      })).apply(instance, ShirePineFoliagePlacer::new);
   });
   private final FeatureSpread heightSpread;

   public ShirePineFoliagePlacer(FeatureSpread radius, FeatureSpread offset, FeatureSpread heightSpread) {
      super(radius, offset);
      this.heightSpread = heightSpread;
   }

   protected FoliagePlacerType func_230371_a_() {
      return LOTRFoliagePlacers.SHIRE_PINE_FOLIAGE;
   }

   protected void func_230372_a_(IWorldGenerationReader world, Random rand, BaseTreeFeatureConfig config, int p_230372_4_, Foliage foliage, int foliageHeight, int foliageMaxWidth, Set leaves, int foliageOffset, MutableBoundingBox bb) {
      int layerWidth = rand.nextInt(2);
      int nextMaxLayerWidth = 1;
      int nextStartingLayerWidth = 0;

      for(int y = foliageOffset; y >= foliageOffset - foliageHeight; --y) {
         this.func_236753_a_(world, rand, config, foliage.func_236763_a_(), layerWidth, leaves, y, foliage.func_236765_c_(), bb);
         if (layerWidth >= nextMaxLayerWidth) {
            layerWidth = nextStartingLayerWidth;
            nextStartingLayerWidth = 1;
            nextMaxLayerWidth = Math.min(nextMaxLayerWidth + 1, foliageMaxWidth);
         } else {
            ++layerWidth;
         }
      }

   }

   public int func_230374_a_(Random rand, int trunkHeight, BaseTreeFeatureConfig config) {
      return this.heightSpread.func_242259_a(rand);
   }

   public int func_230376_a_(Random rand, int trunkHeight) {
      return super.func_230376_a_(rand, trunkHeight);
   }

   protected boolean func_230373_a_(Random rand, int absX, int layerY, int absZ, int layerWidth, boolean bool6) {
      return absX == layerWidth && absZ == layerWidth && layerWidth > 0;
   }
}
