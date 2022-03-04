package lotr.common.world.biome;

import java.util.Random;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRMod;
import lotr.common.world.feature.LOTRWorldGenBoulder;
import lotr.common.world.feature.LOTRWorldGenGrassPatch;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.world.spawning.LOTRBiomeSpawnList;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.world.spawning.LOTRInvasions;
import lotr.common.world.spawning.LOTRSpawnList;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRBiomeGenEmynMuil extends LOTRBiome {
   private WorldGenerator boulderGenSmall;
   private WorldGenerator boulderGenLarge;
   private WorldGenerator clayBoulderGenSmall;
   private WorldGenerator clayBoulderGenLarge;
   private WorldGenerator grassPatchGen;

   public LOTRBiomeGenEmynMuil(int i, boolean major) {
      super(i, major);
      this.boulderGenSmall = new LOTRWorldGenBoulder(Blocks.field_150348_b, 0, 1, 4);
      this.boulderGenLarge = (new LOTRWorldGenBoulder(Blocks.field_150348_b, 0, 5, 8)).setHeightCheck(6);
      this.clayBoulderGenSmall = new LOTRWorldGenBoulder(Blocks.field_150405_ch, 0, 1, 4);
      this.clayBoulderGenLarge = (new LOTRWorldGenBoulder(Blocks.field_150405_ch, 0, 5, 10)).setHeightCheck(6);
      this.grassPatchGen = new LOTRWorldGenGrassPatch();
      this.field_76752_A = Blocks.field_150348_b;
      this.field_76753_B = Blocks.field_150348_b;
      this.field_76762_K.clear();
      this.spawnableLOTRAmbientList.clear();
      LOTRBiomeSpawnList.FactionContainer var10000 = this.npcSpawnList.newFactionList(100);
      LOTRBiomeSpawnList.SpawnListContainer[] var10001 = new LOTRBiomeSpawnList.SpawnListContainer[2];
      LOTRBiomeSpawnList var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORDOR_ORCS, 10);
      var10004 = this.npcSpawnList;
      var10001[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORDOR_WARGS, 1);
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(0);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[2];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GONDOR_SOLDIERS, 1);
      var10004 = this.npcSpawnList;
      var10001[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.RANGERS_ITHILIEN, 10);
      var10000.add(var10001);
      this.decorator.flowersPerChunk = 1;
      this.decorator.grassPerChunk = 10;
      this.decorator.doubleGrassPerChunk = 2;
      this.registerMountainsFlowers();
      this.biomeColors.setGrass(9539937);
      this.biomeColors.setSky(10000788);
      this.decorator.generateOrcDungeon = true;
      this.setBanditChance(LOTREventSpawner.EventChance.BANDIT_COMMON);
      this.invasionSpawns.addInvasion(LOTRInvasions.MORDOR, LOTREventSpawner.EventChance.RARE);
      this.invasionSpawns.addInvasion(LOTRInvasions.MORDOR_WARG, LOTREventSpawner.EventChance.RARE);
   }

   public LOTRAchievement getBiomeAchievement() {
      return LOTRAchievement.enterEmynMuil;
   }

   public LOTRWaypoint.Region getBiomeWaypoints() {
      return LOTRWaypoint.Region.EMYN_MUIL;
   }

   public LOTRMusicRegion.Sub getBiomeMusic() {
      return LOTRMusicRegion.BROWN_LANDS.getSubregion("emynMuil");
   }

   public void func_76728_a(World world, Random random, int i, int k) {
      super.func_76728_a(world, random, i, k);

      int l;
      int i1;
      int l1;
      for(l = 0; l < 20; ++l) {
         i1 = i + random.nextInt(16) + 8;
         l1 = k + random.nextInt(16) + 8;
         if (random.nextInt(5) == 0) {
            this.clayBoulderGenSmall.func_76484_a(world, random, i1, world.func_72976_f(i1, l1), l1);
         } else {
            this.boulderGenSmall.func_76484_a(world, random, i1, world.func_72976_f(i1, l1), l1);
         }
      }

      for(l = 0; l < 20; ++l) {
         i1 = i + random.nextInt(16) + 8;
         l1 = k + random.nextInt(16) + 8;
         if (random.nextInt(5) == 0) {
            this.clayBoulderGenLarge.func_76484_a(world, random, i1, world.func_72976_f(i1, l1), l1);
         } else {
            this.boulderGenLarge.func_76484_a(world, random, i1, world.func_72976_f(i1, l1), l1);
         }
      }

      for(l = 0; l < 10; ++l) {
         Block block = Blocks.field_150348_b;
         if (random.nextInt(5) == 0) {
            block = Blocks.field_150405_ch;
         }

         for(l1 = 0; l1 < 10; ++l1) {
            int i1 = i + random.nextInt(16) + 8;
            int k1 = k + random.nextInt(16) + 8;
            int j1 = world.func_72976_f(i1, k1);
            if (world.func_147439_a(i1, j1 - 1, k1) == block) {
               int height = j1 + random.nextInt(4);

               for(int j2 = j1; j2 < height && !LOTRMod.isOpaque(world, i1, j2, k1); ++j2) {
                  world.func_147465_d(i1, j2, k1, block, 0, 3);
               }
            }
         }
      }

      for(l = 0; l < 3; ++l) {
         i1 = i + random.nextInt(16) + 8;
         l1 = k + random.nextInt(16) + 8;
         this.grassPatchGen.func_76484_a(world, random, i1, world.func_72976_f(i1, l1), l1);
      }

   }
}
