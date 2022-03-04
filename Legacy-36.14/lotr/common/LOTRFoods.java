package lotr.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import lotr.common.item.LOTRItemMug;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class LOTRFoods {
   public static LOTRFoods HOBBIT;
   public static LOTRFoods HOBBIT_DRINK;
   public static LOTRFoods BREE;
   public static LOTRFoods BREE_DRINK;
   public static LOTRFoods RANGER;
   public static LOTRFoods RANGER_DRINK;
   public static LOTRFoods ROHAN;
   public static LOTRFoods ROHAN_DRINK;
   public static LOTRFoods GONDOR;
   public static LOTRFoods GONDOR_DRINK;
   public static LOTRFoods DALE;
   public static LOTRFoods DALE_DRINK;
   public static LOTRFoods DWARF;
   public static LOTRFoods BLUE_DWARF;
   public static LOTRFoods DWARF_DRINK;
   public static LOTRFoods DUNLENDING;
   public static LOTRFoods DUNLENDING_DRINK;
   public static LOTRFoods RHUDAUR;
   public static LOTRFoods RHUDAUR_DRINK;
   public static LOTRFoods ELF;
   public static LOTRFoods ELF_DRINK;
   public static LOTRFoods WOOD_ELF_DRINK;
   public static LOTRFoods DORWINION;
   public static LOTRFoods DORWINION_DRINK;
   public static LOTRFoods RHUN;
   public static LOTRFoods RHUN_DRINK;
   public static LOTRFoods SOUTHRON;
   public static LOTRFoods SOUTHRON_DRINK;
   public static LOTRFoods HARNEDOR;
   public static LOTRFoods HARNEDOR_DRINK;
   public static LOTRFoods CORSAIR;
   public static LOTRFoods CORSAIR_DRINK;
   public static LOTRFoods NOMAD;
   public static LOTRFoods NOMAD_DRINK;
   public static LOTRFoods GULF_HARAD;
   public static LOTRFoods GULF_HARAD_DRINK;
   public static LOTRFoods HARAD_SLAVE;
   public static LOTRFoods HARAD_SLAVE_DRINK;
   public static LOTRFoods MOREDAIN;
   public static LOTRFoods MOREDAIN_DRINK;
   public static LOTRFoods TAUREDAIN;
   public static LOTRFoods TAUREDAIN_DRINK;
   public static LOTRFoods ORC;
   public static LOTRFoods ORC_DRINK;
   public static LOTRFoods NURN_SLAVE;
   public static LOTRFoods NURN_SLAVE_DRINK;
   public static LOTRFoods HALF_TROLL;
   public static LOTRFoods HALF_TROLL_DRINK;
   private ItemStack[] foodList;
   private LOTRItemMug.Vessel[] drinkVessels;
   private LOTRItemMug.Vessel[] drinkVesselsPlaceable;

   public LOTRFoods(ItemStack[] items) {
      this.foodList = items;
   }

   public ItemStack getRandomFood(Random random) {
      ItemStack food = this.foodList[random.nextInt(this.foodList.length)].func_77946_l();
      this.setDrinkVessel(food, random, false);
      return food;
   }

   public ItemStack getRandomPlaceableDrink(Random random) {
      ItemStack food = this.foodList[random.nextInt(this.foodList.length)].func_77946_l();
      this.setDrinkVessel(food, random, true);
      return food;
   }

   public ItemStack getRandomFoodForPlate(Random random) {
      List foodsNoContainer = new ArrayList();
      ItemStack[] var3 = this.foodList;
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         ItemStack itemstack = var3[var5];
         Item item = itemstack.func_77973_b();
         if (!item.hasContainerItem(itemstack)) {
            foodsNoContainer.add(itemstack.func_77946_l());
         }
      }

      ItemStack food = (ItemStack)foodsNoContainer.get(random.nextInt(foodsNoContainer.size()));
      return food;
   }

   public LOTRFoods setDrinkVessels(LOTRItemMug.Vessel... vessels) {
      this.drinkVessels = vessels;
      List placeable = new ArrayList();
      LOTRItemMug.Vessel[] var3 = this.drinkVessels;
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         LOTRItemMug.Vessel v = var3[var5];
         if (v.canPlace) {
            placeable.add(v);
         }
      }

      if (!placeable.isEmpty()) {
         this.drinkVesselsPlaceable = (LOTRItemMug.Vessel[])placeable.toArray(new LOTRItemMug.Vessel[0]);
      } else {
         this.drinkVesselsPlaceable = new LOTRItemMug.Vessel[]{LOTRItemMug.Vessel.MUG};
      }

      return this;
   }

   public LOTRItemMug.Vessel[] getDrinkVessels() {
      return this.drinkVessels;
   }

   public LOTRItemMug.Vessel[] getPlaceableDrinkVessels() {
      return this.drinkVesselsPlaceable;
   }

   public LOTRItemMug.Vessel getRandomVessel(Random random) {
      return this.drinkVessels[random.nextInt(this.drinkVessels.length)];
   }

   public LOTRItemMug.Vessel getRandomPlaceableVessel(Random random) {
      return this.drinkVesselsPlaceable[random.nextInt(this.drinkVesselsPlaceable.length)];
   }

   public ItemStack getRandomBrewableDrink(Random random) {
      List alcohols = new ArrayList();
      ItemStack[] var3 = this.foodList;
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         ItemStack itemstack = var3[var5];
         Item item = itemstack.func_77973_b();
         if (item instanceof LOTRItemMug && ((LOTRItemMug)item).isBrewable) {
            alcohols.add(itemstack.func_77946_l());
         }
      }

      ItemStack drink = (ItemStack)alcohols.get(random.nextInt(alcohols.size()));
      this.setDrinkVessel(drink, random, false);
      return drink;
   }

   private void setDrinkVessel(ItemStack itemstack, Random random, boolean requirePlaceable) {
      Item item = itemstack.func_77973_b();
      if (item instanceof LOTRItemMug && ((LOTRItemMug)item).isFullMug) {
         LOTRItemMug.Vessel v;
         if (requirePlaceable) {
            v = this.getRandomPlaceableVessel(random);
         } else {
            v = this.getRandomVessel(random);
         }

         LOTRItemMug.setVessel(itemstack, v, true);
      }

   }

   static {
      HOBBIT = new LOTRFoods(new ItemStack[]{new ItemStack(Items.field_151157_am), new ItemStack(Items.field_151101_aQ), new ItemStack(Items.field_151077_bg), new ItemStack(Items.field_151083_be), new ItemStack(LOTRMod.muttonCooked), new ItemStack(LOTRMod.gammon), new ItemStack(Items.field_151168_bH), new ItemStack(Items.field_151034_e), new ItemStack(LOTRMod.appleGreen), new ItemStack(Items.field_151025_P), new ItemStack(LOTRMod.cornBread), new ItemStack(Items.field_151172_bF), new ItemStack(LOTRMod.lettuce), new ItemStack(LOTRMod.leek), new ItemStack(LOTRMod.leekSoup), new ItemStack(Items.field_151009_A), new ItemStack(LOTRMod.turnip), new ItemStack(LOTRMod.turnipCooked), new ItemStack(Items.field_151158_bO), new ItemStack(LOTRMod.mushroomPie), new ItemStack(LOTRMod.pear), new ItemStack(LOTRMod.cherry), new ItemStack(LOTRMod.plum), new ItemStack(Items.field_151106_aX), new ItemStack(LOTRMod.hobbitPancake), new ItemStack(LOTRMod.rabbitCooked), new ItemStack(LOTRMod.blueberry), new ItemStack(LOTRMod.blackberry), new ItemStack(LOTRMod.raspberry), new ItemStack(LOTRMod.cranberry), new ItemStack(LOTRMod.elderberry), new ItemStack(LOTRMod.chestnutRoast), new ItemStack(LOTRMod.cornCooked), new ItemStack(LOTRMod.marzipan), new ItemStack(LOTRMod.marzipanChocolate)});
      HOBBIT_DRINK = (new LOTRFoods(new ItemStack[]{new ItemStack(LOTRMod.mugAle), new ItemStack(LOTRMod.mugAle), new ItemStack(LOTRMod.mugCider), new ItemStack(LOTRMod.mugPerry), new ItemStack(LOTRMod.mugCherryLiqueur), new ItemStack(LOTRMod.mugAppleJuice)})).setDrinkVessels(LOTRItemMug.Vessel.MUG, LOTRItemMug.Vessel.MUG_CLAY, LOTRItemMug.Vessel.GOBLET_WOOD, LOTRItemMug.Vessel.BOTTLE);
      BREE = new LOTRFoods(new ItemStack[]{new ItemStack(Items.field_151157_am), new ItemStack(Items.field_151077_bg), new ItemStack(Items.field_151083_be), new ItemStack(Items.field_151101_aQ), new ItemStack(LOTRMod.muttonCooked), new ItemStack(Items.field_151168_bH), new ItemStack(Items.field_151034_e), new ItemStack(LOTRMod.appleGreen), new ItemStack(LOTRMod.pear), new ItemStack(LOTRMod.plum), new ItemStack(Items.field_151025_P), new ItemStack(LOTRMod.rabbitCooked), new ItemStack(LOTRMod.rabbitStew), new ItemStack(LOTRMod.deerCooked), new ItemStack(LOTRMod.turnip), new ItemStack(LOTRMod.turnipCooked), new ItemStack(Items.field_151172_bF), new ItemStack(LOTRMod.lettuce), new ItemStack(LOTRMod.leek), new ItemStack(LOTRMod.leekSoup), new ItemStack(Items.field_151009_A), new ItemStack(LOTRMod.mushroomPie)});
      BREE_DRINK = (new LOTRFoods(new ItemStack[]{new ItemStack(LOTRMod.mugAle), new ItemStack(LOTRMod.mugAle), new ItemStack(LOTRMod.mugAle), new ItemStack(LOTRMod.mugCider), new ItemStack(LOTRMod.mugCider), new ItemStack(LOTRMod.mugPerry), new ItemStack(LOTRMod.mugMead), new ItemStack(LOTRMod.mugAppleJuice)})).setDrinkVessels(LOTRItemMug.Vessel.MUG, LOTRItemMug.Vessel.MUG_CLAY, LOTRItemMug.Vessel.GOBLET_COPPER, LOTRItemMug.Vessel.GOBLET_WOOD);
      RANGER = new LOTRFoods(new ItemStack[]{new ItemStack(Items.field_151157_am), new ItemStack(Items.field_151077_bg), new ItemStack(Items.field_151083_be), new ItemStack(Items.field_151101_aQ), new ItemStack(LOTRMod.muttonCooked), new ItemStack(LOTRMod.deerCooked), new ItemStack(Items.field_151168_bH), new ItemStack(Items.field_151034_e), new ItemStack(LOTRMod.appleGreen), new ItemStack(LOTRMod.pear), new ItemStack(Items.field_151025_P), new ItemStack(LOTRMod.blueberry), new ItemStack(LOTRMod.blackberry), new ItemStack(LOTRMod.cranberry), new ItemStack(LOTRMod.raspberry), new ItemStack(LOTRMod.elderberry)});
      RANGER_DRINK = (new LOTRFoods(new ItemStack[]{new ItemStack(LOTRMod.mugAle), new ItemStack(LOTRMod.mugAle), new ItemStack(LOTRMod.mugCider), new ItemStack(LOTRMod.mugPerry), new ItemStack(LOTRMod.mugAppleJuice)})).setDrinkVessels(LOTRItemMug.Vessel.MUG, LOTRItemMug.Vessel.GOBLET_WOOD, LOTRItemMug.Vessel.BOTTLE, LOTRItemMug.Vessel.SKIN, LOTRItemMug.Vessel.HORN);
      ROHAN = new LOTRFoods(new ItemStack[]{new ItemStack(Items.field_151157_am), new ItemStack(Items.field_151077_bg), new ItemStack(Items.field_151083_be), new ItemStack(LOTRMod.muttonCooked), new ItemStack(Items.field_151168_bH), new ItemStack(Items.field_151034_e), new ItemStack(LOTRMod.appleGreen), new ItemStack(LOTRMod.pear), new ItemStack(Items.field_151025_P), new ItemStack(LOTRMod.rabbitCooked), new ItemStack(LOTRMod.blueberry), new ItemStack(LOTRMod.blackberry)});
      ROHAN_DRINK = (new LOTRFoods(new ItemStack[]{new ItemStack(LOTRMod.mugMead), new ItemStack(LOTRMod.mugMead), new ItemStack(LOTRMod.mugMead), new ItemStack(LOTRMod.mugAle), new ItemStack(LOTRMod.mugCider), new ItemStack(LOTRMod.mugPerry), new ItemStack(LOTRMod.mugAppleJuice)})).setDrinkVessels(LOTRItemMug.Vessel.MUG, LOTRItemMug.Vessel.MUG_CLAY, LOTRItemMug.Vessel.GOBLET_GOLD, LOTRItemMug.Vessel.GOBLET_SILVER, LOTRItemMug.Vessel.GOBLET_COPPER, LOTRItemMug.Vessel.GOBLET_WOOD, LOTRItemMug.Vessel.HORN, LOTRItemMug.Vessel.HORN_GOLD);
      GONDOR = new LOTRFoods(new ItemStack[]{new ItemStack(Items.field_151157_am), new ItemStack(Items.field_151077_bg), new ItemStack(Items.field_151083_be), new ItemStack(Items.field_151101_aQ), new ItemStack(LOTRMod.muttonCooked), new ItemStack(LOTRMod.deerCooked), new ItemStack(Items.field_151168_bH), new ItemStack(Items.field_151034_e), new ItemStack(LOTRMod.appleGreen), new ItemStack(LOTRMod.pear), new ItemStack(LOTRMod.olive), new ItemStack(LOTRMod.plum), new ItemStack(Items.field_151025_P), new ItemStack(LOTRMod.oliveBread), new ItemStack(LOTRMod.blueberry), new ItemStack(LOTRMod.blackberry), new ItemStack(LOTRMod.cranberry)});
      GONDOR_DRINK = (new LOTRFoods(new ItemStack[]{new ItemStack(LOTRMod.mugAle), new ItemStack(LOTRMod.mugAle), new ItemStack(LOTRMod.mugAle), new ItemStack(LOTRMod.mugMead), new ItemStack(LOTRMod.mugCider), new ItemStack(LOTRMod.mugPerry), new ItemStack(LOTRMod.mugAppleJuice)})).setDrinkVessels(LOTRItemMug.Vessel.MUG, LOTRItemMug.Vessel.MUG_CLAY, LOTRItemMug.Vessel.GOBLET_GOLD, LOTRItemMug.Vessel.GOBLET_SILVER, LOTRItemMug.Vessel.GOBLET_COPPER, LOTRItemMug.Vessel.GOBLET_WOOD, LOTRItemMug.Vessel.BOTTLE, LOTRItemMug.Vessel.HORN);
      DALE = new LOTRFoods(new ItemStack[]{new ItemStack(Items.field_151157_am), new ItemStack(Items.field_151077_bg), new ItemStack(Items.field_151083_be), new ItemStack(LOTRMod.muttonCooked), new ItemStack(Items.field_151168_bH), new ItemStack(Items.field_151034_e), new ItemStack(LOTRMod.appleGreen), new ItemStack(LOTRMod.pear), new ItemStack(LOTRMod.plum), new ItemStack(Items.field_151025_P), new ItemStack(LOTRMod.rabbitCooked), new ItemStack(LOTRMod.deerCooked), new ItemStack(LOTRMod.cram), new ItemStack(LOTRMod.cram), new ItemStack(LOTRMod.cram), new ItemStack(LOTRMod.cram), new ItemStack(LOTRMod.marzipan), new ItemStack(LOTRMod.marzipanChocolate)});
      DALE_DRINK = (new LOTRFoods(new ItemStack[]{new ItemStack(LOTRMod.mugMead), new ItemStack(LOTRMod.mugAle), new ItemStack(LOTRMod.mugCider), new ItemStack(LOTRMod.mugPerry), new ItemStack(LOTRMod.mugVodka), new ItemStack(LOTRMod.mugPlumKvass), new ItemStack(LOTRMod.mugDwarvenAle), new ItemStack(LOTRMod.mugRedWine), new ItemStack(LOTRMod.mugWhiteWine), new ItemStack(LOTRMod.mugAppleJuice)})).setDrinkVessels(LOTRItemMug.Vessel.MUG, LOTRItemMug.Vessel.MUG_CLAY, LOTRItemMug.Vessel.GOBLET_GOLD, LOTRItemMug.Vessel.GOBLET_SILVER, LOTRItemMug.Vessel.GOBLET_COPPER, LOTRItemMug.Vessel.GOBLET_WOOD, LOTRItemMug.Vessel.BOTTLE, LOTRItemMug.Vessel.HORN);
      DWARF = new LOTRFoods(new ItemStack[]{new ItemStack(Items.field_151157_am), new ItemStack(Items.field_151101_aQ), new ItemStack(Items.field_151077_bg), new ItemStack(Items.field_151083_be), new ItemStack(LOTRMod.muttonCooked), new ItemStack(LOTRMod.gammon), new ItemStack(Items.field_151025_P), new ItemStack(LOTRMod.cram), new ItemStack(LOTRMod.cram), new ItemStack(LOTRMod.cram)});
      BLUE_DWARF = new LOTRFoods(new ItemStack[]{new ItemStack(Items.field_151157_am), new ItemStack(Items.field_151101_aQ), new ItemStack(Items.field_151077_bg), new ItemStack(Items.field_151083_be), new ItemStack(LOTRMod.muttonCooked), new ItemStack(LOTRMod.gammon), new ItemStack(Items.field_151025_P)});
      DWARF_DRINK = (new LOTRFoods(new ItemStack[]{new ItemStack(LOTRMod.mugDwarvenAle), new ItemStack(LOTRMod.mugDwarvenAle), new ItemStack(LOTRMod.mugDwarvenAle), new ItemStack(LOTRMod.mugDwarvenAle), new ItemStack(LOTRMod.mugDwarvenTonic)})).setDrinkVessels(LOTRItemMug.Vessel.MUG, LOTRItemMug.Vessel.MUG_CLAY, LOTRItemMug.Vessel.GOBLET_WOOD, LOTRItemMug.Vessel.HORN);
      DUNLENDING = new LOTRFoods(new ItemStack[]{new ItemStack(Items.field_151157_am), new ItemStack(Items.field_151101_aQ), new ItemStack(Items.field_151077_bg), new ItemStack(Items.field_151083_be), new ItemStack(LOTRMod.muttonCooked), new ItemStack(LOTRMod.gammon), new ItemStack(Items.field_151168_bH), new ItemStack(Items.field_151034_e), new ItemStack(LOTRMod.appleGreen), new ItemStack(LOTRMod.pear), new ItemStack(Items.field_151025_P), new ItemStack(LOTRMod.rabbitCooked), new ItemStack(LOTRMod.chestnutRoast)});
      DUNLENDING_DRINK = (new LOTRFoods(new ItemStack[]{new ItemStack(LOTRMod.mugAle), new ItemStack(LOTRMod.mugAle), new ItemStack(LOTRMod.mugMead), new ItemStack(LOTRMod.mugCider), new ItemStack(LOTRMod.mugRum)})).setDrinkVessels(LOTRItemMug.Vessel.MUG, LOTRItemMug.Vessel.GOBLET_WOOD, LOTRItemMug.Vessel.SKULL, LOTRItemMug.Vessel.SKIN, LOTRItemMug.Vessel.HORN);
      RHUDAUR = new LOTRFoods(new ItemStack[]{new ItemStack(Items.field_151157_am), new ItemStack(Items.field_151101_aQ), new ItemStack(Items.field_151077_bg), new ItemStack(Items.field_151083_be), new ItemStack(LOTRMod.muttonCooked), new ItemStack(Items.field_151025_P), new ItemStack(LOTRMod.rabbitCooked), new ItemStack(Items.field_151078_bh)});
      RHUDAUR_DRINK = (new LOTRFoods(new ItemStack[]{new ItemStack(LOTRMod.mugAle), new ItemStack(LOTRMod.mugAle), new ItemStack(LOTRMod.mugMead), new ItemStack(LOTRMod.mugOrcDraught)})).setDrinkVessels(LOTRItemMug.Vessel.MUG, LOTRItemMug.Vessel.GOBLET_WOOD, LOTRItemMug.Vessel.SKULL, LOTRItemMug.Vessel.SKIN, LOTRItemMug.Vessel.HORN);
      ELF = new LOTRFoods(new ItemStack[]{new ItemStack(Items.field_151025_P), new ItemStack(Items.field_151034_e), new ItemStack(LOTRMod.appleGreen), new ItemStack(LOTRMod.pear), new ItemStack(LOTRMod.plum), new ItemStack(LOTRMod.lettuce), new ItemStack(Items.field_151172_bF), new ItemStack(LOTRMod.lembas), new ItemStack(LOTRMod.lembas), new ItemStack(LOTRMod.lembas), new ItemStack(LOTRMod.chestnutRoast)});
      ELF_DRINK = (new LOTRFoods(new ItemStack[]{new ItemStack(LOTRMod.mugMiruvor), new ItemStack(LOTRMod.mugMiruvor), new ItemStack(LOTRMod.mugMiruvor), new ItemStack(LOTRMod.mugAppleJuice), new ItemStack(LOTRMod.mugBlueberryJuice), new ItemStack(LOTRMod.mugBlackberryJuice), new ItemStack(LOTRMod.mugElderberryJuice), new ItemStack(LOTRMod.mugRaspberryJuice), new ItemStack(LOTRMod.mugCranberryJuice)})).setDrinkVessels(LOTRItemMug.Vessel.MUG, LOTRItemMug.Vessel.MUG_CLAY, LOTRItemMug.Vessel.GOBLET_GOLD, LOTRItemMug.Vessel.GOBLET_SILVER, LOTRItemMug.Vessel.GOBLET_COPPER, LOTRItemMug.Vessel.GOBLET_WOOD, LOTRItemMug.Vessel.GLASS, LOTRItemMug.Vessel.BOTTLE);
      WOOD_ELF_DRINK = (new LOTRFoods(new ItemStack[]{new ItemStack(LOTRMod.mugRedWine), new ItemStack(LOTRMod.mugRedWine), new ItemStack(LOTRMod.mugRedWine), new ItemStack(LOTRMod.mugRedWine), new ItemStack(LOTRMod.mugWhiteWine), new ItemStack(LOTRMod.mugWhiteWine), new ItemStack(LOTRMod.mugAppleJuice), new ItemStack(LOTRMod.mugBlueberryJuice), new ItemStack(LOTRMod.mugBlackberryJuice), new ItemStack(LOTRMod.mugElderberryJuice), new ItemStack(LOTRMod.mugRaspberryJuice), new ItemStack(LOTRMod.mugCranberryJuice)})).setDrinkVessels(LOTRItemMug.Vessel.MUG, LOTRItemMug.Vessel.GOBLET_WOOD, LOTRItemMug.Vessel.BOTTLE);
      DORWINION = new LOTRFoods(new ItemStack[]{new ItemStack(Items.field_151025_P), new ItemStack(LOTRMod.oliveBread), new ItemStack(Items.field_151172_bF), new ItemStack(LOTRMod.lettuce), new ItemStack(LOTRMod.turnip), new ItemStack(LOTRMod.turnipCooked), new ItemStack(LOTRMod.rabbitCooked), new ItemStack(LOTRMod.deerCooked), new ItemStack(LOTRMod.olive), new ItemStack(LOTRMod.plum), new ItemStack(LOTRMod.almond), new ItemStack(LOTRMod.marzipan), new ItemStack(LOTRMod.marzipanChocolate), new ItemStack(Items.field_151101_aQ), new ItemStack(LOTRMod.grapeRed), new ItemStack(LOTRMod.grapeWhite), new ItemStack(LOTRMod.raisins)});
      DORWINION_DRINK = (new LOTRFoods(new ItemStack[]{new ItemStack(LOTRMod.mugRedWine), new ItemStack(LOTRMod.mugWhiteWine), new ItemStack(LOTRMod.mugRedGrapeJuice), new ItemStack(LOTRMod.mugWhiteGrapeJuice)})).setDrinkVessels(LOTRItemMug.Vessel.GOBLET_GOLD, LOTRItemMug.Vessel.GOBLET_SILVER, LOTRItemMug.Vessel.GLASS, LOTRItemMug.Vessel.BOTTLE);
      RHUN = new LOTRFoods(new ItemStack[]{new ItemStack(Items.field_151025_P), new ItemStack(LOTRMod.oliveBread), new ItemStack(Items.field_151172_bF), new ItemStack(LOTRMod.lettuce), new ItemStack(LOTRMod.turnip), new ItemStack(LOTRMod.turnipCooked), new ItemStack(LOTRMod.rabbitCooked), new ItemStack(LOTRMod.deerCooked), new ItemStack(LOTRMod.olive), new ItemStack(LOTRMod.plum), new ItemStack(LOTRMod.almond), new ItemStack(Items.field_151101_aQ), new ItemStack(Items.field_151077_bg), new ItemStack(Items.field_151083_be), new ItemStack(Items.field_151157_am), new ItemStack(LOTRMod.grapeRed), new ItemStack(LOTRMod.grapeWhite), new ItemStack(LOTRMod.raisins), new ItemStack(LOTRMod.date), new ItemStack(LOTRMod.pomegranate)});
      RHUN_DRINK = (new LOTRFoods(new ItemStack[]{new ItemStack(LOTRMod.mugSourMilk), new ItemStack(LOTRMod.mugSourMilk), new ItemStack(LOTRMod.mugSourMilk), new ItemStack(LOTRMod.mugAraq), new ItemStack(LOTRMod.mugAraq), new ItemStack(LOTRMod.mugAraq), new ItemStack(LOTRMod.mugAle), new ItemStack(LOTRMod.mugRedWine), new ItemStack(LOTRMod.mugWhiteWine), new ItemStack(LOTRMod.mugRedGrapeJuice), new ItemStack(LOTRMod.mugWhiteGrapeJuice), new ItemStack(LOTRMod.mugPomegranateWine), new ItemStack(LOTRMod.mugPomegranateJuice)})).setDrinkVessels(LOTRItemMug.Vessel.MUG, LOTRItemMug.Vessel.MUG_CLAY, LOTRItemMug.Vessel.GOBLET_GOLD, LOTRItemMug.Vessel.GOBLET_SILVER, LOTRItemMug.Vessel.GOBLET_COPPER, LOTRItemMug.Vessel.GOBLET_WOOD, LOTRItemMug.Vessel.GLASS, LOTRItemMug.Vessel.BOTTLE, LOTRItemMug.Vessel.SKIN);
      SOUTHRON = new LOTRFoods(new ItemStack[]{new ItemStack(Items.field_151025_P), new ItemStack(LOTRMod.oliveBread), new ItemStack(Items.field_151034_e), new ItemStack(LOTRMod.appleGreen), new ItemStack(LOTRMod.pear), new ItemStack(LOTRMod.date), new ItemStack(LOTRMod.lemon), new ItemStack(LOTRMod.orange), new ItemStack(LOTRMod.lime), new ItemStack(LOTRMod.olive), new ItemStack(LOTRMod.almond), new ItemStack(LOTRMod.plum), new ItemStack(Items.field_151172_bF), new ItemStack(Items.field_151168_bH), new ItemStack(LOTRMod.lettuce), new ItemStack(Items.field_151157_am), new ItemStack(Items.field_151101_aQ), new ItemStack(Items.field_151077_bg), new ItemStack(Items.field_151083_be), new ItemStack(LOTRMod.muttonCooked), new ItemStack(LOTRMod.kebab), new ItemStack(LOTRMod.shishKebab), new ItemStack(LOTRMod.camelCooked)});
      SOUTHRON_DRINK = (new LOTRFoods(new ItemStack[]{new ItemStack(LOTRMod.mugWater), new ItemStack(LOTRMod.mugAraq), new ItemStack(LOTRMod.mugAraq), new ItemStack(LOTRMod.mugAraq), new ItemStack(LOTRMod.mugCactusLiqueur), new ItemStack(LOTRMod.mugOrangeJuice), new ItemStack(LOTRMod.mugLemonLiqueur), new ItemStack(LOTRMod.mugLemonade), new ItemStack(LOTRMod.mugLimeLiqueur), new ItemStack(LOTRMod.mugAle), new ItemStack(LOTRMod.mugCider)})).setDrinkVessels(LOTRItemMug.Vessel.MUG, LOTRItemMug.Vessel.MUG_CLAY, LOTRItemMug.Vessel.GOBLET_COPPER, LOTRItemMug.Vessel.GOBLET_GOLD, LOTRItemMug.Vessel.GOBLET_WOOD, LOTRItemMug.Vessel.BOTTLE, LOTRItemMug.Vessel.SKIN);
      HARNEDOR = new LOTRFoods(new ItemStack[]{new ItemStack(Items.field_151025_P), new ItemStack(LOTRMod.oliveBread), new ItemStack(Items.field_151034_e), new ItemStack(LOTRMod.appleGreen), new ItemStack(LOTRMod.pear), new ItemStack(LOTRMod.date), new ItemStack(LOTRMod.olive), new ItemStack(LOTRMod.plum), new ItemStack(Items.field_151172_bF), new ItemStack(Items.field_151168_bH), new ItemStack(LOTRMod.lettuce), new ItemStack(Items.field_151157_am), new ItemStack(Items.field_151101_aQ), new ItemStack(Items.field_151077_bg), new ItemStack(Items.field_151083_be), new ItemStack(LOTRMod.muttonCooked), new ItemStack(LOTRMod.kebab), new ItemStack(LOTRMod.shishKebab), new ItemStack(LOTRMod.camelCooked)});
      HARNEDOR_DRINK = (new LOTRFoods(new ItemStack[]{new ItemStack(LOTRMod.mugWater), new ItemStack(LOTRMod.mugAraq), new ItemStack(LOTRMod.mugAraq), new ItemStack(LOTRMod.mugAraq), new ItemStack(LOTRMod.mugCactusLiqueur), new ItemStack(LOTRMod.mugOrangeJuice), new ItemStack(LOTRMod.mugAle), new ItemStack(LOTRMod.mugCider)})).setDrinkVessels(LOTRItemMug.Vessel.MUG, LOTRItemMug.Vessel.MUG_CLAY, LOTRItemMug.Vessel.GOBLET_COPPER, LOTRItemMug.Vessel.GOBLET_WOOD, LOTRItemMug.Vessel.SKIN);
      CORSAIR = new LOTRFoods(new ItemStack[]{new ItemStack(Items.field_151025_P), new ItemStack(LOTRMod.oliveBread), new ItemStack(LOTRMod.date), new ItemStack(Items.field_151101_aQ), new ItemStack(Items.field_151101_aQ), new ItemStack(LOTRMod.kebab), new ItemStack(LOTRMod.shishKebab)});
      CORSAIR_DRINK = (new LOTRFoods(new ItemStack[]{new ItemStack(LOTRMod.mugAraq), new ItemStack(LOTRMod.mugRum)})).setDrinkVessels(LOTRItemMug.Vessel.GOBLET_COPPER, LOTRItemMug.Vessel.GOBLET_GOLD, LOTRItemMug.Vessel.GOBLET_WOOD, LOTRItemMug.Vessel.BOTTLE, LOTRItemMug.Vessel.SKIN, LOTRItemMug.Vessel.SKULL);
      NOMAD = new LOTRFoods(new ItemStack[]{new ItemStack(Items.field_151025_P), new ItemStack(LOTRMod.oliveBread), new ItemStack(LOTRMod.date), new ItemStack(Items.field_151083_be), new ItemStack(LOTRMod.muttonCooked), new ItemStack(LOTRMod.kebab), new ItemStack(LOTRMod.shishKebab), new ItemStack(LOTRMod.camelCooked), new ItemStack(LOTRMod.camelCooked), new ItemStack(LOTRMod.camelCooked), new ItemStack(LOTRMod.camelCooked), new ItemStack(LOTRMod.saltedFlesh)});
      NOMAD_DRINK = (new LOTRFoods(new ItemStack[]{new ItemStack(LOTRMod.mugWater), new ItemStack(LOTRMod.mugWater), new ItemStack(LOTRMod.mugWater), new ItemStack(LOTRMod.mugAraq), new ItemStack(LOTRMod.mugAraq), new ItemStack(LOTRMod.mugCactusLiqueur), new ItemStack(LOTRMod.mugAle)})).setDrinkVessels(LOTRItemMug.Vessel.MUG, LOTRItemMug.Vessel.MUG_CLAY, LOTRItemMug.Vessel.GOBLET_COPPER, LOTRItemMug.Vessel.GOBLET_WOOD, LOTRItemMug.Vessel.SKIN);
      GULF_HARAD = new LOTRFoods(new ItemStack[]{new ItemStack(Items.field_151025_P), new ItemStack(LOTRMod.oliveBread), new ItemStack(Items.field_151034_e), new ItemStack(LOTRMod.appleGreen), new ItemStack(LOTRMod.pear), new ItemStack(LOTRMod.date), new ItemStack(LOTRMod.olive), new ItemStack(LOTRMod.plum), new ItemStack(Items.field_151172_bF), new ItemStack(Items.field_151168_bH), new ItemStack(LOTRMod.orange), new ItemStack(Items.field_151157_am), new ItemStack(Items.field_151101_aQ), new ItemStack(Items.field_151077_bg), new ItemStack(Items.field_151083_be), new ItemStack(LOTRMod.muttonCooked), new ItemStack(LOTRMod.kebab), new ItemStack(LOTRMod.shishKebab), new ItemStack(LOTRMod.camelCooked), new ItemStack(LOTRMod.saltedFlesh), new ItemStack(LOTRMod.saltedFlesh), new ItemStack(LOTRMod.saltedFlesh)});
      GULF_HARAD_DRINK = (new LOTRFoods(new ItemStack[]{new ItemStack(LOTRMod.mugWater), new ItemStack(LOTRMod.mugAraq), new ItemStack(LOTRMod.mugAraq), new ItemStack(LOTRMod.mugAraq), new ItemStack(LOTRMod.mugCactusLiqueur), new ItemStack(LOTRMod.mugOrangeJuice), new ItemStack(LOTRMod.mugAle), new ItemStack(LOTRMod.mugCider), new ItemStack(LOTRMod.mugBananaBeer), new ItemStack(LOTRMod.mugMangoJuice)})).setDrinkVessels(LOTRItemMug.Vessel.MUG, LOTRItemMug.Vessel.MUG_CLAY, LOTRItemMug.Vessel.GOBLET_COPPER, LOTRItemMug.Vessel.GOBLET_GOLD, LOTRItemMug.Vessel.GOBLET_WOOD, LOTRItemMug.Vessel.SKIN, LOTRItemMug.Vessel.SKULL, LOTRItemMug.Vessel.SKULL, LOTRItemMug.Vessel.SKULL);
      HARAD_SLAVE = new LOTRFoods(new ItemStack[]{new ItemStack(Items.field_151025_P), new ItemStack(LOTRMod.date), new ItemStack(LOTRMod.kebab)});
      HARAD_SLAVE_DRINK = (new LOTRFoods(new ItemStack[]{new ItemStack(LOTRMod.mugWater)})).setDrinkVessels(LOTRItemMug.Vessel.SKIN);
      MOREDAIN = new LOTRFoods(new ItemStack[]{new ItemStack(Items.field_151025_P), new ItemStack(LOTRMod.bananaBread), new ItemStack(LOTRMod.banana), new ItemStack(LOTRMod.mango), new ItemStack(Items.field_151127_ba), new ItemStack(LOTRMod.lionCooked), new ItemStack(LOTRMod.zebraCooked), new ItemStack(LOTRMod.rhinoCooked), new ItemStack(LOTRMod.yamRoast)});
      MOREDAIN_DRINK = (new LOTRFoods(new ItemStack[]{new ItemStack(LOTRMod.mugMangoJuice), new ItemStack(LOTRMod.mugBananaBeer), new ItemStack(LOTRMod.mugMelonLiqueur)})).setDrinkVessels(LOTRItemMug.Vessel.MUG, LOTRItemMug.Vessel.MUG_CLAY, LOTRItemMug.Vessel.GOBLET_WOOD, LOTRItemMug.Vessel.GOBLET_GOLD, LOTRItemMug.Vessel.SKIN);
      TAUREDAIN = new LOTRFoods(new ItemStack[]{new ItemStack(Items.field_151025_P), new ItemStack(LOTRMod.bananaBread), new ItemStack(LOTRMod.cornBread), new ItemStack(LOTRMod.corn), new ItemStack(LOTRMod.cornCooked), new ItemStack(Items.field_151168_bH), new ItemStack(LOTRMod.banana), new ItemStack(LOTRMod.mango), new ItemStack(Items.field_151127_ba), new ItemStack(LOTRMod.melonSoup), new ItemStack(Items.field_151101_aQ)});
      TAUREDAIN_DRINK = (new LOTRFoods(new ItemStack[]{new ItemStack(LOTRMod.mugChocolate), new ItemStack(LOTRMod.mugMangoJuice), new ItemStack(LOTRMod.mugBananaBeer), new ItemStack(LOTRMod.mugMelonLiqueur), new ItemStack(LOTRMod.mugCornLiquor)})).setDrinkVessels(LOTRItemMug.Vessel.MUG, LOTRItemMug.Vessel.GOBLET_GOLD, LOTRItemMug.Vessel.GOBLET_WOOD);
      ORC = new LOTRFoods(new ItemStack[]{new ItemStack(LOTRMod.maggotyBread)});
      ORC_DRINK = (new LOTRFoods(new ItemStack[]{new ItemStack(LOTRMod.mugOrcDraught)})).setDrinkVessels(LOTRItemMug.Vessel.MUG, LOTRItemMug.Vessel.SKULL, LOTRItemMug.Vessel.SKIN);
      NURN_SLAVE = new LOTRFoods(new ItemStack[]{new ItemStack(LOTRMod.maggotyBread)});
      NURN_SLAVE_DRINK = (new LOTRFoods(new ItemStack[]{new ItemStack(LOTRMod.mugWater), new ItemStack(LOTRMod.mugOrcDraught)})).setDrinkVessels(LOTRItemMug.Vessel.SKIN);
      HALF_TROLL = new LOTRFoods(new ItemStack[]{new ItemStack(LOTRMod.maggotyBread), new ItemStack(Items.field_151078_bh), new ItemStack(LOTRMod.lionRaw), new ItemStack(LOTRMod.zebraRaw), new ItemStack(LOTRMod.rhinoRaw), new ItemStack(LOTRMod.torogStew)});
      HALF_TROLL_DRINK = (new LOTRFoods(new ItemStack[]{new ItemStack(LOTRMod.mugTorogDraught)})).setDrinkVessels(LOTRItemMug.Vessel.MUG, LOTRItemMug.Vessel.SKULL, LOTRItemMug.Vessel.SKIN);
   }
}
