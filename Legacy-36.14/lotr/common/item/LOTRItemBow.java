package lotr.common.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Arrays;
import java.util.Iterator;
import lotr.client.render.item.LOTRRenderBow;
import lotr.common.LOTRCreativeTabs;
import lotr.common.LOTRMod;
import lotr.common.enchant.LOTREnchantment;
import lotr.common.enchant.LOTREnchantmentHelper;
import lotr.common.entity.item.LOTREntityArrowPoisoned;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;

public class LOTRItemBow extends ItemBow {
   private ToolMaterial bowMaterial;
   public final double arrowDamageFactor;
   private int bowPullTime;
   public static final float MIN_BOW_DRAW_AMOUNT = 0.65F;
   @SideOnly(Side.CLIENT)
   private IIcon[] bowPullIcons;

   public LOTRItemBow(LOTRMaterial material) {
      this(material.toToolMaterial(), 1.0D);
   }

   public LOTRItemBow(LOTRMaterial material, double d) {
      this(material.toToolMaterial(), d);
   }

   public LOTRItemBow(ToolMaterial material) {
      this(material, 1.0D);
   }

   public LOTRItemBow(ToolMaterial material, double d) {
      this.bowMaterial = material;
      this.func_77656_e((int)((float)material.func_77997_a() * 1.5F));
      this.func_77637_a(LOTRCreativeTabs.tabCombat);
      this.arrowDamageFactor = d;
      this.bowPullTime = 20;
   }

   public LOTRItemBow setDrawTime(int i) {
      this.bowPullTime = i;
      return this;
   }

   public void func_77615_a(ItemStack itemstack, World world, EntityPlayer entityplayer, int i) {
      int useTick = this.func_77626_a(itemstack) - i;
      ArrowLooseEvent event = new ArrowLooseEvent(entityplayer, itemstack, useTick);
      MinecraftForge.EVENT_BUS.post(event);
      if (!event.isCanceled()) {
         useTick = event.charge;
         ItemStack arrowItem = null;
         int arrowSlot = this.getInvArrowSlot(entityplayer);
         if (arrowSlot >= 0) {
            arrowItem = entityplayer.field_71071_by.field_70462_a[arrowSlot];
         }

         boolean shouldConsume = this.shouldConsumeArrow(itemstack, entityplayer);
         if (arrowItem == null && !shouldConsume) {
            arrowItem = new ItemStack(Items.field_151032_g);
         }

         if (arrowItem != null) {
            float charge = (float)useTick / (float)this.getMaxDrawTime();
            if (charge < 0.65F) {
               return;
            }

            charge = (charge * charge + charge * 2.0F) / 3.0F;
            charge = Math.min(charge, 1.0F);
            Object arrow;
            if (arrowItem.func_77973_b() == LOTRMod.arrowPoisoned) {
               arrow = new LOTREntityArrowPoisoned(world, entityplayer, charge * 2.0F * getLaunchSpeedFactor(itemstack));
            } else {
               arrow = new EntityArrow(world, entityplayer, charge * 2.0F * getLaunchSpeedFactor(itemstack));
            }

            if (((EntityArrow)arrow).func_70242_d() < 1.0D) {
               ((EntityArrow)arrow).func_70239_b(1.0D);
            }

            if (charge >= 1.0F) {
               ((EntityArrow)arrow).func_70243_d(true);
            }

            applyBowModifiers((EntityArrow)arrow, itemstack);
            itemstack.func_77972_a(1, entityplayer);
            world.func_72956_a(entityplayer, "random.bow", 1.0F, 1.0F / (field_77697_d.nextFloat() * 0.4F + 1.2F) + charge * 0.5F);
            if (!shouldConsume) {
               ((EntityArrow)arrow).field_70251_a = 2;
            } else if (arrowSlot >= 0) {
               --arrowItem.field_77994_a;
               if (arrowItem.field_77994_a <= 0) {
                  entityplayer.field_71071_by.field_70462_a[arrowSlot] = null;
               }
            }

            if (!world.field_72995_K) {
               world.func_72838_d((Entity)arrow);
            }
         }

      }
   }

   public static float getLaunchSpeedFactor(ItemStack itemstack) {
      float f = 1.0F;
      if (itemstack != null) {
         if (itemstack.func_77973_b() instanceof LOTRItemBow) {
            f = (float)((double)f * ((LOTRItemBow)itemstack.func_77973_b()).arrowDamageFactor);
         }

         f *= LOTREnchantmentHelper.calcRangedDamageFactor(itemstack);
      }

      return f;
   }

