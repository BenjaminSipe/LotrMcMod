package lotr.common.world.structure2;

import java.util.Random;
import net.minecraft.entity.EntityCreature;
import net.minecraft.world.World;

public class LOTRWorldGenGulfPasture extends LOTRWorldGenGulfStructure {
   public LOTRWorldGenGulfPasture(boolean flag) {
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

         for(i1 = -5; i1 <= 5; ++i1) {
            for(k1 = -5; k1 <= 5; ++k1) {
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

      for(minHeight = -5; minHeight <= 5; ++minHeight) {
         for(maxHeight = -5; maxHeight <= 5; ++maxHeight) {
            i1 = Math.abs(minHeight);
            k1 = Math.abs(maxHeight);
            if (i1 != 5 || k1 != 5) {
               for(j1 = 1; j1 <= 6; ++j1) {
                  this.setAir(world, minHeight, j1, maxHeight);
               }
            }
         }
      }

      this.loadStrScan("gulf_pasture");
      this.associateBlockMetaAlias("WOOD", this.woodBlock, this.woodMeta);
      this.associateBlockMetaAlias("FENCE", this.fenceBlock, this.fenceMeta);
      this.associateBlockAlias("FENCE_GATE", this.fenceGateBlock);
      this.associateBlockMetaAlias("FLAG", this.flagBlock, this.flagMeta);
      this.associateBlockMetaAlias("BONE", this.boneBlock, this.boneMeta);
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
