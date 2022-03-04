package lotr.common.block;

import java.util.Random;
import java.util.function.Supplier;
import net.minecraft.block.BlockState;
import net.minecraft.block.DoublePlantBlock;
import net.minecraft.block.IGrowable;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class DoubleableLOTRGrassBlock extends LOTRGrassBlock implements IGrowable {
   private final Supplier growableDoubleGrass;

   public DoubleableLOTRGrassBlock(Properties properties, Supplier doubleGrass) {
      super(properties);
      this.growableDoubleGrass = castDoublePlantSupplier(doubleGrass);
   }

   public DoubleableLOTRGrassBlock(Supplier doubleGrass) {
      this.growableDoubleGrass = castDoublePlantSupplier(doubleGrass);
   }

   private static Supplier castDoublePlantSupplier(Supplier doubleGrass) {
      return () -> {
         return (DoublePlantBlock)doubleGrass.get();
      };
   }

   public boolean func_176473_a(IBlockReader world, BlockPos pos, BlockState state, boolean isClient) {
      return true;
   }

   public boolean func_180670_a(World world, Random rand, BlockPos pos, BlockState state) {
      return true;
   }

   public void func_225535_a_(ServerWorld world, Random rand, BlockPos pos, BlockState state) {
      DoublePlantBlock doubleGrassBlock = (DoublePlantBlock)this.growableDoubleGrass.get();
      if (doubleGrassBlock.func_176223_P().func_196955_c(world, pos) && world.func_175623_d(pos.func_177984_a())) {
         doubleGrassBlock.func_196390_a(world, pos, 2);
      }

   }
}
