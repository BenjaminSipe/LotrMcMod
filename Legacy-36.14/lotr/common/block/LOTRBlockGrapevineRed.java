package lotr.common.block;

import lotr.common.LOTRMod;
import net.minecraft.item.Item;

public class LOTRBlockGrapevineRed extends LOTRBlockGrapevine {
   public LOTRBlockGrapevineRed() {
      super(true);
   }

   public Item getGrapeItem() {
      return LOTRMod.grapeRed;
   }

   public Item getGrapeSeedsItem() {
      return LOTRMod.seedsGrapeRed;
   }
}
