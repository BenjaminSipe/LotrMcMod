package lotr.common.item;

import lotr.common.init.LOTRMaterial;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;

public class HammerItem extends LOTRSwordItem {
   private boolean metallicSound;

   public HammerItem(IItemTier tier) {
      super(tier, 6, -3.3F);
      this.metallicSound = true;
   }

   public HammerItem(LOTRMaterial material) {
      this((IItemTier)material.asTool());
   }

   public HammerItem noClink() {
      this.metallicSound = false;
      return this;
   }

   public boolean func_77644_a(ItemStack stack, LivingEntity target, LivingEntity attacker) {
      if (super.func_77644_a(stack, target, attacker)) {
         int extraKb = 1;
         target.func_233627_a_((float)extraKb * 0.5F, (double)MathHelper.func_76126_a(attacker.field_70177_z * 0.017453292F), (double)(-MathHelper.func_76134_b(attacker.field_70177_z * 0.017453292F)));
         if (this.metallicSound) {
            attacker.field_70170_p.func_184148_a((PlayerEntity)null, attacker.func_226277_ct_(), attacker.func_226278_cu_(), attacker.func_226281_cx_(), SoundEvents.field_187692_g, attacker.func_184176_by(), 1.0F, 0.75F);
         }

         return true;
      } else {
         return false;
      }
   }
}
