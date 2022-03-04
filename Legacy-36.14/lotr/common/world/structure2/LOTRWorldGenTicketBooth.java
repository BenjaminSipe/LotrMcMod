package lotr.common.world.structure2;

import com.google.common.math.IntMath;
import java.util.Random;
import lotr.common.entity.animal.LOTREntityLion;
import lotr.common.world.map.LOTRFixedStructures;
import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.world.World;

public class LOTRWorldGenTicketBooth extends LOTRWorldGenEasterlingStructureTown {
   public LOTRWorldGenTicketBooth(boolean flag) {
      super(flag);
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, 3, 3);
      this.setupRandomBlocks(random);
      int woolType = 14;
      Block woodBlock = Blocks.field_150344_f;
      int woodMeta = 0;
      Block stairBlock = Blocks.field_150476_ad;
      Block trapdoorBlock = Blocks.field_150415_aT;
      Block seatBlock = Blocks.field_150476_ad;
      Block fillerBlock = this.getBiome(world, 0, 0).field_76753_B;

      int i1;
      int j1;
      int lootAmount;
      for(i1 = -2; i1 <= 15; ++i1) {
         for(j1 = -2; j1 <= 9; ++j1) {
            if (j1 < 5 || i1 > 1) {
               this.setBlockAndMetadata(world, j1, 0, i1, Blocks.field_150347_e, 0);

               for(lootAmount = -1; !this.isOpaque(world, j1, lootAmount, i1) && this.getY(lootAmount) >= 0; --lootAmount) {
                  this.setBlockAndMetadata(world, j1, lootAmount, i1, fillerBlock, 0);
                  this.setGrassToDirt(world, j1, lootAmount - 1, i1);
               }

               for(lootAmount = 1; lootAmount <= 3; ++lootAmount) {
                  this.setBlockAndMetadata(world, j1, lootAmount, i1, woodBlock, woodMeta);
               }

               if (i1 > 2) {
                  for(lootAmount = 4; lootAmount <= 5; ++lootAmount) {
                     this.setBlockAndMetadata(world, j1, lootAmount, i1, woodBlock, woodMeta);
                  }
               }

               this.setBlockAndMetadata(world, j1, 2, i1, Blocks.field_150417_aV, 0);
            }
         }
      }

      for(i1 = 3; i1 <= 14; ++i1) {
         for(j1 = -1; j1 <= 8; ++j1) {
            for(lootAmount = 1; lootAmount <= 4; ++lootAmount) {
               this.setAir(world, j1, lootAmount, i1);
            }

            if (i1 > 9 || IntMath.mod(i1, 2) != 1) {
               this.setBlockAndMetadata(world, j1, 0, i1, Blocks.field_150325_L, woolType);
            }

            if (i1 <= 9 && IntMath.mod(i1, 2) == 1 && j1 != 3 && j1 != 4) {
               this.setBlockAndMetadata(world, j1, 1, i1, seatBlock, 3);
            }

            if (j1 == 3 || j1 == 4) {
               this.setBlockAndMetadata(world, j1, 0, i1, Blocks.field_150347_e, 0);
            }
         }
      }

      for(i1 = 0; i1 <= 4; ++i1) {
         for(j1 = 2; j1 <= 5; ++j1) {
            if (i1 >= 1 && i1 <= 3 && j1 >= 3 && j1 <= 4) {
               this.setBlockAndMetadata(world, j1, i1, 14, Blocks.field_150406_ce, 15);
            } else {
               this.setBlockAndMetadata(world, j1, i1, 14, Blocks.field_150405_ch, 0);
            }
         }
      }

      for(i1 = -2; i1 <= 2; ++i1) {
         for(j1 = 1; j1 <= 2; ++j1) {
            this.setAir(world, 3, j1, i1);
         }
      }

      for(i1 = -1; i1 <= 0; ++i1) {
         for(j1 = 1; j1 <= 2; ++j1) {
            for(lootAmount = -1; lootAmount <= 1; ++lootAmount) {
               this.setAir(world, lootAmount, j1, i1);
               if (i1 == -1 && j1 == 2) {
                  this.setAir(world, lootAmount, j1, i1 - 1);
               }
            }
         }
      }

