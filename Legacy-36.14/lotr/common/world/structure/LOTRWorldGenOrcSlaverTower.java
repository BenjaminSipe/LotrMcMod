package lotr.common.world.structure;

import java.util.Random;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityMordorOrc;
import lotr.common.entity.npc.LOTREntityMordorOrcSlaver;
import lotr.common.entity.npc.LOTREntityOrc;
import lotr.common.world.biome.LOTRBiomeGenNurn;
import net.minecraft.block.Block;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenOrcSlaverTower extends LOTRWorldGenStructureBase {
   public LOTRWorldGenOrcSlaverTower(boolean flag) {
      super(flag);
   }

   public boolean func_76484_a(World world, Random random, int i, int j, int k) {
      if (!this.restrictions || world.func_147439_a(i, j - 1, k) == Blocks.field_150349_c && world.func_72807_a(i, k) instanceof LOTRBiomeGenNurn) {
         int height = 5 + random.nextInt(4);
         j += height;
         int rotation = random.nextInt(4);
         if (!this.restrictions && this.usingPlayer != null) {
            rotation = this.usingPlayerRotation();
         }

         switch(rotation) {
         case 0:
            ++k;
            break;
         case 1:
            --i;
            break;
         case 2:
            --k;
            break;
         case 3:
            ++i;
         }

         int j1;
         int orcs;
         int l;
         if (this.restrictions) {
            for(j1 = i - 3; j1 <= i + 3; ++j1) {
               for(orcs = k - 3; orcs <= k + 3; ++orcs) {
                  l = world.func_72976_f(j1, orcs) - 1;
                  Block l = world.func_147439_a(j1, l, orcs);
                  if (l != Blocks.field_150349_c) {
                     return false;
                  }
               }
            }
         }

         for(j1 = i - 3; j1 <= i + 3; ++j1) {
            for(orcs = k - 3; orcs <= k + 3; ++orcs) {
               this.func_150516_a(world, j1, j, orcs, LOTRMod.planks, 3);
               this.func_150516_a(world, j1, j + 6, orcs, LOTRMod.planks, 3);
               if (Math.abs(j1 - i) == 3 || Math.abs(orcs - k) == 3) {
                  this.func_150516_a(world, j1, j + 1, orcs, LOTRMod.fence, 3);
                  this.func_150516_a(world, j1, j + 5, orcs, LOTRMod.fence, 3);
                  this.func_150516_a(world, j1, j + 7, orcs, LOTRMod.fence, 3);
               }
            }
         }

         for(j1 = i - 3; j1 <= i + 3; j1 += 6) {
            for(orcs = k - 3; orcs <= k + 3; orcs += 6) {
               for(l = j + 5; (l >= j || !LOTRMod.isOpaque(world, j1, l, orcs)) && l >= 0; --l) {
                  this.func_150516_a(world, j1, l, orcs, LOTRMod.wood, 3);
                  this.setGrassToDirt(world, j1, l - 1, orcs);
               }
            }
         }

         for(j1 = j + 2; j1 <= j + 4; ++j1) {
            this.func_150516_a(world, i - 2, j1, k - 3, LOTRMod.fence, 3);
            this.func_150516_a(world, i - 2, j1, k + 3, LOTRMod.fence, 3);
            this.func_150516_a(world, i + 2, j1, k - 3, LOTRMod.fence, 3);
            this.func_150516_a(world, i + 2, j1, k + 3, LOTRMod.fence, 3);
            this.func_150516_a(world, i - 3, j1, k - 2, LOTRMod.fence, 3);
            this.func_150516_a(world, i + 3, j1, k - 2, LOTRMod.fence, 3);
            this.func_150516_a(world, i - 3, j1, k + 2, LOTRMod.fence, 3);
            this.func_150516_a(world, i + 3, j1, k + 2, LOTRMod.fence, 3);
         }

         for(j1 = j + 11; (j1 >= j || !LOTRMod.isOpaque(world, i, j1, k)) && j1 >= 0; --j1) {
            this.func_150516_a(world, i, j1, k, LOTRMod.wood, 3);
            this.setGrassToDirt(world, i, j1 - 1, k);
            if (j1 <= j + 6) {
               this.func_150516_a(world, i, j1, k - 1, Blocks.field_150468_ap, 2);
            }
         }

         this.func_150516_a(world, i, j + 1, k - 1, LOTRMod.trapdoorCharred, 0);
         this.func_150516_a(world, i, j + 7, k - 1, LOTRMod.trapdoorCharred, 0);
         this.placeOrcTorch(world, i - 3, j + 8, k - 3);
         this.placeOrcTorch(world, i - 3, j + 8, k + 3);
         this.placeOrcTorch(world, i + 3, j + 8, k - 3);
         this.placeOrcTorch(world, i + 3, j + 8, k + 3);
         this.func_150516_a(world, i, j + 12, k, LOTRMod.fence, 3);
         this.func_150516_a(world, i, j + 13, k, LOTRMod.fence, 3);
         this.func_150516_a(world, i, j + 12, k - 1, LOTRMod.fence, 3);
         this.func_150516_a(world, i, j + 12, k + 1, LOTRMod.fence, 3);
         this.func_150516_a(world, i - 1, j + 12, k, LOTRMod.fence, 3);
         this.func_150516_a(world, i + 1, j + 12, k, LOTRMod.fence, 3);
         this.placeOrcTorch(world, i, j + 14, k);
         this.placeOrcTorch(world, i, j + 13, k - 1);
         this.placeOrcTorch(world, i, j + 13, k + 1);
         this.placeOrcTorch(world, i - 1, j + 13, k);
         this.placeOrcTorch(world, i + 1, j + 13, k);
         LOTREntityMordorOrcSlaver slaver = new LOTREntityMordorOrcSlaver(world);
         slaver.func_70012_b((double)i + 1.5D, (double)(j + 7), (double)k + 1.5D, 0.0F, 0.0F);
         slaver.func_110161_a((IEntityLivingData)null);
         slaver.setPersistentAndTraderShouldRespawn();
         world.func_72838_d(slaver);
         slaver.func_110171_b(i, j + 6, k, 12);
         orcs = 2 + random.nextInt(3);

         for(l = 0; l < orcs; ++l) {
            LOTREntityOrc orc = new LOTREntityMordorOrc(world);
            orc.func_70012_b((double)i + 1.5D, (double)(j + 1), (double)k + 1.5D, 0.0F, 0.0F);
            orc.func_110161_a((IEntityLivingData)null);
            orc.isNPCPersistent = true;
            world.func_72838_d(orc);
            orc.func_110171_b(i, j + 1, k, 8);
         }

         return true;
      } else {
         return false;
      }
   }
}
