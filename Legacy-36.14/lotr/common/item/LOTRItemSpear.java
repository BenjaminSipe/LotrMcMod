package lotr.common.item;

import java.util.Iterator;
import lotr.common.dispenser.LOTRDispenseSpear;
import lotr.common.enchant.LOTREnchantment;
import lotr.common.enchant.LOTREnchantmentHelper;
import lotr.common.entity.projectile.LOTREntitySpear;
import net.minecraft.block.BlockDispenser;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.world.World;

public class LOTRItemSpear extends LOTRItemSword {
   public LOTRItemSpear(LOTRMaterial material) {
      this(material.toToolMaterial());
   }

   public LOTRItemSpear(ToolMaterial material) {
      super(material);
      --this.lotrWeaponDamage;
      BlockDispenser.field_149943_a.func_82595_a(this, new LOTRDispenseSpear());
   }

   public void func_77615_a(ItemStack itemstack, World world, EntityPlayer entityplayer, int i) {
      if (entityplayer.func_70694_bm() == itemstack) {
         int useTick = this.func_77626_a(itemstack) - i;
         float charge = (float)useTick / (float)this.getMaxDrawTime();
         if (charge >= 0.1F) {
            charge = (charge * charge + charge * 2.0F) / 3.0F;
            charge = Math.min(charge, 1.0F);
            LOTREntitySpear spear = new LOTREntitySpear(world, entityplayer, itemstack.func_77946_l(), charge * 2.0F);
            if (charge >= 1.0F) {
               spear.setIsCritical(true);
            }

            int fireAspect = EnchantmentHelper.func_77506_a(Enchantment.field_77343_v.field_77352_x, itemstack) + LOTREnchantmentHelper.calcFireAspect(itemstack);
            if (fireAspect > 0) {
               spear.func_70015_d(100);
            }

            Iterator var9 = LOTREnchantment.allEnchantments.iterator();

            while(var9.hasNext()) {
               LOTREnchantment ench = (LOTREnchantment)var9.next();
               if (ench.applyToProjectile() && LOTREnchantmentHelper.hasEnchant(itemstack, ench)) {
                  LOTREnchantmentHelper.setProjectileEnchantment(spear, ench);
               }
            }

            if (entityplayer.field_71075_bZ.field_75098_d) {
               spear.canBePickedUp = 2;
            }

            world.func_72956_a(entityplayer, "random.bow", 1.0F, 1.0F / (field_77697_d.nextFloat() * 0.4F + 1.2F) + charge * 0.5F);
            if (!world.field_72995_K) {
               world.func_72838_d(spear);
            }

            if (!entityplayer.field_71075_bZ.field_75098_d) {
               --itemstack.field_77994_a;
               if (itemstack.field_77994_a <= 0) {
                  entityplayer.field_71071_by.func_70299_a(entityplayer.field_71071_by.field_70461_c, (ItemStack)null);
               }
            }

         }
      }
   }

   public int getMaxDrawTime() {
      return 20;
   }

   public EnumAction func_77661_b(ItemStack itemstack) {
      return EnumAction.bow;
   }

   public int func_77626_a(ItemStack itemstack) {
      return 72000;
   }

   public float getRangedDamageMultiplier(ItemStack itemstack, Entity shooter, Entity hit) {
      float damage = this.getLOTRWeaponDamage();
      if (shooter instanceof EntityLivingBase && hit instanceof EntityLivingBase) {
         damage += EnchantmentHelper.func_77512_a((EntityLivingBase)shooter, (EntityLivingBase)hit);
      } else {
         damage += EnchantmentHelper.func_152377_a(itemstack, EnumCreatureAttribute.UNDEFINED);
      }

      return damage * 0.7F;
   }
}
