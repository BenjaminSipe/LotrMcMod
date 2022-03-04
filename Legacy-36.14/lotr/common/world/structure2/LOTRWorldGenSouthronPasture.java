package lotr.common.world.structure2;

import java.util.Random;
import net.minecraft.entity.EntityCreature;
import net.minecraft.world.World;

public class LOTRWorldGenSouthronPasture extends LOTRWorldGenSouthronStructure {
   public LOTRWorldGenSouthronPasture(boolean flag) {
      super(flag);
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, 6);
      this.setupRandomBlocks(random);
      int minHeight;
      int maxHeight;
      int i1;
      int k1;
      int j1;
      if (this.restrictions) {
         minHeight = 0;
         maxHeight = 0;

         for(i1 = -4; i1 <= 4; ++i1) {
            for(k1 = -6; k1 <= 6; ++k1) {
               j1 = this.getTopBlock(world, i1, k1) - 1;
               if (!this.isSurface(world, i1, j1, k1)) {
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

      for(minHeight = -4; minHeight <= 4; ++minHeight) {
         for(maxHeight = -6; maxHeight <= 6; ++maxHeight) {
            i1 = Math.abs(minHeight);
            k1 = Math.abs(maxHeight);
            if (i1 != 4 || k1 != 6) {
               for(j1 = 1; j1 <= 4; ++j1) {
                  this.setAir(world, minHeight, j1, maxHeight);
               }
            }
         }
      }

      this.loadStrScan("southron_pasture");
      this.associateBlockMetaAlias("STONE", this.stoneBlock, this.stoneMeta);
      this.associateBlockMetaAlias("BRICK", this.brickBlock, this.brickMeta);
      this.associateBlockMetaAlias("BRICK_SLAB", this.brickSlabBlock, this.brickSlabMeta);
      this.associateBlockMetaAlias("FENCE", this.fenceBlock, this.fenceMeta);
      this.associateBlockAlias("FENCE_GATE", this.fenceGateBlock);
      this.generateStrScan(world, random, 0, 0, 0);
      minHeight = 2 + random.nextInt(4);

      for(maxHeight = 0; maxHeight < minHeight; ++maxHeight) {
         EntityCreature animal = LOTRWorldGenHarnedorPasture.getRandomAnimal(world, random);
         this.spawnNPCAndSetHome(animal, world, 0, 1, 0, 0);
         animal.func_110177_bN();
      }

      return true;
   }
}
