package lotr.common.item;

import lotr.common.LOTRCreativeTabs;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.fac.LOTRAlignmentValues;
import lotr.common.fac.LOTRFaction;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class LOTRItemQuenditeCrystal extends LOTRItemWithAnvilNameColor {
   public LOTRItemQuenditeCrystal() {
      super(EnumChatFormatting.DARK_AQUA);
      this.func_77637_a(LOTRCreativeTabs.tabMaterials);
   }

   public boolean func_77648_a(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int side, float f, float f1, float f2) {
      if (!entityplayer.func_82247_a(i, j, k, side, itemstack)) {
         return false;
      } else if (world.func_147439_a(i, j, k) != Blocks.field_150349_c) {
         return false;
      } else {
         int l;
         if (LOTRLevelData.getData(entityplayer).getAlignment(LOTRFaction.LOTHLORIEN) < 1.0F && LOTRLevelData.getData(entityplayer).getAlignment(LOTRFaction.HIGH_ELF) < 1.0F) {
            for(l = 0; l < 8; ++l) {
               double d = (double)i + (double)world.field_73012_v.nextFloat();
               double d1 = (double)j + 1.0D;
               double d2 = (double)k + (double)world.field_73012_v.nextFloat();
               world.func_72869_a("smoke", d, d1, d2, 0.0D, 0.0D, 0.0D);
            }

            if (!world.field_72995_K) {
               LOTRAlignmentValues.notifyAlignmentNotHighEnough(entityplayer, 1.0F, LOTRFaction.LOTHLORIEN, LOTRFaction.HIGH_ELF);
            }
         } else {
            world.func_147465_d(i, j, k, LOTRMod.quenditeGrass, 0, 3);
            --itemstack.field_77994_a;

            for(l = 0; l < 8; ++l) {
               world.func_72869_a("iconcrack_" + Item.func_150891_b(this), (double)i + (double)world.field_73012_v.nextFloat(), (double)j + 1.5D, (double)k + (double)world.field_73012_v.nextFloat(), 0.0D, 0.0D, 0.0D);
            }
         }

         return true;
      }
   }
}
