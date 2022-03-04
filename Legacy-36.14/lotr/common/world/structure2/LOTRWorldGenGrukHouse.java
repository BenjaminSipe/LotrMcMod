package lotr.common.world.structure2;

import java.util.Random;
import lotr.common.LOTRMod;
import lotr.common.item.LOTRItemMug;
import lotr.common.world.biome.LOTRBiome;
import lotr.common.world.map.LOTRFixedStructures;
import lotr.common.world.map.LOTRRoadType;
import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class LOTRWorldGenGrukHouse extends LOTRWorldGenStructureBase2 {
   public LOTRWorldGenGrukHouse(boolean flag) {
      super(flag);
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, 9);
      int i1;
      int k1;
      int dx;
      if (this.restrictions) {
         for(i1 = -5; i1 <= 5; ++i1) {
            for(k1 = -8; k1 <= 8; ++k1) {
               dx = this.getTopBlock(world, i1, k1);
               Block block = this.getBlock(world, i1, dx - 1, k1);
               if (block != Blocks.field_150349_c) {
                  return false;
               }
            }
         }
      }

      int i2;
      int i1;
      for(i1 = -5; i1 <= 5; ++i1) {
         for(k1 = -8; k1 <= 8; ++k1) {
            dx = Math.abs(i1);
            i1 = Math.abs(k1);

            for(i2 = 0; (i2 == 0 || !this.isOpaque(world, i1, i2, k1)) && this.getY(i2) >= 0; --i2) {
               this.setBlockAndMetadata(world, i1, i2, k1, Blocks.field_150347_e, 0);
               this.setGrassToDirt(world, i1, i2 - 1, k1);
            }

            if (dx == 5 && i1 == 8) {
               for(i2 = 1; i2 <= 5; ++i2) {
                  this.setBlockAndMetadata(world, i1, i2, k1, LOTRMod.woodBeamV1, 1);
               }
            } else if (dx != 5 && i1 != 8) {
               for(i2 = 1; i2 <= 10; ++i2) {
                  this.setAir(world, i1, i2, k1);
               }
            } else {
               for(i2 = 1; i2 <= 5; ++i2) {
                  this.setBlockAndMetadata(world, i1, i2, k1, Blocks.field_150344_f, 1);
               }
            }
         }
      }

      for(i1 = -9; i1 <= 9; ++i1) {
         for(k1 = 0; k1 <= 5; ++k1) {
            this.setBlockAndMetadata(world, -6 + k1, 5 + k1, i1, LOTRMod.stairsReed, 1);
            this.setBlockAndMetadata(world, 6 - k1, 5 + k1, i1, LOTRMod.stairsReed, 0);
            this.setBlockAndMetadata(world, -6 + k1, 4 + k1, i1, LOTRMod.stairsReed, 4);
            this.setBlockAndMetadata(world, 6 - k1, 4 + k1, i1, LOTRMod.stairsReed, 5);
         }

         this.setBlockAndMetadata(world, 0, 10, i1, LOTRMod.thatch, 1);
         this.setBlockAndMetadata(world, 0, 11, i1, LOTRMod.slabSingleThatch, 1);
      }

      for(i1 = 0; i1 <= 5; ++i1) {
         for(k1 = -5 + i1; k1 <= 5 - i1; ++k1) {
            this.setBlockAndMetadata(world, k1, 5 + i1, -8, Blocks.field_150344_f, 1);
            this.setBlockAndMetadata(world, k1, 5 + i1, 8, Blocks.field_150344_f, 1);
         }
      }

      for(i1 = -5; i1 <= 5; ++i1) {
         this.setBlockAndMetadata(world, i1, 5, -8, LOTRMod.woodBeamV1, 5);
         this.setBlockAndMetadata(world, i1, 5, 8, LOTRMod.woodBeamV1, 5);
         this.setBlockAndMetadata(world, i1, 5, -7, Blocks.field_150422_aJ, 0);
         this.setBlockAndMetadata(world, i1, 5, 7, Blocks.field_150422_aJ, 0);
      }

      for(i1 = -7; i1 <= 7; ++i1) {
         this.setBlockAndMetadata(world, -5, 5, i1, LOTRMod.woodBeamV1, 9);
         this.setBlockAndMetadata(world, 5, 5, i1, LOTRMod.woodBeamV1, 9);
         this.setBlockAndMetadata(world, -4, 5, i1, Blocks.field_150422_aJ, 0);
         this.setBlockAndMetadata(world, 4, 5, i1, Blocks.field_150422_aJ, 0);
      }

      for(i1 = -5; i1 <= 5; ++i1) {
         k1 = Math.abs(i1);
         if (k1 == 2 || k1 == 3) {
            this.setBlockAndMetadata(world, i1, 2, -8, LOTRMod.reedBars, 0);
            this.setBlockAndMetadata(world, i1, 3, -8, LOTRMod.reedBars, 0);
         }
      }

      for(i1 = -7; i1 <= 7; ++i1) {
         k1 = Math.abs(i1);
         if (k1 != 0 && k1 != 1 && k1 != 5 && k1 != 6) {
            if (k1 == 3) {
               for(dx = 0; dx <= 4; ++dx) {
                  this.setBlockAndMetadata(world, -5, dx, i1, LOTRMod.woodBeamV1, 1);
                  this.setBlockAndMetadata(world, 5, dx, i1, LOTRMod.woodBeamV1, 1);
               }

               this.setBlockAndMetadata(world, -3, 1, i1, Blocks.field_150422_aJ, 1);
               this.setBlockAndMetadata(world, -3, 2, i1, Blocks.field_150478_aa, 5);
               this.setBlockAndMetadata(world, 3, 1, i1, Blocks.field_150422_aJ, 1);
               this.setBlockAndMetadata(world, 3, 2, i1, Blocks.field_150478_aa, 5);
            }
         } else {
            this.setBlockAndMetadata(world, -5, 2, i1, LOTRMod.reedBars, 0);
            this.setBlockAndMetadata(world, -5, 3, i1, LOTRMod.reedBars, 0);
            this.setBlockAndMetadata(world, 5, 2, i1, LOTRMod.reedBars, 0);
            this.setBlockAndMetadata(world, 5, 3, i1, LOTRMod.reedBars, 0);
         }
      }

      this.setBlockAndMetadata(world, 0, 0, -8, Blocks.field_150347_e, 0);
      this.setBlockAndMetadata(world, 0, 1, -8, LOTRMod.doorPine, 1);
      this.setBlockAndMetadata(world, 0, 2, -8, LOTRMod.doorPine, 8);
      this.setBlockAndMetadata(world, 0, 4, -9, Blocks.field_150478_aa, 4);
      this.setBlockAndMetadata(world, 0, 3, -7, Blocks.field_150478_aa, 3);
      this.setBlockAndMetadata(world, 0, 1, 7, Blocks.field_150422_aJ, 1);
      this.setBlockAndMetadata(world, 0, 2, 7, Blocks.field_150478_aa, 5);

      for(i1 = -7; i1 <= 7; ++i1) {
         int[] var17 = new int[]{-4, 4};
         dx = var17.length;

         for(i1 = 0; i1 < dx; ++i1) {
            i2 = var17[i1];
            this.setBlockAndMetadata(world, i2, 1, i1, LOTRMod.planks2, 4);
            if (random.nextBoolean()) {
               this.placeMug(world, random, i2, 2, i1, random.nextInt(4), this.getRandomDrink(random), new LOTRItemMug.Vessel[]{LOTRItemMug.Vessel.GOBLET_GOLD, LOTRItemMug.Vessel.GOBLET_SILVER, LOTRItemMug.Vessel.HORN, LOTRItemMug.Vessel.HORN_GOLD});
            }
         }
      }

      for(i1 = -3; i1 <= 3; ++i1) {
         if (i1 != 0) {
            this.placeBarrel(world, random, i1, 1, 7, 2, this.getRandomDrink(random));
            this.placeBarrel(world, random, i1, 2, 7, 2, this.getRandomDrink(random));
         }
      }

      for(i1 = -1; i1 <= 1; ++i1) {
         this.setBlockAndMetadata(world, i1, 4, 7, Blocks.field_150325_L, 14);
         this.setBlockAndMetadata(world, i1, 5, 7, Blocks.field_150325_L, 0);
      }

      for(i1 = -1; i1 <= 1; ++i1) {
         for(k1 = -6; k1 <= -3; ++k1) {
            this.setBlockAndMetadata(world, i1, 1, k1, Blocks.field_150404_cg, 14);
         }

         for(k1 = -2; k1 <= 1; ++k1) {
            this.setBlockAndMetadata(world, i1, 1, k1, Blocks.field_150404_cg, 0);
         }
      }

      int[] var21 = new int[]{-8, 8};
      k1 = var21.length;

      int k1;
      for(dx = 0; dx < k1; ++dx) {
         i1 = var21[dx];

         for(i2 = i1 - 2; i2 <= i1 + 2; ++i2) {
            for(k1 = -20; k1 <= -16; ++k1) {
               int j1;
               for(j1 = 4; (j1 >= 0 || !this.isOpaque(world, i2, j1, k1)) && this.getY(j1) >= 0; --j1) {
                  this.setBlockAndMetadata(world, i2, j1, k1, Blocks.field_150347_e, 0);
                  this.setGrassToDirt(world, i2, j1 - 1, k1);
               }

               for(j1 = 5; j1 <= 10; ++j1) {
                  this.setAir(world, i2, j1, k1);
               }

               if (Math.abs(i2 - i1) <= 1 && Math.abs(k1 - -18) <= 1) {
                  this.setBlockAndMetadata(world, i2, 4, k1, LOTRMod.hearth, 0);
                  this.setBlockAndMetadata(world, i2, 5, k1, Blocks.field_150480_ab, 0);
               }
            }
         }
      }

      for(i1 = -12; i1 <= 12; ++i1) {
         for(k1 = -20; k1 <= 0; ++k1) {
            dx = i1 - 0;
            i1 = k1 - -8;
            i2 = dx * dx + i1 * i1;
            if (i2 <= 144 && random.nextInt(6) != 0) {
               k1 = this.getTopBlock(world, i1, k1) - 1;
               BiomeGenBase biome = this.getBiome(world, i1, k1);
               Block below = this.getBlock(world, i1, k1, k1);
               if (below == biome.field_76752_A || below == biome.field_76753_B) {
                  LOTRRoadType.RoadBlock roadblock = LOTRRoadType.PATH.getBlock(random, LOTRBiome.tundra, true, false);
                  this.setBlockAndMetadata(world, i1, k1, k1, roadblock.block, roadblock.meta);
               }
            }
         }
      }

      this.setBlockAndMetadata(world, 0, 3, -9, Blocks.field_150444_as, 2);
      TileEntity te = this.getTileEntity(world, 0, 3, -9);
      TileEntitySign sign;
      if (te instanceof TileEntitySign) {
         sign = (TileEntitySign)te;
         sign.field_145915_a[1] = "Kvas";
         sign.field_145915_a[2] = "chlebovÃ½";
      }

      this.setBlockAndMetadata(world, 0, 3, 7, Blocks.field_150444_as, 2);
      te = this.getTileEntity(world, 0, 3, 7);
      if (te instanceof TileEntitySign) {
         sign = (TileEntitySign)te;
         sign.field_145915_a[1] = ":^)";
      }

      this.setBlockAndMetadata(world, 0, 8, -7, Blocks.field_150444_as, 3);
      te = this.getTileEntity(world, 0, 8, -7);
      if (te instanceof TileEntitySign) {
         sign = (TileEntitySign)te;
         sign.field_145915_a[1] = "Textures?";
      }

      this.spawnItemFrame(world, -1, 7, -8, 0, new ItemStack(LOTRMod.rollingPin));
      this.spawnItemFrame(world, 1, 7, -8, 0, new ItemStack(Items.field_151122_aG));
      EntityOcelot bazyl = new EntityOcelot(world);
      bazyl.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(1.0E8D);
      bazyl.func_70606_j(bazyl.func_110138_aP());
      bazyl.func_70903_f(true);
      bazyl.func_152115_b("6c94c61a-aebb-4b77-9699-4d5236d0e78a");
      bazyl.func_70912_b(1);
      bazyl.func_94058_c("Bazyl");
      this.spawnNPCAndSetHome(bazyl, world, -1, 1, 0, 16);
      EntityWolf wiktor = new EntityWolf(world);
      wiktor.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(1.0E8D);
      wiktor.func_70606_j(wiktor.func_110138_aP());
      wiktor.func_70903_f(true);
      wiktor.func_152115_b("6c94c61a-aebb-4b77-9699-4d5236d0e78a");
      wiktor.func_94058_c("Wiktor");
      this.spawnNPCAndSetHome(wiktor, world, 1, 1, 0, 16);
      return true;
   }

   private ItemStack getRandomDrink(Random random) {
      return random.nextBoolean() ? new ItemStack(LOTRMod.mugPlumKvass) : new ItemStack(LOTRMod.mugVodka);
   }

   public static boolean generatesAt(World world, int i, int k) {
      return LOTRFixedStructures.generatesAtMapImageCoords(i, k, 989, 528);
   }
}
