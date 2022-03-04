package lotr.common.recipe;

import java.util.ArrayList;
import java.util.Iterator;
import lotr.common.LOTRMod;
import lotr.common.tileentity.LOTRTileEntityBarrel;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class LOTRBrewingRecipes {
   private static ArrayList recipes = new ArrayList();
   public static int BARREL_CAPACITY = 16;

   public static void createBrewingRecipes() {
      addBrewingRecipe(new ItemStack(LOTRMod.mugAle, BARREL_CAPACITY), Items.field_151015_O, Items.field_151015_O, Items.field_151015_O, Items.field_151015_O, Items.field_151015_O, Items.field_151015_O);
      addBrewingRecipe(new ItemStack(LOTRMod.mugMiruvor, BARREL_CAPACITY), LOTRMod.mallornNut, LOTRMod.mallornNut, LOTRMod.mallornNut, LOTRMod.elanor, LOTRMod.niphredil, Items.field_151102_aT);
      addBrewingRecipe(new ItemStack(LOTRMod.mugOrcDraught, BARREL_CAPACITY), LOTRMod.morgulShroom, LOTRMod.morgulShroom, LOTRMod.morgulShroom, "bone", "bone", "bone");
      addBrewingRecipe(new ItemStack(LOTRMod.mugMead, BARREL_CAPACITY), Items.field_151102_aT, Items.field_151102_aT, Items.field_151102_aT, Items.field_151102_aT, Items.field_151102_aT, Items.field_151102_aT);
      addBrewingRecipe(new ItemStack(LOTRMod.mugCider, BARREL_CAPACITY), "apple", "apple", "apple", "apple", "apple", "apple");
      addBrewingRecipe(new ItemStack(LOTRMod.mugPerry, BARREL_CAPACITY), LOTRMod.pear, LOTRMod.pear, LOTRMod.pear, LOTRMod.pear, LOTRMod.pear, LOTRMod.pear);
      addBrewingRecipe(new ItemStack(LOTRMod.mugCherryLiqueur, BARREL_CAPACITY), LOTRMod.cherry, LOTRMod.cherry, LOTRMod.cherry, LOTRMod.cherry, LOTRMod.cherry, LOTRMod.cherry);
      addBrewingRecipe(new ItemStack(LOTRMod.mugRum, BARREL_CAPACITY), Items.field_151120_aE, Items.field_151120_aE, Items.field_151120_aE, Items.field_151120_aE, Items.field_151120_aE, Items.field_151120_aE);
      addBrewingRecipe(new ItemStack(LOTRMod.mugAthelasBrew, BARREL_CAPACITY), LOTRMod.athelas, LOTRMod.athelas, LOTRMod.athelas, LOTRMod.athelas, LOTRMod.athelas, LOTRMod.athelas);
      addBrewingRecipe(new ItemStack(LOTRMod.mugDwarvenTonic, BARREL_CAPACITY), Items.field_151015_O, Items.field_151015_O, Items.field_151015_O, LOTRMod.dwarfHerb, LOTRMod.dwarfHerb, LOTRMod.mithrilNugget);
      addBrewingRecipe(new ItemStack(LOTRMod.mugDwarvenAle, BARREL_CAPACITY), Items.field_151015_O, Items.field_151015_O, Items.field_151015_O, Items.field_151015_O, LOTRMod.dwarfHerb, LOTRMod.dwarfHerb);
      addBrewingRecipe(new ItemStack(LOTRMod.mugVodka, BARREL_CAPACITY), Items.field_151174_bG, Items.field_151174_bG, Items.field_151174_bG, Items.field_151174_bG, Items.field_151174_bG, Items.field_151174_bG);
      addBrewingRecipe(new ItemStack(LOTRMod.mugMapleBeer, BARREL_CAPACITY), Items.field_151015_O, Items.field_151015_O, Items.field_151015_O, Items.field_151015_O, LOTRMod.mapleSyrup, LOTRMod.mapleSyrup);
      addBrewingRecipe(new ItemStack(LOTRMod.mugAraq, BARREL_CAPACITY), LOTRMod.date, LOTRMod.date, LOTRMod.date, LOTRMod.date, LOTRMod.date, LOTRMod.date);
      addBrewingRecipe(new ItemStack(LOTRMod.mugCarrotWine, BARREL_CAPACITY), Items.field_151172_bF, Items.field_151172_bF, Items.field_151172_bF, Items.field_151172_bF, Items.field_151172_bF, Items.field_151172_bF);
      addBrewingRecipe(new ItemStack(LOTRMod.mugBananaBeer, BARREL_CAPACITY), LOTRMod.banana, LOTRMod.banana, LOTRMod.banana, LOTRMod.banana, LOTRMod.banana, LOTRMod.banana);
      addBrewingRecipe(new ItemStack(LOTRMod.mugMelonLiqueur, BARREL_CAPACITY), Items.field_151127_ba, Items.field_151127_ba, Items.field_151127_ba, Items.field_151127_ba, Items.field_151127_ba, Items.field_151127_ba);
      addBrewingRecipe(new ItemStack(LOTRMod.mugCactusLiqueur, BARREL_CAPACITY), Blocks.field_150434_aF, Blocks.field_150434_aF, Blocks.field_150434_aF, Blocks.field_150434_aF, Blocks.field_150434_aF, Blocks.field_150434_aF);
      addBrewingRecipe(new ItemStack(LOTRMod.mugTorogDraught, BARREL_CAPACITY), Items.field_151120_aE, Items.field_151120_aE, Items.field_151078_bh, Items.field_151078_bh, Blocks.field_150346_d, LOTRMod.rhinoHorn);
      addBrewingRecipe(new ItemStack(LOTRMod.mugLemonLiqueur, BARREL_CAPACITY), LOTRMod.lemon, LOTRMod.lemon, LOTRMod.lemon, LOTRMod.lemon, LOTRMod.lemon, LOTRMod.lemon);
      addBrewingRecipe(new ItemStack(LOTRMod.mugLimeLiqueur, BARREL_CAPACITY), LOTRMod.lime, LOTRMod.lime, LOTRMod.lime, LOTRMod.lime, LOTRMod.lime, LOTRMod.lime);
      addBrewingRecipe(new ItemStack(LOTRMod.mugCornLiquor, BARREL_CAPACITY), LOTRMod.corn, LOTRMod.corn, LOTRMod.corn, LOTRMod.corn, LOTRMod.corn, LOTRMod.corn);
      addBrewingRecipe(new ItemStack(LOTRMod.mugRedWine, BARREL_CAPACITY), LOTRMod.grapeRed, LOTRMod.grapeRed, LOTRMod.grapeRed, LOTRMod.grapeRed, LOTRMod.grapeRed, LOTRMod.grapeRed);
      addBrewingRecipe(new ItemStack(LOTRMod.mugWhiteWine, BARREL_CAPACITY), LOTRMod.grapeWhite, LOTRMod.grapeWhite, LOTRMod.grapeWhite, LOTRMod.grapeWhite, LOTRMod.grapeWhite, LOTRMod.grapeWhite);
      addBrewingRecipe(new ItemStack(LOTRMod.mugMorgulDraught, BARREL_CAPACITY), LOTRMod.morgulFlower, LOTRMod.morgulFlower, LOTRMod.morgulFlower, "bone", "bone", "bone");
      addBrewingRecipe(new ItemStack(LOTRMod.mugPlumKvass, BARREL_CAPACITY), Items.field_151015_O, Items.field_151015_O, Items.field_151015_O, LOTRMod.plum, LOTRMod.plum, LOTRMod.plum);
      addBrewingRecipe(new ItemStack(LOTRMod.mugTermiteTequila, BARREL_CAPACITY), Blocks.field_150434_aF, Blocks.field_150434_aF, Blocks.field_150434_aF, Blocks.field_150434_aF, Blocks.field_150434_aF, LOTRMod.termite);
      addBrewingRecipe(new ItemStack(LOTRMod.mugSourMilk, BARREL_CAPACITY), Items.field_151117_aB, Items.field_151117_aB, Items.field_151117_aB, Items.field_151117_aB, Items.field_151117_aB, Items.field_151117_aB);
      addBrewingRecipe(new ItemStack(LOTRMod.mugPomegranateWine, BARREL_CAPACITY), LOTRMod.pomegranate, LOTRMod.pomegranate, LOTRMod.pomegranate, LOTRMod.pomegranate, LOTRMod.pomegranate, LOTRMod.pomegranate);
   }

   private static void addBrewingRecipe(ItemStack result, Object... ingredients) {
      if (ingredients.length != 6) {
         throw new IllegalArgumentException("Brewing recipes must contain exactly 6 items");
      } else {
         recipes.add(new ShapelessOreRecipe(result, ingredients));
      }
   }

   public static boolean isWaterSource(ItemStack itemstack) {
      return itemstack != null && itemstack.func_77973_b() == Items.field_151131_as;
   }

   public static ItemStack findMatchingRecipe(LOTRTileEntityBarrel barrel) {
      for(int i = 6; i < 9; ++i) {
         ItemStack itemstack = barrel.func_70301_a(i);
         if (!isWaterSource(itemstack)) {
            return null;
         }
      }

      Iterator var12 = recipes.iterator();

      ArrayList ingredients;
      ShapelessOreRecipe recipe;
      label74:
      do {
         while(var12.hasNext()) {
            recipe = (ShapelessOreRecipe)var12.next();
            ingredients = new ArrayList(recipe.getInput());
            int i = 0;

            while(true) {
               if (i >= 6) {
                  continue label74;
               }

               ItemStack itemstack = barrel.func_70301_a(i);
               if (itemstack != null) {
                  boolean inRecipe = false;
                  Iterator it = ingredients.iterator();

                  while(it.hasNext()) {
                     boolean match = false;
                     Object next = it.next();
                     if (next instanceof ItemStack) {
                        match = LOTRRecipes.checkItemEquals((ItemStack)next, itemstack);
                     } else {
                        ItemStack item;
                        if (next instanceof ArrayList) {
                           for(Iterator var10 = ((ArrayList)next).iterator(); var10.hasNext(); match = match || LOTRRecipes.checkItemEquals(item, itemstack)) {
                              item = (ItemStack)var10.next();
                           }
                        }
                     }

                     if (match) {
                        inRecipe = true;
                        ingredients.remove(next);
                        break;
                     }
                  }

                  if (!inRecipe) {
                     break;
                  }
               }

               ++i;
            }
         }

         return null;
      } while(!ingredients.isEmpty());

      return recipe.func_77571_b().func_77946_l();
   }
}
