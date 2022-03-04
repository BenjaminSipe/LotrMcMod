package lotr.common.entity.animal;

import java.util.UUID;
import lotr.common.LOTRMod;
import lotr.common.entity.LOTRRandomSkinEntity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Items;
import net.minecraft.world.World;

public class LOTREntityWhiteOryx extends LOTREntityGemsbok implements LOTRRandomSkinEntity {
   public static final float ORYX_SCALE = 0.9F;

   public LOTREntityWhiteOryx(World world) {
      super(world);
      this.func_70105_a(this.field_70130_N * 0.9F, this.field_70131_O * 0.9F);
   }

   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(16.0D);
   }

   public void setUniqueID(UUID uuid) {
      this.field_96093_i = uuid;
   }

   public EntityAgeable func_90011_a(EntityAgeable entity) {
      return new LOTREntityWhiteOryx(this.field_70170_p);
   }

   protected void func_70628_a(boolean flag, int i) {
      int hide = this.field_70146_Z.nextInt(3) + this.field_70146_Z.nextInt(1 + i);

      int meat;
      for(meat = 0; meat < hide; ++meat) {
         this.func_145779_a(Items.field_151116_aA, 1);
      }

      meat = this.field_70146_Z.nextInt(3) + this.field_70146_Z.nextInt(1 + i);

      for(int l = 0; l < meat; ++l) {
         if (this.func_70027_ad()) {
            this.func_145779_a(LOTRMod.deerCooked, 1);
         } else {
            this.func_145779_a(LOTRMod.deerRaw, 1);
         }
      }

   }

   protected float getGemsbokSoundPitch() {
      return 0.9F;
   }
}
