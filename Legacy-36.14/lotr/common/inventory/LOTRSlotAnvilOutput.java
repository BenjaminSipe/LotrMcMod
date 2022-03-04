package lotr.common.inventory;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class LOTRSlotAnvilOutput extends Slot {
   private LOTRContainerAnvil theAnvil;

   public LOTRSlotAnvilOutput(LOTRContainerAnvil container, IInventory inv, int id, int i, int j) {
      super(inv, id, i, j);
      this.theAnvil = container;
   }

   public boolean func_75214_a(ItemStack itemstack) {
      return false;
   }

   public boolean func_82869_a(EntityPlayer entityplayer) {
      if (this.func_75216_d()) {
         return this.theAnvil.materialCost > 0 ? this.theAnvil.hasMaterialOrCoinAmount(this.theAnvil.materialCost) : true;
      } else {
         return false;
      }
   }

   public void func_82870_a(EntityPlayer entityplayer, ItemStack itemstack) {
      int materials = this.theAnvil.materialCost;
      boolean wasSmithCombine = this.theAnvil.isSmithScrollCombine;
      this.theAnvil.invInput.func_70299_a(0, (ItemStack)null);
      ItemStack combinerItem = this.theAnvil.invInput.func_70301_a(1);
      if (combinerItem != null) {
         --combinerItem.field_77994_a;
         if (combinerItem.field_77994_a <= 0) {
            this.theAnvil.invInput.func_70299_a(1, (ItemStack)null);
         } else {
            this.theAnvil.invInput.func_70299_a(1, combinerItem);
         }
      }

      if (materials > 0) {
         this.theAnvil.takeMaterialOrCoinAmount(materials);
      }

      if (!entityplayer.field_70170_p.field_72995_K && wasSmithCombine) {
         LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.combineSmithScrolls);
      }

      this.theAnvil.materialCost = 0;
      this.theAnvil.isSmithScrollCombine = false;
      this.theAnvil.playAnvilSound();
   }
}
