package lotr.common.world.gen.tree;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import net.minecraft.util.Direction;
import net.minecraft.util.Direction.Plane;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.FeatureSpread;
import net.minecraft.world.gen.foliageplacer.FoliagePlacer;
import net.minecraft.world.gen.foliageplacer.FoliagePlacerType;
import net.minecraft.world.gen.foliageplacer.FoliagePlacer.Foliage;

public class CypressFoliagePlacer extends FoliagePlacer {
   public static final Codec CODEC = RecordCodecBuilder.create((instance) -> {
      return func_242830_b(instance).and(FeatureSpread.func_242254_a(0, 16, 8).fieldOf("trunk_height").forGetter((foliage) -> {
         return foliage.trunkHeightSpread;
      })).apply(instance, CypressFoliagePlacer::new);
   });
   private final FeatureSpread trunkHeightSpread;

   public CypressFoliagePlacer(FeatureSpread radius, FeatureSpread offset, FeatureSpread trunkHeightSpread) {
      super(radius, offset);
      this.trunkHeightSpread = trunkHeightSpread;
   }

   protected FoliagePlacerType func_230371_a_() {
      return LOTRFoliagePlacers.CYPRESS_FOLIAGE;
   }

   protected void func_230372_a_(IWorldGenerationReader world, Random rand, BaseTreeFeatureConfig config, int p_230372_4_, Foliage foliage, int foliageHeight, int foliageMaxWidth, Set leaves, int foliageOffset, MutableBoundingBox bb) {
      int topCrossY = foliageOffset - 2;
      int bottomCrossesHighestY = foliageOffset - foliageHeight + rand.nextInt(3);

      for(int y = foliageOffset; y >= foliageOffset - foliageHeight; --y) {
         int layerWidth = foliage.func_236764_b_();
         if (y < topCrossY && y > bottomCrossesHighestY) {
            ++layerWidth;
         }

         this.func_236753_a_(world, rand, config, foliage.func_236763_a_(), layerWidth, leaves, y, foliage.func_236765_c_(), bb);
         if (y == topCrossY || y <= bottomCrossesHighestY) {
            Iterator var15 = Plane.HORIZONTAL.iterator();

            while(var15.hasNext()) {
               Direction dir = (Direction)var15.next();
               this.func_236753_a_(world, rand, config, foliage.func_236763_a_().func_177972_a(dir), layerWidth, leaves, y, foliage.func_236765_c_(), bb);
            }
         }
      }

   }

   public int func_230374_a_(Random rand, int trunkHeight, BaseTreeFeatureConfig config) {
      return Math.max(3, trunkHeight - this.trunkHeightSpread.func_242259_a(rand));
   }

   public int func_230376_a_(Random rand, int trunkHeight) {
      return super.func_230376_a_(rand, trunkHeight);
   }

   protected boolean func_230373_a_(Random rand, int x, int layerY, int z, int layerWidth, boolean bool6) {
      return false;
   }
}
