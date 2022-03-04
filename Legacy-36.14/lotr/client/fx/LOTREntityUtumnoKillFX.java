package lotr.client.fx;

import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.client.particle.EntityFlameFX;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTREntityUtumnoKillFX extends EntityFlameFX {
   private double paramMotionX;
   private double paramMotionY;
   private double paramMotionZ;

   public LOTREntityUtumnoKillFX(World world, double d, double d1, double d2, double d3, double d4, double d5) {
      super(world, d, d1, d2, d3, d4, d5);
      this.paramMotionX = this.field_70159_w = d3;
      this.paramMotionY = this.field_70181_x = d4;
      this.paramMotionZ = this.field_70179_y = d5;
      this.func_70536_a(144 + this.field_70146_Z.nextInt(3));
   }

   public void func_70071_h_() {
      super.func_70071_h_();
      this.field_70159_w = this.paramMotionX;
      this.field_70181_x = this.paramMotionY;
      this.field_70179_y = this.paramMotionZ;
      Block block = this.field_70170_p.func_147439_a(MathHelper.func_76128_c(this.field_70165_t), MathHelper.func_76128_c(this.field_70163_u), MathHelper.func_76128_c(this.field_70161_v));
      if (block == LOTRMod.utumnoReturnPortalBase) {
         this.func_70106_y();
      }

   }
}
