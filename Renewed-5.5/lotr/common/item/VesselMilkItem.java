package lotr.common.item;

import com.google.common.collect.ImmutableList;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;

public class VesselMilkItem extends VesselDrinkItem {
   public VesselMilkItem() {
      super(0.0F, 0, 0.0F, false, 0.0F, ImmutableList.of());
   }

   public boolean canBeginDrinking(PlayerEntity player, ItemStack stack) {
      return true;
   }

   public ItemStack func_77654_b(ItemStack stack, World world, LivingEntity entity) {
      ItemStack result = super.func_77654_b(stack, world, entity);
      if (!world.field_72995_K) {
         ItemStack proxyCure = new ItemStack(Items.field_151117_aB);
         entity.curePotionEffects(proxyCure);
      }

      return result;
   }
}
