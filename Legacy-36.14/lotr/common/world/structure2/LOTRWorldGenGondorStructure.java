package lotr.common.world.structure2;

import java.util.Random;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityBlackrootArcher;
import lotr.common.entity.npc.LOTREntityBlackrootCaptain;
import lotr.common.entity.npc.LOTREntityBlackrootSoldier;
import lotr.common.entity.npc.LOTREntityDolAmrothCaptain;
import lotr.common.entity.npc.LOTREntityDolAmrothSoldier;
import lotr.common.entity.npc.LOTREntityGondorArcher;
import lotr.common.entity.npc.LOTREntityGondorLevyman;
import lotr.common.entity.npc.LOTREntityGondorMan;
import lotr.common.entity.npc.LOTREntityGondorSoldier;
import lotr.common.entity.npc.LOTREntityGondorianCaptain;
import lotr.common.entity.npc.LOTREntityLamedonArcher;
import lotr.common.entity.npc.LOTREntityLamedonCaptain;
import lotr.common.entity.npc.LOTREntityLamedonHillman;
import lotr.common.entity.npc.LOTREntityLamedonSoldier;
import lotr.common.entity.npc.LOTREntityLebenninCaptain;
import lotr.common.entity.npc.LOTREntityLebenninLevyman;
import lotr.common.entity.npc.LOTREntityLossarnachAxeman;
import lotr.common.entity.npc.LOTREntityLossarnachCaptain;
import lotr.common.entity.npc.LOTREntityPelargirCaptain;
import lotr.common.entity.npc.LOTREntityPelargirMarine;
import lotr.common.entity.npc.LOTREntityPinnathGelinCaptain;
import lotr.common.entity.npc.LOTREntityPinnathGelinSoldier;
import lotr.common.entity.npc.LOTREntitySwanKnight;
import lotr.common.item.LOTRItemBanner;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public abstract class LOTRWorldGenGondorStructure extends LOTRWorldGenStructureBase2 {
   public LOTRWorldGenGondorStructure.GondorFiefdom strFief;
   protected Block rockBlock;
   protected int rockMeta;
   protected Block rockSlabBlock;
   protected int rockSlabMeta;
   protected Block rockSlabDoubleBlock;
   protected int rockSlabDoubleMeta;
   protected Block rockStairBlock;
   protected Block rockWallBlock;
   protected int rockWallMeta;
   protected Block brickBlock;
   protected int brickMeta;
   protected Block brickSlabBlock;
   protected int brickSlabMeta;
   protected Block brickStairBlock;
   protected Block brickWallBlock;
   protected int brickWallMeta;
   protected Block brickMossyBlock;
   protected int brickMossyMeta;
   protected Block brickMossySlabBlock;
   protected int brickMossySlabMeta;
   protected Block brickMossyStairBlock;
   protected Block brickMossyWallBlock;
   protected int brickMossyWallMeta;
   protected Block brickCrackedBlock;
   protected int brickCrackedMeta;
   protected Block brickCrackedSlabBlock;
   protected int brickCrackedSlabMeta;
   protected Block brickCrackedStairBlock;
   protected Block brickCrackedWallBlock;
   protected int brickCrackedWallMeta;
   protected Block pillarBlock;
   protected int pillarMeta;
   protected Block brick2Block;
   protected int brick2Meta;
   protected Block brick2SlabBlock;
   protected int brick2SlabMeta;
   protected Block brick2StairBlock;
   protected Block brick2WallBlock;
   protected int brick2WallMeta;
   protected Block pillar2Block;
   protected int pillar2Meta;
   protected Block cobbleBlock;
   protected int cobbleMeta;
   protected Block cobbleSlabBlock;
   protected int cobbleSlabMeta;
   protected Block cobbleStairBlock;
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
   protected Block gateBlock;
   protected Block plateBlock;
   protected Block cropBlock;
   protected int cropMeta;
   protected Item seedItem;
   protected LOTRItemBanner.BannerType bannerType;
   protected LOTRItemBanner.BannerType bannerType2;
   protected LOTRChestContents chestContents;

   public LOTRWorldGenGondorStructure(boolean flag) {
      super(flag);
      this.strFief = LOTRWorldGenGondorStructure.GondorFiefdom.GONDOR;
   }

   protected void setupRandomBlocks(Random random) {
      this.rockBlock = LOTRMod.rock;
      this.rockMeta = 1;
      this.rockSlabBlock = LOTRMod.slabSingle;
      this.rockSlabMeta = 2;
      this.rockSlabDoubleBlock = LOTRMod.slabDouble;
      this.rockSlabDoubleMeta = 2;
      this.rockStairBlock = LOTRMod.stairsGondorRock;
      this.rockWallBlock = LOTRMod.wall;
      this.rockWallMeta = 2;
      if (this.strFief != LOTRWorldGenGondorStructure.GondorFiefdom.GONDOR && this.strFief != LOTRWorldGenGondorStructure.GondorFiefdom.LEBENNIN && this.strFief != LOTRWorldGenGondorStructure.GondorFiefdom.PELARGIR) {
         if (this.strFief == LOTRWorldGenGondorStructure.GondorFiefdom.DOL_AMROTH) {
            this.brickBlock = LOTRMod.brick3;
            this.brickMeta = 9;
            this.brickSlabBlock = LOTRMod.slabSingle6;
            this.brickSlabMeta = 7;
            this.brickStairBlock = LOTRMod.stairsDolAmrothBrick;
            this.brickWallBlock = LOTRMod.wall2;
            this.brickWallMeta = 14;
            this.brickMossyBlock = this.brickBlock;
            this.brickMossyMeta = this.brickMeta;
            this.brickMossySlabBlock = this.brickSlabBlock;
            this.brickMossySlabMeta = this.brickSlabMeta;
            this.brickMossyStairBlock = this.brickStairBlock;
            this.brickMossyWallBlock = this.brickWallBlock;
            this.brickMossyWallMeta = this.brickWallMeta;
            this.brickCrackedBlock = this.brickBlock;
            this.brickCrackedMeta = this.brickMeta;
            this.brickCrackedSlabBlock = this.brickSlabBlock;
            this.brickCrackedSlabMeta = this.brickSlabMeta;
            this.brickCrackedStairBlock = this.brickStairBlock;
            this.brickCrackedWallBlock = this.brickWallBlock;
            this.brickCrackedWallMeta = this.brickWallMeta;
         } else {
            this.brickBlock = LOTRMod.brick5;
            this.brickMeta = 8;
            this.brickSlabBlock = LOTRMod.slabSingle11;
            this.brickSlabMeta = 0;
            this.brickStairBlock = LOTRMod.stairsGondorBrickRustic;
            this.brickWallBlock = LOTRMod.wall4;
            this.brickWallMeta = 7;
            this.brickMossyBlock = LOTRMod.brick5;
            this.brickMossyMeta = 9;
            this.brickMossySlabBlock = LOTRMod.slabSingle11;
            this.brickMossySlabMeta = 1;
            this.brickMossyStairBlock = LOTRMod.stairsGondorBrickRusticMossy;
            this.brickMossyWallBlock = LOTRMod.wall4;
            this.brickMossyWallMeta = 8;
            this.brickCrackedBlock = LOTRMod.brick5;
            this.brickCrackedMeta = 10;
            this.brickCrackedSlabBlock = LOTRMod.slabSingle11;
            this.brickCrackedSlabMeta = 2;
            this.brickCrackedStairBlock = LOTRMod.stairsGondorBrickRusticCracked;
            this.brickCrackedWallBlock = LOTRMod.wall4;
            this.brickCrackedWallMeta = 9;
         }
      } else {
         this.brickBlock = LOTRMod.brick;
         this.brickMeta = 1;
         this.brickSlabBlock = LOTRMod.slabSingle;
         this.brickSlabMeta = 3;
         this.brickStairBlock = LOTRMod.stairsGondorBrick;
         this.brickWallBlock = LOTRMod.wall;
         this.brickWallMeta = 3;
         this.brickMossyBlock = LOTRMod.brick;
         this.brickMossyMeta = 2;
         this.brickMossySlabBlock = LOTRMod.slabSingle;
         this.brickMossySlabMeta = 4;
         this.brickMossyStairBlock = LOTRMod.stairsGondorBrickMossy;
         this.brickMossyWallBlock = LOTRMod.wall;
         this.brickMossyWallMeta = 4;
         this.brickCrackedBlock = LOTRMod.brick;
         this.brickCrackedMeta = 3;
         this.brickCrackedSlabBlock = LOTRMod.slabSingle;
         this.brickCrackedSlabMeta = 5;
         this.brickCrackedStairBlock = LOTRMod.stairsGondorBrickCracked;
         this.brickCrackedWallBlock = LOTRMod.wall;
         this.brickCrackedWallMeta = 5;
      }

      this.pillarBlock = LOTRMod.pillar;
      this.pillarMeta = 6;
      if (this.strFief != LOTRWorldGenGondorStructure.GondorFiefdom.GONDOR && this.strFief != LOTRWorldGenGondorStructure.GondorFiefdom.BLACKROOT_VALE) {
         if (this.strFief == LOTRWorldGenGondorStructure.GondorFiefdom.PELARGIR) {
            this.brick2Block = LOTRMod.whiteSandstone;
            this.brick2Meta = 0;
            this.brick2SlabBlock = LOTRMod.slabSingle10;
            this.brick2SlabMeta = 6;
            this.brick2StairBlock = LOTRMod.stairsWhiteSandstone;
            this.brick2WallBlock = LOTRMod.wall3;
            this.brick2WallMeta = 14;
            this.pillar2Block = LOTRMod.pillar;
            this.pillar2Meta = 9;
         } else if (this.strFief == LOTRWorldGenGondorStructure.GondorFiefdom.LAMEDON) {
            this.brick2Block = Blocks.field_150347_e;
            this.brick2Meta = 0;
            this.brick2SlabBlock = Blocks.field_150333_U;
            this.brick2SlabMeta = 3;
            this.brick2StairBlock = Blocks.field_150446_ar;
            this.brick2WallBlock = Blocks.field_150463_bK;
            this.brick2WallMeta = 0;
            this.pillar2Block = LOTRMod.pillar2;
            this.pillar2Meta = 2;
         } else if (this.strFief == LOTRWorldGenGondorStructure.GondorFiefdom.PINNATH_GELIN) {
            this.brick2Block = LOTRMod.clayTileDyed;
            this.brick2Meta = 13;
            this.brick2SlabBlock = LOTRMod.slabClayTileDyedSingle2;
            this.brick2SlabMeta = 5;
            this.brick2StairBlock = LOTRMod.stairsClayTileDyedGreen;
            this.brick2WallBlock = LOTRMod.wallClayTileDyed;
            this.brick2WallMeta = 13;
            this.pillar2Block = LOTRMod.pillar;
            this.pillar2Meta = 6;
         } else if (this.strFief == LOTRWorldGenGondorStructure.GondorFiefdom.DOL_AMROTH) {
            this.brick2Block = LOTRMod.clayTileDyed;
            this.brick2Meta = 11;
            this.brick2SlabBlock = LOTRMod.slabClayTileDyedSingle2;
            this.brick2SlabMeta = 3;
            this.brick2StairBlock = LOTRMod.stairsClayTileDyedBlue;
            this.brick2WallBlock = LOTRMod.wallClayTileDyed;
            this.brick2WallMeta = 11;
            this.pillar2Block = LOTRMod.pillar;
            this.pillar2Meta = 6;
         } else {
            this.brick2Block = Blocks.field_150417_aV;
            this.brick2Meta = 0;
            this.brick2SlabBlock = Blocks.field_150333_U;
            this.brick2SlabMeta = 5;
            this.brick2StairBlock = Blocks.field_150390_bg;
            this.brick2WallBlock = LOTRMod.wallStoneV;
            this.brick2WallMeta = 1;
            this.pillar2Block = LOTRMod.pillar2;
            this.pillar2Meta = 2;
         }
      } else {
         this.brick2Block = LOTRMod.brick2;
         this.brick2Meta = 11;
         this.brick2SlabBlock = LOTRMod.slabSingle5;
         this.brick2SlabMeta = 3;
         this.brick2StairBlock = LOTRMod.stairsBlackGondorBrick;
         this.brick2WallBlock = LOTRMod.wall2;
         this.brick2WallMeta = 10;
         this.pillar2Block = LOTRMod.pillar;
         this.pillar2Meta = 9;
      }

      this.cobbleBlock = Blocks.field_150347_e;
      this.cobbleMeta = 0;
      this.cobbleSlabBlock = Blocks.field_150333_U;
      this.cobbleSlabMeta = 3;
      this.cobbleStairBlock = Blocks.field_150446_ar;
      int randomCrop;
      if (this.strFief == LOTRWorldGenGondorStructure.GondorFiefdom.BLACKROOT_VALE) {
         this.plankBlock = Blocks.field_150344_f;
         this.plankMeta = 5;
         this.plankSlabBlock = Blocks.field_150376_bx;
         this.plankSlabMeta = 5;
         this.plankStairBlock = Blocks.field_150401_cl;
         this.fenceBlock = Blocks.field_150422_aJ;
         this.fenceMeta = 5;
         this.fenceGateBlock = LOTRMod.fenceGateDarkOak;
         this.woodBeamBlock = LOTRMod.woodBeamV2;
         this.woodBeamMeta = 1;
         this.doorBlock = LOTRMod.doorDarkOak;
         this.trapdoorBlock = LOTRMod.trapdoorDarkOak;
      } else {
         randomCrop = random.nextInt(7);
         if (randomCrop != 0 && randomCrop != 1 && randomCrop != 2) {
            if (randomCrop == 3) {
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
            } else if (randomCrop == 4) {
               this.plankBlock = LOTRMod.planks2;
               this.plankMeta = 2;
               this.plankSlabBlock = LOTRMod.woodSlabSingle3;
               this.plankSlabMeta = 2;
               this.plankStairBlock = LOTRMod.stairsCedar;
               this.fenceBlock = LOTRMod.fence2;
               this.fenceMeta = 2;
               this.fenceGateBlock = LOTRMod.fenceGateCedar;
               this.woodBeamBlock = LOTRMod.woodBeam4;
               this.woodBeamMeta = 2;
               this.doorBlock = LOTRMod.doorCedar;
               this.trapdoorBlock = LOTRMod.trapdoorCedar;
            } else if (randomCrop == 5) {
               this.plankBlock = LOTRMod.planks;
               this.plankMeta = 8;
               this.plankSlabBlock = LOTRMod.woodSlabSingle2;
               this.plankSlabMeta = 0;
               this.plankStairBlock = LOTRMod.stairsLebethron;
               this.fenceBlock = LOTRMod.fence;
               this.fenceMeta = 8;
               this.fenceGateBlock = LOTRMod.fenceGateLebethron;
               this.woodBeamBlock = LOTRMod.woodBeam2;
               this.woodBeamMeta = 0;
               this.doorBlock = LOTRMod.doorLebethron;
               this.trapdoorBlock = LOTRMod.trapdoorLebethron;
            } else if (randomCrop == 6) {
               this.plankBlock = Blocks.field_150344_f;
               this.plankMeta = 2;
               this.plankSlabBlock = Blocks.field_150376_bx;
               this.plankSlabMeta = 2;
               this.plankStairBlock = Blocks.field_150487_bG;
               this.fenceBlock = Blocks.field_150422_aJ;
               this.fenceMeta = 2;
               this.fenceGateBlock = LOTRMod.fenceGateBirch;
               this.woodBeamBlock = LOTRMod.woodBeamV1;
               this.woodBeamMeta = 2;
               this.doorBlock = LOTRMod.doorBirch;
               this.trapdoorBlock = LOTRMod.trapdoorBirch;
            }
         } else {
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
      }

      if (this.strFief == LOTRWorldGenGondorStructure.GondorFiefdom.LOSSARNACH) {
         this.pillarBlock = this.woodBeamBlock;
         this.pillarMeta = this.woodBeamMeta;
         this.brick2Block = this.plankBlock;
         this.brick2Meta = this.plankMeta;
         this.brick2SlabBlock = this.plankSlabBlock;
         this.brick2SlabMeta = this.plankSlabMeta;
         this.brick2StairBlock = this.plankStairBlock;
         this.brick2WallBlock = this.fenceBlock;
         this.brick2WallMeta = this.fenceMeta;
         this.pillar2Block = this.woodBeamBlock;
         this.pillar2Meta = this.woodBeamMeta;
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
      this.barsBlock = Blocks.field_150411_aY;
      this.tableBlock = LOTRMod.gondorianTable;
      this.bedBlock = LOTRMod.strawBed;
      if (this.strFief != LOTRWorldGenGondorStructure.GondorFiefdom.PINNATH_GELIN && this.strFief != LOTRWorldGenGondorStructure.GondorFiefdom.LOSSARNACH && this.strFief != LOTRWorldGenGondorStructure.GondorFiefdom.LAMEDON) {
         if (this.strFief == LOTRWorldGenGondorStructure.GondorFiefdom.DOL_AMROTH) {
            this.gateBlock = LOTRMod.gateDolAmroth;
         } else {
            this.gateBlock = LOTRMod.gateGondor;
         }
      } else {
         this.gateBlock = LOTRMod.gateWooden;
      }

      if (this.strFief != LOTRWorldGenGondorStructure.GondorFiefdom.LOSSARNACH && this.strFief != LOTRWorldGenGondorStructure.GondorFiefdom.LAMEDON && this.strFief != LOTRWorldGenGondorStructure.GondorFiefdom.BLACKROOT_VALE) {
         this.plateBlock = LOTRMod.ceramicPlateBlock;
      } else if (random.nextBoolean()) {
         this.plateBlock = LOTRMod.woodPlateBlock;
      } else {
         this.plateBlock = LOTRMod.ceramicPlateBlock;
      }

      if (random.nextBoolean()) {
         this.cropBlock = Blocks.field_150464_aj;
         this.cropMeta = 7;
         this.seedItem = Items.field_151014_N;
      } else {
         randomCrop = random.nextInt(6);
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
            this.cropBlock = LOTRMod.cornStalk;
            this.cropMeta = 0;
            this.seedItem = Item.func_150898_a(LOTRMod.cornStalk);
         } else if (randomCrop == 4) {
            this.cropBlock = LOTRMod.leekCrop;
            this.cropMeta = 7;
            this.seedItem = LOTRMod.leek;
         } else if (randomCrop == 5) {
            this.cropBlock = LOTRMod.turnipCrop;
            this.cropMeta = 7;
            this.seedItem = LOTRMod.turnip;
         }
      }

      if (this.strFief == LOTRWorldGenGondorStructure.GondorFiefdom.GONDOR) {
         this.bannerType = LOTRItemBanner.BannerType.GONDOR;
      } else if (this.strFief == LOTRWorldGenGondorStructure.GondorFiefdom.LOSSARNACH) {
         this.bannerType = LOTRItemBanner.BannerType.LOSSARNACH;
      } else if (this.strFief == LOTRWorldGenGondorStructure.GondorFiefdom.LEBENNIN) {
         this.bannerType = LOTRItemBanner.BannerType.LEBENNIN;
      } else if (this.strFief == LOTRWorldGenGondorStructure.GondorFiefdom.PELARGIR) {
         this.bannerType = LOTRItemBanner.BannerType.PELARGIR;
      } else if (this.strFief == LOTRWorldGenGondorStructure.GondorFiefdom.PINNATH_GELIN) {
         this.bannerType = LOTRItemBanner.BannerType.PINNATH_GELIN;
      } else if (this.strFief == LOTRWorldGenGondorStructure.GondorFiefdom.BLACKROOT_VALE) {
         this.bannerType = LOTRItemBanner.BannerType.BLACKROOT_VALE;
      } else if (this.strFief == LOTRWorldGenGondorStructure.GondorFiefdom.LAMEDON) {
         this.bannerType = LOTRItemBanner.BannerType.LAMEDON;
      } else if (this.strFief == LOTRWorldGenGondorStructure.GondorFiefdom.DOL_AMROTH) {
         this.bannerType = LOTRItemBanner.BannerType.DOL_AMROTH;
      }

      if (this.strFief == LOTRWorldGenGondorStructure.GondorFiefdom.PELARGIR) {
         this.bannerType2 = LOTRItemBanner.BannerType.LEBENNIN;
      } else {
         this.bannerType2 = LOTRItemBanner.BannerType.GONDOR;
      }

      this.chestContents = LOTRChestContents.GONDOR_HOUSE;
   }

   protected ItemStack getGondorFramedItem(Random random) {
      ItemStack[] items = new ItemStack[]{new ItemStack(LOTRMod.helmetGondor), new ItemStack(LOTRMod.bodyGondor), new ItemStack(LOTRMod.daggerGondor), new ItemStack(LOTRMod.spearGondor), new ItemStack(LOTRMod.gondorBow), new ItemStack(Items.field_151032_g), new ItemStack(Items.field_151040_l), new ItemStack(Items.field_151036_c), new ItemStack(LOTRMod.daggerIron), new ItemStack(LOTRMod.pikeIron), new ItemStack(LOTRMod.ironCrossbow), new ItemStack(LOTRMod.goldRing), new ItemStack(LOTRMod.silverRing)};
      return items[random.nextInt(items.length)].func_77946_l();
   }

   public static enum GondorFiefdom {
      GONDOR,
      LOSSARNACH,
      LEBENNIN,
      PELARGIR,
      PINNATH_GELIN,
      BLACKROOT_VALE,
      LAMEDON,
      DOL_AMROTH;

      public LOTREntityGondorMan createCaptain(World world) {
         if (this == GONDOR) {
            return new LOTREntityGondorianCaptain(world);
         } else if (this == LOSSARNACH) {
            return new LOTREntityLossarnachCaptain(world);
         } else if (this == LEBENNIN) {
            return new LOTREntityLebenninCaptain(world);
         } else if (this == PELARGIR) {
            return new LOTREntityPelargirCaptain(world);
         } else if (this == PINNATH_GELIN) {
            return new LOTREntityPinnathGelinCaptain(world);
         } else if (this == BLACKROOT_VALE) {
            return new LOTREntityBlackrootCaptain(world);
         } else if (this == LAMEDON) {
            return new LOTREntityLamedonCaptain(world);
         } else {
            return this == DOL_AMROTH ? new LOTREntityDolAmrothCaptain(world) : null;
         }
      }

      public Class[] getSoldierClasses() {
         if (this == GONDOR) {
            return new Class[]{LOTREntityGondorSoldier.class, LOTREntityGondorArcher.class};
         } else if (this == LOSSARNACH) {
            return new Class[]{LOTREntityLossarnachAxeman.class, LOTREntityLossarnachAxeman.class};
         } else if (this == LEBENNIN) {
            return new Class[]{LOTREntityLebenninLevyman.class, LOTREntityGondorSoldier.class};
         } else if (this == PELARGIR) {
            return new Class[]{LOTREntityPelargirMarine.class, LOTREntityPelargirMarine.class};
         } else if (this == PINNATH_GELIN) {
            return new Class[]{LOTREntityPinnathGelinSoldier.class, LOTREntityPinnathGelinSoldier.class};
         } else if (this == BLACKROOT_VALE) {
            return new Class[]{LOTREntityBlackrootArcher.class, LOTREntityBlackrootSoldier.class};
         } else if (this == LAMEDON) {
            return new Class[]{LOTREntityLamedonSoldier.class, LOTREntityLamedonArcher.class};
         } else {
            return this == DOL_AMROTH ? new Class[]{LOTREntityDolAmrothSoldier.class, LOTREntitySwanKnight.class} : null;
         }
      }

      public Class[] getLevyClasses() {
         if (this == GONDOR) {
            return new Class[]{LOTREntityGondorLevyman.class, LOTREntityGondorSoldier.class};
         } else if (this == LOSSARNACH) {
            return new Class[]{LOTREntityGondorLevyman.class, LOTREntityLossarnachAxeman.class};
         } else if (this == LEBENNIN) {
            return new Class[]{LOTREntityLebenninLevyman.class, LOTREntityGondorSoldier.class};
         } else if (this == PELARGIR) {
            return new Class[]{LOTREntityLebenninLevyman.class, LOTREntityPelargirMarine.class};
         } else if (this == PINNATH_GELIN) {
            return new Class[]{LOTREntityGondorLevyman.class, LOTREntityPinnathGelinSoldier.class};
         } else if (this == BLACKROOT_VALE) {
            return new Class[]{LOTREntityGondorLevyman.class, LOTREntityBlackrootArcher.class};
         } else if (this == LAMEDON) {
            return new Class[]{LOTREntityLamedonHillman.class, LOTREntityLamedonSoldier.class};
         } else {
            return this == DOL_AMROTH ? new Class[]{LOTREntityDolAmrothSoldier.class, LOTREntitySwanKnight.class} : null;
         }
      }

      protected ItemStack[] getFiefdomArmor() {
         if (this == GONDOR) {
            return new ItemStack[]{new ItemStack(LOTRMod.helmetGondor), new ItemStack(LOTRMod.bodyGondor), new ItemStack(LOTRMod.legsGondor), new ItemStack(LOTRMod.bootsGondor)};
         } else if (this == LOSSARNACH) {
            return new ItemStack[]{new ItemStack(LOTRMod.helmetLossarnach), new ItemStack(LOTRMod.bodyLossarnach), new ItemStack(LOTRMod.legsLossarnach), new ItemStack(LOTRMod.bootsLossarnach)};
         } else if (this == LEBENNIN) {
            return new ItemStack[]{new ItemStack(LOTRMod.helmetGondor), new ItemStack(LOTRMod.bodyGondor), new ItemStack(LOTRMod.legsGondor), new ItemStack(LOTRMod.bootsGondor)};
         } else if (this == PELARGIR) {
            return new ItemStack[]{new ItemStack(LOTRMod.helmetPelargir), new ItemStack(LOTRMod.bodyPelargir), new ItemStack(LOTRMod.legsPelargir), new ItemStack(LOTRMod.bootsPelargir)};
         } else if (this == PINNATH_GELIN) {
            return new ItemStack[]{new ItemStack(LOTRMod.helmetPinnathGelin), new ItemStack(LOTRMod.bodyPinnathGelin), new ItemStack(LOTRMod.legsPinnathGelin), new ItemStack(LOTRMod.bootsPinnathGelin)};
         } else if (this == BLACKROOT_VALE) {
            return new ItemStack[]{new ItemStack(LOTRMod.helmetBlackroot), new ItemStack(LOTRMod.bodyBlackroot), new ItemStack(LOTRMod.legsBlackroot), new ItemStack(LOTRMod.bootsBlackroot)};
         } else if (this == LAMEDON) {
            return new ItemStack[]{new ItemStack(LOTRMod.helmetLamedon), new ItemStack(LOTRMod.bodyLamedon), new ItemStack(LOTRMod.legsLamedon), new ItemStack(LOTRMod.bootsLamedon)};
         } else {
            return this == DOL_AMROTH ? new ItemStack[]{new ItemStack(LOTRMod.helmetDolAmroth), new ItemStack(LOTRMod.bodyDolAmroth), new ItemStack(LOTRMod.legsDolAmroth), new ItemStack(LOTRMod.bootsDolAmroth)} : null;
         }
      }
   }
}
