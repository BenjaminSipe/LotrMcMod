package lotr.common.world.map;

import java.util.Random;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.BiomeGenBase;

public abstract class LOTRRoadType {
   public static final LOTRRoadType PATH = new LOTRRoadType() {
      public LOTRRoadType.RoadBlock getBlock(Random rand, BiomeGenBase biome, boolean top, boolean slab) {
         float f;
         if (slab) {
            f = rand.nextFloat();
            if (f < 0.5F) {
               return new LOTRRoadType.RoadBlock(LOTRMod.slabSingleDirt, 1);
            } else {
               return f < 0.8F ? new LOTRRoadType.RoadBlock(LOTRMod.slabSingleDirt, 0) : new LOTRRoadType.RoadBlock(LOTRMod.slabSingleGravel, 0);
            }
         } else if (top) {
            f = rand.nextFloat();
            if (f < 0.5F) {
               return new LOTRRoadType.RoadBlock(LOTRMod.dirtPath, 0);
            } else {
               return f < 0.8F ? new LOTRRoadType.RoadBlock(Blocks.field_150346_d, 1) : new LOTRRoadType.RoadBlock(Blocks.field_150351_n, 0);
            }
         } else {
            return new LOTRRoadType.RoadBlock(LOTRMod.dirtPath, 0);
         }
      }
   };
   public static final LOTRRoadType PAVED_PATH = new LOTRRoadType() {
      public LOTRRoadType.RoadBlock getBlock(Random rand, BiomeGenBase biome, boolean top, boolean slab) {
         float f;
         if (slab) {
            f = rand.nextFloat();
            if (f < 0.5F) {
               return new LOTRRoadType.RoadBlock(LOTRMod.slabSingleDirt, 1);
            } else {
               return f < 0.8F ? new LOTRRoadType.RoadBlock(Blocks.field_150333_U, 3) : new LOTRRoadType.RoadBlock(LOTRMod.slabSingleGravel, 0);
            }
         } else if (top) {
            f = rand.nextFloat();
            if (f < 0.5F) {
               return new LOTRRoadType.RoadBlock(LOTRMod.dirtPath, 0);
            } else {
               return f < 0.8F ? new LOTRRoadType.RoadBlock(Blocks.field_150347_e, 0) : new LOTRRoadType.RoadBlock(Blocks.field_150351_n, 0);
            }
         } else {
            return new LOTRRoadType.RoadBlock(LOTRMod.dirtPath, 0);
         }
      }
   };
   public static final LOTRRoadType COBBLESTONE = new LOTRRoadType() {
      public LOTRRoadType.RoadBlock getBlock(Random rand, BiomeGenBase biome, boolean top, boolean slab) {
         return slab ? new LOTRRoadType.RoadBlock(Blocks.field_150333_U, 3) : new LOTRRoadType.RoadBlock(Blocks.field_150347_e, 0);
      }
   };
   public static final LOTRRoadType DIRT = new LOTRRoadType() {
      public LOTRRoadType.RoadBlock getBlock(Random rand, BiomeGenBase biome, boolean top, boolean slab) {
         return slab ? new LOTRRoadType.RoadBlock(LOTRMod.slabSingleDirt, 0) : new LOTRRoadType.RoadBlock(Blocks.field_150346_d, 1);
      }
   };
   public static final LOTRRoadType GALADHRIM = new LOTRRoadType() {
      public LOTRRoadType.RoadBlock getBlock(Random rand, BiomeGenBase biome, boolean top, boolean slab) {
         return slab ? new LOTRRoadType.RoadBlock(LOTRMod.slabSingle2, 3) : new LOTRRoadType.RoadBlock(LOTRMod.brick, 11);
      }
   };
   public static final LOTRRoadType GALADHRIM_RUINED = new LOTRRoadType() {
      public LOTRRoadType.RoadBlock getBlock(Random rand, BiomeGenBase biome, boolean top, boolean slab) {
         if (slab) {
            if (rand.nextInt(4) == 0) {
               return rand.nextBoolean() ? new LOTRRoadType.RoadBlock(LOTRMod.slabSingle2, 4) : new LOTRRoadType.RoadBlock(LOTRMod.slabSingle2, 5);
            } else {
               return new LOTRRoadType.RoadBlock(LOTRMod.slabSingle2, 3);
            }
         } else if (rand.nextInt(4) == 0) {
            return rand.nextBoolean() ? new LOTRRoadType.RoadBlock(LOTRMod.brick, 12) : new LOTRRoadType.RoadBlock(LOTRMod.brick, 13);
         } else {
            return new LOTRRoadType.RoadBlock(LOTRMod.brick, 11);
         }
      }
   };
   public static final LOTRRoadType HIGH_ELVEN = new LOTRRoadType() {
      public LOTRRoadType.RoadBlock getBlock(Random rand, BiomeGenBase biome, boolean top, boolean slab) {
         return slab ? new LOTRRoadType.RoadBlock(LOTRMod.slabSingle5, 5) : new LOTRRoadType.RoadBlock(LOTRMod.brick3, 2);
      }
   };
   public static final LOTRRoadType HIGH_ELVEN_RUINED = new LOTRRoadType() {
      public LOTRRoadType.RoadBlock getBlock(Random rand, BiomeGenBase biome, boolean top, boolean slab) {
         if (slab) {
            if (rand.nextInt(4) == 0) {
               return rand.nextBoolean() ? new LOTRRoadType.RoadBlock(LOTRMod.slabSingle5, 6) : new LOTRRoadType.RoadBlock(LOTRMod.slabSingle5, 7);
            } else {
               return new LOTRRoadType.RoadBlock(LOTRMod.slabSingle5, 5);
            }
         } else if (rand.nextInt(4) == 0) {
            return rand.nextBoolean() ? new LOTRRoadType.RoadBlock(LOTRMod.brick3, 3) : new LOTRRoadType.RoadBlock(LOTRMod.brick3, 4);
         } else {
            return new LOTRRoadType.RoadBlock(LOTRMod.brick3, 2);
         }
      }
   };
   public static final LOTRRoadType WOOD_ELVEN = new LOTRRoadType() {
      public LOTRRoadType.RoadBlock getBlock(Random rand, BiomeGenBase biome, boolean top, boolean slab) {
         return slab ? new LOTRRoadType.RoadBlock(LOTRMod.slabSingle6, 2) : new LOTRRoadType.RoadBlock(LOTRMod.brick3, 5);
      }
   };
   public static final LOTRRoadType WOOD_ELVEN_RUINED = new LOTRRoadType() {
      public LOTRRoadType.RoadBlock getBlock(Random rand, BiomeGenBase biome, boolean top, boolean slab) {
         if (slab) {
            if (rand.nextInt(4) == 0) {
               return rand.nextBoolean() ? new LOTRRoadType.RoadBlock(LOTRMod.slabSingle6, 3) : new LOTRRoadType.RoadBlock(LOTRMod.slabSingle6, 4);
            } else {
               return new LOTRRoadType.RoadBlock(LOTRMod.slabSingle6, 2);
            }
         } else if (rand.nextInt(4) == 0) {
            return rand.nextBoolean() ? new LOTRRoadType.RoadBlock(LOTRMod.brick3, 6) : new LOTRRoadType.RoadBlock(LOTRMod.brick3, 7);
         } else {
            return new LOTRRoadType.RoadBlock(LOTRMod.brick3, 5);
         }
      }
   };
   public static final LOTRRoadType ARNOR = new LOTRRoadType() {
      public LOTRRoadType.RoadBlock getBlock(Random rand, BiomeGenBase biome, boolean top, boolean slab) {
         if (slab) {
            if (rand.nextInt(4) == 0) {
               return rand.nextBoolean() ? new LOTRRoadType.RoadBlock(LOTRMod.slabSingle4, 2) : new LOTRRoadType.RoadBlock(LOTRMod.slabSingle4, 3);
            } else {
               return new LOTRRoadType.RoadBlock(LOTRMod.slabSingle4, 1);
            }
         } else if (rand.nextInt(4) == 0) {
            return rand.nextBoolean() ? new LOTRRoadType.RoadBlock(LOTRMod.brick2, 4) : new LOTRRoadType.RoadBlock(LOTRMod.brick2, 5);
         } else {
            return new LOTRRoadType.RoadBlock(LOTRMod.brick2, 3);
         }
      }
   };
   public static final LOTRRoadType GONDOR = new LOTRRoadType() {
      public LOTRRoadType.RoadBlock getBlock(Random rand, BiomeGenBase biome, boolean top, boolean slab) {
         return slab ? new LOTRRoadType.RoadBlock(LOTRMod.slabSingle, 3) : new LOTRRoadType.RoadBlock(LOTRMod.brick, 1);
      }
   };
   public static final LOTRRoadType GONDOR_MIX = new LOTRRoadType() {
      public LOTRRoadType.RoadBlock getBlock(Random rand, BiomeGenBase biome, boolean top, boolean slab) {
         if (slab) {
            if (rand.nextInt(8) == 0) {
               return new LOTRRoadType.RoadBlock(LOTRMod.slabSingle, 2);
            } else if (rand.nextInt(8) == 0) {
               return rand.nextBoolean() ? new LOTRRoadType.RoadBlock(LOTRMod.slabSingle, 4) : new LOTRRoadType.RoadBlock(LOTRMod.slabSingle, 5);
            } else {
               return new LOTRRoadType.RoadBlock(LOTRMod.slabSingle, 3);
            }
         } else if (rand.nextInt(8) == 0) {
            return new LOTRRoadType.RoadBlock(LOTRMod.slabDouble, 2);
         } else if (rand.nextInt(8) == 0) {
            return rand.nextBoolean() ? new LOTRRoadType.RoadBlock(LOTRMod.brick, 2) : new LOTRRoadType.RoadBlock(LOTRMod.brick, 3);
         } else {
            return new LOTRRoadType.RoadBlock(LOTRMod.brick, 1);
         }
      }
   };
   public static final LOTRRoadType DOL_AMROTH = new LOTRRoadType() {
      public LOTRRoadType.RoadBlock getBlock(Random rand, BiomeGenBase biome, boolean top, boolean slab) {
         return slab ? new LOTRRoadType.RoadBlock(LOTRMod.slabSingle6, 7) : new LOTRRoadType.RoadBlock(LOTRMod.brick3, 9);
      }
   };
   public static final LOTRRoadType ROHAN = new LOTRRoadType() {
      public LOTRRoadType.RoadBlock getBlock(Random rand, BiomeGenBase biome, boolean top, boolean slab) {
         return slab ? new LOTRRoadType.RoadBlock(LOTRMod.slabSingle, 6) : new LOTRRoadType.RoadBlock(LOTRMod.brick, 4);
      }
   };
   public static final LOTRRoadType ROHAN_MIX = new LOTRRoadType() {
      public LOTRRoadType.RoadBlock getBlock(Random rand, BiomeGenBase biome, boolean top, boolean slab) {
         if (slab) {
            if (rand.nextInt(3) == 0) {
               return rand.nextBoolean() ? new LOTRRoadType.RoadBlock(LOTRMod.slabSingleDirt, 0) : new LOTRRoadType.RoadBlock(LOTRMod.slabSingleDirt, 1);
            } else {
               return rand.nextBoolean() ? new LOTRRoadType.RoadBlock(LOTRMod.slabSingle, 6) : new LOTRRoadType.RoadBlock(LOTRMod.slabSingle11, 4);
            }
         } else if (rand.nextInt(3) == 0) {
            return rand.nextBoolean() ? new LOTRRoadType.RoadBlock(Blocks.field_150346_d, 1) : new LOTRRoadType.RoadBlock(LOTRMod.dirtPath, 0);
         } else {
            return rand.nextBoolean() ? new LOTRRoadType.RoadBlock(LOTRMod.brick, 4) : new LOTRRoadType.RoadBlock(LOTRMod.rock, 2);
         }
      }
   };
   public static final LOTRRoadType DWARVEN = new LOTRRoadType() {
      public LOTRRoadType.RoadBlock getBlock(Random rand, BiomeGenBase biome, boolean top, boolean slab) {
         return slab ? new LOTRRoadType.RoadBlock(LOTRMod.slabSingle, 7) : new LOTRRoadType.RoadBlock(LOTRMod.brick, 6);
      }
   };
   public static final LOTRRoadType DALE = new LOTRRoadType() {
      public LOTRRoadType.RoadBlock getBlock(Random rand, BiomeGenBase biome, boolean top, boolean slab) {
         return slab ? new LOTRRoadType.RoadBlock(LOTRMod.slabSingle9, 6) : new LOTRRoadType.RoadBlock(LOTRMod.brick5, 1);
      }
   };
   public static final LOTRRoadType HARAD = new LOTRRoadType() {
      public LOTRRoadType.RoadBlock getBlock(Random rand, BiomeGenBase biome, boolean top, boolean slab) {
         return slab ? new LOTRRoadType.RoadBlock(LOTRMod.slabSingle4, 0) : new LOTRRoadType.RoadBlock(LOTRMod.brick, 15);
      }
   };
   public static final LOTRRoadType HARAD_PATH = new LOTRRoadType() {
      public LOTRRoadType.RoadBlock getBlock(Random rand, BiomeGenBase biome, boolean top, boolean slab) {
         float f;
         if (slab) {
            f = rand.nextFloat();
            if (f < 0.33F) {
               return rand.nextInt(4) == 0 ? new LOTRRoadType.RoadBlock(LOTRMod.slabSingle7, 1) : new LOTRRoadType.RoadBlock(LOTRMod.slabSingle4, 0);
            } else {
               return f < 0.67F ? new LOTRRoadType.RoadBlock(LOTRMod.slabSingleSand, 0) : new LOTRRoadType.RoadBlock(LOTRMod.slabSingleDirt, 1);
            }
         } else {
            f = rand.nextFloat();
            if (f < 0.33F) {
               return rand.nextInt(4) == 0 ? new LOTRRoadType.RoadBlock(LOTRMod.brick3, 11) : new LOTRRoadType.RoadBlock(LOTRMod.brick, 15);
            } else if (f < 0.67F) {
               return top ? new LOTRRoadType.RoadBlock(Blocks.field_150354_m, 0) : new LOTRRoadType.RoadBlock(Blocks.field_150322_A, 0);
            } else {
               return new LOTRRoadType.RoadBlock(LOTRMod.dirtPath, 0);
            }
         }
      }
   };
   public static final LOTRRoadType HARAD_TOWN = new LOTRRoadType() {
      public LOTRRoadType.RoadBlock getBlock(Random rand, BiomeGenBase biome, boolean top, boolean slab) {
         float f;
         if (slab) {
            f = rand.nextFloat();
            if (f < 0.17F) {
               return new LOTRRoadType.RoadBlock(LOTRMod.slabSingleDirt, 0);
            } else if (f < 0.33F) {
               return new LOTRRoadType.RoadBlock(LOTRMod.slabSingleDirt, 1);
            } else if (f < 0.5F) {
               return new LOTRRoadType.RoadBlock(LOTRMod.slabSingleSand, 0);
            } else if (f < 0.67F) {
               return new LOTRRoadType.RoadBlock(LOTRMod.slabSingle4, 0);
            } else {
               return f < 0.83F ? new LOTRRoadType.RoadBlock(LOTRMod.slabSingle7, 1) : new LOTRRoadType.RoadBlock(LOTRMod.slabSingle4, 7);
            }
         } else {
            f = rand.nextFloat();
            if (f < 0.17F) {
               return new LOTRRoadType.RoadBlock(Blocks.field_150346_d, 1);
            } else if (f < 0.33F) {
               return new LOTRRoadType.RoadBlock(LOTRMod.dirtPath, 0);
            } else if (f < 0.5F) {
               return top ? new LOTRRoadType.RoadBlock(Blocks.field_150354_m, 0) : new LOTRRoadType.RoadBlock(Blocks.field_150322_A, 0);
            } else if (f < 0.67F) {
               return new LOTRRoadType.RoadBlock(LOTRMod.brick, 15);
            } else {
               return f < 0.83F ? new LOTRRoadType.RoadBlock(LOTRMod.brick3, 11) : new LOTRRoadType.RoadBlock(LOTRMod.pillar, 5);
            }
         }
      }
   };
   public static final LOTRRoadType UMBAR = new LOTRRoadType() {
      public LOTRRoadType.RoadBlock getBlock(Random rand, BiomeGenBase biome, boolean top, boolean slab) {
         return slab ? new LOTRRoadType.RoadBlock(LOTRMod.slabSingle13, 2) : new LOTRRoadType.RoadBlock(LOTRMod.brick6, 6);
      }
   };
   public static final LOTRRoadType GULF_HARAD = new LOTRRoadType() {
      public LOTRRoadType.RoadBlock getBlock(Random rand, BiomeGenBase biome, boolean top, boolean slab) {
         float f;
         if (slab) {
            f = rand.nextFloat();
            if (f < 0.25F) {
               return new LOTRRoadType.RoadBlock(LOTRMod.slabSingleDirt, 0);
            } else if (f < 0.5F) {
               return new LOTRRoadType.RoadBlock(LOTRMod.slabSingleSand, 1);
            } else {
               return f < 0.75F ? new LOTRRoadType.RoadBlock(LOTRMod.slabSingle7, 2) : new LOTRRoadType.RoadBlock(LOTRMod.slabSingle7, 3);
            }
         } else {
            f = rand.nextFloat();
            if (f < 0.25F) {
               return new LOTRRoadType.RoadBlock(Blocks.field_150346_d, 1);
            } else if (f < 0.5F) {
               return top ? new LOTRRoadType.RoadBlock(Blocks.field_150354_m, 1) : new LOTRRoadType.RoadBlock(LOTRMod.redSandstone, 0);
            } else {
               return f < 0.75F ? new LOTRRoadType.RoadBlock(LOTRMod.brick3, 13) : new LOTRRoadType.RoadBlock(LOTRMod.brick3, 14);
            }
         }
      }
   };
   public static final LOTRRoadType TAUREDAIN = new LOTRRoadType() {
      public LOTRRoadType.RoadBlock getBlock(Random rand, BiomeGenBase biome, boolean top, boolean slab) {
         if (slab) {
            if (rand.nextInt(4) == 0) {
               return rand.nextBoolean() ? new LOTRRoadType.RoadBlock(LOTRMod.slabSingle8, 1) : new LOTRRoadType.RoadBlock(LOTRMod.slabSingle8, 2);
            } else {
               return new LOTRRoadType.RoadBlock(LOTRMod.slabSingle8, 0);
            }
         } else if (rand.nextInt(4) == 0) {
            return rand.nextBoolean() ? new LOTRRoadType.RoadBlock(LOTRMod.brick4, 1) : new LOTRRoadType.RoadBlock(LOTRMod.brick4, 2);
         } else {
            return new LOTRRoadType.RoadBlock(LOTRMod.brick4, 0);
         }
      }
   };
   public static final LOTRRoadType MORDOR = new LOTRRoadType() {
      public LOTRRoadType.RoadBlock getBlock(Random rand, BiomeGenBase biome, boolean top, boolean slab) {
         return slab ? new LOTRRoadType.RoadBlock(LOTRMod.slabSingleDirt, 3) : new LOTRRoadType.RoadBlock(LOTRMod.mordorDirt, 0);
      }
   };
   public static final LOTRRoadType DORWINION = new LOTRRoadType() {
      public LOTRRoadType.RoadBlock getBlock(Random rand, BiomeGenBase biome, boolean top, boolean slab) {
         return slab ? new LOTRRoadType.RoadBlock(LOTRMod.slabSingle9, 7) : new LOTRRoadType.RoadBlock(LOTRMod.brick5, 2);
      }
   };
   public static final LOTRRoadType RHUN = new LOTRRoadType() {
      public LOTRRoadType.RoadBlock getBlock(Random rand, BiomeGenBase biome, boolean top, boolean slab) {
         if (slab) {
            if (rand.nextInt(8) == 0) {
               return rand.nextBoolean() ? new LOTRRoadType.RoadBlock(LOTRMod.slabSingle12, 1) : new LOTRRoadType.RoadBlock(LOTRMod.slabSingle12, 2);
            } else {
               return new LOTRRoadType.RoadBlock(LOTRMod.slabSingle12, 0);
            }
         } else if (rand.nextInt(8) == 0) {
            return rand.nextBoolean() ? new LOTRRoadType.RoadBlock(LOTRMod.brick5, 13) : new LOTRRoadType.RoadBlock(LOTRMod.brick5, 14);
         } else {
            return new LOTRRoadType.RoadBlock(LOTRMod.brick5, 11);
         }
      }
   };

