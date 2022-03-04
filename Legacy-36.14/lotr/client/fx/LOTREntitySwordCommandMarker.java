package lotr.client.fx;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class LOTREntitySwordCommandMarker extends Entity {
   private int particleAge;
   private int particleMaxAge;

   public LOTREntitySwordCommandMarker(World world, double d, double d1, double d2) {
      super(world);
      this.func_70105_a(0.5F, 0.5F);
      this.field_70129_M = this.field_70131_O / 2.0F;
      this.func_70107_b(d, d1, d2);
      this.particleAge = 0;
      this.particleMaxAge = 30;
   }

   protected void func_70088_a() {
   }

   public void func_70014_b(NBTTagCompound nbt) {
   }

   public void func_70037_a(NBTTagCompound nbt) {
   }

   public void func_70071_h_() {
      super.func_70071_h_();
      this.field_70163_u -= 0.35D;
      ++this.particleAge;
      if (this.particleAge >= this.particleMaxAge) {
         this.func_70106_y();
      }

   }

   protected boolean func_70041_e_() {
      return false;
   }

   public boolean func_85032_ar() {
      return true;
   }

   public boolean func_70104_M() {
      return false;
   }
}
