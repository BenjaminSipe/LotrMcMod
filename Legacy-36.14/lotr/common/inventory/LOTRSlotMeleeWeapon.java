package lotr.common.inventory;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.item.LOTRWeaponStats;
import lotr.common.util.LOTRCommonIcons;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class LOTRSlotMeleeWeapon extends Slot {
   public LOTRSlotMeleeWeapon(IInventory inv, int i, int j, int k) {
      super(inv, i, j, k);
   }

   public int func_75219_a() {
      return 1;
   }

   public boolean func_75214_a(ItemStack itemstack) {
      return LOTRWeaponStats.isMeleeWeapon(itemstack);
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_75212_b() {
      return LOTRCommonIcons.iconMeleeWeapon;
   }
}
