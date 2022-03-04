package lotr.common.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class LOTRItemMugTermite extends LOTRItemMug {
   public LOTRItemMugTermite(float f) {
      super(f);
   }

   public ItemStack func_77654_b(ItemStack itemstack, World world, EntityPlayer entityplayer) {
      ItemStack result = super.func_77654_b(itemstack, world, entityplayer);
      if (!world.field_72995_K && world.field_73012_v.nextInt(6) == 0) {
         world.func_72876_a((Entity)null, entityplayer.field_70165_t, entityplayer.field_70163_u, entityplayer.field_70161_v, 3.0F, false);
      }

      return result;
   }

   @SideOnly(Side.CLIENT)
   public void func_77624_a(ItemStack itemstack, EntityPlayer entityplayer, List list, boolean flag) {
      super.func_77624_a(itemstack, entityplayer, list, flag);
      list.add(EnumChatFormatting.DARK_GRAY + StatCollector.func_74838_a("item.lotr.drink.explode"));
   }
}
