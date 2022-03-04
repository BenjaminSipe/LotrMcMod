package lotr.common.item;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.ITextComponent.Serializer;

public class ItemOwnership {
   private static final int PREVIOUS_OWNERS_RECORDED = 3;

   public static void setCurrentOwner(ItemStack itemstack, ITextComponent name) {
      ITextComponent previousCurrentOwner = getCurrentOwner(itemstack);
      if (previousCurrentOwner != null) {
         addPreviousOwner(itemstack, previousCurrentOwner);
      }

      itemstack.func_190925_c("LOTROwnership").func_74778_a("CurrentOwner", Serializer.func_150696_a(name));
   }

   public static ITextComponent getCurrentOwner(ItemStack itemstack) {
      CompoundNBT nbt = itemstack.func_179543_a("LOTROwnership");
      if (nbt != null && nbt.func_150297_b("CurrentOwner", 8)) {
         String ownerJson = nbt.func_74779_i("CurrentOwner");
         return Serializer.func_240644_b_(ownerJson);
      } else {
         return null;
      }
   }

   public static void addPreviousOwner(ItemStack itemstack, ITextComponent name) {
      List previousOwners = getPreviousOwners(itemstack);
      List lastPreviousOwners = previousOwners;
      List previousOwners = new ArrayList();
      previousOwners.add(name);
      previousOwners.addAll(lastPreviousOwners);

      while(previousOwners.size() > 3) {
         previousOwners.remove(previousOwners.size() - 1);
      }

      ListNBT tagList = new ListNBT();
      tagList.addAll((Collection)previousOwners.stream().map(Serializer::func_150696_a).map(StringNBT::func_229705_a_).collect(Collectors.toList()));
      CompoundNBT nbt = itemstack.func_190925_c("LOTROwnership");
      nbt.func_218657_a("PreviousOwners", tagList);
   }

   public static List getPreviousOwners(ItemStack itemstack) {
      List owners = new ArrayList();
      CompoundNBT nbt = itemstack.func_179543_a("LOTROwnership");
      if (nbt != null && nbt.func_150297_b("PreviousOwners", 9)) {
         ListNBT tagList = nbt.func_150295_c("PreviousOwners", 8);

         for(int i = 0; i < tagList.size(); ++i) {
            String ownerJson = tagList.func_150307_f(i);
            owners.add(Serializer.func_240644_b_(ownerJson));
         }
      }

      return owners;
   }
}
