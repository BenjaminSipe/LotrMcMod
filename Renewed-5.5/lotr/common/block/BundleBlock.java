package lotr.common.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DirectionalBlock;
import net.minecraft.block.FireBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;

public class BundleBlock extends DirectionalBlock {
   public BundleBlock(Properties properties) {
      super(properties);
      this.func_180632_j((BlockState)this.func_176223_P().func_206870_a(field_176387_N, Direction.UP));
      ((FireBlock)Blocks.field_150480_ab).func_180686_a(this, 20, 40);
   }

   public BundleBlock(MaterialColor color) {
      this(Properties.func_200949_a(Material.field_151580_n, color).func_200943_b(0.4F).func_200947_a(SoundType.field_185854_g));
   }

   protected void func_206840_a(Builder builder) {
      builder.func_206894_a(new Property[]{field_176387_N});
   }

   public BlockState func_196258_a(BlockItemUseContext context) {
      return (BlockState)this.func_176223_P().func_206870_a(field_176387_N, context.func_196010_d().func_176734_d());
   }

   public BlockState func_185499_a(BlockState state, Rotation rot) {
      return (BlockState)state.func_206870_a(field_176387_N, rot.func_185831_a((Direction)state.func_177229_b(field_176387_N)));
   }

   public BlockState func_185471_a(BlockState state, Mirror mirror) {
      return state.func_185907_a(mirror.func_185800_a((Direction)state.func_177229_b(field_176387_N)));
   }
}
