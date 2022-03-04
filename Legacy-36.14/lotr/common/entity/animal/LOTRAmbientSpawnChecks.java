package lotr.common.entity.animal;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTRAmbientSpawnChecks {
   public static boolean canSpawn(EntityLiving entity, int xzRange, int yRange, int attempts, int required, Material... materials) {
      World world = entity.field_70170_p;
      Random rand = entity.func_70681_au();
      int i = MathHelper.func_76128_c(entity.field_70165_t);
      int j = MathHelper.func_76128_c(entity.field_70163_u);
      int k = MathHelper.func_76128_c(entity.field_70161_v);
      Block below = world.func_147439_a(i, j - 1, k);
      if (below == world.func_72807_a(i, k).field_76752_A) {
         int light = world.func_72957_l(i, j, k);
         if (j >= 62 && light >= rand.nextInt(8) || light >= 8) {
            List validMaterials = Arrays.asList(materials);
            int counted = 0;

            for(int l = 0; l < attempts; ++l) {
               int i1 = i + rand.nextInt(xzRange) - rand.nextInt(xzRange);
               int k1 = k + rand.nextInt(xzRange) - rand.nextInt(xzRange);
               int j1 = j + rand.nextInt(yRange) - rand.nextInt(yRange);
               if (world.func_72899_e(i1, j1, k1)) {
                  Block block = world.func_147439_a(i1, j1, k1);
                  if (validMaterials.contains(block.func_149688_o())) {
                     ++counted;
                     if (counted > required) {
                        return true;
                     }
                  }
               }
            }
         }
      }

      return false;
   }
}