   public abstract LOTRRoadType.RoadBlock getBlock(Random var1, BiomeGenBase var2, boolean var3, boolean var4);

   public float getRepair() {
      return 1.0F;
   }

   public boolean hasFlowers() {
      return false;
   }

   public LOTRRoadType setRepair(final float f) {
      return new LOTRRoadType() {
         public LOTRRoadType.RoadBlock getBlock(Random rand, BiomeGenBase biome, boolean top, boolean slab) {
            return LOTRRoadType.this.getBlock(rand, biome, top, slab);
         }

         public float getRepair() {
            return f;
         }

         public boolean hasFlowers() {
            return LOTRRoadType.this.hasFlowers();
         }
      };
   }

   public LOTRRoadType setHasFlowers(final boolean flag) {
      return new LOTRRoadType() {
         public LOTRRoadType.RoadBlock getBlock(Random rand, BiomeGenBase biome, boolean top, boolean slab) {
            return LOTRRoadType.this.getBlock(rand, biome, top, slab);
         }

         public float getRepair() {
            return LOTRRoadType.this.getRepair();
         }

         public boolean hasFlowers() {
            return flag;
         }
      };
   }

   public static class RoadBlock {
      public Block block;
      public final int meta;

      public RoadBlock(Block b, int i) {
         this.block = b;
         this.meta = i;
      }
   }

