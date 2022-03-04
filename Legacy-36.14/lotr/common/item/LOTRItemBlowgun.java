package lotr.common.item;

import java.util.Iterator;
import lotr.common.LOTRCreativeTabs;
import lotr.common.LOTRMod;
import lotr.common.enchant.LOTREnchantment;
import lotr.common.enchant.LOTREnchantmentHelper;
import lotr.common.entity.projectile.LOTREntityDart;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.world.World;

public class LOTRItemBlowgun extends Item {
   public LOTRItemBlowgun(LOTRMaterial material) {
      this(material.toToolMaterial());
   }

   public LOTRItemBlowgun(ToolMaterial material) {
      this.func_77625_d(1);
      this.func_77656_e(material.func_77997_a());
      this.func_77637_a(LOTRCreativeTabs.tabCombat);
      this.func_77664_n();
   }

   public void func_77615_a(ItemStack itemstack, World world, EntityPlayer entityplayer, int i) {
      ItemStack dartItem = null;
      int dartSlot = -1;

      int useTick;
      for(useTick = 0; useTick < entityplayer.field_71071_by.field_70462_a.length; ++useTick) {
         ItemStack invItem = entityplayer.field_71071_by.field_70462_a[useTick];
         if (invItem != null && invItem.func_77973_b() instanceof LOTRItemDart) {
            dartItem = invItem;
            dartSlot = useTick;
            break;
         }
      }

      if (dartItem == null && entityplayer.field_71075_bZ.field_75098_d) {
         dartItem = new ItemStack(LOTRMod.tauredainDart);
      }

      if (dartItem != null) {
         useTick = this.func_77626_a(itemstack) - i;
         float charge = (float)useTick / (float)this.getMaxDrawTime();
         if (charge < 0.65F) {
            return;
         }

         charge = (charge * charge + charge * 2.0F) / 3.0F;
         charge = Math.min(charge, 1.0F);
         itemstack.func_77972_a(1, entityplayer);
         if (!entityplayer.field_71075_bZ.field_75098_d && dartSlot >= 0) {
            --dartItem.field_77994_a;
            if (dartItem.field_77994_a <= 0) {
               entityplayer.field_71071_by.field_70462_a[dartSlot] = null;
            }
         }

         world.func_72956_a(entityplayer, "lotr:item.dart", 1.0F, 1.0F / (field_77697_d.nextFloat() * 0.4F + 1.2F) + charge * 0.5F);
         if (!world.field_72995_K) {
            ItemStack shotDart = dartItem.func_77946_l();
            shotDart.field_77994_a = 1;
            LOTREntityDart dart = ((LOTRItemDart)shotDart.func_77973_b()).createDart(world, entityplayer, shotDart, charge * 2.0F * getBlowgunLaunchSpeedFactor(itemstack));
            if (dart.dartDamageFactor < 1.0F) {
               dart.dartDamageFactor = 1.0F;
            }

            if (charge >= 1.0F) {
               dart.setIsCritical(true);
            }

            applyBlowgunModifiers(dart, itemstack);
            if (entityplayer.field_71075_bZ.field_75098_d) {
               dart.canBePickedUp = 2;
            }

            world.func_72838_d(dart);
         }
      }

   }

   public static float getBlowgunLaunchSpeedFactor(ItemStack itemstack) {
      float f = 1.0F;
      if (itemstack != null) {
         f *= LOTREnchantmentHelper.calcRangedDamageFactor(itemstack);
      }

      return f;
   }

   public static void applyBlowgunModifiers(LOTREntityDart dart, ItemStack itemstack) {
      int punch = LOTREnchantmentHelper.calcRangedKnockback(itemstack);
      if (punch > 0) {
         dart.knockbackStrength = punch;
      }

      int fireAspect = EnchantmentHelper.func_77506_a(Enchantment.field_77343_v.field_77352_x, itemstack) + LOTREnchantmentHelper.calcFireAspect(itemstack);
      if (fireAspect > 0) {
         dart.func_70015_d(100);
      }

      Iterator var4 = LOTREnchantment.allEnchantments.iterator();

      while(var4.hasNext()) {
         LOTREnchantment ench = (LOTREnchantment)var4.next();
         if (ench.applyToProjectile() && LOTREnchantmentHelper.hasEnchant(itemstack, ench)) {
            LOTREnchantmentHelper.setProjectileEnchantment(dart, ench);
         }
      }

   }

   public int getMaxDrawTime() {
      return 5;
   }

   public ItemStack func_77659_a(ItemStack itemstack, World world, EntityPlayer entityplayer) {
      boolean anyDart = false;
      ItemStack[] var5 = entityplayer.field_71071_by.field_70462_a;
      int var6 = var5.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         ItemStack invItem = var5[var7];
         if (invItem != null && invItem.func_77973_b() instanceof LOTRItemDart) {
            anyDart = true;
            break;
         }
      }

      if (anyDart || entityplayer.field_71075_bZ.field_75098_d) {
         entityplayer.func_71008_a(itemstack, this.func_77626_a(itemstack));
      }

      return itemstack;
   }

   public EnumAction func_77661_b(ItemStack itemstack) {
      return EnumAction.bow;
   }

   public int func_77626_a(ItemStack itemstack) {
      return 72000;
   }

   public boolean func_82789_a(ItemStack itemstack, ItemStack repairItem) {
      return repairItem.func_77973_b() == Item.func_150898_a(LOTRMod.reeds);
   }
}
