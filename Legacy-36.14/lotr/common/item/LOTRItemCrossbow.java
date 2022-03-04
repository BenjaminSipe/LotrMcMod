package lotr.common.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Iterator;
import java.util.List;
import lotr.common.LOTRCreativeTabs;
import lotr.common.LOTRMod;
import lotr.common.enchant.LOTREnchantment;
import lotr.common.enchant.LOTREnchantmentHelper;
import lotr.common.entity.projectile.LOTREntityCrossbowBolt;
import lotr.common.recipe.LOTRRecipes;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class LOTRItemCrossbow extends ItemBow {
   public final double boltDamageFactor;
   private ToolMaterial crossbowMaterial;
   @SideOnly(Side.CLIENT)
   private IIcon[] crossbowPullIcons;

   public LOTRItemCrossbow(LOTRMaterial material) {
      this(material.toToolMaterial());
   }

   public LOTRItemCrossbow(ToolMaterial material) {
      this.func_77637_a(LOTRCreativeTabs.tabCombat);
      this.crossbowMaterial = material;
      this.func_77656_e((int)((float)this.crossbowMaterial.func_77997_a() * 1.25F));
      this.func_77625_d(1);
      this.boltDamageFactor = (double)(1.0F + Math.max(0.0F, (this.crossbowMaterial.func_78000_c() - 2.0F) * 0.1F));
   }

   public ItemStack func_77659_a(ItemStack itemstack, World world, EntityPlayer entityplayer) {
      if (isLoaded(itemstack)) {
         ItemStack boltItem = getLoaded(itemstack);
         if (boltItem != null) {
            float charge = 1.0F;
            ItemStack shotBolt = boltItem.func_77946_l();
            shotBolt.field_77994_a = 1;
            LOTREntityCrossbowBolt bolt = new LOTREntityCrossbowBolt(world, entityplayer, shotBolt, charge * 2.0F * getCrossbowLaunchSpeedFactor(itemstack));
            if (bolt.boltDamageFactor < 1.0D) {
               bolt.boltDamageFactor = 1.0D;
            }

            if (charge >= 1.0F) {
               bolt.setIsCritical(true);
            }

            applyCrossbowModifiers(bolt, itemstack);
            if (!this.shouldConsumeBolt(itemstack, entityplayer)) {
               bolt.canBePickedUp = 2;
            }

            if (!world.field_72995_K) {
               world.func_72838_d(bolt);
            }

            world.func_72956_a(entityplayer, "lotr:item.crossbow", 1.0F, 1.0F / (field_77697_d.nextFloat() * 0.4F + 1.2F) + charge * 0.5F);
            itemstack.func_77972_a(1, entityplayer);
            if (!world.field_72995_K) {
               this.setLoaded(itemstack, (ItemStack)null);
            }
         }
      } else if (!this.shouldConsumeBolt(itemstack, entityplayer) || this.getInvBoltSlot(entityplayer) >= 0) {
         entityplayer.func_71008_a(itemstack, this.func_77626_a(itemstack));
      }

      return itemstack;
   }

   public static float getCrossbowLaunchSpeedFactor(ItemStack itemstack) {
      float f = 1.0F;
      if (itemstack != null) {
         if (itemstack.func_77973_b() instanceof LOTRItemCrossbow) {
            f = (float)((double)f * ((LOTRItemCrossbow)itemstack.func_77973_b()).boltDamageFactor);
         }

         f *= LOTREnchantmentHelper.calcRangedDamageFactor(itemstack);
      }

      return f;
   }

   public static void applyCrossbowModifiers(LOTREntityCrossbowBolt bolt, ItemStack itemstack) {
      int power = EnchantmentHelper.func_77506_a(Enchantment.field_77345_t.field_77352_x, itemstack);
      if (power > 0) {
         bolt.boltDamageFactor += (double)power * 0.5D + 0.5D;
      }

      int punch = EnchantmentHelper.func_77506_a(Enchantment.field_77344_u.field_77352_x, itemstack);
      punch += LOTREnchantmentHelper.calcRangedKnockback(itemstack);
      if (punch > 0) {
         bolt.knockbackStrength = punch;
      }

      int fireAspect = EnchantmentHelper.func_77506_a(Enchantment.field_77343_v.field_77352_x, itemstack) + LOTREnchantmentHelper.calcFireAspect(itemstack);
      if (fireAspect > 0) {
         bolt.func_70015_d(100);
      }

      Iterator var5 = LOTREnchantment.allEnchantments.iterator();

      while(var5.hasNext()) {
         LOTREnchantment ench = (LOTREnchantment)var5.next();
         if (ench.applyToProjectile() && LOTREnchantmentHelper.hasEnchant(itemstack, ench)) {
            LOTREnchantmentHelper.setProjectileEnchantment(bolt, ench);
         }
      }

   }

   public void onUsingTick(ItemStack itemstack, EntityPlayer entityplayer, int count) {
      World world = entityplayer.field_70170_p;
      if (!world.field_72995_K && !isLoaded(itemstack) && this.func_77626_a(itemstack) - count == this.getMaxDrawTime()) {
         world.func_72956_a(entityplayer, "lotr:item.crossbowLoad", 1.0F, 1.5F + world.field_73012_v.nextFloat() * 0.2F);
      }

   }

   public void func_77615_a(ItemStack itemstack, World world, EntityPlayer entityplayer, int useTick) {
      int ticksInUse = this.func_77626_a(itemstack) - useTick;
      if (ticksInUse >= this.getMaxDrawTime() && !isLoaded(itemstack)) {
         ItemStack boltItem = null;
         int boltSlot = this.getInvBoltSlot(entityplayer);
         if (boltSlot >= 0) {
            boltItem = entityplayer.field_71071_by.field_70462_a[boltSlot];
         }

         boolean shouldConsume = this.shouldConsumeBolt(itemstack, entityplayer);
         if (boltItem == null && !shouldConsume) {
            boltItem = new ItemStack(LOTRMod.crossbowBolt);
         }

         if (boltItem != null) {
            if (shouldConsume && boltSlot >= 0) {
               --boltItem.field_77994_a;
               if (boltItem.field_77994_a <= 0) {
                  entityplayer.field_71071_by.field_70462_a[boltSlot] = null;
               }
            }

            if (!world.field_72995_K) {
               this.setLoaded(itemstack, boltItem.func_77946_l());
            }
         }

         entityplayer.func_71041_bz();
      }

   }

   public int getMaxDrawTime() {
      return 50;
   }

   public int func_77626_a(ItemStack itemstack) {
      return 72000;
   }

   public static boolean isLoaded(ItemStack itemstack) {
      return getLoaded(itemstack) != null;
   }

   public static ItemStack getLoaded(ItemStack itemstack) {
      if (itemstack != null && itemstack.func_77973_b() instanceof LOTRItemCrossbow) {
         NBTTagCompound nbt = itemstack.func_77978_p();
         if (nbt == null) {
            return null;
         }

         if (nbt.func_74764_b("LOTRCrossbowAmmo")) {
            NBTTagCompound ammoData = nbt.func_74775_l("LOTRCrossbowAmmo");
            return ItemStack.func_77949_a(ammoData);
         }

         if (nbt.func_74764_b("LOTRCrossbowLoaded")) {
            return new ItemStack(LOTRMod.crossbowBolt);
         }
      }

      return null;
   }

   private void setLoaded(ItemStack itemstack, ItemStack ammo) {
      if (itemstack != null && itemstack.func_77973_b() instanceof LOTRItemCrossbow) {
         NBTTagCompound nbt = itemstack.func_77978_p();
         if (nbt == null) {
            nbt = new NBTTagCompound();
            itemstack.func_77982_d(nbt);
         }

         if (ammo != null) {
            NBTTagCompound ammoData = new NBTTagCompound();
            ammo.func_77955_b(ammoData);
            nbt.func_74782_a("LOTRCrossbowAmmo", ammoData);
         } else {
            nbt.func_82580_o("LOTRCrossbowAmmo");
         }

         if (nbt.func_74764_b("LOTRCrossbowLoaded")) {
            nbt.func_82580_o("LOTRCrossbowLoaded");
         }
      }

   }

   private boolean shouldConsumeBolt(ItemStack itemstack, EntityPlayer entityplayer) {
      return !entityplayer.field_71075_bZ.field_75098_d && EnchantmentHelper.func_77506_a(Enchantment.field_77342_w.field_77352_x, itemstack) == 0;
   }

   private int getInvBoltSlot(EntityPlayer entityplayer) {
      for(int slot = 0; slot < entityplayer.field_71071_by.field_70462_a.length; ++slot) {
         ItemStack invItem = entityplayer.field_71071_by.field_70462_a[slot];
         if (invItem != null && invItem.func_77973_b() instanceof LOTRItemCrossbowBolt) {
            return slot;
         }
      }

      return -1;
   }

   public String func_77653_i(ItemStack itemstack) {
      String name = super.func_77653_i(itemstack);
      if (isLoaded(itemstack)) {
         name = StatCollector.func_74837_a("item.lotr.crossbow.loaded", new Object[]{name});
      }

      return name;
   }

   @SideOnly(Side.CLIENT)
   public void func_77624_a(ItemStack itemstack, EntityPlayer entityplayer, List list, boolean flag) {
      ItemStack ammo = getLoaded(itemstack);
      if (ammo != null) {
         String ammoName = ammo.func_82833_r();
         list.add(StatCollector.func_74837_a("item.lotr.crossbow.loadedItem", new Object[]{ammoName}));
      }

   }

   public int func_77619_b() {
      return 1 + this.crossbowMaterial.func_77995_e() / 5;
   }

   public ToolMaterial getCrossbowMaterial() {
      return this.crossbowMaterial;
   }

   public boolean func_82789_a(ItemStack itemstack, ItemStack repairItem) {
      return LOTRRecipes.checkItemEquals(this.crossbowMaterial.getRepairItemStack(), repairItem) ? true : super.func_82789_a(itemstack, repairItem);
   }

   @SideOnly(Side.CLIENT)
   public IIcon getIcon(ItemStack itemstack, int renderPass, EntityPlayer entityplayer, ItemStack usingItem, int useRemaining) {
      if (isLoaded(itemstack)) {
         return this.crossbowPullIcons[2];
      } else {
         if (usingItem != null && usingItem.func_77973_b() == this) {
            int ticksInUse = usingItem.func_77988_m() - useRemaining;
            double useAmount = (double)ticksInUse / (double)this.getMaxDrawTime();
            if (useAmount >= 1.0D) {
               return this.crossbowPullIcons[2];
            }

            if (useAmount > 0.5D) {
               return this.crossbowPullIcons[1];
            }

            if (useAmount > 0.0D) {
               return this.crossbowPullIcons[0];
            }
         }

         return this.field_77791_bV;
      }
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_77650_f(ItemStack itemstack) {
      return isLoaded(itemstack) ? this.crossbowPullIcons[2] : this.field_77791_bV;
   }

   public IIcon getIcon(ItemStack itemstack, int pass) {
      return this.func_77650_f(itemstack);
   }

   @SideOnly(Side.CLIENT)
   public void func_94581_a(IIconRegister iconregister) {
      this.field_77791_bV = iconregister.func_94245_a(this.func_111208_A());
      this.crossbowPullIcons = new IIcon[3];
      this.crossbowPullIcons[0] = iconregister.func_94245_a(this.func_111208_A() + "_" + LOTRItemBow.BowState.PULL_0.iconName);
      this.crossbowPullIcons[1] = iconregister.func_94245_a(this.func_111208_A() + "_" + LOTRItemBow.BowState.PULL_1.iconName);
      this.crossbowPullIcons[2] = iconregister.func_94245_a(this.func_111208_A() + "_" + LOTRItemBow.BowState.PULL_2.iconName);
   }
}
