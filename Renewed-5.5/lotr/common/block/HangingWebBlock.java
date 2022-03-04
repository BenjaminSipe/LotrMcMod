package lotr.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class HangingWebBlock extends Block {
   public static final EnumProperty WEB_TYPE;

   public HangingWebBlock(Properties properties) {
      super(properties);
      this.func_180632_j((BlockState)this.func_176223_P().func_206870_a(WEB_TYPE, HangingWebBlock.Type.SINGLE));
   }

   public HangingWebBlock() {
      this(Properties.func_200945_a(Material.field_151569_G).func_200942_a().func_200943_b(0.5F));
   }

   protected void func_206840_a(Builder builder) {
      super.func_206840_a(builder);
      builder.func_206894_a(new Property[]{WEB_TYPE});
   }

   public VoxelShape func_220053_a(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
      return super.func_220053_a(state, world, pos, context);
   }

   public void func_196262_a(BlockState state, World world, BlockPos pos, Entity entity) {
      if (WebBlockHelper.shouldApplyWebSlowness(entity)) {
         entity.func_213295_a(state, new Vector3d(0.75D, 0.5D, 0.75D));
      }

   }

   public BlockState func_196258_a(BlockItemUseContext context) {
      World world = context.func_195991_k();
      BlockPos pos = context.func_195995_a();
      BlockState placeState = this.func_176223_P();
      BlockState doubleBottom = (BlockState)placeState.func_206870_a(WEB_TYPE, HangingWebBlock.Type.DOUBLE_BOTTOM);
      boolean doubleBottomValid = doubleBottom.func_196955_c(world, pos);
      if (doubleBottomValid) {
         return doubleBottom;
      } else {
         BlockState single = (BlockState)placeState.func_206870_a(WEB_TYPE, HangingWebBlock.Type.SINGLE);
         boolean singleValid = single.func_196955_c(world, pos);
         return singleValid ? single : null;
      }
   }

   public BlockState func_196271_a(BlockState state, Direction facing, BlockState facingState, IWorld world, BlockPos currentPos, BlockPos facingPos) {
      boolean check = false;
      HangingWebBlock.Type type = (HangingWebBlock.Type)state.func_177229_b(WEB_TYPE);
      if (type == HangingWebBlock.Type.SINGLE && facing == Direction.UP) {
         check = true;
      } else if ((type == HangingWebBlock.Type.DOUBLE_TOP || type == HangingWebBlock.Type.DOUBLE_BOTTOM) && (facing == Direction.DOWN || facing == Direction.UP)) {
         check = true;
      }

      if (check && !state.func_196955_c(world, currentPos)) {
         if (type == HangingWebBlock.Type.DOUBLE_TOP) {
            BlockState singleState = (BlockState)state.func_206870_a(WEB_TYPE, HangingWebBlock.Type.SINGLE);
            if (singleState.func_196955_c(world, currentPos)) {
               return singleState;
            }
         }

         return Blocks.field_150350_a.func_176223_P();
      } else {
         return type == HangingWebBlock.Type.SINGLE && facing == Direction.DOWN && this.matchType(world, currentPos.func_177977_b(), HangingWebBlock.Type.DOUBLE_BOTTOM) ? (BlockState)state.func_206870_a(WEB_TYPE, HangingWebBlock.Type.DOUBLE_TOP) : super.func_196271_a(state, facing, facingState, world, currentPos, facingPos);
      }
   }

   public boolean func_196260_a(BlockState state, IWorldReader world, BlockPos pos) {
      boolean isPresent = world.func_180495_p(pos).func_177230_c() == this;
      HangingWebBlock.Type type = (HangingWebBlock.Type)state.func_177229_b(WEB_TYPE);
      if (type == HangingWebBlock.Type.SINGLE) {
         return this.checkSolidSide(world, pos.func_177984_a(), Direction.DOWN);
      } else if (type != HangingWebBlock.Type.DOUBLE_TOP) {
         if (type == HangingWebBlock.Type.DOUBLE_BOTTOM) {
            return isPresent ? this.matchType(world, pos.func_177984_a(), HangingWebBlock.Type.DOUBLE_TOP) : this.matchType(world, pos.func_177984_a(), HangingWebBlock.Type.SINGLE);
         } else {
            return true;
         }
      } else {
         return this.checkSolidSide(world, pos.func_177984_a(), Direction.DOWN) && this.matchType(world, pos.func_177977_b(), HangingWebBlock.Type.DOUBLE_BOTTOM);
      }
   }

   private boolean checkSolidSide(IWorldReader world, BlockPos pos, Direction dir) {
      BlockState state = world.func_180495_p(pos);
      return state.func_235714_a_(BlockTags.field_206952_E) || Block.func_208061_a(state.func_196951_e(world, pos), dir);
   }

   private boolean matchType(IWorldReader world, BlockPos pos, HangingWebBlock.Type type) {
      BlockState state = world.func_180495_p(pos);
      return state.func_177230_c() == this && state.func_177229_b(WEB_TYPE) == type;
   }

   public ActionResultType func_225533_a_(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult trace) {
      ItemStack heldItem = player.func_184614_ca();
      if (heldItem.func_77973_b() == this.func_199767_j()) {
         BlockItemUseContext useContext = new BlockItemUseContext(new ItemUseContext(player, hand, trace));
         HangingWebBlock.Type type = (HangingWebBlock.Type)state.func_177229_b(WEB_TYPE);
         BlockPos placePos = null;
         BlockState placeState = null;
         if (type == HangingWebBlock.Type.SINGLE) {
            placePos = pos.func_177977_b();
            placeState = (BlockState)this.func_176223_P().func_206870_a(WEB_TYPE, HangingWebBlock.Type.DOUBLE_BOTTOM);
         }

         if (placePos != null && placeState != null) {
            boolean canDouble = world.func_180495_p(placePos).func_196953_a(useContext);
            if (canDouble) {
               world.func_180501_a(placePos, placeState, 3);
               SoundType sound = this.getSoundType(placeState, world, placePos, player);
               world.func_184133_a(player, placePos, sound.func_185841_e(), SoundCategory.BLOCKS, (sound.func_185843_a() + 1.0F) / 2.0F, sound.func_185847_b() * 0.8F);
               if (!player.field_71075_bZ.field_75098_d) {
                  heldItem.func_190918_g(1);
               }

               return ActionResultType.SUCCESS;
            }
         }
      }

      return super.func_225533_a_(state, world, pos, player, hand, trace);
   }

   static {
      WEB_TYPE = LOTRBlockStates.HANGING_WEB_TYPE;
   }

   public static enum Type implements IStringSerializable {
      SINGLE("single"),
      DOUBLE_TOP("double_top"),
      DOUBLE_BOTTOM("double_bottom");

      private final String typeName;

      private Type(String s) {
         this.typeName = s;
      }

      public String func_176610_l() {
         return this.typeName;
      }
   }
}
