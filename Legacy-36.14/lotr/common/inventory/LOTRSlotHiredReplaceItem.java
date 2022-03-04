package lotr.common.inventory;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.entity.npc.LOTRInventoryHiredReplacedItems;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class LOTRSlotHiredReplaceItem extends Slot {
   private LOTREntityNPC theNPC;
   private LOTRInventoryHiredReplacedItems npcInv;
   private Slot parentSlot;

   public LOTRSlotHiredReplaceItem(Slot slot, LOTREntityNPC npc) {
      super(slot.field_75224_c, slot.getSlotIndex(), slot.field_75223_e, slot.field_75221_f);
      this.parentSlot = slot;
      this.theNPC = npc;
      this.npcInv = this.theNPC.hiredReplacedInv;
      if (!this.theNPC.field_70170_p.field_72995_K) {
         int i = this.getSlotIndex();
         if (this.npcInv.hasReplacedEquipment(i)) {
            this.field_75224_c.func_70299_a(i, this.npcInv.getEquippedReplacement(i));
         }
      }

   }

   public boolean func_75214_a(ItemStack itemstack) {
      return this.parentSlot.func_75214_a(itemstack) && this.theNPC.canReEquipHired(this.getSlotIndex(), itemstack);
   }

   public int func_75219_a() {
      return this.parentSlot.func_75219_a();
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_75212_b() {
      return this.parentSlot.func_75212_b();
   }

   public void func_75218_e() {
      super.func_75218_e();
      if (!this.theNPC.field_70170_p.field_72995_K) {
         this.npcInv.onEquipmentChanged(this.getSlotIndex(), this.func_75211_c());
      }

   }
}
