package lotr.common.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.UUID;
import lotr.common.LOTRCreativeTabs;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.tileentity.LOTRTileEntityForgeBase;
import lotr.common.tileentity.LOTRTileEntityHobbitOven;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import org.apache.commons.lang3.StringUtils;

public class LOTRItemBrandingIron extends Item {
   @SideOnly(Side.CLIENT)
   private IIcon iconCool;
   @SideOnly(Side.CLIENT)
   private IIcon iconHot;
   private static final int HEAT_USES = 5;

   public LOTRItemBrandingIron() {
      this.func_77637_a(LOTRCreativeTabs.tabTools);
      this.func_77625_d(1);
      this.func_77656_e(100);
      this.func_77664_n();
   }

   private static boolean isHeated(ItemStack itemstack) {
      return itemstack.func_77942_o() ? itemstack.func_77978_p().func_74767_n("HotIron") : false;
   }

   private static void setHeated(ItemStack itemstack, boolean flag) {
      if (!itemstack.func_77942_o()) {
         itemstack.func_77982_d(new NBTTagCompound());
      }

      itemstack.func_77978_p().func_74757_a("HotIron", flag);
   }

   public static String trimAcceptableBrandName(String s) {
      s = StringUtils.trim(s);
      int maxLength = 64;
      if (s.length() > maxLength) {
         s = s.substring(0, maxLength);
      }

      return s;
   }

   public static String getBrandName(ItemStack itemstack) {
      if (itemstack.func_77942_o()) {
         String s = itemstack.func_77978_p().func_74779_i("BrandName");
         if (!StringUtils.isBlank(s)) {
            return s;
         }
      }

      return null;
   }

   public static boolean hasBrandName(ItemStack itemstack) {
      return getBrandName(itemstack) != null;
   }

   public static void setBrandName(ItemStack itemstack, String s) {
      if (!itemstack.func_77942_o()) {
         itemstack.func_77982_d(new NBTTagCompound());
      }

      itemstack.func_77978_p().func_74778_a("BrandName", s);
   }

   public ItemStack func_77659_a(ItemStack itemstack, World world, EntityPlayer entityplayer) {
      if (!hasBrandName(itemstack)) {
         entityplayer.openGui(LOTRMod.instance, 61, world, 0, 0, 0);
      }

      return itemstack;
   }

   public boolean func_111207_a(ItemStack itemstack, EntityPlayer entityplayer, EntityLivingBase entity) {
      if (isHeated(itemstack) && hasBrandName(itemstack)) {
         String brandName = getBrandName(itemstack);
         if (entity instanceof EntityLiving) {
            EntityLiving entityliving = (EntityLiving)entity;
            boolean acceptableEntity = false;
            if (entityliving instanceof EntityAnimal) {
               acceptableEntity = true;
            } else if (entityliving instanceof LOTREntityNPC && ((LOTREntityNPC)entityliving).canRenameNPC()) {
               acceptableEntity = true;
            }

            if (acceptableEntity && !entityliving.func_94057_bL().equals(brandName)) {
               entityliving.func_94058_c(brandName);
               entityliving.func_110163_bv();
               entityliving.func_70642_aH();
               entityliving.func_70683_ar().func_75660_a();
               World world = entityliving.field_70170_p;
               world.func_72956_a(entityliving, "random.fizz", 0.5F, 2.6F + (world.field_73012_v.nextFloat() - world.field_73012_v.nextFloat()) * 0.8F);
               entityplayer.func_71038_i();
               int preDamage = itemstack.func_77960_j();
               itemstack.func_77972_a(1, entityplayer);
               int newDamage = itemstack.func_77960_j();
               if (preDamage / 5 != newDamage / 5) {
                  setHeated(itemstack, false);
               }

               if (!world.field_72995_K) {
                  setBrandingPlayer(entityliving, entityplayer.func_110124_au());
               }

               return true;
            }
         }
      }

      return false;
   }

   public boolean func_77648_a(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int side, float f, float f1, float f2) {
      if (hasBrandName(itemstack) && !isHeated(itemstack)) {
         boolean isHotBlock = false;
         TileEntity te = world.func_147438_o(i, j, k);
         if (te instanceof TileEntityFurnace && ((TileEntityFurnace)te).func_145950_i()) {
            isHotBlock = true;
         } else if (te instanceof LOTRTileEntityForgeBase && ((LOTRTileEntityForgeBase)te).isSmelting()) {
            isHotBlock = true;
         } else if (te instanceof LOTRTileEntityHobbitOven && ((LOTRTileEntityHobbitOven)te).isCooking()) {
            isHotBlock = true;
         }

         if (!isHotBlock) {
            ForgeDirection dir = ForgeDirection.getOrientation(side);
            Block block = world.func_147439_a(i + dir.offsetX, j + dir.offsetY, k + dir.offsetZ);
            if (block.func_149688_o() == Material.field_151581_o) {
               isHotBlock = true;
            }
         }

         if (isHotBlock) {
            setHeated(itemstack, true);
            return true;
         }
      }

      return false;
   }

   public boolean func_82789_a(ItemStack itemstack, ItemStack repairItem) {
      return repairItem.func_77973_b() == Items.field_151042_j;
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_77650_f(ItemStack itemstack) {
      return this.getIcon(itemstack, 0);
   }

   public IIcon getIcon(ItemStack itemstack, int pass) {
      return isHeated(itemstack) ? this.iconHot : this.iconCool;
   }

   @SideOnly(Side.CLIENT)
   public void func_94581_a(IIconRegister iconregister) {
      this.iconCool = iconregister.func_94245_a(this.func_111208_A());
      this.iconHot = iconregister.func_94245_a(this.func_111208_A() + "_hot");
   }

   public String func_77653_i(ItemStack itemstack) {
      String name = super.func_77653_i(itemstack);
      if (hasBrandName(itemstack)) {
         String brandName = getBrandName(itemstack);
         name = StatCollector.func_74837_a("item.lotr.brandingIron.named", new Object[]{name, brandName});
      } else {
         name = StatCollector.func_74837_a("item.lotr.brandingIron.unnamed", new Object[]{name});
      }

      return name;
   }

   public static UUID getBrandingPlayer(Entity entity) {
      NBTTagCompound nbt = entity.getEntityData();
      if (nbt.func_74764_b("LOTRBrander")) {
         String s = nbt.func_74779_i("LOTRBrander");
         return UUID.fromString(s);
      } else {
         return null;
      }
   }

   public static void setBrandingPlayer(Entity entity, UUID player) {
      String s = player.toString();
      entity.getEntityData().func_74778_a("LOTRBrander", s);
   }
}
