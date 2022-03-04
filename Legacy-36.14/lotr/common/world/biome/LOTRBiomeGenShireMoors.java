package lotr.common.world.biome;

import java.util.Random;
import lotr.common.LOTRMod;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.feature.LOTRWorldGenBoulder;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.world.structure2.LOTRWorldGenHobbitFarm;
import lotr.common.world.structure2.LOTRWorldGenHobbitTavern;
import lotr.common.world.structure2.LOTRWorldGenHobbitWindmill;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRBiomeGenShireMoors extends LOTRBiomeGenShire {
   private WorldGenerator boulderSmall;
   private WorldGenerator boulderLarge;

   public LOTRBiomeGenShireMoors(int i, boolean major) {
      super(i, major);
      this.boulderSmall = new LOTRWorldGenBoulder(Blocks.field_150348_b, 0, 1, 2);
      this.boulderLarge = new LOTRWorldGenBoulder(Blocks.field_150348_b, 0, 3, 5);
      this.clearBiomeVariants();
      this.variantChance = 0.2F;
      this.addBiomeVariantSet(LOTRBiomeVariant.SET_MOUNTAINS);
      this.decorator.treesPerChunk = 0;
      this.decorator.flowersPerChunk = 16;
      this.decorator.doubleFlowersPerChunk = 0;
      this.decorator.grassPerChunk = 16;
      this.decorator.doubleGrassPerChunk = 1;
      this.decorator.addTree(LOTRTreeType.OAK_LARGE, 8000);
      this.decorator.addTree(LOTRTreeType.CHESTNUT_LARGE, 2000);
      this.addFlower(LOTRMod.shireHeather, 0, 100);
      this.biomeColors.resetGrass();
      this.decorator.addRandomStructure(new LOTRWorldGenHobbitWindmill(false), 500);
      this.decorator.addRandomStructure(new LOTRWorldGenHobbitFarm(false), 1000);
      this.decorator.addRandomStructure(new LOTRWorldGenHobbitTavern(false), 200);
      this.setBanditChance(LOTREventSpawner.EventChance.BANDIT_RARE);
   }

   public LOTRMusicRegion.Sub getBiomeMusic() {
      return LOTRMusicRegion.SHIRE.getSubregion("moors");
   }

   public void func_76728_a(World world, Random random, int i, int k) {
      super.func_76728_a(world, random, i, k);
      int l;
      int i1;
      int k1;
      if (random.nextInt(8) == 0) {
         for(l = 0; l < 4; ++l) {
            i1 = i + random.nextInt(16) + 8;
            k1 = k + random.nextInt(16) + 8;
            this.boulderSmall.func_76484_a(world, random, i1, world.func_72976_f(i1, k1), k1);
         }
      }

      if (random.nextInt(30) == 0) {
         for(l = 0; l < 4; ++l) {
            i1 = i + random.nextInt(16) + 8;
            k1 = k + random.nextInt(16) + 8;
            this.boulderLarge.func_76484_a(world, random, i1, world.func_72976_f(i1, k1), k1);
         }
      }

   }

   public float getTreeIncreaseChance() {
      return 0.1F;
   }

   public float getChanceToSpawnAnimals() {
      return 0.25F;
   }

   public int spawnCountMultiplier() {
      return super.spawnCountMultiplier() * 2;
   }
}
