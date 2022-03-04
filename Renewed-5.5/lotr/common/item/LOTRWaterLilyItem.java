package lotr.common.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item.Properties;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class LOTRWaterLilyItem extends WaterPlantBlockItem {
   public LOTRWaterLilyItem(Block blockIn, Properties properties) {
      super(blockIn, properties);
   }

   protected boolean canPlaceOnIce() {
      return true;
   }

   protected void playPlaceSound(World world, BlockPos pos, BlockState state, PlayerEntity player) {
      world.func_184133_a(player, pos, SoundEvents.field_187916_gp, SoundCategory.BLOCKS, 1.0F, 1.0F);
   }
}
