package lotr.common.world.structure2;

import java.util.Random;
import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityDaleBaker;
import lotr.common.entity.npc.LOTRNames;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.world.World;

public class LOTRWorldGenDaleBakery extends LOTRWorldGenDaleStructure {
   public LOTRWorldGenDaleBakery(boolean flag) {
      super(flag);
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, 2);
      this.setupRandomBlocks(random);
      int i1;
      int k1;
      int i2;
      int j1;
      int i1;
      if (this.restrictions) {
         i1 = 0;
         k1 = 0;

         for(i2 = -7; i2 <= 7; ++i2) {
            for(j1 = -4; j1 <= 15; ++j1) {
               i1 = this.getTopBlock(world, i2, j1) - 1;
               Block block = this.getBlock(world, i2, i1, j1);
               if (block != Blocks.field_150349_c) {
                  return false;
               }

               if (i1 < i1) {
                  i1 = i1;
               }

               if (i1 > k1) {
                  k1 = i1;
               }

               if (k1 - i1 > 6) {
                  return false;
               }
            }
         }
      }

      for(i1 = -5; i1 <= 5; ++i1) {
         for(k1 = 0; k1 <= 13; ++k1) {
            for(i2 = 0; !this.isOpaque(world, i1, i2, k1) && this.getY(i2) >= 0; --i2) {
               this.setBlockAndMetadata(world, i1, i2, k1, this.brickBlock, this.brickMeta);
               this.setGrassToDirt(world, i1, i2 - 1, k1);
            }

            for(i2 = 1; i2 <= 7; ++i2) {
               this.setAir(world, i1, i2, k1);
            }

            if (Math.abs(i1) != 5 && k1 != 0 && k1 != 13) {
               this.setBlockAndMetadata(world, i1, 0, k1, this.floorBlock, this.floorMeta);
            } else {
               for(i2 = 1; i2 <= 3; ++i2) {
                  this.setBlockAndMetadata(world, i1, i2, k1, this.brickBlock, this.brickMeta);
               }
            }
         }
      }

      for(i1 = -6; i1 <= 6; ++i1) {
         for(k1 = -1; k1 <= 14; ++k1) {
            if (Math.abs(i1) == 6 && (k1 == -1 || k1 == 14) || Math.abs(i1) == 1 && k1 == -1) {
               for(i2 = 4; (i2 >= 1 || !this.isOpaque(world, i1, i2, k1)) && this.getY(i2) >= 0; --i2) {
                  this.setBlockAndMetadata(world, i1, i2, k1, this.woodBeamBlock, this.woodBeamMeta);
                  this.setGrassToDirt(world, i1, i2 - 1, k1);
               }
            }
         }
      }

      for(i1 = -1; i1 <= 1; ++i1) {
         byte k1;
         if (Math.abs(i1) == 1) {
            k1 = -2;

            for(i2 = 2; (i2 >= 1 || !this.isOpaque(world, i1, i2, k1)) && this.getY(i2) >= 0; --i2) {
               this.setBlockAndMetadata(world, i1, i2, k1, this.fenceBlock, this.fenceMeta);
            }
         } else if (i1 == 0) {
            k1 = -1;

            for(i2 = 0; (i2 >= 0 || !this.isOpaque(world, i1, i2, k1)) && this.getY(i2) >= 0; --i2) {
               this.setBlockAndMetadata(world, i1, i2, k1, this.floorBlock, this.floorMeta);
               this.setGrassToDirt(world, i1, i2 - 1, k1);
            }
         }
      }

      for(i1 = -5; i1 <= 5; ++i1) {
         this.setBlockAndMetadata(world, i1, 4, -1, this.brickBlock, this.brickMeta);
         this.setBlockAndMetadata(world, i1, 4, 14, this.brickBlock, this.brickMeta);
      }

