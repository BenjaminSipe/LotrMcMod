package lotr.common.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRCreativeTabs;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.entity.projectile.LOTREntitySmokeRing;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class LOTRItemHobbitPipe extends Item {
   public static final int MAGIC_COLOR = 16;

   public LOTRItemHobbitPipe() {
      this.func_77656_e(300);
      this.func_77625_d(1);
      this.func_77637_a(LOTRCreativeTabs.tabMisc);
   }

   public ItemStack func_77654_b(ItemStack itemstack, World world, EntityPlayer entityplayer) {
      if (entityplayer.field_71071_by.func_146028_b(LOTRMod.pipeweed) || entityplayer.field_71075_bZ.field_75098_d) {
         itemstack.func_77972_a(1, entityplayer);
         if (!entityplayer.field_71075_bZ.field_75098_d) {
            entityplayer.field_71071_by.func_146026_a(LOTRMod.pipeweed);
         }

         if (entityplayer.func_71043_e(false)) {
            entityplayer.func_71024_bL().func_75122_a(2, 0.3F);
         }

         if (!world.field_72995_K) {
            LOTREntitySmokeRing smoke = new LOTREntitySmokeRing(world, entityplayer);
            int color = getSmokeColor(itemstack);
            smoke.setSmokeColour(color);
            world.func_72838_d(smoke);
            if (color == 16) {
               LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.useMagicPipe);
            }
         }

         world.func_72956_a(entityplayer, "lotr:item.puff", 1.0F, (field_77697_d.nextFloat() - field_77697_d.nextFloat()) * 0.2F + 1.0F);
      }

      return itemstack;
   }

   public int func_77626_a(ItemStack itemstack) {
      return 40;
   }

   public EnumAction func_77661_b(ItemStack itemstack) {
      return EnumAction.bow;
   }

   public ItemStack func_77659_a(ItemStack itemstack, World world, EntityPlayer entityplayer) {
      if (entityplayer.field_71071_by.func_146028_b(LOTRMod.pipeweed) || entityplayer.field_71075_bZ.field_75098_d) {
         entityplayer.func_71008_a(itemstack, this.func_77626_a(itemstack));
      }

      return itemstack;
   }

   public static int getSmokeColor(ItemStack itemstack) {
      return itemstack.func_77978_p() != null && itemstack.func_77978_p().func_74764_b("SmokeColour") ? itemstack.func_77978_p().func_74762_e("SmokeColour") : 0;
   }

   public static void setSmokeColor(ItemStack itemstack, int i) {
      if (itemstack.func_77978_p() == null) {
         itemstack.func_77982_d(new NBTTagCompound());
      }

      itemstack.func_77978_p().func_74768_a("SmokeColour", i);
   }

   public static boolean isPipeDyed(ItemStack itemstack) {
      int color = getSmokeColor(itemstack);
      return color != 0 && color != 16;
   }

   public static void removePipeDye(ItemStack itemstack) {
      setSmokeColor(itemstack, 0);
   }

   @SideOnly(Side.CLIENT)
   public void func_77624_a(ItemStack itemstack, EntityPlayer entityplayer, List list, boolean flag) {
      int color = getSmokeColor(itemstack);
      list.add(StatCollector.func_74838_a(this.func_77658_a() + ".subtitle." + color));
   }

   @SideOnly(Side.CLIENT)
   public void func_150895_a(Item item, CreativeTabs tab, List list) {
      for(int i = 0; i <= 16; ++i) {
         ItemStack itemstack = new ItemStack(this);
         setSmokeColor(itemstack, i);
         list.add(itemstack);
      }

   }
}
