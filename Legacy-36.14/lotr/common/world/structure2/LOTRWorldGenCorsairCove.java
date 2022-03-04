package lotr.common.world.structure2;

import java.util.Random;
import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.npc.LOTREntityCorsair;
import lotr.common.entity.npc.LOTREntityCorsairCaptain;
import lotr.common.entity.npc.LOTREntityCorsairSlaver;
import lotr.common.entity.npc.LOTREntityHaradSlave;
import lotr.common.item.LOTRItemBanner;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityCreature;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRWorldGenCorsairCove extends LOTRWorldGenCorsairStructure {
   public LOTRWorldGenCorsairCove(boolean flag) {
      super(flag);
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, 0);
      this.setupRandomBlocks(random);
      int i1;
      int k1;
      int j1;
      int l;
      if (this.restrictions) {
         i1 = 0;
         k1 = 0;

         for(j1 = -15; j1 <= 9; ++j1) {
            for(int k1 = -1; k1 <= 12; ++k1) {
               l = this.getTopBlock(world, j1, k1) - 1;
               Block block = this.getBlock(world, j1, l, k1);
               if (!this.isSurface(world, j1, l, k1) && block != Blocks.field_150348_b && block != Blocks.field_150322_A) {
                  return false;
               }

               if (l < i1) {
                  i1 = l;
               }

               if (l > k1) {
                  k1 = l;
               }

               if (k1 - i1 > 8) {
                  return false;
               }
            }
         }
      }

      for(i1 = -14; i1 <= 4; ++i1) {
         for(k1 = 0; k1 <= 7; ++k1) {
            for(j1 = 1; j1 <= 9; ++j1) {
               this.setAir(world, i1, j1, k1);
            }
         }
      }

      this.loadStrScan("corsair_cove");
      this.addBlockMetaAliasOption("STONE", 10, Blocks.field_150348_b, 0);
      this.addBlockMetaAliasOption("STONE", 3, Blocks.field_150322_A, 0);
      this.addBlockMetaAliasOption("STONE", 3, Blocks.field_150346_d, 1);
      this.associateBlockMetaAlias("BRICK", this.brickBlock, this.brickMeta);
      this.associateBlockMetaAlias("BRICK_SLAB", this.brickSlabBlock, this.brickSlabMeta);
      this.associateBlockMetaAlias("BRICK_SLAB_INV", this.brickSlabBlock, this.brickSlabMeta | 8);
      this.associateBlockAlias("BRICK_STAIR", this.brickStairBlock);
      this.associateBlockMetaAlias("BRICK_WALL", this.brickWallBlock, this.brickWallMeta);
      this.associateBlockMetaAlias("PILLAR", this.pillarBlock, this.pillarMeta);
      this.associateBlockMetaAlias("PILLAR_SLAB", this.pillarSlabBlock, this.pillarSlabMeta);
      this.associateBlockMetaAlias("PLANK", this.plankBlock, this.plankMeta);
      this.associateBlockMetaAlias("PLANK_SLAB", this.plankSlabBlock, this.plankSlabMeta);
      this.associateBlockMetaAlias("PLANK_SLAB_INV", this.plankSlabBlock, this.plankSlabMeta | 8);
      this.associateBlockAlias("PLANK_STAIR", this.plankStairBlock);
      this.associateBlockMetaAlias("FENCE", this.fenceBlock, this.fenceMeta);
      this.associateBlockAlias("FENCE_GATE", this.fenceGateBlock);
      this.generateStrScan(world, random, 0, 0, 0);
      this.placeSkull(world, random, -3, 7, 3);
      this.placeBanner(world, 1, 5, 3, LOTRItemBanner.BannerType.UMBAR, 2);
      this.placeChest(world, random, -14, 4, 4, Blocks.field_150486_ae, 4, LOTRChestContents.CORSAIR, MathHelper.func_76136_a(random, 6, 12));
      this.placeBarrel(world, random, -14, 5, 6, 4, LOTRFoods.CORSAIR_DRINK);
      this.placeWallBanner(world, -12, 8, 3, LOTRItemBanner.BannerType.UMBAR, 2);
      this.placeWallBanner(world, -12, 8, 7, LOTRItemBanner.BannerType.UMBAR, 0);
      this.placeWallBanner(world, -14, 8, 5, LOTRItemBanner.BannerType.UMBAR, 3);
      this.placeWallBanner(world, -10, 8, 5, LOTRItemBanner.BannerType.UMBAR, 1);
      this.placeWeaponRack(world, -7, 5, 8, 6, this.getRandomCorsairWeapon(random));
      this.placeWeaponRack(world, -6, 5, 8, 6, this.getRandomCorsairWeapon(random));
      this.placeWeaponRack(world, -5, 5, 8, 6, this.getRandomCorsairWeapon(random));
      if (random.nextInt(3) == 0) {
         this.placeTreasure(world, random, -14, 4, 2);
         this.placeTreasure(world, random, -14, 4, 1);
         this.placeTreasure(world, random, -13, 4, 1);
         this.placeTreasure(world, random, -12, 4, 1);
         this.placeTreasure(world, random, -12, 4, 0);
         this.placeTreasure(world, random, -11, 4, 0);
      }

      if (random.nextInt(3) == 0) {
         this.placeTreasure(world, random, -4, 4, 0);
         this.placeTreasure(world, random, -3, 5, 0);
         this.placeTreasure(world, random, -3, 4, 1);
         this.placeTreasure(world, random, -3, 4, 2);
         this.placeTreasure(world, random, -2, 4, 1);
      }

      for(i1 = -14; i1 <= -5; ++i1) {
         for(k1 = 0; k1 <= 8; ++k1) {
            int j1 = 4;
            if (this.isAir(world, i1, j1, k1) && this.isOpaque(world, i1, j1 - 1, k1) && random.nextInt(20) == 0) {
               this.placeFoodOrDrink(world, random, i1, j1, k1);
            }
         }
      }

      i1 = 2 + random.nextInt(2);

      for(k1 = 0; k1 < i1; ++k1) {
         LOTREntityCorsair corsair = new LOTREntityCorsair(world);
         this.spawnNPCAndSetHome(corsair, world, -9, 4, 4, 16);
      }

      LOTREntityCorsair captain = random.nextBoolean() ? new LOTREntityCorsairCaptain(world) : new LOTREntityCorsairSlaver(world);
      this.spawnNPCAndSetHome((EntityCreature)captain, world, -9, 4, 4, 4);
      LOTREntityNPCRespawner respawner = new LOTREntityNPCRespawner(world);
      respawner.setSpawnClass(LOTREntityCorsair.class);
      respawner.setCheckRanges(24, -16, 12, 8);
      respawner.setSpawnRanges(3, -2, 2, 16);
      this.placeNPCRespawner(respawner, world, -9, 4, 4);
      LOTREntityHaradSlave slave = new LOTREntityHaradSlave(world);
      this.spawnNPCAndSetHome(slave, world, -7, 7, 6, 8);

      for(l = 0; l < 16; ++l) {
         LOTRTreeType tree = LOTRTreeType.PALM;
         WorldGenerator treeGen = tree.create(this.notifyChanges, random);
         if (treeGen != null) {
            int i1 = 2;
            int j1 = 6;
            int k1 = 7;
            if (treeGen.func_76484_a(world, random, this.getX(i1, k1), this.getY(j1), this.getZ(i1, k1))) {
               break;
            }
         }
      }

      return true;
   }

   private void placeFoodOrDrink(World world, Random random, int i, int j, int k) {
      if (random.nextInt(3) != 0) {
         this.placeMug(world, random, i, j, k, random.nextInt(4), LOTRFoods.CORSAIR_DRINK);
      } else {
         Block plateBlock = LOTRMod.woodPlateBlock;
         if (random.nextBoolean()) {
            this.setBlockAndMetadata(world, i, j, k, plateBlock, 0);
         } else {
            this.placePlateWithCertainty(world, random, i, j, k, plateBlock, LOTRFoods.CORSAIR);
         }
      }

   }

   private void placeTreasure(World world, Random random, int i, int j, int k) {
      Block block = random.nextBoolean() ? LOTRMod.treasureGold : LOTRMod.treasureSilver;
      this.setBlockAndMetadata(world, i, j, k, block, random.nextInt(7));
   }
}
