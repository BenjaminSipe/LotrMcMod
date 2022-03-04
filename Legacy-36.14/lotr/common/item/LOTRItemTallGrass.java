package lotr.common.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.LOTRMod;
import lotr.common.block.LOTRBlockTallGrass;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class LOTRItemTallGrass extends LOTRItemBlockMetadata {
   public LOTRItemTallGrass(Block block) {
      super(block);
   }

   public boolean func_77623_v() {
      return true;
   }

   public int getRenderPasses(int meta) {
      return LOTRBlockTallGrass.grassOverlay[meta] ? 2 : 1;
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_77618_c(int meta, int pass) {
      return pass > 0 ? LOTRMod.tallGrass.func_149691_a(-1, meta) : super.func_77618_c(meta, pass);
   }

   @SideOnly(Side.CLIENT)
   public int func_82790_a(ItemStack itemstack, int pass) {
      return pass > 0 ? 16777215 : super.func_82790_a(itemstack, pass);
   }
}
