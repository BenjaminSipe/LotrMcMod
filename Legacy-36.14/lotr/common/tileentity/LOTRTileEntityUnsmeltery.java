package lotr.common.tileentity;

import com.mojang.authlib.GameProfile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import lotr.common.LOTRMod;
import lotr.common.block.LOTRBlockCraftingTable;
import lotr.common.inventory.LOTRContainerCraftingTable;
import lotr.common.item.LOTRItemCrossbow;
import lotr.common.item.LOTRItemMountArmor;
import lotr.common.item.LOTRItemThrowingAxe;
import lotr.common.item.LOTRMaterial;
import lotr.common.item.LOTRStoryItem;
import lotr.common.recipe.LOTRRecipePoisonWeapon;
import lotr.common.recipe.LOTRRecipes;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import org.apache.commons.lang3.tuple.Pair;

public class LOTRTileEntityUnsmeltery extends LOTRTileEntityForgeBase {
   private static Random unsmeltingRand = new Random();
   private static Map unsmeltableCraftingCounts = new HashMap();
   private float prevRocking;
   private float rocking;
   private float prevRockingPhase;
   private float rockingPhase;
   private boolean prevServerActive;
   private boolean serverActive;
   private boolean clientActive;

   public LOTRTileEntityUnsmeltery() {
      this.rockingPhase = unsmeltingRand.nextFloat() * 3.1415927F * 2.0F;
   }

   public int getForgeInvSize() {
      return 3;
   }

   public void setupForgeSlots() {
      this.inputSlots = new int[]{0};
      this.fuelSlot = 1;
      this.outputSlots = new int[]{2};
   }

   protected boolean canMachineInsertInput(ItemStack itemstack) {
      return itemstack != null && this.getLargestUnsmeltingResult(itemstack) != null;
   }

   public void func_145845_h() {
      super.func_145845_h();
      if (!this.field_145850_b.field_72995_K) {
         this.prevServerActive = this.serverActive;
         this.serverActive = this.isSmelting();
         if (this.serverActive != this.prevServerActive) {
            this.field_145850_b.func_147471_g(this.field_145851_c, this.field_145848_d, this.field_145849_e);
         }
      } else {
         this.prevRocking = this.rocking;
         this.prevRockingPhase = this.rockingPhase;
         this.rockingPhase += 0.1F;
         if (this.clientActive) {
            this.rocking += 0.05F;
         } else {
            this.rocking -= 0.01F;
         }

         this.rocking = MathHelper.func_76131_a(this.rocking, 0.0F, 1.0F);
      }

   }

   public float getRockingAmount(float tick) {
      float mag = this.prevRocking + (this.rocking - this.prevRocking) * tick;
      float phase = this.prevRockingPhase + (this.rockingPhase - this.prevRockingPhase) * tick;
      return mag * MathHelper.func_76126_a(phase);
   }

   public int getSmeltingDuration() {
      return 400;
   }

   protected boolean canDoSmelting() {
      ItemStack input = this.inventory[this.inputSlots[0]];
      if (input == null) {
         return false;
      } else {
         ItemStack result = this.getLargestUnsmeltingResult(input);
         if (result == null) {
            return false;
         } else {
            ItemStack output = this.inventory[this.outputSlots[0]];
            if (output == null) {
               return true;
            } else if (!output.func_77969_a(result)) {
               return false;
            } else {
               int resultSize = output.field_77994_a + result.field_77994_a;
               return resultSize <= this.func_70297_j_() && resultSize <= result.func_77976_d();
            }
         }
      }
   }

