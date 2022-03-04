package lotr.common.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRCommonProxy;
import lotr.common.LOTRCreativeTabs;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class LOTRItemDaleCracker extends Item {
   @SideOnly(Side.CLIENT)
   private IIcon[] crackerIcons;
   private String[] crackerNames = new String[]{"red", "blue", "green", "silver", "gold"};
   private static int emptyMeta = 4096;
   public static final int CUSTOM_CAPACITY = 3;

   public LOTRItemDaleCracker() {
      this.func_77625_d(1);
      this.func_77627_a(true);
      this.func_77656_e(0);
      this.func_77637_a(LOTRCreativeTabs.tabMisc);
   }

   public static boolean isEmpty(ItemStack itemstack) {
      return (itemstack.func_77960_j() & emptyMeta) == emptyMeta;
   }

   public static ItemStack setEmpty(ItemStack itemstack, boolean flag) {
      int i = itemstack.func_77960_j();
      if (flag) {
         i |= emptyMeta;
      } else {
         i &= ~emptyMeta;
      }

      itemstack.func_77964_b(i);
      return itemstack;
   }

   private static int getBaseCrackerMetadata(ItemStack itemstack) {
      return getBaseCrackerMetadata(itemstack.func_77960_j());
   }

   private static int getBaseCrackerMetadata(int i) {
      return i & ~emptyMeta;
   }

   public static String getSealingPlayerName(ItemStack itemstack) {
      return itemstack.func_77978_p() != null && itemstack.func_77978_p().func_74764_b("SealingPlayer") ? itemstack.func_77978_p().func_74779_i("SealingPlayer") : null;
   }

   public static void setSealingPlayerName(ItemStack itemstack, String name) {
      if (itemstack.func_77978_p() == null) {
         itemstack.func_77982_d(new NBTTagCompound());
      }

      if (name == null) {
         itemstack.func_77978_p().func_82580_o("SealingPlayer");
      } else {
         itemstack.func_77978_p().func_74778_a("SealingPlayer", name);
      }

   }

   public static IInventory loadCustomCrackerContents(ItemStack itemstack) {
      if (itemstack.func_77978_p() != null && itemstack.func_77978_p().func_74764_b("CustomCracker")) {
         NBTTagCompound invData = itemstack.func_77978_p().func_74775_l("CustomCracker");
         int size = invData.func_74762_e("Size");
         IInventory inv = new InventoryBasic("cracker", false, size);
         NBTTagList items = invData.func_150295_c("Items", 10);

         for(int i = 0; i < items.func_74745_c(); ++i) {
            NBTTagCompound itemData = items.func_150305_b(i);
            int slot = itemData.func_74771_c("Slot");
            if (slot >= 0 && slot < inv.func_70302_i_()) {
               inv.func_70299_a(slot, ItemStack.func_77949_a(itemData));
            }
         }

         return inv;
      } else {
         return null;
      }
   }

   public static void setCustomCrackerContents(ItemStack itemstack, IInventory inv) {
      if (itemstack.func_77978_p() == null) {
         itemstack.func_77982_d(new NBTTagCompound());
      }

      if (inv == null) {
         itemstack.func_77978_p().func_82580_o("CustomCracker");
      } else {
         NBTTagCompound invData = new NBTTagCompound();
         int size = inv.func_70302_i_();
         invData.func_74768_a("Size", size);
         NBTTagList items = new NBTTagList();

         for(int i = 0; i < inv.func_70302_i_(); ++i) {
            ItemStack invItem = inv.func_70301_a(i);
            if (invItem != null) {
               NBTTagCompound itemData = new NBTTagCompound();
               itemData.func_74774_a("Slot", (byte)i);
               invItem.func_77955_b(itemData);
               items.func_74742_a(itemData);
            }
         }

         invData.func_74782_a("Items", items);
         itemstack.func_77978_p().func_74782_a("CustomCracker", invData);
      }

   }

   public String func_77653_i(ItemStack itemstack) {
      if (isEmpty(itemstack)) {
         String name = super.func_77653_i(itemstack);
         return StatCollector.func_74837_a("item.lotr.cracker.empty", new Object[]{name});
      } else {
         return super.func_77653_i(itemstack);
      }
   }

   @SideOnly(Side.CLIENT)
   public void func_77624_a(ItemStack itemstack, EntityPlayer entityplayer, List list, boolean flag) {
      if (!isEmpty(itemstack)) {
         String name = getSealingPlayerName(itemstack);
         if (name == null) {
            name = StatCollector.func_74838_a("item.lotr.cracker.sealedByDale");
         }

         list.add(StatCollector.func_74837_a("item.lotr.cracker.sealedBy", new Object[]{name}));
      }

   }

   public int func_77626_a(ItemStack itemstack) {
      return 40;
   }

   public EnumAction func_77661_b(ItemStack itemstack) {
      return EnumAction.bow;
   }

   public ItemStack func_77659_a(ItemStack itemstack, World world, EntityPlayer entityplayer) {
      if (!isEmpty(itemstack)) {
         entityplayer.func_71008_a(itemstack, this.func_77626_a(itemstack));
      } else {
         LOTRCommonProxy var10002 = LOTRMod.proxy;
         entityplayer.openGui(LOTRMod.instance, 48, world, 0, 0, 0);
      }

      return itemstack;
   }

   public ItemStack func_77654_b(ItemStack itemstack, World world, EntityPlayer entityplayer) {
      if (!isEmpty(itemstack)) {
         if (!entityplayer.field_71075_bZ.field_75098_d) {
            --itemstack.field_77994_a;
            if (itemstack.field_77994_a <= 0) {
               entityplayer.field_71071_by.func_70299_a(entityplayer.field_71071_by.field_70461_c, (ItemStack)null);
            }
         }

         world.func_72956_a(entityplayer, "fireworks.blast", 1.0F, 0.9F + world.field_73012_v.nextFloat() * 0.1F);
         if (!world.field_72995_K) {
            IInventory crackerItems = null;
            IInventory customItems = loadCustomCrackerContents(itemstack);
            int amount;
            if (customItems != null) {
               crackerItems = customItems;
            } else {
               amount = 1;
               if (world.field_73012_v.nextInt(3) == 0) {
                  ++amount;
               }

               if (LOTRMod.isChristmas()) {
                  amount += 1 + world.field_73012_v.nextInt(4);
               }

               crackerItems = new InventoryBasic("cracker", true, amount);
               LOTRChestContents.fillInventory((IInventory)crackerItems, world.field_73012_v, LOTRChestContents.DALE_CRACKER, amount);
            }

            for(amount = 0; amount < ((IInventory)crackerItems).func_70302_i_(); ++amount) {
               ItemStack loot = ((IInventory)crackerItems).func_70301_a(amount);
               if (!entityplayer.field_71071_by.func_70441_a(loot)) {
                  entityplayer.func_71019_a(loot, false);
               }
            }

            LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.openDaleCracker);
            return entityplayer.field_71071_by.func_70301_a(entityplayer.field_71071_by.field_70461_c);
         } else {
            return itemstack;
         }
      } else {
         return itemstack;
      }
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_77617_a(int i) {
      i = getBaseCrackerMetadata(i);
      if (i >= this.crackerIcons.length) {
         i = 0;
      }

      return this.crackerIcons[i];
   }

   @SideOnly(Side.CLIENT)
   public void func_94581_a(IIconRegister iconregister) {
      this.crackerIcons = new IIcon[this.crackerNames.length];

      for(int i = 0; i < this.crackerNames.length; ++i) {
         this.crackerIcons[i] = iconregister.func_94245_a(this.func_111208_A() + "_" + this.crackerNames[i]);
      }

   }

   @SideOnly(Side.CLIENT)
   public void func_150895_a(Item item, CreativeTabs tab, List list) {
      for(int i = 0; i < this.crackerNames.length; ++i) {
         list.add(new ItemStack(item, 1, i));
         list.add(setEmpty(new ItemStack(item, 1, i), true));
      }

   }
}
