package lotr.common.world.structure2;

import java.util.Random;
import lotr.common.LOTRMod;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.npc.LOTREntityHarnedhrim;
import lotr.common.entity.npc.LOTREntityHarnedorArcher;
import lotr.common.entity.npc.LOTREntityHarnedorWarrior;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.entity.EntityCreature;
import net.minecraft.world.World;

public class LOTRWorldGenHarnedorTower extends LOTRWorldGenHarnedorStructure {
   public LOTRWorldGenHarnedorTower(boolean flag) {
      super(flag);
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, 0);
      this.setupRandomBlocks(random);
      int minHeight;
      int maxHeight;
      int j1;
      if (this.restrictions) {
         minHeight = 0;
         maxHeight = 0;

         for(j1 = -3; j1 <= 3; ++j1) {
            for(int k1 = -3; k1 <= 3; ++k1) {
               int j1 = this.getTopBlock(world, j1, k1) - 1;
               if (!this.isSurface(world, j1, j1, k1)) {
                  return false;
               }

               if (j1 < minHeight) {
                  minHeight = j1;
               }

               if (j1 > maxHeight) {
                  maxHeight = j1;
               }

               if (maxHeight - minHeight > 8) {
                  return false;
               }
            }
         }
      }

      for(minHeight = -3; minHeight <= 3; ++minHeight) {
         for(maxHeight = -3; maxHeight <= 3; ++maxHeight) {
            for(j1 = 6; j1 <= 16; ++j1) {
               this.setAir(world, minHeight, j1, maxHeight);
            }
         }
      }

      this.loadStrScan("harnedor_tower");
      this.associateBlockMetaAlias("PLANK", this.plankBlock, this.plankMeta);
      this.associateBlockMetaAlias("PLANK_SLAB", this.plankSlabBlock, this.plankSlabMeta);
      this.associateBlockMetaAlias("FENCE", this.fenceBlock, this.fenceMeta);
      this.associateBlockAlias("TRAPDOOR", this.trapdoorBlock);
      this.associateBlockMetaAlias("ROOF", this.roofBlock, this.roofMeta);
      this.generateStrScan(world, random, 0, 1, 0);
      this.placeSkull(world, random, -3, 5, -3);
      this.placeSkull(world, random, 3, 6, -3);
      this.placeSkull(world, random, 3, 6, 3);
      this.placeSkull(world, random, -3, 7, -2);
      this.placeSkull(world, random, -3, 7, 2);
      this.placeSkull(world, random, 0, 8, 3);
      this.placeSkull(world, random, -3, 10, 3);
      this.placeSkull(world, random, -3, 12, -3);
      this.placeSkull(world, random, 3, 13, 2);
      this.placeChest(world, random, -2, 11, 2, LOTRMod.chestBasket, 2, LOTRChestContents.HARNENNOR_HOUSE);
      minHeight = 1 + random.nextInt(2);

      for(maxHeight = 0; maxHeight < minHeight; ++maxHeight) {
         LOTREntityHarnedhrim warrior = random.nextInt(3) == 0 ? new LOTREntityHarnedorArcher(world) : new LOTREntityHarnedorWarrior(world);
         ((LOTREntityHarnedhrim)warrior).spawnRidingHorse = false;
         this.spawnNPCAndSetHome((EntityCreature)warrior, world, 0, 13, 0, 8);
      }

      LOTREntityNPCRespawner respawner = new LOTREntityNPCRespawner(world);
      respawner.setSpawnClasses(LOTREntityHarnedorWarrior.class, LOTREntityHarnedorArcher.class);
      respawner.setCheckRanges(6, -16, 4, 4);
      respawner.setSpawnRanges(2, -1, 1, 8);
      this.placeNPCRespawner(respawner, world, 0, 13, 0);
      return true;
   }
}
