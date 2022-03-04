package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import lotr.common.LOTRMod;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.ColorizerGrass;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class LOTRBlockClover extends LOTRBlockFlower {
   @SideOnly(Side.CLIENT)
   public static IIcon stemIcon;
   @SideOnly(Side.CLIENT)
   public static IIcon petalIcon;

   public LOTRBlockClover() {
      this.func_149676_a(0.2F, 0.0F, 0.2F, 0.8F, 0.4F, 0.8F);
   }

   @SideOnly(Side.CLIENT)
   public AxisAlignedBB func_149633_g(World world, int i, int j, int k) {
      double posX = (double)i;
      double posY = (double)j;
      double posZ = (double)k;
      long seed = (long)(i * 3129871) ^ (long)k * 116129781L ^ (long)j;
      seed = seed * seed * 42317861L + seed * 11L;
      posX += ((double)((float)(seed >> 16 & 15L) / 15.0F) - 0.5D) * 0.5D;
      posZ += ((double)((float)(seed >> 24 & 15L) / 15.0F) - 0.5D) * 0.5D;
      return AxisAlignedBB.func_72330_a(posX + this.field_149759_B, posY + this.field_149760_C, posZ + this.field_149754_D, posX + this.field_149755_E, posY + this.field_149756_F, posZ + this.field_149757_G);
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_149691_a(int i, int j) {
      return petalIcon;
   }

   @SideOnly(Side.CLIENT)
   public void func_149651_a(IIconRegister iconregister) {
      stemIcon = iconregister.func_94245_a(this.func_149641_N() + "_stem");
      petalIcon = iconregister.func_94245_a(this.func_149641_N() + "_petal");
   }

   public boolean isReplaceable(IBlockAccess world, int i, int j, int k) {
      int meta = world.func_72805_g(i, j, k);
      return meta != 1;
   }

   public int func_149692_a(int i) {
      return i;
   }

   @SideOnly(Side.CLIENT)
   public void func_149666_a(Item item, CreativeTabs tab, List list) {
      for(int j = 0; j <= 1; ++j) {
         list.add(new ItemStack(item, 1, j));
      }

   }

   public int func_149645_b() {
      return LOTRMod.proxy.getCloverRenderID();
   }

   @SideOnly(Side.CLIENT)
   public int func_149635_D() {
      return ColorizerGrass.func_77480_a(1.0D, 1.0D);
   }

   @SideOnly(Side.CLIENT)
   public int func_149741_i(int i) {
      return this.func_149635_D();
   }

   @SideOnly(Side.CLIENT)
   public int func_149720_d(IBlockAccess world, int i, int j, int k) {
      return world.func_72807_a(i, k).func_150558_b(i, j, k);
   }
}
