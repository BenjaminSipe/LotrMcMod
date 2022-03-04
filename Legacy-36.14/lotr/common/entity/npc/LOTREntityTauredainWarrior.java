package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import lotr.common.LOTRShields;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityTauredainWarrior extends LOTREntityTauredain {
   public LOTREntityTauredainWarrior(World world) {
      super(world);
      this.addTargetTasks(true);
      this.npcShield = LOTRShields.ALIGNMENT_TAUREDAIN;
   }

   protected EntityAIBase createHaradrimAttackAI() {
      return new LOTREntityAIAttackOnCollide(this, 1.5D, true);
   }

   public void setupNPCGender() {
      this.familyInfo.setMale(true);
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      int i = this.field_70146_Z.nextInt(8);
      if (i != 0 && i != 1 && i != 2) {
         if (i == 3) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerTauredain));
         } else if (i == 4) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerTauredainPoisoned));
         } else if (i == 5) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.hammerTauredain));
         } else if (i == 6) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.battleaxeTauredain));
         } else if (i == 7) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.pikeTauredain));
         }
      } else {
         this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.swordTauredain));
      }

      if (this.field_70146_Z.nextInt(5) == 0) {
         this.npcItemsInv.setSpearBackup(this.npcItemsInv.getMeleeWeapon());
         this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.spearTauredain));
      }

      this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
      this.func_70062_b(1, new ItemStack(LOTRMod.bootsTauredain));
      this.func_70062_b(2, new ItemStack(LOTRMod.legsTauredain));
      this.func_70062_b(3, new ItemStack(LOTRMod.bodyTauredain));
      if (this.field_70146_Z.nextInt(5) != 0) {
         this.func_70062_b(4, new ItemStack(LOTRMod.helmetTauredain));
      }

      return data;
   }

   public float getAlignmentBonus() {
      return 2.0F;
   }

   public String getSpeechBank(EntityPlayer entityplayer) {
      if (this.isFriendlyAndAligned(entityplayer)) {
         return this.hiredNPCInfo.getHiringPlayer() == entityplayer ? "tauredain/warrior/hired" : "tauredain/warrior/friendly";
      } else {
         return "tauredain/warrior/hostile";
      }
   }
}
