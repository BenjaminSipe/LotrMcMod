package lotr.common.world.structure2;

import java.util.Random;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public abstract class LOTRWorldGenMoredainHut extends LOTRWorldGenStructureBase2 {
   protected Block clayBlock;
   protected int clayMeta;
   protected Block stainedClayBlock;
   protected int stainedClayMeta;
   protected Block brickBlock;
   protected int brickMeta;
   protected Block brickSlabBlock;
   protected int brickSlabMeta;
   protected Block plankBlock;
   protected int plankMeta;
   protected Block plankSlabBlock;
   protected int plankSlabMeta;
   protected Block fenceBlock;
   protected int fenceMeta;
   protected Block thatchBlock;
   protected int thatchMeta;
   protected Block thatchSlabBlock;
   protected int thatchSlabMeta;

   public LOTRWorldGenMoredainHut(boolean flag) {
      super(flag);
      this.clayBlock = Blocks.field_150405_ch;
      this.clayMeta = 0;
      this.stainedClayBlock = Blocks.field_150406_ce;
      this.stainedClayMeta = 1;
      this.brickBlock = LOTRMod.brick3;
      this.brickMeta = 10;
      this.brickSlabBlock = LOTRMod.slabSingle7;
      this.brickSlabMeta = 0;
      this.plankBlock = Blocks.field_150344_f;
      this.plankMeta = 4;
      this.plankSlabBlock = Blocks.field_150376_bx;
      this.plankSlabMeta = 4;
      this.fenceBlock = Blocks.field_150422_aJ;
      this.fenceMeta = 4;
      this.thatchBlock = LOTRMod.thatch;
      this.thatchMeta = 0;
      this.thatchSlabBlock = LOTRMod.slabSingleThatch;
      this.thatchSlabMeta = 0;
   }

   protected abstract int getOffset();

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, this.getOffset());
      if (this.restrictions) {
         int minHeight = 0;
         int maxHeight = 0;
         int range = this.getOffset();

         for(int i1 = -range; i1 <= range; ++i1) {
            for(int k1 = -range; k1 <= range; ++k1) {
               int j1 = this.getTopBlock(world, i1, k1);
               Block block = this.getBlock(world, i1, j1 - 1, k1);
               if (block != Blocks.field_150349_c && block != Blocks.field_150346_d && block != Blocks.field_150354_m && block != Blocks.field_150348_b) {
                  return false;
               }

               if (j1 < minHeight) {
                  minHeight = j1;
               }

               if (j1 > maxHeight) {
                  maxHeight = j1;
               }

               if (maxHeight - minHeight > 5) {
                  return false;
               }
            }
         }
      }

      return true;
   }

   protected void layFoundation(World world, int i, int k) {
      for(int j = 0; (j == 0 || !this.isOpaque(world, i, j, k)) && this.getY(j) >= 0; --j) {
         this.setBlockAndMetadata(world, i, j, k, this.clayBlock, this.clayMeta);
         this.setGrassToDirt(world, i, j - 1, k);
      }

   }

   protected void dropFence(World world, int i, int j, int k) {
      while(true) {
         this.setBlockAndMetadata(world, i, j, k, this.fenceBlock, this.fenceMeta);
         if (this.isOpaque(world, i, j - 1, k)) {
            return;
         }

         --j;
      }
   }
}
