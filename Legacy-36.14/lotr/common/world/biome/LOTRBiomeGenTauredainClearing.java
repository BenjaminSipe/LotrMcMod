package lotr.common.world.biome;

import lotr.common.entity.animal.LOTREntityBird;
import lotr.common.entity.animal.LOTREntityButterfly;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.map.LOTRRoadType;
import lotr.common.world.spawning.LOTRBiomeSpawnList;
import lotr.common.world.spawning.LOTRSpawnList;
import lotr.common.world.village.LOTRVillageGenTauredain;
import net.minecraft.world.biome.BiomeGenBase.SpawnListEntry;

public class LOTRBiomeGenTauredainClearing extends LOTRBiomeGenFarHaradJungle {
   public LOTRBiomeGenTauredainClearing(int i, boolean major) {
      super(i, major);
      this.obsidianGravelRarity = 500;
      this.field_76761_J.clear();
      this.spawnableLOTRAmbientList.clear();
      this.spawnableLOTRAmbientList.add(new SpawnListEntry(LOTREntityBird.class, 10, 4, 4));
      this.spawnableLOTRAmbientList.add(new SpawnListEntry(LOTREntityButterfly.class, 15, 4, 4));
      LOTRBiomeSpawnList.FactionContainer var10000 = this.npcSpawnList.newFactionList(100);
      LOTRBiomeSpawnList.SpawnListContainer[] var10001 = new LOTRBiomeSpawnList.SpawnListContainer[3];
      LOTRBiomeSpawnList var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.TAURETHRIM, 10);
      var10004 = this.npcSpawnList;
      var10001[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.TAURETHRIM_WARRIORS, 4);
      var10004 = this.npcSpawnList;
      var10001[2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.TAURETHRIM_WARRIORS, 10).setConquestOnly();
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(0);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[2];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORDOR_ORCS, 10);
      var10004 = this.npcSpawnList;
      var10001[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.BLACK_URUKS, 2).setConquestThreshold(50.0F);
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(0);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[2];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.SOUTHRON_WARRIORS, 10);
      var10004 = this.npcSpawnList;
      var10001[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GULF_WARRIORS, 10);
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(0);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[2];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORWAITH_WARRIORS, 10);
      var10004 = this.npcSpawnList;
      var10001[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORWAITH, 5);
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(0);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[1];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.HALF_TROLLS, 10);
      var10000.add(var10001);
      this.npcSpawnList.conquestGainRate = 0.5F;
      this.clearBiomeVariants();
      this.variantChance = 0.1F;
      this.addBiomeVariant(LOTRBiomeVariant.FLOWERS);
      this.decorator.setTreeCluster(16, 40);
      this.decorator.treesPerChunk = 0;
      this.decorator.vinesPerChunk = 0;
      this.decorator.grassPerChunk = 10;
      this.decorator.doubleGrassPerChunk = 6;
      this.decorator.addVillage(new LOTRVillageGenTauredain(this, 0.6F));
      this.biomeColors.setSky(11590117);
      this.biomeColors.setFog(12705243);
      this.invasionSpawns.clearInvasions();
   }

   public LOTRRoadType getRoadBlock() {
      return LOTRRoadType.TAUREDAIN;
   }

   public boolean hasJungleLakes() {
      return false;
   }

   public float getTreeIncreaseChance() {
      return 0.1F;
   }

   public float getChanceToSpawnAnimals() {
      return 0.25F;
   }

   public int spawnCountMultiplier() {
      return 3;
   }
}
