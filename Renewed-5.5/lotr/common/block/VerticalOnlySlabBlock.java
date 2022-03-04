package lotr.common.block;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.SoundType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.state.EnumProperty;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.Direction.Axis;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class VerticalOnlySlabBlock extends AxialSlabBlock {
   private static final BiMap VANILLA_SLABS_TO_VERTICAL_SLABS = HashBiMap.create();
   private final SlabBlock correspondingVanillaSlab;

   public VerticalOnlySlabBlock(Block vanillaSlab) {
      super(vanillaSlab);
      if (!(vanillaSlab instanceof SlabBlock)) {
         throw new IllegalArgumentException("Can only construct a VerticalOnlySlabBlock from a SlabBlock");
      } else {
         this.correspondingVanillaSlab = (SlabBlock)vanillaSlab;
         if (VANILLA_SLABS_TO_VERTICAL_SLABS.containsKey(this.correspondingVanillaSlab)) {
            throw new IllegalArgumentException("Vanilla slab " + this.correspondingVanillaSlab.getRegistryName() + " already corresponds to a VerticalOnlySlabBlock!");
         } else {
            VANILLA_SLABS_TO_VERTICAL_SLABS.put(this.correspondingVanillaSlab, this);
         }
      }
   }

   protected EnumProperty getSlabAxisProperty() {
      return LOTRBlockStates.VERTICAL_ONLY_SLAB_AXIS;
   }

   public static VerticalOnlySlabBlock getVerticalSlabFor(SlabBlock vanillaSlab) {
      return (VerticalOnlySlabBlock)VANILLA_SLABS_TO_VERTICAL_SLABS.get(vanillaSlab);
   }

   public static SlabBlock getVanillaSlabFor(VerticalOnlySlabBlock verticalSlab) {
      return (SlabBlock)VANILLA_SLABS_TO_VERTICAL_SLABS.inverse().get(verticalSlab);
   }

   protected boolean isSameSlab(SlabBlock otherSlab) {
      return otherSlab == this || otherSlab == getVanillaSlabFor(this);
   }

   public ActionResultType placeVerticalOrVanilla(PlayerEntity player, Hand hand, ItemStack heldItem, World world, BlockPos pos, Direction side, BlockRayTraceResult blockRayTrace) {
      ItemUseContext itemUseContext = new ItemUseContext(player, hand, blockRayTrace);
      BlockItemUseContext blockItemUseContext = new AxialSlabBlock.AxialSlabUseContext(itemUseContext);
      if (!blockItemUseContext.func_196011_b()) {
         return ActionResultType.FAIL;
      } else {
         BlockPos placePos = blockItemUseContext.func_195995_a();
         BlockState stateToPlace = this.getStateForVerticalOrVanillaPlacement(blockItemUseContext);
         if (stateToPlace == null) {
            return ActionResultType.FAIL;
         } else if (world.func_180501_a(placePos, stateToPlace, 11)) {
            BlockState placedState = world.func_180495_p(placePos);
            Block placedBlock = placedState.func_177230_c();
            if (placedBlock == stateToPlace.func_177230_c() && player instanceof ServerPlayerEntity) {
               CriteriaTriggers.field_193137_x.func_193173_a((ServerPlayerEntity)player, placePos, heldItem);
            }

            SoundType blockSound = placedState.getSoundType(world, placePos, player);
            world.func_184133_a(player, placePos, blockSound.func_185841_e(), SoundCategory.BLOCKS, (blockSound.func_185843_a() + 1.0F) / 2.0F, blockSound.func_185847_b() * 0.8F);
            if (!player.field_71075_bZ.field_75098_d) {
               heldItem.func_190918_g(1);
            }

            return ActionResultType.SUCCESS;
         } else {
            return ActionResultType.FAIL;
         }
      }
   }

   private BlockState getStateForVerticalOrVanillaPlacement(BlockItemUseContext context) {
      AxialSlabBlock.AxialSlabPlacement slabPlacement = this.getSlabPlacementState(context);
      Axis placeAxis = slabPlacement.axis;
      BlockState stateToPlace = placeAxis == Axis.Y ? this.correspondingVanillaSlab.func_176223_P() : (BlockState)this.func_176223_P().func_206870_a(this.getSlabAxisProperty(), placeAxis);
      stateToPlace = (BlockState)((BlockState)stateToPlace.func_206870_a(field_196505_a, slabPlacement.slabType)).func_206870_a(field_204512_b, slabPlacement.waterlogged);
      return this.canPlace(context, stateToPlace) ? stateToPlace : null;
   }

   private boolean canPlace(BlockItemUseContext context, BlockState stateToPlace) {
      World world = context.func_195991_k();
      BlockPos pos = context.func_195995_a();
      PlayerEntity player = context.func_195999_j();
      ISelectionContext selection = player == null ? ISelectionContext.func_216377_a() : ISelectionContext.func_216374_a(player);
      return stateToPlace.func_196955_c(world, pos) && world.func_226663_a_(stateToPlace, pos, selection);
   }

   public ItemStack getPickBlock(BlockState state, RayTraceResult target, IBlockReader world, BlockPos pos, PlayerEntity player) {
      SlabBlock vanillaSlab = getVanillaSlabFor(this);
      return new ItemStack(vanillaSlab);
   }
}