      for(i1 = 0; i1 <= 13; ++i1) {
         this.setBlockAndMetadata(world, -6, 4, i1, this.brickBlock, this.brickMeta);
         this.setBlockAndMetadata(world, 6, 4, i1, this.brickBlock, this.brickMeta);
      }

      for(i1 = -7; i1 <= 7; ++i1) {
         this.setBlockAndMetadata(world, i1, 4, -2, this.brickSlabBlock, this.brickSlabMeta | 8);
         this.setBlockAndMetadata(world, i1, 4, 15, this.brickSlabBlock, this.brickSlabMeta | 8);
      }

      for(i1 = -2; i1 <= 15; ++i1) {
         this.setBlockAndMetadata(world, -7, 4, i1, this.brickSlabBlock, this.brickSlabMeta | 8);
         this.setBlockAndMetadata(world, 7, 4, i1, this.brickSlabBlock, this.brickSlabMeta | 8);
      }

      int[] var18 = new int[]{-5, 2};
      k1 = var18.length;

      for(i2 = 0; i2 < k1; ++i2) {
         j1 = var18[i2];
         this.setBlockAndMetadata(world, j1, 2, -1, this.trapdoorBlock, 12);
         this.setBlockAndMetadata(world, j1, 3, -1, this.brickSlabBlock, this.brickSlabMeta | 8);
         this.setBlockAndMetadata(world, j1, 4, -2, this.brickBlock, this.brickMeta);
         this.setBlockAndMetadata(world, j1 + 1, 2, 0, this.fenceBlock, this.fenceMeta);
         this.setBlockAndMetadata(world, j1 + 1, 3, -1, this.plankSlabBlock, this.plankSlabMeta);
         this.setBlockAndMetadata(world, j1 + 2, 2, 0, this.fenceBlock, this.fenceMeta);
         this.setBlockAndMetadata(world, j1 + 2, 3, -1, this.plankSlabBlock, this.plankSlabMeta);
         this.setBlockAndMetadata(world, j1 + 3, 2, -1, this.trapdoorBlock, 12);
         this.setBlockAndMetadata(world, j1 + 3, 3, -1, this.brickSlabBlock, this.brickSlabMeta | 8);
         this.setBlockAndMetadata(world, j1 + 3, 4, -2, this.brickBlock, this.brickMeta);
      }

      var18 = new int[]{-5, 2};
      k1 = var18.length;

      for(i2 = 0; i2 < k1; ++i2) {
         j1 = var18[i2];
         this.setBlockAndMetadata(world, j1, 2, 14, this.trapdoorBlock, 13);
         this.setBlockAndMetadata(world, j1, 3, 14, this.brickSlabBlock, this.brickSlabMeta | 8);
         this.setBlockAndMetadata(world, j1, 4, 15, this.brickBlock, this.brickMeta);
         this.setBlockAndMetadata(world, j1 + 1, 2, 13, this.fenceBlock, this.fenceMeta);
         this.setBlockAndMetadata(world, j1 + 1, 3, 14, this.plankSlabBlock, this.plankSlabMeta);
         this.setBlockAndMetadata(world, j1 + 2, 2, 13, this.fenceBlock, this.fenceMeta);
         this.setBlockAndMetadata(world, j1 + 2, 3, 14, this.plankSlabBlock, this.plankSlabMeta);
         this.setBlockAndMetadata(world, j1 + 3, 2, 14, this.trapdoorBlock, 13);
         this.setBlockAndMetadata(world, j1 + 3, 3, 14, this.brickSlabBlock, this.brickSlabMeta | 8);
         this.setBlockAndMetadata(world, j1 + 3, 4, 15, this.brickBlock, this.brickMeta);
      }

