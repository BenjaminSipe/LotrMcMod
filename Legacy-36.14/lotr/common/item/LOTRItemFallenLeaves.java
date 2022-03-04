package lotr.common.item;

import lotr.common.block.LOTRBlockFallenLeaves;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class LOTRItemFallenLeaves extends LOTRItemBlockMetadata {
   public LOTRItemFallenLeaves(Block block) {
      super(block);
   }

   public String func_77653_i(ItemStack itemstack) {
      Object[] obj = ((LOTRBlockFallenLeaves)this.field_150939_a).leafBlockMetaFromFallenMeta(itemstack.func_77960_j());
      ItemStack leaves = new ItemStack((Block)obj[0], 1, (Integer)obj[1]);
      String name = leaves.func_82833_r();
      return StatCollector.func_74837_a("tile.lotr.fallenLeaves", new Object[]{name});
   }

   public ItemStack func_77659_a(ItemStack itemstack, World world, EntityPlayer entityplayer) {
      return LOTRItemWaterPlant.tryPlaceWaterPlant(this, itemstack, world, entityplayer, this.func_77621_a(world, entityplayer, true));
   }
}
