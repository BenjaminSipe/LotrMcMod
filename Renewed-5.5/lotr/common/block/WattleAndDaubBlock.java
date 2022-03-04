package lotr.common.block;

import java.util.List;
import java.util.stream.Stream;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FireBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.common.Tags.Items;

public class WattleAndDaubBlock extends Block {
   public static final BooleanProperty CONNECTED;

   public WattleAndDaubBlock(Properties props) {
      super(props);
      ((FireBlock)Blocks.field_150480_ab).func_180686_a(this, 40, 40);
      this.func_180632_j((BlockState)this.func_176223_P().func_206870_a(CONNECTED, true));
   }

   public WattleAndDaubBlock() {
      this(Properties.func_200949_a(Material.field_151577_b, MaterialColor.field_151677_p).func_200943_b(1.0F).func_200947_a(SoundType.field_222470_q));
   }

   protected void func_206840_a(Builder builder) {
      builder.func_206894_a(new Property[]{CONNECTED});
   }

   public static boolean doBlocksConnectVisually(BlockState state, BlockState otherState, List connectOffsets) {
      Block otherBlock = otherState.func_177230_c();
      if (!(otherBlock instanceof WattleAndDaubBlock)) {
         return false;
      } else {
         return (Boolean)state.func_177229_b(CONNECTED) && (Boolean)otherState.func_177229_b(CONNECTED);
      }
   }

   public ActionResultType func_225533_a_(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult target) {
      ItemStack heldItem = player.func_184586_b(hand);
      if (heldItem.func_77973_b().func_206844_a(Items.RODS_WOODEN) && this.isSurroundedByAnyWattle(world, pos)) {
         world.func_175656_a(pos, (BlockState)state.func_206870_a(CONNECTED, !(Boolean)state.func_177229_b(CONNECTED)));
         world.func_184133_a(player, pos, this.field_149762_H.func_185841_e(), SoundCategory.BLOCKS, (this.field_149762_H.func_185843_a() + 1.0F) / 4.0F, this.field_149762_H.func_185847_b() * 1.0F);
         world.func_217379_c(2001, pos, Block.func_196246_j(state));
         return ActionResultType.SUCCESS;
      } else {
         return super.func_225533_a_(state, world, pos, player, hand, target);
      }
   }

   private boolean isSurroundedByAnyWattle(IWorld world, BlockPos pos) {
      Stream var10000 = Stream.of(Direction.values());
      pos.getClass();
      var10000 = var10000.map(pos::func_177972_a);
      world.getClass();
      return var10000.map(world::func_180495_p).anyMatch((state) -> {
         return state.func_177230_c() == this;
      });
   }

   public BlockState func_196271_a(BlockState state, Direction facing, BlockState facingState, IWorld world, BlockPos currentPos, BlockPos facingPos) {
      if ((Boolean)state.func_177229_b(CONNECTED) && !this.isSurroundedByAnyWattle(world, currentPos)) {
         state = (BlockState)state.func_206870_a(CONNECTED, true);
      }

      return super.func_196271_a(state, facing, facingState, world, currentPos, facingPos);
   }

   static {
      CONNECTED = LOTRBlockStates.WATTLE_CONNECTED;
   }
}
