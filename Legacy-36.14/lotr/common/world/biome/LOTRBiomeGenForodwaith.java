package lotr.common.world.biome;

import java.util.Random;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRMod;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.feature.LOTRWorldGenBoulder;
import lotr.common.world.feature.LOTRWorldGenMirkOak;
import lotr.common.world.feature.LOTRWorldGenStalactites;
import lotr.common.world.map.LOTRFixedStructures;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.world.map.LOTRWorldGenUtumnoEntrance;
import lotr.common.world.spawning.LOTRBiomeSpawnList;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.world.spawning.LOTRSpawnList;
import lotr.common.world.structure2.LOTRWorldGenRuinedHouse;
import lotr.common.world.structure2.LOTRWorldGenStoneRuin;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRBiomeGenForodwaith extends LOTRBiome {
   private WorldGenerator boulderGen;
   private LOTRWorldGenStalactites stalactiteIceGen;

   public LOTRBiomeGenForodwaith(int i, boolean major) {
      super(i, major);
      this.boulderGen = new LOTRWorldGenBoulder(Blocks.field_150348_b, 0, 1, 2);
      this.stalactiteIceGen = new LOTRWorldGenStalactites(LOTRMod.stalactiteIce);
      this.func_76742_b();
      this.field_76752_A = Blocks.field_150433_aE;
      this.field_76762_K.clear();
      this.field_76755_L.clear();
      this.field_82914_M.clear();
      this.spawnableLOTRAmbientList.clear();
      LOTRBiomeSpawnList.FactionContainer var10000 = this.npcSpawnList.newFactionList(100);
      LOTRBiomeSpawnList.SpawnListContainer[] var10001 = new LOTRBiomeSpawnList.SpawnListContainer[1];
      LOTRBiomeSpawnList var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.SNOW_TROLLS, 10).setSpawnChance(100000);
      var10000.add(var10001);
      this.decorator.addSoil(new WorldGenMinable(Blocks.field_150403_cj, 16), 40.0F, 32, 256);
      this.decorator.treesPerChunk = 0;
      this.decorator.flowersPerChunk = 0;
      this.decorator.grassPerChunk = 0;
      this.decorator.generateWater = false;
      this.biomeColors.setSky(10069160);
      this.decorator.addRandomStructure(new LOTRWorldGenRuinedHouse(false), 4000);
      this.decorator.addRandomStructure(new LOTRWorldGenStoneRuin.STONE(1, 5), 4000);
      this.setBanditChance(LOTREventSpawner.EventChance.NEVER);
   }

   public LOTRAchievement getBiomeAchievement() {
      return LOTRAchievement.enterForodwaith;
   }

   public LOTRWaypoint.Region getBiomeWaypoints() {
      return LOTRWaypoint.Region.FORODWAITH;
   }

   public LOTRMusicRegion.Sub getBiomeMusic() {
      return LOTRMusicRegion.FORODWAITH.getSubregion("forodwaith");
   }

   public boolean getEnableRiver() {
      return false;
   }

   public void func_76728_a(World world, Random random, int i, int k) {
      super.func_76728_a(world, random, i, k);
      if (LOTRFixedStructures.UTUMNO_ENTRANCE.isAt(world, i, k)) {
         (new LOTRWorldGenUtumnoEntrance()).func_76484_a(world, random, i, world.func_72976_f(i, k), k);
      }

      int boulders;
      int i1;
      int k1;
      int j1;
      if (random.nextInt(32) == 0) {
         boulders = 1 + random.nextInt(5);

         for(i1 = 0; i1 < boulders; ++i1) {
            k1 = i + random.nextInt(16) + 8;
            j1 = k + random.nextInt(16) + 8;
            this.boulderGen.func_76484_a(world, random, k1, world.func_72976_f(k1, j1), j1);
         }
      }

      for(boulders = 0; boulders < 2; ++boulders) {
         i1 = i + random.nextInt(16) + 8;
         k1 = random.nextInt(60);
         j1 = k + random.nextInt(16) + 8;
         this.stalactiteIceGen.func_76484_a(world, random, i1, k1, j1);
      }

      if (random.nextInt(20000) == 0) {
         LOTRWorldGenMirkOak tree = ((LOTRWorldGenMirkOak)LOTRTreeType.RED_OAK_WEIRWOOD.create(false, random)).disableRestrictions();
         i1 = i + random.nextInt(16) + 8;
         k1 = k + random.nextInt(16) + 8;
         j1 = world.func_72976_f(i1, k1);
         tree.func_76484_a(world, random, i1, j1, k1);
      }

   }

   public float getTreeIncreaseChance() {
      return 0.0F;
   }
}
