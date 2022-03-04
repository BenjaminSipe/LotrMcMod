package lotr.common.world.structure2;

import java.util.Random;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityTauredainFarmer;
import lotr.common.entity.npc.LOTREntityTauredainFarmhand;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class LOTRWorldGenTauredainVillageFarm extends LOTRWorldGenTauredainHouse {
   private Block cropBlock;
   private int cropMeta;
   private Item seedItem;
   private boolean melon;

   public LOTRWorldGenTauredainVillageFarm(boolean flag) {
      super(flag);
   }

   protected int getOffset() {
      return 4;
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      if (!super.generateWithSetRotation(world, random, i, j, k, rotation)) {
         return false;
      } else {
         int randomCrop = random.nextInt(8);
         if (randomCrop != 0 && randomCrop != 1) {
            if (randomCrop != 2 && randomCrop != 3) {
               if (randomCrop == 4) {
                  this.cropBlock = Blocks.field_150464_aj;
                  this.cropMeta = 7;
                  this.seedItem = Items.field_151014_N;
                  this.melon = false;
               } else if (randomCrop == 5) {
                  this.cropBlock = Blocks.field_150459_bM;
                  this.cropMeta = 7;
                  this.seedItem = Items.field_151172_bF;
                  this.melon = false;
               } else if (randomCrop == 6 || randomCrop == 7) {
                  this.cropBlock = Blocks.field_150394_bc;
                  this.cropMeta = 7;
                  this.seedItem = Items.field_151081_bc;
                  this.melon = true;
               }
            } else {
               this.cropBlock = LOTRMod.cornStalk;
               this.cropMeta = 0;
               this.seedItem = Item.func_150898_a(LOTRMod.cornStalk);
               this.melon = false;
            }
         } else {
            this.cropBlock = Blocks.field_150469_bN;
            this.cropMeta = 7;
            this.seedItem = Items.field_151174_bG;
            this.melon = false;
         }

         int i1;
         int k1;
         int j1;
         for(i1 = -4; i1 <= 4; ++i1) {
            for(k1 = -3; k1 <= 3; ++k1) {
               for(j1 = 1; j1 <= 4; ++j1) {
                  this.setAir(world, i1, j1, k1);
               }
            }
         }

         this.loadStrScan("taurethrim_farm");
         this.associateBlockAlias("BRICK_STAIR", this.brickStairBlock);
         this.associateBlockMetaAlias("BRICK_WALL", this.brickWallBlock, this.brickWallMeta);
         this.associateBlockMetaAlias("FENCE", this.fenceBlock, this.fenceMeta);
         this.associateBlockAlias("FENCE_GATE", this.fenceGateBlock);
         this.associateBlockMetaAlias("CROP", this.cropBlock, this.cropMeta);
         this.generateStrScan(world, random, 0, 0, 0);
         if (this.melon) {
            for(i1 = -2; i1 <= 2; ++i1) {
               this.setBlockAndMetadata(world, 0, 1, i1, this.brickBlock, this.brickMeta);
            }

            for(i1 = -1; i1 <= 1; ++i1) {
               this.setBlockAndMetadata(world, i1, 0, 0, Blocks.field_150406_ce, 12);
               this.setBlockAndMetadata(world, i1, 1, 0, Blocks.field_150355_j, 0);
               this.setAir(world, i1, 2, 0);
            }

            int[] var14 = new int[]{-1, 1};
            k1 = var14.length;

            for(j1 = 0; j1 < k1; ++j1) {
               int k1 = var14[j1];

               for(int i1 = -3; i1 <= 3; ++i1) {
                  if (i1 != 0) {
                     this.setBlockAndMetadata(world, i1, 0, k1, Blocks.field_150354_m, 0);
                     this.setBlockAndMetadata(world, i1, 1, k1, LOTRMod.mudGrass, 0);
                  }
               }
            }
         }

         if (random.nextInt(3) == 0) {
            LOTREntityTauredainFarmer farmer = new LOTREntityTauredainFarmer(world);
            this.spawnNPCAndSetHome(farmer, world, 0, 2, 1, 4);
         }

         LOTREntityTauredainFarmhand farmhand1 = new LOTREntityTauredainFarmhand(world);
         farmhand1.seedsItem = this.seedItem;
         this.spawnNPCAndSetHome(farmhand1, world, -2, 2, 0, 6);
         LOTREntityTauredainFarmhand farmhand2 = new LOTREntityTauredainFarmhand(world);
         farmhand2.seedsItem = this.seedItem;
         this.spawnNPCAndSetHome(farmhand2, world, 2, 2, 0, 6);
         return true;
      }
   }
}
