package lotr.common.entity.npc;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRFoods;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.entity.ai.LOTREntityAIAvoidHuorn;
import lotr.common.entity.ai.LOTREntityAIDrink;
import lotr.common.entity.ai.LOTREntityAIEat;
import lotr.common.entity.ai.LOTREntityAIHobbitChildFollowGoodPlayer;
import lotr.common.entity.ai.LOTREntityAIHobbitSmoke;
import lotr.common.entity.ai.LOTREntityAINPCAvoidEvilPlayer;
import lotr.common.entity.ai.LOTREntityAINPCFollowParent;
import lotr.common.entity.ai.LOTREntityAINPCFollowSpouse;
import lotr.common.entity.ai.LOTREntityAINPCMarry;
import lotr.common.entity.ai.LOTREntityAINPCMate;
import lotr.common.fac.LOTRFaction;
import lotr.common.quest.LOTRMiniQuest;
import lotr.common.quest.LOTRMiniQuestFactory;
import lotr.common.world.biome.LOTRBiomeGenShire;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityAIWatchClosest2;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class LOTREntityHobbit extends LOTREntityMan {
   public LOTREntityHobbit(World world) {
      super(world);
      this.func_70105_a(0.45F, 1.2F);
      this.func_70661_as().func_75491_a(true);
      this.func_70661_as().func_75498_b(true);
      this.field_70714_bg.func_75776_a(0, new EntityAISwimming(this));
      this.field_70714_bg.func_75776_a(1, new EntityAIAvoidEntity(this, LOTREntityOrc.class, 12.0F, 1.5D, 1.8D));
      this.field_70714_bg.func_75776_a(1, new EntityAIAvoidEntity(this, LOTREntityWarg.class, 12.0F, 1.5D, 1.8D));
      this.field_70714_bg.func_75776_a(1, new EntityAIAvoidEntity(this, LOTREntityTroll.class, 12.0F, 1.5D, 1.8D));
      this.field_70714_bg.func_75776_a(1, new EntityAIAvoidEntity(this, LOTREntitySpiderBase.class, 12.0F, 1.5D, 1.8D));
      this.field_70714_bg.func_75776_a(1, new EntityAIAvoidEntity(this, LOTREntityRuffianBrute.class, 8.0F, 1.0D, 1.5D));
      this.field_70714_bg.func_75776_a(1, new LOTREntityAIAvoidHuorn(this, 12.0F, 1.5D, 1.8D));
      this.field_70714_bg.func_75776_a(2, new EntityAIPanic(this, 1.6D));
      this.field_70714_bg.func_75776_a(3, new LOTREntityAINPCAvoidEvilPlayer(this, 8.0F, 1.5D, 1.8D));
      this.field_70714_bg.func_75776_a(4, new LOTREntityAIHobbitChildFollowGoodPlayer(this, 12.0F, 1.5D));
      this.field_70714_bg.func_75776_a(5, new LOTREntityAINPCMarry(this, 1.3D));
      this.field_70714_bg.func_75776_a(6, new LOTREntityAINPCMate(this, 1.3D));
      this.field_70714_bg.func_75776_a(7, new LOTREntityAINPCFollowParent(this, 1.4D));
      this.field_70714_bg.func_75776_a(8, new LOTREntityAINPCFollowSpouse(this, 1.1D));
      this.field_70714_bg.func_75776_a(9, new EntityAIOpenDoor(this, true));
      this.field_70714_bg.func_75776_a(10, new EntityAIWander(this, 1.1D));
      this.field_70714_bg.func_75776_a(11, new LOTREntityAIEat(this, this.getHobbitFoods(), 3000));
      this.field_70714_bg.func_75776_a(11, new LOTREntityAIDrink(this, this.getHobbitDrinks(), 3000));
      this.field_70714_bg.func_75776_a(11, new LOTREntityAIHobbitSmoke(this, 4000));
      this.field_70714_bg.func_75776_a(12, new EntityAIWatchClosest2(this, EntityPlayer.class, 8.0F, 0.05F));
      this.field_70714_bg.func_75776_a(12, new EntityAIWatchClosest2(this, LOTREntityNPC.class, 5.0F, 0.05F));
      this.field_70714_bg.func_75776_a(13, new EntityAIWatchClosest(this, EntityLiving.class, 8.0F, 0.02F));
      this.field_70714_bg.func_75776_a(14, new EntityAILookIdle(this));
      this.familyInfo.marriageEntityClass = LOTREntityHobbit.class;
      this.familyInfo.marriageRing = LOTRMod.hobbitRing;
      this.familyInfo.marriageAlignmentRequired = 100.0F;
      this.familyInfo.marriageAchievement = LOTRAchievement.marryHobbit;
      this.familyInfo.potentialMaxChildren = 4;
      this.familyInfo.timeToMature = 48000;
      this.familyInfo.breedingDelay = 24000;
   }

   protected LOTRFoods getHobbitFoods() {
      return LOTRFoods.HOBBIT;
   }

   protected LOTRFoods getHobbitDrinks() {
      return LOTRFoods.HOBBIT_DRINK;
   }

   public void setupNPCGender() {
      this.familyInfo.setMale(this.field_70146_Z.nextBoolean());
   }

   public void setupNPCName() {
      this.familyInfo.setName(LOTRNames.getHobbitName(this.field_70146_Z, this.familyInfo.isMale()));
   }

   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(16.0D);
      this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.2D);
   }

   protected void onAttackModeChange(LOTREntityNPC.AttackMode mode, boolean mounted) {
      if (mode == LOTREntityNPC.AttackMode.IDLE) {
         this.func_70062_b(0, this.npcItemsInv.getIdleItem());
      } else {
         this.func_70062_b(0, this.npcItemsInv.getMeleeWeapon());
      }

   }

   public LOTRFaction getFaction() {
      return LOTRFaction.HOBBIT;
   }

   public String getNPCName() {
      return this.familyInfo.getName();
   }

   public void changeNPCNameForMarriage(LOTREntityNPC spouse) {
      if (this.familyInfo.isMale()) {
         LOTRNames.changeHobbitSurnameForMarriage(this, (LOTREntityHobbit)spouse);
      } else if (spouse.familyInfo.isMale()) {
         LOTRNames.changeHobbitSurnameForMarriage((LOTREntityHobbit)spouse, this);
      }

   }

   public void createNPCChildName(LOTREntityNPC maleParent, LOTREntityNPC femaleParent) {
      this.familyInfo.setName(LOTRNames.getHobbitChildNameForParent(this.field_70146_Z, this.familyInfo.isMale(), (LOTREntityHobbit)maleParent));
   }

   public boolean func_70085_c(EntityPlayer entityplayer) {
      return this.familyInfo.interact(entityplayer) ? true : super.func_70085_c(entityplayer);
   }

   public boolean speakTo(EntityPlayer entityplayer) {
      boolean flag = super.speakTo(entityplayer);
      if (flag && this.isDrunkard() && entityplayer.func_82165_m(Potion.field_76431_k.field_76415_H)) {
         LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.speakToDrunkard);
      }

      return flag;
   }

   protected LOTRAchievement getKillAchievement() {
      return LOTRAchievement.killHobbit;
   }

   public float getAlignmentBonus() {
      return 1.0F;
   }

   protected void func_70628_a(boolean flag, int i) {
      super.func_70628_a(flag, i);
      int bones = this.field_70146_Z.nextInt(2) + this.field_70146_Z.nextInt(i + 1);

      for(int l = 0; l < bones; ++l) {
         this.func_145779_a(LOTRMod.hobbitBone, 1);
      }

      this.dropHobbitItems(flag, i);
   }

   protected void dropHobbitItems(boolean flag, int i) {
      if (this.field_70146_Z.nextInt(8) == 0) {
         this.dropChestContents(LOTRChestContents.HOBBIT_HOLE_STUDY, 1, 1 + i);
      }

      if (this.field_70146_Z.nextInt(4) == 0) {
         this.dropChestContents(LOTRChestContents.HOBBIT_HOLE_LARDER, 1, 2 + i);
      }

   }

   protected int func_70693_a(EntityPlayer entityplayer) {
      return 1 + this.field_70146_Z.nextInt(3);
   }

   public boolean func_70601_bi() {
      if (super.func_70601_bi()) {
         if (this.liftSpawnRestrictions) {
            return true;
         } else {
            int i = MathHelper.func_76128_c(this.field_70165_t);
            int j = MathHelper.func_76128_c(this.field_70121_D.field_72338_b);
            int k = MathHelper.func_76128_c(this.field_70161_v);
            return j > 62 && this.field_70170_p.func_147439_a(i, j - 1, k) == this.field_70170_p.func_72807_a(i, k).field_76752_A;
         }
      } else {
         return false;
      }
   }

   public float func_70783_a(int i, int j, int k) {
      float f = 0.0F;
      BiomeGenBase biome = this.field_70170_p.func_72807_a(i, k);
      if (biome instanceof LOTRBiomeGenShire) {
         f += 20.0F;
      }

      return f;
   }

   public String getSpeechBank(EntityPlayer entityplayer) {
      if (this.isDrunkard()) {
         return "hobbit/drunkard/neutral";
      } else if (this.isFriendlyAndAligned(entityplayer)) {
         return this.func_70631_g_() ? "hobbit/child/friendly" : "hobbit/hobbit/friendly";
      } else {
         return this.func_70631_g_() ? "hobbit/child/hostile" : "hobbit/hobbit/hostile";
      }
   }

   public LOTRMiniQuest createMiniQuest() {
      return LOTRMiniQuestFactory.HOBBIT.createQuest(this);
   }

   public LOTRMiniQuestFactory getBountyHelpSpeechDir() {
      return LOTRMiniQuestFactory.HOBBIT;
   }

   public void onArtificalSpawn() {
      if (this.getClass() == this.familyInfo.marriageEntityClass && this.field_70146_Z.nextInt(10) == 0) {
         this.familyInfo.setChild();
      }

   }
}
