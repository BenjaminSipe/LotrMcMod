package lotr.common.entity.npc;

import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import lotr.common.entity.ai.LOTREntityAIDrink;
import lotr.common.entity.ai.LOTREntityAIEat;
import lotr.common.entity.ai.LOTREntityAIFarm;
import lotr.common.entity.ai.LOTREntityAIFollowHiringPlayer;
import lotr.common.entity.ai.LOTREntityAINPCHurtByTarget;
import lotr.common.fac.LOTRFaction;
import lotr.common.world.biome.LOTRBiomeGenNurn;
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
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.IPlantable;

public class LOTREntityNurnSlave extends LOTREntityMan implements LOTRFarmhand {
   private boolean isFree = false;

   public LOTREntityNurnSlave(World world) {
      super(world);
      this.func_70105_a(0.6F, 1.8F);
      this.func_70661_as().func_75491_a(true);
      this.func_70661_as().func_75498_b(true);
      this.field_70714_bg.func_75776_a(0, new EntityAISwimming(this));
      this.field_70714_bg.func_75776_a(1, new LOTREntityAIAttackOnCollide(this, 1.3D, false));
      this.field_70714_bg.func_75776_a(2, new LOTREntityAIFollowHiringPlayer(this));
      this.field_70714_bg.func_75776_a(3, new LOTREntityAIFarm(this, 1.0D, 1.0F));
      this.field_70714_bg.func_75776_a(4, new EntityAIOpenDoor(this, true));
      this.field_70714_bg.func_75776_a(5, new EntityAIWander(this, 1.0D));
      this.field_70714_bg.func_75776_a(6, new LOTREntityAIEat(this, LOTRFoods.NURN_SLAVE, 12000));
      this.field_70714_bg.func_75776_a(6, new LOTREntityAIDrink(this, LOTRFoods.NURN_SLAVE_DRINK, 12000));
      this.field_70714_bg.func_75776_a(7, new EntityAIWatchClosest2(this, EntityPlayer.class, 8.0F, 0.02F));
      this.field_70714_bg.func_75776_a(7, new EntityAIWatchClosest2(this, LOTREntityNPC.class, 5.0F, 0.02F));
      this.field_70714_bg.func_75776_a(8, new EntityAIWatchClosest(this, EntityLiving.class, 8.0F, 0.02F));
      this.field_70714_bg.func_75776_a(9, new EntityAILookIdle(this));
      this.field_70715_bh.field_75782_a.clear();
      this.field_70715_bh.func_75776_a(1, new LOTREntityAINPCHurtByTarget(this, false));
   }

   public void setupNPCGender() {
      this.familyInfo.setMale(this.field_70146_Z.nextBoolean());
   }

   public void setupNPCName() {
      this.familyInfo.setName(LOTRNames.getGondorName(this.field_70146_Z, this.familyInfo.isMale()));
   }

   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(20.0D);
      this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.2D);
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.hoeOrc));
      this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
      return data;
   }

   public void onAttackModeChange(LOTREntityNPC.AttackMode mode, boolean mounted) {
      if (mode == LOTREntityNPC.AttackMode.IDLE) {
         this.func_70062_b(0, this.npcItemsInv.getIdleItem());
      } else {
         this.func_70062_b(0, this.npcItemsInv.getMeleeWeapon());
      }

   }

   public IPlantable getUnhiredSeeds() {
      return (IPlantable)Items.field_151014_N;
   }

   public LOTRFaction getFaction() {
      return LOTRFaction.GONDOR;
   }

   public LOTRFaction getHiringFaction() {
      return !this.isFree ? LOTRFaction.MORDOR : super.getHiringFaction();
   }

   public boolean canBeFreelyTargetedBy(EntityLiving attacker) {
      return !this.isFree && !LOTRMod.getNPCFaction(attacker).isBadRelation(this.getHiringFaction()) ? false : super.canBeFreelyTargetedBy(attacker);
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

   }

   public float func_70783_a(int i, int j, int k) {
      float f = 0.0F;
      BiomeGenBase biome = this.field_70170_p.func_72807_a(i, k);
      if (!this.isFree && biome instanceof LOTRBiomeGenNurn) {
         f += 20.0F;
      }

      return f;
   }

   public String getSpeechBank(EntityPlayer entityplayer) {
      if (this.isFree) {
         if (this.isFriendlyAndAligned(entityplayer)) {
            return this.hiredNPCInfo.getHiringPlayer() == entityplayer ? "mordor/nurnSlave/free_hired" : "mordor/nurnSlave/free_friendly";
         } else {
            return "mordor/nurnSlave/free_hostile";
         }
      } else {
         return this.hiredNPCInfo.getHiringPlayer() == entityplayer ? "mordor/nurnSlave/hired" : "mordor/nurnSlave/neutral";
      }
   }
}
