package lotr.common.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.List;
import lotr.client.render.LOTRDrinkIcons;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRCreativeTabs;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.LOTRReflection;
import lotr.common.block.LOTRBlockMug;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.Facing;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StatCollector;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;

public class LOTRItemMug extends Item {
   private static String[] strengthNames = new String[]{"weak", "light", "moderate", "strong", "potent"};
   private static float[] strengths = new float[]{0.25F, 0.5F, 1.0F, 2.0F, 3.0F};
   private static float[] foodStrengths = new float[]{0.5F, 0.75F, 1.0F, 1.25F, 1.5F};
   public static int vesselMeta = 100;
   @SideOnly(Side.CLIENT)
   private IIcon[] drinkIcons;
   @SideOnly(Side.CLIENT)
   private IIcon liquidIcon;
   @SideOnly(Side.CLIENT)
   public static IIcon barrelGui_emptyBucketSlotIcon;
   @SideOnly(Side.CLIENT)
   public static IIcon barrelGui_emptyMugSlotIcon;
   public final boolean isFullMug;
   public final boolean isFoodDrink;
   public final boolean isBrewable;
   public final float alcoholicity;
   protected int foodHealAmount;
   protected float foodSaturationAmount;
   protected List potionEffects;
   protected int damageAmount;
   protected boolean curesEffects;

   public LOTRItemMug(boolean full, boolean food, boolean brew, float alc) {
      this.potionEffects = new ArrayList();
      if (full) {
         this.func_77625_d(1);
         this.func_77627_a(true);
         this.func_77656_e(0);
      } else {
         this.func_77625_d(64);
      }

      this.func_77637_a(LOTRCreativeTabs.tabFood);
      this.isFullMug = full;
      this.isFoodDrink = food;
      this.isBrewable = brew;
      this.alcoholicity = alc;
   }

   public LOTRItemMug(boolean full, boolean food) {
      this(full, food, false, 0.0F);
   }

   public LOTRItemMug(float alc) {
      this(true, false, true, alc);
   }

   public LOTRItemMug setDrinkStats(int i, float f) {
      this.foodHealAmount = i;
      this.foodSaturationAmount = f;
      return this;
   }

   public LOTRItemMug addPotionEffect(int i, int j) {
      this.potionEffects.add(new PotionEffect(i, j * 20));
      return this;
   }

   public LOTRItemMug setDamageAmount(int i) {
      this.damageAmount = i;
      return this;
   }

   public LOTRItemMug setCuresEffects() {
      this.curesEffects = true;
      return this;
   }

   public static boolean isItemFullDrink(ItemStack itemstack) {
      if (itemstack != null) {
         Item item = itemstack.func_77973_b();
         if (item instanceof LOTRItemMug) {
            return ((LOTRItemMug)item).isFullMug;
         }

         if (item == Items.field_151068_bn && itemstack.func_77960_j() == 0) {
            return true;
         }
      }

      return false;
   }

   public static boolean isItemEmptyDrink(ItemStack itemstack) {
      if (itemstack != null) {
         Item item = itemstack.func_77973_b();
         if (item instanceof LOTRItemMug) {
            return !((LOTRItemMug)item).isFullMug;
         }

         if (item == Items.field_151069_bo) {
            return true;
         }
      }

      return false;
   }

   public static ItemStack getEquivalentDrink(ItemStack itemstack) {
      if (itemstack != null) {
         Item item = itemstack.func_77973_b();
         if (item instanceof LOTRItemMug) {
            return itemstack;
         }

         if (item == Items.field_151068_bn && itemstack.func_77960_j() == 0) {
            ItemStack water = itemstack.func_77946_l();
            water.func_150996_a(LOTRMod.mugWater);
            setVessel(water, LOTRItemMug.Vessel.BOTTLE, false);
            return water;
         }
      }

      return itemstack;
   }

   public static ItemStack getRealDrink(ItemStack itemstack) {
      if (itemstack != null) {
         Item item = itemstack.func_77973_b();
         if (item == LOTRMod.mugWater && getVessel(itemstack) == LOTRItemMug.Vessel.BOTTLE) {
            ItemStack water = itemstack.func_77946_l();
            water.func_150996_a(Items.field_151068_bn);
            water.func_77964_b(0);
            return water;
         }
      }

      return itemstack;
   }

