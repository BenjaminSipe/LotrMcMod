package lotr.common.block;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.common.registry.RegistryDelegate;
import cpw.mods.fml.common.registry.RegistryDelegate.Delegate;
import io.gitlab.dwarfyassassin.lotrucp.core.hooks.GenericModHooks;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import lotr.common.LOTRReflection;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatCrafting;
import net.minecraft.stats.StatList;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ObjectIntIdentityMap;
import net.minecraft.util.RegistryNamespaced;
import net.minecraft.util.RegistrySimple;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class LOTRBlockReplacement {
   private static boolean initForgeHooks = false;

   public static void replaceVanillaBlock(Block oldBlock, Block newBlock, Class itemClass) {
      try {
         Item oldItem = Item.func_150898_a(oldBlock);
         int id = Block.field_149771_c.func_148757_b(oldBlock);
         String blockName = LOTRBlockReplacement.Reflect.getBlockName(oldBlock);
         String registryName = Block.field_149771_c.func_148750_c(oldBlock);
         String itemblockName = blockName;
         if (oldItem != null) {
            itemblockName = LOTRBlockReplacement.Reflect.getItemName(oldItem);
         }

         GenericModHooks.removeBlockFromOreDictionary(oldBlock);
         newBlock.func_149663_c(blockName);
         LOTRBlockReplacement.Reflect.overwriteBlockList(oldBlock, newBlock);
         LOTRBlockReplacement.Reflect.setDelegateName(newBlock.delegate, oldBlock.delegate.name());
         LOTRBlockReplacement.Reflect.getUnderlyingIntMap(Block.field_149771_c).func_148746_a(newBlock, id);
         LOTRBlockReplacement.Reflect.getUnderlyingObjMap(Block.field_149771_c).put(registryName, newBlock);
         if (!initForgeHooks) {
            ForgeHooks.isToolEffective(new ItemStack(Items.field_151037_a), Blocks.field_150346_d, 0);
            initForgeHooks = true;
         }

         for(int meta = 0; meta <= 15; ++meta) {
            newBlock.setHarvestLevel(oldBlock.getHarvestTool(meta), oldBlock.getHarvestLevel(meta), meta);
         }

         if (itemClass != null) {
            Constructor itemCtor = null;
            Constructor[] itemCtors = itemClass.getConstructors();
            Constructor[] var10 = itemCtors;
            int var11 = itemCtors.length;

            for(int var12 = 0; var12 < var11; ++var12) {
               Constructor ct = var10[var12];
               Class[] params = ct.getParameterTypes();
               if (params.length == 1 && Block.class.isAssignableFrom(params[0])) {
                  itemCtor = ct;
                  break;
               }
            }

            ItemBlock itemblock = ((ItemBlock)itemCtor.newInstance(newBlock)).func_77655_b(itemblockName);
            LOTRBlockReplacement.Reflect.setDelegateName(itemblock.delegate, oldItem.delegate.name());
            LOTRBlockReplacement.Reflect.getUnderlyingIntMap(Item.field_150901_e).func_148746_a(itemblock, id);
            LOTRBlockReplacement.Reflect.getUnderlyingObjMap(Item.field_150901_e).put(registryName, itemblock);
            replaceBlockStats(id, newBlock, itemblock);
            replaceRecipesEtc(itemblock);
         }

      } catch (Exception var15) {
         FMLLog.severe("Failed to replace vanilla block %s", new Object[]{oldBlock.func_149739_a()});
         throw new RuntimeException(var15);
      }
   }

   private static void replaceBlockStats(int id, Block newBlock, ItemBlock itemblock) {
      replaceStat(id, StatList.field_75934_C, new StatCrafting("stat.mineBlock." + id, new ChatComponentTranslation("stat.mineBlock", new Object[]{(new ItemStack(newBlock)).func_151000_E()}), itemblock));
      replaceStat(id, StatList.field_75929_E, new StatCrafting("stat.useItem." + id, new ChatComponentTranslation("stat.useItem", new Object[]{(new ItemStack(itemblock)).func_151000_E()}), itemblock));
      replaceStat(id, StatList.field_75928_D, new StatCrafting("stat.craftItem." + id, new ChatComponentTranslation("stat.craftItem", new Object[]{(new ItemStack(itemblock)).func_151000_E()}), itemblock));
   }

   public static void replaceVanillaItem(Item oldItem, Item newItem) {
      try {
         int id = Item.field_150901_e.func_148757_b(oldItem);
         String itemName = LOTRBlockReplacement.Reflect.getItemName(oldItem);
         String registryName = Item.field_150901_e.func_148750_c(oldItem);
         GenericModHooks.removeItemFromOreDictionary(oldItem);
         newItem.func_77655_b(itemName);
         LOTRBlockReplacement.Reflect.overwriteItemList(oldItem, newItem);
         LOTRBlockReplacement.Reflect.setDelegateName(newItem.delegate, oldItem.delegate.name());
         LOTRBlockReplacement.Reflect.getUnderlyingIntMap(Item.field_150901_e).func_148746_a(newItem, id);
         LOTRBlockReplacement.Reflect.getUnderlyingObjMap(Item.field_150901_e).put(registryName, newItem);
         replaceItemStats(id, newItem);
         replaceRecipesEtc(newItem);
      } catch (Exception var5) {
         FMLLog.severe("Failed to replace vanilla item %s", new Object[]{oldItem.func_77658_a()});
         throw new RuntimeException(var5);
      }
   }

   private static void replaceItemStats(int id, Item newItem) {
      replaceStat(id, StatList.field_75929_E, new StatCrafting("stat.useItem." + id, new ChatComponentTranslation("stat.useItem", new Object[]{(new ItemStack(newItem)).func_151000_E()}), newItem));
      replaceStat(id, StatList.field_75928_D, new StatCrafting("stat.craftItem." + id, new ChatComponentTranslation("stat.craftItem", new Object[]{(new ItemStack(newItem)).func_151000_E()}), newItem));
      if (newItem.func_77645_m()) {
         replaceStat(id, StatList.field_75930_F, new StatCrafting("stat.breakItem." + id, new ChatComponentTranslation("stat.breakItem", new Object[]{(new ItemStack(newItem)).func_151000_E()}), newItem));
      }

   }

   private static void replaceStat(int id, StatBase[] stats, StatBase newStat) {
      StatBase oldStat = stats[id];
      if (oldStat != null && oldStat.field_75975_e.equals(newStat.field_75975_e)) {
         for(int i = 0; i < stats.length; ++i) {
            StatBase otherOldStat = stats[i];
            if (otherOldStat != null && otherOldStat.field_75975_e.equals(oldStat.field_75975_e)) {
               StatList.field_75940_b.remove(otherOldStat);
               StatList.field_75939_e.remove(otherOldStat);
               StatList.field_75938_d.remove(otherOldStat);
               StatList.field_75941_c.remove(otherOldStat);
               LOTRBlockReplacement.Reflect.getOneShotStats().remove(otherOldStat.field_75975_e);
               stats[i] = newStat;
            }
         }

         newStat.func_75971_g();
      }

   }

   private static void replaceRecipesEtc(Item newItem) {
      String newItemName = newItem.func_77658_a();
      List craftingRecipes = CraftingManager.func_77594_a().func_77592_b();
      Iterator var3 = craftingRecipes.iterator();

      Object obj;
      ItemStack output;
      while(var3.hasNext()) {
         obj = var3.next();
         if (obj instanceof ShapedRecipes) {
            ShapedRecipes recipe = (ShapedRecipes)obj;
            output = recipe.func_77571_b();
            if (output != null && output.func_77973_b() != null && output.func_77973_b().func_77658_a().equals(newItemName)) {
               injectReplacementItem(output, newItem);
            }
         }

         if (obj instanceof ShapelessRecipes) {
            ShapelessRecipes recipe = (ShapelessRecipes)obj;
            output = recipe.func_77571_b();
            if (output != null && output.func_77973_b() != null && output.func_77973_b().func_77658_a().equals(newItemName)) {
               injectReplacementItem(output, newItem);
            }
         }

         if (obj instanceof ShapedOreRecipe) {
            ShapedOreRecipe recipe = (ShapedOreRecipe)obj;
            output = recipe.func_77571_b();
            if (output != null && output.func_77973_b() != null && output.func_77973_b().func_77658_a().equals(newItemName)) {
               injectReplacementItem(output, newItem);
            }
         }

         if (obj instanceof ShapelessOreRecipe) {
            ShapelessOreRecipe recipe = (ShapelessOreRecipe)obj;
            output = recipe.func_77571_b();
            if (output != null && output.func_77973_b() != null && output.func_77973_b().func_77658_a().equals(newItemName)) {
               injectReplacementItem(output, newItem);
            }
         }
      }

      var3 = AchievementList.field_76007_e.iterator();

      while(var3.hasNext()) {
         obj = var3.next();
         Achievement a = (Achievement)obj;
         output = a.field_75990_d;
         if (output.func_77973_b().func_77658_a().equals(newItem.func_77658_a())) {
            injectReplacementItem(output, newItem);
         }
      }

   }

   private static void injectReplacementItem(ItemStack itemstack, Item newItem) {
      NBTTagCompound nbt = new NBTTagCompound();
      itemstack.func_77955_b(nbt);
      itemstack.func_77963_c(nbt);
   }

   private static class Reflect {
      private static void overwriteBlockList(Block oldBlock, Block newBlock) {
         try {
            Field field = null;
            Field[] declaredFields = Blocks.class.getDeclaredFields();
            Field[] var4 = declaredFields;
            int var5 = declaredFields.length;

            for(int var6 = 0; var6 < var5; ++var6) {
               Field f = var4[var6];
               LOTRReflection.unlockFinalField(f);
               if (f.get((Object)null) == oldBlock) {
                  field = f;
                  break;
               }
            }

            LOTRReflection.setFinalField(Blocks.class, (Object)null, newBlock, (Field)field);
         } catch (Exception var8) {
            LOTRReflection.logFailure(var8);
         }

      }

      private static void overwriteItemList(Item oldItem, Item newItem) {
         try {
            Field field = null;
            Field[] declaredFields = Items.class.getDeclaredFields();
            Field[] var4 = declaredFields;
            int var5 = declaredFields.length;

            for(int var6 = 0; var6 < var5; ++var6) {
               Field f = var4[var6];
               LOTRReflection.unlockFinalField(f);
               if (f.get((Object)null) == oldItem) {
                  field = f;
                  break;
               }
            }

            LOTRReflection.setFinalField(Items.class, (Object)null, newItem, (Field)field);
         } catch (Exception var8) {
            LOTRReflection.logFailure(var8);
         }

      }

      private static String getBlockName(Block block) {
         try {
            return (String)ObfuscationReflectionHelper.getPrivateValue(Block.class, block, new String[]{"unlocalizedName", "field_149770_b"});
         } catch (Exception var2) {
            LOTRReflection.logFailure(var2);
            return null;
         }
      }

      private static String getItemName(Item item) {
         try {
            return (String)ObfuscationReflectionHelper.getPrivateValue(Item.class, item, new String[]{"unlocalizedName", "field_77774_bZ"});
         } catch (Exception var2) {
            LOTRReflection.logFailure(var2);
            return null;
         }
      }

      private static ObjectIntIdentityMap getUnderlyingIntMap(RegistryNamespaced registry) {
         try {
            return (ObjectIntIdentityMap)ObfuscationReflectionHelper.getPrivateValue(RegistryNamespaced.class, registry, new String[]{"underlyingIntegerMap", "field_148759_a"});
         } catch (Exception var2) {
            LOTRReflection.logFailure(var2);
            return null;
         }
      }

      private static Map getUnderlyingObjMap(RegistryNamespaced registry) {
         try {
            return (Map)ObfuscationReflectionHelper.getPrivateValue(RegistrySimple.class, registry, new String[]{"registryObjects", "field_82596_a"});
         } catch (Exception var2) {
            LOTRReflection.logFailure(var2);
            return null;
         }
      }

      private static void setDelegateName(RegistryDelegate rd, String name) {
         Delegate delegate = (Delegate)rd;

         try {
            ObfuscationReflectionHelper.setPrivateValue(Delegate.class, delegate, name, new String[]{"name"});
         } catch (Exception var4) {
            LOTRReflection.logFailure(var4);
         }

      }

      private static Map getOneShotStats() {
         try {
            return (Map)ObfuscationReflectionHelper.getPrivateValue(StatList.class, (Object)null, new String[]{"oneShotStats", "field_75942_a"});
         } catch (Exception var1) {
            LOTRReflection.logFailure(var1);
            return null;
         }
      }
   }
}
