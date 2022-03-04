package lotr.common.inventory;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class LOTRSlotMillstone extends Slot {
   private EntityPlayer thePlayer;
   private int itemsTaken;

   public LOTRSlotMillstone(EntityPlayer entityplayer, IInventory inv, int i, int j, int k) {
      super(inv, i, j, k);
      this.thePlayer = entityplayer;
   }

   public boolean func_75214_a(ItemStack itemstack) {
      return false;
   }

   public ItemStack func_75209_a(int i) {
      if (this.func_75216_d()) {
         this.itemsTaken += Math.min(i, this.func_75211_c().field_77994_a);
      }

      return super.func_75209_a(i);
   }

   public void func_82870_a(EntityPlayer entityplayer, ItemStack itemstack) {
      this.func_75208_c(itemstack);
      super.func_82870_a(entityplayer, itemstack);
   }

   protected void func_75210_a(ItemStack itemstack, int i) {
      this.itemsTaken += i;
      this.func_75208_c(itemstack);
   }

   protected void func_75208_c(ItemStack itemstack) {
      itemstack.func_77980_a(this.thePlayer.field_70170_p, this.thePlayer, this.itemsTaken);
      this.itemsTaken = 0;
      if (!this.thePlayer.field_70170_p.field_72995_K && itemstack.func_77973_b() == LOTRMod.obsidianShard) {
         LOTRLevelData.getData(this.thePlayer).addAchievement(LOTRAchievement.smeltObsidianShard);
      }

   }
}
