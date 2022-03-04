package lotr.common.item;

import lotr.common.LOTRCreativeTabs;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTRItemSalt extends Item {
   public LOTRItemSalt() {
      this.func_77637_a(LOTRCreativeTabs.tabFood);
   }

   public boolean func_77648_a(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int side, float f, float f1, float f2) {
      if (!world.field_72995_K) {
         boolean changedAny = false;
         int range = 1 + world.field_73012_v.nextInt(3);
         int yRange = range / 2;

         for(int i1 = -range; i1 <= range; ++i1) {
            for(int j1 = -yRange; j1 <= yRange; ++j1) {
               for(int k1 = -range; k1 <= range; ++k1) {
                  int i2 = i + i1;
                  int j2 = j + j1;
                  int k2 = k + k1;
                  if (i1 * i1 + k1 * k1 <= range * range) {
                     Block block = world.func_147439_a(i2, j2, k2);
                     int meta = world.func_72805_g(i2, j2, k2);
                     Block newBlock = null;
                     int newMeta = 0;
                     if (block != Blocks.field_150349_c && (block != Blocks.field_150346_d || meta != 0) && block != Blocks.field_150458_ak) {
                        if (block == LOTRMod.mudGrass || block == LOTRMod.mud && meta == 0 || block == LOTRMod.mudFarmland) {
                           newBlock = LOTRMod.mud;
                           newMeta = 1;
                        }
                     } else {
                        newBlock = Blocks.field_150346_d;
                        newMeta = 1;
                     }

                     if (newBlock != null) {
                        if (i1 == 0 && j1 == 0 && k1 == 0 || world.field_73012_v.nextInt(3) != 0) {
                           world.func_147465_d(i2, j2, k2, newBlock, newMeta, 3);
                        }

                        changedAny = true;
                     }
                  }
               }
            }
         }

         if (changedAny) {
            --itemstack.field_77994_a;
            return true;
         }
      }

      return true;
   }
}
