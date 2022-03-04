package lotr.common.inventory;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class LOTRSlotArmorStand extends Slot {
   private int armorSlot;
   private Entity armorTestEntity;

   public LOTRSlotArmorStand(IInventory inv, int i, int j, int k, int a, Entity entity) {
      super(inv, i, j, k);
      this.armorSlot = a;
      this.armorTestEntity = entity;
   }

   public int func_75219_a() {
      return 1;
   }

   public boolean func_75214_a(ItemStack itemstack) {
      if (itemstack != null) {
         Item item = itemstack.func_77973_b();
         return item.isValidArmor(itemstack, this.armorSlot, this.armorTestEntity);
      } else {
         return true;
      }
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_75212_b() {
      return ItemArmor.func_94602_b(this.armorSlot);
   }
}
