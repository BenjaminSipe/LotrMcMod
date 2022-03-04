package lotr.common.world.biome;

import lotr.common.entity.animal.LOTREntitySeagull;
import net.minecraft.block.Block;
import net.minecraft.world.biome.BiomeGenBase.SpawnListEntry;

public class LOTRBiomeGenBeach extends LOTRBiomeGenOcean {
   public LOTRBiomeGenBeach(int i, boolean major) {
      super(i, major);
      this.setMinMaxHeight(0.1F, 0.0F);
      this.setTemperatureRainfall(0.8F, 0.4F);
      this.field_76762_K.clear();
      this.field_76755_L.clear();
      this.spawnableLOTRAmbientList.clear();
      this.spawnableLOTRAmbientList.add(new SpawnListEntry(LOTREntitySeagull.class, 20, 4, 4));
   }

   public LOTRBiomeGenBeach setBeachBlock(Block block, int meta) {
      this.field_76752_A = block;
      this.topBlockMeta = meta;
      this.field_76753_B = block;
      this.fillerBlockMeta = meta;
      return this;
   }
}
