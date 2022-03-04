package lotr.common.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import lotr.common.LOTRCreativeTabs;
import lotr.common.entity.item.LOTREntityStoneTroll;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class LOTRItemTrollStatue extends Item {
   public LOTRItemTrollStatue() {
      this.func_77627_a(true);
      this.func_77656_e(0);
      this.func_77625_d(1);
      this.func_77637_a(LOTRCreativeTabs.tabDeco);
   }

   public boolean func_77648_a(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l, float f, float f1, float f2) {
      Block block = world.func_147439_a(i, j, k);
      if (block == Blocks.field_150431_aC) {
         l = 1;
      } else if (!block.isReplaceable(world, i, j, k)) {
         if (l == 0) {
            --j;
         }

         if (l == 1) {
            ++j;
         }

         if (l == 2) {
            --k;
         }

         if (l == 3) {
            ++k;
         }

         if (l == 4) {
            --i;
         }

         if (l == 5) {
            ++i;
         }
      }

      if (!entityplayer.func_82247_a(i, j, k, l, itemstack)) {
         return false;
      } else {
         if (world.func_147439_a(i, j - 1, k).isSideSolid(world, i, j - 1, k, ForgeDirection.UP) && !world.field_72995_K) {
            LOTREntityStoneTroll trollStatue = new LOTREntityStoneTroll(world);
            trollStatue.func_70012_b((double)i + (double)f, (double)j, (double)k + (double)f2, 180.0F - entityplayer.field_70177_z % 360.0F, 0.0F);
            if (world.func_72855_b(trollStatue.field_70121_D) && world.func_72945_a(trollStatue, trollStatue.field_70121_D).size() == 0 && !world.func_72953_d(trollStatue.field_70121_D)) {
               trollStatue.setTrollOutfit(itemstack.func_77960_j());
               if (itemstack.func_77942_o()) {
                  trollStatue.setHasTwoHeads(itemstack.func_77978_p().func_74767_n("TwoHeads"));
               }

               trollStatue.placedByPlayer = true;
               world.func_72838_d(trollStatue);
               world.func_72956_a(trollStatue, Blocks.field_150348_b.field_149762_H.func_150496_b(), (Blocks.field_150348_b.field_149762_H.func_150497_c() + 1.0F) / 2.0F, Blocks.field_150348_b.field_149762_H.func_150494_d() * 0.8F);
               --itemstack.field_77994_a;
               return true;
            }

            trollStatue.func_70106_y();
         }

         return false;
      }
   }

   public String func_77667_c(ItemStack itemstack) {
      return super.func_77658_a() + "." + itemstack.func_77960_j();
   }

   @SideOnly(Side.CLIENT)
   public void func_77624_a(ItemStack itemstack, EntityPlayer entityplayer, List list, boolean flag) {
      if (itemstack.func_77942_o()) {
         boolean twoHeads = itemstack.func_77978_p().func_74767_n("TwoHeads");
         if (twoHeads) {
            list.add(StatCollector.func_74838_a("item.lotr.trollStatue.twoHeads"));
         }
      }

   }

   @SideOnly(Side.CLIENT)
   public void func_150895_a(Item item, CreativeTabs tab, List list) {
      for(int j = 0; j <= 2; ++j) {
         ItemStack statue = new ItemStack(item, 1, j);
         list.add(statue);
         statue = statue.func_77946_l();
         statue.func_77982_d(new NBTTagCompound());
         statue.func_77978_p().func_74757_a("TwoHeads", true);
         list.add(statue);
      }

   }
}