   public abstract static class BridgeType {
      public static final LOTRRoadType.BridgeType DEFAULT = new LOTRRoadType.BridgeType() {
         public LOTRRoadType.RoadBlock getBlock(Random rand, boolean slab) {
            return slab ? new LOTRRoadType.RoadBlock(Blocks.field_150376_bx, 0) : new LOTRRoadType.RoadBlock(Blocks.field_150344_f, 0);
         }

         public LOTRRoadType.RoadBlock getEdge(Random rand) {
            return new LOTRRoadType.RoadBlock(LOTRMod.woodBeamV1, 0);
         }

         public LOTRRoadType.RoadBlock getFence(Random rand) {
            return new LOTRRoadType.RoadBlock(Blocks.field_150422_aJ, 0);
         }
      };
      public static final LOTRRoadType.BridgeType MIRKWOOD = new LOTRRoadType.BridgeType() {
         public LOTRRoadType.RoadBlock getBlock(Random rand, boolean slab) {
            return slab ? new LOTRRoadType.RoadBlock(LOTRMod.woodSlabSingle, 2) : new LOTRRoadType.RoadBlock(LOTRMod.planks, 2);
         }

         public LOTRRoadType.RoadBlock getEdge(Random rand) {
            return new LOTRRoadType.RoadBlock(LOTRMod.woodBeam1, 2);
         }

         public LOTRRoadType.RoadBlock getFence(Random rand) {
            return new LOTRRoadType.RoadBlock(LOTRMod.fence, 2);
         }
      };
      public static final LOTRRoadType.BridgeType CHARRED = new LOTRRoadType.BridgeType() {
         public LOTRRoadType.RoadBlock getBlock(Random rand, boolean slab) {
            return slab ? new LOTRRoadType.RoadBlock(LOTRMod.woodSlabSingle, 3) : new LOTRRoadType.RoadBlock(LOTRMod.planks, 3);
         }

         public LOTRRoadType.RoadBlock getEdge(Random rand) {
            return new LOTRRoadType.RoadBlock(LOTRMod.woodBeam1, 3);
         }

         public LOTRRoadType.RoadBlock getFence(Random rand) {
            return new LOTRRoadType.RoadBlock(LOTRMod.fence, 3);
         }
      };

      private BridgeType() {
      }

      public abstract LOTRRoadType.RoadBlock getBlock(Random var1, boolean var2);

      public abstract LOTRRoadType.RoadBlock getEdge(Random var1);

      public abstract LOTRRoadType.RoadBlock getFence(Random var1);

      // $FF: synthetic method
      BridgeType(Object x0) {
         this();
      }
   }
}
