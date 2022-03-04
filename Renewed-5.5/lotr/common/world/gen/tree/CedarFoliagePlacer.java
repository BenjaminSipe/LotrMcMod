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

public class CedarFoliagePlacer extends FoliagePlacer {
   public static final Codec CODEC = RecordCodecBuilder.create((instance) -> {
      return func_242830_b(instance).and(Codec.intRange(0, 16).fieldOf("foliage_height").forGetter((foliage) -> {
         return foliage.foliageHeight;
      })).apply(instance, CedarFoliagePlacer::new);
   });
   private final int foliageHeight;

   public CedarFoliagePlacer(FeatureSpread radius, FeatureSpread offset, int height) {
      super(radius, offset);
      this.foliageHeight = height;
   }

   protected FoliagePlacerType func_230371_a_() {
      return LOTRFoliagePlacers.CEDAR_FOLIAGE;
   }

   protected void func_230372_a_(IWorldGenerationReader world, Random rand, BaseTreeFeatureConfig config, int p_230372_4_, Foliage foliage, int foliageHeight, int foliageMaxWidth, Set leaves, int foliageOffset, MutableBoundingBox bb) {
      int leafBottom = foliageOffset - foliageHeight - foliage.func_236764_b_();

      for(int y = foliageOffset; y >= leafBottom; --y) {
         int layerWidth = foliageMaxWidth + foliage.func_236764_b_() - y;
         this.func_236753_a_(world, rand, config, foliage.func_236763_a_(), layerWidth, leaves, y, foliage.func_236765_c_(), bb);
      }

   }

   public int func_230374_a_(Random rand, int trunkHeight, BaseTreeFeatureConfig config) {
      return this.foliageHeight;
   }

   public int func_230376_a_(Random rand, int trunkHeight) {
      return super.func_230376_a_(rand, trunkHeight);
   }

   protected boolean func_230373_a_(Random rand, int absX, int layerY, int absZ, int layerWidth, boolean bool6) {
      int dSq = absX * absX + absZ * absZ;
      return dSq > layerWidth * layerWidth;
   }
}
