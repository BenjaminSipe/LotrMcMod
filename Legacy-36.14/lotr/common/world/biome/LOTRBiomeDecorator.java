package lotr.common.world.biome;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import lotr.common.LOTRMod;
import lotr.common.world.LOTRChunkProvider;
import lotr.common.world.LOTRWorldChunkManager;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.feature.LOTRWorldGenBerryBush;
import lotr.common.world.feature.LOTRWorldGenBiomeFlowers;
import lotr.common.world.feature.LOTRWorldGenBushes;
import lotr.common.world.feature.LOTRWorldGenCaveCobwebs;
import lotr.common.world.feature.LOTRWorldGenCorn;
import lotr.common.world.feature.LOTRWorldGenFallenLeaves;
import lotr.common.world.feature.LOTRWorldGenLogs;
import lotr.common.world.feature.LOTRWorldGenReeds;
import lotr.common.world.feature.LOTRWorldGenSand;
import lotr.common.world.feature.LOTRWorldGenStalactites;
import lotr.common.world.feature.LOTRWorldGenStreams;
import lotr.common.world.feature.LOTRWorldGenSurfaceGravel;
import lotr.common.world.feature.LOTRWorldGenTrollHoard;
import lotr.common.world.map.LOTRRoads;
import lotr.common.world.structure.LOTRWorldGenMarshHut;
import lotr.common.world.structure.LOTRWorldGenOrcDungeon;
import lotr.common.world.structure.LOTRWorldGenStructureBase;
import lotr.common.world.structure2.LOTRWorldGenGrukHouse;
import lotr.common.world.structure2.LOTRWorldGenStructureBase2;
import lotr.common.world.structure2.LOTRWorldGenTicketBooth;
import lotr.common.world.village.LOTRVillageGen;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.WeightedRandom.Item;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenCactus;
import net.minecraft.world.gen.feature.WorldGenDeadBush;
import net.minecraft.world.gen.feature.WorldGenFlowers;
import net.minecraft.world.gen.feature.WorldGenMelon;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenPumpkin;
import net.minecraft.world.gen.feature.WorldGenReed;
import net.minecraft.world.gen.feature.WorldGenVines;
import net.minecraft.world.gen.feature.WorldGenWaterlily;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRBiomeDecorator {
   private World worldObj;
   private Random rand;
   private int chunkX;
   private int chunkZ;
   private LOTRBiome biome;
   private List biomeSoils = new ArrayList();
   private List biomeOres = new ArrayList();
   private List biomeGems = new ArrayList();
   public float biomeOreFactor = 1.0F;
   public float biomeGemFactor = 0.5F;
   protected WorldGenerator clayGen;
   private WorldGenerator sandGen;
   private WorldGenerator whiteSandGen;
   private WorldGenerator quagmireGen;
   private WorldGenerator surfaceGravelGen;
   private WorldGenerator flowerGen;
   private WorldGenerator logGen;
   private WorldGenerator mushroomBrownGen;
   private WorldGenerator mushroomRedGen;
   private WorldGenerator caneGen;
   private WorldGenerator reedGen;
   private WorldGenerator dryReedGen;
   private WorldGenerator cornGen;
   private WorldGenerator pumpkinGen;
   private WorldGenerator waterlilyGen;
   private WorldGenerator cobwebGen;
   private WorldGenerator stalactiteGen;
   private WorldGenerator vinesGen;
   private WorldGenerator cactusGen;
   private WorldGenerator melonGen;
   public int sandPerChunk;
   public int clayPerChunk;
   public int quagmirePerChunk;
   public int treesPerChunk;
   public int willowPerChunk;
   public int logsPerChunk;
   public int vinesPerChunk;
   public int flowersPerChunk;
   public int doubleFlowersPerChunk;
   public int grassPerChunk;
   public int doubleGrassPerChunk;
   public boolean enableFern;
   public boolean enableSpecialGrasses;
   public int deadBushPerChunk;
   public int waterlilyPerChunk;
   public int mushroomsPerChunk;
   public boolean enableRandomMushroom;
   public int canePerChunk;
   public int reedPerChunk;
   public float dryReedChance;
   public int cornPerChunk;
   public int cactiPerChunk;
   public float melonPerChunk;
   public boolean generateWater;
   public boolean generateLava;
   public boolean generateCobwebs;
   public boolean generateAthelas;
   public boolean whiteSand;
   private int treeClusterSize;
   private int treeClusterChance;
   private WorldGenerator orcDungeonGen;
   private WorldGenerator trollHoardGen;
   public boolean generateOrcDungeon;
   public boolean generateTrollHoard;
   private List treeTypes;
   private Random structureRand;
   private List randomStructures;
   private List villages;

   public void addSoil(WorldGenerator gen, float f, int min, int max) {
      this.biomeSoils.add(new LOTRBiomeDecorator.OreGenerant(gen, f, min, max));
   }

   public void addOre(WorldGenerator gen, float f, int min, int max) {
      this.biomeOres.add(new LOTRBiomeDecorator.OreGenerant(gen, f, min, max));
   }

   public void addGem(WorldGenerator gen, float f, int min, int max) {
      this.biomeGems.add(new LOTRBiomeDecorator.OreGenerant(gen, f, min, max));
   }

   public void clearOres() {
      this.biomeSoils.clear();
      this.biomeOres.clear();
      this.biomeGems.clear();
   }

   private void addDefaultOres() {
      this.addSoil(new WorldGenMinable(Blocks.field_150346_d, 32), 40.0F, 0, 256);
      this.addSoil(new WorldGenMinable(Blocks.field_150351_n, 32), 20.0F, 0, 256);
      this.addOre(new WorldGenMinable(Blocks.field_150365_q, 16), 40.0F, 0, 128);
      this.addOre(new WorldGenMinable(LOTRMod.oreCopper, 8), 16.0F, 0, 128);
      this.addOre(new WorldGenMinable(LOTRMod.oreTin, 8), 16.0F, 0, 128);
      this.addOre(new WorldGenMinable(Blocks.field_150366_p, 8), 20.0F, 0, 64);
      this.addOre(new WorldGenMinable(LOTRMod.oreSulfur, 8), 2.0F, 0, 64);
      this.addOre(new WorldGenMinable(LOTRMod.oreSaltpeter, 8), 2.0F, 0, 64);
      this.addOre(new WorldGenMinable(LOTRMod.oreSalt, 12), 2.0F, 0, 64);
      this.addOre(new WorldGenMinable(Blocks.field_150352_o, 8), 2.0F, 0, 32);
      this.addOre(new WorldGenMinable(LOTRMod.oreSilver, 8), 3.0F, 0, 32);
      this.addGem(new WorldGenMinable(LOTRMod.oreGem, 1, 6, Blocks.field_150348_b), 2.0F, 0, 64);
      this.addGem(new WorldGenMinable(LOTRMod.oreGem, 0, 6, Blocks.field_150348_b), 2.0F, 0, 64);
      this.addGem(new WorldGenMinable(LOTRMod.oreGem, 4, 5, Blocks.field_150348_b), 1.5F, 0, 48);
      this.addGem(new WorldGenMinable(LOTRMod.oreGem, 6, 5, Blocks.field_150348_b), 1.5F, 0, 48);
      this.addGem(new WorldGenMinable(LOTRMod.oreGem, 2, 4, Blocks.field_150348_b), 1.0F, 0, 32);
      this.addGem(new WorldGenMinable(LOTRMod.oreGem, 3, 4, Blocks.field_150348_b), 1.0F, 0, 32);
      this.addGem(new WorldGenMinable(LOTRMod.oreGem, 7, 4, Blocks.field_150348_b), 0.75F, 0, 24);
      this.addGem(new WorldGenMinable(LOTRMod.oreGem, 5, 4, Blocks.field_150348_b), 0.5F, 0, 16);
   }

   public LOTRBiomeDecorator(LOTRBiome lotrbiome) {
      this.clayGen = new LOTRWorldGenSand(Blocks.field_150435_aG, 5, 1);
      this.sandGen = new LOTRWorldGenSand(Blocks.field_150354_m, 7, 2);
      this.whiteSandGen = new LOTRWorldGenSand(LOTRMod.whiteSand, 7, 2);
      this.quagmireGen = new LOTRWorldGenSand(LOTRMod.quagmire, 7, 2);
      this.surfaceGravelGen = new LOTRWorldGenSurfaceGravel();
      this.flowerGen = new LOTRWorldGenBiomeFlowers();
      this.logGen = new LOTRWorldGenLogs();
      this.mushroomBrownGen = new WorldGenFlowers(Blocks.field_150338_P);
      this.mushroomRedGen = new WorldGenFlowers(Blocks.field_150337_Q);
      this.caneGen = new WorldGenReed();
      this.reedGen = new LOTRWorldGenReeds(LOTRMod.reeds);
      this.dryReedGen = new LOTRWorldGenReeds(LOTRMod.driedReeds);
      this.cornGen = new LOTRWorldGenCorn();
      this.pumpkinGen = new WorldGenPumpkin();
      this.waterlilyGen = new WorldGenWaterlily();
      this.cobwebGen = new LOTRWorldGenCaveCobwebs();
      this.stalactiteGen = new LOTRWorldGenStalactites();
      this.vinesGen = new WorldGenVines();
      this.cactusGen = new WorldGenCactus();
      this.melonGen = new WorldGenMelon();
      this.sandPerChunk = 4;
      this.clayPerChunk = 3;
      this.quagmirePerChunk = 0;
      this.treesPerChunk = 0;
      this.willowPerChunk = 0;
      this.logsPerChunk = 0;
      this.vinesPerChunk = 0;
      this.flowersPerChunk = 2;
      this.doubleFlowersPerChunk = 0;
      this.grassPerChunk = 1;
      this.doubleGrassPerChunk = 0;
      this.enableFern = false;
      this.enableSpecialGrasses = true;
      this.deadBushPerChunk = 0;
      this.waterlilyPerChunk = 0;
      this.mushroomsPerChunk = 0;
      this.enableRandomMushroom = true;
      this.canePerChunk = 0;
      this.reedPerChunk = 1;
      this.dryReedChance = 0.1F;
      this.cornPerChunk = 0;
      this.cactiPerChunk = 0;
      this.melonPerChunk = 0.0F;
      this.generateWater = true;
      this.generateLava = true;
      this.generateCobwebs = true;
      this.generateAthelas = false;
      this.whiteSand = false;
      this.treeClusterChance = -1;
      this.orcDungeonGen = new LOTRWorldGenOrcDungeon(false);
      this.trollHoardGen = new LOTRWorldGenTrollHoard();
      this.generateOrcDungeon = false;
      this.generateTrollHoard = false;
      this.treeTypes = new ArrayList();
      this.structureRand = new Random();
      this.randomStructures = new ArrayList();
      this.villages = new ArrayList();
      this.biome = lotrbiome;
      this.addDefaultOres();
   }

   public void addTree(LOTRTreeType type, int weight) {
      this.treeTypes.add(new LOTRTreeType.WeightedTreeType(type, weight));
   }

   public void clearTrees() {
      this.treeTypes.clear();
   }

   public LOTRTreeType getRandomTree(Random random) {
      if (this.treeTypes.isEmpty()) {
         return LOTRTreeType.OAK;
      } else {
         Item item = WeightedRandom.func_76271_a(random, this.treeTypes);
         return ((LOTRTreeType.WeightedTreeType)item).treeType;
      }
   }

   public LOTRTreeType getRandomTreeForVariant(Random random, LOTRBiomeVariant variant) {
      if (variant.treeTypes.isEmpty()) {
         return this.getRandomTree(random);
      } else {
         float f = variant.variantTreeChance;
         return random.nextFloat() < f ? variant.getRandomTree(random) : this.getRandomTree(random);
      }
   }

   public void genTree(World world, Random random, int i, int j, int k) {
      WorldGenerator treeGen = this.biome.getTreeGen(world, random, i, j, k);
      treeGen.func_76484_a(world, random, i, j, k);
   }

   public void setTreeCluster(int size, int chance) {
      this.treeClusterSize = size;
      this.treeClusterChance = chance;
   }

   public void resetTreeCluster() {
      this.setTreeCluster(0, -1);
   }

   public void addRandomStructure(WorldGenerator structure, int chunkChance) {
      this.randomStructures.add(new LOTRBiomeDecorator.RandomStructure(structure, chunkChance));
   }

   public void clearRandomStructures() {
      this.randomStructures.clear();
   }

   public void addVillage(LOTRVillageGen village) {
      this.villages.add(village);
   }

   public void clearVillages() {
      this.villages.clear();
   }

   public void checkForVillages(World world, int i, int k, LOTRChunkProvider.ChunkFlags chunkFlags) {
      chunkFlags.isVillage = false;
      chunkFlags.isFlatVillage = false;
      Iterator var5 = this.villages.iterator();

      while(true) {
         List instances;
         do {
            if (!var5.hasNext()) {
               return;
            }

            LOTRVillageGen village = (LOTRVillageGen)var5.next();
            instances = village.getNearbyVillagesAtPosition(world, i, k);
         } while(instances.isEmpty());

         chunkFlags.isVillage = true;
         Iterator var8 = instances.iterator();

         while(var8.hasNext()) {
            LOTRVillageGen.AbstractInstance inst = (LOTRVillageGen.AbstractInstance)var8.next();
            if (inst.isFlat()) {
               chunkFlags.isFlatVillage = true;
            }
         }
      }
   }

   public boolean anyFixedVillagesAt(World world, int i, int k) {
      Iterator var4 = this.villages.iterator();

      LOTRVillageGen village;
      do {
         if (!var4.hasNext()) {
            return false;
         }

         village = (LOTRVillageGen)var4.next();
      } while(!village.anyFixedVillagesAt(world, i, k));

      return true;
   }

   public int getVariantTreesPerChunk(LOTRBiomeVariant variant) {
      int trees = this.treesPerChunk;
      if (variant.treeFactor > 1.0F) {
         trees = Math.max(trees, 1);
      }

      trees = Math.round((float)trees * variant.treeFactor);
      return trees;
   }

   public void decorate(World world, Random random, int i, int k) {
      this.worldObj = world;
      this.rand = random;
      this.chunkX = i;
      this.chunkZ = k;
      this.decorate();
   }

   private void decorate() {
      LOTRBiomeVariant biomeVariant = ((LOTRWorldChunkManager)this.worldObj.func_72959_q()).getBiomeVariantAt(this.chunkX + 8, this.chunkZ + 8);
      this.generateOres();
      biomeVariant.decorateVariant(this.worldObj, this.rand, this.chunkX, this.chunkZ, this.biome);
      int trees;
      int k;
      int cluster;
      if (this.rand.nextBoolean() && this.generateCobwebs) {
         trees = this.chunkX + this.rand.nextInt(16) + 8;
         k = this.rand.nextInt(60);
         cluster = this.chunkZ + this.rand.nextInt(16) + 8;
         this.cobwebGen.func_76484_a(this.worldObj, this.rand, trees, k, cluster);
      }

      int flowers;
      for(trees = 0; trees < 3; ++trees) {
         k = this.chunkX + this.rand.nextInt(16) + 8;
         cluster = this.rand.nextInt(60);
         flowers = this.chunkZ + this.rand.nextInt(16) + 8;
         this.stalactiteGen.func_76484_a(this.worldObj, this.rand, k, cluster, flowers);
      }

      for(trees = 0; trees < this.quagmirePerChunk; ++trees) {
         k = this.chunkX + this.rand.nextInt(16) + 8;
         cluster = this.chunkZ + this.rand.nextInt(16) + 8;
         this.quagmireGen.func_76484_a(this.worldObj, this.rand, k, this.worldObj.func_72825_h(k, cluster), cluster);
      }

      for(trees = 0; trees < this.sandPerChunk; ++trees) {
         k = this.chunkX + this.rand.nextInt(16) + 8;
         cluster = this.chunkZ + this.rand.nextInt(16) + 8;
         WorldGenerator biomeSandGenerator = this.sandGen;
         if (this.whiteSand) {
            biomeSandGenerator = this.whiteSandGen;
         }

         biomeSandGenerator.func_76484_a(this.worldObj, this.rand, k, this.worldObj.func_72825_h(k, cluster), cluster);
      }

      for(trees = 0; trees < this.clayPerChunk; ++trees) {
         k = this.chunkX + this.rand.nextInt(16) + 8;
         cluster = this.chunkZ + this.rand.nextInt(16) + 8;
         this.clayGen.func_76484_a(this.worldObj, this.rand, k, this.worldObj.func_72825_h(k, cluster), cluster);
      }

      if (this.rand.nextInt(60) == 0) {
         trees = this.chunkX + this.rand.nextInt(16) + 8;
         k = this.chunkZ + this.rand.nextInt(16) + 8;
         this.surfaceGravelGen.func_76484_a(this.worldObj, this.rand, trees, 0, k);
      }

      int grasses;
      int doubleGrasses;
      int boulders;
      if (!biomeVariant.disableStructures && Math.abs(this.chunkX) > 32 && Math.abs(this.chunkZ) > 32) {
         long seed = (long)(this.chunkX * 1879267) ^ (long)this.chunkZ * 67209689L;
         seed = seed * seed * 5829687L + seed * 2876L;
         this.structureRand.setSeed(seed);
         boolean roadNear = LOTRRoads.isRoadNear(this.chunkX + 8, this.chunkZ + 8, 16) >= 0.0F;
         Iterator var19;
         if (!roadNear) {
            var19 = this.randomStructures.iterator();

            while(var19.hasNext()) {
               LOTRBiomeDecorator.RandomStructure randomstructure = (LOTRBiomeDecorator.RandomStructure)var19.next();
               if (this.structureRand.nextInt(randomstructure.chunkChance) == 0) {
                  grasses = this.chunkX + this.rand.nextInt(16) + 8;
                  doubleGrasses = this.chunkZ + this.rand.nextInt(16) + 8;
                  boulders = this.worldObj.func_72825_h(grasses, doubleGrasses);
                  randomstructure.structureGen.func_76484_a(this.worldObj, this.rand, grasses, boulders, doubleGrasses);
               }
            }
         }

         var19 = this.villages.iterator();

         while(var19.hasNext()) {
            LOTRVillageGen village = (LOTRVillageGen)var19.next();
            village.generateInChunk(this.worldObj, this.chunkX, this.chunkZ);
         }
      }

      if (LOTRWorldGenMarshHut.generatesAt(this.worldObj, this.chunkX, this.chunkZ)) {
         trees = this.chunkX + 8;
         k = this.chunkZ + 8;
         cluster = this.worldObj.func_72825_h(trees, k);
         LOTRWorldGenStructureBase house = new LOTRWorldGenMarshHut();
         house.restrictions = false;
         house.func_76484_a(this.worldObj, this.rand, trees, cluster, k);
      }

      if (LOTRWorldGenGrukHouse.generatesAt(this.worldObj, this.chunkX, this.chunkZ)) {
         trees = this.chunkX + 8;
         k = this.chunkZ + 8;
         cluster = this.worldObj.func_72825_h(trees, k);
         LOTRWorldGenStructureBase2 house = new LOTRWorldGenGrukHouse(false);
         house.restrictions = false;
         house.generateWithSetRotation(this.worldObj, this.rand, trees, cluster, k, 2);
      }

      if (LOTRWorldGenTicketBooth.generatesAt(this.worldObj, this.chunkX, this.chunkZ)) {
         trees = this.chunkX + 8;
         k = this.chunkZ + 8;
         cluster = this.worldObj.func_72825_h(trees, k);
         LOTRWorldGenStructureBase2 booth = new LOTRWorldGenTicketBooth(false);
         booth.restrictions = false;
         booth.generateWithSetRotation(this.worldObj, this.rand, trees, cluster, k, 3);
      }

      trees = this.getVariantTreesPerChunk(biomeVariant);
      if (this.rand.nextFloat() < this.biome.getTreeIncreaseChance() * biomeVariant.treeFactor) {
         ++trees;
      }

      float reciprocalTreeFactor = 1.0F / Math.max(biomeVariant.treeFactor, 0.001F);
      cluster = Math.round((float)this.treeClusterChance * reciprocalTreeFactor);
      if (cluster > 0) {
         Random chunkRand = new Random();
         long seed = (long)(this.chunkX / this.treeClusterSize * 3129871) ^ (long)(this.chunkZ / this.treeClusterSize) * 116129781L;
         seed = seed * seed * 42317861L + seed * 11L;
         chunkRand.setSeed(seed);
         if (chunkRand.nextInt(cluster) == 0) {
            trees += 6 + this.rand.nextInt(5);
         }
      }

      int doubleFlowers;
      WorldGenAbstractTree treeGen;
      for(flowers = 0; flowers < trees; ++flowers) {
         doubleFlowers = this.chunkX + this.rand.nextInt(16) + 8;
         grasses = this.chunkZ + this.rand.nextInt(16) + 8;
         treeGen = this.getRandomTreeForVariant(this.rand, biomeVariant).create(false, this.rand);
         treeGen.func_76484_a(this.worldObj, this.rand, doubleFlowers, this.worldObj.func_72976_f(doubleFlowers, grasses), grasses);
      }

      for(flowers = 0; flowers < this.willowPerChunk; ++flowers) {
         doubleFlowers = this.chunkX + this.rand.nextInt(16) + 8;
         grasses = this.chunkZ + this.rand.nextInt(16) + 8;
         treeGen = LOTRTreeType.WILLOW_WATER.create(false, this.rand);
         treeGen.func_76484_a(this.worldObj, this.rand, doubleFlowers, this.worldObj.func_72976_f(doubleFlowers, grasses), grasses);
      }

      int l;
      float bushesR;
      float bushes;
      if (trees > 0) {
         bushes = (float)trees / 2.0F;
         doubleFlowers = (int)bushes;
         bushesR = bushes - (float)doubleFlowers;
         if (this.rand.nextFloat() < bushesR) {
            ++doubleFlowers;
         }

         for(doubleGrasses = 0; (float)doubleGrasses < bushes; ++doubleGrasses) {
            boulders = this.chunkX + this.rand.nextInt(16) + 8;
            l = this.chunkZ + this.rand.nextInt(16) + 8;
            (new LOTRWorldGenFallenLeaves()).func_76484_a(this.worldObj, this.rand, boulders, this.worldObj.func_72825_h(boulders, l), l);
         }
      }

      if (trees > 0) {
         bushes = (float)trees / 3.0F;
         doubleFlowers = (int)bushes;
         bushesR = bushes - (float)doubleFlowers;
         if (this.rand.nextFloat() < bushesR) {
            ++doubleFlowers;
         }

         for(doubleGrasses = 0; (float)doubleGrasses < bushes; ++doubleGrasses) {
            boulders = this.chunkX + this.rand.nextInt(16) + 8;
            l = this.chunkZ + this.rand.nextInt(16) + 8;
            (new LOTRWorldGenBushes()).func_76484_a(this.worldObj, this.rand, boulders, this.worldObj.func_72825_h(boulders, l), l);
         }
      }

      for(flowers = 0; flowers < this.logsPerChunk; ++flowers) {
         doubleFlowers = this.chunkX + this.rand.nextInt(16) + 8;
         grasses = this.chunkZ + this.rand.nextInt(16) + 8;
         this.logGen.func_76484_a(this.worldObj, this.rand, doubleFlowers, this.worldObj.func_72976_f(doubleFlowers, grasses), grasses);
      }

      for(flowers = 0; flowers < this.vinesPerChunk; ++flowers) {
         doubleFlowers = this.chunkX + this.rand.nextInt(16) + 8;
         int j = 64;
         doubleGrasses = this.chunkZ + this.rand.nextInt(16) + 8;
         this.vinesGen.func_76484_a(this.worldObj, this.rand, doubleFlowers, j, doubleGrasses);
      }

      flowers = this.flowersPerChunk;
      flowers = Math.round((float)flowers * biomeVariant.flowerFactor);

      for(doubleFlowers = 0; doubleFlowers < flowers; ++doubleFlowers) {
         grasses = this.chunkX + this.rand.nextInt(16) + 8;
         doubleGrasses = this.rand.nextInt(128);
         boulders = this.chunkZ + this.rand.nextInt(16) + 8;
         this.flowerGen.func_76484_a(this.worldObj, this.rand, grasses, doubleGrasses, boulders);
      }

      doubleFlowers = this.doubleFlowersPerChunk;
      doubleFlowers = Math.round((float)doubleFlowers * biomeVariant.flowerFactor);

      for(grasses = 0; grasses < doubleFlowers; ++grasses) {
         doubleGrasses = this.chunkX + this.rand.nextInt(16) + 8;
         boulders = this.rand.nextInt(128);
         l = this.chunkZ + this.rand.nextInt(16) + 8;
         WorldGenerator doubleFlowerGen = this.biome.getRandomWorldGenForDoubleFlower(this.rand);
         doubleFlowerGen.func_76484_a(this.worldObj, this.rand, doubleGrasses, boulders, l);
      }

      grasses = this.grassPerChunk;
      grasses = Math.round((float)grasses * biomeVariant.grassFactor);

      int i;
      for(doubleGrasses = 0; doubleGrasses < grasses; ++doubleGrasses) {
         boulders = this.chunkX + this.rand.nextInt(16) + 8;
         l = this.rand.nextInt(128);
         i = this.chunkZ + this.rand.nextInt(16) + 8;
         WorldGenerator grassGen = this.biome.func_76730_b(this.rand);
         grassGen.func_76484_a(this.worldObj, this.rand, boulders, l, i);
      }

      doubleGrasses = this.doubleGrassPerChunk;
      doubleGrasses = Math.round((float)doubleGrasses * biomeVariant.grassFactor);

      int k;
      for(boulders = 0; boulders < doubleGrasses; ++boulders) {
         l = this.chunkX + this.rand.nextInt(16) + 8;
         i = this.rand.nextInt(128);
         k = this.chunkZ + this.rand.nextInt(16) + 8;
         WorldGenerator grassGen = this.biome.getRandomWorldGenForDoubleGrass(this.rand);
         grassGen.func_76484_a(this.worldObj, this.rand, l, i, k);
      }

      for(boulders = 0; boulders < this.deadBushPerChunk; ++boulders) {
         l = this.chunkX + this.rand.nextInt(16) + 8;
         i = this.rand.nextInt(128);
         k = this.chunkZ + this.rand.nextInt(16) + 8;
         (new WorldGenDeadBush(Blocks.field_150330_I)).func_76484_a(this.worldObj, this.rand, l, i, k);
      }

      for(boulders = 0; boulders < this.waterlilyPerChunk; ++boulders) {
         l = this.chunkX + this.rand.nextInt(16) + 8;
         i = this.chunkZ + this.rand.nextInt(16) + 8;

         for(k = this.rand.nextInt(128); k > 0 && this.worldObj.func_147439_a(l, k - 1, i) == Blocks.field_150350_a; --k) {
         }

         this.waterlilyGen.func_76484_a(this.worldObj, this.rand, l, k, i);
      }

      for(boulders = 0; boulders < this.mushroomsPerChunk; ++boulders) {
         if (this.rand.nextInt(4) == 0) {
            l = this.chunkX + this.rand.nextInt(16) + 8;
            i = this.chunkZ + this.rand.nextInt(16) + 8;
            k = this.worldObj.func_72976_f(l, i);
            this.mushroomBrownGen.func_76484_a(this.worldObj, this.rand, l, k, i);
         }

         if (this.rand.nextInt(8) == 0) {
            l = this.chunkX + this.rand.nextInt(16) + 8;
            i = this.chunkZ + this.rand.nextInt(16) + 8;
            k = this.worldObj.func_72976_f(l, i);
            this.mushroomRedGen.func_76484_a(this.worldObj, this.rand, l, k, i);
         }
      }

      if (this.enableRandomMushroom) {
         if (this.rand.nextInt(4) == 0) {
            boulders = this.chunkX + this.rand.nextInt(16) + 8;
            l = this.rand.nextInt(128);
            i = this.chunkZ + this.rand.nextInt(16) + 8;
            this.mushroomBrownGen.func_76484_a(this.worldObj, this.rand, boulders, l, i);
         }

         if (this.rand.nextInt(8) == 0) {
            boulders = this.chunkX + this.rand.nextInt(16) + 8;
            l = this.rand.nextInt(128);
            i = this.chunkZ + this.rand.nextInt(16) + 8;
            this.mushroomRedGen.func_76484_a(this.worldObj, this.rand, boulders, l, i);
         }
      }

      for(boulders = 0; boulders < this.canePerChunk; ++boulders) {
         l = this.chunkX + this.rand.nextInt(16) + 8;
         i = this.rand.nextInt(128);
         k = this.chunkZ + this.rand.nextInt(16) + 8;
         this.caneGen.func_76484_a(this.worldObj, this.rand, l, i, k);
      }

      for(boulders = 0; boulders < 10; ++boulders) {
         l = this.chunkX + this.rand.nextInt(16) + 8;
         i = this.rand.nextInt(128);
         k = this.chunkZ + this.rand.nextInt(16) + 8;
         this.caneGen.func_76484_a(this.worldObj, this.rand, l, i, k);
      }

      for(boulders = 0; boulders < this.reedPerChunk; ++boulders) {
         l = this.chunkX + this.rand.nextInt(16) + 8;
         i = this.chunkZ + this.rand.nextInt(16) + 8;

         for(k = this.rand.nextInt(128); k > 0 && this.worldObj.func_147439_a(l, k - 1, i) == Blocks.field_150350_a; --k) {
         }

         if (this.rand.nextFloat() < this.dryReedChance) {
            this.dryReedGen.func_76484_a(this.worldObj, this.rand, l, k, i);
         } else {
            this.reedGen.func_76484_a(this.worldObj, this.rand, l, k, i);
         }
      }

      for(boulders = 0; boulders < this.cornPerChunk; ++boulders) {
         l = this.chunkX + this.rand.nextInt(16) + 8;
         i = this.rand.nextInt(128);
         k = this.chunkZ + this.rand.nextInt(16) + 8;
         this.cornGen.func_76484_a(this.worldObj, this.rand, l, i, k);
      }

      for(boulders = 0; boulders < this.cactiPerChunk; ++boulders) {
         l = this.chunkX + this.rand.nextInt(16) + 8;
         i = this.rand.nextInt(128);
         k = this.chunkZ + this.rand.nextInt(16) + 8;
         this.cactusGen.func_76484_a(this.worldObj, this.rand, l, i, k);
      }

      int k;
      int k;
      if (this.melonPerChunk > 0.0F) {
         boulders = MathHelper.func_76128_c((double)this.melonPerChunk);
         float melonF = this.melonPerChunk - (float)boulders;

         for(i = 0; i < boulders; ++i) {
            k = this.chunkX + this.rand.nextInt(16) + 8;
            k = this.chunkZ + this.rand.nextInt(16) + 8;
            k = this.worldObj.func_72976_f(k, k);
            this.melonGen.func_76484_a(this.worldObj, this.rand, k, k, k);
         }

         if (this.rand.nextFloat() < melonF) {
            i = this.chunkX + this.rand.nextInt(16) + 8;
            k = this.chunkZ + this.rand.nextInt(16) + 8;
            k = this.worldObj.func_72976_f(i, k);
            this.melonGen.func_76484_a(this.worldObj, this.rand, i, k, k);
         }
      }

      if (this.flowersPerChunk > 0 && this.rand.nextInt(32) == 0) {
         boulders = this.chunkX + this.rand.nextInt(16) + 8;
         l = this.rand.nextInt(128);
         i = this.chunkZ + this.rand.nextInt(16) + 8;
         this.pumpkinGen.func_76484_a(this.worldObj, this.rand, boulders, l, i);
      }

      if (this.flowersPerChunk > 0 && this.rand.nextInt(4) == 0) {
         boulders = this.chunkX + this.rand.nextInt(16) + 8;
         l = this.rand.nextInt(128);
         i = this.chunkZ + this.rand.nextInt(16) + 8;
         (new LOTRWorldGenBerryBush()).func_76484_a(this.worldObj, this.rand, boulders, l, i);
      }

      if (this.generateAthelas && this.rand.nextInt(30) == 0) {
         boulders = this.chunkX + this.rand.nextInt(16) + 8;
         l = this.rand.nextInt(128);
         i = this.chunkZ + this.rand.nextInt(16) + 8;
         (new WorldGenFlowers(LOTRMod.athelas)).func_76484_a(this.worldObj, this.rand, boulders, l, i);
      }

      LOTRWorldGenStreams waterGen;
      if (this.generateWater) {
         waterGen = new LOTRWorldGenStreams(Blocks.field_150358_i);

         for(l = 0; l < 50; ++l) {
            i = this.chunkX + this.rand.nextInt(16) + 8;
            k = this.rand.nextInt(this.rand.nextInt(120) + 8);
            k = this.chunkZ + this.rand.nextInt(16) + 8;
            waterGen.func_76484_a(this.worldObj, this.rand, i, k, k);
         }

         if (this.biome.field_76748_D > 1.0F) {
            for(l = 0; l < 50; ++l) {
               i = this.chunkX + this.rand.nextInt(16) + 8;
               k = 100 + this.rand.nextInt(150);
               k = this.chunkZ + this.rand.nextInt(16) + 8;
               waterGen.func_76484_a(this.worldObj, this.rand, i, k, k);
            }
         }
      }

      if (this.generateLava) {
         waterGen = new LOTRWorldGenStreams(Blocks.field_150356_k);
         int lava = 20;
         if (this.biome instanceof LOTRBiomeGenMordor) {
            lava = 50;
         }

         for(i = 0; i < lava; ++i) {
            k = this.chunkX + this.rand.nextInt(16) + 8;
            k = this.rand.nextInt(this.rand.nextInt(this.rand.nextInt(112) + 8) + 8);
            k = this.chunkZ + this.rand.nextInt(16) + 8;
            waterGen.func_76484_a(this.worldObj, this.rand, k, k, k);
         }
      }

      if (this.generateOrcDungeon) {
         for(boulders = 0; boulders < 6; ++boulders) {
            l = this.chunkX + this.rand.nextInt(16) + 8;
            i = this.rand.nextInt(128);
            k = this.chunkZ + this.rand.nextInt(16) + 8;
            this.orcDungeonGen.func_76484_a(this.worldObj, this.rand, l, i, k);
         }
      }

      if (this.generateTrollHoard) {
         for(boulders = 0; boulders < 2; ++boulders) {
            l = this.chunkX + this.rand.nextInt(16) + 8;
            i = MathHelper.func_76136_a(this.rand, 36, 90);
            k = this.chunkZ + this.rand.nextInt(16) + 8;
            this.trollHoardGen.func_76484_a(this.worldObj, this.rand, l, i, k);
         }
      }

      if (biomeVariant.boulderGen != null && this.rand.nextInt(biomeVariant.boulderChance) == 0) {
         boulders = MathHelper.func_76136_a(this.rand, 1, biomeVariant.boulderMax);

         for(l = 0; l < boulders; ++l) {
            i = this.chunkX + this.rand.nextInt(16) + 8;
            k = this.chunkZ + this.rand.nextInt(16) + 8;
            biomeVariant.boulderGen.func_76484_a(this.worldObj, this.rand, i, this.worldObj.func_72976_f(i, k), k);
         }
      }

   }

   private void generateOres() {
      Iterator var1 = this.biomeSoils.iterator();

      LOTRBiomeDecorator.OreGenerant gem;
      while(var1.hasNext()) {
         gem = (LOTRBiomeDecorator.OreGenerant)var1.next();
         this.genStandardOre(gem.oreChance, gem.oreGen, gem.minHeight, gem.maxHeight);
      }

      var1 = this.biomeOres.iterator();

      float f;
      while(var1.hasNext()) {
         gem = (LOTRBiomeDecorator.OreGenerant)var1.next();
         f = gem.oreChance * this.biomeOreFactor;
         this.genStandardOre(f, gem.oreGen, gem.minHeight, gem.maxHeight);
      }

      var1 = this.biomeGems.iterator();

      while(var1.hasNext()) {
         gem = (LOTRBiomeDecorator.OreGenerant)var1.next();
         f = gem.oreChance * this.biomeGemFactor;
         this.genStandardOre(f, gem.oreGen, gem.minHeight, gem.maxHeight);
      }

   }

   private void genStandardOre(float ores, WorldGenerator oreGen, int minHeight, int maxHeight) {
      while(ores > 0.0F) {
         boolean generate;
         if (ores >= 1.0F) {
            generate = true;
         } else {
            generate = this.rand.nextFloat() < ores;
         }

         --ores;
         if (generate) {
            int i = this.chunkX + this.rand.nextInt(16);
            int j = MathHelper.func_76136_a(this.rand, minHeight, maxHeight);
            int k = this.chunkZ + this.rand.nextInt(16);
            oreGen.func_76484_a(this.worldObj, this.rand, i, j, k);
         }
      }

   }

   private class RandomStructure {
      public WorldGenerator structureGen;
      public int chunkChance;

      public RandomStructure(WorldGenerator w, int i) {
         this.structureGen = w;
         this.chunkChance = i;
      }
   }

   private class OreGenerant {
      private WorldGenerator oreGen;
      private float oreChance;
      private int minHeight;
      private int maxHeight;

      public OreGenerant(WorldGenerator gen, float f, int min, int max) {
         this.oreGen = gen;
         this.oreChance = f;
         this.minHeight = min;
         this.maxHeight = max;
      }
   }
}
