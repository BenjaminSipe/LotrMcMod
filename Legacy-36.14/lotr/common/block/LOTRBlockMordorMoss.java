package lotr.common.block;

import java.util.ArrayList;
import java.util.Random;
import lotr.common.LOTRCreativeTabs;
import lotr.common.world.biome.LOTRBiomeGenMordor;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;

public class LOTRBlockMordorMoss extends Block implements IShearable {
   public LOTRBlockMordorMoss() {
      super(Material.field_151585_k);
      this.func_149676_a(0.0F, 0.0F, 0.0F, 1.0F, 0.0625F, 1.0F);
      this.func_149675_a(true);
      this.func_149647_a(LOTRCreativeTabs.tabDeco);
      this.func_149711_c(0.2F);
      this.func_149672_a(Block.field_149779_h);
   }

   public boolean func_149742_c(World world, int i, int j, int k) {
      return super.func_149742_c(world, i, j, k) && this.func_149718_j(world, i, j, k);
   }

   public boolean func_149718_j(World world, int i, int j, int k) {
      return j >= 0 && j < 256 ? LOTRBiomeGenMordor.isSurfaceMordorBlock(world, i, j - 1, k) : false;
   }

   public void func_149695_a(World world, int i, int j, int k, Block block) {
      super.func_149695_a(world, i, j, k, block);
      this.checkMossCanStay(world, i, j, k);
   }

   public void func_149674_a(World world, int i, int j, int k, Random random) {
      this.checkMossCanStay(world, i, j, k);
   }

   private void checkMossCanStay(World world, int i, int j, int k) {
      if (!this.func_149718_j(world, i, j, k)) {
         this.func_149697_b(world, i, j, k, world.func_72805_g(i, j, k), 0);
         world.func_147468_f(i, j, k);
      }

   }

   public boolean func_149662_c() {
      return false;
   }

   public Item func_149650_a(int i, Random random, int j) {
      return null;
   }

   public boolean isShearable(ItemStack item, IBlockAccess world, int i, int j, int k) {
      return true;
   }

   public ArrayList onSheared(ItemStack item, IBlockAccess world, int i, int j, int k, int fortune) {
      ArrayList drops = new ArrayList();
      drops.add(new ItemStack(this));
      return drops;
   }
}
