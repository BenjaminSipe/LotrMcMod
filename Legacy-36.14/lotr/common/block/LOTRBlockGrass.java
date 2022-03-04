package lotr.common.block;

import java.util.ArrayList;
import java.util.Random;
import lotr.common.LOTRCreativeTabs;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.common.util.ForgeDirection;

public class LOTRBlockGrass extends BlockBush implements IShearable {
   private boolean isSandy;

   public LOTRBlockGrass() {
      super(Material.field_151582_l);
      this.func_149676_a(0.1F, 0.0F, 0.1F, 0.9F, 0.8F, 0.9F);
      this.func_149647_a(LOTRCreativeTabs.tabDeco);
      this.func_149711_c(0.0F);
      this.func_149672_a(Block.field_149779_h);
   }

   public LOTRBlockGrass setSandy() {
      this.isSandy = true;
      return this;
   }

   public boolean func_149718_j(World world, int i, int j, int k) {
      Block below = world.func_147439_a(i, j - 1, k);
      return this.isSandy && below.func_149688_o() == Material.field_151595_p && below.isSideSolid(world, i, j - 1, k, ForgeDirection.UP) ? true : below.canSustainPlant(world, i, j, k, ForgeDirection.UP, this);
   }

   public boolean isReplaceable(IBlockAccess world, int i, int j, int k) {
      return true;
   }

   public int func_149645_b() {
      return LOTRMod.proxy.getGrassRenderID();
   }

   public int func_149679_a(int i, Random random) {
      return Blocks.field_150329_H.func_149679_a(i, random);
   }

   public ArrayList getDrops(World world, int i, int j, int k, int meta, int fortune) {
      return Blocks.field_150329_H.getDrops(world, i, j, k, meta, fortune);
   }

   public int func_149643_k(World world, int i, int j, int k) {
      return world.func_72805_g(i, j, k);
   }

   public boolean isShearable(ItemStack item, IBlockAccess world, int i, int j, int k) {
      return true;
   }

   public ArrayList onSheared(ItemStack item, IBlockAccess world, int i, int j, int k, int fortune) {
      ArrayList list = new ArrayList();
      list.add(new ItemStack(this, 1, world.func_72805_g(i, j, k)));
      return list;
   }
}
