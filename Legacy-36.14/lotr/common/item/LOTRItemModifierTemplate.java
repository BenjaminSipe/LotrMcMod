package lotr.common.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import lotr.common.LOTRCreativeTabs;
import lotr.common.LOTRMod;
import lotr.common.enchant.LOTREnchantment;
import lotr.common.enchant.LOTREnchantmentHelper;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.WeightedRandom;

public class LOTRItemModifierTemplate extends Item {
   public LOTRItemModifierTemplate() {
      this.func_77625_d(1);
      this.func_77637_a(LOTRCreativeTabs.tabMaterials);
   }

   public static LOTREnchantment getModifier(ItemStack itemstack) {
      NBTTagCompound nbt = itemstack.func_77978_p();
      if (nbt != null) {
         String s = nbt.func_74779_i("ScrollModifier");
         return LOTREnchantment.getEnchantmentByName(s);
      } else {
         return null;
      }
   }

   public static void setModifier(ItemStack itemstack, LOTREnchantment ench) {
      String s = ench.enchantName;
      itemstack.func_77983_a("ScrollModifier", new NBTTagString(s));
   }

   public String func_77653_i(ItemStack itemstack) {
      String s = super.func_77653_i(itemstack);
      LOTREnchantment mod = getModifier(itemstack);
      if (mod != null) {
         s = String.format(s, mod.getDisplayName());
      }

      return s;
   }

   @SideOnly(Side.CLIENT)
   public void func_77624_a(ItemStack itemstack, EntityPlayer entityplayer, List list, boolean flag) {
      super.func_77624_a(itemstack, entityplayer, list, flag);
      LOTREnchantment mod = getModifier(itemstack);
      if (mod != null) {
         String desc = mod.getNamedFormattedDescription(itemstack);
         list.add(desc);
      }

   }

   @SideOnly(Side.CLIENT)
   public void func_150895_a(Item item, CreativeTabs tab, List list) {
      Iterator var4 = LOTREnchantment.allEnchantments.iterator();

      while(var4.hasNext()) {
         LOTREnchantment ench = (LOTREnchantment)var4.next();
         if (ench.hasTemplateItem()) {
            ItemStack itemstack = new ItemStack(this);
            setModifier(itemstack, ench);
            list.add(itemstack);
         }
      }

   }

   public static ItemStack getRandomCommonTemplate(Random random) {
      List applicable = new ArrayList();
      Iterator var2 = LOTREnchantment.allEnchantments.iterator();

      LOTREnchantment ench;
      while(var2.hasNext()) {
         ench = (LOTREnchantment)var2.next();
         if (ench.hasTemplateItem()) {
            int weight = LOTREnchantmentHelper.getSkilfulWeight(ench);
            LOTREnchantmentHelper.WeightedRandomEnchant wre = new LOTREnchantmentHelper.WeightedRandomEnchant(ench, weight);
            applicable.add(wre);
         }
      }

      LOTREnchantmentHelper.WeightedRandomEnchant chosenWre = (LOTREnchantmentHelper.WeightedRandomEnchant)WeightedRandom.func_76271_a(random, applicable);
      ench = chosenWre.theEnchant;
      ItemStack itemstack = new ItemStack(LOTRMod.modTemplate);
      setModifier(itemstack, ench);
      return itemstack;
   }
}
