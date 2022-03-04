package lotr.common.item;

import java.util.Iterator;
import java.util.List;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRCreativeTabs;
import lotr.common.LOTRLevelData;
import lotr.common.fac.LOTRFaction;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class LOTRItemManFlesh extends ItemFood {
   public LOTRItemManFlesh(int i, float f, boolean flag) {
      super(i, f, flag);
      this.func_77637_a(LOTRCreativeTabs.tabFood);
   }

   public static List getManFleshFactions() {
      return LOTRFaction.getAllOfType(LOTRFaction.FactionType.TYPE_ORC, LOTRFaction.FactionType.TYPE_TROLL);
   }

   public ItemStack func_77654_b(ItemStack itemstack, World world, EntityPlayer entityplayer) {
      --itemstack.field_77994_a;
      boolean orcAligned = false;
      Iterator var5 = getManFleshFactions().iterator();

      while(var5.hasNext()) {
         LOTRFaction faction = (LOTRFaction)var5.next();
         float alignment = LOTRLevelData.getData(entityplayer).getAlignment(faction);
         if (alignment > 0.0F) {
            orcAligned = true;
            break;
         }
      }

      if (orcAligned) {
         entityplayer.func_71024_bL().func_151686_a(this, itemstack);
         if (!world.field_72995_K) {
            LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.eatManFlesh);
         }
      } else if (!world.field_72995_K) {
         int dur = 30;
         entityplayer.func_70690_d(new PotionEffect(Potion.field_76438_s.field_76415_H, dur * 20));
      }

      world.func_72956_a(entityplayer, "random.burp", 0.5F, world.field_73012_v.nextFloat() * 0.1F + 0.9F);
      this.func_77849_c(itemstack, world, entityplayer);
      return itemstack;
   }
}
