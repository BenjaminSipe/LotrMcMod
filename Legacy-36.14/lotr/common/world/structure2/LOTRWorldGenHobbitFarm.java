package lotr.common.world.structure2;

import java.util.Random;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityHobbit;
import lotr.common.entity.npc.LOTREntityHobbitFarmer;
import lotr.common.entity.npc.LOTREntityHobbitFarmhand;
import lotr.common.world.biome.LOTRBiome;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase.SpawnListEntry;

public class LOTRWorldGenHobbitFarm extends LOTRWorldGenStructureBase2 {
   private Block wood1Block;
   private int wood1Meta;
   private Block wood1SlabBlock;
   private int wood1SlabMeta;
   private Block wood1Stair;
   private Block beam1Block;
   private int beam1Meta;
   private Block wood2Block;
   private int wood2Meta;
   private Block cropBlock;
   private int cropMeta;
   private Item seedItem;

   public LOTRWorldGenHobbitFarm(boolean flag) {
      super(flag);
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, 6);
      int randomWood = random.nextInt(4);
      switch(randomWood) {
      case 0:
         this.wood1Block = Blocks.field_150344_f;
         this.wood1Meta = 0;
         this.wood1SlabBlock = Blocks.field_150376_bx;
         this.wood1SlabMeta = 0;
         this.wood1Stair = Blocks.field_150476_ad;
         this.beam1Block = LOTRMod.woodBeamV1;
         this.beam1Meta = 0;
         break;
      case 1:
         this.wood1Block = Blocks.field_150344_f;
         this.wood1Meta = 2;
         this.wood1SlabBlock = Blocks.field_150376_bx;
         this.wood1SlabMeta = 2;
         this.wood1Stair = Blocks.field_150487_bG;
         this.beam1Block = LOTRMod.woodBeamV1;
         this.beam1Meta = 2;
         break;
      case 2:
         this.wood1Block = LOTRMod.planks;
         this.wood1Meta = 0;
         this.wood1SlabBlock = LOTRMod.woodSlabSingle;
         this.wood1SlabMeta = 0;
         this.wood1Stair = LOTRMod.stairsShirePine;
         this.beam1Block = LOTRMod.woodBeam1;
         this.beam1Meta = 0;
         break;
      case 3:
         this.wood1Block = LOTRMod.planks;
         this.wood1Meta = 4;
         this.wood1SlabBlock = LOTRMod.woodSlabSingle;
         this.wood1SlabMeta = 4;
         this.wood1Stair = LOTRMod.stairsApple;
         this.beam1Block = LOTRMod.woodBeamFruit;
         this.beam1Meta = 0;
      }

      int randomWood2 = random.nextInt(2);
      switch(randomWood2) {
      case 0:
         this.wood2Block = Blocks.field_150344_f;
         this.wood2Meta = 1;
         break;
      case 1:
         this.wood2Block = LOTRMod.planks;
         this.wood2Meta = 6;
      }

      int randomCrop = random.nextInt(8);
      switch(randomCrop) {
      case 0:
         this.cropBlock = Blocks.field_150464_aj;
         this.cropMeta = 7;
         this.seedItem = Items.field_151014_N;
         break;
      case 1:
         this.cropBlock = Blocks.field_150459_bM;
         this.cropMeta = 7;
         this.seedItem = Items.field_151172_bF;
         break;
      case 2:
         this.cropBlock = Blocks.field_150469_bN;
         this.cropMeta = 7;
         this.seedItem = Items.field_151174_bG;
         break;
      case 3:
         this.cropBlock = LOTRMod.lettuceCrop;
         this.cropMeta = 7;
         this.seedItem = LOTRMod.lettuce;
         break;
      case 4:
         this.cropBlock = LOTRMod.pipeweedCrop;
         this.cropMeta = 7;
         this.seedItem = LOTRMod.pipeweedSeeds;
         break;
      case 5:
         this.cropBlock = LOTRMod.cornStalk;
         this.cropMeta = 0;
         this.seedItem = Item.func_150898_a(LOTRMod.cornStalk);
         break;
      case 6:
         this.cropBlock = LOTRMod.leekCrop;
         this.cropMeta = 7;
         this.seedItem = LOTRMod.leek;
         break;
      case 7:
         this.cropBlock = LOTRMod.turnipCrop;
         this.cropMeta = 7;
         this.seedItem = LOTRMod.turnip;
      }

