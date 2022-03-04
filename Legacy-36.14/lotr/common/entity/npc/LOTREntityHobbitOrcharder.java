package lotr.common.entity.npc;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.entity.LOTREntityUtils;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import lotr.common.item.LOTRItemLeatherHat;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityHobbitOrcharder extends LOTREntityHobbit implements LOTRTradeable {
   public LOTREntityHobbitOrcharder(World world) {
      super(world);
      LOTREntityUtils.removeAITask(this, EntityAIPanic.class);
      this.field_70714_bg.func_75776_a(2, new LOTREntityAIAttackOnCollide(this, 1.2D, false));
      this.addTargetTasks(false);
      this.isNPCPersistent = false;
   }

   public LOTRTradeEntries getBuyPool() {
      return LOTRTradeEntries.HOBBIT_ORCHARDER_BUY;
   }

   public LOTRTradeEntries getSellPool() {
      return LOTRTradeEntries.HOBBIT_ORCHARDER_SELL;
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      ItemStack hat = new ItemStack(LOTRMod.leatherHat);
      LOTRItemLeatherHat.setHatColor(hat, 4818735);
      this.func_70062_b(4, hat);
      int i = this.field_70146_Z.nextInt(3);
      if (i == 0) {
         this.npcItemsInv.setMeleeWeapon(new ItemStack(Items.field_151036_c));
      } else if (i == 1) {
         this.npcItemsInv.setMeleeWeapon(new ItemStack(Items.field_151049_t));
      } else if (i == 2) {
         this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.axeBronze));
      }

      this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
      return data;
   }

   public float getAlignmentBonus() {
      return 2.0F;
   }

   public boolean canTradeWith(EntityPlayer entityplayer) {
      return this.isFriendlyAndAligned(entityplayer);
   }

   public void onPlayerTrade(EntityPlayer entityplayer, LOTRTradeEntries.TradeType type, ItemStack itemstack) {
      if (type == LOTRTradeEntries.TradeType.BUY && itemstack.func_77973_b() instanceof ItemFood) {
         LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.buyOrcharderFood);
      }

   }

   public String getSpeechBank(EntityPlayer entityplayer) {
      return this.isFriendlyAndAligned(entityplayer) ? "hobbit/orcharder/friendly" : "hobbit/hobbit/hostile";
   }
}
