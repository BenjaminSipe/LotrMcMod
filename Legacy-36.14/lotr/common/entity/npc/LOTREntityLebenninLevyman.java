package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityLebenninLevyman extends LOTREntityGondorLevyman {
   public LOTREntityLebenninLevyman(World world) {
      super(world);
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      this.func_70062_b(3, new ItemStack(LOTRMod.bodyLebenninGambeson));
      return data;
   }
}