      int carpet;
      int k1;
      int i1;
      int animals;
      int l;
      if (this.restrictions) {
         carpet = 1;
         k1 = 1;

         for(i1 = -5; i1 <= 10; ++i1) {
            for(animals = -7; animals <= 8; ++animals) {
               l = this.getTopBlock(world, i1, animals);
               Block block = this.getBlock(world, i1, l - 1, animals);
               if (block != Blocks.field_150349_c && block != Blocks.field_150346_d && block != Blocks.field_150348_b) {
                  return false;
               }

               if (l > k1) {
                  k1 = l;
               }

               if (l < carpet) {
                  carpet = l;
               }
            }
         }

         if (Math.abs(k1 - carpet) > 6) {
            return false;
         }
      }

      for(carpet = -5; carpet <= 10; ++carpet) {
         for(k1 = -7; k1 <= 8; ++k1) {
            for(i1 = 1; i1 <= 10; ++i1) {
               this.setAir(world, carpet, i1, k1);
            }

            this.setBlockAndMetadata(world, carpet, 0, k1, Blocks.field_150349_c, 0);
            this.setGrassToDirt(world, carpet, -1, k1);

            for(i1 = -1; !this.isOpaque(world, carpet, i1, k1) && this.getY(i1) >= 0; --i1) {
               this.setBlockAndMetadata(world, carpet, i1, k1, Blocks.field_150346_d, 0);
               this.setGrassToDirt(world, carpet, i1 - 1, k1);
            }
         }
      }

      for(carpet = -5; carpet <= 6; ++carpet) {
         for(k1 = -5; k1 <= 4; ++k1) {
            if (carpet == -5 || carpet == 6 || k1 == -5 || k1 == 4) {
               for(i1 = 1; i1 <= 5; ++i1) {
                  this.setBlockAndMetadata(world, k1, i1, carpet, this.wood2Block, this.wood2Meta);
                  this.setGrassToDirt(world, k1, i1 - 1, carpet);
               }
            }
         }
      }

      int k1;
      int j1;
      for(carpet = 0; carpet <= 4; ++carpet) {
         k1 = 5 + carpet;

         for(i1 = -5 + carpet; i1 <= 4 - carpet; ++i1) {
            int[] var19 = new int[]{-5, 6};
            l = var19.length;

            for(j1 = 0; j1 < l; ++j1) {
               k1 = var19[j1];
               this.setBlockAndMetadata(world, i1, k1, k1, this.wood2Block, this.wood2Meta);
            }
         }

         for(i1 = -6; i1 <= 7; ++i1) {
            this.setBlockAndMetadata(world, -6 + carpet, k1, i1, LOTRMod.stairsThatch, 1);
            this.setBlockAndMetadata(world, 5 - carpet, k1, i1, LOTRMod.stairsThatch, 0);
         }
      }

      for(carpet = -4; carpet <= 5; ++carpet) {
         for(k1 = -4; k1 <= 3; ++k1) {
            this.setBlockAndMetadata(world, k1, 5, carpet, this.wood1Block, this.wood1Meta);
         }
      }

      int[] var20;
      for(carpet = 1; carpet <= 5; ++carpet) {
         var20 = new int[]{-5, 6};
         i1 = var20.length;

         for(animals = 0; animals < i1; ++animals) {
            l = var20[animals];
            this.setBlockAndMetadata(world, -5, carpet, l, this.beam1Block, this.beam1Meta);
            this.setBlockAndMetadata(world, -2, carpet, l, this.wood1Block, this.wood1Meta);
            this.setBlockAndMetadata(world, 1, carpet, l, this.wood1Block, this.wood1Meta);
            this.setBlockAndMetadata(world, 4, carpet, l, this.beam1Block, this.beam1Meta);
         }

         var20 = new int[]{-5, 4};
         i1 = var20.length;

         for(animals = 0; animals < i1; ++animals) {
            l = var20[animals];
            this.setBlockAndMetadata(world, l, carpet, -1, this.beam1Block, this.beam1Meta);
            this.setBlockAndMetadata(world, l, carpet, 2, this.beam1Block, this.beam1Meta);
         }
      }

