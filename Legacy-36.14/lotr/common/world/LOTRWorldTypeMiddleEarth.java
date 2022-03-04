package lotr.common.world;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.world.WorldType;

public class LOTRWorldTypeMiddleEarth extends WorldType {
   public LOTRWorldTypeMiddleEarth(String name) {
      super(name);
   }

   @SideOnly(Side.CLIENT)
   public boolean func_151357_h() {
      return true;
   }
}
