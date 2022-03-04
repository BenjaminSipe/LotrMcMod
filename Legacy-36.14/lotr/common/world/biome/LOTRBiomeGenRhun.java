package lotr.common.world.biome;

import java.util.Random;
import lotr.common.LOTRAchievement;
import lotr.common.entity.animal.LOTREntityBear;
import lotr.common.entity.animal.LOTREntityHorse;
import lotr.common.entity.npc.LOTREntityNearHaradMerchant;
import lotr.common.entity.npc.LOTREntityScrapTrader;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.feature.LOTRWorldGenBoulder;
import lotr.common.world.feature.LOTRWorldGenDoubleFlower;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.world.spawning.LOTRInvasions;
import lotr.common.world.structure2.LOTRWorldGenSmallStoneRuin;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase.SpawnListEntry;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRBiomeGenRhun extends LOTRBiome {
   private WorldGenerator boulderGen;

   public LOTRBiomeGenRhun(int i, boolean major) {
      super(i, major);
      this.boulderGen = new LOTRWorldGenBoulder(Blocks.field_150348_b, 0, 1, 4);
      this.field_76762_K.add(new SpawnListEntry(LOTREntityHorse.class, 20, 2, 6));
      this.field_76762_K.add(new SpawnListEntry(LOTREntityBear.class, 5, 1, 4));
      this.npcSpawnList.clear();
      this.variantChance = 0.3F;
      this.addBiomeVariantSet(LOTRBiomeVariant.SET_NORMAL_OAK_SPRUCE);
      this.addBiomeVariant(LOTRBiomeVariant.DENSEFOREST_SPRUCE, 3.0F);
      this.addBiomeVariant(LOTRBiomeVariant.SCRUBLAND);
      this.addBiomeVariant(LOTRBiomeVariant.HILLS_SCRUBLAND);
      this.addBiomeVariant(LOTRBiomeVariant.FOREST_BEECH, 0.2F);
      this.addBiomeVariant(LOTRBiomeVariant.FOREST_BIRCH, 0.2F);
      this.addBiomeVariant(LOTRBiomeVariant.FOREST_PINE, 0.2F);
      this.addBiomeVariant(LOTRBiomeVariant.FOREST_ASPEN, 0.2F);
      this.addBiomeVariant(LOTRBiomeVariant.FOREST_MAPLE, 0.2F);
      this.decorator.resetTreeCluster();
      this.decorator.willowPerChunk = 1;
      this.decorator.flowersPerChunk = 1;
      this.decorator.grassPerChunk = 12;
      this.decorator.doubleGrassPerChunk = 8;
      this.decorator.addTree(LOTRTreeType.OAK, 100);
      this.decorator.addTree(LOTRTreeType.OAK_LARGE, 50);
      this.decorator.addTree(LOTRTreeType.SPRUCE, 200);
      this.decorator.addTree(LOTRTreeType.PINE, 500);
      this.decorator.addTree(LOTRTreeType.PINE_SHRUB, 4000);
      this.decorator.addTree(LOTRTreeType.CHESTNUT, 500);
      this.decorator.addTree(LOTRTreeType.CHESTNUT_LARGE, 20);
      this.decorator.addTree(LOTRTreeType.ASPEN, 100);
      this.decorator.addTree(LOTRTreeType.ASPEN_LARGE, 20);
      this.decorator.addTree(LOTRTreeType.MAPLE, 50);
      this.decorator.addTree(LOTRTreeType.MAPLE_LARGE, 20);
      this.registerRhunPlainsFlowers();
      this.biomeColors.setGrass(12504664);
      this.decorator.addRandomStructure(new LOTRWorldGenSmallStoneRuin(false), 1000);
      this.registerTravellingTrader(LOTREntityNearHaradMerchant.class);
      this.registerTravellingTrader(LOTREntityScrapTrader.class);
      this.setBanditChance(LOTREventSpawner.EventChance.BANDIT_RARE);
      this.invasionSpawns.addInvasion(LOTRInvasions.RHUN, LOTREventSpawner.EventChance.RARE);
   }

   public LOTRAchievement getBiomeAchievement() {
      return LOTRAchievement.enterRhun;
   }

   public LOTRWaypoint.Region getBiomeWaypoints() {
      return LOTRWaypoint.Region.RHUN;
   }

   public LOTRMusicRegion.Sub getBiomeMusic() {
      return LOTRMusicRegion.RHUN.getSubregion("rhun");
   }

   public void func_76728_a(World world, Random random, int i, int k) {
      super.func_76728_a(world, random, i, k);
      if (random.nextInt(200) == 0) {
         for(int l = 0; l < 3; ++l) {
            int i1 = i + random.nextInt(16) + 8;
            int k1 = k + random.nextInt(16) + 8;
            this.boulderGen.func_76484_a(world, random, i1, world.func_72976_f(i1, k1), k1);
         }
      }

   }

   public WorldGenerator getRandomWorldGenForDoubleFlower(Random random) {
      if (random.nextInt(4) == 0) {
         LOTRWorldGenDoubleFlower doubleFlowerGen = new LOTRWorldGenDoubleFlower();
         doubleFlowerGen.setFlowerType(0);
         return doubleFlowerGen;
      } else {
         return super.getRandomWorldGenForDoubleFlower(random);
      }
   }

   public float getTreeIncreaseChance() {
      return 0.03F;
   }

   public float getChanceToSpawnAnimals() {
      return 0.25F;
   }
}