   private static int getStrengthMeta(int damage) {
      int i = damage % vesselMeta;
      if (i < 0 || i >= strengths.length) {
         i = 0;
      }

      return i;
   }

   public static int getStrengthMeta(ItemStack itemstack) {
      return getStrengthMeta(itemstack.func_77960_j());
   }

   public static void setStrengthMeta(ItemStack itemstack, int i) {
      LOTRItemMug.Vessel v = getVessel(itemstack);
      itemstack.func_77964_b(i);
      setVessel(itemstack, v, true);
   }

   public static float getStrength(ItemStack itemstack) {
      Item item = itemstack.func_77973_b();
      if (item instanceof LOTRItemMug && ((LOTRItemMug)item).isBrewable) {
         int i = getStrengthMeta(itemstack);
         return strengths[i];
      } else {
         return 1.0F;
      }
   }

   public static float getFoodStrength(ItemStack itemstack) {
      Item item = itemstack.func_77973_b();
      if (item instanceof LOTRItemMug && ((LOTRItemMug)item).isBrewable) {
         int i = getStrengthMeta(itemstack);
         return foodStrengths[i];
      } else {
         return 1.0F;
      }
   }

   public static LOTRItemMug.Vessel getVessel(ItemStack itemstack) {
      Item item = itemstack.func_77973_b();
      if (item instanceof LOTRItemMug) {
         LOTRItemMug itemMug = (LOTRItemMug)item;
         return itemMug.isFullMug ? getVessel(itemstack.func_77960_j()) : itemMug.getEmptyVesselType();
      } else if (item == Items.field_151069_bo) {
         return LOTRItemMug.Vessel.BOTTLE;
      } else {
         return item == Items.field_151068_bn && itemstack.func_77960_j() == 0 ? LOTRItemMug.Vessel.BOTTLE : null;
      }
   }

   private static LOTRItemMug.Vessel getVessel(int damage) {
      int i = damage / vesselMeta;
      return LOTRItemMug.Vessel.forMeta(i);
   }

   public static void setVessel(ItemStack itemstack, LOTRItemMug.Vessel v, boolean correctItem) {
      if (correctItem && itemstack.func_77973_b() == Items.field_151068_bn && itemstack.func_77960_j() == 0) {
         itemstack.func_150996_a(LOTRMod.mugWater);
         itemstack.func_77964_b(0);
      }

      int i = itemstack.func_77960_j();
      i %= vesselMeta;
      itemstack.func_77964_b(v.id * vesselMeta + i);
      if (correctItem && itemstack.func_77973_b() == LOTRMod.mugWater && v == LOTRItemMug.Vessel.BOTTLE) {
         itemstack.func_150996_a(Items.field_151068_bn);
         itemstack.func_77964_b(0);
      }

   }

   public IIcon func_77617_a(int i) {
      if (this.isFullMug) {
         if (i == -1) {
            return this.liquidIcon;
         } else {
            int vessel = getVessel(i).id;
            return this.drinkIcons[vessel];
         }
      } else {
         return super.func_77617_a(i);
      }
   }

   public void func_94581_a(IIconRegister iconregister) {
      if (this.isFullMug) {
         this.drinkIcons = new IIcon[LOTRItemMug.Vessel.values().length];

         for(int i = 0; i < LOTRItemMug.Vessel.values().length; ++i) {
            this.drinkIcons[i] = LOTRDrinkIcons.registerDrinkIcon(iconregister, this, this.func_111208_A(), LOTRItemMug.Vessel.values()[i].name);
         }

         this.liquidIcon = LOTRDrinkIcons.registerLiquidIcon(iconregister, this, this.func_111208_A());
         barrelGui_emptyBucketSlotIcon = iconregister.func_94245_a("lotr:barrel_emptyBucketSlot");
         barrelGui_emptyMugSlotIcon = iconregister.func_94245_a("lotr:barrel_emptyMugSlot");
      } else {
         super.func_94581_a(iconregister);
      }

   }

