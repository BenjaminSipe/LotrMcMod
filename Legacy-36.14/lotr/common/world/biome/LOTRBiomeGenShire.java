package lotr.common.world.biome;

import java.util.Random;
import lotr.common.LOTRMod;
import lotr.common.entity.animal.LOTREntityShirePony;
import lotr.common.entity.npc.LOTREntityBlueDwarfMerchant;
import lotr.common.entity.npc.LOTREntityDaleMerchant;
import lotr.common.entity.npc.LOTREntityGaladhrimTrader;
import lotr.common.entity.npc.LOTREntityIronHillsMerchant;
import lotr.common.entity.npc.LOTREntityRivendellTrader;
import lotr.common.entity.npc.LOTREntityScrapTrader;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.biome.variant.LOTRBiomeVariantOrchard;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.feature.LOTRWorldGenClover;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.world.spawning.LOTRBiomeSpawnList;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.world.spawning.LOTRSpawnList;
import lotr.common.world.structure.LOTRWorldGenHobbitPicnicBench;
import lotr.common.world.structure2.LOTRWorldGenHobbitBurrow;
import lotr.common.world.structure2.LOTRWorldGenHobbitFarm;
import lotr.common.world.structure2.LOTRWorldGenHobbitHole;
import lotr.common.world.structure2.LOTRWorldGenHobbitHouse;
import lotr.common.world.structure2.LOTRWorldGenHobbitTavern;
import lotr.common.world.structure2.LOTRWorldGenHobbitWindmill;
import lotr.common.world.structure2.LOTRWorldGenStoneRuin;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase.SpawnListEntry;
import net.minecraft.world.gen.feature.WorldGenDoublePlant;
import net.minecraft.world.gen.feature.WorldGenFlowers;

public class LOTRBiomeGenShire extends LOTRBiome {
   private LOTRBiomeSpawnList orcharderSpawnList = new LOTRBiomeSpawnList(this);
   private static final float orcharderSpawnWeight = 0.3F;

