package lotr.common.block;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;
import lotr.common.LOTRBannerProtection;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFire;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import org.apache.commons.lang3.tuple.Pair;

public class LOTRBlockRhunFire extends BlockFire {
   public LOTRBlockRhunFire() {
      this.func_149715_a(1.0F);
   }

   private boolean isBannered(World world, int i, int j, int k) {
      return LOTRBannerProtection.isProtected(world, i, j, k, LOTRBannerProtection.anyBanner(), false);
   }

   public void func_149674_a(World world, int i, int j, int k, Random random) {
      if (LOTRMod.doFireTick(world)) {
         if (this.isBannered(world, i, j, k)) {
            world.func_147468_f(i, j, k);
         } else {
            Map infos = new HashMap();
            boolean canBurnStone = random.nextFloat() < 0.9F;
            if (canBurnStone) {
               ForgeDirection[] var8 = ForgeDirection.VALID_DIRECTIONS;
               int var9 = var8.length;

               for(int var10 = 0; var10 < var9; ++var10) {
                  ForgeDirection dir = var8[var10];
                  Block block = world.func_147439_a(i + dir.offsetX, j + dir.offsetY, k + dir.offsetZ);
                  Material material = block.func_149688_o();
                  if ((material == Material.field_151576_e || material == Material.field_151571_B || block instanceof LOTRBlockGate) && block.func_149638_a((Entity)null) < 100.0F) {
                     int enco = this.getEncouragement(block);
                     int flam = this.getFlammability(block);
                     infos.put(block, Pair.of(enco, flam));
                     Blocks.field_150480_ab.setFireInfo(block, 30, 30);
                  }
               }
            }

            if (random.nextInt(12) == 0) {
               world.func_147468_f(i, j, k);
            } else {
               this.runBaseFireUpdate(world, i, j, k, random);
            }

            if (!infos.isEmpty()) {
               Iterator var16 = infos.entrySet().iterator();

               while(var16.hasNext()) {
                  Entry e = (Entry)var16.next();
                  Blocks.field_150480_ab.setFireInfo((Block)e.getKey(), (Integer)((Pair)e.getValue()).getLeft(), (Integer)((Pair)e.getValue()).getRight());
               }
            }
         }
      }

   }

