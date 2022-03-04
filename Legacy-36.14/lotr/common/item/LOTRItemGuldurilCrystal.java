package lotr.common.item;

import lotr.common.LOTRCreativeTabs;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.block.LOTRBlockGuldurilBrick;
import lotr.common.fac.LOTRAlignmentValues;
import lotr.common.fac.LOTRFaction;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class LOTRItemGuldurilCrystal extends LOTRItemWithAnvilNameColor {
   public LOTRItemGuldurilCrystal() {
      super(EnumChatFormatting.DARK_GREEN);
      this.func_77637_a(LOTRCreativeTabs.tabMaterials);
   }

   public boolean func_77648_a(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int side, float f, float f1, float f2) {
      if (!entityplayer.func_82247_a(i, j, k, side, itemstack)) {
         return false;
      } else {
         int guldurilBrickMeta = LOTRBlockGuldurilBrick.guldurilMetaForBlock(world.func_147439_a(i, j, k), world.func_72805_g(i, j, k));
         boolean hasAlignment = LOTRLevelData.getData(entityplayer).getAlignment(LOTRFaction.MORDOR) >= 1.0F || LOTRLevelData.getData(entityplayer).getAlignment(LOTRFaction.ANGMAR) >= 1.0F || LOTRLevelData.getData(entityplayer).getAlignment(LOTRFaction.DOL_GULDUR) >= 1.0F;
         if (guldurilBrickMeta < 0) {
            if (world.func_147439_a(i, j, k) == LOTRMod.sapling && (world.func_72805_g(i, j, k) & 3) == 1 && LOTRLevelData.getData(entityplayer).getAlignment(LOTRFaction.FANGORN) < 0.0F) {
               world.func_147465_d(i, j, k, LOTRMod.corruptMallorn, 0, 3);
               --itemstack.field_77994_a;
               this.spawnCrystalParticles(world, i, j, k);
               return true;
            } else {
               return false;
            }
         } else {
            if (hasAlignment) {
               world.func_147465_d(i, j, k, LOTRMod.guldurilBrick, guldurilBrickMeta, 3);
               --itemstack.field_77994_a;
               this.spawnCrystalParticles(world, i, j, k);
            } else {
               for(int l = 0; l < 8; ++l) {
                  double d = (double)i - 0.25D + (double)world.field_73012_v.nextFloat() * 1.5D;
                  double d1 = (double)j - 0.25D + (double)world.field_73012_v.nextFloat() * 1.5D;
                  double d2 = (double)k - 0.25D + (double)world.field_73012_v.nextFloat() * 1.5D;
                  world.func_72869_a("smoke", d, d1, d2, 0.0D, 0.0D, 0.0D);
               }

               if (!world.field_72995_K) {
                  LOTRAlignmentValues.notifyAlignmentNotHighEnough(entityplayer, 1.0F, LOTRFaction.MORDOR, LOTRFaction.ANGMAR, LOTRFaction.DOL_GULDUR);
               }
            }

            return true;
         }
      }
   }

   private void spawnCrystalParticles(World world, int i, int j, int k) {
      for(int l = 0; l < 16; ++l) {
         double d = (double)i - 0.25D + (double)world.field_73012_v.nextFloat() * 1.5D;
         double d1 = (double)j - 0.25D + (double)world.field_73012_v.nextFloat() * 1.5D;
         double d2 = (double)k - 0.25D + (double)world.field_73012_v.nextFloat() * 1.5D;
         world.func_72869_a("iconcrack_" + Item.func_150891_b(this), d, d1, d2, 0.0D, 0.0D, 0.0D);
      }

   }
}
