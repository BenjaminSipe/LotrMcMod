package lotr.common.inventory;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRConfig;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.enchant.LOTREnchantment;
import lotr.common.enchant.LOTREnchantmentCombining;
import lotr.common.enchant.LOTREnchantmentHelper;
import lotr.common.entity.npc.LOTREntityDwarf;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.entity.npc.LOTREntityScrapTrader;
import lotr.common.entity.npc.LOTRTradeEntries;
import lotr.common.entity.npc.LOTRTradeEntry;
import lotr.common.entity.npc.LOTRTradeable;
import lotr.common.item.AnvilNameColorProvider;
import lotr.common.item.LOTRItemBlowgun;
import lotr.common.item.LOTRItemChisel;
import lotr.common.item.LOTRItemCoin;
import lotr.common.item.LOTRItemCrossbow;
import lotr.common.item.LOTRItemEnchantment;
import lotr.common.item.LOTRItemModifierTemplate;
import lotr.common.item.LOTRItemOwnership;
import lotr.common.item.LOTRItemThrowingAxe;
import lotr.common.item.LOTRMaterial;
import lotr.common.recipe.LOTRRecipePoisonWeapon;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import org.apache.commons.lang3.StringUtils;

public class LOTRContainerAnvil extends Container {
   public IInventory invOutput;
   public IInventory invInput;
   public final EntityPlayer thePlayer;
   public final World theWorld;
   public final boolean isTrader;
   private int xCoord;
   private int yCoord;
   private int zCoord;
   public LOTREntityNPC theNPC;
   public LOTRTradeable theTrader;
   public int materialCost;
   public int reforgeCost;
   public int engraveOwnerCost;
   private String repairedItemName;
   private long lastReforgeTime;
   public static final int maxReforgeTime = 40;
   public int clientReforgeTime;
   private boolean doneMischief;
   public boolean isSmithScrollCombine;

   private LOTRContainerAnvil(EntityPlayer entityplayer, boolean trader) {
      this.lastReforgeTime = -1L;
      this.thePlayer = entityplayer;
      this.theWorld = entityplayer.field_70170_p;
      this.isTrader = trader;
      this.invOutput = new InventoryCraftResult();
      this.invInput = new InventoryBasic("Repair", true, this.isTrader ? 2 : 3) {
         public void func_70296_d() {
            super.func_70296_d();
            LOTRContainerAnvil.this.func_75130_a(this);
         }
      };
      this.func_75146_a(new Slot(this.invInput, 0, 27, 58));
      this.func_75146_a(new Slot(this.invInput, 1, 76, 47));
      if (!this.isTrader) {
         this.func_75146_a(new Slot(this.invInput, 2, 76, 70));
      }

      this.func_75146_a(new LOTRSlotAnvilOutput(this, this.invOutput, 0, 134, 58));

      int i1;
      for(i1 = 0; i1 < 3; ++i1) {
         for(int i1 = 0; i1 < 9; ++i1) {
            this.func_75146_a(new Slot(entityplayer.field_71071_by, i1 + i1 * 9 + 9, 8 + i1 * 18, 116 + i1 * 18));
         }
      }

      for(i1 = 0; i1 < 9; ++i1) {
         this.func_75146_a(new Slot(entityplayer.field_71071_by, i1, 8 + i1 * 18, 174));
      }

   }

   public LOTRContainerAnvil(EntityPlayer entityplayer, int i, int j, int k) {
      this(entityplayer, false);
      this.xCoord = i;
      this.yCoord = j;
      this.zCoord = k;
   }

   public LOTRContainerAnvil(EntityPlayer entityplayer, LOTREntityNPC npc) {
      this(entityplayer, true);
      this.theNPC = npc;
      this.theTrader = (LOTRTradeable)npc;
   }

   public void func_75130_a(IInventory inv) {
      super.func_75130_a(inv);
      if (inv == this.invInput) {
         this.updateRepairOutput();
      }

   }