      for(i1 = 0; i1 <= 13; ++i1) {
         if (i1 == 0 || i1 == 3 || i1 == 6 || i1 == 8 || i1 == 13) {
            this.setBlockAndMetadata(world, -6, 2, i1, this.trapdoorBlock, 15);
            this.setBlockAndMetadata(world, -6, 3, i1, this.brickSlabBlock, this.brickSlabMeta | 8);
            this.setBlockAndMetadata(world, -7, 4, i1, this.brickBlock, this.brickMeta);
            this.setBlockAndMetadata(world, 6, 2, i1, this.trapdoorBlock, 14);
            this.setBlockAndMetadata(world, 6, 3, i1, this.brickSlabBlock, this.brickSlabMeta | 8);
            this.setBlockAndMetadata(world, 7, 4, i1, this.brickBlock, this.brickMeta);
         }

         if (i1 == 1 || i1 == 2 || i1 == 4 || i1 == 5 || i1 >= 9 && i1 <= 12) {
            this.setBlockAndMetadata(world, -5, 2, i1, this.fenceBlock, this.fenceMeta);
            this.setBlockAndMetadata(world, -6, 3, i1, this.plankSlabBlock, this.plankSlabMeta);
            this.setBlockAndMetadata(world, 5, 2, i1, this.fenceBlock, this.fenceMeta);
            this.setBlockAndMetadata(world, 6, 3, i1, this.plankSlabBlock, this.plankSlabMeta);
         }
      }

      for(i1 = -2; i1 <= -1; ++i1) {
         this.setBlockAndMetadata(world, -1, 3, i1, Blocks.field_150325_L, 14);
         this.setBlockAndMetadata(world, 0, 3, i1, Blocks.field_150325_L, 0);
         this.setBlockAndMetadata(world, 1, 3, i1, Blocks.field_150325_L, 14);
      }

      this.setBlockAndMetadata(world, 0, 1, -1, this.doorBlock, 3);
      this.setBlockAndMetadata(world, 0, 2, -1, this.doorBlock, 8);
      this.setBlockAndMetadata(world, 0, 0, 0, this.floorBlock, this.floorMeta);
      this.setAir(world, 0, 1, 0);
      this.setAir(world, 0, 2, 0);

      for(i1 = 0; i1 <= 13; ++i1) {
         for(k1 = -5; k1 <= 5; ++k1) {
            i2 = Math.abs(k1);
            if (i2 < 1 || i2 > 3 || (i1 < 2 || i1 > 6) && (i1 < 8 || i1 > 11)) {
               this.setBlockAndMetadata(world, k1, 4, i1, this.plankBlock, this.plankMeta);
            } else {
               this.setBlockAndMetadata(world, k1, 4, i1, this.plankSlabBlock, this.plankSlabMeta | 8);
            }
         }

         for(k1 = -6; k1 <= 6; ++k1) {
            this.setBlockAndMetadata(world, k1, 5, i1, this.roofBlock, this.roofMeta);
         }

         for(k1 = -5; k1 <= 5; ++k1) {
            this.setBlockAndMetadata(world, k1, 6, i1, this.roofBlock, this.roofMeta);
         }
      }

      var18 = new int[]{-1, 14};
      k1 = var18.length;

      for(i2 = 0; i2 < k1; ++i2) {
         j1 = var18[i2];

         for(i1 = -6; i1 <= 6; ++i1) {
            this.setBlockAndMetadata(world, i1, 5, j1, this.plankBlock, this.plankMeta);
         }

         for(i1 = -5; i1 <= 5; ++i1) {
            this.setBlockAndMetadata(world, i1, 6, j1, this.plankBlock, this.plankMeta);
         }
      }

      var18 = new int[]{-2, 15};
      k1 = var18.length;

      int i1;
      for(i2 = 0; i2 < k1; ++i2) {
         j1 = var18[i2];
         this.setBlockAndMetadata(world, -6, 5, j1, this.roofStairBlock, 4);
         this.setBlockAndMetadata(world, -5, 6, j1, this.roofStairBlock, 4);
         this.setBlockAndMetadata(world, 5, 6, j1, this.roofStairBlock, 5);
         this.setBlockAndMetadata(world, 6, 5, j1, this.roofStairBlock, 5);
         int[] var19 = new int[]{-3, 0, 3};
         i1 = var19.length;

         for(int var13 = 0; var13 < i1; ++var13) {
            int i1 = var19[var13];

            for(int j1 = 5; j1 <= 6; ++j1) {
               this.setBlockAndMetadata(world, i1, j1, j1, this.brickBlock, this.brickMeta);
            }
         }
      }

