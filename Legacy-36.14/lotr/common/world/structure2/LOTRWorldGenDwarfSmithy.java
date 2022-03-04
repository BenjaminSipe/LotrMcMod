package lotr.common.world.structure2;

import java.util.Random;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityDwarf;
import lotr.common.entity.npc.LOTREntityDwarfSmith;
import lotr.common.tileentity.LOTRTileEntityDwarvenForge;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTRWorldGenDwarfSmithy extends LOTRWorldGenStructureBase2 {
   protected Block baseBrickBlock;
   protected int baseBrickMeta;
   protected Block brickBlock;
   protected int brickMeta;
   protected Block brickSlabBlock;
   protected int brickSlabMeta;
   protected Block brickStairBlock;
   protected Block carvedBrickBlock;
   protected int carvedBrickMeta;
   protected Block pillarBlock;
   protected int pillarMeta;
   protected Block plankBlock;
   protected int plankMeta;
   protected Block gateBlock;
   protected Block tableBlock;
   protected Block barsBlock;

   public LOTRWorldGenDwarfSmithy(boolean flag) {
      super(flag);
      this.baseBrickBlock = Blocks.field_150417_aV;
      this.baseBrickMeta = 0;
      this.brickBlock = LOTRMod.brick;
      this.brickMeta = 6;
      this.brickSlabBlock = LOTRMod.slabSingle;
      this.brickSlabMeta = 7;
      this.brickStairBlock = LOTRMod.stairsDwarvenBrick;
      this.carvedBrickBlock = LOTRMod.brick2;
      this.carvedBrickMeta = 12;
      this.pillarBlock = LOTRMod.pillar;
      this.pillarMeta = 0;
      this.tableBlock = LOTRMod.dwarvenTable;
      this.barsBlock = LOTRMod.dwarfBars;
   }

   protected LOTREntityDwarf createSmith(World world) {
      return new LOTREntityDwarfSmith(world);
   }

   protected LOTRChestContents getChestContents() {
      return LOTRChestContents.DWARF_SMITHY;
   }

   protected void setupRandomBlocks(Random random) {
      int randomWood = random.nextInt(4);
      if (randomWood == 0) {
         this.plankBlock = Blocks.field_150344_f;
         this.plankMeta = 1;
         this.gateBlock = Blocks.field_150396_be;
      } else if (randomWood == 1) {
         this.plankBlock = LOTRMod.planks;
         this.plankMeta = 13;
         this.gateBlock = LOTRMod.fenceGateLarch;
      } else if (randomWood == 2) {
         this.plankBlock = LOTRMod.planks2;
         this.plankMeta = 4;
         this.gateBlock = LOTRMod.fenceGatePine;
      } else if (randomWood == 3) {
         this.plankBlock = LOTRMod.planks2;
         this.plankMeta = 3;
         this.gateBlock = LOTRMod.fenceGateFir;
      }

   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, 5);
      this.setupRandomBlocks(random);
      int i1;
      int k1;
      int i2;
      int k2;
      int j1;
      if (this.restrictions) {
         i1 = 0;
         k1 = 0;

         for(i2 = -4; i2 <= 4; ++i2) {
            for(k2 = -4; k2 <= 4; ++k2) {
               j1 = this.getTopBlock(world, i2, k2);
               Block block = this.getBlock(world, i2, j1 - 1, k2);
               if (block != Blocks.field_150349_c) {
                  return false;
               }

               if (j1 < i1) {
                  i1 = j1;
               }

               if (j1 > k1) {
                  k1 = j1;
               }

               if (k1 - i1 > 5) {
                  return false;
               }
            }
         }
      }

      for(i1 = -4; i1 <= 4; ++i1) {
         for(k1 = -4; k1 <= 4; ++k1) {
            i2 = Math.abs(i1);
            k2 = Math.abs(k1);
            if (i2 + k2 <= 6) {
               this.layFoundation(world, i1, k1);

               for(j1 = 1; j1 <= 5; ++j1) {
                  this.setAir(world, i1, j1, k1);
               }

               if (i2 == 4 || k2 == 4) {
                  this.setBlockAndMetadata(world, i1, 1, k1, this.baseBrickBlock, this.baseBrickMeta);
                  this.setBlockAndMetadata(world, i1, 2, k1, this.plankBlock, this.plankMeta);
                  this.setBlockAndMetadata(world, i1, 3, k1, this.brickBlock, this.brickMeta);
               }

               if (i2 == 3 && k2 == 3) {
                  for(j1 = 1; j1 <= 3; ++j1) {
                     this.setBlockAndMetadata(world, i1, j1, k1, this.pillarBlock, this.pillarMeta);
                  }
               }
            }
         }
      }

      for(i1 = -2; i1 <= 2; ++i1) {
         this.setBlockAndMetadata(world, i1, 3, -3, this.brickStairBlock, 7);
         this.setBlockAndMetadata(world, i1, 3, 3, this.brickStairBlock, 6);
      }

      for(i1 = -2; i1 <= 2; ++i1) {
         this.setBlockAndMetadata(world, -3, 3, i1, this.brickStairBlock, 4);
         this.setBlockAndMetadata(world, 3, 3, i1, this.brickStairBlock, 5);
      }

      for(i1 = -3; i1 <= 3; ++i1) {
         for(k1 = -3; k1 <= 3; ++k1) {
            this.setBlockAndMetadata(world, i1, 4, k1, this.brickBlock, this.brickMeta);
         }
      }

      for(i1 = -2; i1 <= 2; ++i1) {
         this.setBlockAndMetadata(world, i1, 4, -4, this.brickStairBlock, 2);
         this.setBlockAndMetadata(world, i1, 4, 4, this.brickStairBlock, 3);
      }

      for(i1 = -2; i1 <= 2; ++i1) {
         this.setBlockAndMetadata(world, -4, 4, i1, this.brickStairBlock, 1);
         this.setBlockAndMetadata(world, 4, 4, i1, this.brickStairBlock, 0);
      }

      this.setBlockAndMetadata(world, -4, 4, 2, this.brickStairBlock, 3);
      this.setBlockAndMetadata(world, -3, 4, 2, this.brickStairBlock, 1);
      this.setBlockAndMetadata(world, -3, 4, 3, this.brickStairBlock, 3);
      this.setBlockAndMetadata(world, -2, 4, 3, this.brickStairBlock, 1);
      this.setBlockAndMetadata(world, 4, 4, 2, this.brickStairBlock, 3);
      this.setBlockAndMetadata(world, 3, 4, 2, this.brickStairBlock, 0);
      this.setBlockAndMetadata(world, 3, 4, 3, this.brickStairBlock, 3);
      this.setBlockAndMetadata(world, 2, 4, 3, this.brickStairBlock, 0);
      this.setBlockAndMetadata(world, -4, 4, -2, this.brickStairBlock, 2);
      this.setBlockAndMetadata(world, -3, 4, -2, this.brickStairBlock, 1);
      this.setBlockAndMetadata(world, -3, 4, -3, this.brickStairBlock, 2);
      this.setBlockAndMetadata(world, -2, 4, -3, this.brickStairBlock, 1);
      this.setBlockAndMetadata(world, 4, 4, -2, this.brickStairBlock, 2);
      this.setBlockAndMetadata(world, 3, 4, -2, this.brickStairBlock, 0);
      this.setBlockAndMetadata(world, 3, 4, -3, this.brickStairBlock, 2);
      this.setBlockAndMetadata(world, 2, 4, -3, this.brickStairBlock, 0);

      for(i1 = -1; i1 <= 1; ++i1) {
         for(k1 = 2; k1 <= 4; ++k1) {
            i2 = Math.abs(i1 - 0);
            k2 = Math.abs(k1 - 3);
            if (i2 == 1 && k2 == 1) {
               this.setBlockAndMetadata(world, i1, 5, k1, this.brickSlabBlock, this.brickSlabMeta);
            } else if (i2 != 1 && k2 != 1) {
               if (i2 == 0 && k2 == 0) {
                  this.setAir(world, i1, 3, k1);
                  this.setAir(world, i1, 4, k1);
               }
            } else {
               this.setBlockAndMetadata(world, i1, 5, k1, this.brickBlock, this.brickMeta);
            }
         }

         this.setBlockAndMetadata(world, i1, 4, 4, this.brickBlock, this.brickMeta);

         for(k1 = 1; k1 <= 2; ++k1) {
            this.setBlockAndMetadata(world, i1, k1, 4, this.brickBlock, this.brickMeta);
         }
      }

      this.setBlockAndMetadata(world, 0, 6, 2, this.brickStairBlock, 2);
      this.setBlockAndMetadata(world, -1, 6, 3, this.brickStairBlock, 1);
      this.setBlockAndMetadata(world, 1, 6, 3, this.brickStairBlock, 0);
      this.setBlockAndMetadata(world, 0, 6, 4, this.brickStairBlock, 3);
      this.setBlockAndMetadata(world, 0, 1, -4, this.gateBlock, 0);
      this.setAir(world, 0, 2, -4);
      this.setBlockAndMetadata(world, -2, 2, -3, Blocks.field_150478_aa, 2);
      this.setBlockAndMetadata(world, 2, 2, -3, Blocks.field_150478_aa, 1);
      this.setBlockAndMetadata(world, 0, 1, -1, Blocks.field_150467_bQ, 1);
      int[] var13 = new int[]{-3, 3};
      k1 = var13.length;

      for(i2 = 0; i2 < k1; ++i2) {
         k2 = var13[i2];
         this.setBlockAndMetadata(world, k2, 1, -1, Blocks.field_150467_bQ, 0);
         this.setBlockAndMetadata(world, k2, 1, 0, this.tableBlock, 0);
         this.setBlockAndMetadata(world, k2, 1, 2, Blocks.field_150462_ai, 0);
      }

      this.setBlockAndMetadata(world, -3, 1, -2, LOTRMod.unsmeltery, 4);
      this.setBlockAndMetadata(world, 3, 1, -2, LOTRMod.unsmeltery, 5);
      this.placeChest(world, random, -3, 1, 1, 4, this.getChestContents());
      this.placeChest(world, random, 3, 1, 1, 5, this.getChestContents());
      this.placeDwarfForge(world, random, 0, 1, 2, 2);
      this.placeDwarfForge(world, random, -1, 1, 3, 5);
      this.placeDwarfForge(world, random, 1, 1, 3, 4);
      var13 = new int[]{-1, 1};
      k1 = var13.length;

      for(i2 = 0; i2 < k1; ++i2) {
         k2 = var13[i2];
         this.setBlockAndMetadata(world, k2, 1, 2, this.brickBlock, this.brickMeta);
         this.setBlockAndMetadata(world, k2, 2, 2, this.carvedBrickBlock, this.carvedBrickMeta);
         this.setBlockAndMetadata(world, k2, 3, 2, this.brickStairBlock, 2);
         this.setBlockAndMetadata(world, k2, 2, 3, this.barsBlock, 0);
         this.setBlockAndMetadata(world, k2, 3, 3, this.brickBlock, this.brickMeta);
      }

      this.setBlockAndMetadata(world, 0, 2, 2, this.barsBlock, 0);
      this.setBlockAndMetadata(world, 0, 3, 2, this.brickStairBlock, 2);
      this.setBlockAndMetadata(world, 0, 1, 3, Blocks.field_150353_l, 0);
      LOTREntityDwarf smith = this.createSmith(world);
      this.spawnNPCAndSetHome(smith, world, 0, 1, 0, 8);
      return true;
   }

   protected void layFoundation(World world, int i, int k) {
      for(int j = 0; (j == 0 || !this.isOpaque(world, i, j, k)) && this.getY(j) >= 0; --j) {
         this.setBlockAndMetadata(world, i, j, k, this.baseBrickBlock, this.baseBrickMeta);
         this.setGrassToDirt(world, i, j - 1, k);
      }

   }

   protected void placeDwarfForge(World world, Random random, int i, int j, int k, int meta) {
      this.setBlockAndMetadata(world, i, j, k, LOTRMod.dwarvenForge, meta);
      TileEntity tileentity = this.getTileEntity(world, i, j, k);
      if (tileentity instanceof LOTRTileEntityDwarvenForge) {
         LOTRTileEntityDwarvenForge forge = (LOTRTileEntityDwarvenForge)tileentity;
         int fuelAmount = MathHelper.func_76136_a(random, 0, 4);
         if (fuelAmount > 0) {
            ItemStack fuel = new ItemStack(Items.field_151044_h, fuelAmount, 0);
            forge.func_70299_a(forge.fuelSlot, fuel);
         }
      }

   }
}
