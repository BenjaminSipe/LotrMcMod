package lotr.common.world.biome;

import java.util.Random;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.world.spawning.LOTREventSpawner;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRBiomeGenHaradMountains extends LOTRBiomeGenFarHarad {
   public LOTRBiomeGenHaradMountains(int i, boolean major) {
      super(i, major);
      this.field_76761_J.clear();
      this.addBiomeVariantSet(LOTRBiomeVariant.SET_MOUNTAINS);
      this.decorator.biomeGemFactor = 1.0F;
      this.setBanditChance(LOTREventSpawner.EventChance.NEVER);
   }

   public LOTRWaypoint.Region getBiomeWaypoints() {
      return LOTRWaypoint.Region.HARAD_MOUNTAINS;
   }

   public LOTRMusicRegion.Sub getBiomeMusic() {
      return LOTRMusicRegion.FAR_HARAD.getSubregion("mountains");
   }

   public boolean getEnableRiver() {
      return false;
   }

   protected void generateMountainTerrain(World world, Random random, Block[] blocks, byte[] meta, int i, int k, int xzIndex, int ySize, int height, int rockDepth, LOTRBiomeVariant variant) {
      int stoneHeight = 100 - rockDepth;

      for(int j = ySize - 1; j >= stoneHeight; --j) {
         int index = xzIndex * ySize + j;
         Block block = blocks[index];
         if (block == this.field_76752_A || block == this.field_76753_B) {
            blocks[index] = Blocks.field_150348_b;
            meta[index] = 0;
         }
      }

   }

   public float getChanceToSpawnAnimals() {
      return 0.0F;
   }
}