   private void updateRepairOutput() {
      ItemStack inputItem = this.invInput.func_70301_a(0);
      this.materialCost = 0;
      this.reforgeCost = 0;
      this.engraveOwnerCost = 0;
      this.isSmithScrollCombine = false;
      int baseAnvilCost = false;
      int repairCost = 0;
      int combineCost = 0;
      int renameCost = 0;
      if (inputItem == null) {
         this.invOutput.func_70299_a(0, (ItemStack)null);
         this.materialCost = 0;
      } else {
         ItemStack inputCopy = inputItem.func_77946_l();
         ItemStack combinerItem = this.invInput.func_70301_a(1);
         ItemStack materialItem = this.isTrader ? null : this.invInput.func_70301_a(2);
         Map inputEnchants = EnchantmentHelper.func_82781_a(inputCopy);
         boolean enchantingWithBook = false;
         List inputModifiers = LOTREnchantmentHelper.getEnchantList(inputCopy);
         int baseAnvilCost = LOTREnchantmentHelper.getAnvilCost(inputItem) + (combinerItem == null ? 0 : LOTREnchantmentHelper.getAnvilCost(combinerItem));
         this.materialCost = 0;
         String previousDisplayName = inputCopy.func_82833_r();
         String defaultItemName = inputCopy.func_77973_b().func_77653_i(inputCopy);
         String nameToApply = this.repairedItemName;
         String formattedNameToApply = nameToApply;
         List colorsToApply = new ArrayList();
         colorsToApply.addAll(getAppliedFormattingCodes(inputCopy.func_82833_r()));
         boolean alteringNameColor = false;
         int nextAnvilCost;
         int oneItemRepair;
         if (costsToRename(inputItem) && combinerItem != null) {
            if (combinerItem.func_77973_b() instanceof AnvilNameColorProvider) {
               AnvilNameColorProvider nameColorProvider = (AnvilNameColorProvider)combinerItem.func_77973_b();
               EnumChatFormatting newColor = nameColorProvider.getAnvilNameColor();
               boolean isDifferentColor = !colorsToApply.contains(newColor);
               if (isDifferentColor) {
                  EnumChatFormatting[] var21 = EnumChatFormatting.values();
                  nextAnvilCost = var21.length;

                  for(oneItemRepair = 0; oneItemRepair < nextAnvilCost; ++oneItemRepair) {
                     EnumChatFormatting ecf = var21[oneItemRepair];
                     if (ecf.func_96302_c()) {
                        while(colorsToApply.contains(ecf)) {
                           colorsToApply.remove(ecf);
                        }
                     }
                  }

                  colorsToApply.add(newColor);
                  alteringNameColor = true;
               }
            } else if (combinerItem.func_77973_b() == Items.field_151145_ak && !colorsToApply.isEmpty()) {
               colorsToApply.clear();
               alteringNameColor = true;
            }

            if (alteringNameColor) {
               ++renameCost;
            }
         }

         if (!colorsToApply.isEmpty()) {
            if (StringUtils.isBlank(nameToApply)) {
               formattedNameToApply = defaultItemName;
            }

            formattedNameToApply = applyFormattingCodes(formattedNameToApply, colorsToApply);
         }

         boolean nameChange = false;
         if (formattedNameToApply != null && !formattedNameToApply.equals(previousDisplayName)) {
            if (!StringUtils.isBlank(formattedNameToApply) && !formattedNameToApply.equals(defaultItemName)) {
               inputCopy.func_151001_c(formattedNameToApply);
               if (!stripFormattingCodes(previousDisplayName).equals(stripFormattingCodes(formattedNameToApply))) {
                  nameChange = true;
               }
            } else if (inputCopy.func_82837_s()) {
               inputCopy.func_135074_t();
               if (!stripFormattingCodes(previousDisplayName).equals(stripFormattingCodes(formattedNameToApply))) {
                  nameChange = true;
               }
            }
         }

         boolean combining;
         if (nameChange) {
            combining = costsToRename(inputItem);
            if (combining) {
               ++renameCost;
            }
         }

         if (this.isTrader) {
            LOTREnchantmentCombining.CombineRecipe scrollCombine = LOTREnchantmentCombining.getCombinationResult(inputItem, combinerItem);
            if (scrollCombine != null) {
               this.invOutput.func_70299_a(0, scrollCombine.createOutputItem());
               this.materialCost = scrollCombine.cost;
               this.reforgeCost = 0;
               this.engraveOwnerCost = 0;
               this.isSmithScrollCombine = true;
               return;
            }
         }

         combining = false;
         int newDamage;
         int inputEnchLevel;
         byte stringFactor;
         int usedMaterials;
         if (combinerItem != null) {
            enchantingWithBook = combinerItem.func_77973_b() == Items.field_151134_bR && Items.field_151134_bR.func_92110_g(combinerItem).func_74745_c() > 0;
            if (enchantingWithBook && !LOTRConfig.enchantingVanilla) {
               this.invOutput.func_70299_a(0, (ItemStack)null);
               this.materialCost = 0;
               return;
            }

            LOTREnchantment combinerItemEnchant = null;
            if (combinerItem.func_77973_b() instanceof LOTRItemEnchantment) {
               combinerItemEnchant = ((LOTRItemEnchantment)combinerItem.func_77973_b()).theEnchant;
            } else if (combinerItem.func_77973_b() instanceof LOTRItemModifierTemplate) {
               combinerItemEnchant = LOTRItemModifierTemplate.getModifier(combinerItem);
            }

            if (!enchantingWithBook && combinerItemEnchant == null) {
               if (inputCopy.func_77984_f() && inputCopy.func_77973_b() == combinerItem.func_77973_b()) {
                  int inputUseLeft = inputItem.func_77958_k() - inputItem.func_77952_i();
                  nextAnvilCost = combinerItem.func_77958_k() - combinerItem.func_77952_i();
                  oneItemRepair = nextAnvilCost + inputCopy.func_77958_k() * 12 / 100;
                  usedMaterials = inputUseLeft + oneItemRepair;
                  newDamage = inputCopy.func_77958_k() - usedMaterials;
                  newDamage = Math.max(newDamage, 0);
                  if (newDamage < inputCopy.func_77960_j()) {
                     inputCopy.func_77964_b(newDamage);
                     int restoredUses1 = inputCopy.func_77958_k() - inputUseLeft;
                     inputEnchLevel = inputCopy.func_77958_k() - nextAnvilCost;
                     combineCost += Math.max(0, Math.min(restoredUses1, inputEnchLevel) / 100);
                  }

                  combining = true;
               } else if (!alteringNameColor) {
                  this.invOutput.func_70299_a(0, (ItemStack)null);
                  this.materialCost = 0;
                  return;
               }
            }

            Map outputEnchants = new HashMap(inputEnchants);
            int combinerEnchLevel;
            if (LOTRConfig.enchantingVanilla) {
               Map combinerEnchants = EnchantmentHelper.func_82781_a(combinerItem);
               Iterator var50 = combinerEnchants.keySet().iterator();

               label421:
               while(var50.hasNext()) {
                  Object obj = var50.next();
                  newDamage = (Integer)obj;
                  Enchantment combinerEnch = Enchantment.field_77331_b[newDamage];
                  inputEnchLevel = 0;
                  if (outputEnchants.containsKey(newDamage)) {
                     inputEnchLevel = (Integer)outputEnchants.get(newDamage);
                  }

                  combinerEnchLevel = (Integer)combinerEnchants.get(newDamage);
                  int combinedEnchLevel;
                  if (inputEnchLevel == combinerEnchLevel) {
                     ++combinerEnchLevel;
                     combinedEnchLevel = combinerEnchLevel;
                  } else {
                     combinedEnchLevel = Math.max(combinerEnchLevel, inputEnchLevel);
                  }

                  int levelsAdded = combinedEnchLevel - inputEnchLevel;
                  boolean canApply = combinerEnch.func_92089_a(inputItem);
                  if (this.thePlayer.field_71075_bZ.field_75098_d || inputItem.func_77973_b() == Items.field_151134_bR) {
                     canApply = true;
                  }

                  Iterator var32 = outputEnchants.keySet().iterator();

                  while(true) {
                     int inputEnchID;
                     Enchantment inputEnch;
                     do {
                        do {
                           if (!var32.hasNext()) {
                              if (canApply) {
                                 combinerEnchLevel = Math.min(combinedEnchLevel, combinerEnch.func_77325_b());
                                 outputEnchants.put(newDamage, combinerEnchLevel);
                                 int costPerLevel = 0;
                                 int enchWeight = combinerEnch.func_77324_c();
                                 if (enchWeight == 1) {
                                    costPerLevel = 8;
                                 } else if (enchWeight == 2) {
                                    costPerLevel = 4;
                                 } else if (enchWeight == 5) {
                                    costPerLevel = 2;
                                 } else if (enchWeight == 10) {
                                    costPerLevel = 1;
                                 }

                                 combineCost += costPerLevel * levelsAdded;
                              }
                              continue label421;
                           }

                           Object objIn = var32.next();
                           inputEnchID = (Integer)objIn;
                           inputEnch = Enchantment.field_77331_b[inputEnchID];
                        } while(inputEnchID == newDamage);
                     } while(combinerEnch.func_77326_a(inputEnch) && inputEnch.func_77326_a(combinerEnch));

                     canApply = false;
                     combineCost += levelsAdded;
                  }
               }
            } else {
               outputEnchants.clear();
            }

            EnchantmentHelper.func_82782_a(outputEnchants, inputCopy);
            stringFactor = 3;
            List outputMods = new ArrayList();
            outputMods.addAll(inputModifiers);
            List combinerMods = LOTREnchantmentHelper.getEnchantList(combinerItem);
            if (combinerItemEnchant != null) {
               combinerMods.add(combinerItemEnchant);
               if (combinerItemEnchant == LOTREnchantment.fire) {
                  Item item = inputCopy.func_77973_b();
                  if (LOTRRecipePoisonWeapon.poisonedToInput.containsKey(item)) {
                     Item unpoisoned = (Item)LOTRRecipePoisonWeapon.poisonedToInput.get(item);
                     inputCopy.func_150996_a(unpoisoned);
                  }
               }
            }

            Iterator var57 = combinerMods.iterator();

            while(true) {
               if (!var57.hasNext()) {
                  LOTREnchantmentHelper.setEnchantList(inputCopy, outputMods);
                  break;
               }

               LOTREnchantment combinerMod = (LOTREnchantment)var57.next();
               boolean canApply = combinerMod.canApply(inputItem, false);
               if (canApply) {
                  Iterator var65 = outputMods.iterator();

                  label381:
                  while(true) {
                     LOTREnchantment mod;
                     do {
                        if (!var65.hasNext()) {
                           break label381;
                        }

                        mod = (LOTREnchantment)var65.next();
                     } while(mod.isCompatibleWith(combinerMod) && combinerMod.isCompatibleWith(mod));

                     canApply = false;
                  }
               }

               combinerEnchLevel = 0;
               Iterator var68 = outputMods.iterator();

               while(var68.hasNext()) {
                  LOTREnchantment mod = (LOTREnchantment)var68.next();
                  if (!mod.bypassAnvilLimit()) {
                     ++combinerEnchLevel;
                  }
               }

               if (!combinerMod.bypassAnvilLimit() && combinerEnchLevel >= stringFactor) {
                  canApply = false;
               }

               if (canApply) {
                  outputMods.add(combinerMod);
                  if (combinerMod.isBeneficial()) {
                     combineCost += Math.max(1, (int)combinerMod.getValueModifier());
                  }
               }
            }
         }

         if (combineCost > 0) {
            combining = true;
         }

         int numEnchants = 0;

         Iterator var44;
         byte costPerLevel;
         for(var44 = inputEnchants.keySet().iterator(); var44.hasNext(); baseAnvilCost += numEnchants + newDamage * costPerLevel) {
            Object obj = var44.next();
            oneItemRepair = (Integer)obj;
            Enchantment ench = Enchantment.field_77331_b[oneItemRepair];
            newDamage = (Integer)inputEnchants.get(oneItemRepair);
            ++numEnchants;
            costPerLevel = 0;
            inputEnchLevel = ench.func_77324_c();
            if (inputEnchLevel == 1) {
               costPerLevel = 8;
            } else if (inputEnchLevel == 2) {
               costPerLevel = 4;
            } else if (inputEnchLevel == 5) {
               costPerLevel = 2;
            } else if (inputEnchLevel == 10) {
               costPerLevel = 1;
            }
         }

         if (enchantingWithBook && !inputCopy.func_77973_b().isBookEnchantable(inputCopy, combinerItem)) {
            inputCopy = null;
         }

         var44 = inputModifiers.iterator();

         while(var44.hasNext()) {
            LOTREnchantment mod = (LOTREnchantment)var44.next();
            if (mod.isBeneficial()) {
               baseAnvilCost += Math.max(1, (int)mod.getValueModifier());
            }
         }

         boolean repairing;
         if (inputCopy.func_77984_f()) {
            repairing = false;
            nextAnvilCost = 0;
            if (this.isTrader) {
               repairing = this.getTraderMaterialPrice(inputItem) > 0.0F;
               nextAnvilCost = Integer.MAX_VALUE;
            } else {
               repairing = materialItem != null && this.isRepairMaterial(inputItem, materialItem);
               if (materialItem != null) {
                  nextAnvilCost = materialItem.field_77994_a - combineCost - renameCost;
               }
            }

            oneItemRepair = Math.min(inputCopy.func_77952_i(), inputCopy.func_77958_k() / 4);
            if (repairing && nextAnvilCost > 0 && oneItemRepair > 0) {
               nextAnvilCost -= baseAnvilCost;
               if (nextAnvilCost <= 0) {
                  if (!nameChange && !combining) {
                     repairCost = 1;
                     usedMaterials = inputCopy.func_77952_i() - oneItemRepair;
                     inputCopy.func_77964_b(usedMaterials);
                  }
               } else {
                  for(usedMaterials = 0; oneItemRepair > 0 && usedMaterials < nextAnvilCost; ++usedMaterials) {
                     newDamage = inputCopy.func_77952_i() - oneItemRepair;
                     inputCopy.func_77964_b(newDamage);
                     oneItemRepair = Math.min(inputCopy.func_77952_i(), inputCopy.func_77958_k() / 4);
                  }

                  repairCost += usedMaterials;
               }
            }
         }

         repairing = repairCost > 0;
         if (!combining && !repairing) {
            this.materialCost = 0;
         } else {
            this.materialCost = baseAnvilCost;
            this.materialCost += combineCost + repairCost;
         }

         this.materialCost += renameCost;
         if (inputCopy != null) {
            nextAnvilCost = LOTREnchantmentHelper.getAnvilCost(inputItem);
            if (combinerItem != null) {
               oneItemRepair = LOTREnchantmentHelper.getAnvilCost(combinerItem);
               nextAnvilCost = Math.max(nextAnvilCost, oneItemRepair);
            }

            if (combining) {
               nextAnvilCost += 2;
            } else if (repairing) {
               ++nextAnvilCost;
            }

            nextAnvilCost = Math.max(nextAnvilCost, 0);
            if (nextAnvilCost > 0) {
               LOTREnchantmentHelper.setAnvilCost(inputCopy, nextAnvilCost);
            }
         }

         if (LOTREnchantmentHelper.isReforgeable(inputItem)) {
            this.reforgeCost = 2;
            if (inputItem.func_77973_b() instanceof ItemArmor) {
               this.reforgeCost = 3;
            }

            if (inputItem.func_77984_f()) {
               ItemStack reforgeCopy = inputItem.func_77946_l();
               oneItemRepair = Math.min(reforgeCopy.func_77952_i(), reforgeCopy.func_77958_k() / 4);
               if (oneItemRepair > 0) {
                  for(usedMaterials = 0; oneItemRepair > 0; ++usedMaterials) {
                     newDamage = reforgeCopy.func_77952_i() - oneItemRepair;
                     reforgeCopy.func_77964_b(newDamage);
                     oneItemRepair = Math.min(reforgeCopy.func_77952_i(), reforgeCopy.func_77958_k() / 4);
                  }

                  this.reforgeCost += usedMaterials;
               }
            }

            this.engraveOwnerCost = 2;
         } else {
            this.reforgeCost = 0;
            this.engraveOwnerCost = 0;
         }

         if (this.isRepairMaterial(inputItem, new ItemStack(Items.field_151007_F))) {
            stringFactor = 3;
            this.materialCost *= stringFactor;
            this.reforgeCost *= stringFactor;
            this.engraveOwnerCost *= stringFactor;
         }

         if (this.isTrader) {
            boolean isCommonRenameOnly = nameChange && this.materialCost == 0;
            float materialPrice = this.getTraderMaterialPrice(inputItem);
            if (materialPrice > 0.0F) {
               this.materialCost = Math.round((float)this.materialCost * materialPrice);
               this.materialCost = Math.max(this.materialCost, 1);
               this.reforgeCost = Math.round((float)this.reforgeCost * materialPrice);
               this.reforgeCost = Math.max(this.reforgeCost, 1);
               this.engraveOwnerCost = Math.round((float)this.engraveOwnerCost * materialPrice);
               this.engraveOwnerCost = Math.max(this.engraveOwnerCost, 1);
               if (this.theTrader instanceof LOTREntityScrapTrader) {
                  this.materialCost = MathHelper.func_76123_f((float)this.materialCost * 0.5F);
                  this.materialCost = Math.max(this.materialCost, 1);
                  this.reforgeCost = MathHelper.func_76123_f((float)this.reforgeCost * 0.5F);
                  this.reforgeCost = Math.max(this.reforgeCost, 1);
                  this.engraveOwnerCost = MathHelper.func_76123_f((float)this.engraveOwnerCost * 0.5F);
                  this.engraveOwnerCost = Math.max(this.engraveOwnerCost, 1);
               }
            } else if (!isCommonRenameOnly) {
               this.invOutput.func_70299_a(0, (ItemStack)null);
               this.materialCost = 0;
               this.reforgeCost = 0;
               this.engraveOwnerCost = 0;
               return;
            }
         }

         if (!combining && !repairing && !nameChange && !alteringNameColor) {
            this.invOutput.func_70299_a(0, (ItemStack)null);
            this.materialCost = 0;
         } else {
            this.invOutput.func_70299_a(0, inputCopy);
         }

         this.func_75142_b();
      }

   }

