package lotr.common.world.structure2;

import java.util.Random;
import lotr.common.entity.animal.LOTREntityCamel;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenHarnedorPasture extends LOTRWorldGenHarnedorStructure {
   public LOTRWorldGenHarnedorPasture(boolean flag) {
      super(flag);
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, 4);
      this.setupRandomBlocks(random);
      int minHeight;
      int maxHeight;
      int j1;
      int i1;
      int j1;
      if (this.restrictions) {
         minHeight = 0;
         maxHeight = 0;

         for(j1 = -3; j1 <= 3; ++j1) {
            for(i1 = -3; i1 <= 6; ++i1) {
               j1 = this.getTopBlock(world, j1, i1) - 1;
               if (!this.isSurface(world, j1, j1, i1)) {
                  return false;
               }

               if (j1 < minHeight) {
                  minHeight = j1;
               }

               if (j1 > maxHeight) {
                  maxHeight = j1;
               }

               if (maxHeight - minHeight > 6) {
                  return false;
               }
            }
         }
      }

      for(minHeight = -3; minHeight <= 3; ++minHeight) {
         for(maxHeight = -3; maxHeight <= 6; ++maxHeight) {
            for(j1 = -1; !this.isOpaque(world, minHeight, j1, maxHeight) && this.getY(j1) >= 0; --j1) {
               this.setBlockAndMetadata(world, minHeight, j1, maxHeight, Blocks.field_150346_d, 0);
               this.setGrassToDirt(world, minHeight, j1 - 1, maxHeight);
            }

            for(j1 = 1; j1 <= 4; ++j1) {
               this.setAir(world, minHeight, j1, maxHeight);
            }
         }
      }

      this.loadStrScan("harnedor_pasture");
      this.associateBlockMetaAlias("WOOD", this.woodBlock, this.woodMeta);
      this.associateBlockMetaAlias("FENCE", this.fenceBlock, this.fenceMeta);
      this.associateBlockAlias("FENCE_GATE", this.fenceGateBlock);
      this.generateStrScan(world, random, 0, 0, 0);
      int[] var15 = new int[]{-2, -1, 1, 2};
      maxHeight = var15.length;

      for(j1 = 0; j1 < maxHeight; ++j1) {
         i1 = var15[j1];
         j1 = 0;

         for(int step = 0; step < 6; ++step) {
            int k1 = -4 - step;
            int j2;
            if (this.isOpaque(world, i1, j1 + 1, k1)) {
               this.setAir(world, i1, j1 + 1, k1);
               this.setAir(world, i1, j1 + 2, k1);
               this.setAir(world, i1, j1 + 3, k1);
               this.setBlockAndMetadata(world, i1, j1, k1, Blocks.field_150349_c, 0);
               this.setGrassToDirt(world, i1, j1 - 1, k1);

               for(j2 = j1 - 1; !this.isOpaque(world, i1, j2, k1) && this.getY(j2) >= 0; --j2) {
                  this.setBlockAndMetadata(world, i1, j2, k1, Blocks.field_150346_d, 0);
                  this.setGrassToDirt(world, i1, j2 - 1, k1);
               }

               ++j1;
            } else {
               if (this.isOpaque(world, i1, j1, k1)) {
                  break;
               }

               this.setAir(world, i1, j1 + 1, k1);
               this.setAir(world, i1, j1 + 2, k1);
               this.setAir(world, i1, j1 + 3, k1);
               this.setBlockAndMetadata(world, i1, j1, k1, Blocks.field_150349_c, 0);
               this.setGrassToDirt(world, i1, j1 - 1, k1);

               for(j2 = j1 - 1; !this.isOpaque(world, i1, j2, k1) && this.getY(j2) >= 0; --j2) {
                  this.setBlockAndMetadata(world, i1, j2, k1, Blocks.field_150346_d, 0);
                  this.setGrassToDirt(world, i1, j2 - 1, k1);
               }

               --j1;
            }
         }
      }

      minHeight = 2 + random.nextInt(4);

      for(maxHeight = 0; maxHeight < minHeight; ++maxHeight) {
         EntityCreature animal = getRandomAnimal(world, random);
         this.spawnNPCAndSetHome(animal, world, 0, 1, 0, 0);
         animal.func_110177_bN();
      }

      return true;
   }

   public static EntityAnimal getRandomAnimal(World world, Random random) {
      int animal = random.nextInt(5);
      if (animal == 0) {
         return new EntityCow(world);
      } else if (animal == 1) {
         return new EntityPig(world);
      } else if (animal == 2) {
         return new EntitySheep(world);
      } else if (animal == 3) {
         return new EntityChicken(world);
      } else {
         return animal == 4 ? new LOTREntityCamel(world) : null;
      }
   }
}
