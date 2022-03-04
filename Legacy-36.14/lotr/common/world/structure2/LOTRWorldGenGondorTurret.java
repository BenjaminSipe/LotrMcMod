package lotr.common.world.structure2;

import java.util.Random;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityGondorArcher;
import lotr.common.entity.npc.LOTREntityGondorSoldier;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityCreature;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenGondorTurret extends LOTRWorldGenStructureBase2 {
   public LOTRWorldGenGondorTurret(boolean flag) {
      super(flag);
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, 3);
      int i1;
      int k1;
      int k1;
      if (this.restrictions) {
         for(i1 = -2; i1 <= 2; ++i1) {
            for(k1 = -2; k1 <= 2; ++k1) {
               k1 = this.getTopBlock(world, i1, k1);
               Block block = this.getBlock(world, i1, k1 - 1, k1);
               if (block != Blocks.field_150349_c) {
                  return false;
               }
            }
         }
      }

      for(i1 = -2; i1 <= 2; ++i1) {
         for(k1 = -2; k1 <= 2; ++k1) {
            this.setBlockAndMetadata(world, i1, 0, k1, LOTRMod.slabDouble, 2);

            for(k1 = -1; !this.isOpaque(world, i1, k1, k1) && this.getY(k1) >= 0; --k1) {
               this.setBlockAndMetadata(world, i1, k1, k1, LOTRMod.slabDouble, 2);
               this.setGrassToDirt(world, i1, k1 - 1, k1);
            }
         }
      }

      for(i1 = 1; i1 <= 4; ++i1) {
         for(k1 = -1; k1 <= 1; ++k1) {
            for(k1 = -1; k1 <= 1; ++k1) {
               if (Math.abs(k1) == 1 && Math.abs(k1) == 1) {
                  this.setBlockAndMetadata(world, k1, i1, k1, LOTRMod.brick, 5);
               } else {
                  this.setBlockAndMetadata(world, k1, i1, k1, LOTRMod.rock, 1);
               }
            }
         }
      }

      this.setBlockAndMetadata(world, -2, 1, -2, LOTRMod.slabDouble, 2);
      this.setBlockAndMetadata(world, -2, 1, 2, LOTRMod.slabDouble, 2);
      this.setBlockAndMetadata(world, 2, 1, -2, LOTRMod.slabDouble, 2);
      this.setBlockAndMetadata(world, 2, 1, 2, LOTRMod.slabDouble, 2);

      for(i1 = 2; i1 <= 4; ++i1) {
         this.setBlockAndMetadata(world, -2, i1, -2, LOTRMod.wall, 2);
         this.setBlockAndMetadata(world, -2, i1, 2, LOTRMod.wall, 2);
         this.setBlockAndMetadata(world, 2, i1, -2, LOTRMod.wall, 2);
         this.setBlockAndMetadata(world, 2, i1, 2, LOTRMod.wall, 2);
      }

      this.setBlockAndMetadata(world, -2, 5, -2, Blocks.field_150364_r, 0);
      this.setBlockAndMetadata(world, -2, 5, 2, Blocks.field_150364_r, 0);
      this.setBlockAndMetadata(world, 2, 5, -2, Blocks.field_150364_r, 0);
      this.setBlockAndMetadata(world, 2, 5, 2, Blocks.field_150364_r, 0);

      for(i1 = -1; i1 <= 1; ++i1) {
         this.setBlockAndMetadata(world, -2, 1, i1, LOTRMod.stairsGondorBrick, 1);
         this.setBlockAndMetadata(world, 2, 1, i1, LOTRMod.stairsGondorBrick, 0);
         this.setBlockAndMetadata(world, -2, 3, i1, LOTRMod.slabSingle, 10);
         this.setBlockAndMetadata(world, -2, 4, i1, Blocks.field_150364_r, 0);
         this.setBlockAndMetadata(world, -2, 5, i1, Blocks.field_150364_r, 0);
         this.setBlockAndMetadata(world, 2, 3, i1, LOTRMod.slabSingle, 10);
         this.setBlockAndMetadata(world, 2, 4, i1, Blocks.field_150364_r, 0);
         this.setBlockAndMetadata(world, 2, 5, i1, Blocks.field_150364_r, 0);
      }

      for(i1 = -1; i1 <= 1; ++i1) {
         this.setBlockAndMetadata(world, i1, 1, 2, LOTRMod.stairsGondorBrick, 3);
         this.setBlockAndMetadata(world, i1, 3, -2, LOTRMod.slabSingle, 10);
         this.setBlockAndMetadata(world, i1, 4, -2, Blocks.field_150364_r, 0);
         this.setBlockAndMetadata(world, i1, 5, -2, Blocks.field_150364_r, 0);
         this.setBlockAndMetadata(world, i1, 3, 2, LOTRMod.slabSingle, 10);
         this.setBlockAndMetadata(world, i1, 4, 2, Blocks.field_150364_r, 0);
         this.setBlockAndMetadata(world, i1, 5, 2, Blocks.field_150364_r, 0);
      }

      for(i1 = 1; i1 <= 4; ++i1) {
         this.setBlockAndMetadata(world, 0, i1, 0, Blocks.field_150468_ap, 2);
      }

      this.setBlockAndMetadata(world, 0, 1, -1, LOTRMod.doorLebethron, 1);
      this.setBlockAndMetadata(world, 0, 2, -1, LOTRMod.doorLebethron, 8);
      this.setBlockAndMetadata(world, 0, 5, 0, LOTRMod.trapdoorLebethron, 0);
      this.setBlockAndMetadata(world, 0, 5, 1, LOTRMod.slabSingle, 2);
      this.placeChest(world, random, 1, 5, 1, LOTRMod.chestLebethron, 2, LOTRChestContents.GONDOR_FORTRESS_SUPPLIES);

      for(i1 = -2; i1 <= 2; ++i1) {
         for(k1 = -2; k1 <= 2; ++k1) {
            if (Math.abs(i1) == 2 || Math.abs(k1) == 2) {
               this.setBlockAndMetadata(world, i1, 6, k1, LOTRMod.wall, 2);
               if (Math.abs(i1) == 2 && Math.abs(k1) == 2) {
                  this.setBlockAndMetadata(world, i1, 7, k1, Blocks.field_150478_aa, 5);
               }
            }
         }
      }

      i1 = 1 + random.nextInt(2);

      for(k1 = 0; k1 < i1; ++k1) {
         LOTREntityGondorSoldier soldier = random.nextBoolean() ? new LOTREntityGondorSoldier(world) : new LOTREntityGondorArcher(world);
         ((LOTREntityGondorSoldier)soldier).spawnRidingHorse = false;
         this.spawnNPCAndSetHome((EntityCreature)soldier, world, 0, 6, 0, 8);
      }

      return true;
   }
}
