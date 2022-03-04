package lotr.common.world.structure2;

import java.util.Random;
import lotr.common.LOTRMod;
import lotr.common.item.LOTRItemBanner;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public abstract class LOTRWorldGenEasterlingStructure extends LOTRWorldGenStructureBase2 {
   protected Block brickBlock;
   protected int brickMeta;
   protected Block brickSlabBlock;
   protected int brickSlabMeta;
   protected Block brickStairBlock;
   protected Block brickWallBlock;
   protected int brickWallMeta;
   protected Block brickCarvedBlock;
   protected int brickCarvedMeta;
   protected Block brickFloweryBlock;
   protected int brickFloweryMeta;
   protected Block brickFlowerySlabBlock;
   protected int brickFlowerySlabMeta;
   protected Block brickGoldBlock;
   protected int brickGoldMeta;
   protected Block brickRedBlock;
   protected int brickRedMeta;
   protected Block brickRedSlabBlock;
   protected int brickRedSlabMeta;
   protected Block brickRedStairBlock;
   protected Block brickRedWallBlock;
   protected int brickRedWallMeta;
   protected Block brickRedCarvedBlock;
   protected int brickRedCarvedMeta;
   protected Block pillarBlock;
   protected int pillarMeta;
   protected Block pillarRedBlock;
   protected int pillarRedMeta;
   protected Block logBlock;
   protected int logMeta;
   protected Block plankBlock;
   protected int plankMeta;
   protected Block plankSlabBlock;
   protected int plankSlabMeta;
   protected Block plankStairBlock;
   protected Block fenceBlock;
   protected int fenceMeta;
   protected Block fenceGateBlock;
   protected Block woodBeamBlock;
   protected int woodBeamMeta;
   protected Block doorBlock;
   protected Block trapdoorBlock;
   protected Block roofBlock;
   protected int roofMeta;
   protected Block roofSlabBlock;
   protected int roofSlabMeta;
   protected Block roofStairBlock;
   protected Block roofWallBlock;
   protected int roofWallMeta;
   protected Block barsBlock;
   protected Block tableBlock;
   protected Block gateBlock;
   protected Block bedBlock;
   protected Block plateBlock;
   protected Block cropBlock;
   protected int cropMeta;
   protected Item seedItem;
   protected LOTRItemBanner.BannerType bannerType;
   protected LOTRChestContents chestContents;

   public LOTRWorldGenEasterlingStructure(boolean flag) {
      super(flag);
   }

   protected boolean useTownBlocks() {
      return false;
   }

   protected void setupRandomBlocks(Random random) {
      this.brickBlock = LOTRMod.brick5;
      this.brickMeta = 11;
      this.brickSlabBlock = LOTRMod.slabSingle12;
      this.brickSlabMeta = 0;
      this.brickStairBlock = LOTRMod.stairsRhunBrick;
      this.brickWallBlock = LOTRMod.wall3;
      this.brickWallMeta = 15;
      this.brickCarvedBlock = LOTRMod.brick5;
      this.brickCarvedMeta = 12;
      this.brickFloweryBlock = LOTRMod.brick5;
      this.brickFloweryMeta = 15;
      this.brickFlowerySlabBlock = LOTRMod.slabSingle12;
      this.brickFlowerySlabMeta = 3;
      this.brickGoldBlock = LOTRMod.brick6;
      this.brickGoldMeta = 0;
      this.brickRedBlock = LOTRMod.brick6;
      this.brickRedMeta = 1;
      this.brickRedSlabBlock = LOTRMod.slabSingle12;
      this.brickRedSlabMeta = 5;
      this.brickRedStairBlock = LOTRMod.stairsRhunBrickRed;
      this.brickRedWallBlock = LOTRMod.wall4;
      this.brickRedWallMeta = 13;
      this.brickRedCarvedBlock = LOTRMod.brick6;
      this.brickRedCarvedMeta = 2;
      this.pillarBlock = LOTRMod.pillar2;
      this.pillarMeta = 8;
      this.pillarRedBlock = LOTRMod.pillar2;
      this.pillarRedMeta = 9;
      int randomCrop;
      if (random.nextBoolean()) {
         this.logBlock = LOTRMod.wood8;
         this.logMeta = 1;
         this.plankBlock = LOTRMod.planks3;
         this.plankMeta = 1;
         this.plankSlabBlock = LOTRMod.woodSlabSingle5;
         this.plankSlabMeta = 1;
         this.plankStairBlock = LOTRMod.stairsRedwood;
         this.fenceBlock = LOTRMod.fence3;
         this.fenceMeta = 1;
         this.fenceGateBlock = LOTRMod.fenceGateRedwood;
         this.woodBeamBlock = LOTRMod.woodBeam8;
         this.woodBeamMeta = 1;
         this.doorBlock = LOTRMod.doorRedwood;
         this.trapdoorBlock = LOTRMod.trapdoorRedwood;
      } else {
         randomCrop = random.nextInt(4);
         if (randomCrop == 0) {
            this.logBlock = Blocks.field_150364_r;
            this.logMeta = 0;
            this.plankBlock = Blocks.field_150344_f;
            this.plankMeta = 0;
            this.plankSlabBlock = Blocks.field_150376_bx;
            this.plankSlabMeta = 0;
            this.plankStairBlock = Blocks.field_150476_ad;
            this.fenceBlock = Blocks.field_150422_aJ;
            this.fenceMeta = 0;
            this.fenceGateBlock = Blocks.field_150396_be;
            this.woodBeamBlock = LOTRMod.woodBeamV1;
            this.woodBeamMeta = 0;
            this.doorBlock = Blocks.field_150466_ao;
            this.trapdoorBlock = Blocks.field_150415_aT;
         } else if (randomCrop == 1) {
            this.logBlock = LOTRMod.wood2;
            this.logMeta = 1;
            this.plankBlock = LOTRMod.planks;
            this.plankMeta = 9;
            this.plankSlabBlock = LOTRMod.woodSlabSingle2;
            this.plankSlabMeta = 1;
            this.plankStairBlock = LOTRMod.stairsBeech;
            this.fenceBlock = LOTRMod.fence;
            this.fenceMeta = 9;
            this.fenceGateBlock = LOTRMod.fenceGateBeech;
            this.woodBeamBlock = LOTRMod.woodBeam2;
            this.woodBeamMeta = 1;
            this.doorBlock = LOTRMod.doorBeech;
            this.trapdoorBlock = LOTRMod.trapdoorBeech;
         } else if (randomCrop == 2) {
            this.logBlock = LOTRMod.wood6;
            this.logMeta = 2;
            this.plankBlock = LOTRMod.planks2;
            this.plankMeta = 10;
            this.plankSlabBlock = LOTRMod.woodSlabSingle4;
            this.plankSlabMeta = 2;
            this.plankStairBlock = LOTRMod.stairsCypress;
            this.fenceBlock = LOTRMod.fence2;
            this.fenceMeta = 10;
            this.fenceGateBlock = LOTRMod.fenceGateCypress;
            this.woodBeamBlock = LOTRMod.woodBeam6;
            this.woodBeamMeta = 2;
            this.doorBlock = LOTRMod.doorCypress;
            this.trapdoorBlock = LOTRMod.trapdoorCypress;
         } else if (randomCrop == 3) {
            this.logBlock = LOTRMod.wood6;
            this.logMeta = 3;
            this.plankBlock = LOTRMod.planks2;
            this.plankMeta = 11;
            this.plankSlabBlock = LOTRMod.woodSlabSingle4;
            this.plankSlabMeta = 3;
            this.plankStairBlock = LOTRMod.stairsOlive;
            this.fenceBlock = LOTRMod.fence2;
            this.fenceMeta = 11;
            this.fenceGateBlock = LOTRMod.fenceGateOlive;
            this.woodBeamBlock = LOTRMod.woodBeam6;
            this.woodBeamMeta = 3;
            this.doorBlock = LOTRMod.doorOlive;
            this.trapdoorBlock = LOTRMod.trapdoorOlive;
         }
      }

      if (this.useTownBlocks()) {
         if (random.nextBoolean()) {
            this.roofBlock = LOTRMod.clayTileDyed;
            this.roofMeta = 14;
            this.roofSlabBlock = LOTRMod.slabClayTileDyedSingle2;
            this.roofSlabMeta = 6;
            this.roofStairBlock = LOTRMod.stairsClayTileDyedRed;
            this.roofWallBlock = LOTRMod.wallClayTileDyed;
            this.roofWallMeta = 14;
         } else {
            randomCrop = random.nextInt(2);
            if (randomCrop == 0) {
               this.roofBlock = LOTRMod.clayTileDyed;
               this.roofMeta = 12;
               this.roofSlabBlock = LOTRMod.slabClayTileDyedSingle2;
               this.roofSlabMeta = 4;
               this.roofStairBlock = LOTRMod.stairsClayTileDyedBrown;
               this.roofWallBlock = LOTRMod.wallClayTileDyed;
               this.roofWallMeta = 12;
            } else if (randomCrop == 1) {
               this.roofBlock = LOTRMod.clayTileDyed;
               this.roofMeta = 1;
               this.roofSlabBlock = LOTRMod.slabClayTileDyedSingle;
               this.roofSlabMeta = 1;
               this.roofStairBlock = LOTRMod.stairsClayTileDyedOrange;
               this.roofWallBlock = LOTRMod.wallClayTileDyed;
               this.roofWallMeta = 1;
            }
         }
      } else {
         this.roofBlock = LOTRMod.thatch;
         this.roofMeta = 0;
         this.roofSlabBlock = LOTRMod.slabSingleThatch;
         this.roofSlabMeta = 0;
         this.roofStairBlock = LOTRMod.stairsThatch;
         this.roofWallBlock = this.fenceBlock;
         this.roofWallMeta = this.fenceMeta;
      }

      if (random.nextBoolean()) {
         this.barsBlock = Blocks.field_150411_aY;
      } else {
         this.barsBlock = LOTRMod.bronzeBars;
      }

      this.tableBlock = LOTRMod.rhunTable;
      this.gateBlock = LOTRMod.gateRhun;
      if (this.useTownBlocks()) {
         this.bedBlock = Blocks.field_150324_C;
      } else {
         this.bedBlock = LOTRMod.strawBed;
      }

      if (this.useTownBlocks()) {
         this.plateBlock = LOTRMod.ceramicPlateBlock;
      } else if (random.nextBoolean()) {
         this.plateBlock = LOTRMod.ceramicPlateBlock;
      } else {
         this.plateBlock = LOTRMod.woodPlateBlock;
      }

      if (random.nextBoolean()) {
         this.cropBlock = Blocks.field_150464_aj;
         this.cropMeta = 7;
         this.seedItem = Items.field_151014_N;
      } else {
         randomCrop = random.nextInt(5);
         if (randomCrop == 0) {
            this.cropBlock = Blocks.field_150459_bM;
            this.cropMeta = 7;
            this.seedItem = Items.field_151172_bF;
         } else if (randomCrop == 1) {
            this.cropBlock = Blocks.field_150469_bN;
            this.cropMeta = 7;
            this.seedItem = Items.field_151174_bG;
         } else if (randomCrop == 2) {
            this.cropBlock = LOTRMod.lettuceCrop;
            this.cropMeta = 7;
            this.seedItem = LOTRMod.lettuce;
         } else if (randomCrop == 3) {
            this.cropBlock = LOTRMod.leekCrop;
            this.cropMeta = 7;
            this.seedItem = LOTRMod.leek;
         } else if (randomCrop == 4) {
            this.cropBlock = LOTRMod.turnipCrop;
            this.cropMeta = 7;
            this.seedItem = LOTRMod.turnip;
         }
      }

      this.bannerType = LOTRItemBanner.BannerType.RHUN;
      this.chestContents = LOTRChestContents.EASTERLING_HOUSE;
   }

   protected ItemStack getEasterlingFramedItem(Random random) {
      ItemStack[] items = new ItemStack[]{new ItemStack(LOTRMod.helmetRhun), new ItemStack(LOTRMod.bodyRhun), new ItemStack(LOTRMod.legsRhun), new ItemStack(LOTRMod.bootsRhun), new ItemStack(LOTRMod.helmetRhunGold), new ItemStack(LOTRMod.bodyRhunGold), new ItemStack(LOTRMod.legsRhunGold), new ItemStack(LOTRMod.bootsRhunGold), new ItemStack(LOTRMod.daggerRhun), new ItemStack(LOTRMod.swordRhun), new ItemStack(LOTRMod.battleaxeRhun), new ItemStack(LOTRMod.spearRhun), new ItemStack(LOTRMod.rhunBow), new ItemStack(Items.field_151032_g), new ItemStack(Items.field_151144_bL), new ItemStack(Items.field_151103_aS), new ItemStack(LOTRMod.gobletGold), new ItemStack(LOTRMod.gobletSilver), new ItemStack(LOTRMod.mug), new ItemStack(LOTRMod.goldRing)};
      return items[random.nextInt(items.length)].func_77946_l();
   }

   protected ItemStack getEasterlingWeaponItem(Random random) {
      ItemStack[] items = new ItemStack[]{new ItemStack(LOTRMod.swordRhun), new ItemStack(LOTRMod.daggerRhun), new ItemStack(LOTRMod.daggerRhunPoisoned), new ItemStack(LOTRMod.spearRhun), new ItemStack(LOTRMod.battleaxeRhun), new ItemStack(LOTRMod.polearmRhun), new ItemStack(LOTRMod.pikeRhun)};
      return items[random.nextInt(items.length)].func_77946_l();
   }
}
