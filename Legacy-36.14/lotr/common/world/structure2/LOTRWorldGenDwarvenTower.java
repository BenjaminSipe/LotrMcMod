package lotr.common.world.structure2;

import java.util.Random;
import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.npc.LOTREntityDwarf;
import lotr.common.entity.npc.LOTREntityDwarfAxeThrower;
import lotr.common.entity.npc.LOTREntityDwarfCommander;
import lotr.common.entity.npc.LOTREntityDwarfWarrior;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.item.LOTRItemBanner;
import lotr.common.tileentity.LOTRTileEntityAlloyForge;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityCreature;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class LOTRWorldGenDwarvenTower extends LOTRWorldGenStructureBase2 {
   protected Block brickBlock;
   protected int brickMeta;
   protected Block brickSlabBlock;
   protected int brickSlabMeta;
   protected Block brickStairBlock;
   protected Block brickWallBlock;
   protected int brickWallMeta;
   protected Block pillarBlock;
   protected int pillarMeta;
   protected Block plankBlock;
   protected int plankMeta;
   protected Block plankSlabBlock;
   protected int plankSlabMeta;
   protected Block barsBlock;
   protected Block gateBlock;
   protected Block tableBlock;
   protected Block forgeBlock;
   protected Block glowBrickBlock;
   protected int glowBrickMeta;
   protected Block plateBlock;
   protected LOTRItemBanner.BannerType bannerType;
   protected LOTRChestContents chestContents;
   protected boolean ruined = false;

   public LOTRWorldGenDwarvenTower(boolean flag) {
      super(flag);
      this.brickBlock = LOTRMod.brick;
      this.brickMeta = 6;
      this.brickSlabBlock = LOTRMod.slabSingle;
      this.brickSlabMeta = 7;
      this.brickStairBlock = LOTRMod.stairsDwarvenBrick;
      this.brickWallBlock = LOTRMod.wall;
      this.brickWallMeta = 7;
      this.pillarBlock = LOTRMod.pillar;
      this.pillarMeta = 0;
      this.barsBlock = LOTRMod.dwarfBars;
      this.gateBlock = LOTRMod.gateDwarven;
      this.tableBlock = LOTRMod.dwarvenTable;
      this.forgeBlock = LOTRMod.dwarvenForge;
      this.glowBrickBlock = LOTRMod.brick3;
      this.glowBrickMeta = 12;
      this.bannerType = LOTRItemBanner.BannerType.DWARF;
      this.chestContents = LOTRChestContents.DWARVEN_TOWER;
   }

   protected LOTREntityNPC getCommanderNPC(World world) {
      return new LOTREntityDwarfCommander(world);
   }

   protected void setupRandomBlocks(Random random) {
      int randomWood = random.nextInt(4);
      if (randomWood == 0) {
         this.plankBlock = Blocks.field_150344_f;
         this.plankMeta = 1;
         this.plankSlabBlock = Blocks.field_150376_bx;
         this.plankSlabMeta = 1;
      } else if (randomWood == 1) {
         this.plankBlock = LOTRMod.planks;
         this.plankMeta = 13;
         this.plankSlabBlock = LOTRMod.woodSlabSingle2;
         this.plankSlabMeta = 5;
      } else if (randomWood == 2) {
         this.plankBlock = LOTRMod.planks2;
         this.plankMeta = 4;
         this.plankSlabBlock = LOTRMod.woodSlabSingle3;
         this.plankSlabMeta = 4;
      } else if (randomWood == 3) {
         this.plankBlock = LOTRMod.planks2;
         this.plankMeta = 3;
         this.plankSlabBlock = LOTRMod.woodSlabSingle3;
         this.plankSlabMeta = 3;
      }

      if (random.nextBoolean()) {
         this.plateBlock = LOTRMod.ceramicPlateBlock;
      } else {
         this.plateBlock = LOTRMod.woodPlateBlock;
      }

   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, 6);
      this.setupRandomBlocks(random);
      int sections = 5 + random.nextInt(3);
      int i1;
      int sectionBase;
      int j1;
      if (this.restrictions) {
         for(i1 = -6; i1 <= 6; ++i1) {
            for(sectionBase = -6; sectionBase <= 6; ++sectionBase) {
               j1 = this.getTopBlock(world, i1, sectionBase);
               Block block = this.getBlock(world, i1, j1 - 1, sectionBase);
               if (block != Blocks.field_150349_c && block != Blocks.field_150348_b && block != Blocks.field_150433_aE) {
                  return false;
               }
            }
         }
      }

      for(i1 = -5; i1 <= 5; ++i1) {
         for(sectionBase = -5; sectionBase <= 5; ++sectionBase) {
            for(j1 = 0; (j1 == 0 || !this.isOpaque(world, i1, j1, sectionBase)) && this.getY(j1) >= 0; --j1) {
               this.placeBrick(world, random, i1, j1, sectionBase);
               this.setGrassToDirt(world, i1, j1 - 1, sectionBase);
            }
         }
      }

      for(i1 = -4; i1 <= 4; ++i1) {
         for(sectionBase = -4; sectionBase <= 4; ++sectionBase) {
            boolean flag = true;
            if (this.ruined) {
               flag = random.nextInt(12) != 0;
            }

            if (flag) {
               this.setBlockAndMetadata(world, i1, 0, sectionBase, this.plankBlock, this.plankMeta);
            }
         }
      }

      int i1;
      int k1;
      for(i1 = 0; i1 <= sections; ++i1) {
         sectionBase = i1 * 5;

         for(j1 = -4; j1 <= 4; ++j1) {
            for(k1 = sectionBase + 1; k1 <= sectionBase + 5; ++k1) {
               for(i1 = -4; i1 <= 4; ++i1) {
                  this.setAir(world, j1, k1, i1);
                  this.setAir(world, j1, k1, i1);
               }
            }
         }

         int k2;
         int j1;
         for(j1 = sectionBase + 1; j1 <= sectionBase + 5; ++j1) {
            int i1;
            boolean flag;
            int[] var19;
            for(k1 = -5; k1 <= 5; ++k1) {
               var19 = new int[]{-5, 5};
               k2 = var19.length;

               for(j1 = 0; j1 < k2; ++j1) {
                  i1 = var19[j1];
                  flag = true;
                  if (this.ruined) {
                     flag = random.nextInt(20) != 0;
                  }

                  if (flag) {
                     this.placeBrick(world, random, k1, j1, i1);
                  }
               }
            }

            for(k1 = -4; k1 <= 4; ++k1) {
               var19 = new int[]{-5, 5};
               k2 = var19.length;

               for(j1 = 0; j1 < k2; ++j1) {
                  i1 = var19[j1];
                  flag = true;
                  if (this.ruined) {
                     flag = random.nextInt(20) != 0;
                  }

                  if (flag) {
                     this.placeBrick(world, random, i1, j1, k1);
                  }
               }
            }
         }

         this.placePillar(world, random, -4, sectionBase + 1, -4);
         this.placePillar(world, random, -4, sectionBase + 2, -4);
         this.setBlockAndMetadata(world, -4, sectionBase + 3, -4, this.glowBrickBlock, this.glowBrickMeta);
         this.placePillar(world, random, -4, sectionBase + 4, -4);
         this.placePillar(world, random, -4, sectionBase + 1, 4);
         this.placePillar(world, random, -4, sectionBase + 2, 4);
         this.setBlockAndMetadata(world, -4, sectionBase + 3, 4, this.glowBrickBlock, this.glowBrickMeta);
         this.placePillar(world, random, -4, sectionBase + 4, 4);
         this.placePillar(world, random, 4, sectionBase + 1, -4);
         this.placePillar(world, random, 4, sectionBase + 2, -4);
         this.setBlockAndMetadata(world, 4, sectionBase + 3, -4, this.glowBrickBlock, this.glowBrickMeta);
         this.placePillar(world, random, 4, sectionBase + 4, -4);
         this.placePillar(world, random, 4, sectionBase + 1, 4);
         this.placePillar(world, random, 4, sectionBase + 2, 4);
         this.setBlockAndMetadata(world, 4, sectionBase + 3, 4, this.glowBrickBlock, this.glowBrickMeta);
         this.placePillar(world, random, 4, sectionBase + 4, 4);

         for(j1 = -4; j1 <= 4; ++j1) {
            for(k1 = -4; k1 <= 4; ++k1) {
               boolean flag = true;
               if (this.ruined) {
                  flag = random.nextInt(12) != 0;
               }

               if (flag) {
                  this.setBlockAndMetadata(world, j1, sectionBase + 5, k1, this.plankBlock, this.plankMeta);
               }
            }
         }

         for(j1 = -2; j1 <= 2; ++j1) {
            for(k1 = sectionBase + 1; k1 <= sectionBase + 4; ++k1) {
               if (Math.abs(j1) >= 2 || k1 != sectionBase + 2 && k1 != sectionBase + 3) {
                  this.placePillar(world, random, -5, k1, j1);
                  this.placePillar(world, random, 5, k1, j1);
               } else {
                  this.setBlockAndMetadata(world, -5, k1, j1, this.barsBlock, 0);
                  this.setBlockAndMetadata(world, 5, k1, j1, this.barsBlock, 0);
               }
            }
         }

         j1 = random.nextInt(5);
         if (i1 % 2 == 0) {
            k1 = -1;

            while(true) {
               if (k1 > 4) {
                  this.placeRandomFeature(world, random, -2, sectionBase + 1, 4, j1, false);
                  this.placeRandomFeature(world, random, -1, sectionBase + 1, 4, j1, false);
                  this.setBlockAndMetadata(world, 0, sectionBase + 1, 4, this.plankBlock, this.plankMeta);
                  this.setBlockAndMetadata(world, -3, sectionBase + 1, 4, this.plankBlock, this.plankMeta);
                  this.setBlockAndMetadata(world, 0, sectionBase + 2, 4, this.plankSlabBlock, this.plankSlabMeta);
                  this.setBlockAndMetadata(world, -3, sectionBase + 2, 4, this.plankSlabBlock, this.plankSlabMeta);
                  break;
               }

               for(i1 = 1; i1 <= 2; ++i1) {
                  this.setAir(world, i1, sectionBase + 5, k1);
                  k2 = k1 - -1;

                  for(j1 = sectionBase + 1; j1 <= sectionBase + k2; ++j1) {
                     this.placeBrick(world, random, i1, j1, k1);
                  }

                  if (k2 < 5) {
                     this.placeBrickStair(world, random, i1, sectionBase + k2 + 1, k1, 2);
                  }
               }

               ++k1;
            }
         } else {
            k1 = -4;

            while(true) {
               if (k1 > 1) {
                  this.placeRandomFeature(world, random, 2, sectionBase + 1, -4, j1, true);
                  this.placeRandomFeature(world, random, 1, sectionBase + 1, -4, j1, true);
                  this.setBlockAndMetadata(world, 0, sectionBase + 1, -4, this.plankBlock, this.plankMeta);
                  this.setBlockAndMetadata(world, 3, sectionBase + 1, -4, this.plankBlock, this.plankMeta);
                  this.setBlockAndMetadata(world, 0, sectionBase + 2, -4, this.plankSlabBlock, this.plankSlabMeta);
                  this.setBlockAndMetadata(world, 3, sectionBase + 2, -4, this.plankSlabBlock, this.plankSlabMeta);
                  break;
               }

               for(i1 = -2; i1 <= -1; ++i1) {
                  this.setAir(world, i1, sectionBase + 5, k1);
                  k2 = 5 - (k1 - -4);

                  for(j1 = sectionBase + 1; j1 <= sectionBase + k2; ++j1) {
                     this.placeBrick(world, random, i1, j1, k1);
                  }

                  if (k2 < 5) {
                     this.placeBrickStair(world, random, i1, sectionBase + k2 + 1, k1, 3);
                  }
               }

               ++k1;
            }
         }

         if (!this.ruined) {
            LOTREntityDwarf dwarf = random.nextInt(3) == 0 ? new LOTREntityDwarfAxeThrower(world) : new LOTREntityDwarfWarrior(world);
            this.spawnNPCAndSetHome((EntityCreature)dwarf, world, 0, sectionBase + 1, 0, 12);
         }
      }

      for(i1 = -4; i1 <= 4; ++i1) {
         for(sectionBase = 1; sectionBase <= 2; ++sectionBase) {
            for(j1 = -4; j1 <= 4; ++j1) {
               this.setAir(world, i1, (sections + 1) * 5 + sectionBase, j1);
            }
         }
      }

      for(i1 = -4; i1 <= 4; ++i1) {
         this.placeBrickWall(world, random, i1, (sections + 1) * 5 + 1, -5);
         this.placeBrickWall(world, random, i1, (sections + 1) * 5 + 1, 5);
      }

      for(i1 = -4; i1 <= 4; ++i1) {
         this.placeBrickWall(world, random, -5, (sections + 1) * 5 + 1, i1);
         this.placeBrickWall(world, random, 5, (sections + 1) * 5 + 1, i1);
      }

      this.generateCornerPillars(world, random, -5, (sections + 1) * 5 + 5, -5);
      this.generateCornerPillars(world, random, -5, (sections + 1) * 5 + 5, 6);
      this.generateCornerPillars(world, random, 6, (sections + 1) * 5 + 5, -5);
      this.generateCornerPillars(world, random, 6, (sections + 1) * 5 + 5, 6);

      for(i1 = -1; i1 <= 1; ++i1) {
         this.placePillar(world, random, i1, 0, -5);

         for(sectionBase = 1; sectionBase <= 4; ++sectionBase) {
            this.setBlockAndMetadata(world, i1, sectionBase, -5, this.gateBlock, 2);
         }
      }

      int[] var21 = new int[]{-2, 2};
      sectionBase = var21.length;

      for(j1 = 0; j1 < sectionBase; ++j1) {
         k1 = var21[j1];

         for(i1 = 4; !this.isOpaque(world, k1, i1, -6) && this.getY(i1) >= 0; --i1) {
            if (i1 == 3) {
               this.setBlockAndMetadata(world, k1, i1, -6, this.glowBrickBlock, this.glowBrickMeta);
            } else {
               this.placePillar(world, random, k1, i1, -6);
            }

            this.setGrassToDirt(world, k1, i1 - 1, -6);
         }
      }

      for(i1 = -2; i1 <= 2; ++i1) {
         this.placeBrickSlab(world, random, i1, 5, -6, false);
      }

      if (this.bannerType != null) {
         this.placeWallBanner(world, -2, 7, -5, this.bannerType, 2);
         this.placeWallBanner(world, 0, 8, -5, this.bannerType, 2);
         this.placeWallBanner(world, 2, 7, -5, this.bannerType, 2);
      }

      LOTREntityNPC commander = this.getCommanderNPC(world);
      if (commander != null) {
         this.spawnNPCAndSetHome(commander, world, 0, (sections + 1) * 5 + 1, 0, 16);
         if (sections % 2 == 0) {
            this.setBlockAndMetadata(world, -3, (sections + 1) * 5 + 1, -3, LOTRMod.commandTable, 0);
         } else {
            this.setBlockAndMetadata(world, 3, (sections + 1) * 5 + 1, 3, LOTRMod.commandTable, 0);
         }
      }

      this.placePillar(world, random, -4, (sections + 1) * 5 + 1, 0);
      this.placePillar(world, random, -4, (sections + 1) * 5 + 2, 0);
      this.placePillar(world, random, 4, (sections + 1) * 5 + 1, 0);
      this.placePillar(world, random, 4, (sections + 1) * 5 + 2, 0);
      if (this.bannerType != null) {
         this.placeBrick(world, random, -4, (sections + 1) * 5 + 1, 0);
         this.placeBanner(world, -4, (sections + 1) * 5 + 1, 0, this.bannerType, 1);
         this.placeBrick(world, random, 4, (sections + 1) * 5 + 1, 0);
         this.placeBanner(world, 4, (sections + 1) * 5 + 1, 0, this.bannerType, 3);
      }

      if (!this.ruined) {
         LOTREntityNPCRespawner respawner = new LOTREntityNPCRespawner(world);
         respawner.setSpawnClasses(LOTREntityDwarfWarrior.class, LOTREntityDwarfAxeThrower.class);
         respawner.setCheckRanges(12, -8, 42, 16);
         respawner.setSpawnRanges(4, 1, 41, 12);
         this.placeNPCRespawner(respawner, world, 0, 0, 0);
      }

      return true;
   }

   protected void placeBrick(World world, Random random, int i, int j, int k) {
      this.setBlockAndMetadata(world, i, j, k, this.brickBlock, this.brickMeta);
   }

   protected void placeBrickSlab(World world, Random random, int i, int j, int k, boolean flip) {
      this.setBlockAndMetadata(world, i, j, k, this.brickSlabBlock, this.brickSlabMeta | (flip ? 8 : 0));
   }

   protected void placeBrickStair(World world, Random random, int i, int j, int k, int meta) {
      this.setBlockAndMetadata(world, i, j, k, this.brickStairBlock, meta);
   }

   protected void placeBrickWall(World world, Random random, int i, int j, int k) {
      this.setBlockAndMetadata(world, i, j, k, this.brickWallBlock, this.brickWallMeta);
   }

   protected void placePillar(World world, Random random, int i, int j, int k) {
      this.setBlockAndMetadata(world, i, j, k, this.pillarBlock, this.pillarMeta);
   }

   private void generateCornerPillars(World world, Random random, int i, int j, int k) {
      for(int i1 = i - 1; i1 <= i; ++i1) {
         for(int k1 = k - 1; k1 <= k; ++k1) {
            for(int j1 = j; (j1 == 0 || !this.isOpaque(world, i1, j1, k1)) && this.getY(j1) >= 0; --j1) {
               if (j1 == j - 2) {
                  this.setBlockAndMetadata(world, i1, j1, k1, this.glowBrickBlock, this.glowBrickMeta);
               } else {
                  this.placePillar(world, random, i1, j1, k1);
                  this.setGrassToDirt(world, i1, j1 - 1, k1);
               }
            }
         }
      }

   }

   private void placeRandomFeature(World world, Random random, int i, int j, int k, int randomFeature, boolean flip) {
      if (randomFeature == 0) {
         this.setBlockAndMetadata(world, i, j, k, this.tableBlock, 0);
      } else if (randomFeature == 1) {
         this.setBlockAndMetadata(world, i, j, k, this.forgeBlock, flip ? 3 : 2);
         TileEntity tileentity = this.getTileEntity(world, i, j, k);
         if (tileentity instanceof LOTRTileEntityAlloyForge) {
            ((LOTRTileEntityAlloyForge)tileentity).func_70299_a(12, new ItemStack(Items.field_151044_h, 1 + random.nextInt(4)));
         }
      } else if (randomFeature == 2) {
         this.setBlockAndMetadata(world, i, j, k, this.plankSlabBlock, this.plankSlabMeta | 8);
         this.placeChest(world, random, i, j + 1, k, flip ? 3 : 2, this.chestContents);
      } else if (randomFeature == 3) {
         this.setBlockAndMetadata(world, i, j, k, this.plankSlabBlock, this.plankSlabMeta | 8);
         if (!this.ruined) {
            this.placePlateWithCertainty(world, random, i, j + 1, k, this.plateBlock, LOTRFoods.DWARF);
         }
      } else if (randomFeature == 4) {
         this.setBlockAndMetadata(world, i, j, k, this.plankSlabBlock, this.plankSlabMeta | 8);
         if (!this.ruined) {
            this.placeBarrel(world, random, i, j + 1, k, flip ? 3 : 2, LOTRFoods.DWARF_DRINK);
         }
      }

   }
}
