package lotr.common.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import lotr.common.LOTRCreativeTabs;
import lotr.common.LOTRMod;
import lotr.common.block.LOTRBlockChest;
import lotr.common.block.LOTRBlockSpawnerChest;
import lotr.common.inventory.LOTRContainerChestWithPouch;
import lotr.common.inventory.LOTRContainerPouch;
import lotr.common.inventory.LOTRInventoryPouch;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockEnderChest;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryEnderChest;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class LOTRItemPouch extends Item {
   private static final int POUCH_COLOR = 10841676;
   @SideOnly(Side.CLIENT)
   private IIcon[] pouchIcons;
   @SideOnly(Side.CLIENT)
   private IIcon[] pouchIconsOpen;
   @SideOnly(Side.CLIENT)
   private IIcon[] overlayIcons;
   @SideOnly(Side.CLIENT)
   private IIcon[] overlayIconsOpen;
   private static String[] pouchTypes = new String[]{"small", "medium", "large"};

   public LOTRItemPouch() {
      this.func_77627_a(true);
      this.func_77656_e(0);
      this.func_77625_d(1);
      this.func_77637_a(LOTRCreativeTabs.tabMisc);
   }

   public ItemStack func_77659_a(ItemStack itemstack, World world, EntityPlayer entityplayer) {
      if (!world.field_72995_K) {
         entityplayer.openGui(LOTRMod.instance, 15, world, entityplayer.field_71071_by.field_70461_c, 0, 0);
      }

      return itemstack;
   }

   public boolean onItemUseFirst(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int side, float hitX, float hitY, float hitZ) {
      IInventory chest = getChestInvAt(entityplayer, world, i, j, k);
      if (chest != null) {
         LOTRMod.proxy.usePouchOnChest(entityplayer, world, i, j, k, side, itemstack, entityplayer.field_71071_by.field_70461_c);
         return true;
      } else {
         return false;
      }
   }

   public static boolean isHoldingPouch(EntityPlayer entityplayer, int slot) {
      return entityplayer.field_71071_by.func_70301_a(slot) != null && entityplayer.field_71071_by.func_70301_a(slot).func_77973_b() instanceof LOTRItemPouch;
   }

   public static IInventory getChestInvAt(EntityPlayer entityplayer, World world, int i, int j, int k) {
      Block block = world.func_147439_a(i, j, k);
      if (block instanceof LOTRBlockSpawnerChest) {
         return null;
      } else {
         TileEntity te = world.func_147438_o(i, j, k);
         if (block instanceof BlockChest) {
            return ((BlockChest)block).func_149951_m(world, i, j, k);
         } else if (block instanceof LOTRBlockChest) {
            return ((LOTRBlockChest)block).getModChestAt(world, i, j, k);
         } else {
            if (block instanceof BlockEnderChest && !world.func_147439_a(i, j + 1, k).func_149721_r()) {
               InventoryEnderChest enderInv = entityplayer.func_71005_bN();
               if (enderInv != null && te instanceof TileEntityEnderChest) {
                  TileEntityEnderChest enderChest = (TileEntityEnderChest)te;
                  if (!world.field_72995_K) {
                     enderInv.func_146031_a(enderChest);
                  }

                  return enderInv;
               }
            }

            return null;
         }
      }
   }

   public static int getCapacity(ItemStack itemstack) {
      return getCapacityForMeta(itemstack.func_77960_j());
   }

   public static int getCapacityForMeta(int i) {
      return (i + 1) * 9;
   }

   public static int getMaxPouchCapacity() {
      return getCapacityForMeta(pouchTypes.length - 1);
   }

   public static int getRandomPouchSize(Random random) {
      float f = random.nextFloat();
      if (f < 0.6F) {
         return 0;
      } else {
         return f < 0.9F ? 1 : 2;
      }
   }

   @SideOnly(Side.CLIENT)
   public void func_94581_a(IIconRegister iconregister) {
      this.pouchIcons = new IIcon[pouchTypes.length];
      this.pouchIconsOpen = new IIcon[pouchTypes.length];
      this.overlayIcons = new IIcon[pouchTypes.length];
      this.overlayIconsOpen = new IIcon[pouchTypes.length];

      for(int i = 0; i < pouchTypes.length; ++i) {
         this.pouchIcons[i] = iconregister.func_94245_a(this.func_111208_A() + "_" + pouchTypes[i]);
         this.pouchIconsOpen[i] = iconregister.func_94245_a(this.func_111208_A() + "_" + pouchTypes[i] + "_open");
         this.overlayIcons[i] = iconregister.func_94245_a(this.func_111208_A() + "_" + pouchTypes[i] + "_overlay");
         this.overlayIconsOpen[i] = iconregister.func_94245_a(this.func_111208_A() + "_" + pouchTypes[i] + "_open_overlay");
      }

   }

   @SideOnly(Side.CLIENT)
   public boolean func_77623_v() {
      return true;
   }

   public int getRenderPasses(int meta) {
      return 2;
   }

   public IIcon getIcon(ItemStack itemstack, int pass) {
      boolean open = false;
      EntityPlayer entityplayer = LOTRMod.proxy.getClientPlayer();
      if (entityplayer != null) {
         Container container = entityplayer.field_71070_bA;
         if ((container instanceof LOTRContainerPouch || container instanceof LOTRContainerChestWithPouch) && itemstack == entityplayer.func_70694_bm()) {
            open = true;
         }
      }

      int meta = itemstack.func_77960_j();
      if (meta >= this.pouchIcons.length) {
         meta = 0;
      }

      if (open) {
         return pass > 0 ? this.overlayIconsOpen[meta] : this.pouchIconsOpen[meta];
      } else {
         return pass > 0 ? this.overlayIcons[meta] : this.pouchIcons[meta];
      }
   }

   @SideOnly(Side.CLIENT)
   public int func_82790_a(ItemStack itemstack, int pass) {
      return pass == 0 ? getPouchColor(itemstack) : 16777215;
   }

   public static int getPouchColor(ItemStack itemstack) {
      int dye = getSavedDyeColor(itemstack);
      return dye != -1 ? dye : 10841676;
   }

   private static int getSavedDyeColor(ItemStack itemstack) {
      return itemstack.func_77978_p() != null && itemstack.func_77978_p().func_74764_b("PouchColor") ? itemstack.func_77978_p().func_74762_e("PouchColor") : -1;
   }

   public static boolean isPouchDyed(ItemStack itemstack) {
      return getSavedDyeColor(itemstack) != -1;
   }

   public static void setPouchColor(ItemStack itemstack, int i) {
      if (itemstack.func_77978_p() == null) {
         itemstack.func_77982_d(new NBTTagCompound());
      }

      itemstack.func_77978_p().func_74768_a("PouchColor", i);
   }

   public static void removePouchDye(ItemStack itemstack) {
      if (itemstack.func_77978_p() != null) {
         itemstack.func_77978_p().func_82580_o("PouchColor");
      }

   }

   public String func_77667_c(ItemStack itemstack) {
      return super.func_77658_a() + "." + itemstack.func_77960_j();
   }

   @SideOnly(Side.CLIENT)
   public void func_77624_a(ItemStack itemstack, EntityPlayer entityplayer, List list, boolean flag) {
      int slots = getCapacity(itemstack);
      int slotsFull = 0;
      LOTRInventoryPouch pouchInv = new LOTRInventoryPouch(itemstack);

      for(int i = 0; i < pouchInv.func_70302_i_(); ++i) {
         ItemStack slotItem = pouchInv.func_70301_a(i);
         if (slotItem != null) {
            ++slotsFull;
         }
      }

      list.add(StatCollector.func_74837_a("item.lotr.pouch.slots", new Object[]{slotsFull, slots}));
      if (isPouchDyed(itemstack)) {
         list.add(StatCollector.func_74838_a("item.lotr.pouch.dyed"));
      }

   }

   @SideOnly(Side.CLIENT)
   public void func_150895_a(Item item, CreativeTabs tab, List list) {
      for(int i = 0; i < pouchTypes.length; ++i) {
         list.add(new ItemStack(item, 1, i));
      }

   }

   public static boolean tryAddItemToPouch(ItemStack pouch, ItemStack itemstack, boolean requireMatchInPouch) {
      if (itemstack != null && itemstack.field_77994_a > 0) {
         LOTRInventoryPouch pouchInv = new LOTRInventoryPouch(pouch);

         for(int i = 0; i < pouchInv.func_70302_i_() && itemstack.field_77994_a > 0; ++i) {
            ItemStack itemInSlot = pouchInv.func_70301_a(i);
            if (itemInSlot == null) {
               if (requireMatchInPouch) {
                  continue;
               }
            } else if (itemInSlot.field_77994_a >= itemInSlot.func_77976_d() || itemInSlot.func_77973_b() != itemstack.func_77973_b() || !itemInSlot.func_77985_e() || itemInSlot.func_77981_g() && itemInSlot.func_77960_j() != itemstack.func_77960_j() || !ItemStack.func_77970_a(itemInSlot, itemstack)) {
               continue;
            }

            if (itemInSlot == null) {
               pouchInv.func_70299_a(i, itemstack);
               return true;
            }

            int maxStackSize = itemInSlot.func_77976_d();
            if (pouchInv.func_70297_j_() < maxStackSize) {
               maxStackSize = pouchInv.func_70297_j_();
            }

            int difference = maxStackSize - itemInSlot.field_77994_a;
            if (difference > itemstack.field_77994_a) {
               difference = itemstack.field_77994_a;
            }

            itemstack.field_77994_a -= difference;
            itemInSlot.field_77994_a += difference;
            pouchInv.func_70299_a(i, itemInSlot);
            if (itemstack.field_77994_a <= 0) {
               return true;
            }
         }
      }

      return false;
   }

   public static boolean restockPouches(EntityPlayer player) {
      InventoryPlayer inv = player.field_71071_by;
      List pouchSlots = new ArrayList();
      List itemSlots = new ArrayList();

      for(int i = 0; i < inv.field_70462_a.length; ++i) {
         ItemStack itemstack = inv.func_70301_a(i);
         if (itemstack != null) {
            if (itemstack.func_77973_b() instanceof LOTRItemPouch) {
               pouchSlots.add(i);
            } else {
               itemSlots.add(i);
            }
         }
      }

      boolean movedAny = false;
      Iterator var13 = itemSlots.iterator();

      while(true) {
         while(var13.hasNext()) {
            int i = (Integer)var13.next();
            ItemStack itemstack = inv.func_70301_a(i);
            Iterator var8 = pouchSlots.iterator();

            while(var8.hasNext()) {
               int p = (Integer)var8.next();
               ItemStack pouch = inv.func_70301_a(p);
               int stackSizeBefore = itemstack.field_77994_a;
               tryAddItemToPouch(pouch, itemstack, true);
               if (itemstack.field_77994_a != stackSizeBefore) {
                  movedAny = true;
               }

               if (itemstack.field_77994_a <= 0) {
                  inv.func_70299_a(i, (ItemStack)null);
                  break;
               }
            }
         }

         return movedAny;
      }
   }
}
