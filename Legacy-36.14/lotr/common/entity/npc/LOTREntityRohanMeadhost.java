package lotr.common.entity.npc;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRFoods;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityRohanMeadhost extends LOTREntityRohanMan implements LOTRTradeable.Bartender {
   public LOTREntityRohanMeadhost(World world) {
      super(world);
      this.addTargetTasks(false);
      this.npcLocationName = "entity.lotr.RohanMeadhost.locationName";
   }

   public LOTRTradeEntries getBuyPool() {
      return LOTRTradeEntries.ROHAN_MEADHOST_BUY;
   }

   public LOTRTradeEntries getSellPool() {
      return LOTRTradeEntries.ROHAN_MEADHOST_SELL;
   }

   public EntityAIBase createRohanAttackAI() {
      return new LOTREntityAIAttackOnCollide(this, 1.3D, false);
   }

   public void setupNPCGender() {
      this.familyInfo.setMale(this.field_70146_Z.nextBoolean());
   }

   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.2D);
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.mugMead));
      this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
      return data;
   }

   public float getAlignmentBonus() {
      return 2.0F;
   }

   protected void func_70628_a(boolean flag, int i) {
      super.func_70628_a(flag, i);
      int j = this.field_70146_Z.nextInt(3) + this.field_70146_Z.nextInt(i + 1);

      for(int k = 0; k < j; ++k) {
         int l = this.field_70146_Z.nextInt(11);
         switch(l) {
         case 0:
         case 1:
         case 2:
            Item food = LOTRFoods.ROHAN.getRandomFood(this.field_70146_Z).func_77973_b();
            this.func_70099_a(new ItemStack(food), 0.0F);
            break;
         case 3:
            this.func_70099_a(new ItemStack(Items.field_151074_bl, 2 + this.field_70146_Z.nextInt(3)), 0.0F);
            break;
         case 4:
            this.func_70099_a(new ItemStack(Items.field_151015_O, 1 + this.field_70146_Z.nextInt(4)), 0.0F);
            break;
         case 5:
            this.func_70099_a(new ItemStack(Items.field_151102_aT, 1 + this.field_70146_Z.nextInt(3)), 0.0F);
            break;
         case 6:
            this.func_70099_a(new ItemStack(Items.field_151121_aF, 1 + this.field_70146_Z.nextInt(2)), 0.0F);
            break;
         case 7:
         case 8:
            this.func_70099_a(new ItemStack(LOTRMod.mug), 0.0F);
            break;
         case 9:
         case 10:
            Item drink = LOTRMod.mugMead;
            this.func_70099_a(new ItemStack(drink, 1, 1 + this.field_70146_Z.nextInt(3)), 0.0F);
         }
      }

   }

   public boolean canTradeWith(EntityPlayer entityplayer) {
      return this.isFriendlyAndAligned(entityplayer);
   }

   public void onPlayerTrade(EntityPlayer entityplayer, LOTRTradeEntries.TradeType type, ItemStack itemstack) {
      if (type == LOTRTradeEntries.TradeType.BUY && itemstack.func_77973_b() == LOTRMod.mugMead) {
         LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.buyRohanMead);
      }

   }

   public String getSpeechBank(EntityPlayer entityplayer) {
      return this.isFriendlyAndAligned(entityplayer) ? "rohan/meadhost/friendly" : "rohan/meadhost/hostile";
   }
}
