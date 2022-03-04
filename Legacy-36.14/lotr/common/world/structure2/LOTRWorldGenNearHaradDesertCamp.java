package lotr.common.world.structure2;

import java.util.Random;
import lotr.common.LOTRMod;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.animal.LOTREntityCamel;
import lotr.common.entity.npc.LOTREntityHarnedorWarrior;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLeashKnot;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenNearHaradDesertCamp extends LOTRWorldGenStructureBase2 {
   public LOTRWorldGenNearHaradDesertCamp(boolean flag) {
      super(flag);
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, 0);
      int highestHeight = 0;

      int k1;
      int j1;
      for(int i1 = -1; i1 <= 1; ++i1) {
         for(k1 = -1; k1 <= 1; ++k1) {
            j1 = this.getTopBlock(world, i1, k1);
            Block block = this.getBlock(world, i1, j1 - 1, k1);
            if (block != Blocks.field_150354_m && block != Blocks.field_150346_d && block != Blocks.field_150349_c) {
               return false;
            }

            if (j1 > highestHeight) {
               highestHeight = j1;
            }
         }
      }

      boolean flag = false;
      k1 = -2 + random.nextInt(5);
      j1 = 4 + random.nextInt(4);
      int y = this.getTopBlock(world, k1, j1);
      flag = (new LOTRWorldGenNearHaradTent(this.notifyChanges)).generateWithSetRotation(world, random, this.getX(k1, j1), this.getY(y), this.getZ(k1, j1), this.getRotationMode()) || flag;
      k1 = -2 + random.nextInt(5);
      j1 = -4 - random.nextInt(4);
      y = this.getTopBlock(world, k1, j1);
      flag = (new LOTRWorldGenNearHaradTent(this.notifyChanges)).generateWithSetRotation(world, random, this.getX(k1, j1), this.getY(y), this.getZ(k1, j1), (this.getRotationMode() + 2) % 4) || flag;
      if (!flag) {
         return false;
      } else {
         int i1;
         int k1;
         for(i1 = -1; i1 <= 1; ++i1) {
            for(k1 = -1; k1 <= 1; ++k1) {
               for(int j1 = highestHeight - 1; !this.isOpaque(world, i1, j1, k1) && this.getY(j1) >= 0; --j1) {
                  this.setBlockAndMetadata(world, i1, j1, k1, Blocks.field_150322_A, 0);
                  this.setGrassToDirt(world, i1, j1 - 1, k1);
               }
            }

            this.setBlockAndMetadata(world, i1, highestHeight, -1, LOTRMod.stairsNearHaradBrick, 2);
            this.setBlockAndMetadata(world, i1, highestHeight, 1, LOTRMod.stairsNearHaradBrick, 3);
         }

         this.setBlockAndMetadata(world, -1, highestHeight, 0, LOTRMod.stairsNearHaradBrick, 1);
         this.setBlockAndMetadata(world, 1, highestHeight, 0, LOTRMod.stairsNearHaradBrick, 0);
         this.setBlockAndMetadata(world, 0, highestHeight, 0, Blocks.field_150355_j, 0);
         i1 = 1 + random.nextInt(4);

         for(k1 = 0; k1 < i1; ++k1) {
            LOTREntityCamel camel = new LOTREntityCamel(world);
            int camelX = random.nextBoolean() ? -3 - random.nextInt(3) : 3 + random.nextInt(3);
            int camelZ = -3 + random.nextInt(7);
            int camelY = this.getTopBlock(world, camelX, camelZ);
            if (this.getBlock(world, camelX, camelY - 1, camelZ) == Blocks.field_150354_m && this.getBlock(world, camelX, camelY, camelZ) == Blocks.field_150350_a && this.getBlock(world, camelX, camelY + 1, camelZ) == Blocks.field_150350_a) {
               this.setBlockAndMetadata(world, camelX, camelY, camelZ, LOTRMod.fence2, 2);
               this.setBlockAndMetadata(world, camelX, camelY + 1, camelZ, LOTRMod.fence2, 2);
               this.spawnNPCAndSetHome(camel, world, camelX, camelY, camelZ, 0);
               camel.func_110177_bN();
               camel.saddleMountForWorldGen();
               if (random.nextBoolean()) {
                  camel.setChestedForWorldGen();
               }

               EntityLeashKnot leash = EntityLeashKnot.func_110129_a(world, this.getX(camelX, camelZ), this.getY(camelY), this.getZ(camelX, camelZ));
               camel.func_110162_b(leash, true);
            }
         }

         LOTREntityNPCRespawner respawner = new LOTREntityNPCRespawner(world);
         respawner.setSpawnClass(LOTREntityHarnedorWarrior.class);
         respawner.setCheckRanges(20, -12, 12, 4);
         respawner.setSpawnRanges(8, -4, 4, 16);
         this.placeNPCRespawner(respawner, world, 0, 0, 0);
         return true;
      }
   }
}
