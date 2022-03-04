package lotr.client.fx;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTREntityDeadMarshFace extends EntityFX {
   public float faceAlpha;

   public LOTREntityDeadMarshFace(World world, double d, double d1, double d2) {
      super(world, d, d1, d2, 0.0D, 0.0D, 0.0D);
      this.field_70547_e = 120 + this.field_70146_Z.nextInt(120);
      this.field_70177_z = world.field_73012_v.nextFloat() * 360.0F;
      this.field_70125_A = -60.0F + world.field_73012_v.nextFloat() * 120.0F;
   }

   public void func_70071_h_() {
      this.field_70169_q = this.field_70165_t;
      this.field_70167_r = this.field_70163_u;
      this.field_70166_s = this.field_70161_v;
      ++this.field_70546_d;
      this.faceAlpha = MathHelper.func_76126_a((float)this.field_70546_d / (float)this.field_70547_e * 3.1415927F);
      if (this.field_70546_d > this.field_70547_e) {
         this.func_70106_y();
      }

   }
}
