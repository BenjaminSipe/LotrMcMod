package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Random;
import lotr.common.LOTRCreativeTabs;
import lotr.common.LOTRMod;
import lotr.common.tileentity.LOTRTileEntityMillstone;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class LOTRBlockMillstone extends BlockContainer {
   @SideOnly(Side.CLIENT)
   private IIcon iconSide;
   @SideOnly(Side.CLIENT)
   private IIcon iconTop;
   @SideOnly(Side.CLIENT)
   private IIcon iconSideActive;
   @SideOnly(Side.CLIENT)
   private IIcon iconTopActive;

   public LOTRBlockMillstone() {
      super(Material.field_151576_e);
      this.func_149647_a(LOTRCreativeTabs.tabUtil);
      this.func_149711_c(4.0F);
      this.func_149672_a(Block.field_149769_e);
   }

   public TileEntity func_149915_a(World world, int i) {
      return new LOTRTileEntityMillstone();
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_149673_e(IBlockAccess world, int i, int j, int k, int side) {
      boolean active = isMillstoneActive(world, i, j, k);
      if (side != 1 && side != 0) {
         return active ? this.iconSideActive : this.iconSide;
      } else {
         return active ? this.iconTopActive : this.iconTop;
      }
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_149691_a(int i, int j) {
      return i != 1 && i != 0 ? this.iconSide : this.iconTop;
   }

   @SideOnly(Side.CLIENT)
   public void func_149651_a(IIconRegister iconregister) {
      this.iconSide = iconregister.func_94245_a(this.func_149641_N() + "_side");
      this.iconTop = iconregister.func_94245_a(this.func_149641_N() + "_top");
      this.iconSideActive = iconregister.func_94245_a(this.func_149641_N() + "_side_active");
      this.iconTopActive = iconregister.func_94245_a(this.func_149641_N() + "_top_active");
   }

   public boolean func_149727_a(World world, int i, int j, int k, EntityPlayer entityplayer, int side, float f, float f1, float f2) {
      if (!world.field_72995_K) {
         entityplayer.openGui(LOTRMod.instance, 52, world, i, j, k);
      }

      return true;
   }

   @SideOnly(Side.CLIENT)
   public void func_149734_b(World world, int i, int j, int k, Random random) {
      if (isMillstoneActive(world, i, j, k)) {
         for(int l = 0; l < 6; ++l) {
            float f10 = 0.5F + MathHelper.func_151240_a(random, -0.2F, 0.2F);
            float f11 = 0.5F + MathHelper.func_151240_a(random, -0.2F, 0.2F);
            float f12 = 0.9F + random.nextFloat() * 0.2F;
            world.func_72869_a("smoke", (double)((float)i + f10), (double)((float)j + f12), (double)((float)k + f11), 0.0D, 0.0D, 0.0D);
         }
      }

   }

   public void func_149689_a(World world, int i, int j, int k, EntityLivingBase entity, ItemStack itemstack) {
      if (itemstack.func_82837_s()) {
         ((LOTRTileEntityMillstone)world.func_147438_o(i, j, k)).setSpecialMillstoneName(itemstack.func_82833_r());
      }

   }

   public static boolean isMillstoneActive(IBlockAccess world, int i, int j, int k) {
      int meta = world.func_72805_g(i, j, k);
      return (meta & 8) != 0;
   }

   public static void toggleMillstoneActive(World world, int i, int j, int k) {
      int meta = world.func_72805_g(i, j, k);
      world.func_72921_c(i, j, k, meta ^ 8, 2);
   }

   public void func_149749_a(World world, int i, int j, int k, Block block, int meta) {
      LOTRTileEntityMillstone millstone = (LOTRTileEntityMillstone)world.func_147438_o(i, j, k);
      if (millstone != null) {
         LOTRMod.dropContainerItems(millstone, world, i, j, k);
         world.func_147453_f(i, j, k, block);
      }

      super.func_149749_a(world, i, j, k, block, meta);
   }

   public boolean func_149740_M() {
      return true;
   }

   public int func_149736_g(World world, int i, int j, int k, int direction) {
      return Container.func_94526_b((IInventory)world.func_147438_o(i, j, k));
   }
}
