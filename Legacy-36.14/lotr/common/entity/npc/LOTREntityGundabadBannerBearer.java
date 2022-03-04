package lotr.common.entity.npc;

import lotr.common.item.LOTRItemBanner;
import net.minecraft.world.World;

public class LOTREntityGundabadBannerBearer extends LOTREntityGundabadOrc implements LOTRBannerBearer {
   public LOTREntityGundabadBannerBearer(World world) {
      super(world);
   }

   public LOTRItemBanner.BannerType getBannerType() {
      return LOTRItemBanner.BannerType.GUNDABAD;
   }
}
