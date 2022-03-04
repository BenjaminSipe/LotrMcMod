package lotr.common.item;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

public class LOTRItemBerry extends LOTRItemFood {
   private static List allBerries = new ArrayList();
   private boolean isPoisonous = false;

   public LOTRItemBerry() {
      super(2, 0.2F, false);
      allBerries.add(this);
   }

   public LOTRItemBerry setPoisonous() {
      this.isPoisonous = true;
      return this;
   }

   public ItemStack func_77654_b(ItemStack itemstack, World world, EntityPlayer entityplayer) {
      ItemStack ret = super.func_77654_b(itemstack, world, entityplayer);
      if (this.isPoisonous && !world.field_72995_K) {
         int duration = 3 + world.field_73012_v.nextInt(4);
         PotionEffect poison = new PotionEffect(Potion.field_76436_u.field_76415_H, duration * 20);
         entityplayer.func_70690_d(poison);
      }

      return ret;
   }

   public static void registerAllBerries(String name) {
      Iterator var1 = allBerries.iterator();

      while(var1.hasNext()) {
         Item berry = (Item)var1.next();
         OreDictionary.registerOre(name, berry);
      }

   }
}
