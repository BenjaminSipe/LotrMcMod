package lotr.common.inv;

import lotr.common.init.LOTRContainers;
import lotr.common.item.PouchItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import org.apache.commons.lang3.StringUtils;

public class PouchContainer extends Container implements OpenPouchContainer {
   private final PlayerInventory playerInv;
   private final int playerInvSlot;
   private final ItemStack pouchItem;
   private final PouchInventory pouchInventory;

   public static void writeContainerInitData(PacketBuffer extraData, int playerInvSlot) {
      extraData.func_150787_b(playerInvSlot);
   }

   public PouchContainer(int windowID, PlayerInventory playerInv, PacketBuffer extraData) {
      super((ContainerType)LOTRContainers.POUCH.get(), windowID);
      this.playerInv = playerInv;
      World world = playerInv.field_70458_d.field_70170_p;
      this.playerInvSlot = extraData.func_150792_a();
      this.pouchItem = playerInv.func_70301_a(this.playerInvSlot);
      this.pouchInventory = PouchInventory.worldSidedInventory(this.pouchItem, world);
      if (!world.field_72995_K) {
         PouchItem.setPickedUpNewItems(this.pouchItem, false);
      }

      int rows = this.getPouchCapacity() / 9;

      int x;
      int x;
      for(x = 0; x < rows; ++x) {
         for(x = 0; x < 9; ++x) {
            this.func_75146_a(new PouchSlot(this.pouchInventory, x + x * 9, 8 + x * 18, 30 + x * 18));
         }
      }

      for(x = 0; x < 3; ++x) {
         for(x = 0; x < 9; ++x) {
            this.func_75146_a(new Slot(playerInv, x + x * 9 + 9, 8 + x * 18, 98 + x * 18));
         }
      }

      for(x = 0; x < 9; ++x) {
         this.func_75146_a(new Slot(playerInv, x, 8 + x * 18, 156));
      }

   }

   public boolean isOpenPouch(ItemStack stack) {
      return this.playerInv.func_70301_a(this.playerInvSlot) == stack;
   }

   public int getPouchCapacity() {
      return this.pouchInventory.func_70302_i_();
   }

   public PouchInventory getPouchInventory() {
      return this.pouchInventory;
   }

   public void reloadPouchFromPickup() {
      this.pouchInventory.reloadFromItemNBT();
      PouchItem.setPickedUpNewItems(this.pouchItem, false);
      this.func_75142_b();
   }

   public ITextComponent getPouchDisplayName() {
      return this.pouchItem.func_200301_q();
   }

   public ITextComponent getPouchDefaultDisplayName() {
      return this.pouchItem.func_77973_b().func_200295_i(this.pouchItem);
   }

   public void renamePouch(String name) {
      if (StringUtils.isBlank(name)) {
         this.pouchItem.func_135074_t();
      } else {
         this.pouchItem.func_200302_a(new StringTextComponent(name));
      }

   }

   public boolean func_75145_c(PlayerEntity player) {
      return player.field_71071_by.func_70301_a(this.playerInvSlot) == this.pouchItem;
   }

   public ItemStack func_184996_a(int slotId, int dragType, ClickType clickType, PlayerEntity player) {
      if (isCurrentPouchSlot(this, slotId, player, this.playerInvSlot)) {
         return ItemStack.field_190927_a;
      } else {
         return clickType == ClickType.SWAP && dragType == this.playerInvSlot ? ItemStack.field_190927_a : super.func_184996_a(slotId, dragType, clickType, player);
      }
   }

   public static boolean isCurrentPouchSlot(Container container, int slotId, PlayerEntity player, int playerInvSlot) {
      if (slotId >= 0 && slotId < container.field_75151_b.size()) {
         Slot slot = container.func_75139_a(slotId);
         if (slot.field_75224_c == player.field_71071_by && slot.getSlotIndex() == playerInvSlot) {
            return true;
         }
      }

      return false;
   }

   public ItemStack func_82846_b(PlayerEntity player, int index) {
      ItemStack itemstack = ItemStack.field_190927_a;
      Slot slot = (Slot)this.field_75151_b.get(index);
      int capacity = this.getPouchCapacity();
      if (slot != null && slot.func_75216_d()) {
         ItemStack itemstack1 = slot.func_75211_c();
         itemstack = itemstack1.func_77946_l();
         if (index < capacity) {
            if (!this.func_75135_a(itemstack1, capacity, capacity + 36, true)) {
               return ItemStack.field_190927_a;
            }

            slot.func_75220_a(itemstack1, itemstack);
         } else if (!this.func_75135_a(itemstack1, 0, capacity, false)) {
            return ItemStack.field_190927_a;
         }

         if (itemstack1.func_190926_b()) {
            slot.func_75215_d(ItemStack.field_190927_a);
         } else {
            slot.func_75218_e();
         }

         if (itemstack1.func_190916_E() == itemstack.func_190916_E()) {
            return ItemStack.field_190927_a;
         }

         slot.func_190901_a(player, itemstack1);
      }

      return itemstack;
   }

   public void func_75134_a(PlayerEntity player) {
      super.func_75134_a(player);
      player.field_70170_p.func_184148_a((PlayerEntity)null, player.func_226277_ct_(), player.func_226278_cu_(), player.func_226281_cx_(), ((PouchItem)this.pouchItem.func_77973_b()).getCloseSound(), SoundCategory.PLAYERS, 1.0F, 1.0F);
   }
}