   protected void doSmelt() {
      if (this.canDoSmelting()) {
         ItemStack input = this.inventory[this.inputSlots[0]];
         ItemStack result = this.getRandomUnsmeltingResult(input);
         if (result != null) {
            int slot = this.outputSlots[0];
            if (this.inventory[this.outputSlots[0]] == null) {
               this.inventory[this.outputSlots[0]] = result.func_77946_l();
            } else if (this.inventory[this.outputSlots[0]].func_77969_a(result)) {
               ItemStack var10000 = this.inventory[this.outputSlots[0]];
               var10000.field_77994_a += result.field_77994_a;
            }
         }

         --this.inventory[this.inputSlots[0]].field_77994_a;
         if (this.inventory[this.inputSlots[0]].field_77994_a <= 0) {
            this.inventory[this.inputSlots[0]] = null;
         }
      }

   }

   public String getForgeName() {
      return StatCollector.func_74838_a("container.lotr.unsmeltery");
   }

   public boolean canBeUnsmelted(ItemStack itemstack) {
      if (itemstack == null) {
         return false;
      } else if (itemstack.func_77973_b() instanceof LOTRStoryItem) {
         return false;
      } else {
         ItemStack material = getEquipmentMaterial(itemstack);
         if (material != null) {
            if (TileEntityFurnace.func_145952_a(material) != 0) {
               return false;
            } else if (itemstack.func_77973_b() instanceof ItemBlock && Block.func_149634_a(itemstack.func_77973_b()).func_149688_o().func_76217_h()) {
               return false;
            } else {
               return this.determineResourcesUsed(itemstack, material) > 0;
            }
         } else {
            return false;
         }
      }
   }

   private ItemStack getLargestUnsmeltingResult(ItemStack itemstack) {
      if (itemstack != null && this.canBeUnsmelted(itemstack)) {
         ItemStack material = getEquipmentMaterial(itemstack);
         int items = this.determineResourcesUsed(itemstack, material);
         int meta = material.func_77960_j();
         if (meta == 32767) {
            meta = 0;
         }

         return new ItemStack(material.func_77973_b(), items, meta);
      } else {
         return null;
      }
   }

   private ItemStack getRandomUnsmeltingResult(ItemStack itemstack) {
      ItemStack result = this.getLargestUnsmeltingResult(itemstack);
      if (result == null) {
         return null;
      } else {
         float items = (float)result.field_77994_a;
         items *= 0.8F;
         if (itemstack.func_77984_f()) {
            items *= (float)(itemstack.func_77958_k() - itemstack.func_77960_j()) / (float)itemstack.func_77958_k();
         }

         items *= MathHelper.func_151240_a(unsmeltingRand, 0.7F, 1.0F);
         int items_int = Math.round(items);
         return items_int <= 0 ? null : new ItemStack(result.func_77973_b(), items_int, result.func_77960_j());
      }
   }

   private static ItemStack getEquipmentMaterial(ItemStack itemstack) {
      if (itemstack == null) {
         return null;
      } else {
         Item item = itemstack.func_77973_b();
         ItemStack material = null;
         if (item instanceof ItemTool) {
            material = ((ItemTool)item).func_150913_i().getRepairItemStack();
         } else if (item instanceof ItemSword) {
            material = LOTRMaterial.getToolMaterialByName(((ItemSword)item).func_150932_j()).getRepairItemStack();
         } else if (item instanceof LOTRItemCrossbow) {
            material = ((LOTRItemCrossbow)item).getCrossbowMaterial().getRepairItemStack();
         } else if (item instanceof LOTRItemThrowingAxe) {
            material = ((LOTRItemThrowingAxe)item).getAxeMaterial().getRepairItemStack();
         } else if (item instanceof ItemArmor) {
            material = new ItemStack(((ItemArmor)item).func_82812_d().func_151685_b());
         } else if (item instanceof LOTRItemMountArmor) {
            material = new ItemStack(((LOTRItemMountArmor)item).getMountArmorMaterial().func_151685_b());
         }

         if (material != null) {
            if (item.func_82789_a(itemstack, material)) {
               return material;
            }
         } else {
            if (item instanceof ItemHoe) {
               return LOTRMaterial.getToolMaterialByName(((ItemHoe)item).func_77842_f()).getRepairItemStack();
            }

            if (item == Items.field_151133_ar) {
               return new ItemStack(Items.field_151042_j);
            }

            if (item == LOTRMod.silverRing) {
               return new ItemStack(LOTRMod.silverNugget);
            }

            if (item == LOTRMod.goldRing) {
               return new ItemStack(Items.field_151074_bl);
            }

            if (item == LOTRMod.mithrilRing) {
               return new ItemStack(LOTRMod.mithrilNugget);
            }

            if (item == LOTRMod.gobletGold) {
               return new ItemStack(Items.field_151043_k);
            }

            if (item == LOTRMod.gobletSilver) {
               return new ItemStack(LOTRMod.silver);
            }

            if (item == LOTRMod.gobletCopper) {
               return new ItemStack(LOTRMod.copper);
            }
         }

         return null;
      }
   }

