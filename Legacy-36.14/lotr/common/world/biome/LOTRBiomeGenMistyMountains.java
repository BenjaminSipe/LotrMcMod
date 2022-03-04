package lotr.common.world.biome;

import java.util.Random;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRMod;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.world.spawning.LOTRBiomeSpawnList;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.world.spawning.LOTRSpawnList;
import lotr.common.world.structure2.LOTRWorldGenRuinedDwarvenTower;
import lotr.common.world.structure2.LOTRWorldGenSmallStoneRuin;
import lotr.common.world.structure2.LOTRWorldGenStoneRuin;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenMinable;

public class LOTRBiomeGenMistyMountains extends LOTRBiome {
   public LOTRBiomeGenMistyMountains(int i, boolean major) {
      super(i, major);
      this.field_76762_K.clear();
      this.spawnableLOTRAmbientList.clear();
      LOTRBiomeSpawnList.FactionContainer var10000 = this.npcSpawnList.newFactionList(100);
      LOTRBiomeSpawnList.SpawnListContainer[] var10001 = new LOTRBiomeSpawnList.SpawnListContainer[3];
      LOTRBiomeSpawnList var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GUNDABAD_ORCS, 30);
      var10004 = this.npcSpawnList;
      var10001[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GUNDABAD_WARGS, 20);
      var10004 = this.npcSpawnList;
      var10001[2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GUNDABAD_URUKS, 7);
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(20);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[4];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.SNOW_TROLLS, 1).setSpawnChance(5000);
      var10004 = this.npcSpawnList;
      var10001[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ANGMAR_ORCS, 30).setConquestOnly();
      var10004 = this.npcSpawnList;
      var10001[2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ANGMAR_WARGS, 20).setConquestOnly();
      var10004 = this.npcSpawnList;
      var10001[3] = LOTRBiomeSpawnList.entry(LOTRSpawnList.SNOW_TROLLS, 6).setConquestOnly();
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(0);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[1];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.BLUE_DWARVES, 10);
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(0);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[1];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.DWARVES, 10);
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(0);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[3];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ISENGARD_SNAGA, 15);
      var10004 = this.npcSpawnList;
      var10001[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.URUK_HAI, 30);
      var10004 = this.npcSpawnList;
      var10001[2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.URUK_WARGS, 20);
      var10000.add(var10001);
      this.variantChance = 0.1F;
      this.addBiomeVariantSet(LOTRBiomeVariant.SET_MOUNTAINS);
      this.addBiomeVariant(LOTRBiomeVariant.FOREST_LARCH, 0.3F);
      this.addBiomeVariant(LOTRBiomeVariant.FOREST_PINE, 0.3F);
      this.addBiomeVariant(LOTRBiomeVariant.FOREST_ASPEN, 0.3F);
      this.decorator.biomeGemFactor = 1.0F;
      this.decorator.addOre(new WorldGenMinable(LOTRMod.oreMithril, 6), 0.25F, 0, 16);
      this.decorator.addOre(new WorldGenMinable(LOTRMod.oreGlowstone, 4), 8.0F, 0, 48);
      this.decorator.flowersPerChunk = 1;
      this.decorator.grassPerChunk = 3;
      this.decorator.doubleGrassPerChunk = 1;
      this.decorator.generateWater = false;
      this.decorator.addTree(LOTRTreeType.SPRUCE, 400);
      this.decorator.addTree(LOTRTreeType.SPRUCE_THIN, 400);
      this.decorator.addTree(LOTRTreeType.LARCH, 300);
      this.decorator.addTree(LOTRTreeType.SPRUCE_MEGA, 100);
      this.decorator.addTree(LOTRTreeType.SPRUCE_MEGA_THIN, 20);
      this.decorator.addTree(LOTRTreeType.FIR, 500);
      this.decorator.addTree(LOTRTreeType.PINE, 500);
      this.registerMountainsFlowers();
      this.biomeColors.setSky(12241873);
      this.decorator.generateOrcDungeon = true;
      this.decorator.addRandomStructure(new LOTRWorldGenStoneRuin.STONE(1, 4), 500);
      this.decorator.addRandomStructure(new LOTRWorldGenStoneRuin.DWARVEN(1, 4), 1500);
      this.decorator.addRandomStructure(new LOTRWorldGenRuinedDwarvenTower(false), 1000);
      this.decorator.addRandomStructure(new LOTRWorldGenSmallStoneRuin(false), 400);
      this.setBanditChance(LOTREventSpawner.EventChance.NEVER);
   }

   public LOTRAchievement getBiomeAchievement() {
      return LOTRAchievement.enterMistyMountains;
   }

   public LOTRWaypoint.Region getBiomeWaypoints() {
      return LOTRWaypoint.Region.MISTY_MOUNTAINS;
   }

   public LOTRMusicRegion.Sub getBiomeMusic() {
      return LOTRMusicRegion.MISTY_MOUNTAINS.getSubregion("mistyMountains");
   }

   public boolean getEnableRiver() {
      return false;
   }

   protected void generateMountainTerrain(World world, Random random, Block[] blocks, byte[] meta, int i, int k, int xzIndex, int ySize, int height, int rockDepth, LOTRBiomeVariant variant) {
      int snowHeight = 120 - rockDepth;
      int stoneHeight = snowHeight - 30;

      for(int j = ySize - 1; j >= stoneHeight; --j) {
         int index = xzIndex * ySize + j;
         Block block = blocks[index];
         if (j >= snowHeight && block == this.field_76752_A) {
            blocks[index] = Blocks.field_150433_aE;
            meta[index] = 0;
         } else if (block == this.field_76752_A || block == this.field_76753_B) {
            blocks[index] = Blocks.field_150348_b;
            meta[index] = 0;
         }
      }

   }

   public void func_76728_a(World world, Random random, int i, int k) {
      super.func_76728_a(world, random, i, k);

      for(int count = 0; count < 2; ++count) {
         int i1 = i + random.nextInt(16) + 8;
         int k1 = k + random.nextInt(16) + 8;
         int j1 = world.func_72976_f(i1, k1);
         if (j1 < 100) {
            this.decorator.genTree(world, random, i1, j1, k1);
         }
      }

   }

   public float getChanceToSpawnAnimals() {
      return 0.0F;
   }

   public float getTreeIncreaseChance() {
      return 0.05F;
   }
}
