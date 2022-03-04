package lotr.common.world.biome;

import java.util.Random;
import lotr.common.LOTRAchievement;
import lotr.common.entity.animal.LOTREntityCamel;
import lotr.common.entity.animal.LOTREntityHorse;
import lotr.common.entity.npc.LOTREntityBanditHarad;
import lotr.common.entity.npc.LOTREntityDorwinionMerchantMan;
import lotr.common.entity.npc.LOTREntityNomadMerchant;
import lotr.common.entity.npc.LOTREntityScrapTrader;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.feature.LOTRWorldGenDoubleFlower;
import lotr.common.world.map.LOTRRoadType;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.world.spawning.LOTRBiomeSpawnList;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.world.spawning.LOTRSpawnList;
import lotr.common.world.structure.LOTRWorldGenHaradObelisk;
import lotr.common.world.structure2.LOTRWorldGenMoredainMercCamp;
import lotr.common.world.structure2.LOTRWorldGenStoneRuin;
import lotr.common.world.village.LOTRVillageGenSouthron;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase.SpawnListEntry;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRBiomeGenNearHaradFertile extends LOTRBiome {
   protected static NoiseGeneratorPerlin noiseDirt = new NoiseGeneratorPerlin(new Random(12960262626062L), 1);
   protected static NoiseGeneratorPerlin noiseSand = new NoiseGeneratorPerlin(new Random(17860128964L), 1);
   protected static NoiseGeneratorPerlin noiseRedSand = new NoiseGeneratorPerlin(new Random(358960629620L), 1);

   public LOTRBiomeGenNearHaradFertile(int i, boolean major) {
      super(i, major);
      this.field_76762_K.add(new SpawnListEntry(LOTREntityCamel.class, 6, 4, 4));
      this.field_76762_K.add(new SpawnListEntry(LOTREntityHorse.class, 15, 4, 4));
      LOTRBiomeSpawnList.FactionContainer var10000 = this.npcSpawnList.newFactionList(100, 0.0F);
      LOTRBiomeSpawnList.SpawnListContainer[] var10001 = new LOTRBiomeSpawnList.SpawnListContainer[2];
      LOTRBiomeSpawnList var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.COAST_SOUTHRONS, 20).setSpawnChance(100);
      var10004 = this.npcSpawnList;
      var10001[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.SOUTHRON_WARRIORS, 15).setSpawnChance(100);
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(0);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[3];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.SOUTHRON_WARRIORS, 10);
      var10004 = this.npcSpawnList;
      var10001[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.HARNEDOR_WARRIORS, 2);
      var10004 = this.npcSpawnList;
      var10001[2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.UMBAR_SOLDIERS, 2);
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(0);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[4];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.RANGERS_ITHILIEN, 10);
      var10004 = this.npcSpawnList;
      var10001[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GONDOR_SOLDIERS, 5);
      var10004 = this.npcSpawnList;
      var10001[2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GONDOR_SOLDIERS, 5).setConquestThreshold(50.0F);
      var10004 = this.npcSpawnList;
      var10001[3] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GONDOR_SOLDIERS, 5).setConquestThreshold(100.0F);
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(0);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[1];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ROHIRRIM_WARRIORS, 10);
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(0);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[2];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORDOR_ORCS, 10);
      var10004 = this.npcSpawnList;
      var10001[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.BLACK_URUKS, 3).setConquestThreshold(50.0F);
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(0);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[1];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORWAITH_WARRIORS, 10);
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(0);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[1];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.HALF_TROLLS, 10);
      var10000.add(var10001);
      this.addBiomeVariant(LOTRBiomeVariant.FLOWERS);
      this.addBiomeVariant(LOTRBiomeVariant.FOREST);
      this.addBiomeVariant(LOTRBiomeVariant.FOREST_LIGHT);
      this.addBiomeVariant(LOTRBiomeVariant.STEPPE);
      this.addBiomeVariant(LOTRBiomeVariant.STEPPE_BARREN);
      this.addBiomeVariant(LOTRBiomeVariant.HILLS);
      this.addBiomeVariant(LOTRBiomeVariant.HILLS_FOREST);
      this.addBiomeVariant(LOTRBiomeVariant.SHRUBLAND_OAK);
      this.addBiomeVariant(LOTRBiomeVariant.ORCHARD_ORANGE, 0.2F);
      this.addBiomeVariant(LOTRBiomeVariant.ORCHARD_LEMON, 0.2F);
      this.addBiomeVariant(LOTRBiomeVariant.ORCHARD_LIME, 0.2F);
      this.addBiomeVariant(LOTRBiomeVariant.ORCHARD_OLIVE, 0.2F);
      this.addBiomeVariant(LOTRBiomeVariant.ORCHARD_ALMOND, 0.2F);
      this.addBiomeVariant(LOTRBiomeVariant.ORCHARD_PLUM, 0.2F);
      this.addBiomeVariant(LOTRBiomeVariant.ORCHARD_DATE, 0.2F);
      this.addBiomeVariant(LOTRBiomeVariant.SCRUBLAND_SAND);
      this.addBiomeVariant(LOTRBiomeVariant.HILLS_SCRUBLAND_SAND);
      this.decorator.addOre(new WorldGenMinable(Blocks.field_150369_x, 6), 1.0F, 0, 48);
      this.decorator.grassPerChunk = 10;
      this.decorator.doubleGrassPerChunk = 2;
      this.decorator.flowersPerChunk = 3;
      this.decorator.doubleFlowersPerChunk = 1;
      this.decorator.deadBushPerChunk = 1;
      this.decorator.cactiPerChunk = 1;
      this.decorator.addTree(LOTRTreeType.CEDAR, 800);
      this.decorator.addTree(LOTRTreeType.OAK_DESERT, 500);
      this.decorator.addTree(LOTRTreeType.DATE_PALM, 50);
      this.decorator.addTree(LOTRTreeType.CYPRESS, 400);
      this.decorator.addTree(LOTRTreeType.CYPRESS_LARGE, 50);
      this.decorator.addTree(LOTRTreeType.PALM, 100);
      this.decorator.addTree(LOTRTreeType.LEMON, 5);
      this.decorator.addTree(LOTRTreeType.ORANGE, 5);
      this.decorator.addTree(LOTRTreeType.LIME, 5);
      this.decorator.addTree(LOTRTreeType.OLIVE, 5);
      this.decorator.addTree(LOTRTreeType.OLIVE_LARGE, 10);
      this.decorator.addTree(LOTRTreeType.ALMOND, 5);
      this.decorator.addTree(LOTRTreeType.PLUM, 5);
      this.registerHaradFlowers();
      this.biomeColors.setGrass(11914805);
      this.decorator.addRandomStructure(new LOTRWorldGenHaradObelisk(false), 3000);
      this.decorator.addRandomStructure(new LOTRWorldGenMoredainMercCamp(false), 1000);
      this.decorator.addRandomStructure(new LOTRWorldGenStoneRuin.NEAR_HARAD(1, 3), 300);
      this.decorator.addRandomStructure(new LOTRWorldGenStoneRuin.NUMENOR(1, 3), 4000);
      this.decorator.addVillage(new LOTRVillageGenSouthron(this, 0.6F));
      this.registerTravellingTrader(LOTREntityScrapTrader.class);
      this.registerTravellingTrader(LOTREntityDorwinionMerchantMan.class);
      this.registerTravellingTrader(LOTREntityNomadMerchant.class);
      this.setBanditChance(LOTREventSpawner.EventChance.BANDIT_RARE);
      this.setBanditEntityClass(LOTREntityBanditHarad.class);
   }

   public LOTRAchievement getBiomeAchievement() {
      return LOTRAchievement.enterSouthronCoasts;
   }

   public LOTRWaypoint.Region getBiomeWaypoints() {
      return LOTRWaypoint.Region.SOUTHRON_COASTS;
   }

   public LOTRMusicRegion.Sub getBiomeMusic() {
      return LOTRMusicRegion.NEAR_HARAD.getSubregion("fertile");
   }

   public LOTRRoadType getRoadBlock() {
      return LOTRRoadType.HARAD_PATH;
   }

   public void generateBiomeTerrain(World world, Random random, Block[] blocks, byte[] meta, int i, int k, double stoneNoise, int height, LOTRBiomeVariant variant) {
      Block topBlock_pre = this.field_76752_A;
      int topBlockMeta_pre = this.topBlockMeta;
      Block fillerBlock_pre = this.field_76753_B;
      int fillerBlockMeta_pre = this.fillerBlockMeta;
      if (this.hasMixedHaradSoils()) {
         double d1 = noiseDirt.func_151601_a((double)i * 0.002D, (double)k * 0.002D);
         double d2 = noiseDirt.func_151601_a((double)i * 0.07D, (double)k * 0.07D);
         double d3 = noiseDirt.func_151601_a((double)i * 0.25D, (double)k * 0.25D);
         double d4 = noiseSand.func_151601_a((double)i * 0.002D, (double)k * 0.002D);
         double d5 = noiseSand.func_151601_a((double)i * 0.07D, (double)k * 0.07D);
         double d6 = noiseSand.func_151601_a((double)i * 0.25D, (double)k * 0.25D);
         double d7 = noiseRedSand.func_151601_a((double)i * 0.002D, (double)k * 0.002D);
         double d8 = noiseRedSand.func_151601_a((double)i * 0.07D, (double)k * 0.07D);
         double d9 = noiseRedSand.func_151601_a((double)i * 0.25D, (double)k * 0.25D);
         if (d7 + d8 + d9 > 1.6D) {
            this.field_76752_A = Blocks.field_150354_m;
            this.topBlockMeta = 1;
            this.field_76753_B = this.field_76752_A;
            this.fillerBlockMeta = this.topBlockMeta;
         } else if (d4 + d5 + d6 > 0.9D) {
            this.field_76752_A = Blocks.field_150354_m;
            this.topBlockMeta = 0;
            this.field_76753_B = this.field_76752_A;
            this.fillerBlockMeta = this.topBlockMeta;
         } else if (d1 + d2 + d3 > 0.4D) {
            this.field_76752_A = Blocks.field_150346_d;
            this.topBlockMeta = 1;
         }
      }

      super.generateBiomeTerrain(world, random, blocks, meta, i, k, stoneNoise, height, variant);
      this.field_76752_A = topBlock_pre;
      this.topBlockMeta = topBlockMeta_pre;
      this.field_76753_B = fillerBlock_pre;
      this.fillerBlockMeta = fillerBlockMeta_pre;
   }

   protected boolean hasMixedHaradSoils() {
      return true;
   }

   public WorldGenerator getRandomWorldGenForDoubleFlower(Random random) {
      LOTRWorldGenDoubleFlower doubleFlowerGen = new LOTRWorldGenDoubleFlower();
      if (random.nextInt(5) == 0) {
         doubleFlowerGen.setFlowerType(3);
      } else {
         doubleFlowerGen.setFlowerType(2);
      }

      return doubleFlowerGen;
   }

   public float getTreeIncreaseChance() {
      return 0.3F;
   }

   public float getChanceToSpawnAnimals() {
      return 0.5F;
   }

   public int spawnCountMultiplier() {
      return 3;
   }
}
