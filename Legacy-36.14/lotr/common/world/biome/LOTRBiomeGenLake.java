package lotr.common.world.biome;

import net.minecraft.block.Block;

public class LOTRBiomeGenLake extends LOTRBiome {
   public LOTRBiomeGenLake(int i, boolean major) {
      super(i, major);
      this.setMinMaxHeight(-0.5F, 0.2F);
      this.field_76762_K.clear();
      this.spawnableLOTRAmbientList.clear();
      this.npcSpawnList.clear();
      this.decorator.sandPerChunk = 0;
   }

   public LOTRBiomeGenLake setLakeBlock(Block block) {
      this.field_76752_A = block;
      this.field_76753_B = block;
      return this;
   }

   public LOTRMusicRegion.Sub getBiomeMusic() {
      return LOTRMusicRegion.SEA.getSubregion("lake");
   }

   public boolean getEnableRiver() {
      return false;
   }
}
