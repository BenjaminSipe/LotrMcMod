package lotr.common.block;

import java.util.Random;
import java.util.function.Supplier;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowerPotBlock;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class LOTRPottedPlantBlock extends FlowerPotBlock {
   public LOTRPottedPlantBlock(Supplier plant) {
      super(() -> {
         return (FlowerPotBlock)Blocks.field_150457_bL;
      }, plant, Properties.func_200945_a(Material.field_151594_q).func_200943_b(0.0F).func_226896_b_());
      registerPottedPlant((Block)plant.get(), this);
   }

   private static void registerPottedPlant(Block plant, Block potted) {
      FlowerPotBlock pot = (FlowerPotBlock)Blocks.field_150457_bL;
      pot.addPlant(plant.getRegistryName(), () -> {
         return potted;
      });
   }

   @OnlyIn(Dist.CLIENT)
   public void func_180655_c(BlockState state, World world, BlockPos pos, Random rand) {
      this.func_220276_d().func_180655_c(this.func_220276_d().func_176223_P(), world, pos, rand);
   }
}
