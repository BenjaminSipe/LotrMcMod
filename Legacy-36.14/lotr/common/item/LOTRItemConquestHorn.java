package lotr.common.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import lotr.common.LOTRBannerProtection;
import lotr.common.LOTRCreativeTabs;
import lotr.common.LOTRDimension;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.entity.LOTREntityInvasionSpawner;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.fac.LOTRAlignmentValues;
import lotr.common.fac.LOTRFaction;
import lotr.common.world.spawning.LOTRInvasions;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class LOTRItemConquestHorn extends Item {
   @SideOnly(Side.CLIENT)
   private IIcon baseIcon;
   @SideOnly(Side.CLIENT)
   private IIcon overlayIcon;

   public LOTRItemConquestHorn() {
      this.func_77625_d(1);
      this.func_77637_a(LOTRCreativeTabs.tabCombat);
   }

   public static LOTRInvasions getInvasionType(ItemStack itemstack) {
      LOTRInvasions invasionType = null;
      String s;
      if (itemstack.func_77978_p() != null && itemstack.func_77978_p().func_74764_b("InvasionType")) {
         s = itemstack.func_77978_p().func_74779_i("InvasionType");
         invasionType = LOTRInvasions.forName(s);
      }

      if (invasionType == null && itemstack.func_77978_p() != null && itemstack.func_77978_p().func_74764_b("HornFaction")) {
         s = itemstack.func_77978_p().func_74779_i("HornFaction");
         invasionType = LOTRInvasions.forName(s);
      }

      if (invasionType == null) {
         invasionType = LOTRInvasions.HOBBIT;
      }

      return invasionType;
   }

   public static void setInvasionType(ItemStack itemstack, LOTRInvasions type) {
      if (itemstack.func_77978_p() == null) {
         itemstack.func_77982_d(new NBTTagCompound());
      }

      itemstack.func_77978_p().func_74778_a("InvasionType", type.codeName());
   }

   public static ItemStack createHorn(LOTRInvasions type) {
      ItemStack itemstack = new ItemStack(LOTRMod.conquestHorn);
      setInvasionType(itemstack, type);
      return itemstack;
   }

   private boolean canUseHorn(ItemStack itemstack, World world, EntityPlayer entityplayer, boolean sendMessage) {
      if (LOTRDimension.getCurrentDimensionWithFallback(world) == LOTRDimension.UTUMNO) {
         if (sendMessage && !world.field_72995_K) {
            entityplayer.func_145747_a(new ChatComponentTranslation("chat.lotr.conquestHornProtected", new Object[0]));
         }

         return false;
      } else {
         LOTRInvasions invasionType = getInvasionType(itemstack);
         LOTRFaction invasionFaction = invasionType.invasionFaction;
         float alignmentRequired = 1500.0F;
         if (LOTRLevelData.getData(entityplayer).getAlignment(invasionFaction) >= alignmentRequired) {
            boolean blocked = false;
            if (LOTRBannerProtection.isProtected(world, entityplayer, LOTRBannerProtection.forFaction(invasionFaction), false)) {
               blocked = true;
            }

            if (LOTREntityNPCRespawner.isSpawnBlocked(entityplayer, invasionFaction)) {
               blocked = true;
            }

            if (blocked) {
               if (sendMessage && !world.field_72995_K) {
                  entityplayer.func_145747_a(new ChatComponentTranslation("chat.lotr.conquestHornProtected", new Object[]{invasionFaction.factionName()}));
               }

               return false;
            } else {
               return true;
            }
         } else {
            if (sendMessage && !world.field_72995_K) {
               LOTRAlignmentValues.notifyAlignmentNotHighEnough(entityplayer, alignmentRequired, invasionType.invasionFaction);
            }

            return false;
         }
      }
   }

   public ItemStack func_77659_a(ItemStack itemstack, World world, EntityPlayer entityplayer) {
      this.canUseHorn(itemstack, world, entityplayer, false);
      entityplayer.func_71008_a(itemstack, this.func_77626_a(itemstack));
      return itemstack;
   }

   public ItemStack func_77654_b(ItemStack itemstack, World world, EntityPlayer entityplayer) {
      LOTRInvasions invasionType = getInvasionType(itemstack);
      if (this.canUseHorn(itemstack, world, entityplayer, true)) {
         if (!world.field_72995_K) {
            LOTREntityInvasionSpawner invasion = new LOTREntityInvasionSpawner(world);
            invasion.setInvasionType(invasionType);
            invasion.isWarhorn = true;
            invasion.spawnsPersistent = true;
            invasion.func_70012_b(entityplayer.field_70165_t, entityplayer.field_70163_u + 3.0D, entityplayer.field_70161_v, 0.0F, 0.0F);
            world.func_72838_d(invasion);
            invasion.startInvasion(entityplayer);
         }

         if (!entityplayer.field_71075_bZ.field_75098_d) {
            --itemstack.field_77994_a;
         }

         return itemstack;
      } else {
         return itemstack;
      }
   }

   public int func_77626_a(ItemStack itemstack) {
      return 40;
   }

   public EnumAction func_77661_b(ItemStack itemstack) {
      return EnumAction.bow;
   }

   @SideOnly(Side.CLIENT)
   public void func_94581_a(IIconRegister iconregister) {
      this.baseIcon = iconregister.func_94245_a(this.func_111208_A() + "_base");
      this.overlayIcon = iconregister.func_94245_a(this.func_111208_A() + "_overlay");
   }

   @SideOnly(Side.CLIENT)
   public boolean func_77623_v() {
      return true;
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_77618_c(int i, int pass) {
      return pass > 0 ? this.overlayIcon : this.baseIcon;
   }

   @SideOnly(Side.CLIENT)
   public int func_82790_a(ItemStack itemstack, int pass) {
      if (pass == 0) {
         LOTRFaction faction = getInvasionType(itemstack).invasionFaction;
         return faction.getFactionColor();
      } else {
         return 16777215;
      }
   }

   public String func_77653_i(ItemStack itemstack) {
      LOTRInvasions type = getInvasionType(itemstack);
      return type != null ? StatCollector.func_74838_a(type.codeNameHorn()) : super.func_77653_i(itemstack);
   }

   @SideOnly(Side.CLIENT)
   public void func_77624_a(ItemStack itemstack, EntityPlayer entityplayer, List list, boolean flag) {
      LOTRInvasions type = getInvasionType(itemstack);
      list.add(type.invasionName());
   }

   @SideOnly(Side.CLIENT)
   public void func_150895_a(Item item, CreativeTabs tab, List list) {
      LOTRInvasions[] var4 = LOTRInvasions.values();
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         LOTRInvasions type = var4[var6];
         ItemStack itemstack = new ItemStack(item);
         setInvasionType(itemstack, type);
         list.add(itemstack);
      }

   }
}
