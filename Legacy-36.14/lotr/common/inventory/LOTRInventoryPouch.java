package lotr.common.inventory;

import lotr.common.item.LOTRItemPouch;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class LOTRInventoryPouch extends InventoryBasic {
   private LOTRContainerPouch theContainer;
   private EntityPlayer thePlayer;
   private int playerSlot;
   private boolean isTemporary;
   private ItemStack tempPouchItem;

   public LOTRInventoryPouch(EntityPlayer entityplayer, LOTRContainerPouch container, int slot) {
      super(entityplayer.field_71071_by.func_70301_a(slot).func_82833_r(), true, LOTRItemPouch.getCapacity(entityplayer.field_71071_by.func_70301_a(slot)));
      this.isTemporary = false;
      this.thePlayer = entityplayer;
      this.theContainer = container;
      this.playerSlot = slot;
      if (!this.thePlayer.field_70170_p.field_72995_K) {
         this.loadPouchContents();
      }

   }

   public LOTRInventoryPouch(ItemStack itemstack) {
      super("tempPouch", true, LOTRItemPouch.getCapacity(itemstack));
      this.isTemporary = true;
      this.tempPouchItem = itemstack;
      this.loadPouchContents();
   }

   public ItemStack getPouchItem() {
      return this.isTemporary ? this.tempPouchItem : this.thePlayer.field_71071_by.func_70301_a(this.playerSlot);
   }

   public String func_145825_b() {
      return this.getPouchItem().func_82833_r();
   }

   public void func_70296_d() {
      super.func_70296_d();
      if (this.isTemporary || !this.thePlayer.field_70170_p.field_72995_K) {
         this.savePouchContents();
      }

   }

   private void loadPouchContents() {
      if (this.getPouchItem().func_77942_o() && this.getPouchItem().func_77978_p().func_74764_b("LOTRPouchData")) {
         NBTTagCompound nbt = this.getPouchItem().func_77978_p().func_74775_l("LOTRPouchData");
         NBTTagList items = nbt.func_150295_c("Items", 10);

         for(int i = 0; i < items.func_74745_c(); ++i) {
            NBTTagCompound itemData = items.func_150305_b(i);
            int slot = itemData.func_74771_c("Slot");
            if (slot >= 0 && slot < this.func_70302_i_()) {
               this.func_70299_a(slot, ItemStack.func_77949_a(itemData));
            }
         }
      }

      if (!this.isTemporary) {
         this.theContainer.syncPouchItem(this.getPouchItem());
      }

   }

   private void savePouchContents() {
      if (!this.getPouchItem().func_77942_o()) {
         this.getPouchItem().func_77982_d(new NBTTagCompound());
      }

      NBTTagCompound nbt = new NBTTagCompound();
      NBTTagList items = new NBTTagList();

      for(int i = 0; i < this.func_70302_i_(); ++i) {
         ItemStack itemstack = this.func_70301_a(i);
         if (itemstack != null) {
            NBTTagCompound itemData = new NBTTagCompound();
            itemData.func_74774_a("Slot", (byte)i);
            itemstack.func_77955_b(itemData);
            items.func_74742_a(itemData);
         }
      }

      nbt.func_74782_a("Items", items);
      this.getPouchItem().func_77978_p().func_74782_a("LOTRPouchData", nbt);
      if (!this.isTemporary) {
         this.theContainer.syncPouchItem(this.getPouchItem());
      }

   }
}
