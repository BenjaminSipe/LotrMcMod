package lotr.common.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;

public class LatitudeBasedFeature extends Feature {
   public LatitudeBasedFeature(Codec codec) {
      super(codec);
   }

   public boolean generate(ISeedReader world, ChunkGenerator generator, Random rand, BlockPos pos, LatitudeBasedFeatureConfig config) {
      LatitudeBasedFeatureConfig.LatitudeConfiguration latConfig = config.latitudeConfig;
      int z = pos.func_177952_p();
      double latitudeProgressF = latConfig.type.getLatitudeProgress(z);
      if (latConfig.invert) {
         latitudeProgressF = 1.0D - latitudeProgressF;
      }

      if (latitudeProgressF > (double)latConfig.max) {
         return false;
      } else if (latitudeProgressF < (double)latConfig.min) {
         return false;
      } else if ((double)rand.nextFloat() < latitudeProgressF) {
         ConfiguredFeature feature = config.feature;
         return feature.func_242765_a(world, generator, rand, pos);
      } else {
         return false;
      }
   }
}