      this.setBlockAndMetadata(world, -1, 2, 0, Blocks.field_150478_aa, 2);
      this.setBlockAndMetadata(world, 1, 2, 0, Blocks.field_150478_aa, 1);
      this.setBlockAndMetadata(world, 0, 1, -2, Blocks.field_150422_aJ, 0);
      this.setBlockAndMetadata(world, -1, 2, -2, Blocks.field_150410_aZ, 0);
      this.setBlockAndMetadata(world, 1, 2, -2, Blocks.field_150410_aZ, 0);

      for(i1 = 4; i1 <= 14; ++i1) {
         this.setBlockAndMetadata(world, -1, 4, i1, stairBlock, 4);
         this.setBlockAndMetadata(world, 8, 4, i1, stairBlock, 5);
      }

      for(i1 = 0; i1 <= 7; ++i1) {
         this.setBlockAndMetadata(world, i1, 4, 3, stairBlock, 7);
         if (i1 <= 1 || i1 >= 6) {
            this.setBlockAndMetadata(world, i1, 4, 14, stairBlock, 6);
         }
      }

      for(i1 = 0; i1 <= 4; ++i1) {
         Block block = woodBlock;
         int meta = woodMeta;
         if (i1 == 2) {
            block = Blocks.field_150426_aN;
            meta = 0;
         }

         this.setBlockAndMetadata(world, -1, i1, 3, block, meta);
         this.setBlockAndMetadata(world, -1, i1, 14, block, meta);
         this.setBlockAndMetadata(world, 8, i1, 3, block, meta);
         this.setBlockAndMetadata(world, 8, i1, 14, block, meta);
      }

      for(i1 = -2; i1 <= 4; ++i1) {
         if (i1 == 3) {
            this.setBlockAndMetadata(world, i1, 3, -3, woodBlock, woodMeta);
         } else {
            this.setBlockAndMetadata(world, i1, 3, -3, stairBlock, 2);
         }
      }

      for(i1 = -2; i1 <= 3; ++i1) {
         this.setBlockAndMetadata(world, -3, 3, i1, stairBlock, 1);
      }

      for(i1 = -2; i1 <= 0; ++i1) {
         this.setBlockAndMetadata(world, 5, 3, i1, stairBlock, 0);
      }

      this.generateSupports(world, 5, 3, 1, stairBlock, 2, woodBlock, woodMeta);

      for(i1 = 6; i1 <= 9; ++i1) {
         this.setBlockAndMetadata(world, i1, 3, 1, stairBlock, 2);
      }

      for(i1 = -2; i1 <= 9; ++i1) {
         this.setBlockAndMetadata(world, i1, 5, 2, stairBlock, 2);
         this.setBlockAndMetadata(world, i1, 5, 16, stairBlock, 3);
      }

      for(i1 = 3; i1 <= 15; ++i1) {
         this.setBlockAndMetadata(world, -3, 5, i1, stairBlock, 1);
         this.setBlockAndMetadata(world, 10, 5, i1, stairBlock, 0);
      }

      this.setBlockAndMetadata(world, 10, 3, 2, stairBlock, 0);
      this.setBlockAndMetadata(world, 10, 3, 3, stairBlock, 0);
      this.generateSupports(world, -3, 3, -3, stairBlock, 2, woodBlock, woodMeta);
      this.generateSupports(world, 5, 3, -3, stairBlock, 2, woodBlock, woodMeta);
      this.generateSupports(world, 10, 3, 1, stairBlock, 2, woodBlock, woodMeta);
      this.generateSupports(world, 10, 3, 4, stairBlock, 3, woodBlock, woodMeta);
      this.generateSupports(world, -3, 3, 4, stairBlock, 3, woodBlock, woodMeta);
      this.setBlockAndMetadata(world, -3, 5, 2, stairBlock, 2);
      this.setBlockAndMetadata(world, 10, 5, 2, stairBlock, 2);
      this.generateSupports(world, -3, 5, 16, stairBlock, 3, woodBlock, woodMeta);
      this.generateSupports(world, 10, 5, 16, stairBlock, 3, woodBlock, woodMeta);
      this.setBlockAndMetadata(world, 3, 1, -2, Blocks.field_150466_ao, 1);
      this.setBlockAndMetadata(world, 3, 2, -2, Blocks.field_150466_ao, 8);

