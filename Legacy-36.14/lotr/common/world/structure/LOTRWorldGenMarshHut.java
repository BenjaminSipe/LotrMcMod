package lotr.common.world.structure;

import java.util.Random;
import lotr.common.LOTRMod;
import lotr.common.entity.animal.LOTREntityHorse;
import lotr.common.entity.npc.LOTREntityTroll;
import lotr.common.world.feature.LOTRWorldGenFangornTrees;
import lotr.common.world.map.LOTRFixedStructures;
import net.minecraft.block.Block;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.world.World;

public class LOTRWorldGenMarshHut extends LOTRWorldGenStructureBase {
   private static Random generateRand = new Random();

   public LOTRWorldGenMarshHut() {
      super(false);
   }

   public boolean func_76484_a(World world, Random random, int i, int j, int k) {
      --j;
      int radius = 8;
      int radiusPlusOne = radius + 1;
      int wallThresholdMin = radius * radius;
      int wallThresholdMax = radiusPlusOne * radiusPlusOne;
      Block plankBlock = LOTRMod.planks2;
      int plankMeta = 9;
      Block doorBlock = LOTRMod.doorWillow;

      int i1;
      int k1;
      int k1;
      int j1;
      int distSq;
      int j1;
      for(i1 = i - radiusPlusOne; i1 <= i + radiusPlusOne; ++i1) {
         for(k1 = k - radiusPlusOne; k1 <= k + radiusPlusOne; ++k1) {
            k1 = i1 - i;
            j1 = k1 - k;
            distSq = k1 * k1 + j1 * j1;
            if (distSq < wallThresholdMax) {
               for(j1 = j; (j1 == j || !LOTRMod.isOpaque(world, i1, j1, k1)) && j1 >= 0; --j1) {
                  this.func_150516_a(world, i1, j1, k1, Blocks.field_150346_d, 1);
                  this.setGrassToDirt(world, i1, j1 - 1, k1);
               }

               for(j1 = j + 1; j1 <= j + 6; ++j1) {
                  if (distSq >= wallThresholdMin) {
                     this.func_150516_a(world, i1, j1, k1, plankBlock, plankMeta);
                  } else {
                     this.func_150516_a(world, i1, j1, k1, Blocks.field_150350_a, 0);
                  }
               }
            }
         }
      }

      i1 = (radiusPlusOne + 2) * (radiusPlusOne + 2);

      for(k1 = i - radiusPlusOne - 2; k1 <= i + radiusPlusOne + 2; ++k1) {
         for(k1 = k - radiusPlusOne - 2; k1 <= k + radiusPlusOne + 2; ++k1) {
            for(j1 = j + 6; j1 <= j + 10; ++j1) {
               distSq = k1 - i;
               j1 = k1 - k;
               int j2 = j1 - (j + 4);
               int distSq = distSq * distSq + j1 * j1 + j2 * j2;
               if (distSq + j2 * j2 < wallThresholdMax) {
                  boolean grass = !LOTRMod.isOpaque(world, k1, j1 + 1, k1);
                  this.func_150516_a(world, k1, j1, k1, (Block)(grass ? Blocks.field_150349_c : Blocks.field_150346_d), 0);
                  this.setGrassToDirt(world, k1, j1 - 1, k1);
               }
            }
         }
      }

      this.func_150516_a(world, i - (radius - 1), j + 3, k, Blocks.field_150478_aa, 1);
      this.func_150516_a(world, i + (radius - 1), j + 3, k, Blocks.field_150478_aa, 2);
      this.func_150516_a(world, i, j + 3, k - (radius - 1), Blocks.field_150478_aa, 3);
      this.func_150516_a(world, i, j + 3, k + (radius - 1), Blocks.field_150478_aa, 4);
      this.func_150516_a(world, i, j + 1, k - radius, doorBlock, 1);
      this.func_150516_a(world, i, j + 2, k - radius, doorBlock, 8);
      (new LOTRWorldGenFangornTrees(false, Blocks.field_150364_r, 0, Blocks.field_150362_t, 0)).disableRestrictions().func_76484_a(world, random, i, j + 11, k);
      LOTREntityTroll troll = new LOTREntityTroll(world);
      troll.func_70012_b((double)i + 0.5D, (double)(j + 1), (double)k + 0.5D, 0.0F, 0.0F);
      troll.isNPCPersistent = true;
      troll.func_110161_a((IEntityLivingData)null);
      troll.trollImmuneToSun = true;
      troll.isPassive = true;
      troll.familyInfo.setName("" + 'S' + 'h' + 'r' + 'e' + 'k');
      troll.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(1.0E8D);
      troll.func_70606_j(troll.func_110138_aP());
      world.func_72838_d(troll);
      LOTREntityTroll troll2 = new LOTREntityTroll(world);
      troll2.func_70012_b((double)i + 0.5D, (double)(j + 1), (double)k + 0.5D, 0.0F, 0.0F);
      troll2.isNPCPersistent = true;
      troll2.func_110161_a((IEntityLivingData)null);
      troll2.trollImmuneToSun = true;
      troll2.isPassive = true;
      troll2.familyInfo.setName("" + 'D' + 'r' + 'e' + 'k');
      troll2.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(1.0E8D);
      troll2.func_70606_j(troll2.func_110138_aP());
      world.func_72838_d(troll2);
      LOTREntityHorse horse = new LOTREntityHorse(world);
      horse.func_70012_b((double)i + 0.5D, (double)(j + 1), (double)k + 0.5D, 0.0F, 0.0F);
      horse.func_110214_p(1);
      horse.setMountable(false);
      horse.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(1.0E8D);
      horse.func_70606_j(horse.func_110138_aP());
      world.func_72838_d(horse);
      EntityOcelot cat = new EntityOcelot(world);
      cat.func_70012_b((double)i + 0.5D, (double)(j + 1), (double)k + 0.5D, 0.0F, 0.0F);
      cat.func_70903_f(true);
      cat.func_70912_b(2);
      cat.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(1.0E8D);
      cat.func_70606_j(cat.func_110138_aP());
      world.func_72838_d(cat);
      this.func_150516_a(world, i, j + 2, k + (radius - 1), Blocks.field_150444_as, 2);
      TileEntity tileentity = world.func_147438_o(i, j + 2, k + (radius - 1));
      TileEntitySign sign;
      if (tileentity instanceof TileEntitySign) {
         sign = (TileEntitySign)tileentity;
         sign.field_145915_a[0] = "Check yourself";
         sign.field_145915_a[1] = "before you";
         sign.field_145915_a[2] = troll.familyInfo.getName() + " yourself";
      }

      this.func_150516_a(world, i, j + 1, k + (radius - 1), Blocks.field_150444_as, 2);
      tileentity = world.func_147438_o(i, j + 1, k + (radius - 1));
      if (tileentity instanceof TileEntitySign) {
         sign = (TileEntitySign)tileentity;
         sign.field_145915_a[0] = troll.familyInfo.getName().toUpperCase();
         sign.field_145915_a[1] = "IS";
         sign.field_145915_a[2] = troll2.familyInfo.getName().toUpperCase();
      }

      return true;
   }

   public static boolean generatesAt(World world, int i, int k) {
      return LOTRFixedStructures.generatesAtMapImageCoords(i, k, 1419, 1134);
   }
}
