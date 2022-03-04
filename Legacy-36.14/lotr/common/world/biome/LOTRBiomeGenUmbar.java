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
import lotr.common.world.structure2.LOTRWorldGenCorsairCamp;
import lotr.common.world.structure2.LOTRWorldGenMoredainMercCamp;
import lotr.common.world.structure2.LOTRWorldGenStoneRuin;
import lotr.common.world.village.LOTRVillageGenUmbar;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase.SpawnListEntry;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRBiomeGenUmbar extends LOTRBiome {
   protected static NoiseGeneratorPerlin noiseDirt = new NoiseGeneratorPerlin(new Random(7849067306796L), 1);
   protected static NoiseGeneratorPerlin noiseSand = new NoiseGeneratorPerlin(new Random(628602597026L), 1);

   public LOTRBiomeGenUmbar(int i, boolean major) {
      super(i, major);
      this.field_76762_K.add(new SpawnListEntry(LOTREntityCamel.class, 4, 4, 4));
      this.field_76762_K.add(new SpawnListEntry(LOTREntityHorse.class, 5, 4, 4));
      LOTRBiomeSpawnList.FactionContainer var10000 = this.npcSpawnList.newFactionList(100, 0.0F);
      LOTRBiomeSpawnList.SpawnListContainer[] var10001 = new LOTRBiomeSpawnList.SpawnListContainer[4];
      LOTRBiomeSpawnList var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.UMBARIANS, 120).setSpawnChance(100);
      var10004 = this.npcSpawnList;
      var10001[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.UMBAR_SOLDIERS, 40).setSpawnChance(100);
      var10004 = this.npcSpawnList;
      var10001[2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.CORSAIRS, 40).setSpawnChance(100);
      var10004 = this.npcSpawnList;
      var10001[3] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GONDOR_RENEGADES, 1).setSpawnChance(100);
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(0);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[4];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.UMBAR_SOLDIERS, 50);
      var10004 = this.npcSpawnList;
      var10001[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.SOUTHRON_WARRIORS, 10);
      var10004 = this.npcSpawnList;
      var10001[2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GULF_WARRIORS, 10);
      var10004 = this.npcSpawnList;
      var10001[3] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GONDOR_RENEGADES, 1);
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(0);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[3];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GONDOR_SOLDIERS, 10);
      var10004 = this.npcSpawnList;
      var10001[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.PELARGIR_SOLDIERS, 5).setConquestThreshold(100.0F);
      var10004 = this.npcSpawnList;
      var10001[2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.DOL_AMROTH_SOLDIERS, 5).setConquestThreshold(100.0F);
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(0);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[1];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ROHIRRIM_WARRIORS, 10);
      var10000.add(var10001);
      this.npcSpawnList.conquestGainRate = 0.2F;
      this.variantChance = 0.3F;
      this.addBiomeVariant(LOTRBiomeVariant.FLOWERS);
      this.addBiomeVariant(LOTRBiomeVariant.FOREST);
      this.addBiomeVariant(LOTRBiomeVariant.FOREST_LIGHT);
      this.addBiomeVariant(LOTRBiomeVariant.HILLS);
      this.addBiomeVariant(LOTRBiomeVariant.HILLS_FOREST);
      this.addBiomeVariant(LOTRBiomeVariant.SHRUBLAND_OAK);
      this.addBiomeVariant(LOTRBiomeVariant.ORCHARD_ORANGE, 0.1F);
      this.addBiomeVariant(LOTRBiomeVariant.ORCHARD_LEMON, 0.1F);
      this.addBiomeVariant(LOTRBiomeVariant.ORCHARD_LIME, 0.1F);
      this.addBiomeVariant(LOTRBiomeVariant.ORCHARD_OLIVE, 0.1F);
      this.addBiomeVariant(LOTRBiomeVariant.ORCHARD_ALMOND, 0.1F);
      this.addBiomeVariant(LOTRBiomeVariant.ORCHARD_PLUM, 0.1F);
      this.decorator.addOre(new WorldGenMinable(Blocks.field_150369_x, 6), 1.0F, 0, 48);
      this.decorator.grassPerChunk = 6;
      this.decorator.doubleGrassPerChunk = 1;
      this.decorator.flowersPerChunk = 3;
      this.decorator.doubleFlowersPerChunk = 1;
      this.decorator.addTree(LOTRTreeType.OAK_DESERT, 1000);
      this.decorator.addTree(LOTRTreeType.CEDAR, 300);
      this.decorator.addTree(LOTRTreeType.CYPRESS, 500);
      this.decorator.addTree(LOTRTreeType.CYPRESS_LARGE, 50);
      this.decorator.addTree(LOTRTreeType.PALM, 100);
      this.decorator.addTree(LOTRTreeType.DATE_PALM, 5);
      this.decorator.addTree(LOTRTreeType.LEMON, 2);
      this.decorator.addTree(LOTRTreeType.ORANGE, 2);
      this.decorator.addTree(LOTRTreeType.LIME, 2);
      this.decorator.addTree(LOTRTreeType.OLIVE, 5);
      this.decorator.addTree(LOTRTreeType.OLIVE_LARGE, 5);
      this.decorator.addTree(LOTRTreeType.PLUM, 2);
      this.registerHaradFlowers();
      this.biomeColors.setGrass(11914805);
      this.decorator.addRandomStructure(new LOTRWorldGenMoredainMercCamp(false), 1500);
      this.decorator.addRandomStructure(new LOTRWorldGenCorsairCamp(false), 800);
      this.decorator.addRandomStructure(new LOTRWorldGenStoneRuin.UMBAR(1, 3), 800);
      this.decorator.addRandomStructure(new LOTRWorldGenStoneRuin.NEAR_HARAD(1, 3), 800);
      this.decorator.addRandomStructure(new LOTRWorldGenStoneRuin.NUMENOR(1, 3), 2000);
      this.decorator.addVillage(new LOTRVillageGenUmbar(this, 0.9F));
      this.registerTravellingTrader(LOTREntityScrapTrader.class);
      this.registerTravellingTrader(LOTREntityDorwinionMerchantMan.class);
      this.registerTravellingTrader(LOTREntityNomadMerchant.class);
      this.setBanditChance(LOTREventSpawner.EventChance.BANDIT_RARE);
      this.setBanditEntityClass(LOTREntityBanditHarad.class);
   }

   public LOTRAchievement getBiomeAchievement() {
      return LOTRAchievement.enterUmbar;
   }

   public LOTRWaypoint.Region getBiomeWaypoints() {
      return LOTRWaypoint.Region.UMBAR;
   }

   public LOTRMusicRegion.Sub getBiomeMusic() {
      return LOTRMusicRegion.NEAR_HARAD.getSubregion("umbar");
   }

   public LOTRRoadType getRoadBlock() {
      return LOTRRoadType.UMBAR;
   }

   public void generateBiomeTerrain(World world, Random random, Block[] blocks, byte[] meta, int i, int k, double stoneNoise, int height, LOTRBiomeVariant variant) {
      Block topBlock_pre = this.field_76752_A;
      int topBlockMeta_pre = this.topBlockMeta;
      Block fillerBlock_pre = this.field_76753_B;
      int fillerBlockMeta_pre = this.fillerBlockMeta;
      double d1 = noiseDirt.func_151601_a((double)i * 0.002D, (double)k * 0.002D);
      double d2 = noiseDirt.func_151601_a((double)i * 0.07D, (double)k * 0.07D);
      double d3 = noiseDirt.func_151601_a((double)i * 0.25D, (double)k * 0.25D);
      double d4 = noiseSand.func_151601_a((double)i * 0.002D, (double)k * 0.002D);
      double d5 = noiseSand.func_151601_a((double)i * 0.07D, (double)k * 0.07D);
      double d6 = noiseSand.func_151601_a((double)i * 0.25D, (double)k * 0.25D);
      if (d4 + d5 + d6 > 1.1D) {
         this.field_76752_A = Blocks.field_150354_m;
         this.topBlockMeta = 0;
         this.field_76753_B = this.field_76752_A;
         this.fillerBlockMeta = this.topBlockMeta;
      } else if (d1 + d2 + d3 > 0.6D) {
         this.field_76752_A = Blocks.field_150346_d;
         this.topBlockMeta = 1;
      }

      super.generateBiomeTerrain(world, random, blocks, meta, i, k, stoneNoise, height, variant);
      this.field_76752_A = topBlock_pre;
      this.topBlockMeta = topBlockMeta_pre;
      this.field_76753_B = fillerBlock_pre;
      this.fillerBlockMeta = fillerBlockMeta_pre;
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
      return 0.15F;
   }

   public float getChanceToSpawnAnimals() {
      return 0.05F;
   }

   public int spawnCountMultiplier() {
      return 2;
   }
}
