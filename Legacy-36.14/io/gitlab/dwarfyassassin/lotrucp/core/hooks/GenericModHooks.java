package io.gitlab.dwarfyassassin.lotrucp.core.hooks;

import cpw.mods.fml.common.registry.GameData;
import cpw.mods.fml.common.registry.RegistryDelegate.Delegate;
import cpw.mods.fml.relauncher.ReflectionHelper;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class GenericModHooks {
   public static void setItemDelagateName(Item item, String name) {
      Delegate delegate = (Delegate)item.delegate;
      ReflectionHelper.setPrivateValue(Delegate.class, delegate, name, new String[]{"name"});
   }

   public static void setBlockDelagateName(Block block, String name) {
      Delegate delegate = (Delegate)block.delegate;
      ReflectionHelper.setPrivateValue(Delegate.class, delegate, name, new String[]{"name"});
   }

   public static void removeBlockFromOreDictionary(Block block) {
      removeItemFromOreDictionary(Item.func_150898_a(block));
   }

   public static void removeItemFromOreDictionary(Item item) {
      if (item != null) {
         ItemStack stack = new ItemStack(item, 1, 32767);
         int[] oreIDs = OreDictionary.getOreIDs(stack);
         List oreIdToStacks = (List)ReflectionHelper.getPrivateValue(OreDictionary.class, (Object)null, new String[]{"idToStack"});
         int[] var4 = oreIDs;
         int stackId = oreIDs.length;

         for(int var6 = 0; var6 < stackId; ++var6) {
            int oreID = var4[var6];
            ArrayList oreStacks = (ArrayList)oreIdToStacks.get(oreID);
            if (oreStacks != null) {
               Set toRemove = new HashSet();
               Iterator var10 = oreStacks.iterator();

               while(var10.hasNext()) {
                  ItemStack oreStack = (ItemStack)var10.next();
                  if (oreStack.func_77973_b() == stack.func_77973_b()) {
                     toRemove.add(oreStack);
                  }
               }

               oreStacks.removeAll(toRemove);
            }
         }

         String registryName = stack.func_77973_b().delegate.name();
         if (registryName != null) {
            stackId = GameData.getItemRegistry().getId(registryName);
            Map stackIdToOreId = (Map)ReflectionHelper.getPrivateValue(OreDictionary.class, (Object)null, new String[]{"stackToId"});
            stackIdToOreId.remove(stackId);
         }
      }
   }
}