   private List convertPotionEffectsForStrength(float strength) {
      List list = new ArrayList();

      for(int i = 0; i < this.potionEffects.size(); ++i) {
         PotionEffect base = (PotionEffect)this.potionEffects.get(i);
         PotionEffect modified = new PotionEffect(base.func_76456_a(), (int)((float)base.func_76459_b() * strength));
         list.add(modified);
      }

      return list;
   }

   public static String getStrengthSubtitle(ItemStack itemstack) {
      if (itemstack != null) {
         Item item = itemstack.func_77973_b();
         if (item instanceof LOTRItemMug && ((LOTRItemMug)item).isBrewable) {
            int i = getStrengthMeta(itemstack);
            return StatCollector.func_74838_a("item.lotr.drink." + strengthNames[i]);
         }
      }

      return null;
   }

   public String func_77653_i(ItemStack itemstack) {
      if (LOTRMod.isAprilFools() && this.isFullMug) {
         return "Hooch";
      } else {
         return itemstack.func_77973_b() == LOTRMod.mugWater && getVessel(itemstack) == LOTRItemMug.Vessel.BOTTLE ? "Â§cMY DUDE YOU DONE MESSED UP" : super.func_77653_i(itemstack);
      }
   }

   @SideOnly(Side.CLIENT)
   public void func_77624_a(ItemStack itemstack, EntityPlayer entityplayer, List list, boolean flag) {
      if (this.isBrewable) {
         float strength = getStrength(itemstack);
         list.add(getStrengthSubtitle(itemstack));
         if (this.alcoholicity > 0.0F) {
            EnumChatFormatting c = EnumChatFormatting.GREEN;
            float f = this.alcoholicity * strength * 10.0F;
            if (f < 2.0F) {
               c = EnumChatFormatting.GREEN;
            } else if (f < 5.0F) {
               c = EnumChatFormatting.YELLOW;
            } else if (f < 10.0F) {
               c = EnumChatFormatting.GOLD;
            } else if (f < 20.0F) {
               c = EnumChatFormatting.RED;
            } else {
               c = EnumChatFormatting.DARK_RED;
            }

            list.add(c + StatCollector.func_74838_a("item.lotr.drink.alcoholicity") + ": " + String.format("%.2f", f) + "%");
         }

         addPotionEffectsToTooltip(itemstack, entityplayer, list, flag, this.convertPotionEffectsForStrength(strength));
      }

   }

   public static void addPotionEffectsToTooltip(ItemStack itemstack, EntityPlayer entityplayer, List list, boolean flag, List itemEffects) {
      if (!itemEffects.isEmpty()) {
         ItemStack potionEquivalent = new ItemStack(Items.field_151068_bn);
         potionEquivalent.func_77964_b(69);
         NBTTagList effectsData = new NBTTagList();

         for(int l = 0; l < itemEffects.size(); ++l) {
            PotionEffect effect = (PotionEffect)itemEffects.get(l);
            NBTTagCompound nbt = new NBTTagCompound();
            effect.func_82719_a(nbt);
            effectsData.func_74742_a(nbt);
         }

         potionEquivalent.func_77982_d(new NBTTagCompound());
         potionEquivalent.func_77978_p().func_74782_a("CustomPotionEffects", effectsData);
         List effectTooltips = new ArrayList();
         potionEquivalent.func_77973_b().func_77624_a(potionEquivalent, entityplayer, effectTooltips, flag);
         list.addAll(effectTooltips);
      }

   }

