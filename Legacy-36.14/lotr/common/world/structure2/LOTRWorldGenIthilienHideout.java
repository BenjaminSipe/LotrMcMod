package lotr.common.world.structure2;

import java.util.Random;
import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityRangerIthilien;
import lotr.common.entity.npc.LOTREntityRangerIthilienCaptain;
import lotr.common.item.LOTRItemBanner;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTRWorldGenIthilienHideout extends LOTRWorldGenStructureBase2 {
   public LOTRWorldGenIthilienHideout(boolean flag) {
      super(flag);
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, 0);
      int width = 5;
      int height = 4;
      int baseY = -(height + 2 + random.nextInt(4));
      int ladderY;
      int pass;
      int i1;
      if (this.restrictions) {
         if (!this.isSurface(world, 0, -1, 0)) {
            return false;
         }

         for(ladderY = -width; ladderY <= width; ++ladderY) {
            for(pass = -width; pass <= width; ++pass) {
               for(i1 = baseY; i1 <= baseY + height + 2; ++i1) {
                  if (!this.isOpaque(world, ladderY, i1, pass)) {
                     return false;
                  }
               }
            }
         }
      }

      int k1;
      int k2;
      for(ladderY = -width - 1; ladderY <= width + 1; ++ladderY) {
         for(pass = -width - 1; pass <= width + 1; ++pass) {
            i1 = Math.abs(ladderY);
            k1 = Math.abs(pass);
            boolean withinWalls = i1 <= width && k1 <= width;
            this.setBlockAndMetadata(world, ladderY, baseY, pass, Blocks.field_150348_b, 0);
            this.setBlockAndMetadata(world, ladderY, baseY + height + 1, pass, Blocks.field_150348_b, 0);

            for(k2 = baseY + 1; k2 <= baseY + height; ++k2) {
               if (withinWalls) {
                  this.setAir(world, ladderY, k2, pass);
               } else {
                  this.setBlockAndMetadata(world, ladderY, k2, pass, Blocks.field_150348_b, 0);
               }
            }

            if (withinWalls) {
               if (i1 <= 2 && k1 <= 2 || random.nextInt(3) == 0) {
                  this.setBlockAndMetadata(world, ladderY, baseY + 1, pass, LOTRMod.thatchFloor, 0);
               }

               if (i1 == width || k1 == width) {
                  this.setBlockAndMetadata(world, ladderY, baseY + 1, pass, LOTRMod.planks, 8);
               }
            }
         }
      }

      for(ladderY = baseY + 1; ladderY <= baseY + height || this.isOpaque(world, 0, ladderY, 0) || this.isOpaque(world, -1, ladderY, 0) && this.isOpaque(world, 1, ladderY, 0) && this.isOpaque(world, 0, ladderY, -1) && this.isOpaque(world, 0, ladderY, 1); ++ladderY) {
         if (!this.isOpaque(world, 0, ladderY, -1)) {
            this.setBlockAndMetadata(world, 0, ladderY, -1, Blocks.field_150348_b, 0);
         }

         this.setBlockAndMetadata(world, 0, ladderY, 0, Blocks.field_150468_ap, 3);
      }

      int i2;
      for(pass = 0; pass <= 1; ++pass) {
         for(i1 = -1; i1 <= 1; ++i1) {
            for(k1 = -1; k1 <= 1; ++k1) {
               i2 = Math.abs(i1);
               k2 = Math.abs(k1);
               if (i1 != 0 || k1 != 0) {
                  int j1;
                  if (pass == 0 && i1 == 0 && k1 == 1) {
                     for(int j1 = 0; j1 <= 3; ++j1) {
                        j1 = ladderY + j1;
                        if (LOTRTreeType.OAK_ITHILIEN_HIDEOUT.create(this.notifyChanges, random).func_76484_a(world, random, this.getX(i1, k1), this.getY(j1), this.getZ(i1, k1))) {
                           break;
                        }
                     }
                  }

                  if (pass == 1) {
                     boolean doublegrass = i2 != k2;

                     for(j1 = -3; j1 <= 3; ++j1) {
                        int j2 = ladderY + j1;
                        Block below = this.getBlock(world, i1, j2 - 1, k1);
                        if ((below == Blocks.field_150349_c || below == Blocks.field_150346_d) && !this.isOpaque(world, i1, j2, k1) && !this.isOpaque(world, i1, j2 + 1, k1)) {
                           if (doublegrass) {
                              this.setBlockAndMetadata(world, i1, j2, k1, Blocks.field_150398_cm, 2);
                              this.setBlockAndMetadata(world, i1, j2 + 1, k1, Blocks.field_150398_cm, 8);
                           } else {
                              this.setBlockAndMetadata(world, i1, j2, k1, Blocks.field_150329_H, 1);
                           }
                           break;
                        }
                     }
                  }
               }
            }
         }
      }

      this.setBlockAndMetadata(world, -width, baseY + 3, -width, Blocks.field_150478_aa, 2);
      this.setBlockAndMetadata(world, -width, baseY + 3, width, Blocks.field_150478_aa, 2);
      this.setBlockAndMetadata(world, width, baseY + 3, -width, Blocks.field_150478_aa, 1);
      this.setBlockAndMetadata(world, width, baseY + 3, width, Blocks.field_150478_aa, 1);
      this.placeWallBanner(world, -width - 1, baseY + 4, 0, LOTRItemBanner.BannerType.ITHILIEN, 1);
      this.placeWallBanner(world, 0, baseY + 4, -width - 1, LOTRItemBanner.BannerType.ITHILIEN, 0);
      this.placeWallBanner(world, width + 1, baseY + 4, 0, LOTRItemBanner.BannerType.ITHILIEN, 3);
      this.placeWallBanner(world, -2, baseY + 4, width + 1, LOTRItemBanner.BannerType.ITHILIEN, 2);
      this.placeWallBanner(world, 0, baseY + 4, width + 1, LOTRItemBanner.BannerType.GONDOR, 2);
      this.placeWallBanner(world, 2, baseY + 4, width + 1, LOTRItemBanner.BannerType.ITHILIEN, 2);
      this.setBlockAndMetadata(world, -2, baseY + 1, width, LOTRMod.gondorianTable, 0);
      this.setBlockAndMetadata(world, 0, baseY + 1, width, Blocks.field_150462_ai, 0);
      this.setBlockAndMetadata(world, 2, baseY + 1, width, Blocks.field_150460_al, 2);
      this.placeChest(world, random, width, baseY + 1, 0, LOTRMod.chestLebethron, 5, LOTRChestContents.GONDOR_FORTRESS_DRINKS);
      ItemStack drink = LOTRFoods.GONDOR_DRINK.getRandomBrewableDrink(random);
      this.placeBarrel(world, random, width, baseY + 2, -3, 5, drink);
      this.placeBarrel(world, random, width, baseY + 2, -2, 5, drink);
      this.placeBarrel(world, random, width, baseY + 2, 2, 5, drink);
      this.placeBarrel(world, random, width, baseY + 2, 3, 5, drink);

      for(i1 = -3; i1 <= 3; i1 += 2) {
         this.setBlockAndMetadata(world, i1, baseY + 1, -width + 1, LOTRMod.strawBed, 2);
         this.setBlockAndMetadata(world, i1, baseY + 1, -width, LOTRMod.strawBed, 10);
      }

      this.placeChest(world, random, -width, baseY + 1, 0, LOTRMod.chestLebethron, 4, LOTRChestContents.GONDOR_FORTRESS_SUPPLIES);
      ItemStack[] rangerArmor = new ItemStack[]{new ItemStack(LOTRMod.helmetRangerIthilien), new ItemStack(LOTRMod.bodyRangerIthilien), new ItemStack(LOTRMod.legsRangerIthilien), new ItemStack(LOTRMod.bootsRangerIthilien)};
      this.placeArmorStand(world, -width, baseY + 2, -2, 3, rangerArmor);
      this.placeArmorStand(world, -width, baseY + 2, 2, 3, rangerArmor);
      k1 = 2 + random.nextInt(3);

      for(i2 = 0; i2 < k1; ++i2) {
         LOTREntityRangerIthilien ranger = new LOTREntityRangerIthilien(world);
         this.spawnNPCAndSetHome(ranger, world, -2, baseY + 1, -2, 16);
      }

      this.spawnNPCAndSetHome(new LOTREntityRangerIthilienCaptain(world), world, -2, baseY + 1, -2, 16);
      return true;
   }
}
