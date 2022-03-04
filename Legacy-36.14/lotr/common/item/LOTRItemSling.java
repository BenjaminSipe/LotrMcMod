package lotr.common.item;

import lotr.common.LOTRCreativeTabs;
import lotr.common.LOTRMod;
import lotr.common.entity.projectile.LOTREntityPebble;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTRItemSling extends Item {
   public LOTRItemSling() {
      this.func_77625_d(1);
      this.func_77656_e(250);
      this.func_77637_a(LOTRCreativeTabs.tabCombat);
   }

   public ItemStack func_77659_a(ItemStack itemstack, World world, EntityPlayer entityplayer) {
      if (entityplayer.field_71071_by.func_146028_b(LOTRMod.pebble) || entityplayer.field_71075_bZ.field_75098_d) {
         itemstack.func_77972_a(1, entityplayer);
         if (!entityplayer.field_71075_bZ.field_75098_d) {
            entityplayer.field_71071_by.func_146026_a(LOTRMod.pebble);
         }

         world.func_72956_a(entityplayer, "random.bow", 0.5F, 0.4F / (field_77697_d.nextFloat() * 0.4F + 0.8F));
         if (!world.field_72995_K) {
            world.func_72838_d((new LOTREntityPebble(world, entityplayer)).setSling());
         }
      }

      return itemstack;
   }

   public boolean func_82789_a(ItemStack itemstack, ItemStack repairItem) {
      return repairItem.func_77973_b() == Items.field_151116_aA ? true : super.func_82789_a(itemstack, repairItem);
   }
}
