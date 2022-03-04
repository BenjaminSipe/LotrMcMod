package lotr.common.item;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;

public class LOTRItemOwnership {
   private static final int PREVIOUS_OWNERS_RECORDED = 3;

   public static void setCurrentOwner(ItemStack itemstack, String name) {
      if (!itemstack.func_77942_o()) {
         itemstack.func_77982_d(new NBTTagCompound());
      }

      String previousCurrentOwner = getCurrentOwner(itemstack);
      if (previousCurrentOwner != null) {
         addPreviousOwner(itemstack, previousCurrentOwner);
      }

      NBTTagCompound nbt = itemstack.func_77978_p();
      nbt.func_74778_a("LOTRCurrentOwner", name);
   }

   public static String getCurrentOwner(ItemStack itemstack) {
      if (itemstack.func_77978_p() != null) {
         NBTTagCompound nbt = itemstack.func_77978_p();
         if (nbt.func_150297_b("LOTRCurrentOwner", 8)) {
            return nbt.func_74779_i("LOTRCurrentOwner");
         }
      }

      return null;
   }

   public static void addPreviousOwner(ItemStack itemstack, String name) {
      if (!itemstack.func_77942_o()) {
         itemstack.func_77982_d(new NBTTagCompound());
      }

      List previousOwners = getPreviousOwners(itemstack);
      NBTTagCompound nbt = itemstack.func_77978_p();
      if (nbt.func_150297_b("LOTROwner", 8)) {
         nbt.func_82580_o("LOTROwner");
      }

      List lastPreviousOwners = previousOwners;
      List previousOwners = new ArrayList();
      previousOwners.add(name);
      previousOwners.addAll(lastPreviousOwners);

      while(previousOwners.size() > 3) {
         previousOwners.remove(previousOwners.size() - 1);
      }

      NBTTagList tagList = new NBTTagList();
      Iterator var6 = previousOwners.iterator();

      while(var6.hasNext()) {
         String owner = (String)var6.next();
         tagList.func_74742_a(new NBTTagString(owner));
      }

      nbt.func_74782_a("LOTRPrevOwnerList", tagList);
   }

   public static List getPreviousOwners(ItemStack itemstack) {
      List owners = new ArrayList();
      if (itemstack.func_77978_p() != null) {
         NBTTagCompound nbt = itemstack.func_77978_p();
         if (nbt.func_150297_b("LOTROwner", 8)) {
            String outdatedOwner = nbt.func_74779_i("LOTROwner");
            owners.add(outdatedOwner);
         }

         if (nbt.func_150297_b("LOTRPrevOwnerList", 9)) {
            NBTTagList tagList = nbt.func_150295_c("LOTRPrevOwnerList", 8);

            for(int i = 0; i < tagList.func_74745_c(); ++i) {
               String owner = tagList.func_150307_f(i);
               owners.add(owner);
            }
         }
      }

      return owners;
   }
}
