package lotr.common.entity.npc;

import java.util.Iterator;
import java.util.List;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.LOTRShields;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import lotr.common.fac.LOTRFaction;
import lotr.common.quest.LOTRMiniQuest;
import lotr.common.quest.LOTRMiniQuestFactory;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class LOTREntityUrukHai extends LOTREntityOrc {
   public LOTREntityUrukHai(World world) {
      super(world);
      this.func_70105_a(0.6F, 1.8F);
      this.isWeakOrc = false;
      this.npcShield = LOTRShields.ALIGNMENT_URUK_HAI;
   }

   public EntityAIBase createOrcAttackAI() {
      return new LOTREntityAIAttackOnCollide(this, 1.4D, false);
   }

   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(26.0D);
      this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.22D);
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      int i = this.field_70146_Z.nextInt(8);
      if (i != 0 && i != 1) {
         if (i != 2 && i != 3) {
            if (i == 4) {
               this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.battleaxeUruk));
            } else if (i == 5) {
               this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerUruk));
            } else if (i == 6) {
               this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerUrukPoisoned));
            } else if (i == 7) {
               this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.hammerUruk));
            }
         } else {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.pikeUruk));
         }
      } else {
         this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.scimitarUruk));
      }

      if (this.field_70146_Z.nextInt(6) == 0) {
         this.npcItemsInv.setSpearBackup(this.npcItemsInv.getMeleeWeapon());
         this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.spearUruk));
      }

      this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
      this.func_70062_b(1, new ItemStack(LOTRMod.bootsUruk));
      this.func_70062_b(2, new ItemStack(LOTRMod.legsUruk));
      this.func_70062_b(3, new ItemStack(LOTRMod.bodyUruk));
      if (this.field_70146_Z.nextInt(10) != 0) {
         this.func_70062_b(4, new ItemStack(LOTRMod.helmetUruk));
      }

      return data;
   }

   protected void dropOrcItems(boolean flag, int i) {
      if (this.field_70146_Z.nextInt(6) == 0) {
         this.dropChestContents(LOTRChestContents.URUK_TENT, 1, 2 + i);
      }

   }

   public void func_70645_a(DamageSource damagesource) {
      super.func_70645_a(damagesource);
      if (!this.field_70170_p.field_72995_K && damagesource.func_76346_g() instanceof EntityPlayer) {
         EntityPlayer entityplayer = (EntityPlayer)damagesource.func_76346_g();
         double range = 12.0D;
         List nearbySpawners = this.field_70170_p.func_72872_a(LOTREntityNPCRespawner.class, this.field_70121_D.func_72314_b(range, range, range));
         Iterator var6 = nearbySpawners.iterator();

         while(var6.hasNext()) {
            Object obj = var6.next();
            LOTREntityNPCRespawner spawner = (LOTREntityNPCRespawner)obj;
            if (spawner.spawnClass1 != null && LOTREntityUrukHai.class.isAssignableFrom(spawner.spawnClass1)) {
               LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.raidUrukCamp);
               break;
            }
         }
      }

   }

   public LOTRFaction getFaction() {
      return LOTRFaction.ISENGARD;
   }

   public float getAlignmentBonus() {
      return 2.0F;
   }

   protected LOTRAchievement getKillAchievement() {
      return LOTRAchievement.killUrukHai;
   }

   protected float func_70647_i() {
      return super.func_70647_i() * 0.75F;
   }

   public boolean canOrcSkirmish() {
      return false;
   }

   public String getSpeechBank(EntityPlayer entityplayer) {
      if (this.isFriendlyAndAligned(entityplayer)) {
         if (this.hiredNPCInfo.getHiringPlayer() == entityplayer) {
            return "isengard/orc/hired";
         } else {
            return LOTRLevelData.getData(entityplayer).getAlignment(this.getFaction()) >= 100.0F ? "isengard/orc/friendly" : "isengard/orc/neutral";
         }
      } else {
         return "isengard/orc/hostile";
      }
   }

   public LOTRMiniQuest createMiniQuest() {
      return LOTRMiniQuestFactory.ISENGARD.createQuest(this);
   }

   public LOTRMiniQuestFactory getBountyHelpSpeechDir() {
      return LOTRMiniQuestFactory.ISENGARD;
   }
}
