package lotr.common.world.structure2;

import java.util.Random;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityHaradSlave;
import lotr.common.entity.npc.LOTREntityNearHaradrimBase;
import lotr.common.entity.npc.LOTREntitySouthronFarmer;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class LOTRWorldGenSouthronFarm extends LOTRWorldGenSouthronStructure {
   private Block crop1Block;
   private Item seed1;
   private Block crop2Block;
   private Item seed2;

   public LOTRWorldGenSouthronFarm(boolean flag) {
      super(flag);
   }

   protected void setupRandomBlocks(Random random) {
      super.setupRandomBlocks(random);
      int randomCrop;
      if (random.nextBoolean()) {
         this.crop1Block = Blocks.field_150464_aj;
         this.seed1 = Items.field_151014_N;
      } else {
         randomCrop = random.nextInt(4);
         if (randomCrop == 0) {
            this.crop1Block = Blocks.field_150459_bM;
            this.seed1 = Items.field_151172_bF;
         } else if (randomCrop == 1) {
            this.crop1Block = Blocks.field_150469_bN;
            this.seed1 = Items.field_151174_bG;
         } else if (randomCrop == 2) {
            this.crop1Block = LOTRMod.lettuceCrop;
            this.seed1 = LOTRMod.lettuce;
         } else if (randomCrop == 3) {
            this.crop1Block = LOTRMod.turnipCrop;
            this.seed1 = LOTRMod.turnip;
         }
      }

      if (random.nextBoolean()) {
         this.crop2Block = Blocks.field_150464_aj;
         this.seed2 = Items.field_151014_N;
      } else {
         randomCrop = random.nextInt(4);
         if (randomCrop == 0) {
            this.crop2Block = Blocks.field_150459_bM;
            this.seed2 = Items.field_151172_bF;
         } else if (randomCrop == 1) {
            this.crop2Block = Blocks.field_150469_bN;
            this.seed2 = Items.field_151174_bG;
         } else if (randomCrop == 2) {
            this.crop2Block = LOTRMod.lettuceCrop;
            this.seed2 = LOTRMod.lettuce;
         } else if (randomCrop == 3) {
            this.crop2Block = LOTRMod.turnipCrop;
            this.seed2 = LOTRMod.turnip;
         }
      }

   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, 6);
      this.setupRandomBlocks(random);
      int minHeight;
      int maxHeight;
      int i1;
      int k1;
      int j1;
      if (this.restrictions) {
         minHeight = 0;
         maxHeight = 0;

         for(i1 = -4; i1 <= 4; ++i1) {
            for(k1 = -6; k1 <= 6; ++k1) {
               j1 = this.getTopBlock(world, i1, k1) - 1;
               if (!this.isSurface(world, i1, j1, k1)) {
                  return false;
               }

               if (j1 < minHeight) {
                  minHeight = j1;
               }

               if (j1 > maxHeight) {
                  maxHeight = j1;
               }

               if (maxHeight - minHeight > 6) {
                  return false;
               }
            }
         }
      }

      for(minHeight = -4; minHeight <= 4; ++minHeight) {
         for(maxHeight = -6; maxHeight <= 6; ++maxHeight) {
            i1 = Math.abs(minHeight);
            k1 = Math.abs(maxHeight);
            if (i1 != 4 || k1 != 6) {
               for(j1 = -1; !this.isOpaque(world, minHeight, j1, maxHeight) && this.getY(j1) >= 0; --j1) {
                  this.setBlockAndMetadata(world, minHeight, j1, maxHeight, this.stoneBlock, this.stoneMeta);
                  this.setGrassToDirt(world, minHeight, j1 - 1, maxHeight);
               }

               for(j1 = 1; j1 <= 4; ++j1) {
                  this.setAir(world, minHeight, j1, maxHeight);
               }
            }
         }
      }

      this.loadStrScan("southron_farm");
      this.associateBlockMetaAlias("STONE", this.stoneBlock, this.stoneMeta);
      this.associateBlockMetaAlias("BRICK", this.brickBlock, this.brickMeta);
      this.associateBlockMetaAlias("BRICK_SLAB", this.brickSlabBlock, this.brickSlabMeta);
      this.associateBlockMetaAlias("FENCE", this.fenceBlock, this.fenceMeta);
      this.associateBlockAlias("FENCE_GATE", this.fenceGateBlock);
      this.associateBlockAlias("CROP1", this.crop1Block);
      this.associateBlockAlias("CROP2", this.crop2Block);
      this.generateStrScan(world, random, 0, 0, 0);
      this.placeSkull(world, random, -4, 4, 0);
      this.placeSkull(world, random, 4, 4, 0);
      if (random.nextInt(4) == 0) {
         LOTREntityNearHaradrimBase farmer = this.createFarmer(world);
         this.spawnNPCAndSetHome(farmer, world, 0, 1, 1, 8);
      }

      LOTREntityHaradSlave farmhand1 = new LOTREntityHaradSlave(world);
      farmhand1.seedsItem = this.seed1;
      this.spawnNPCAndSetHome(farmhand1, world, -2, 1, 0, 8);
      LOTREntityHaradSlave farmhand2 = new LOTREntityHaradSlave(world);
      farmhand2.seedsItem = this.seed2;
      this.spawnNPCAndSetHome(farmhand2, world, 2, 1, 0, 8);
      return true;
   }

   protected LOTREntityNearHaradrimBase createFarmer(World world) {
      return new LOTREntitySouthronFarmer(world);
   }
}
