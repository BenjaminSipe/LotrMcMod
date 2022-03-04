package lotr.common.world.structure2;

import java.util.Random;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityHarnedorFarmer;
import lotr.common.entity.npc.LOTREntityHarnedorFarmhand;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class LOTRWorldGenHarnedorFarm extends LOTRWorldGenHarnedorStructure {
   private Block crop1Block;
   private Item seed1;
   private Block crop2Block;
   private Item seed2;

   public LOTRWorldGenHarnedorFarm(boolean flag) {
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
      this.setOriginAndRotation(world, i, j, k, rotation, 5);
      this.setupRandomBlocks(random);
      int minHeight;
      int maxHeight;
      int j1;
      int i1;
      int j1;
      if (this.restrictions) {
         minHeight = 0;
         maxHeight = 0;

         for(j1 = -4; j1 <= 4; ++j1) {
            for(i1 = -4; i1 <= 4; ++i1) {
               j1 = this.getTopBlock(world, j1, i1) - 1;
               if (!this.isSurface(world, j1, j1, i1)) {
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
         for(maxHeight = -4; maxHeight <= 4; ++maxHeight) {
            for(j1 = -1; !this.isOpaque(world, minHeight, j1, maxHeight) && this.getY(j1) >= 0; --j1) {
               this.setBlockAndMetadata(world, minHeight, j1, maxHeight, Blocks.field_150346_d, 0);
               this.setGrassToDirt(world, minHeight, j1 - 1, maxHeight);
            }

            for(j1 = 1; j1 <= 4; ++j1) {
               this.setAir(world, minHeight, j1, maxHeight);
            }
         }
      }

      this.loadStrScan("harnedor_farm");
      this.associateBlockMetaAlias("WOOD", this.woodBlock, this.woodMeta);
      this.associateBlockMetaAlias("PLANK", this.plankBlock, this.plankMeta);
      this.associateBlockMetaAlias("PLANK_SLAB", this.plankSlabBlock, this.plankSlabMeta);
      this.associateBlockMetaAlias("PLANK_SLAB_INV", this.plankSlabBlock, this.plankSlabMeta | 8);
      this.associateBlockMetaAlias("FENCE", this.fenceBlock, this.fenceMeta);
      this.associateBlockAlias("FENCE_GATE", this.fenceGateBlock);
      this.associateBlockMetaAlias("ROOF", this.roofBlock, this.roofMeta);
      this.associateBlockAlias("CROP1", this.crop1Block);
      this.associateBlockAlias("CROP2", this.crop2Block);
      this.generateStrScan(world, random, 0, 0, 0);
      this.placeSkull(world, random, 0, 4, 0);
      int[] var15 = new int[]{-2, 2};
      maxHeight = var15.length;

      for(j1 = 0; j1 < maxHeight; ++j1) {
         i1 = var15[j1];
         j1 = 0;

         for(int step = 0; step < 6; ++step) {
            int k1 = -5 - step;
            int j2;
            if (this.isOpaque(world, i1, j1 + 1, k1)) {
               this.setAir(world, i1, j1 + 1, k1);
               this.setAir(world, i1, j1 + 2, k1);
               this.setAir(world, i1, j1 + 3, k1);
               this.setBlockAndMetadata(world, i1, j1, k1, Blocks.field_150349_c, 0);
               this.setGrassToDirt(world, i1, j1 - 1, k1);

               for(j2 = j1 - 1; !this.isOpaque(world, i1, j2, k1) && this.getY(j2) >= 0; --j2) {
                  this.setBlockAndMetadata(world, i1, j2, k1, Blocks.field_150346_d, 0);
                  this.setGrassToDirt(world, i1, j2 - 1, k1);
               }

               ++j1;
            } else {
               if (this.isOpaque(world, i1, j1, k1)) {
                  break;
               }

               this.setAir(world, i1, j1 + 1, k1);
               this.setAir(world, i1, j1 + 2, k1);
               this.setAir(world, i1, j1 + 3, k1);
               this.setBlockAndMetadata(world, i1, j1, k1, Blocks.field_150349_c, 0);
               this.setGrassToDirt(world, i1, j1 - 1, k1);

               for(j2 = j1 - 1; !this.isOpaque(world, i1, j2, k1) && this.getY(j2) >= 0; --j2) {
                  this.setBlockAndMetadata(world, i1, j2, k1, Blocks.field_150346_d, 0);
                  this.setGrassToDirt(world, i1, j2 - 1, k1);
               }

               --j1;
            }
         }
      }

      if (random.nextInt(4) == 0) {
         LOTREntityHarnedorFarmer farmer = new LOTREntityHarnedorFarmer(world);
         this.spawnNPCAndSetHome(farmer, world, 0, 1, 1, 8);
      }

      LOTREntityHarnedorFarmhand farmhand1 = new LOTREntityHarnedorFarmhand(world);
      farmhand1.seedsItem = this.seed1;
      this.spawnNPCAndSetHome(farmhand1, world, -2, 1, 0, 8);
      LOTREntityHarnedorFarmhand farmhand2 = new LOTREntityHarnedorFarmhand(world);
      farmhand2.seedsItem = this.seed2;
      this.spawnNPCAndSetHome(farmhand2, world, 2, 1, 0, 8);
      return true;
   }
}
