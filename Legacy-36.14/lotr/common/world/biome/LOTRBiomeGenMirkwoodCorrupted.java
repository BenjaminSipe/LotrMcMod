package lotr.common.world.biome;

import java.util.Random;
import lotr.common.entity.animal.LOTREntityButterfly;
import lotr.common.entity.animal.LOTREntityGorcrow;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.feature.LOTRWorldGenWebOfUngoliant;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.world.spawning.LOTRInvasions;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase.SpawnListEntry;

public class LOTRBiomeGenMirkwoodCorrupted extends LOTRBiomeGenMirkwood {
   public LOTRBiomeGenMirkwoodCorrupted(int i, boolean major) {
      super(i, major);
      this.field_76755_L.clear();
      this.spawnableLOTRAmbientList.clear();
      this.spawnableLOTRAmbientList.add(new SpawnListEntry(LOTREntityButterfly.class, 10, 4, 4));
      this.spawnableLOTRAmbientList.add(new SpawnListEntry(LOTREntityGorcrow.class, 6, 4, 4));
      this.variantChance = 0.2F;
      this.addBiomeVariant(LOTRBiomeVariant.HILLS);
      this.decorator.treesPerChunk = 8;
      this.decorator.willowPerChunk = 1;
      this.decorator.vinesPerChunk = 20;
      this.decorator.logsPerChunk = 3;
      this.decorator.flowersPerChunk = 0;
      this.decorator.grassPerChunk = 12;
      this.decorator.doubleGrassPerChunk = 6;
      this.decorator.enableFern = true;
      this.decorator.mushroomsPerChunk = 4;
      this.decorator.generateCobwebs = false;
      this.decorator.addTree(LOTRTreeType.MIRK_OAK_LARGE, 1000);
      this.decorator.addTree(LOTRTreeType.OAK_LARGE, 300);
      this.decorator.addTree(LOTRTreeType.SPRUCE, 200);
      this.decorator.addTree(LOTRTreeType.FIR, 200);
      this.decorator.addTree(LOTRTreeType.PINE, 400);
      this.biomeColors.setGrass(2841381);
      this.biomeColors.setFoliage(2503461);
      this.biomeColors.setFog(3302525);
      this.biomeColors.setFoggy(true);
      this.biomeColors.setWater(1708838);
      this.setBanditChance(LOTREventSpawner.EventChance.NEVER);
      this.invasionSpawns.addInvasion(LOTRInvasions.WOOD_ELF, LOTREventSpawner.EventChance.UNCOMMON);
   }

   public LOTRMusicRegion.Sub getBiomeMusic() {
      return LOTRMusicRegion.MIRKWOOD.getSubregion("mirkwood");
   }

   public void func_76728_a(World world, Random random, int i, int k) {
      super.func_76728_a(world, random, i, k);
      int l;
      int i1;
      int j1;
      int k1;
      if (this.decorator.treesPerChunk > 2) {
         for(l = 0; l < this.decorator.treesPerChunk / 2; ++l) {
            i1 = i + random.nextInt(16) + 8;
            j1 = k + random.nextInt(16) + 8;
            k1 = world.func_72825_h(i1, j1);
            LOTRTreeType.MIRK_OAK.create(false, random).func_76484_a(world, random, i1, k1, j1);
         }
      }

      for(l = 0; l < 6; ++l) {
         i1 = i + random.nextInt(16) + 8;
         j1 = random.nextInt(128);
         k1 = k + random.nextInt(16) + 8;
         (new LOTRWorldGenWebOfUngoliant(false, 64)).func_76484_a(world, random, i1, j1, k1);
      }

   }

   public float getChanceToSpawnAnimals() {
      return 0.1F;
   }
}
