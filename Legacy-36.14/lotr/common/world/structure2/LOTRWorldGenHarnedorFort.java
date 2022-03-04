package lotr.common.world.structure2;

import java.util.Random;
import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.animal.LOTREntityHorse;
import lotr.common.entity.npc.LOTREntityHarnedhrim;
import lotr.common.entity.npc.LOTREntityHarnedorArcher;
import lotr.common.entity.npc.LOTREntityHarnedorWarlord;
import lotr.common.entity.npc.LOTREntityHarnedorWarrior;
import lotr.common.item.LOTRItemBanner;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.entity.EntityCreature;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTRWorldGenHarnedorFort extends LOTRWorldGenHarnedorStructure {
   public LOTRWorldGenHarnedorFort(boolean flag) {
      super(flag);
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, 12);
      this.setupRandomBlocks(random);
      int i1;
      int k1;
      int i2;
      int k2;
      int j1;
      if (this.restrictions) {
         i1 = 0;
         k1 = 0;

         for(i2 = -15; i2 <= 15; ++i2) {
            for(k2 = -15; k2 <= 15; ++k2) {
               j1 = this.getTopBlock(world, i2, k2) - 1;
               if (!this.isSurface(world, i2, j1, k2)) {
                  return false;
               }

               if (j1 < i1) {
                  i1 = j1;
               }

               if (j1 > k1) {
                  k1 = j1;
               }

               if (k1 - i1 > 12) {
                  return false;
               }
            }
         }
      }

      for(i1 = -15; i1 <= 15; ++i1) {
         for(k1 = -15; k1 <= 15; ++k1) {
            i2 = Math.abs(i1);
            k2 = Math.abs(k1);
            boolean bedRegion = i2 <= 3 && k1 >= 5 && k1 <= 9 || i2 <= 2 && k1 == 4 || i2 <= 1 && k1 == 3;
            int airHeight = 7;

            int j1;
            for(j1 = 0; j1 <= airHeight; ++j1) {
               this.setAir(world, i1, j1, k1);
            }

            for(j1 = 0; (j1 >= -1 || !this.isOpaque(world, i1, j1, k1)) && this.getY(j1) >= 0; --j1) {
               if (!bedRegion || j1 != 0) {
                  if (j1 == 0) {
                     int randomGround;
                     if (i2 <= 11 && k2 <= 11) {
                        if (random.nextBoolean()) {
                           this.setBlockAndMetadata(world, i1, j1, k1, LOTRMod.dirtPath, 0);
                        } else {
                           randomGround = random.nextInt(3);
                           if (randomGround == 0) {
                              this.setBlockAndMetadata(world, i1, j1, k1, Blocks.field_150349_c, 0);
                           } else if (randomGround == 1) {
                              this.setBlockAndMetadata(world, i1, j1, k1, Blocks.field_150346_d, 1);
                           } else if (randomGround == 2) {
                              this.setBlockAndMetadata(world, i1, j1, k1, Blocks.field_150354_m, 0);
                           }
                        }
                     } else {
                        randomGround = random.nextInt(3);
                        if (randomGround == 0) {
                           this.setBlockAndMetadata(world, i1, j1, k1, Blocks.field_150349_c, 0);
                        } else if (randomGround == 1) {
                           this.setBlockAndMetadata(world, i1, j1, k1, Blocks.field_150346_d, 1);
                        } else if (randomGround == 2) {
                           this.setBlockAndMetadata(world, i1, j1, k1, Blocks.field_150354_m, 0);
                        }
                     }
                  } else {
                     this.setBlockAndMetadata(world, i1, j1, k1, Blocks.field_150346_d, 0);
                  }

                  this.setGrassToDirt(world, i1, j1 - 1, k1);
               }
            }

            if (!bedRegion && i2 <= 10 && k2 <= 10 && random.nextInt(5) == 0) {
               this.setBlockAndMetadata(world, i1, 1, k1, LOTRMod.thatchFloor, 0);
            }
         }
      }

      this.loadStrScan("harnedor_fort");
      this.associateBlockMetaAlias("WOOD", this.woodBlock, this.woodMeta);
      this.associateBlockMetaAlias("WOOD|4", this.woodBlock, this.woodMeta | 4);
      this.associateBlockMetaAlias("PLANK", this.plankBlock, this.plankMeta);
      this.associateBlockMetaAlias("PLANK_SLAB", this.plankSlabBlock, this.plankSlabMeta);
      this.associateBlockMetaAlias("PLANK_SLAB_INV", this.plankSlabBlock, this.plankSlabMeta | 8);
      this.associateBlockAlias("PLANK_STAIR", this.plankStairBlock);
      this.associateBlockMetaAlias("FENCE", this.fenceBlock, this.fenceMeta);
      this.associateBlockAlias("FENCE_GATE", this.fenceGateBlock);
      this.associateBlockMetaAlias("ROOF", this.roofBlock, this.roofMeta);
      this.associateBlockMetaAlias("PLANK2", this.plank2Block, this.plank2Meta);
      this.associateBlockMetaAlias("PLANK2_SLAB", this.plank2SlabBlock, this.plank2SlabMeta);
      this.associateBlockMetaAlias("PLANK2_SLAB_INV", this.plank2SlabBlock, this.plank2SlabMeta | 8);
      this.associateBlockMetaAlias("BONE", this.boneBlock, this.boneMeta);
      this.generateStrScan(world, random, 0, 1, 0);
      this.setBlockAndMetadata(world, 2, 0, 8, this.bedBlock, 0);
      this.setBlockAndMetadata(world, 2, 0, 9, this.bedBlock, 8);
      this.setBlockAndMetadata(world, 10, 1, 7, this.bedBlock, 0);
      this.setBlockAndMetadata(world, 10, 1, 8, this.bedBlock, 8);
      this.setBlockAndMetadata(world, 7, 1, 10, this.bedBlock, 1);
      this.setBlockAndMetadata(world, 8, 1, 10, this.bedBlock, 9);
      this.setBlockAndMetadata(world, -10, 1, 7, this.bedBlock, 0);
      this.setBlockAndMetadata(world, -10, 1, 8, this.bedBlock, 8);
      this.setBlockAndMetadata(world, -7, 1, 10, this.bedBlock, 3);
      this.setBlockAndMetadata(world, -8, 1, 10, this.bedBlock, 11);
      this.placeChest(world, random, 0, 0, 7, LOTRMod.chestBasket, 3, LOTRChestContents.HARNENNOR_HOUSE);
      this.placeChest(world, random, -9, 1, 9, LOTRMod.chestBasket, 2, LOTRChestContents.HARNENNOR_HOUSE);
      this.placeChest(world, random, 9, 1, 9, LOTRMod.chestBasket, 2, LOTRChestContents.HARNENNOR_HOUSE);

      for(i1 = -2; i1 <= 0; ++i1) {
         int j1 = 1;
         int k1 = 9;
         if (random.nextBoolean()) {
            this.placePlate(world, random, i1, j1, k1, LOTRMod.ceramicPlateBlock, LOTRFoods.HARNEDOR);
         } else {
            this.placeMug(world, random, i1, j1, k1, 0, LOTRFoods.HARNEDOR_DRINK);
         }
      }

      this.placeWeaponRack(world, 4, 2, -1, 6, this.getRandomHarnedorWeapon(random));
      this.placeWeaponRack(world, 5, 2, 0, 5, this.getRandomHarnedorWeapon(random));
      this.placeWeaponRack(world, 4, 2, 1, 4, this.getRandomHarnedorWeapon(random));
      this.placeWeaponRack(world, 3, 2, 0, 7, this.getRandomHarnedorWeapon(random));
      this.placeWeaponRack(world, -4, 2, -1, 6, this.getRandomHarnedorWeapon(random));
      this.placeWeaponRack(world, -3, 2, 0, 5, this.getRandomHarnedorWeapon(random));
      this.placeWeaponRack(world, -4, 2, 1, 4, this.getRandomHarnedorWeapon(random));
      this.placeWeaponRack(world, -5, 2, 0, 7, this.getRandomHarnedorWeapon(random));
      this.placeHarnedorArmor(world, random, 9, 1, -3, 1);
      this.placeHarnedorArmor(world, random, 9, 1, 0, 1);
      this.placeHarnedorArmor(world, random, 9, 1, 3, 1);
      this.placeSkull(world, random, -8, 3, -4);
      this.placeSkull(world, random, -8, 3, 2);
      this.placeSkull(world, random, -10, 6, 0);
      this.placeSkull(world, random, 10, 6, 0);
      this.placeSkull(world, random, -13, 8, -15);
      this.placeSkull(world, random, 13, 8, -15);
      this.placeSkull(world, random, -13, 8, 15);
      this.placeSkull(world, random, 13, 8, 15);
      this.placeWallBanner(world, 0, 5, -13, LOTRItemBanner.BannerType.NEAR_HARAD, 2);
      this.placeWallBanner(world, -3, 4, -13, LOTRItemBanner.BannerType.NEAR_HARAD, 2);
      this.placeWallBanner(world, 3, 4, -13, LOTRItemBanner.BannerType.NEAR_HARAD, 2);
      this.placeWallBanner(world, 0, 6, 8, LOTRItemBanner.BannerType.NEAR_HARAD, 2);
      this.setBlockAndMetadata(world, 7, 1, 5, LOTRMod.commandTable, 0);
      LOTREntityHarnedorWarlord warlord = new LOTREntityHarnedorWarlord(world);
      warlord.spawnRidingHorse = false;
      this.spawnNPCAndSetHome(warlord, world, 0, 3, 7, 4);
      k1 = 4 + random.nextInt(4);

      for(i2 = 0; i2 < k1; ++i2) {
         LOTREntityHarnedhrim warrior = random.nextInt(3) == 0 ? new LOTREntityHarnedorArcher(world) : new LOTREntityHarnedorWarrior(world);
         ((LOTREntityHarnedhrim)warrior).spawnRidingHorse = false;
         this.spawnNPCAndSetHome((EntityCreature)warrior, world, 0, 1, 0, 16);
      }

      int[] var19 = new int[]{-4, 4};
      k2 = var19.length;

      for(j1 = 0; j1 < k2; ++j1) {
         int i1 = var19[j1];
         int j1 = 1;
         int k1 = -6;
         LOTREntityHorse horse = new LOTREntityHorse(world);
         this.spawnNPCAndSetHome(horse, world, i1, j1, k1, 0);
         horse.func_110214_p(0);
         horse.saddleMountForWorldGen();
         horse.func_110177_bN();
         this.leashEntityTo(horse, world, i1, j1, k1);
      }

      LOTREntityNPCRespawner respawner = new LOTREntityNPCRespawner(world);
      respawner.setSpawnClasses(LOTREntityHarnedorWarrior.class, LOTREntityHarnedorArcher.class);
      respawner.setCheckRanges(16, -8, 12, 12);
      respawner.setSpawnRanges(8, -2, 2, 16);
      this.placeNPCRespawner(respawner, world, 0, 0, 0);
      return true;
   }

   private void placeHarnedorArmor(World world, Random random, int i, int j, int k, int meta) {
      ItemStack[] armor;
      if (random.nextInt(3) != 0) {
         armor = new ItemStack[]{null, null, null, null};
      } else {
         armor = new ItemStack[]{new ItemStack(LOTRMod.helmetHarnedor), new ItemStack(LOTRMod.bodyHarnedor), new ItemStack(LOTRMod.legsHarnedor), new ItemStack(LOTRMod.bootsHarnedor)};
      }

      this.placeArmorStand(world, i, j, k, meta, armor);
   }
}
