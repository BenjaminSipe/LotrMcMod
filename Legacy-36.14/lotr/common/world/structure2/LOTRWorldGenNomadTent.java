package lotr.common.world.structure2;

import java.util.Random;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityNomad;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.world.World;

public class LOTRWorldGenNomadTent extends LOTRWorldGenNomadStructure {
   public LOTRWorldGenNomadTent(boolean flag) {
      super(flag);
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, 7);
      this.setupRandomBlocks(random);
      int minHeight;
      int maxHeight;
      int i1;
      int k1;
      int j1;
      if (this.restrictions) {
         minHeight = 0;
         maxHeight = 0;

         for(i1 = -6; i1 <= 6; ++i1) {
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

      for(minHeight = -6; minHeight <= 6; ++minHeight) {
         for(maxHeight = -6; maxHeight <= 6; ++maxHeight) {
            i1 = Math.abs(minHeight);
            k1 = Math.abs(maxHeight);
            if (i1 + k1 <= 9) {
               if (!this.isSurface(world, minHeight, 0, maxHeight)) {
                  this.laySandBase(world, minHeight, 0, maxHeight);
               }

               for(j1 = 1; j1 <= 8; ++j1) {
                  this.setAir(world, minHeight, j1, maxHeight);
               }
            }
         }
      }

      this.loadStrScan("nomad_tent");
      this.associateBlockMetaAlias("TENT", this.tentBlock, this.tentMeta);
      this.associateBlockMetaAlias("TENT2", this.tent2Block, this.tent2Meta);
      this.associateBlockMetaAlias("FENCE", this.fenceBlock, this.fenceMeta);
      this.generateStrScan(world, random, 0, 1, 0);
      this.setBlockAndMetadata(world, -3, 1, -2, this.bedBlock, 3);
      this.setBlockAndMetadata(world, -4, 1, -2, this.bedBlock, 11);
      this.setBlockAndMetadata(world, -3, 1, 2, this.bedBlock, 3);
      this.setBlockAndMetadata(world, -4, 1, 2, this.bedBlock, 11);
      this.placeWeaponRack(world, 0, 3, 5, 6, this.getRandomNomadWeapon(random));
      this.placeChest(world, random, 0, 1, 5, LOTRMod.chestBasket, 2, LOTRChestContents.NOMAD_TENT);
      minHeight = 1 + random.nextInt(2);

      for(maxHeight = 0; maxHeight < minHeight; ++maxHeight) {
         LOTREntityNomad nomad = new LOTREntityNomad(world);
         this.spawnNPCAndSetHome(nomad, world, 0, 1, -1, 16);
      }

      return true;
   }
}
