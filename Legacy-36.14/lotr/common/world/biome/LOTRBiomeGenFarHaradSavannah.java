package lotr.common.world.biome;

import java.util.List;
import java.util.Random;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityBanditHarad;
import lotr.common.entity.npc.LOTREntityNomadMerchant;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.LOTRWorldGenBoulder;
import lotr.common.world.feature.LOTRWorldGenSand;
import lotr.common.world.feature.LOTRWorldGenYams;
import lotr.common.world.spawning.LOTRBiomeSpawnList;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.world.spawning.LOTRInvasions;
import lotr.common.world.spawning.LOTRSpawnList;
import lotr.common.world.structure2.LOTRWorldGenMoredainCamp;
import lotr.common.world.structure2.LOTRWorldGenMoredainVillage;
import lotr.common.world.structure2.LOTRWorldGenStoneRuin;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraft.world.gen.feature.WorldGenDoublePlant;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRBiomeGenFarHaradSavannah extends LOTRBiomeGenFarHarad {
   private static NoiseGeneratorPerlin populatedNoise = new NoiseGeneratorPerlin(new Random(100L), 1);
   protected LOTRBiomeSpawnList populatedSpawnList = new LOTRBiomeSpawnList(this);
   private WorldGenerator boulderGen;

   public LOTRBiomeGenFarHaradSavannah(int i, boolean major) {
      super(i, major);
      this.boulderGen = new LOTRWorldGenBoulder(Blocks.field_150348_b, 0, 1, 3);
      this.npcSpawnList.clear();
      LOTRBiomeSpawnList.FactionContainer var10000 = this.npcSpawnList.newFactionList(100, 0.0F);
      LOTRBiomeSpawnList.SpawnListContainer[] var10001 = new LOTRBiomeSpawnList.SpawnListContainer[2];
      LOTRBiomeSpawnList var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORWAITH, 10).setSpawnChance(10000);
      var10004 = this.npcSpawnList;
      var10001[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORWAITH_WARRIORS, 5).setSpawnChance(10000);
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(0);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[2];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORWAITH, 5);
      var10004 = this.npcSpawnList;
      var10001[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORWAITH_WARRIORS, 10);
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(0);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[1];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GONDOR_SOLDIERS, 10);
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(0);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[2];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORDOR_ORCS, 10);
      var10004 = this.npcSpawnList;
      var10001[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.BLACK_URUKS, 1).setConquestThreshold(50.0F);
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(0);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[3];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.SOUTHRON_WARRIORS, 10);
      var10004 = this.npcSpawnList;
      var10001[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.NOMAD_WARRIORS, 5);
      var10004 = this.npcSpawnList;
      var10001[2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GULF_WARRIORS, 10);
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(0);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[4];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.TAURETHRIM_WARRIORS, 10);
      var10004 = this.npcSpawnList;
      var10001[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.TAURETHRIM, 5);
      var10004 = this.npcSpawnList;
      var10001[2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.TAURETHRIM, 5).setConquestThreshold(100.0F);
      var10004 = this.npcSpawnList;
      var10001[3] = LOTRBiomeSpawnList.entry(LOTRSpawnList.TAURETHRIM, 5).setConquestThreshold(200.0F);
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(0);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[1];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.HALF_TROLLS, 10);
      var10000.add(var10001);
      var10000 = this.populatedSpawnList.newFactionList(0);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[2];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORWAITH, 10);
      var10004 = this.npcSpawnList;
      var10001[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORWAITH_WARRIORS, 5);
      var10000.add(var10001);
      this.variantChance = 0.3F;
      this.addBiomeVariant(LOTRBiomeVariant.FLOWERS);
      this.addBiomeVariant(LOTRBiomeVariant.FOREST);
      this.addBiomeVariant(LOTRBiomeVariant.FOREST_LIGHT);
      this.addBiomeVariant(LOTRBiomeVariant.STEPPE);
      this.addBiomeVariant(LOTRBiomeVariant.STEPPE_BARREN);
      this.addBiomeVariant(LOTRBiomeVariant.HILLS);
      this.addBiomeVariant(LOTRBiomeVariant.HILLS_FOREST);
      this.addBiomeVariant(LOTRBiomeVariant.SHRUBLAND_OAK);
      this.addBiomeVariant(LOTRBiomeVariant.SAVANNAH_BAOBAB, 3.0F);
      this.addBiomeVariant(LOTRBiomeVariant.SCRUBLAND, 2.0F);
      this.addBiomeVariant(LOTRBiomeVariant.HILLS_SCRUBLAND);
      this.addBiomeVariant(LOTRBiomeVariant.WASTELAND);
      this.decorator.addSoil(new WorldGenMinable(LOTRMod.redClay, 32, Blocks.field_150346_d), 40.0F, 0, 80);
      this.decorator.setTreeCluster(3, 60);
      this.decorator.clayGen = new LOTRWorldGenSand(LOTRMod.redClay, 5, 1);
      this.decorator.clayPerChunk = 4;
      this.decorator.grassPerChunk = 10;
      this.decorator.doubleGrassPerChunk = 12;
      this.decorator.flowersPerChunk = 3;
      this.decorator.doubleFlowersPerChunk = 1;
      this.decorator.melonPerChunk = 0.01F;
      this.decorator.addRandomStructure(new LOTRWorldGenMoredainVillage(false), 200);
      this.decorator.addRandomStructure(new LOTRWorldGenMoredainCamp(false), 500);
      this.decorator.addRandomStructure(new LOTRWorldGenStoneRuin.TAUREDAIN(1, 2), 5000);
      this.registerTravellingTrader(LOTREntityNomadMerchant.class);
      this.setBanditChance(LOTREventSpawner.EventChance.BANDIT_RARE);
      this.setBanditEntityClass(LOTREntityBanditHarad.class);
      this.invasionSpawns.addInvasion(LOTRInvasions.MOREDAIN, LOTREventSpawner.EventChance.UNCOMMON);
      this.invasionSpawns.addInvasion(LOTRInvasions.TAUREDAIN, LOTREventSpawner.EventChance.RARE);
   }

   public void addBiomeF3Info(List info, World world, LOTRBiomeVariant variant, int i, int j, int k) {
      super.addBiomeF3Info(info, world, variant, i, j, k);
      boolean populated = isBiomePopulated(i, j, k);
      info.add("HaradPopulated: " + populated);
   }

   public static boolean isBiomePopulated(int i, int j, int k) {
      double scale = 8.0E-4D;
      double d = populatedNoise.func_151601_a((double)i * scale, (double)k * scale);
      return d > 0.5D;
   }

   public LOTRBiomeSpawnList getNPCSpawnList(World world, Random random, int i, int j, int k, LOTRBiomeVariant variant) {
      return isBiomePopulated(i, j, k) ? this.populatedSpawnList : super.getNPCSpawnList(world, random, i, j, k, variant);
   }

   public LOTRAchievement getBiomeAchievement() {
      return LOTRAchievement.enterFarHaradSavannah;
   }

   public LOTRMusicRegion.Sub getBiomeMusic() {
      return LOTRMusicRegion.FAR_HARAD.getSubregion("savannah");
   }

   public void func_76728_a(World world, Random random, int i, int k) {
      super.func_76728_a(world, random, i, k);
      int i1;
      int j1;
      int i1;
      if (random.nextInt(32) == 0) {
         i1 = 1 + random.nextInt(4);

         for(j1 = 0; j1 < i1; ++j1) {
            i1 = i + random.nextInt(16) + 8;
            int k1 = k + random.nextInt(16) + 8;
            this.boulderGen.func_76484_a(world, random, i1, world.func_72976_f(i1, k1), k1);
         }
      }

      if (random.nextInt(6) == 0) {
         i1 = i + random.nextInt(16) + 8;
         j1 = random.nextInt(128);
         i1 = k + random.nextInt(16) + 8;
         (new LOTRWorldGenYams()).func_76484_a(world, random, i1, j1, i1);
      }

   }

   public float getTreeIncreaseChance() {
      return 0.1F;
   }

   public WorldGenerator getRandomWorldGenForDoubleFlower(Random random) {
      if (random.nextInt(6) == 0) {
         WorldGenDoublePlant gen = new WorldGenDoublePlant();
         gen.func_150548_a(0);
         return gen;
      } else {
         return super.getRandomWorldGenForDoubleFlower(random);
      }
   }

   public float getChanceToSpawnAnimals() {
      return 0.75F;
   }

   public int spawnCountMultiplier() {
      return 3;
   }
}
