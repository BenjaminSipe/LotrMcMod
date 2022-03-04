package lotr.common.entity.npc;

import lotr.common.item.LOTRItemBanner;
import net.minecraft.world.World;

public class LOTREntityUrukHaiBannerBearer extends LOTREntityUrukHai implements LOTRBannerBearer {
   public LOTREntityUrukHaiBannerBearer(World world) {
      super(world);
   }

   public LOTRItemBanner.BannerType getBannerType() {
      return LOTRItemBanner.BannerType.ISENGARD;
   }
}
