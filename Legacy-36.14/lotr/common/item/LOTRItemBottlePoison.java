package lotr.common.item;

import lotr.common.LOTRCreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTRItemBottlePoison extends Item {
   public LOTRItemBottlePoison() {
      this.func_77625_d(1);
      this.func_77637_a(LOTRCreativeTabs.tabMisc);
      this.func_77642_a(Items.field_151069_bo);
   }

   public int func_77626_a(ItemStack itemstack) {
      return 32;
   }

   public EnumAction func_77661_b(ItemStack itemstack) {
      return EnumAction.drink;
   }

   public ItemStack func_77659_a(ItemStack itemstack, World world, EntityPlayer entityplayer) {
      entityplayer.func_71008_a(itemstack, this.func_77626_a(itemstack));
      return itemstack;
   }

   public ItemStack func_77654_b(ItemStack itemstack, World world, EntityPlayer entityplayer) {
      if (!world.field_72995_K) {
         LOTRPoisonedDrinks.addPoisonEffect(entityplayer, itemstack);
      }

      return !entityplayer.field_71075_bZ.field_75098_d ? new ItemStack(Items.field_151069_bo) : itemstack;
   }
}
