package lotr.common.world.mapgen;

import lotr.common.world.LOTRUtumnoLevel;
import lotr.common.world.biome.LOTRBiome;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

public class LOTRMapGenCavesUtumno extends LOTRMapGenCaves {
   protected int caveRarity() {
      return 3;
   }

   protected int getCaveGenerationHeight() {
      return this.field_75038_b.nextInt(this.field_75038_b.nextInt(240) + 8);
   }

   protected void digBlock(Block[] blockArray, int index, int xzIndex, int i, int j, int k, int chunkX, int chunkZ, LOTRBiome biome, boolean foundTop) {
      if (j >= LOTRUtumnoLevel.forY(0).getLowestCorridorFloor() && j <= LOTRUtumnoLevel.forY(255).getHighestCorridorRoof()) {
         for(int l = 0; l < LOTRUtumnoLevel.values().length - 1; ++l) {
            LOTRUtumnoLevel levelUpper = LOTRUtumnoLevel.values()[l];
            LOTRUtumnoLevel levelLower = LOTRUtumnoLevel.values()[l + 1];
            if (j > levelLower.getHighestCorridorRoof() && j < levelUpper.getLowestCorridorFloor()) {
               return;
            }
         }

         blockArray[index] = Blocks.field_150350_a;
      }
   }
}
