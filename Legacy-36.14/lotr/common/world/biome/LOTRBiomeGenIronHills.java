package lotr.common.world.biome;

import java.util.Random;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRMod;
import lotr.common.entity.animal.LOTREntityBear;
import lotr.common.entity.animal.LOTREntityWildBoar;
import lotr.common.entity.npc.LOTREntityBlueDwarfMerchant;
import lotr.common.entity.npc.LOTREntityDaleMerchant;
import lotr.common.entity.npc.LOTREntityDorwinionMerchantMan;
import lotr.common.entity.npc.LOTREntityScrapTrader;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.map.LOTRRoadType;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.world.spawning.LOTRBiomeSpawnList;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.world.spawning.LOTRInvasions;
import lotr.common.world.spawning.LOTRSpawnList;
import lotr.common.world.structure2.LOTRWorldGenDwarfHouse;
import lotr.common.world.structure2.LOTRWorldGenDwarfSmithy;
import lotr.common.world.structure2.LOTRWorldGenDwarvenTower;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase.SpawnListEntry;
import net.minecraft.world.gen.feature.WorldGenMinable;

public class LOTRBiomeGenIronHills extends LOTRBiome {
   public LOTRBiomeGenIronHills(int i, boolean major) {
      super(i, major);
      this.field_76762_K.add(new SpawnListEntry(LOTREntityWildBoar.class, 50, 4, 4));
      this.field_76762_K.add(new SpawnListEntry(LOTREntityBear.class, 8, 1, 4));
      LOTRBiomeSpawnList.FactionContainer var10000 = this.npcSpawnList.newFactionList(500);
      LOTRBiomeSpawnList.SpawnListContainer[] var10001 = new LOTRBiomeSpawnList.SpawnListContainer[1];
      LOTRBiomeSpawnList var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.DWARVES, 10);
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(0, 1.0F);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[4];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GUNDABAD_ORCS, 10);
      var10004 = this.npcSpawnList;
      var10001[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GUNDABAD_WARGS, 2).setConquestOnly();
      var10004 = this.npcSpawnList;
      var10001[2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GUNDABAD_URUKS, 1).setConquestThreshold(50.0F);
      var10004 = this.npcSpawnList;
      var10001[3] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GUNDABAD_URUKS, 1).setConquestThreshold(100.0F);
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(0, 1.0F);
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
      var10000 = this.npcSpawnList.newFactionList(0, 1.0F);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[3];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.EASTERLING_WARRIORS, 10);
      var10004 = this.npcSpawnList;
      var10001[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.EASTERLING_GOLD_WARRIORS, 1);
      var10004 = this.npcSpawnList;
      var10001[2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.EASTERLING_GOLD_WARRIORS, 2).setConquestThreshold(50.0F);
      var10000.add(var10001);
      this.variantChance = 0.3F;
      this.addBiomeVariantSet(LOTRBiomeVariant.SET_MOUNTAINS);
      this.decorator.biomeGemFactor = 0.75F;
      this.decorator.addOre(new WorldGenMinable(Blocks.field_150366_p, 4), 20.0F, 0, 96);
      this.decorator.addOre(new WorldGenMinable(Blocks.field_150352_o, 4), 2.0F, 0, 48);
      this.decorator.addOre(new WorldGenMinable(LOTRMod.oreSilver, 4), 2.0F, 0, 48);
      this.decorator.addOre(new WorldGenMinable(LOTRMod.oreGlowstone, 4), 8.0F, 0, 48);
      this.decorator.treesPerChunk = 0;
      this.decorator.flowersPerChunk = 1;
      this.decorator.grassPerChunk = 8;
      this.decorator.doubleGrassPerChunk = 1;
      this.decorator.generateWater = false;
      this.decorator.generateLava = false;
      this.decorator.generateCobwebs = false;
      this.decorator.addTree(LOTRTreeType.SPRUCE, 500);
      this.decorator.addTree(LOTRTreeType.SPRUCE_MEGA, 200);
      this.decorator.addTree(LOTRTreeType.SPRUCE_MEGA_THIN, 50);
      this.decorator.addTree(LOTRTreeType.FIR, 400);
      this.decorator.addTree(LOTRTreeType.PINE, 400);
      this.registerMountainsFlowers();
      this.addFlower(LOTRMod.dwarfHerb, 0, 1);
      this.decorator.addRandomStructure(new LOTRWorldGenDwarvenTower(false), 300);
      this.decorator.addRandomStructure(new LOTRWorldGenDwarfSmithy(false), 150);
      this.registerTravellingTrader(LOTREntityBlueDwarfMerchant.class);
      this.registerTravellingTrader(LOTREntityScrapTrader.class);
      this.registerTravellingTrader(LOTREntityDaleMerchant.class);
      this.registerTravellingTrader(LOTREntityDorwinionMerchantMan.class);
      this.setBanditChance(LOTREventSpawner.EventChance.BANDIT_RARE);
      this.invasionSpawns.addInvasion(LOTRInvasions.GUNDABAD, LOTREventSpawner.EventChance.RARE);
      this.invasionSpawns.addInvasion(LOTRInvasions.GUNDABAD_WARG, LOTREventSpawner.EventChance.UNCOMMON);
   }

   public LOTRAchievement getBiomeAchievement() {
      return LOTRAchievement.enterIronHills;
   }

   public LOTRWaypoint.Region getBiomeWaypoints() {
      return LOTRWaypoint.Region.IRON_HILLS;
   }

   public LOTRMusicRegion.Sub getBiomeMusic() {
      return LOTRMusicRegion.DWARVEN.getSubregion("ironHills");
   }

   public LOTRRoadType getRoadBlock() {
      return LOTRRoadType.DWARVEN;
   }

   public void func_76728_a(World world, Random random, int i, int k) {
      super.func_76728_a(world, random, i, k);

      int l;
      int i1;
      int k1;
      int j1;
      for(l = 0; l < 4; ++l) {
         i1 = i + random.nextInt(16) + 8;
         k1 = 70 + random.nextInt(60);
         j1 = k + random.nextInt(16) + 8;
         (new LOTRWorldGenDwarfHouse(false)).func_76484_a(world, random, i1, k1, j1);
      }

      for(l = 0; l < 8; ++l) {
         i1 = i + random.nextInt(16) + 8;
         k1 = k + random.nextInt(16) + 8;
         j1 = world.func_72976_f(i1, k1);
         if (j1 > 80) {
            this.decorator.genTree(world, random, i1, j1, k1);
         }
      }

   }

   public float getChanceToSpawnAnimals() {
      return 0.25F;
   }

   public float getTreeIncreaseChance() {
      return 0.1F;
   }
}
