package lotr.common.world.structure2;

import java.util.Random;
import lotr.common.entity.npc.LOTRNames;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenHarnedorVillageSign extends LOTRWorldGenHarnedorStructure {
   private String[] signText = LOTRNames.getHaradVillageName(new Random());

   public LOTRWorldGenHarnedorVillageSign(boolean flag) {
      super(flag);
   }

   public LOTRWorldGenHarnedorVillageSign setSignText(String[] s) {
      this.signText = s;
      return this;
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

      for(int j1 = 0; (j1 >= 0 || !this.isOpaque(world, 0, j1, 0)) && this.getY(j1) >= 0; --j1) {
         this.setBlockAndMetadata(world, 0, j1, 0, this.woodBlock, this.woodMeta);
         this.setGrassToDirt(world, 0, j1 - 1, 0);
      }

      this.setBlockAndMetadata(world, 0, 1, 0, this.woodBlock, this.woodMeta);
      this.setBlockAndMetadata(world, 0, 2, 0, this.woodBlock, this.woodMeta);
      this.setBlockAndMetadata(world, 0, 3, 0, this.woodBlock, this.woodMeta);
      this.setBlockAndMetadata(world, 0, 4, 0, this.fenceBlock, this.fenceMeta);
      this.placeSkull(world, random, 0, 5, 0);
      this.setBlockAndMetadata(world, -1, 3, 0, Blocks.field_150478_aa, 1);
      this.setBlockAndMetadata(world, 1, 3, 0, Blocks.field_150478_aa, 2);
      this.setBlockAndMetadata(world, 0, 3, -1, Blocks.field_150478_aa, 4);
      this.setBlockAndMetadata(world, 0, 3, 1, Blocks.field_150478_aa, 3);
      if (this.signText != null) {
         this.placeSign(world, -1, 2, 0, Blocks.field_150444_as, 5, this.signText);
         this.placeSign(world, 1, 2, 0, Blocks.field_150444_as, 4, this.signText);
         this.placeSign(world, 0, 2, -1, Blocks.field_150444_as, 2, this.signText);
         this.placeSign(world, 0, 2, 1, Blocks.field_150444_as, 3, this.signText);
      }

      return true;
   }
}
