package lotr.common.entity.item;

import lotr.common.LOTRMod;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityGiraffeRug extends LOTREntityRugBase {
   public LOTREntityGiraffeRug(World world) {
      super(world);
      this.func_70105_a(2.0F, 0.3F);
   }

   protected String getRugNoise() {
      return "";
   }

   protected ItemStack getRugItem() {
      return new ItemStack(LOTRMod.giraffeRug);
   }
}
