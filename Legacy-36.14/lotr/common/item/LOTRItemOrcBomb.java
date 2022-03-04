package lotr.common.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import lotr.common.block.LOTRBlockOrcBomb;
import lotr.common.dispenser.LOTRDispenseOrcBomb;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class LOTRItemOrcBomb extends ItemBlock {
   public LOTRItemOrcBomb(Block block) {
      super(block);
      this.func_77656_e(0);
      this.func_77627_a(true);
      BlockDispenser.field_149943_a.func_82595_a(this, new LOTRDispenseOrcBomb());
   }

   public int func_77647_b(int i) {
      return i;
   }

   @SideOnly(Side.CLIENT)
   public void func_77624_a(ItemStack itemstack, EntityPlayer entityplayer, List list, boolean flag) {
      int meta = itemstack.func_77960_j();
      int strength = LOTRBlockOrcBomb.getBombStrengthLevel(meta);
      if (strength == 1) {
         list.add(StatCollector.func_74838_a("tile.lotr.orcBomb.doubleStrength"));
      }

      if (strength == 2) {
         list.add(StatCollector.func_74838_a("tile.lotr.orcBomb.tripleStrength"));
      }

      if (LOTRBlockOrcBomb.isFireBomb(meta)) {
         list.add(StatCollector.func_74838_a("tile.lotr.orcBomb.fire"));
      }

   }
}
