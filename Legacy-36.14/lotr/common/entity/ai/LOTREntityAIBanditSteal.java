package lotr.common.entity.ai;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import lotr.common.LOTRLevelData;
import lotr.common.entity.npc.IBandit;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.item.LOTRItemCoin;
import lotr.common.item.LOTRItemGem;
import lotr.common.item.LOTRItemPouch;
import lotr.common.item.LOTRItemRing;
import lotr.common.item.LOTRValuableItems;
import lotr.common.recipe.LOTRRecipes;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.util.MathHelper;

public class LOTREntityAIBanditSteal extends EntityAIBase {
   private IBandit theBandit;
   private LOTREntityNPC theBanditAsNPC;
   private EntityPlayer targetPlayer;
   private EntityPlayer prevTargetPlayer;
   private double speed;
   private int chaseTimer;
   private int rePathDelay;

   public LOTREntityAIBanditSteal(IBandit bandit, double d) {
      this.theBandit = bandit;
      this.theBanditAsNPC = this.theBandit.getBanditAsNPC();
      this.speed = d;
      this.func_75248_a(3);
   }

   public boolean func_75250_a() {
      if (!this.theBandit.getBanditInventory().isEmpty()) {
         return false;
      } else {
         double range = 32.0D;
         List players = this.theBanditAsNPC.field_70170_p.func_72872_a(EntityPlayer.class, this.theBanditAsNPC.field_70121_D.func_72314_b(range, range, range));
         List validTargets = new ArrayList();

         for(int i = 0; i < players.size(); ++i) {
            EntityPlayer entityplayer = (EntityPlayer)players.get(i);
            if (!entityplayer.field_71075_bZ.field_75098_d && this.theBandit.canTargetPlayerForTheft(entityplayer) && IBandit.Helper.canStealFromPlayerInv(this.theBandit, entityplayer)) {
               validTargets.add(entityplayer);
            }
         }

         if (validTargets.isEmpty()) {
            return false;
         } else {
            this.targetPlayer = (EntityPlayer)validTargets.get(this.theBanditAsNPC.func_70681_au().nextInt(validTargets.size()));
            if (this.targetPlayer != this.prevTargetPlayer) {
               this.theBanditAsNPC.sendSpeechBank(this.targetPlayer, this.theBandit.getTheftSpeechBank(this.targetPlayer));
            }

            return true;
         }
      }
   }

   public void func_75249_e() {
      this.chaseTimer = 600;
   }

   public void func_75246_d() {
      --this.chaseTimer;
      this.theBanditAsNPC.func_70671_ap().func_75651_a(this.targetPlayer, 30.0F, 30.0F);
      --this.rePathDelay;
      if (this.rePathDelay <= 0) {
         this.rePathDelay = 10;
         this.theBanditAsNPC.func_70661_as().func_75497_a(this.targetPlayer, this.speed);
      }

      if (this.theBanditAsNPC.func_70068_e(this.targetPlayer) <= 2.0D) {
         this.chaseTimer = 0;
         this.steal();
      }

   }

   public boolean func_75253_b() {
      if (this.targetPlayer != null && this.targetPlayer.func_70089_S() && !this.targetPlayer.field_71075_bZ.field_75098_d && IBandit.Helper.canStealFromPlayerInv(this.theBandit, this.targetPlayer)) {
         return this.chaseTimer > 0 && this.theBanditAsNPC.func_70068_e(this.targetPlayer) < 256.0D;
      } else {
         return false;
      }
   }

   public void func_75251_c() {
      this.chaseTimer = 0;
      this.rePathDelay = 0;
      if (this.targetPlayer != null) {
         this.prevTargetPlayer = this.targetPlayer;
      }

      this.targetPlayer = null;
   }

   private void steal() {
      InventoryPlayer inv = this.targetPlayer.field_71071_by;
      int thefts = MathHelper.func_76136_a(this.theBanditAsNPC.func_70681_au(), 1, this.theBandit.getMaxThefts());
      boolean stolenSomething = false;

      for(int i = 0; i < thefts; ++i) {
         if (this.tryStealItem(inv, LOTRItemCoin.class)) {
            stolenSomething = true;
         }

         if (this.tryStealItem(inv, LOTRItemGem.class)) {
            stolenSomething = true;
         } else if (this.tryStealItem(inv, LOTRValuableItems.getToolMaterials())) {
            stolenSomething = true;
         } else if (this.tryStealItem(inv, LOTRItemRing.class)) {
            stolenSomething = true;
         } else if (this.tryStealItem(inv, ItemArmor.class)) {
            stolenSomething = true;
         } else if (this.tryStealItem(inv, ItemSword.class)) {
            stolenSomething = true;
         } else if (this.tryStealItem(inv, ItemTool.class)) {
            stolenSomething = true;
         } else if (this.tryStealItem(inv, LOTRItemPouch.class)) {
            stolenSomething = true;
         } else if (this.tryStealItem(inv)) {
            stolenSomething = true;
         }
      }

      if (stolenSomething) {
         this.targetPlayer.func_145747_a(this.theBandit.getTheftChatMsg(this.targetPlayer));
         this.theBanditAsNPC.func_85030_a("mob.horse.leather", 0.5F, 1.0F);
         if (this.theBanditAsNPC.func_70638_az() != null) {
            this.theBanditAsNPC.func_70624_b((EntityLivingBase)null);
         }

         LOTRLevelData.getData(this.targetPlayer).cancelFastTravel();
      }

   }

