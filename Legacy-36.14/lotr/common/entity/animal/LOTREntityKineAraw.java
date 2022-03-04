package lotr.common.entity.animal;

import lotr.common.LOTRMod;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.world.World;

public class LOTREntityKineAraw extends LOTREntityAurochs {
   public static float KINE_SCALE = 1.15F;

   public LOTREntityKineAraw(World world) {
      super(world);
      this.func_70105_a(this.aurochsWidth * KINE_SCALE, this.aurochsHeight * KINE_SCALE);
   }

   protected EntityAIBase createAurochsAttackAI() {
      return new LOTREntityAIAttackOnCollide(this, 1.9D, true);
   }

   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(40.0D);
      this.func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(5.0D);
   }

   protected void dropHornItem(boolean flag, int i) {
      this.func_145779_a(LOTRMod.kineArawHorn, 1);
   }

   public EntityCow func_90011_a(EntityAgeable entity) {
      return new LOTREntityKineAraw(this.field_70170_p);
   }
}
