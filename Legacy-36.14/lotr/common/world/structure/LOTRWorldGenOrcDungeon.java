package lotr.common.world.structure;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import lotr.common.LOTRMod;
import lotr.common.entity.LOTREntities;
import lotr.common.entity.npc.LOTREntityGundabadOrc;
import lotr.common.entity.npc.LOTREntityOrc;
import lotr.common.world.LOTRWorldChunkManager;
import lotr.common.world.biome.LOTRBiome;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.spawning.LOTRBiomeSpawnList;
import lotr.common.world.spawning.LOTRSpawnEntry;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class LOTRWorldGenOrcDungeon extends LOTRWorldGenStructureBase {
   public LOTRWorldGenOrcDungeon(boolean flag) {
      super(flag);
   }

   public boolean func_76484_a(World world, Random random, int i, int j, int k) {
      int xSize = random.nextInt(3) + 2;
      int ySize = 3;
      int zSize = random.nextInt(3) + 2;
      int height = 0;
      int i1;
      if (!this.restrictions && this.usingPlayer != null) {
         i1 = this.usingPlayerRotation();
         switch(i1) {
         case 0:
            k += zSize + 2;
            break;
         case 1:
            i -= xSize + 2;
            break;
         case 2:
            k -= zSize + 2;
            break;
         case 3:
            i += xSize + 2;
         }
      }

      int j1;
      int i1;
      if (this.restrictions) {
         for(i1 = i - xSize - 1; i1 <= i + xSize + 1; ++i1) {
            for(j1 = j - 1; j1 <= j + ySize + 1; ++j1) {
               for(i1 = k - zSize - 1; i1 <= k + zSize + 1; ++i1) {
                  Material material = world.func_147439_a(i1, j1, i1).func_149688_o();
                  if (j1 == j - 1 && !material.func_76220_a()) {
                     return false;
                  }

                  if (j1 == j + ySize + 1 && !material.func_76220_a()) {
                     return false;
                  }

                  if ((i1 == i - xSize - 1 || i1 == i + xSize + 1 || i1 == k - zSize - 1 || i1 == k + zSize + 1) && j1 == j && world.func_147437_c(i1, j1, i1) && world.func_147437_c(i1, j1 + 1, i1)) {
                     ++height;
                  }
               }
            }
         }
      } else {
         height = 3;
      }

      if (height >= 1 && height <= 5) {
         for(i1 = i - xSize - 1; i1 <= i + xSize + 1; ++i1) {
            for(j1 = j + ySize; j1 >= j - 1; --j1) {
               for(i1 = k - zSize - 1; i1 <= k + zSize + 1; ++i1) {
                  if (i1 != i - xSize - 1 && j1 != j - 1 && i1 != k - zSize - 1 && i1 != i + xSize + 1 && j1 != j + ySize + 1 && i1 != k + zSize + 1) {
                     this.func_150516_a(world, i1, j1, i1, Blocks.field_150350_a, 0);
                  } else if (!this.restrictions || world.func_147439_a(i1, j1, i1).func_149688_o().func_76220_a()) {
                     if (random.nextInt(4) != 0) {
                        this.func_150516_a(world, i1, j1, i1, Blocks.field_150417_aV, 1 + random.nextInt(2));
                     } else {
                        this.func_150516_a(world, i1, j1, i1, Blocks.field_150417_aV, 0);
                     }
                  }
               }
            }
         }

         for(i1 = 0; i1 < 2; ++i1) {
            for(j1 = 0; j1 < 3; ++j1) {
               i1 = i + random.nextInt(xSize * 2 + 1) - xSize;
               int k1 = k + random.nextInt(zSize * 2 + 1) - zSize;
               if (world.func_147437_c(i1, j, k1)) {
                  boolean flag = false;
                  if (world.func_147439_a(i1 - 1, j, k1).func_149688_o().func_76220_a()) {
                     flag = true;
                  }

                  if (world.func_147439_a(i1 + 1, j, k1).func_149688_o().func_76220_a()) {
                     flag = true;
                  }

                  if (world.func_147439_a(i1, j, k1 - 1).func_149688_o().func_76220_a()) {
                     flag = true;
                  }

                  if (world.func_147439_a(i1, j, k1 + 1).func_149688_o().func_76220_a()) {
                     flag = true;
                  }

                  if (flag) {
                     this.func_150516_a(world, i1, j, k1, LOTRMod.chestStone, 0);
                     LOTRChestContents.fillChest(world, random, i1, j, k1, LOTRChestContents.ORC_DUNGEON);
                     break;
                  }
               }
            }
         }

         Class backupClass = LOTREntityGundabadOrc.class;
         List biomeClasses = new ArrayList();
         BiomeGenBase biome = world.func_72807_a(i, k);
         if (biome instanceof LOTRBiome) {
            LOTRBiomeVariant variant = ((LOTRWorldChunkManager)world.field_73011_w.field_76578_c).getBiomeVariantAt(i, k);
            LOTRBiomeSpawnList biomeSpawns = ((LOTRBiome)biome).getNPCSpawnList(world, random, i, j, k, variant);
            Iterator var16 = biomeSpawns.getAllSpawnEntries(world).iterator();

            while(var16.hasNext()) {
               LOTRSpawnEntry spawnEntry = (LOTRSpawnEntry)var16.next();
               Class spawnClass = spawnEntry.field_76300_b;
               if (LOTREntityOrc.class.isAssignableFrom(spawnClass)) {
                  biomeClasses.add(spawnClass);
               }
            }
         }

         int orcs = MathHelper.func_76136_a(random, 3, 6);

         while(orcs > 0) {
            Class orcClass = backupClass;
            if (!biomeClasses.isEmpty()) {
               orcClass = (Class)biomeClasses.get(random.nextInt(biomeClasses.size()));
            }

            LOTREntityOrc orc = (LOTREntityOrc)EntityList.func_75620_a(LOTREntities.getStringFromClass(orcClass), world);
            if (!orc.isOrcBombardier()) {
               orc.func_70012_b((double)i + 0.5D, (double)(j + 1), (double)k + 0.5D, 0.0F, 0.0F);
               orc.func_110171_b(i, j + 1, k, 4);
               orc.func_110161_a((IEntityLivingData)null);
               orc.isNPCPersistent = true;
               world.func_72838_d(orc);
               --orcs;
            }
         }

         int pillars = random.nextInt(6);

         label147:
         for(int l = 0; l < pillars; ++l) {
            int i1 = i + random.nextInt(xSize * 2 + 1) - xSize;
            int k1 = k + random.nextInt(zSize * 2 + 1) - zSize;
            if (i1 != i || k1 != k) {
               int j1;
               for(j1 = j + ySize; j1 >= j; --j1) {
                  if (!world.func_147437_c(i1, j1, k1)) {
                     continue label147;
                  }
               }

               for(j1 = j + ySize; j1 >= j; --j1) {
                  if (random.nextInt(4) != 0) {
                     this.func_150516_a(world, i1, j1, k1, Blocks.field_150417_aV, 1 + random.nextInt(2));
                  } else {
                     this.func_150516_a(world, i1, j1, k1, Blocks.field_150417_aV, 0);
                  }
               }
            }
         }

         return true;
      } else {
         return false;
      }
   }
}
