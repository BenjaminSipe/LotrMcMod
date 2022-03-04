package lotr.common.item;

import com.google.common.collect.Multimap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import lotr.common.enchant.LOTREnchantmentHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ISpecialArmor;

public class LOTRWeaponStats {
   private static int basePlayerMeleeTime = 15;
   private static int baseMobMeleeTime = 20;
   private static Map meleeSpeed = new HashMap();
   private static Map meleeReach = new HashMap();
   private static Map meleeExtraKnockback = new HashMap();
   public static float MAX_MODIFIABLE_REACH;
   public static float MAX_MODIFIABLE_SPEED;
   public static int MAX_MODIFIABLE_KNOCKBACK;

   public static boolean isMeleeWeapon(ItemStack itemstack) {
      if (itemstack != null) {
         Multimap weaponAttributes = itemstack.func_111283_C();
         if (weaponAttributes != null) {
            Iterator var2 = weaponAttributes.entries().iterator();

            while(var2.hasNext()) {
               Object obj = var2.next();
               Entry e = (Entry)obj;
               AttributeModifier mod = (AttributeModifier)e.getValue();
               if (mod.func_111167_a() == LOTRItemSword.accessWeaponDamageModifier()) {
                  return true;
               }
            }
         }
      }

      return false;
   }

   public static float getMeleeDamageBonus(ItemStack itemstack) {
      float damage = 0.0F;
      if (itemstack != null) {
         Multimap weaponAttributes = itemstack.func_111283_C();
         if (weaponAttributes != null) {
            Iterator var3 = weaponAttributes.entries().iterator();

            while(var3.hasNext()) {
               Object obj = var3.next();
               Entry e = (Entry)obj;
               AttributeModifier mod = (AttributeModifier)e.getValue();
               if (mod.func_111167_a() == LOTRItemSword.accessWeaponDamageModifier()) {
                  damage = (float)((double)damage + mod.func_111164_d());
                  damage += EnchantmentHelper.func_152377_a(itemstack, EnumCreatureAttribute.UNDEFINED);
               }
            }
         }
      }

      return damage;
   }

   public static void registerMeleeSpeed(Object obj, float f) {
      meleeSpeed.put(obj, f);
   }

   public static void registerMeleeReach(Object obj, float f) {
      meleeReach.put(obj, f);
   }

   public static void registerMeleeExtraKnockback(Object obj, int i) {
      meleeExtraKnockback.put(obj, i);
   }

   private static Object getClassOrItemProperty(ItemStack itemstack, Map propertyMap) {
      if (itemstack != null) {
         Item item = itemstack.func_77973_b();
         if (propertyMap.containsKey(item)) {
            return propertyMap.get(item);
         }

         Class itemClass = item.getClass();

         while(true) {
            if (propertyMap.containsKey(itemClass)) {
               return propertyMap.get(itemClass);
            }

            if (itemClass == Item.class) {
               break;
            }

            itemClass = itemClass.getSuperclass();
         }
      }

      return null;
   }

   public static int getAttackTimePlayer(ItemStack itemstack) {
      return getAttackTimeWithBase(itemstack, basePlayerMeleeTime);
   }

   public static int getAttackTimeMob(ItemStack itemstack) {
      return getAttackTimeWithBase(itemstack, baseMobMeleeTime);
   }

   public static int getAttackTimeWithBase(ItemStack itemstack, int baseTime) {
      float time = (float)baseTime;
      Float factor = (Float)getClassOrItemProperty(itemstack, meleeSpeed);
      if (factor != null) {
         time /= factor;
      }

      time /= LOTREnchantmentHelper.calcMeleeSpeedFactor(itemstack);
      time = Math.max(time, 1.0F);
      return Math.round(time);
   }

   public static float getMeleeSpeed(ItemStack itemstack) {
      int base = basePlayerMeleeTime;
      return 1.0F / ((float)getAttackTimeWithBase(itemstack, base) / (float)base);
   }

