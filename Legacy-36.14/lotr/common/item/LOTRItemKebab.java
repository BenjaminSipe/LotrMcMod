package lotr.common.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class LOTRItemKebab extends LOTRItemFood {
   public LOTRItemKebab(int healAmount, float saturation, boolean canWolfEat) {
      super(healAmount, saturation, canWolfEat);
   }

   public ItemStack func_77654_b(ItemStack itemstack, World world, EntityPlayer entityplayer) {
      if (!world.field_72995_K && world.field_73012_v.nextInt(100) == 0) {
         entityplayer.func_145747_a(new ChatComponentText("That was a good kebab. You feel a lot better."));
      }

      return super.func_77654_b(itemstack, world, entityplayer);
   }
}
