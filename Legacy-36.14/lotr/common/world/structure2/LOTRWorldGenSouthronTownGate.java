package lotr.common.world.structure2;

import java.util.Random;
import lotr.common.entity.npc.LOTRNames;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenSouthronTownGate extends LOTRWorldGenSouthronStructure {
   private String[] signText = LOTRNames.getHaradVillageName(new Random());

   public LOTRWorldGenSouthronTownGate(boolean flag) {
      super(flag);
   }

   public LOTRWorldGenSouthronTownGate setSignText(String[] s) {
      this.signText = s;
      return this;
   }

   protected boolean canUseRedBricks() {
      return false;
   }

   protected boolean forceCedarWood() {
      return true;
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, 4);
      this.setupRandomBlocks(random);
      int i1;
      int step;
      int i1;
      if (this.restrictions) {
         for(i1 = -8; i1 <= 8; ++i1) {
            for(step = -3; step <= 3; ++step) {
               i1 = this.getTopBlock(world, i1, step) - 1;
               if (!this.isSurface(world, i1, i1, step)) {
                  return false;
               }
            }
         }
      }

      for(i1 = -8; i1 <= 8; ++i1) {
         for(step = -3; step <= 3; ++step) {
            for(i1 = 1; i1 <= 12; ++i1) {
               this.setAir(world, i1, i1, step);
            }
         }
      }

      this.loadStrScan("southron_town_gate");
      this.associateBlockMetaAlias("STONE", this.stoneBlock, this.stoneMeta);
      this.associateBlockAlias("STONE_STAIR", this.stoneStairBlock);
      this.associateBlockMetaAlias("BRICK", this.brickBlock, this.brickMeta);
      this.associateBlockMetaAlias("BRICK_SLAB", this.brickSlabBlock, this.brickSlabMeta);
      this.associateBlockMetaAlias("BRICK_SLAB_INV", this.brickSlabBlock, this.brickSlabMeta | 8);
      this.associateBlockAlias("BRICK_STAIR", this.brickStairBlock);
      this.associateBlockMetaAlias("BRICK_WALL", this.brickWallBlock, this.brickWallMeta);
      this.associateBlockMetaAlias("PILLAR", this.pillarBlock, this.pillarMeta);
      this.associateBlockMetaAlias("BRICK2", this.brick2Block, this.brick2Meta);
      this.associateBlockMetaAlias("BRICK2_SLAB", this.brick2SlabBlock, this.brick2SlabMeta);
      this.associateBlockMetaAlias("BRICK2_SLAB_INV", this.brick2SlabBlock, this.brick2SlabMeta | 8);
      this.associateBlockAlias("PLANK_STAIR", this.plankStairBlock);
      this.associateBlockMetaAlias("FENCE", this.fenceBlock, this.fenceMeta);
      this.associateBlockMetaAlias("BEAM", this.woodBeamBlock, this.woodBeamMeta);
      this.associateBlockMetaAlias("BEAM|4", this.woodBeamBlock, this.woodBeamMeta4);
      this.associateBlockAlias("GATE_METAL", this.gateMetalBlock);
      this.generateStrScan(world, random, 0, 0, 0);
      if (this.signText != null) {
         this.placeSign(world, -3, 3, -4, Blocks.field_150444_as, 2, this.signText);
         this.placeSign(world, 3, 3, -4, Blocks.field_150444_as, 2, this.signText);
      }

      this.placeWallBanner(world, -6, 4, -2, this.bannerType, 2);
      this.placeWallBanner(world, 6, 4, -2, this.bannerType, 2);
      int maxSteps = true;

      int j1;
      byte k1;
      int j2;
      for(step = 0; step < 12; ++step) {
         i1 = -7 - step;
         j1 = 5 - step;
         k1 = 2;
         if (this.isOpaque(world, i1, j1, k1)) {
            break;
         }

         if (j1 <= 1) {
            this.setBlockAndMetadata(world, i1, j1, k1, this.stoneStairBlock, 1);
         } else {
            this.setBlockAndMetadata(world, i1, j1, k1, this.brickStairBlock, 1);
         }

         this.setGrassToDirt(world, i1, j1 - 1, k1);

         for(j2 = j1 - 1; !this.isOpaque(world, i1, j2, k1) && this.getY(j2) >= 0; --j2) {
            if (j2 <= 1) {
               this.setBlockAndMetadata(world, i1, j2, k1, this.stoneBlock, this.stoneMeta);
            } else {
               this.setBlockAndMetadata(world, i1, j2, k1, this.brickBlock, this.brickMeta);
            }

            this.setGrassToDirt(world, i1, j2 - 1, k1);
         }
      }

      for(step = 0; step < 12; ++step) {
         i1 = 7 + step;
         j1 = 5 - step;
         k1 = 2;
         if (this.isOpaque(world, i1, j1, k1)) {
            break;
         }

         if (j1 <= 1) {
            this.setBlockAndMetadata(world, i1, j1, k1, this.stoneStairBlock, 0);
         } else {
            this.setBlockAndMetadata(world, i1, j1, k1, this.brickStairBlock, 0);
         }

         this.setGrassToDirt(world, i1, j1 - 1, k1);

         for(j2 = j1 - 1; !this.isOpaque(world, i1, j2, k1) && this.getY(j2) >= 0; --j2) {
            if (j2 <= 1) {
               this.setBlockAndMetadata(world, i1, j2, k1, this.stoneBlock, this.stoneMeta);
            } else {
               this.setBlockAndMetadata(world, i1, j2, k1, this.brickBlock, this.brickMeta);
            }

            this.setGrassToDirt(world, i1, j2 - 1, k1);
         }
      }

      return true;
   }
}
