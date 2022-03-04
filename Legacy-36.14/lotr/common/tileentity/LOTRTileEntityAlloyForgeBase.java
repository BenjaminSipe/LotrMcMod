package lotr.common.tileentity;

import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

public abstract class LOTRTileEntityAlloyForgeBase extends LOTRTileEntityForgeBase {
   public int getForgeInvSize() {
      return 13;
   }

   public void setupForgeSlots() {
      this.inputSlots = new int[]{4, 5, 6, 7};
      this.outputSlots = new int[]{8, 9, 10, 11};
      this.fuelSlot = 12;
   }

   protected boolean canMachineInsertInput(ItemStack itemstack) {
      return itemstack != null && this.getSmeltingResult(itemstack) != null;
   }

   public int getSmeltingDuration() {
      return 200;
   }

   protected boolean canDoSmelting() {
      for(int i = 4; i < 8; ++i) {
         if (this.canSmelt(i)) {
            return true;
         }
      }

      return false;
   }

   protected void doSmelt() {
      for(int i = 4; i < 8; ++i) {
         this.smeltItemInSlot(i);
      }

   }

   private boolean canSmelt(int i) {
      if (this.inventory[i] == null) {
         return false;
      } else {
         ItemStack result;
         int resultSize;
         if (this.inventory[i - 4] != null) {
            result = this.getAlloySmeltingResult(this.inventory[i], this.inventory[i - 4]);
            if (result != null) {
               if (this.inventory[i + 4] == null) {
                  return true;
               }

               resultSize = this.inventory[i + 4].field_77994_a + result.field_77994_a;
               if (this.inventory[i + 4].func_77969_a(result) && resultSize <= this.func_70297_j_() && resultSize <= result.func_77976_d()) {
                  return true;
               }
            }
         }

         result = this.getSmeltingResult(this.inventory[i]);
         if (result == null) {
            return false;
         } else if (this.inventory[i + 4] == null) {
            return true;
         } else if (!this.inventory[i + 4].func_77969_a(result)) {
            return false;
         } else {
            resultSize = this.inventory[i + 4].field_77994_a + result.field_77994_a;
            return resultSize <= this.func_70297_j_() && resultSize <= result.func_77976_d();
         }
      }
   }

   private void smeltItemInSlot(int i) {
      if (this.canSmelt(i)) {
         boolean smeltedAlloyItem = false;
         ItemStack var10000;
         ItemStack result;
         if (this.inventory[i - 4] != null) {
            result = this.getAlloySmeltingResult(this.inventory[i], this.inventory[i - 4]);
            if (result != null && (this.inventory[i + 4] == null || this.inventory[i + 4].func_77969_a(result))) {
               if (this.inventory[i + 4] == null) {
                  this.inventory[i + 4] = result.func_77946_l();
               } else if (this.inventory[i + 4].func_77969_a(result)) {
                  var10000 = this.inventory[i + 4];
                  var10000.field_77994_a += result.field_77994_a;
               }

               --this.inventory[i].field_77994_a;
               if (this.inventory[i].field_77994_a <= 0) {
                  this.inventory[i] = null;
               }

               --this.inventory[i - 4].field_77994_a;
               if (this.inventory[i - 4].field_77994_a <= 0) {
                  this.inventory[i - 4] = null;
               }

               smeltedAlloyItem = true;
            }
         }

         if (!smeltedAlloyItem) {
            result = this.getSmeltingResult(this.inventory[i]);
            if (this.inventory[i + 4] == null) {
               this.inventory[i + 4] = result.func_77946_l();
            } else if (this.inventory[i + 4].func_77969_a(result)) {
               var10000 = this.inventory[i + 4];
               var10000.field_77994_a += result.field_77994_a;
            }

            --this.inventory[i].field_77994_a;
            if (this.inventory[i].field_77994_a <= 0) {
               this.inventory[i] = null;
            }
         }
      }

   }

   public ItemStack getSmeltingResult(ItemStack itemstack) {
      boolean isStoneMaterial = false;
      Item item = itemstack.func_77973_b();
      Block block = Block.func_149634_a(item);
      if (block != null && block != Blocks.field_150350_a) {
         Material material = block.func_149688_o();
         if (material == Material.field_151576_e || material == Material.field_151595_p || material == Material.field_151571_B) {
            isStoneMaterial = true;
         }
      } else if (item == Items.field_151119_aD || item == LOTRMod.redClayBall || item == LOTRMod.clayMug || item == LOTRMod.clayPlate || item == LOTRMod.ceramicPlate) {
         isStoneMaterial = true;
      }

      return !isStoneMaterial && !this.isWood(itemstack) ? null : FurnaceRecipes.func_77602_a().func_151395_a(itemstack);
   }

   protected ItemStack getAlloySmeltingResult(ItemStack itemstack, ItemStack alloyItem) {
      return (!this.isCopper(itemstack) || !this.isTin(alloyItem)) && (!this.isTin(itemstack) || !this.isCopper(alloyItem)) ? null : new ItemStack(LOTRMod.bronze, 2);
   }

   protected boolean isCopper(ItemStack itemstack) {
      return LOTRMod.isOreNameEqual(itemstack, "oreCopper") || LOTRMod.isOreNameEqual(itemstack, "ingotCopper");
   }

   protected boolean isTin(ItemStack itemstack) {
      return LOTRMod.isOreNameEqual(itemstack, "oreTin") || LOTRMod.isOreNameEqual(itemstack, "ingotTin");
   }

   protected boolean isIron(ItemStack itemstack) {
      return LOTRMod.isOreNameEqual(itemstack, "oreIron") || LOTRMod.isOreNameEqual(itemstack, "ingotIron");
   }

   protected boolean isGold(ItemStack itemstack) {
      return LOTRMod.isOreNameEqual(itemstack, "oreGold") || LOTRMod.isOreNameEqual(itemstack, "ingotGold");
   }

   protected boolean isGoldNugget(ItemStack itemstack) {
      return LOTRMod.isOreNameEqual(itemstack, "nuggetGold");
   }

   protected boolean isSilver(ItemStack itemstack) {
      return LOTRMod.isOreNameEqual(itemstack, "oreSilver") || LOTRMod.isOreNameEqual(itemstack, "ingotSilver");
   }

   protected boolean isSilverNugget(ItemStack itemstack) {
      return LOTRMod.isOreNameEqual(itemstack, "nuggetSilver");
   }

   protected boolean isMithril(ItemStack itemstack) {
      return itemstack.func_77973_b() == Item.func_150898_a(LOTRMod.oreMithril) || itemstack.func_77973_b() == LOTRMod.mithril;
   }

   protected boolean isMithrilNugget(ItemStack itemstack) {
      return itemstack.func_77973_b() == LOTRMod.mithrilNugget;
   }

   protected boolean isOrcSteel(ItemStack itemstack) {
      return itemstack.func_77973_b() == Item.func_150898_a(LOTRMod.oreMorgulIron) || itemstack.func_77973_b() == LOTRMod.orcSteel;
   }

   protected boolean isWood(ItemStack itemstack) {
      return LOTRMod.isOreNameEqual(itemstack, "logWood");
   }

   protected boolean isCoal(ItemStack itemstack) {
      return itemstack.func_77973_b() == Items.field_151044_h;
   }
}
