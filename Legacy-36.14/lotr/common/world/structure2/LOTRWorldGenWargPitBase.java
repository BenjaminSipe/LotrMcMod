package lotr.common.world.structure2;

import java.util.Random;
import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.item.LOTRItemBanner;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class LOTRWorldGenWargPitBase extends LOTRWorldGenStructureBase2 {
   protected Block brickBlock;
   protected int brickMeta;
   protected Block brickSlabBlock;
   protected int brickSlabMeta;
   protected Block brickStairBlock;
   protected Block brickWallBlock;
   protected int brickWallMeta;
   protected Block pillarBlock;
   protected int pillarMeta;
   protected Block plankBlock;
   protected int plankMeta;
   protected Block plankSlabBlock;
   protected int plankSlabMeta;
   protected Block plankStairBlock;
   protected Block fenceBlock;
   protected int fenceMeta;
   protected Block beamBlock;
   protected int beamMeta;
   protected Block doorBlock;
   protected Block woolBlock;
   protected int woolMeta;
   protected Block carpetBlock;
   protected int carpetMeta;
   protected Block barsBlock;
   protected Block gateOrcBlock;
   protected Block gateMetalBlock;
   protected Block tableBlock;
   protected Block bedBlock;
   protected LOTRItemBanner.BannerType banner;
   protected LOTRChestContents chestContents;

   public LOTRWorldGenWargPitBase(boolean flag) {
      super(flag);
   }

   protected abstract LOTREntityNPC getOrc(World var1);

   protected abstract LOTREntityNPC getWarg(World var1);

   protected abstract void setOrcSpawner(LOTREntityNPCRespawner var1);

   protected abstract void setWargSpawner(LOTREntityNPCRespawner var1);

   protected void setupRandomBlocks(Random random) {
      this.plankBlock = LOTRMod.planks;
      this.plankMeta = 3;
      this.plankSlabBlock = LOTRMod.woodSlabSingle;
      this.plankSlabMeta = 3;
      this.plankStairBlock = LOTRMod.stairsCharred;
      this.fenceBlock = LOTRMod.fence;
      this.fenceMeta = 3;
      this.beamBlock = LOTRMod.woodBeam1;
      this.beamMeta = 3;
      this.doorBlock = LOTRMod.doorCharred;
      this.woolBlock = Blocks.field_150325_L;
      this.woolMeta = 12;
      this.carpetBlock = Blocks.field_150404_cg;
      this.carpetMeta = 12;
      this.barsBlock = LOTRMod.orcSteelBars;
      this.gateOrcBlock = LOTRMod.gateOrc;
      this.gateMetalBlock = LOTRMod.gateBronzeBars;
      this.bedBlock = LOTRMod.orcBed;
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, 8, -10);
      this.originY -= 4;
      this.setupRandomBlocks(random);
      int maxHeight;
      int i1;
      int i1;
      int l;
      if (this.restrictions) {
         int minHeight = 0;
         maxHeight = 0;

         for(i1 = -13; i1 <= 12; ++i1) {
            for(i1 = -12; i1 <= 14; ++i1) {
               l = this.getTopBlock(world, i1, i1) - 1;
               if (!this.isSurface(world, i1, l, i1)) {
                  return false;
               }

               if (l < minHeight) {
                  minHeight = l;
               }

               if (l > maxHeight) {
                  maxHeight = l;
               }
            }
         }

         if (maxHeight - minHeight > 12) {
            return false;
         }
      }

      int radius = 8;

      for(maxHeight = -radius; maxHeight <= radius; ++maxHeight) {
         for(i1 = -radius; i1 <= radius; ++i1) {
            if (maxHeight * maxHeight + i1 * i1 < radius * radius) {
               for(i1 = 0; i1 <= 12; ++i1) {
                  this.setAir(world, maxHeight, i1, i1);
               }
            }
         }
      }

      int r2 = 12;

      for(i1 = -r2; i1 <= r2; ++i1) {
         for(i1 = -r2; i1 <= r2; ++i1) {
            if (i1 * i1 + i1 * i1 < r2 * r2 && i1 >= -4 && i1 <= 4) {
               for(l = 0; l <= 12; ++l) {
                  this.setAir(world, i1, l, i1);
               }
            }
         }
      }

      for(i1 = -12; i1 <= -8; ++i1) {
         for(i1 = -7; i1 <= -4; ++i1) {
            if (i1 != -7 || i1 != -12 && i1 != -8) {
               for(l = 5; l <= 12; ++l) {
                  this.setAir(world, i1, l, i1);
               }
            }
         }
      }

      for(i1 = -3; i1 <= 3; ++i1) {
         for(i1 = 8; i1 <= 12; ++i1) {
            for(l = 7; l <= 11; ++l) {
               this.setAir(world, i1, l, i1);
            }
         }
      }

      for(i1 = -1; i1 <= 1; ++i1) {
         for(i1 = -11; i1 <= -6; ++i1) {
            for(l = 0; l <= 3; ++l) {
               this.setAir(world, i1, l, i1);
            }
         }
      }

      for(i1 = 6; i1 <= 11; ++i1) {
         for(i1 = -1; i1 <= 1; ++i1) {
            for(l = 0; l <= 3; ++l) {
               this.setAir(world, i1, l, i1);
            }
         }
      }

      this.loadStrScan("warg_pit");
      this.associateBlockMetaAlias("BRICK", this.brickBlock, this.brickMeta);
      this.associateBlockMetaAlias("BRICK_SLAB_INV", this.brickSlabBlock, this.brickSlabMeta | 8);
      this.associateBlockAlias("BRICK_STAIR", this.brickStairBlock);
      this.associateBlockMetaAlias("BRICK_WALL", this.brickWallBlock, this.brickWallMeta);
      this.associateBlockMetaAlias("PILLAR", this.pillarBlock, this.pillarMeta);
      this.associateBlockMetaAlias("PLANK", this.plankBlock, this.plankMeta);
      this.associateBlockMetaAlias("PLANK_SLAB", this.plankSlabBlock, this.plankSlabMeta);
      this.associateBlockMetaAlias("PLANK_SLAB_INV", this.plankSlabBlock, this.plankSlabMeta | 8);
      this.associateBlockAlias("PLANK_STAIR", this.plankStairBlock);
      this.associateBlockMetaAlias("FENCE", this.fenceBlock, this.fenceMeta);
      this.associateBlockMetaAlias("BEAM", this.beamBlock, this.beamMeta);
      this.associateBlockMetaAlias("BEAM|4", this.beamBlock, this.beamMeta | 4);
      this.associateBlockMetaAlias("BEAM|8", this.beamBlock, this.beamMeta | 8);
      this.associateBlockAlias("DOOR", this.doorBlock);
      this.associateBlockMetaAlias("WOOL", this.woolBlock, this.woolMeta);
      this.associateBlockMetaAlias("CARPET", this.carpetBlock, this.carpetMeta);
      this.associateGroundBlocks();
      this.associateBlockMetaAlias("BARS", this.barsBlock, 0);
      this.associateBlockAlias("GATE_ORC", this.gateOrcBlock);
      this.associateBlockAlias("GATE_METAL", this.gateMetalBlock);
      this.associateBlockMetaAlias("TABLE", this.tableBlock, 0);
      this.generateStrScan(world, random, 0, 0, 0);
      this.placeWallBanner(world, -7, 5, 0, this.banner, 1);
      this.placeWallBanner(world, 7, 5, 0, this.banner, 3);
      this.placeWallBanner(world, 0, 5, -7, this.banner, 0);
      this.placeWallBanner(world, 0, 5, 7, this.banner, 2);
      this.placeOrcTorch(world, 2, 4, -5);
      this.placeOrcTorch(world, -2, 4, -5);
      this.placeOrcTorch(world, 5, 4, -2);
      this.placeOrcTorch(world, -5, 4, -2);
      this.placeOrcTorch(world, 5, 4, 2);
      this.placeOrcTorch(world, -5, 4, 2);
      this.placeOrcTorch(world, 2, 4, 5);
      this.placeOrcTorch(world, -2, 4, 5);
      this.placeOrcTorch(world, 1, 7, 8);
      this.placeOrcTorch(world, -1, 7, 8);
      this.placeOrcTorch(world, 4, 8, -4);
      this.placeOrcTorch(world, -4, 8, -4);
      this.placeOrcTorch(world, 4, 8, 4);
      this.placeOrcTorch(world, -4, 8, 4);
      this.placeOrcTorch(world, -8, 10, -4);
      this.placeOrcTorch(world, -12, 10, -4);
      this.placeChest(world, random, -7, 1, 0, 4, this.chestContents);
      this.placeChest(world, random, 1, 7, 12, 2, this.chestContents);
      this.setBlockAndMetadata(world, -2, 7, 9, this.bedBlock, 3);
      this.setBlockAndMetadata(world, -3, 7, 9, this.bedBlock, 11);
      this.setBlockAndMetadata(world, -2, 7, 11, this.bedBlock, 3);
      this.setBlockAndMetadata(world, -3, 7, 11, this.bedBlock, 11);
      this.placeBarrel(world, random, 3, 8, 11, 5, LOTRFoods.ORC_DRINK);
      this.placeMug(world, random, 3, 8, 10, 1, LOTRFoods.ORC_DRINK);
      this.placePlateWithCertainty(world, random, 3, 8, 9, LOTRMod.woodPlateBlock, LOTRFoods.ORC);
      int maxStep = 12;

      int j1;
      int k1;
      int j2;
      for(i1 = -1; i1 <= 1; ++i1) {
         for(l = 0; l < 2; ++l) {
            j1 = 5 - l;
            k1 = -9 - l;
            if (this.isSideSolid(world, i1, j1, k1, ForgeDirection.UP)) {
               break;
            }

            this.setBlockAndMetadata(world, i1, j1, k1, this.brickStairBlock, 2);
            this.setGrassToDirt(world, i1, j1 - 1, k1);

            for(j2 = j1 - 1; !this.isSideSolid(world, i1, j2, k1, ForgeDirection.UP) && this.getY(j2) >= 0; --j2) {
               this.setBlockAndMetadata(world, i1, j2, k1, this.brickBlock, this.brickMeta);
               this.setGrassToDirt(world, i1, j2 - 1, k1);
            }
         }
      }

      for(i1 = -1; i1 <= 1; ++i1) {
         for(l = 0; l < maxStep; ++l) {
            j1 = 3 - l;
            k1 = -13 - l;
            if (this.isOpaque(world, i1, j1, k1)) {
               break;
            }

            this.setBlockAndMetadata(world, i1, j1, k1, this.brickStairBlock, 2);
            this.setGrassToDirt(world, i1, j1 - 1, k1);

            for(j2 = j1 - 1; !this.isOpaque(world, i1, j2, k1) && this.getY(j2) >= 0; --j2) {
               this.setBlockAndMetadata(world, i1, j2, k1, this.brickBlock, this.brickMeta);
               this.setGrassToDirt(world, i1, j2 - 1, k1);
            }
         }
      }

      i1 = 2 + random.nextInt(5);

      for(l = 0; l < i1; ++l) {
         LOTREntityNPC warg = this.getWarg(world);
         this.spawnNPCAndSetHome(warg, world, 0, 1, 0, 8);
      }

      LOTREntityNPC orc = this.getOrc(world);
      this.spawnNPCAndSetHome(orc, world, 0, 1, 0, 24);
      LOTREntityNPCRespawner wargSpawner = new LOTREntityNPCRespawner(world);
      this.setWargSpawner(wargSpawner);
      wargSpawner.setCheckRanges(12, -8, 16, 8);
      wargSpawner.setSpawnRanges(4, -4, 4, 24);
      this.placeNPCRespawner(wargSpawner, world, 0, 0, 0);
      LOTREntityNPCRespawner orcSpawner = new LOTREntityNPCRespawner(world);
      this.setOrcSpawner(orcSpawner);
      orcSpawner.setCheckRanges(32, -12, 20, 16);
      orcSpawner.setSpawnRanges(16, -4, 8, 16);
      this.placeNPCRespawner(orcSpawner, world, 0, 0, 0);
      return true;
   }

   protected void associateGroundBlocks() {
      this.addBlockMetaAliasOption("GROUND", 4, Blocks.field_150346_d, 1);
      this.addBlockMetaAliasOption("GROUND", 4, LOTRMod.dirtPath, 0);
      this.addBlockMetaAliasOption("GROUND", 4, Blocks.field_150351_n, 0);
      this.addBlockMetaAliasOption("GROUND", 4, Blocks.field_150347_e, 0);
      this.addBlockMetaAliasOption("GROUND_SLAB", 4, LOTRMod.slabSingleDirt, 0);
      this.addBlockMetaAliasOption("GROUND_SLAB", 4, LOTRMod.slabSingleDirt, 1);
      this.addBlockMetaAliasOption("GROUND_SLAB", 4, LOTRMod.slabSingleGravel, 0);
      this.addBlockMetaAliasOption("GROUND_SLAB", 4, Blocks.field_150333_U, 3);
      this.addBlockMetaAliasOption("GROUND_COVER", 1, LOTRMod.thatchFloor, 0);
      this.setBlockAliasChance("GROUND_COVER", 0.25F);
   }

   protected void placeOrcTorch(World world, int i, int j, int k) {
      this.setBlockAndMetadata(world, i, j, k, LOTRMod.orcTorch, 0);
      this.setBlockAndMetadata(world, i, j + 1, k, LOTRMod.orcTorch, 1);
   }
}
