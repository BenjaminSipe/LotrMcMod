package lotr.common.entity.npc.data;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import lotr.common.item.VesselDrinkItem;
import lotr.common.item.VesselType;
import lotr.common.tileentity.PlateTileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class NPCFoodPool extends SuppliableItemTable {
   private List drinkVessels;
   private List drinkVesselsPlaceable;

   private NPCFoodPool(Object... items) {
      super(items);
   }

   public static NPCFoodPool of(Object... items) {
      return new NPCFoodPool(items);
   }

   protected NPCFoodPool setDrinkVessels(VesselType... vessels) {
      if (this.drinkVessels != null) {
         throw new IllegalStateException("drinkVessels already set!");
      } else {
         this.drinkVessels = Arrays.asList(vessels);
         this.drinkVesselsPlaceable = (List)this.drinkVessels.stream().filter(VesselType::isPlaceable).collect(Collectors.toList());
         if (this.drinkVesselsPlaceable.isEmpty()) {
            this.drinkVesselsPlaceable = Arrays.asList(VesselType.WOODEN_MUG);
         }

         return this;
      }
   }

   public ItemStack getRandomFood(Random random) {
      ItemStack food = this.getRandomItem(random);
      this.setDrinkVessel(food, random, false);
      return food;
   }

   public ItemStack getRandomPlaceableDrink(Random random) {
      ItemStack food = this.getRandomItem(random);
      this.setDrinkVessel(food, random, true);
      return food;
   }

   public ItemStack getRandomFoodForPlate(Random random) {
      return this.getRandomItem(random, PlateTileEntity::isValidFoodItem);
   }

   public List getDrinkVessels() {
      return this.drinkVessels;
   }

   public List getPlaceableDrinkVessels() {
      return this.drinkVesselsPlaceable;
   }

   public VesselType getRandomVessel(Random random) {
      return (VesselType)this.drinkVessels.get(random.nextInt(this.drinkVessels.size()));
   }

   public VesselType getRandomPlaceableVessel(Random random) {
      return (VesselType)this.drinkVesselsPlaceable.get(random.nextInt(this.drinkVesselsPlaceable.size()));
   }

   private void setDrinkVessel(ItemStack itemstack, Random random, boolean requirePlaceable) {
      Item item = itemstack.func_77973_b();
      if (item instanceof VesselDrinkItem) {
         VesselType vessel = requirePlaceable ? this.getRandomPlaceableVessel(random) : this.getRandomVessel(random);
         VesselDrinkItem.setVessel(itemstack, vessel);
      }

   }
}
