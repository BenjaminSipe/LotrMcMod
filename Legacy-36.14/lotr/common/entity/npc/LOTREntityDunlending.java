package lotr.common.entity.npc;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import lotr.common.entity.ai.LOTREntityAIDrink;
import lotr.common.entity.ai.LOTREntityAIEat;
import lotr.common.entity.ai.LOTREntityAIFollowHiringPlayer;
import lotr.common.entity.ai.LOTREntityAIHiredRemainStill;
import lotr.common.fac.LOTRFaction;
import lotr.common.quest.LOTRMiniQuest;
import lotr.common.quest.LOTRMiniQuestFactory;
import lotr.common.world.biome.LOTRBiomeGenAdornland;
import lotr.common.world.biome.LOTRBiomeGenDunland;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityAIWatchClosest2;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class LOTREntityDunlending extends LOTREntityMan {
   public LOTREntityDunlending(World world) {
      super(world);
      this.func_70105_a(0.6F, 1.8F);
      this.func_70661_as().func_75491_a(true);
      this.func_70661_as().func_75498_b(true);
      this.field_70714_bg.func_75776_a(0, new EntityAISwimming(this));
      this.field_70714_bg.func_75776_a(1, new LOTREntityAIHiredRemainStill(this));
      this.field_70714_bg.func_75776_a(2, this.getDunlendingAttackAI());
      this.field_70714_bg.func_75776_a(3, new LOTREntityAIFollowHiringPlayer(this));
      this.field_70714_bg.func_75776_a(4, new EntityAIOpenDoor(this, true));
      this.field_70714_bg.func_75776_a(5, new EntityAIWander(this, 1.0D));
      this.field_70714_bg.func_75776_a(6, new LOTREntityAIEat(this, LOTRFoods.DUNLENDING, 8000));
      this.field_70714_bg.func_75776_a(6, new LOTREntityAIDrink(this, LOTRFoods.DUNLENDING_DRINK, 8000));
      this.field_70714_bg.func_75776_a(7, new EntityAIWatchClosest2(this, EntityPlayer.class, 8.0F, 0.02F));
      this.field_70714_bg.func_75776_a(7, new EntityAIWatchClosest2(this, LOTREntityNPC.class, 5.0F, 0.02F));
      this.field_70714_bg.func_75776_a(8, new EntityAIWatchClosest(this, EntityLiving.class, 8.0F, 0.02F));
      this.field_70714_bg.func_75776_a(9, new EntityAILookIdle(this));
      this.addTargetTasks(true);
   }

   public EntityAIBase getDunlendingAttackAI() {
      return new LOTREntityAIAttackOnCollide(this, 1.5D, false);
   }

   public void setupNPCGender() {
      this.familyInfo.setMale(this.field_70146_Z.nextBoolean());
   }

   public void setupNPCName() {
      this.familyInfo.setName(LOTRNames.getDunlendingName(this.field_70146_Z, this.familyInfo.isMale()));
   }

   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(20.0D);
      this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.2D);
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      int i = this.field_70146_Z.nextInt(9);
      if (i != 0 && i != 1) {
         if (i != 2 && i != 3) {
            if (i == 4) {
               this.npcItemsInv.setMeleeWeapon(new ItemStack(Items.field_151041_m));
            } else if (i == 5) {
               this.npcItemsInv.setMeleeWeapon(new ItemStack(Items.field_151052_q));
            } else if (i == 6) {
               this.npcItemsInv.setMeleeWeapon(new ItemStack(Items.field_151049_t));
            } else if (i == 7) {
               this.npcItemsInv.setMeleeWeapon(new ItemStack(Items.field_151018_J));
            } else if (i == 8) {
               this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.spearStone));
            }
         } else {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.dunlendingTrident));
         }
      } else {
         this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.dunlendingClub));
      }

      this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
      if (this.field_70146_Z.nextInt(4) == 0) {
         this.func_70062_b(4, new ItemStack(LOTRMod.helmetFur));
      }

      if (this.field_70146_Z.nextInt(10000) == 0) {
         LOTREntityWarg warg = new LOTREntityUrukWargBombardier(this.field_70170_p);
         warg.func_70012_b(this.field_70165_t, this.field_70163_u, this.field_70161_v, this.field_70177_z, 0.0F);
         warg.func_110161_a((IEntityLivingData)null);
         warg.isNPCPersistent = this.isNPCPersistent;
         this.field_70170_p.func_72838_d(warg);
         this.func_70078_a(warg);
         this.func_70062_b(4, new ItemStack(LOTRMod.orcBomb));
         this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.orcBomb));
         this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
      }

      return data;
   }

   protected void onAttackModeChange(LOTREntityNPC.AttackMode mode, boolean mounted) {
      if (mode == LOTREntityNPC.AttackMode.IDLE) {
         this.func_70062_b(0, this.npcItemsInv.getIdleItem());
      } else {
         this.func_70062_b(0, this.npcItemsInv.getMeleeWeapon());
      }

   }

   public LOTRFaction getFaction() {
      return LOTRFaction.DUNLAND;
   }

   public String getNPCName() {
      return this.familyInfo.getName();
   }

   public void func_70037_a(NBTTagCompound nbt) {
      super.func_70037_a(nbt);
      if (nbt.func_74764_b("DunlendingName")) {
         this.familyInfo.setName(nbt.func_74779_i("DunlendingName"));
      }

   }

   protected void func_70628_a(boolean flag, int i) {
      super.func_70628_a(flag, i);
      int bones = this.field_70146_Z.nextInt(2) + this.field_70146_Z.nextInt(i + 1);

      for(int l = 0; l < bones; ++l) {
         this.func_145779_a(Items.field_151103_aS, 1);
      }

      this.dropDunlendingItems(flag, i);
   }

   public void dropDunlendingItems(boolean flag, int i) {
      if (this.field_70146_Z.nextInt(5) == 0) {
         this.dropChestContents(LOTRChestContents.DUNLENDING_HOUSE, 1, 2 + i);
      }

   }

   protected LOTRAchievement getKillAchievement() {
      return LOTRAchievement.killDunlending;
   }

   public float getAlignmentBonus() {
      return 1.0F;
   }

   public boolean func_70601_bi() {
      if (super.func_70601_bi()) {
         if (this.liftSpawnRestrictions) {
            return true;
         }

         int i = MathHelper.func_76128_c(this.field_70165_t);
         int j = MathHelper.func_76128_c(this.field_70121_D.field_72338_b);
         int k = MathHelper.func_76128_c(this.field_70161_v);
         BiomeGenBase biome = this.field_70170_p.func_72807_a(i, k);
         if (j > 62 && this.field_70170_p.func_147439_a(i, j - 1, k) == biome.field_76752_A) {
            return true;
         }
      }

      return false;
   }

   public float func_70783_a(int i, int j, int k) {
      float f = 0.0F;
      BiomeGenBase biome = this.field_70170_p.func_72807_a(i, k);
      if (biome instanceof LOTRBiomeGenDunland || biome instanceof LOTRBiomeGenAdornland) {
         f += 20.0F;
      }

      return f;
   }

   public String getSpeechBank(EntityPlayer entityplayer) {
      if (this.isDrunkard()) {
         return "dunlending/drunkard/neutral";
      } else if (this.isFriendlyAndAligned(entityplayer)) {
         return this.hiredNPCInfo.getHiringPlayer() == entityplayer ? "dunlending/dunlending/hired" : "dunlending/dunlending/friendly";
      } else {
         return "dunlending/dunlending/hostile";
      }
   }

   public LOTRMiniQuest createMiniQuest() {
      return LOTRMiniQuestFactory.DUNLAND.createQuest(this);
   }

   public LOTRMiniQuestFactory getBountyHelpSpeechDir() {
      return LOTRMiniQuestFactory.DUNLAND;
   }
}
