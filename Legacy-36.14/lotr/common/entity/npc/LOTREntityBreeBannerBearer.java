package lotr.common.entity.npc;

import lotr.common.item.LOTRItemBanner;
import net.minecraft.world.World;

public class LOTREntityBreeBannerBearer extends LOTREntityBreeGuard implements LOTRBannerBearer {
   public LOTREntityBreeBannerBearer(World world) {
      super(world);
   }

   public LOTRItemBanner.BannerType getBannerType() {
      return LOTRItemBanner.BannerType.BREE;
   }
}
