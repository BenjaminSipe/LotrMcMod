package lotr.common.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRBannerProtection;
import lotr.common.LOTRConfig;
import lotr.common.LOTRCreativeTabs;
import lotr.common.LOTRLevelData;
import lotr.common.entity.item.LOTREntityBanner;
import lotr.common.entity.item.LOTREntityBannerWall;
import lotr.common.fac.LOTRAlignmentValues;
import lotr.common.fac.LOTRFaction;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.Direction;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class LOTRItemBanner extends Item {
   @SideOnly(Side.CLIENT)
   private IIcon[] bannerIcons;

   public LOTRItemBanner() {
      this.func_77637_a(LOTRCreativeTabs.tabDeco);
      this.func_77625_d(16);
      this.func_77656_e(0);
      this.func_77627_a(true);
      this.func_77664_n();
   }

   public static LOTRItemBanner.BannerType getBannerType(ItemStack itemstack) {
      return itemstack.func_77973_b() instanceof LOTRItemBanner ? getBannerType(itemstack.func_77960_j()) : null;
   }

   public static LOTRItemBanner.BannerType getBannerType(int i) {
      return LOTRItemBanner.BannerType.forID(i);
   }

   public static NBTTagCompound getProtectionData(ItemStack itemstack) {
      if (itemstack.func_77978_p() != null && itemstack.func_77978_p().func_74764_b("LOTRBannerData")) {
         NBTTagCompound data = itemstack.func_77978_p().func_74775_l("LOTRBannerData");
         return data;
      } else {
         return null;
      }
   }

   public static void setProtectionData(ItemStack itemstack, NBTTagCompound data) {
      if (data == null) {
         if (itemstack.func_77978_p() != null) {
            itemstack.func_77978_p().func_82580_o("LOTRBannerData");
         }
      } else {
         if (itemstack.func_77978_p() == null) {
            itemstack.func_77982_d(new NBTTagCompound());
         }

         itemstack.func_77978_p().func_74782_a("LOTRBannerData", data);
      }

   }

   @SideOnly(Side.CLIENT)
   public IIcon func_77617_a(int i) {
      if (i >= this.bannerIcons.length) {
         i = 0;
      }

      return this.bannerIcons[i];
   }

   @SideOnly(Side.CLIENT)
   public void func_94581_a(IIconRegister iconregister) {
      this.bannerIcons = new IIcon[LOTRItemBanner.BannerType.bannerTypes.size()];

      for(int i = 0; i < this.bannerIcons.length; ++i) {
         this.bannerIcons[i] = iconregister.func_94245_a(this.func_111208_A() + "_" + ((LOTRItemBanner.BannerType)LOTRItemBanner.BannerType.bannerTypes.get(i)).bannerName);
      }

   }

   public String func_77667_c(ItemStack itemstack) {
      return super.func_77658_a() + "." + getBannerType(itemstack).bannerName;
   }

   @SideOnly(Side.CLIENT)
   public void func_77624_a(ItemStack itemstack, EntityPlayer entityplayer, List list, boolean flag) {
      NBTTagCompound protectData = getProtectionData(itemstack);
      if (protectData != null) {
         list.add(StatCollector.func_74838_a("item.lotr.banner.protect"));
      }

   }

   public boolean func_77648_a(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int side, float f, float f1, float f2) {
      LOTRItemBanner.BannerType bannerType = getBannerType(itemstack);
      NBTTagCompound protectData = getProtectionData(itemstack);
      if (world.func_147439_a(i, j, k).isReplaceable(world, i, j, k)) {
         side = 1;
      } else if (side == 1) {
         ++j;
      }

      if (side == 0) {
         return false;
      } else {
         if (side == 1) {
            if (!entityplayer.func_82247_a(i, j, k, side, itemstack)) {
               return false;
            }

            Block block = world.func_147439_a(i, j - 1, k);
            int meta = world.func_72805_g(i, j - 1, k);
            if (block.isSideSolid(world, i, j - 1, k, ForgeDirection.UP)) {
               if (LOTRConfig.allowBannerProtection && !entityplayer.field_71075_bZ.field_75098_d) {
                  int protectRange = LOTRBannerProtection.getProtectionRange(block, meta);
                  if (protectRange > 0) {
                     LOTRFaction faction = bannerType.faction;
                     if (LOTRLevelData.getData(entityplayer).getAlignment(faction) < 1.0F) {
                        if (!world.field_72995_K) {
                           LOTRAlignmentValues.notifyAlignmentNotHighEnough(entityplayer, 1.0F, faction);
                        }

                        return false;
                     }

                     if (!world.field_72995_K && LOTRBannerProtection.isProtected(world, i, j, k, LOTRBannerProtection.forPlayer(entityplayer, LOTRBannerProtection.Permission.FULL), false, (double)protectRange)) {
                        entityplayer.func_145747_a(new ChatComponentTranslation("chat.lotr.alreadyProtected", new Object[0]));
                        return false;
                     }
                  }
               }

               if (!world.field_72995_K) {
                  LOTREntityBanner banner = new LOTREntityBanner(world);
                  banner.func_70012_b((double)i + 0.5D, (double)j, (double)k + 0.5D, 90.0F * (float)(MathHelper.func_76128_c((double)(entityplayer.field_70177_z * 4.0F / 360.0F) + 0.5D) & 3), 0.0F);
                  if (world.func_72855_b(banner.field_70121_D) && world.func_72945_a(banner, banner.field_70121_D).size() == 0 && !world.func_72953_d(banner.field_70121_D) && world.func_72872_a(LOTREntityBanner.class, banner.field_70121_D).isEmpty()) {
                     banner.setBannerType(bannerType);
                     if (protectData != null) {
                        banner.readProtectionFromNBT(protectData);
                     }

                     if (banner.getPlacingPlayer() == null || !shouldKeepOriginalOwnerOnPlacement(entityplayer, itemstack)) {
                        banner.setPlacingPlayer(entityplayer);
                     }

                     world.func_72838_d(banner);
                     if (banner.isProtectingTerritory()) {
                        LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.bannerProtect);
                     }

                     world.func_72956_a(banner, Blocks.field_150344_f.field_149762_H.func_150496_b(), (Blocks.field_150344_f.field_149762_H.func_150497_c() + 1.0F) / 2.0F, Blocks.field_150344_f.field_149762_H.func_150494_d() * 0.8F);
                     --itemstack.field_77994_a;
                     return true;
                  }

                  banner.func_70106_y();
               }
            }
         } else {
            if (!entityplayer.func_82247_a(i, j, k, side, itemstack)) {
               return false;
            }

            if (!world.field_72995_K) {
               int l = Direction.field_71579_d[side];
               LOTREntityBannerWall banner = new LOTREntityBannerWall(world, i, j, k, l);
               if (banner.func_70518_d()) {
                  banner.setBannerType(bannerType);
                  if (protectData != null) {
                     banner.setProtectData((NBTTagCompound)protectData.func_74737_b());
                  }

                  world.func_72838_d(banner);
                  world.func_72956_a(banner, Blocks.field_150344_f.field_149762_H.func_150496_b(), (Blocks.field_150344_f.field_149762_H.func_150497_c() + 1.0F) / 2.0F, Blocks.field_150344_f.field_149762_H.func_150494_d() * 0.8F);
                  --itemstack.field_77994_a;
                  return true;
               }

               banner.func_70106_y();
            }
         }

         return false;
      }
   }

   public static boolean shouldKeepOriginalOwnerOnPlacement(EntityPlayer entityplayer, ItemStack bannerItem) {
      return hasChoiceToKeepOriginalOwner(entityplayer) && entityplayer.func_70093_af();
   }

   public static boolean hasChoiceToKeepOriginalOwner(EntityPlayer entityplayer) {
      return entityplayer.field_71075_bZ.field_75098_d;
   }

   public static boolean isHoldingBannerWithExistingProtection(EntityPlayer entityplayer) {
      ItemStack itemstack = entityplayer.func_70694_bm();
      if (itemstack != null && itemstack.func_77973_b() instanceof LOTRItemBanner) {
         NBTTagCompound protectData = getProtectionData(itemstack);
         return protectData != null && !protectData.func_82582_d();
      } else {
         return false;
      }
   }

   @SideOnly(Side.CLIENT)
   public void func_150895_a(Item item, CreativeTabs tab, List list) {
      Iterator var4 = LOTRItemBanner.BannerType.bannerTypes.iterator();

      while(var4.hasNext()) {
         LOTRItemBanner.BannerType type = (LOTRItemBanner.BannerType)var4.next();
         list.add(new ItemStack(item, 1, type.bannerID));
      }

   }

   public static enum BannerType {
      GONDOR(0, "gondor", LOTRFaction.GONDOR),
      ROHAN(1, "rohan", LOTRFaction.ROHAN),
      MORDOR(2, "mordor", LOTRFaction.MORDOR),
      GALADHRIM(3, "lothlorien", LOTRFaction.LOTHLORIEN),
      WOOD_ELF(4, "mirkwood", LOTRFaction.WOOD_ELF),
      DUNLAND(5, "dunland", LOTRFaction.DUNLAND),
      ISENGARD(6, "isengard", LOTRFaction.ISENGARD),
      DWARF(7, "durin", LOTRFaction.DURINS_FOLK),
      ANGMAR(8, "angmar", LOTRFaction.ANGMAR),
      NEAR_HARAD(9, "nearHarad", LOTRFaction.NEAR_HARAD),
      HIGH_ELF(10, "highElf", LOTRFaction.HIGH_ELF),
      BLUE_MOUNTAINS(11, "blueMountains", LOTRFaction.BLUE_MOUNTAINS),
      RANGER_NORTH(12, "ranger", LOTRFaction.RANGER_NORTH),
      DOL_GULDUR(13, "dolGuldur", LOTRFaction.DOL_GULDUR),
      GUNDABAD(14, "gundabad", LOTRFaction.GUNDABAD),
      HALF_TROLL(15, "halfTroll", LOTRFaction.HALF_TROLL),
      DOL_AMROTH(16, "dolAmroth", LOTRFaction.GONDOR),
      MOREDAIN(17, "moredain", LOTRFaction.MORWAITH),
      TAUREDAIN(18, "tauredain", LOTRFaction.TAURETHRIM),
      DALE(19, "dale", LOTRFaction.DALE),
      DORWINION(20, "dorwinion", LOTRFaction.DORWINION),
      HOBBIT(21, "hobbit", LOTRFaction.HOBBIT),
      ANORIEN(22, "anorien", LOTRFaction.GONDOR),
      ITHILIEN(23, "ithilien", LOTRFaction.GONDOR),
      LOSSARNACH(24, "lossarnach", LOTRFaction.GONDOR),
      LEBENNIN(25, "lebennin", LOTRFaction.GONDOR),
      PELARGIR(26, "pelargir", LOTRFaction.GONDOR),
      BLACKROOT_VALE(27, "blackrootVale", LOTRFaction.GONDOR),
      PINNATH_GELIN(28, "pinnathGelin", LOTRFaction.GONDOR),
      MINAS_MORGUL(29, "minasMorgul", LOTRFaction.MORDOR),
      BLACK_URUK(30, "blackUruk", LOTRFaction.MORDOR),
      GONDOR_STEWARD(31, "gondorSteward", LOTRFaction.GONDOR),
      NAN_UNGOL(32, "nanUngol", LOTRFaction.MORDOR),
      RHUDAUR(33, "rhudaur", LOTRFaction.ANGMAR),
      LAMEDON(34, "lamedon", LOTRFaction.GONDOR),
      RHUN(35, "rhun", LOTRFaction.RHUDEL),
      RIVENDELL(36, "rivendell", LOTRFaction.HIGH_ELF),
      ESGAROTH(37, "esgaroth", LOTRFaction.DALE),
      UMBAR(38, "umbar", LOTRFaction.NEAR_HARAD),
      HARAD_NOMAD(39, "haradNomad", LOTRFaction.NEAR_HARAD),
      HARAD_GULF(40, "haradGulf", LOTRFaction.NEAR_HARAD),
      BREE(41, "bree", LOTRFaction.BREE);

      public static List bannerTypes = new ArrayList();
      private static Map bannerForID = new HashMap();
      public final int bannerID;
      public final String bannerName;
      public final LOTRFaction faction;

      private BannerType(int i, String s, LOTRFaction f) {
         this.bannerID = i;
         this.bannerName = s;
         this.faction = f;
         this.faction.factionBanners.add(this);
      }

      public static LOTRItemBanner.BannerType forID(int ID) {
         return (LOTRItemBanner.BannerType)bannerForID.get(ID);
      }

      static {
         LOTRItemBanner.BannerType[] var0 = values();
         int var1 = var0.length;

         for(int var2 = 0; var2 < var1; ++var2) {
            LOTRItemBanner.BannerType t = var0[var2];
            bannerTypes.add(t);
            bannerForID.put(t.bannerID, t);
         }

      }
   }
}
