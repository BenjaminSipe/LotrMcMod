package lotr.common.world.biome;

import lotr.common.LOTRAchievement;
import lotr.common.entity.animal.LOTREntityButterfly;
import lotr.common.entity.animal.LOTREntityDeer;
import lotr.common.entity.animal.LOTREntityElk;
import lotr.common.world.map.LOTRRoadType;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.world.spawning.LOTRBiomeSpawnList;
import lotr.common.world.spawning.LOTRSpawnList;
import lotr.common.world.structure.LOTRWorldGenRuinedWoodElfTower;
import net.minecraft.world.biome.BiomeGenBase.SpawnListEntry;

public abstract class LOTRBiomeGenMirkwood extends LOTRBiome {
   public LOTRBiomeGenMirkwood(int i, boolean major) {
      super(i, major);
      this.field_76762_K.add(new SpawnListEntry(LOTREntityElk.class, 30, 4, 6));
      this.field_76762_K.add(new SpawnListEntry(LOTREntityDeer.class, 30, 4, 6));
      this.spawnableLOTRAmbientList.add(new SpawnListEntry(LOTREntityButterfly.class, 10, 4, 4));
      LOTRBiomeSpawnList.FactionContainer var10000 = this.npcSpawnList.newFactionList(100);
      LOTRBiomeSpawnList.SpawnListContainer[] var10001 = new LOTRBiomeSpawnList.SpawnListContainer[3];
      LOTRBiomeSpawnList var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MIRKWOOD_SPIDERS, 10);
      var10004 = this.npcSpawnList;
      var10001[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.DOL_GULDUR_ORCS, 10).setConquestOnly();
      var10004 = this.npcSpawnList;
      var10001[2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MIRK_TROLLS, 2).setConquestThreshold(100.0F);
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(0);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[3];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GUNDABAD_ORCS, 10);
      var10004 = this.npcSpawnList;
      var10001[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GUNDABAD_WARGS, 2);
      var10004 = this.npcSpawnList;
      var10001[2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GUNDABAD_URUKS, 1).setConquestThreshold(100.0F);
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(0);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[4];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.WOOD_ELF_WARRIORS, 10);
      var10004 = this.npcSpawnList;
      var10001[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.WOOD_ELVES, 5).setConquestThreshold(50.0F);
      var10004 = this.npcSpawnList;
      var10001[2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.WOOD_ELVES, 5).setConquestThreshold(200.0F);
      var10004 = this.npcSpawnList;
      var10001[3] = LOTRBiomeSpawnList.entry(LOTRSpawnList.WOOD_ELVES, 5).setConquestThreshold(400.0F);
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(0);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[2];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GALADHRIM_WARRIORS, 10);
      var10004 = this.npcSpawnList;
      var10001[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GALADHRIM_WARDENS, 5);
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(0);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[2];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.HUORNS, 20);
      var10004 = this.npcSpawnList;
      var10001[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ENTS, 10).setConquestThreshold(50.0F);
      var10000.add(var10001);
      this.registerForestFlowers();
      this.decorator.addRandomStructure(new LOTRWorldGenRuinedWoodElfTower(false), 500);
   }

   public LOTRAchievement getBiomeAchievement() {
      return LOTRAchievement.enterMirkwood;
   }

   public LOTRWaypoint.Region getBiomeWaypoints() {
      return LOTRWaypoint.Region.MIRKWOOD;
   }

   public LOTRRoadType getRoadBlock() {
      return LOTRRoadType.WOOD_ELVEN_RUINED.setRepair(0.6F);
   }

   public LOTRRoadType.BridgeType getBridgeBlock() {
      return LOTRRoadType.BridgeType.MIRKWOOD;
   }
}
