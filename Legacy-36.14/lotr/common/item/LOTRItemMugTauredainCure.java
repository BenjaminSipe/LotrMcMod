package lotr.common.item;

import lotr.common.LOTRReflection;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.world.World;

public class LOTRItemMugTauredainCure extends LOTRItemMug {
   public LOTRItemMugTauredainCure() {
      super(true, false);
   }

   public ItemStack func_77654_b(ItemStack itemstack, World world, EntityPlayer entityplayer) {
      ItemStack result = super.func_77654_b(itemstack, world, entityplayer);
      if (!world.field_72995_K) {
         for(int i = 0; i < Potion.field_76425_a.length; ++i) {
            Potion potion = Potion.field_76425_a[i];
            if (potion != null && LOTRReflection.isBadEffect(potion)) {
               entityplayer.func_82170_o(potion.field_76415_H);
            }
         }
      }

      return result;
   }
}