      for(carpet = 0; carpet <= 1; ++carpet) {
         var20 = new int[]{-5, 4};
         i1 = var20.length;

         for(animals = 0; animals < i1; ++animals) {
            l = var20[animals];
            this.setBlockAndMetadata(world, l, 2, carpet, this.wood1Block, this.wood1Meta);
            this.setBlockAndMetadata(world, l, 4, carpet, this.wood1Block, this.wood1Meta);
         }
      }

      int[] var21 = new int[]{-5, 6};
      k1 = var21.length;

      for(i1 = 0; i1 < k1; ++i1) {
         animals = var21[i1];

         for(l = -1; l <= 0; ++l) {
            this.setBlockAndMetadata(world, l, 3, animals, this.wood1Block, this.wood1Meta);
            this.setBlockAndMetadata(world, l, 5, animals, this.wood1Block, this.wood1Meta);
            this.setBlockAndMetadata(world, l, 7, animals, LOTRMod.glassPane, 0);
         }

         for(l = -2; l <= 1; ++l) {
            this.setBlockAndMetadata(world, l, 0, animals, Blocks.field_150349_c, 0);

            for(j1 = 1; j1 <= 3; ++j1) {
               this.setBlockAndMetadata(world, l, j1, animals, LOTRMod.gateWooden, 2);
            }
         }
      }

      for(carpet = -1; carpet <= 0; ++carpet) {
         for(k1 = -6; k1 <= 7; ++k1) {
            this.setBlockAndMetadata(world, carpet, 10, k1, LOTRMod.slabSingleThatch, 0);
         }
      }

      for(carpet = -3; carpet <= 2; ++carpet) {
         this.setBlockAndMetadata(world, carpet, 5, -6, this.wood1Stair, 6);
         this.setBlockAndMetadata(world, carpet, 5, 7, this.wood1Stair, 7);
      }

      this.setBlockAndMetadata(world, -5, 5, -6, this.wood1Block, this.wood1Meta);
      this.setBlockAndMetadata(world, -4, 5, -6, this.wood1Stair, 4);
      this.setBlockAndMetadata(world, 3, 5, -6, this.wood1Stair, 5);
      this.setBlockAndMetadata(world, 4, 5, -6, this.wood1Block, this.wood1Meta);
      this.setBlockAndMetadata(world, -5, 5, 7, this.wood1Block, this.wood1Meta);
      this.setBlockAndMetadata(world, -4, 5, 7, this.wood1Stair, 4);
      this.setBlockAndMetadata(world, 3, 5, 7, this.wood1Stair, 5);
      this.setBlockAndMetadata(world, 4, 5, 7, this.wood1Block, this.wood1Meta);
      var21 = new int[]{-4, 3};
      k1 = var21.length;

      for(i1 = 0; i1 < k1; ++i1) {
         animals = var21[i1];
         int[] var23 = new int[]{-1, 2};
         j1 = var23.length;

         for(k1 = 0; k1 < j1; ++k1) {
            int k1 = var23[k1];
            this.setBlockAndMetadata(world, animals, 1, k1, Blocks.field_150462_ai, 0);
            this.setBlockAndMetadata(world, animals, 2, k1, Blocks.field_150422_aJ, 0);
            this.setBlockAndMetadata(world, animals, 3, k1, Blocks.field_150422_aJ, 0);
            this.setBlockAndMetadata(world, animals, 4, k1, Blocks.field_150478_aa, 5);
         }
      }

      this.setBlockAndMetadata(world, -4, 1, -4, Blocks.field_150407_cf, 0);
      this.setBlockAndMetadata(world, -4, 2, -4, Blocks.field_150407_cf, 0);
      this.setBlockAndMetadata(world, -3, 1, -4, Blocks.field_150407_cf, 0);
      this.setBlockAndMetadata(world, -4, 1, -3, Blocks.field_150407_cf, 0);
      this.setBlockAndMetadata(world, -4, 1, 5, Blocks.field_150407_cf, 0);
      this.setBlockAndMetadata(world, -4, 2, 5, Blocks.field_150407_cf, 0);
      this.setBlockAndMetadata(world, -3, 1, 5, Blocks.field_150407_cf, 0);
      this.setBlockAndMetadata(world, -4, 1, 4, Blocks.field_150407_cf, 0);
      this.setBlockAndMetadata(world, 3, 1, 5, Blocks.field_150407_cf, 0);
      this.setBlockAndMetadata(world, 3, 2, 5, Blocks.field_150407_cf, 0);
      this.setBlockAndMetadata(world, 2, 1, 5, Blocks.field_150407_cf, 0);
      this.setBlockAndMetadata(world, 3, 1, 4, Blocks.field_150407_cf, 0);

