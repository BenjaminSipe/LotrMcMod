package lotr.common.world.biome;

import java.util.Random;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRMod;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.spawning.LOTRBiomeSpawnList;
import lotr.common.world.spawning.LOTRSpawnList;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraft.world.gen.feature.WorldGenFlowers;
import net.minecraft.world.gen.feature.WorldGenMinable;

public class LOTRBiomeGenMorgulVale extends LOTRBiomeGenMordor {
   private NoiseGeneratorPerlin noiseDirt = new NoiseGeneratorPerlin(new Random(1860286702860L), 1);
   private NoiseGeneratorPerlin noiseGravel = new NoiseGeneratorPerlin(new Random(8903486028509023054L), 1);
   private NoiseGeneratorPerlin noiseRock = new NoiseGeneratorPerlin(new Random(769385178389572607L), 1);

   public LOTRBiomeGenMorgulVale(int i, boolean major) {
      super(i, major);
      this.npcSpawnList.clear();
      LOTRBiomeSpawnList.FactionContainer var10000 = this.npcSpawnList.newFactionList(100);
      LOTRBiomeSpawnList.SpawnListContainer[] var10001 = new LOTRBiomeSpawnList.SpawnListContainer[5];
      LOTRBiomeSpawnList var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORDOR_ORCS, 15).setSpawnChance(30);
      var10004 = this.npcSpawnList;
      var10001[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORDOR_WARGS, 2).setSpawnChance(30);
      var10004 = this.npcSpawnList;
      var10001[2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.BLACK_URUKS, 2).setConquestThreshold(50.0F);
      var10004 = this.npcSpawnList;
      var10001[3] = LOTRBiomeSpawnList.entry(LOTRSpawnList.BLACK_URUKS, 2).setConquestThreshold(100.0F);
      var10004 = this.npcSpawnList;
      var10001[4] = LOTRBiomeSpawnList.entry(LOTRSpawnList.OLOG_HAI, 2).setConquestThreshold(200.0F);
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(0);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[1];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ROHIRRIM_WARRIORS, 10);
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(0);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[2];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GONDOR_SOLDIERS, 10);
      var10004 = this.npcSpawnList;
      var10001[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.RANGERS_ITHILIEN, 3);
      var10000.add(var10001);
      this.npcSpawnList.conquestGainRate = 0.5F;
      this.field_76752_A = Blocks.field_150349_c;
      this.field_76753_B = Blocks.field_150346_d;
      this.decorator.addOre(new WorldGenMinable(LOTRMod.oreGulduril, 1, 8, LOTRMod.rock), 10.0F, 0, 60);
      this.decorator.treesPerChunk = 0;
      this.decorator.flowersPerChunk = 1;
      this.decorator.grassPerChunk = 3;
      this.decorator.dryReedChance = 1.0F;
      this.decorator.addTree(LOTRTreeType.OAK, 200);
      this.decorator.addTree(LOTRTreeType.OAK_DESERT, 500);
      this.decorator.addTree(LOTRTreeType.OAK_DEAD, 500);
      this.decorator.addTree(LOTRTreeType.CHARRED, 500);
      this.flowers.clear();
      this.addFlower(LOTRMod.morgulFlower, 0, 20);
      this.biomeColors.setGrass(6054733);
      this.biomeColors.setFoliage(4475954);
      this.biomeColors.setSky(7835270);
      this.biomeColors.setClouds(5860197);
      this.biomeColors.setFog(6318950);
      this.biomeColors.setWater(3563598);
   }

   public boolean isGorgoroth() {
      return false;
   }

   protected boolean hasMordorSoils() {
      return false;
   }

   public LOTRAchievement getBiomeAchievement() {
      return LOTRAchievement.enterMorgulVale;
   }

   public LOTRMusicRegion.Sub getBiomeMusic() {
      return LOTRMusicRegion.MORDOR.getSubregion("morgulVale");
   }

   public void generateBiomeTerrain(World world, Random random, Block[] blocks, byte[] meta, int i, int k, double stoneNoise, int height, LOTRBiomeVariant variant) {
      Block topBlock_pre = this.field_76752_A;
      int topBlockMeta_pre = this.topBlockMeta;
      Block fillerBlock_pre = this.field_76753_B;
      int fillerBlockMeta_pre = this.fillerBlockMeta;
      double d1 = this.noiseDirt.func_151601_a((double)i * 0.06D, (double)k * 0.06D);
      double d2 = this.noiseDirt.func_151601_a((double)i * 0.3D, (double)k * 0.3D);
      double d3 = this.noiseGravel.func_151601_a((double)i * 0.06D, (double)k * 0.06D);
      double d4 = this.noiseGravel.func_151601_a((double)i * 0.3D, (double)k * 0.3D);
      double d5 = this.noiseRock.func_151601_a((double)i * 0.06D, (double)k * 0.06D);
      double d6 = this.noiseRock.func_151601_a((double)i * 0.3D, (double)k * 0.3D);
      if (d5 + d6 > 1.1D) {
         this.field_76752_A = LOTRMod.rock;
         this.topBlockMeta = 0;
         this.field_76753_B = this.field_76752_A;
         this.fillerBlockMeta = this.topBlockMeta;
      } else if (d3 + d4 > 0.7D) {
         this.field_76752_A = LOTRMod.mordorGravel;
         this.topBlockMeta = 0;
         this.field_76753_B = this.field_76752_A;
         this.fillerBlockMeta = this.topBlockMeta;
      } else if (d1 + d2 > 0.7D) {
         this.field_76752_A = LOTRMod.mordorDirt;
         this.topBlockMeta = 1;
         this.field_76753_B = this.field_76752_A;
         this.fillerBlockMeta = this.topBlockMeta;
      }

      super.generateBiomeTerrain(world, random, blocks, meta, i, k, stoneNoise, height, variant);
      this.field_76752_A = topBlock_pre;
      this.topBlockMeta = topBlockMeta_pre;
      this.field_76753_B = fillerBlock_pre;
      this.fillerBlockMeta = fillerBlockMeta_pre;
   }

   public void func_76728_a(World world, Random random, int i, int k) {
      super.func_76728_a(world, random, i, k);

      for(int l = 0; l < 4; ++l) {
         int i1 = i + random.nextInt(16) + 8;
         int k1 = k + random.nextInt(16) + 8;
         int j1 = world.func_72976_f(i1, k1);
         boolean foundWater = false;

         for(int a = 0; a < 20; ++a) {
            int range = 8;
            int i2 = i1 + MathHelper.func_76136_a(random, -range, range);
            int j2 = j1 + MathHelper.func_76136_a(random, -range, range);
            int k2 = k1 + MathHelper.func_76136_a(random, -range, range);
            Block block = world.func_147439_a(i2, j2, k2);
            if (block.func_149688_o() == Material.field_151586_h) {
               foundWater = true;
               break;
            }
         }

         if (foundWater) {
            WorldGenFlowers flowerGen = new WorldGenFlowers(LOTRMod.morgulFlower);
            flowerGen.func_76484_a(world, random, i1, j1, k1);
         }
      }

   }

   public float getTreeIncreaseChance() {
      return 0.2F;
   }
}
