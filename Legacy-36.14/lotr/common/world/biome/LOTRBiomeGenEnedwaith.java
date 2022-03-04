package lotr.common.world.biome;

import java.util.Random;
import lotr.common.LOTRAchievement;
import lotr.common.entity.animal.LOTREntityBear;
import lotr.common.entity.animal.LOTREntityHorse;
import lotr.common.entity.npc.LOTREntityScrapTrader;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.feature.LOTRWorldGenBoulder;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.world.spawning.LOTRBiomeSpawnList;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.world.spawning.LOTRInvasions;
import lotr.common.world.spawning.LOTRSpawnList;
import lotr.common.world.structure.LOTRWorldGenGondorRuin;
import lotr.common.world.structure2.LOTRWorldGenBurntHouse;
import lotr.common.world.structure2.LOTRWorldGenGondorObelisk;
import lotr.common.world.structure2.LOTRWorldGenRottenHouse;
import lotr.common.world.structure2.LOTRWorldGenRuinedHouse;
import lotr.common.world.structure2.LOTRWorldGenSmallStoneRuin;
import lotr.common.world.structure2.LOTRWorldGenStoneRuin;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase.SpawnListEntry;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRBiomeGenEnedwaith extends LOTRBiome {
   private WorldGenerator boulderGen;

   public LOTRBiomeGenEnedwaith(int i, boolean major) {
      super(i, major);
      this.boulderGen = new LOTRWorldGenBoulder(Blocks.field_150348_b, 0, 1, 4);
      this.field_76762_K.add(new SpawnListEntry(LOTREntityHorse.class, 5, 2, 6));
      this.field_76762_K.add(new SpawnListEntry(LOTREntityBear.class, 4, 1, 4));
      LOTRBiomeSpawnList.FactionContainer var10000 = this.npcSpawnList.newFactionList(100);
      LOTRBiomeSpawnList.SpawnListContainer[] var10001 = new LOTRBiomeSpawnList.SpawnListContainer[4];
      LOTRBiomeSpawnList var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.DUNLENDINGS, 1).setSpawnChance(10000);
      var10004 = this.npcSpawnList;
      var10001[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.DUNLENDING_WARRIORS, 1).setSpawnChance(10000);
      var10004 = this.npcSpawnList;
      var10001[2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.DUNLENDINGS, 10).setConquestOnly();
      var10004 = this.npcSpawnList;
      var10001[3] = LOTRBiomeSpawnList.entry(LOTRSpawnList.DUNLENDING_WARRIORS, 10).setConquestOnly();
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(0);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[1];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.RANGERS_NORTH, 10);
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(0);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[2];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GUNDABAD_ORCS, 10);
      var10004 = this.npcSpawnList;
      var10001[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GUNDABAD_WARGS, 3);
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(0);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[3];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ISENGARD_SNAGA, 5);
      var10004 = this.npcSpawnList;
      var10001[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.URUK_HAI, 10);
      var10004 = this.npcSpawnList;
      var10001[2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.URUK_WARGS, 3);
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(0);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[1];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ROHIRRIM_WARRIORS, 10);
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(0);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[1];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GONDOR_SOLDIERS, 10);
      var10000.add(var10001);
      this.addBiomeVariantSet(LOTRBiomeVariant.SET_NORMAL_OAK_SPRUCE);
      this.addBiomeVariant(LOTRBiomeVariant.SCRUBLAND, 3.0F);
      this.addBiomeVariant(LOTRBiomeVariant.HILLS_SCRUBLAND, 1.0F);
      this.addBiomeVariant(LOTRBiomeVariant.MOUNTAIN);
      this.addBiomeVariant(LOTRBiomeVariant.WASTELAND);
      this.addBiomeVariant(LOTRBiomeVariant.FOREST_BEECH, 0.1F);
      this.addBiomeVariant(LOTRBiomeVariant.FOREST_BIRCH, 0.1F);
      this.addBiomeVariant(LOTRBiomeVariant.FOREST_LARCH, 0.1F);
      this.addBiomeVariant(LOTRBiomeVariant.FOREST_PINE, 0.1F);
      this.addBiomeVariant(LOTRBiomeVariant.FOREST_ASPEN, 0.1F);
      this.decorator.treesPerChunk = 0;
      this.decorator.setTreeCluster(8, 30);
      this.decorator.willowPerChunk = 1;
      this.decorator.flowersPerChunk = 1;
      this.decorator.grassPerChunk = 8;
      this.decorator.doubleGrassPerChunk = 4;
      this.decorator.addTree(LOTRTreeType.OAK, 500);
      this.decorator.addTree(LOTRTreeType.OAK_TALL, 300);
      this.decorator.addTree(LOTRTreeType.OAK_LARGE, 200);
      this.decorator.addTree(LOTRTreeType.SPRUCE, 1000);
      this.decorator.addTree(LOTRTreeType.CHESTNUT, 1000);
      this.registerPlainsFlowers();
      this.decorator.addRandomStructure(new LOTRWorldGenRuinedHouse(false), 1500);
      this.decorator.addRandomStructure(new LOTRWorldGenBurntHouse(false), 3000);
      this.decorator.addRandomStructure(new LOTRWorldGenRottenHouse(false), 3000);
      this.decorator.addRandomStructure(new LOTRWorldGenGondorObelisk(false), 2000);
      this.decorator.addRandomStructure(new LOTRWorldGenGondorRuin(false), 2000);
      this.decorator.addRandomStructure(new LOTRWorldGenStoneRuin.STONE(1, 5), 500);
      this.decorator.addRandomStructure(new LOTRWorldGenSmallStoneRuin(false), 400);
      this.registerTravellingTrader(LOTREntityScrapTrader.class);
      this.setBanditChance(LOTREventSpawner.EventChance.BANDIT_COMMON);
      this.invasionSpawns.addInvasion(LOTRInvasions.RANGER_NORTH, LOTREventSpawner.EventChance.RARE);
      this.invasionSpawns.addInvasion(LOTRInvasions.GUNDABAD, LOTREventSpawner.EventChance.RARE);
      this.invasionSpawns.addInvasion(LOTRInvasions.GUNDABAD_WARG, LOTREventSpawner.EventChance.UNCOMMON);
      this.invasionSpawns.addInvasion(LOTRInvasions.DUNLAND, LOTREventSpawner.EventChance.UNCOMMON);
   }

   public LOTRAchievement getBiomeAchievement() {
      return LOTRAchievement.enterEnedwaith;
   }

   public LOTRWaypoint.Region getBiomeWaypoints() {
      return LOTRWaypoint.Region.ENEDWAITH;
   }

   public LOTRMusicRegion.Sub getBiomeMusic() {
      return LOTRMusicRegion.ENEDWAITH.getSubregion("enedwaith");
   }

   public void func_76728_a(World world, Random random, int i, int k) {
      super.func_76728_a(world, random, i, k);
      int boulders;
      int i1;
      int k1;
      int j1;
      if (random.nextInt(24) == 0) {
         boulders = 1 + random.nextInt(6);

         for(i1 = 0; i1 < boulders; ++i1) {
            k1 = i + random.nextInt(16) + 8;
            j1 = k + random.nextInt(16) + 8;
            this.boulderGen.func_76484_a(world, random, k1, world.func_72976_f(k1, j1), j1);
         }
      }

      for(boulders = 0; boulders < 2; ++boulders) {
         i1 = i + random.nextInt(16) + 8;
         k1 = k + random.nextInt(16) + 8;
         j1 = world.func_72976_f(i1, k1);
         if (j1 > 75) {
            this.decorator.genTree(world, random, i1, j1, k1);
         }
      }

   }

   public float getTreeIncreaseChance() {
      return 0.05F;
   }

   public float getChanceToSpawnAnimals() {
      return 0.1F;
   }

   public int spawnCountMultiplier() {
      return 3;
   }
}
