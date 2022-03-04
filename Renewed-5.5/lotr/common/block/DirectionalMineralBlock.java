package lotr.common.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.util.Direction;
import net.minecraftforge.common.ToolType;

public class DirectionalMineralBlock extends HorizontalBlock {
   public static final DirectionProperty FACING;
   private final int oreHarvestLvl;

   public DirectionalMineralBlock(Properties properties, int harvestLvl) {
      super(properties);
      this.oreHarvestLvl = harvestLvl;
      this.func_180632_j((BlockState)((BlockState)this.field_176227_L.func_177621_b()).func_206870_a(FACING, Direction.NORTH));
   }

   protected void func_206840_a(Builder builder) {
      builder.func_206894_a(new Property[]{FACING});
   }

   public ToolType getHarvestTool(BlockState state) {
      return ToolType.PICKAXE;
   }

   public int getHarvestLevel(BlockState state) {
      return this.oreHarvestLvl;
   }

   public BlockState func_196258_a(BlockItemUseContext context) {
      return (BlockState)this.func_176223_P().func_206870_a(FACING, context.func_195992_f().func_176734_d());
   }

   static {
      FACING = HorizontalBlock.field_185512_D;
   }
}
