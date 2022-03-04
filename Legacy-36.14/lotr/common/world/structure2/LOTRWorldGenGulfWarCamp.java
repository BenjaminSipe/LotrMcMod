package lotr.common.world.structure2;

import java.util.Random;
import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.animal.LOTREntityHorse;
import lotr.common.entity.npc.LOTREntityGulfHaradArcher;
import lotr.common.entity.npc.LOTREntityGulfHaradWarlord;
import lotr.common.entity.npc.LOTREntityGulfHaradWarrior;
import lotr.common.item.LOTRItemBanner;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.entity.EntityCreature;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTRWorldGenGulfWarCamp extends LOTRWorldGenGulfStructure {
   public LOTRWorldGenGulfWarCamp(boolean flag) {
      super(flag);
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, 15);
      this.setupRandomBlocks(random);
      int i1;
      int k1;
      int l;
      int k2;
      int j1;
      if (this.restrictions) {
         i1 = 0;
         k1 = 0;

         for(l = -16; l <= 16; ++l) {
            for(k2 = -16; k2 <= 16; ++k2) {
               j1 = this.getTopBlock(world, l, k2) - 1;
               if (!this.isSurface(world, l, j1, k2)) {
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

      byte j1;
      for(i1 = -15; i1 <= 15; ++i1) {
         for(k1 = -15; k1 <= 15; ++k1) {
            l = Math.abs(i1);
            k2 = Math.abs(k1);

            int randomGround;
            for(j1 = 0; (j1 >= -1 || !this.isOpaque(world, i1, j1, k1)) && this.getY(j1) >= 0; --j1) {
               if (j1 == 0) {
                  if (l <= 14 && k2 <= 14) {
                     if (random.nextBoolean()) {
                        this.setBlockAndMetadata(world, i1, j1, k1, LOTRMod.dirtPath, 0);
                     } else {
                        randomGround = random.nextInt(3);
                        if (randomGround == 0) {
                           this.setBlockAndMetadata(world, i1, j1, k1, Blocks.field_150349_c, 0);
                        } else if (randomGround == 1) {
                           this.setBlockAndMetadata(world, i1, j1, k1, Blocks.field_150346_d, 1);
                        } else if (randomGround == 2) {
                           this.setBlockAndMetadata(world, i1, j1, k1, Blocks.field_150354_m, 1);
                        }
                     }
                  } else {
                     this.setBlockAndMetadata(world, i1, j1, k1, Blocks.field_150349_c, 0);
                  }
               } else {
                  this.setBlockAndMetadata(world, i1, j1, k1, Blocks.field_150346_d, 0);
               }

               this.setGrassToDirt(world, i1, j1 - 1, k1);
            }

            j1 = 6;
            if (l <= 4 && k2 <= 4) {
               j1 = 15;
            }

            for(randomGround = 1; randomGround <= j1; ++randomGround) {
               this.setAir(world, i1, randomGround, k1);
            }

            if (l <= 12 && k2 <= 12 && random.nextInt(5) == 0) {
               this.setBlockAndMetadata(world, i1, 1, k1, LOTRMod.thatchFloor, 0);
            }
         }
      }

      this.loadStrScan("gulf_war_camp");
      this.associateBlockMetaAlias("WOOD", this.woodBlock, this.woodMeta);
      this.associateBlockMetaAlias("WOOD|4", this.woodBlock, this.woodMeta | 4);
      this.associateBlockMetaAlias("PLANK", this.plankBlock, this.plankMeta);
      this.associateBlockMetaAlias("FENCE", this.fenceBlock, this.fenceMeta);
      this.associateBlockAlias("TRAPDOOR", this.trapdoorBlock);
      this.associateBlockMetaAlias("ROOF", this.roofBlock, this.roofMeta);
      this.associateBlockAlias("ROOF_STAIR", this.roofStairBlock);
      this.associateBlockMetaAlias("FLAG", this.flagBlock, this.flagMeta);
      this.associateBlockMetaAlias("BONE", this.boneBlock, this.boneMeta);
      this.generateStrScan(world, random, 0, 0, 0);

      for(i1 = -13; i1 <= -9; i1 += 2) {
         this.setBlockAndMetadata(world, i1, 1, 12, this.bedBlock, 0);
         this.setBlockAndMetadata(world, i1, 1, 13, this.bedBlock, 8);
      }

      for(i1 = 9; i1 <= 13; i1 += 2) {
         this.setBlockAndMetadata(world, i1, 1, 12, this.bedBlock, 0);
         this.setBlockAndMetadata(world, i1, 1, 13, this.bedBlock, 8);
      }

      this.placeChest(world, random, -12, 1, 13, LOTRMod.chestBasket, 2, LOTRChestContents.GULF_HOUSE);
      this.placeChest(world, random, -10, 1, 13, LOTRMod.chestBasket, 2, LOTRChestContents.GULF_HOUSE);
      this.placeChest(world, random, 10, 1, 13, LOTRMod.chestBasket, 2, LOTRChestContents.GULF_HOUSE);
      this.placeChest(world, random, 12, 1, 13, LOTRMod.chestBasket, 2, LOTRChestContents.GULF_HOUSE);
      this.placeChest(world, random, -1, 1, 3, LOTRMod.chestBasket, 2, LOTRChestContents.GULF_HOUSE);
      this.placeGulfArmor(world, random, -11, 1, -13, 2);
      this.placeGulfArmor(world, random, -9, 1, -13, 2);
      this.placeGulfArmor(world, random, -13, 1, -11, 3);
      this.placeGulfArmor(world, random, -13, 1, -9, 3);
      this.placeGulfArmor(world, random, 9, 1, -13, 2);
      this.placeGulfArmor(world, random, 11, 1, -13, 2);
      this.placeGulfArmor(world, random, 13, 1, -11, 1);
      this.placeGulfArmor(world, random, 13, 1, -9, 1);
      this.placeWeaponRack(world, -8, 2, -9, 6, this.getRandomGulfWeapon(random));
      this.placeWeaponRack(world, -9, 2, -8, 7, this.getRandomGulfWeapon(random));
      this.placeWeaponRack(world, -7, 2, -8, 5, this.getRandomGulfWeapon(random));
      this.placeWeaponRack(world, -8, 2, -7, 4, this.getRandomGulfWeapon(random));
      this.placeWeaponRack(world, 8, 2, -9, 6, this.getRandomGulfWeapon(random));
      this.placeWeaponRack(world, 7, 2, -8, 7, this.getRandomGulfWeapon(random));
      this.placeWeaponRack(world, 9, 2, -8, 5, this.getRandomGulfWeapon(random));
      this.placeWeaponRack(world, 8, 2, -7, 4, this.getRandomGulfWeapon(random));
      this.placeSkull(world, random, -12, 3, -2);
      this.placeSkull(world, random, -12, 3, 2);
      this.placeWeaponRack(world, 11, 2, -4, 7, new ItemStack(LOTRMod.nearHaradBow));
      this.placeWeaponRack(world, 11, 2, 4, 7, new ItemStack(LOTRMod.nearHaradBow));
      this.placeBarrel(world, random, -13, 2, 9, 3, LOTRFoods.GULF_HARAD_DRINK);
      this.placeBarrel(world, random, 13, 2, 9, 3, LOTRFoods.GULF_HARAD_DRINK);
      this.placeWallBanner(world, 0, 6, -15, LOTRItemBanner.BannerType.HARAD_GULF, 2);
      this.placeWallBanner(world, -2, 5, -15, LOTRItemBanner.BannerType.HARAD_GULF, 2);
      this.placeWallBanner(world, 2, 5, -15, LOTRItemBanner.BannerType.HARAD_GULF, 2);
      this.placeWallBanner(world, -4, 4, -15, LOTRItemBanner.BannerType.HARAD_GULF, 2);
      this.placeWallBanner(world, 4, 4, -15, LOTRItemBanner.BannerType.HARAD_GULF, 2);
      this.placeWallBanner(world, -5, 13, -5, LOTRItemBanner.BannerType.HARAD_GULF, 2);
      this.placeWallBanner(world, 5, 13, -5, LOTRItemBanner.BannerType.HARAD_GULF, 2);
      this.placeWallBanner(world, -5, 13, 5, LOTRItemBanner.BannerType.HARAD_GULF, 0);
      this.placeWallBanner(world, 5, 13, 5, LOTRItemBanner.BannerType.HARAD_GULF, 0);
      this.placeWallBanner(world, -5, 13, -5, LOTRItemBanner.BannerType.HARAD_GULF, 3);
      this.placeWallBanner(world, -5, 13, 5, LOTRItemBanner.BannerType.HARAD_GULF, 3);
      this.placeWallBanner(world, 5, 13, -5, LOTRItemBanner.BannerType.HARAD_GULF, 1);
      this.placeWallBanner(world, 5, 13, 5, LOTRItemBanner.BannerType.HARAD_GULF, 1);
      int[] var14 = new int[]{-2, 2};
      k1 = var14.length;

      for(l = 0; l < k1; ++l) {
         k2 = var14[l];
         j1 = 1;
         int k1 = 12;
         LOTREntityHorse horse = new LOTREntityHorse(world);
         this.spawnNPCAndSetHome(horse, world, k2, j1, k1, 0);
         horse.func_110214_p(0);
         horse.saddleMountForWorldGen();
         horse.func_110177_bN();
         this.leashEntityTo(horse, world, k2, j1, k1);
      }

      LOTREntityGulfHaradWarlord warlord = new LOTREntityGulfHaradWarlord(world);
      warlord.spawnRidingHorse = false;
      this.spawnNPCAndSetHome(warlord, world, 0, 9, -3, 6);
      this.setBlockAndMetadata(world, 0, 9, 3, LOTRMod.commandTable, 0);
      int warriors = 6;

      for(l = 0; l < warriors; ++l) {
         LOTREntityGulfHaradWarrior warrior = random.nextInt(3) == 0 ? new LOTREntityGulfHaradArcher(world) : new LOTREntityGulfHaradWarrior(world);
         ((LOTREntityGulfHaradWarrior)warrior).spawnRidingHorse = false;
         this.spawnNPCAndSetHome((EntityCreature)warrior, world, 0, 1, -1, 16);
      }

      LOTREntityNPCRespawner respawner = new LOTREntityNPCRespawner(world);
      respawner.setSpawnClasses(LOTREntityGulfHaradWarrior.class, LOTREntityGulfHaradArcher.class);
      respawner.setCheckRanges(32, -8, 12, 24);
      respawner.setSpawnRanges(24, -4, 6, 16);
      this.placeNPCRespawner(respawner, world, 0, 0, 0);
      return true;
   }

   private void placeGulfArmor(World world, Random random, int i, int j, int k, int meta) {
      ItemStack[] armor;
      if (random.nextInt(3) != 0) {
         armor = new ItemStack[]{null, null, null, null};
      } else {
         armor = new ItemStack[]{new ItemStack(LOTRMod.helmetGulfHarad), new ItemStack(LOTRMod.bodyGulfHarad), new ItemStack(LOTRMod.legsGulfHarad), new ItemStack(LOTRMod.bootsGulfHarad)};
      }

      this.placeArmorStand(world, i, j, k, meta, armor);
   }
}
