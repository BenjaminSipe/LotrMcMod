package lotr.common.world.biome;

import java.util.Random;
import lotr.common.LOTRAchievement;
import lotr.common.entity.animal.LOTREntityBear;
import lotr.common.entity.animal.LOTREntityCrebain;
import lotr.common.entity.npc.LOTREntityBlueDwarfMerchant;
import lotr.common.entity.npc.LOTREntityDaleMerchant;
import lotr.common.entity.npc.LOTREntityIronHillsMerchant;
import lotr.common.entity.npc.LOTREntityScrapTrader;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.feature.LOTRWorldGenBoulder;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.world.spawning.LOTRBiomeSpawnList;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.world.spawning.LOTRInvasions;
import lotr.common.world.spawning.LOTRSpawnList;
import lotr.common.world.structure.LOTRWorldGenDunlendingCampfire;
import lotr.common.world.structure2.LOTRWorldGenDunlandHillFort;
import lotr.common.world.structure2.LOTRWorldGenDunlendingHouse;
import lotr.common.world.structure2.LOTRWorldGenDunlendingTavern;
import lotr.common.world.structure2.LOTRWorldGenSmallStoneRuin;
import lotr.common.world.structure2.LOTRWorldGenStoneRuin;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase.SpawnListEntry;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRBiomeGenDunland extends LOTRBiome {
   private WorldGenerator boulderGen;

   public LOTRBiomeGenDunland(int i, boolean major) {
      super(i, major);
      this.boulderGen = new LOTRWorldGenBoulder(Blocks.field_150348_b, 0, 1, 3);
      this.field_76762_K.add(new SpawnListEntry(EntityWolf.class, 8, 4, 8));
      this.field_76762_K.add(new SpawnListEntry(LOTREntityBear.class, 4, 1, 4));
      this.spawnableLOTRAmbientList.add(new SpawnListEntry(LOTREntityCrebain.class, 10, 4, 4));
      LOTRBiomeSpawnList.FactionContainer var10000 = this.npcSpawnList.newFactionList(100);
      LOTRBiomeSpawnList.SpawnListContainer[] var10001 = new LOTRBiomeSpawnList.SpawnListContainer[2];
      LOTRBiomeSpawnList var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.DUNLENDINGS, 3);
      var10004 = this.npcSpawnList;
      var10001[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.DUNLENDING_WARRIORS, 1);
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
      var10000 = this.npcSpawnList.newFactionList(2);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[1];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.RUFFIANS, 10);
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(0);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[3];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ISENGARD_SNAGA, 5);
      var10004 = this.npcSpawnList;
      var10001[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.URUK_HAI, 10);
      var10004 = this.npcSpawnList;
      var10001[2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.URUK_WARGS, 4);
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(0);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[1];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ROHIRRIM_WARRIORS, 10);
      var10000.add(var10001);
      this.npcSpawnList.conquestGainRate = 0.5F;
      this.addBiomeVariantSet(LOTRBiomeVariant.SET_MOUNTAINS);
      this.addBiomeVariant(LOTRBiomeVariant.FOREST_LARCH, 0.4F);
      this.addBiomeVariant(LOTRBiomeVariant.FOREST_PINE, 0.4F);
      this.decorator.treesPerChunk = 0;
      this.decorator.willowPerChunk = 1;
      this.decorator.grassPerChunk = 6;
      this.decorator.doubleGrassPerChunk = 1;
      this.decorator.addTree(LOTRTreeType.SPRUCE, 500);
      this.decorator.addTree(LOTRTreeType.OAK_TALL, 200);
      this.decorator.addTree(LOTRTreeType.OAK_LARGE, 20);
      this.decorator.addTree(LOTRTreeType.CHESTNUT, 50);
      this.decorator.addTree(LOTRTreeType.CHESTNUT_LARGE, 10);
      this.decorator.addTree(LOTRTreeType.FIR, 500);
      this.decorator.addTree(LOTRTreeType.PINE, 500);
      this.registerForestFlowers();
      this.decorator.addRandomStructure(new LOTRWorldGenDunlendingHouse(false), 25);
      this.decorator.addRandomStructure(new LOTRWorldGenDunlendingTavern(false), 100);
      this.decorator.addRandomStructure(new LOTRWorldGenDunlendingCampfire(false), 40);
      this.decorator.addRandomStructure(new LOTRWorldGenDunlandHillFort(false), 150);
      this.decorator.addRandomStructure(new LOTRWorldGenStoneRuin.STONE(1, 3), 500);
      this.decorator.addRandomStructure(new LOTRWorldGenSmallStoneRuin(false), 300);
      this.registerTravellingTrader(LOTREntityBlueDwarfMerchant.class);
      this.registerTravellingTrader(LOTREntityIronHillsMerchant.class);
      this.registerTravellingTrader(LOTREntityScrapTrader.class);
      this.registerTravellingTrader(LOTREntityDaleMerchant.class);
      this.setBanditChance(LOTREventSpawner.EventChance.BANDIT_COMMON);
      this.invasionSpawns.addInvasion(LOTRInvasions.RANGER_NORTH, LOTREventSpawner.EventChance.RARE);
      this.invasionSpawns.addInvasion(LOTRInvasions.ROHAN, LOTREventSpawner.EventChance.UNCOMMON);
      this.invasionSpawns.addInvasion(LOTRInvasions.GUNDABAD, LOTREventSpawner.EventChance.RARE);
      this.invasionSpawns.addInvasion(LOTRInvasions.GUNDABAD_WARG, LOTREventSpawner.EventChance.RARE);
   }

   public LOTRAchievement getBiomeAchievement() {
      return LOTRAchievement.enterDunland;
   }

   public LOTRWaypoint.Region getBiomeWaypoints() {
      return LOTRWaypoint.Region.DUNLAND;
   }

   public LOTRMusicRegion.Sub getBiomeMusic() {
      return LOTRMusicRegion.DUNLAND.getSubregion("dunland");
   }

   public void func_76728_a(World world, Random random, int i, int k) {
      super.func_76728_a(world, random, i, k);

      int l;
      int i1;
      int k1;
      for(l = 0; l < 6; ++l) {
         i1 = i + random.nextInt(16) + 8;
         k1 = k + random.nextInt(16) + 8;
         int j1 = world.func_72976_f(i1, k1);
         if (j1 > 85) {
            this.decorator.genTree(world, random, i1, j1, k1);
         }
      }

      if (random.nextInt(8) == 0) {
         for(l = 0; l < 4; ++l) {
            i1 = i + random.nextInt(16) + 8;
            k1 = k + random.nextInt(16) + 8;
            this.boulderGen.func_76484_a(world, random, i1, world.func_72976_f(i1, k1), k1);
         }
      }

   }

   public float getTreeIncreaseChance() {
      return 0.75F;
   }

   public float getChanceToSpawnAnimals() {
      return 0.5F;
   }

   public int spawnCountMultiplier() {
      return 2;
   }
}
