package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Random;
import lotr.common.LOTRMod;
import lotr.common.util.LOTRCommonIcons;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSign;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.Facing;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class LOTRBlockSignCarved extends BlockSign {
   public LOTRBlockSignCarved(Class cls) {
      super(cls, false);
      this.func_149672_a(Block.field_149769_e);
      this.func_149711_c(0.5F);
   }

   public void func_149719_a(IBlockAccess world, int i, int j, int k) {
      super.func_149719_a(world, i, j, k);
      this.func_149676_a((float)this.field_149759_B, 0.0F, (float)this.field_149754_D, (float)this.field_149755_E, 1.0F, (float)this.field_149757_G);
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_149691_a(int i, int j) {
      return LOTRCommonIcons.iconEmptyBlock;
   }

   public IIcon getOnBlockIcon(IBlockAccess world, int i, int j, int k, int side) {
      int onX = i - Facing.field_71586_b[side];
      int onY = j - Facing.field_71587_c[side];
      int onZ = k - Facing.field_71585_d[side];
      Block onBlock = world.func_147439_a(onX, onY, onZ);
      IIcon icon = onBlock.func_149673_e(world, onX, onY, onZ, side);
      if (icon == null) {
         icon = Blocks.field_150348_b.func_149691_a(0, 0);
      }

      return icon;
   }

   public Item func_149650_a(int i, Random random, int j) {
      return null;
   }

   @SideOnly(Side.CLIENT)
   public Item func_149694_d(World world, int i, int j, int k) {
      return this == LOTRMod.signCarvedIthildin ? LOTRMod.chiselIthildin : LOTRMod.chisel;
   }
}
