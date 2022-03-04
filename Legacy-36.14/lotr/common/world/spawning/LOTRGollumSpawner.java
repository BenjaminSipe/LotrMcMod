package lotr.common.world.spawning;

import lotr.common.LOTRLevelData;
import lotr.common.entity.npc.LOTREntityGollum;
import lotr.common.world.map.LOTRWaypoint;
import net.minecraft.block.Block;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTRGollumSpawner {
   public static void performSpawning(World world) {
      if (!LOTRLevelData.gollumSpawned()) {
         if (world.func_82737_E() % 20L == 0L) {
            LOTRWaypoint home = LOTRWaypoint.HIGH_PASS;
            int x = home.getXCoord();
            int z = home.getZCoord();
            int homeRange = 128;
            int i = MathHelper.func_76136_a(world.field_73012_v, x - homeRange, x + homeRange);
            int j = MathHelper.func_76136_a(world.field_73012_v, 16, 32);
            int k = MathHelper.func_76136_a(world.field_73012_v, z - homeRange, z + homeRange);
            int checkRange = 16;
            if (world.func_72904_c(i - checkRange, j - checkRange, k - checkRange, i + checkRange, j + checkRange, k + checkRange)) {
               AxisAlignedBB aabb = AxisAlignedBB.func_72330_a((double)i, (double)j, (double)k, (double)(i + 1), (double)(j + 1), (double)(k + 1));
               aabb = aabb.func_72314_b((double)checkRange, (double)checkRange, (double)checkRange);
               if (world.func_72872_a(EntityPlayer.class, aabb).isEmpty()) {
                  Block block = world.func_147439_a(i, j, k);
                  Block below = world.func_147439_a(i, j - 1, k);
                  Block above = world.func_147439_a(i, j + 1, k);
                  if (below.func_149721_r() && !block.func_149721_r() && !above.func_149721_r()) {
                     LOTREntityGollum gollum = new LOTREntityGollum(world);
                     gollum.func_70012_b((double)i + 0.5D, (double)j, (double)k + 0.5D, 0.0F, 0.0F);
                     if (gollum.func_70601_bi()) {
                        gollum.func_110161_a((IEntityLivingData)null);
                        world.func_72838_d(gollum);
                        LOTRLevelData.setGollumSpawned(true);
                     }
                  }
               }
            }
         }

      }
   }
}
