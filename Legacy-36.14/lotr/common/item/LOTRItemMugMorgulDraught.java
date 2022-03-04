package lotr.common.item;

import lotr.common.LOTRLevelData;
import lotr.common.fac.LOTRFaction;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class LOTRItemMugMorgulDraught extends LOTRItemMug {
   public LOTRItemMugMorgulDraught() {
      super(0.0F);
   }

   public ItemStack func_77654_b(ItemStack itemstack, World world, EntityPlayer entityplayer) {
      if (!this.shouldApplyPotionEffects(itemstack, entityplayer)) {
         ItemStack result = super.func_77654_b(itemstack, world, entityplayer);
         if (!world.field_72995_K) {
            entityplayer.func_70690_d(new PotionEffect(Potion.field_76436_u.field_76415_H, 100));
         }

         return result;
      } else {
         return super.func_77654_b(itemstack, world, entityplayer);
      }
   }

   protected boolean shouldApplyPotionEffects(ItemStack itemstack, EntityPlayer entityplayer) {
      return LOTRLevelData.getData(entityplayer).getAlignment(LOTRFaction.MORDOR) > 0.0F;
   }
}
