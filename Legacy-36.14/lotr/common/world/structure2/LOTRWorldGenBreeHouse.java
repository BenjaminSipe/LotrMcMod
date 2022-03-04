package lotr.common.world.structure2;

import java.util.Random;
import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityBreeMan;
import lotr.common.entity.npc.LOTRNames;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTRWorldGenBreeHouse extends LOTRWorldGenBreeStructure {
   public LOTRWorldGenBreeHouse(boolean flag) {
      super(flag);
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

      for(i1 = 2; i1 <= 7; ++i1) {
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

      this.loadStrScan("bree_house");
      this.associateBlockMetaAlias("BRICK", this.brickBlock, this.brickMeta);
      this.associateBlockMetaAlias("FLOOR", this.floorBlock, this.floorMeta);
      this.associateBlockMetaAlias("STONE_WALL", this.stoneWallBlock, this.stoneWallMeta);
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
      this.addBlockMetaAliasOption("PATH", 5, Blocks.field_150349_c, 0);
      this.addBlockMetaAliasOption("PATH", 5, Blocks.field_150346_d, 1);
      this.addBlockMetaAliasOption("PATH", 5, LOTRMod.dirtPath, 0);
      this.addBlockMetaAliasOption("PATH", 5, Blocks.field_150347_e, 0);
      this.associateBlockMetaAlias("LEAF", Blocks.field_150362_t, 4);
      this.generateStrScan(world, random, 0, 0, 0);
      int maxSteps = true;

      int j1;
      int k1;
      int randPath;
      int j2;
      for(i1 = 3; i1 <= 6; ++i1) {
         for(k1 = 0; k1 < 12; ++k1) {
            j1 = -1 - k1;
            k1 = 6 + k1;
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
               this.setBlockAndMetadata(world, i1, j1, k1, Blocks.field_150347_e, 0);
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
            this.setBlockAndMetadata(world, i1, j1, k1, Blocks.field_150347_e, 0);
         }

         this.setGrassToDirt(world, i1, j1 - 1, k1);

         for(j2 = j1 - 1; !this.isOpaque(world, i1, j2, k1) && this.getY(j2) >= 0; --j2) {
            this.setBlockAndMetadata(world, i1, j2, k1, Blocks.field_150346_d, 0);
            this.setGrassToDirt(world, i1, j2 - 1, k1);
         }
      }

      for(i1 = -6; i1 <= -3; ++i1) {
         for(k1 = -3; k1 <= 1; ++k1) {
            int j1 = 1;
            if (this.getBlock(world, i1, j1 - 1, k1) == Blocks.field_150349_c && random.nextInt(4) == 0) {
               this.plantFlower(world, random, i1, j1, k1);
            }
         }
      }

      this.placeRandomFlowerPot(world, random, 6, 1, 3);
      this.placeRandomFlowerPot(world, random, 3, 1, 3);
      this.placeRandomFlowerPot(world, random, -1, 5, -1);
      this.placeRandomFlowerPot(world, random, 2, 5, 1);
      this.plantFlower(world, random, 0, 2, 3);
      this.plantFlower(world, random, 8, 6, -1);
      this.placeChest(world, random, -1, 1, 1, 4, LOTRChestContents.BREE_HOUSE);
      this.placeChest(world, random, 1, 5, 1, 2, LOTRChestContents.BREE_HOUSE);
      this.placeMug(world, random, 3, 2, -2, 3, LOTRFoods.BREE_DRINK);
      this.placePlateWithCertainty(world, random, 3, 2, -3, LOTRMod.plateBlock, LOTRFoods.BREE);
      this.setBlockAndMetadata(world, 0, 5, 0, this.bedBlock, 3);
      this.setBlockAndMetadata(world, -1, 5, 0, this.bedBlock, 11);
      if (random.nextBoolean()) {
         this.spawnItemFrame(world, 2, 3, 0, 3, new ItemStack(Items.field_151113_aN));
      }

      String[] breeNames = LOTRNames.getBreeCoupleAndHomeNames(random);
      LOTREntityBreeMan man = new LOTREntityBreeMan(world);
      man.familyInfo.setMale(true);
      man.familyInfo.setName(breeNames[0]);
      this.spawnNPCAndSetHome(man, world, 0, 1, 0, 16);
      LOTREntityBreeMan woman = new LOTREntityBreeMan(world);
      woman.familyInfo.setMale(false);
      woman.familyInfo.setName(breeNames[1]);
      this.spawnNPCAndSetHome(woman, world, 0, 1, 0, 16);
      this.placeSign(world, 2, 2, -8, Blocks.field_150472_an, 9, new String[]{"", breeNames[2], breeNames[3], ""});
      return true;
   }
}
