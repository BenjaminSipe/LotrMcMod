package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Random;
import net.minecraft.block.BlockNewLeaf;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class LOTRBlockLeavesVanilla2 extends LOTRBlockLeavesBase {
   public LOTRBlockLeavesVanilla2() {
      super(true, "lotr:leavesV2");
      this.setLeafNames(new String[]{"acacia", "darkOak"});
      this.setSeasonal(new boolean[]{false, true});
   }

   public String[] func_150125_e() {
      return BlockNewLeaf.field_150133_O;
   }

   @SideOnly(Side.CLIENT)
   public int func_149741_i(int i) {
      int meta = i & 3;
      return meta != 0 && meta != 1 ? super.func_149741_i(i) : ColorizerFoliage.func_77468_c();
   }

   @SideOnly(Side.CLIENT)
   public int func_149720_d(IBlockAccess world, int i, int j, int k) {
      int meta = world.func_72805_g(i, j, k) & 3;
      return meta != 0 && meta != 1 ? super.func_149720_d(world, i, j, k) : getBiomeLeafColor(world, i, j, k);
   }

   public Item func_149650_a(int i, Random random, int j) {
      return Item.func_150898_a(Blocks.field_150345_g);
   }

   public int func_149692_a(int i) {
      return super.func_149692_a(i) + 4;
   }

   protected int getSaplingChance(int meta) {
      return meta == 1 ? 12 : super.getSaplingChance(meta);
   }

   public int func_149643_k(World world, int i, int j, int k) {
      return super.func_149692_a(world.func_72805_g(i, j, k));
   }
}
