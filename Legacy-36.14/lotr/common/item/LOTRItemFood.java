package lotr.common.item;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRCreativeTabs;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTRItemFood extends ItemFood {
   public LOTRItemFood(int healAmount, float saturation, boolean canWolfEat) {
      super(healAmount, saturation, canWolfEat);
      this.func_77637_a(LOTRCreativeTabs.tabFood);
   }

   public ItemStack func_77654_b(ItemStack itemstack, World world, EntityPlayer entityplayer) {
      if (!world.field_72995_K && this == LOTRMod.maggotyBread) {
         LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.eatMaggotyBread);
      }

      return super.func_77654_b(itemstack, world, entityplayer);
   }
}
