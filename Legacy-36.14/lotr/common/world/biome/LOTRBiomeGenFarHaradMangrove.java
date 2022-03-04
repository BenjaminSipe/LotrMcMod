package lotr.common.world.biome;

import java.util.Random;
import lotr.common.LOTRMod;
import lotr.common.world.feature.LOTRTreeType;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenMinable;

public class LOTRBiomeGenFarHaradMangrove extends LOTRBiomeGenFarHarad {
   public LOTRBiomeGenFarHaradMangrove(int i, boolean major) {
      super(i, major);
      this.field_76752_A = Blocks.field_150354_m;
      this.field_76755_L.clear();
      this.spawnableLOTRAmbientList.clear();
      this.decorator.addSoil(new WorldGenMinable(Blocks.field_150346_d, 1, 60, Blocks.field_150354_m), 12.0F, 60, 90);
      this.decorator.quagmirePerChunk = 1;
      this.decorator.treesPerChunk = 5;
      this.decorator.vinesPerChunk = 20;
      this.decorator.grassPerChunk = 8;
      this.decorator.enableFern = true;
      this.decorator.waterlilyPerChunk = 3;
      this.decorator.addTree(LOTRTreeType.MANGROVE, 1000);
      this.decorator.addTree(LOTRTreeType.ACACIA, 10);
      this.decorator.addTree(LOTRTreeType.OAK_DESERT, 5);
      this.registerSwampFlowers();
      this.biomeColors.setGrass(10466679);
      this.biomeColors.setFoliage(6715206);
      this.biomeColors.setWater(5985085);
   }

   public LOTRMusicRegion.Sub getBiomeMusic() {
      return LOTRMusicRegion.FAR_HARAD.getSubregion("mangrove");
   }

   public boolean getEnableRiver() {
      return true;
   }

   public void func_76728_a(World world, Random random, int i, int k) {
      super.func_76728_a(world, random, i, k);

      int l;
      int i1;
      int j1;
      int k1;
      for(l = 0; l < 2; ++l) {
         i1 = i + random.nextInt(16);
         j1 = k + random.nextInt(16);
         k1 = world.func_72825_h(i1, j1);
         if (world.func_147439_a(i1, k1 - 1, j1).func_149662_c() && world.func_147439_a(i1, k1, j1).func_149688_o() == Material.field_151586_h) {
            this.decorator.genTree(world, random, i1, k1, j1);
         }
      }

      for(l = 0; l < 20; ++l) {
         i1 = i + random.nextInt(16);
         j1 = 50 + random.nextInt(15);
         k1 = k + random.nextInt(16);
         if (world.func_147439_a(i1, j1, k1).func_149662_c() && world.func_147439_a(i1, j1 + 1, k1).func_149688_o() == Material.field_151586_h) {
            int height = 2 + random.nextInt(3);

            for(int j2 = j1; j2 <= j1 + height; ++j2) {
               world.func_147465_d(i1, j2, k1, LOTRMod.wood3, 3, 2);
            }
         }
      }

   }

   public float getTreeIncreaseChance() {
      return 0.15F;
   }

   public float getChanceToSpawnAnimals() {
      return 0.4F;
   }
}
