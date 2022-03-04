package lotr.common.block;

import lotr.common.init.LOTRTileEntities;
import lotr.common.stat.LOTRStats;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockReader;

public class HobbitOvenBlock extends AbstractAlloyForgeBlock {
   public HobbitOvenBlock(MaterialColor color) {
      super(color);
   }

   public TileEntity func_196283_a_(IBlockReader world) {
      return ((TileEntityType)LOTRTileEntities.HOBBIT_OVEN.get()).func_200968_a();
   }

   protected ResourceLocation getForgeInteractionStat() {
      return LOTRStats.INTERACT_HOBBIT_OVEN;
   }
}
