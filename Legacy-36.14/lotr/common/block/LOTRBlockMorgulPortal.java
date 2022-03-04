package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Random;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.fac.LOTRFaction;
import lotr.common.tileentity.LOTRTileEntityMorgulPortal;
import lotr.common.world.LOTRTeleporterMorgulPortal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class LOTRBlockMorgulPortal extends LOTRBlockPortal {
   public LOTRBlockMorgulPortal() {
      super(new LOTRFaction[]{LOTRFaction.MORDOR, LOTRFaction.ANGMAR, LOTRFaction.DOL_GULDUR}, LOTRTeleporterMorgulPortal.class);
   }

   public TileEntity func_149915_a(World world, int i) {
      return new LOTRTileEntityMorgulPortal();
   }

   @SideOnly(Side.CLIENT)
   public void func_149734_b(World world, int i, int j, int k, Random random) {
      double d = (double)((float)i + random.nextFloat());
      double d1 = (double)((float)j + 0.8F);
      double d2 = (double)((float)k + random.nextFloat());
      double d3 = -0.05D + (double)random.nextFloat() * 0.1D;
      double d4 = 0.1D + (double)random.nextFloat() * 0.1D;
      double d5 = -0.05D + (double)random.nextFloat() * 0.1D;
      LOTRMod.proxy.spawnParticle("morgulPortal", d, d1, d2, d3, d4, d5);
   }

   public void setPlayerInPortal(EntityPlayer entityplayer) {
      LOTRMod.proxy.setInMorgulPortal(entityplayer);
      if (!entityplayer.field_70170_p.field_72995_K) {
         LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.useMorgulPortal);
      }

   }

   public boolean isValidPortalLocation(World world, int i, int j, int k, boolean portalAlreadyMade) {
      for(int i1 = i - 2; i1 <= i + 2; ++i1) {
         for(int k1 = k - 2; k1 <= k + 2; ++k1) {
            if (Math.abs(i1 - i) == 2 && Math.abs(k1 - k) == 2) {
               for(int j1 = j + 1; j1 <= j + 3; ++j1) {
                  if (world.func_147439_a(i1, j1, k1) != LOTRMod.guldurilBrick) {
                     return false;
                  }
               }
            } else if (Math.abs(i1 - i) != 2 && Math.abs(k1 - k) != 2) {
               if (world.func_147439_a(i1, j, k1) != (portalAlreadyMade ? LOTRMod.morgulPortal : Blocks.field_150353_l) || !LOTRMod.isOpaque(world, i1, j - 1, k1)) {
                  return false;
               }
            } else if (!LOTRMod.isOpaque(world, i1, j, k1)) {
               return false;
            }
         }
      }

      return true;
   }
}
