package lotr.common.item;

import lotr.common.init.LOTRBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item.Properties;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class BlueVitriolItem extends Item {
   public BlueVitriolItem(Properties properties) {
      super(properties);
   }

   public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
      World world = entity.field_70170_p;
      if (!world.field_72995_K && entity.func_70027_ad()) {
         BlockPos pos = entity.func_233580_cy_();
         BlockPos belowPos = pos.func_177977_b();
         BlockState state = world.func_180495_p(pos);
         BlockState belowState = world.func_180495_p(belowPos);
         if (state.func_177230_c() == Blocks.field_150480_ab && belowState.func_177230_c() == LOTRBlocks.HEARTH_BLOCK.get()) {
            world.func_175656_a(belowPos, ((Block)LOTRBlocks.SOUL_FIRE_HEARTH_BLOCK.get()).func_176223_P());
            world.func_175656_a(pos, Blocks.field_235335_bO_.func_176223_P());
            world.func_184133_a((PlayerEntity)null, pos, SoundEvents.field_187606_E, SoundCategory.BLOCKS, 0.5F, MathHelper.func_151240_a(world.field_73012_v, 0.75F, 0.95F));
            entity.func_70106_y();
         }
      }

      return false;
   }
}
