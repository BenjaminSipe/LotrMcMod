package lotr.common.item;

import lotr.common.init.LOTRItems;
import lotr.common.init.LOTRSoundEvents;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CauldronBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.Item.Properties;
import net.minecraft.stats.Stats;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceContext.FluidMode;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.World;

public interface IEmptyVesselItem {
   VesselType getVesselType();

   default ActionResult doEmptyVesselRightClick(World world, PlayerEntity player, Hand hand) {
      ItemStack heldItem = player.func_184586_b(hand);
      RayTraceResult target = IEmptyVesselItem.HackyProxyItemImpl.proxyRayTrace(world, player, FluidMode.SOURCE_ONLY);
      if (target.func_216346_c() == Type.MISS) {
         return ActionResult.func_226250_c_(heldItem);
      } else {
         if (target.func_216346_c() == Type.BLOCK) {
            BlockPos pos = ((BlockRayTraceResult)target).func_216350_a();
            if (!world.func_175660_a(player, pos)) {
               return ActionResult.func_226250_c_(heldItem);
            }

            if (world.func_204610_c(pos).func_206884_a(FluidTags.field_206959_a)) {
               world.func_184148_a(player, player.func_226277_ct_(), player.func_226278_cu_(), player.func_226281_cx_(), LOTRSoundEvents.MUG_FILL, SoundCategory.NEUTRAL, 0.5F, 0.8F + world.field_73012_v.nextFloat() * 0.4F);
               return ActionResult.func_226248_a_(this.createWaterVessel(heldItem, player));
            }
         }

         return ActionResult.func_226250_c_(heldItem);
      }
   }

   default ActionResultType tryToPlaceVesselBlock(ItemUseContext context) {
      return ActionResultType.PASS;
   }

   default ActionResultType doEmptyVesselUseOnBlock(ItemUseContext context) {
      ItemStack heldItem = context.func_195996_i();
      PlayerEntity player = context.func_195999_j();
      World world = context.func_195991_k();
      BlockPos pos = context.func_195995_a();
      BlockState state = world.func_180495_p(pos);
      if (state.func_177230_c() == Blocks.field_150383_bp) {
         int level = (Integer)state.func_177229_b(CauldronBlock.field_176591_a);
         if (level > 0) {
            if (!world.field_72995_K) {
               if (!player.field_71075_bZ.field_75098_d) {
                  ItemStack waterDrink = new ItemStack((IItemProvider)LOTRItems.WATER_DRINK.get());
                  VesselDrinkItem.setVessel(waterDrink, this.getVesselType());
                  player.func_195066_a(Stats.field_188078_L);
                  heldItem.func_190918_g(1);
                  if (heldItem.func_190926_b()) {
                     player.func_184611_a(context.func_221531_n(), waterDrink);
                  } else if (!player.field_71071_by.func_70441_a(waterDrink)) {
                     player.func_71019_a(waterDrink, false);
                  } else if (player instanceof ServerPlayerEntity) {
                     ((ServerPlayerEntity)player).func_71120_a(player.field_71069_bz);
                  }
               }

               world.func_184133_a((PlayerEntity)null, pos, LOTRSoundEvents.MUG_FILL, SoundCategory.BLOCKS, 0.5F, 0.8F + world.field_73012_v.nextFloat() * 0.4F);
               ((CauldronBlock)state.func_177230_c()).func_176590_a(world, pos, state, level - 1);
            }

            return ActionResultType.SUCCESS;
         }
      }

      return ActionResultType.PASS;
   }

   default ItemStack createWaterVessel(ItemStack heldItem, PlayerEntity player) {
      ItemStack waterDrink = new ItemStack((IItemProvider)LOTRItems.WATER_DRINK.get());
      VesselDrinkItem.setVessel(waterDrink, this.getVesselType());
      heldItem.func_190918_g(1);
      player.func_71029_a(Stats.field_75929_E.func_199076_b((Item)this));
      if (heldItem.func_190926_b()) {
         return waterDrink;
      } else {
         if (!player.field_71071_by.func_70441_a(waterDrink)) {
            player.func_71019_a(waterDrink, false);
         }

         return heldItem;
      }
   }

   static boolean canMilk(Entity target) {
      return target instanceof CowEntity && !((CowEntity)target).func_70631_g_();
   }

   public static final class HackyProxyItemImpl extends Item {
      public HackyProxyItemImpl(Properties properties) {
         super(properties);
      }

      protected static RayTraceResult proxyRayTrace(World world, PlayerEntity player, FluidMode fluidMode) {
         return Item.func_219968_a(world, player, fluidMode);
      }
   }
}