   private static boolean costsToRename(ItemStack itemstack) {
      Item item = itemstack.func_77973_b();
      if (!(item instanceof ItemSword) && !(item instanceof ItemTool)) {
         if (item instanceof ItemArmor && ((ItemArmor)item).field_77879_b > 0) {
            return true;
         } else {
            return item instanceof ItemBow || item instanceof LOTRItemCrossbow || item instanceof LOTRItemThrowingAxe || item instanceof LOTRItemBlowgun;
         }
      } else {
         return true;
      }
   }

   private static List getAppliedFormattingCodes(String name) {
      List colors = new ArrayList();
      EnumChatFormatting[] var2 = EnumChatFormatting.values();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         EnumChatFormatting color = var2[var4];
         String formatCode = color.toString();
         if (name.startsWith(formatCode)) {
            colors.add(color);
         }
      }

      return colors;
   }

   public static String stripFormattingCodes(String name) {
      EnumChatFormatting[] var1 = EnumChatFormatting.values();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         EnumChatFormatting color = var1[var3];
         String formatCode = color.toString();
         if (name.startsWith(formatCode)) {
            name = name.substring(formatCode.length());
         }
      }

      return name;
   }

   private static String applyFormattingCodes(String name, List colors) {
      EnumChatFormatting color;
      for(Iterator var2 = colors.iterator(); var2.hasNext(); name = color + name) {
         color = (EnumChatFormatting)var2.next();
      }

      return name;
   }

   public List getActiveItemNameFormatting() {
      ItemStack inputItem = this.invInput.func_70301_a(0);
      ItemStack resultItem = this.invOutput.func_70301_a(0);
      if (resultItem != null) {
         return getAppliedFormattingCodes(resultItem.func_82833_r());
      } else {
         return (List)(inputItem != null ? getAppliedFormattingCodes(inputItem.func_82833_r()) : new ArrayList());
      }
   }

   public boolean isRepairMaterial(ItemStack inputItem, ItemStack materialItem) {
      if (inputItem.func_77973_b().func_82789_a(inputItem, materialItem)) {
         return true;
      } else {
         Item item = inputItem.func_77973_b();
         if (item == Items.field_151031_f && LOTRMod.rohanBow.func_82789_a(inputItem, materialItem)) {
            return true;
         } else if (item instanceof ItemFishingRod && materialItem.func_77973_b() == Items.field_151007_F) {
            return true;
         } else if (item instanceof ItemShears && materialItem.func_77973_b() == Items.field_151042_j) {
            return true;
         } else if (item instanceof LOTRItemChisel && materialItem.func_77973_b() == Items.field_151042_j) {
            return true;
         } else if (item instanceof ItemEnchantedBook && materialItem.func_77973_b() == Items.field_151121_aF) {
            return true;
         } else {
            ToolMaterial material = null;
            if (item instanceof ItemTool) {
               material = ToolMaterial.valueOf(((ItemTool)item).func_77861_e());
            } else if (item instanceof ItemSword) {
               material = ToolMaterial.valueOf(((ItemSword)item).func_150932_j());
            }

            if (material != ToolMaterial.WOOD && material != LOTRMaterial.MOREDAIN_WOOD.toToolMaterial()) {
               if (material == LOTRMaterial.MALLORN.toToolMaterial()) {
                  return materialItem.func_77973_b() == Item.func_150898_a(LOTRMod.planks) && materialItem.func_77960_j() == 1;
               } else if (material != LOTRMaterial.MALLORN_MACE.toToolMaterial()) {
                  if (item instanceof ItemArmor) {
                     ItemArmor armor = (ItemArmor)item;
                     ArmorMaterial armorMaterial = armor.func_82812_d();
                     if (armorMaterial == LOTRMaterial.BONE.toArmorMaterial()) {
                        return LOTRMod.isOreNameEqual(materialItem, "bone");
                     }
                  }

                  return false;
               } else {
                  return materialItem.func_77973_b() == Item.func_150898_a(LOTRMod.wood) && materialItem.func_77960_j() == 1;
               }
            } else {
               return LOTRMod.isOreNameEqual(materialItem, "plankWood");
            }
         }
      }
   }

   private float getTraderMaterialPrice(ItemStack inputItem) {
      float materialPrice = 0.0F;
      LOTRTradeEntry[] sellTrades = this.theNPC.traderNPCInfo.getSellTrades();
      int var6;
      if (sellTrades != null) {
         LOTRTradeEntry[] var4 = sellTrades;
         int var5 = sellTrades.length;

         for(var6 = 0; var6 < var5; ++var6) {
            LOTRTradeEntry trade = var4[var6];
            ItemStack tradeItem = trade.createTradeItem();
            if (this.isRepairMaterial(inputItem, tradeItem)) {
               materialPrice = (float)trade.getCost() / (float)trade.createTradeItem().field_77994_a;
               break;
            }
         }
      }

      if (materialPrice <= 0.0F) {
         LOTRTradeEntries sellPool = this.theTrader.getSellPool();
         LOTRTradeEntry[] var11 = sellPool.tradeEntries;
         var6 = var11.length;

         for(int var12 = 0; var12 < var6; ++var12) {
            LOTRTradeEntry trade = var11[var12];
            ItemStack tradeItem = trade.createTradeItem();
            if (this.isRepairMaterial(inputItem, tradeItem)) {
               materialPrice = (float)trade.getCost() / (float)trade.createTradeItem().field_77994_a;
               break;
            }
         }
      }

      if (materialPrice <= 0.0F && (this.isRepairMaterial(inputItem, new ItemStack(LOTRMod.mithril)) || this.isRepairMaterial(inputItem, new ItemStack(LOTRMod.mithrilMail))) && this.theTrader instanceof LOTREntityDwarf) {
         materialPrice = 200.0F;
      }

      return materialPrice;
   }

   public void func_75142_b() {
      super.func_75142_b();

      for(int i = 0; i < this.field_75149_d.size(); ++i) {
         ICrafting crafting = (ICrafting)this.field_75149_d.get(i);
         crafting.func_71112_a(this, 0, this.materialCost);
         crafting.func_71112_a(this, 1, this.reforgeCost);
         crafting.func_71112_a(this, 3, this.engraveOwnerCost);
      }

   }

   @SideOnly(Side.CLIENT)
   public void func_75137_b(int i, int j) {
      if (i == 0) {
         this.materialCost = j;
      }

      if (i == 1) {
         this.reforgeCost = j;
      }

      if (i == 2) {
         this.clientReforgeTime = 40;
      }

      if (i == 3) {
         this.engraveOwnerCost = j;
      }

   }

   public boolean hasMaterialOrCoinAmount(int cost) {
      if (this.isTrader) {
         return LOTRItemCoin.getInventoryValue(this.thePlayer, false) >= cost;
      } else {
         ItemStack inputItem = this.invInput.func_70301_a(0);
         ItemStack materialItem = this.invInput.func_70301_a(2);
         if (materialItem == null) {
            return false;
         } else {
            return this.isRepairMaterial(inputItem, materialItem) && materialItem.field_77994_a >= cost;
         }
      }
   }

   public void takeMaterialOrCoinAmount(int cost) {
      if (this.isTrader) {
         if (!this.theWorld.field_72995_K) {
            LOTRItemCoin.takeCoins(cost, this.thePlayer);
            this.func_75142_b();
            this.theNPC.playTradeSound();
         }
      } else {
         ItemStack materialItem = this.invInput.func_70301_a(2);
         if (materialItem != null) {
            materialItem.field_77994_a -= cost;
            if (materialItem.field_77994_a <= 0) {
               this.invInput.func_70299_a(2, (ItemStack)null);
            } else {
               this.invInput.func_70299_a(2, materialItem);
            }
         }
      }

   }

   public void func_75134_a(EntityPlayer entityplayer) {
      super.func_75134_a(entityplayer);
      if (!this.theWorld.field_72995_K) {
         for(int i = 0; i < this.invInput.func_70302_i_(); ++i) {
            ItemStack itemstack = this.invInput.func_70304_b(i);
            if (itemstack != null) {
               entityplayer.func_71019_a(itemstack, false);
            }
         }

         if (this.doneMischief && this.isTrader && this.theNPC instanceof LOTREntityScrapTrader) {
            this.theNPC.sendSpeechBank(entityplayer, ((LOTREntityScrapTrader)this.theNPC).getSmithSpeechBank());
         }
      }

   }

   public boolean func_75145_c(EntityPlayer entityplayer) {
      if (this.isTrader) {
         return this.theNPC != null && (double)entityplayer.func_70032_d(this.theNPC) <= 12.0D && this.theNPC.func_70089_S() && this.theNPC.func_70638_az() == null && this.theTrader.canTradeWith(entityplayer);
      } else {
         return this.theWorld.func_147439_a(this.xCoord, this.yCoord, this.zCoord) == Blocks.field_150467_bQ && entityplayer.func_70092_e((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) <= 64.0D;
      }
   }

   public ItemStack func_75144_a(int slotNo, int j, int k, EntityPlayer entityplayer) {
      ItemStack resultItem = this.invOutput.func_70301_a(0);
      resultItem = ItemStack.func_77944_b(resultItem);
      boolean changed = false;
      ItemStack slotClickResult;
      if (resultItem != null && slotNo == this.func_75147_a(this.invOutput, 0).field_75222_d && !this.theWorld.field_72995_K && this.isTrader && this.theNPC instanceof LOTREntityScrapTrader) {
         slotClickResult = resultItem.func_77946_l();
         changed = this.applyMischief(slotClickResult);
         if (changed) {
            this.invOutput.func_70299_a(0, slotClickResult);
         }
      }

      slotClickResult = super.func_75144_a(slotNo, j, k, entityplayer);
      if (changed) {
         this.doneMischief = true;
         if (this.invOutput.func_70301_a(0) != null) {
            this.invOutput.func_70299_a(0, resultItem.func_77946_l());
         }
      }

      return slotClickResult;
   }

   public ItemStack func_82846_b(EntityPlayer entityplayer, int i) {
      ItemStack itemstack = null;
      Slot slot = (Slot)this.field_75151_b.get(i);
      if (slot != null && slot.func_75216_d()) {
         ItemStack itemstack1 = slot.func_75211_c();
         itemstack = itemstack1.func_77946_l();
         int inputSize = this.invInput.func_70302_i_();
         if (i == inputSize) {
            if (!this.func_75135_a(itemstack1, inputSize + 1, inputSize + 37, true)) {
               return null;
            }

            slot.func_75220_a(itemstack1, itemstack);
         } else if (i >= inputSize + 1) {
            if (i >= inputSize + 1 && i < inputSize + 37 && !this.func_75135_a(itemstack1, 0, inputSize, false)) {
               return null;
            }
         } else if (!this.func_75135_a(itemstack1, inputSize + 1, inputSize + 37, false)) {
            return null;
         }

         if (itemstack1.field_77994_a == 0) {
            slot.func_75215_d((ItemStack)null);
         } else {
            slot.func_75218_e();
         }

         if (itemstack1.field_77994_a == itemstack.field_77994_a) {
            return null;
         }

         slot.func_82870_a(entityplayer, itemstack1);
      }

      return itemstack;
   }

   public void updateItemName(String name) {
      List colors = getAppliedFormattingCodes(name);
      name = stripFormattingCodes(name);
      name = ChatAllowedCharacters.func_71565_a(name);
      this.repairedItemName = name;
      ItemStack itemstack = this.invOutput.func_70301_a(0);
      if (itemstack != null) {
         if (StringUtils.isBlank(this.repairedItemName)) {
            itemstack.func_135074_t();
         } else {
            itemstack.func_151001_c(this.repairedItemName);
         }

         if (!colors.isEmpty()) {
            itemstack.func_151001_c(applyFormattingCodes(itemstack.func_82833_r(), colors));
         }
      }

      this.updateRepairOutput();
   }

   public void reforgeItem() {
      long curTime = System.currentTimeMillis();
      if (this.lastReforgeTime < 0L || curTime - this.lastReforgeTime >= 2000L) {
         ItemStack inputItem = this.invInput.func_70301_a(0);
         if (inputItem != null && this.reforgeCost > 0 && this.hasMaterialOrCoinAmount(this.reforgeCost)) {
            int cost = this.reforgeCost;
            if (inputItem.func_77984_f()) {
               inputItem.func_77964_b(0);
            }

            LOTREnchantmentHelper.applyRandomEnchantments(inputItem, this.theWorld.field_73012_v, true, true);
            LOTREnchantmentHelper.setAnvilCost(inputItem, 0);
            if (this.isTrader && this.theNPC instanceof LOTREntityScrapTrader) {
               boolean changed = this.applyMischief(inputItem);
               if (changed) {
                  this.doneMischief = true;
               }
            }

            this.invInput.func_70299_a(0, inputItem);
            this.takeMaterialOrCoinAmount(cost);
            this.playAnvilSound();
            this.lastReforgeTime = curTime;
            ((EntityPlayerMP)this.thePlayer).func_71112_a(this, 2, 0);
            if (!this.isTrader) {
               LOTRLevelData.getData(this.thePlayer).addAchievement(LOTRAchievement.reforge);
            }
         }
      }

   }

   public boolean canEngraveNewOwner(ItemStack itemstack, EntityPlayer entityplayer) {
      String currentOwner = LOTRItemOwnership.getCurrentOwner(itemstack);
      if (currentOwner == null) {
         return true;
      } else {
         return !currentOwner.equals(entityplayer.func_70005_c_());
      }
   }

   public void engraveOwnership() {
      ItemStack inputItem = this.invInput.func_70301_a(0);
      if (inputItem != null && this.engraveOwnerCost > 0 && this.hasMaterialOrCoinAmount(this.engraveOwnerCost)) {
         int cost = this.engraveOwnerCost;
         LOTRItemOwnership.setCurrentOwner(inputItem, this.thePlayer.func_70005_c_());
         if (this.isTrader && this.theNPC instanceof LOTREntityScrapTrader) {
            boolean changed = this.applyMischief(inputItem);
            if (changed) {
               this.doneMischief = true;
            }
         }

         this.invInput.func_70299_a(0, inputItem);
         this.takeMaterialOrCoinAmount(cost);
         this.playAnvilSound();
         LOTRLevelData.getData(this.thePlayer).addAchievement(LOTRAchievement.engraveOwnership);
      }

   }

   private boolean applyMischief(ItemStack itemstack) {
      boolean changed = false;
      Random rand = this.theWorld.field_73012_v;
      if (rand.nextFloat() < 0.8F) {
         String name = itemstack.func_82833_r();
         name = OddmentCollectorNameMischief.garbleName(name, rand);
         if (name.equals(itemstack.func_77973_b().func_77653_i(itemstack))) {
            itemstack.func_135074_t();
         } else {
            itemstack.func_151001_c(name);
         }

         changed = true;
      }

      if (rand.nextFloat() < 0.2F) {
         LOTREnchantmentHelper.applyRandomEnchantments(itemstack, rand, false, true);
         changed = true;
      }

      return changed;
   }

   public void playAnvilSound() {
      if (!this.theWorld.field_72995_K) {
         int i;
         int j;
         int k;
         if (this.isTrader) {
            i = MathHelper.func_76128_c(this.theNPC.field_70165_t);
            j = MathHelper.func_76128_c(this.theNPC.field_70163_u);
            k = MathHelper.func_76128_c(this.theNPC.field_70161_v);
         } else {
            i = this.xCoord;
            j = this.yCoord;
            k = this.zCoord;
         }

         this.theWorld.func_72926_e(1021, i, j, k, 0);
      }

   }
}
