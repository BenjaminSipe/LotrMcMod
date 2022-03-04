package lotr.common.block;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;
import lotr.common.init.LOTRBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.TorchBlock;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.material.Material;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class LOTRTorchBlock extends TorchBlock {
   protected List torchParticles;

   public LOTRTorchBlock(Properties properties) {
      super(properties, ParticleTypes.field_197631_x);
      this.setParticles(() -> {
         return ParticleTypes.field_197601_L;
      }, () -> {
         return ParticleTypes.field_197631_x;
      });
   }

   public LOTRTorchBlock(int light) {
      this(light, SoundType.field_185848_a);
   }

   public LOTRTorchBlock(int light, SoundType sound) {
      this(LOTRBlocks.constantLight(light), sound);
   }

   public LOTRTorchBlock(ToIntFunction lightLevel, SoundType sound) {
      this(Properties.func_200945_a(Material.field_151594_q).func_200942_a().func_200943_b(0.0F).func_235838_a_(lightLevel).func_200947_a(sound));
   }

   public LOTRTorchBlock setParticles(Supplier... pars) {
      this.torchParticles = Arrays.asList(pars);
      return this;
   }

   public List copyTorchParticles() {
      return new ArrayList(this.torchParticles);
   }

   @OnlyIn(Dist.CLIENT)
   public void func_180655_c(BlockState state, World world, BlockPos pos, Random rand) {
      this.animateTorch(world, pos, rand, 0.5D, 0.7D, 0.5D);
   }

   protected void animateTorch(World world, BlockPos pos, Random rand, double x, double y, double z) {
      double d0 = (double)pos.func_177958_n() + x;
      double d1 = (double)pos.func_177956_o() + y;
      double d2 = (double)pos.func_177952_p() + z;
      Iterator var16 = this.torchParticles.iterator();

      while(var16.hasNext()) {
         Supplier particle = (Supplier)var16.next();
         world.func_195594_a((IParticleData)particle.get(), d0, d1, d2, 0.0D, 0.0D, 0.0D);
      }

   }
}
