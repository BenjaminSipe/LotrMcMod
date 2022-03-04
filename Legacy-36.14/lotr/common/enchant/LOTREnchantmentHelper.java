package lotr.common.enchant;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRConfig;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.item.LOTRMaterial;
import lotr.common.network.LOTRPacketCancelItemHighlight;
import lotr.common.network.LOTRPacketHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.StatCollector;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.WeightedRandom.Item;

public class LOTREnchantmentHelper {
   private static Map lastKnownPlayerInventories = new HashMap();
   private static Random backupRand;

   private static NBTTagList getItemEnchantTags(ItemStack itemstack, boolean create) {
      NBTTagCompound itemData = itemstack.func_77978_p();
      NBTTagList tags = null;
      if (itemData != null && itemData.func_74764_b("LOTREnch")) {
         tags = itemData.func_150295_c("LOTREnch", 8);
      } else if (create) {
         if (itemData == null) {
            itemData = new NBTTagCompound();
            itemstack.func_77982_d(itemData);
         }

         tags = new NBTTagList();
         itemData.func_74782_a("LOTREnch", tags);
      }

      return tags;
   }

   private static NBTTagCompound getItemEnchantProgress(ItemStack itemstack, LOTREnchantment ench, boolean create) {
      NBTTagCompound itemData = itemstack.func_77978_p();
      NBTTagList tags;
      NBTTagCompound enchData;
      if (itemData != null && itemData.func_74764_b("LOTREnchProgress")) {
         tags = itemData.func_150295_c("LOTREnchProgress", 10);

         for(int i = 0; i < tags.func_74745_c(); ++i) {
            NBTTagCompound enchData = tags.func_150305_b(i);
            if (enchData.func_74779_i("Name").equals(ench.enchantName)) {
               return enchData;
            }
         }

         if (create) {
            enchData = new NBTTagCompound();
            enchData.func_74778_a("Name", ench.enchantName);
            tags.func_74742_a(enchData);
            return enchData;
         }
      } else if (create) {
         if (itemData == null) {
            itemData = new NBTTagCompound();
            itemstack.func_77982_d(itemData);
         }

         tags = new NBTTagList();
         itemData.func_74782_a("LOTREnchProgress", tags);
         enchData = new NBTTagCompound();
         enchData.func_74778_a("Name", ench.enchantName);
         tags.func_74742_a(enchData);
         return enchData;
      }

      return null;
   }

   public static boolean hasEnchant(ItemStack itemstack, LOTREnchantment ench) {
      NBTTagList tags = getItemEnchantTags(itemstack, false);
      if (tags != null) {
         for(int i = 0; i < tags.func_74745_c(); ++i) {
            String s = tags.func_150307_f(i);
            if (s.equals(ench.enchantName)) {
               return true;
            }
         }
      }

      return false;
   }

   public static void setHasEnchant(ItemStack itemstack, LOTREnchantment ench) {
      if (!hasEnchant(itemstack, ench)) {
         NBTTagList tags = getItemEnchantTags(itemstack, true);
         if (tags != null) {
            String enchName = ench.enchantName;
            tags.func_74742_a(new NBTTagString(enchName));
         }
      }

   }

   public static void removeEnchant(ItemStack itemstack, LOTREnchantment ench) {
      NBTTagList tags = getItemEnchantTags(itemstack, true);
      if (tags != null) {
         String enchName = ench.enchantName;

         for(int i = 0; i < tags.func_74745_c(); ++i) {
            String s = tags.func_150307_f(i);
            if (s.equals(enchName)) {
               tags.func_74744_a(i);
               break;
            }
         }
      }

   }

   public static List getEnchantList(ItemStack itemstack) {
      List enchants = new ArrayList();
      NBTTagList tags = getItemEnchantTags(itemstack, false);
      if (tags != null) {
         for(int i = 0; i < tags.func_74745_c(); ++i) {
            String s = tags.func_150307_f(i);
            LOTREnchantment ench = LOTREnchantment.getEnchantmentByName(s);
            if (ench != null) {
               enchants.add(ench);
            }
         }
      }

      return enchants;
   }

