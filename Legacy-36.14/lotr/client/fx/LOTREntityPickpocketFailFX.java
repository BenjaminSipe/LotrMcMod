package lotr.client.fx;

import net.minecraft.world.World;

public class LOTREntityPickpocketFailFX extends LOTREntityPickpocketFX {
   public LOTREntityPickpocketFailFX(World world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
      super(world, x, y, z, xSpeed, ySpeed, zSpeed);
      this.func_70536_a(176 + this.field_70146_Z.nextInt(6));
      this.field_70545_g = 0.6F;
      this.bounciness = 0.5F;
   }

   protected void updatePickpocketIcon() {
   }
}
