package lotr.common.world.structure2;

import java.util.Random;
import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTRNames;
import lotr.common.item.LOTRItemBanner;
import lotr.common.world.biome.LOTRBiome;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRWorldGenHobbitHole extends LOTRWorldGenHobbitStructure {
   public LOTRWorldGenHobbitHole(boolean flag) {
      super(flag);
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, 17);
      this.setupRandomBlocks(random);
      int radius = 16;
      int height = 7;
      int extraRadius = 2;
      int k1;
      int grassRadius;
      int grass;
      int flowers;
      int homeRadius;
      if (this.restrictions) {
         k1 = 0;
         grassRadius = 0;

         for(grass = -radius; grass <= radius; ++grass) {
            for(flowers = -radius; flowers <= radius; ++flowers) {
               if (grass * grass + flowers * flowers <= radius * radius) {
                  homeRadius = this.getTopBlock(world, grass, flowers) - 1;
                  if (!this.isSurface(world, grass, homeRadius, flowers)) {
                     return false;
                  }

                  if (homeRadius < k1) {
                     k1 = homeRadius;
                  }

                  if (homeRadius > grassRadius) {
                     grassRadius = homeRadius;
                  }
               }
            }
         }

         if (grassRadius - k1 > 8) {
            return false;
         }
      }

      for(k1 = -radius; k1 <= radius; ++k1) {
         for(grassRadius = -radius; grassRadius <= radius; ++grassRadius) {
            for(grass = height; grass >= 0; --grass) {
               homeRadius = grass + (radius - height);
               if (k1 * k1 + homeRadius * homeRadius + grassRadius * grassRadius < (radius + extraRadius) * (radius + extraRadius)) {
                  boolean grass = !this.isOpaque(world, k1, grass + 1, grassRadius);
                  this.setBlockAndMetadata(world, k1, grass, grassRadius, (Block)(grass ? Blocks.field_150349_c : Blocks.field_150346_d), 0);
                  this.setGrassToDirt(world, k1, grass - 1, grassRadius);
               }
            }
         }
      }

      boolean grass;
      for(k1 = -radius; k1 <= radius; ++k1) {
         for(grassRadius = -radius; grassRadius <= radius; ++grassRadius) {
            if (k1 * k1 + grassRadius * grassRadius < radius * radius) {
               for(grass = -1; !this.isOpaque(world, k1, grass, grassRadius) && this.getY(grass) >= 0; --grass) {
                  grass = !this.isOpaque(world, k1, grass + 1, grassRadius);
                  this.setBlockAndMetadata(world, k1, grass, grassRadius, (Block)(grass ? Blocks.field_150349_c : Blocks.field_150346_d), 0);
                  this.setGrassToDirt(world, k1, grass - 1, grassRadius);
               }
            }
         }
      }

      this.setGrassToDirt(world, 0, 7, 0);
      this.setBlockAndMetadata(world, 0, 8, 0, Blocks.field_150336_V, 0);
      this.setBlockAndMetadata(world, 0, 9, 0, Blocks.field_150336_V, 0);
      this.setBlockAndMetadata(world, 0, 10, 0, Blocks.field_150457_bL, 0);

      for(k1 = -16; k1 <= -13; ++k1) {
         for(grassRadius = 1; grassRadius <= 4; ++grassRadius) {
            for(grass = -3; grass <= 3; ++grass) {
               this.setAir(world, grass, grassRadius, k1);
            }
         }
      }

      for(k1 = 1; k1 <= 3; ++k1) {
         for(grassRadius = -2; grassRadius <= 2; ++grassRadius) {
            this.setAir(world, grassRadius, k1, -12);
         }
      }

      for(k1 = -17; k1 <= -13; ++k1) {
         for(grassRadius = -5; grassRadius <= 5; ++grassRadius) {
            for(grass = 0; grass == 0 || !this.isOpaque(world, grassRadius, grass, k1) && this.getY(grass) >= 0; --grass) {
               grass = grass == 0;
               this.setBlockAndMetadata(world, grassRadius, grass, k1, (Block)(grass ? Blocks.field_150349_c : Blocks.field_150346_d), 0);
            }

            for(grass = 1; grass <= 3; ++grass) {
               this.setAir(world, grassRadius, grass, k1);
            }
         }
      }

      for(k1 = -16; k1 <= -13; ++k1) {
         this.setBlockAndMetadata(world, 4, 1, k1, this.outFenceBlock, this.outFenceMeta);
         this.setBlockAndMetadata(world, -4, 1, k1, this.outFenceBlock, this.outFenceMeta);
         this.setBlockAndMetadata(world, 0, 0, k1, this.pathBlock, this.pathMeta);
      }

      for(k1 = -1; k1 <= 1; ++k1) {
         this.setBlockAndMetadata(world, k1, 0, -12, this.pathBlock, this.pathMeta);
         this.setBlockAndMetadata(world, k1, 0, -11, this.pathBlock, this.pathMeta);
      }

      for(k1 = -3; k1 <= 3; ++k1) {
         this.setBlockAndMetadata(world, k1, 1, -16, this.outFenceBlock, this.outFenceMeta);
      }

      this.setBlockAndMetadata(world, 0, 1, -16, this.outFenceGateBlock, 0);
      int[] signPos;
      if (random.nextInt(5) == 0) {
         String[] signLines = LOTRNames.getHobbitSign(random);
         signPos = new int[]{-3, -2, -1, 1, 2, 3};
         grass = signPos[random.nextInt(signPos.length)];
         flowers = MathHelper.func_76136_a(random, 6, 10) & 15;
         this.placeSign(world, grass, 2, -16, Blocks.field_150472_an, flowers, signLines);
      }

      for(k1 = -15; k1 <= -13; ++k1) {
         signPos = new int[]{-1, 1};
         grass = signPos.length;

         for(flowers = 0; flowers < grass; ++flowers) {
            homeRadius = signPos[flowers];
            int j1 = 1;
            this.plantFlower(world, random, homeRadius, j1, k1);
         }
      }

      if (random.nextInt(3) == 0) {
         for(k1 = -14; k1 <= -13; ++k1) {
            signPos = new int[]{-2, 2};
            grass = signPos.length;

            for(flowers = 0; flowers < grass; ++flowers) {
               homeRadius = signPos[flowers];
               this.setBlockAndMetadata(world, homeRadius, 1, k1, this.hedgeBlock, this.hedgeMeta);
            }
         }
      }

      for(k1 = -2; k1 <= 2; ++k1) {
         for(grassRadius = 1; grassRadius <= 3; ++grassRadius) {
            this.setBlockAndMetadata(world, k1, grassRadius, -10, Blocks.field_150336_V, 0);
         }
      }

      boolean gateFlip = random.nextBoolean();
      if (gateFlip) {
         for(grassRadius = 0; grassRadius <= 1; ++grassRadius) {
            this.setBlockAndMetadata(world, grassRadius, 0, -10, this.floorBlock, this.floorMeta);

            for(grass = 1; grass <= 2; ++grass) {
               this.setAir(world, grassRadius, grass, -11);
               this.setBlockAndMetadata(world, grassRadius, grass, -10, this.gateBlock, 2);
            }
         }

         this.setBlockAndMetadata(world, -2, 1, -11, this.plank2Block, this.plank2Meta);
         this.setBlockAndMetadata(world, -2, 2, -11, this.plank2Block, this.plank2Meta);
         this.setBlockAndMetadata(world, -2, 3, -11, this.plank2Block, this.plank2Meta);
         this.setBlockAndMetadata(world, -2, 1, -12, this.plank2StairBlock, 2);
         this.setBlockAndMetadata(world, -2, 3, -12, this.plank2StairBlock, 6);
         this.setBlockAndMetadata(world, -1, 3, -12, this.plank2StairBlock, 4);
         this.setBlockAndMetadata(world, -1, 1, -11, this.plank2StairBlock, 0);
         this.setBlockAndMetadata(world, -1, 2, -11, this.plank2StairBlock, 4);
         this.setBlockAndMetadata(world, -1, 3, -11, this.plank2Block, this.plank2Meta);
         this.setBlockAndMetadata(world, 0, 3, -11, this.plank2StairBlock, 4);
         this.setBlockAndMetadata(world, 0, 3, -12, this.plank2SlabBlock, this.plank2SlabMeta | 8);
         this.setBlockAndMetadata(world, 1, 3, -11, this.plank2StairBlock, 5);
         this.setBlockAndMetadata(world, 1, 3, -12, this.plank2SlabBlock, this.plank2SlabMeta | 8);
         this.setBlockAndMetadata(world, 2, 1, -11, this.plank2StairBlock, 1);
         this.setBlockAndMetadata(world, 2, 2, -11, this.plank2StairBlock, 5);
         this.setBlockAndMetadata(world, 2, 3, -11, this.plank2Block, this.plank2Meta);
         this.setBlockAndMetadata(world, 2, 1, -12, this.plank2StairBlock, 2);
         this.setBlockAndMetadata(world, 2, 3, -12, this.plank2StairBlock, 6);
         this.placeSign(world, -2, 2, -12, Blocks.field_150444_as, 2, new String[]{"", this.homeName1, this.homeName2, ""});
      } else {
         for(grassRadius = -1; grassRadius <= 0; ++grassRadius) {
            this.setBlockAndMetadata(world, grassRadius, 0, -10, this.floorBlock, this.floorMeta);

            for(grass = 1; grass <= 2; ++grass) {
               this.setAir(world, grassRadius, grass, -11);
               this.setBlockAndMetadata(world, grassRadius, grass, -10, this.gateBlock, 2);
            }
         }

         this.setBlockAndMetadata(world, 2, 1, -11, this.plank2Block, this.plank2Meta);
         this.setBlockAndMetadata(world, 2, 2, -11, this.plank2Block, this.plank2Meta);
         this.setBlockAndMetadata(world, 2, 3, -11, this.plank2Block, this.plank2Meta);
         this.setBlockAndMetadata(world, 2, 1, -12, this.plank2StairBlock, 2);
         this.setBlockAndMetadata(world, 2, 3, -12, this.plank2StairBlock, 6);
         this.setBlockAndMetadata(world, 1, 3, -12, this.plank2StairBlock, 5);
         this.setBlockAndMetadata(world, 1, 1, -11, this.plank2StairBlock, 1);
         this.setBlockAndMetadata(world, 1, 2, -11, this.plank2StairBlock, 5);
         this.setBlockAndMetadata(world, 1, 3, -11, this.plank2Block, this.plank2Meta);
         this.setBlockAndMetadata(world, 0, 3, -11, this.plank2StairBlock, 5);
         this.setBlockAndMetadata(world, 0, 3, -12, this.plank2SlabBlock, this.plank2SlabMeta | 8);
         this.setBlockAndMetadata(world, -1, 3, -11, this.plank2StairBlock, 4);
         this.setBlockAndMetadata(world, -1, 3, -12, this.plank2SlabBlock, this.plank2SlabMeta | 8);
         this.setBlockAndMetadata(world, -2, 1, -11, this.plank2StairBlock, 0);
         this.setBlockAndMetadata(world, -2, 2, -11, this.plank2StairBlock, 4);
         this.setBlockAndMetadata(world, -2, 3, -11, this.plank2Block, this.plank2Meta);
         this.setBlockAndMetadata(world, -2, 1, -12, this.plank2StairBlock, 2);
         this.setBlockAndMetadata(world, -2, 3, -12, this.plank2StairBlock, 6);
         this.placeSign(world, 2, 2, -12, Blocks.field_150444_as, 2, new String[]{"", this.homeName1, this.homeName2, ""});
      }

      for(grassRadius = 1; grassRadius <= 3; ++grassRadius) {
         this.setBlockAndMetadata(world, -3, grassRadius, -12, LOTRMod.woodBeamV1, 0);
         this.setBlockAndMetadata(world, 3, grassRadius, -12, LOTRMod.woodBeamV1, 0);
      }

      for(grassRadius = -3; grassRadius <= 3; ++grassRadius) {
         if (Math.abs(grassRadius) <= 1) {
            this.setBlockAndMetadata(world, grassRadius, 4, -13, LOTRMod.slabClayTileDyedSingle2, 5);
         } else {
            this.setBlockAndMetadata(world, grassRadius, 3, -13, LOTRMod.slabClayTileDyedSingle2, 13);
         }
      }

      this.setBlockAndMetadata(world, -4, 3, -13, LOTRMod.slabClayTileDyedSingle2, 5);
      this.setBlockAndMetadata(world, 4, 3, -13, LOTRMod.slabClayTileDyedSingle2, 5);

      for(grassRadius = -9; grassRadius <= 1; ++grassRadius) {
         for(grass = -2; grass <= 2; ++grass) {
            for(flowers = 1; flowers <= 3; ++flowers) {
               this.setAir(world, grass, flowers, grassRadius);
            }
         }

         this.setBlockAndMetadata(world, 1, 0, grassRadius, this.floorBlock, this.floorMeta);
         this.setBlockAndMetadata(world, 0, 0, grassRadius, this.plankBlock, this.plankMeta);
         this.setBlockAndMetadata(world, -1, 0, grassRadius, this.floorBlock, this.floorMeta);
         this.setBlockAndMetadata(world, 2, 1, grassRadius, this.plank2StairBlock, 1);
         this.setBlockAndMetadata(world, -2, 1, grassRadius, this.plank2StairBlock, 0);

         for(grass = 1; grass <= 3; ++grass) {
            this.setBlockAndMetadata(world, 3, grass, grassRadius, this.plank2Block, this.plank2Meta);
            this.setBlockAndMetadata(world, -3, grass, grassRadius, this.plank2Block, this.plank2Meta);
         }

         this.setBlockAndMetadata(world, 2, 3, grassRadius, this.plank2Block, this.plank2Meta);
         this.setBlockAndMetadata(world, -2, 3, grassRadius, this.plank2Block, this.plank2Meta);
         this.setBlockAndMetadata(world, 1, 3, grassRadius, this.plank2SlabBlock, this.plank2SlabMeta | 8);
         this.setBlockAndMetadata(world, -1, 3, grassRadius, this.plank2SlabBlock, this.plank2SlabMeta | 8);
         this.setBlockAndMetadata(world, 0, 4, grassRadius, this.plank2Block, this.plank2Meta);
      }

      signPos = new int[]{-8, -4, 0};
      grass = signPos.length;

      for(flowers = 0; flowers < grass; ++flowers) {
         homeRadius = signPos[flowers];
         this.setBlockAndMetadata(world, 0, 3, homeRadius, this.chandelierBlock, this.chandelierMeta);
      }

      for(grassRadius = 1; grassRadius <= 3; ++grassRadius) {
         for(grass = -2; grass <= 2; ++grass) {
            this.setBlockAndMetadata(world, grass, grassRadius, 2, this.plank2Block, this.plank2Meta);
         }
      }

      this.setBlockAndMetadata(world, 0, 0, 2, this.plankBlock, this.plankMeta);
      this.setAir(world, 0, 1, 2);
      this.setAir(world, 0, 2, 2);
      this.setBlockAndMetadata(world, -1, 1, 2, this.plank2StairBlock, 0);
      this.setBlockAndMetadata(world, 1, 1, 2, this.plank2StairBlock, 1);
      this.setBlockAndMetadata(world, -1, 2, 2, this.plank2StairBlock, 4);
      this.setBlockAndMetadata(world, 1, 2, 2, this.plank2StairBlock, 5);

      for(grassRadius = 3; grassRadius <= 9; ++grassRadius) {
         for(grass = -3; grass <= 3; ++grass) {
            for(flowers = 1; flowers <= 3; ++flowers) {
               this.setAir(world, grass, flowers, grassRadius);
            }

            this.setBlockAndMetadata(world, grass, 4, grassRadius, this.plank2Block, this.plank2Meta);
            this.setBlockAndMetadata(world, grass, 0, grassRadius, this.plankBlock, this.plankMeta);
         }
      }

      this.setBlockAndMetadata(world, 0, 3, 6, this.chandelierBlock, this.chandelierMeta);

      for(grassRadius = 5; grassRadius <= 7; ++grassRadius) {
         for(grass = -1; grass <= 1; ++grass) {
            this.setBlockAndMetadata(world, grass, 1, grassRadius, this.carpetBlock, this.carpetMeta);
         }
      }

      if (this.isWealthy && random.nextBoolean()) {
         this.placeChest(world, random, 0, 0, 6, 2, LOTRChestContents.HOBBIT_HOLE_TREASURE);
      }

      for(grassRadius = -3; grassRadius <= 3; ++grassRadius) {
         for(grass = 1; grass <= 3; ++grass) {
            this.setBlockAndMetadata(world, grassRadius, grass, 10, this.plank2Block, this.plank2Meta);
         }
      }

      for(grassRadius = -1; grassRadius <= 1; ++grassRadius) {
         this.setBlockAndMetadata(world, grassRadius, 2, 10, LOTRMod.glassPane, 0);
         this.setBlockAndMetadata(world, grassRadius, 3, 10, LOTRMod.glassPane, 0);

         for(grass = 11; grass <= 14; ++grass) {
            this.setBlockAndMetadata(world, grassRadius, 1, grass, Blocks.field_150349_c, 0);

            for(flowers = 2; flowers <= 3; ++flowers) {
               this.setAir(world, grassRadius, flowers, grass);
            }
         }

         this.setBlockAndMetadata(world, grassRadius, 4, 10, this.plank2Block, this.plank2Meta);
      }

      for(grassRadius = 1; grassRadius <= 3; ++grassRadius) {
         this.setBlockAndMetadata(world, -3, grassRadius, 3, this.plank2Block, this.plank2Meta);
         this.setBlockAndMetadata(world, 3, grassRadius, 3, this.plank2Block, this.plank2Meta);
      }

      for(grassRadius = -2; grassRadius <= 2; ++grassRadius) {
         this.setBlockAndMetadata(world, grassRadius, 3, 3, this.plank2SlabBlock, this.plank2SlabMeta | 8);
      }

      for(grassRadius = 4; grassRadius <= 9; ++grassRadius) {
         this.setBlockAndMetadata(world, -3, 3, grassRadius, this.plank2SlabBlock, this.plank2SlabMeta | 8);
         this.setBlockAndMetadata(world, 3, 3, grassRadius, this.plank2SlabBlock, this.plank2SlabMeta | 8);
      }

      for(grassRadius = -3; grassRadius <= 3; ++grassRadius) {
         this.setBlockAndMetadata(world, grassRadius, 1, 9, Blocks.field_150476_ad, 2);
      }

      this.setBlockAndMetadata(world, -3, 1, 8, Blocks.field_150476_ad, 0);
      this.setBlockAndMetadata(world, 3, 1, 8, Blocks.field_150476_ad, 1);

      for(grassRadius = 4; grassRadius <= 9; ++grassRadius) {
         for(grass = 1; grass <= 3; ++grass) {
            this.setBlockAndMetadata(world, -4, grass, grassRadius, this.plank2Block, this.plank2Meta);
            this.setBlockAndMetadata(world, 4, grass, grassRadius, this.plank2Block, this.plank2Meta);
         }
      }

      this.setAir(world, -4, 1, 6);
      this.setAir(world, -4, 2, 6);
      this.setBlockAndMetadata(world, -4, 1, 5, this.plank2StairBlock, 3);
      this.setBlockAndMetadata(world, -4, 1, 7, this.plank2StairBlock, 2);
      this.setBlockAndMetadata(world, -4, 2, 5, this.plank2StairBlock, 7);
      this.setBlockAndMetadata(world, -4, 2, 7, this.plank2StairBlock, 6);
      this.setAir(world, 4, 1, 6);
      this.setAir(world, 4, 2, 6);
      this.setBlockAndMetadata(world, 4, 1, 5, this.plank2StairBlock, 3);
      this.setBlockAndMetadata(world, 4, 1, 7, this.plank2StairBlock, 2);
      this.setBlockAndMetadata(world, 4, 2, 5, this.plank2StairBlock, 7);
      this.setBlockAndMetadata(world, 4, 2, 7, this.plank2StairBlock, 6);
      this.setBlockAndMetadata(world, -3, 2, 4, Blocks.field_150478_aa, 3);
      this.setBlockAndMetadata(world, 3, 2, 4, Blocks.field_150478_aa, 3);
      this.setBlockAndMetadata(world, -3, 2, 9, Blocks.field_150478_aa, 2);
      this.setBlockAndMetadata(world, 3, 2, 9, Blocks.field_150478_aa, 1);
      this.setAir(world, 2, 1, -6);
      this.setBlockAndMetadata(world, 2, 0, -6, this.floorBlock, this.floorMeta);
      this.setBlockAndMetadata(world, 3, 0, -6, this.floorBlock, this.floorMeta);
      this.setAir(world, 3, 1, -6);
      this.setAir(world, 3, 2, -6);
      this.setBlockAndMetadata(world, 3, 1, -7, this.plank2StairBlock, 3);
      this.setBlockAndMetadata(world, 3, 1, -5, this.plank2StairBlock, 2);
      this.setBlockAndMetadata(world, 3, 2, -7, this.plank2StairBlock, 7);
      this.setBlockAndMetadata(world, 3, 2, -5, this.plank2StairBlock, 6);

      for(grassRadius = -8; grassRadius <= -3; ++grassRadius) {
         for(grass = 4; grass <= 8; ++grass) {
            if (grass != 8 || grassRadius != -8) {
               for(flowers = 1; flowers <= 3; ++flowers) {
                  this.setAir(world, grass, flowers, grassRadius);
               }

               this.setBlockAndMetadata(world, grass, 0, grassRadius, this.floorBlock, this.floorMeta);
               if (grass < 7 || grassRadius > -7) {
                  this.setBlockAndMetadata(world, grass, 4, grassRadius, this.plank2Block, this.plank2Meta);
               }
            }
         }
      }

      for(grassRadius = 4; grassRadius <= 7; ++grassRadius) {
         for(grass = 1; grass <= 3; ++grass) {
            this.setBlockAndMetadata(world, grassRadius, grass, -2, this.plank2Block, this.plank2Meta);
            this.setBlockAndMetadata(world, grassRadius, grass, -8, this.plank2Block, this.plank2Meta);
         }

         this.setBlockAndMetadata(world, grassRadius, 3, -7, this.plank2SlabBlock, this.plank2SlabMeta | 8);
         this.setBlockAndMetadata(world, grassRadius, 3, -3, this.plank2SlabBlock, this.plank2SlabMeta | 8);
      }

      for(grassRadius = -7; grassRadius <= -3; ++grassRadius) {
         for(grass = 1; grass <= 3; ++grass) {
            this.setBlockAndMetadata(world, 8, grass, grassRadius, this.plank2Block, this.plank2Meta);
         }
      }

      for(grassRadius = 1; grassRadius <= 2; ++grassRadius) {
         for(grass = 5; grass <= 6; ++grass) {
            this.setAir(world, grass, grassRadius, -8);
            this.setBlockAndMetadata(world, grass, grassRadius, -9, Blocks.field_150342_X, 0);
         }

         for(grass = -6; grass <= -4; ++grass) {
            this.setAir(world, 8, grassRadius, grass);
            this.setBlockAndMetadata(world, 9, grassRadius, grass, Blocks.field_150342_X, 0);
         }
      }

      this.setBlockAndMetadata(world, 6, 3, -5, this.chandelierBlock, this.chandelierMeta);
      this.setBlockAndMetadata(world, 5, 1, -5, Blocks.field_150476_ad, 3);
      this.setBlockAndMetadata(world, 5, 1, -3, Blocks.field_150376_bx, 8);
      this.placeChest(world, random, 7, 1, -3, 2, LOTRChestContents.HOBBIT_HOLE_STUDY);
      if (random.nextBoolean()) {
         this.placeWallBanner(world, 3, 3, -4, LOTRItemBanner.BannerType.HOBBIT, 1);
      }

      this.setAir(world, -2, 1, -6);
      this.setBlockAndMetadata(world, -2, 0, -6, this.floorBlock, this.floorMeta);
      this.setBlockAndMetadata(world, -3, 0, -6, this.floorBlock, this.floorMeta);
      this.setAir(world, -3, 1, -6);
      this.setAir(world, -3, 2, -6);
      this.setBlockAndMetadata(world, -3, 1, -7, this.plank2StairBlock, 3);
      this.setBlockAndMetadata(world, -3, 1, -5, this.plank2StairBlock, 2);
      this.setBlockAndMetadata(world, -3, 2, -7, this.plank2StairBlock, 7);
      this.setBlockAndMetadata(world, -3, 2, -5, this.plank2StairBlock, 6);

      for(grassRadius = -7; grassRadius <= -4; ++grassRadius) {
         for(grass = -4; grass >= -7; --grass) {
            this.setBlockAndMetadata(world, grass, 0, grassRadius, this.floorBlock, this.floorMeta);

            for(flowers = 1; flowers <= 3; ++flowers) {
               this.setAir(world, grass, flowers, grassRadius);
            }

            this.setBlockAndMetadata(world, grass, 4, grassRadius, this.plank2Block, this.plank2Meta);
         }
      }

      for(grassRadius = -4; grassRadius >= -7; --grassRadius) {
         for(grass = 1; grass <= 3; ++grass) {
            this.setBlockAndMetadata(world, grassRadius, grass, -8, this.plank2Block, this.plank2Meta);
            this.setBlockAndMetadata(world, grassRadius, grass, -3, this.plank2Block, this.plank2Meta);
         }

         this.setBlockAndMetadata(world, grassRadius, 3, -7, this.plank2SlabBlock, this.plank2SlabMeta | 8);
         this.setBlockAndMetadata(world, grassRadius, 3, -4, this.plank2SlabBlock, this.plank2SlabMeta | 8);
      }

      for(grassRadius = -7; grassRadius <= -3; ++grassRadius) {
         for(grass = 1; grass <= 3; ++grass) {
            this.setBlockAndMetadata(world, -8, grass, grassRadius, this.plank2Block, this.plank2Meta);
         }
      }

      for(grassRadius = -7; grassRadius <= -6; ++grassRadius) {
         for(grass = -5; grass >= -6; --grass) {
            this.setBlockAndMetadata(world, grass, 1, grassRadius, this.carpetBlock, this.carpetMeta);
         }
      }

      for(grassRadius = -5; grassRadius >= -6; --grassRadius) {
         this.setBlockAndMetadata(world, grassRadius, 0, -8, this.floorBlock, this.floorMeta);
         this.setBlockAndMetadata(world, grassRadius, 1, -8, Blocks.field_150376_bx, 8);
         this.setBlockAndMetadata(world, grassRadius, 2, -8, Blocks.field_150342_X, 0);
         this.setBlockAndMetadata(world, grassRadius, 1, -9, this.plank2Block, this.plank2Meta);
      }

      this.setBlockAndMetadata(world, -4, 1, -4, Blocks.field_150344_f, 0);
      this.setBlockAndMetadata(world, -7, 1, -4, Blocks.field_150344_f, 0);
      this.setBlockAndMetadata(world, -4, 2, -4, Blocks.field_150478_aa, 5);
      this.setBlockAndMetadata(world, -7, 2, -4, Blocks.field_150478_aa, 5);
      this.setBlockAndMetadata(world, -5, 1, -5, this.bedBlock, 0);
      this.setBlockAndMetadata(world, -5, 1, -4, this.bedBlock, 8);
      this.setBlockAndMetadata(world, -6, 1, -5, this.bedBlock, 0);
      this.setBlockAndMetadata(world, -6, 1, -4, this.bedBlock, 8);
      this.spawnItemFrame(world, -8, 2, -6, 1, new ItemStack(Items.field_151113_aN));
      this.setBlockAndMetadata(world, 4, 0, 6, this.plankBlock, this.plankMeta);

      for(grassRadius = 5; grassRadius <= 6; ++grassRadius) {
         this.setBlockAndMetadata(world, grassRadius, 0, 7, this.floorBlock, this.floorMeta);

         for(grass = 1; grass <= 3; ++grass) {
            this.setAir(world, grassRadius, grass, 7);
         }

         this.setBlockAndMetadata(world, grassRadius, 4, 7, this.plank2Block, this.plank2Meta);
      }

      for(grassRadius = 5; grassRadius <= 7; ++grassRadius) {
         this.setBlockAndMetadata(world, grassRadius, 0, 6, this.floorBlock, this.floorMeta);

         for(grass = 1; grass <= 3; ++grass) {
            this.setAir(world, grassRadius, grass, 6);
         }

         this.setBlockAndMetadata(world, grassRadius, 4, 6, this.plank2Block, this.plank2Meta);
      }

      for(grassRadius = 5; grassRadius <= 8; ++grassRadius) {
         this.setBlockAndMetadata(world, grassRadius, 0, 5, this.floorBlock, this.floorMeta);

         for(grass = 1; grass <= 3; ++grass) {
            this.setAir(world, grassRadius, grass, 5);
         }

         this.setBlockAndMetadata(world, grassRadius, 4, 5, this.plank2Block, this.plank2Meta);
      }

      for(grassRadius = 1; grassRadius <= 3; ++grassRadius) {
         this.setBlockAndMetadata(world, 7, grassRadius, 7, this.plank2Block, this.plank2Meta);
         this.setBlockAndMetadata(world, 8, grassRadius, 6, this.plank2Block, this.plank2Meta);
         this.setBlockAndMetadata(world, 9, grassRadius, 5, this.plank2Block, this.plank2Meta);
      }

      this.setBlockAndMetadata(world, 7, 2, 6, Blocks.field_150478_aa, 4);
      this.setBlockAndMetadata(world, 8, 2, 5, Blocks.field_150478_aa, 1);

      for(grassRadius = 4; grassRadius >= -1; --grassRadius) {
         for(grass = 4; grass <= 9; ++grass) {
            this.setBlockAndMetadata(world, grass, 0, grassRadius, this.floorBlock, this.floorMeta);

            for(flowers = 1; flowers <= 3; ++flowers) {
               this.setAir(world, grass, flowers, grassRadius);
            }

            this.setBlockAndMetadata(world, grass, 4, grassRadius, this.plank2Block, this.plank2Meta);
         }

         for(grass = 1; grass <= 3; ++grass) {
            this.setBlockAndMetadata(world, 3, grass, grassRadius, this.plank2Block, this.plank2Meta);
            this.setBlockAndMetadata(world, 10, grass, grassRadius, this.plank2Block, this.plank2Meta);
         }

         this.setBlockAndMetadata(world, 4, 3, grassRadius, this.plank2SlabBlock, this.plank2SlabMeta | 8);
         this.setBlockAndMetadata(world, 9, 3, grassRadius, this.plank2SlabBlock, this.plank2SlabMeta | 8);
      }

      for(grassRadius = 2; grassRadius >= 0; --grassRadius) {
         this.setBlockAndMetadata(world, 4, 1, grassRadius, Blocks.field_150476_ad, 0);
         this.setBlockAndMetadata(world, 9, 1, grassRadius, Blocks.field_150476_ad, 1);
      }

      this.setBlockAndMetadata(world, 4, 1, -1, Blocks.field_150476_ad, 3);
      this.setBlockAndMetadata(world, 4, 1, 3, Blocks.field_150476_ad, 2);
      this.setBlockAndMetadata(world, 9, 1, -1, Blocks.field_150476_ad, 3);
      this.setBlockAndMetadata(world, 9, 1, 3, Blocks.field_150476_ad, 2);
      this.setBlockAndMetadata(world, 6, 3, 1, this.chandelierBlock, this.chandelierMeta);
      this.setBlockAndMetadata(world, 7, 3, 1, this.chandelierBlock, this.chandelierMeta);
      this.setBlockAndMetadata(world, 6, 1, 2, Blocks.field_150344_f, 1);
      this.setBlockAndMetadata(world, 7, 1, 2, Blocks.field_150344_f, 1);
      this.setBlockAndMetadata(world, 6, 1, 1, Blocks.field_150376_bx, 9);
      this.setBlockAndMetadata(world, 7, 1, 1, Blocks.field_150376_bx, 9);
      this.setBlockAndMetadata(world, 6, 1, 0, Blocks.field_150344_f, 1);
      this.setBlockAndMetadata(world, 7, 1, 0, Blocks.field_150344_f, 1);

      for(grassRadius = 6; grassRadius <= 7; ++grassRadius) {
         for(grass = 2; grass >= 0; --grass) {
            if (random.nextInt(3) == 0) {
               this.placeMug(world, random, grassRadius, 2, grass, random.nextInt(4), LOTRFoods.HOBBIT_DRINK);
            } else if (random.nextBoolean()) {
               this.placePlateWithCertainty(world, random, grassRadius, 2, grass, this.plateBlock, LOTRFoods.HOBBIT);
            } else {
               this.placePlate(world, random, grassRadius, 2, grass, this.plateBlock, LOTRFoods.HOBBIT);
            }
         }
      }

      this.setBlockAndMetadata(world, 5, 3, 7, this.plank2SlabBlock, this.plank2SlabMeta | 8);
      this.setBlockAndMetadata(world, 6, 3, 7, this.plank2SlabBlock, this.plank2SlabMeta | 8);
      this.setBlockAndMetadata(world, 7, 3, 6, this.plank2SlabBlock, this.plank2SlabMeta | 8);
      this.setBlockAndMetadata(world, 8, 3, 5, this.plank2SlabBlock, this.plank2SlabMeta | 8);

      for(grassRadius = 1; grassRadius <= 3; ++grassRadius) {
         for(grass = 5; grass <= 6; ++grass) {
            this.setBlockAndMetadata(world, grass, grassRadius, 8, this.plank2Block, this.plank2Meta);
         }

         for(grass = 8; grass <= 9; ++grass) {
            this.setBlockAndMetadata(world, grass, grassRadius, -2, this.plank2Block, this.plank2Meta);
         }
      }

      this.setBlockAndMetadata(world, -4, 0, 6, this.plankBlock, this.plankMeta);

      for(grassRadius = 7; grassRadius >= 3; --grassRadius) {
         for(grass = -5; grass >= -7; --grass) {
            this.setBlockAndMetadata(world, grass, 0, grassRadius, Blocks.field_150334_T, 0);

            for(flowers = 1; flowers <= 3; ++flowers) {
               this.setAir(world, grass, flowers, grassRadius);
            }

            this.setBlockAndMetadata(world, grass, 4, grassRadius, this.plank2Block, this.plank2Meta);
         }
      }

      for(grassRadius = 6; grassRadius >= 3; --grassRadius) {
         for(grass = -5; grass >= -6; --grass) {
            this.setBlockAndMetadata(world, grass, 0, grassRadius, this.floorBlock, this.floorMeta);
         }
      }

      this.setBlockAndMetadata(world, -5, 1, 8, Blocks.field_150462_ai, 0);
      this.setBlockAndMetadata(world, -6, 1, 8, Blocks.field_150462_ai, 0);
      this.setBlockAndMetadata(world, -7, 1, 8, this.tableBlock, 0);

      for(grassRadius = -7; grassRadius <= -5; ++grassRadius) {
         this.setAir(world, grassRadius, 2, 8);
         this.setBlockAndMetadata(world, grassRadius, 2, 9, this.plank2Block, this.plank2Meta);
         this.setBlockAndMetadata(world, grassRadius, 3, 8, Blocks.field_150334_T, 0);
      }

      this.setBlockAndMetadata(world, -8, 1, 8, this.plank2Block, this.plank2Meta);
      this.setBlockAndMetadata(world, -8, 2, 8, this.plank2Block, this.plank2Meta);

      for(grassRadius = 6; grassRadius <= 7; ++grassRadius) {
         for(grass = 1; grass <= 3; ++grass) {
            this.setBlockAndMetadata(world, -8, grass, grassRadius, this.plank2Block, this.plank2Meta);
         }
      }

      for(grassRadius = 3; grassRadius <= 5; ++grassRadius) {
         this.setBlockAndMetadata(world, -8, 0, grassRadius, Blocks.field_150334_T, 0);
         this.setAir(world, -8, 2, grassRadius);
         this.setBlockAndMetadata(world, -9, 2, grassRadius, this.plank2Block, this.plank2Meta);
         this.setBlockAndMetadata(world, -8, 3, grassRadius, Blocks.field_150334_T, 0);
      }

      this.setBlockAndMetadata(world, -8, 1, 4, this.plank2Block, this.plank2Meta);
      this.setBlockAndMetadata(world, -8, 1, 5, LOTRMod.hobbitOven, 4);
      this.setBlockAndMetadata(world, -8, 1, 3, LOTRMod.hobbitOven, 4);
      this.setBlockAndMetadata(world, -8, 1, 4, Blocks.field_150383_bp, 3);
      this.setBlockAndMetadata(world, -6, 3, 5, this.chandelierBlock, this.chandelierMeta);

      for(grassRadius = -4; grassRadius >= -9; --grassRadius) {
         for(grass = 1; grass <= 3; ++grass) {
            this.setBlockAndMetadata(world, grassRadius, grass, 2, this.plank2Block, this.plank2Meta);
         }
      }

      this.setBlockAndMetadata(world, -6, 0, 2, this.plankBlock, this.plankMeta);
      this.setAir(world, -6, 1, 2);
      this.setAir(world, -6, 2, 2);
      this.setBlockAndMetadata(world, -7, 1, 2, this.plank2StairBlock, 0);
      this.setBlockAndMetadata(world, -5, 1, 2, this.plank2StairBlock, 1);
      this.setBlockAndMetadata(world, -7, 2, 2, this.plank2StairBlock, 4);
      this.setBlockAndMetadata(world, -5, 2, 2, this.plank2StairBlock, 5);

      for(grassRadius = -2; grassRadius <= 1; ++grassRadius) {
         for(grass = -9; grass <= -4; ++grass) {
            this.setBlockAndMetadata(world, grass, 0, grassRadius, this.plankBlock, this.plankMeta);

            for(flowers = 1; flowers <= 3; ++flowers) {
               this.setAir(world, grass, flowers, grassRadius);
            }

            this.setBlockAndMetadata(world, grass, 4, grassRadius, Blocks.field_150334_T, 0);
         }

         for(grass = 1; grass <= 3; ++grass) {
            this.setBlockAndMetadata(world, -10, grass, grassRadius, this.plank2Block, this.plank2Meta);
         }
      }

      for(grassRadius = -9; grassRadius <= -4; ++grassRadius) {
         this.setBlockAndMetadata(world, grassRadius, 1, -2, Blocks.field_150376_bx, 8);
         this.setBlockAndMetadata(world, grassRadius, 3, -2, Blocks.field_150376_bx, 0);
      }

      for(grassRadius = -2; grassRadius <= 1; ++grassRadius) {
         this.setBlockAndMetadata(world, -4, 1, grassRadius, Blocks.field_150376_bx, 8);
         this.setBlockAndMetadata(world, -4, 3, grassRadius, Blocks.field_150376_bx, 0);
         this.setBlockAndMetadata(world, -9, 1, grassRadius, Blocks.field_150376_bx, 8);
         this.setBlockAndMetadata(world, -9, 3, grassRadius, Blocks.field_150376_bx, 0);
      }

      this.setBlockAndMetadata(world, -8, 1, 1, Blocks.field_150376_bx, 8);
      this.setBlockAndMetadata(world, -8, 3, 1, Blocks.field_150376_bx, 0);
      this.setBlockAndMetadata(world, -6, 3, 1, Blocks.field_150478_aa, 4);

      for(grassRadius = -2; grassRadius <= 1; ++grassRadius) {
         if (random.nextInt(3) != 0) {
            Block cakeBlock = LOTRWorldGenHobbitStructure.getRandomCakeBlock(random);
            this.setBlockAndMetadata(world, -4, 2, grassRadius, cakeBlock, 0);
         }
      }

      for(grassRadius = -7; grassRadius <= -6; ++grassRadius) {
         this.placePlateWithCertainty(world, random, grassRadius, 2, -2, this.plateBlock, LOTRFoods.HOBBIT);
      }

      this.placeBarrel(world, random, -5, 2, -2, 3, LOTRFoods.HOBBIT_DRINK);

      for(grassRadius = 1; grassRadius <= 3; ++grassRadius) {
         this.setBlockAndMetadata(world, -9, grassRadius, -3, this.plank2Block, this.plank2Meta);
         this.setBlockAndMetadata(world, -4, grassRadius, 3, this.plank2Block, this.plank2Meta);
      }

      this.placeChest(world, random, -8, 2, -2, Blocks.field_150486_ae, 3, LOTRChestContents.HOBBIT_HOLE_LARDER);
      this.placeChest(world, random, -9, 2, -1, Blocks.field_150486_ae, 4, LOTRChestContents.HOBBIT_HOLE_LARDER);
      this.placeChest(world, random, -9, 2, 0, Blocks.field_150486_ae, 4, LOTRChestContents.HOBBIT_HOLE_LARDER);
      this.placeChest(world, random, -8, 2, 1, Blocks.field_150486_ae, 2, LOTRChestContents.HOBBIT_HOLE_LARDER);
      if (gateFlip) {
         this.setBlockAndMetadata(world, -1, 2, -9, Blocks.field_150479_bC, 0);
      } else {
         this.setBlockAndMetadata(world, 1, 2, -9, Blocks.field_150479_bC, 0);
      }

      grassRadius = radius - 3;
      grass = MathHelper.func_76136_a(random, 80, 120);

      int k1;
      int j1;
      for(flowers = 0; flowers < grass; ++flowers) {
         homeRadius = MathHelper.func_76136_a(random, -grassRadius, grassRadius);
         k1 = MathHelper.func_76136_a(random, -grassRadius, grassRadius);
         j1 = this.getTopBlock(world, homeRadius, k1);
         this.plantTallGrass(world, random, homeRadius, j1, k1);
      }

      flowers = MathHelper.func_76136_a(random, 8, 16);

      for(homeRadius = 0; homeRadius < flowers; ++homeRadius) {
         k1 = MathHelper.func_76136_a(random, -grassRadius, grassRadius);
         j1 = MathHelper.func_76136_a(random, -grassRadius, grassRadius);
         int j1 = this.getTopBlock(world, k1, j1);
         this.plantFlower(world, random, k1, j1, j1);
      }

      if (random.nextInt(4) == 0) {
         homeRadius = MathHelper.func_76136_a(random, -grassRadius, grassRadius);
         k1 = MathHelper.func_76136_a(random, -grassRadius, grassRadius);
         j1 = this.getTopBlock(world, homeRadius, k1);
         WorldGenerator treeGen = LOTRBiome.shire.func_150567_a(random);
         treeGen.func_76484_a(world, random, this.getX(homeRadius, k1), this.getY(j1), this.getZ(homeRadius, k1));
      }

      homeRadius = MathHelper.func_76128_c((double)radius * 1.5D);
      this.spawnHobbitCouple(world, 0, 1, 0, homeRadius);
      return true;
   }
}
