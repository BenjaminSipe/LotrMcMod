package lotr.common.world.structure;

import java.util.Random;
import lotr.common.LOTRMod;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.npc.LOTREntityNurnSlave;
import lotr.common.world.biome.LOTRBiomeGenNurn;
import net.minecraft.block.Block;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public abstract class LOTRWorldGenNurnFarmBase extends LOTRWorldGenStructureBase {
   public LOTRWorldGenNurnFarmBase(boolean flag) {
      super(flag);
   }

   public boolean func_76484_a(World world, Random random, int i, int j, int k) {
      if (!this.restrictions || world.func_147439_a(i, j - 1, k) == Blocks.field_150349_c && world.func_72807_a(i, k) instanceof LOTRBiomeGenNurn) {
         --j;
         int rotation = random.nextInt(4);
         if (!this.restrictions && this.usingPlayer != null) {
            rotation = this.usingPlayerRotation();
         }

         switch(rotation) {
         case 0:
            k += 8;
            break;
         case 1:
            i -= 8;
            break;
         case 2:
            k -= 8;
            break;
         case 3:
            i += 8;
         }

         int i1;
         int k1;
         int j1;
         if (this.restrictions) {
            for(i1 = i - 8; i1 <= i + 8; ++i1) {
               for(k1 = k - 8; k1 <= k + 8; ++k1) {
                  j1 = world.func_72976_f(i1, k1) - 1;
                  if (Math.abs(j1 - j) > 4) {
                     return false;
                  }

                  Block l = world.func_147439_a(i1, j1, k1);
                  if (l != Blocks.field_150349_c) {
                     return false;
                  }
               }
            }
         }

         for(i1 = i - 7; i1 <= i + 7; ++i1) {
            for(k1 = k - 7; k1 <= k + 7; ++k1) {
               for(j1 = j + 1; j1 <= j + 4; ++j1) {
                  this.func_150516_a(world, i1, j1, k1, Blocks.field_150350_a, 0);
               }

               for(j1 = j; (j1 == j || !LOTRMod.isOpaque(world, i1, j1, k1)) && j1 >= 0; --j1) {
                  this.func_150516_a(world, i1, j1, k1, LOTRMod.brick, 0);
                  this.setGrassToDirt(world, i1, j1 - 1, k1);
               }

               if (Math.abs(i1 - i) != 7 && Math.abs(k1 - k) != 7) {
                  if (Math.abs(i1 - i) <= 4 && Math.abs(k1 - k) <= 4) {
                     this.func_150516_a(world, i1, j + 1, k1, LOTRMod.slabSingle, 1);
                  } else {
                     this.func_150516_a(world, i1, j + 1, k1, LOTRMod.slabSingle, 1);
                  }
               } else {
                  this.func_150516_a(world, i1, j + 1, k1, LOTRMod.brick, 0);
                  this.func_150516_a(world, i1, j + 2, k1, LOTRMod.wall, 1);
               }

               if (Math.abs(i1 - i) == 7 && Math.abs(k1 - k) == 7) {
                  this.placeOrcTorch(world, i1, j + 3, k1);
               }
            }
         }

         if (rotation == 0) {
            this.func_150516_a(world, i, j + 1, k - 7, LOTRMod.slabSingle, 1);
            this.func_150516_a(world, i, j + 2, k - 7, Blocks.field_150350_a, 0);
            this.func_150516_a(world, i - 1, j + 3, k - 7, LOTRMod.wall, 1);
            this.func_150516_a(world, i + 1, j + 3, k - 7, LOTRMod.wall, 1);
            this.func_150516_a(world, i - 1, j + 4, k - 7, LOTRMod.wall, 1);
            this.func_150516_a(world, i, j + 4, k - 7, LOTRMod.wall, 1);
            this.func_150516_a(world, i + 1, j + 4, k - 7, LOTRMod.wall, 1);
         } else if (rotation == 1) {
            this.func_150516_a(world, i + 7, j + 1, k, LOTRMod.slabSingle, 1);
            this.func_150516_a(world, i + 7, j + 2, k, Blocks.field_150350_a, 0);
            this.func_150516_a(world, i + 7, j + 3, k - 1, LOTRMod.wall, 1);
            this.func_150516_a(world, i + 7, j + 3, k + 1, LOTRMod.wall, 1);
            this.func_150516_a(world, i + 7, j + 4, k - 1, LOTRMod.wall, 1);
            this.func_150516_a(world, i + 7, j + 4, k, LOTRMod.wall, 1);
            this.func_150516_a(world, i + 7, j + 4, k + 1, LOTRMod.wall, 1);
         } else if (rotation == 2) {
            this.func_150516_a(world, i, j + 1, k + 7, LOTRMod.slabSingle, 1);
            this.func_150516_a(world, i, j + 2, k + 7, Blocks.field_150350_a, 0);
            this.func_150516_a(world, i - 1, j + 3, k + 7, LOTRMod.wall, 1);
            this.func_150516_a(world, i + 1, j + 3, k + 7, LOTRMod.wall, 1);
            this.func_150516_a(world, i - 1, j + 4, k + 7, LOTRMod.wall, 1);
            this.func_150516_a(world, i, j + 4, k + 7, LOTRMod.wall, 1);
            this.func_150516_a(world, i + 1, j + 4, k + 7, LOTRMod.wall, 1);
         } else if (rotation == 3) {
            this.func_150516_a(world, i - 7, j + 1, k, LOTRMod.slabSingle, 1);
            this.func_150516_a(world, i - 7, j + 2, k, Blocks.field_150350_a, 0);
            this.func_150516_a(world, i - 7, j + 3, k - 1, LOTRMod.wall, 1);
            this.func_150516_a(world, i - 7, j + 3, k + 1, LOTRMod.wall, 1);
            this.func_150516_a(world, i - 7, j + 4, k - 1, LOTRMod.wall, 1);
            this.func_150516_a(world, i - 7, j + 4, k, LOTRMod.wall, 1);
            this.func_150516_a(world, i - 7, j + 4, k + 1, LOTRMod.wall, 1);
         }

         this.generateCrops(world, random, i, j, k);
         i1 = 2 + random.nextInt(4);

         for(k1 = 0; k1 < i1; ++k1) {
            LOTREntityNurnSlave slave = new LOTREntityNurnSlave(world);
            slave.func_70012_b((double)i + 0.5D, (double)(j + 2), (double)k + 0.5D, world.field_73012_v.nextFloat() * 360.0F, 0.0F);
            slave.func_110161_a((IEntityLivingData)null);
            slave.func_110171_b(i, j, k, 8);
            slave.isNPCPersistent = true;
            world.func_72838_d(slave);
         }

         LOTREntityNPCRespawner respawner = new LOTREntityNPCRespawner(world);
         respawner.setSpawnClass(LOTREntityNurnSlave.class);
         respawner.setCheckRanges(12, -8, 8, 8);
         respawner.setSpawnRanges(6, -2, 2, 8);
         this.placeNPCRespawner(respawner, world, i, j, k);
         return true;
      } else {
         return false;
      }
   }

   public abstract void generateCrops(World var1, Random var2, int var3, int var4, int var5);
}