   public static void setEnchantList(ItemStack itemstack, List enchants) {
      clearEnchants(itemstack);
      Iterator var2 = enchants.iterator();

      while(var2.hasNext()) {
         LOTREnchantment ench = (LOTREnchantment)var2.next();
         setHasEnchant(itemstack, ench);
      }

   }

   public static void clearEnchants(ItemStack itemstack) {
      NBTTagCompound itemData = itemstack.func_77978_p();
      if (itemData != null && itemData.func_74764_b("LOTREnch")) {
         itemData.func_82580_o("LOTREnch");
      }

   }

   public static void clearEnchantsAndProgress(ItemStack itemstack) {
      clearEnchants(itemstack);
      NBTTagCompound itemData = itemstack.func_77978_p();
      if (itemData != null && itemData.func_74764_b("LOTREnchProgress")) {
         itemData.func_82580_o("LOTREnchProgress");
      }

   }

   public static boolean checkEnchantCompatible(ItemStack itemstack, LOTREnchantment ench) {
      List enchants = getEnchantList(itemstack);
      Iterator var3 = enchants.iterator();

      LOTREnchantment itemEnch;
      do {
         if (!var3.hasNext()) {
            return true;
         }

         itemEnch = (LOTREnchantment)var3.next();
      } while(itemEnch.isCompatibleWith(ench) && ench.isCompatibleWith(itemEnch));

      return false;
   }

   public static String getFullEnchantedName(ItemStack itemstack, String name) {
      List enchants = getEnchantList(itemstack);
      enchants = Lists.reverse(enchants);

      LOTREnchantment ench;
      for(Iterator var3 = enchants.iterator(); var3.hasNext(); name = StatCollector.func_74837_a("lotr.enchant.nameFormat", new Object[]{ench.getDisplayName(), name})) {
         ench = (LOTREnchantment)var3.next();
      }

      return name;
   }

   private static boolean hasAppliedRandomEnchants(ItemStack itemstack) {
      NBTTagCompound nbt = itemstack.func_77978_p();
      return nbt != null && nbt.func_74764_b("LOTRRandomEnch") ? nbt.func_74767_n("LOTRRandomEnch") : false;
   }

   private static void setAppliedRandomEnchants(ItemStack itemstack) {
      if (!itemstack.func_77942_o()) {
         itemstack.func_77982_d(new NBTTagCompound());
      }

      itemstack.func_77978_p().func_74757_a("LOTRRandomEnch", true);
   }

   private static boolean canApplyAnyEnchant(ItemStack itemstack) {
      Iterator var1 = LOTREnchantment.allEnchantments.iterator();

      LOTREnchantment ench;
      do {
         if (!var1.hasNext()) {
            return false;
         }

         ench = (LOTREnchantment)var1.next();
      } while(!ench.canApply(itemstack, true));

      return true;
   }

   public static int getAnvilCost(ItemStack itemstack) {
      NBTTagCompound nbt = itemstack.func_77978_p();
      return nbt != null && nbt.func_74764_b("LOTRRepairCost") ? nbt.func_74762_e("LOTRRepairCost") : 0;
   }

   public static void setAnvilCost(ItemStack itemstack, int cost) {
      if (!itemstack.func_77942_o()) {
         itemstack.func_77982_d(new NBTTagCompound());
      }

      itemstack.func_77978_p().func_74768_a("LOTRRepairCost", cost);
   }

   public static boolean isReforgeable(ItemStack itemstack) {
      return !getEnchantList(itemstack).isEmpty() || canApplyAnyEnchant(itemstack);
   }

