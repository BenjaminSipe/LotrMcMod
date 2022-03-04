package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import lotr.common.LOTRShields;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityDwarfWarrior extends LOTREntityDwarf {
   public LOTREntityDwarfWarrior(World world) {
      super(world);
      this.npcShield = LOTRShields.ALIGNMENT_DWARF;
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      int i = this.field_70146_Z.nextInt(7);
      if (i == 0) {
         this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.swordDwarven));
      } else if (i != 1 && i != 2) {
         if (i != 3 && i != 4) {
            if (i == 5) {
               this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.mattockDwarven));
            } else if (i == 6) {
               this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.pikeDwarven));
            }
         } else {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.hammerDwarven));
         }
      } else {
         this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.battleaxeDwarven));
      }

      if (this.field_70146_Z.nextInt(6) == 0) {
         this.npcItemsInv.setSpearBackup(this.npcItemsInv.getMeleeWeapon());
         this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.spearDwarven));
      }

      this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
      this.func_70062_b(1, new ItemStack(LOTRMod.bootsDwarven));
      this.func_70062_b(2, new ItemStack(LOTRMod.legsDwarven));
      this.func_70062_b(3, new ItemStack(LOTRMod.bodyDwarven));
      if (this.field_70146_Z.nextInt(10) != 0) {
         this.func_70062_b(4, new ItemStack(LOTRMod.helmetDwarven));
      }

      return data;
   }

   public float getAlignmentBonus() {
      return 2.0F;
   }
}
