package lotr.common.world.structure2;

import java.util.Random;
import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityBreeRuffian;
import lotr.common.entity.npc.LOTREntityRuffianBrute;
import lotr.common.entity.npc.LOTREntityRuffianSpy;
import lotr.common.entity.npc.LOTRNames;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityCreature;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenBreeRuffianHouse extends LOTRWorldGenBreeStructure {
   private String fixedName;

   public LOTRWorldGenBreeRuffianHouse(boolean flag) {
      super(flag);
   }

   public LOTRWorldGenBreeRuffianHouse setRuffianName(String name) {
      this.fixedName = name;
      return this;
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, 9);
      this.setupRandomBlocks(random);
      int i1;
      int i1;
      int k1;
      if (this.restrictions) {
         for(i1 = -7; i1 <= 8; ++i1) {
            for(i1 = -8; i1 <= 5; ++i1) {
               k1 = this.getTopBlock(world, i1, i1) - 1;
               if (!this.isSurface(world, i1, k1, i1)) {
                  return false;
               }
            }
         }
      }

      for(i1 = -3; i1 <= 8; ++i1) {
         for(i1 = -5; i1 <= 3; ++i1) {
            for(k1 = 1; k1 <= 8; ++k1) {
               this.setAir(world, i1, k1, i1);
            }
         }
      }

      for(i1 = -2; i1 <= 2; ++i1) {
         for(i1 = -8; i1 <= -6; ++i1) {
            for(k1 = 1; k1 <= 8; ++k1) {
               this.setAir(world, i1, k1, i1);
            }
         }
      }

      for(i1 = 0; i1 <= 7; ++i1) {
         for(i1 = 3; i1 <= 5; ++i1) {
            for(k1 = 1; k1 <= 8; ++k1) {
               this.setAir(world, i1, k1, i1);
            }
         }
      }

      for(i1 = -7; i1 <= -3; ++i1) {
         for(i1 = -4; i1 <= 2; ++i1) {
            for(k1 = 1; k1 <= 8; ++k1) {
               this.setAir(world, i1, k1, i1);
            }
         }
      }

      for(i1 = 0; i1 <= 5; ++i1) {
         for(i1 = -4; i1 <= 4; ++i1) {
            for(k1 = -2; k1 <= 0; ++k1) {
               this.setAir(world, i1, k1, i1);
            }
         }
      }

      for(i1 = -2; i1 <= -1; ++i1) {
         int k1 = 0;

         for(k1 = -2; k1 <= 0; ++k1) {
            this.setAir(world, i1, k1, k1);
         }
      }

      this.loadStrScan("bree_ruffian_house");
      this.associateBlockMetaAlias("BRICK", this.brickBlock, this.brickMeta);
      this.addBlockMetaAliasOption("COBBLE", 3, Blocks.field_150347_e, 0);
      this.addBlockMetaAliasOption("COBBLE", 1, Blocks.field_150341_Y, 0);
      this.addBlockMetaAliasOption("COBBLE_SLAB_INV", 3, Blocks.field_150333_U, 11);
      this.addBlockMetaAliasOption("COBBLE_SLAB_INV", 1, LOTRMod.slabSingleV, 12);
      this.addBlockAliasOption("COBBLE_STAIR", 3, Blocks.field_150446_ar);
      this.addBlockAliasOption("COBBLE_STAIR", 1, LOTRMod.stairsCobblestoneMossy);
      this.addBlockMetaAliasOption("COBBLE_WALL", 3, Blocks.field_150463_bK, 0);
      this.addBlockMetaAliasOption("COBBLE_WALL", 1, Blocks.field_150463_bK, 1);
      this.associateBlockMetaAlias("PLANK", this.plankBlock, this.plankMeta);
      this.associateBlockMetaAlias("PLANK_SLAB", this.plankSlabBlock, this.plankSlabMeta);
      this.associateBlockMetaAlias("PLANK_SLAB_INV", this.plankSlabBlock, this.plankSlabMeta | 8);
      this.associateBlockAlias("PLANK_STAIR", this.plankStairBlock);
      this.associateBlockMetaAlias("FENCE", this.fenceBlock, this.fenceMeta);
      this.associateBlockAlias("FENCE_GATE", this.fenceGateBlock);
      this.associateBlockAlias("DOOR", this.doorBlock);
      this.associateBlockAlias("TRAPDOOR", this.trapdoorBlock);
      this.associateBlockMetaAlias("BEAM", this.beamBlock, this.beamMeta);
      this.associateBlockMetaAlias("BEAM|4", this.beamBlock, this.beamMeta | 4);
      this.associateBlockMetaAlias("BEAM|8", this.beamBlock, this.beamMeta | 8);
      this.associateBlockMetaAlias("ROOF", this.roofBlock, this.roofMeta);
      this.associateBlockMetaAlias("ROOF_SLAB", this.roofSlabBlock, this.roofSlabMeta);
      this.associateBlockMetaAlias("ROOF_SLAB_INV", this.roofSlabBlock, this.roofSlabMeta | 8);
      this.associateBlockAlias("ROOF_STAIR", this.roofStairBlock);
      this.associateBlockMetaAlias("TABLE", this.tableBlock, 0);
      this.associateBlockMetaAlias("CARPET", this.carpetBlock, this.carpetMeta);
      this.addBlockMetaAliasOption("THATCH_FLOOR", 1, LOTRMod.thatchFloor, 0);
      this.setBlockAliasChance("THATCH_FLOOR", 0.2F);
      this.addBlockMetaAliasOption("LEAF_FLOOR", 1, LOTRMod.fallenLeaves, 0);
      this.setBlockAliasChance("LEAF_FLOOR", 0.3F);
      this.addBlockMetaAliasOption("WEB", 1, Blocks.field_150321_G, 0);
      this.setBlockAliasChance("WEB", 0.3F);
      this.addBlockMetaAliasOption("PATH", 10, Blocks.field_150349_c, 0);
      this.addBlockMetaAliasOption("PATH", 10, Blocks.field_150346_d, 1);
      this.addBlockMetaAliasOption("PATH", 10, LOTRMod.dirtPath, 0);
      this.addBlockMetaAliasOption("PATH", 5, Blocks.field_150347_e, 0);
      this.addBlockMetaAliasOption("PATH", 5, Blocks.field_150341_Y, 0);
      this.associateBlockMetaAlias("LEAF", Blocks.field_150362_t, 4);
      this.generateStrScan(world, random, 0, 0, 0);
      int maxSteps = true;

      int j1;
      int k1;
      int randPath;
      int j2;
      for(i1 = 4; i1 <= 6; ++i1) {
         for(k1 = 0; k1 < 12; ++k1) {
            j1 = -1 - k1;
            k1 = 5 + k1;
            if (this.isOpaque(world, i1, j1, k1)) {
               break;
            }

            randPath = random.nextInt(4);
            if (randPath == 0) {
               this.setBlockAndMetadata(world, i1, j1, k1, Blocks.field_150349_c, 0);
            } else if (randPath == 1) {
               this.setBlockAndMetadata(world, i1, j1, k1, Blocks.field_150346_d, 1);
            } else if (randPath == 2) {
               this.setBlockAndMetadata(world, i1, j1, k1, LOTRMod.dirtPath, 0);
            } else if (randPath == 3) {
               if (random.nextBoolean()) {
                  this.setBlockAndMetadata(world, i1, j1, k1, Blocks.field_150347_e, 0);
               } else {
                  this.setBlockAndMetadata(world, i1, j1, k1, Blocks.field_150341_Y, 0);
               }
            }

            this.setGrassToDirt(world, i1, j1 - 1, k1);

            for(j2 = j1 - 1; !this.isOpaque(world, i1, j2, k1) && this.getY(j2) >= 0; --j2) {
               this.setBlockAndMetadata(world, i1, j2, k1, Blocks.field_150346_d, 0);
               this.setGrassToDirt(world, i1, j2 - 1, k1);
            }
         }
      }

      for(i1 = 0; i1 < 12; ++i1) {
         int i1 = -5;
         j1 = 0 - i1;
         k1 = -5 - i1;
         if (this.isOpaque(world, i1, j1, k1)) {
            break;
         }

         randPath = random.nextInt(4);
         if (randPath == 0) {
            this.setBlockAndMetadata(world, i1, j1, k1, Blocks.field_150349_c, 0);
         } else if (randPath == 1) {
            this.setBlockAndMetadata(world, i1, j1, k1, Blocks.field_150346_d, 1);
         } else if (randPath == 2) {
            this.setBlockAndMetadata(world, i1, j1, k1, LOTRMod.dirtPath, 0);
         } else if (randPath == 3) {
            if (random.nextBoolean()) {
               this.setBlockAndMetadata(world, i1, j1, k1, Blocks.field_150347_e, 0);
            } else {
               this.setBlockAndMetadata(world, i1, j1, k1, Blocks.field_150341_Y, 0);
            }
         }

         this.setGrassToDirt(world, i1, j1 - 1, k1);

         for(j2 = j1 - 1; !this.isOpaque(world, i1, j2, k1) && this.getY(j2) >= 0; --j2) {
            this.setBlockAndMetadata(world, i1, j2, k1, Blocks.field_150346_d, 0);
            this.setGrassToDirt(world, i1, j2 - 1, k1);
         }
      }

      this.setBlockAndMetadata(world, 4, -2, -1, this.bedBlock, 3);
      this.setBlockAndMetadata(world, 3, -2, -1, this.bedBlock, 11);
      this.setBlockAndMetadata(world, 5, -2, 1, this.bedBlock, 9);
      this.setBlockAndMetadata(world, 4, -2, 1, this.bedBlock, 1);
      this.setBlockAndMetadata(world, 0, 5, 0, this.bedBlock, 3);
      this.setBlockAndMetadata(world, -1, 5, 0, this.bedBlock, 11);
      this.placePlateWithCertainty(world, random, 1, -1, -4, LOTRMod.ceramicPlateBlock, LOTRFoods.BREE);
      this.placeMug(world, random, 0, -1, -4, 0, LOTRFoods.BREE_DRINK);
      this.placeBarrel(world, random, 5, -2, -4, 5, LOTRFoods.BREE_DRINK);
      this.placeBarrel(world, random, 4, -2, -3, 2, LOTRFoods.BREE_DRINK);
      this.placeChest(world, random, 3, -2, -3, 2, LOTRChestContents.BREE_RUFFIAN_PIPEWEED);
      this.placeChest(world, random, -2, -2, 0, 4, LOTRChestContents.BREE_TREASURE);
      this.placeChest(world, random, 3, -2, 1, 2, LOTRChestContents.BREE_TREASURE);
      this.placePlateWithCertainty(world, random, 3, 2, -3, LOTRMod.plateBlock, LOTRFoods.BREE);
      this.placeMug(world, random, 3, 2, -2, 3, LOTRFoods.BREE_DRINK);
      this.placeChest(world, random, -1, 1, 1, 4, LOTRChestContents.BREE_HOUSE);
      this.placeChest(world, random, 1, 5, 1, 2, LOTRChestContents.BREE_HOUSE);

      for(i1 = -6; i1 <= -3; ++i1) {
         for(k1 = -3; k1 <= 1; ++k1) {
            int j1 = 1;
            Block gardenBlock = this.getBlock(world, i1, j1 - 1, k1);
            if ((gardenBlock == Blocks.field_150349_c || gardenBlock == Blocks.field_150346_d) && random.nextInt(3) == 0) {
               if (random.nextInt(3) == 0) {
                  this.setBlockAndMetadata(world, i1, j1, k1, Blocks.field_150398_cm, 2);
                  this.setBlockAndMetadata(world, i1, j1 + 1, k1, Blocks.field_150398_cm, 10);
               } else {
                  this.plantTallGrass(world, random, i1, j1, k1);
               }
            }
         }
      }

      LOTREntityBreeRuffian ruffian = random.nextInt(3) == 0 ? new LOTREntityRuffianBrute(world) : new LOTREntityRuffianSpy(world);
      if (this.fixedName != null) {
         ((LOTREntityBreeRuffian)ruffian).familyInfo.setName(this.fixedName);
      }

      this.spawnNPCAndSetHome((EntityCreature)ruffian, world, 0, 1, 0, 16);
      this.placeSign(world, 2, 2, -8, Blocks.field_150472_an, 9, LOTRNames.getBreeRuffianSign(random));
      return true;
   }
}
