package lotr.common.world.biome;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRMod;
import lotr.common.entity.animal.LOTREntityGorcrow;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.spawning.LOTRBiomeSpawnList;
import lotr.common.world.spawning.LOTRSpawnList;
import lotr.common.world.structure2.LOTRWorldGenDolGuldurAltar;
import lotr.common.world.structure2.LOTRWorldGenDolGuldurCamp;
import lotr.common.world.structure2.LOTRWorldGenDolGuldurSpiderPit;
import lotr.common.world.structure2.LOTRWorldGenDolGuldurTower;
import lotr.common.world.structure2.LOTRWorldGenSmallStoneRuin;
import lotr.common.world.structure2.LOTRWorldGenStoneRuin;
import net.minecraft.world.biome.BiomeGenBase.SpawnListEntry;
import net.minecraft.world.gen.feature.WorldGenMinable;

public class LOTRBiomeGenDolGuldur extends LOTRBiomeGenMirkwoodCorrupted {
   public LOTRBiomeGenDolGuldur(int i, boolean major) {
      super(i, major);
      this.field_76762_K.clear();
      this.field_76755_L.clear();
      this.spawnableLOTRAmbientList.add(new SpawnListEntry(LOTREntityGorcrow.class, 8, 4, 4));
      this.npcSpawnList.clear();
      LOTRBiomeSpawnList.FactionContainer var10000 = this.npcSpawnList.newFactionList(100);
      LOTRBiomeSpawnList.SpawnListContainer[] var10001 = new LOTRBiomeSpawnList.SpawnListContainer[3];
      LOTRBiomeSpawnList var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MIRKWOOD_SPIDERS, 20);
      var10004 = this.npcSpawnList;
      var10001[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.DOL_GULDUR_ORCS, 30);
      var10004 = this.npcSpawnList;
      var10001[2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MIRK_TROLLS, 5);
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
      var10001[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GALADHRIM_WARDENS, 3);
      var10000.add(var10001);
      this.npcSpawnList.conquestGainRate = 0.2F;
      this.decorator.addOre(new WorldGenMinable(LOTRMod.oreMorgulIron, 8), 20.0F, 0, 64);
      this.decorator.addOre(new WorldGenMinable(LOTRMod.oreGulduril, 8), 8.0F, 0, 32);
      this.decorator.treesPerChunk = 1;
      this.decorator.vinesPerChunk = 2;
      this.decorator.flowersPerChunk = 0;
      this.decorator.grassPerChunk = 6;
      this.decorator.doubleGrassPerChunk = 1;
      this.decorator.clearTrees();
      this.decorator.addTree(LOTRTreeType.MIRK_OAK, 200);
      this.decorator.addTree(LOTRTreeType.MIRK_OAK_DEAD, 1000);
      this.biomeColors.setGrass(3032113);
      this.biomeColors.setFoliage(3032113);
      this.biomeColors.setSky(4343633);
      this.biomeColors.setClouds(2632757);
      this.biomeColors.setFoggy(true);
      this.decorator.clearRandomStructures();
      this.decorator.addRandomStructure(new LOTRWorldGenStoneRuin.DOL_GULDUR(1, 4), 5);
      this.decorator.addRandomStructure(new LOTRWorldGenDolGuldurAltar(false), 160);
      this.decorator.addRandomStructure(new LOTRWorldGenDolGuldurTower(false), 80);
      this.decorator.addRandomStructure(new LOTRWorldGenDolGuldurCamp(false), 50);
      this.decorator.addRandomStructure(new LOTRWorldGenDolGuldurSpiderPit(false), 50);
      this.decorator.addRandomStructure(new LOTRWorldGenSmallStoneRuin(false), 400);
   }

   public LOTRAchievement getBiomeAchievement() {
      return LOTRAchievement.enterDolGuldur;
   }

   public LOTRMusicRegion.Sub getBiomeMusic() {
      return LOTRMusicRegion.MIRKWOOD.getSubregion("dolGuldur");
   }

   public float getTreeIncreaseChance() {
      return 0.25F;
   }

   public boolean canSpawnHostilesInDay() {
      return true;
   }
}
