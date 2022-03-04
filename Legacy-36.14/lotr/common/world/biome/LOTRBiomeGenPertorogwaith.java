package lotr.common.world.biome;

import java.util.Random;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRMod;
import lotr.common.entity.animal.LOTREntityGemsbok;
import lotr.common.entity.animal.LOTREntityRhino;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.feature.LOTRWorldGenBoulder;
import lotr.common.world.feature.LOTRWorldGenSkullPile;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.world.spawning.LOTRBiomeSpawnList;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.world.spawning.LOTRSpawnList;
import lotr.common.world.structure2.LOTRWorldGenHalfTrollHouse;
import lotr.common.world.structure2.LOTRWorldGenHalfTrollWarlordHouse;
import lotr.common.world.structure2.LOTRWorldGenStoneRuin;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase.SpawnListEntry;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRBiomeGenPertorogwaith extends LOTRBiome {
   private WorldGenerator boulderGen;
   private WorldGenerator clayBoulderGen;
   private WorldGenerator deadMoundGen;

   public LOTRBiomeGenPertorogwaith(int i, boolean major) {
      super(i, major);
      this.boulderGen = new LOTRWorldGenBoulder(Blocks.field_150348_b, 0, 1, 3);
      this.clayBoulderGen = new LOTRWorldGenBoulder(Blocks.field_150405_ch, 0, 1, 3);
      this.deadMoundGen = new LOTRWorldGenBoulder(LOTRMod.wasteBlock, 0, 1, 3);
      this.field_76762_K.clear();
      this.field_76762_K.add(new SpawnListEntry(LOTREntityRhino.class, 8, 4, 4));
      this.field_76762_K.add(new SpawnListEntry(LOTREntityGemsbok.class, 4, 4, 4));
      this.spawnableLOTRAmbientList.clear();
      LOTRBiomeSpawnList.FactionContainer var10000 = this.npcSpawnList.newFactionList(100);
      LOTRBiomeSpawnList.SpawnListContainer[] var10001 = new LOTRBiomeSpawnList.SpawnListContainer[1];
      LOTRBiomeSpawnList var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.HALF_TROLLS, 10);
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(0);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[3];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORDOR_ORCS, 10);
      var10004 = this.npcSpawnList;
      var10001[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.BLACK_URUKS, 2).setConquestThreshold(50.0F);
      var10004 = this.npcSpawnList;
      var10001[2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.OLOG_HAI, 1).setConquestThreshold(200.0F);
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(0);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[1];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GULF_WARRIORS, 10);
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(0);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[2];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORWAITH_WARRIORS, 10);
      var10004 = this.npcSpawnList;
      var10001[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORWAITH, 3);
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(0);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[1];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.TAURETHRIM_WARRIORS, 10);
      var10000.add(var10001);
      this.variantChance = 0.6F;
      this.addBiomeVariant(LOTRBiomeVariant.FOREST);
      this.addBiomeVariant(LOTRBiomeVariant.FOREST_LIGHT);
      this.addBiomeVariant(LOTRBiomeVariant.STEPPE);
      this.addBiomeVariant(LOTRBiomeVariant.STEPPE_BARREN);
      this.addBiomeVariant(LOTRBiomeVariant.HILLS);
      this.addBiomeVariant(LOTRBiomeVariant.HILLS_FOREST);
      this.addBiomeVariant(LOTRBiomeVariant.DEADFOREST_OAK);
      this.addBiomeVariant(LOTRBiomeVariant.SHRUBLAND_OAK);
      this.addBiomeVariant(LOTRBiomeVariant.SCRUBLAND, 3.0F);
      this.addBiomeVariant(LOTRBiomeVariant.HILLS_SCRUBLAND, 2.0F);
      this.addBiomeVariant(LOTRBiomeVariant.WASTELAND, 4.0F);
      this.decorator.grassPerChunk = 10;
      this.decorator.doubleGrassPerChunk = 4;
      this.decorator.flowersPerChunk = 0;
      this.decorator.canePerChunk = 10;
      this.decorator.addTree(LOTRTreeType.OAK_DESERT, 50);
      this.decorator.addTree(LOTRTreeType.OAK_DEAD, 100);
      this.decorator.addTree(LOTRTreeType.ACACIA, 100);
      this.decorator.addTree(LOTRTreeType.ACACIA_DEAD, 200);
      this.decorator.addTree(LOTRTreeType.BAOBAB, 10);
      this.registerHaradFlowers();
      this.decorator.addRandomStructure(new LOTRWorldGenHalfTrollHouse(false), 40);
      this.decorator.addRandomStructure(new LOTRWorldGenHalfTrollWarlordHouse(false), 200);
      this.decorator.addRandomStructure(new LOTRWorldGenStoneRuin.MORDOR(1, 3), 100);
      this.biomeColors.setSky(8551538);
      this.biomeColors.setClouds(7500401);
      this.biomeColors.setFog(7500401);
      this.biomeColors.setWater(9080439);
      this.setBanditChance(LOTREventSpawner.EventChance.NEVER);
   }

   public LOTRAchievement getBiomeAchievement() {
      return LOTRAchievement.enterPertorogwaith;
   }

   public LOTRWaypoint.Region getBiomeWaypoints() {
      return LOTRWaypoint.Region.PERTOROGWAITH;
   }

   public LOTRMusicRegion.Sub getBiomeMusic() {
      return LOTRMusicRegion.PERDOROGWAITH.getSubregion("pertorogwaith");
   }

   public void func_76728_a(World world, Random random, int i, int k) {
      super.func_76728_a(world, random, i, k);
      int l;
      int i1;
      int k1;
      int j1;
      if (random.nextInt(6) == 0) {
         l = 1 + random.nextInt(4);

         for(i1 = 0; i1 < l; ++i1) {
            k1 = i + random.nextInt(16) + 8;
            j1 = k + random.nextInt(16) + 8;
            this.boulderGen.func_76484_a(world, random, k1, world.func_72976_f(k1, j1), j1);
         }
      }

      if (random.nextInt(12) == 0) {
         l = 1 + random.nextInt(4);

         for(i1 = 0; i1 < l; ++i1) {
            k1 = i + random.nextInt(16) + 8;
            j1 = k + random.nextInt(16) + 8;
            this.clayBoulderGen.func_76484_a(world, random, k1, world.func_72976_f(k1, j1), j1);
         }
      }

      if (random.nextInt(40) == 0) {
         for(l = 0; l < 3; ++l) {
            i1 = i + random.nextInt(16) + 8;
            k1 = k + random.nextInt(16) + 8;
            j1 = world.func_72976_f(i1, k1);
            this.deadMoundGen.func_76484_a(world, random, i1, j1, k1);
            (new LOTRWorldGenSkullPile()).func_76484_a(world, random, i1, j1, k1);
         }
      }

   }

   public float getTreeIncreaseChance() {
      return 0.25F;
   }

   public float getChanceToSpawnAnimals() {
      return 0.05F;
   }

   public boolean canSpawnHostilesInDay() {
      return true;
   }

   public int spawnCountMultiplier() {
      return 2;
   }
}
