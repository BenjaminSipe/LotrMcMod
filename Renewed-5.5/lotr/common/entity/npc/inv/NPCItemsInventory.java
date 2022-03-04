package lotr.common.entity.npc.inv;

import lotr.common.entity.npc.NPCEntity;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.SPacketNPCState;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Hand;

public class NPCItemsInventory extends NPCInventory {
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

   public NPCItemsInventory(NPCEntity entity) {
      super(entity, 12, "NPCItemsInv");
   }

   public boolean getIsEating() {
      return this.isEating;
   }

   private void setIsEating(boolean flag) {
      if (this.isEating != flag) {
         this.isEating = flag;
         this.sendIsEatingToWatchers();
      }

   }

   public void writeToEntityNBT(CompoundNBT nbt) {
      super.writeToEntityNBT(nbt);
      nbt.func_74757_a("NPCEating", this.isEating);
   }

   public void readFromEntityNBT(CompoundNBT nbt) {
      super.readFromEntityNBT(nbt);
      this.isEating = nbt.func_74767_n("NPCEating");
      if (this.isEating) {
         this.stopEatingAndRestoreHeld();
      }

   }

   public void sendIsEating(ServerPlayerEntity player) {
      LOTRPacketHandler.sendTo(this.createIsEatingPacket(), player);
   }

   public void sendIsEatingToWatchers() {
      LOTRPacketHandler.sendToAllTrackingEntity(this.createIsEatingPacket(), this.theEntity);
   }

   private SPacketNPCState createIsEatingPacket() {
      return new SPacketNPCState((NPCEntity)this.theEntity, SPacketNPCState.Type.IS_EATING, this.getIsEating());
   }

   public void receiveClientIsEating(boolean state) {
      if (!((NPCEntity)this.theEntity).field_70170_p.field_72995_K) {
         throw new IllegalStateException("This method should only be called on the clientside");
      } else {
         this.isEating = state;
      }
   }

   public ItemStack getIdleItem() {
      return this.func_70301_a(0).func_77946_l();
   }

   public void setIdleItem(ItemStack item) {
      this.func_70299_a(0, item);
   }

   public void setIdleItemsFromMeleeWeapons() {
      this.setIdleItem(this.getMeleeWeapon());
      this.setIdleItemMounted(this.getMeleeWeaponMounted());
   }

   public void setIdleItemsFromRangedWeapons() {
      this.setIdleItem(this.getRangedWeapon());
      this.setIdleItemMounted(this.getRangedWeapon());
   }

   public void clearIdleItem() {
      this.setIdleItem(ItemStack.field_190927_a);
   }

   public ItemStack getMeleeWeapon() {
      return this.func_70301_a(1).func_77946_l();
   }

   public void setMeleeWeapon(ItemStack item) {
      this.func_70299_a(1, item);
   }

   public void clearMeleeWeapon() {
      this.setMeleeWeapon(ItemStack.field_190927_a);
   }

   public ItemStack getRangedWeapon() {
      return this.func_70301_a(2).func_77946_l();
   }

   public void setRangedWeapon(ItemStack item) {
      this.func_70299_a(2, item);
   }

   public void clearRangedWeapon() {
      this.setRangedWeapon(ItemStack.field_190927_a);
   }

   public ItemStack getSpearBackup() {
      return this.func_70301_a(3).func_77946_l();
   }

   public void setSpearBackup(ItemStack item) {
      this.func_70299_a(3, item);
   }

   private ItemStack getEatingBackup() {
      return this.func_70301_a(4).func_77946_l();
   }

   private void setEatingBackup(ItemStack item) {
      this.func_70299_a(4, item);
   }

   public void backupHeldAndStartEating(ItemStack eatingItem) {
      this.setEatingBackup(((NPCEntity)this.theEntity).func_184586_b(Hand.MAIN_HAND));
      this.setIsEating(true);
      ((NPCEntity)this.theEntity).func_184611_a(Hand.MAIN_HAND, eatingItem);
   }

   public void stopEatingAndRestoreHeld() {
      ((NPCEntity)this.theEntity).func_184611_a(Hand.MAIN_HAND, this.getEatingBackup());
      this.setEatingBackup(ItemStack.field_190927_a);
      this.setIsEating(false);
   }

   public ItemStack getIdleItemMounted() {
      return this.func_70301_a(5).func_77946_l();
   }

   public void setIdleItemMounted(ItemStack item) {
      this.func_70299_a(5, item);
   }

   public ItemStack getMeleeWeaponMounted() {
      return this.func_70301_a(6).func_77946_l();
   }

   public void setMeleeWeaponMounted(ItemStack item) {
      this.func_70299_a(6, item);
   }

   public ItemStack getReplacedIdleItem() {
      return this.func_70301_a(7).func_77946_l();
   }

   public void setReplacedIdleItem(ItemStack item) {
      this.func_70299_a(7, item);
   }

   public ItemStack getReplacedMeleeWeaponMounted() {
      return this.func_70301_a(8).func_77946_l();
   }

   public void setReplacedMeleeWeaponMounted(ItemStack item) {
      this.func_70299_a(8, item);
   }

   public ItemStack getReplacedIdleItemMounted() {
      return this.func_70301_a(9).func_77946_l();
   }

   public void setReplacedIdleItemMounted(ItemStack item) {
      this.func_70299_a(9, item);
   }

   public ItemStack getBombingItem() {
      return this.func_70301_a(10).func_77946_l();
   }

   public void setBombingItem(ItemStack item) {
      this.func_70299_a(10, item);
   }

   public ItemStack getBomb() {
      return this.func_70301_a(11).func_77946_l();
   }

   public void setBomb(ItemStack item) {
      this.func_70299_a(11, item);
   }
}
