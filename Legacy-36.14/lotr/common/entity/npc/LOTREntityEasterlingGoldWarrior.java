package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import lotr.common.LOTRShields;
import lotr.common.entity.animal.LOTREntityHorse;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityEasterlingGoldWarrior extends LOTREntityEasterlingWarrior {
   public LOTREntityEasterlingGoldWarrior(World world) {
      super(world);
      this.npcShield = LOTRShields.ALIGNMENT_RHUN;
   }

   public LOTRNPCMount createMountToRide() {
      LOTREntityHorse horse = (LOTREntityHorse)super.createMountToRide();
      horse.setMountArmor(new ItemStack(LOTRMod.horseArmorRhunGold));
      return horse;
   }

   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(25.0D);
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      this.func_70062_b(1, new ItemStack(LOTRMod.bootsRhunGold));
      this.func_70062_b(2, new ItemStack(LOTRMod.legsRhunGold));
      this.func_70062_b(3, new ItemStack(LOTRMod.bodyRhunGold));
      this.func_70062_b(4, new ItemStack(LOTRMod.helmetRhunGold));
      return data;
   }
}