   private void runBaseFireUpdate(World world, int i, int j, int k, Random random) {
      if (LOTRMod.doFireTick(world)) {
         boolean isFireplace = world.func_147439_a(i, j - 1, k).isFireSource(world, i, j - 1, k, ForgeDirection.UP);
         if (!this.func_149742_c(world, i, j, k)) {
            world.func_147468_f(i, j, k);
         }

         if (!isFireplace && world.func_72896_J() && (world.func_72951_B(i, j, k) || world.func_72951_B(i - 1, j, k) || world.func_72951_B(i + 1, j, k) || world.func_72951_B(i, j, k - 1) || world.func_72951_B(i, j, k + 1))) {
            world.func_147468_f(i, j, k);
         } else {
            int meta = world.func_72805_g(i, j, k);
            if (meta < 15) {
               world.func_72921_c(i, j, k, meta + random.nextInt(3) / 2, 4);
            }

            world.func_147464_a(i, j, k, this, this.func_149738_a(world) + random.nextInt(10));
            if (!isFireplace && !this.canNeighborBurn(world, i, j, k)) {
               if (!World.func_147466_a(world, i, j - 1, k) || meta > 3) {
                  world.func_147468_f(i, j, k);
               }
            } else if (!isFireplace && !this.canCatchFire(world, i, j - 1, k, ForgeDirection.UP) && meta == 15 && random.nextInt(4) == 0) {
               world.func_147468_f(i, j, k);
            } else {
               int extraChance = 0;
               boolean humid = world.func_72958_C(i, j, k);
               if (humid) {
                  extraChance = -50;
               }

               int hChance = 300 + extraChance;
               int vChance = 250 + extraChance;
               ForgeDirection[] var12 = ForgeDirection.VALID_DIRECTIONS;
               int var13 = var12.length;

               for(int var14 = 0; var14 < var13; ++var14) {
                  ForgeDirection dir = var12[var14];
                  this.tryCatchFire(world, i + dir.offsetX, j + dir.offsetY, k + dir.offsetZ, dir.offsetY == 0 ? hChance : vChance, random, meta, dir);
               }

               int xzRange = 1;
               int yMin = -1;
               int yMax = 4;

               for(int i1 = i - xzRange; i1 <= i + xzRange; ++i1) {
                  for(int k1 = k - xzRange; k1 <= k + xzRange; ++k1) {
                     for(int j1 = j + yMin; j1 <= j + yMax; ++j1) {
                        if ((i1 != i || j1 != j || k1 != k) && !this.isBannered(world, i1, j1, k1)) {
                           int totalChance = 100;
                           if (j1 > j + 1) {
                              totalChance += (j1 - (j + 1)) * 100;
                           }

                           int encourage = this.getChanceOfNeighborsEncouragingFire(world, i1, j1, k1);
                           if (encourage > 0) {
                              int chance = (encourage + 40 + world.field_73013_u.func_151525_a() * 7) / (meta + 30);
                              if (humid) {
                                 chance /= 2;
                              }

                              if (chance > 0 && random.nextInt(totalChance) <= chance && (!world.func_72896_J() || !world.func_72951_B(i1, j1, k1)) && !world.func_72951_B(i1 - 1, j1, k) && !world.func_72951_B(i1 + 1, j1, k1) && !world.func_72951_B(i1, j1, k1 - 1) && !world.func_72951_B(i1, j1, k1 + 1)) {
                                 int newMeta = meta + random.nextInt(5) / 4;
                                 if (newMeta > 15) {
                                    newMeta = 15;
                                 }

                                 world.func_147465_d(i1, j1, k1, this, newMeta, 3);
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }

   }

   private void tryCatchFire(World world, int i, int j, int k, int chance, Random random, int meta, ForgeDirection face) {
      if (!this.isBannered(world, i, j, k)) {
         int flamm = world.func_147439_a(i, j, k).getFlammability(world, i, j, k, face);
         if (random.nextInt(chance) < flamm) {
            boolean isTNT = world.func_147439_a(i, j, k) == Blocks.field_150335_W;
            if (random.nextInt(meta + 10) < 5 && !world.func_72951_B(i, j, k)) {
               int newMeta = meta + random.nextInt(5) / 4;
               if (newMeta > 15) {
                  newMeta = 15;
               }

               world.func_147465_d(i, j, k, this, newMeta, 3);
            } else {
               world.func_147468_f(i, j, k);
            }

            if (isTNT) {
               Blocks.field_150335_W.func_149664_b(world, i, j, k, 1);
            }
         }

      }
   }

   private int getChanceOfNeighborsEncouragingFire(World world, int i, int j, int k) {
      if (!world.func_147437_c(i, j, k)) {
         return 0;
      } else {
         int chance = 0;
         ForgeDirection[] var6 = ForgeDirection.VALID_DIRECTIONS;
         int var7 = var6.length;

         for(int var8 = 0; var8 < var7; ++var8) {
            ForgeDirection dir = var6[var8];
            chance = this.getChanceToEncourageFire(world, i + dir.offsetX, j + dir.offsetY, k + dir.offsetZ, chance, dir);
         }

         return chance;
      }
   }

   private boolean canNeighborBurn(World world, int i, int j, int k) {
      ForgeDirection[] var5 = ForgeDirection.VALID_DIRECTIONS;
      int var6 = var5.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         ForgeDirection dir = var5[var7];
         if (this.canCatchFireNotBannered(world, i + dir.offsetX, j + dir.offsetY, k + dir.offsetZ, dir)) {
            return true;
         }
      }

      return false;
   }

   private boolean canCatchFireNotBannered(World world, int i, int j, int k, ForgeDirection face) {
      return this.isBannered(world, i, j, k) ? false : this.canCatchFire(world, i, j, k, face);
   }

   public int getChanceToEncourageFire(IBlockAccess world, int i, int j, int k, int oldChance, ForgeDirection face) {
      int chance = super.getChanceToEncourageFire(world, i, j, k, oldChance, face);
      return (int)((float)chance * 1.25F);
   }

   public int func_149738_a(World world) {
      return 2;
   }

   public boolean isBurning(IBlockAccess world, int i, int j, int k) {
      return true;
   }
}
