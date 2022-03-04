package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import lotr.common.LOTRMod;
import lotr.common.tileentity.LOTRTileEntityBookshelf;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class LOTRBlockBookshelfStorage extends BlockContainer {
   public LOTRBlockBookshelfStorage() {
      super(Material.field_151575_d);
      this.func_149711_c(1.5F);
      this.func_149672_a(Block.field_149766_f);
      this.func_149647_a((CreativeTabs)null);
   }

   public TileEntity func_149915_a(World world, int i) {
      return new LOTRTileEntityBookshelf();
   }

   @SideOnly(Side.CLIENT)
   public void func_149651_a(IIconRegister iconregister) {
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_149691_a(int i, int j) {
      return Blocks.field_150342_X.func_149691_a(i, j);
   }

   public ArrayList getDrops(World world, int i, int j, int k, int meta, int fortune) {
      return Blocks.field_150342_X.getDrops(world, i, j, k, meta, fortune);
   }

   public static boolean canOpenBookshelf(World world, int i, int j, int k, EntityPlayer entityplayer) {
      ItemStack itemstack = entityplayer.field_71071_by.func_70448_g();
      return itemstack == null || itemstack.func_77973_b() != Item.func_150898_a(Blocks.field_150342_X);
   }

   public boolean func_149727_a(World world, int i, int j, int k, EntityPlayer entityplayer, int side, float f, float f1, float f2) {
      if (!canOpenBookshelf(world, i, j, k, entityplayer)) {
         return false;
      } else {
         if (!world.field_72995_K) {
            entityplayer.openGui(LOTRMod.instance, 55, world, i, j, k);
         }

         return true;
      }
   }

   public void func_149749_a(World world, int i, int j, int k, Block block, int meta) {
      LOTRTileEntityBookshelf bookshelf = (LOTRTileEntityBookshelf)world.func_147438_o(i, j, k);
      if (bookshelf != null) {
         LOTRMod.dropContainerItems(bookshelf, world, i, j, k);
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

   protected boolean func_149700_E() {
      return true;
   }

   protected ItemStack func_149644_j(int i) {
      return new ItemStack(Blocks.field_150342_X);
   }
}