      for(carpet = 1; carpet <= 4; ++carpet) {
         this.setBlockAndMetadata(world, 2, carpet, -3, Blocks.field_150422_aJ, 0);
      }

      this.setBlockAndMetadata(world, 1, 1, -4, this.wood1Stair, 1);
      this.setBlockAndMetadata(world, 2, 1, -4, this.wood1Block, this.wood1Meta);
      this.setBlockAndMetadata(world, 2, 2, -4, this.wood1Stair, 1);
      this.setBlockAndMetadata(world, 3, 1, -4, this.wood1Block, this.wood1Meta);
      this.setBlockAndMetadata(world, 3, 2, -4, this.wood1Block, this.wood1Meta);
      this.setBlockAndMetadata(world, 3, 2, -3, this.wood1Stair, 7);
      this.setBlockAndMetadata(world, 3, 3, -3, this.wood1Stair, 2);
      this.setBlockAndMetadata(world, 3, 3, -2, this.wood1Block, this.wood1Meta);
      this.setBlockAndMetadata(world, 2, 3, -2, this.wood1Stair, 5);
      this.setBlockAndMetadata(world, 2, 4, -2, this.wood1Stair, 0);
      this.setBlockAndMetadata(world, 1, 4, -2, this.wood1Stair, 5);
      this.setBlockAndMetadata(world, 1, 5, -2, this.wood1Stair, 0);
      this.setAir(world, 3, 5, -4);
      this.setAir(world, 3, 5, -3);
      this.setAir(world, 3, 5, -2);
      this.setAir(world, 2, 5, -2);

      for(carpet = 0; carpet <= 2; ++carpet) {
         this.setBlockAndMetadata(world, carpet, 6, -3, Blocks.field_150422_aJ, 0);
         this.setBlockAndMetadata(world, carpet, 6, -1, Blocks.field_150422_aJ, 0);
      }

      this.setBlockAndMetadata(world, 2, 6, -4, Blocks.field_150422_aJ, 0);
      this.setBlockAndMetadata(world, 0, 6, -2, Blocks.field_150396_be, 3);

      for(carpet = -4; carpet <= 5; ++carpet) {
         this.setBlockAndMetadata(world, -4, 6, carpet, this.wood2Block, this.wood2Meta);
      }

      for(carpet = -1; carpet <= 5; ++carpet) {
         this.setBlockAndMetadata(world, 3, 6, carpet, this.wood2Block, this.wood2Meta);
      }

      this.setBlockAndMetadata(world, -2, 7, -4, Blocks.field_150478_aa, 3);
      this.setBlockAndMetadata(world, 1, 7, -4, Blocks.field_150478_aa, 3);
      this.setBlockAndMetadata(world, -2, 7, 5, Blocks.field_150478_aa, 4);
      this.setBlockAndMetadata(world, 1, 7, 5, Blocks.field_150478_aa, 4);
      carpet = random.nextInt(16);
      this.setBlockAndMetadata(world, -1, 6, 2, Blocks.field_150404_cg, carpet);
      this.setBlockAndMetadata(world, 0, 6, 2, Blocks.field_150404_cg, carpet);
      this.setBlockAndMetadata(world, -1, 6, 3, Blocks.field_150404_cg, carpet);
      this.setBlockAndMetadata(world, 0, 6, 3, Blocks.field_150404_cg, carpet);

      for(k1 = 4; k1 <= 5; ++k1) {
         for(i1 = 6; i1 <= 7; ++i1) {
            this.setBlockAndMetadata(world, -3, i1, k1, Blocks.field_150342_X, 0);
            this.setBlockAndMetadata(world, 2, i1, k1, Blocks.field_150342_X, 0);
         }
      }