   private int determineResourcesUsed(ItemStack itemstack, ItemStack material) {
      return this.determineResourcesUsed(itemstack, material, (List)null);
   }

   private int determineResourcesUsed(ItemStack itemstack, ItemStack material, List recursiveCheckedRecipes) {
      if (itemstack == null) {
         return 0;
      } else {
         Pair key = Pair.of(itemstack.func_77973_b(), itemstack.func_77960_j());
         if (unsmeltableCraftingCounts.containsKey(key)) {
            return (Integer)unsmeltableCraftingCounts.get(key);
         } else {
            int count = 0;
            List allRecipeLists = new ArrayList();
            allRecipeLists.add(CraftingManager.func_77594_a().func_77592_b());
            EntityPlayer player = this.getProxyPlayer();
            Iterator var8 = LOTRBlockCraftingTable.allCraftingTables.iterator();

            while(var8.hasNext()) {
               LOTRBlockCraftingTable table = (LOTRBlockCraftingTable)var8.next();
               Object container = LOTRMod.proxy.getServerGuiElement(table.tableGUIID, player, this.field_145850_b, 0, 0, 0);
               if (container instanceof LOTRContainerCraftingTable) {
                  LOTRContainerCraftingTable containerTable = (LOTRContainerCraftingTable)container;
                  allRecipeLists.add(containerTable.recipeList);
               }
            }

            allRecipeLists.add(LOTRRecipes.uncraftableUnsmeltingRecipes);
            if (recursiveCheckedRecipes == null) {
               recursiveCheckedRecipes = new ArrayList();
            }

            var8 = allRecipeLists.iterator();

            label89:
            while(var8.hasNext()) {
               List recipes = (List)var8.next();
               Iterator var18 = recipes.iterator();

               while(true) {
                  IRecipe irecipe;
                  ItemStack result;
                  do {
                     do {
                        do {
                           do {
                              if (!var18.hasNext()) {
                                 continue label89;
                              }

                              Object recipesObj = var18.next();
                              irecipe = (IRecipe)recipesObj;
                           } while(((List)recursiveCheckedRecipes).contains(irecipe));

                           result = irecipe.func_77571_b();
                        } while(result == null);
                     } while(result.func_77973_b() != itemstack.func_77973_b());
                  } while(!itemstack.func_77984_f() && result.func_77960_j() != itemstack.func_77960_j());

                  ((List)recursiveCheckedRecipes).add(irecipe);
                  int i;
                  if (irecipe instanceof ShapedRecipes) {
                     ShapedRecipes shaped = (ShapedRecipes)irecipe;
                     ItemStack[] ingredients = shaped.field_77574_d;
                     i = this.countMatchingIngredients(material, Arrays.asList(ingredients), (List)recursiveCheckedRecipes);
                     i /= result.field_77994_a;
                     if (i > 0) {
                        count = i;
                        break label89;
                     }
                  }

                  if (irecipe instanceof ShapelessRecipes) {
                     ShapelessRecipes shapeless = (ShapelessRecipes)irecipe;
                     List ingredients = shapeless.field_77579_b;
                     i = this.countMatchingIngredients(material, ingredients, (List)recursiveCheckedRecipes);
                     i /= result.field_77994_a;
                     if (i > 0) {
                        count = i;
                        break label89;
                     }
                  }

                  Object[] ingredients;
                  if (irecipe instanceof ShapedOreRecipe) {
                     ShapedOreRecipe shaped = (ShapedOreRecipe)irecipe;
                     ingredients = shaped.getInput();
                     i = this.countMatchingIngredients(material, Arrays.asList(ingredients), (List)recursiveCheckedRecipes);
                     i /= result.field_77994_a;
                     if (i > 0) {
                        count = i;
                        break label89;
                     }
                  }

                  if (irecipe instanceof ShapelessOreRecipe) {
                     ShapelessOreRecipe shapeless = (ShapelessOreRecipe)irecipe;
                     List ingredients = shapeless.getInput();
                     i = this.countMatchingIngredients(material, ingredients, (List)recursiveCheckedRecipes);
                     i /= result.field_77994_a;
                     if (i > 0) {
                        count = i;
                        break label89;
                     }
                  }

                  if (irecipe instanceof LOTRRecipePoisonWeapon) {
                     LOTRRecipePoisonWeapon poison = (LOTRRecipePoisonWeapon)irecipe;
                     ingredients = new Object[]{poison.getInputItem()};
                     i = this.countMatchingIngredients(material, Arrays.asList(ingredients), (List)recursiveCheckedRecipes);
                     i /= result.field_77994_a;
                     if (i > 0) {
                        count = i;
                        break label89;
                     }
                  }
               }
            }

            unsmeltableCraftingCounts.put(key, count);
            return count;
         }
      }
   }

