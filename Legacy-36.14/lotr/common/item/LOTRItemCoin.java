package lotr.common.item;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import lotr.common.LOTRCreativeTabs;
import lotr.common.LOTRMod;
import lotr.common.quest.IPickpocketable;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class LOTRItemCoin extends Item {
   @SideOnly(Side.CLIENT)
   private IIcon[] coinIcons;
   public static int[] values = new int[]{1, 10, 100};

   public LOTRItemCoin() {
      this.func_77627_a(true);
      this.func_77656_e(0);
      this.func_77637_a(LOTRCreativeTabs.tabMaterials);
   }

   private static int getSingleItemValue(ItemStack itemstack, boolean allowStolen) {
      if (itemstack != null && itemstack.func_77973_b() instanceof LOTRItemCoin) {
         if (!allowStolen && IPickpocketable.Helper.isPickpocketed(itemstack)) {
            return 0;
         } else {
            int i = itemstack.func_77960_j();
            if (i >= values.length) {
               i = 0;
            }

            return values[i];
         }
      } else {
         return 0;
      }
   }

   public static int getStackValue(ItemStack itemstack, boolean allowStolen) {
      return itemstack == null ? 0 : getSingleItemValue(itemstack, allowStolen) * itemstack.field_77994_a;
   }

   public static int getInventoryValue(EntityPlayer entityplayer, boolean allowStolen) {
      int coins = 0;
      ItemStack[] var3 = entityplayer.field_71071_by.field_70462_a;
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         ItemStack itemstack = var3[var5];
         coins += getStackValue(itemstack, allowStolen);
      }

      coins += getStackValue(entityplayer.field_71071_by.func_70445_o(), allowStolen);
      return coins;
   }

   public static int getContainerValue(IInventory inv, boolean allowStolen) {
      if (inv instanceof InventoryPlayer) {
         return getInventoryValue(((InventoryPlayer)inv).field_70458_d, allowStolen);
      } else {
         int coins = 0;

         for(int i = 0; i < inv.func_70302_i_(); ++i) {
            ItemStack itemstack = inv.func_70301_a(i);
            coins += getStackValue(itemstack, allowStolen);
         }

         return coins;
      }
   }

   public static void takeCoins(int coins, EntityPlayer entityplayer) {
      InventoryPlayer inv = entityplayer.field_71071_by;
      int invValue = getInventoryValue(entityplayer, false);
      if (invValue < coins) {
         FMLLog.warning("Attempted to take " + coins + " coins from player " + entityplayer.func_70005_c_() + " who has only " + invValue, new Object[0]);
      }

      int initCoins = coins;

      int i;
      int value;
      ItemStack coin;
      int slot;
      ItemStack itemstack;
      ItemStack is;
      label113:
      for(i = values.length - 1; i >= 0; --i) {
         value = values[i];
         if (value <= initCoins) {
            coin = new ItemStack(LOTRMod.silverCoin, 1, i);

            for(slot = -1; slot < inv.field_70462_a.length; ++slot) {
               while((itemstack = slot == -1 ? inv.func_70445_o() : inv.field_70462_a[slot]) != null && itemstack.func_77969_a(coin)) {
                  if (slot == -1) {
                     is = inv.func_70445_o();
                     if (is != null) {
                        --is.field_77994_a;
                        if (is.field_77994_a <= 0) {
                           inv.func_70437_b((ItemStack)null);
                        }
                     }
                  } else {
                     inv.func_70298_a(slot, 1);
                  }

                  coins -= value;
                  if (coins < value) {
                     continue label113;
                  }
               }
            }
         }
      }

      if (coins > 0) {
         for(i = 0; i < values.length; ++i) {
            if (i != 0) {
               value = values[i];
               coin = new ItemStack(LOTRMod.silverCoin, 1, i);

               label85:
               for(slot = -1; slot < inv.field_70462_a.length; ++slot) {
                  while((itemstack = slot == -1 ? inv.func_70445_o() : inv.field_70462_a[slot]) != null && itemstack.func_77969_a(coin)) {
                     if (slot == -1) {
                        is = inv.func_70445_o();
                        if (is != null) {
                           --is.field_77994_a;
                           if (is.field_77994_a <= 0) {
                              inv.func_70437_b((ItemStack)null);
                           }
                        }
                     } else {
                        inv.func_70298_a(slot, 1);
                     }

                     coins -= value;
                     if (coins < 0) {
                        break label85;
                     }
                  }
               }

               if (coins < 0) {
                  break;
               }
            }
         }
      }

      if (coins < 0) {
         giveCoins(-coins, entityplayer);
      }

   }

   public static void giveCoins(int coins, EntityPlayer entityplayer) {
      InventoryPlayer inv = entityplayer.field_71071_by;
      if (coins <= 0) {
         FMLLog.warning("Attempted to give a non-positive value of coins " + coins + " to player " + entityplayer.func_70005_c_(), new Object[0]);
      }

      int i;
      int value;
      ItemStack coin;
      for(i = values.length - 1; i >= 0; --i) {
         value = values[i];

         for(coin = new ItemStack(LOTRMod.silverCoin, 1, i); coins >= value && inv.func_70441_a(coin.func_77946_l()); coins -= value) {
         }
      }

      if (coins > 0) {
         for(i = values.length - 1; i >= 0; --i) {
            value = values[i];

            for(coin = new ItemStack(LOTRMod.silverCoin, 1, i); coins >= value; coins -= value) {
               entityplayer.func_71019_a(coin.func_77946_l(), false);
            }
         }
      }

   }

   @SideOnly(Side.CLIENT)
   public IIcon func_77617_a(int i) {
      if (i >= this.coinIcons.length) {
         i = 0;
      }

      return this.coinIcons[i];
   }

   public String func_77667_c(ItemStack itemstack) {
      int i = itemstack.func_77960_j();
      if (i >= values.length) {
         i = 0;
      }

      return super.func_77658_a() + "." + values[i];
   }

   @SideOnly(Side.CLIENT)
   public void func_94581_a(IIconRegister iconregister) {
      this.coinIcons = new IIcon[values.length];

      for(int i = 0; i < values.length; ++i) {
         this.coinIcons[i] = iconregister.func_94245_a(this.func_111208_A() + "_" + values[i]);
      }

   }

   @SideOnly(Side.CLIENT)
   public void func_150895_a(Item item, CreativeTabs tab, List list) {
      for(int j = 0; j < values.length; ++j) {
         list.add(new ItemStack(item, 1, j));
      }

   }
}
