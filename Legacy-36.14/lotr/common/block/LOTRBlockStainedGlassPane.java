package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import lotr.common.LOTRMod;
import net.minecraft.block.BlockStainedGlassPane;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class LOTRBlockStainedGlassPane extends LOTRBlockGlassPane {
   private IIcon[] paneIcons = new IIcon[16];

   @SideOnly(Side.CLIENT)
   public IIcon func_149735_b(int i, int j) {
      return this.paneIcons[j % this.paneIcons.length];
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_150097_e() {
      return ((LOTRBlockPane)LOTRMod.glassPane).func_150097_e();
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_149691_a(int i, int j) {
      return this.func_149735_b(i, ~j & 15);
   }

   @SideOnly(Side.CLIENT)
   public void func_149651_a(IIconRegister iconregister) {
      for(int i = 0; i < this.paneIcons.length; ++i) {
         this.paneIcons[i] = iconregister.func_94245_a("lotr:stainedGlass_" + ItemDye.field_150921_b[BlockStainedGlassPane.func_150103_c(i)]);
      }

   }

   public int func_149692_a(int i) {
      return i;
   }

   @SideOnly(Side.CLIENT)
   public int func_149701_w() {
      return 1;
   }

   @SideOnly(Side.CLIENT)
   public void func_149666_a(Item item, CreativeTabs tab, List list) {
      for(int i = 0; i < this.paneIcons.length; ++i) {
         list.add(new ItemStack(item, 1, i));
      }

   }
}
