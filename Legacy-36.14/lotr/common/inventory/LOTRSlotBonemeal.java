package lotr.common.inventory;

import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTRSlotBonemeal extends Slot {
   private World worldObj;

   public LOTRSlotBonemeal(IInventory inv, int i, int j, int k, World world) {
      super(inv, i, j, k);
      this.worldObj = world;
   }

   public boolean func_75214_a(ItemStack itemstack) {
      return itemstack.func_77973_b() == Items.field_151100_aR && itemstack.func_77960_j() == 15;
   }
}
