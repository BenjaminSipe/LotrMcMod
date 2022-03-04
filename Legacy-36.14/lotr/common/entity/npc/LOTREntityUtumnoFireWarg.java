package lotr.common.entity.npc;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class LOTREntityUtumnoFireWarg extends LOTREntityUtumnoWarg {
   public LOTREntityUtumnoFireWarg(World world) {
      super(world);
      this.field_70178_ae = true;
   }

   public void func_70088_a() {
      super.func_70088_a();
      this.setWargType(LOTREntityWarg.WargType.FIRE);
   }

   public void func_70636_d() {
      super.func_70636_d();
      String s = this.field_70146_Z.nextInt(3) > 0 ? "flame" : "smoke";
      this.field_70170_p.func_72869_a(s, this.field_70165_t + (this.field_70146_Z.nextDouble() - 0.5D) * (double)this.field_70130_N, this.field_70163_u + this.field_70146_Z.nextDouble() * (double)this.field_70131_O, this.field_70161_v + (this.field_70146_Z.nextDouble() - 0.5D) * (double)this.field_70130_N, 0.0D, 0.0D, 0.0D);
   }

   public boolean func_70652_k(Entity entity) {
      boolean flag = super.func_70652_k(entity);
      if (!this.field_70170_p.field_72995_K && flag) {
         entity.func_70015_d(4);
      }

      return flag;
   }
}
