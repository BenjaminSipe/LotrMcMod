package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Random;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.fac.LOTRFaction;
import lotr.common.tileentity.LOTRTileEntityElvenPortal;
import lotr.common.world.LOTRTeleporterElvenPortal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class LOTRBlockElvenPortal extends LOTRBlockPortal {
   public LOTRBlockElvenPortal() {
      super(new LOTRFaction[]{LOTRFaction.LOTHLORIEN, LOTRFaction.HIGH_ELF}, LOTRTeleporterElvenPortal.class);
   }

   public TileEntity func_149915_a(World world, int i) {
      return new LOTRTileEntityElvenPortal();
   }

   @SideOnly(Side.CLIENT)
   public void func_149734_b(World world, int i, int j, int k, Random random) {
      if (random.nextInt(3) == 0) {
         double d = (double)((float)i + random.nextFloat());
         double d1 = (double)j + 0.8D;
         double d2 = (double)((float)k + random.nextFloat());
         LOTRMod.proxy.spawnParticle("elvenGlow", d, d1, d2, 0.0D, 0.3D, 0.0D);
      }

      super.func_149734_b(world, i, j, k, random);
   }

   public void setPlayerInPortal(EntityPlayer entityplayer) {
      LOTRMod.proxy.setInElvenPortal(entityplayer);
      if (!entityplayer.field_70170_p.field_72995_K) {
         LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.useElvenPortal);
      }

   }

   public boolean isValidPortalLocation(World world, int i, int j, int k, boolean portalAlreadyMade) {
      for(int i1 = i - 2; i1 <= i + 2; ++i1) {
         for(int k1 = k - 2; k1 <= k + 2; ++k1) {
            if (Math.abs(i1 - i) != 2 || Math.abs(k1 - k) != 2) {
               if (Math.abs(i1 - i) != 2 && Math.abs(k1 - k) != 2) {
                  if (world.func_147439_a(i1, j, k1) != (portalAlreadyMade ? LOTRMod.elvenPortal : Blocks.field_150355_j) || !LOTRMod.isOpaque(world, i1, j - 1, k1)) {
                     return false;
                  }
               } else if (world.func_147439_a(i1, j, k1) != LOTRMod.quenditeGrass) {
                  return false;
               }
            }
         }
      }

      return true;
   }
}
