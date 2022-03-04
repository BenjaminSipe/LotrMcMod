package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import lotr.common.LOTRShields;
import lotr.common.entity.animal.LOTREntityRhino;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityHalfTrollWarrior extends LOTREntityHalfTroll {
   public LOTREntityHalfTrollWarrior(World world) {
      super(world);
      this.npcShield = LOTRShields.ALIGNMENT_HALF_TROLL;
      this.spawnRidingHorse = this.field_70146_Z.nextInt(12) == 0;
   }

   public LOTRNPCMount createMountToRide() {
      LOTREntityRhino rhino = new LOTREntityRhino(this.field_70170_p);
      if (this.field_70146_Z.nextBoolean()) {
         rhino.setMountArmor(new ItemStack(LOTRMod.rhinoArmorHalfTroll));
      }

      return rhino;
   }

   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.24D);
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      int i = this.field_70146_Z.nextInt(7);
      if (i == 0) {
         this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.battleaxeHalfTroll));
      } else if (i == 1) {
         this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.hammerHalfTroll));
      } else if (i == 2) {
         this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.maceHalfTroll));
      } else if (i == 3) {
         this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.scimitarHalfTroll));
      } else if (i == 4) {
         this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerHalfTroll));
      } else if (i == 5) {
         this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerHalfTrollPoisoned));
      } else if (i == 6) {
         this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.pikeHalfTroll));
      }

      this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
      this.func_70062_b(1, new ItemStack(LOTRMod.bootsHalfTroll));
      this.func_70062_b(2, new ItemStack(LOTRMod.legsHalfTroll));
      this.func_70062_b(3, new ItemStack(LOTRMod.bodyHalfTroll));
      if (this.field_70146_Z.nextInt(4) != 0) {
         this.func_70062_b(4, new ItemStack(LOTRMod.helmetHalfTroll));
      }

      return data;
   }

   public float getAlignmentBonus() {
      return 2.0F;
   }
}
