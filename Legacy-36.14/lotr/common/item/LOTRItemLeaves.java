package lotr.common.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class LOTRItemLeaves extends ItemBlock {
   public LOTRItemLeaves(Block block) {
      super(block);
      this.func_77656_e(0);
      this.func_77627_a(true);
   }

   public int func_77647_b(int i) {
      return i | 4;
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_77617_a(int i) {
      return this.field_150939_a.func_149691_a(0, i);
   }

   @SideOnly(Side.CLIENT)
   public int func_82790_a(ItemStack itemstack, int i) {
      return 16777215;
   }

   public String func_77667_c(ItemStack itemstack) {
      return super.func_77658_a() + "." + itemstack.func_77960_j();
   }
}
