package lotr.common.world.structure2;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.entity.animal.LOTREntityBird;
import lotr.common.entity.animal.LOTREntityButterfly;
import lotr.common.entity.npc.LOTREntityHarnedorBaker;
import lotr.common.entity.npc.LOTREntityHarnedorBlacksmith;
import lotr.common.entity.npc.LOTREntityHarnedorBrewer;
import lotr.common.entity.npc.LOTREntityHarnedorButcher;
import lotr.common.entity.npc.LOTREntityHarnedorFarmer;
import lotr.common.entity.npc.LOTREntityHarnedorFishmonger;
import lotr.common.entity.npc.LOTREntityHarnedorHunter;
import lotr.common.entity.npc.LOTREntityHarnedorLumberman;
import lotr.common.entity.npc.LOTREntityHarnedorMason;
import lotr.common.entity.npc.LOTREntityHarnedorMiner;
import lotr.common.entity.npc.LOTREntityHarnedorTrader;
import lotr.common.item.LOTRItemBanner;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTRWorldGenHarnedorMarket extends LOTRWorldGenHarnedorStructure {
   private static Class[] stalls = new Class[]{LOTRWorldGenHarnedorMarket.Brewer.class, LOTRWorldGenHarnedorMarket.Fish.class, LOTRWorldGenHarnedorMarket.Butcher.class, LOTRWorldGenHarnedorMarket.Baker.class, LOTRWorldGenHarnedorMarket.Lumber.class, LOTRWorldGenHarnedorMarket.Miner.class, LOTRWorldGenHarnedorMarket.Mason.class, LOTRWorldGenHarnedorMarket.Hunter.class, LOTRWorldGenHarnedorMarket.Blacksmith.class, LOTRWorldGenHarnedorMarket.Farmer.class};

   public LOTRWorldGenHarnedorMarket(boolean flag) {
      super(flag);
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, 8);
      this.setupRandomBlocks(random);
      int minHeight;
      int maxHeight;
      int k1;
      int step;
      int j1;
      if (this.restrictions) {
         minHeight = 0;
         maxHeight = 0;

         for(k1 = -9; k1 <= 9; ++k1) {
            for(step = -9; step <= 9; ++step) {
               j1 = this.getTopBlock(world, k1, step) - 1;
               if (!this.isSurface(world, k1, j1, step)) {
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

      for(minHeight = -8; minHeight <= 8; ++minHeight) {
         for(maxHeight = -8; maxHeight <= 8; ++maxHeight) {
            k1 = Math.abs(minHeight);
            step = Math.abs(maxHeight);
            if (k1 <= 6 && step <= 6 || k1 == 7 && step <= 4 || step == 7 && k1 <= 4 || k1 == 8 && step <= 1 || step == 8 && k1 <= 1) {
               for(j1 = 1; j1 <= 8; ++j1) {
                  this.setAir(world, minHeight, j1, maxHeight);
               }

               for(j1 = -1; !this.isOpaque(world, minHeight, j1, maxHeight) && this.getY(j1) >= 0; --j1) {
                  this.setBlockAndMetadata(world, minHeight, j1, maxHeight, this.plank2Block, this.plank2Meta);
                  this.setGrassToDirt(world, minHeight, j1 - 1, maxHeight);
               }
            }
         }
      }

      this.loadStrScan("harnedor_market");
      this.associateBlockMetaAlias("WOOD", this.woodBlock, this.woodMeta);
      this.associateBlockMetaAlias("WOOD|12", this.woodBlock, this.woodMeta | 12);
      this.associateBlockMetaAlias("PLANK", this.plankBlock, this.plankMeta);
      this.associateBlockMetaAlias("PLANK_SLAB", this.plankSlabBlock, this.plankSlabMeta);
      this.associateBlockMetaAlias("PLANK_SLAB_INV", this.plankSlabBlock, this.plankSlabMeta | 8);
      this.associateBlockAlias("PLANK_STAIR", this.plankStairBlock);
      this.associateBlockMetaAlias("FENCE", this.fenceBlock, this.fenceMeta);
      this.associateBlockAlias("FENCE_GATE", this.fenceGateBlock);
      this.associateBlockMetaAlias("PLANK2", this.plank2Block, this.plank2Meta);
      this.associateBlockMetaAlias("ROOF", this.roofBlock, this.roofMeta);
      this.generateStrScan(world, random, 0, 1, 0);
      this.placeWallBanner(world, 0, 5, -2, LOTRItemBanner.BannerType.NEAR_HARAD, 2);
      this.placeWallBanner(world, 0, 5, 2, LOTRItemBanner.BannerType.NEAR_HARAD, 0);
      this.placeWallBanner(world, -2, 5, 0, LOTRItemBanner.BannerType.NEAR_HARAD, 3);
      this.placeWallBanner(world, 2, 5, 0, LOTRItemBanner.BannerType.NEAR_HARAD, 1);
      this.spawnItemFrame(world, 2, 2, -3, 3, this.getHarnedorFramedItem(random));
      this.spawnItemFrame(world, -2, 2, 3, 1, this.getHarnedorFramedItem(random));
      this.placeWeaponRack(world, -3, 2, 1, 6, this.getRandomHarnedorWeapon(random));
      this.placeArmorStand(world, 2, 1, -2, 2, new ItemStack[]{new ItemStack(LOTRMod.helmetHarnedor), null, null, null});
      this.placeFlowerPot(world, -2, 2, 2, this.getRandomFlower(world, random));
      this.placeAnimalJar(world, 2, 1, 1, LOTRMod.butterflyJar, 0, new LOTREntityButterfly(world));
      this.placeAnimalJar(world, -3, 1, -1, LOTRMod.birdCageWood, 0, new LOTREntityBird(world));
      this.placeAnimalJar(world, -2, 3, -2, LOTRMod.birdCage, 0, new LOTREntityBird(world));
      this.placeAnimalJar(world, 6, 3, 1, LOTRMod.birdCage, 0, new LOTREntityBird(world));
      this.placeSkull(world, random, 2, 4, -5);
      List stallClasses = Arrays.asList(Arrays.copyOf(stalls, stalls.length));
      Collections.shuffle(stallClasses, random);

      try {
         LOTRWorldGenStructureBase2 stall0 = (LOTRWorldGenStructureBase2)((Class)stallClasses.get(0)).getConstructor(Boolean.TYPE).newInstance(this.notifyChanges);
         LOTRWorldGenStructureBase2 stall1 = (LOTRWorldGenStructureBase2)((Class)stallClasses.get(1)).getConstructor(Boolean.TYPE).newInstance(this.notifyChanges);
         LOTRWorldGenStructureBase2 stall2 = (LOTRWorldGenStructureBase2)((Class)stallClasses.get(2)).getConstructor(Boolean.TYPE).newInstance(this.notifyChanges);
         LOTRWorldGenStructureBase2 stall3 = (LOTRWorldGenStructureBase2)((Class)stallClasses.get(3)).getConstructor(Boolean.TYPE).newInstance(this.notifyChanges);
         this.generateSubstructure(stall0, world, random, 2, 1, 2, 0);
         this.generateSubstructure(stall1, world, random, 2, 1, -2, 1);
         this.generateSubstructure(stall2, world, random, -2, 1, -2, 2);
         this.generateSubstructure(stall3, world, random, -2, 1, 2, 3);
      } catch (Exception var14) {
         var14.printStackTrace();
      }

      int maxSteps = true;

      int i1;
      int j2;
      for(k1 = -1; k1 <= 1; ++k1) {
         for(step = 0; step < 12; ++step) {
            j1 = 0 - step;
            i1 = -9 - step;
            if (this.isOpaque(world, k1, j1, i1)) {
               break;
            }

            this.setBlockAndMetadata(world, k1, j1, i1, this.plank2StairBlock, 2);
            this.setGrassToDirt(world, k1, j1 - 1, i1);

            for(j2 = j1 - 1; !this.isOpaque(world, k1, j2, i1) && this.getY(j2) >= 0; --j2) {
               this.setBlockAndMetadata(world, k1, j2, i1, this.plank2Block, this.plank2Meta);
               this.setGrassToDirt(world, k1, j2 - 1, i1);
            }
         }
      }

      for(k1 = -1; k1 <= 1; ++k1) {
         for(step = 0; step < 12; ++step) {
            j1 = 0 - step;
            i1 = 9 + step;
            if (this.isOpaque(world, k1, j1, i1)) {
               break;
            }

            this.setBlockAndMetadata(world, k1, j1, i1, this.plank2StairBlock, 3);
            this.setGrassToDirt(world, k1, j1 - 1, i1);

            for(j2 = j1 - 1; !this.isOpaque(world, k1, j2, i1) && this.getY(j2) >= 0; --j2) {
               this.setBlockAndMetadata(world, k1, j2, i1, this.plank2Block, this.plank2Meta);
               this.setGrassToDirt(world, k1, j2 - 1, i1);
            }
         }
      }

      for(k1 = -1; k1 <= 1; ++k1) {
         for(step = 0; step < 12; ++step) {
            j1 = 0 - step;
            i1 = -9 - step;
            if (this.isOpaque(world, i1, j1, k1)) {
               break;
            }

            this.setBlockAndMetadata(world, i1, j1, k1, this.plank2StairBlock, 1);
            this.setGrassToDirt(world, i1, j1 - 1, k1);

            for(j2 = j1 - 1; !this.isOpaque(world, i1, j2, k1) && this.getY(j2) >= 0; --j2) {
               this.setBlockAndMetadata(world, i1, j2, k1, this.plank2Block, this.plank2Meta);
               this.setGrassToDirt(world, i1, j2 - 1, k1);
            }
         }
      }

      for(k1 = -1; k1 <= 1; ++k1) {
         for(step = 0; step < 12; ++step) {
            j1 = 0 - step;
            i1 = 9 + step;
            if (this.isOpaque(world, i1, j1, k1)) {
               break;
            }

            this.setBlockAndMetadata(world, i1, j1, k1, this.plank2StairBlock, 0);
            this.setGrassToDirt(world, i1, j1 - 1, k1);

            for(j2 = j1 - 1; !this.isOpaque(world, i1, j2, k1) && this.getY(j2) >= 0; --j2) {
               this.setBlockAndMetadata(world, i1, j2, k1, this.plank2Block, this.plank2Meta);
               this.setGrassToDirt(world, i1, j2 - 1, k1);
            }
         }
      }

      return true;
   }

   private static class Farmer extends LOTRWorldGenStructureBase2 {
      public Farmer(boolean flag) {
         super(flag);
      }

      public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
         this.setOriginAndRotation(world, i, j, k, rotation, 0);
         this.setBlockAndMetadata(world, 2, 1, 4, Blocks.field_150407_cf, 0);
         this.setBlockAndMetadata(world, 3, 1, 3, Blocks.field_150407_cf, 0);
         this.setBlockAndMetadata(world, 3, 1, 2, LOTRMod.berryBush, 9);
         this.setBlockAndMetadata(world, 4, 1, 2, LOTRMod.berryBush, 9);
         this.placePlate_item(world, random, 3, 2, 0, LOTRMod.woodPlateBlock, this.getRandomFarmFood(random), true);
         this.placePlate_item(world, random, 0, 2, 2, LOTRMod.woodPlateBlock, this.getRandomFarmFood(random), true);
         this.placePlate_item(world, random, 0, 2, 4, LOTRMod.woodPlateBlock, this.getRandomFarmFood(random), true);
         LOTREntityHarnedorFarmer trader = new LOTREntityHarnedorFarmer(world);
         this.spawnNPCAndSetHome(trader, world, 2, 1, 2, 4);
         return true;
      }

      private ItemStack getRandomFarmFood(Random random) {
         ItemStack[] items = new ItemStack[]{new ItemStack(LOTRMod.orange), new ItemStack(LOTRMod.lemon), new ItemStack(LOTRMod.lime), new ItemStack(Items.field_151172_bF), new ItemStack(Items.field_151174_bG), new ItemStack(LOTRMod.lettuce), new ItemStack(LOTRMod.turnip)};
         ItemStack ret = items[random.nextInt(items.length)].func_77946_l();
         ret.field_77994_a = 1 + random.nextInt(3);
         return ret;
      }
   }

   private static class Blacksmith extends LOTRWorldGenStructureBase2 {
      public Blacksmith(boolean flag) {
         super(flag);
      }

      public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
         this.setOriginAndRotation(world, i, j, k, rotation, 0);
         this.placeWeaponRack(world, 3, 2, 0, 2, (new LOTRWorldGenHarnedorMarket(false)).getRandomHarnedorWeapon(random));
         this.placeWeaponRack(world, 0, 2, 4, 3, (new LOTRWorldGenHarnedorMarket(false)).getRandomHarnedorWeapon(random));
         this.placeFlowerPot(world, 0, 2, 2, this.getRandomFlower(world, random));
         this.setBlockAndMetadata(world, 3, 1, 3, Blocks.field_150467_bQ, 1);
         this.placeArmorStand(world, 4, 1, 2, 0, new ItemStack[]{new ItemStack(LOTRMod.helmetHarnedor), new ItemStack(LOTRMod.bodyHarnedor), null, null});
         this.placeArmorStand(world, 2, 1, 4, 1, (ItemStack[])null);
         LOTREntityHarnedorBlacksmith trader = new LOTREntityHarnedorBlacksmith(world);
         this.spawnNPCAndSetHome(trader, world, 2, 1, 2, 4);
         return true;
      }
   }

   private static class Hunter extends LOTRWorldGenStructureBase2 {
      public Hunter(boolean flag) {
         super(flag);
      }

      public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
         this.setOriginAndRotation(world, i, j, k, rotation, 0);
         this.placePlate_item(world, random, 2, 2, 0, LOTRMod.woodPlateBlock, new ItemStack(LOTRMod.camelRaw, 1 + random.nextInt(3), 0), true);
         this.placePlate_item(world, random, 0, 2, 3, LOTRMod.woodPlateBlock, new ItemStack(LOTRMod.rabbitRaw, 1 + random.nextInt(3), 0), true);
         this.setBlockAndMetadata(world, 3, 1, 3, LOTRMod.woodSlabSingle4, 15);
         this.placePlate_item(world, random, 3, 2, 3, LOTRMod.woodPlateBlock, new ItemStack(LOTRMod.deerRaw, 1 + random.nextInt(3), 0), true);
         this.spawnItemFrame(world, 4, 2, 3, 2, new ItemStack(LOTRMod.fur));
         this.spawnItemFrame(world, 3, 2, 4, 3, new ItemStack(Items.field_151116_aA));
         LOTREntityHarnedorTrader trader = new LOTREntityHarnedorHunter(world);
         this.spawnNPCAndSetHome(trader, world, 2, 1, 2, 4);
         return true;
      }
   }

   private static class Mason extends LOTRWorldGenStructureBase2 {
      public Mason(boolean flag) {
         super(flag);
      }

      public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
         this.setOriginAndRotation(world, i, j, k, rotation, 0);
         this.placeFlowerPot(world, 2, 2, 0, this.getRandomFlower(world, random));
         this.placeWeaponRack(world, 0, 2, 3, 3, new ItemStack(LOTRMod.pickaxeBronze));
         this.setBlockAndMetadata(world, 4, 1, 2, Blocks.field_150322_A, 0);
         this.setBlockAndMetadata(world, 2, 1, 3, Blocks.field_150322_A, 0);
         this.setBlockAndMetadata(world, 3, 1, 3, LOTRMod.redSandstone, 0);
         this.setBlockAndMetadata(world, 3, 2, 3, LOTRMod.redSandstone, 0);
         this.setBlockAndMetadata(world, 2, 1, 4, LOTRMod.redSandstone, 0);
         LOTREntityHarnedorTrader trader = new LOTREntityHarnedorMason(world);
         this.spawnNPCAndSetHome(trader, world, 2, 1, 2, 4);
         return true;
      }
   }

   private static class Miner extends LOTRWorldGenStructureBase2 {
      public Miner(boolean flag) {
         super(flag);
      }

      public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
         this.setOriginAndRotation(world, i, j, k, rotation, 0);
         this.placeWeaponRack(world, 2, 2, 0, 2, new ItemStack(LOTRMod.pickaxeBronze));
         this.placeWeaponRack(world, 0, 2, 3, 3, new ItemStack(LOTRMod.shovelBronze));
         this.setBlockAndMetadata(world, 4, 1, 2, LOTRMod.oreCopper, 0);
         this.setBlockAndMetadata(world, 2, 1, 3, LOTRMod.oreCopper, 0);
         this.setBlockAndMetadata(world, 3, 1, 3, LOTRMod.oreTin, 0);
         this.setBlockAndMetadata(world, 3, 2, 3, LOTRMod.oreCopper, 0);
         this.setBlockAndMetadata(world, 2, 1, 4, LOTRMod.oreTin, 0);
         LOTREntityHarnedorTrader trader = new LOTREntityHarnedorMiner(world);
         this.spawnNPCAndSetHome(trader, world, 2, 1, 2, 4);
         return true;
      }
   }

   private static class Lumber extends LOTRWorldGenStructureBase2 {
      public Lumber(boolean flag) {
         super(flag);
      }

      public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
         this.setOriginAndRotation(world, i, j, k, rotation, 0);
         this.placeFlowerPot(world, 2, 2, 0, new ItemStack(LOTRMod.sapling4, 1, 2));
         this.placeFlowerPot(world, 0, 2, 2, new ItemStack(LOTRMod.sapling8, 1, 3));
         this.placeFlowerPot(world, 0, 2, 4, new ItemStack(LOTRMod.sapling7, 1, 3));
         this.setBlockAndMetadata(world, 3, 1, 3, LOTRMod.wood8, 3);
         this.setBlockAndMetadata(world, 3, 2, 3, LOTRMod.wood8, 3);
         this.setBlockAndMetadata(world, 2, 1, 4, LOTRMod.wood6, 3);
         this.setBlockAndMetadata(world, 2, 1, 3, LOTRMod.wood6, 11);
         this.setBlockAndMetadata(world, 4, 1, 2, LOTRMod.woodBeam8, 11);
         this.placeWeaponRack(world, 2, 2, 4, 7, new ItemStack(LOTRMod.axeBronze));
         LOTREntityHarnedorTrader trader = new LOTREntityHarnedorLumberman(world);
         this.spawnNPCAndSetHome(trader, world, 2, 1, 2, 4);
         return true;
      }
   }

   private static class Baker extends LOTRWorldGenStructureBase2 {
      public Baker(boolean flag) {
         super(flag);
      }

      public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
         this.setOriginAndRotation(world, i, j, k, rotation, 0);
         this.placeFlowerPot(world, 2, 2, 0, this.getRandomFlower(world, random));
         this.placePlate_item(world, random, 2, 2, 0, LOTRMod.woodPlateBlock, new ItemStack(LOTRMod.oliveBread, 1 + random.nextInt(3), 0), true);
         this.placePlate_item(world, random, 0, 2, 2, LOTRMod.ceramicPlateBlock, new ItemStack(Items.field_151025_P, 1 + random.nextInt(3), 0), true);
         this.setBlockAndMetadata(world, 0, 2, 4, LOTRMod.lemonCake, 0);
         this.setBlockAndMetadata(world, 3, 1, 3, LOTRMod.woodSlabSingle4, 15);
         this.setBlockAndMetadata(world, 3, 2, 3, LOTRMod.marzipanBlock, 0);
         this.placeWeaponRack(world, 2, 2, 4, 7, new ItemStack(LOTRMod.rollingPin));
         LOTREntityHarnedorTrader trader = new LOTREntityHarnedorBaker(world);
         this.spawnNPCAndSetHome(trader, world, 2, 1, 2, 4);
         return true;
      }
   }

   private static class Butcher extends LOTRWorldGenStructureBase2 {
      public Butcher(boolean flag) {
         super(flag);
      }

      public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
         this.setOriginAndRotation(world, i, j, k, rotation, 0);
         this.placePlate_item(world, random, 2, 2, 0, LOTRMod.ceramicPlateBlock, new ItemStack(LOTRMod.camelRaw, 1 + random.nextInt(3), 0), true);
         this.placePlate_item(world, random, 0, 2, 2, LOTRMod.woodPlateBlock, new ItemStack(LOTRMod.kebab, 1 + random.nextInt(3), 0), true);
         this.placePlate_item(world, random, 0, 2, 4, LOTRMod.woodPlateBlock, new ItemStack(LOTRMod.kebab, 1 + random.nextInt(3), 0), true);
         this.setBlockAndMetadata(world, 3, 1, 3, Blocks.field_150460_al, 2);
         this.placeKebabStand(world, random, 3, 2, 3, LOTRMod.kebabStand, 2);
         this.setBlockAndMetadata(world, 2, 3, 3, LOTRMod.kebabBlock, 0);
         this.setBlockAndMetadata(world, 2, 4, 3, LOTRMod.fence2, 2);
         this.setBlockAndMetadata(world, 2, 5, 3, LOTRMod.fence2, 2);
         LOTREntityHarnedorTrader trader = new LOTREntityHarnedorButcher(world);
         this.spawnNPCAndSetHome(trader, world, 2, 1, 2, 4);
         return true;
      }
   }

   private static class Fish extends LOTRWorldGenStructureBase2 {
      public Fish(boolean flag) {
         super(flag);
      }

      public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
         this.setOriginAndRotation(world, i, j, k, rotation, 0);
         this.placePlate_item(world, random, 2, 2, 0, LOTRMod.ceramicPlateBlock, new ItemStack(Items.field_151115_aP, 1 + random.nextInt(3), 1), true);
         this.placePlate_item(world, random, 0, 2, 3, LOTRMod.woodPlateBlock, new ItemStack(Items.field_151115_aP, 1 + random.nextInt(3), 0), true);
         this.placeFlowerPot(world, 0, 2, 4, this.getRandomFlower(world, random));
         this.setBlockAndMetadata(world, 3, 1, 3, LOTRMod.woodSlabSingle4, 15);
         this.placePlate_item(world, random, 3, 2, 3, LOTRMod.woodPlateBlock, new ItemStack(Items.field_151115_aP, 1 + random.nextInt(3), 0), true);
         this.setBlockAndMetadata(world, 2, 1, 4, Blocks.field_150383_bp, 3);
         this.placeWeaponRack(world, 4, 2, 2, 6, new ItemStack(Items.field_151112_aM));
         LOTREntityHarnedorTrader trader = new LOTREntityHarnedorFishmonger(world);
         this.spawnNPCAndSetHome(trader, world, 2, 1, 2, 4);
         return true;
      }
   }

   private static class Brewer extends LOTRWorldGenStructureBase2 {
      public Brewer(boolean flag) {
         super(flag);
      }

      public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
         this.setOriginAndRotation(world, i, j, k, rotation, 0);
         this.placeMug(world, random, 3, 2, 0, 0, LOTRFoods.HARNEDOR_DRINK);
         this.placeMug(world, random, 0, 2, 2, 1, LOTRFoods.HARNEDOR_DRINK);
         this.setBlockAndMetadata(world, 0, 2, 4, LOTRMod.barrel, 4);
         this.setBlockAndMetadata(world, 3, 1, 3, LOTRMod.woodSlabSingle4, 15);
         this.setBlockAndMetadata(world, 3, 2, 3, LOTRMod.barrel, 2);
         LOTREntityHarnedorTrader trader = new LOTREntityHarnedorBrewer(world);
         this.spawnNPCAndSetHome(trader, world, 2, 1, 2, 4);
         return true;
      }
   }
}