      for(i1 = -2; i1 <= 15; ++i1) {
         this.setBlockAndMetadata(world, -7, 5, i1, this.roofStairBlock, 1);
         this.setBlockAndMetadata(world, -6, 6, i1, this.roofStairBlock, 1);
         this.setBlockAndMetadata(world, -5, 7, i1, this.roofStairBlock, 1);

         for(k1 = -4; k1 <= 4; ++k1) {
            this.setBlockAndMetadata(world, k1, 7, i1, this.roofBlock, this.roofMeta);
         }

         for(k1 = -2; k1 <= 2; ++k1) {
            this.setBlockAndMetadata(world, k1, 8, i1, this.roofSlabBlock, this.roofSlabMeta);
         }

         this.setBlockAndMetadata(world, 5, 7, i1, this.roofStairBlock, 0);
         this.setBlockAndMetadata(world, 6, 6, i1, this.roofStairBlock, 0);
         this.setBlockAndMetadata(world, 7, 5, i1, this.roofStairBlock, 0);
      }

      for(i1 = 1; i1 <= 9; ++i1) {
         this.setBlockAndMetadata(world, 0, i1, 11, Blocks.field_150336_V, 0);
         this.setBlockAndMetadata(world, -1, i1, 12, Blocks.field_150336_V, 0);
         this.setAir(world, 0, i1, 12);
         this.setBlockAndMetadata(world, 1, i1, 12, Blocks.field_150336_V, 0);
         this.setBlockAndMetadata(world, 0, i1, 13, Blocks.field_150336_V, 0);
      }

      var18 = new int[]{1, 8};
      k1 = var18.length;

      for(i2 = 0; i2 < k1; ++i2) {
         j1 = var18[i2];
         this.setBlockAndMetadata(world, 0, j1, 12, LOTRMod.hearth, 0);
         this.setBlockAndMetadata(world, 0, j1 + 1, 12, Blocks.field_150480_ab, 0);
      }

      this.setBlockAndMetadata(world, -2, 3, 1, Blocks.field_150478_aa, 3);
      this.setBlockAndMetadata(world, 2, 3, 1, Blocks.field_150478_aa, 3);
      this.setBlockAndMetadata(world, -4, 3, 3, Blocks.field_150478_aa, 2);
      this.setBlockAndMetadata(world, 4, 3, 3, Blocks.field_150478_aa, 1);
      this.setBlockAndMetadata(world, -4, 3, 10, Blocks.field_150478_aa, 2);
      this.setBlockAndMetadata(world, 4, 3, 10, Blocks.field_150478_aa, 1);
      this.setBlockAndMetadata(world, -2, 3, 12, Blocks.field_150478_aa, 4);
      this.setBlockAndMetadata(world, 2, 3, 12, Blocks.field_150478_aa, 4);
      this.setBlockAndMetadata(world, -2, 4, 4, this.plankBlock, this.plankMeta);
      this.setBlockAndMetadata(world, -2, 3, 4, LOTRMod.chandelier, 3);
      this.setBlockAndMetadata(world, 2, 4, 4, this.plankBlock, this.plankMeta);
      this.setBlockAndMetadata(world, 2, 3, 4, LOTRMod.chandelier, 3);

      for(i1 = -4; i1 <= 4; ++i1) {
         this.setBlockAndMetadata(world, i1, 1, 7, this.brickBlock, this.brickMeta);
         this.setBlockAndMetadata(world, i1, 3, 7, this.fenceBlock, this.fenceMeta);
      }

