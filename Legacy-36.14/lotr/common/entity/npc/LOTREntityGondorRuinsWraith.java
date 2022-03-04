package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityGondorRuinsWraith extends LOTREntitySkeletalWraith {
   public LOTREntityGondorRuinsWraith(World world) {
      super(world);
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.swordGondor));
      this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
      this.func_70062_b(1, new ItemStack(LOTRMod.bootsGondor));
      this.func_70062_b(2, new ItemStack(LOTRMod.legsGondor));
      this.func_70062_b(3, new ItemStack(LOTRMod.bodyGondor));
      return data;
   }
}
