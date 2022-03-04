package lotr.common.world.structure2;

import java.util.Random;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityGulfFarmer;
import lotr.common.entity.npc.LOTREntityHaradSlave;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class LOTRWorldGenGulfFarm extends LOTRWorldGenGulfStructure {
   private Block crop1Block;
   private Item seed1;
   private Block crop2Block;
   private Item seed2;

   public LOTRWorldGenGulfFarm(boolean flag) {
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

         for(i1 = -5; i1 <= 5; ++i1) {
            for(k1 = -5; k1 <= 5; ++k1) {
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

      for(minHeight = -5; minHeight <= 5; ++minHeight) {
         for(maxHeight = -5; maxHeight <= 5; ++maxHeight) {
            i1 = Math.abs(minHeight);
            k1 = Math.abs(maxHeight);
            if (i1 != 5 || k1 != 5) {
               for(j1 = 1; j1 <= 6; ++j1) {
                  this.setAir(world, minHeight, j1, maxHeight);
               }
            }
         }
      }

      this.loadStrScan("gulf_farm");
      this.associateBlockMetaAlias("WOOD", this.woodBlock, this.woodMeta);
      this.associateBlockMetaAlias("FENCE", this.fenceBlock, this.fenceMeta);
      this.associateBlockAlias("FENCE_GATE", this.fenceGateBlock);
      this.associateBlockMetaAlias("CROP1", this.crop1Block, 7);
      this.associateBlockMetaAlias("CROP2", this.crop2Block, 7);
      this.associateBlockMetaAlias("FLAG", this.flagBlock, this.flagMeta);
      this.associateBlockMetaAlias("BONE", this.boneBlock, this.boneMeta);
      this.generateStrScan(world, random, 0, 0, 0);
      if (random.nextInt(4) == 0) {
         LOTREntityGulfFarmer farmer = new LOTREntityGulfFarmer(world);
         this.spawnNPCAndSetHome(farmer, world, 0, 1, -1, 8);
      }

      LOTREntityHaradSlave farmhand1 = new LOTREntityHaradSlave(world);
      farmhand1.seedsItem = this.seed1;
      this.spawnNPCAndSetHome(farmhand1, world, -2, 1, 0, 8);
      LOTREntityHaradSlave farmhand2 = new LOTREntityHaradSlave(world);
      farmhand2.seedsItem = this.seed2;
      this.spawnNPCAndSetHome(farmhand2, world, 2, 1, 0, 8);
      return true;
   }
}
