package lotr.common.inventory;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.List;
import lotr.common.entity.animal.LOTREntityHorse;
import net.minecraft.inventory.ContainerHorseInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class LOTRContainerMountInventory extends ContainerHorseInventory {
   public LOTRContainerMountInventory(IInventory playerInv, IInventory horseInv, final LOTREntityHorse horse) {
      super(playerInv, horseInv, horse);
      List slots = new ArrayList(this.field_75151_b);
      this.field_75151_b.clear();
      this.field_75153_a.clear();
      this.func_75146_a((Slot)slots.get(0));
      Slot armorSlot = (Slot)slots.get(1);
      this.func_75146_a(new Slot(armorSlot.field_75224_c, armorSlot.field_75222_d, armorSlot.field_75223_e, armorSlot.field_75221_f) {
         public boolean func_75214_a(ItemStack itemstack) {
            return super.func_75214_a(itemstack) && horse.func_110259_cr() && horse.isMountArmorValid(itemstack);
         }

         @SideOnly(Side.CLIENT)
         public boolean func_111238_b() {
            return horse.func_110259_cr();
         }
      });

      for(int i = 2; i < slots.size(); ++i) {
         this.func_75146_a((Slot)slots.get(i));
      }

   }
}