      for(i1 = 5; i1 <= 12; ++i1) {
         if (IntMath.mod(i1, 3) != 1) {
            this.setBlockAndMetadata(world, -1, 2, i1, Blocks.field_150478_aa, 2);
            this.setBlockAndMetadata(world, 8, 2, i1, Blocks.field_150478_aa, 1);
         }
      }

      for(i1 = 1; i1 <= 6; ++i1) {
         if (i1 <= 1 || i1 >= 6) {
            this.setBlockAndMetadata(world, i1, 2, 14, Blocks.field_150478_aa, 4);
         }

         if (i1 <= 2 || i1 >= 5) {
            this.setBlockAndMetadata(world, i1, 2, 3, Blocks.field_150478_aa, 3);
         }
      }

      this.setBlockAndMetadata(world, -2, 2, -3, Blocks.field_150478_aa, 4);
      this.setBlockAndMetadata(world, 2, 2, -3, Blocks.field_150478_aa, 4);
      this.setBlockAndMetadata(world, 4, 2, -3, Blocks.field_150478_aa, 4);
      this.placeSign(world, 3, 3, -4, Blocks.field_150444_as, 2, new String[]{"---------------", "Now showing:", "The Lion King", "---------------"});
      LOTREntityLion lion = new LOTREntityLion(world);
      lion.func_94058_c("Ticket Lion");
      lion.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(1.0E8D);
      lion.func_70606_j(lion.func_110138_aP());
      this.spawnNPCAndSetHome(lion, world, 0, 1, -1, 4);
      this.setBlockAndMetadata(world, 0, 1, 2, Blocks.field_150486_ae, 3);
      TileEntityChest chest = (TileEntityChest)this.getTileEntity(world, 0, 1, 2);
      if (chest != null) {
         lootAmount = 2 + random.nextInt(4);

         for(int l = 0; l < lootAmount; ++l) {
            chest.func_70299_a(random.nextInt(chest.func_70302_i_()), this.getBasicLoot(random));
         }
      }

      this.setBlockAndMetadata(world, 0, 2, 2, trapdoorBlock, 1);
      this.placeSign(world, 3, 2, 13, Blocks.field_150444_as, 2, new String[]{"", "Showings", "postponed", ""});
      this.placeSign(world, 4, 2, 13, Blocks.field_150444_as, 2, new String[]{"", "until further", "notice.", ""});
      return true;
   }

   private ItemStack getBasicLoot(Random random) {
      int i = random.nextInt(11);
      switch(i) {
      case 0:
      default:
         return new ItemStack(Items.field_151055_y, 2 + random.nextInt(4));
      case 1:
         return new ItemStack(Items.field_151121_aF, 1 + random.nextInt(3));
      case 2:
         return new ItemStack(Items.field_151122_aG, 1 + random.nextInt(2));
      case 3:
         return new ItemStack(Items.field_151025_P, 3 + random.nextInt(2));
      case 4:
         return new ItemStack(Items.field_151111_aL);
      case 5:
         return new ItemStack(Items.field_151074_bl, 2 + random.nextInt(6));
      case 6:
         return new ItemStack(Items.field_151034_e, 1 + random.nextInt(3));
      case 7:
         return new ItemStack(Items.field_151007_F, 2 + random.nextInt(2));
      case 8:
         return new ItemStack(Items.field_151054_z, 1 + random.nextInt(4));
      case 9:
         return new ItemStack(Items.field_151106_aX, 1 + random.nextInt(3));
      case 10:
         return new ItemStack(Items.field_151044_h, 1 + random.nextInt(2));
      }
   }

   private void generateSupports(World world, int i, int j, int k, Block stairBlock, int stairMeta, Block woodBlock, int woodMeta) {
      this.setBlockAndMetadata(world, i, j, k, stairBlock, stairMeta);

      for(int j1 = -1; !this.isOpaque(world, i, j + j1, k) && this.getY(j + j1) >= 0; --j1) {
         Block block = Blocks.field_150422_aJ;
         int meta = 0;
         Block below = world.func_147439_a(i, j + j1, k);
         if (below.func_149688_o().func_76224_d()) {
            block = Blocks.field_150344_f;
            meta = woodMeta;
         }

         this.setBlockAndMetadata(world, i, j + j1, k, block, meta);
      }

   }

   public static boolean generatesAt(World world, int i, int k) {
      return LOTRFixedStructures.generatesAtMapImageCoords(i, k, 1583, 2527);
   }
}
