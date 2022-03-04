package lotr.common.entity.npc;

import lotr.common.item.LOTRItemBanner;
import net.minecraft.world.World;

public class LOTREntityPinnathGelinBannerBearer extends LOTREntityPinnathGelinSoldier implements LOTRBannerBearer {
   public LOTREntityPinnathGelinBannerBearer(World world) {
      super(world);
   }

   public LOTRItemBanner.BannerType getBannerType() {
      return LOTRItemBanner.BannerType.PINNATH_GELIN;
   }
}
