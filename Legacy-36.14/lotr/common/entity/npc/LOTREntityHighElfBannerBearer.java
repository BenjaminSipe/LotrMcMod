package lotr.common.entity.npc;

import lotr.common.item.LOTRItemBanner;
import net.minecraft.world.World;

public class LOTREntityHighElfBannerBearer extends LOTREntityHighElfWarrior implements LOTRBannerBearer {
   public LOTREntityHighElfBannerBearer(World world) {
      super(world);
   }

   public LOTRItemBanner.BannerType getBannerType() {
      return LOTRItemBanner.BannerType.HIGH_ELF;
   }
}