      this.setBlockAndMetadata(world, -1, 1, 7, this.brickStairBlock, 4);
      this.setBlockAndMetadata(world, 0, 1, 7, this.brickSlabBlock, this.brickSlabMeta | 8);
      this.setBlockAndMetadata(world, 1, 1, 7, this.brickStairBlock, 5);
      this.setBlockAndMetadata(world, 3, 1, 7, this.fenceGateBlock, 0);
      this.setBlockAndMetadata(world, -1, 1, 11, Blocks.field_150460_al, 2);
      this.setBlockAndMetadata(world, 0, 1, 11, Blocks.field_150389_bf, 2);
      this.setBlockAndMetadata(world, 1, 1, 11, Blocks.field_150460_al, 2);
      this.setBlockAndMetadata(world, -1, 2, 11, Blocks.field_150460_al, 2);
      this.setBlockAndMetadata(world, 0, 2, 11, this.barsBlock, 0);
      this.setBlockAndMetadata(world, 1, 2, 11, Blocks.field_150460_al, 2);
      this.setBlockAndMetadata(world, -1, 3, 11, Blocks.field_150336_V, 0);
      this.setBlockAndMetadata(world, 1, 3, 11, Blocks.field_150336_V, 0);

      for(i1 = -2; i1 <= 2; ++i1) {
         for(k1 = 10; k1 <= 12; ++k1) {
            this.setBlockAndMetadata(world, i1, 4, k1, Blocks.field_150336_V, 0);
         }
      }

      this.setBlockAndMetadata(world, -2, 1, 1, this.plankBlock, this.plankMeta);
      this.setBlockAndMetadata(world, -3, 1, 1, this.plankSlabBlock, this.plankSlabMeta | 8);
      this.setBlockAndMetadata(world, -4, 1, 1, this.plankBlock, this.plankMeta);

      for(i1 = 2; i1 <= 5; ++i1) {
         this.setBlockAndMetadata(world, -4, 1, i1, this.plankSlabBlock, this.plankSlabMeta | 8);
      }

      this.setBlockAndMetadata(world, -4, 1, 6, this.plankBlock, this.plankMeta);
      this.setBlockAndMetadata(world, -3, 1, 6, this.plankSlabBlock, this.plankSlabMeta | 8);
      this.setBlockAndMetadata(world, -2, 1, 6, this.plankBlock, this.plankMeta);

      for(i1 = 1; i1 <= 6; ++i1) {
         this.placeRandomCake(world, random, -4, 2, i1);
      }

      for(i1 = -3; i1 <= -2; ++i1) {
         this.placeRandomCake(world, random, i1, 2, 1);
         this.placeRandomCake(world, random, i1, 2, 6);
      }

      this.setBlockAndMetadata(world, 2, 1, 3, this.plankStairBlock, 7);
      this.setBlockAndMetadata(world, 2, 1, 4, this.plankStairBlock, 6);
      this.placeRandomCake(world, random, 2, 2, 3);
      this.placeRandomCake(world, random, 2, 2, 4);
      this.setBlockAndMetadata(world, 4, 1, 1, this.plankBlock, this.plankMeta);

      for(i1 = 2; i1 <= 5; ++i1) {
         this.setBlockAndMetadata(world, 4, 1, i1, this.plankSlabBlock, this.plankSlabMeta | 8);
      }

      this.setBlockAndMetadata(world, 4, 1, 6, this.plankBlock, this.plankMeta);

      for(i1 = 1; i1 <= 6; ++i1) {
         this.placeRandomCake(world, random, 4, 2, i1);
      }

      for(i1 = -4; i1 <= -3; ++i1) {
         this.setBlockAndMetadata(world, i1, 1, 8, this.plankBlock, this.plankMeta);

         for(k1 = 9; k1 <= 11; ++k1) {
            this.setBlockAndMetadata(world, i1, 1, k1, this.plankSlabBlock, this.plankSlabMeta | 8);
         }

         this.setBlockAndMetadata(world, i1, 1, 12, this.plankBlock, this.plankMeta);

         for(k1 = 8; k1 <= 12; ++k1) {
            this.placeRandomCake(world, random, i1, 2, k1);
         }
      }

