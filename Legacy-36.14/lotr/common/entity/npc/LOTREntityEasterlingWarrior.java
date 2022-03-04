package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityEasterlingWarrior extends LOTREntityEasterlingLevyman {
   public LOTREntityEasterlingWarrior(World world) {
      super(world);
      this.addTargetTasks(true);
      this.spawnRidingHorse = this.field_70146_Z.nextInt(6) == 0;
   }

   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(npcRangedAccuracy).func_111128_a(0.75D);
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      int i = this.field_70146_Z.nextInt(10);
      if (i != 0 && i != 1 && i != 2) {
         if (i != 3 && i != 4 && i != 5) {
            if (i == 6) {
               this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.polearmRhun));
            } else if (i == 7) {
               this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerRhun));
            } else if (i == 8) {
               this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerRhunPoisoned));
            } else if (i == 9) {
               this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.pikeRhun));
            }
         } else {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.battleaxeRhun));
         }
      } else {
         this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.swordRhun));
      }

      if (this.field_70146_Z.nextInt(5) == 0) {
         this.npcItemsInv.setSpearBackup(this.npcItemsInv.getMeleeWeapon());
         this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.spearRhun));
      }

      this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
      this.func_70062_b(1, new ItemStack(LOTRMod.bootsRhun));
      this.func_70062_b(2, new ItemStack(LOTRMod.legsRhun));
      this.func_70062_b(3, new ItemStack(LOTRMod.bodyRhun));
      this.func_70062_b(4, new ItemStack(LOTRMod.helmetRhun));
      return data;
   }
}