   public static void applyBowModifiers(EntityArrow arrow, ItemStack itemstack) {
      int power = EnchantmentHelper.func_77506_a(Enchantment.field_77345_t.field_77352_x, itemstack);
      if (power > 0) {
         arrow.func_70239_b(arrow.func_70242_d() + (double)power * 0.5D + 0.5D);
      }

      int punch = EnchantmentHelper.func_77506_a(Enchantment.field_77344_u.field_77352_x, itemstack);
      punch += LOTREnchantmentHelper.calcRangedKnockback(itemstack);
      if (punch > 0) {
         arrow.func_70240_a(punch);
      }

      int fireAspect = EnchantmentHelper.func_77506_a(Enchantment.field_77343_v.field_77352_x, itemstack) + LOTREnchantmentHelper.calcFireAspect(itemstack);
      if (fireAspect > 0) {
         arrow.func_70015_d(100);
      }

      Iterator var5 = LOTREnchantment.allEnchantments.iterator();

      while(var5.hasNext()) {
         LOTREnchantment ench = (LOTREnchantment)var5.next();
         if (ench.applyToProjectile() && LOTREnchantmentHelper.hasEnchant(itemstack, ench)) {
            LOTREnchantmentHelper.setProjectileEnchantment(arrow, ench);
         }
      }

   }

   public int getMaxDrawTime() {
      return this.bowPullTime;
   }

   public ItemStack func_77659_a(ItemStack itemstack, World world, EntityPlayer entityplayer) {
      ArrowNockEvent event = new ArrowNockEvent(entityplayer, itemstack);
      MinecraftForge.EVENT_BUS.post(event);
      if (event.isCanceled()) {
         return event.result;
      } else {
         if (!this.shouldConsumeArrow(itemstack, entityplayer) || this.getInvArrowSlot(entityplayer) >= 0) {
            entityplayer.func_71008_a(itemstack, this.func_77626_a(itemstack));
         }

         return itemstack;
      }
   }

   private boolean shouldConsumeArrow(ItemStack itemstack, EntityPlayer entityplayer) {
      return !entityplayer.field_71075_bZ.field_75098_d && EnchantmentHelper.func_77506_a(Enchantment.field_77342_w.field_77352_x, itemstack) == 0;
   }

   private int getInvArrowSlot(EntityPlayer entityplayer) {
      for(int slot = 0; slot < entityplayer.field_71071_by.field_70462_a.length; ++slot) {
         ItemStack invItem = entityplayer.field_71071_by.field_70462_a[slot];
         if (invItem != null && (invItem.func_77973_b() == Items.field_151032_g || invItem.func_77973_b() == LOTRMod.arrowPoisoned)) {
            return slot;
         }
      }

      return -1;
   }

   public int func_77619_b() {
      return 1 + this.bowMaterial.func_77995_e() / 5;
   }

   public boolean func_82789_a(ItemStack itemstack, ItemStack repairItem) {
      return repairItem.func_77973_b() == Items.field_151007_F ? true : super.func_82789_a(itemstack, repairItem);
   }

   public LOTRItemBow.BowState getBowState(EntityLivingBase entity, ItemStack usingItem, int useRemaining) {
      if (entity instanceof EntityPlayer && usingItem != null && usingItem.func_77973_b() == this) {
         int ticksInUse = usingItem.func_77988_m() - useRemaining;
         double useAmount = (double)ticksInUse / (double)this.bowPullTime;
         if (useAmount >= 0.9D) {
            return LOTRItemBow.BowState.PULL_2;
         }

         if (useAmount > 0.65D) {
            return LOTRItemBow.BowState.PULL_1;
         }

         if (useAmount > 0.0D) {
            return LOTRItemBow.BowState.PULL_0;
         }
      }

      return LOTRItemBow.BowState.HELD;
   }

   @SideOnly(Side.CLIENT)
   public IIcon getIcon(ItemStack itemstack, int renderPass, EntityPlayer entityplayer, ItemStack usingItem, int useRemaining) {
      LOTRItemBow.BowState bowState = this.getBowState(entityplayer, usingItem, useRemaining);
      if (bowState == LOTRItemBow.BowState.PULL_0) {
         return this.bowPullIcons[0];
      } else if (bowState == LOTRItemBow.BowState.PULL_1) {
         return this.bowPullIcons[1];
      } else {
         return bowState == LOTRItemBow.BowState.PULL_2 ? this.bowPullIcons[2] : this.field_77791_bV;
      }
   }

   @SideOnly(Side.CLIENT)
   public void func_94581_a(IIconRegister iconregister) {
      this.field_77791_bV = iconregister.func_94245_a(this.func_111208_A());
      this.bowPullIcons = new IIcon[3];
      IItemRenderer bowRenderer = MinecraftForgeClient.getItemRenderer(new ItemStack(this), ItemRenderType.EQUIPPED_FIRST_PERSON);
      if (bowRenderer instanceof LOTRRenderBow && ((LOTRRenderBow)bowRenderer).isLargeBow()) {
         Arrays.fill(this.bowPullIcons, this.field_77791_bV);
      } else {
         this.bowPullIcons[0] = iconregister.func_94245_a(this.func_111208_A() + "_" + LOTRItemBow.BowState.PULL_0.iconName);
         this.bowPullIcons[1] = iconregister.func_94245_a(this.func_111208_A() + "_" + LOTRItemBow.BowState.PULL_1.iconName);
         this.bowPullIcons[2] = iconregister.func_94245_a(this.func_111208_A() + "_" + LOTRItemBow.BowState.PULL_2.iconName);
      }

   }

   public static enum BowState {
      HELD(""),
      PULL_0("pull_0"),
      PULL_1("pull_1"),
      PULL_2("pull_2");

      public final String iconName;

      private BowState(String s) {
         this.iconName = s;
      }
   }
}
