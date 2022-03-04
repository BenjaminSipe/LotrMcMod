package lotr.common.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import lotr.common.LOTRCommonProxy;
import lotr.common.LOTRCreativeTabs;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.LOTRPlayerData;
import lotr.common.quest.LOTRMiniQuestEvent;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class LOTRItemRedBook extends Item {
   @SideOnly(Side.CLIENT)
   public static IIcon questOfferIcon;

   public LOTRItemRedBook() {
      this.func_77625_d(1);
      this.func_77637_a(LOTRCreativeTabs.tabMisc);
   }

   public ItemStack func_77659_a(ItemStack itemstack, World world, EntityPlayer entityplayer) {
      LOTRCommonProxy var10002 = LOTRMod.proxy;
      entityplayer.openGui(LOTRMod.instance, 32, world, 0, 0, 0);
      if (!world.field_72995_K) {
         LOTRLevelData.getData(entityplayer).distributeMQEvent(new LOTRMiniQuestEvent.OpenRedBook());
      }

      return itemstack;
   }

   @SideOnly(Side.CLIENT)
   public void func_77624_a(ItemStack itemstack, EntityPlayer entityplayer, List list, boolean flag) {
      LOTRPlayerData playerData = LOTRLevelData.getData(entityplayer);
      int activeQuests = playerData.getActiveMiniQuests().size();
      list.add(StatCollector.func_74837_a("item.lotr.redBook.activeQuests", new Object[]{activeQuests}));
   }

   @SideOnly(Side.CLIENT)
   public void func_94581_a(IIconRegister iconregister) {
      super.func_94581_a(iconregister);
      questOfferIcon = iconregister.func_94245_a("lotr:questOffer");
   }
}
