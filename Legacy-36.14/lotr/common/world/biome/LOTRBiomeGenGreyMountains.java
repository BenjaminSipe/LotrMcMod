package lotr.common.world.biome;

import java.util.Random;
import lotr.common.LOTRAchievement;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.world.spawning.LOTRBiomeSpawnList;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.world.spawning.LOTRInvasions;
import lotr.common.world.spawning.LOTRSpawnList;
import lotr.common.world.structure2.LOTRWorldGenRuinedDwarvenTower;
import lotr.common.world.structure2.LOTRWorldGenSmallStoneRuin;
import lotr.common.world.structure2.LOTRWorldGenStoneRuin;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRBiomeGenGreyMountains extends LOTRBiome {
   public LOTRBiomeGenGreyMountains(int i, boolean major) {
      super(i, major);
      this.field_76762_K.clear();
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
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[3];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.DOL_GULDUR_ORCS, 10);
      var10004 = this.npcSpawnList;
      var10001[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MIRKWOOD_SPIDERS, 2).setConquestThreshold(50.0F);
      var10004 = this.npcSpawnList;
      var10001[2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MIRK_TROLLS, 1).setConquestThreshold(200.0F);
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(0);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[1];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.DWARVES, 10);
      var10000.add(var10001);
      this.addBiomeVariantSet(LOTRBiomeVariant.SET_MOUNTAINS);
      this.addBiomeVariant(LOTRBiomeVariant.FOREST_LARCH, 0.2F);
      this.addBiomeVariant(LOTRBiomeVariant.FOREST_PINE, 0.2F);
      this.addBiomeVariant(LOTRBiomeVariant.FOREST_ASPEN, 0.2F);
      this.addBiomeVariant(LOTRBiomeVariant.FOREST_MAPLE, 0.2F);
      this.decorator.biomeGemFactor = 1.0F;
      this.decorator.flowersPerChunk = 0;
      this.decorator.grassPerChunk = 0;
      this.decorator.doubleGrassPerChunk = 0;
      this.decorator.addTree(LOTRTreeType.SPRUCE, 400);
      this.decorator.addTree(LOTRTreeType.SPRUCE_THIN, 400);
      this.decorator.addTree(LOTRTreeType.SPRUCE_MEGA, 50);
      this.decorator.addTree(LOTRTreeType.SPRUCE_MEGA_THIN, 10);
      this.decorator.addTree(LOTRTreeType.LARCH, 500);
      this.decorator.addTree(LOTRTreeType.FIR, 500);
      this.decorator.addTree(LOTRTreeType.PINE, 500);
      this.registerMountainsFlowers();
      this.biomeColors.setSky(10862798);
      this.decorator.generateOrcDungeon = true;
      this.decorator.addRandomStructure(new LOTRWorldGenStoneRuin.STONE(1, 4), 500);
      this.decorator.addRandomStructure(new LOTRWorldGenStoneRuin.DWARVEN(1, 4), 1000);
      this.decorator.addRandomStructure(new LOTRWorldGenRuinedDwarvenTower(false), 1000);
      this.decorator.addRandomStructure(new LOTRWorldGenSmallStoneRuin(false), 500);
      this.setBanditChance(LOTREventSpawner.EventChance.BANDIT_RARE);
      this.invasionSpawns.addInvasion(LOTRInvasions.GUNDABAD, LOTREventSpawner.EventChance.RARE);
      this.invasionSpawns.addInvasion(LOTRInvasions.GUNDABAD_WARG, LOTREventSpawner.EventChance.UNCOMMON);
      this.invasionSpawns.addInvasion(LOTRInvasions.DWARF, LOTREventSpawner.EventChance.RARE);
   }

   public LOTRAchievement getBiomeAchievement() {
      return LOTRAchievement.enterGreyMountains;
   }

   public LOTRWaypoint.Region getBiomeWaypoints() {
      return LOTRWaypoint.Region.GREY_MOUNTAINS;
   }

   public LOTRMusicRegion.Sub getBiomeMusic() {
      return LOTRMusicRegion.GREY_MOUNTAINS.getSubregion("greyMountains");
   }

   public boolean getEnableRiver() {
      return false;
   }

   protected void generateMountainTerrain(World world, Random random, Block[] blocks, byte[] meta, int i, int k, int xzIndex, int ySize, int height, int rockDepth, LOTRBiomeVariant variant) {
      int snowHeight = 150 - rockDepth;
      int stoneHeight = snowHeight - 40;

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

      int count;
      int i1;
      int j1;
      int k1;
      for(count = 0; count < 3; ++count) {
         i1 = i + random.nextInt(16) + 8;
         j1 = k + random.nextInt(16) + 8;
         k1 = world.func_72976_f(i1, j1);
         if (k1 < 100) {
            this.decorator.genTree(world, random, i1, k1, j1);
         }
      }

      for(count = 0; count < 2; ++count) {
         i1 = i + random.nextInt(16) + 8;
         j1 = random.nextInt(128);
         k1 = k + random.nextInt(16) + 8;
         if (j1 < 100) {
            this.func_76730_b(random).func_76484_a(world, random, i1, j1, k1);
         }
      }

   }

   public float getChanceToSpawnAnimals() {
      return 0.0F;
   }

   public float getTreeIncreaseChance() {
      return 0.0F;
   }
}
