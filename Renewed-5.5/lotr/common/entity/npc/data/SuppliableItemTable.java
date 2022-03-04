package lotr.common.entity.npc.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IItemProvider;

public class SuppliableItemTable {
   private final List itemSupplierList = new ArrayList();

   public SuppliableItemTable(Object... items) {
      Object[] var2 = items;
      int var3 = items.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Object item = var2[var4];
         if (item instanceof IItemProvider) {
            this.itemSupplierList.add(() -> {
               return new ItemStack((IItemProvider)item);
            });
         } else {
            if (!(item instanceof Supplier)) {
               throw new IllegalArgumentException(String.format("DEVELOPMENT ERROR! Unacceptable object type %s in %s constructor", item, this.getClass().getName()));
            }

            this.itemSupplierList.add(() -> {
               Object supplied = ((Supplier)item).get();
               if (supplied instanceof IItemProvider) {
                  return new ItemStack((IItemProvider)supplied);
               } else if (supplied instanceof ItemStack) {
                  return (ItemStack)supplied;
               } else {
                  throw new IllegalArgumentException(String.format("DEVELOPMENT ERROR! Unacceptable Supplier-supplied type %s in %s constructor", supplied, this.getClass().getName()));
               }
            });
         }
      }

   }

   public final ItemStack getRandomItem(Random random) {
      return (ItemStack)((Supplier)this.itemSupplierList.get(random.nextInt(this.itemSupplierList.size()))).get();
   }

   public final ItemStack getRandomItem(Random random, Predicate filter) {
      List matchingItems = (List)this.itemSupplierList.stream().map(Supplier::get).filter(filter).collect(Collectors.toList());
      return (ItemStack)matchingItems.get(random.nextInt(matchingItems.size()));
   }
}
