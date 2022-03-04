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

public class ClusterFoliagePlacer extends FoliagePlacer {
   public static final Codec CODEC = RecordCodecBuilder.create((instance) -> {
      return func_242830_b(instance).apply(instance, ClusterFoliagePlacer::new);
   });

   public ClusterFoliagePlacer(FeatureSpread radius, FeatureSpread offset) {
      super(radius, offset);
   }

   protected FoliagePlacerType func_230371_a_() {
      return LOTRFoliagePlacers.CLUSTER_FOLIAGE;
   }

   protected void func_230372_a_(IWorldGenerationReader world, Random rand, BaseTreeFeatureConfig config, int p_230372_4_, Foliage foliage, int foliageHeight, int foliageMaxWidth, Set leaves, int foliageOffset, MutableBoundingBox bb) {
      int sphereWidth = foliageMaxWidth + foliage.func_236764_b_();
      int leafBottom = -sphereWidth;

      for(int y = sphereWidth; y >= leafBottom; --y) {
         this.func_236753_a_(world, rand, config, foliage.func_236763_a_(), sphereWidth, leaves, y, foliage.func_236765_c_(), bb);
      }

   }

   public int func_230374_a_(Random rand, int trunkHeight, BaseTreeFeatureConfig config) {
      return 0;
   }

   public int func_230376_a_(Random rand, int trunkHeight) {
      return super.func_230376_a_(rand, trunkHeight);
   }

   protected boolean func_230373_a_(Random rand, int absX, int layerY, int absZ, int layerWidth, boolean bool6) {
      int dSq = absX * absX + layerY * layerY + absZ * absZ;
      if (dSq < (layerWidth - 1) * (layerWidth - 1)) {
         return false;
      } else {
         return dSq >= layerWidth * layerWidth || rand.nextInt(3) == 0;
      }
   }
}
