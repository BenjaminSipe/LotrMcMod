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

public class LOTRPineFoliagePlacer extends FoliagePlacer {
   public static final Codec CODEC = RecordCodecBuilder.create((instance) -> {
      return func_242830_b(instance).and(FeatureSpread.func_242254_a(0, 16, 8).fieldOf("foliage_height").forGetter((foliage) -> {
         return foliage.heightSpread;
      })).apply(instance, LOTRPineFoliagePlacer::new);
   });
   private final FeatureSpread heightSpread;

   public LOTRPineFoliagePlacer(FeatureSpread radius, FeatureSpread offset, FeatureSpread heightSpread) {
      super(radius, offset);
      this.heightSpread = heightSpread;
   }

   protected FoliagePlacerType func_230371_a_() {
      return LOTRFoliagePlacers.PINE_FOLIAGE;
   }

   protected void func_230372_a_(IWorldGenerationReader world, Random rand, BaseTreeFeatureConfig config, int p_230372_4_, Foliage foliage, int foliageHeight, int foliageMaxWidth, Set leaves, int foliageOffset, MutableBoundingBox bb) {
      int baseFoliageWidth = foliage.func_236764_b_();
      this.func_236753_a_(world, rand, config, foliage.func_236763_a_(), baseFoliageWidth, leaves, foliageOffset, foliage.func_236765_c_(), bb);
      this.func_236753_a_(world, rand, config, foliage.func_236763_a_(), baseFoliageWidth + 1, leaves, foliageOffset - 1, foliage.func_236765_c_(), bb);
      int y = foliageOffset - 3;

      while(y > foliageOffset - foliageHeight) {
         int layerType = rand.nextInt(3);
         if (layerType == 0) {
            this.func_236753_a_(world, rand, config, foliage.func_236763_a_(), baseFoliageWidth + 1, leaves, y, foliage.func_236765_c_(), bb);
            y -= 2;
         } else if (layerType == 1) {
            --y;
            this.func_236753_a_(world, rand, config, foliage.func_236763_a_(), baseFoliageWidth + foliageMaxWidth - 2, leaves, y + 1, foliage.func_236765_c_(), bb);
            this.func_236753_a_(world, rand, config, foliage.func_236763_a_(), baseFoliageWidth + foliageMaxWidth - 1, leaves, y, foliage.func_236765_c_(), bb);
            this.func_236753_a_(world, rand, config, foliage.func_236763_a_(), baseFoliageWidth + foliageMaxWidth - 2, leaves, y - 1, foliage.func_236765_c_(), bb);
            y -= 3;
         } else if (layerType == 2) {
            --y;
            this.func_236753_a_(world, rand, config, foliage.func_236763_a_(), baseFoliageWidth + foliageMaxWidth - 1, leaves, y + 1, foliage.func_236765_c_(), bb);
            this.func_236753_a_(world, rand, config, foliage.func_236763_a_(), baseFoliageWidth + foliageMaxWidth, leaves, y, foliage.func_236765_c_(), bb);
            this.func_236753_a_(world, rand, config, foliage.func_236763_a_(), baseFoliageWidth + foliageMaxWidth - 1, leaves, y - 1, foliage.func_236765_c_(), bb);
            y -= 3;
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
      return absX + absZ > layerWidth && layerWidth > 0;
   }
}