   private EntityPlayer getProxyPlayer() {
      return (EntityPlayer)(!this.field_145850_b.field_72995_K ? FakePlayerFactory.get((WorldServer)this.field_145850_b, new GameProfile((UUID)null, "LOTRUnsmeltery")) : LOTRMod.proxy.getClientPlayer());
   }

   private int countMatchingIngredients(ItemStack material, List ingredientList, List recursiveCheckedRecipes) {
      int i = 0;
      Iterator var5 = ingredientList.iterator();

      while(true) {
         while(var5.hasNext()) {
            Object obj = var5.next();
            if (obj instanceof ItemStack) {
               ItemStack ingredient = (ItemStack)obj;
               if (OreDictionary.itemMatches(material, ingredient, false)) {
                  ++i;
               } else {
                  int sub = this.determineResourcesUsed(ingredient, material, recursiveCheckedRecipes);
                  if (sub > 0) {
                     i += sub;
                  }
               }
            } else if (obj instanceof List) {
               List oreIngredients = (List)obj;
               boolean matched = false;
               Iterator var9 = oreIngredients.iterator();

               ItemStack ingredient;
               while(var9.hasNext()) {
                  ingredient = (ItemStack)var9.next();
                  if (OreDictionary.itemMatches(material, ingredient, false)) {
                     matched = true;
                     break;
                  }
               }

               if (matched) {
                  ++i;
               } else {
                  var9 = oreIngredients.iterator();

                  while(var9.hasNext()) {
                     ingredient = (ItemStack)var9.next();
                     int sub = this.determineResourcesUsed(ingredient, material, recursiveCheckedRecipes);
                     if (sub > 0) {
                        i += sub;
                        break;
                     }
                  }
               }
            }
         }

         return i;
      }
   }

   public Packet func_145844_m() {
      NBTTagCompound data = new NBTTagCompound();
      data.func_74757_a("Active", this.serverActive);
      return new S35PacketUpdateTileEntity(this.field_145851_c, this.field_145848_d, this.field_145849_e, 0, data);
   }

   public void onDataPacket(NetworkManager manager, S35PacketUpdateTileEntity packet) {
      NBTTagCompound data = packet.func_148857_g();
      this.clientActive = data.func_74767_n("Active");
   }
}
