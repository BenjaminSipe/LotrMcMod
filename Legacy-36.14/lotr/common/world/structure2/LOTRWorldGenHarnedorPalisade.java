package lotr.common.world.structure2;

import java.util.Random;
import lotr.common.LOTRMod;
import lotr.common.item.LOTRItemBanner;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenHarnedorPalisade extends LOTRWorldGenHarnedorStructure {
   private boolean isTall = false;

   public LOTRWorldGenHarnedorPalisade(boolean flag) {
      super(flag);
   }

   public void setTall() {
      this.isTall = true;
   }

   protected void setupRandomBlocks(Random random) {
      super.setupRandomBlocks(random);
      int randomWood = random.nextInt(3);
      if (randomWood == 0) {
         this.woodBlock = LOTRMod.wood4;
         this.woodMeta = 2;
      } else if (randomWood == 1) {
         this.woodBlock = Blocks.field_150364_r;
         this.woodMeta = 0;
      } else if (randomWood == 2) {
         this.woodBlock = LOTRMod.wood6;
         this.woodMeta = 3;
      }

   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, 0);
      this.setupRandomBlocks(random);
      if (this.restrictions) {
         int i1 = 0;
         int k1 = 0;
         int j1 = this.getTopBlock(world, i1, k1) - 1;
         if (!this.isSurface(world, i1, j1, k1)) {
            return false;
         }
      }

      int height = 3 + random.nextInt(2);
      if (this.isTall) {
         height += 4;
      }

      if (this.isRuined()) {
         height = Math.max(1, height - 2);
      }

      for(int j1 = height; (j1 >= 0 || !this.isOpaque(world, 0, j1, 0)) && this.getY(j1) >= 0; --j1) {
         this.setBlockAndMetadata(world, 0, j1, 0, this.woodBlock, this.woodMeta);
         this.setGrassToDirt(world, 0, j1 - 1, 0);
      }

      if (this.isTall || random.nextInt(5) == 0) {
         this.setBlockAndMetadata(world, 0, height + 1, 0, this.fenceBlock, this.fenceMeta);
         this.placeSkull(world, random, 0, height + 2, 0);
      }

      if (!this.isRuined() && this.isTall) {
         this.placeWallBanner(world, 0, height, 0, LOTRItemBanner.BannerType.NEAR_HARAD, 2);
      }

      return true;
   }
}
