package lotr.common.world.structure2;

import java.util.Random;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityElf;
import net.minecraft.world.World;

public class LOTRWorldGenRuinedEregionForge extends LOTRWorldGenHighElvenForge {
   public LOTRWorldGenRuinedEregionForge(boolean flag) {
      super(flag);
      this.ruined = true;
      this.roofBlock = LOTRMod.clayTileDyed;
      this.roofMeta = 8;
      this.roofStairBlock = LOTRMod.stairsClayTileDyedLightGray;
   }

   protected LOTREntityElf getElf(World world) {
      return null;
   }

   protected void placeBrick(World world, int i, int j, int k, Random random) {
      if (random.nextInt(12) != 0) {
         int l = random.nextInt(3);
         switch(l) {
         case 0:
            this.setBlockAndMetadata(world, i, j, k, LOTRMod.brick3, 2);
            break;
         case 1:
            this.setBlockAndMetadata(world, i, j, k, LOTRMod.brick3, 3);
            break;
         case 2:
            this.setBlockAndMetadata(world, i, j, k, LOTRMod.brick3, 4);
         }

      }
   }

   protected void placePillar(World world, int i, int j, int k, Random random) {
      if (random.nextInt(12) != 0) {
         if (random.nextInt(3) == 0) {
            this.setBlockAndMetadata(world, i, j, k, LOTRMod.pillar, 11);
         } else {
            this.setBlockAndMetadata(world, i, j, k, LOTRMod.pillar, 10);
         }

      }
   }

   protected void placeSlab(World world, int i, int j, int k, boolean flag, Random random) {
      if (random.nextInt(12) != 0) {
         int l = random.nextInt(3);
         switch(l) {
         case 0:
            this.setBlockAndMetadata(world, i, j, k, LOTRMod.slabSingle5, 5 | (flag ? 8 : 0));
            break;
         case 1:
            this.setBlockAndMetadata(world, i, j, k, LOTRMod.slabSingle5, 6 | (flag ? 8 : 0));
            break;
         case 2:
            this.setBlockAndMetadata(world, i, j, k, LOTRMod.slabSingle5, 7 | (flag ? 8 : 0));
         }

      }
   }

   protected void placeWall(World world, int i, int j, int k, Random random) {
      if (random.nextInt(12) != 0) {
         int l = random.nextInt(3);
         switch(l) {
         case 0:
            this.setBlockAndMetadata(world, i, j, k, LOTRMod.wall2, 11);
            break;
         case 1:
            this.setBlockAndMetadata(world, i, j, k, LOTRMod.wall2, 12);
            break;
         case 2:
            this.setBlockAndMetadata(world, i, j, k, LOTRMod.wall2, 13);
         }

      }
   }

   protected void placeStairs(World world, int i, int j, int k, int meta, Random random) {
      if (random.nextInt(12) != 0) {
         int l = random.nextInt(3);
         switch(l) {
         case 0:
            this.setBlockAndMetadata(world, i, j, k, LOTRMod.stairsHighElvenBrick, meta);
            break;
         case 1:
            this.setBlockAndMetadata(world, i, j, k, LOTRMod.stairsHighElvenBrickMossy, meta);
            break;
         case 2:
            this.setBlockAndMetadata(world, i, j, k, LOTRMod.stairsHighElvenBrickCracked, meta);
         }

      }
   }

   protected void placeRoof(World world, int i, int j, int k, Random random) {
      if (random.nextInt(12) != 0) {
         super.placeRoof(world, i, j, k, random);
      }
   }

   protected void placeRoofStairs(World world, int i, int j, int k, int meta, Random random) {
      if (random.nextInt(12) != 0) {
         super.placeRoofStairs(world, i, j, k, meta, random);
      }
   }
}
