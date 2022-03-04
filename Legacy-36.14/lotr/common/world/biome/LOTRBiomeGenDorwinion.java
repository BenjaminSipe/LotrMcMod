package lotr.common.world.biome;

import com.google.common.math.IntMath;
import java.util.Random;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRMod;
import lotr.common.entity.animal.LOTREntityBear;
import lotr.common.entity.animal.LOTREntityHorse;
import lotr.common.entity.animal.LOTREntityKineAraw;
import lotr.common.entity.npc.LOTREntityDaleMerchant;
import lotr.common.entity.npc.LOTREntityGaladhrimTrader;
import lotr.common.entity.npc.LOTREntityIronHillsMerchant;
import lotr.common.entity.npc.LOTREntityNearHaradMerchant;
import lotr.common.entity.npc.LOTREntityScrapTrader;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.feature.LOTRWorldGenBoulder;
import lotr.common.world.feature.LOTRWorldGenDoubleFlower;
import lotr.common.world.map.LOTRRoadType;
import lotr.common.world.map.LOTRRoads;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.world.spawning.LOTRBiomeSpawnList;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.world.spawning.LOTRInvasions;
import lotr.common.world.spawning.LOTRSpawnList;
import lotr.common.world.structure2.LOTRWorldGenDorwinionBath;
import lotr.common.world.structure2.LOTRWorldGenDorwinionBrewery;
import lotr.common.world.structure2.LOTRWorldGenDorwinionCamp;
import lotr.common.world.structure2.LOTRWorldGenDorwinionElfHouse;
import lotr.common.world.structure2.LOTRWorldGenDorwinionGarden;
import lotr.common.world.structure2.LOTRWorldGenDorwinionHouse;
import lotr.common.world.structure2.LOTRWorldGenStoneRuin;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase.SpawnListEntry;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRBiomeGenDorwinion extends LOTRBiome {
   private WorldGenerator boulderGen;
   private LOTRBiomeSpawnList vineyardSpawnList;

   public LOTRBiomeGenDorwinion(int i, boolean major) {
      super(i, major);
      this.boulderGen = new LOTRWorldGenBoulder(LOTRMod.rock, 5, 1, 2);
      this.vineyardSpawnList = new LOTRBiomeSpawnList(this);
      this.field_76762_K.add(new SpawnListEntry(LOTREntityHorse.class, 5, 2, 6));
      this.field_76762_K.add(new SpawnListEntry(LOTREntityKineAraw.class, 6, 4, 4));
      this.field_76762_K.add(new SpawnListEntry(LOTREntityBear.class, 2, 1, 4));
      LOTRBiomeSpawnList.FactionContainer var10000 = this.npcSpawnList.newFactionList(100);
      LOTRBiomeSpawnList.SpawnListContainer[] var10001 = new LOTRBiomeSpawnList.SpawnListContainer[4];
      LOTRBiomeSpawnList var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.DORWINION_MEN, 30);
      var10004 = this.npcSpawnList;
      var10001[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.DORWINION_GUARDS, 10);
      var10004 = this.npcSpawnList;
      var10001[2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.DORWINION_ELVES, 5);
      var10004 = this.npcSpawnList;
      var10001[3] = LOTRBiomeSpawnList.entry(LOTRSpawnList.DORWINION_ELF_WARRIORS, 2);
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(0);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[3];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.DOL_GULDUR_ORCS, 10);
      var10004 = this.npcSpawnList;
      var10001[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MIRKWOOD_SPIDERS, 2).setConquestThreshold(50.0F);
      var10004 = this.npcSpawnList;
      var10001[2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MIRK_TROLLS, 1).setConquestThreshold(200.0F);
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(0);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[2];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GONDOR_SOLDIERS, 10);
      var10004 = this.npcSpawnList;
      var10001[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GONDOR_MEN, 5).setConquestThreshold(100.0F);
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(0);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[4];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORDOR_ORCS, 10);
      var10004 = this.npcSpawnList;
      var10001[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORDOR_WARGS, 2);
      var10004 = this.npcSpawnList;
      var10001[2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.BLACK_URUKS, 2).setConquestThreshold(50.0F);
      var10004 = this.npcSpawnList;
      var10001[3] = LOTRBiomeSpawnList.entry(LOTRSpawnList.OLOG_HAI, 1).setConquestThreshold(200.0F);
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(0);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[4];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.EASTERLING_WARRIORS, 10);
      var10004 = this.npcSpawnList;
      var10001[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.EASTERLING_GOLD_WARRIORS, 1);
      var10004 = this.npcSpawnList;
      var10001[2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.EASTERLING_GOLD_WARRIORS, 2).setConquestThreshold(50.0F);
      var10004 = this.npcSpawnList;
      var10001[3] = LOTRBiomeSpawnList.entry(LOTRSpawnList.EASTERLINGS, 5).setConquestThreshold(200.0F);
      var10000.add(var10001);
      this.npcSpawnList.conquestGainRate = 0.75F;
      var10000 = this.vineyardSpawnList.newFactionList(100);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[1];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.DORWINION_VINEYARDS, 10);
      var10000.add(var10001);
      this.variantChance = 0.3F;
      this.addBiomeVariantSet(LOTRBiomeVariant.SET_NORMAL_OAK);
      this.addBiomeVariant(LOTRBiomeVariant.VINEYARD, 8.0F);
      this.addBiomeVariant(LOTRBiomeVariant.FOREST_BEECH, 0.5F);
      this.addBiomeVariant(LOTRBiomeVariant.FOREST_BIRCH, 0.5F);
      this.addBiomeVariant(LOTRBiomeVariant.ORCHARD_OLIVE, 0.5F);
      this.addBiomeVariant(LOTRBiomeVariant.ORCHARD_APPLE_PEAR, 0.2F);
      this.addBiomeVariant(LOTRBiomeVariant.ORCHARD_ALMOND, 0.2F);
      this.addBiomeVariant(LOTRBiomeVariant.ORCHARD_PLUM, 0.2F);
      this.decorator.setTreeCluster(8, 20);
      this.decorator.willowPerChunk = 1;
      this.decorator.flowersPerChunk = 6;
      this.decorator.doubleFlowersPerChunk = 1;
      this.decorator.grassPerChunk = 8;
      this.decorator.doubleGrassPerChunk = 2;
      this.decorator.addTree(LOTRTreeType.OAK, 200);
      this.decorator.addTree(LOTRTreeType.OAK_LARGE, 100);
      this.decorator.addTree(LOTRTreeType.BIRCH, 50);
      this.decorator.addTree(LOTRTreeType.BIRCH_TALL, 50);
      this.decorator.addTree(LOTRTreeType.BIRCH_LARGE, 50);
      this.decorator.addTree(LOTRTreeType.BEECH, 20);
      this.decorator.addTree(LOTRTreeType.BEECH_LARGE, 20);
      this.decorator.addTree(LOTRTreeType.CYPRESS, 500);
      this.decorator.addTree(LOTRTreeType.CYPRESS_LARGE, 50);
      this.decorator.addTree(LOTRTreeType.OAK_SHRUB, 800);
      this.decorator.addTree(LOTRTreeType.APPLE, 5);
      this.decorator.addTree(LOTRTreeType.PEAR, 5);
      this.decorator.addTree(LOTRTreeType.OLIVE, 20);
      this.decorator.addTree(LOTRTreeType.OLIVE_LARGE, 20);
      this.decorator.addTree(LOTRTreeType.ALMOND, 10);
      this.decorator.addTree(LOTRTreeType.PLUM, 10);
      this.registerRhunPlainsFlowers();
      this.biomeColors.setGrass(10538541);
      this.decorator.addRandomStructure(new LOTRWorldGenStoneRuin.DORWINION(1, 4), 1000);
      this.decorator.addRandomStructure(new LOTRWorldGenDorwinionGarden(false), 300);
      this.decorator.addRandomStructure(new LOTRWorldGenDorwinionCamp(false), 400);
      this.decorator.addRandomStructure(new LOTRWorldGenDorwinionHouse(false), 200);
      this.decorator.addRandomStructure(new LOTRWorldGenDorwinionBrewery(false), 500);
      this.decorator.addRandomStructure(new LOTRWorldGenDorwinionElfHouse(false), 400);
      this.decorator.addRandomStructure(new LOTRWorldGenDorwinionBath(false), 600);
      this.registerTravellingTrader(LOTREntityGaladhrimTrader.class);
      this.registerTravellingTrader(LOTREntityNearHaradMerchant.class);
      this.registerTravellingTrader(LOTREntityIronHillsMerchant.class);
      this.registerTravellingTrader(LOTREntityScrapTrader.class);
      this.registerTravellingTrader(LOTREntityDaleMerchant.class);
      this.setBanditChance(LOTREventSpawner.EventChance.BANDIT_RARE);
      this.invasionSpawns.addInvasion(LOTRInvasions.DOL_GULDUR, LOTREventSpawner.EventChance.RARE);
      this.invasionSpawns.addInvasion(LOTRInvasions.MORDOR, LOTREventSpawner.EventChance.RARE);
   }

   public LOTRBiomeSpawnList getNPCSpawnList(World world, Random random, int i, int j, int k, LOTRBiomeVariant variant) {
      return variant == LOTRBiomeVariant.VINEYARD ? this.vineyardSpawnList : super.getNPCSpawnList(world, random, i, j, k, variant);
   }

   public LOTRAchievement getBiomeAchievement() {
      return LOTRAchievement.enterDorwinion;
   }

   public LOTRWaypoint.Region getBiomeWaypoints() {
      return LOTRWaypoint.Region.DORWINION;
   }

   public LOTRMusicRegion.Sub getBiomeMusic() {
      return LOTRMusicRegion.DORWINION.getSubregion("dorwinion");
   }

   public LOTRRoadType getRoadBlock() {
      return LOTRRoadType.DORWINION;
   }

   public boolean hasDomesticAnimals() {
      return true;
   }

   public void generateBiomeTerrain(World world, Random random, Block[] blocks, byte[] meta, int i, int k, double stoneNoise, int height, LOTRBiomeVariant variant) {
      super.generateBiomeTerrain(world, random, blocks, meta, i, k, stoneNoise, height, variant);
      int chunkX = i & 15;
      int chunkZ = k & 15;
      int xzIndex = chunkX * 16 + chunkZ;
      int ySize = blocks.length / 256;
      boolean vineyard = variant == LOTRBiomeVariant.VINEYARD;
      if (vineyard && !LOTRRoads.isRoadAt(i, k)) {
         for(int j = 128; j >= 0; --j) {
            int index = xzIndex * ySize + j;
            Block above = blocks[index + 1];
            Block block = blocks[index];
            if (block != null && block.func_149662_c() && (above == null || above.func_149688_o() == Material.field_151579_a)) {
               int i1 = IntMath.mod(i, 6);
               int i2 = IntMath.mod(i, 24);
               int k1 = IntMath.mod(k, 32);
               int k2 = IntMath.mod(k, 64);
               if ((i1 == 0 || i1 == 5) && k1 != 0) {
                  blocks[index] = Blocks.field_150458_ak;
                  meta[index] = 0;
                  int h = 2;
                  if (biomeTerrainNoise.func_151601_a((double)i, (double)k) > 0.0D) {
                     ++h;
                  }

                  double d = 0.01D;
                  boolean red = biomeTerrainNoise.func_151601_a((double)i * d, (double)k * d) > 0.0D;
                  Block vineBlock = red ? LOTRMod.grapevineRed : LOTRMod.grapevineWhite;

                  for(int j1 = 1; j1 <= h; ++j1) {
                     blocks[index + j1] = vineBlock;
                     meta[index + j1] = 7;
                  }
               } else if (i1 >= 2 && i1 <= 3) {
                  blocks[index] = LOTRMod.dirtPath;
                  meta[index] = 0;
                  if (i1 == i2 && (i1 == 2 && k2 == 16 || i1 == 3 && k2 == 48)) {
                     int h = 3;

                     for(int j1 = 1; j1 <= h; ++j1) {
                        if (j1 == h) {
                           blocks[index + j1] = Blocks.field_150478_aa;
                           meta[index + j1] = 5;
                        } else {
                           blocks[index + j1] = LOTRMod.fence2;
                           meta[index + j1] = 10;
                        }
                     }
                  }
               } else {
                  blocks[index] = this.field_76752_A;
                  meta[index] = (byte)this.topBlockMeta;
               }
               break;
            }
         }
      }

   }

   public void func_76728_a(World world, Random random, int i, int k) {
      super.func_76728_a(world, random, i, k);
      if (random.nextInt(50) == 0) {
         for(int l = 0; l < 3; ++l) {
            int i1 = i + random.nextInt(16) + 8;
            int k1 = k + random.nextInt(16) + 8;
            this.boulderGen.func_76484_a(world, random, i1, world.func_72976_f(i1, k1), k1);
         }
      }

   }

   public WorldGenerator getRandomWorldGenForDoubleFlower(Random random) {
      if (random.nextInt(3) == 0) {
         LOTRWorldGenDoubleFlower doubleFlowerGen = new LOTRWorldGenDoubleFlower();
         doubleFlowerGen.setFlowerType(0);
         return doubleFlowerGen;
      } else {
         return super.getRandomWorldGenForDoubleFlower(random);
      }
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