      this.setBlockAndMetadata(world, 4, 1, 8, this.plankBlock, this.plankMeta);
      this.placeRandomCake(world, random, 4, 2, 8);
      this.setBlockAndMetadata(world, 4, 1, 9, Blocks.field_150383_bp, 3);
      this.setBlockAndMetadata(world, 4, 1, 10, this.plankSlabBlock, this.plankSlabMeta | 8);
      this.setBlockAndMetadata(world, 4, 1, 11, Blocks.field_150462_ai, 0);
      this.setBlockAndMetadata(world, 4, 1, 12, this.plankBlock, this.plankMeta);
      this.placeRandomCake(world, random, 4, 2, 12);
      this.setBlockAndMetadata(world, 3, 1, 12, this.plankSlabBlock, this.plankSlabMeta | 8);
      this.placeRandomCake(world, random, 3, 2, 12);
      this.setBlockAndMetadata(world, 2, 1, 12, this.plankBlock, this.plankMeta);
      this.placeBarrel(world, random, 2, 2, 12, 2, LOTRFoods.DALE_DRINK);
      this.spawnItemFrame(world, 5, 3, 9, 3, new ItemStack(Items.field_151113_aN));
      LOTREntityDaleBaker baker = new LOTREntityDaleBaker(world);
      this.spawnNPCAndSetHome(baker, world, 0, 1, 8, 8);
      this.setBlockAndMetadata(world, 0, 5, -3, this.plankSlabBlock, this.plankSlabMeta | 8);
      this.setBlockAndMetadata(world, 0, 6, -3, this.plankSlabBlock, this.plankSlabMeta);
      this.setBlockAndMetadata(world, 0, 6, -4, this.plankSlabBlock, this.plankSlabMeta);
      this.setBlockAndMetadata(world, 0, 5, -4, this.fenceBlock, this.fenceMeta);
      this.setBlockAndMetadata(world, 0, 4, -4, this.plankBlock, this.plankMeta);
      String[] bakeryName = LOTRNames.getDaleBakeryName(random, baker.getNPCName());
      baker.setSpecificLocationName(bakeryName[0] + " " + bakeryName[1]);
      this.setBlockAndMetadata(world, -1, 4, -4, Blocks.field_150444_as, 5);
      this.setBlockAndMetadata(world, 1, 4, -4, Blocks.field_150444_as, 4);
      int[] var22 = new int[]{-1, 1};
      j1 = var22.length;

      for(i1 = 0; i1 < j1; ++i1) {
         i1 = var22[i1];
         TileEntity te = this.getTileEntity(world, i1, 4, -4);
         if (te instanceof TileEntitySign) {
            TileEntitySign sign = (TileEntitySign)te;
            sign.field_145915_a[1] = bakeryName[0];
            sign.field_145915_a[2] = bakeryName[1];
         }
      }

      return true;
   }

   private void placeRandomCake(World world, Random random, int i, int j, int k) {
      if (random.nextBoolean()) {
         Block cakeBlock = null;
         if (random.nextBoolean()) {
            cakeBlock = LOTRMod.dalishPastry;
         } else {
            int randomCake = random.nextInt(4);
            if (randomCake == 0) {
               cakeBlock = Blocks.field_150414_aQ;
            } else if (randomCake == 1) {
               cakeBlock = LOTRMod.appleCrumble;
            } else if (randomCake == 2) {
               cakeBlock = LOTRMod.berryPie;
            } else if (randomCake == 3) {
               cakeBlock = LOTRMod.marzipanBlock;
            }
         }

         this.setBlockAndMetadata(world, i, j, k, cakeBlock, 0);
      }

   }
}
