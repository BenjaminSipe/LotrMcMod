package lotr.common.entity.npc;

import lotr.common.inventory.LOTRInventoryNPC;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class LOTRInventoryNPCItems extends LOTRInventoryNPC {
   private static final int IDLE_ITEM = 0;
   private static final int WEAPON_MELEE = 1;
   private static final int WEAPON_RANGED = 2;
   private static final int SPEAR_BACKUP = 3;
   private static final int EATING_BACKUP = 4;
   private static final int IDLE_ITEM_MOUNTED = 5;
   private static final int WEAPON_MELEE_MOUNTED = 6;
   private static final int REPLACED_IDLE = 7;
   private static final int REPLACED_MELEE_MOUNTED = 8;
   private static final int REPLACED_IDLE_MOUNTED = 9;
   private static final int BOMBING_ITEM = 10;
   private static final int BOMB = 11;
   private boolean isEating = false;

   public LOTRInventoryNPCItems(LOTREntityNPC npc) {
      super("NPCItemsInv", npc, 12);
   }

   public void setIsEating(boolean flag) {
      this.isEating = flag;
      this.theNPC.sendIsEatingToWatchers();
   }

   public boolean getIsEating() {
      return this.isEating;
   }

   public void writeToNBT(NBTTagCompound nbt) {
      super.writeToNBT(nbt);
      nbt.func_74757_a("NPCEating", this.isEating);
   }

   public void readFromNBT(NBTTagCompound nbt) {
      super.readFromNBT(nbt);
      this.isEating = nbt.func_74767_n("NPCEating");
      if (this.isEating) {
         this.theNPC.func_70062_b(0, this.getEatingBackup());
         this.setEatingBackup((ItemStack)null);
         this.setIsEating(false);
      }

   }

   public ItemStack getIdleItem() {
      ItemStack item = this.func_70301_a(0);
      return item == null ? null : item.func_77946_l();
   }

   public void setIdleItem(ItemStack item) {
      this.func_70299_a(0, item);
   }

   public ItemStack getMeleeWeapon() {
      ItemStack item = this.func_70301_a(1);
      return item == null ? null : item.func_77946_l();
   }

   public void setMeleeWeapon(ItemStack item) {
      this.func_70299_a(1, item);
   }

   public ItemStack getRangedWeapon() {
      ItemStack item = this.func_70301_a(2);
      return item == null ? null : item.func_77946_l();
   }

   public void setRangedWeapon(ItemStack item) {
      this.func_70299_a(2, item);
   }

   public ItemStack getSpearBackup() {
      ItemStack item = this.func_70301_a(3);
      return item == null ? null : item.func_77946_l();
   }

   public void setSpearBackup(ItemStack item) {
      this.func_70299_a(3, item);
   }

   public ItemStack getEatingBackup() {
      ItemStack item = this.func_70301_a(4);
      return item == null ? null : item.func_77946_l();
   }

   public void setEatingBackup(ItemStack item) {
      this.func_70299_a(4, item);
   }

   public ItemStack getIdleItemMounted() {
      ItemStack item = this.func_70301_a(5);
      return item == null ? null : item.func_77946_l();
   }

   public void setIdleItemMounted(ItemStack item) {
      this.func_70299_a(5, item);
   }

   public ItemStack getMeleeWeaponMounted() {
      ItemStack item = this.func_70301_a(6);
      return item == null ? null : item.func_77946_l();
   }

   public void setMeleeWeaponMounted(ItemStack item) {
      this.func_70299_a(6, item);
   }

   public ItemStack getReplacedIdleItem() {
      ItemStack item = this.func_70301_a(7);
      return item == null ? null : item.func_77946_l();
   }

   public void setReplacedIdleItem(ItemStack item) {
      this.func_70299_a(7, item);
   }

   public ItemStack getReplacedMeleeWeaponMounted() {
      ItemStack item = this.func_70301_a(8);
      return item == null ? null : item.func_77946_l();
   }

   public void setReplacedMeleeWeaponMounted(ItemStack item) {
      this.func_70299_a(8, item);
   }

   public ItemStack getReplacedIdleItemMounted() {
      ItemStack item = this.func_70301_a(9);
      return item == null ? null : item.func_77946_l();
   }

   public void setReplacedIdleItemMounted(ItemStack item) {
      this.func_70299_a(9, item);
   }

   public ItemStack getBombingItem() {
      ItemStack item = this.func_70301_a(10);
      return item == null ? null : item.func_77946_l();
   }

   public void setBombingItem(ItemStack item) {
      this.func_70299_a(10, item);
   }

   public ItemStack getBomb() {
      ItemStack item = this.func_70301_a(11);
      return item == null ? null : item.func_77946_l();
   }

   public void setBomb(ItemStack item) {
      this.func_70299_a(11, item);
   }
}
