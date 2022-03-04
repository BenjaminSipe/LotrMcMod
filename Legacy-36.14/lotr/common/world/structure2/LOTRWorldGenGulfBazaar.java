package lotr.common.world.structure2;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.entity.animal.LOTREntityBird;
import lotr.common.entity.npc.LOTREntityGulfBaker;
import lotr.common.entity.npc.LOTREntityGulfBlacksmith;
import lotr.common.entity.npc.LOTREntityGulfBrewer;
import lotr.common.entity.npc.LOTREntityGulfButcher;
import lotr.common.entity.npc.LOTREntityGulfFarmer;
import lotr.common.entity.npc.LOTREntityGulfFishmonger;
import lotr.common.entity.npc.LOTREntityGulfGoldsmith;
import lotr.common.entity.npc.LOTREntityGulfHaradWarrior;
import lotr.common.entity.npc.LOTREntityGulfHunter;
import lotr.common.entity.npc.LOTREntityGulfLumberman;
import lotr.common.entity.npc.LOTREntityGulfMason;
import lotr.common.entity.npc.LOTREntityGulfMiner;
import lotr.common.entity.npc.LOTREntityGulfTrader;
import lotr.common.item.LOTRItemBanner;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTRWorldGenGulfBazaar extends LOTRWorldGenGulfStructure {
   private static Class[] stalls = new Class[]{LOTRWorldGenGulfBazaar.Mason.class, LOTRWorldGenGulfBazaar.Butcher.class, LOTRWorldGenGulfBazaar.Brewer.class, LOTRWorldGenGulfBazaar.Fish.class, LOTRWorldGenGulfBazaar.Baker.class, LOTRWorldGenGulfBazaar.Miner.class, LOTRWorldGenGulfBazaar.Goldsmith.class, LOTRWorldGenGulfBazaar.Lumber.class, LOTRWorldGenGulfBazaar.Hunter.class, LOTRWorldGenGulfBazaar.Blacksmith.class, LOTRWorldGenGulfBazaar.Farmer.class};

   public LOTRWorldGenGulfBazaar(boolean flag) {
      super(flag);
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, 8);
      this.setupRandomBlocks(random);
      int minHeight;
      int maxHeight;
      int i2;
      int k2;
      int j1;
      if (this.restrictions) {
         minHeight = 0;
         maxHeight = 0;

         for(i2 = -17; i2 <= 17; ++i2) {
            for(k2 = -12; k2 <= 8; ++k2) {
               j1 = this.getTopBlock(world, i2, k2) - 1;
               if (!this.isSurface(world, i2, j1, k2)) {
                  return false;
               }

               if (j1 < minHeight) {
                  minHeight = j1;
               }

               if (j1 > maxHeight) {
                  maxHeight = j1;
               }

               if (maxHeight - minHeight > 12) {
                  return false;
               }
            }
         }
      }

      for(minHeight = -17; minHeight <= 17; ++minHeight) {
         for(maxHeight = -12; maxHeight <= 8; ++maxHeight) {
            i2 = Math.abs(minHeight);
            k2 = Math.abs(maxHeight);
            if (i2 >= 5 && i2 <= 9 && k2 >= 10 && k2 <= 12) {
               for(j1 = 1; j1 <= 5; ++j1) {
                  this.setAir(world, minHeight, j1, maxHeight);
               }
            }

            if (maxHeight >= -6 && maxHeight <= -5 && i2 <= 4 || maxHeight == -5 && i2 >= 10 && i2 <= 12 || k2 == 4 && i2 <= 14 || k2 >= 2 && k2 <= 3 && i2 <= 15 || k2 <= 1 && i2 <= 16 || maxHeight == 5 && i2 <= 12 || maxHeight == 6 && i2 <= 9 || maxHeight == 7 && i2 <= 4) {
               for(j1 = 1; j1 <= 6; ++j1) {
                  this.setAir(world, minHeight, j1, maxHeight);
               }
            }
         }
      }

      this.loadStrScan("gulf_bazaar");
      this.addBlockMetaAliasOption("BRICK", 6, this.brickBlock, this.brickMeta);
      this.addBlockMetaAliasOption("BRICK", 2, LOTRMod.brick3, 11);
      this.addBlockMetaAliasOption("BRICK", 8, Blocks.field_150322_A, 0);
      this.addBlockAliasOption("BRICK_STAIR", 6, this.brickStairBlock);
      this.addBlockAliasOption("BRICK_STAIR", 2, LOTRMod.stairsNearHaradBrickCracked);
      this.addBlockAliasOption("BRICK_STAIR", 8, Blocks.field_150372_bz);
      this.addBlockMetaAliasOption("BRICK_WALL", 6, this.brickWallBlock, this.brickWallMeta);
      this.addBlockMetaAliasOption("BRICK_WALL", 2, LOTRMod.wall3, 3);
      this.addBlockMetaAliasOption("BRICK_WALL", 8, LOTRMod.wallStoneV, 4);
      this.associateBlockMetaAlias("PLANK", this.plankBlock, this.plankMeta);
      this.associateBlockMetaAlias("PLANK_SLAB", this.plankSlabBlock, this.plankSlabMeta);
      this.associateBlockAlias("PLANK_STAIR", this.plankStairBlock);
      this.associateBlockMetaAlias("FENCE", this.fenceBlock, this.fenceMeta);
      this.associateBlockAlias("FENCE_GATE", this.fenceGateBlock);
      this.associateBlockMetaAlias("BEAM", this.beamBlock, this.beamMeta);
      this.associateBlockMetaAlias("BEAM2", this.beam2Block, this.beam2Meta);
      this.associateBlockMetaAlias("BEAM2|4", this.beam2Block, this.beam2Meta | 4);
      this.associateBlockMetaAlias("BEAM2|8", this.beam2Block, this.beam2Meta | 8);
      this.addBlockMetaAliasOption("GROUND", 10, Blocks.field_150354_m, 0);
      this.addBlockMetaAliasOption("GROUND", 3, Blocks.field_150346_d, 1);
      this.addBlockMetaAliasOption("GROUND", 2, LOTRMod.dirtPath, 0);
      this.associateBlockMetaAlias("WOOL", Blocks.field_150325_L, 14);
      this.associateBlockMetaAlias("CARPET", Blocks.field_150404_cg, 14);
      this.associateBlockMetaAlias("WOOL2", Blocks.field_150325_L, 15);
      this.associateBlockMetaAlias("CARPET2", Blocks.field_150404_cg, 15);
      this.associateBlockMetaAlias("BONE", this.boneBlock, this.boneMeta);
      this.generateStrScan(world, random, 0, 0, 0);
      this.placeAnimalJar(world, -5, 4, -2, LOTRMod.birdCageWood, 0, new LOTREntityBird(world));
      this.placeAnimalJar(world, -7, 5, 0, LOTRMod.birdCageWood, 0, new LOTREntityBird(world));
      this.placeWallBanner(world, -3, 4, -7, LOTRItemBanner.BannerType.HARAD_GULF, 2);
      this.placeWallBanner(world, 3, 4, -7, LOTRItemBanner.BannerType.HARAD_GULF, 2);
      this.placeWallBanner(world, -7, 10, -8, LOTRItemBanner.BannerType.HARAD_GULF, 2);
      this.placeWallBanner(world, -7, 10, -6, LOTRItemBanner.BannerType.HARAD_GULF, 0);
      this.placeWallBanner(world, -8, 10, -7, LOTRItemBanner.BannerType.HARAD_GULF, 3);
      this.placeWallBanner(world, -6, 10, -7, LOTRItemBanner.BannerType.HARAD_GULF, 1);
      this.placeWallBanner(world, 7, 10, -8, LOTRItemBanner.BannerType.HARAD_GULF, 2);
      this.placeWallBanner(world, 7, 10, -6, LOTRItemBanner.BannerType.HARAD_GULF, 0);
      this.placeWallBanner(world, 6, 10, -7, LOTRItemBanner.BannerType.HARAD_GULF, 3);
      this.placeWallBanner(world, 8, 10, -7, LOTRItemBanner.BannerType.HARAD_GULF, 1);
      int[] var15 = new int[]{-7, 7};
      maxHeight = var15.length;

      for(i2 = 0; i2 < maxHeight; ++i2) {
         k2 = var15[i2];
         int j1 = 1;
         int k1 = -11;
         LOTREntityGulfHaradWarrior guard = new LOTREntityGulfHaradWarrior(world);
         guard.spawnRidingHorse = false;
         this.spawnNPCAndSetHome(guard, world, k2, j1, k1, 4);
      }

      List stallClasses = Arrays.asList(Arrays.copyOf(stalls, stalls.length));
      Collections.shuffle(stallClasses, random);

      try {
         LOTRWorldGenStructureBase2 stall0 = (LOTRWorldGenStructureBase2)((Class)stallClasses.get(0)).getConstructor(Boolean.TYPE).newInstance(this.notifyChanges);
         LOTRWorldGenStructureBase2 stall1 = (LOTRWorldGenStructureBase2)((Class)stallClasses.get(1)).getConstructor(Boolean.TYPE).newInstance(this.notifyChanges);
         LOTRWorldGenStructureBase2 stall2 = (LOTRWorldGenStructureBase2)((Class)stallClasses.get(2)).getConstructor(Boolean.TYPE).newInstance(this.notifyChanges);
         LOTRWorldGenStructureBase2 stall3 = (LOTRWorldGenStructureBase2)((Class)stallClasses.get(3)).getConstructor(Boolean.TYPE).newInstance(this.notifyChanges);
         LOTRWorldGenStructureBase2 stall4 = (LOTRWorldGenStructureBase2)((Class)stallClasses.get(4)).getConstructor(Boolean.TYPE).newInstance(this.notifyChanges);
         this.generateSubstructure(stall0, world, random, -9, 1, 0, 3);
         this.generateSubstructure(stall1, world, random, -5, 1, 5, 1);
         this.generateSubstructure(stall2, world, random, 0, 1, 6, 1);
         this.generateSubstructure(stall3, world, random, 8, 1, 2, 3);
         this.generateSubstructure(stall4, world, random, 11, 1, -2, 0);
      } catch (Exception var14) {
         var14.printStackTrace();
      }

      return true;
   }

   private static class Farmer extends LOTRWorldGenStructureBase2 {
      public Farmer(boolean flag) {
         super(flag);
      }

      public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
         this.setOriginAndRotation(world, i, j, k, rotation, 0);
         this.setBlockAndMetadata(world, 2, 1, 2, Blocks.field_150383_bp, 3);
         this.setBlockAndMetadata(world, 1, 2, 3, Blocks.field_150407_cf, 0);
         this.placePlate_item(world, random, 3, 2, 1, LOTRMod.woodPlateBlock, new ItemStack(LOTRMod.orange, 1 + random.nextInt(3)), true);
         this.placeFlowerPot(world, 0, 2, 2, this.getRandomFlower(world, random));
         LOTREntityGulfFarmer trader = new LOTREntityGulfFarmer(world);
         this.spawnNPCAndSetHome(trader, world, 2, 1, 1, 4);
         return true;
      }
   }

   private static class Blacksmith extends LOTRWorldGenStructureBase2 {
      public Blacksmith(boolean flag) {
         super(flag);
      }

      public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
         this.setOriginAndRotation(world, i, j, k, rotation, 0);
         this.setBlockAndMetadata(world, 2, 1, 2, Blocks.field_150467_bQ, 3);
         this.placeArmorStand(world, 1, 1, 2, 0, new ItemStack[]{null, new ItemStack(LOTRMod.bodyGulfHarad), null, null});
         this.placeWeaponRack(world, 0, 2, 2, 1, (new LOTRWorldGenGulfBazaar(false)).getRandomGulfWeapon(random));
         this.placeWeaponRack(world, 3, 2, 2, 3, (new LOTRWorldGenGulfBazaar(false)).getRandomGulfWeapon(random));
         LOTREntityGulfBlacksmith trader = new LOTREntityGulfBlacksmith(world);
         this.spawnNPCAndSetHome(trader, world, 2, 1, 1, 4);
         return true;
      }
   }

   private static class Hunter extends LOTRWorldGenStructureBase2 {
      public Hunter(boolean flag) {
         super(flag);
      }

      public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
         this.setOriginAndRotation(world, i, j, k, rotation, 0);
         this.setBlockAndMetadata(world, 2, 1, 2, LOTRMod.wood8, 3);
         this.setBlockAndMetadata(world, 2, 2, 2, LOTRMod.wood8, 3);
         this.placeSkull(world, random, 2, 3, 2);
         this.placeSkull(world, random, 3, 2, 2);
         this.spawnItemFrame(world, 2, 2, 2, 2, new ItemStack(LOTRMod.lionFur));
         this.placePlate_item(world, random, 1, 2, 0, LOTRMod.woodPlateBlock, new ItemStack(LOTRMod.rabbitRaw, 1 + random.nextInt(3)), true);
         this.placeWeaponRack(world, 0, 2, 2, 1, new ItemStack(LOTRMod.spearHarad));
         LOTREntityGulfTrader trader = new LOTREntityGulfHunter(world);
         this.spawnNPCAndSetHome(trader, world, 2, 1, 1, 4);
         return true;
      }
   }

   private static class Lumber extends LOTRWorldGenStructureBase2 {
      public Lumber(boolean flag) {
         super(flag);
      }

      public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
         this.setOriginAndRotation(world, i, j, k, rotation, 0);
         this.setBlockAndMetadata(world, 2, 1, 2, LOTRMod.wood8, 3);
         this.setBlockAndMetadata(world, 2, 2, 2, LOTRMod.wood8, 3);
         this.placeFlowerPot(world, 0, 2, 2, new ItemStack(Blocks.field_150345_g, 1, 4));
         this.placeFlowerPot(world, 3, 2, 1, new ItemStack(LOTRMod.sapling8, 1, 3));
         LOTREntityGulfTrader trader = new LOTREntityGulfLumberman(world);
         this.spawnNPCAndSetHome(trader, world, 2, 1, 1, 4);
         return true;
      }
   }

   private static class Goldsmith extends LOTRWorldGenStructureBase2 {
      public Goldsmith(boolean flag) {
         super(flag);
      }

      public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
         this.setOriginAndRotation(world, i, j, k, rotation, 0);
         this.setBlockAndMetadata(world, 2, 2, 2, LOTRMod.birdCage, 3);
         this.setBlockAndMetadata(world, 2, 3, 2, LOTRMod.goldBars, 0);
         this.placeFlowerPot(world, 0, 2, 1, this.getRandomFlower(world, random));
         LOTREntityGulfTrader trader = new LOTREntityGulfGoldsmith(world);
         this.spawnNPCAndSetHome(trader, world, 2, 1, 1, 4);
         return true;
      }
   }

   private static class Miner extends LOTRWorldGenStructureBase2 {
      public Miner(boolean flag) {
         super(flag);
      }

      public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
         this.setOriginAndRotation(world, i, j, k, rotation, 0);
         this.setBlockAndMetadata(world, 1, 1, 2, LOTRMod.chestBasket, 2);
         this.setBlockAndMetadata(world, 2, 1, 2, LOTRMod.oreTin, 0);
         this.setBlockAndMetadata(world, 2, 2, 2, LOTRMod.oreCopper, 0);
         this.placeWeaponRack(world, 0, 2, 2, 1, new ItemStack(LOTRMod.pickaxeBronze));
         LOTREntityGulfTrader trader = new LOTREntityGulfMiner(world);
         this.spawnNPCAndSetHome(trader, world, 2, 1, 1, 4);
         return true;
      }
   }

   private static class Baker extends LOTRWorldGenStructureBase2 {
      public Baker(boolean flag) {
         super(flag);
      }

      public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
         this.setOriginAndRotation(world, i, j, k, rotation, 0);
         this.setBlockAndMetadata(world, 2, 1, 2, Blocks.field_150460_al, 2);
         this.setBlockAndMetadata(world, 1, 1, 2, LOTRMod.chestBasket, 2);
         this.placePlate_item(world, random, 1, 2, 0, LOTRMod.ceramicPlateBlock, new ItemStack(Items.field_151025_P, 1 + random.nextInt(3)), true);
         this.setBlockAndMetadata(world, 3, 2, 2, LOTRMod.bananaCake, 0);
         this.placeWeaponRack(world, 0, 2, 2, 1, new ItemStack(LOTRMod.rollingPin));
         LOTREntityGulfTrader trader = new LOTREntityGulfBaker(world);
         this.spawnNPCAndSetHome(trader, world, 2, 1, 1, 4);
         return true;
      }
   }

   private static class Fish extends LOTRWorldGenStructureBase2 {
      public Fish(boolean flag) {
         super(flag);
      }

      public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
         this.setOriginAndRotation(world, i, j, k, rotation, 0);
         this.setBlockAndMetadata(world, 2, 1, 2, Blocks.field_150383_bp, 3);
         this.placePlate_item(world, random, 1, 2, 0, LOTRMod.woodPlateBlock, new ItemStack(Items.field_151115_aP, 1 + random.nextInt(3), 0), true);
         this.placePlate_item(world, random, 0, 2, 2, LOTRMod.woodPlateBlock, new ItemStack(Items.field_151115_aP, 1 + random.nextInt(3), 1), true);
         this.placePlate_item(world, random, 3, 2, 1, LOTRMod.woodPlateBlock, new ItemStack(Items.field_151115_aP, 1 + random.nextInt(3), 0), true);
         this.placeWeaponRack(world, 1, 2, 3, 0, new ItemStack(Items.field_151112_aM));
         LOTREntityGulfTrader trader = new LOTREntityGulfFishmonger(world);
         this.spawnNPCAndSetHome(trader, world, 2, 1, 1, 4);
         return true;
      }
   }

   private static class Brewer extends LOTRWorldGenStructureBase2 {
      public Brewer(boolean flag) {
         super(flag);
      }

      public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
         this.setOriginAndRotation(world, i, j, k, rotation, 0);
         this.setBlockAndMetadata(world, 1, 1, 1, LOTRMod.barrel, 3);
         this.placeMug(world, random, 1, 2, 0, 0, LOTRFoods.GULF_HARAD_DRINK);
         this.placeMug(world, random, 0, 2, 2, 3, LOTRFoods.GULF_HARAD_DRINK);
         this.placeMug(world, random, 3, 2, 1, 1, LOTRFoods.GULF_HARAD_DRINK);
         this.placeFlowerPot(world, 2, 2, 3, this.getRandomFlower(world, random));
         LOTREntityGulfTrader trader = new LOTREntityGulfBrewer(world);
         this.spawnNPCAndSetHome(trader, world, 2, 1, 1, 4);
         return true;
      }
   }

   private static class Butcher extends LOTRWorldGenStructureBase2 {
      public Butcher(boolean flag) {
         super(flag);
      }

      public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
         this.setOriginAndRotation(world, i, j, k, rotation, 0);
         this.placePlate_item(world, random, 1, 2, 0, LOTRMod.woodPlateBlock, new ItemStack(LOTRMod.rabbitRaw, 1 + random.nextInt(3)), true);
         this.placePlate_item(world, random, 0, 2, 2, LOTRMod.woodPlateBlock, new ItemStack(LOTRMod.camelRaw, 1 + random.nextInt(3)), true);
         this.placePlate_item(world, random, 3, 2, 1, LOTRMod.woodPlateBlock, new ItemStack(LOTRMod.muttonRaw, 1 + random.nextInt(3)), true);
         this.placeSkull(world, random, 2, 2, 3);
         LOTREntityGulfTrader trader = new LOTREntityGulfButcher(world);
         this.spawnNPCAndSetHome(trader, world, 2, 1, 1, 4);
         return true;
      }
   }

   private static class Mason extends LOTRWorldGenStructureBase2 {
      public Mason(boolean flag) {
         super(flag);
      }

      public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
         this.setOriginAndRotation(world, i, j, k, rotation, 0);
         this.setBlockAndMetadata(world, 2, 1, 2, LOTRMod.brick, 15);
         this.setBlockAndMetadata(world, 2, 2, 2, LOTRMod.brick3, 13);
         this.placeFlowerPot(world, 0, 2, 2, this.getRandomFlower(world, random));
         this.placeWeaponRack(world, 3, 2, 2, 3, new ItemStack(LOTRMod.pickaxeBronze));
         LOTREntityGulfTrader trader = new LOTREntityGulfMason(world);
         this.spawnNPCAndSetHome(trader, world, 2, 1, 1, 4);
         return true;
      }
   }
}
