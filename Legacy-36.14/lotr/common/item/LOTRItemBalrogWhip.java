package lotr.common.item;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import lotr.common.LOTRBannerProtection;
import lotr.common.LOTRMod;
import lotr.common.enchant.LOTREnchantment;
import lotr.common.enchant.LOTREnchantmentHelper;
import net.minecraft.block.BlockLeavesBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class LOTRItemBalrogWhip extends LOTRItemSword {
   public LOTRItemBalrogWhip() {
      super(LOTRMaterial.UTUMNO);
      this.lotrWeaponDamage = 7.0F;
      this.func_77656_e(1000);
   }

   public boolean func_77644_a(ItemStack itemstack, EntityLivingBase hitEntity, EntityLivingBase user) {
      if (super.func_77644_a(itemstack, hitEntity, user)) {
         this.checkIncompatibleModifiers(itemstack);
         if (!user.field_70170_p.field_72995_K && hitEntity.field_70737_aN == hitEntity.field_70738_aO) {
            this.launchWhip(user, hitEntity);
         }

         return true;
      } else {
         return false;
      }
   }

   public EnumAction func_77661_b(ItemStack itemstack) {
      return EnumAction.bow;
   }

   public int func_77626_a(ItemStack itemstack) {
      return 20;
   }

   public ItemStack func_77659_a(ItemStack itemstack, World world, EntityPlayer entityplayer) {
      entityplayer.func_71008_a(itemstack, this.func_77626_a(itemstack));
      return itemstack;
   }

   public ItemStack func_77654_b(ItemStack itemstack, World world, EntityPlayer entityplayer) {
      this.checkIncompatibleModifiers(itemstack);
      entityplayer.func_71038_i();
      if (!world.field_72995_K) {
         this.launchWhip(entityplayer, (EntityLivingBase)null);
      }

      itemstack.func_77972_a(1, entityplayer);
      return itemstack;
   }

   private void launchWhip(EntityLivingBase user, EntityLivingBase hitEntity) {
      World world = user.field_70170_p;
      world.func_72956_a(user, "lotr:item.balrogWhip", 2.0F, 0.7F + field_77697_d.nextFloat() * 0.6F);
      double range = 16.0D;
      Vec3 position = Vec3.func_72443_a(user.field_70165_t, user.field_70163_u, user.field_70161_v);
      Vec3 look = user.func_70040_Z();
      Vec3 sight = position.func_72441_c(look.field_72450_a * range, look.field_72448_b * range, look.field_72449_c * range);
      float sightWidth = 1.0F;
      List list = world.func_72839_b(user, user.field_70121_D.func_72321_a(look.field_72450_a * range, look.field_72448_b * range, look.field_72449_c * range).func_72314_b((double)sightWidth, (double)sightWidth, (double)sightWidth));
      List whipTargets = new ArrayList();

      for(int i = 0; i < list.size(); ++i) {
         Entity obj = (Entity)list.get(i);
         if (obj instanceof EntityLivingBase) {
            EntityLivingBase entity = (EntityLivingBase)obj;
            if ((entity != user.field_70154_o || entity.canRiderInteract()) && entity.func_70067_L()) {
               float width = 1.0F;
               AxisAlignedBB axisalignedbb = entity.field_70121_D.func_72314_b((double)width, (double)width, (double)width);
               MovingObjectPosition movingobjectposition = axisalignedbb.func_72327_a(position, sight);
               if (axisalignedbb.func_72318_a(position)) {
                  whipTargets.add(entity);
               } else if (movingobjectposition != null) {
                  whipTargets.add(entity);
               }
            }
         }
      }

      Iterator var34 = whipTargets.iterator();

      while(true) {
         EntityLivingBase entity;
         do {
            if (!var34.hasNext()) {
               Vec3 eyeHeight = position.func_72441_c(0.0D, (double)user.func_70047_e(), 0.0D);

               for(int l = 4; l < (int)range; ++l) {
                  double d = (double)l / range;
                  double dx = sight.field_72450_a - eyeHeight.field_72450_a;
                  double dy = sight.field_72448_b - eyeHeight.field_72448_b;
                  double dz = sight.field_72449_c - eyeHeight.field_72449_c;
                  double x = eyeHeight.field_72450_a + dx * d;
                  double y = eyeHeight.field_72448_b + dy * d;
                  double z = eyeHeight.field_72449_c + dz * d;
                  int i = MathHelper.func_76128_c(x);
                  int j = MathHelper.func_76128_c(y);
                  int k = MathHelper.func_76128_c(z);
                  boolean placedFire = false;

                  for(int j1 = j - 3; j1 <= j + 3; ++j1) {
                     if ((World.func_147466_a(world, i, j1 - 1, k) || world.func_147439_a(i, j1 - 1, k) instanceof BlockLeavesBase) && world.func_147439_a(i, j1, k).isReplaceable(world, i, j1, k)) {
                        boolean protection = false;
                        if (user instanceof EntityPlayer) {
                           protection = LOTRBannerProtection.isProtected(world, i, j1, k, LOTRBannerProtection.forPlayer((EntityPlayer)user, LOTRBannerProtection.Permission.FULL), false);
                        } else if (user instanceof EntityLiving) {
                           protection = LOTRBannerProtection.isProtected(world, i, j1, k, LOTRBannerProtection.forNPC((EntityLiving)user), false);
                        }

                        if (!protection) {
                           world.func_147465_d(i, j1, k, Blocks.field_150480_ab, 0, 3);
                           placedFire = true;
                           break;
                        }
                     }
                  }

                  if (!placedFire) {
                     break;
                  }
               }

               return;
            }

            entity = (EntityLivingBase)var34.next();
         } while(entity != hitEntity && !entity.func_70097_a(DamageSource.func_76358_a(user), 1.0F));

         entity.func_70015_d(5);
      }
   }

   public int func_77619_b() {
      return 0;
   }

   public boolean func_82789_a(ItemStack itemstack, ItemStack repairItem) {
      return repairItem.func_77973_b() == LOTRMod.balrogFire;
   }

   private void checkIncompatibleModifiers(ItemStack itemstack) {
      LOTREnchantment[] var2 = new LOTREnchantment[]{LOTREnchantment.fire, LOTREnchantment.chill};
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         LOTREnchantment ench = var2[var4];
         if (LOTREnchantmentHelper.hasEnchant(itemstack, ench)) {
            LOTREnchantmentHelper.removeEnchant(itemstack, ench);
         }
      }

   }
}
