package lotr.common.recipe;

import cpw.mods.fml.common.FMLLog;
import java.lang.reflect.Field;
import java.util.Iterator;
import lotr.common.LOTRMod;
import lotr.common.item.LOTRPoisonedDrinks;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class LOTRRecipesPoisonDrinks implements IRecipe {
   public boolean func_77569_a(InventoryCrafting inv, World world) {
      ItemStack drink = null;
      ItemStack poison = null;

      for(int i = 0; i < inv.func_70302_i_(); ++i) {
         ItemStack itemstack = inv.func_70301_a(i);
         if (itemstack != null) {
            if (LOTRPoisonedDrinks.canPoison(itemstack)) {
               if (drink != null) {
                  return false;
               }

               drink = itemstack;
            } else {
               if (!LOTRMod.isOreNameEqual(itemstack, "poison")) {
                  return false;
               }

               if (poison != null) {
                  return false;
               }

               poison = itemstack;
            }
         }
      }

      return drink != null && poison != null;
   }

   public ItemStack func_77572_b(InventoryCrafting inv) {
      ItemStack drink = null;
      ItemStack poison = null;

      for(int i = 0; i < inv.func_70302_i_(); ++i) {
         ItemStack itemstack = inv.func_70301_a(i);
         if (itemstack != null) {
            if (LOTRPoisonedDrinks.canPoison(itemstack)) {
               if (drink != null) {
                  return null;
               }

               drink = itemstack.func_77946_l();
            } else {
               if (!LOTRMod.isOreNameEqual(itemstack, "poison")) {
                  return null;
               }

               if (poison != null) {
                  return null;
               }

               poison = itemstack.func_77946_l();
            }
         }
      }

      if (drink != null && poison != null) {
         ItemStack result = drink.func_77946_l();
         LOTRPoisonedDrinks.setDrinkPoisoned(result, true);
         EntityPlayer craftingPlayer = null;

         try {
            if (inv instanceof InventoryCrafting) {
               Container cwb = null;
               Field[] var7 = inv.getClass().getDeclaredFields();
               int var8 = var7.length;

               for(int var9 = 0; var9 < var8; ++var9) {
                  Field f = var7[var9];
                  f.setAccessible(true);
                  Object obj = f.get(inv);
                  if (obj instanceof Container) {
                     cwb = (Container)obj;
                     break;
                  }
               }

               if (cwb != null) {
                  Iterator var15 = cwb.field_75151_b.iterator();

                  while(var15.hasNext()) {
                     Object obj = var15.next();
                     Slot slot = (Slot)obj;
                     IInventory slotInv = slot.field_75224_c;
                     if (slotInv instanceof InventoryPlayer) {
                        InventoryPlayer playerInv = (InventoryPlayer)slotInv;
                        craftingPlayer = playerInv.field_70458_d;
                        break;
                     }
                  }
               }
            }
         } catch (Exception var12) {
            var12.printStackTrace();
         }

         if (craftingPlayer != null) {
            LOTRPoisonedDrinks.setPoisonerPlayer(result, craftingPlayer);
         } else {
            FMLLog.bigWarning("LOTR Warning! Poisoned drink was crafted, player could not be found!", new Object[0]);
         }

         return result;
      } else {
         return null;
      }
   }

   public int func_77570_a() {
      return 2;
   }

   public ItemStack func_77571_b() {
      return null;
   }
}
