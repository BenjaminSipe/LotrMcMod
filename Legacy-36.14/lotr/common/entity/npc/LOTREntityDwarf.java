package lotr.common.entity.npc;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import lotr.common.entity.ai.LOTREntityAIDrink;
import lotr.common.entity.ai.LOTREntityAIEat;
import lotr.common.entity.ai.LOTREntityAIFollowHiringPlayer;
import lotr.common.entity.ai.LOTREntityAIHiredRemainStill;
import lotr.common.entity.ai.LOTREntityAINPCAvoidEvilPlayer;
import lotr.common.entity.ai.LOTREntityAINPCFollowParent;
import lotr.common.entity.ai.LOTREntityAINPCFollowSpouse;
import lotr.common.entity.ai.LOTREntityAINPCMarry;
import lotr.common.entity.ai.LOTREntityAINPCMate;
import lotr.common.fac.LOTRFaction;
import lotr.common.quest.LOTRMiniQuest;
import lotr.common.quest.LOTRMiniQuestFactory;
import lotr.common.world.biome.LOTRBiomeGenBlueMountains;
import lotr.common.world.biome.LOTRBiomeGenErebor;
import lotr.common.world.biome.LOTRBiomeGenIronHills;
import lotr.common.world.biome.LOTRBiomeGenRedMountains;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
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
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class LOTREntityDwarf extends LOTREntityNPC {
   public LOTREntityDwarf(World world) {
      super(world);
      this.func_70105_a(0.5F, 1.5F);
      this.func_70661_as().func_75491_a(true);
      this.func_70661_as().func_75498_b(true);
      this.field_70714_bg.func_75776_a(0, new EntityAISwimming(this));
      this.field_70714_bg.func_75776_a(1, new LOTREntityAIHiredRemainStill(this));
      this.field_70714_bg.func_75776_a(2, new LOTREntityAINPCAvoidEvilPlayer(this, 8.0F, 1.5D, 1.8D));
      this.field_70714_bg.func_75776_a(3, this.getDwarfAttackAI());
      this.field_70714_bg.func_75776_a(4, new LOTREntityAIFollowHiringPlayer(this));
      this.field_70714_bg.func_75776_a(5, new LOTREntityAINPCMarry(this, 1.3D));
      this.field_70714_bg.func_75776_a(6, new LOTREntityAINPCMate(this, 1.3D));
      this.field_70714_bg.func_75776_a(7, new LOTREntityAINPCFollowParent(this, 1.4D));
      this.field_70714_bg.func_75776_a(8, new LOTREntityAINPCFollowSpouse(this, 1.1D));
      this.field_70714_bg.func_75776_a(9, new EntityAIOpenDoor(this, true));
      this.field_70714_bg.func_75776_a(10, new EntityAIWander(this, 1.0D));
      this.field_70714_bg.func_75776_a(11, new LOTREntityAIEat(this, this.getDwarfFoods(), 6000));
      this.field_70714_bg.func_75776_a(11, new LOTREntityAIDrink(this, LOTRFoods.DWARF_DRINK, 6000));
      this.field_70714_bg.func_75776_a(12, new EntityAIWatchClosest2(this, EntityPlayer.class, 8.0F, 0.02F));
      this.field_70714_bg.func_75776_a(12, new EntityAIWatchClosest2(this, LOTREntityNPC.class, 5.0F, 0.02F));
      this.field_70714_bg.func_75776_a(13, new EntityAIWatchClosest(this, EntityLiving.class, 8.0F, 0.02F));
      this.field_70714_bg.func_75776_a(14, new EntityAILookIdle(this));
      this.addTargetTasks(true);
      this.familyInfo.marriageEntityClass = LOTREntityDwarf.class;
      this.familyInfo.marriageRing = LOTRMod.dwarvenRing;
      this.familyInfo.marriageAlignmentRequired = 200.0F;
      this.familyInfo.marriageAchievement = LOTRAchievement.marryDwarf;
      this.familyInfo.potentialMaxChildren = 3;
      this.familyInfo.timeToMature = 72000;
      this.familyInfo.breedingDelay = 48000;
   }

   protected EntityAIBase getDwarfAttackAI() {
      return new LOTREntityAIAttackOnCollide(this, 1.4D, false);
   }

   protected LOTRFoods getDwarfFoods() {
      return LOTRFoods.DWARF;
   }

   public void setupNPCGender() {
      this.familyInfo.setMale(true);
   }

   public void setupNPCName() {
      this.familyInfo.setName(LOTRNames.getDwarfName(this.field_70146_Z, this.familyInfo.isMale()));
   }

   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(26.0D);
      this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.2D);
   }

   public IEntityLivingData initCreatureForHire(IEntityLivingData data) {
      data = super.initCreatureForHire(data);
      data = this.func_110161_a(data);
      if (this.getClass() == this.familyInfo.marriageEntityClass && this.field_70146_Z.nextInt(3) == 0) {
         this.familyInfo.setMale(false);
         this.setupNPCName();
      }

      return data;
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerDwarven));
      this.npcItemsInv.setIdleItem((ItemStack)null);
      return data;
   }

   public LOTRFaction getFaction() {
      return LOTRFaction.DURINS_FOLK;
   }

   public String getNPCName() {
      return this.familyInfo.getName();
   }

   public void func_70037_a(NBTTagCompound nbt) {
      super.func_70037_a(nbt);
      if (nbt.func_74764_b("DwarfName")) {
         this.familyInfo.setName(nbt.func_74779_i("DwarfName"));
      }

   }

   public void createNPCChildName(LOTREntityNPC maleParent, LOTREntityNPC femaleParent) {
      this.familyInfo.setName(LOTRNames.getDwarfChildNameForParent(this.field_70146_Z, this.familyInfo.isMale(), (LOTREntityDwarf)maleParent));
   }

   public boolean func_70085_c(EntityPlayer entityplayer) {
      return this.familyInfo.interact(entityplayer) ? true : super.func_70085_c(entityplayer);
   }

   public void onAttackModeChange(LOTREntityNPC.AttackMode mode, boolean mounted) {
      if (mode == LOTREntityNPC.AttackMode.IDLE) {
         this.func_70062_b(0, this.npcItemsInv.getIdleItem());
      } else {
         this.func_70062_b(0, this.npcItemsInv.getMeleeWeapon());
      }

   }

   protected LOTRAchievement getKillAchievement() {
      return LOTRAchievement.killDwarf;
   }

   public float getAlignmentBonus() {
      return 1.0F;
   }

   protected void func_70628_a(boolean flag, int i) {
      super.func_70628_a(flag, i);
      int bones = this.field_70146_Z.nextInt(2) + this.field_70146_Z.nextInt(i + 1);

      int rareDropChance;
      for(rareDropChance = 0; rareDropChance < bones; ++rareDropChance) {
         this.func_145779_a(LOTRMod.dwarfBone, 1);
      }

      if (this.field_70146_Z.nextInt(4) == 0) {
         this.dropChestContents(this.getLarderDrops(), 1, 2 + i);
      }

      if (this.field_70146_Z.nextInt(8) == 0) {
         this.dropChestContents(this.getGenericDrops(), 1, 2 + i);
      }

      if (flag) {
         rareDropChance = 20 - i * 4;
         rareDropChance = Math.max(rareDropChance, 1);
         int mithrilBookChance;
         if (this.field_70146_Z.nextInt(rareDropChance) == 0) {
            mithrilBookChance = this.field_70146_Z.nextInt(4);
            switch(mithrilBookChance) {
            case 0:
               this.func_70099_a(new ItemStack(Items.field_151042_j), 0.0F);
               break;
            case 1:
               this.func_70099_a(new ItemStack(this.getDwarfSteelDrop()), 0.0F);
               break;
            case 2:
               this.func_70099_a(new ItemStack(Items.field_151074_bl, 1 + this.field_70146_Z.nextInt(3)), 0.0F);
               break;
            case 3:
               this.func_70099_a(new ItemStack(LOTRMod.silverNugget, 1 + this.field_70146_Z.nextInt(3)), 0.0F);
            }
         }

         mithrilBookChance = 40 - i * 5;
         mithrilBookChance = Math.max(mithrilBookChance, 1);
         if (this.field_70146_Z.nextInt(mithrilBookChance) == 0) {
            this.func_70099_a(new ItemStack(LOTRMod.mithrilBook), 0.0F);
         }
      }

   }

   protected Item getDwarfSteelDrop() {
      return LOTRMod.dwarfSteel;
   }

   protected LOTRChestContents getLarderDrops() {
      return LOTRChestContents.DWARF_HOUSE_LARDER;
   }

   protected LOTRChestContents getGenericDrops() {
      return LOTRChestContents.DWARVEN_TOWER;
   }

   public boolean func_70601_bi() {
      if (super.func_70601_bi()) {
         return this.liftSpawnRestrictions ? true : this.canDwarfSpawnHere();
      } else {
         return false;
      }
   }

   protected boolean canDwarfSpawnHere() {
      int i = MathHelper.func_76128_c(this.field_70165_t);
      int j = MathHelper.func_76128_c(this.field_70121_D.field_72338_b);
      int k = MathHelper.func_76128_c(this.field_70161_v);
      if (this.field_70146_Z.nextInt(200) == 0) {
         return this.canDwarfSpawnAboveGround();
      } else {
         return j < 60 && this.field_70170_p.func_147439_a(i, j - 1, k).func_149688_o() == Material.field_151576_e && !this.field_70170_p.func_72937_j(i, j, k) && this.field_70170_p.func_72972_b(EnumSkyBlock.Block, i, j, k) >= 10;
      }
   }

   protected boolean canDwarfSpawnAboveGround() {
      return true;
   }

   public float func_70783_a(int i, int j, int k) {
      float f = 0.0F;
      BiomeGenBase biome = this.field_70170_p.func_72807_a(i, k);
      if (biome instanceof LOTRBiomeGenIronHills || biome instanceof LOTRBiomeGenErebor || biome instanceof LOTRBiomeGenBlueMountains || biome instanceof LOTRBiomeGenRedMountains) {
         f += 20.0F;
      }

      return f;
   }

   public int func_70641_bl() {
      return 6;
   }

   protected float func_70647_i() {
      float f = super.func_70647_i();
      if (!this.familyInfo.isMale()) {
         f *= 1.4F;
      }

      return f;
   }

   public String func_70621_aR() {
      return "lotr:dwarf.hurt";
   }

   public String func_70673_aS() {
      return "lotr:dwarf.hurt";
   }

   public String getAttackSound() {
      return "lotr:dwarf.attack";
   }

   public void func_70074_a(EntityLivingBase entity) {
      super.func_70074_a(entity);
      this.func_85030_a("lotr:dwarf.kill", this.func_70599_aP(), this.func_70647_i());
   }

   protected LOTRAchievement getTalkAchievement() {
      return !this.familyInfo.isMale() ? LOTRAchievement.talkDwarfWoman : super.getTalkAchievement();
   }

   public String getSpeechBank(EntityPlayer entityplayer) {
      if (this.isFriendlyAndAligned(entityplayer)) {
         if (this.hiredNPCInfo.getHiringPlayer() == entityplayer) {
            return "dwarf/dwarf/hired";
         } else {
            return this.func_70631_g_() ? "dwarf/child/friendly" : "dwarf/dwarf/friendly";
         }
      } else {
         return this.func_70631_g_() ? "dwarf/child/hostile" : "dwarf/dwarf/hostile";
      }
   }

   public LOTRMiniQuest createMiniQuest() {
      return LOTRMiniQuestFactory.DURIN.createQuest(this);
   }

   public LOTRMiniQuestFactory getBountyHelpSpeechDir() {
      return LOTRMiniQuestFactory.DURIN;
   }

   public void onArtificalSpawn() {
      if (this.getClass() == this.familyInfo.marriageEntityClass) {
         if (this.field_70146_Z.nextInt(3) == 0) {
            this.familyInfo.setMale(false);
            this.setupNPCName();
         }

         if (this.field_70146_Z.nextInt(20) == 0) {
            this.familyInfo.setChild();
         }
      }

   }
}
