package lotr.common.inventory;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.LOTRMod;
import lotr.common.item.LOTRItemMug;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class LOTRSlotBarrelResult extends Slot {
   public LOTRSlotBarrelResult(IInventory inv, int i, int j, int k) {
      super(inv, i, j, k);
   }

   public boolean func_75214_a(ItemStack itemstack) {
      return false;
   }

   public boolean func_82869_a(EntityPlayer entityplayer) {
      return false;
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_75212_b() {
      IIcon var1;
      if (this.getSlotIndex() > 5) {
         LOTRItemMug var10000 = (LOTRItemMug)LOTRMod.mugAle;
         var1 = LOTRItemMug.barrelGui_emptyMugSlotIcon;
      } else {
         var1 = null;
      }

      return var1;
   }
}
