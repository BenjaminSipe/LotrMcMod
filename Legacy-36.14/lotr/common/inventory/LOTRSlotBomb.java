package lotr.common.inventory;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.block.LOTRBlockOrcBomb;
import lotr.common.util.LOTRCommonIcons;
import net.minecraft.block.Block;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class LOTRSlotBomb extends Slot {
   public LOTRSlotBomb(IInventory inv, int i, int j, int k) {
      super(inv, i, j, k);
   }

   public int func_75219_a() {
      return 1;
   }

   public boolean func_75214_a(ItemStack itemstack) {
      return itemstack != null && Block.func_149634_a(itemstack.func_77973_b()) instanceof LOTRBlockOrcBomb;
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_75212_b() {
      return LOTRCommonIcons.iconBomb;
   }
}
