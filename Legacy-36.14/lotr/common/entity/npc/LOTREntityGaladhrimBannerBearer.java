package lotr.common.entity.npc;

import lotr.common.item.LOTRItemBanner;
import net.minecraft.world.World;

public class LOTREntityGaladhrimBannerBearer extends LOTREntityGaladhrimWarrior implements LOTRBannerBearer {
   public LOTREntityGaladhrimBannerBearer(World world) {
      super(world);
   }

   public LOTRItemBanner.BannerType getBannerType() {
      return LOTRItemBanner.BannerType.GALADHRIM;
   }
}