   public static void onEntityUpdate(EntityLivingBase entity) {
      Random rand = entity.func_70681_au();
      if (LOTRConfig.enchantingLOTR) {
         if (entity instanceof EntityLiving) {
            boolean init = entity.getEntityData().func_74767_n("LOTREnchantInit");
            if (!init) {
               for(int i = 0; i < entity.func_70035_c().length; ++i) {
                  ItemStack itemstack = entity.func_71124_b(i);
                  tryApplyRandomEnchantsForEntity(itemstack, rand);
               }

               entity.getEntityData().func_74757_a("LOTREnchantInit", true);
            }
         }

         if (entity instanceof EntityPlayerMP) {
            EntityPlayerMP entityplayer = (EntityPlayerMP)entity;
            UUID playerID = entityplayer.func_110124_au();
            InventoryPlayer inv = entityplayer.field_71071_by;
            ItemStack[] lastKnownInv = (ItemStack[])lastKnownPlayerInventories.get(playerID);
            if (lastKnownInv == null) {
               lastKnownInv = new ItemStack[inv.func_70302_i_()];
            }

            for(int i = 0; i < inv.func_70302_i_(); ++i) {
               ItemStack itemstack = inv.func_70301_a(i);
               ItemStack lastKnownItem = lastKnownInv[i];
               if (!ItemStack.func_77989_b(itemstack, lastKnownItem)) {
                  tryApplyRandomEnchantsForEntity(itemstack, rand);
                  lastKnownItem = itemstack == null ? null : itemstack.func_77946_l();
                  lastKnownInv[i] = lastKnownItem;
               }
            }

            if (tryApplyRandomEnchantsForEntity(inv.func_70445_o(), rand)) {
               entityplayer.func_71113_k();
            }

            lastKnownPlayerInventories.put(playerID, lastKnownInv);
            if (lastKnownPlayerInventories.size() > 200) {
               lastKnownPlayerInventories.clear();
            }
         }
      }

   }

   private static boolean tryApplyRandomEnchantsForEntity(ItemStack itemstack, Random rand) {
      if (itemstack != null && !hasAppliedRandomEnchants(itemstack) && canApplyAnyEnchant(itemstack)) {
         applyRandomEnchantments(itemstack, rand, false, false);
         return true;
      } else {
         return false;
      }
   }

   public static int getSkilfulWeight(LOTREnchantment ench) {
      int weight = ench.getEnchantWeight();
      double wd = (double)weight;
      if (ench.isBeneficial()) {
         wd = Math.pow(wd, 0.3D);
      }

      wd *= 100.0D;
      if (!ench.isBeneficial()) {
         wd *= 0.15D;
      }

      weight = (int)Math.round(wd);
      weight = Math.max(weight, 1);
      return weight;
   }

