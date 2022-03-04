package lotr.common.world.structure2;

import java.util.Random;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityBreeFarmer;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenBreeBarn extends LOTRWorldGenBreeStructure {
   public LOTRWorldGenBreeBarn(boolean flag) {
      super(flag);
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, 8);
      this.setupRandomBlocks(random);
      int i1;
      int i1;
      int step;
      if (this.restrictions) {
         for(i1 = -6; i1 <= 6; ++i1) {
            for(i1 = -9; i1 <= 9; ++i1) {
               step = this.getTopBlock(world, i1, i1) - 1;
               if (!this.isSurface(world, i1, step, i1)) {
                  return false;
               }
            }
         }
      }

      for(i1 = -5; i1 <= 5; ++i1) {
         for(i1 = -7; i1 <= 7; ++i1) {
            for(step = 1; step <= 4; ++step) {
               this.setAir(world, i1, step, i1);
            }
         }
      }

      for(i1 = -6; i1 <= 6; ++i1) {
         for(i1 = -9; i1 <= 9; ++i1) {
            for(step = 5; step <= 10; ++step) {
               this.setAir(world, i1, step, i1);
            }
         }
      }

      int j1;
      int k1;
      int j2;
      for(i1 = -1; i1 <= 1; ++i1) {
         int[] var14 = new int[]{-8, 8};
         step = var14.length;

         for(j1 = 0; j1 < step; ++j1) {
            k1 = var14[j1];

            for(j2 = 1; j2 <= 3; ++j2) {
               this.setAir(world, i1, j2, k1);
            }
         }
      }

      this.loadStrScan("bree_barn");
      this.associateBlockMetaAlias("BRICK", this.brickBlock, this.brickMeta);
      this.associateBlockMetaAlias("STONE_WALL", this.stoneWallBlock, this.stoneWallMeta);
      this.associateBlockMetaAlias("PLANK", this.plankBlock, this.plankMeta);
      this.associateBlockMetaAlias("PLANK_SLAB", this.plankSlabBlock, this.plankSlabMeta);
      this.associateBlockMetaAlias("PLANK_SLAB_INV", this.plankSlabBlock, this.plankSlabMeta | 8);
      this.associateBlockMetaAlias("FENCE", this.fenceBlock, this.fenceMeta);
      this.associateBlockAlias("FENCE_GATE", this.fenceGateBlock);
      this.associateBlockAlias("TRAPDOOR", this.trapdoorBlock);
      this.associateBlockMetaAlias("BEAM", this.beamBlock, this.beamMeta);
      this.associateBlockMetaAlias("BEAM|4", this.beamBlock, this.beamMeta | 4);
      this.associateBlockMetaAlias("BEAM|8", this.beamBlock, this.beamMeta | 8);
      this.associateBlockMetaAlias("ROOF", this.roofBlock, this.roofMeta);
      this.associateBlockMetaAlias("ROOF_SLAB", this.roofSlabBlock, this.roofSlabMeta);
      this.associateBlockMetaAlias("ROOF_SLAB_INV", this.roofSlabBlock, this.roofSlabMeta | 8);
      this.associateBlockAlias("ROOF_STAIR", this.roofStairBlock);
      this.addBlockMetaAliasOption("THATCH_FLOOR", 1, LOTRMod.thatchFloor, 0);
      this.setBlockAliasChance("THATCH_FLOOR", 0.2F);
      this.addBlockMetaAliasOption("GROUND", 13, Blocks.field_150349_c, 0);
      this.addBlockMetaAliasOption("GROUND", 7, Blocks.field_150347_e, 0);
      this.associateBlockMetaAlias("LEAF", Blocks.field_150362_t, 4);
      this.generateStrScan(world, random, 0, 0, 0);
      int maxSteps = true;

      for(i1 = -1; i1 <= 1; ++i1) {
         for(step = 0; step < 12; ++step) {
            j1 = 0 - step;
            k1 = -8 - step;
            if (this.isOpaque(world, i1, j1, k1)) {
               break;
            }

            this.setBlockAndMetadata(world, i1, j1, k1, Blocks.field_150349_c, 0);
            this.setGrassToDirt(world, i1, j1 - 1, k1);

            for(j2 = j1 - 1; !this.isOpaque(world, i1, j2, k1) && this.getY(j2) >= 0; --j2) {
               this.setBlockAndMetadata(world, i1, j2, k1, Blocks.field_150346_d, 0);
               this.setGrassToDirt(world, i1, j2 - 1, k1);
            }
         }
      }

      for(i1 = -1; i1 <= 1; ++i1) {
         for(step = 0; step < 12; ++step) {
            j1 = 0 - step;
            k1 = 8 + step;
            if (this.isOpaque(world, i1, j1, k1)) {
               break;
            }

            this.setBlockAndMetadata(world, i1, j1, k1, Blocks.field_150349_c, 0);
            this.setGrassToDirt(world, i1, j1 - 1, k1);

            for(j2 = j1 - 1; !this.isOpaque(world, i1, j2, k1) && this.getY(j2) >= 0; --j2) {
               this.setBlockAndMetadata(world, i1, j2, k1, Blocks.field_150346_d, 0);
               this.setGrassToDirt(world, i1, j2 - 1, k1);
            }
         }
      }

      this.placeChest(world, random, -4, 1, -6, 4, LOTRChestContents.BREE_HOUSE, 1 + random.nextInt(2));
      this.placeChest(world, random, -4, 1, -5, 4, LOTRChestContents.BREE_HOUSE, 1 + random.nextInt(2));
      this.placeChest(world, random, 4, 1, 5, 5, LOTRChestContents.BREE_HOUSE, 1 + random.nextInt(2));
      this.placeChest(world, random, 4, 1, 6, 5, LOTRChestContents.BREE_HOUSE, 1 + random.nextInt(2));
      this.placeChest(world, random, -4, 0, -1, 4, LOTRChestContents.BREE_TREASURE);
      this.placeChest(world, random, 4, 5, -5, 5, LOTRChestContents.BREE_HOUSE, 1 + random.nextInt(2));
      this.placeChest(world, random, -4, 5, 0, 4, LOTRChestContents.BREE_TREASURE, 1 + random.nextInt(2));
      this.placeChest(world, random, -4, 5, 6, 4, LOTRChestContents.BREE_TREASURE);
      LOTREntityBreeFarmer farmer = new LOTREntityBreeFarmer(world);
      this.spawnNPCAndSetHome(farmer, world, 0, 1, 0, 16);
      this.spawnAnimal(world, random, -3, 1, -2);
      this.spawnAnimal(world, random, 3, 1, -2);
      this.spawnAnimal(world, random, -3, 1, 2);
      this.spawnAnimal(world, random, 3, 1, 2);
      return true;
   }

   private void spawnAnimal(World world, Random random, int i, int j, int k) {
      int animals = 2;

      for(int l = 0; l < animals; ++l) {
         EntityCreature animal = getRandomAnimal(world, random);
         this.spawnNPCAndSetHome(animal, world, i, j, k, 0);
         animal.func_110177_bN();
      }

   }

   public static EntityAnimal getRandomAnimal(World world, Random random) {
      int animal = random.nextInt(4);
      if (animal == 0) {
         return new EntityCow(world);
      } else if (animal == 1) {
         return new EntityPig(world);
      } else if (animal == 2) {
         return new EntitySheep(world);
      } else {
         return animal == 3 ? new EntityChicken(world) : null;
      }
   }
}
