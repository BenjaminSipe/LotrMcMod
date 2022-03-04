package lotr.common.world.feature;

import java.util.Random;
import lotr.common.LOTRMod;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

public class LOTRWorldGenLogs extends WorldGenerator {
   public LOTRWorldGenLogs() {
      super(false);
   }

   public boolean func_76484_a(World world, Random random, int i, int j, int k) {
      if (!this.isSuitablePositionForLog(world, i, j, k)) {
         return false;
      } else {
         int logType = random.nextInt(5);
         int length;
         int k1;
         if (logType == 0) {
            length = 2 + random.nextInt(6);

            for(k1 = i; k1 < i + length && this.isSuitablePositionForLog(world, k1, j, k); ++k1) {
               this.func_150516_a(world, k1, j, k, LOTRMod.rottenLog, 4);
               world.func_147439_a(k1, j - 1, k).onPlantGrow(world, k1, j - 1, k, k1, j, k);
            }

            return true;
         } else if (logType != 1) {
            this.func_150516_a(world, i, j, k, LOTRMod.rottenLog, 0);
            world.func_147439_a(i, j - 1, k).onPlantGrow(world, i, j - 1, k, i, j, k);
            return true;
         } else {
            length = 2 + random.nextInt(6);

            for(k1 = k; k1 < k + length && this.isSuitablePositionForLog(world, i, j, k1); ++k1) {
               this.func_150516_a(world, i, j, k1, LOTRMod.rottenLog, 8);
               world.func_147439_a(i, j - 1, k1).onPlantGrow(world, i, j - 1, k1, i, j, k1);
            }

            return true;
         }
      }
   }

   private boolean isSuitablePositionForLog(World world, int i, int j, int k) {
      return !world.func_147439_a(i, j - 1, k).canSustainPlant(world, i, j - 1, k, ForgeDirection.UP, (IPlantable)Blocks.field_150345_g) ? false : world.func_147439_a(i, j, k).isReplaceable(world, i, j, k);
   }
}
