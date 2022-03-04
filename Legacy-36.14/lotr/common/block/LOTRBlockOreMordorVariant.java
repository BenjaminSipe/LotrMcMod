package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class LOTRBlockOreMordorVariant extends LOTRBlockOre {
   @SideOnly(Side.CLIENT)
   private IIcon mordorIcon;
   private boolean dropsBlock;

   public LOTRBlockOreMordorVariant(boolean flag) {
      this.dropsBlock = flag;
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_149691_a(int i, int j) {
      return j == 1 ? this.mordorIcon : super.func_149691_a(i, j);
   }

   @SideOnly(Side.CLIENT)
   public void func_149651_a(IIconRegister iconregister) {
      super.func_149651_a(iconregister);
      this.mordorIcon = iconregister.func_94245_a(this.func_149641_N() + "_mordor");
   }

   public int func_149692_a(int i) {
      return this.dropsBlock ? i : super.func_149692_a(i);
   }

   @SideOnly(Side.CLIENT)
   public void func_149666_a(Item item, CreativeTabs tab, List list) {
      for(int j = 0; j <= 1; ++j) {
         list.add(new ItemStack(item, 1, j));
      }

   }

   public ItemStack getPickBlock(MovingObjectPosition target, World world, int i, int j, int k, EntityPlayer entityplayer) {
      return new ItemStack(this, 1, world.func_72805_g(i, j, k));
   }
}
