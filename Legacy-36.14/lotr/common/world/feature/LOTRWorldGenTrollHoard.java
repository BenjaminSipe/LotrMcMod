package lotr.common.world.feature;

import java.util.Random;
import lotr.common.LOTRMod;
import lotr.common.world.biome.LOTRBiomeGenEttenmoors;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRWorldGenTrollHoard extends WorldGenerator {
   public boolean func_76484_a(World world, Random random, int i, int j, int k) {
      int height = world.func_72825_h(i, k);
      int maxCaveHeight = height - 4;
      int chests = 2 + random.nextInt(5);
      int chestsGenerated = 0;

      int l;
      int i1;
      int j1;
      int k1;
      for(l = 0; l < 64; ++l) {
         i1 = i + MathHelper.func_76136_a(random, -3, 3);
         j1 = j + MathHelper.func_76136_a(random, -3, 3);
         k1 = k + MathHelper.func_76136_a(random, -3, 3);
         if (j1 <= maxCaveHeight && world.func_147437_c(i1, j1, k1) && world.func_147439_a(i1, j1 - 1, k1) == Blocks.field_150348_b) {
            Block treasureBlock;
            if (random.nextInt(5) == 0) {
               treasureBlock = LOTRMod.treasureCopper;
            } else if (random.nextBoolean()) {
               treasureBlock = LOTRMod.treasureSilver;
            } else {
               treasureBlock = LOTRMod.treasureGold;
            }

            int top = j1 + random.nextInt(3);

            for(int j2 = j1; j2 <= top; ++j2) {
               int treasureMeta = 7;
               if (j2 == top) {
                  treasureMeta = random.nextInt(7);
               }

               if (!world.func_147437_c(i1, j2, k1)) {
                  break;
               }

               this.func_150516_a(world, i1, j2, k1, treasureBlock, treasureMeta);
            }
         }
      }

      for(l = 0; l < 48; ++l) {
         i1 = i + MathHelper.func_76136_a(random, -8, 8);
         j1 = j + MathHelper.func_76136_a(random, -2, 2);
         k1 = k + MathHelper.func_76136_a(random, -8, 8);
         if (j1 <= maxCaveHeight && world.func_147437_c(i1, j1, k1) && world.func_147439_a(i1, j1 - 1, k1) == Blocks.field_150348_b) {
            this.func_150516_a(world, i1, j1, k1, Blocks.field_150486_ae, 0);
            LOTRChestContents.fillChest(world, random, i1, j1, k1, LOTRChestContents.TROLL_HOARD);
            if (world.func_72807_a(i1, k1) instanceof LOTRBiomeGenEttenmoors && random.nextInt(5) == 0) {
               LOTRChestContents.fillChest(world, random, i1, j1, k1, LOTRChestContents.TROLL_HOARD_ETTENMOORS);
            }

            ++chestsGenerated;
            if (chestsGenerated >= chests) {
               break;
            }
         }
      }

      return chestsGenerated > 0;
   }
}
