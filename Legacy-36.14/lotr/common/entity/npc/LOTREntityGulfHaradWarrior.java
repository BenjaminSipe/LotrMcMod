package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import lotr.common.LOTRShields;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityGulfHaradWarrior extends LOTREntityGulfHaradrim {
   public LOTREntityGulfHaradWarrior(World world) {
      super(world);
      this.addTargetTasks(true);
      this.spawnRidingHorse = this.field_70146_Z.nextInt(10) == 0;
      this.npcShield = LOTRShields.ALIGNMENT_GULF;
   }

   public void setupNPCGender() {
      this.familyInfo.setMale(true);
   }

   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(npcRangedAccuracy).func_111128_a(0.75D);
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      if (this.field_70146_Z.nextInt(3) != 0) {
         this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.swordGulfHarad));
      } else {
         int i = this.field_70146_Z.nextInt(5);
         if (i != 0 && i != 1) {
            if (i == 2) {
               this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerHarad));
            } else if (i == 3) {
               this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerHaradPoisoned));
            } else if (i == 4) {
               this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.pikeHarad));
            }
         } else {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.swordHarad));
         }
      }

      if (this.field_70146_Z.nextInt(5) == 0) {
         this.npcItemsInv.setSpearBackup(this.npcItemsInv.getMeleeWeapon());
         this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.spearHarad));
      }

      this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
      this.func_70062_b(1, new ItemStack(LOTRMod.bootsGulfHarad));
      this.func_70062_b(2, new ItemStack(LOTRMod.legsGulfHarad));
      this.func_70062_b(3, new ItemStack(LOTRMod.bodyGulfHarad));
      if (this.field_70146_Z.nextInt(10) == 0) {
         this.func_70062_b(4, (ItemStack)null);
      } else {
         this.func_70062_b(4, new ItemStack(LOTRMod.helmetGulfHarad));
      }

      return data;
   }

   public float getAlignmentBonus() {
      return 2.0F;
   }

   public String getSpeechBank(EntityPlayer entityplayer) {
      if (this.isFriendlyAndAligned(entityplayer)) {
         return this.hiredNPCInfo.getHiringPlayer() == entityplayer ? "nearHarad/gulf/warrior/hired" : "nearHarad/gulf/warrior/friendly";
      } else {
         return "nearHarad/gulf/warrior/hostile";
      }
   }
}
