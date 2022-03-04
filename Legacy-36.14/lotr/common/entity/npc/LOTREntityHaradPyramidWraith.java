package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityHaradPyramidWraith extends LOTREntitySkeletalWraith {
   public LOTREntityHaradPyramidWraith(World world) {
      super(world);
   }

   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(30.0D);
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      if (this.field_70146_Z.nextBoolean()) {
         this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerNearHaradPoisoned));
         this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
         this.func_70062_b(1, new ItemStack(LOTRMod.bootsNearHarad));
         this.func_70062_b(2, new ItemStack(LOTRMod.legsNearHarad));
         this.func_70062_b(3, new ItemStack(LOTRMod.bodyNearHarad));
      } else {
         this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerHaradPoisoned));
         this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
         this.func_70062_b(1, new ItemStack(LOTRMod.bootsGulfHarad));
         this.func_70062_b(2, new ItemStack(LOTRMod.legsGulfHarad));
         this.func_70062_b(3, new ItemStack(LOTRMod.bodyGulfHarad));
      }

      return data;
   }
}
