package lotr.common.world.biome;

import java.util.Random;
import lotr.common.LOTRAchievement;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.feature.LOTRWorldGenBoulder;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.world.spawning.LOTRBiomeSpawnList;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.world.spawning.LOTRSpawnList;
import lotr.common.world.structure.LOTRWorldGenGondorRuin;
import lotr.common.world.structure.LOTRWorldGenGondorRuins;
import lotr.common.world.structure.LOTRWorldGenRuinedGondorTower;
import lotr.common.world.structure2.LOTRWorldGenCorsairCamp;
import lotr.common.world.structure2.LOTRWorldGenGondorObelisk;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRBiomeGenTolfalas extends LOTRBiome {
   private WorldGenerator boulderGen;

   public LOTRBiomeGenTolfalas(int i, boolean major) {
      super(i, major);
      this.boulderGen = new LOTRWorldGenBoulder(Blocks.field_150348_b, 0, 1, 6);
      LOTRBiomeSpawnList.FactionContainer var10000 = this.npcSpawnList.newFactionList(100);
      LOTRBiomeSpawnList.SpawnListContainer[] var10001 = new LOTRBiomeSpawnList.SpawnListContainer[2];
      LOTRBiomeSpawnList var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.CORSAIRS, 10).setSpawnChance(200);
      var10004 = this.npcSpawnList;
      var10001[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.CORSAIRS, 10).setConquestOnly();
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(0);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[3];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GONDOR_SOLDIERS, 10).setSpawnChance(100);
      var10004 = this.npcSpawnList;
      var10001[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.PELARGIR_SOLDIERS, 2).setSpawnChance(100).setConquestThreshold(50.0F);
      var10004 = this.npcSpawnList;
      var10001[2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.DOL_AMROTH_SOLDIERS, 2).setSpawnChance(100).setConquestThreshold(50.0F);
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(0);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[2];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORDOR_ORCS, 10);
      var10004 = this.npcSpawnList;
      var10001[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.BLACK_URUKS, 2).setConquestThreshold(50.0F);
      var10000.add(var10001);
      this.decorator.treesPerChunk = 1;
      this.decorator.flowersPerChunk = 0;
      this.decorator.doubleFlowersPerChunk = 0;
      this.decorator.grassPerChunk = 10;
      this.decorator.doubleGrassPerChunk = 6;
      this.decorator.generateAthelas = true;
      this.decorator.addTree(LOTRTreeType.OAK_DEAD, 2000);
      this.decorator.addRandomStructure(new LOTRWorldGenGondorRuins(), 1000);
      this.decorator.addRandomStructure(new LOTRWorldGenRuinedGondorTower(false), 1500);
      this.decorator.addRandomStructure(new LOTRWorldGenGondorObelisk(false), 1500);
      this.decorator.addRandomStructure(new LOTRWorldGenGondorRuin(false), 1500);
      this.decorator.addRandomStructure(new LOTRWorldGenCorsairCamp(false), 100);
      this.setBanditChance(LOTREventSpawner.EventChance.BANDIT_COMMON);
   }

   public LOTRAchievement getBiomeAchievement() {
      return LOTRAchievement.enterTolfalas;
   }

   public LOTRWaypoint.Region getBiomeWaypoints() {
      return LOTRWaypoint.Region.TOLFALAS;
   }

   public LOTRMusicRegion.Sub getBiomeMusic() {
      return LOTRMusicRegion.GONDOR.getSubregion("tolfalas");
   }

   protected void generateMountainTerrain(World world, Random random, Block[] blocks, byte[] meta, int i, int k, int xzIndex, int ySize, int height, int rockDepth, LOTRBiomeVariant variant) {
      int stoneHeight = 90 - rockDepth;

      for(int j = ySize - 1; j >= stoneHeight; --j) {
         int index = xzIndex * ySize + j;
         Block block = blocks[index];
         if (block == this.field_76752_A || block == this.field_76753_B) {
            blocks[index] = Blocks.field_150348_b;
            meta[index] = 0;
         }
      }

   }

   public void func_76728_a(World world, Random random, int i, int k) {
      super.func_76728_a(world, random, i, k);
      if (random.nextInt(4) == 0) {
         for(int l = 0; l < 3; ++l) {
            int i1 = i + random.nextInt(16) + 8;
            int k1 = k + random.nextInt(16) + 8;
            this.boulderGen.func_76484_a(world, random, i1, world.func_72976_f(i1, k1), k1);
         }
      }

   }

   public float getChanceToSpawnAnimals() {
      return 0.25F;
   }
}
