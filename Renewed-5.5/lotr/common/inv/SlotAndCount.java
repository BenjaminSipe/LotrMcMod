package lotr.common.inv;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.minecraft.inventory.IInventory;

public class SlotAndCount implements Comparable {
   public final int slotIndex;
   public final int stackSize;

   public SlotAndCount(int i, int j) {
      this.slotIndex = i;
      this.stackSize = j;
   }

   public int compareTo(Object other) {
      if (other instanceof SlotAndCount) {
         SlotAndCount obj1 = (SlotAndCount)other;
         if (obj1.stackSize < this.stackSize) {
            return 1;
         }

         if (obj1.stackSize > this.stackSize) {
            return -1;
         }

         if (obj1.stackSize == this.stackSize) {
            if (obj1.slotIndex < this.slotIndex) {
               return 1;
            }

            if (obj1.slotIndex > this.slotIndex) {
               return -1;
            }
         }
      }

      return 0;
   }

   public static int[] sortSlotsByCount(IInventory inv, int[] slotIndices) {
      List slotsWithStackSize = new ArrayList();
      int[] sortedSlots = slotIndices;
      int i = slotIndices.length;

      for(int var5 = 0; var5 < i; ++var5) {
         int slot = sortedSlots[var5];
         int size = inv.func_70301_a(slot).func_190916_E();
         slotsWithStackSize.add(new SlotAndCount(slot, size));
      }

      Collections.sort(slotsWithStackSize);
      sortedSlots = new int[slotIndices.length];

      for(i = 0; i < sortedSlots.length; ++i) {
         sortedSlots[i] = ((SlotAndCount)slotsWithStackSize.get(i)).slotIndex;
      }

      return sortedSlots;
   }
}
