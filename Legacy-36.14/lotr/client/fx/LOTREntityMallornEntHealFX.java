package lotr.client.fx;

import net.minecraft.block.Block;
import net.minecraft.client.particle.EntityDiggingFX;
import net.minecraft.world.World;

public class LOTREntityMallornEntHealFX extends EntityDiggingFX {
   public LOTREntityMallornEntHealFX(World world, double d, double d1, double d2, double d3, double d4, double d5, Block block, int meta, int color) {
      super(world, d, d1, d2, d3, d4, d5, block, meta);
      this.field_70552_h *= (float)(color >> 16 & 255) / 255.0F;
      this.field_70553_i *= (float)(color >> 8 & 255) / 255.0F;
      this.field_70551_j *= (float)(color & 255) / 255.0F;
      this.field_70544_f *= 2.0F;
      this.field_70547_e = 30;
      this.field_70159_w = d3;
      this.field_70181_x = d4;
      this.field_70179_y = d5;
      this.field_70545_g = 0.0F;
      this.field_70155_l = 10.0D;
      this.field_70145_X = true;
   }
}
