package lotr.common.entity.npc;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRMod;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import lotr.common.fac.LOTRFaction;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityUtumnoOrc extends LOTREntityOrc {
   public LOTREntityUtumnoOrc(World world) {
      super(world);
      this.func_70105_a(0.6F, 1.8F);
      this.isWeakOrc = false;
   }

   public EntityAIBase createOrcAttackAI() {
      return new LOTREntityAIAttackOnCollide(this, 1.5D, true);
   }

   public void setupNPCName() {
      if (this.field_70146_Z.nextInt(5) == 0) {
         this.familyInfo.setName(LOTRNames.getSindarinOrQuenyaName(this.field_70146_Z, this.field_70146_Z.nextBoolean()));
      } else {
         this.familyInfo.setName(LOTRNames.getOrcName(this.field_70146_Z));
      }

   }

   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(24.0D);
      this.func_110148_a(SharedMonsterAttributes.field_111265_b).func_111128_a(24.0D);
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      int i = this.field_70146_Z.nextInt(6);
      if (i != 0 && i != 1) {
         if (i == 2) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.battleaxeUtumno));
         } else if (i == 3) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerUtumno));
         } else if (i == 4) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerUtumnoPoisoned));
         } else if (i == 5) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.hammerUtumno));
         }
      } else {
         this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.swordUtumno));
      }

      if (this.field_70146_Z.nextInt(6) == 0) {
         this.npcItemsInv.setSpearBackup(this.npcItemsInv.getMeleeWeapon());
         this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.spearUtumno));
      }

      this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
      this.func_70062_b(1, new ItemStack(LOTRMod.bootsUtumno));
      this.func_70062_b(2, new ItemStack(LOTRMod.legsUtumno));
      this.func_70062_b(3, new ItemStack(LOTRMod.bodyUtumno));
      if (this.field_70146_Z.nextInt(10) != 0) {
         this.func_70062_b(4, new ItemStack(LOTRMod.helmetUtumno));
      }

      return data;
   }

   public LOTRFaction getFaction() {
      return LOTRFaction.UTUMNO;
   }

   protected LOTRAchievement getKillAchievement() {
      return LOTRAchievement.killUtumnoOrc;
   }

   protected float func_70647_i() {
      return super.func_70647_i() * 0.65F;
   }

   public boolean canOrcSkirmish() {
      return false;
   }

   public String getSpeechBank(EntityPlayer entityplayer) {
      return "utumno/orc/hostile";
   }
}
