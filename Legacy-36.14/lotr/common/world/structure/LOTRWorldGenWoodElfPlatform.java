package lotr.common.world.structure;

import java.util.Random;
import lotr.common.LOTRMod;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.npc.LOTREntityWoodElf;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenWoodElfPlatform extends LOTRWorldGenStructureBase {
   public LOTRWorldGenWoodElfPlatform(boolean flag) {
      super(flag);
   }

   public boolean func_76484_a(World world, Random random, int i, int j, int k) {
      int rotation = -1;
      if (this.restrictions) {
         rotation = random.nextInt(4);
         switch(rotation) {
         case 0:
            k -= 3;
            break;
         case 1:
            i += 3;
            break;
         case 2:
            k += 3;
            break;
         case 3:
            i -= 3;
         }
      } else if (this.usingPlayer != null) {
         rotation = this.usingPlayerRotation();
      }

      boolean flag = false;
      switch(rotation) {
      case 0:
         flag = this.generateFacingSouth(world, random, i, j, k);
         break;
      case 1:
         flag = this.generateFacingWest(world, random, i, j, k);
         break;
      case 2:
         flag = this.generateFacingNorth(world, random, i, j, k);
         break;
      case 3:
         flag = this.generateFacingEast(world, random, i, j, k);
      }

      if (flag) {
         LOTREntityNPCRespawner respawner = new LOTREntityNPCRespawner(world);
         respawner.setSpawnClass(LOTREntityWoodElf.class);
         respawner.setCheckRanges(8, -8, 8, 2);
         respawner.setSpawnRanges(3, -2, 2, 8);
         this.placeNPCRespawner(respawner, world, i, j + 1, k);
      }

      return false;
   }

   private boolean generateFacingSouth(World world, Random random, int i, int j, int k) {
      int j1;
      int j1;
      int k1;
      if (this.restrictions) {
         for(j1 = i - 2; j1 <= i + 2; ++j1) {
            for(j1 = j; j1 <= j + 4; ++j1) {
               if (!world.func_147439_a(j1, j1, k + 1).isWood(world, j1, j1, k + 1)) {
                  return false;
               }

               for(k1 = k; k1 >= k - 3; --k1) {
                  if (!world.func_147437_c(j1, j1, k1)) {
                     return false;
                  }
               }
            }
         }
      } else {
         for(j1 = i - 2; j1 <= i + 2; ++j1) {
            for(j1 = j; j1 <= j + 4; ++j1) {
               for(k1 = k; k1 >= k - 3; --k1) {
                  this.func_150516_a(world, j1, j1, k1, Blocks.field_150350_a, 0);
               }
            }
         }
      }

      for(j1 = i - 1; j1 <= i + 1; ++j1) {
         for(j1 = k; j1 >= k - 2; --j1) {
            this.func_150516_a(world, j1, j, j1, LOTRMod.planks2, 13);
         }
      }

      for(j1 = i - 2; j1 <= i + 2; ++j1) {
         this.func_150516_a(world, j1, j, k - 3, LOTRMod.stairsGreenOak, 6);
         this.func_150516_a(world, j1, j + 1, k - 3, LOTRMod.fence2, 13);
      }

      for(j1 = k; j1 >= k - 2; --j1) {
         this.func_150516_a(world, i - 2, j, j1, LOTRMod.stairsGreenOak, 4);
         this.func_150516_a(world, i + 2, j, j1, LOTRMod.stairsGreenOak, 5);
         this.func_150516_a(world, i - 2, j + 1, j1, LOTRMod.fence2, 13);
         this.func_150516_a(world, i + 2, j + 1, j1, LOTRMod.fence2, 13);
      }

      this.func_150516_a(world, i - 2, j + 2, k, LOTRMod.fence2, 13);
      this.func_150516_a(world, i - 2, j + 3, k, LOTRMod.woodElvenTorch, 5);
      this.func_150516_a(world, i + 2, j + 2, k, LOTRMod.fence2, 13);
      this.func_150516_a(world, i + 2, j + 3, k, LOTRMod.woodElvenTorch, 5);
      this.func_150516_a(world, i - 2, j + 2, k - 3, LOTRMod.fence2, 13);
      this.func_150516_a(world, i - 2, j + 3, k - 3, LOTRMod.woodElvenTorch, 5);
      this.func_150516_a(world, i + 2, j + 2, k - 3, LOTRMod.fence2, 13);
      this.func_150516_a(world, i + 2, j + 3, k - 3, LOTRMod.woodElvenTorch, 5);

      for(j1 = j; j1 >= 0 && LOTRMod.isOpaque(world, i, j1, k + 1) && (j1 >= j || !LOTRMod.isOpaque(world, i, j1, k)); --j1) {
         this.func_150516_a(world, i, j1, k, Blocks.field_150468_ap, 2);
      }

      return true;
   }

   private boolean generateFacingWest(World world, Random random, int i, int j, int k) {
      int j1;
      int j1;
      int i1;
      if (this.restrictions) {
         for(j1 = k - 2; j1 <= k + 2; ++j1) {
            for(j1 = j; j1 <= j + 4; ++j1) {
               if (!world.func_147439_a(i - 1, j1, j1).isWood(world, i - 1, j1, j1)) {
                  return false;
               }

               for(i1 = i; i1 <= i + 3; ++i1) {
                  if (!world.func_147437_c(i1, j1, j1)) {
                     return false;
                  }
               }
            }
         }
      } else {
         for(j1 = k - 2; j1 <= k + 2; ++j1) {
            for(j1 = j; j1 <= j + 4; ++j1) {
               for(i1 = i; i1 <= i + 3; ++i1) {
                  this.func_150516_a(world, i1, j1, j1, Blocks.field_150350_a, 0);
               }
            }
         }
      }

      for(j1 = k - 1; j1 <= k + 1; ++j1) {
         for(j1 = i; j1 <= i + 2; ++j1) {
            this.func_150516_a(world, j1, j, j1, LOTRMod.planks2, 13);
         }
      }

      for(j1 = k - 2; j1 <= k + 2; ++j1) {
         this.func_150516_a(world, i + 3, j, j1, LOTRMod.stairsGreenOak, 5);
         this.func_150516_a(world, i + 3, j + 1, j1, LOTRMod.fence2, 13);
      }

      for(j1 = i; j1 <= i + 2; ++j1) {
         this.func_150516_a(world, j1, j, k - 2, LOTRMod.stairsGreenOak, 6);
         this.func_150516_a(world, j1, j, k + 2, LOTRMod.stairsGreenOak, 7);
         this.func_150516_a(world, j1, j + 1, k - 2, LOTRMod.fence2, 13);
         this.func_150516_a(world, j1, j + 1, k + 2, LOTRMod.fence2, 13);
      }

      this.func_150516_a(world, i, j + 2, k - 2, LOTRMod.fence2, 13);
      this.func_150516_a(world, i, j + 3, k - 2, LOTRMod.woodElvenTorch, 5);
      this.func_150516_a(world, i, j + 2, k + 2, LOTRMod.fence2, 13);
      this.func_150516_a(world, i, j + 3, k + 2, LOTRMod.woodElvenTorch, 5);
      this.func_150516_a(world, i + 3, j + 2, k - 2, LOTRMod.fence2, 13);
      this.func_150516_a(world, i + 3, j + 3, k - 2, LOTRMod.woodElvenTorch, 5);
      this.func_150516_a(world, i + 3, j + 2, k + 2, LOTRMod.fence2, 13);
      this.func_150516_a(world, i + 3, j + 3, k + 2, LOTRMod.woodElvenTorch, 5);

      for(j1 = j; j1 >= 0 && LOTRMod.isOpaque(world, i - 1, j1, k) && (j1 >= j || !LOTRMod.isOpaque(world, i, j1, k)); --j1) {
         this.func_150516_a(world, i, j1, k, Blocks.field_150468_ap, 5);
      }

      return true;
   }

   private boolean generateFacingNorth(World world, Random random, int i, int j, int k) {
      int j1;
      int j1;
      int k1;
      if (this.restrictions) {
         for(j1 = i - 2; j1 <= i + 2; ++j1) {
            for(j1 = j; j1 <= j + 4; ++j1) {
               if (!world.func_147439_a(j1, j1, k - 1).isWood(world, j1, j1, k - 1)) {
                  return false;
               }

               for(k1 = k; k1 <= k + 3; ++k1) {
                  if (!world.func_147437_c(j1, j1, k1)) {
                     return false;
                  }
               }
            }
         }
      } else {
         for(j1 = i - 2; j1 <= i + 2; ++j1) {
            for(j1 = j; j1 <= j + 4; ++j1) {
               for(k1 = k; k1 <= k + 3; ++k1) {
                  this.func_150516_a(world, j1, j1, k1, Blocks.field_150350_a, 0);
               }
            }
         }
      }

      for(j1 = i - 1; j1 <= i + 1; ++j1) {
         for(j1 = k; j1 <= k + 2; ++j1) {
            this.func_150516_a(world, j1, j, j1, LOTRMod.planks2, 13);
         }
      }

      for(j1 = i - 2; j1 <= i + 2; ++j1) {
         this.func_150516_a(world, j1, j, k + 3, LOTRMod.stairsGreenOak, 7);
         this.func_150516_a(world, j1, j + 1, k + 3, LOTRMod.fence2, 13);
      }

      for(j1 = k; j1 <= k + 2; ++j1) {
         this.func_150516_a(world, i - 2, j, j1, LOTRMod.stairsGreenOak, 4);
         this.func_150516_a(world, i + 2, j, j1, LOTRMod.stairsGreenOak, 5);
         this.func_150516_a(world, i - 2, j + 1, j1, LOTRMod.fence2, 13);
         this.func_150516_a(world, i + 2, j + 1, j1, LOTRMod.fence2, 13);
      }

      this.func_150516_a(world, i - 2, j + 2, k, LOTRMod.fence2, 13);
      this.func_150516_a(world, i - 2, j + 3, k, LOTRMod.woodElvenTorch, 5);
      this.func_150516_a(world, i + 2, j + 2, k, LOTRMod.fence2, 13);
      this.func_150516_a(world, i + 2, j + 3, k, LOTRMod.woodElvenTorch, 5);
      this.func_150516_a(world, i - 2, j + 2, k + 3, LOTRMod.fence2, 13);
      this.func_150516_a(world, i - 2, j + 3, k + 3, LOTRMod.woodElvenTorch, 5);
      this.func_150516_a(world, i + 2, j + 2, k + 3, LOTRMod.fence2, 13);
      this.func_150516_a(world, i + 2, j + 3, k + 3, LOTRMod.woodElvenTorch, 5);

      for(j1 = j; j1 >= 0 && LOTRMod.isOpaque(world, i, j1, k - 1) && (j1 >= j || !LOTRMod.isOpaque(world, i, j1, k)); --j1) {
         this.func_150516_a(world, i, j1, k, Blocks.field_150468_ap, 3);
      }

      return true;
   }

   private boolean generateFacingEast(World world, Random random, int i, int j, int k) {
      int j1;
      int j1;
      int i1;
      if (this.restrictions) {
         for(j1 = k - 2; j1 <= k + 2; ++j1) {
            for(j1 = j; j1 <= j + 4; ++j1) {
               if (!world.func_147439_a(i + 1, j1, j1).isWood(world, i + 1, j1, j1)) {
                  return false;
               }

               for(i1 = i; i1 >= i - 3; --i1) {
                  if (!world.func_147437_c(i1, j1, j1)) {
                     return false;
                  }
               }
            }
         }
      } else {
         for(j1 = k - 2; j1 <= k + 2; ++j1) {
            for(j1 = j; j1 <= j + 4; ++j1) {
               for(i1 = i; i1 >= i - 3; --i1) {
                  this.func_150516_a(world, i1, j1, j1, Blocks.field_150350_a, 0);
               }
            }
         }
      }

      for(j1 = k - 1; j1 <= k + 1; ++j1) {
         for(j1 = i; j1 >= i - 2; --j1) {
            this.func_150516_a(world, j1, j, j1, LOTRMod.planks2, 13);
         }
      }

      for(j1 = k - 2; j1 <= k + 2; ++j1) {
         this.func_150516_a(world, i - 3, j, j1, LOTRMod.stairsGreenOak, 4);
         this.func_150516_a(world, i - 3, j + 1, j1, LOTRMod.fence2, 13);
      }

      for(j1 = i; j1 >= i - 2; --j1) {
         this.func_150516_a(world, j1, j, k - 2, LOTRMod.stairsGreenOak, 6);
         this.func_150516_a(world, j1, j, k + 2, LOTRMod.stairsGreenOak, 7);
         this.func_150516_a(world, j1, j + 1, k - 2, LOTRMod.fence2, 13);
         this.func_150516_a(world, j1, j + 1, k + 2, LOTRMod.fence2, 13);
      }

      this.func_150516_a(world, i, j + 2, k - 2, LOTRMod.fence2, 13);
      this.func_150516_a(world, i, j + 3, k - 2, LOTRMod.woodElvenTorch, 5);
      this.func_150516_a(world, i, j + 2, k + 2, LOTRMod.fence2, 13);
      this.func_150516_a(world, i, j + 3, k + 2, LOTRMod.woodElvenTorch, 5);
      this.func_150516_a(world, i - 3, j + 2, k - 2, LOTRMod.fence2, 13);
      this.func_150516_a(world, i - 3, j + 3, k - 2, LOTRMod.woodElvenTorch, 5);
      this.func_150516_a(world, i - 3, j + 2, k + 2, LOTRMod.fence2, 13);
      this.func_150516_a(world, i - 3, j + 3, k + 2, LOTRMod.woodElvenTorch, 5);

      for(j1 = j; j1 >= 0 && LOTRMod.isOpaque(world, i + 1, j1, k) && (j1 >= j || !LOTRMod.isOpaque(world, i, j1, k)); --j1) {
         this.func_150516_a(world, i, j1, k, Blocks.field_150468_ap, 4);
      }

      return true;
   }
}
