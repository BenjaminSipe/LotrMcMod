package lotr.client.fx;

import net.minecraft.client.particle.EntityNoteFX;
import net.minecraft.world.World;

public class LOTREntityMusicFX extends EntityNoteFX {
   private double noteMoveX;
   private double noteMoveY;
   private double noteMoveZ;

   public LOTREntityMusicFX(World world, double d, double d1, double d2, double d3, double d4, double d5, double pitch) {
      super(world, d, d1, d2, pitch, 0.0D, 0.0D);
      this.noteMoveX = d3;
      this.noteMoveY = d4;
      this.noteMoveZ = d5;
      this.field_70547_e = 8 + this.field_70146_Z.nextInt(20);
   }

   public void func_70071_h_() {
      super.func_70071_h_();
      double decel = 0.98D;
      this.noteMoveX *= decel;
      this.noteMoveY *= decel;
      this.noteMoveZ *= decel;
      this.field_70159_w = this.noteMoveX;
      this.field_70181_x = this.noteMoveY;
      this.field_70179_y = this.noteMoveZ;
   }
}
