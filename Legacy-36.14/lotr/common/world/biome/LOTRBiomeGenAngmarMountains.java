package lotr.common.world.biome;

import java.util.Random;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.spawning.LOTRBiomeSpawnList;
import lotr.common.world.spawning.LOTRSpawnList;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRBiomeGenAngmarMountains extends LOTRBiomeGenAngmar {
   public LOTRBiomeGenAngmarMountains(int i, boolean major) {
      super(i, major);
      this.field_76762_K.clear();
      LOTRBiomeSpawnList.FactionContainer var10000 = this.npcSpawnList.newFactionList(50);
      LOTRBiomeSpawnList.SpawnListContainer[] var10001 = new LOTRBiomeSpawnList.SpawnListContainer[1];
      LOTRBiomeSpawnList var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.SNOW_TROLLS, 10);
      var10000.add(var10001);
      this.clearBiomeVariants();
      this.addBiomeVariantSet(LOTRBiomeVariant.SET_MOUNTAINS);
      this.decorator.biomeGemFactor = 0.75F;
   }

   public boolean getEnableRiver() {
      return false;
   }

   protected void generateMountainTerrain(World world, Random random, Block[] blocks, byte[] meta, int i, int k, int xzIndex, int ySize, int height, int rockDepth, LOTRBiomeVariant variant) {
      int snowHeight = 130 - rockDepth;
      int stoneHeight = snowHeight - 20;

      for(int j = ySize - 1; j >= stoneHeight; --j) {
         int index = xzIndex * ySize + j;
         Block block = blocks[index];
         if (j >= snowHeight && block == this.field_76752_A) {
            blocks[index] = Blocks.field_150433_aE;
            meta[index] = 0;
         } else if (block == this.field_76752_A || block == this.field_76753_B) {
            blocks[index] = Blocks.field_150348_b;
            meta[index] = 0;
         }
      }

   }
}
