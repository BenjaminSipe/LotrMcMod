package lotr.common.entity.npc;

import java.awt.Color;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRDimension;
import lotr.common.LOTRFoods;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import lotr.common.entity.ai.LOTREntityAIDrink;
import lotr.common.entity.ai.LOTREntityAIEat;
import lotr.common.fac.LOTRFaction;
import lotr.common.item.LOTRItemLeatherHat;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityAIWatchClosest2;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTREntityScrapTrader extends LOTREntityMan implements LOTRTravellingTrader, LOTRTradeable.Smith {
   private int timeUntilFadeOut;
   public static final int maxFadeoutTick = 60;

   public LOTREntityScrapTrader(World world) {
      super(world);
      this.func_70105_a(0.6F, 1.8F);
      this.func_70661_as().func_75491_a(true);
      this.func_70661_as().func_75498_b(true);
      this.field_70714_bg.func_75776_a(0, new EntityAISwimming(this));
      this.field_70714_bg.func_75776_a(1, new LOTREntityAIAttackOnCollide(this, 1.3D, true));
      this.field_70714_bg.func_75776_a(2, new EntityAIOpenDoor(this, true));
      this.field_70714_bg.func_75776_a(3, new EntityAIWander(this, 1.0D));
      this.field_70714_bg.func_75776_a(4, new LOTREntityAIEat(this, LOTRFoods.DUNLENDING, 8000));
      this.field_70714_bg.func_75776_a(4, new LOTREntityAIDrink(this, LOTRFoods.DUNLENDING_DRINK, 8000));
      this.field_70714_bg.func_75776_a(5, new EntityAIWatchClosest2(this, EntityPlayer.class, 10.0F, 0.1F));
      this.field_70714_bg.func_75776_a(5, new EntityAIWatchClosest2(this, LOTREntityNPC.class, 5.0F, 0.05F));
      this.field_70714_bg.func_75776_a(6, new EntityAIWatchClosest(this, EntityLiving.class, 8.0F, 0.02F));
      this.field_70714_bg.func_75776_a(7, new EntityAILookIdle(this));
      this.addTargetTasks(false);
   }

   public LOTRTradeEntries getBuyPool() {
      return LOTRTradeEntries.SCRAP_TRADER_BUY;
   }

   public LOTRTradeEntries getSellPool() {
      return LOTRTradeEntries.SCRAP_TRADER_SELL;
   }

   public void func_70088_a() {
      super.func_70088_a();
      this.field_70180_af.func_75682_a(20, -1);
   }

   public int getFadeoutTick() {
      return this.field_70180_af.func_75679_c(20);
   }

   public void setFadeoutTick(int i) {
      this.field_70180_af.func_75692_b(20, i);
   }

   public float getFadeoutProgress(float f) {
      int i = this.getFadeoutTick();
      return i >= 0 ? ((float)(60 - i) + f) / 60.0F : 0.0F;
   }

   public void setupNPCGender() {
      this.familyInfo.setMale(true);
   }

   public void setupNPCName() {
      int i = this.field_70146_Z.nextInt(4);
      if (i == 0) {
         this.familyInfo.setName(LOTRNames.getBreeName(this.field_70146_Z, this.familyInfo.isMale()));
      } else if (i == 1) {
         this.familyInfo.setName(LOTRNames.getDunlendingName(this.field_70146_Z, this.familyInfo.isMale()));
      } else if (i == 2) {
         this.familyInfo.setName(LOTRNames.getRohirricName(this.field_70146_Z, this.familyInfo.isMale()));
      } else if (i == 3) {
         this.familyInfo.setName(LOTRNames.getGondorName(this.field_70146_Z, this.familyInfo.isMale()));
      }

   }

   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(20.0D);
      this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.2D);
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      int weapon = this.field_70146_Z.nextInt(2);
      if (weapon == 0) {
         this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerIron));
      } else if (weapon == 1) {
         this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerBronze));
      }

      this.npcItemsInv.setIdleItem(new ItemStack(LOTRMod.shireHeather));
      ItemStack hat = new ItemStack(LOTRMod.leatherHat);
      float h = 0.06111111F;
      float s = MathHelper.func_151240_a(this.field_70146_Z, 0.0F, 0.5F);
      float b = MathHelper.func_151240_a(this.field_70146_Z, 0.0F, 0.5F);
      int hatColor = Color.HSBtoRGB(h, s, b) & 16777215;
      LOTRItemLeatherHat.setHatColor(hat, hatColor);
      if (this.field_70146_Z.nextInt(3) == 0) {
         h = this.field_70146_Z.nextFloat();
         s = MathHelper.func_151240_a(this.field_70146_Z, 0.7F, 0.9F);
         b = MathHelper.func_151240_a(this.field_70146_Z, 0.8F, 1.0F);
      } else {
         h = 0.0F;
         s = 0.0F;
         b = this.field_70146_Z.nextFloat();
      }

      int featherColor = Color.HSBtoRGB(h, s, b) & 16777215;
      LOTRItemLeatherHat.setFeatherColor(hat, featherColor);
      this.func_70062_b(4, hat);
      return data;
   }

   public String getNPCName() {
      return this.familyInfo.getName();
   }

   public LOTRFaction getFaction() {
      return LOTRFaction.UNALIGNED;
   }

   public LOTREntityNPC createTravellingEscort() {
      return null;
   }

   public String getDepartureSpeech() {
      return "misc/scrapTrader/departure";
   }

   public void onAttackModeChange(LOTREntityNPC.AttackMode mode, boolean mounted) {
      if (mode == LOTREntityNPC.AttackMode.IDLE) {
         this.func_70062_b(0, this.npcItemsInv.getIdleItem());
      } else {
         this.func_70062_b(0, this.npcItemsInv.getMeleeWeapon());
      }

   }

   public int func_70658_aO() {
      return 5;
   }

   public boolean func_70085_c(EntityPlayer entityplayer) {
      boolean flag = super.func_70085_c(entityplayer);
      if (flag && !this.field_70170_p.field_72995_K && LOTRDimension.getCurrentDimensionWithFallback(this.field_70170_p) == LOTRDimension.UTUMNO && this.timeUntilFadeOut <= 0) {
         this.timeUntilFadeOut = 100;
      }

      return flag;
   }

   public boolean canBeFreelyTargetedBy(EntityLiving attacker) {
      return LOTRDimension.getCurrentDimensionWithFallback(this.field_70170_p) == LOTRDimension.UTUMNO ? false : super.canBeFreelyTargetedBy(attacker);
   }

   public boolean func_70097_a(DamageSource damagesource, float f) {
      if (damagesource.func_76346_g() != null && LOTRDimension.getCurrentDimensionWithFallback(this.field_70170_p) == LOTRDimension.UTUMNO) {
         if (!this.field_70170_p.field_72995_K && this.getFadeoutTick() < 0) {
            this.setFadeoutTick(60);
         }

         return false;
      } else {
         return super.func_70097_a(damagesource, f);
      }
   }

   public void func_70636_d() {
      super.func_70636_d();
      if (!this.field_70170_p.field_72995_K && LOTRDimension.getCurrentDimensionWithFallback(this.field_70170_p) == LOTRDimension.UTUMNO) {
         if (this.timeUntilFadeOut > 0) {
            --this.timeUntilFadeOut;
            if (this.timeUntilFadeOut <= 0) {
               this.setFadeoutTick(60);
            }
         }

         if (this.getFadeoutTick() > 0) {
            this.setFadeoutTick(this.getFadeoutTick() - 1);
            if (this.getFadeoutTick() <= 0) {
               this.func_70106_y();
            }
         }
      }

   }

   public float getAlignmentBonus() {
      return 0.0F;
   }

   protected void func_70628_a(boolean flag, int i) {
      super.func_70628_a(flag, i);
      int bones = this.field_70146_Z.nextInt(2) + this.field_70146_Z.nextInt(i + 1);

      for(int l = 0; l < bones; ++l) {
         this.func_145779_a(Items.field_151103_aS, 1);
      }

   }

   public boolean canTradeWith(EntityPlayer entityplayer) {
      return this.isFriendlyAndAligned(entityplayer) && LOTRDimension.getCurrentDimensionWithFallback(this.field_70170_p) != LOTRDimension.UTUMNO;
   }

   public void onPlayerTrade(EntityPlayer entityplayer, LOTRTradeEntries.TradeType type, ItemStack itemstack) {
      LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.tradeScrapTrader);
   }

   public String getSpeechBank(EntityPlayer entityplayer) {
      if (this.isFriendlyAndAligned(entityplayer)) {
         return LOTRDimension.getCurrentDimensionWithFallback(this.field_70170_p) == LOTRDimension.UTUMNO ? "misc/scrapTrader/utumno" : "misc/scrapTrader/friendly";
      } else {
         return "misc/scrapTrader/hostile";
      }
   }

   public String getSmithSpeechBank() {
      return "misc/scrapTrader/smith";
   }
}
