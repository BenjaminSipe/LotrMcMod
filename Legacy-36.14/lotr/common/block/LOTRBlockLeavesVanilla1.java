package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Random;
import net.minecraft.block.BlockOldLeaf;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.IBlockAccess;

public class LOTRBlockLeavesVanilla1 extends LOTRBlockLeavesBase {
   public LOTRBlockLeavesVanilla1() {
      super(true, "lotr:leavesV1");
      this.setLeafNames(new String[]{"oak", "spruce", "birch", "jungle"});
      this.setSeasonal(new boolean[]{true, false, true, false});
   }

   public String[] func_150125_e() {
      return BlockOldLeaf.field_150131_O;
   }

   @SideOnly(Side.CLIENT)
   public int func_149741_i(int i) {
      int meta = i & 3;
      return meta == 0 ? ColorizerFoliage.func_77468_c() : super.func_149741_i(i);
   }

   @SideOnly(Side.CLIENT)
   public int func_149720_d(IBlockAccess world, int i, int j, int k) {
      int meta = world.func_72805_g(i, j, k) & 3;
      return meta == 0 ? getBiomeLeafColor(world, i, j, k) : super.func_149720_d(world, i, j, k);
   }

   public Item func_149650_a(int i, Random random, int j) {
      return Item.func_150898_a(Blocks.field_150345_g);
   }

   protected int getSaplingChance(int meta) {
      return meta == 3 ? 30 : super.getSaplingChance(meta);
   }
}