   public static void applyRandomEnchantments(ItemStack itemstack, Random random, boolean skilful, boolean keepBanes) {
      if (!keepBanes) {
         clearEnchantsAndProgress(itemstack);
      } else {
         List enchants = getEnchantList(itemstack);
         Iterator var5 = enchants.iterator();

         while(var5.hasNext()) {
            LOTREnchantment ench = (LOTREnchantment)var5.next();
            if (!ench.persistsReforge()) {
               removeEnchant(itemstack, ench);
            }
         }
      }

      LOTREnchantment ench;
      if (itemstack.func_77973_b() instanceof ItemSword && LOTRMaterial.getToolMaterialByName(((ItemSword)itemstack.func_77973_b()).func_150932_j()) == LOTRMaterial.BARROW.toToolMaterial()) {
         ench = LOTREnchantment.baneWight;
         if (ench.canApply(itemstack, false)) {
            setHasEnchant(itemstack, ench);
         }
      }

      if (itemstack.func_77973_b() == LOTRMod.sting) {
         ench = LOTREnchantment.baneSpider;
         if (ench.canApply(itemstack, false)) {
            setHasEnchant(itemstack, ench);
         }
      }

      int enchants = 0;
      float chance = random.nextFloat();
      if (skilful) {
         if (chance < 0.15F) {
            enchants = 2;
         } else if (chance < 0.8F) {
            enchants = 1;
         }
      } else if (chance < 0.1F) {
         enchants = 2;
      } else if (chance < 0.65F) {
         enchants = 1;
      }

      List applicable = new ArrayList();
      Iterator var7 = LOTREnchantment.allEnchantments.iterator();

      while(true) {
         LOTREnchantment ench;
         do {
            do {
               if (!var7.hasNext()) {
                  if (!applicable.isEmpty()) {
                     List chosenEnchants = new ArrayList();

                     for(int l = 0; l < enchants && !applicable.isEmpty(); ++l) {
                        LOTREnchantmentHelper.WeightedRandomEnchant chosenWre = (LOTREnchantmentHelper.WeightedRandomEnchant)WeightedRandom.func_76271_a(random, applicable);
                        LOTREnchantment chosenEnch = chosenWre.theEnchant;
                        chosenEnchants.add(chosenEnch);
                        applicable.remove(chosenWre);
                        List nowIncompatibles = new ArrayList();
                        Iterator var12 = applicable.iterator();

                        while(var12.hasNext()) {
                           LOTREnchantmentHelper.WeightedRandomEnchant wre = (LOTREnchantmentHelper.WeightedRandomEnchant)var12.next();
                           LOTREnchantment otherEnch = wre.theEnchant;
                           if (!otherEnch.isCompatibleWith(chosenEnch)) {
                              nowIncompatibles.add(wre);
                           }
                        }

                        applicable.removeAll(nowIncompatibles);
                     }

                     Iterator var21 = chosenEnchants.iterator();

                     while(var21.hasNext()) {
                        LOTREnchantment ench = (LOTREnchantment)var21.next();
                        if (ench.canApply(itemstack, false)) {
                           setHasEnchant(itemstack, ench);
                        }
                     }
                  }

                  if (!getEnchantList(itemstack).isEmpty() || canApplyAnyEnchant(itemstack)) {
                     setAppliedRandomEnchants(itemstack);
                  }

                  return;
               }

               ench = (LOTREnchantment)var7.next();
            } while(!ench.canApply(itemstack, true));
         } while(ench.isSkilful() && !skilful);

         int weight = ench.getEnchantWeight();
         if (weight > 0) {
            if (skilful) {
               weight = getSkilfulWeight(ench);
            } else {
               weight *= 100;
            }

            if (weight > 0 && itemstack.func_77973_b() instanceof ItemTool && !ench.itemTypes.contains(LOTREnchantmentType.TOOL) && !ench.itemTypes.contains(LOTREnchantmentType.BREAKABLE)) {
               weight /= 3;
               weight = Math.max(weight, 1);
            }

            LOTREnchantmentHelper.WeightedRandomEnchant wre = new LOTREnchantmentHelper.WeightedRandomEnchant(ench, weight);
            applicable.add(wre);
         }
      }
   }

   public static float calcTradeValueFactor(ItemStack itemstack) {
      float value = 1.0F;
      List enchants = getEnchantList(itemstack);
      Iterator var3 = enchants.iterator();

      while(var3.hasNext()) {
         LOTREnchantment ench = (LOTREnchantment)var3.next();
         value *= ench.getValueModifier();
         if (ench.isSkilful()) {
            value *= 1.5F;
         }
      }

      return value;
   }

   public static float calcBaseMeleeDamageBoost(ItemStack itemstack) {
      float damage = 0.0F;
      if (itemstack != null) {
         List enchants = getEnchantList(itemstack);
         Iterator var3 = enchants.iterator();

         while(var3.hasNext()) {
            LOTREnchantment ench = (LOTREnchantment)var3.next();
            if (ench instanceof LOTREnchantmentDamage) {
               damage += ((LOTREnchantmentDamage)ench).getBaseDamageBoost();
            }
         }
      }

      return damage;
   }

   public static float calcEntitySpecificDamage(ItemStack itemstack, EntityLivingBase entity) {
      float damage = 0.0F;
      if (itemstack != null) {
         List enchants = getEnchantList(itemstack);
         Iterator var4 = enchants.iterator();

         while(var4.hasNext()) {
            LOTREnchantment ench = (LOTREnchantment)var4.next();
            if (ench instanceof LOTREnchantmentDamage) {
               damage += ((LOTREnchantmentDamage)ench).getEntitySpecificDamage(entity);
            }
         }
      }

      return damage;
   }

   public static float calcMeleeSpeedFactor(ItemStack itemstack) {
      float speed = 1.0F;
      if (itemstack != null) {
         List enchants = getEnchantList(itemstack);
         Iterator var3 = enchants.iterator();

         while(var3.hasNext()) {
            LOTREnchantment ench = (LOTREnchantment)var3.next();
            if (ench instanceof LOTREnchantmentMeleeSpeed) {
               speed *= ((LOTREnchantmentMeleeSpeed)ench).speedFactor;
            }
         }
      }

      return speed;
   }

