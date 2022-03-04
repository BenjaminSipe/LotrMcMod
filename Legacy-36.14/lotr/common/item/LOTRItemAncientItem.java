package lotr.common.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRConfig;
import lotr.common.LOTRCreativeTabs;
import lotr.common.LOTRLevelData;
import lotr.common.enchant.LOTREnchantment;
import lotr.common.enchant.LOTREnchantmentHelper;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class LOTRItemAncientItem extends Item {
   @SideOnly(Side.CLIENT)
   private IIcon[] icons;
   private String[] itemNames = new String[]{"sword", "dagger", "helmet", "body", "legs", "boots"};

   public LOTRItemAncientItem() {
      this.func_77625_d(1);
      this.func_77627_a(true);
      this.func_77656_e(0);
      this.func_77637_a(LOTRCreativeTabs.tabMisc);
   }

   public ItemStack func_77659_a(ItemStack itemstack, World world, EntityPlayer entityplayer) {
      if (!world.field_72995_K) {
         ItemStack ancientItem = getRandomItem(itemstack);
         LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.craftAncientItem);
         world.func_72956_a(entityplayer, "random.pop", 0.2F, ((world.field_73012_v.nextFloat() - world.field_73012_v.nextFloat()) * 0.7F + 1.0F) * 2.0F);
         return ancientItem;
      } else {
         return itemstack;
      }
   }

   public static ItemStack getRandomItem(ItemStack itemstack) {
      ItemStack randomItem = null;
      IInventory randomItemInv = new InventoryBasic("ancientItem", true, 1);
      LOTRChestContents itemPool = null;
      if (itemstack.func_77960_j() == 0) {
         itemPool = LOTRChestContents.ANCIENT_SWORD;
      } else if (itemstack.func_77960_j() == 1) {
         itemPool = LOTRChestContents.ANCIENT_DAGGER;
      } else if (itemstack.func_77960_j() == 2) {
         itemPool = LOTRChestContents.ANCIENT_HELMET;
      } else if (itemstack.func_77960_j() == 3) {
         itemPool = LOTRChestContents.ANCIENT_BODY;
      } else if (itemstack.func_77960_j() == 4) {
         itemPool = LOTRChestContents.ANCIENT_LEGS;
      } else if (itemstack.func_77960_j() == 5) {
         itemPool = LOTRChestContents.ANCIENT_BOOTS;
      }

      LOTRChestContents.fillInventory(randomItemInv, field_77697_d, itemPool, 1);
      randomItem = randomItemInv.func_70301_a(0);
      if (randomItem != null) {
         if (LOTRConfig.enchantingLOTR) {
            LOTREnchantment wraithbane = LOTREnchantment.baneWraith;
            if (wraithbane.canApply(randomItem, false) && field_77697_d.nextInt(4) == 0) {
               LOTREnchantmentHelper.setHasEnchant(randomItem, wraithbane);
            }
         }

         return randomItem;
      } else {
         return itemstack;
      }
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_77617_a(int i) {
      if (i >= this.icons.length) {
         i = 0;
      }

      return this.icons[i];
   }

   public String func_77667_c(ItemStack itemstack) {
      return super.func_77658_a() + "." + itemstack.func_77960_j();
   }

   @SideOnly(Side.CLIENT)
   public void func_94581_a(IIconRegister iconregister) {
      this.icons = new IIcon[this.itemNames.length];

      for(int i = 0; i < this.itemNames.length; ++i) {
         this.icons[i] = iconregister.func_94245_a(this.func_111208_A() + "_" + this.itemNames[i]);
      }

   }

   @SideOnly(Side.CLIENT)
   public void func_150895_a(Item item, CreativeTabs tab, List list) {
      for(int i = 0; i <= 5; ++i) {
         list.add(new ItemStack(item, 1, i));
      }

   }
}
