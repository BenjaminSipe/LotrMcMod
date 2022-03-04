package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import lotr.common.LOTRCreativeTabs;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.common.util.ForgeDirection;

public class LOTRBlockFallenLeaves extends Block implements IShearable {
   public static List allFallenLeaves = new ArrayList();
   private static Random leafRand = new Random();
   private Block[] leafBlocks;

   public LOTRBlockFallenLeaves() {
      super(Material.field_151582_l);
      allFallenLeaves.add(this);
      this.func_149647_a(LOTRCreativeTabs.tabDeco);
      this.func_149711_c(0.2F);
      this.func_149672_a(Block.field_149779_h);
      this.field_149783_u = true;
      this.func_149676_a(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
   }

   public static void assignLeaves(Block fallenLeaves, Block... leaves) {
      ((LOTRBlockFallenLeaves)fallenLeaves).leafBlocks = leaves;
   }

   public Block[] getLeafBlocks() {
      return this.leafBlocks;
   }

   public Object[] leafBlockMetaFromFallenMeta(int meta) {
      Block leaf = this.leafBlocks[meta / 4];
      int leafMeta = meta & 3;
      return new Object[]{leaf, leafMeta};
   }

   public static Object[] fallenBlockMetaFromLeafBlockMeta(Block block, int meta) {
      meta &= 3;
      Iterator var2 = allFallenLeaves.iterator();

      while(var2.hasNext()) {
         LOTRBlockFallenLeaves fallenLeaves = (LOTRBlockFallenLeaves)var2.next();

         for(int i = 0; i < fallenLeaves.leafBlocks.length; ++i) {
            Block leafBlock = fallenLeaves.leafBlocks[i];
            if (leafBlock == block) {
               return new Object[]{fallenLeaves, i * 4 + meta};
            }
         }
      }

      return null;
   }

   public void func_149743_a(World world, int i, int j, int k, AxisAlignedBB bb, List boxes, Entity entity) {
   }

   public boolean func_149662_c() {
      return false;
   }

   public boolean func_149686_d() {
      return false;
   }

   public int func_149645_b() {
      return LOTRMod.proxy.getFallenLeavesRenderID();
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_149691_a(int i, int j) {
      Object[] obj = this.leafBlockMetaFromFallenMeta(j);
      return ((Block)obj[0]).func_149691_a(i, (Integer)obj[1]);
   }

   @SideOnly(Side.CLIENT)
   public void func_149651_a(IIconRegister iconregister) {
   }

   @SideOnly(Side.CLIENT)
   public int func_149741_i(int i) {
      Object[] obj = this.leafBlockMetaFromFallenMeta(i);
      return ((Block)obj[0]).func_149741_i((Integer)obj[1]);
   }

   @SideOnly(Side.CLIENT)
   public int func_149720_d(IBlockAccess world, int i, int j, int k) {
      int meta = world.func_72805_g(i, j, k);
      Object[] obj = this.leafBlockMetaFromFallenMeta(meta);
      return ((Block)obj[0]).func_149720_d(world, i, j, k);
   }

   public boolean func_149718_j(World world, int i, int j, int k) {
      Block below = world.func_147439_a(i, j - 1, k);
      int belowMeta = world.func_72805_g(i, j - 1, k);
      return below.func_149688_o() == Material.field_151586_h && belowMeta == 0 ? true : below.isSideSolid(world, i, j - 1, k, ForgeDirection.UP);
   }

   public boolean func_149742_c(World world, int i, int j, int k) {
      Block block = world.func_147439_a(i, j, k);
      return block.func_149688_o().func_76224_d() ? false : this.func_149718_j(world, i, j, k);
   }

   public void func_149695_a(World world, int i, int j, int k, Block block) {
      if (!this.func_149718_j(world, i, j, k)) {
         this.func_149697_b(world, i, j, k, world.func_72805_g(i, j, k), 0);
         world.func_147468_f(i, j, k);
      }

   }

   public Item func_149650_a(int i, Random random, int j) {
      return null;
   }

   public int func_149692_a(int i) {
      return i;
   }

   public boolean isShearable(ItemStack item, IBlockAccess world, int i, int j, int k) {
      return true;
   }

   public ArrayList onSheared(ItemStack item, IBlockAccess world, int i, int j, int k, int fortune) {
      ArrayList drops = new ArrayList();
      drops.add(new ItemStack(this, 1, world.func_72805_g(i, j, k)));
      return drops;
   }

   @SideOnly(Side.CLIENT)
   public void func_149666_a(Item item, CreativeTabs tab, List list) {
      for(int i = 0; i < this.leafBlocks.length; ++i) {
         Block leaf = this.leafBlocks[i];
         List leafTypes = new ArrayList();
         leaf.func_149666_a(Item.func_150898_a(leaf), leaf.func_149708_J(), leafTypes);
         Iterator var7 = leafTypes.iterator();

         while(var7.hasNext()) {
            ItemStack leafItem = (ItemStack)var7.next();
            int meta = leafItem.func_77960_j();
            list.add(new ItemStack(item, 1, i * 4 + meta));
         }
      }

   }
}
