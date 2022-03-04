package lotr.common.world.biome;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.awt.Color;
import java.util.Random;
import lotr.common.LOTRMod;
import lotr.common.entity.animal.LOTREntityBear;
import lotr.common.entity.animal.LOTREntityDeer;
import lotr.common.entity.animal.LOTREntityElk;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.feature.LOTRWorldGenBoulder;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.world.spawning.LOTRBiomeSpawnList;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.world.spawning.LOTRSpawnList;
import lotr.common.world.structure2.LOTRWorldGenRuinedHouse;
import lotr.common.world.structure2.LOTRWorldGenSmallStoneRuin;
import net.minecraft.block.Block;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase.SpawnListEntry;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

public class LOTRBiomeGenTundra extends LOTRBiome {
   protected static NoiseGeneratorPerlin noiseDirt = new NoiseGeneratorPerlin(new Random(47684796930956L), 1);
   protected static NoiseGeneratorPerlin noiseStone = new NoiseGeneratorPerlin(new Random(8894086030764L), 1);
   protected static NoiseGeneratorPerlin noiseSnow = new NoiseGeneratorPerlin(new Random(2490309256000602L), 1);
   private WorldGenerator boulderGen;

   public LOTRBiomeGenTundra(int i, boolean major) {
      super(i, major);
      this.boulderGen = new LOTRWorldGenBoulder(Blocks.field_150348_b, 0, 1, 3);
      this.func_76742_b();
      this.field_76762_K.clear();
      this.field_76762_K.add(new SpawnListEntry(EntityWolf.class, 10, 4, 8));
      this.field_76762_K.add(new SpawnListEntry(LOTREntityDeer.class, 10, 4, 6));
      this.field_76762_K.add(new SpawnListEntry(LOTREntityElk.class, 10, 4, 6));
      this.field_76762_K.add(new SpawnListEntry(LOTREntityBear.class, 10, 1, 4));
      this.spawnableLOTRAmbientList.clear();
      this.npcSpawnList.clear();
      LOTRBiomeSpawnList.FactionContainer var10000 = this.npcSpawnList.newFactionList(100);
      LOTRBiomeSpawnList.SpawnListContainer[] var10001 = new LOTRBiomeSpawnList.SpawnListContainer[2];
      LOTRBiomeSpawnList var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GUNDABAD_ORCS, 10).setSpawnChance(1000);
      var10004 = this.npcSpawnList;
      var10001[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GUNDABAD_WARGS, 5).setSpawnChance(1000);
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(10);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[1];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.RANGERS_NORTH, 10).setSpawnChance(5000);
      var10000.add(var10001);
      this.variantChance = 0.2F;
      this.addBiomeVariant(LOTRBiomeVariant.FOREST_LIGHT);
      this.addBiomeVariant(LOTRBiomeVariant.STEPPE);
      this.addBiomeVariant(LOTRBiomeVariant.STEPPE_BARREN);
      this.addBiomeVariant(LOTRBiomeVariant.HILLS);
      this.addBiomeVariant(LOTRBiomeVariant.DEADFOREST_OAK);
      this.addBiomeVariant(LOTRBiomeVariant.DEADFOREST_SPRUCE);
      this.addBiomeVariant(LOTRBiomeVariant.DEADFOREST_OAK_SPRUCE);
      this.decorator.treesPerChunk = 0;
      this.decorator.flowersPerChunk = 2;
      this.decorator.grassPerChunk = 4;
      this.decorator.doubleGrassPerChunk = 1;
      this.decorator.generateOrcDungeon = true;
      this.decorator.addTree(LOTRTreeType.SPRUCE_THIN, 100);
      this.decorator.addTree(LOTRTreeType.SPRUCE_DEAD, 100);
      this.decorator.addTree(LOTRTreeType.PINE, 100);
      this.decorator.addTree(LOTRTreeType.FIR, 100);
      this.decorator.addTree(LOTRTreeType.MAPLE, 10);
      this.decorator.addTree(LOTRTreeType.BEECH, 10);
      this.registerTaigaFlowers();
      this.decorator.addRandomStructure(new LOTRWorldGenRuinedHouse(false), 1500);
      this.decorator.addRandomStructure(new LOTRWorldGenSmallStoneRuin(false), 500);
      this.setBanditChance(LOTREventSpawner.EventChance.BANDIT_UNCOMMON);
   }

   public LOTRWaypoint.Region getBiomeWaypoints() {
      return LOTRWaypoint.Region.FORODWAITH;
   }

   public LOTRMusicRegion.Sub getBiomeMusic() {
      return LOTRMusicRegion.FORODWAITH.getSubregion("tundra");
   }

   public void generateBiomeTerrain(World world, Random random, Block[] blocks, byte[] meta, int i, int k, double stoneNoise, int height, LOTRBiomeVariant variant) {
      Block topBlock_pre = this.field_76752_A;
      int topBlockMeta_pre = this.topBlockMeta;
      Block fillerBlock_pre = this.field_76753_B;
      int fillerBlockMeta_pre = this.fillerBlockMeta;
      double d1 = noiseDirt.func_151601_a((double)i * 0.07D, (double)k * 0.07D);
      double d2 = noiseDirt.func_151601_a((double)i * 0.3D, (double)k * 0.3D);
      double d3 = noiseStone.func_151601_a((double)i * 0.07D, (double)k * 0.07D);
      double d4 = noiseStone.func_151601_a((double)i * 0.3D, (double)k * 0.3D);
      if (d3 + d4 > 1.2D) {
         this.field_76752_A = Blocks.field_150348_b;
         this.topBlockMeta = 0;
         this.field_76753_B = this.field_76752_A;
         this.fillerBlockMeta = this.topBlockMeta;
      } else if (d1 + d2 > 0.8D) {
         this.field_76752_A = Blocks.field_150346_d;
         this.topBlockMeta = 1;
      }

      super.generateBiomeTerrain(world, random, blocks, meta, i, k, stoneNoise, height, variant);
      this.field_76752_A = topBlock_pre;
      this.topBlockMeta = topBlockMeta_pre;
      this.field_76753_B = fillerBlock_pre;
      this.fillerBlockMeta = fillerBlockMeta_pre;
   }

   public void func_76728_a(World world, Random random, int i, int k) {
      super.func_76728_a(world, random, i, k);
      int boulders;
      int l;
      int i1;
      int k1;
      if (random.nextInt(2) == 0) {
         boulders = i + random.nextInt(16) + 8;
         l = k + random.nextInt(16) + 8;
         i1 = world.func_72976_f(boulders, l);
         k1 = 4 + random.nextInt(20);

         for(int l = 0; l < k1; ++l) {
            int i2 = boulders + MathHelper.func_76136_a(random, -4, 4);
            int k2 = l + MathHelper.func_76136_a(random, -4, 4);
            int j2 = i1 + MathHelper.func_76136_a(random, -1, 1);
            Block below = world.func_147439_a(i2, j2 - 1, k2);
            Block block = world.func_147439_a(i2, j2, k2);
            if (below.canSustainPlant(world, i2, j2 - 1, k2, ForgeDirection.UP, (IPlantable)Blocks.field_150345_g) && !block.func_149688_o().func_76224_d() && block.isReplaceable(world, i2, j2, k2)) {
               Block leafBlock = Blocks.field_150362_t;
               int leafMeta = 1;
               if (random.nextInt(3) == 0) {
                  leafBlock = LOTRMod.leaves3;
                  leafMeta = 0;
               } else if (random.nextInt(3) == 0) {
                  leafBlock = LOTRMod.leaves2;
                  leafMeta = 1;
               }

               world.func_147465_d(i2, j2, k2, (Block)leafBlock, leafMeta | 4, 2);
            }
         }
      }

      if (random.nextInt(40) == 0) {
         boulders = 1 + random.nextInt(4);

         for(l = 0; l < boulders; ++l) {
            i1 = i + random.nextInt(16) + 8;
            k1 = k + random.nextInt(16) + 8;
            this.boulderGen.func_76484_a(world, random, i1, world.func_72976_f(i1, k1), k1);
         }
      }

   }

   public float getTreeIncreaseChance() {
      return 0.04F;
   }

   @SideOnly(Side.CLIENT)
   public int func_150558_b(int i, int j, int k) {
      int color1 = 10708034;
      int color2 = 13747522;
      double d1 = biomeTerrainNoise.func_151601_a((double)i * 0.002D, (double)k * 0.002D);
      double d2 = biomeTerrainNoise.func_151601_a((double)i * 0.04D, (double)k * 0.04D);
      d2 *= 0.4D;
      float noise = (float)MathHelper.func_151237_a(d1 + d2, -2.0D, 2.0D);
      noise += 2.0F;
      noise /= 4.0F;
      float[] rgb1 = (new Color(color1)).getColorComponents((float[])null);
      float[] rgb2 = (new Color(color2)).getColorComponents((float[])null);
      float[] rgbNoise = new float[rgb1.length];

      for(int l = 0; l < rgbNoise.length; ++l) {
         rgbNoise[l] = rgb1[l] + (rgb2[l] - rgb1[l]) * noise;
      }

      return (new Color(rgbNoise[0], rgbNoise[1], rgbNoise[2])).getRGB();
   }

   public static boolean isTundraSnowy(int i, int k) {
      double d1 = noiseSnow.func_151601_a((double)i * 0.002D, (double)k * 0.002D);
      double d2 = noiseSnow.func_151601_a((double)i * 0.05D, (double)k * 0.05D);
      double d3 = noiseSnow.func_151601_a((double)i * 0.3D, (double)k * 0.3D);
      d2 *= 0.3D;
      d3 *= 0.3D;
      return d1 + d2 + d3 > 0.8D;
   }

   public float getChanceToSpawnAnimals() {
      return 0.02F;
   }
}
