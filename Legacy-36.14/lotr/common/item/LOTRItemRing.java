package lotr.common.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;

public class LOTRItemRing extends Item {
   @SideOnly(Side.CLIENT)
   public static IIcon saxIcon;

   public LOTRItemRing() {
      this.func_77637_a(LOTRCreativeTabs.tabMisc);
   }

   @SideOnly(Side.CLIENT)
   public void func_94581_a(IIconRegister iconregister) {
      super.func_94581_a(iconregister);
      saxIcon = iconregister.func_94245_a("lotr:sax");
   }
}
