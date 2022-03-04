package lotr.common.block;

import java.util.Random;
import lotr.common.init.LOTRBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.OreBlock;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.Direction;
import net.minecraft.util.Direction.Axis;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolType;

public class LOTROreBlock extends OreBlock {
   private int oreHarvestLvl;

   public LOTROreBlock(Properties properties) {
      super(properties.func_235861_h_());
   }

   public LOTROreBlock setOreLevel(int harvestLvl) {
      this.oreHarvestLvl = harvestLvl;
      return this;
   }

   public ToolType getHarvestTool(BlockState state) {
      return ToolType.PICKAXE;
   }

   public int getHarvestLevel(BlockState state) {
      return this.oreHarvestLvl;
   }

   protected int func_220281_a(Random rand) {
      if (this != LOTRBlocks.SULFUR_ORE.get() && this != LOTRBlocks.NITER_ORE.get()) {
         if (this == LOTRBlocks.DURNOR_ORE.get()) {
            return MathHelper.func_76136_a(rand, 0, 2);
         } else if (this == LOTRBlocks.GLOWSTONE_ORE.get()) {
            return MathHelper.func_76136_a(rand, 2, 4);
         } else if (this == LOTRBlocks.EDHELVIR_ORE.get()) {
            return MathHelper.func_76136_a(rand, 2, 5);
         } else {
            return this != LOTRBlocks.GULDURIL_ORE_MORDOR.get() && this != LOTRBlocks.GULDURIL_ORE_STONE.get() ? super.func_220281_a(rand) : MathHelper.func_76136_a(rand, 2, 5);
         }
      } else {
         return MathHelper.func_76136_a(rand, 0, 2);
      }
   }

   @OnlyIn(Dist.CLIENT)
   public void func_180655_c(BlockState state, World world, BlockPos pos, Random rand) {
      if (this == LOTRBlocks.DURNOR_ORE.get() && rand.nextFloat() < 0.33F) {
         doDurnorParticles(world, pos, rand);
      }

   }

   public static void doDurnorParticles(World world, BlockPos pos, Random rand) {
      Direction faceDir = Direction.func_239631_a_(rand);
      BlockPos offsetPos = pos.func_177972_a(faceDir);
      if (!world.func_180495_p(offsetPos).func_200015_d(world, offsetPos)) {
         double sideOffset = 0.55D;
         Vector3d vec = Vector3d.func_237489_a_(pos).func_72441_c((double)faceDir.func_82601_c() * sideOffset, (double)faceDir.func_96559_d() * sideOffset, (double)faceDir.func_82599_e() * sideOffset);
         float f1 = MathHelper.func_151240_a(rand, -0.5F, 0.5F);
         float f2 = MathHelper.func_151240_a(rand, -0.5F, 0.5F);
         if (faceDir.func_176740_k() == Axis.X) {
            vec = vec.func_72441_c(0.0D, (double)f1, (double)f2);
         } else if (faceDir.func_176740_k() == Axis.Y) {
            vec = vec.func_72441_c((double)f1, 0.0D, (double)f2);
         } else if (faceDir.func_176740_k() == Axis.Z) {
            vec = vec.func_72441_c((double)f1, (double)f2, 0.0D);
         }

         world.func_195594_a(ParticleTypes.field_197631_x, vec.field_72450_a, vec.field_72448_b, vec.field_72449_c, 0.0D, 0.0D, 0.0D);
      }

   }
}
