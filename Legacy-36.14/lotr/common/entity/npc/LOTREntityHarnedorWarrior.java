package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import lotr.common.LOTRShields;
import lotr.common.item.LOTRItemHaradRobes;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityHarnedorWarrior extends LOTREntityHarnedhrim {
   private static ItemStack[] weaponsBronze;
   private static int[] turbanColors;

   public LOTREntityHarnedorWarrior(World world) {
      super(world);
      this.addTargetTasks(true);
      this.spawnRidingHorse = this.field_70146_Z.nextInt(8) == 0;
      this.npcShield = LOTRShields.ALIGNMENT_HARNEDOR;
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
      if (this.field_70146_Z.nextInt(5) == 0) {
         this.npcItemsInv.setSpearBackup(this.npcItemsInv.getMeleeWeapon());
         this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.spearHarad));
      }

      this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
      this.func_70062_b(1, new ItemStack(LOTRMod.bootsHarnedor));
      this.func_70062_b(2, new ItemStack(LOTRMod.legsHarnedor));
      this.func_70062_b(3, new ItemStack(LOTRMod.bodyHarnedor));
      if (this.field_70146_Z.nextInt(10) == 0) {
         this.func_70062_b(4, (ItemStack)null);
      } else if (this.field_70146_Z.nextInt(5) == 0) {
         ItemStack turban = new ItemStack(LOTRMod.helmetHaradRobes);
         int robeColor = turbanColors[this.field_70146_Z.nextInt(turbanColors.length)];
         LOTRItemHaradRobes.setRobesColor(turban, robeColor);
         this.func_70062_b(4, turban);
      } else {
         this.func_70062_b(4, new ItemStack(LOTRMod.helmetHarnedor));
      }

      return data;
   }

   public float getAlignmentBonus() {
      return 2.0F;
   }

   public String getSpeechBank(EntityPlayer entityplayer) {
      if (this.isFriendlyAndAligned(entityplayer)) {
         return this.hiredNPCInfo.getHiringPlayer() == entityplayer ? "nearHarad/harnennor/warrior/hired" : "nearHarad/harnennor/warrior/friendly";
      } else {
         return "nearHarad/harnennor/warrior/hostile";
      }
   }

   static {
      weaponsBronze = new ItemStack[]{new ItemStack(LOTRMod.swordHarad), new ItemStack(LOTRMod.swordHarad), new ItemStack(LOTRMod.swordHarad), new ItemStack(LOTRMod.daggerHarad), new ItemStack(LOTRMod.daggerHaradPoisoned), new ItemStack(LOTRMod.pikeHarad)};
      turbanColors = new int[]{1643539, 6309443, 7014914, 7809314, 5978155};
   }
}
