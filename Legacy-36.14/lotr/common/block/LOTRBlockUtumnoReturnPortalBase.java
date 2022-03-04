package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class LOTRBlockUtumnoReturnPortalBase extends Block {
   public static int MAX_SACRIFICE = 15;
   public static int RANGE = 5;
   @SideOnly(Side.CLIENT)
   private IIcon topIcon;

   public LOTRBlockUtumnoReturnPortalBase() {
      super(Material.field_151594_q);
      this.func_149711_c(-1.0F);
      this.func_149752_b(Float.MAX_VALUE);
      this.func_149672_a(Block.field_149769_e);
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_149691_a(int i, int j) {
      return i == 1 ? this.topIcon : super.func_149691_a(i, j);
   }

   @SideOnly(Side.CLIENT)
   public void func_149651_a(IIconRegister iconregister) {
      super.func_149651_a(iconregister);
      this.topIcon = iconregister.func_94245_a(this.func_149641_N() + "_top");
   }

   public AxisAlignedBB func_149668_a(World world, int i, int j, int k) {
      this.func_149719_a(world, i, j, k);
      return super.func_149668_a(world, i, j, k);
   }

   public void func_149719_a(IBlockAccess world, int i, int j, int k) {
      int meta = world.func_72805_g(i, j, k);
      this.setBlockBoundsMeta(meta);
   }

   public void func_149683_g() {
      this.setBlockBoundsMeta(0);
   }

   private void setBlockBoundsMeta(int meta) {
      float f = (float)meta / (float)MAX_SACRIFICE;
      float f1 = 0.0625F;
      float f2 = f1 + (1.0F - f1) * f;
      this.func_149676_a(0.0F, 0.0F, 0.0F, 1.0F, f2, 1.0F);
   }

   public int getLightValue(IBlockAccess world, int i, int j, int k) {
      int meta = world.func_72805_g(i, j, k);
      float f = (float)meta / (float)MAX_SACRIFICE;
      float f1 = 0.5F;
      float f2 = f1 + (1.0F - f1) * f;
      f2 *= 16.0F;
      return (int)f2;
   }

   public boolean func_149662_c() {
      return false;
   }

   public boolean func_149686_d() {
      return false;
   }

   public int func_149745_a(Random random) {
      return 0;
   }
}
