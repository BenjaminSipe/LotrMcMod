package lotr.common.world.structure2;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityBreeBaker;
import lotr.common.entity.npc.LOTREntityBreeBlacksmith;
import lotr.common.entity.npc.LOTREntityBreeBrewer;
import lotr.common.entity.npc.LOTREntityBreeButcher;
import lotr.common.entity.npc.LOTREntityBreeFarmer;
import lotr.common.entity.npc.LOTREntityBreeFlorist;
import lotr.common.entity.npc.LOTREntityBreeGuard;
import lotr.common.entity.npc.LOTREntityBreeHobbitBaker;
import lotr.common.entity.npc.LOTREntityBreeHobbitBrewer;
import lotr.common.entity.npc.LOTREntityBreeHobbitButcher;
import lotr.common.entity.npc.LOTREntityBreeHobbitFlorist;
import lotr.common.entity.npc.LOTREntityBreeLumberman;
import lotr.common.entity.npc.LOTREntityBreeMason;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.block.Block;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public abstract class LOTRWorldGenBreeMarketStall extends LOTRWorldGenBreeStructure {
   private static final Class[] allStallTypes = new Class[]{LOTRWorldGenBreeMarketStall.Baker.class, LOTRWorldGenBreeMarketStall.Butcher.class, LOTRWorldGenBreeMarketStall.Brewer.class, LOTRWorldGenBreeMarketStall.Mason.class, LOTRWorldGenBreeMarketStall.Lumber.class, LOTRWorldGenBreeMarketStall.Smith.class, LOTRWorldGenBreeMarketStall.Florist.class, LOTRWorldGenBreeMarketStall.Farmer.class};
   protected Block wool1Block;
   protected int wool1Meta;
   protected Block wool2Block;
   protected int wool2Meta;

   public static LOTRWorldGenBreeMarketStall getRandomStall(Random random, boolean flag) {
      try {
         Class cls = allStallTypes[random.nextInt(allStallTypes.length)];
         return (LOTRWorldGenBreeMarketStall)cls.getConstructor(Boolean.TYPE).newInstance(flag);
      } catch (Exception var3) {
         var3.printStackTrace();
         return null;
      }
   }

   public static LOTRWorldGenBreeMarketStall[] getRandomStalls(Random random, boolean flag, int num) {
      List types = Arrays.asList(Arrays.copyOf(allStallTypes, allStallTypes.length));
      Collections.shuffle(types, random);
      LOTRWorldGenBreeMarketStall[] ret = new LOTRWorldGenBreeMarketStall[num];

      for(int i = 0; i < ret.length; ++i) {
         int listIndex = i % types.size();
         Class cls = (Class)types.get(listIndex);

         try {
            ret[i] = (LOTRWorldGenBreeMarketStall)cls.getConstructor(Boolean.TYPE).newInstance(flag);
         } catch (Exception var9) {
            var9.printStackTrace();
         }
      }

      return ret;
   }

   public LOTRWorldGenBreeMarketStall(boolean flag) {
      super(flag);
   }

   protected void setupRandomBlocks(Random random) {
      super.setupRandomBlocks(random);
      this.wool1Block = Blocks.field_150325_L;
      this.wool1Meta = 14;
      this.wool2Block = Blocks.field_150325_L;
      this.wool2Meta = 0;
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, 3);
      this.setupRandomBlocks(random);
      int i1;
      int step;
      int i1;
      int j1;
      int k1;
      if (this.restrictions) {
         i1 = 0;
         step = 0;

         for(i1 = -3; i1 <= 3; ++i1) {
            for(j1 = -3; j1 <= 3; ++j1) {
               k1 = this.getTopBlock(world, i1, j1) - 1;
               if (!this.isSurface(world, i1, k1, j1)) {
                  return false;
               }

               if (k1 < i1) {
                  i1 = k1;
               }

               if (k1 > step) {
                  step = k1;
               }

               if (step - i1 > 6) {
                  return false;
               }
            }
         }
      }

      for(i1 = -2; i1 <= 2; ++i1) {
         for(step = -2; step <= 2; ++step) {
            i1 = Math.abs(i1);
            j1 = Math.abs(step);
            if (i1 == 2 && j1 == 2) {
               for(k1 = 3; (k1 >= 0 || !this.isOpaque(world, i1, k1, step)) && this.getY(k1) >= 0; --k1) {
                  this.setBlockAndMetadata(world, i1, k1, step, this.beamBlock, this.beamMeta);
                  this.setGrassToDirt(world, i1, k1 - 1, step);
               }
            } else {
               this.placeRandomFloor(world, random, i1, 0, step);
               this.setGrassToDirt(world, i1, -1, step);

               for(k1 = -1; !this.isOpaque(world, i1, k1, step) && this.getY(k1) >= 0; --k1) {
                  this.setBlockAndMetadata(world, i1, k1, step, Blocks.field_150346_d, 0);
                  this.setGrassToDirt(world, i1, k1 - 1, step);
               }

               for(k1 = 1; k1 <= 4; ++k1) {
                  this.setAir(world, i1, k1, step);
               }

               if (i1 == 2 & j1 <= 1 || j1 == 2 && i1 <= 1) {
                  this.setBlockAndMetadata(world, i1, 3, step, this.fenceBlock, this.fenceMeta);
               }
            }
         }
      }

      for(i1 = -3; i1 <= 3; ++i1) {
         for(step = -3; step <= 3; ++step) {
            i1 = Math.abs(i1);
            j1 = Math.abs(step);
            if (i1 == 3 && j1 >= 1 && j1 <= 2 || j1 == 3 && i1 >= 1 && i1 <= 2) {
               this.setBlockAndMetadata(world, i1, 3, step, this.wool1Block, this.wool1Meta);
            }

            if (i1 + j1 == 3 || i1 + j1 == 4) {
               if (i1 == 2 && j1 == 2) {
                  this.setBlockAndMetadata(world, i1, 4, step, this.wool2Block, this.wool2Meta);
               } else {
                  this.setBlockAndMetadata(world, i1, 4, step, this.wool1Block, this.wool1Meta);
               }
            }

            if (i1 + j1 <= 2) {
               if (i1 == j1) {
                  this.setBlockAndMetadata(world, i1, 5, step, this.wool2Block, this.wool2Meta);
               } else {
                  this.setBlockAndMetadata(world, i1, 5, step, this.wool1Block, this.wool1Meta);
               }
            }
         }
      }

      this.setBlockAndMetadata(world, -1, 1, -2, this.plankStairBlock, 4);
      this.setBlockAndMetadata(world, 0, 1, -2, this.plankSlabBlock, this.plankSlabMeta | 8);
      this.setBlockAndMetadata(world, 1, 1, -2, this.plankStairBlock, 5);
      this.setBlockAndMetadata(world, 2, 1, -1, this.fenceGateBlock, 3);
      this.setBlockAndMetadata(world, 2, 1, 0, this.plankStairBlock, 7);
      this.setBlockAndMetadata(world, 2, 1, 1, this.plankStairBlock, 6);
      this.setBlockAndMetadata(world, 1, 1, 2, this.plankStairBlock, 5);
      this.setBlockAndMetadata(world, 0, 1, 2, this.plankStairBlock, 4);
      this.setBlockAndMetadata(world, -1, 1, 2, this.fenceGateBlock, 0);
      this.setBlockAndMetadata(world, -2, 1, 1, this.plankStairBlock, 6);
      this.setBlockAndMetadata(world, -2, 1, 0, this.plankSlabBlock, this.plankSlabMeta | 8);
      this.setBlockAndMetadata(world, -2, 1, -1, this.plankStairBlock, 7);

      for(i1 = -1; i1 <= 1; ++i1) {
         this.setBlockAndMetadata(world, i1, 1, -3, this.trapdoorBlock, 12);
      }

      for(i1 = -1; i1 <= 1; ++i1) {
         this.setBlockAndMetadata(world, -3, 1, i1, this.trapdoorBlock, 15);
      }

      if (random.nextBoolean()) {
         this.setBlockAndMetadata(world, 1, 1, 1, Blocks.field_150486_ae, 2);
      } else {
         this.setBlockAndMetadata(world, 1, 1, 1, LOTRMod.chestBasket, 2);
      }

      int maxSteps = true;

      int j2;
      for(step = 0; step < 12; ++step) {
         i1 = 3 + step;
         j1 = 0 - step;
         int k1 = -1;
         if (this.isOpaque(world, i1, j1, k1)) {
            break;
         }

         this.placeRandomFloor(world, random, i1, j1, k1);
         this.setGrassToDirt(world, i1, j1 - 1, k1);

         for(j2 = j1 - 1; !this.isOpaque(world, i1, j2, k1) && this.getY(j2) >= 0; --j2) {
            this.setBlockAndMetadata(world, i1, j2, k1, Blocks.field_150346_d, 0);
            this.setGrassToDirt(world, i1, j2 - 1, k1);
         }
      }

      for(step = 0; step < 12; ++step) {
         int i1 = -1;
         j1 = 0 - step;
         k1 = 3 + step;
         if (this.isOpaque(world, i1, j1, k1)) {
            break;
         }

         this.placeRandomFloor(world, random, i1, j1, k1);
         this.setGrassToDirt(world, i1, j1 - 1, k1);

         for(j2 = j1 - 1; !this.isOpaque(world, i1, j2, k1) && this.getY(j2) >= 0; --j2) {
            this.setBlockAndMetadata(world, i1, j2, k1, Blocks.field_150346_d, 0);
            this.setGrassToDirt(world, i1, j2 - 1, k1);
         }
      }

      this.decorateStall(world, random);
      LOTREntityNPC trader = this.createTrader(world, random);
      this.spawnNPCAndSetHome(trader, world, 0, 1, 0, 1);
      return true;
   }

   protected abstract void decorateStall(World var1, Random var2);

   protected abstract LOTREntityNPC createTrader(World var1, Random var2);

   public static class Farmer extends LOTRWorldGenBreeMarketStall {
      public Farmer(boolean flag) {
         super(flag);
      }

      protected void setupRandomBlocks(Random random) {
         super.setupRandomBlocks(random);
         this.wool1Block = Blocks.field_150325_L;
         this.wool1Meta = 13;
      }

      protected void decorateStall(World world, Random random) {
         this.placePlate_item(world, random, -1, 2, -2, LOTRMod.plateBlock, this.getRandomFarmerItem(random), true);
         this.placePlate_item(world, random, 0, 2, -2, LOTRMod.woodPlateBlock, this.getRandomFarmerItem(random), true);
         this.placePlate_item(world, random, -2, 2, -1, LOTRMod.woodPlateBlock, this.getRandomFarmerItem(random), true);
         this.placePlate_item(world, random, -2, 2, 1, LOTRMod.ceramicPlateBlock, this.getRandomFarmerItem(random), true);
         this.placePlate_item(world, random, 2, 2, 0, LOTRMod.ceramicPlateBlock, this.getRandomFarmerItem(random), true);
         this.setBlockAndMetadata(world, -1, 1, -1, Blocks.field_150423_aK, 3);
         this.setGrassToDirt(world, -1, 0, -1);
      }

      protected ItemStack getRandomFarmerItem(Random random) {
         ItemStack[] foods = new ItemStack[]{new ItemStack(Items.field_151172_bF), new ItemStack(Items.field_151174_bG), new ItemStack(LOTRMod.lettuce), new ItemStack(LOTRMod.turnip), new ItemStack(LOTRMod.leek), new ItemStack(Items.field_151034_e), new ItemStack(LOTRMod.appleGreen), new ItemStack(LOTRMod.pear), new ItemStack(LOTRMod.plum)};
         ItemStack ret = foods[random.nextInt(foods.length)].func_77946_l();
         ret.field_77994_a = 1 + random.nextInt(3);
         return ret;
      }

      protected LOTREntityNPC createTrader(World world, Random random) {
         return new LOTREntityBreeFarmer(world);
      }
   }

   public static class Florist extends LOTRWorldGenBreeMarketStall {
      public Florist(boolean flag) {
         super(flag);
      }

      protected void setupRandomBlocks(Random random) {
         super.setupRandomBlocks(random);
         this.wool1Block = Blocks.field_150325_L;
         this.wool1Meta = 10;
      }

      protected void decorateStall(World world, Random random) {
         this.placeRandomFlowerPot(world, random, -1, 2, -2);
         this.placeRandomFlowerPot(world, random, 1, 2, -2);
         this.placeRandomFlowerPot(world, random, -2, 2, 0);
         this.placeRandomFlowerPot(world, random, 2, 2, 1);
         this.placeRandomFlowerPot(world, random, 0, 2, 2);
      }

      protected LOTREntityNPC createTrader(World world, Random random) {
         return (LOTREntityNPC)(random.nextBoolean() ? new LOTREntityBreeHobbitFlorist(world) : new LOTREntityBreeFlorist(world));
      }
   }

   public static class Smith extends LOTRWorldGenBreeMarketStall {
      public Smith(boolean flag) {
         super(flag);
      }

      protected void setupRandomBlocks(Random random) {
         super.setupRandomBlocks(random);
         this.wool1Block = Blocks.field_150325_L;
         this.wool1Meta = 7;
      }

      protected void decorateStall(World world, Random random) {
         this.placeWeaponRack(world, 1, 2, -2, 7, new ItemStack(LOTRMod.ironCrossbow));
         this.placeWeaponRack(world, -2, 2, 1, 3, new ItemStack(LOTRMod.battleaxeIron));
         this.placeWeaponRack(world, 2, 2, 1, 1, new ItemStack(Items.field_151040_l));
         LOTREntityBreeGuard armorGuard = new LOTREntityBreeGuard(world);
         armorGuard.func_110161_a((IEntityLivingData)null);
         this.placeArmorStand(world, 0, 1, 1, 0, new ItemStack[]{armorGuard.func_71124_b(4), armorGuard.func_71124_b(3), null, null});
      }

      protected LOTREntityNPC createTrader(World world, Random random) {
         return new LOTREntityBreeBlacksmith(world);
      }
   }

   public static class Lumber extends LOTRWorldGenBreeMarketStall {
      public Lumber(boolean flag) {
         super(flag);
      }

      protected void setupRandomBlocks(Random random) {
         super.setupRandomBlocks(random);
         this.wool1Block = Blocks.field_150325_L;
         this.wool1Meta = 12;
      }

      protected void decorateStall(World world, Random random) {
         this.setBlockAndMetadata(world, 0, 1, 1, Blocks.field_150364_r, 0);
         this.setGrassToDirt(world, 0, 0, 1);
         this.setBlockAndMetadata(world, -1, 1, -1, LOTRMod.wood5, 4);
         this.setGrassToDirt(world, -1, 0, -1);
         this.placeWeaponRack(world, 1, 2, -2, 7, new ItemStack(Items.field_151036_c));
      }

      protected LOTREntityNPC createTrader(World world, Random random) {
         return new LOTREntityBreeLumberman(world);
      }
   }

   public static class Mason extends LOTRWorldGenBreeMarketStall {
      public Mason(boolean flag) {
         super(flag);
      }

      protected void setupRandomBlocks(Random random) {
         super.setupRandomBlocks(random);
         this.wool1Block = Blocks.field_150325_L;
         this.wool1Meta = 8;
      }

      protected void decorateStall(World world, Random random) {
         this.setBlockAndMetadata(world, 0, 1, 1, this.brickBlock, this.brickMeta);
         this.setBlockAndMetadata(world, 0, 2, 1, this.brickBlock, this.brickMeta);
         this.setGrassToDirt(world, 0, 0, 1);
         this.setBlockAndMetadata(world, -1, 1, -1, Blocks.field_150347_e, 0);
         this.setGrassToDirt(world, -1, 0, -1);
         this.placeWeaponRack(world, 1, 2, -2, 7, new ItemStack(Items.field_151035_b));
         this.placeWeaponRack(world, -2, 2, 1, 6, new ItemStack(LOTRMod.pickaxeBronze));
      }

      protected LOTREntityNPC createTrader(World world, Random random) {
         return new LOTREntityBreeMason(world);
      }
   }

   public static class Brewer extends LOTRWorldGenBreeMarketStall {
      public Brewer(boolean flag) {
         super(flag);
      }

      protected void setupRandomBlocks(Random random) {
         super.setupRandomBlocks(random);
         this.wool1Block = Blocks.field_150325_L;
         this.wool1Meta = 1;
      }

      protected void decorateStall(World world, Random random) {
         this.placeMug(world, random, -1, 2, -2, 0, LOTRFoods.BREE_DRINK);
         this.placeMug(world, random, 1, 2, -2, 0, LOTRFoods.BREE_DRINK);
         this.placeMug(world, random, 1, 2, 2, 2, LOTRFoods.BREE_DRINK);
         this.setBlockAndMetadata(world, -1, 1, -1, LOTRMod.barrel, 3);
         this.setBlockAndMetadata(world, -2, 2, 1, LOTRMod.barrel, 2);
         this.setBlockAndMetadata(world, 2, 2, 1, LOTRMod.barrel, 5);
      }

      protected LOTREntityNPC createTrader(World world, Random random) {
         return (LOTREntityNPC)(random.nextBoolean() ? new LOTREntityBreeHobbitBrewer(world) : new LOTREntityBreeBrewer(world));
      }
   }

   public static class Butcher extends LOTRWorldGenBreeMarketStall {
      public Butcher(boolean flag) {
         super(flag);
      }

      protected void setupRandomBlocks(Random random) {
         super.setupRandomBlocks(random);
         this.wool1Block = Blocks.field_150325_L;
         this.wool1Meta = 14;
      }

      protected void decorateStall(World world, Random random) {
         this.placePlate_item(world, random, -1, 2, -2, LOTRMod.plateBlock, this.getRandomButcherItem(random), true);
         this.placePlate_item(world, random, 1, 2, -2, LOTRMod.ceramicPlateBlock, this.getRandomButcherItem(random), true);
         this.placePlate_item(world, random, -2, 2, 0, LOTRMod.woodPlateBlock, this.getRandomButcherItem(random), true);
         this.placePlate_item(world, random, 2, 2, 1, LOTRMod.plateBlock, this.getRandomButcherItem(random), true);
         this.placePlate_item(world, random, 0, 2, 2, LOTRMod.ceramicPlateBlock, this.getRandomButcherItem(random), true);
      }

      protected ItemStack getRandomButcherItem(Random random) {
         ItemStack[] foods = new ItemStack[]{new ItemStack(Items.field_151082_bd), new ItemStack(Items.field_151147_al), new ItemStack(LOTRMod.gammon), new ItemStack(Items.field_151076_bf), new ItemStack(LOTRMod.muttonRaw), new ItemStack(LOTRMod.rabbitRaw), new ItemStack(LOTRMod.deerRaw)};
         ItemStack ret = foods[random.nextInt(foods.length)].func_77946_l();
         ret.field_77994_a = 1 + random.nextInt(3);
         return ret;
      }

      protected LOTREntityNPC createTrader(World world, Random random) {
         return (LOTREntityNPC)(random.nextBoolean() ? new LOTREntityBreeHobbitButcher(world) : new LOTREntityBreeButcher(world));
      }
   }

   public static class Baker extends LOTRWorldGenBreeMarketStall {
      public Baker(boolean flag) {
         super(flag);
      }

      protected void setupRandomBlocks(Random random) {
         super.setupRandomBlocks(random);
         this.wool1Block = Blocks.field_150325_L;
         this.wool1Meta = 4;
      }

      protected void decorateStall(World world, Random random) {
         this.placePlate_item(world, random, -1, 2, -2, LOTRMod.woodPlateBlock, this.getRandomBakeryItem(random), true);
         this.placePlate_item(world, random, 1, 2, 2, LOTRMod.ceramicPlateBlock, this.getRandomBakeryItem(random), true);
         this.placePlate_item(world, random, -2, 2, 1, LOTRMod.plateBlock, this.getRandomBakeryItem(random), true);
         this.setBlockAndMetadata(world, 1, 2, -2, getRandomPieBlock(random), 0);
         this.setBlockAndMetadata(world, -2, 2, -1, getRandomPieBlock(random), 0);
         this.setBlockAndMetadata(world, 2, 2, 1, getRandomPieBlock(random), 0);
      }

      protected ItemStack getRandomBakeryItem(Random random) {
         ItemStack[] foods = new ItemStack[]{new ItemStack(Items.field_151025_P), new ItemStack(LOTRMod.cornBread), new ItemStack(LOTRMod.hobbitPancake), new ItemStack(LOTRMod.hobbitPancakeMapleSyrup)};
         ItemStack ret = foods[random.nextInt(foods.length)].func_77946_l();
         ret.field_77994_a = 1 + random.nextInt(3);
         return ret;
      }

      protected LOTREntityNPC createTrader(World world, Random random) {
         return (LOTREntityNPC)(random.nextBoolean() ? new LOTREntityBreeHobbitBaker(world) : new LOTREntityBreeBaker(world));
      }
   }
}
