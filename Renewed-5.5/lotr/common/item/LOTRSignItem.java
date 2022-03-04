package lotr.common.item;

import java.util.function.Supplier;
import lotr.common.init.LOTRItemGroups;
import net.minecraft.block.Block;
import net.minecraft.item.SignItem;
import net.minecraft.item.Item.Properties;

public class LOTRSignItem extends SignItem {
   public LOTRSignItem(Supplier standingSign, Supplier wallSign) {
      super((new Properties()).func_200917_a(16).func_200916_a(LOTRItemGroups.DECO), (Block)standingSign.get(), (Block)wallSign.get());
   }
}
