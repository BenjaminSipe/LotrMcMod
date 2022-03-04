package lotr.common.world.biome;

import java.util.Random;
import lotr.common.entity.animal.LOTREntityBird;
import lotr.common.entity.animal.LOTREntityButterfly;
import lotr.common.entity.animal.LOTREntityCrocodile;
import lotr.common.entity.animal.LOTREntityDikDik;
import lotr.common.entity.animal.LOTREntityGemsbok;
import lotr.common.entity.animal.LOTREntityGiraffe;
import lotr.common.entity.animal.LOTREntityLion;
import lotr.common.entity.animal.LOTREntityLioness;
import lotr.common.entity.animal.LOTREntityRhino;
import lotr.common.entity.animal.LOTREntityZebra;
import lotr.common.world.LOTRWorldChunkManager;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.feature.LOTRWorldGenDoubleFlower;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.world.spawning.LOTREventSpawner;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase.SpawnListEntry;
import net.minecraft.world.gen.feature.WorldGenerator;

public abstract class LOTRBiomeGenFarHarad extends LOTRBiome {
   public LOTRBiomeGenFarHarad(int i, boolean major) {
      super(i, major);
      this.field_76762_K.clear();
      this.field_76762_K.add(new SpawnListEntry(LOTREntityLion.class, 4, 2, 4));
      this.field_76762_K.add(new SpawnListEntry(LOTREntityLioness.class, 4, 2, 4));
      this.field_76762_K.add(new SpawnListEntry(LOTREntityGiraffe.class, 4, 4, 6));
      this.field_76762_K.add(new SpawnListEntry(LOTREntityZebra.class, 8, 4, 8));
      this.field_76762_K.add(new SpawnListEntry(LOTREntityRhino.class, 8, 4, 4));
      this.field_76762_K.add(new SpawnListEntry(LOTREntityGemsbok.class, 8, 4, 8));
      this.spawnableLOTRAmbientList.clear();
      this.spawnableLOTRAmbientList.add(new SpawnListEntry(LOTREntityButterfly.class, 5, 4, 4));
      this.spawnableLOTRAmbientList.add(new SpawnListEntry(LOTREntityBird.class, 8, 4, 4));
      this.spawnableLOTRAmbientList.add(new SpawnListEntry(LOTREntityDikDik.class, 8, 4, 6));
      this.field_76761_J.add(new SpawnListEntry(LOTREntityCrocodile.class, 10, 4, 4));
      this.npcSpawnList.clear();
      this.decorator.biomeGemFactor = 0.75F;
      this.decorator.treesPerChunk = 0;
      this.decorator.grassPerChunk = 8;
      this.decorator.doubleGrassPerChunk = 12;
      this.decorator.flowersPerChunk = 3;
      this.decorator.doubleFlowersPerChunk = 1;
      this.decorator.addTree(LOTRTreeType.ACACIA, 1000);
      this.decorator.addTree(LOTRTreeType.OAK_DESERT, 300);
      this.decorator.addTree(LOTRTreeType.BAOBAB, 20);
      this.decorator.addTree(LOTRTreeType.MANGO, 1);
      this.registerHaradFlowers();
      this.setBanditChance(LOTREventSpawner.EventChance.NEVER);
   }

   public LOTRWaypoint.Region getBiomeWaypoints() {
      return LOTRWaypoint.Region.FAR_HARAD;
   }

   public void func_76728_a(World world, Random random, int i, int k) {
      super.func_76728_a(world, random, i, k);
      LOTRBiomeVariant variant = ((LOTRWorldChunkManager)world.func_72959_q()).getBiomeVariantAt(i + 8, k + 8);
      if (variant == LOTRBiomeVariant.RIVER && random.nextInt(3) == 0) {
         WorldGenerator bananaTree = LOTRTreeType.BANANA.create(false, random);
         int bananas = 3 + random.nextInt(8);

         for(int l = 0; l < bananas; ++l) {
            int i1 = i + random.nextInt(16) + 8;
            int k1 = k + random.nextInt(16) + 8;
            int j1 = world.func_72825_h(i1, k1);
            bananaTree.func_76484_a(world, random, i1, j1, k1);
         }
      }

   }

   public WorldGenerator getRandomWorldGenForDoubleFlower(Random random) {
      LOTRWorldGenDoubleFlower doubleFlowerGen = new LOTRWorldGenDoubleFlower();
      if (random.nextInt(5) == 0) {
         doubleFlowerGen.setFlowerType(3);
      } else {
         doubleFlowerGen.setFlowerType(2);
      }

      return doubleFlowerGen;
   }
}
