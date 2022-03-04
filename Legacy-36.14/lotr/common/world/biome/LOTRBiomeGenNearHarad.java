package lotr.common.world.biome;

import java.util.Random;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRMod;
import lotr.common.entity.animal.LOTREntityCamel;
import lotr.common.entity.animal.LOTREntityDesertScorpion;
import lotr.common.entity.npc.LOTREntityBanditHarad;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.feature.LOTRWorldGenBoulder;
import lotr.common.world.map.LOTRRoadType;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.world.spawning.LOTRBiomeSpawnList;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.world.spawning.LOTRSpawnList;
import lotr.common.world.structure.LOTRWorldGenHaradObelisk;
import lotr.common.world.structure2.LOTRWorldGenHaradPyramid;
import lotr.common.world.structure2.LOTRWorldGenHaradRuinedFort;
import lotr.common.world.structure2.LOTRWorldGenMumakSkeleton;
import lotr.common.world.structure2.LOTRWorldGenStoneRuin;
import lotr.common.world.village.LOTRVillageGenHaradNomad;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase.SpawnListEntry;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraft.world.gen.feature.WorldGenCactus;
import net.minecraft.world.gen.feature.WorldGenDeadBush;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRBiomeGenNearHarad extends LOTRBiome {
   private static NoiseGeneratorPerlin noiseAridGrass = new NoiseGeneratorPerlin(new Random(62926025827260L), 1);
   private WorldGenerator boulderGen;
   private WorldGenerator boulderGenSandstone;

   public LOTRBiomeGenNearHarad(int i, boolean major) {
      super(i, major);
      this.boulderGen = new LOTRWorldGenBoulder(Blocks.field_150348_b, 0, 1, 3);
      this.boulderGenSandstone = new LOTRWorldGenBoulder(Blocks.field_150322_A, 0, 1, 3);
      this.func_76745_m();
      this.field_76752_A = Blocks.field_150354_m;
      this.field_76753_B = Blocks.field_150354_m;
      this.field_76762_K.clear();
      this.field_76762_K.add(new SpawnListEntry(LOTREntityCamel.class, 10, 2, 6));
      this.spawnableLOTRAmbientList.clear();
      this.field_76761_J.add(new SpawnListEntry(LOTREntityDesertScorpion.class, 10, 4, 4));
      LOTRBiomeSpawnList.FactionContainer var10000 = this.npcSpawnList.newFactionList(100);
      LOTRBiomeSpawnList.SpawnListContainer[] var10001 = new LOTRBiomeSpawnList.SpawnListContainer[2];
      LOTRBiomeSpawnList var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.NOMADS, 20).setSpawnChance(10000);
      var10004 = this.npcSpawnList;
      var10001[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.NOMAD_WARRIORS, 15).setSpawnChance(10000);
      var10000.add(var10001);
      this.variantChance = 0.8F;
      this.addBiomeVariant(LOTRBiomeVariant.DUNES, 0.5F);
      this.addBiomeVariant(LOTRBiomeVariant.STEPPE);
      this.addBiomeVariant(LOTRBiomeVariant.HILLS);
      this.addBiomeVariant(LOTRBiomeVariant.BOULDERS_RED);
      this.addBiomeVariant(LOTRBiomeVariant.DEADFOREST_OAK);
      this.addBiomeVariant(LOTRBiomeVariant.SCRUBLAND_SAND);
      this.decorator.addOre(new WorldGenMinable(Blocks.field_150369_x, 6), 1.0F, 0, 48);
      this.decorator.grassPerChunk = 0;
      this.decorator.doubleGrassPerChunk = 0;
      this.decorator.cactiPerChunk = 0;
      this.decorator.deadBushPerChunk = 0;
      this.decorator.addTree(LOTRTreeType.OAK_DEAD, 800);
      this.decorator.addTree(LOTRTreeType.OAK_DESERT, 200);
      this.registerHaradFlowers();
      this.biomeColors.setFog(16180681);
      this.decorator.addRandomStructure(new LOTRWorldGenHaradObelisk(false), 3000);
      this.decorator.addRandomStructure(new LOTRWorldGenHaradPyramid(false), 3000);
      this.decorator.addRandomStructure(new LOTRWorldGenStoneRuin.NEAR_HARAD(1, 4), 2000);
      this.decorator.addRandomStructure(new LOTRWorldGenMumakSkeleton(false), 1500);
      this.decorator.addRandomStructure(new LOTRWorldGenHaradRuinedFort(false), 3000);
      this.decorator.addVillage(new LOTRVillageGenHaradNomad(this, 0.05F));
      this.clearTravellingTraders();
      this.setBanditChance(LOTREventSpawner.EventChance.BANDIT_RARE);
      this.setBanditEntityClass(LOTREntityBanditHarad.class);
   }

   public LOTRAchievement getBiomeAchievement() {
      return LOTRAchievement.enterNearHarad;
   }

   public LOTRWaypoint.Region getBiomeWaypoints() {
      return LOTRWaypoint.Region.HARAD_DESERT;
   }

   public LOTRMusicRegion.Sub getBiomeMusic() {
      return LOTRMusicRegion.NEAR_HARAD.getSubregion("desert");
   }

   public boolean getEnableRiver() {
      return false;
   }

   public LOTRRoadType getRoadBlock() {
      return LOTRRoadType.HARAD.setRepair(0.5F);
   }

   public void func_76728_a(World world, Random random, int i, int k) {
      int preGrasses = this.decorator.grassPerChunk;
      int grasses = preGrasses;
      double d1 = noiseAridGrass.func_151601_a((double)i * 0.002D, (double)k * 0.002D);
      if (d1 > 0.5D) {
         grasses = preGrasses + 1;
      }

      this.decorator.grassPerChunk = grasses;
      super.func_76728_a(world, random, i, k);
      this.decorator.grassPerChunk = preGrasses;
      int trees;
      int l;
      int i1;
      if (random.nextInt(50) == 0) {
         trees = i + random.nextInt(16) + 8;
         l = k + random.nextInt(16) + 8;
         i1 = world.func_72976_f(trees, l);
         (new WorldGenCactus()).func_76484_a(world, random, trees, i1, l);
      }

      if (random.nextInt(16) == 0) {
         trees = i + random.nextInt(16) + 8;
         l = k + random.nextInt(16) + 8;
         i1 = world.func_72976_f(trees, l);
         (new WorldGenDeadBush(Blocks.field_150330_I)).func_76484_a(world, random, trees, i1, l);
      }

      int k1;
      int j1;
      if (random.nextInt(120) == 0) {
         trees = 1 + random.nextInt(4);

         for(l = 0; l < trees; ++l) {
            i1 = i + random.nextInt(16) + 8;
            k1 = k + random.nextInt(16) + 8;
            j1 = world.func_72976_f(i1, k1);
            if (random.nextBoolean()) {
               this.boulderGen.func_76484_a(world, random, i1, j1, k1);
            } else {
               this.boulderGenSandstone.func_76484_a(world, random, i1, j1, k1);
            }
         }
      }

      if (random.nextInt(2000) == 0) {
         trees = 1 + random.nextInt(4);

         for(l = 0; l < trees; ++l) {
            i1 = i + random.nextInt(8) + 8;
            k1 = k + random.nextInt(8) + 8;
            j1 = world.func_72976_f(i1, k1);
            this.decorator.genTree(world, random, i1, j1, k1);
         }
      }

   }

   public float getTreeIncreaseChance() {
      return 5.0E-4F;
   }

   public LOTRBiome.GrassBlockAndMeta getRandomGrass(Random random) {
      return new LOTRBiome.GrassBlockAndMeta(LOTRMod.aridGrass, 0);
   }

   public float getChanceToSpawnAnimals() {
      return 0.05F;
   }

   public interface ImmuneToHeat {
   }
}
