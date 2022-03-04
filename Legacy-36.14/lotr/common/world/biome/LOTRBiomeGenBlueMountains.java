package lotr.common.world.biome;

import java.util.Random;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityDaleMerchant;
import lotr.common.entity.npc.LOTREntityIronHillsMerchant;
import lotr.common.entity.npc.LOTREntityRivendellTrader;
import lotr.common.entity.npc.LOTREntityScrapTrader;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.map.LOTRRoadType;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.world.spawning.LOTRBiomeSpawnList;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.world.spawning.LOTRInvasions;
import lotr.common.world.spawning.LOTRSpawnList;
import lotr.common.world.structure.LOTRWorldGenBlueMountainsStronghold;
import lotr.common.world.structure2.LOTRWorldGenBlueMountainsHouse;
import lotr.common.world.structure2.LOTRWorldGenBlueMountainsSmithy;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenMinable;

public class LOTRBiomeGenBlueMountains extends LOTRBiome {
   public LOTRBiomeGenBlueMountains(int i, boolean major) {
      super(i, major);
      LOTRBiomeSpawnList.FactionContainer var10000 = this.npcSpawnList.newFactionList(600);
      LOTRBiomeSpawnList.SpawnListContainer[] var10001 = new LOTRBiomeSpawnList.SpawnListContainer[1];
      LOTRBiomeSpawnList var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.BLUE_DWARVES, 10);
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(1, 2.0F);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[3];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GUNDABAD_ORCS, 4);
      var10004 = this.npcSpawnList;
      var10001[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GUNDABAD_WARGS, 1);
      var10004 = this.npcSpawnList;
      var10001[2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GUNDABAD_URUKS, 1).setConquestThreshold(50.0F);
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(0, 2.0F);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[2];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ANGMAR_ORCS, 4);
      var10004 = this.npcSpawnList;
      var10001[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ANGMAR_WARGS, 1);
      var10000.add(var10001);
      this.variantChance = 0.2F;
      this.addBiomeVariantSet(LOTRBiomeVariant.SET_MOUNTAINS);
      this.addBiomeVariant(LOTRBiomeVariant.FOREST_BEECH, 0.2F);
      this.addBiomeVariant(LOTRBiomeVariant.FOREST_BIRCH, 0.2F);
      this.addBiomeVariant(LOTRBiomeVariant.FOREST_LARCH, 0.2F);
      this.addBiomeVariant(LOTRBiomeVariant.FOREST_PINE, 0.2F);
      this.addBiomeVariant(LOTRBiomeVariant.FOREST_ASPEN, 0.2F);
      this.decorator.biomeGemFactor = 1.0F;
      this.decorator.addSoil(new WorldGenMinable(LOTRMod.rock, 3, 60, Blocks.field_150348_b), 6.0F, 0, 96);
      this.decorator.addOre(new WorldGenMinable(Blocks.field_150365_q, 8), 10.0F, 0, 128);
      this.decorator.addOre(new WorldGenMinable(Blocks.field_150366_p, 4), 10.0F, 0, 96);
      this.decorator.addOre(new WorldGenMinable(LOTRMod.oreGlowstone, 4), 8.0F, 0, 48);
      this.decorator.treesPerChunk = 1;
      this.decorator.willowPerChunk = 1;
      this.decorator.flowersPerChunk = 1;
      this.decorator.grassPerChunk = 6;
      this.decorator.doubleGrassPerChunk = 1;
      this.decorator.generateWater = false;
      this.decorator.generateLava = false;
      this.decorator.generateCobwebs = false;
      this.decorator.addTree(LOTRTreeType.OAK, 300);
      this.decorator.addTree(LOTRTreeType.OAK_LARGE, 100);
      this.decorator.addTree(LOTRTreeType.SPRUCE, 500);
      this.decorator.addTree(LOTRTreeType.BIRCH, 400);
      this.decorator.addTree(LOTRTreeType.FIR, 500);
      this.decorator.addTree(LOTRTreeType.PINE, 500);
      this.registerMountainsFlowers();
      this.addFlower(LOTRMod.dwarfHerb, 0, 1);
      this.biomeColors.setSky(7506425);
      this.decorator.addRandomStructure(new LOTRWorldGenBlueMountainsStronghold(false), 400);
      this.decorator.addRandomStructure(new LOTRWorldGenBlueMountainsSmithy(false), 150);
      this.registerTravellingTrader(LOTREntityRivendellTrader.class);
      this.registerTravellingTrader(LOTREntityIronHillsMerchant.class);
      this.registerTravellingTrader(LOTREntityScrapTrader.class);
      this.registerTravellingTrader(LOTREntityDaleMerchant.class);
      this.setBanditChance(LOTREventSpawner.EventChance.BANDIT_RARE);
      this.invasionSpawns.addInvasion(LOTRInvasions.GUNDABAD, LOTREventSpawner.EventChance.RARE);
      this.invasionSpawns.addInvasion(LOTRInvasions.GUNDABAD_WARG, LOTREventSpawner.EventChance.UNCOMMON);
   }

   public LOTRAchievement getBiomeAchievement() {
      return LOTRAchievement.enterBlueMountains;
   }

   public LOTRWaypoint.Region getBiomeWaypoints() {
      return LOTRWaypoint.Region.BLUE_MOUNTAINS;
   }

   public LOTRMusicRegion.Sub getBiomeMusic() {
      return LOTRMusicRegion.DWARVEN.getSubregion("blueMountains");
   }

   public boolean getEnableRiver() {
      return false;
   }

   public LOTRRoadType getRoadBlock() {
      return LOTRRoadType.DWARVEN;
   }

   protected void generateMountainTerrain(World world, Random random, Block[] blocks, byte[] meta, int i, int k, int xzIndex, int ySize, int height, int rockDepth, LOTRBiomeVariant variant) {
      int snowHeight = 110 - rockDepth;
      int stoneHeight = snowHeight - 20;

      for(int j = ySize - 1; j >= stoneHeight; --j) {
         int index = xzIndex * ySize + j;
         Block block = blocks[index];
         if (j >= snowHeight && block == this.field_76752_A) {
            blocks[index] = Blocks.field_150433_aE;
            meta[index] = 0;
         } else if (block == this.field_76752_A || block == this.field_76753_B) {
            blocks[index] = LOTRMod.rock;
            meta[index] = 3;
         }
      }

   }

   public void func_76728_a(World world, Random random, int i, int k) {
      super.func_76728_a(world, random, i, k);

      for(int l = 0; l < 4; ++l) {
         int i1 = i + random.nextInt(16) + 8;
         int j1 = 70 + random.nextInt(80);
         int k1 = k + random.nextInt(16) + 8;
         (new LOTRWorldGenBlueMountainsHouse(false)).func_76484_a(world, random, i1, j1, k1);
      }

   }

   public float getChanceToSpawnAnimals() {
      return 0.2F;
   }
}
