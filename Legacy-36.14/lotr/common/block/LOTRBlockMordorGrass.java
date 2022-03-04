package lotr.common.block;

import java.util.ArrayList;
import java.util.Random;
import lotr.common.LOTRCreativeTabs;
import lotr.common.LOTRMod;
import lotr.common.world.biome.LOTRBiomeGenMordor;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;

public class LOTRBlockMordorGrass extends BlockBush implements IShearable {
   public LOTRBlockMordorGrass() {
      super(Material.field_151582_l);
      this.func_149676_a(0.1F, 0.0F, 0.1F, 0.9F, 0.8F, 0.9F);
      this.func_149647_a(LOTRCreativeTabs.tabDeco);
      this.func_149711_c(0.0F);
      this.func_149672_a(Block.field_149779_h);
   }

   public boolean func_149718_j(World world, int i, int j, int k) {
      return j >= 0 && j < 256 ? LOTRBiomeGenMordor.isSurfaceMordorBlock(world, i, j - 1, k) : false;
   }

   public boolean isReplaceable(IBlockAccess world, int i, int j, int k) {
      return true;
   }

   public int func_149645_b() {
      return LOTRMod.proxy.getGrassRenderID();
   }

   public Item func_149650_a(int i, Random random, int j) {
      return null;
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
