package lotr.common.world.structure2;

import java.util.Random;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

public abstract class LOTRWorldGenDaleStructure extends LOTRWorldGenStructureBase2 {
   protected Block brickBlock;
   protected int brickMeta;
   protected Block brickSlabBlock;
   protected int brickSlabMeta;
   protected Block brickStairBlock;
   protected Block brickWallBlock;
   protected int brickWallMeta;
   protected Block pillarBlock;
   protected int pillarMeta;
   protected Block floorBlock;
   protected int floorMeta;
   protected Block plankBlock;
   protected int plankMeta;
   protected Block plankSlabBlock;
   protected int plankSlabMeta;
   protected Block plankStairBlock;
   protected Block fenceBlock;
   protected int fenceMeta;
   protected Block fenceGateBlock;
   protected Block woodBlock;
   protected int woodMeta;
   protected Block woodBeamBlock;
   protected int woodBeamMeta;
   protected Block doorBlock;
   protected Block trapdoorBlock;
   protected Block roofBlock;
   protected int roofMeta;
   protected Block roofSlabBlock;
   protected int roofSlabMeta;
   protected Block roofStairBlock;
   protected Block barsBlock;
   protected Block plateBlock;

   public LOTRWorldGenDaleStructure(boolean flag) {
      super(flag);
   }

   protected void setupRandomBlocks(Random random) {
      this.brickBlock = LOTRMod.brick5;
      this.brickMeta = 1;
      this.brickSlabBlock = LOTRMod.slabSingle9;
      this.brickSlabMeta = 6;
      this.brickStairBlock = LOTRMod.stairsDaleBrick;
      this.brickWallBlock = LOTRMod.wall3;
      this.brickWallMeta = 9;
      this.pillarBlock = LOTRMod.pillar2;
      this.pillarMeta = 5;
      this.floorBlock = Blocks.field_150347_e;
      this.floorMeta = 0;
      int randomWood = random.nextInt(3);
      if (randomWood == 0) {
         this.plankBlock = Blocks.field_150344_f;
         this.plankMeta = 1;
         this.plankSlabBlock = Blocks.field_150376_bx;
         this.plankSlabMeta = 1;
         this.plankStairBlock = Blocks.field_150485_bF;
         this.fenceBlock = Blocks.field_150422_aJ;
         this.fenceMeta = 0;
         this.fenceGateBlock = Blocks.field_150396_be;
         this.woodBlock = Blocks.field_150364_r;
         this.woodMeta = 0;
         this.woodBeamBlock = LOTRMod.woodBeamV1;
         this.woodBeamMeta = 0;
         this.doorBlock = LOTRMod.doorSpruce;
         this.trapdoorBlock = LOTRMod.trapdoorSpruce;
      } else if (randomWood == 1) {
         this.plankBlock = LOTRMod.planks2;
         this.plankMeta = 4;
         this.plankSlabBlock = LOTRMod.woodSlabSingle3;
         this.plankSlabMeta = 4;
         this.plankStairBlock = LOTRMod.stairsPine;
         this.fenceBlock = LOTRMod.fence2;
         this.fenceMeta = 4;
         this.fenceGateBlock = LOTRMod.fenceGatePine;
         this.woodBlock = LOTRMod.wood5;
         this.woodMeta = 0;
         this.woodBeamBlock = LOTRMod.woodBeam5;
         this.woodBeamMeta = 0;
         this.doorBlock = LOTRMod.doorPine;
         this.trapdoorBlock = LOTRMod.trapdoorPine;
      } else if (randomWood == 2) {
         this.plankBlock = LOTRMod.planks2;
         this.plankMeta = 3;
         this.plankSlabBlock = LOTRMod.woodSlabSingle3;
         this.plankSlabMeta = 3;
         this.plankStairBlock = LOTRMod.stairsFir;
         this.fenceBlock = LOTRMod.fence2;
         this.fenceMeta = 3;
         this.fenceGateBlock = LOTRMod.fenceGateFir;
         this.woodBlock = LOTRMod.wood4;
         this.woodMeta = 3;
         this.woodBeamBlock = LOTRMod.woodBeam4;
         this.woodBeamMeta = 3;
         this.doorBlock = LOTRMod.doorFir;
         this.trapdoorBlock = LOTRMod.trapdoorFir;
      }

      int randomClay = random.nextInt(4);
      if (randomClay == 0) {
         this.roofBlock = LOTRMod.clayTileDyed;
         this.roofMeta = 1;
         this.roofSlabBlock = LOTRMod.slabClayTileDyedSingle;
         this.roofSlabMeta = 1;
         this.roofStairBlock = LOTRMod.stairsClayTileDyedOrange;
      } else if (randomClay == 1) {
         this.roofBlock = LOTRMod.clayTileDyed;
         this.roofMeta = 14;
         this.roofSlabBlock = LOTRMod.slabClayTileDyedSingle2;
         this.roofSlabMeta = 6;
         this.roofStairBlock = LOTRMod.stairsClayTileDyedRed;
      } else if (randomClay == 2) {
         this.roofBlock = LOTRMod.clayTileDyed;
         this.roofMeta = 12;
         this.roofSlabBlock = LOTRMod.slabClayTileDyedSingle2;
         this.roofSlabMeta = 4;
         this.roofStairBlock = LOTRMod.stairsClayTileDyedBrown;
      } else if (randomClay == 3) {
         this.roofBlock = LOTRMod.clayTileDyed;
         this.roofMeta = 11;
         this.roofSlabBlock = LOTRMod.slabClayTileDyedSingle2;
         this.roofSlabMeta = 3;
         this.roofStairBlock = LOTRMod.stairsClayTileDyedBlue;
      }

      if (random.nextInt(3) == 0) {
         this.barsBlock = Blocks.field_150411_aY;
      } else {
         this.barsBlock = LOTRMod.bronzeBars;
      }

      if (random.nextBoolean()) {
         if (random.nextBoolean()) {
            this.plateBlock = LOTRMod.plateBlock;
         } else {
            this.plateBlock = LOTRMod.ceramicPlateBlock;
         }
      } else {
         this.plateBlock = LOTRMod.woodPlateBlock;
      }

   }
}
