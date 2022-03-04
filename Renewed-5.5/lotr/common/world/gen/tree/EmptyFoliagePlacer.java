package lotr.common.world.gen.tree;

import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.Set;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.FeatureSpread;
import net.minecraft.world.gen.foliageplacer.FoliagePlacer;
import net.minecraft.world.gen.foliageplacer.FoliagePlacerType;
import net.minecraft.world.gen.foliageplacer.FoliagePlacer.Foliage;

public class EmptyFoliagePlacer extends FoliagePlacer {
   public static final Codec CODEC = Codec.unit(EmptyFoliagePlacer::new);

   public EmptyFoliagePlacer() {
      super(FeatureSpread.func_242252_a(0), FeatureSpread.func_242252_a(0));
   }

   protected FoliagePlacerType func_230371_a_() {
      return LOTRFoliagePlacers.EMPTY_FOLIAGE;
   }

   protected void func_230372_a_(IWorldGenerationReader world, Random rand, BaseTreeFeatureConfig config, int par4, Foliage foliage, int foliageHeight, int foliageMaxWidth, Set leaves, int foliageOffset, MutableBoundingBox bb) {
   }

   public int func_230374_a_(Random rand, int trunkHeight, BaseTreeFeatureConfig config) {
      return 0;
   }

   public int func_230376_a_(Random rand, int trunkHeight) {
      return 0;
   }

   protected boolean func_230373_a_(Random rand, int absX, int layerY, int absZ, int layerWidth, boolean bool6) {
      return false;
   }
}
