package lotr.common.item;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.EnumDifficulty;

public class LOTRItemDagger extends LOTRItemSword {
   private LOTRItemDagger.DaggerEffect effect;

   public LOTRItemDagger(LOTRMaterial material) {
      this(material, LOTRItemDagger.DaggerEffect.NONE);
   }

   public LOTRItemDagger(ToolMaterial material) {
      this(material, LOTRItemDagger.DaggerEffect.NONE);
   }

   public LOTRItemDagger(LOTRMaterial material, LOTRItemDagger.DaggerEffect e) {
      this(material.toToolMaterial(), e);
   }

   public LOTRItemDagger(ToolMaterial material, LOTRItemDagger.DaggerEffect e) {
      super(material);
      this.lotrWeaponDamage -= 3.0F;
      this.effect = e;
   }

   public LOTRItemDagger.DaggerEffect getDaggerEffect() {
      return this.effect;
   }

   public boolean func_77644_a(ItemStack itemstack, EntityLivingBase hitEntity, EntityLivingBase user) {
      itemstack.func_77972_a(1, user);
      if (this.effect == LOTRItemDagger.DaggerEffect.NONE) {
         return true;
      } else {
         if (this.effect == LOTRItemDagger.DaggerEffect.POISON) {
            applyStandardPoison(hitEntity);
         }

         return true;
      }
   }

   public static void applyStandardPoison(EntityLivingBase entity) {
      EnumDifficulty difficulty = entity.field_70170_p.field_73013_u;
      int duration = 1 + difficulty.func_151525_a() * 2;
      PotionEffect poison = new PotionEffect(Potion.field_76436_u.field_76415_H, (duration + field_77697_d.nextInt(duration)) * 20);
      entity.func_70690_d(poison);
   }

   public static enum DaggerEffect {
      NONE,
      POISON;
   }
}
