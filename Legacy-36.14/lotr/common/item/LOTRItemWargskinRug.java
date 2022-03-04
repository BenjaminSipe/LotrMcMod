package lotr.common.item;

import lotr.common.entity.item.LOTREntityRugBase;
import lotr.common.entity.item.LOTREntityWargskinRug;
import lotr.common.entity.npc.LOTREntityWarg;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTRItemWargskinRug extends LOTRItemRugBase {
   public LOTRItemWargskinRug() {
      super(LOTREntityWarg.WargType.wargTypeNames());
   }

   protected LOTREntityRugBase createRug(World world, ItemStack itemstack) {
      LOTREntityWargskinRug rug = new LOTREntityWargskinRug(world);
      rug.setRugType(LOTREntityWarg.WargType.forID(itemstack.func_77960_j()));
      return rug;
   }
}
