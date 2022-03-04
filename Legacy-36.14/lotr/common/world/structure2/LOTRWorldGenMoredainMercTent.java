package lotr.common.world.structure2;

import com.google.common.math.IntMath;
import java.util.Random;
import lotr.common.LOTRMod;
import lotr.common.item.LOTRItemBanner;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenMoredainMercTent extends LOTRWorldGenStructureBase2 {
   private Block fenceBlock;
   private int fenceMeta;
   private Block tentBlock;
   private int tentMeta;
   private Block tent2Block;
   private int tent2Meta;
   private Block tableBlock;
   private LOTRChestContents chestContents;
   private LOTRItemBanner.BannerType bannerType;

   public LOTRWorldGenMoredainMercTent(boolean flag) {
      super(flag);
   }

   protected void setupRandomBlocks(Random random) {
      this.fenceBlock = LOTRMod.fence2;
      this.fenceMeta = 2;
      int randomWool = random.nextInt(3);
      if (randomWool == 0) {
         this.tentBlock = Blocks.field_150325_L;
         this.tentMeta = 14;
      } else if (randomWool == 1) {
         this.tentBlock = Blocks.field_150325_L;
         this.tentMeta = 12;
      } else if (randomWool == 2) {
         this.tentBlock = Blocks.field_150325_L;
         this.tentMeta = 1;
      }

      this.tent2Block = Blocks.field_150325_L;
      this.tent2Meta = 15;
      this.chestContents = LOTRChestContents.MOREDAIN_MERC_TENT;
      if (random.nextBoolean()) {
         this.tableBlock = LOTRMod.nearHaradTable;
         this.bannerType = LOTRItemBanner.BannerType.NEAR_HARAD;
      } else {
         this.tableBlock = LOTRMod.moredainTable;
         this.bannerType = LOTRItemBanner.BannerType.MOREDAIN;
      }

   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, 4);
      this.setupRandomBlocks(random);
      int i1;
      int k1;
      int j1;
      if (this.restrictions) {
         for(i1 = -2; i1 <= 2; ++i1) {
            for(k1 = -3; k1 <= 3; ++k1) {
               j1 = this.getTopBlock(world, i1, k1) - 1;
               if (!this.isSurface(world, i1, j1, k1)) {
                  return false;
               }
            }
         }
      }

      int randomGround;
      for(i1 = -2; i1 <= 2; ++i1) {
         for(k1 = -3; k1 <= 3; ++k1) {
            for(j1 = 0; (j1 >= 0 || !this.isOpaque(world, i1, j1, k1)) && this.getY(j1) >= 0; --j1) {
               randomGround = random.nextInt(3);
               if (randomGround == 0) {
                  if (j1 == 0) {
                     this.setBiomeTop(world, i1, j1, k1);
                  } else {
                     this.setBiomeFiller(world, i1, j1, k1);
                  }
               } else if (randomGround == 1) {
                  this.setBlockAndMetadata(world, i1, j1, k1, Blocks.field_150351_n, 0);
               } else if (randomGround == 2) {
                  this.setBlockAndMetadata(world, i1, j1, k1, Blocks.field_150322_A, 0);
               }

               this.setGrassToDirt(world, i1, j1 - 1, k1);
            }

            for(j1 = 1; j1 <= 3; ++j1) {
               this.setAir(world, i1, j1, k1);
            }
         }
      }

      for(i1 = -3; i1 <= 3; ++i1) {
         boolean tent2 = IntMath.mod(i1, 2) == 0;
         Block block = tent2 ? this.tent2Block : this.tentBlock;
         randomGround = tent2 ? this.tent2Meta : this.tentMeta;
         int[] var11 = new int[]{-2, 2};
         int var12 = var11.length;

         for(int var13 = 0; var13 < var12; ++var13) {
            int i1 = var11[var13];

            for(int j1 = 1; j1 <= 2; ++j1) {
               this.setBlockAndMetadata(world, i1, j1, i1, block, randomGround);
            }

            this.setGrassToDirt(world, i1, 0, i1);
         }

         this.setBlockAndMetadata(world, -1, 3, i1, block, randomGround);
         this.setBlockAndMetadata(world, 1, 3, i1, block, randomGround);
         this.setBlockAndMetadata(world, 0, 4, i1, block, randomGround);
         if (Math.abs(i1) == 3) {
            this.setBlockAndMetadata(world, 0, 5, i1, block, randomGround);
         }
      }

      for(i1 = 1; i1 <= 3; ++i1) {
         this.setBlockAndMetadata(world, 0, i1, -3, this.fenceBlock, this.fenceMeta);
         this.setBlockAndMetadata(world, 0, i1, 3, this.fenceBlock, this.fenceMeta);
      }

      this.setBlockAndMetadata(world, -1, 2, -3, Blocks.field_150478_aa, 2);
      this.setBlockAndMetadata(world, 1, 2, -3, Blocks.field_150478_aa, 1);
      this.setBlockAndMetadata(world, -1, 2, 3, Blocks.field_150478_aa, 2);
      this.setBlockAndMetadata(world, 1, 2, 3, Blocks.field_150478_aa, 1);
      if (random.nextBoolean()) {
         this.placeChest(world, random, -1, 1, 0, 4, this.chestContents);
         this.setBlockAndMetadata(world, -1, 1, -1, Blocks.field_150462_ai, 0);
         this.setGrassToDirt(world, -1, 0, -1);
         this.setBlockAndMetadata(world, -1, 1, 1, this.tableBlock, 0);
         this.setGrassToDirt(world, -1, 0, 1);
      } else {
         this.placeChest(world, random, 1, 1, 0, 5, this.chestContents);
         this.setBlockAndMetadata(world, 1, 1, -1, Blocks.field_150462_ai, 0);
         this.setGrassToDirt(world, 1, 0, -1);
         this.setBlockAndMetadata(world, 1, 1, 1, this.tableBlock, 0);
         this.setGrassToDirt(world, 1, 0, 1);
      }

      this.placeWallBanner(world, 0, 5, -3, this.bannerType, 2);
      this.placeWallBanner(world, 0, 5, 3, this.bannerType, 0);
      return true;
   }
}