      this.setBlockAndMetadata(world, -3, 6, 0, this.wood2Block, this.wood2Meta);
      this.setBlockAndMetadata(world, -3, 7, 0, LOTRWorldGenHobbitStructure.getRandomCakeBlock(random), 0);
      this.setBlockAndMetadata(world, -3, 6, 1, Blocks.field_150383_bp, 3);
      this.setBlockAndMetadata(world, -3, 6, 2, LOTRMod.hobbitOven, 4);
      this.setBlockAndMetadata(world, -3, 6, 3, Blocks.field_150462_ai, 0);
      this.placeChest(world, random, 2, 6, 1, 5, LOTRChestContents.HOBBIT_HOLE_LARDER);
      this.setBlockAndMetadata(world, 2, 6, 2, Blocks.field_150324_C, 0);
      this.setBlockAndMetadata(world, 2, 6, 3, Blocks.field_150324_C, 8);

      for(k1 = 5; k1 <= 10; ++k1) {
         this.setBlockAndMetadata(world, k1, 1, -5, Blocks.field_150422_aJ, 0);
         this.setBlockAndMetadata(world, k1, 1, 6, Blocks.field_150422_aJ, 0);
      }

      for(k1 = -4; k1 <= 5; ++k1) {
         this.setBlockAndMetadata(world, 10, 1, k1, Blocks.field_150422_aJ, 0);
      }

      this.setBlockAndMetadata(world, 7, 1, -5, Blocks.field_150396_be, 0);
      this.setBlockAndMetadata(world, 5, 2, -5, Blocks.field_150478_aa, 5);
      this.setBlockAndMetadata(world, 10, 2, -5, Blocks.field_150478_aa, 5);
      this.setBlockAndMetadata(world, 10, 2, -1, Blocks.field_150478_aa, 5);
      this.setBlockAndMetadata(world, 10, 2, 2, Blocks.field_150478_aa, 5);
      this.setBlockAndMetadata(world, 5, 2, 6, Blocks.field_150478_aa, 5);
      this.setBlockAndMetadata(world, 10, 2, 6, Blocks.field_150478_aa, 5);

      for(k1 = 5; k1 <= 9; ++k1) {
         this.setBlockAndMetadata(world, k1, 0, -4, Blocks.field_150351_n, 0);
         this.setBlockAndMetadata(world, k1, 0, 5, Blocks.field_150351_n, 0);
      }

      for(k1 = -3; k1 <= 4; ++k1) {
         this.setBlockAndMetadata(world, 4, 0, k1, Blocks.field_150417_aV, 0);
         this.setBlockAndMetadata(world, 5, 0, k1, Blocks.field_150355_j, 0);
         this.setBlockAndMetadata(world, 5, 1, k1, Blocks.field_150333_U, 5);
         this.setBlockAndMetadata(world, 9, 0, k1, Blocks.field_150351_n, 0);

         for(i1 = 6; i1 <= 8; ++i1) {
            this.setBlockAndMetadata(world, i1, 0, k1, Blocks.field_150458_ak, 7);
            this.setBlockAndMetadata(world, i1, 1, k1, this.cropBlock, this.cropMeta);
         }
      }

      this.setBlockAndMetadata(world, 10, 2, 0, Blocks.field_150422_aJ, 0);
      this.setBlockAndMetadata(world, 10, 3, 0, Blocks.field_150407_cf, 0);
      this.setBlockAndMetadata(world, 10, 4, 0, Blocks.field_150423_aK, 1);
      LOTREntityHobbit farmer = new LOTREntityHobbitFarmer(world);
      this.spawnNPCAndSetHome(farmer, world, 0, 6, 0, 16);
      i1 = 1 + random.nextInt(3);

      for(animals = 0; animals < i1; ++animals) {
         LOTREntityHobbitFarmhand farmhand = new LOTREntityHobbitFarmhand(world);
         farmhand.seedsItem = this.seedItem;
         this.spawnNPCAndSetHome(farmhand, world, 7, 1, 0, 8);
      }

      animals = 3 + random.nextInt(6);

      for(l = 0; l < animals; ++l) {
         Class animalClass = ((SpawnListEntry)WeightedRandom.func_76271_a(world.field_73012_v, LOTRBiome.shire.func_76747_a(EnumCreatureType.creature))).field_76300_b;
         EntityCreature animal = null;

         try {
            animal = (EntityCreature)animalClass.getConstructor(World.class).newInstance(world);
         } catch (Exception var18) {
            var18.printStackTrace();
            continue;
         }

         this.spawnNPCAndSetHome(animal, world, 0, 1, 0, 8);
      }

      return true;
   }
}
