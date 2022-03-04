package lotr.common.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.block.LOTRBlockDoubleFlower;
import net.minecraft.block.Block;
import net.minecraft.util.IIcon;

public class LOTRItemDoubleFlower extends LOTRItemBlockMetadata {
   public LOTRItemDoubleFlower(Block block) {
      super(block);
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_77617_a(int i) {
      return ((LOTRBlockDoubleFlower)this.field_150939_a).func_149888_a(true, i);
   }
}
