package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import lotr.common.item.LOTRItemHaradRobes;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityNomadWarrior extends LOTREntityNomad {
   private static ItemStack[] weaponsBronze;

   public LOTREntityNomadWarrior(World world) {
      super(world);
      this.addTargetTasks(true);
      this.spawnRidingHorse = this.field_70146_Z.nextInt(8) == 0;
      this.npcShield = null;
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
      int i = this.field_70146_Z.nextInt(weaponsBronze.length);
      this.npcItemsInv.setMeleeWeapon(weaponsBronze[i].func_77946_l());
      if (this.field_70146_Z.nextInt(6) == 0) {
         this.npcItemsInv.setSpearBackup(this.npcItemsInv.getMeleeWeapon());
         this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.spearHarad));
      }

      this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
      this.func_70062_b(1, new ItemStack(LOTRMod.bootsNomad));
      this.func_70062_b(2, new ItemStack(LOTRMod.legsNomad));
      this.func_70062_b(3, new ItemStack(LOTRMod.bodyNomad));
      if (this.field_70146_Z.nextInt(10) == 0) {
         this.func_70062_b(4, (ItemStack)null);
      } else if (this.field_70146_Z.nextInt(3) == 0) {
         ItemStack turban = new ItemStack(LOTRMod.helmetHaradRobes);
         int robeColor = nomadTurbanColors[this.field_70146_Z.nextInt(nomadTurbanColors.length)];
         LOTRItemHaradRobes.setRobesColor(turban, robeColor);
         this.func_70062_b(4, turban);
      } else {
         this.func_70062_b(4, new ItemStack(LOTRMod.helmetNomad));
      }

      return data;
   }

   public float getAlignmentBonus() {
      return 2.0F;
   }

   public String getSpeechBank(EntityPlayer entityplayer) {
      if (this.isFriendlyAndAligned(entityplayer)) {
         return this.hiredNPCInfo.getHiringPlayer() == entityplayer ? "nearHarad/nomad/warrior/hired" : "nearHarad/nomad/warrior/friendly";
      } else {
         return "nearHarad/nomad/warrior/hostile";
      }
   }

   static {
      weaponsBronze = new ItemStack[]{new ItemStack(LOTRMod.swordHarad), new ItemStack(LOTRMod.swordHarad), new ItemStack(LOTRMod.swordHarad), new ItemStack(LOTRMod.daggerHarad), new ItemStack(LOTRMod.daggerHaradPoisoned), new ItemStack(LOTRMod.pikeHarad)};
   }
}
