package lotr.common.world.structure2;

import java.util.Random;
import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.entity.animal.LOTREntityHorse;
import lotr.common.entity.npc.LOTREntityBreeMan;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenBreeStable extends LOTRWorldGenBreeStructure {
   public LOTRWorldGenBreeStable(boolean flag) {
      super(flag);
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, 6);
      this.setupRandomBlocks(random);
      int i1;
      int i1;
      int i1;
      if (this.restrictions) {
         for(i1 = -9; i1 <= 9; ++i1) {
            for(i1 = -5; i1 <= 9; ++i1) {
               i1 = this.getTopBlock(world, i1, i1) - 1;
               if (!this.isSurface(world, i1, i1, i1)) {
                  return false;
               }
            }
         }
      }

      for(i1 = -8; i1 <= 8; ++i1) {
         for(i1 = -5; i1 <= 5; ++i1) {
            for(i1 = 1; i1 <= 8; ++i1) {
               this.setAir(world, i1, i1, i1);
            }
         }
      }

      for(i1 = -5; i1 <= 5; ++i1) {
         for(i1 = 6; i1 <= 8; ++i1) {
            for(i1 = 1; i1 <= 4; ++i1) {
               this.setAir(world, i1, i1, i1);
            }
         }
      }

      for(i1 = -2; i1 <= 2; ++i1) {
         if (i1 != 0) {
            int k1 = -5;

            for(i1 = 1; i1 <= 3; ++i1) {
               this.setAir(world, i1, i1, k1);
            }
         }
      }

      int maxSteps = true;

      int j1;
      int j2;
      for(i1 = -2; i1 <= 2; ++i1) {
         if (i1 != 0) {
            for(i1 = 0; i1 < 12; ++i1) {
               j1 = 0 - i1;
               int k1 = -5 - i1;
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
      }

      for(i1 = 1; i1 <= 2; ++i1) {
         this.setAir(world, 5, i1, 6);
      }

      for(i1 = 0; i1 < 12; ++i1) {
         i1 = 5 + i1;
         j1 = 0 - i1;
         int k1 = 6;
         if (this.isOpaque(world, i1, j1, k1)) {
            break;
         }

         this.setBlockAndMetadata(world, i1, j1, k1, Blocks.field_150446_ar, 0);
         this.setGrassToDirt(world, i1, j1 - 1, k1);

         for(j2 = j1 - 1; !this.isOpaque(world, i1, j2, k1) && this.getY(j2) >= 0; --j2) {
            this.setBlockAndMetadata(world, i1, j2, k1, Blocks.field_150347_e, 0);
            this.setGrassToDirt(world, i1, j2 - 1, k1);
         }
      }

      this.loadStrScan("bree_stable");
      this.associateBlockMetaAlias("BRICK", this.brickBlock, this.brickMeta);
      this.associateBlockMetaAlias("COBBLE", Blocks.field_150347_e, 0);
      this.associateBlockMetaAlias("COBBLE_WALL", Blocks.field_150463_bK, 0);
      this.associateBlockMetaAlias("PLANK_SLAB_INV", this.plankSlabBlock, this.plankSlabMeta | 8);
      this.associateBlockAlias("PLANK_STAIR", this.plankStairBlock);
      this.associateBlockMetaAlias("FENCE", this.fenceBlock, this.fenceMeta);
      this.associateBlockAlias("FENCE_GATE", this.fenceGateBlock);
      this.associateBlockAlias("DOOR", this.doorBlock);
      this.associateBlockMetaAlias("BEAM", this.beamBlock, this.beamMeta);
      this.associateBlockMetaAlias("BEAM|4", this.beamBlock, this.beamMeta | 4);
      this.associateBlockMetaAlias("BEAM|8", this.beamBlock, this.beamMeta | 8);
      this.associateBlockMetaAlias("ROOF", this.roofBlock, this.roofMeta);
      this.associateBlockMetaAlias("ROOF_SLAB", this.roofSlabBlock, this.roofSlabMeta);
      this.associateBlockMetaAlias("ROOF_SLAB_INV", this.roofSlabBlock, this.roofSlabMeta | 8);
      this.associateBlockAlias("ROOF_STAIR", this.roofStairBlock);
      this.addBlockMetaAliasOption("GROUND", 1, Blocks.field_150351_n, 0);
      this.addBlockMetaAliasOption("GROUND", 1, Blocks.field_150349_c, 0);
      this.addBlockMetaAliasOption("GROUND", 1, Blocks.field_150346_d, 1);
      this.addBlockMetaAliasOption("GROUND", 1, LOTRMod.dirtPath, 0);
      this.addBlockMetaAliasOption("THATCH_FLOOR", 1, LOTRMod.thatchFloor, 0);
      this.setBlockAliasChance("THATCH_FLOOR", 0.15F);
      this.associateBlockMetaAlias("LEAF", Blocks.field_150362_t, 4);
      this.generateStrScan(world, random, 0, 0, 0);
      this.setBlockAndMetadata(world, -3, 1, 6, this.bedBlock, 2);
      this.setBlockAndMetadata(world, -3, 1, 5, this.bedBlock, 10);
      this.placeRandomFlowerPot(world, random, 3, 2, 5);
      this.placePlateWithCertainty(world, random, 1, 2, 7, LOTRMod.ceramicPlateBlock, LOTRFoods.BREE);
      this.placeMug(world, random, 0, 2, 7, 3, LOTRFoods.BREE_DRINK);
      this.placeBarrel(world, random, -1, 2, 7, 2, LOTRFoods.BREE_DRINK);
      this.placeChest(world, random, -3, 1, 7, 4, LOTRChestContents.BREE_HOUSE);
      this.placeWeaponRack(world, 0, 2, 3, 6, this.getRandomBreeWeapon(random));
      LOTREntityBreeMan stabler = new LOTREntityBreeMan(world);
      this.spawnNPCAndSetHome(stabler, world, 0, 1, -1, 16);
      this.spawnHorse(world, random, -6, 1, -2);
      this.spawnHorse(world, random, 6, 1, -2);
      this.spawnHorse(world, random, -6, 1, 2);
      this.spawnHorse(world, random, 6, 1, 2);
      return true;
   }

   private void spawnHorse(World world, Random random, int i, int j, int k) {
      int horses = 1 + random.nextInt(2);

      for(int l = 0; l < horses; ++l) {
         LOTREntityHorse horse = new LOTREntityHorse(world);
         this.spawnNPCAndSetHome(horse, world, i, j, k, 0);
         horse.func_110214_p(0);
         horse.saddleMountForWorldGen();
         horse.func_110177_bN();
      }

   }
}
