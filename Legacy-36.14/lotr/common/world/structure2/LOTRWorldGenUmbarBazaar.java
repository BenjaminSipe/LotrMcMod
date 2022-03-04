package lotr.common.world.structure2;

import java.util.Random;
import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityNearHaradBlacksmith;
import lotr.common.entity.npc.LOTREntityUmbarBaker;
import lotr.common.entity.npc.LOTREntityUmbarBrewer;
import lotr.common.entity.npc.LOTREntityUmbarButcher;
import lotr.common.entity.npc.LOTREntityUmbarFarmer;
import lotr.common.entity.npc.LOTREntityUmbarFishmonger;
import lotr.common.entity.npc.LOTREntityUmbarFlorist;
import lotr.common.entity.npc.LOTREntityUmbarGoldsmith;
import lotr.common.entity.npc.LOTREntityUmbarLumberman;
import lotr.common.entity.npc.LOTREntityUmbarMason;
import lotr.common.entity.npc.LOTREntityUmbarMiner;
import lotr.common.entity.npc.LOTREntityUmbarTrader;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTRWorldGenUmbarBazaar extends LOTRWorldGenSouthronBazaar {
   private static Class[] stallsUmbar = new Class[]{LOTRWorldGenUmbarBazaar.Lumber.class, LOTRWorldGenUmbarBazaar.Mason.class, LOTRWorldGenUmbarBazaar.Fish.class, LOTRWorldGenUmbarBazaar.Baker.class, LOTRWorldGenUmbarBazaar.Goldsmith.class, LOTRWorldGenUmbarBazaar.Farmer.class, LOTRWorldGenUmbarBazaar.Blacksmith.class, LOTRWorldGenUmbarBazaar.Brewer.class, LOTRWorldGenUmbarBazaar.Miner.class, LOTRWorldGenUmbarBazaar.Florist.class, LOTRWorldGenUmbarBazaar.Butcher.class};

   public LOTRWorldGenUmbarBazaar(boolean flag) {
      super(flag);
   }

   protected boolean isUmbar() {
      return true;
   }

   protected Class[] getStallClasses() {
      return stallsUmbar;
   }

   private static class Butcher extends LOTRWorldGenSouthronStructure {
      public Butcher(boolean flag) {
         super(flag);
      }

      protected boolean isUmbar() {
         return true;
      }

      public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
         this.setOriginAndRotation(world, i, j, k, rotation, 0);
         this.setupRandomBlocks(random);
         this.setBlockAndMetadata(world, 0, 1, 1, Blocks.field_150460_al, 2);
         this.placeKebabStand(world, random, 0, 2, 1, LOTRMod.kebabStand, 3);
         this.placePlate_item(world, random, -2, 2, 0, LOTRMod.ceramicPlateBlock, new ItemStack(LOTRMod.muttonRaw, 1 + random.nextInt(3), 0), true);
         this.placePlate_item(world, random, 2, 2, 0, LOTRMod.ceramicPlateBlock, new ItemStack(LOTRMod.camelRaw, 1 + random.nextInt(3), 1), true);
         LOTREntityUmbarTrader trader = new LOTREntityUmbarButcher(world);
         this.spawnNPCAndSetHome(trader, world, 0, 1, 0, 4);
         return true;
      }
   }

   private static class Florist extends LOTRWorldGenSouthronStructure {
      public Florist(boolean flag) {
         super(flag);
      }

      protected boolean isUmbar() {
         return true;
      }

      public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
         this.setOriginAndRotation(world, i, j, k, rotation, 0);
         this.setupRandomBlocks(random);
         this.placeFlowerPot(world, -2, 2, 0, this.getRandomFlower(world, random));
         this.placeFlowerPot(world, 2, 2, 0, this.getRandomFlower(world, random));
         this.setBlockAndMetadata(world, -1, 0, 1, Blocks.field_150349_c, 0);
         this.setBlockAndMetadata(world, -1, 1, 1, LOTRMod.doubleFlower, 3);
         this.setBlockAndMetadata(world, -1, 2, 1, LOTRMod.doubleFlower, 11);
         this.setBlockAndMetadata(world, 1, 1, 1, Blocks.field_150349_c, 0);
         this.plantFlower(world, random, 1, 2, 1);
         this.setBlockAndMetadata(world, 1, 1, 0, Blocks.field_150415_aT, 12);
         this.setBlockAndMetadata(world, 0, 1, 1, Blocks.field_150415_aT, 15);
         LOTREntityUmbarTrader trader = new LOTREntityUmbarFlorist(world);
         this.spawnNPCAndSetHome(trader, world, 0, 1, 0, 4);
         return true;
      }
   }

   private static class Miner extends LOTRWorldGenSouthronStructure {
      public Miner(boolean flag) {
         super(flag);
      }

      protected boolean isUmbar() {
         return true;
      }

      public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
         this.setOriginAndRotation(world, i, j, k, rotation, 0);
         this.setupRandomBlocks(random);
         this.setBlockAndMetadata(world, -1, 1, 1, LOTRMod.oreTin, 0);
         this.setBlockAndMetadata(world, -1, 2, 1, LOTRMod.oreCopper, 0);
         this.setBlockAndMetadata(world, 1, 1, 1, Blocks.field_150366_p, 0);
         this.placeWeaponRack(world, 1, 2, 1, 2, new ItemStack(Items.field_151035_b));
         LOTREntityUmbarTrader trader = new LOTREntityUmbarMiner(world);
         this.spawnNPCAndSetHome(trader, world, 0, 1, 0, 4);
         return true;
      }
   }

   private static class Brewer extends LOTRWorldGenSouthronStructure {
      public Brewer(boolean flag) {
         super(flag);
      }

      protected boolean isUmbar() {
         return true;
      }

      public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
         this.setOriginAndRotation(world, i, j, k, rotation, 0);
         this.setupRandomBlocks(random);
         this.setBlockAndMetadata(world, -1, 1, 1, LOTRMod.stairsCedar, 6);
         this.setBlockAndMetadata(world, -1, 2, 1, LOTRMod.barrel, 2);
         this.setBlockAndMetadata(world, 1, 1, 1, LOTRMod.stairsCedar, 6);
         this.setBlockAndMetadata(world, 1, 2, 1, LOTRMod.barrel, 2);
         this.placeMug(world, random, -2, 2, 0, 1, LOTRFoods.SOUTHRON_DRINK);
         this.placeMug(world, random, 2, 2, 0, 1, LOTRFoods.SOUTHRON_DRINK);
         LOTREntityUmbarTrader trader = new LOTREntityUmbarBrewer(world);
         this.spawnNPCAndSetHome(trader, world, 0, 1, 0, 4);
         return true;
      }
   }

   private static class Blacksmith extends LOTRWorldGenSouthronStructure {
      public Blacksmith(boolean flag) {
         super(flag);
      }

      protected boolean isUmbar() {
         return true;
      }

      public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
         this.setOriginAndRotation(world, i, j, k, rotation, 0);
         this.setupRandomBlocks(random);
         this.setBlockAndMetadata(world, -1, 1, 1, Blocks.field_150467_bQ, 3);
         this.placeArmorStand(world, 1, 1, 1, 0, new ItemStack[]{new ItemStack(LOTRMod.helmetNearHarad), new ItemStack(LOTRMod.bodyNearHarad), null, null});
         this.placeWeaponRack(world, -2, 2, 0, 1, (new LOTRWorldGenUmbarBazaar(false)).getRandomHaradWeapon(random));
         this.placeWeaponRack(world, 2, 2, 0, 3, (new LOTRWorldGenUmbarBazaar(false)).getRandomHaradWeapon(random));
         LOTREntityNearHaradBlacksmith trader = new LOTREntityNearHaradBlacksmith(world);
         this.spawnNPCAndSetHome(trader, world, 0, 1, 0, 4);
         return true;
      }
   }

   private static class Farmer extends LOTRWorldGenSouthronStructure {
      public Farmer(boolean flag) {
         super(flag);
      }

      protected boolean isUmbar() {
         return true;
      }

      public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
         this.setOriginAndRotation(world, i, j, k, rotation, 0);
         this.setupRandomBlocks(random);
         this.setBlockAndMetadata(world, -1, 1, 1, Blocks.field_150383_bp, 3);
         this.setBlockAndMetadata(world, 1, 1, 1, Blocks.field_150407_cf, 0);
         this.setBlockAndMetadata(world, -1, 1, -1, Blocks.field_150407_cf, 0);
         this.placePlate_item(world, random, -2, 2, 0, LOTRMod.woodPlateBlock, new ItemStack(LOTRMod.orange, 1 + random.nextInt(3), 0), true);
         this.placePlate_item(world, random, 2, 2, 0, LOTRMod.woodPlateBlock, new ItemStack(LOTRMod.lettuce, 1 + random.nextInt(3), 1), true);
         LOTREntityUmbarFarmer trader = new LOTREntityUmbarFarmer(world);
         this.spawnNPCAndSetHome(trader, world, 0, 1, 0, 4);
         return true;
      }
   }

   private static class Goldsmith extends LOTRWorldGenSouthronStructure {
      public Goldsmith(boolean flag) {
         super(flag);
      }

      protected boolean isUmbar() {
         return true;
      }

      public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
         this.setOriginAndRotation(world, i, j, k, rotation, 0);
         this.setupRandomBlocks(random);
         this.setBlockAndMetadata(world, -1, 1, -1, LOTRMod.goldBars, 0);
         this.setBlockAndMetadata(world, 1, 1, -1, LOTRMod.goldBars, 0);
         this.setBlockAndMetadata(world, -1, 1, 1, LOTRMod.goldBars, 0);
         this.setBlockAndMetadata(world, 1, 1, 1, LOTRMod.goldBars, 0);
         this.setBlockAndMetadata(world, random.nextBoolean() ? -1 : 1, 2, -1, LOTRMod.birdCage, 2);
         this.setBlockAndMetadata(world, random.nextBoolean() ? -1 : 1, 2, 1, LOTRMod.birdCage, 3);
         LOTREntityUmbarTrader trader = new LOTREntityUmbarGoldsmith(world);
         this.spawnNPCAndSetHome(trader, world, 0, 1, 0, 4);
         return true;
      }
   }

   private static class Baker extends LOTRWorldGenSouthronStructure {
      public Baker(boolean flag) {
         super(flag);
      }

      protected boolean isUmbar() {
         return true;
      }

      public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
         this.setOriginAndRotation(world, i, j, k, rotation, 0);
         this.setupRandomBlocks(random);
         this.setBlockAndMetadata(world, 0, 1, 1, Blocks.field_150460_al, 2);
         this.setBlockAndMetadata(world, -1, 1, 1, LOTRMod.planks2, 2);
         this.setBlockAndMetadata(world, 1, 1, 1, LOTRMod.planks2, 2);
         this.placePlate_item(world, random, -1, 2, 1, LOTRMod.ceramicPlateBlock, new ItemStack(Items.field_151025_P, 1 + random.nextInt(3)), true);
         this.placePlate_item(world, random, 1, 2, 1, LOTRMod.ceramicPlateBlock, new ItemStack(LOTRMod.oliveBread, 1 + random.nextInt(3)), true);
         this.placeFlowerPot(world, random.nextBoolean() ? -2 : 2, 2, 0, this.getRandomFlower(world, random));
         LOTREntityUmbarTrader trader = new LOTREntityUmbarBaker(world);
         this.spawnNPCAndSetHome(trader, world, 0, 1, 0, 4);
         return true;
      }
   }

   private static class Fish extends LOTRWorldGenSouthronStructure {
      public Fish(boolean flag) {
         super(flag);
      }

      protected boolean isUmbar() {
         return true;
      }

      public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
         this.setOriginAndRotation(world, i, j, k, rotation, 0);
         this.setupRandomBlocks(random);
         this.setBlockAndMetadata(world, 1, 1, 1, Blocks.field_150383_bp, 3);
         this.setBlockAndMetadata(world, -1, 1, -1, Blocks.field_150360_v, 0);
         this.placePlate_item(world, random, -2, 2, 0, LOTRMod.ceramicPlateBlock, new ItemStack(Items.field_151115_aP, 1 + random.nextInt(3), 0), true);
         this.placePlate_item(world, random, 2, 2, 0, LOTRMod.ceramicPlateBlock, new ItemStack(Items.field_151115_aP, 1 + random.nextInt(3), 1), true);
         LOTREntityUmbarTrader trader = new LOTREntityUmbarFishmonger(world);
         this.spawnNPCAndSetHome(trader, world, 0, 1, 0, 4);
         return true;
      }
   }

   private static class Mason extends LOTRWorldGenSouthronStructure {
      public Mason(boolean flag) {
         super(flag);
      }

      protected boolean isUmbar() {
         return true;
      }

      public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
         this.setOriginAndRotation(world, i, j, k, rotation, 0);
         this.setupRandomBlocks(random);
         this.setBlockAndMetadata(world, -1, 1, 1, LOTRMod.brick6, 6);
         this.setBlockAndMetadata(world, -1, 2, 1, LOTRMod.slabSingle13, 2);
         this.setBlockAndMetadata(world, 1, 1, 1, LOTRMod.brick6, 9);
         this.placeWeaponRack(world, 1, 2, 1, 2, new ItemStack(Items.field_151035_b));
         LOTREntityUmbarTrader trader = new LOTREntityUmbarMason(world);
         this.spawnNPCAndSetHome(trader, world, 0, 1, 0, 4);
         return true;
      }
   }

   private static class Lumber extends LOTRWorldGenSouthronStructure {
      public Lumber(boolean flag) {
         super(flag);
      }

      public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
         this.setOriginAndRotation(world, i, j, k, rotation, 0);
         this.setupRandomBlocks(random);
         this.setBlockAndMetadata(world, -1, 1, 1, LOTRMod.wood4, 10);
         this.setBlockAndMetadata(world, 1, 1, 1, LOTRMod.wood4, 2);
         this.setBlockAndMetadata(world, 1, 2, 1, LOTRMod.wood4, 2);
         this.placeFlowerPot(world, -2, 2, 0, new ItemStack(LOTRMod.sapling4, 1, 2));
         this.placeFlowerPot(world, 2, 2, 0, new ItemStack(LOTRMod.sapling8, 1, 3));
         LOTREntityUmbarTrader trader = new LOTREntityUmbarLumberman(world);
         this.spawnNPCAndSetHome(trader, world, 0, 1, 0, 4);
         return true;
      }
   }
}
