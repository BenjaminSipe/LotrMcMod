package lotr.common.world.feature;

import java.util.Random;
import lotr.common.LOTRMod;
import net.minecraft.init.Blocks;
import net.minecraft.util.WeightedRandom.Item;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenCanopyTree;
import net.minecraft.world.gen.feature.WorldGenMegaJungle;
import net.minecraft.world.gen.feature.WorldGenMegaPineTree;
import net.minecraft.world.gen.feature.WorldGenSavannaTree;
import net.minecraft.world.gen.feature.WorldGenSwamp;
import net.minecraft.world.gen.feature.WorldGenTaiga1;
import net.minecraft.world.gen.feature.WorldGenTaiga2;
import net.minecraft.world.gen.feature.WorldGenTrees;

public enum LOTRTreeType {
   OAK(new LOTRTreeType.ITreeFactory() {
      public WorldGenAbstractTree createTree(boolean flag, Random rand) {
         return (WorldGenAbstractTree)(rand.nextInt(4) == 0 ? new LOTRWorldGenGnarledOak(flag) : new LOTRWorldGenSimpleTrees(flag, 4, 6, Blocks.field_150364_r, 0, Blocks.field_150362_t, 0));
      }
   }),
   OAK_TALL(new LOTRTreeType.ITreeFactory() {
      public WorldGenAbstractTree createTree(boolean flag, Random rand) {
         return (WorldGenAbstractTree)(rand.nextInt(4) == 0 ? (new LOTRWorldGenGnarledOak(flag)).setMinMaxHeight(6, 10) : new LOTRWorldGenSimpleTrees(flag, 8, 12, Blocks.field_150364_r, 0, Blocks.field_150362_t, 0));
      }
   }),
   OAK_TALLER(new LOTRTreeType.ITreeFactory() {
      public WorldGenAbstractTree createTree(boolean flag, Random rand) {
         return new LOTRWorldGenSimpleTrees(flag, 12, 16, Blocks.field_150364_r, 0, Blocks.field_150362_t, 0);
      }
   }),
   OAK_ITHILIEN_HIDEOUT(new LOTRTreeType.ITreeFactory() {
      public WorldGenAbstractTree createTree(boolean flag, Random rand) {
         return new LOTRWorldGenSimpleTrees(flag, 6, 6, Blocks.field_150364_r, 0, Blocks.field_150362_t, 0);
      }
   }),
   OAK_LARGE(new LOTRTreeType.ITreeFactory() {
      public WorldGenAbstractTree createTree(boolean flag, Random rand) {
         return new LOTRWorldGenBigTrees(flag, Blocks.field_150364_r, 0, Blocks.field_150362_t, 0);
      }
   }),
   OAK_PARTY(new LOTRTreeType.ITreeFactory() {
      public WorldGenAbstractTree createTree(boolean flag, Random rand) {
         return new LOTRWorldGenPartyTrees(Blocks.field_150364_r, 0, Blocks.field_150362_t, 0);
      }
   }),
   OAK_FANGORN(new LOTRTreeType.ITreeFactory() {
      public WorldGenAbstractTree createTree(boolean flag, Random rand) {
         return new LOTRWorldGenFangornTrees(flag, Blocks.field_150364_r, 0, Blocks.field_150362_t, 0);
      }
   }),
   OAK_FANGORN_DEAD(new LOTRTreeType.ITreeFactory() {
      public WorldGenAbstractTree createTree(boolean flag, Random rand) {
         return (new LOTRWorldGenFangornTrees(flag, Blocks.field_150364_r, 0, Blocks.field_150362_t, 0)).setNoLeaves();
      }
   }),
   OAK_SWAMP(new LOTRTreeType.ITreeFactory() {
      public WorldGenAbstractTree createTree(boolean flag, Random rand) {
         return new WorldGenSwamp();
      }
   }),
   OAK_DEAD(new LOTRTreeType.ITreeFactory() {
      public WorldGenAbstractTree createTree(boolean flag, Random rand) {
         return new LOTRWorldGenDeadTrees(Blocks.field_150364_r, 0);
      }
   }),
   OAK_DESERT(new LOTRTreeType.ITreeFactory() {
      public WorldGenAbstractTree createTree(boolean flag, Random rand) {
         return new LOTRWorldGenDesertTrees(flag, Blocks.field_150364_r, 0, Blocks.field_150362_t, 0);
      }
   }),
   OAK_SHRUB(new LOTRTreeType.ITreeFactory() {
      public WorldGenAbstractTree createTree(boolean flag, Random rand) {
         return new LOTRWorldGenShrub(Blocks.field_150364_r, 0, Blocks.field_150362_t, 0);
      }
   }),
   BIRCH(new LOTRTreeType.ITreeFactory() {
      public WorldGenAbstractTree createTree(boolean flag, Random rand) {
         return (WorldGenAbstractTree)(rand.nextInt(3) != 0 ? (new LOTRWorldGenAspen(flag)).setBlocks(Blocks.field_150364_r, 2, Blocks.field_150362_t, 2).setMinMaxHeight(8, 16) : new LOTRWorldGenSimpleTrees(flag, 5, 7, Blocks.field_150364_r, 2, Blocks.field_150362_t, 2));
      }
   }),
   BIRCH_TALL(new LOTRTreeType.ITreeFactory() {
      public WorldGenAbstractTree createTree(boolean flag, Random rand) {
         return new LOTRWorldGenSimpleTrees(flag, 8, 11, Blocks.field_150364_r, 2, Blocks.field_150362_t, 2);
      }
   }),
   BIRCH_LARGE(new LOTRTreeType.ITreeFactory() {
      public WorldGenAbstractTree createTree(boolean flag, Random rand) {
         return new LOTRWorldGenBigTrees(flag, Blocks.field_150364_r, 2, Blocks.field_150362_t, 2);
      }
   }),
   BIRCH_PARTY(new LOTRTreeType.ITreeFactory() {
      public WorldGenAbstractTree createTree(boolean flag, Random rand) {
         return new LOTRWorldGenPartyTrees(Blocks.field_150364_r, 2, Blocks.field_150362_t, 2);
      }
   }),
   BIRCH_FANGORN(new LOTRTreeType.ITreeFactory() {
      public WorldGenAbstractTree createTree(boolean flag, Random rand) {
         return new LOTRWorldGenFangornTrees(flag, Blocks.field_150364_r, 2, Blocks.field_150362_t, 2);
      }
   }),
   BIRCH_DEAD(new LOTRTreeType.ITreeFactory() {
      public WorldGenAbstractTree createTree(boolean flag, Random rand) {
         return new LOTRWorldGenDeadTrees(Blocks.field_150364_r, 2);
      }
   }),
   SPRUCE(new LOTRTreeType.ITreeFactory() {
      public WorldGenAbstractTree createTree(boolean flag, Random rand) {
         return new WorldGenTaiga2(flag);
      }
   }),
   SPRUCE_THIN(new LOTRTreeType.ITreeFactory() {
      public WorldGenAbstractTree createTree(boolean flag, Random rand) {
         return new WorldGenTaiga1();
      }
   }),
   SPRUCE_MEGA(new LOTRTreeType.ITreeFactory() {
      public WorldGenAbstractTree createTree(boolean flag, Random rand) {
         return new WorldGenMegaPineTree(flag, true);
      }
   }),
   SPRUCE_MEGA_THIN(new LOTRTreeType.ITreeFactory() {
      public WorldGenAbstractTree createTree(boolean flag, Random rand) {
         return new WorldGenMegaPineTree(flag, false);
      }
   }),
   SPRUCE_DEAD(new LOTRTreeType.ITreeFactory() {
      public WorldGenAbstractTree createTree(boolean flag, Random rand) {
         return new LOTRWorldGenDeadTrees(Blocks.field_150364_r, 1);
      }
   }),
   JUNGLE(new LOTRTreeType.ITreeFactory() {
      public WorldGenAbstractTree createTree(boolean flag, Random rand) {
         return new WorldGenTrees(flag, 7, 3, 3, true);
      }
   }),
   JUNGLE_LARGE(new LOTRTreeType.ITreeFactory() {
      public WorldGenAbstractTree createTree(boolean flag, Random rand) {
         return new WorldGenMegaJungle(flag, 10, 20, 3, 3);
      }
   }),
   JUNGLE_CLOUD(new LOTRTreeType.ITreeFactory() {
      public WorldGenAbstractTree createTree(boolean flag, Random rand) {
         return new WorldGenMegaJungle(flag, 30, 30, 3, 3);
      }
   }),
   JUNGLE_SHRUB(new LOTRTreeType.ITreeFactory() {
      public WorldGenAbstractTree createTree(boolean flag, Random rand) {
         return new LOTRWorldGenShrub(Blocks.field_150364_r, 3, Blocks.field_150362_t, 3);
      }
   }),
   JUNGLE_FANGORN(new LOTRTreeType.ITreeFactory() {
      public WorldGenAbstractTree createTree(boolean flag, Random rand) {
         return (new LOTRWorldGenFangornTrees(flag, Blocks.field_150364_r, 3, Blocks.field_150362_t, 3)).setHeightFactor(1.5F);
      }
   }),
   ACACIA(new LOTRTreeType.ITreeFactory() {
      public WorldGenAbstractTree createTree(boolean flag, Random rand) {
         return new WorldGenSavannaTree(flag);
      }
   }),
   ACACIA_DEAD(new LOTRTreeType.ITreeFactory() {
      public WorldGenAbstractTree createTree(boolean flag, Random rand) {
         return new LOTRWorldGenDeadTrees(Blocks.field_150363_s, 0);
      }
   }),
   DARK_OAK(new LOTRTreeType.ITreeFactory() {
      public WorldGenAbstractTree createTree(boolean flag, Random rand) {
         return new WorldGenCanopyTree(flag);
      }
   }),
   DARK_OAK_PARTY(new LOTRTreeType.ITreeFactory() {
      public WorldGenAbstractTree createTree(boolean flag, Random rand) {
         return new LOTRWorldGenPartyTrees(Blocks.field_150363_s, 1, Blocks.field_150361_u, 1);
      }
   }),
   SHIRE_PINE(new LOTRTreeType.ITreeFactory() {
      public WorldGenAbstractTree createTree(boolean flag, Random rand) {
         return new LOTRWorldGenShirePine(flag);
      }
   }),
   MALLORN(new LOTRTreeType.ITreeFactory() {
      public WorldGenAbstractTree createTree(boolean flag, Random rand) {
         return new LOTRWorldGenSimpleTrees(flag, 6, 9, LOTRMod.wood, 1, LOTRMod.leaves, 1);
      }
   }),
   MALLORN_BOUGHS(new LOTRTreeType.ITreeFactory() {
      public WorldGenAbstractTree createTree(boolean flag, Random rand) {
         return new LOTRWorldGenMallorn(flag);
      }
   }),
   MALLORN_PARTY(new LOTRTreeType.ITreeFactory() {
      public WorldGenAbstractTree createTree(boolean flag, Random rand) {
         return new LOTRWorldGenPartyTrees(LOTRMod.wood, 1, LOTRMod.leaves, 1);
      }
   }),
   MALLORN_EXTREME(new LOTRTreeType.ITreeFactory() {
      public WorldGenAbstractTree createTree(boolean flag, Random rand) {
         return new LOTRWorldGenMallornExtreme(flag);
      }
   }),
   MALLORN_EXTREME_SAPLING(new LOTRTreeType.ITreeFactory() {
      public WorldGenAbstractTree createTree(boolean flag, Random rand) {
         return (new LOTRWorldGenMallornExtreme(flag)).setSaplingGrowth();
      }
   }),
   MIRK_OAK(new LOTRTreeType.ITreeFactory() {
      public WorldGenAbstractTree createTree(boolean flag, Random rand) {
         return new LOTRWorldGenMirkOak(flag, 4, 7, 0, true);
      }
   }),
   MIRK_OAK_LARGE(new LOTRTreeType.ITreeFactory() {
      public WorldGenAbstractTree createTree(boolean flag, Random rand) {
         return new LOTRWorldGenMirkOak(flag, 12, 16, 1, true);
      }
   }),
   MIRK_OAK_DEAD(new LOTRTreeType.ITreeFactory() {
      public WorldGenAbstractTree createTree(boolean flag, Random rand) {
         return (new LOTRWorldGenMirkOak(flag, 4, 7, 0, true)).setDead();
      }
   }),
   RED_OAK(new LOTRTreeType.ITreeFactory() {
      public WorldGenAbstractTree createTree(boolean flag, Random rand) {
         return (new LOTRWorldGenMirkOak(flag, 6, 9, 0, false)).setRedOak();
      }
   }),
   RED_OAK_LARGE(new LOTRTreeType.ITreeFactory() {
      public WorldGenAbstractTree createTree(boolean flag, Random rand) {
         return (new LOTRWorldGenMirkOak(flag, 12, 17, 1, false)).setRedOak();
      }
   }),
   RED_OAK_WEIRWOOD(new LOTRTreeType.ITreeFactory() {
      public WorldGenAbstractTree createTree(boolean flag, Random rand) {
         return (new LOTRWorldGenMirkOak(flag, 12, 20, 1, false)).setBlocks(LOTRMod.wood9, 0, LOTRMod.leaves, 3);
      }
   }),
   CHARRED(new LOTRTreeType.ITreeFactory() {
      public WorldGenAbstractTree createTree(boolean flag, Random rand) {
         return new LOTRWorldGenCharredTrees();
      }
   }),
   CHARRED_FANGORN(new LOTRTreeType.ITreeFactory() {
      public WorldGenAbstractTree createTree(boolean flag, Random rand) {
         return (new LOTRWorldGenFangornTrees(flag, LOTRMod.wood, 3, Blocks.field_150350_a, 0)).setNoLeaves();
      }
   }),
   APPLE(new LOTRTreeType.ITreeFactory() {
      public WorldGenAbstractTree createTree(boolean flag, Random rand) {
         return new LOTRWorldGenSimpleTrees(flag, 4, 7, LOTRMod.fruitWood, 0, LOTRMod.fruitLeaves, 0);
      }
   }),
   PEAR(new LOTRTreeType.ITreeFactory() {
      public WorldGenAbstractTree createTree(boolean flag, Random rand) {
         return new LOTRWorldGenSimpleTrees(flag, 4, 5, LOTRMod.fruitWood, 1, LOTRMod.fruitLeaves, 1);
      }
   }),
   CHERRY(new LOTRTreeType.ITreeFactory() {
      public WorldGenAbstractTree createTree(boolean flag, Random rand) {
         return new LOTRWorldGenSimpleTrees(flag, 4, 8, LOTRMod.fruitWood, 2, LOTRMod.fruitLeaves, 2);
      }
   }),
   CHERRY_MORDOR(new LOTRTreeType.ITreeFactory() {
      public WorldGenAbstractTree createTree(boolean flag, Random rand) {
         return (new LOTRWorldGenPartyTrees(LOTRMod.fruitWood, 2, LOTRMod.fruitLeaves, 2)).disableRestrictions();
      }
   }),
   MANGO(new LOTRTreeType.ITreeFactory() {
      public WorldGenAbstractTree createTree(boolean flag, Random rand) {
         return (WorldGenAbstractTree)(rand.nextInt(3) == 0 ? (new LOTRWorldGenOlive(flag)).setBlocks(LOTRMod.fruitWood, 3, LOTRMod.fruitLeaves, 3) : new LOTRWorldGenDesertTrees(flag, LOTRMod.fruitWood, 3, LOTRMod.fruitLeaves, 3));
      }
   }),
   LEBETHRON(new LOTRTreeType.ITreeFactory() {
      public WorldGenAbstractTree createTree(boolean flag, Random rand) {
         return new LOTRWorldGenSimpleTrees(flag, 5, 9, LOTRMod.wood2, 0, LOTRMod.leaves2, 0);
      }
   }),
   LEBETHRON_LARGE(new LOTRTreeType.ITreeFactory() {
      public WorldGenAbstractTree createTree(boolean flag, Random rand) {
         return new LOTRWorldGenBigTrees(flag, LOTRMod.wood2, 0, LOTRMod.leaves2, 0);
      }
   }),
   LEBETHRON_PARTY(new LOTRTreeType.ITreeFactory() {
      public WorldGenAbstractTree createTree(boolean flag, Random rand) {
         return new LOTRWorldGenPartyTrees(LOTRMod.wood2, 0, LOTRMod.leaves2, 0);
      }
   }),
   LEBETHRON_DEAD(new LOTRTreeType.ITreeFactory() {
      public WorldGenAbstractTree createTree(boolean flag, Random rand) {
         return new LOTRWorldGenDeadTrees(LOTRMod.wood2, 0);
      }
   }),
   BEECH(new LOTRTreeType.ITreeFactory() {
      public WorldGenAbstractTree createTree(boolean flag, Random rand) {
         return new LOTRWorldGenSimpleTrees(flag, 5, 9, LOTRMod.wood2, 1, LOTRMod.leaves2, 1);
      }
   }),
   BEECH_LARGE(new LOTRTreeType.ITreeFactory() {
      public WorldGenAbstractTree createTree(boolean flag, Random rand) {
         return new LOTRWorldGenBigTrees(flag, LOTRMod.wood2, 1, LOTRMod.leaves2, 1);
      }
   }),
   BEECH_PARTY(new LOTRTreeType.ITreeFactory() {
      public WorldGenAbstractTree createTree(boolean flag, Random rand) {
         return new LOTRWorldGenPartyTrees(LOTRMod.wood2, 1, LOTRMod.leaves2, 1);
      }
   }),
   BEECH_FANGORN(new LOTRTreeType.ITreeFactory() {
      public WorldGenAbstractTree createTree(boolean flag, Random rand) {
         return new LOTRWorldGenFangornTrees(flag, LOTRMod.wood2, 1, LOTRMod.leaves2, 1);
      }
   }),
   BEECH_FANGORN_DEAD(new LOTRTreeType.ITreeFactory() {
      public WorldGenAbstractTree createTree(boolean flag, Random rand) {
         return (new LOTRWorldGenFangornTrees(flag, LOTRMod.wood2, 1, LOTRMod.leaves2, 1)).setNoLeaves();
      }
   }),
   BEECH_DEAD(new LOTRTreeType.ITreeFactory() {
      public WorldGenAbstractTree createTree(boolean flag, Random rand) {
         return new LOTRWorldGenDeadTrees(LOTRMod.wood2, 1);
      }
   }),
   HOLLY(new LOTRTreeType.ITreeFactory() {
      public WorldGenAbstractTree createTree(boolean flag, Random rand) {
         return new LOTRWorldGenHolly(flag);
      }
   }),
   HOLLY_LARGE(new LOTRTreeType.ITreeFactory() {
      public WorldGenAbstractTree createTree(boolean flag, Random rand) {
         return (new LOTRWorldGenHolly(flag)).setLarge();
      }
   }),
   BANANA(new LOTRTreeType.ITreeFactory() {
      public WorldGenAbstractTree createTree(boolean flag, Random rand) {
         return new LOTRWorldGenBanana(flag);
      }
   }),
   MAPLE(new LOTRTreeType.ITreeFactory() {
      public WorldGenAbstractTree createTree(boolean flag, Random rand) {
         return new LOTRWorldGenSimpleTrees(flag, 4, 8, LOTRMod.wood3, 0, LOTRMod.leaves3, 0);
      }
   }),
   MAPLE_LARGE(new LOTRTreeType.ITreeFactory() {
      public WorldGenAbstractTree createTree(boolean flag, Random rand) {
         return new LOTRWorldGenBigTrees(flag, LOTRMod.wood3, 0, LOTRMod.leaves3, 0);
      }
   }),
   MAPLE_PARTY(new LOTRTreeType.ITreeFactory() {
      public WorldGenAbstractTree createTree(boolean flag, Random rand) {
         return new LOTRWorldGenPartyTrees(LOTRMod.wood3, 0, LOTRMod.leaves3, 0);
      }
   }),
   LARCH(new LOTRTreeType.ITreeFactory() {
      public WorldGenAbstractTree createTree(boolean flag, Random rand) {
         return new LOTRWorldGenLarch(flag);
      }
   }),
   DATE_PALM(new LOTRTreeType.ITreeFactory() {
      public WorldGenAbstractTree createTree(boolean flag, Random rand) {
         return (new LOTRWorldGenPalm(flag, LOTRMod.wood3, 2, LOTRMod.leaves3, 2)).setMinMaxHeight(5, 8).setDates();
      }
   }),
   MANGROVE(new LOTRTreeType.ITreeFactory() {
      public WorldGenAbstractTree createTree(boolean flag, Random rand) {
         return new LOTRWorldGenMangrove(flag);
      }
   }),
   CHESTNUT(new LOTRTreeType.ITreeFactory() {
      public WorldGenAbstractTree createTree(boolean flag, Random rand) {
         return new LOTRWorldGenSimpleTrees(flag, 5, 7, LOTRMod.wood4, 0, LOTRMod.leaves4, 0);
      }
   }),
   CHESTNUT_LARGE(new LOTRTreeType.ITreeFactory() {
      public WorldGenAbstractTree createTree(boolean flag, Random rand) {
         return new LOTRWorldGenBigTrees(flag, LOTRMod.wood4, 0, LOTRMod.leaves4, 0);
      }
   }),
   CHESTNUT_PARTY(new LOTRTreeType.ITreeFactory() {
      public WorldGenAbstractTree createTree(boolean flag, Random rand) {
         return new LOTRWorldGenPartyTrees(LOTRMod.wood4, 0, LOTRMod.leaves4, 0);
      }
   }),
   BAOBAB(new LOTRTreeType.ITreeFactory() {
      public WorldGenAbstractTree createTree(boolean flag, Random rand) {
         return new LOTRWorldGenBaobab(flag);
      }
   }),
   CEDAR(new LOTRTreeType.ITreeFactory() {
      public WorldGenAbstractTree createTree(boolean flag, Random rand) {
         return new LOTRWorldGenCedar(flag);
      }
   }),
   CEDAR_LARGE(new LOTRTreeType.ITreeFactory() {
      public WorldGenAbstractTree createTree(boolean flag, Random rand) {
         return (new LOTRWorldGenCedar(flag)).setMinMaxHeight(15, 30);
      }
   }),
   FIR(new LOTRTreeType.ITreeFactory() {
      public WorldGenAbstractTree createTree(boolean flag, Random rand) {
         return new LOTRWorldGenFir(flag);
      }
   }),
   PINE(new LOTRTreeType.ITreeFactory() {
      public WorldGenAbstractTree createTree(boolean flag, Random rand) {
         return new LOTRWorldGenPine(flag);
      }
   }),
   PINE_SHRUB(new LOTRTreeType.ITreeFactory() {
      public WorldGenAbstractTree createTree(boolean flag, Random rand) {
         return new LOTRWorldGenShrub(LOTRMod.wood5, 0, LOTRMod.leaves5, 0);
      }
   }),
   LEMON(new LOTRTreeType.ITreeFactory() {
      public WorldGenAbstractTree createTree(boolean flag, Random rand) {
         return (WorldGenAbstractTree)(rand.nextInt(3) == 0 ? (new LOTRWorldGenOlive(flag)).setBlocks(LOTRMod.wood5, 1, LOTRMod.leaves5, 1) : new LOTRWorldGenDesertTrees(flag, LOTRMod.wood5, 1, LOTRMod.leaves5, 1));
      }
   }),
   ORANGE(new LOTRTreeType.ITreeFactory() {
      public WorldGenAbstractTree createTree(boolean flag, Random rand) {
         return (WorldGenAbstractTree)(rand.nextInt(3) == 0 ? (new LOTRWorldGenOlive(flag)).setBlocks(LOTRMod.wood5, 2, LOTRMod.leaves5, 2) : new LOTRWorldGenDesertTrees(flag, LOTRMod.wood5, 2, LOTRMod.leaves5, 2));
      }
   }),
   LIME(new LOTRTreeType.ITreeFactory() {
      public WorldGenAbstractTree createTree(boolean flag, Random rand) {
         return (WorldGenAbstractTree)(rand.nextInt(3) == 0 ? (new LOTRWorldGenOlive(flag)).setBlocks(LOTRMod.wood5, 3, LOTRMod.leaves5, 3) : new LOTRWorldGenDesertTrees(flag, LOTRMod.wood5, 3, LOTRMod.leaves5, 3));
      }
   }),
   MAHOGANY(new LOTRTreeType.ITreeFactory() {
      public WorldGenAbstractTree createTree(boolean flag, Random rand) {
         return (new LOTRWorldGenCedar(flag)).setBlocks(LOTRMod.wood6, 0, LOTRMod.leaves6, 0).setHangingLeaves();
      }
   }),
   MAHOGANY_FANGORN(new LOTRTreeType.ITreeFactory() {
      public WorldGenAbstractTree createTree(boolean flag, Random rand) {
         return (new LOTRWorldGenFangornTrees(flag, LOTRMod.wood6, 0, LOTRMod.leaves6, 0)).setHeightFactor(1.5F);
      }
   }),
   WILLOW(new LOTRTreeType.ITreeFactory() {
      public WorldGenAbstractTree createTree(boolean flag, Random rand) {
         return new LOTRWorldGenWillow(flag);
      }
   }),
   WILLOW_WATER(new LOTRTreeType.ITreeFactory() {
      public WorldGenAbstractTree createTree(boolean flag, Random rand) {
         return (new LOTRWorldGenWillow(flag)).setNeedsWater();
      }
   }),
   CYPRESS(new LOTRTreeType.ITreeFactory() {
      public WorldGenAbstractTree createTree(boolean flag, Random rand) {
         return new LOTRWorldGenCypress(flag);
      }
   }),
   CYPRESS_LARGE(new LOTRTreeType.ITreeFactory() {
      public WorldGenAbstractTree createTree(boolean flag, Random rand) {
         return (new LOTRWorldGenCypress(flag)).setLarge();
      }
   }),
   OLIVE(new LOTRTreeType.ITreeFactory() {
      public WorldGenAbstractTree createTree(boolean flag, Random rand) {
         return new LOTRWorldGenOlive(flag);
      }
   }),
   OLIVE_LARGE(new LOTRTreeType.ITreeFactory() {
      public WorldGenAbstractTree createTree(boolean flag, Random rand) {
         return (new LOTRWorldGenOlive(flag)).setMinMaxHeight(5, 8).setExtraTrunkWidth(1);
      }
   }),
   ASPEN(new LOTRTreeType.ITreeFactory() {
      public WorldGenAbstractTree createTree(boolean flag, Random rand) {
         return new LOTRWorldGenAspen(flag);
      }
   }),
   ASPEN_LARGE(new LOTRTreeType.ITreeFactory() {
      public WorldGenAbstractTree createTree(boolean flag, Random rand) {
         return (new LOTRWorldGenAspen(flag)).setExtraTrunkWidth(1).setMinMaxHeight(14, 25);
      }
   }),
   GREEN_OAK(new LOTRTreeType.ITreeFactory() {
      public WorldGenAbstractTree createTree(boolean flag, Random rand) {
         return (new LOTRWorldGenMirkOak(flag, 4, 7, 0, false)).setGreenOak();
      }
   }),
   GREEN_OAK_LARGE(new LOTRTreeType.ITreeFactory() {
      public WorldGenAbstractTree createTree(boolean flag, Random rand) {
         return (new LOTRWorldGenMirkOak(flag, 12, 16, 1, false)).setGreenOak();
      }
   }),
   GREEN_OAK_EXTREME(new LOTRTreeType.ITreeFactory() {
      public WorldGenAbstractTree createTree(boolean flag, Random rand) {
         return (new LOTRWorldGenMirkOak(flag, 25, 45, 2, false)).setGreenOak();
      }
   }),
   LAIRELOSSE(new LOTRTreeType.ITreeFactory() {
      public WorldGenAbstractTree createTree(boolean flag, Random rand) {
         return new LOTRWorldGenLairelosse(flag);
      }
   }),
   LAIRELOSSE_LARGE(new LOTRTreeType.ITreeFactory() {
      public WorldGenAbstractTree createTree(boolean flag, Random rand) {
         return (new LOTRWorldGenLairelosse(flag)).setExtraTrunkWidth(1).setMinMaxHeight(8, 12);
      }
   }),
   ALMOND(new LOTRTreeType.ITreeFactory() {
      public WorldGenAbstractTree createTree(boolean flag, Random rand) {
         return new LOTRWorldGenAlmond(flag);
      }
   }),
   PLUM(new LOTRTreeType.ITreeFactory() {
      public WorldGenAbstractTree createTree(boolean flag, Random rand) {
         return new LOTRWorldGenSimpleTrees(flag, 4, 6, LOTRMod.wood8, 0, LOTRMod.leaves8, 0);
      }
   }),
   REDWOOD(new LOTRTreeType.ITreeFactory() {
      public WorldGenAbstractTree createTree(boolean flag, Random rand) {
         return new LOTRWorldGenRedwood(flag);
      }
   }),
   REDWOOD_2(new LOTRTreeType.ITreeFactory() {
      public WorldGenAbstractTree createTree(boolean flag, Random rand) {
         return (new LOTRWorldGenRedwood(flag)).setExtraTrunkWidth(1);
      }
   }),
   REDWOOD_3(new LOTRTreeType.ITreeFactory() {
      public WorldGenAbstractTree createTree(boolean flag, Random rand) {
         return (new LOTRWorldGenRedwood(flag)).setTrunkWidth(1);
      }
   }),
   REDWOOD_4(new LOTRTreeType.ITreeFactory() {
      public WorldGenAbstractTree createTree(boolean flag, Random rand) {
         return (new LOTRWorldGenRedwood(flag)).setTrunkWidth(1).setExtraTrunkWidth(1);
      }
   }),
   REDWOOD_5(new LOTRTreeType.ITreeFactory() {
      public WorldGenAbstractTree createTree(boolean flag, Random rand) {
         return (new LOTRWorldGenRedwood(flag)).setTrunkWidth(2);
      }
   }),
   POMEGRANATE(new LOTRTreeType.ITreeFactory() {
      public WorldGenAbstractTree createTree(boolean flag, Random rand) {
         return (WorldGenAbstractTree)(rand.nextInt(3) == 0 ? (new LOTRWorldGenOlive(flag)).setBlocks(LOTRMod.wood8, 2, LOTRMod.leaves8, 2) : new LOTRWorldGenDesertTrees(flag, LOTRMod.wood8, 2, LOTRMod.leaves8, 2));
      }
   }),
   PALM(new LOTRTreeType.ITreeFactory() {
      public WorldGenAbstractTree createTree(boolean flag, Random rand) {
         return (new LOTRWorldGenPalm(flag, LOTRMod.wood8, 3, LOTRMod.leaves8, 3)).setMinMaxHeight(6, 11);
      }
   }),
   DRAGONBLOOD(new LOTRTreeType.ITreeFactory() {
      public WorldGenAbstractTree createTree(boolean flag, Random rand) {
         return new LOTRWorldGenDragonblood(flag, 3, 7, 0);
      }
   }),
   DRAGONBLOOD_LARGE(new LOTRTreeType.ITreeFactory() {
      public WorldGenAbstractTree createTree(boolean flag, Random rand) {
         return new LOTRWorldGenDragonblood(flag, 6, 10, 1);
      }
   }),
   DRAGONBLOOD_HUGE(new LOTRTreeType.ITreeFactory() {
      public WorldGenAbstractTree createTree(boolean flag, Random rand) {
         return new LOTRWorldGenDragonblood(flag, 8, 16, 2);
      }
   }),
   KANUKA(new LOTRTreeType.ITreeFactory() {
      public WorldGenAbstractTree createTree(boolean flag, Random rand) {
         return new LOTRWorldGenKanuka(flag);
      }
   }),
   NULL((LOTRTreeType.ITreeFactory)null);

   private LOTRTreeType.ITreeFactory treeFactory;

   private LOTRTreeType(LOTRTreeType.ITreeFactory factory) {
      this.treeFactory = factory;
   }

   public WorldGenAbstractTree create(boolean flag, Random rand) {
      return this.treeFactory.createTree(flag, rand);
   }

   public static class WeightedTreeType extends Item {
      public final LOTRTreeType treeType;

      public WeightedTreeType(LOTRTreeType tree, int i) {
         super(i);
         this.treeType = tree;
      }
   }

   private interface ITreeFactory {
      WorldGenAbstractTree createTree(boolean var1, Random var2);
   }
}
