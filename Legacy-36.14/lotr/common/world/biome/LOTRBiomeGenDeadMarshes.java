package lotr.common.world.biome;

import java.util.Random;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRMod;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.feature.LOTRWorldGenMarshLights;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.world.spawning.LOTRInvasions;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenFlowers;
import net.minecraft.world.gen.feature.WorldGenMinable;

public class LOTRBiomeGenDeadMarshes extends LOTRBiome {
   public LOTRBiomeGenDeadMarshes(int i, boolean major) {
      super(i, major);
      this.field_76762_K.clear();
      this.field_76755_L.clear();
      this.spawnableLOTRAmbientList.clear();
      this.npcSpawnList.clear();
      this.decorator.addOre(new WorldGenMinable(LOTRMod.remains, 6, Blocks.field_150346_d), 5.0F, 55, 65);
      this.clearBiomeVariants();
      this.variantChance = 1.0F;
      this.addBiomeVariant(LOTRBiomeVariant.SWAMP_LOWLAND);
      this.decorator.sandPerChunk = 0;
      this.decorator.clayPerChunk = 0;
      this.decorator.quagmirePerChunk = 1;
      this.decorator.treesPerChunk = 0;
      this.decorator.logsPerChunk = 2;
      this.decorator.grassPerChunk = 8;
      this.decorator.doubleGrassPerChunk = 8;
      this.decorator.flowersPerChunk = 0;
      this.decorator.enableFern = true;
      this.decorator.enableSpecialGrasses = false;
      this.decorator.canePerChunk = 10;
      this.decorator.reedPerChunk = 2;
      this.decorator.dryReedChance = 1.0F;
      this.decorator.addTree(LOTRTreeType.OAK_DEAD, 1000);
      this.flowers.clear();
      this.addFlower(LOTRMod.deadPlant, 0, 10);
      this.biomeColors.setGrass(8348751);
      this.biomeColors.setSky(5657394);
      this.biomeColors.setClouds(10525542);
      this.biomeColors.setFog(4210724);
      this.biomeColors.setWater(1316367);
      this.setBanditChance(LOTREventSpawner.EventChance.NEVER);
      this.invasionSpawns.addInvasion(LOTRInvasions.MORDOR, LOTREventSpawner.EventChance.RARE);
   }

   public LOTRAchievement getBiomeAchievement() {
      return LOTRAchievement.enterDeadMarshes;
   }

   public LOTRWaypoint.Region getBiomeWaypoints() {
      return LOTRWaypoint.Region.NINDALF;
   }

   public LOTRMusicRegion.Sub getBiomeMusic() {
      return LOTRMusicRegion.DEAD_MARSHES.getSubregion("deadMarshes");
   }

   public void func_76728_a(World world, Random random, int i, int k) {
      super.func_76728_a(world, random, i, k);

      int l;
      int i1;
      int k1;
      int j1;
      for(l = 0; l < 6; ++l) {
         i1 = i + random.nextInt(16) + 8;
         k1 = k + random.nextInt(16) + 8;
         j1 = random.nextInt(128);
         (new WorldGenFlowers(LOTRMod.deadPlant)).func_76484_a(world, random, i1, j1, k1);
      }

      for(l = 0; l < 4; ++l) {
         i1 = i + random.nextInt(16) + 8;
         k1 = k + random.nextInt(16) + 8;

         for(j1 = 128; j1 > 0 && world.func_147437_c(i1, j1 - 1, k1); --j1) {
         }

         (new LOTRWorldGenMarshLights()).func_76484_a(world, random, i1, j1, k1);
      }

   }

   public float getTreeIncreaseChance() {
      return 0.25F;
   }
}
