package lotr.common.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap.Type;
import net.minecraft.world.gen.feature.BlockClusterFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

public class UnderwaterSpongeFeature extends Feature {
   public UnderwaterSpongeFeature(Codec configFactory) {
      super(configFactory);
   }

   public boolean generate(ISeedReader world, ChunkGenerator generator, Random rand, BlockPos pos, BlockClusterFeatureConfig config) {
      BlockState placeState = config.field_227289_a_.func_225574_a_(rand, pos);
      BlockPos placePos;
      if (config.field_227298_k_) {
         placePos = world.func_205770_a(Type.WORLD_SURFACE_WG, pos);
      } else {
         placePos = pos;
      }

      int placed = 0;
      Mutable movingPos = new Mutable();

      for(int i = 0; i < config.field_227293_f_; ++i) {
         movingPos.func_189533_g(placePos).func_196234_d(rand.nextInt(config.field_227294_g_ + 1) - rand.nextInt(config.field_227294_g_ + 1), rand.nextInt(config.field_227295_h_ + 1) - rand.nextInt(config.field_227295_h_ + 1), rand.nextInt(config.field_227296_i_ + 1) - rand.nextInt(config.field_227296_i_ + 1));
         BlockPos belowPos = movingPos.func_177977_b();
         BlockPos abovePos = movingPos.func_177984_a();
         BlockState belowState = world.func_180495_p(belowPos);
         BlockState aboveState = world.func_180495_p(abovePos);
         if (world.func_180495_p(movingPos).func_185904_a() == Material.field_151586_h && aboveState.func_185904_a() == Material.field_151586_h && placeState.func_196955_c(world, movingPos) && (config.field_227291_c_.isEmpty() || config.field_227291_c_.contains(belowState.func_177230_c())) && !config.field_227292_d_.contains(belowState)) {
            config.field_227290_b_.func_225567_a_(world, movingPos, placeState, rand);
            ++placed;
         }
      }

      return placed > 0;
   }
}