   private boolean tryStealItem(InventoryPlayer inv, final Item item) {
      return this.tryStealItem_do(inv, new LOTREntityAIBanditSteal.BanditItemFilter() {
         public boolean isApplicable(ItemStack itemstack) {
            return itemstack.func_77973_b() == item;
         }
      });
   }

   private boolean tryStealItem(InventoryPlayer inv, final Class itemclass) {
      return this.tryStealItem_do(inv, new LOTREntityAIBanditSteal.BanditItemFilter() {
         public boolean isApplicable(ItemStack itemstack) {
            return itemclass.isAssignableFrom(itemstack.func_77973_b().getClass());
         }
      });
   }

   private boolean tryStealItem(InventoryPlayer inv, final List itemList) {
      return this.tryStealItem_do(inv, new LOTREntityAIBanditSteal.BanditItemFilter() {
         public boolean isApplicable(ItemStack itemstack) {
            Iterator var2 = itemList.iterator();

            ItemStack listItem;
            do {
               if (!var2.hasNext()) {
                  return false;
               }

               listItem = (ItemStack)var2.next();
            } while(!LOTRRecipes.checkItemEquals(listItem, itemstack));

            return true;
         }
      });
   }

   private boolean tryStealItem(InventoryPlayer inv) {
      return this.tryStealItem_do(inv, new LOTREntityAIBanditSteal.BanditItemFilter() {
         public boolean isApplicable(ItemStack itemstack) {
            return true;
         }
      });
   }

   private boolean tryStealItem_do(InventoryPlayer inv, LOTREntityAIBanditSteal.BanditItemFilter filter) {
      Integer[] inventorySlots = new Integer[inv.field_70462_a.length];

      for(int l = 0; l < inventorySlots.length; ++l) {
         inventorySlots[l] = l;
      }

      List slotsAsList = Arrays.asList(inventorySlots);
      Collections.shuffle(slotsAsList);
      inventorySlots = (Integer[])slotsAsList.toArray(inventorySlots);
      Integer[] var5 = inventorySlots;
      int var6 = inventorySlots.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         int slot = var5[var7];
         if (slot != inv.field_70461_c) {
            ItemStack itemstack = inv.func_70301_a(slot);
            if (itemstack != null && filter.isApplicable(itemstack) && this.stealItem(inv, slot)) {
               return true;
            }
         }
      }

      return false;
   }

   private int getRandomTheftAmount(ItemStack itemstack) {
      return MathHelper.func_76136_a(this.theBanditAsNPC.func_70681_au(), 1, 8);
   }

   private boolean stealItem(InventoryPlayer inv, int slot) {
      ItemStack playerItem = inv.func_70301_a(slot);
      int theft = this.getRandomTheftAmount(playerItem);
      if (theft > playerItem.field_77994_a) {
         theft = playerItem.field_77994_a;
      }

      int banditSlot = 0;

      do {
         if (this.theBandit.getBanditInventory().func_70301_a(banditSlot) == null) {
            ItemStack stolenItem = playerItem.func_77946_l();
            stolenItem.field_77994_a = theft;
            this.theBandit.getBanditInventory().func_70299_a(banditSlot, stolenItem);
            playerItem.field_77994_a -= theft;
            if (playerItem.field_77994_a <= 0) {
               inv.func_70299_a(slot, (ItemStack)null);
            }

            this.theBanditAsNPC.isNPCPersistent = true;
            return true;
         }

         ++banditSlot;
      } while(banditSlot < this.theBandit.getBanditInventory().func_70302_i_());

      return false;
   }

   private abstract class BanditItemFilter {
      private BanditItemFilter() {
      }

      public abstract boolean isApplicable(ItemStack var1);

      // $FF: synthetic method
      BanditItemFilter(Object x1) {
         this();
      }
   }
}
