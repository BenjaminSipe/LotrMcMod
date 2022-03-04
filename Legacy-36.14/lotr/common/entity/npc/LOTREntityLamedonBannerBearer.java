package lotr.common.entity.npc;

import lotr.common.item.LOTRItemBanner;
import net.minecraft.world.World;

public class LOTREntityLamedonBannerBearer extends LOTREntityLamedonSoldier implements LOTRBannerBearer {
   public LOTREntityLamedonBannerBearer(World world) {
      super(world);
   }

   public LOTRItemBanner.BannerType getBannerType() {
      return LOTRItemBanner.BannerType.LAMEDON;
   }
}
