package lotr.common.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import lotr.common.dispenser.LOTRDispenseRhunFireJar;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class LOTRItemRhunFireJar extends ItemBlock {
   public LOTRItemRhunFireJar(Block block) {
      super(block);
      BlockDispenser.field_149943_a.func_82595_a(this, new LOTRDispenseRhunFireJar());
   }

   @SideOnly(Side.CLIENT)
   public void func_77624_a(ItemStack itemstack, EntityPlayer entityplayer, List list, boolean flag) {
      super.func_77624_a(itemstack, entityplayer, list, flag);
      list.add(StatCollector.func_74838_a("tile.lotr.rhunFire.warning"));
   }
}