   public static float getMeleeReachFactor(ItemStack itemstack) {
      float reach = 1.0F;
      Float factor = (Float)getClassOrItemProperty(itemstack, meleeReach);
      if (factor != null) {
         reach *= factor;
      }

      reach *= LOTREnchantmentHelper.calcMeleeReachFactor(itemstack);
      return reach;
   }

   public static float getMeleeReachDistance(EntityPlayer entityplayer) {
      float reach = 3.0F;
      reach *= getMeleeReachFactor(entityplayer.func_70694_bm());
      if (entityplayer.field_71075_bZ.field_75098_d) {
         reach = (float)((double)reach + 3.0D);
      }

      return reach;
   }

   public static float getMeleeExtraLookWidth() {
      return 1.0F;
   }

   public static int getBaseExtraKnockback(ItemStack itemstack) {
      int kb = 0;
      Integer extra = (Integer)getClassOrItemProperty(itemstack, meleeExtraKnockback);
      if (extra != null) {
         kb = extra;
      }

      return kb;
   }

   public static int getTotalKnockback(ItemStack itemstack) {
      return EnchantmentHelper.func_77506_a(Enchantment.field_77337_m.field_77352_x, itemstack) + getBaseExtraKnockback(itemstack) + LOTREnchantmentHelper.calcExtraKnockback(itemstack);
   }

   public static boolean isPoisoned(ItemStack itemstack) {
      if (itemstack == null) {
         return false;
      } else {
         Item item = itemstack.func_77973_b();
         return item instanceof LOTRItemDagger && ((LOTRItemDagger)item).getDaggerEffect() == LOTRItemDagger.DaggerEffect.POISON;
      }
   }

   public static boolean isRangedWeapon(ItemStack itemstack) {
      if (itemstack == null) {
         return false;
      } else {
         Item item = itemstack.func_77973_b();
         return item instanceof ItemBow || item instanceof LOTRItemSpear || item instanceof LOTRItemBlowgun || item instanceof LOTRItemThrowingAxe;
      }
   }

   public static float getRangedSpeed(ItemStack itemstack) {
      int base = 20;
      int time = 0;
      if (itemstack != null) {
         Item item = itemstack.func_77973_b();
         if (item instanceof LOTRItemCrossbow) {
            time = ((LOTRItemCrossbow)item).getMaxDrawTime();
         } else if (item instanceof LOTRItemBow) {
            time = ((LOTRItemBow)item).getMaxDrawTime();
         } else if (item == Items.field_151031_f) {
            time = 20;
         }

         if (item instanceof LOTRItemSpear) {
            time = ((LOTRItemSpear)item).getMaxDrawTime();
         }

         if (item instanceof LOTRItemBlowgun) {
            time = ((LOTRItemBlowgun)item).getMaxDrawTime();
         }
      }

      return time > 0 ? 1.0F / ((float)time / (float)base) : 0.0F;
   }

   public static float getRangedDamageFactor(ItemStack itemstack, boolean launchSpeedOnly) {
      float baseArrowFactor = 2.0F;
      float weaponFactor = 0.0F;
      if (itemstack != null) {
         Item item = itemstack.func_77973_b();
         int power;
         if (item instanceof LOTRItemCrossbow) {
            weaponFactor = baseArrowFactor * (float)((LOTRItemCrossbow)item).boltDamageFactor;
            weaponFactor *= LOTREnchantmentHelper.calcRangedDamageFactor(itemstack);
            if (!launchSpeedOnly) {
               power = EnchantmentHelper.func_77506_a(Enchantment.field_77345_t.field_77352_x, itemstack);
               if (power > 0) {
                  weaponFactor = (float)((double)weaponFactor + (double)power * 0.5D + 0.5D);
               }

               weaponFactor *= 2.0F;
            }
         } else if (item instanceof ItemBow) {
            weaponFactor = baseArrowFactor;
            if (item instanceof LOTRItemBow) {
               weaponFactor = baseArrowFactor * (float)((LOTRItemBow)item).arrowDamageFactor;
            }

            weaponFactor *= LOTREnchantmentHelper.calcRangedDamageFactor(itemstack);
            if (!launchSpeedOnly) {
               power = EnchantmentHelper.func_77506_a(Enchantment.field_77345_t.field_77352_x, itemstack);
               if (power > 0) {
                  weaponFactor = (float)((double)weaponFactor + (double)power * 0.5D + 0.5D);
               }
            }
         } else if (item instanceof LOTRItemBlowgun) {
            weaponFactor = baseArrowFactor;
            if (!launchSpeedOnly) {
               weaponFactor = baseArrowFactor * (1.0F / baseArrowFactor);
            }

            weaponFactor *= LOTREnchantmentHelper.calcRangedDamageFactor(itemstack);
         } else if (item instanceof LOTRItemSpear) {
            weaponFactor = ((LOTRItemSpear)item).getRangedDamageMultiplier(itemstack, (Entity)null, (Entity)null);
         } else if (item instanceof LOTRItemThrowingAxe) {
            weaponFactor = ((LOTRItemThrowingAxe)item).getRangedDamageMultiplier(itemstack, (Entity)null, (Entity)null);
         }
      }

      return weaponFactor > 0.0F ? weaponFactor / baseArrowFactor : 0.0F;
   }

