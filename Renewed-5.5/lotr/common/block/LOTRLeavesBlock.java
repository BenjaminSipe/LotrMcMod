package lotr.common.block;

import java.util.Random;
import java.util.function.Supplier;
import lotr.common.event.CompostingHelper;
import lotr.common.init.LOTRBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FireBlock;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.particles.IParticleData;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolType;

public class LOTRLeavesBlock extends LeavesBlock {
   private Supplier fallingParticle;
   private int fallingChance;

   public LOTRLeavesBlock(MaterialColor materialColor) {
      super(Properties.func_200949_a(Material.field_151584_j, materialColor).func_200943_b(0.2F).func_200944_c().func_200947_a(SoundType.field_185850_c).func_226896_b_().harvestTool(ToolType.HOE).func_235827_a_(LOTRBlocks::allowSpawnOnLeaves).func_235842_b_(LOTRBlocks::posPredicateFalse).func_235847_c_(LOTRBlocks::posPredicateFalse));
      ((FireBlock)Blocks.field_150480_ab).func_180686_a(this, 30, 60);
      CompostingHelper.prepareCompostable(this, 0.3F);
   }

   public LOTRLeavesBlock() {
      this(Material.field_151584_j.func_151565_r());
   }

   public LOTRLeavesBlock setFallingParticle(Supplier particle, int chance) {
      this.fallingParticle = particle;
      this.fallingChance = chance;
      return this;
   }

   @OnlyIn(Dist.CLIENT)
   public void func_180655_c(BlockState state, World world, BlockPos pos, Random rand) {
      super.func_180655_c(state, world, pos, rand);
      if (this.fallingParticle != null && rand.nextInt(this.fallingChance) == 0) {
         IParticleData particle = (IParticleData)this.fallingParticle.get();
         double x = (double)((float)pos.func_177958_n() + rand.nextFloat());
         double y = (double)pos.func_177956_o() - 0.05D;
         double z = (double)((float)pos.func_177952_p() + rand.nextFloat());
         double xSpeed = (double)MathHelper.func_151240_a(rand, -0.1F, 0.1F);
         double ySpeed = (double)MathHelper.func_151240_a(rand, -0.03F, -0.01F);
         double zSpeed = (double)MathHelper.func_151240_a(rand, -0.1F, 0.1F);
         world.func_195594_a(particle, x, y, z, xSpeed, ySpeed, zSpeed);
      }

   }
}
