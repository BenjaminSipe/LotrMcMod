package lotr.common.recipe;

import java.util.HashMap;
import java.util.Map;
import lotr.common.LOTRMod;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class LOTRRecipePoisonWeapon implements IRecipe {
   private Item inputItem;
   private Item resultItem;
   private Object catalystObj;
   public static Map inputToPoisoned = new HashMap();
   public static Map poisonedToInput = new HashMap();

   public LOTRRecipePoisonWeapon(Item item1, Item item2) {
      this(item1, item2, "poison");
      inputToPoisoned.put(item1, item2);
      poisonedToInput.put(item2, item1);
   }

   public LOTRRecipePoisonWeapon(Item item1, Item item2, Object cat) {
      this.inputItem = item1;
      this.resultItem = item2;
      this.catalystObj = cat;
   }

   public boolean func_77569_a(InventoryCrafting inv, World world) {
      ItemStack weapon = null;
      ItemStack catalyst = null;

      for(int i = 0; i < inv.func_70302_i_(); ++i) {
         ItemStack itemstack = inv.func_70301_a(i);
         if (itemstack != null) {
            if (itemstack.func_77973_b() == this.inputItem) {
               if (weapon != null) {
                  return false;
               }

               weapon = itemstack;
            } else {
               if (!this.matchesCatalyst(itemstack)) {
                  return false;
               }

               if (catalyst != null) {
                  return false;
               }

               catalyst = itemstack;
            }
         }
      }

      return weapon != null && catalyst != null;
   }

   private boolean matchesCatalyst(ItemStack itemstack) {
      if (this.catalystObj instanceof String) {
         return LOTRMod.isOreNameEqual(itemstack, (String)this.catalystObj);
      } else if (this.catalystObj instanceof Item) {
         return itemstack.func_77973_b() == (Item)this.catalystObj;
      } else {
         return this.catalystObj instanceof ItemStack ? LOTRRecipes.checkItemEquals((ItemStack)this.catalystObj, itemstack) : false;
      }
   }

   public ItemStack func_77572_b(InventoryCrafting inv) {
      ItemStack weapon = null;
      ItemStack catalyst = null;

      for(int i = 0; i < inv.func_70302_i_(); ++i) {
         ItemStack itemstack = inv.func_70301_a(i);
         if (itemstack != null) {
            if (itemstack.func_77973_b() == this.inputItem) {
               if (weapon != null) {
                  return null;
               }

               weapon = itemstack.func_77946_l();
            } else {
               if (!this.matchesCatalyst(itemstack)) {
                  return null;
               }

               if (catalyst != null) {
                  return null;
               }

               catalyst = itemstack.func_77946_l();
            }
         }
      }

      if (weapon != null && catalyst != null) {
         ItemStack result = new ItemStack(this.resultItem);
         result.func_77964_b(weapon.func_77960_j());
         if (weapon.func_77942_o()) {
            NBTTagCompound nbt = (NBTTagCompound)weapon.func_77978_p().func_74737_b();
            result.func_77982_d(nbt);
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
      return new ItemStack(this.resultItem);
   }

   public ItemStack getInputItem() {
      return new ItemStack(this.inputItem);
   }
}