   public static int getRangedKnockback(ItemStack itemstack) {
      return !isMeleeWeapon(itemstack) && (itemstack == null || !(itemstack.func_77973_b() instanceof LOTRItemThrowingAxe)) ? EnchantmentHelper.func_77506_a(Enchantment.field_77344_u.field_77352_x, itemstack) + LOTREnchantmentHelper.calcRangedKnockback(itemstack) : getTotalKnockback(itemstack);
   }

   public static int getArmorProtection(ItemStack itemstack) {
      if (itemstack != null) {
         Item item = itemstack.func_77973_b();
         if (item instanceof ItemArmor) {
            ItemArmor armor = (ItemArmor)item;
            int i = armor.field_77879_b;
            i += LOTREnchantmentHelper.calcCommonArmorProtection(itemstack);
            return i;
         }
      }

      return 0;
   }

   public static int getTotalArmorValue(EntityPlayer entityplayer) {
      int protection = 0;

      for(int i = 0; i < entityplayer.field_71071_by.field_70460_b.length; ++i) {
         ItemStack stack = entityplayer.field_71071_by.field_70460_b[i];
         if (stack != null && stack.func_77973_b() instanceof ISpecialArmor) {
            protection += ((ISpecialArmor)stack.func_77973_b()).getArmorDisplay(entityplayer, stack, i);
         } else if (stack != null && stack.func_77973_b() instanceof ItemArmor) {
            protection += getArmorProtection(stack);
         }
      }

      return protection;
   }

   static {
      registerMeleeSpeed(LOTRItemDagger.class, 1.5F);
      registerMeleeSpeed(LOTRItemSpear.class, 0.833F);
      registerMeleeSpeed(LOTRItemPolearm.class, 0.667F);
      registerMeleeSpeed(LOTRItemPolearmLong.class, 0.5F);
      registerMeleeSpeed(LOTRItemLance.class, 0.5F);
      registerMeleeSpeed(LOTRItemBattleaxe.class, 0.75F);
      registerMeleeSpeed(LOTRItemHammer.class, 0.667F);
      registerMeleeReach(LOTRItemDagger.class, 0.75F);
      registerMeleeReach(LOTRItemSpear.class, 1.5F);
      registerMeleeReach(LOTRItemPolearm.class, 1.5F);
      registerMeleeReach(LOTRItemPolearmLong.class, 2.0F);
      registerMeleeReach(LOTRItemLance.class, 2.0F);
      registerMeleeReach(LOTRItemBalrogWhip.class, 1.5F);
      registerMeleeExtraKnockback(LOTRItemHammer.class, 1);
      registerMeleeExtraKnockback(LOTRItemLance.class, 1);
      MAX_MODIFIABLE_REACH = 2.0F;
      MAX_MODIFIABLE_SPEED = 1.6F;
      MAX_MODIFIABLE_KNOCKBACK = 2;
   }
}
