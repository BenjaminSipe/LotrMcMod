package lotr.common.world.structure2;

import java.util.Random;
import lotr.common.LOTRMod;
import lotr.common.entity.animal.LOTREntityBird;
import lotr.common.entity.animal.LOTREntityButterfly;
import lotr.common.entity.npc.LOTREntityBreeGuard;
import lotr.common.item.LOTRItemBanner;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTRWorldGenBreeMarket extends LOTRWorldGenBreeStructure {
   private LOTRWorldGenBreeMarketStall[] presetStalls;
   private boolean frontStepsOnly = false;

   public LOTRWorldGenBreeMarket(boolean flag) {
      super(flag);
   }

   public LOTRWorldGenBreeMarket setStalls(LOTRWorldGenBreeMarketStall... stalls) {
      if (stalls.length != 4) {
         throw new IllegalArgumentException("Error: Market must have 4 stalls, but " + stalls.length + " supplied");
      } else {
         this.presetStalls = stalls;
         return this;
      }
   }

   public LOTRWorldGenBreeMarket setFrontStepsOnly(boolean flag) {
      this.frontStepsOnly = flag;
      return this;
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, 13);
      this.setupRandomBlocks(random);
      int i1;
      int k1;
      int step;
      if (this.restrictions) {
         for(i1 = -12; i1 <= 12; ++i1) {
            for(k1 = -12; k1 <= 12; ++k1) {
               step = this.getTopBlock(world, i1, k1) - 1;
               if (!this.isSurface(world, i1, step, k1)) {
                  return false;
               }
            }
         }
      }

      int j1;
      int j2;
      for(i1 = -12; i1 <= 12; ++i1) {
         for(k1 = -12; k1 <= 12; ++k1) {
            step = Math.abs(i1);
            j1 = Math.abs(k1);
            boolean marketBounds = false;
            if (step <= 1 && j1 <= 12 || j1 <= 1 && step <= 12) {
               marketBounds = true;
            } else if ((step > 4 || j1 > 11) && (j1 > 4 || step > 11)) {
               if ((step > 6 || j1 > 10) && (j1 > 6 || step > 10)) {
                  if (step <= 7 && j1 <= 9 || j1 <= 7 && step <= 9) {
                     marketBounds = true;
                  } else if (step <= 8 && j1 <= 8) {
                     marketBounds = true;
                  }
               } else {
                  marketBounds = true;
               }
            } else {
               marketBounds = true;
            }

            if (marketBounds) {
               for(j2 = 1; j2 <= 8; ++j2) {
                  this.setAir(world, i1, j2, k1);
               }
            }
         }
      }

      this.loadStrScan("bree_market");
      this.associateBlockMetaAlias("BRICK", this.brickBlock, this.brickMeta);
      this.associateBlockMetaAlias("BRICK_SLAB", Blocks.field_150333_U, 5);
      this.associateBlockMetaAlias("COBBLE", Blocks.field_150347_e, 0);
      this.associateBlockMetaAlias("COBBLE_SLAB", Blocks.field_150333_U, 3);
      this.associateBlockAlias("COBBLE_STAIR", Blocks.field_150446_ar);
      this.associateBlockMetaAlias("COBBLE_WALL", Blocks.field_150463_bK, 0);
      this.associateBlockMetaAlias("BEAM", this.beamBlock, this.beamMeta);
      this.addBlockMetaAliasOption("GROUND", 1, Blocks.field_150351_n, 0);
      this.addBlockMetaAliasOption("GROUND", 1, Blocks.field_150349_c, 0);
      this.addBlockMetaAliasOption("GROUND", 1, Blocks.field_150346_d, 1);
      this.addBlockMetaAliasOption("GROUND", 1, LOTRMod.dirtPath, 0);
      this.addBlockMetaAliasOption("THATCH_FLOOR", 1, LOTRMod.thatchFloor, 0);
      this.setBlockAliasChance("THATCH_FLOOR", 0.15F);
      this.associateBlockMetaAlias("LEAF", Blocks.field_150362_t, 4);
      this.associateBlockMetaAlias("LEAF_FLOOR", LOTRMod.fallenLeaves, 0);
      this.setBlockAliasChance("LEAF_FLOOR", 0.5F);
      this.generateStrScan(world, random, 0, 0, 0);
      int maxSteps = true;

      int i1;
      for(k1 = -1; k1 <= 1; ++k1) {
         for(step = 0; step < 12; ++step) {
            j1 = -1 - step;
            i1 = -13 - step;
            if (this.isOpaque(world, k1, j1, i1)) {
               break;
            }

            this.placeRandomFloor(world, random, k1, j1, i1);
            this.setGrassToDirt(world, k1, j1 - 1, i1);

            for(j2 = j1 - 1; !this.isOpaque(world, k1, j2, i1) && this.getY(j2) >= 0; --j2) {
               this.setBlockAndMetadata(world, k1, j2, i1, Blocks.field_150346_d, 0);
               this.setGrassToDirt(world, k1, j2 - 1, i1);
            }
         }

         if (!this.frontStepsOnly) {
            for(step = 0; step < 12; ++step) {
               j1 = -1 - step;
               i1 = 13 + step;
               if (this.isOpaque(world, k1, j1, i1)) {
                  break;
               }

               this.placeRandomFloor(world, random, k1, j1, i1);
               this.setGrassToDirt(world, k1, j1 - 1, i1);

               for(j2 = j1 - 1; !this.isOpaque(world, k1, j2, i1) && this.getY(j2) >= 0; --j2) {
                  this.setBlockAndMetadata(world, k1, j2, i1, Blocks.field_150346_d, 0);
                  this.setGrassToDirt(world, k1, j2 - 1, i1);
               }
            }
         }
      }

      if (!this.frontStepsOnly) {
         for(k1 = -1; k1 <= 1; ++k1) {
            for(step = 0; step < 12; ++step) {
               j1 = -1 - step;
               i1 = -13 - step;
               if (this.isOpaque(world, i1, j1, k1)) {
                  break;
               }

               this.placeRandomFloor(world, random, i1, j1, k1);
               this.setGrassToDirt(world, i1, j1 - 1, k1);

               for(j2 = j1 - 1; !this.isOpaque(world, i1, j2, k1) && this.getY(j2) >= 0; --j2) {
                  this.setBlockAndMetadata(world, i1, j2, k1, Blocks.field_150346_d, 0);
                  this.setGrassToDirt(world, i1, j2 - 1, k1);
               }
            }

            for(step = 0; step < 12; ++step) {
               j1 = -1 - step;
               i1 = 13 + step;
               if (this.isOpaque(world, i1, j1, k1)) {
                  break;
               }

               this.placeRandomFloor(world, random, i1, j1, k1);
               this.setGrassToDirt(world, i1, j1 - 1, k1);

               for(j2 = j1 - 1; !this.isOpaque(world, i1, j2, k1) && this.getY(j2) >= 0; --j2) {
                  this.setBlockAndMetadata(world, i1, j2, k1, Blocks.field_150346_d, 0);
                  this.setGrassToDirt(world, i1, j2 - 1, k1);
               }
            }
         }
      }

      this.placeWallBanner(world, 0, 4, 0, LOTRItemBanner.BannerType.BREE, 2);
      this.placeAnimalJar(world, -3, 1, 4, LOTRMod.birdCageWood, 0, new LOTREntityBird(world));
      this.placeAnimalJar(world, 3, 2, -8, LOTRMod.birdCage, 1, new LOTREntityBird(world));
      this.placeAnimalJar(world, -1, 4, 0, LOTRMod.birdCageWood, 0, new LOTREntityBird(world));
      this.placeAnimalJar(world, 0, 2, 1, LOTRMod.butterflyJar, 0, new LOTREntityButterfly(world));
      LOTREntityBreeGuard armorGuard = new LOTREntityBreeGuard(world);
      armorGuard.func_110161_a((IEntityLivingData)null);
      this.placeArmorStand(world, 2, 1, 0, 3, new ItemStack[]{armorGuard.func_71124_b(4), armorGuard.func_71124_b(3), null, null});
      LOTRWorldGenBreeMarketStall[] stalls = this.presetStalls;
      if (stalls == null) {
         stalls = LOTRWorldGenBreeMarketStall.getRandomStalls(random, this.notifyChanges, 4);
      }

      this.generateSubstructureWithRestrictionFlag(stalls[0], world, random, 6, 1, 3, 0, false);
      this.generateSubstructureWithRestrictionFlag(stalls[1], world, random, 3, 1, -6, 1, false);
      this.generateSubstructureWithRestrictionFlag(stalls[2], world, random, -6, 1, -3, 2, false);
      this.generateSubstructureWithRestrictionFlag(stalls[3], world, random, -3, 1, 6, 3, false);
      return true;
   }
}