   public static float calcMeleeReachFactor(ItemStack itemstack) {
      float reach = 1.0F;
      if (itemstack != null) {
         List enchants = getEnchantList(itemstack);
         Iterator var3 = enchants.iterator();

         while(var3.hasNext()) {
            LOTREnchantment ench = (LOTREnchantment)var3.next();
            if (ench instanceof LOTREnchantmentMeleeReach) {
               reach *= ((LOTREnchantmentMeleeReach)ench).reachFactor;
            }
         }
      }

      return reach;
   }

   public static int calcExtraKnockback(ItemStack itemstack) {
      int kb = 0;
      if (itemstack != null) {
         List enchants = getEnchantList(itemstack);
         Iterator var3 = enchants.iterator();

         while(var3.hasNext()) {
            LOTREnchantment ench = (LOTREnchantment)var3.next();
            if (ench instanceof LOTREnchantmentKnockback) {
               kb += ((LOTREnchantmentKnockback)ench).knockback;
            }
         }
      }

      return kb;
   }

   public static boolean negateDamage(ItemStack itemstack, Random random) {
      if (itemstack != null) {
         if (random == null) {
            if (backupRand == null) {
               backupRand = new Random();
            }

            random = backupRand;
         }

         List enchants = getEnchantList(itemstack);
         Iterator var3 = enchants.iterator();

         while(var3.hasNext()) {
            LOTREnchantment ench = (LOTREnchantment)var3.next();
            if (ench instanceof LOTREnchantmentDurability) {
               float durability = ((LOTREnchantmentDurability)ench).durabilityFactor;
               if (durability > 1.0F) {
                  float inv = 1.0F / durability;
                  if (random.nextFloat() > inv) {
                     return true;
                  }
               }
            }
         }
      }

      return false;
   }

   public static float calcToolEfficiency(ItemStack itemstack) {
      float speed = 1.0F;
      if (itemstack != null) {
         List enchants = getEnchantList(itemstack);
         Iterator var3 = enchants.iterator();

         while(var3.hasNext()) {
            LOTREnchantment ench = (LOTREnchantment)var3.next();
            if (ench instanceof LOTREnchantmentToolSpeed) {
               speed *= ((LOTREnchantmentToolSpeed)ench).speedFactor;
            }
         }
      }

      return speed;
   }

   public static boolean isSilkTouch(ItemStack itemstack) {
      return itemstack != null && hasEnchant(itemstack, LOTREnchantment.toolSilk);
   }

   public static int calcLootingLevel(ItemStack itemstack) {
      int looting = 0;
      if (itemstack != null) {
         List enchants = getEnchantList(itemstack);
         Iterator var3 = enchants.iterator();

         while(var3.hasNext()) {
            LOTREnchantment ench = (LOTREnchantment)var3.next();
            if (ench instanceof LOTREnchantmentLooting) {
               looting += ((LOTREnchantmentLooting)ench).lootLevel;
            }
         }
      }

      return looting;
   }

   public static int calcCommonArmorProtection(ItemStack itemstack) {
      int protection = 0;
      if (itemstack != null) {
         List enchants = getEnchantList(itemstack);
         Iterator var3 = enchants.iterator();

         while(var3.hasNext()) {
            LOTREnchantment ench = (LOTREnchantment)var3.next();
            if (ench instanceof LOTREnchantmentProtection) {
               protection += ((LOTREnchantmentProtection)ench).protectLevel;
            }
         }
      }

      return protection;
   }

   public static int calcSpecialArmorSetProtection(ItemStack[] armor, DamageSource source) {
      int protection = 0;
      if (armor != null) {
         ItemStack[] var3 = armor;
         int var4 = armor.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            ItemStack itemstack = var3[var5];
            if (itemstack != null) {
               List enchants = getEnchantList(itemstack);
               Iterator var8 = enchants.iterator();

               while(var8.hasNext()) {
                  LOTREnchantment ench = (LOTREnchantment)var8.next();
                  if (ench instanceof LOTREnchantmentProtectionSpecial) {
                     protection += ((LOTREnchantmentProtectionSpecial)ench).calcSpecialProtection(source);
                  }
               }
            }
         }
      }

