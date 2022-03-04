package lotr.common.world.structure2;

import java.util.Random;
import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityHobbit;
import lotr.common.item.LOTRItemMug;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class LOTRWorldGenHobbitWindmill extends LOTRWorldGenStructureBase2 {
   private Block plankBlock;
   private int plankMeta;
   private Block woodBlock;
   private int woodMeta;
   private Block doorBlock;

   public LOTRWorldGenHobbitWindmill(boolean flag) {
      super(flag);
   }

   protected void setupRandomBlocks(Random random) {
      if (random.nextBoolean()) {
         this.woodBlock = Blocks.field_150364_r;
         this.woodMeta = 0;
         this.plankBlock = Blocks.field_150344_f;
         this.plankMeta = 0;
         this.doorBlock = Blocks.field_150466_ao;
      } else {
         this.woodBlock = LOTRMod.wood;
         this.woodMeta = 0;
         this.plankBlock = LOTRMod.planks;
         this.plankMeta = 0;
         this.doorBlock = LOTRMod.doorShirePine;
      }

   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, 5);
      this.setupRandomBlocks(random);
      int i1;
      int k1;
      int i2;
      if (this.restrictions) {
         for(i1 = -4; i1 <= 4; ++i1) {
            for(k1 = -4; k1 <= 4; ++k1) {
               i2 = this.getTopBlock(world, i1, k1) - 1;
               if (!this.isSurface(world, i1, i2, k1)) {
                  return false;
               }
            }
         }
      }

      int k2;
      Block fillBlock;
      int i1;
      int j2;
      for(i1 = -4; i1 <= 4; ++i1) {
         for(k1 = -4; k1 <= 4; ++k1) {
            i2 = Math.abs(i1);
            k2 = Math.abs(k1);
            if ((i2 < 3 || k2 <= 3) && (k2 < 3 || i2 <= 3)) {
               fillBlock = Blocks.field_150350_a;
               i1 = 0;
               if (i2 == 3 && k2 == 3) {
                  fillBlock = this.plankBlock;
                  i1 = this.plankMeta;
               } else if (i2 == 4 && k2 == 2 || i2 == 2 && k2 == 4) {
                  fillBlock = this.woodBlock;
                  i1 = this.woodMeta;
               } else if (i2 != 4 && k2 != 4) {
                  fillBlock = Blocks.field_150350_a;
               } else {
                  fillBlock = this.plankBlock;
                  i1 = this.plankMeta;
               }

               for(j2 = 4; (j2 >= 0 || !this.isOpaque(world, i1, j2, k1)) && this.getY(j2) >= 0; --j2) {
                  if (fillBlock == Blocks.field_150350_a) {
                     if (j2 != 4 && j2 > 0) {
                        this.setAir(world, i1, j2, k1);
                     } else {
                        this.setBlockAndMetadata(world, i1, j2, k1, this.plankBlock, this.plankMeta);
                        this.setGrassToDirt(world, i1, j2 - 1, k1);
                     }
                  } else {
                     this.setBlockAndMetadata(world, i1, j2, k1, fillBlock, i1);
                     this.setGrassToDirt(world, i1, j2 - 1, k1);
                  }
               }
            }
         }
      }

      for(i1 = -3; i1 <= 3; ++i1) {
         for(k1 = -3; k1 <= 3; ++k1) {
            i2 = Math.abs(i1);
            k2 = Math.abs(k1);
            if (i2 != 3 || k2 != 3) {
               fillBlock = Blocks.field_150350_a;
               i1 = 0;
               if (i2 == 3 && k2 == 3) {
                  fillBlock = this.plankBlock;
                  i1 = this.plankMeta;
               } else if (i2 == 3 && k2 == 2 || i2 == 2 && k2 == 3) {
                  fillBlock = this.woodBlock;
                  i1 = this.woodMeta;
               } else if (i2 != 3 && k2 != 3) {
                  fillBlock = Blocks.field_150350_a;
               } else {
                  fillBlock = this.plankBlock;
                  i1 = this.plankMeta;
               }

               for(j2 = 5; j2 <= 8; ++j2) {
                  if (fillBlock == Blocks.field_150350_a) {
                     this.setAir(world, i1, j2, k1);
                  } else {
                     this.setBlockAndMetadata(world, i1, j2, k1, fillBlock, i1);
                  }
               }
            }
         }
      }

      int j1;
      for(i1 = -2; i1 <= 2; ++i1) {
         for(k1 = -2; k1 <= 2; ++k1) {
            i2 = Math.abs(i1);
            k2 = Math.abs(k1);

            for(j1 = 9; j1 <= 12; ++j1) {
               if (i2 == 2 && k2 == 2) {
                  this.setBlockAndMetadata(world, i1, j1, k1, this.woodBlock, this.woodMeta);
               } else if (i2 != 2 && k2 != 2) {
                  this.setAir(world, i1, j1, k1);
               } else {
                  this.setBlockAndMetadata(world, i1, j1, k1, this.plankBlock, this.plankMeta);
               }
            }
         }
      }

      for(i1 = -1; i1 <= 1; ++i1) {
         for(k1 = -1; k1 <= 1; ++k1) {
            for(i2 = 11; i2 <= 12; ++i2) {
               this.setBlockAndMetadata(world, i1, i2, k1, this.plankBlock, this.plankMeta);
            }
         }
      }

      this.setBlockAndMetadata(world, 0, 10, 0, LOTRMod.chandelier, 2);
      int originX = 0;
      int originY = 13;
      int originZ = 0;
      int radius = 4;

      int i2;
      int slot;
      for(j1 = originX - radius; j1 <= originX + radius; ++j1) {
         for(i1 = originY - radius; i1 <= originY + radius; ++i1) {
            for(j2 = originZ - radius; j2 <= originZ + radius; ++j2) {
               i2 = j1 - originX;
               slot = i1 - originY;
               int k2 = j2 - originZ;
               int dist = i2 * i2 + slot * slot + k2 * k2;
               if (dist < radius * radius && i1 >= originY) {
                  this.setBlockAndMetadata(world, j1, i1, j2, LOTRMod.clayTileDyed, 13);
               }
            }
         }
      }

      this.setBlockAndMetadata(world, -3, 6, 0, LOTRMod.glassPane, 0);
      this.setBlockAndMetadata(world, 3, 6, 0, LOTRMod.glassPane, 0);
      this.setBlockAndMetadata(world, 0, 6, -3, LOTRMod.glassPane, 0);
      this.setBlockAndMetadata(world, 0, 6, 3, LOTRMod.glassPane, 0);
      this.placeFenceTorch(world, -2, 2, -3);
      this.placeFenceTorch(world, -2, 2, 3);
      this.placeFenceTorch(world, 2, 2, -3);
      this.placeFenceTorch(world, 2, 2, 3);
      this.placeFenceTorch(world, -3, 2, -2);
      this.placeFenceTorch(world, 3, 2, -2);
      this.placeFenceTorch(world, -3, 2, 2);
      this.placeFenceTorch(world, 3, 2, 2);
      this.setBlockAndMetadata(world, 0, 1, -4, this.doorBlock, 1);
      this.setBlockAndMetadata(world, 0, 2, -4, this.doorBlock, 8);
      this.setBlockAndMetadata(world, -3, 1, -1, Blocks.field_150407_cf, 0);
      this.setBlockAndMetadata(world, -3, 1, 0, Blocks.field_150407_cf, 0);
      this.setBlockAndMetadata(world, -2, 1, 0, Blocks.field_150407_cf, 0);
      this.setBlockAndMetadata(world, -3, 1, 1, Blocks.field_150407_cf, 0);
      this.setBlockAndMetadata(world, -2, 1, 1, Blocks.field_150407_cf, 0);
      this.setBlockAndMetadata(world, -3, 2, 1, Blocks.field_150407_cf, 0);

      for(j1 = 1; j1 <= 4; ++j1) {
         this.setBlockAndMetadata(world, 0, j1, 2, this.woodBlock, this.woodMeta);
         this.setBlockAndMetadata(world, 0, j1, 1, Blocks.field_150468_ap, 2);
      }

      this.setBlockAndMetadata(world, 1, 5, -2, Blocks.field_150324_C, 1);
      this.setBlockAndMetadata(world, 2, 5, -2, Blocks.field_150324_C, 9);
      this.setBlockAndMetadata(world, -2, 5, -2, Blocks.field_150342_X, 0);
      this.setBlockAndMetadata(world, -1, 5, -2, Blocks.field_150342_X, 0);
      this.setBlockAndMetadata(world, -2, 6, -2, Blocks.field_150342_X, 0);
      this.setBlockAndMetadata(world, -1, 6, -2, Blocks.field_150342_X, 0);
      this.setBlockAndMetadata(world, -2, 5, -1, Blocks.field_150462_ai, 0);
      this.setBlockAndMetadata(world, -2, 5, 1, this.plankBlock, this.plankMeta);
      this.setBlockAndMetadata(world, -2, 5, 2, this.plankBlock, this.plankMeta);
      this.setBlockAndMetadata(world, -2, 6, 1, LOTRWorldGenHobbitStructure.getRandomCakeBlock(random), 0);
      this.placeBarrel(world, random, -2, 6, 2, 4, LOTRFoods.HOBBIT_DRINK);
      this.setBlockAndMetadata(world, 2, 5, 1, LOTRMod.hobbitOven, 5);
      this.setBlockAndMetadata(world, 2, 5, 2, LOTRMod.hobbitOven, 5);
      this.placeChest(world, random, -2, 5, 0, 4, LOTRChestContents.HOBBIT_HOLE_STUDY);
      this.placeChest(world, random, 2, 5, 0, 5, LOTRChestContents.HOBBIT_HOLE_LARDER);
      if (random.nextInt(20) == 0) {
         TileEntity te = this.getTileEntity(world, 2, 5, 0);
         if (te instanceof IInventory) {
            IInventory chest = (IInventory)te;
            ItemStack hooch = new ItemStack(LOTRMod.mugLemonLiqueur);
            LOTRItemMug.setStrengthMeta(hooch, 1);
            LOTRItemMug.setVessel(hooch, LOTRItemMug.Vessel.MUG, true);
            hooch.func_151001_c("Bad Windmill Hooch");
            NBTTagList loreTags = hooch.func_77978_p().func_74775_l("display").func_150295_c("Lore", 8);
            loreTags.func_74742_a(new NBTTagString("Really nothing compared to the Spoons Hooch."));
            hooch.func_77978_p().func_74775_l("display").func_74782_a("Lore", loreTags);
            slot = random.nextInt(chest.func_70302_i_());
            chest.func_70299_a(slot, hooch);
         }
      }

      this.setBlockAndMetadata(world, 0, 10, -3, this.plankBlock, this.plankMeta);
      this.setBlockAndMetadata(world, 0, 10, -4, Blocks.field_150325_L, 15);

      for(j1 = 7; j1 <= 13; ++j1) {
         for(i1 = -3; i1 <= 3; ++i1) {
            j2 = Math.abs(j1 - 10);
            i2 = Math.abs(i1);
            if (j2 == i2 && j2 != 0) {
               this.setBlockAndMetadata(world, i1, j1, -4, Blocks.field_150325_L, 0);
            }
         }
      }

      LOTREntityHobbit hobbit = new LOTREntityHobbit(world);
      this.spawnNPCAndSetHome(hobbit, world, 0, 1, 0, 8);
      return true;
   }

   private void placeFenceTorch(World world, int i, int j, int k) {
      this.setBlockAndMetadata(world, i, j, k, Blocks.field_150422_aJ, 0);
      this.setBlockAndMetadata(world, i, j + 1, k, Blocks.field_150478_aa, 5);
   }
}
