package lotr.common.entity.npc;

import com.google.common.base.Predicate;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRMod;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import lotr.common.entity.ai.LOTREntityAIBanditFlee;
import lotr.common.entity.ai.LOTREntityAIBanditSteal;
import lotr.common.inventory.LOTRInventoryNPC;
import lotr.common.item.LOTRItemCoin;
import lotr.common.item.LOTRItemGem;
import lotr.common.item.LOTRItemRing;
import lotr.common.quest.LOTRMiniQuest;
import lotr.common.quest.LOTRMiniQuestFactory;
import lotr.common.quest.MiniQuestSelector;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;

public class LOTREntityRuffianSpy extends LOTREntityBreeRuffian implements IBandit {
   private LOTRInventoryNPC ruffianInventory = IBandit.Helper.createInv(this);
   private EntityPlayer playerToRob;

   public LOTREntityRuffianSpy(World world) {
      super(world);
      this.questInfo.setBountyHelpPredicate(new Predicate() {
         public boolean apply(EntityPlayer player) {
            ItemStack itemstack = player.func_70694_bm();
            if (LOTRItemCoin.getStackValue(itemstack, true) > 0) {
               return true;
            } else if (itemstack == null) {
               return false;
            } else {
               Item item = itemstack.func_77973_b();
               return item == Items.field_151043_k || item == LOTRMod.silver || item instanceof LOTRItemGem || item instanceof LOTRItemRing;
            }
         }
      });
      this.questInfo.setBountyHelpConsumer(new Predicate() {
         public boolean apply(EntityPlayer player) {
            if (!player.field_71075_bZ.field_75098_d) {
               ItemStack itemstack = player.func_70694_bm();
               if (itemstack != null) {
                  --itemstack.field_77994_a;
                  if (itemstack.field_77994_a <= 0) {
                     player.field_71071_by.func_70299_a(player.field_71071_by.field_70461_c, (ItemStack)null);
                  }
               }
            }

            LOTREntityRuffianSpy.this.playTradeSound();
            return true;
         }
      });
      this.questInfo.setActiveBountySelector(new MiniQuestSelector.BountyActiveAnyFaction());
   }

   protected int addBreeAttackAI(int prio) {
      this.field_70714_bg.func_75776_a(prio, new LOTREntityAIBanditSteal(this, 1.6D));
      ++prio;
      this.field_70714_bg.func_75776_a(prio, new LOTREntityAIAttackOnCollide(this, 1.4D, false));
      ++prio;
      this.field_70714_bg.func_75776_a(prio, new LOTREntityAIBanditFlee(this, 1.4D));
      return prio;
   }

   public LOTREntityNPC getBanditAsNPC() {
      return this;
   }

   public int getMaxThefts() {
      return 1;
   }

   public LOTRInventoryNPC getBanditInventory() {
      return this.ruffianInventory;
   }

   public boolean canTargetPlayerForTheft(EntityPlayer player) {
      return player == this.playerToRob || this.canRuffianTarget(player);
   }

   public String getTheftSpeechBank(EntityPlayer player) {
      return "bree/ruffian/hostile";
   }

   public IChatComponent getTheftChatMsg(EntityPlayer player) {
      return new ChatComponentTranslation("chat.lotr.ruffianSteal", new Object[0]);
   }

   public void func_70014_b(NBTTagCompound nbt) {
      super.func_70014_b(nbt);
      this.ruffianInventory.writeToNBT(nbt);
   }

   public void func_70037_a(NBTTagCompound nbt) {
      super.func_70037_a(nbt);
      this.ruffianInventory.readFromNBT(nbt);
   }

   public void setAttackTarget(EntityLivingBase target, boolean speak) {
      if (target instanceof EntityPlayer && !((EntityPlayer)target).field_71075_bZ.field_75098_d) {
         this.playerToRob = (EntityPlayer)target;
      }

      super.setAttackTarget(target, speak);
   }

   public void func_70645_a(DamageSource damagesource) {
      super.func_70645_a(damagesource);
      if (!this.field_70170_p.field_72995_K) {
         this.ruffianInventory.dropAllItems();
      }

   }

   protected LOTRAchievement getKillAchievement() {
      return LOTRAchievement.killRuffianSpy;
   }

   public LOTRMiniQuest createMiniQuest() {
      return LOTRMiniQuestFactory.RUFFIAN_SPY.createQuest(this);
   }

   public LOTRMiniQuestFactory getBountyHelpSpeechDir() {
      return LOTRMiniQuestFactory.RUFFIAN_SPY;
   }
}
