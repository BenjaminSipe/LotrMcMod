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
import lotr.common.world.biome.LOTRBiomeGenAngmar;
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
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class LOTREntityAngmarHillman extends LOTREntityMan {
   private static ItemStack[] weapons;
   private static ItemStack[] spears;

   public LOTREntityAngmarHillman(World world) {
      super(world);
      this.func_70105_a(0.6F, 1.8F);
      this.func_70661_as().func_75491_a(true);
      this.func_70661_as().func_75498_b(true);
      this.field_70714_bg.func_75776_a(0, new EntityAISwimming(this));
      this.field_70714_bg.func_75776_a(1, new LOTREntityAIHiredRemainStill(this));
      this.field_70714_bg.func_75776_a(2, this.getHillmanAttackAI());
      this.field_70714_bg.func_75776_a(3, new LOTREntityAIFollowHiringPlayer(this));
      this.field_70714_bg.func_75776_a(4, new EntityAIOpenDoor(this, true));
      this.field_70714_bg.func_75776_a(5, new EntityAIWander(this, 1.0D));
      this.field_70714_bg.func_75776_a(6, new LOTREntityAIEat(this, LOTRFoods.RHUDAUR, 8000));
      this.field_70714_bg.func_75776_a(6, new LOTREntityAIDrink(this, LOTRFoods.RHUDAUR_DRINK, 8000));
      this.field_70714_bg.func_75776_a(7, new EntityAIWatchClosest2(this, EntityPlayer.class, 8.0F, 0.02F));
      this.field_70714_bg.func_75776_a(7, new EntityAIWatchClosest2(this, LOTREntityNPC.class, 5.0F, 0.02F));
      this.field_70714_bg.func_75776_a(8, new EntityAIWatchClosest(this, EntityLiving.class, 8.0F, 0.02F));
      this.field_70714_bg.func_75776_a(9, new EntityAILookIdle(this));
      this.addTargetTasks(true);
   }

   public EntityAIBase getHillmanAttackAI() {
      return new LOTREntityAIAttackOnCollide(this, 1.5D, false);
   }

   public void setupNPCGender() {
      this.familyInfo.setMale(this.field_70146_Z.nextBoolean());
   }

   public void setupNPCName() {
      this.familyInfo.setName(LOTRNames.getRhudaurName(this.field_70146_Z, this.familyInfo.isMale()));
   }

   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(20.0D);
      this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.2D);
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      int i = this.field_70146_Z.nextInt(weapons.length);
      this.npcItemsInv.setMeleeWeapon(weapons[i].func_77946_l());
      if (this.field_70146_Z.nextInt(8) == 0) {
         this.npcItemsInv.setSpearBackup(this.npcItemsInv.getMeleeWeapon());
         i = this.field_70146_Z.nextInt(spears.length);
         this.npcItemsInv.setMeleeWeapon(spears[i].func_77946_l());
      }

      this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
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
      return LOTRFaction.ANGMAR;
   }

   public String getNPCName() {
      return this.familyInfo.getName();
   }

   protected void func_70628_a(boolean flag, int i) {
      super.func_70628_a(flag, i);
      int bones = this.field_70146_Z.nextInt(2) + this.field_70146_Z.nextInt(i + 1);

      for(int l = 0; l < bones; ++l) {
         this.func_145779_a(Items.field_151103_aS, 1);
      }

      this.dropHillmanItems(flag, i);
   }

   public void dropHillmanItems(boolean flag, int i) {
      if (this.field_70146_Z.nextInt(5) == 0) {
         this.dropChestContents(LOTRChestContents.ANGMAR_HILLMAN_HOUSE, 1, 2 + i);
      }

   }

   protected LOTRAchievement getKillAchievement() {
      return LOTRAchievement.killAngmarHillman;
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
      if (biome instanceof LOTRBiomeGenAngmar) {
         f += 20.0F;
      }

      return f;
   }

   public String getSpeechBank(EntityPlayer entityplayer) {
      if (this.isFriendlyAndAligned(entityplayer)) {
         return this.hiredNPCInfo.getHiringPlayer() == entityplayer ? "angmar/hillman/hired" : "angmar/hillman/friendly";
      } else {
         return "angmar/hillman/hostile";
      }
   }

   public LOTRMiniQuest createMiniQuest() {
      return LOTRMiniQuestFactory.ANGMAR_HILLMAN.createQuest(this);
   }

   public LOTRMiniQuestFactory getBountyHelpSpeechDir() {
      return LOTRMiniQuestFactory.ANGMAR_HILLMAN;
   }

   static {
      weapons = new ItemStack[]{new ItemStack(Items.field_151041_m), new ItemStack(Items.field_151053_p), new ItemStack(Items.field_151052_q), new ItemStack(Items.field_151049_t), new ItemStack(Items.field_151040_l), new ItemStack(Items.field_151036_c), new ItemStack(LOTRMod.daggerIron), new ItemStack(LOTRMod.battleaxeIron), new ItemStack(LOTRMod.pikeIron), new ItemStack(LOTRMod.swordBronze), new ItemStack(LOTRMod.axeBronze), new ItemStack(LOTRMod.daggerBronze), new ItemStack(LOTRMod.battleaxeBronze), new ItemStack(LOTRMod.spearIron), new ItemStack(LOTRMod.spearBronze), new ItemStack(LOTRMod.spearStone)};
      spears = new ItemStack[]{new ItemStack(LOTRMod.spearIron), new ItemStack(LOTRMod.spearBronze), new ItemStack(LOTRMod.spearStone)};
   }
}