   @SideOnly(Side.CLIENT)
   public void func_150895_a(Item item, CreativeTabs tab, List list) {
      if (this.isFullMug) {
         LOTRItemMug.Vessel[] vesselTypes = new LOTRItemMug.Vessel[]{LOTRItemMug.Vessel.MUG};
         if (tab == null || tab.hasSearchBar()) {
            vesselTypes = LOTRItemMug.Vessel.values();
         }

         LOTRItemMug.Vessel[] var5 = vesselTypes;
         int var6 = vesselTypes.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            LOTRItemMug.Vessel v = var5[var7];
            if (this.isBrewable) {
               for(int str = 0; str < strengths.length; ++str) {
                  ItemStack drink = new ItemStack(item);
                  setStrengthMeta(drink, str);
                  setVessel(drink, v, true);
                  if (drink.func_77973_b() == item) {
                     list.add(drink);
                  }
               }
            } else {
               ItemStack drink = new ItemStack(item);
               setVessel(drink, v, true);
               if (drink.func_77973_b() == item) {
                  list.add(drink);
               }
            }
         }
      } else {
         super.func_150895_a(item, tab, list);
      }

   }

   protected final LOTRItemMug.Vessel getEmptyVesselType() {
      LOTRItemMug.Vessel[] var1 = LOTRItemMug.Vessel.values();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         LOTRItemMug.Vessel v = var1[var3];
         if (v.getEmptyVesselItem() == this) {
            return v;
         }
      }

      return LOTRItemMug.Vessel.MUG;
   }

   public boolean func_77648_a(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int side, float f, float f1, float f2) {
      return tryPlaceMug(itemstack, entityplayer, world, i, j, k, side);
   }

   public static boolean tryPlaceMug(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int side) {
      LOTRItemMug.Vessel vessel = getVessel(itemstack);
      if (vessel != null && vessel.canPlace) {
         Block mugBlock = vessel.getBlock();
         i += Facing.field_71586_b[side];
         j += Facing.field_71587_c[side];
         k += Facing.field_71585_d[side];
         Block block = world.func_147439_a(i, j, k);
         if (block != null && !block.isReplaceable(world, i, j, k)) {
            return false;
         } else if (block.func_149688_o() == Material.field_151586_h) {
            return false;
         } else if (entityplayer.func_82247_a(i, j, k, side, itemstack)) {
            if (!mugBlock.func_149742_c(world, i, j, k)) {
               return false;
            } else {
               int l = MathHelper.func_76128_c((double)(entityplayer.field_70177_z * 4.0F / 360.0F) + 0.5D) & 3;
               world.func_147465_d(i, j, k, mugBlock, l, 3);
               ItemStack mugFill = itemstack.func_77946_l();
               mugFill.field_77994_a = 1;
               LOTRBlockMug.setMugItem(world, i, j, k, mugFill, vessel);
               world.func_72908_a((double)i + 0.5D, (double)j + 0.5D, (double)k + 0.5D, mugBlock.field_149762_H.func_150496_b(), (mugBlock.field_149762_H.func_150497_c() + 1.0F) / 2.0F, mugBlock.field_149762_H.func_150494_d() * 0.8F);
               --itemstack.field_77994_a;
               return true;
            }
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   public int func_77626_a(ItemStack itemstack) {
      return 32;
   }

   public EnumAction func_77661_b(ItemStack itemstack) {
      return EnumAction.drink;
   }

   public ItemStack func_77659_a(ItemStack itemstack, World world, EntityPlayer entityplayer) {
      if (!this.isFullMug) {
         ItemStack filled = new ItemStack(LOTRMod.mugWater);
         setVessel(filled, this.getEmptyVesselType(), true);
         MovingObjectPosition m = this.func_77621_a(world, entityplayer, true);
         if (m == null) {
            return itemstack;
         } else {
            if (m.field_72313_a == MovingObjectType.BLOCK) {
               int i = m.field_72311_b;
               int j = m.field_72312_c;
               int k = m.field_72309_d;
               if (!world.func_72962_a(entityplayer, i, j, k)) {
                  return itemstack;
               }

               if (!entityplayer.func_82247_a(i, j, k, m.field_72310_e, itemstack)) {
                  return itemstack;
               }

               if (world.func_147439_a(i, j, k).func_149688_o() == Material.field_151586_h && world.func_72805_g(i, j, k) == 0) {
                  --itemstack.field_77994_a;
                  if (itemstack.field_77994_a <= 0) {
                     world.func_72956_a(entityplayer, "lotr:item.mug_fill", 0.5F, 0.8F + world.field_73012_v.nextFloat() * 0.4F);
                     return filled.func_77946_l();
                  }

                  if (!entityplayer.field_71071_by.func_70441_a(filled.func_77946_l())) {
                     entityplayer.func_71019_a(filled.func_77946_l(), false);
                  }

                  world.func_72956_a(entityplayer, "lotr:item.mug_fill", 0.5F, 0.8F + world.field_73012_v.nextFloat() * 0.4F);
                  return itemstack;
               }
            }

            return itemstack;
         }
      } else {
         if (this.canPlayerDrink(entityplayer)) {
            entityplayer.func_71008_a(itemstack, this.func_77626_a(itemstack));
         }

         return itemstack;
      }
   }

   public ItemStack func_77654_b(ItemStack itemstack, World world, EntityPlayer entityplayer) {
      LOTRItemMug.Vessel vessel = getVessel(itemstack);
      float strength = getStrength(itemstack);
      float foodStrength = getFoodStrength(itemstack);
      if (entityplayer.func_71043_e(false)) {
         entityplayer.func_71024_bL().func_75122_a(Math.round((float)this.foodHealAmount * foodStrength), this.foodSaturationAmount * foodStrength);
      }

      int tolerance;
      if (this.alcoholicity > 0.0F) {
         float alcoholPower = this.alcoholicity * strength;
         tolerance = LOTRLevelData.getData(entityplayer).getAlcoholTolerance();
         if (tolerance > 0) {
            float f = (float)Math.pow(0.99D, (double)tolerance);
            alcoholPower *= f;
         }

         if (!world.field_72995_K && field_77697_d.nextFloat() < alcoholPower) {
            int duration = (int)(60.0F * (1.0F + field_77697_d.nextFloat() * 0.5F) * alcoholPower);
            if (duration >= 1) {
               int durationTicks = duration * 20;
               entityplayer.func_70690_d(new PotionEffect(Potion.field_76431_k.field_76415_H, durationTicks));
               LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.getDrunk);
               int toleranceAdd = Math.round((float)duration / 20.0F);
               tolerance += toleranceAdd;
               LOTRLevelData.getData(entityplayer).setAlcoholTolerance(tolerance);
            }
         }
      }

      if (!world.field_72995_K && this.shouldApplyPotionEffects(itemstack, entityplayer)) {
         List effects = this.convertPotionEffectsForStrength(strength);

         for(tolerance = 0; tolerance < effects.size(); ++tolerance) {
            PotionEffect effect = (PotionEffect)effects.get(tolerance);
            entityplayer.func_70690_d(effect);
         }
      }

      if (this.damageAmount > 0) {
         entityplayer.func_70097_a(DamageSource.field_76376_m, (float)this.damageAmount * strength);
      }

      if (!world.field_72995_K && this.curesEffects) {
         entityplayer.curePotionEffects(new ItemStack(Items.field_151117_aB));
      }

      if (!world.field_72995_K) {
         if (vessel == LOTRItemMug.Vessel.SKULL) {
            LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.drinkSkull);
         }

         if (this == LOTRMod.mugMangoJuice) {
            LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.drinkMangoJuice);
         }

         if (this == LOTRMod.mugOrcDraught) {
            LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.drinkOrcDraught);
         }

         if (this == LOTRMod.mugAthelasBrew) {
            LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.drinkAthelasBrew);

            for(int i = 0; i < Potion.field_76425_a.length; ++i) {
               Potion potion = Potion.field_76425_a[i];
               if (potion != null && LOTRReflection.isBadEffect(potion)) {
                  entityplayer.func_82170_o(potion.field_76415_H);
               }
            }
         }

         if (this == LOTRMod.mugRedWine || this == LOTRMod.mugWhiteWine) {
            LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.drinkWine);
         }

         if (this == LOTRMod.mugDwarvenTonic) {
            LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.drinkDwarvenTonic);
         }
      }

      return !entityplayer.field_71075_bZ.field_75098_d ? new ItemStack(vessel.getEmptyVesselItem()) : itemstack;
   }

   protected boolean shouldApplyPotionEffects(ItemStack itemstack, EntityPlayer entityplayer) {
      return true;
   }

   public void applyToNPC(LOTREntityNPC npc, ItemStack itemstack) {
      float strength = getStrength(itemstack);
      npc.func_70691_i((float)this.foodHealAmount * strength);
      List effects = this.convertPotionEffectsForStrength(strength);

      for(int i = 0; i < effects.size(); ++i) {
         PotionEffect effect = (PotionEffect)effects.get(i);
         npc.func_70690_d(effect);
      }

      if (this.damageAmount > 0) {
         npc.func_70097_a(DamageSource.field_76376_m, (float)this.damageAmount * strength);
      }

      if (this.curesEffects) {
         npc.curePotionEffects(new ItemStack(Items.field_151117_aB));
      }

   }

   public boolean canPlayerDrink(EntityPlayer entityplayer) {
      if (!this.isFullMug) {
         return false;
      } else {
         return !this.isFoodDrink || entityplayer.func_71043_e(false);
      }
   }

   public static enum Vessel {
      MUG(0, "mug", true, 0),
      MUG_CLAY(1, "clay", true, 1),
      GOBLET_GOLD(2, "gobletGold", true, 10),
      GOBLET_SILVER(3, "gobletSilver", true, 8),
      GOBLET_COPPER(4, "gobletCopper", true, 5),
      GOBLET_WOOD(5, "gobletWood", true, 0),
      SKULL(6, "skull", true, 3),
      GLASS(7, "glass", true, 3),
      BOTTLE(8, "bottle", true, 2),
      SKIN(9, "skin", false, 0),
      HORN(10, "horn", true, 5),
      HORN_GOLD(11, "hornGold", true, 8);

      public final String name;
      public final int id;
      public final boolean canPlace;
      public final int extraPrice;

      private Vessel(int i, String s, boolean flag, int p) {
         this.id = i;
         this.name = s;
         this.canPlace = flag;
         this.extraPrice = p;
      }

      public static LOTRItemMug.Vessel forMeta(int i) {
         LOTRItemMug.Vessel[] var1 = values();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            LOTRItemMug.Vessel v = var1[var3];
            if (v.id == i) {
               return v;
            }
         }

         return MUG;
      }

      public Item getEmptyVesselItem() {
         if (this == MUG) {
            return LOTRMod.mug;
         } else if (this == MUG_CLAY) {
            return LOTRMod.ceramicMug;
         } else if (this == GOBLET_GOLD) {
            return LOTRMod.gobletGold;
         } else if (this == GOBLET_SILVER) {
            return LOTRMod.gobletSilver;
         } else if (this == GOBLET_COPPER) {
            return LOTRMod.gobletCopper;
         } else if (this == GOBLET_WOOD) {
            return LOTRMod.gobletWood;
         } else if (this == SKULL) {
            return LOTRMod.skullCup;
         } else if (this == GLASS) {
            return LOTRMod.wineGlass;
         } else if (this == BOTTLE) {
            return Items.field_151069_bo;
         } else if (this == SKIN) {
            return LOTRMod.waterskin;
         } else if (this == HORN) {
            return LOTRMod.aleHorn;
         } else {
            return this == HORN_GOLD ? LOTRMod.aleHornGold : LOTRMod.mug;
         }
      }

      public ItemStack getEmptyVessel() {
         return new ItemStack(this.getEmptyVesselItem());
      }

      public Block getBlock() {
         if (this == MUG) {
            return LOTRMod.mugBlock;
         } else if (this == MUG_CLAY) {
            return LOTRMod.ceramicMugBlock;
         } else if (this == GOBLET_GOLD) {
            return LOTRMod.gobletGoldBlock;
         } else if (this == GOBLET_SILVER) {
            return LOTRMod.gobletSilverBlock;
         } else if (this == GOBLET_COPPER) {
            return LOTRMod.gobletCopperBlock;
         } else if (this == GOBLET_WOOD) {
            return LOTRMod.gobletWoodBlock;
         } else if (this == SKULL) {
            return LOTRMod.skullCupBlock;
         } else if (this == GLASS) {
            return LOTRMod.wineGlassBlock;
         } else if (this == BOTTLE) {
            return LOTRMod.glassBottleBlock;
         } else if (this == SKIN) {
            return null;
         } else if (this == HORN) {
            return LOTRMod.aleHornBlock;
         } else {
            return this == HORN_GOLD ? LOTRMod.aleHornGoldBlock : LOTRMod.mugBlock;
         }
      }
   }
}