   public LOTRBiomeGenShire(int i, boolean major) {
      super(i, major);
      this.field_76762_K.add(new SpawnListEntry(LOTREntityShirePony.class, 12, 2, 6));
      LOTRBiomeSpawnList.FactionContainer var10000 = this.npcSpawnList.newFactionList(100);
      LOTRBiomeSpawnList.SpawnListContainer[] var10001 = new LOTRBiomeSpawnList.SpawnListContainer[1];
      LOTRBiomeSpawnList var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.HOBBITS, 10);
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(0);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[2];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GUNDABAD_ORCS, 10);
      var10004 = this.npcSpawnList;
      var10001[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GUNDABAD_WARGS, 3).setConquestThreshold(100.0F);
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(0);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[2];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ANGMAR_ORCS, 10);
      var10004 = this.npcSpawnList;
      var10001[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ANGMAR_WARGS, 3).setConquestThreshold(100.0F);
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(0);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[3];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.RUFFIANS, 10);
      var10004 = this.npcSpawnList;
      var10001[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ISENGARD_SNAGA, 10).setConquestThreshold(25.0F);
      var10004 = this.npcSpawnList;
      var10001[2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.URUK_HAI, 5).setConquestThreshold(25.0F);
      var10000.add(var10001);
      this.npcSpawnList.conquestGainRate = 0.2F;
      var10000 = this.orcharderSpawnList.newFactionList(100);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[1];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.HOBBITS_ORCHARD);
      var10000.add(var10001);
      this.variantChance = 0.25F;
      this.addBiomeVariant(LOTRBiomeVariant.FLOWERS);
      this.addBiomeVariant(LOTRBiomeVariant.FOREST);
      this.addBiomeVariant(LOTRBiomeVariant.FOREST_LIGHT);
      this.addBiomeVariant(LOTRBiomeVariant.HILLS);
      this.addBiomeVariant(LOTRBiomeVariant.HILLS_FOREST);
      this.addBiomeVariant(LOTRBiomeVariant.FOREST_BIRCH, 0.5F);
      this.addBiomeVariant(LOTRBiomeVariant.FOREST_ASPEN, 0.5F);
      this.addBiomeVariant(LOTRBiomeVariant.ORCHARD_SHIRE, 1.0F);
      this.addBiomeVariant(LOTRBiomeVariant.ORCHARD_PLUM, 0.3F);
      this.decorator.willowPerChunk = 1;
      this.decorator.flowersPerChunk = 3;
      this.decorator.doubleFlowersPerChunk = 1;
      this.decorator.grassPerChunk = 6;
      this.decorator.generateLava = false;
      this.decorator.addTree(LOTRTreeType.OAK, 1000);
      this.decorator.addTree(LOTRTreeType.OAK_LARGE, 400);
      this.decorator.addTree(LOTRTreeType.OAK_PARTY, 10);
      this.decorator.addTree(LOTRTreeType.CHESTNUT, 250);
      this.decorator.addTree(LOTRTreeType.CHESTNUT_LARGE, 100);
      this.decorator.addTree(LOTRTreeType.BIRCH, 25);
      this.decorator.addTree(LOTRTreeType.BIRCH_LARGE, 10);
      this.decorator.addTree(LOTRTreeType.ASPEN, 50);
      this.decorator.addTree(LOTRTreeType.ASPEN_LARGE, 10);
      this.decorator.addTree(LOTRTreeType.APPLE, 5);
      this.decorator.addTree(LOTRTreeType.PEAR, 5);
      this.decorator.addTree(LOTRTreeType.CHERRY, 2);
      this.decorator.addTree(LOTRTreeType.PLUM, 5);
      this.registerPlainsFlowers();
      this.biomeColors.setGrass(8111137);
      if (this.hasShireStructures()) {
         if (this.getClass() == LOTRBiomeGenShire.class) {
            this.decorator.addRandomStructure(new LOTRWorldGenHobbitHole(false), 90);
            this.decorator.addRandomStructure(new LOTRWorldGenHobbitBurrow(false), 45);
            this.decorator.addRandomStructure(new LOTRWorldGenHobbitHouse(false), 45);
            this.decorator.addRandomStructure(new LOTRWorldGenHobbitTavern(false), 70);
            this.decorator.addRandomStructure(new LOTRWorldGenHobbitWindmill(false), 600);
            this.decorator.addRandomStructure(new LOTRWorldGenHobbitFarm(false), 500);
         }

         this.decorator.addRandomStructure(new LOTRWorldGenHobbitPicnicBench(false), 40);
         this.decorator.addRandomStructure(new LOTRWorldGenStoneRuin.STONE(1, 4), 1500);
         this.decorator.addRandomStructure(new LOTRWorldGenStoneRuin.ARNOR(1, 4), 1500);
      }

      this.registerTravellingTrader(LOTREntityGaladhrimTrader.class);
      this.registerTravellingTrader(LOTREntityBlueDwarfMerchant.class);
      this.registerTravellingTrader(LOTREntityIronHillsMerchant.class);
      this.registerTravellingTrader(LOTREntityScrapTrader.class);
      this.registerTravellingTrader(LOTREntityDaleMerchant.class);
      this.registerTravellingTrader(LOTREntityRivendellTrader.class);
      this.setBanditChance(LOTREventSpawner.EventChance.NEVER);
   }

   public LOTRBiomeSpawnList getNPCSpawnList(World world, Random random, int i, int j, int k, LOTRBiomeVariant variant) {
      return variant instanceof LOTRBiomeVariantOrchard && random.nextFloat() < 0.3F ? this.orcharderSpawnList : super.getNPCSpawnList(world, random, i, j, k, variant);
   }

   public LOTRWaypoint.Region getBiomeWaypoints() {
      return LOTRWaypoint.Region.SHIRE;
   }

   public LOTRMusicRegion.Sub getBiomeMusic() {
      return LOTRMusicRegion.SHIRE.getSubregion("shire");
   }

   public boolean hasDomesticAnimals() {
      return true;
   }

   public boolean hasShireStructures() {
      return true;
   }

   public void func_76728_a(World world, Random random, int i, int k) {
      super.func_76728_a(world, random, i, k);

      int i1;
      int j1;
      int k1;
      for(i1 = 0; i1 < this.decorator.grassPerChunk / 2; ++i1) {
         j1 = i + random.nextInt(16) + 8;
         k1 = random.nextInt(128);
         int k1 = k + random.nextInt(16) + 8;
         (new LOTRWorldGenClover()).func_76484_a(world, random, j1, k1, k1);
      }

      if (random.nextInt(6) == 0) {
         i1 = i + random.nextInt(16) + 8;
         j1 = random.nextInt(128);
         k1 = k + random.nextInt(16) + 8;
         (new WorldGenFlowers(LOTRMod.pipeweedPlant)).func_76484_a(world, random, i1, j1, k1);
      }

      if (this.decorator.doubleFlowersPerChunk > 0 && random.nextInt(6) == 0) {
         i1 = i + random.nextInt(16) + 8;
         j1 = random.nextInt(128);
         k1 = k + random.nextInt(16) + 8;
         WorldGenDoublePlant doubleFlowerGen = new WorldGenDoublePlant();
         doubleFlowerGen.func_150548_a(0);
         doubleFlowerGen.func_76484_a(world, random, i1, j1, k1);
      }

   }

   public float getTreeIncreaseChance() {
      return 0.5F;
   }

   public int spawnCountMultiplier() {
      return 3;
   }
}
