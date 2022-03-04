package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import lotr.common.item.LOTRItemBanner;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityDwarfBannerBearer extends LOTREntityDwarfWarrior implements LOTRBannerBearer {
   public LOTREntityDwarfBannerBearer(World world) {
      super(world);
   }

   public LOTRItemBanner.BannerType getBannerType() {
      return LOTRItemBanner.BannerType.DWARF;
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      this.func_70062_b(1, new ItemStack(LOTRMod.bootsDwarvenSilver));
      this.func_70062_b(2, new ItemStack(LOTRMod.legsDwarvenSilver));
      this.func_70062_b(3, new ItemStack(LOTRMod.bodyDwarvenSilver));
      this.func_70062_b(4, new ItemStack(LOTRMod.helmetDwarvenSilver));
      return data;
   }
}
