package lotr.common.block;

import lotr.common.LOTRMod;
import net.minecraft.item.Item;

public class LOTRBlockGrapevineWhite extends LOTRBlockGrapevine {
   public LOTRBlockGrapevineWhite() {
      super(true);
   }

   public Item getGrapeItem() {
      return LOTRMod.grapeWhite;
   }

   public Item getGrapeSeedsItem() {
      return LOTRMod.seedsGrapeWhite;
   }
}
