package lotr.common.world.structure;

import java.util.Random;
import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.npc.LOTREntityDolGuldurOrc;
import lotr.common.entity.npc.LOTREntityDwarf;
import lotr.common.entity.npc.LOTREntityGundabadOrc;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.entity.npc.LOTREntityWoodElf;
import lotr.common.entity.npc.LOTREntityWoodElfCaptain;
import lotr.common.entity.npc.LOTREntityWoodElfScout;
import lotr.common.entity.npc.LOTREntityWoodElfWarrior;
import lotr.common.item.LOTRItemBanner;
import lotr.common.world.feature.LOTRWorldGenMirkOak;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRWorldGenWoodElfTower extends LOTRWorldGenStructureBase {
   private WorldGenerator treeGen = (new LOTRWorldGenMirkOak(true, 6, 6, 0, false)).setGreenOak().disableRestrictions().disableRoots();
   protected Block plateBlock;

   public LOTRWorldGenWoodElfTower(boolean flag) {
      super(flag);
      this.plateBlock = LOTRMod.woodPlateBlock;
   }

   public boolean func_76484_a(World world, Random random, int i, int j, int k) {
      if (this.restrictions && world.func_147439_a(i, j - 1, k) != Blocks.field_150349_c) {
         return false;
      } else {
         --j;
         int rotation = random.nextInt(4);
         int radius = 6;
         int radiusPlusOne = radius + 1;
         if (!this.restrictions && this.usingPlayer != null) {
            rotation = this.usingPlayerRotation();
         }

         switch(rotation) {
         case 0:
            k += radiusPlusOne;
            break;
         case 1:
            i -= radiusPlusOne;
            break;
         case 2:
            k -= radiusPlusOne;
            break;
         case 3:
            i += radiusPlusOne;
         }

         int sections;
         int topHeight;
         int wallThresholdMin;
         int wallThresholdMax;
         int l;
         int sectionBase;
         if (this.restrictions) {
            sections = j;
            int maxHeight = j;

            for(topHeight = i - radiusPlusOne; topHeight <= i + radiusPlusOne; ++topHeight) {
               for(wallThresholdMin = k - radiusPlusOne; wallThresholdMin <= k + radiusPlusOne; ++wallThresholdMin) {
                  wallThresholdMax = topHeight - i;
                  l = wallThresholdMin - k;
                  if (wallThresholdMax * wallThresholdMax + l * l <= radiusPlusOne * radiusPlusOne) {
                     sectionBase = world.func_72825_h(topHeight, wallThresholdMin) - 1;
                     Block block = world.func_147439_a(topHeight, sectionBase, wallThresholdMin);
                     if (block != Blocks.field_150349_c && block != Blocks.field_150346_d && block != Blocks.field_150348_b && !block.isWood(world, topHeight, sectionBase, wallThresholdMin) && !block.isLeaves(world, topHeight, sectionBase, wallThresholdMin)) {
                        return false;
                     }

                     if (sectionBase < sections) {
                        sections = sectionBase;
                     }

                     if (sectionBase > maxHeight) {
                        maxHeight = sectionBase;
                     }

                     if (maxHeight - sections > 8) {
                        return false;
                     }
                  }
               }
            }
         }

         sections = 3 + random.nextInt(3);
         int sectionHeight = 8;
         topHeight = j + sections * sectionHeight;
         wallThresholdMin = radius * radius;
         wallThresholdMax = radiusPlusOne * radiusPlusOne;

         int i1;
         int j1;
         int k1;
         int k2;
         int k1;
         for(l = i - radius; l <= i + radius; ++l) {
            for(sectionBase = k - radius; sectionBase <= k + radius; ++sectionBase) {
               k1 = l - i;
               i1 = sectionBase - k;
               j1 = k1 * k1 + i1 * i1;
               if (j1 < wallThresholdMax) {
                  k1 = j - sectionHeight;

                  for(k2 = k1; (k2 == k1 || !LOTRMod.isOpaque(world, l, k2, sectionBase)) && k2 >= 0; --k2) {
                     if (k2 == k1 && j1 < wallThresholdMin) {
                        this.func_150516_a(world, l, k2, sectionBase, LOTRMod.planks2, 13);
                     } else {
                        this.func_150516_a(world, l, k2, sectionBase, LOTRMod.brick3, 5);
                     }

                     this.setGrassToDirt(world, l, k2 - 1, sectionBase);
                  }
               }
            }
         }

         for(l = -1; l < sections; ++l) {
            sectionBase = j + l * sectionHeight;

            for(k1 = sectionBase + 1; k1 <= sectionBase + sectionHeight; ++k1) {
               for(i1 = i - radius; i1 <= i + radius; ++i1) {
                  for(j1 = k - radius; j1 <= k + radius; ++j1) {
                     k1 = i1 - i;
                     k2 = j1 - k;
                     int distSq = k1 * k1 + k2 * k2;
                     if (distSq < wallThresholdMax) {
                        if (distSq >= wallThresholdMin) {
                           this.func_150516_a(world, i1, k1, j1, LOTRMod.brick3, 5);
                           if (l == sections - 1 && k1 == sectionBase + sectionHeight) {
                              this.func_150516_a(world, i1, k1 + 1, j1, LOTRMod.brick3, 5);
                              this.func_150516_a(world, i1, k1 + 2, j1, LOTRMod.slabSingle6, 2);
                           }
                        } else if (k1 != sectionBase + sectionHeight || Math.abs(k1) <= 2 && Math.abs(k2) <= 2) {
                           this.func_150516_a(world, i1, k1, j1, Blocks.field_150350_a, 0);
                        } else {
                           this.func_150516_a(world, i1, k1, j1, LOTRMod.planks2, 13);
                        }

                        this.setGrassToDirt(world, i1, k1 - 1, j1);
                     }
                  }
               }

               this.func_150516_a(world, i, k1, k, LOTRMod.wood7, 1);
            }

            for(k1 = 0; k1 < 2; ++k1) {
               i1 = sectionBase + k1 * 4;
               this.func_150516_a(world, i - 4, sectionBase + 2, k - 4, LOTRMod.fence2, 13);
               this.func_150516_a(world, i, i1 + 1, k + 1, LOTRMod.slabSingle6, 2);
               this.func_150516_a(world, i, i1 + 1, k + 2, LOTRMod.slabSingle6, 2);
               this.func_150516_a(world, i + 1, i1 + 2, k, LOTRMod.slabSingle6, 2);
               this.func_150516_a(world, i + 2, i1 + 2, k, LOTRMod.slabSingle6, 2);
               this.func_150516_a(world, i, i1 + 3, k - 1, LOTRMod.slabSingle6, 2);
               this.func_150516_a(world, i, i1 + 3, k - 2, LOTRMod.slabSingle6, 2);
               this.func_150516_a(world, i - 1, i1 + 4, k, LOTRMod.slabSingle6, 2);
               this.func_150516_a(world, i - 2, i1 + 4, k, LOTRMod.slabSingle6, 2);

               for(j1 = 0; j1 <= 1; ++j1) {
                  for(k1 = 0; k1 <= 1; ++k1) {
                     this.func_150516_a(world, i + 1 + j1, i1 + 1, k + 1 + k1, LOTRMod.slabSingle6, 10);
                     this.func_150516_a(world, i + 1 + j1, i1 + 2, k - 2 + k1, LOTRMod.slabSingle6, 10);
                     this.func_150516_a(world, i - 2 + j1, i1 + 3, k - 2 + k1, LOTRMod.slabSingle6, 10);
                     this.func_150516_a(world, i - 2 + j1, i1 + 4, k + 1 + k1, LOTRMod.slabSingle6, 10);
                  }
               }

               this.func_150516_a(world, i - 1, i1 + 2, k, LOTRMod.woodElvenTorch, 2);
               this.func_150516_a(world, i + 1, i1 + 4, k, LOTRMod.woodElvenTorch, 1);
            }

            this.func_150516_a(world, i - 4, sectionBase + 2, k - 4, LOTRMod.fence2, 13);
            this.func_150516_a(world, i - 4, sectionBase + 3, k - 4, LOTRMod.woodElvenTorch, 5);
            this.func_150516_a(world, i - 4, sectionBase + 2, k + 4, LOTRMod.fence2, 13);
            this.func_150516_a(world, i - 4, sectionBase + 3, k + 4, LOTRMod.woodElvenTorch, 5);
            this.func_150516_a(world, i + 4, sectionBase + 2, k - 4, LOTRMod.fence2, 13);
            this.func_150516_a(world, i + 4, sectionBase + 3, k - 4, LOTRMod.woodElvenTorch, 5);
            this.func_150516_a(world, i + 4, sectionBase + 2, k + 4, LOTRMod.fence2, 13);
            this.func_150516_a(world, i + 4, sectionBase + 3, k + 4, LOTRMod.woodElvenTorch, 5);
            if (l > 0) {
               k1 = sectionBase + 1;

               while(true) {
                  if (k1 > sectionBase + 4) {
                     this.func_150516_a(world, i - 1, sectionBase + 4, k - 6, LOTRMod.stairsWoodElvenBrick, 5);
                     this.func_150516_a(world, i + 1, sectionBase + 4, k - 6, LOTRMod.stairsWoodElvenBrick, 4);
                     this.func_150516_a(world, i - 1, sectionBase + 4, k + 6, LOTRMod.stairsWoodElvenBrick, 5);
                     this.func_150516_a(world, i + 1, sectionBase + 4, k + 6, LOTRMod.stairsWoodElvenBrick, 4);
                     this.func_150516_a(world, i - 6, sectionBase + 4, k - 1, LOTRMod.stairsWoodElvenBrick, 7);
                     this.func_150516_a(world, i - 6, sectionBase + 4, k + 1, LOTRMod.stairsWoodElvenBrick, 6);
                     this.func_150516_a(world, i + 6, sectionBase + 4, k - 1, LOTRMod.stairsWoodElvenBrick, 7);
                     this.func_150516_a(world, i + 6, sectionBase + 4, k + 1, LOTRMod.stairsWoodElvenBrick, 6);

                     for(k1 = i - 2; k1 <= i + 2; ++k1) {
                        this.func_150516_a(world, k1, sectionBase, k - 8, LOTRMod.stairsGreenOak, 6);
                        this.func_150516_a(world, k1, sectionBase + 1, k - 8, LOTRMod.fence2, 13);
                        this.func_150516_a(world, k1, sectionBase, k + 8, LOTRMod.stairsGreenOak, 7);
                        this.func_150516_a(world, k1, sectionBase + 1, k + 8, LOTRMod.fence2, 13);
                     }

                     for(k1 = k - 2; k1 <= k + 2; ++k1) {
                        this.func_150516_a(world, i - 8, sectionBase, k1, LOTRMod.stairsGreenOak, 4);
                        this.func_150516_a(world, i - 8, sectionBase + 1, k1, LOTRMod.fence2, 13);
                        this.func_150516_a(world, i + 8, sectionBase, k1, LOTRMod.stairsGreenOak, 5);
                        this.func_150516_a(world, i + 8, sectionBase + 1, k1, LOTRMod.fence2, 13);
                     }

                     for(k1 = i - 1; k1 <= i + 1; ++k1) {
                        this.func_150516_a(world, k1, sectionBase, k - 7, LOTRMod.planks2, 13);
                        this.func_150516_a(world, k1, sectionBase, k + 7, LOTRMod.planks2, 13);
                     }

                     for(k1 = k - 1; k1 <= k + 1; ++k1) {
                        this.func_150516_a(world, i - 7, sectionBase, k1, LOTRMod.planks2, 13);
                        this.func_150516_a(world, i + 7, sectionBase, k1, LOTRMod.planks2, 13);
                     }

                     this.func_150516_a(world, i - 7, sectionBase, k - 2, LOTRMod.stairsGreenOak, 6);
                     this.func_150516_a(world, i - 7, sectionBase + 1, k - 2, LOTRMod.fence2, 13);
                     this.func_150516_a(world, i - 8, sectionBase + 2, k - 2, LOTRMod.woodElvenTorch, 5);
                     this.func_150516_a(world, i - 7, sectionBase, k + 2, LOTRMod.stairsGreenOak, 7);
                     this.func_150516_a(world, i - 7, sectionBase + 1, k + 2, LOTRMod.fence2, 13);
                     this.func_150516_a(world, i - 8, sectionBase + 2, k + 2, LOTRMod.woodElvenTorch, 5);
                     this.func_150516_a(world, i + 7, sectionBase, k - 2, LOTRMod.stairsGreenOak, 6);
                     this.func_150516_a(world, i + 7, sectionBase + 1, k - 2, LOTRMod.fence2, 13);
                     this.func_150516_a(world, i + 8, sectionBase + 2, k - 2, LOTRMod.woodElvenTorch, 5);
                     this.func_150516_a(world, i + 7, sectionBase, k + 2, LOTRMod.stairsGreenOak, 7);
                     this.func_150516_a(world, i + 7, sectionBase + 1, k + 2, LOTRMod.fence2, 13);
                     this.func_150516_a(world, i + 8, sectionBase + 2, k + 2, LOTRMod.woodElvenTorch, 5);
                     this.func_150516_a(world, i - 2, sectionBase, k - 7, LOTRMod.stairsGreenOak, 4);
                     this.func_150516_a(world, i - 2, sectionBase + 1, k - 7, LOTRMod.fence2, 13);
                     this.func_150516_a(world, i - 2, sectionBase + 2, k - 8, LOTRMod.woodElvenTorch, 5);
                     this.func_150516_a(world, i + 2, sectionBase, k - 7, LOTRMod.stairsGreenOak, 5);
                     this.func_150516_a(world, i + 2, sectionBase + 1, k - 7, LOTRMod.fence2, 13);
                     this.func_150516_a(world, i + 2, sectionBase + 2, k - 8, LOTRMod.woodElvenTorch, 5);
                     this.func_150516_a(world, i - 2, sectionBase, k + 7, LOTRMod.stairsGreenOak, 4);
                     this.func_150516_a(world, i - 2, sectionBase + 1, k + 7, LOTRMod.fence2, 13);
                     this.func_150516_a(world, i - 2, sectionBase + 2, k + 8, LOTRMod.woodElvenTorch, 5);
                     this.func_150516_a(world, i + 2, sectionBase, k + 7, LOTRMod.stairsGreenOak, 5);
                     this.func_150516_a(world, i + 2, sectionBase + 1, k + 7, LOTRMod.fence2, 13);
                     this.func_150516_a(world, i + 2, sectionBase + 2, k + 8, LOTRMod.woodElvenTorch, 5);
                     break;
                  }

                  for(i1 = i - 1; i1 <= i + 1; ++i1) {
                     this.func_150516_a(world, i1, k1, k - 6, Blocks.field_150350_a, 0);
                     this.func_150516_a(world, i1, k1, k + 6, Blocks.field_150350_a, 0);
                  }

                  for(i1 = k - 1; i1 <= k + 1; ++i1) {
                     this.func_150516_a(world, i - 6, k1, i1, Blocks.field_150350_a, 0);
                     this.func_150516_a(world, i + 6, k1, i1, Blocks.field_150350_a, 0);
                  }

                  ++k1;
               }
            }

            LOTREntityWoodElf woodElf = random.nextInt(3) == 0 ? new LOTREntityWoodElfScout(world) : new LOTREntityWoodElfWarrior(world);
            ((LOTREntityWoodElf)woodElf).func_70012_b((double)(i - 3) + 0.5D, (double)(sectionBase + 1), (double)(k - 3) + 0.5D, world.field_73012_v.nextFloat() * 360.0F, 0.0F);
            ((LOTREntityWoodElf)woodElf).spawnRidingHorse = false;
            ((LOTREntityWoodElf)woodElf).func_110161_a((IEntityLivingData)null);
            ((LOTREntityWoodElf)woodElf).func_110171_b(i, sectionBase + 1, k, 12);
            ((LOTREntityWoodElf)woodElf).isNPCPersistent = true;
            world.func_72838_d((Entity)woodElf);
         }

         this.treeGen.func_76484_a(world, random, i, topHeight, k);

         for(l = topHeight + 2; l <= topHeight + 3; ++l) {
            this.func_150516_a(world, i + 6, l, k - 3, LOTRMod.brick3, 5);
            this.func_150516_a(world, i + 6, l, k, LOTRMod.brick3, 5);
            this.func_150516_a(world, i + 6, l, k + 3, LOTRMod.brick3, 5);
            this.func_150516_a(world, i - 3, l, k + 6, LOTRMod.brick3, 5);
            this.func_150516_a(world, i, l, k + 6, LOTRMod.brick3, 5);
            this.func_150516_a(world, i + 3, l, k + 6, LOTRMod.brick3, 5);
            this.func_150516_a(world, i - 6, l, k - 3, LOTRMod.brick3, 5);
            this.func_150516_a(world, i - 6, l, k, LOTRMod.brick3, 5);
            this.func_150516_a(world, i - 6, l, k + 3, LOTRMod.brick3, 5);
            this.func_150516_a(world, i - 3, l, k - 6, LOTRMod.brick3, 5);
            this.func_150516_a(world, i, l, k - 6, LOTRMod.brick3, 5);
            this.func_150516_a(world, i + 3, l, k - 6, LOTRMod.brick3, 5);
         }

         this.func_150516_a(world, i + 6, topHeight + 2, k - 2, LOTRMod.brick3, 5);
         this.func_150516_a(world, i + 6, topHeight + 2, k + 2, LOTRMod.brick3, 5);
         this.func_150516_a(world, i - 2, topHeight + 2, k + 6, LOTRMod.brick3, 5);
         this.func_150516_a(world, i + 2, topHeight + 2, k + 6, LOTRMod.brick3, 5);
         this.func_150516_a(world, i - 6, topHeight + 2, k - 2, LOTRMod.brick3, 5);
         this.func_150516_a(world, i - 6, topHeight + 2, k + 2, LOTRMod.brick3, 5);
         this.func_150516_a(world, i - 2, topHeight + 2, k - 6, LOTRMod.brick3, 5);
         this.func_150516_a(world, i + 2, topHeight + 2, k - 6, LOTRMod.brick3, 5);
         ItemStack bow1 = new ItemStack(LOTRMod.mirkwoodBow);
         ItemStack bow2 = new ItemStack(LOTRMod.mirkwoodBow);
         ItemStack[] armor = new ItemStack[]{new ItemStack(LOTRMod.helmetWoodElvenScout), new ItemStack(LOTRMod.bodyWoodElvenScout), new ItemStack(LOTRMod.legsWoodElvenScout), new ItemStack(LOTRMod.bootsWoodElvenScout)};
         switch(rotation) {
         case 0:
            this.placeArmorStand(world, i, topHeight + 1, k + 5, 0, armor);
            this.spawnItemFrame(world, i + 6, topHeight + 2, k, 1, bow1);
            this.spawnItemFrame(world, i - 6, topHeight + 2, k, 3, bow2);
            this.func_150516_a(world, i, topHeight + 1, k - 4, LOTRMod.commandTable, 0);
            break;
         case 1:
            this.spawnItemFrame(world, i, topHeight + 2, k + 6, 2, bow1);
            this.spawnItemFrame(world, i, topHeight + 2, k - 6, 0, bow2);
            this.placeArmorStand(world, i - 5, topHeight + 1, k, 1, armor);
            this.func_150516_a(world, i + 4, topHeight + 1, k, LOTRMod.commandTable, 0);
            break;
         case 2:
            this.spawnItemFrame(world, i + 6, topHeight + 2, k, 1, bow1);
            this.placeArmorStand(world, i, topHeight + 1, k - 5, 2, armor);
            this.spawnItemFrame(world, i - 6, topHeight + 2, k, 3, bow2);
            this.func_150516_a(world, i, topHeight + 1, k + 4, LOTRMod.commandTable, 0);
            break;
         case 3:
            this.spawnItemFrame(world, i, topHeight + 2, k + 6, 2, bow1);
            this.placeArmorStand(world, i + 5, topHeight + 1, k, 3, armor);
            this.spawnItemFrame(world, i, topHeight + 2, k - 6, 0, bow2);
            this.func_150516_a(world, i - 4, topHeight + 1, k, LOTRMod.commandTable, 0);
         }

         this.placeWallBanner(world, i, topHeight + 1, k + 6, 0, LOTRItemBanner.BannerType.WOOD_ELF);
         this.placeWallBanner(world, i - 6, topHeight + 1, k, 1, LOTRItemBanner.BannerType.WOOD_ELF);
         this.placeWallBanner(world, i, topHeight + 1, k - 6, 2, LOTRItemBanner.BannerType.WOOD_ELF);
         this.placeWallBanner(world, i + 6, topHeight + 1, k, 3, LOTRItemBanner.BannerType.WOOD_ELF);

         for(i1 = i - 3; i1 <= i + 3; ++i1) {
            this.func_150516_a(world, i1, j - sectionHeight + 1, k - 5, Blocks.field_150376_bx, 8);
            this.func_150516_a(world, i1, j - sectionHeight + 1, k + 5, Blocks.field_150376_bx, 8);
            if (random.nextBoolean()) {
               this.placeMug(world, random, i1, j - sectionHeight + 2, k + 5, 0, LOTRFoods.WOOD_ELF_DRINK);
            }

            if (Math.abs(i1 - i) > 1) {
               this.placeBarrel(world, random, i1, j - sectionHeight + 2, k - 5, 3, LOTRFoods.WOOD_ELF_DRINK);
            }
         }

         this.func_150516_a(world, i - 1, j - sectionHeight + 1, k - 5, LOTRMod.woodElvenTable, 0);
         this.func_150516_a(world, i + 1, j - sectionHeight + 1, k - 5, LOTRMod.woodElvenTable, 0);
         this.func_150516_a(world, i, j - sectionHeight + 1, k - 5, Blocks.field_150486_ae, 0);
         LOTRChestContents.fillChest(world, random, i, j - sectionHeight + 1, k - 5, LOTRChestContents.WOOD_ELF_HOUSE);

         for(i1 = i + 4; i1 <= i + 5; ++i1) {
            this.func_150516_a(world, i1, j - sectionHeight + 1, k - 3, Blocks.field_150476_ad, 3);
            this.func_150516_a(world, i1, j - sectionHeight + 1, k - 1, Blocks.field_150344_f, 0);
            this.placeMug(world, random, i1, j - sectionHeight + 2, k - 1, 0, LOTRFoods.WOOD_ELF_DRINK);
            this.func_150516_a(world, i1, j - sectionHeight + 1, k, Blocks.field_150376_bx, 8);
            this.placePlateWithCertainty(world, random, i1, j - sectionHeight + 2, k, this.plateBlock, LOTRFoods.ELF);
            this.func_150516_a(world, i1, j - sectionHeight + 1, k + 1, Blocks.field_150344_f, 0);
            this.placeMug(world, random, i1, j - sectionHeight + 2, k + 1, 2, LOTRFoods.WOOD_ELF_DRINK);
            this.func_150516_a(world, i1, j - sectionHeight + 1, k + 3, Blocks.field_150476_ad, 2);
         }

         this.func_150516_a(world, i + 4, j - sectionHeight + 1, k - 4, Blocks.field_150344_f, 0);
         this.func_150516_a(world, i + 4, j - sectionHeight + 1, k + 4, Blocks.field_150344_f, 0);

         for(i1 = j - sectionHeight - 6; i1 <= j - sectionHeight - 1; ++i1) {
            this.placeDungeonBlock(world, random, i - 6, i1, k);
            this.placeDungeonBlock(world, random, i - 5, i1, k - 2);
            this.placeDungeonBlock(world, random, i - 5, i1, k - 1);
            this.placeDungeonBlock(world, random, i - 5, i1, k + 1);
            this.placeDungeonBlock(world, random, i - 5, i1, k + 2);
            this.placeDungeonBlock(world, random, i - 4, i1, k - 3);
            this.placeDungeonBlock(world, random, i - 4, i1, k + 3);
            this.placeDungeonBlock(world, random, i - 3, i1, k - 5);
            this.placeDungeonBlock(world, random, i - 3, i1, k - 4);
            this.placeDungeonBlock(world, random, i - 3, i1, k + 4);
            this.placeDungeonBlock(world, random, i - 3, i1, k + 5);
            this.placeDungeonBlock(world, random, i - 2, i1, k - 6);
            this.placeDungeonBlock(world, random, i - 2, i1, k + 6);
            this.placeDungeonBlock(world, random, i - 1, i1, k - 6);
            this.placeDungeonBlock(world, random, i - 1, i1, k + 6);
            this.placeDungeonBlock(world, random, i, i1, k - 6);
            this.placeDungeonBlock(world, random, i, i1, k + 6);
            this.placeDungeonBlock(world, random, i + 1, i1, k - 5);
            this.placeDungeonBlock(world, random, i + 1, i1, k - 4);
            this.placeDungeonBlock(world, random, i + 1, i1, k + 4);
            this.placeDungeonBlock(world, random, i + 1, i1, k + 5);
            this.placeDungeonBlock(world, random, i + 2, i1, k - 3);
            this.placeDungeonBlock(world, random, i + 2, i1, k + 3);
            this.placeDungeonBlock(world, random, i + 3, i1, k - 2);
            this.placeDungeonBlock(world, random, i + 3, i1, k + 2);
            this.placeDungeonBlock(world, random, i + 4, i1, k - 2);
            this.placeDungeonBlock(world, random, i + 4, i1, k + 2);
            this.placeDungeonBlock(world, random, i + 5, i1, k - 1);
            this.placeDungeonBlock(world, random, i + 5, i1, k);
            this.placeDungeonBlock(world, random, i + 5, i1, k + 1);
            if (i1 != j - sectionHeight - 6 && i1 != j - sectionHeight - 1) {
               this.func_150516_a(world, i - 5, i1, k, Blocks.field_150350_a, 0);

               for(j1 = k - 2; j1 <= k + 2; ++j1) {
                  this.func_150516_a(world, i - 4, i1, j1, Blocks.field_150350_a, 0);
               }

               for(j1 = k - 3; j1 <= k + 3; ++j1) {
                  this.func_150516_a(world, i - 3, i1, j1, Blocks.field_150350_a, 0);
               }

               for(j1 = k - 5; j1 <= k + 5; ++j1) {
                  this.func_150516_a(world, i - 2, i1, j1, Blocks.field_150350_a, 0);
                  this.func_150516_a(world, i - 1, i1, j1, Blocks.field_150350_a, 0);
                  this.func_150516_a(world, i, i1, j1, Blocks.field_150350_a, 0);
               }

               for(j1 = k - 3; j1 <= k + 3; ++j1) {
                  this.func_150516_a(world, i + 1, i1, j1, Blocks.field_150350_a, 0);
               }

               for(j1 = k - 2; j1 <= k + 2; ++j1) {
                  this.func_150516_a(world, i + 2, i1, j1, Blocks.field_150350_a, 0);
               }

               for(j1 = k - 1; j1 <= k + 1; ++j1) {
                  this.func_150516_a(world, i + 3, i1, j1, Blocks.field_150350_a, 0);
                  this.func_150516_a(world, i + 4, i1, j1, Blocks.field_150350_a, 0);
               }
            } else {
               this.placeDungeonBlock(world, random, i - 5, i1, k);

               for(j1 = k - 2; j1 <= k + 2; ++j1) {
                  this.placeDungeonBlock(world, random, i - 4, i1, j1);
               }

               for(j1 = k - 3; j1 <= k + 3; ++j1) {
                  this.placeDungeonBlock(world, random, i - 3, i1, j1);
               }

               for(j1 = k - 5; j1 <= k + 5; ++j1) {
                  this.placeDungeonBlock(world, random, i - 2, i1, j1);
                  this.placeDungeonBlock(world, random, i - 1, i1, j1);
                  this.placeDungeonBlock(world, random, i, i1, j1);
               }

               for(j1 = k - 3; j1 <= k + 3; ++j1) {
                  this.placeDungeonBlock(world, random, i + 1, i1, j1);
               }

               for(j1 = k - 2; j1 <= k + 2; ++j1) {
                  this.placeDungeonBlock(world, random, i + 2, i1, j1);
               }

               for(j1 = k - 1; j1 <= k + 1; ++j1) {
                  this.placeDungeonBlock(world, random, i + 3, i1, j1);
                  this.placeDungeonBlock(world, random, i + 4, i1, j1);
               }
            }
         }

         for(i1 = i - 2; i1 <= i; ++i1) {
            this.placeDungeonBlock(world, random, i1, j - sectionHeight - 2, k - 5);
            this.placeDungeonBlock(world, random, i1, j - sectionHeight - 2, k - 4);
            this.placeDungeonBlock(world, random, i1, j - sectionHeight - 2, k + 4);
            this.placeDungeonBlock(world, random, i1, j - sectionHeight - 2, k + 5);
         }

         for(i1 = k - 1; i1 <= k + 1; ++i1) {
            this.placeDungeonBlock(world, random, i + 3, j - sectionHeight - 2, i1);
            this.placeDungeonBlock(world, random, i + 4, j - sectionHeight - 2, i1);
         }

         for(i1 = j - sectionHeight - 5; i1 <= j - sectionHeight - 3; ++i1) {
            for(j1 = i - 2; j1 <= i; ++j1) {
               this.func_150516_a(world, j1, i1, k - 4, LOTRMod.woodElfBars, 0);
               this.func_150516_a(world, j1, i1, k + 4, LOTRMod.woodElfBars, 0);
            }

            for(j1 = k - 1; j1 <= k + 1; ++j1) {
               this.func_150516_a(world, i + 3, i1, j1, LOTRMod.woodElfBars, 0);
            }
         }

         this.placePrisoner(world, random, i - 2, j - sectionHeight - 5, k - 5, 3, 1);
         this.placePrisoner(world, random, i - 2, j - sectionHeight - 5, k + 5, 3, 1);
         this.placePrisoner(world, random, i + 4, j - sectionHeight - 5, k - 1, 1, 3);
         this.func_150516_a(world, i - 4, j - sectionHeight - 3, k - 1, LOTRMod.woodElvenTorch, 1);
         this.func_150516_a(world, i - 4, j - sectionHeight - 3, k + 1, LOTRMod.woodElvenTorch, 1);

         for(i1 = j - sectionHeight - 5; i1 <= j - sectionHeight; ++i1) {
            this.func_150516_a(world, i - 5, i1, k, Blocks.field_150468_ap, 5);
         }

         this.func_150516_a(world, i - 5, j - sectionHeight + 1, k, LOTRMod.trapdoorGreenOak, 3);
         switch(rotation) {
         case 0:
            k1 = k - radius;

            for(i1 = i - 1; i1 <= i + 1; ++i1) {
               for(j1 = j + 1; j1 <= j + 3; ++j1) {
                  this.func_150516_a(world, i1, j1, k1, LOTRMod.gateWoodElven, 2);
               }
            }

            this.placeWallBanner(world, i, j + 6, k1, 2, LOTRItemBanner.BannerType.WOOD_ELF);
            break;
         case 1:
            i1 = i + radius;

            for(k1 = k - 1; k1 <= k + 1; ++k1) {
               for(j1 = j + 1; j1 <= j + 3; ++j1) {
                  this.func_150516_a(world, i1, j1, k1, LOTRMod.gateWoodElven, 5);
               }
            }

            this.placeWallBanner(world, i1, j + 6, k, 3, LOTRItemBanner.BannerType.WOOD_ELF);
            break;
         case 2:
            k1 = k + radius;

            for(i1 = i - 1; i1 <= i + 1; ++i1) {
               for(j1 = j + 1; j1 <= j + 3; ++j1) {
                  this.func_150516_a(world, i1, j1, k1, LOTRMod.gateWoodElven, 3);
               }
            }

            this.placeWallBanner(world, i, j + 6, k1, 0, LOTRItemBanner.BannerType.WOOD_ELF);
            break;
         case 3:
            i1 = i - radius;

            for(k1 = k - 1; k1 <= k + 1; ++k1) {
               for(j1 = j + 1; j1 <= j + 3; ++j1) {
                  this.func_150516_a(world, i1, j1, k1, LOTRMod.gateWoodElven, 4);
               }
            }

            this.placeWallBanner(world, i1, j + 6, k, 1, LOTRItemBanner.BannerType.WOOD_ELF);
         }

         LOTREntityWoodElfCaptain woodElfCaptain = new LOTREntityWoodElfCaptain(world);
         woodElfCaptain.func_70012_b((double)(i - 3) + 0.5D, (double)(topHeight + 1), (double)(k - 3) + 0.5D, world.field_73012_v.nextFloat() * 360.0F, 0.0F);
         woodElfCaptain.spawnRidingHorse = false;
         woodElfCaptain.func_110161_a((IEntityLivingData)null);
         woodElfCaptain.setPersistentAndTraderShouldRespawn();
         woodElfCaptain.func_110171_b(i, topHeight, k, 16);
         world.func_72838_d(woodElfCaptain);
         LOTREntityNPCRespawner respawner = new LOTREntityNPCRespawner(world);
         respawner.setSpawnClasses(LOTREntityWoodElfWarrior.class, LOTREntityWoodElfScout.class);
         respawner.setCheckRanges(12, -16, 40, 12);
         respawner.setSpawnRanges(5, 1, 40, 12);
         this.placeNPCRespawner(respawner, world, i, j, k);
         return true;
      }
   }

   private void placeDungeonBlock(World world, Random random, int i, int j, int k) {
      int l = random.nextInt(3);
      switch(l) {
      case 0:
         this.func_150516_a(world, i, j, k, LOTRMod.brick3, 5);
         break;
      case 1:
         this.func_150516_a(world, i, j, k, LOTRMod.brick3, 6);
         break;
      case 2:
         this.func_150516_a(world, i, j, k, LOTRMod.brick3, 7);
      }

   }

   private void placePrisoner(World world, Random random, int i, int j, int k, int xRange, int zRange) {
      i += random.nextInt(xRange);
      k += random.nextInt(zRange);
      if (random.nextInt(3) == 0) {
         Object npc;
         if (random.nextInt(10) == 0) {
            npc = new LOTREntityDwarf(world);
         } else if (random.nextBoolean()) {
            npc = new LOTREntityGundabadOrc(world);
         } else {
            npc = new LOTREntityDolGuldurOrc(world);
         }

         ((LOTREntityNPC)npc).func_70012_b((double)i + 0.5D, (double)j, (double)k + 0.5D, 0.0F, 0.0F);
         ((LOTREntityNPC)npc).spawnRidingHorse = false;
         ((LOTREntityNPC)npc).func_110161_a((IEntityLivingData)null);

         for(int l = 0; l < 5; ++l) {
            ((LOTREntityNPC)npc).func_70062_b(l, (ItemStack)null);
         }

         ((LOTREntityNPC)npc).npcItemsInv.setMeleeWeapon((ItemStack)null);
         ((LOTREntityNPC)npc).npcItemsInv.setMeleeWeaponMounted((ItemStack)null);
         ((LOTREntityNPC)npc).npcItemsInv.setRangedWeapon((ItemStack)null);
         ((LOTREntityNPC)npc).npcItemsInv.setSpearBackup((ItemStack)null);
         ((LOTREntityNPC)npc).npcItemsInv.setIdleItem((ItemStack)null);
         ((LOTREntityNPC)npc).npcItemsInv.setIdleItemMounted((ItemStack)null);
         ((LOTREntityNPC)npc).isNPCPersistent = true;
         world.func_72838_d((Entity)npc);
      } else {
         this.placeSkull(world, random, i, j, k);
      }

   }
}
