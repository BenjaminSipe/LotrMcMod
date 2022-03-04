package lotr.common.item;

import lotr.common.LOTRCreativeTabs;
import lotr.common.dispenser.LOTRDispensePebble;
import lotr.common.entity.projectile.LOTREntityPebble;
import net.minecraft.block.BlockDispenser;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTRItemPebble extends Item {
   public LOTRItemPebble() {
      this.func_77625_d(64);
      this.func_77637_a(LOTRCreativeTabs.tabCombat);
      BlockDispenser.field_149943_a.func_82595_a(this, new LOTRDispensePebble());
   }

   public ItemStack func_77659_a(ItemStack itemstack, World world, EntityPlayer entityplayer) {
      if (!world.field_72995_K) {
         world.func_72838_d(new LOTREntityPebble(world, entityplayer));
         world.func_72956_a(entityplayer, "random.bow", 0.5F, 0.4F / (field_77697_d.nextFloat() * 0.4F + 0.8F));
         if (!entityplayer.field_71075_bZ.field_75098_d) {
            --itemstack.field_77994_a;
         }
      }

      return itemstack;
   }
}