      return protection;
   }

   public static int getMaxFireProtectionLevel(ItemStack[] armor) {
      int max = 0;
      if (armor != null) {
         ItemStack[] var2 = armor;
         int var3 = armor.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            ItemStack itemstack = var2[var4];
            if (itemstack != null) {
               List enchants = getEnchantList(itemstack);
               Iterator var7 = enchants.iterator();

               while(var7.hasNext()) {
                  LOTREnchantment ench = (LOTREnchantment)var7.next();
                  if (ench instanceof LOTREnchantmentProtectionFire) {
                     int protection = ((LOTREnchantmentProtectionFire)ench).protectLevel;
                     if (protection > max) {
                        max = protection;
                     }
                  }
               }
            }
         }
      }

      return max;
   }

   public static float calcRangedDamageFactor(ItemStack itemstack) {
      float damage = 1.0F;
      if (itemstack != null) {
         List enchants = getEnchantList(itemstack);
         Iterator var3 = enchants.iterator();

         while(var3.hasNext()) {
            LOTREnchantment ench = (LOTREnchantment)var3.next();
            if (ench instanceof LOTREnchantmentRangedDamage) {
               damage *= ((LOTREnchantmentRangedDamage)ench).damageFactor;
            }
         }
      }

      return damage;
   }

   public static int calcRangedKnockback(ItemStack itemstack) {
      int kb = 0;
      if (itemstack != null) {
         List enchants = getEnchantList(itemstack);
         Iterator var3 = enchants.iterator();

         while(var3.hasNext()) {
            LOTREnchantment ench = (LOTREnchantment)var3.next();
            if (ench instanceof LOTREnchantmentRangedKnockback) {
               kb += ((LOTREnchantmentRangedKnockback)ench).knockback;
            }
         }
      }

      return kb;
   }

   public static int calcFireAspect(ItemStack itemstack) {
      int fire = 0;
      if (itemstack != null) {
         List enchants = getEnchantList(itemstack);
         Iterator var3 = enchants.iterator();

         while(var3.hasNext()) {
            LOTREnchantment ench = (LOTREnchantment)var3.next();
            if (ench == LOTREnchantment.fire) {
               fire += LOTREnchantmentWeaponSpecial.getFireAmount();
            }
         }
      }

      return fire;
   }

   public static int calcFireAspectForMelee(ItemStack itemstack) {
      return itemstack != null && LOTREnchantmentType.MELEE.canApply(itemstack, false) ? calcFireAspect(itemstack) : 0;
   }

   public static void onKillEntity(EntityPlayer entityplayer, EntityLivingBase target, DamageSource source) {
      if (source.func_76364_f() == entityplayer) {
         ItemStack weapon = entityplayer.func_70694_bm();
         Random rand = entityplayer.func_70681_au();
         if (weapon != null) {
            boolean progressChanged = false;
            boolean enchantsChanged = false;
            Iterator var7 = LOTREnchantment.allEnchantments.iterator();

            while(true) {
               LOTREnchantmentBane enchBane;
               boolean compatible;
               do {
                  int killed;
                  int requiredKills;
                  do {
                     do {
                        LOTREnchantment ench;
                        do {
                           do {
                              do {
                                 do {
                                    if (!var7.hasNext()) {
                                       if (progressChanged && !enchantsChanged) {
                                          LOTRPacketCancelItemHighlight pkt = new LOTRPacketCancelItemHighlight();
                                          LOTRPacketHandler.networkWrapper.sendTo(pkt, (EntityPlayerMP)entityplayer);
                                       }

                                       return;
                                    }

                                    ench = (LOTREnchantment)var7.next();
                                 } while(!ench.canApply(weapon, false));
                              } while(!(ench instanceof LOTREnchantmentBane));

                              enchBane = (LOTREnchantmentBane)ench;
                           } while(!enchBane.isAchievable);
                        } while(!enchBane.doesEntityKillCountTowardsBane(target, entityplayer.field_70170_p));

                        NBTTagCompound nbt = getItemEnchantProgress(weapon, ench, true);
                        killed = 0;
                        if (nbt.func_74764_b("Kills")) {
                           killed = nbt.func_74762_e("Kills");
                        }

                        ++killed;
                        nbt.func_74768_a("Kills", killed);
                        progressChanged = true;
                        int requiredKills = false;
                        if (nbt.func_74764_b("KillsRequired")) {
                           requiredKills = nbt.func_74762_e("KillsRequired");
                        } else {
                           requiredKills = enchBane.getRandomKillsRequired(rand);
                           nbt.func_74768_a("KillsRequired", requiredKills);
                        }
                     } while(killed < requiredKills);
                  } while(hasEnchant(weapon, enchBane));

                  compatible = checkEnchantCompatible(weapon, enchBane);
               } while(!compatible);

               setHasEnchant(weapon, enchBane);
               enchantsChanged = true;
               entityplayer.func_145747_a(enchBane.getEarnMessage(weapon));
               Iterator var14 = MinecraftServer.func_71276_C().func_71203_ab().field_72404_b.iterator();

               while(var14.hasNext()) {
                  Object obj = var14.next();
                  EntityPlayer otherPlayer = (EntityPlayer)obj;
                  if (otherPlayer != entityplayer) {
                     otherPlayer.func_145747_a(enchBane.getEarnMessageWithName(entityplayer, weapon));
                  }
               }

               if (enchBane == LOTREnchantment.baneElf) {
                  LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.enchantBaneElf);
               } else if (enchBane == LOTREnchantment.baneOrc) {
                  LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.enchantBaneOrc);
               } else if (enchBane == LOTREnchantment.baneDwarf) {
                  LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.enchantBaneDwarf);
               } else if (enchBane == LOTREnchantment.baneWarg) {
                  LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.enchantBaneWarg);
               } else if (enchBane == LOTREnchantment.baneTroll) {
                  LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.enchantBaneTroll);
               } else if (enchBane == LOTREnchantment.baneSpider) {
                  LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.enchantBaneSpider);
               } else if (enchBane == LOTREnchantment.baneWight) {
                  LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.enchantBaneWight);
               }
            }
         }
      }

   }

   private static NBTTagList getEntityEnchantTags(Entity entity, boolean create) {
      NBTTagCompound data = entity.getEntityData();
      NBTTagList tags = null;
      if (data != null && data.func_74764_b("LOTREnchEntity")) {
         tags = data.func_150295_c("LOTREnchEntity", 8);
      } else if (create) {
         tags = new NBTTagList();
         data.func_74782_a("LOTREnchEntity", tags);
      }

      return tags;
   }

   public static void setProjectileEnchantment(Entity entity, LOTREnchantment ench) {
      if (!hasProjectileEnchantment(entity, ench)) {
         NBTTagList tags = getEntityEnchantTags(entity, true);
         if (tags != null) {
            String enchName = ench.enchantName;
            tags.func_74742_a(new NBTTagString(enchName));
         }
      }

   }

   public static boolean hasProjectileEnchantment(Entity entity, LOTREnchantment ench) {
      NBTTagList tags = getEntityEnchantTags(entity, false);
      if (tags != null) {
         for(int i = 0; i < tags.func_74745_c(); ++i) {
            String s = tags.func_150307_f(i);
            if (s.equals(ench.enchantName)) {
               return true;
            }
         }
      }

      return false;
   }

   public static boolean hasMeleeOrRangedEnchant(DamageSource source, LOTREnchantment ench) {
      Entity attacker = source.func_76346_g();
      Entity sourceEntity = source.func_76364_f();
      if (attacker instanceof EntityLivingBase) {
         EntityLivingBase attackerLiving = (EntityLivingBase)attacker;
         if (attackerLiving == sourceEntity) {
            ItemStack weapon = attackerLiving.func_70694_bm();
            if (weapon != null && LOTREnchantmentType.MELEE.canApply(weapon, false) && hasEnchant(weapon, ench)) {
               return true;
            }
         }
      }

      return sourceEntity != null && hasProjectileEnchantment(sourceEntity, ench);
   }

   public static class WeightedRandomEnchant extends Item {
      public final LOTREnchantment theEnchant;

      public WeightedRandomEnchant(LOTREnchantment e, int weight) {
         super(weight);
         this.theEnchant = e;
      }
   }
}
