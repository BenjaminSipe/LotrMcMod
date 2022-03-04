package lotr.common.inv;

import lotr.common.init.LOTRContainers;
import lotr.common.item.VesselDrinkItem;
import lotr.common.recipe.DrinkBrewingRecipe;
import lotr.common.tileentity.KegTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class KegContainer extends Container {
   public final IInventory theKeg;
   private final IIntArray kegData;
   private final Slot brewResultSlot;

   public KegContainer(int id, PlayerInventory playerInv, PacketBuffer extraData) {
      this(id, playerInv, new Inventory(10), new IntArray(3));
   }

   public KegContainer(int id, PlayerInventory playerInv, IInventory keg, IIntArray data) {
      super((ContainerType)LOTRContainers.KEG.get(), id);
      func_216962_a(keg, 10);
      func_216959_a(data, 3);
      this.theKeg = keg;
      this.kegData = data;
      this.theKeg.func_174889_b(playerInv.field_70458_d);

      int x;
      int x;
      for(x = 0; x < 3; ++x) {
         for(x = 0; x < 3; ++x) {
            KegSlot slot = new KegSlot(this, keg, x + x * 3, 14 + x * 18, 34 + x * 18);
            if (x == 2) {
               slot.setWaterSource();
            }

            this.func_75146_a(slot);
         }
      }

      this.func_75146_a(this.brewResultSlot = new KegResultSlot(keg, 9, 108, 52));

      for(x = 0; x < 3; ++x) {
         for(x = 0; x < 9; ++x) {
            this.func_75146_a(new Slot(playerInv, x + x * 9 + 9, 25 + x * 18, 139 + x * 18));
         }
      }

      for(x = 0; x < 9; ++x) {
         this.func_75146_a(new Slot(playerInv, x, 25 + x * 18, 197));
      }

      this.func_216961_a(data);
   }

   public boolean func_75145_c(PlayerEntity player) {
      return this.theKeg.func_70300_a(player);
   }

   public void func_75134_a(PlayerEntity player) {
      super.func_75134_a(player);
      this.theKeg.func_174886_c(player);
   }

   public void handleBrewButtonPress(ServerPlayerEntity player) {
      if (this.theKeg instanceof KegTileEntity) {
         ((KegTileEntity)this.theKeg).handleBrewButtonPress(player);
      }

   }

   public ItemStack func_82846_b(PlayerEntity player, int index) {
      ItemStack itemstack = ItemStack.field_190927_a;
      Slot slot = (Slot)this.field_75151_b.get(index);
      if (slot != null && slot.func_75216_d()) {
         ItemStack itemstack1 = slot.func_75211_c();
         itemstack = itemstack1.func_77946_l();
         if (index < 9) {
            if (!this.func_75135_a(itemstack1, 10, 46, true)) {
               return ItemStack.field_190927_a;
            }
         } else if (index != 9) {
            boolean mergedIntoKeg = false;
            if (this.getKegMode() == KegTileEntity.KegMode.EMPTY) {
               if (DrinkBrewingRecipe.isWaterSource(itemstack1)) {
                  mergedIntoKeg = this.func_75135_a(itemstack1, 6, 9, false);
               } else {
                  mergedIntoKeg = this.func_75135_a(itemstack1, 0, 6, false);
               }
            }

            if (!mergedIntoKeg) {
               if (index >= 10 && index < 37) {
                  if (!this.func_75135_a(itemstack1, 37, 46, false)) {
                     return ItemStack.field_190927_a;
                  }
               } else if (!this.func_75135_a(itemstack1, 10, 37, false)) {
                  return ItemStack.field_190927_a;
               }
            }
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

   public KegTileEntity.KegMode getKegMode() {
      return KegTileEntity.KegMode.forId(this.kegData.func_221476_a(0));
   }

   public boolean hasBrewingResult() {
      return this.brewResultSlot.func_75216_d();
   }

   public ItemStack getBrewingResult() {
      return this.brewResultSlot.func_75211_c();
   }

   public boolean canFinishBrewingNow() {
      ItemStack stack = this.getBrewingResult();
      if (!stack.func_190926_b()) {
         Item item = stack.func_77973_b();
         if (item instanceof VesselDrinkItem) {
            return !VesselDrinkItem.getPotency(stack).isMin();
         } else {
            return true;
         }
      } else {
         return false;
      }
   }

   public ITextComponent getKegTitle() {
      KegTileEntity.KegMode mode = this.getKegMode();
      if (mode == KegTileEntity.KegMode.EMPTY) {
         return new TranslationTextComponent("container.lotr.keg.empty");
      } else if (mode == KegTileEntity.KegMode.BREWING) {
         return new TranslationTextComponent("container.lotr.keg.brewing");
      } else {
         return (ITextComponent)(mode == KegTileEntity.KegMode.FULL ? new TranslationTextComponent("container.lotr.keg.full") : StringTextComponent.field_240750_d_);
      }
   }

   public ITextComponent getKegSubtitle() {
      ItemStack brewingItem = this.getBrewingResult();
      KegTileEntity.KegMode mode = this.getKegMode();
      return (ITextComponent)((mode == KegTileEntity.KegMode.BREWING || mode == KegTileEntity.KegMode.FULL) && !brewingItem.func_190926_b() ? new TranslationTextComponent("container.lotr.keg.item_subtitle", new Object[]{brewingItem.func_200301_q(), VesselDrinkItem.getPotency(brewingItem).getDisplayName()}) : StringTextComponent.field_240750_d_);
   }

   public VesselDrinkItem.Potency getInterruptBrewingPotency() {
      return VesselDrinkItem.getPotency(this.getBrewingResult()).getPrev();
   }

   public VesselDrinkItem.Potency getMinimumPotency() {
      return VesselDrinkItem.Potency.getMin();
   }

   public int getBrewProgressScaled(int i) {
      int fullTime = this.kegData.func_221476_a(2);
      return fullTime == 0 ? 0 : this.kegData.func_221476_a(1) * i / fullTime;
   }

   public int getBarrelFullAmountScaled(int i) {
      return this.getBrewingResult().func_190916_E() * i / 16;
   }
}
