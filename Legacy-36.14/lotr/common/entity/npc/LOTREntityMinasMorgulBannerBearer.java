package lotr.common.entity.npc;

import lotr.common.item.LOTRItemBanner;
import net.minecraft.world.World;

public class LOTREntityMinasMorgulBannerBearer extends LOTREntityMordorOrc implements LOTRBannerBearer {
   public LOTREntityMinasMorgulBannerBearer(World world) {
      super(world);
   }

   public LOTRItemBanner.BannerType getBannerType() {
      return LOTRItemBanner.BannerType.MINAS_MORGUL;
   }
}
