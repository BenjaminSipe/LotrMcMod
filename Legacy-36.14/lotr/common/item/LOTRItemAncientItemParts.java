package lotr.common.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class LOTRItemAncientItemParts extends Item {
   @SideOnly(Side.CLIENT)
   private IIcon[] icons;
   private String[] partNames = new String[]{"swordTip", "swordBlade", "swordHilt", "armorPlate"};

   public LOTRItemAncientItemParts() {
      this.func_77627_a(true);
      this.func_77656_e(0);
      this.func_77637_a(LOTRCreativeTabs.tabMisc);
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_77617_a(int i) {
      if (i >= this.icons.length) {
         i = 0;
      }

      return this.icons[i];
   }

   public String func_77667_c(ItemStack itemstack) {
      return super.func_77658_a() + "." + itemstack.func_77960_j();
   }

   @SideOnly(Side.CLIENT)
   public void func_94581_a(IIconRegister iconregister) {
      this.icons = new IIcon[this.partNames.length];

      for(int i = 0; i < this.partNames.length; ++i) {
         this.icons[i] = iconregister.func_94245_a(this.func_111208_A() + "_" + this.partNames[i]);
      }

   }

   @SideOnly(Side.CLIENT)
   public void func_150895_a(Item item, CreativeTabs tab, List list) {
      for(int i = 0; i <= 3; ++i) {
         list.add(new ItemStack(item, 1, i));
      }

   }
}
