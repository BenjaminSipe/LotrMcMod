package lotr.common.world.biome;

import java.util.Random;
import lotr.common.LOTRAchievement;
import lotr.common.entity.animal.LOTREntityGemsbok;
import lotr.common.entity.animal.LOTREntityLion;
import lotr.common.entity.animal.LOTREntityLioness;
import lotr.common.entity.animal.LOTREntityRhino;
import lotr.common.entity.animal.LOTREntityZebra;
import lotr.common.entity.npc.LOTREntityBanditHarad;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.spawning.LOTRBiomeSpawnList;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.world.spawning.LOTRInvasions;
import lotr.common.world.spawning.LOTRSpawnList;
import lotr.common.world.structure2.LOTRWorldGenCorsairCamp;
import lotr.common.world.structure2.LOTRWorldGenCorsairCove;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase.SpawnListEntry;
import net.minecraft.world.gen.NoiseGeneratorPerlin;

public class LOTRBiomeGenFarHaradCoast extends LOTRBiomeGenFarHaradSavannah {
   protected static NoiseGeneratorPerlin noiseGrass = new NoiseGeneratorPerlin(new Random(75796728360672L), 1);
   protected static NoiseGeneratorPerlin noiseDirt = new NoiseGeneratorPerlin(new Random(63275968906L), 1);
   protected static NoiseGeneratorPerlin noiseSand = new NoiseGeneratorPerlin(new Random(127425276902L), 1);
   protected static NoiseGeneratorPerlin noiseSandstone = new NoiseGeneratorPerlin(new Random(267215026920L), 1);

   public LOTRBiomeGenFarHaradCoast(int i, boolean major) {
      super(i, major);
      this.field_76762_K.clear();
      this.field_76762_K.add(new SpawnListEntry(LOTREntityLion.class, 4, 2, 4));
      this.field_76762_K.add(new SpawnListEntry(LOTREntityLioness.class, 4, 2, 4));
      this.field_76762_K.add(new SpawnListEntry(LOTREntityZebra.class, 8, 4, 8));
      this.field_76762_K.add(new SpawnListEntry(LOTREntityRhino.class, 8, 4, 4));
      this.field_76762_K.add(new SpawnListEntry(LOTREntityGemsbok.class, 8, 4, 8));
      this.npcSpawnList.clear();
      LOTRBiomeSpawnList.FactionContainer var10000 = this.npcSpawnList.newFactionList(100);
      LOTRBiomeSpawnList.SpawnListContainer[] var10001 = new LOTRBiomeSpawnList.SpawnListContainer[1];
      LOTRBiomeSpawnList var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.CORSAIRS, 10).setSpawnChance(5000);
      var10000.add(var10001);
      this.populatedSpawnList.clear();
      this.field_76752_A = Blocks.field_150348_b;
      this.topBlockMeta = 0;
      this.field_76753_B = this.field_76752_A;
      this.fillerBlockMeta = this.topBlockMeta;
      this.biomeTerrain.setXZScale(30.0D);
      this.clearBiomeVariants();
      this.decorator.addTree(LOTRTreeType.PALM, 4000);
      this.decorator.treesPerChunk = 1;
      this.decorator.clearRandomStructures();
      this.decorator.addRandomStructure(new LOTRWorldGenCorsairCove(false), 10);
      this.decorator.addRandomStructure(new LOTRWorldGenCorsairCamp(false), 100);
      this.clearTravellingTraders();
      this.setBanditChance(LOTREventSpawner.EventChance.BANDIT_COMMON);
      this.setBanditEntityClass(LOTREntityBanditHarad.class);
      this.invasionSpawns.clearInvasions();
      this.invasionSpawns.addInvasion(LOTRInvasions.NEAR_HARAD_CORSAIR, LOTREventSpawner.EventChance.COMMON);
   }

   public LOTRAchievement getBiomeAchievement() {
      return LOTRAchievement.enterCorsairCoasts;
   }

   public void generateBiomeTerrain(World world, Random random, Block[] blocks, byte[] meta, int i, int k, double stoneNoise, int height, LOTRBiomeVariant variant) {
      Block topBlock_pre = this.field_76752_A;
      int topBlockMeta_pre = this.topBlockMeta;
      Block fillerBlock_pre = this.field_76753_B;
      int fillerBlockMeta_pre = this.fillerBlockMeta;
      double d1 = noiseGrass.func_151601_a((double)i * 0.06D, (double)k * 0.06D);
      double d2 = noiseGrass.func_151601_a((double)i * 0.47D, (double)k * 0.47D);
      double d3 = noiseDirt.func_151601_a((double)i * 0.06D, (double)k * 0.06D);
      double d4 = noiseDirt.func_151601_a((double)i * 0.47D, (double)k * 0.47D);
      double d5 = noiseSand.func_151601_a((double)i * 0.06D, (double)k * 0.06D);
      double d6 = noiseSand.func_151601_a((double)i * 0.47D, (double)k * 0.47D);
      double d7 = noiseSandstone.func_151601_a((double)i * 0.06D, (double)k * 0.06D);
      double d8 = noiseSandstone.func_151601_a((double)i * 0.47D, (double)k * 0.47D);
      if (d7 + d8 > 0.8D) {
         this.field_76752_A = Blocks.field_150322_A;
         this.topBlockMeta = 0;
         this.field_76753_B = Blocks.field_150354_m;
         this.fillerBlockMeta = 0;
      } else if (d5 + d6 > 0.6D) {
         this.field_76752_A = Blocks.field_150354_m;
         this.topBlockMeta = 0;
         this.field_76753_B = this.field_76752_A;
         this.fillerBlockMeta = this.topBlockMeta;
      } else if (d3 + d4 > 0.5D) {
         this.field_76752_A = Blocks.field_150346_d;
         this.topBlockMeta = 1;
         this.field_76753_B = this.field_76752_A;
         this.fillerBlockMeta = this.topBlockMeta;
      } else if (d1 + d2 > 0.4D) {
         this.field_76752_A = Blocks.field_150349_c;
         this.topBlockMeta = 0;
         this.field_76753_B = Blocks.field_150346_d;
         this.fillerBlockMeta = 0;
      }

      super.generateBiomeTerrain(world, random, blocks, meta, i, k, stoneNoise, height, variant);
      this.field_76752_A = topBlock_pre;
      this.topBlockMeta = topBlockMeta_pre;
      this.field_76753_B = fillerBlock_pre;
      this.fillerBlockMeta = fillerBlockMeta_pre;
   }

   public float getChanceToSpawnAnimals() {
      return 0.1F;
   }
}
