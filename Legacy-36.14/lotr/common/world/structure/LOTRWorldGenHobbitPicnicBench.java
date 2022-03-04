package lotr.common.world.structure;

import java.util.Random;
import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityHobbit;
import lotr.common.world.biome.LOTRBiomeGenShire;
import net.minecraft.block.Block;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenHobbitPicnicBench extends LOTRWorldGenStructureBase {
   private Block baseBlock;
   private int baseMeta;
   private Block stairBlock;
   private Block halfBlock;
   private int halfMeta;
   private Block plateBlock;

   public LOTRWorldGenHobbitPicnicBench(boolean flag) {
      super(flag);
   }

   public boolean func_76484_a(World world, Random random, int i, int j, int k) {
      if (this.restrictions && !(world.func_72807_a(i, k) instanceof LOTRBiomeGenShire)) {
         return false;
      } else {
         int randomWood = random.nextInt(4);
         switch(randomWood) {
         case 0:
            this.baseBlock = Blocks.field_150344_f;
            this.baseMeta = 0;
            this.stairBlock = Blocks.field_150476_ad;
            this.halfBlock = Blocks.field_150376_bx;
            this.halfMeta = 0;
            break;
         case 1:
            this.baseBlock = Blocks.field_150344_f;
            this.baseMeta = 1;
            this.stairBlock = Blocks.field_150485_bF;
            this.halfBlock = Blocks.field_150376_bx;
            this.halfMeta = 1;
            break;
         case 2:
            this.baseBlock = Blocks.field_150344_f;
            this.baseMeta = 2;
            this.stairBlock = Blocks.field_150487_bG;
            this.halfBlock = Blocks.field_150376_bx;
            this.halfMeta = 2;
            break;
         case 3:
            this.baseBlock = LOTRMod.planks;
            this.baseMeta = 0;
            this.stairBlock = LOTRMod.stairsShirePine;
            this.halfBlock = LOTRMod.woodSlabSingle;
            this.halfMeta = 0;
         }

         this.plateBlock = random.nextBoolean() ? LOTRMod.woodPlateBlock : LOTRMod.ceramicPlateBlock;
         int rotation = random.nextInt(4);
         if (!this.restrictions && this.usingPlayer != null) {
            rotation = this.usingPlayerRotation();
         }

         switch(rotation) {
         case 0:
            return this.generateFacingSouth(world, random, i, j, k);
         case 1:
            return this.generateFacingWest(world, random, i, j, k);
         case 2:
            return this.generateFacingNorth(world, random, i, j, k);
         case 3:
            return this.generateFacingEast(world, random, i, j, k);
         default:
            return false;
         }
      }
   }

   private boolean generateFacingSouth(World world, Random random, int i, int j, int k) {
      int k1;
      int i1;
      if (this.restrictions) {
         for(k1 = k; k1 <= k + 5; ++k1) {
            for(i1 = i + 2; i1 >= i - 3; --i1) {
               if (world.func_147439_a(i1, j - 1, k1) != Blocks.field_150349_c || !world.func_147437_c(i1, j, k1) || !world.func_147437_c(i1, j + 1, k1)) {
                  return false;
               }
            }
         }
      }

      for(k1 = k; k1 <= k + 5; ++k1) {
         for(i1 = i + 2; i1 >= i - 3; --i1) {
            this.func_150516_a(world, i1, j, k1, Blocks.field_150350_a, 0);
            this.func_150516_a(world, i1, j + 1, k1, Blocks.field_150350_a, 0);
         }
      }

      for(k1 = k; k1 <= k + 5; ++k1) {
         for(i1 = i; i1 >= i - 1; --i1) {
            if (k1 != k && k1 != k + 5) {
               this.func_150516_a(world, i1, j, k1, this.halfBlock, this.halfMeta | 8);
            } else {
               this.func_150516_a(world, i1, j, k1, this.baseBlock, this.baseMeta);
            }

            this.placePlate(world, random, i1, j + 1, k1, this.plateBlock, LOTRFoods.HOBBIT);
         }

         this.func_150516_a(world, i - 3, j, k1, this.stairBlock, 1);
         this.func_150516_a(world, i + 2, j, k1, this.stairBlock, 0);
      }

      k1 = 2 + random.nextInt(3);

      for(i1 = 0; i1 < k1; ++i1) {
         LOTREntityHobbit hobbit = new LOTREntityHobbit(world);
         int hobbitX = i + 1 - random.nextInt(2) * 3;
         int hobbitZ = k + random.nextInt(6);
         hobbit.func_70012_b((double)hobbitX + 0.5D, (double)j, (double)hobbitZ + 0.5D, 0.0F, 0.0F);
         hobbit.func_110171_b(hobbitX, j, hobbitZ, 16);
         hobbit.func_110161_a((IEntityLivingData)null);
         hobbit.isNPCPersistent = true;
         world.func_72838_d(hobbit);
      }

      return true;
   }

   private boolean generateFacingWest(World world, Random random, int i, int j, int k) {
      int i1;
      int k1;
      if (this.restrictions) {
         for(i1 = i; i1 >= i - 5; --i1) {
            for(k1 = k + 2; k1 >= k - 3; --k1) {
               if (world.func_147439_a(i1, j - 1, k1) != Blocks.field_150349_c || !world.func_147437_c(i1, j, k1) || !world.func_147437_c(i1, j + 1, k1)) {
                  return false;
               }
            }
         }
      }

      for(i1 = i; i1 >= i - 5; --i1) {
         for(k1 = k + 2; k1 >= k - 3; --k1) {
            this.func_150516_a(world, i1, j, k1, Blocks.field_150350_a, 0);
            this.func_150516_a(world, i1, j + 1, k1, Blocks.field_150350_a, 0);
         }
      }

      for(i1 = i; i1 >= i - 5; --i1) {
         for(k1 = k; k1 >= k - 1; --k1) {
            if (i1 != i && i1 != i - 5) {
               this.func_150516_a(world, i1, j, k1, this.halfBlock, this.halfMeta | 8);
            } else {
               this.func_150516_a(world, i1, j, k1, this.baseBlock, this.baseMeta);
            }

            this.placePlate(world, random, i1, j + 1, k1, this.plateBlock, LOTRFoods.HOBBIT);
         }

         this.func_150516_a(world, i1, j, k - 3, this.stairBlock, 3);
         this.func_150516_a(world, i1, j, k + 2, this.stairBlock, 2);
      }

      i1 = 2 + random.nextInt(3);

      for(k1 = 0; k1 < i1; ++k1) {
         LOTREntityHobbit hobbit = new LOTREntityHobbit(world);
         int hobbitX = i - random.nextInt(6);
         int hobbitZ = k + 1 - random.nextInt(2) * 3;
         hobbit.func_70012_b((double)hobbitX + 0.5D, (double)j, (double)hobbitZ + 0.5D, 0.0F, 0.0F);
         hobbit.func_110171_b(hobbitX, j, hobbitZ, 16);
         hobbit.func_110161_a((IEntityLivingData)null);
         hobbit.isNPCPersistent = true;
         world.func_72838_d(hobbit);
      }

      return true;
   }

   private boolean generateFacingNorth(World world, Random random, int i, int j, int k) {
      int k1;
      int i1;
      if (this.restrictions) {
         for(k1 = k; k1 >= k - 5; --k1) {
            for(i1 = i - 2; i1 <= i + 3; ++i1) {
               if (world.func_147439_a(i1, j - 1, k1) != Blocks.field_150349_c || !world.func_147437_c(i1, j, k1) || !world.func_147437_c(i1, j + 1, k1)) {
                  return false;
               }
            }
         }
      }

      for(k1 = k; k1 >= k - 5; --k1) {
         for(i1 = i - 2; i1 <= i + 3; ++i1) {
            this.func_150516_a(world, i1, j, k1, Blocks.field_150350_a, 0);
            this.func_150516_a(world, i1, j + 1, k1, Blocks.field_150350_a, 0);
         }
      }

      for(k1 = k; k1 >= k - 5; --k1) {
         for(i1 = i; i1 <= i + 1; ++i1) {
            if (k1 != k && k1 != k - 5) {
               this.func_150516_a(world, i1, j, k1, this.halfBlock, this.halfMeta | 8);
            } else {
               this.func_150516_a(world, i1, j, k1, this.baseBlock, this.baseMeta);
            }

            this.placePlate(world, random, i1, j + 1, k1, this.plateBlock, LOTRFoods.HOBBIT);
         }

         this.func_150516_a(world, i - 2, j, k1, this.stairBlock, 1);
         this.func_150516_a(world, i + 3, j, k1, this.stairBlock, 0);
      }

      k1 = 2 + random.nextInt(3);

      for(i1 = 0; i1 < k1; ++i1) {
         LOTREntityHobbit hobbit = new LOTREntityHobbit(world);
         int hobbitX = i - 1 + random.nextInt(2) * 3;
         int hobbitZ = k - random.nextInt(6);
         hobbit.func_70012_b((double)hobbitX + 0.5D, (double)j, (double)hobbitZ + 0.5D, 0.0F, 0.0F);
         hobbit.func_110171_b(hobbitX, j, hobbitZ, 16);
         hobbit.func_110161_a((IEntityLivingData)null);
         hobbit.isNPCPersistent = true;
         world.func_72838_d(hobbit);
      }

      return true;
   }

   private boolean generateFacingEast(World world, Random random, int i, int j, int k) {
      int i1;
      int k1;
      if (this.restrictions) {
         for(i1 = i; i1 <= i + 5; ++i1) {
            for(k1 = k - 2; k1 <= k + 3; ++k1) {
               if (world.func_147439_a(i1, j - 1, k1) != Blocks.field_150349_c || !world.func_147437_c(i1, j, k1) || !world.func_147437_c(i1, j + 1, k1)) {
                  return false;
               }
            }
         }
      }

      for(i1 = i; i1 <= i + 5; ++i1) {
         for(k1 = k - 2; k1 <= k + 3; ++k1) {
            this.func_150516_a(world, i1, j, k1, Blocks.field_150350_a, 0);
            this.func_150516_a(world, i1, j + 1, k1, Blocks.field_150350_a, 0);
         }
      }

      for(i1 = i; i1 <= i + 5; ++i1) {
         for(k1 = k; k1 <= k + 1; ++k1) {
            if (i1 != i && i1 != i + 5) {
               this.func_150516_a(world, i1, j, k1, this.halfBlock, this.halfMeta | 8);
            } else {
               this.func_150516_a(world, i1, j, k1, this.baseBlock, this.baseMeta);
            }

            this.placePlate(world, random, i1, j + 1, k1, this.plateBlock, LOTRFoods.HOBBIT);
         }

         this.func_150516_a(world, i1, j, k - 2, this.stairBlock, 3);
         this.func_150516_a(world, i1, j, k + 3, this.stairBlock, 2);
      }

      i1 = 2 + random.nextInt(3);

      for(k1 = 0; k1 < i1; ++k1) {
         LOTREntityHobbit hobbit = new LOTREntityHobbit(world);
         int hobbitX = i + random.nextInt(6);
         int hobbitZ = k - 1 + random.nextInt(2) * 3;
         hobbit.func_70012_b((double)hobbitX + 0.5D, (double)j, (double)hobbitZ + 0.5D, 0.0F, 0.0F);
         hobbit.func_110171_b(hobbitX, j, hobbitZ, 16);
         hobbit.func_110161_a((IEntityLivingData)null);
         hobbit.isNPCPersistent = true;
         world.func_72838_d(hobbit);
      }

      return true;
   }
}
