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

public abstract class LOTRWorldGenRangerStructure extends LOTRWorldGenStructureBase2 {
   protected Block brickBlock;
   protected int brickMeta;
   protected Block brickSlabBlock;
   protected int brickSlabMeta;
   protected Block brickStairBlock;
   protected Block brickWallBlock;
   protected int brickWallMeta;
   protected Block brickCarvedBlock;
   protected int brickCarvedMeta;
   protected Block cobbleBlock;
   protected int cobbleMeta;
   protected Block cobbleSlabBlock;
   protected int cobbleSlabMeta;
   protected Block cobbleStairBlock;
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
   protected Block wallBlock;
   protected int wallMeta;
   protected Block roofBlock;
   protected int roofMeta;
   protected Block roofSlabBlock;
   protected int roofSlabMeta;
   protected Block roofStairBlock;
   protected Block barsBlock;
   protected Block tableBlock;
   protected Block bedBlock;
   protected Block plateBlock;
   protected Block cropBlock;
   protected int cropMeta;
   protected Item seedItem;
   protected LOTRItemBanner.BannerType bannerType;
   protected LOTRChestContents chestContentsHouse;
   protected LOTRChestContents chestContentsRanger;

   public LOTRWorldGenRangerStructure(boolean flag) {
      super(flag);
   }

   protected void setupRandomBlocks(Random random) {
      if (random.nextInt(3) == 0) {
         this.brickBlock = LOTRMod.brick2;
         this.brickMeta = 3;
         this.brickSlabBlock = LOTRMod.slabSingle4;
         this.brickSlabMeta = 1;
         this.brickStairBlock = LOTRMod.stairsArnorBrick;
         this.brickWallBlock = LOTRMod.wall2;
         this.brickWallMeta = 4;
         this.brickCarvedBlock = LOTRMod.brick2;
         this.brickCarvedMeta = 6;
      } else {
         this.brickBlock = Blocks.field_150417_aV;
         this.brickMeta = 0;
         this.brickSlabBlock = Blocks.field_150333_U;
         this.brickSlabMeta = 5;
         this.brickStairBlock = Blocks.field_150390_bg;
         this.brickWallBlock = LOTRMod.wallStoneV;
         this.brickWallMeta = 1;
         this.brickCarvedBlock = Blocks.field_150417_aV;
         this.brickCarvedMeta = 3;
      }

      this.cobbleBlock = Blocks.field_150347_e;
      this.cobbleMeta = 0;
      this.cobbleSlabBlock = Blocks.field_150333_U;
      this.cobbleSlabMeta = 3;
      this.cobbleStairBlock = Blocks.field_150446_ar;
      int randomWood = random.nextInt(7);
      if (randomWood != 0 && randomWood != 1 && randomWood != 2) {
         if (randomWood == 3) {
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
         } else if (randomWood == 4) {
            this.logBlock = Blocks.field_150364_r;
            this.logMeta = 1;
            this.plankBlock = Blocks.field_150344_f;
            this.plankMeta = 1;
            this.plankSlabBlock = Blocks.field_150376_bx;
            this.plankSlabMeta = 1;
            this.plankStairBlock = Blocks.field_150485_bF;
            this.fenceBlock = Blocks.field_150422_aJ;
            this.fenceMeta = 1;
            this.fenceGateBlock = LOTRMod.fenceGateSpruce;
            this.woodBeamBlock = LOTRMod.woodBeamV1;
            this.woodBeamMeta = 1;
            this.doorBlock = LOTRMod.doorSpruce;
            this.trapdoorBlock = LOTRMod.trapdoorSpruce;
         } else if (randomWood == 5) {
            this.logBlock = LOTRMod.wood5;
            this.logMeta = 0;
            this.plankBlock = LOTRMod.planks2;
            this.plankMeta = 4;
            this.plankSlabBlock = LOTRMod.woodSlabSingle3;
            this.plankSlabMeta = 4;
            this.plankStairBlock = LOTRMod.stairsPine;
            this.fenceBlock = LOTRMod.fence2;
            this.fenceMeta = 4;
            this.fenceGateBlock = LOTRMod.fenceGatePine;
            this.woodBeamBlock = LOTRMod.woodBeam5;
            this.woodBeamMeta = 0;
            this.doorBlock = LOTRMod.doorPine;
            this.trapdoorBlock = LOTRMod.trapdoorPine;
         } else if (randomWood == 6) {
            this.logBlock = LOTRMod.wood4;
            this.logMeta = 0;
            this.plankBlock = LOTRMod.planks2;
            this.plankMeta = 0;
            this.plankSlabBlock = LOTRMod.woodSlabSingle3;
            this.plankSlabMeta = 0;
            this.plankStairBlock = LOTRMod.stairsChestnut;
            this.fenceBlock = LOTRMod.fence2;
            this.fenceMeta = 0;
            this.fenceGateBlock = LOTRMod.fenceGateChestnut;
            this.woodBeamBlock = LOTRMod.woodBeam4;
            this.woodBeamMeta = 0;
            this.doorBlock = LOTRMod.doorChestnut;
            this.trapdoorBlock = LOTRMod.trapdoorChestnut;
         }
      } else {
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
      }

      if (random.nextBoolean()) {
         this.wallBlock = LOTRMod.daub;
         this.wallMeta = 0;
      } else {
         this.wallBlock = this.plankBlock;
         this.wallMeta = this.plankMeta;
      }

      this.roofBlock = LOTRMod.thatch;
      this.roofMeta = 0;
      this.roofSlabBlock = LOTRMod.slabSingleThatch;
      this.roofSlabMeta = 0;
      this.roofStairBlock = LOTRMod.stairsThatch;
      if (random.nextBoolean()) {
         this.barsBlock = Blocks.field_150411_aY;
      } else {
         this.barsBlock = LOTRMod.bronzeBars;
      }

      this.tableBlock = LOTRMod.rangerTable;
      this.bedBlock = LOTRMod.strawBed;
      if (random.nextBoolean()) {
         this.plateBlock = LOTRMod.woodPlateBlock;
      } else {
         this.plateBlock = LOTRMod.ceramicPlateBlock;
      }

      if (random.nextBoolean()) {
         this.cropBlock = Blocks.field_150464_aj;
         this.cropMeta = 7;
         this.seedItem = Items.field_151014_N;
      } else {
         int randomCrop = random.nextInt(5);
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

      this.bannerType = LOTRItemBanner.BannerType.RANGER_NORTH;
      this.chestContentsHouse = LOTRChestContents.RANGER_HOUSE;
      this.chestContentsRanger = LOTRChestContents.RANGER_TENT;
   }

   protected ItemStack getRangerFramedItem(Random random) {
      ItemStack[] items = new ItemStack[]{new ItemStack(LOTRMod.helmetRanger), new ItemStack(LOTRMod.bodyRanger), new ItemStack(LOTRMod.legsRanger), new ItemStack(LOTRMod.bootsRanger), new ItemStack(LOTRMod.daggerIron), new ItemStack(LOTRMod.daggerBronze), new ItemStack(LOTRMod.rangerBow), new ItemStack(Items.field_151031_f), new ItemStack(Items.field_151032_g)};
      return items[random.nextInt(items.length)].func_77946_l();
   }
}
