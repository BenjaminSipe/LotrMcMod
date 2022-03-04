package lotr.common.item;

import com.google.common.collect.ImmutableList;
import lotr.common.init.LOTRSoundEvents;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CauldronBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class VesselWaterItem extends VesselDrinkItem {
   public VesselWaterItem() {
      super(0.0F, 0, 0.0F, false, 0.0F, ImmutableList.of());
   }

   public boolean canBeginDrinking(PlayerEntity player, ItemStack stack) {
      return true;
   }

   public ActionResultType func_195939_a(ItemUseContext context) {
      ActionResultType vesselPlaceResult = super.func_195939_a(context);
      if (vesselPlaceResult.func_226246_a_()) {
         return vesselPlaceResult;
      } else {
         ItemStack heldItem = context.func_195996_i();
         PlayerEntity player = context.func_195999_j();
         World world = context.func_195991_k();
         BlockPos pos = context.func_195995_a();
         BlockState state = world.func_180495_p(pos);
         if (state.func_177230_c() == Blocks.field_150383_bp) {
            int level = (Integer)state.func_177229_b(CauldronBlock.field_176591_a);
            if (level < 3) {
               if (!world.field_72995_K) {
                  if (!player.field_71075_bZ.field_75098_d) {
                     ItemStack emptyVessel = getVessel(heldItem).createEmpty();
                     player.func_195066_a(Stats.field_188078_L);
                     player.func_184611_a(context.func_221531_n(), emptyVessel);
                     if (player instanceof ServerPlayerEntity) {
                        ((ServerPlayerEntity)player).func_71120_a(player.field_71069_bz);
                     }
                  }

                  world.func_184133_a((PlayerEntity)null, pos, LOTRSoundEvents.MUG_FILL, SoundCategory.BLOCKS, 0.5F, 0.8F + world.field_73012_v.nextFloat() * 0.4F);
                  ((CauldronBlock)state.func_177230_c()).func_176590_a(world, pos, state, level + 1);
               }

               return ActionResultType.SUCCESS;
            }
         }

         return ActionResultType.PASS;
      }
   }
}
