package lotr.common.world.biome;

import java.util.Random;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityIronHillsMerchant;
import lotr.common.entity.npc.LOTREntityScrapTrader;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.map.LOTRRoadType;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.world.spawning.LOTREventSpawner;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenMinable;

public class LOTRBiomeGenRedMountains extends LOTRBiome {
   public LOTRBiomeGenRedMountains(int i, boolean major) {
      super(i, major);
      this.npcSpawnList.clear();
      this.addBiomeVariantSet(LOTRBiomeVariant.SET_MOUNTAINS);
      this.addBiomeVariant(LOTRBiomeVariant.FOREST_BEECH, 0.2F);
      this.addBiomeVariant(LOTRBiomeVariant.FOREST_BIRCH, 0.2F);
      this.addBiomeVariant(LOTRBiomeVariant.FOREST_LARCH, 0.2F);
      this.addBiomeVariant(LOTRBiomeVariant.FOREST_PINE, 0.2F);
      this.addBiomeVariant(LOTRBiomeVariant.FOREST_MAPLE, 0.2F);
      this.decorator.biomeOreFactor = 2.0F;
      this.decorator.biomeGemFactor = 1.5F;
      this.decorator.addSoil(new WorldGenMinable(LOTRMod.rock, 4, 60, Blocks.field_150348_b), 12.0F, 0, 96);
      this.decorator.addOre(new WorldGenMinable(LOTRMod.oreGlowstone, 4), 8.0F, 0, 48);
      this.decorator.treesPerChunk = 1;
      this.decorator.flowersPerChunk = 1;
      this.decorator.grassPerChunk = 8;
      this.decorator.doubleGrassPerChunk = 1;
      this.decorator.addTree(LOTRTreeType.OAK, 300);
      this.decorator.addTree(LOTRTreeType.OAK_LARGE, 50);
      this.decorator.addTree(LOTRTreeType.SPRUCE, 500);
      this.decorator.addTree(LOTRTreeType.LARCH, 300);
      this.decorator.addTree(LOTRTreeType.MAPLE, 300);
      this.decorator.addTree(LOTRTreeType.MAPLE_LARGE, 50);
      this.decorator.addTree(LOTRTreeType.FIR, 500);
      this.decorator.addTree(LOTRTreeType.PINE, 500);
      this.registerMountainsFlowers();
      this.addFlower(LOTRMod.dwarfHerb, 0, 1);
      this.biomeColors.setSky(13541522);
      this.registerTravellingTrader(LOTREntityIronHillsMerchant.class);
      this.registerTravellingTrader(LOTREntityScrapTrader.class);
      this.setBanditChance(LOTREventSpawner.EventChance.BANDIT_RARE);
   }

   public LOTRAchievement getBiomeAchievement() {
      return LOTRAchievement.enterRedMountains;
   }

   public LOTRWaypoint.Region getBiomeWaypoints() {
      return LOTRWaypoint.Region.RED_MOUNTAINS;
   }

   public LOTRMusicRegion.Sub getBiomeMusic() {
      return LOTRMusicRegion.DWARVEN.getSubregion("redMountains");
   }

   public boolean getEnableRiver() {
      return false;
   }

   public LOTRRoadType getRoadBlock() {
      return LOTRRoadType.DWARVEN;
   }

   protected void generateMountainTerrain(World world, Random random, Block[] blocks, byte[] meta, int i, int k, int xzIndex, int ySize, int height, int rockDepth, LOTRBiomeVariant variant) {
      int stoneHeight = 110 - rockDepth;
      int sandHeight = stoneHeight - 6;

      for(int j = ySize - 1; j >= sandHeight; --j) {
         int index = xzIndex * ySize + j;
         Block block = blocks[index];
         if (block == this.field_76752_A || block == this.field_76753_B) {
            if (j >= stoneHeight) {
               blocks[index] = LOTRMod.rock;
               meta[index] = 4;
            } else {
               blocks[index] = Blocks.field_150354_m;
               meta[index] = 1;
            }
         }
      }

   }

   public float getChanceToSpawnAnimals() {
      return 0.2F;
   }
}
