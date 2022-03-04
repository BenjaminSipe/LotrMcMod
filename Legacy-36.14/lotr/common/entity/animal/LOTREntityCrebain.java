package lotr.common.entity.animal;

import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.world.World;

public class LOTREntityCrebain extends LOTREntityBird {
   public static float CREBAIN_SCALE = 1.8F;

   public LOTREntityCrebain(World world) {
      super(world);
      this.func_70105_a(this.field_70130_N * CREBAIN_SCALE, this.field_70131_O * CREBAIN_SCALE);
   }

   public String getBirdTextureDir() {
      return "crebain";
   }

   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(10.0D);
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      this.setBirdType(LOTREntityBird.BirdType.CROW);
      return data;
   }

   protected float func_70647_i() {
      return super.func_70647_i() * 0.85F;
   }
}
