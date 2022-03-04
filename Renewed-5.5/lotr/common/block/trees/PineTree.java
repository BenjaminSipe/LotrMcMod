package lotr.common.block.trees;

import java.lang.reflect.InvocationTargetException;
import java.util.Random;
import lotr.common.LOTRLog;
import lotr.common.init.LOTRBiomes;
import lotr.common.world.biome.LOTRBiomeFeatures;
import lotr.common.world.biome.ShireBiome;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.trees.Tree;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

public class PineTree extends Tree {
   public boolean func_230339_a_(ServerWorld world, ChunkGenerator chunkGen, BlockPos pos, BlockState state, Random rand) {
      boolean nearbyFlowers = false;

      try {
         nearbyFlowers = (Boolean)ObfuscationReflectionHelper.findMethod(Tree.class, "func_230140_a_", new Class[]{IWorld.class, BlockPos.class}).invoke(this, world, pos);
      } catch (IllegalArgumentException | InvocationTargetException | IllegalAccessException var10) {
         LOTRLog.error("Error determining nearby flowers for Pine tree growth");
         var10.printStackTrace();
      }

      Biome biome = world.func_226691_t_(pos);
      boolean isShire = LOTRBiomes.getWrapperFor(biome, world) instanceof ShireBiome;
      ConfiguredFeature treeGen = isShire ? this.getTreeFeatureShire(rand, nearbyFlowers) : this.func_225546_b_(rand, nearbyFlowers);
      if (treeGen == null) {
         return false;
      } else {
         world.func_180501_a(pos, Blocks.field_150350_a.func_176223_P(), 4);
         ((BaseTreeFeatureConfig)treeGen.field_222738_b).func_227373_a_();
         if (treeGen.func_242765_a(world, chunkGen, rand, pos)) {
            return true;
         } else {
            world.func_180501_a(pos, state, 4);
            return false;
         }
      }
   }

   protected ConfiguredFeature func_225546_b_(Random rand, boolean bees) {
      return LOTRBiomeFeatures.pine();
   }

   protected ConfiguredFeature getTreeFeatureShire(Random rand, boolean bees) {
      return LOTRBiomeFeatures.shirePine();
   }
}
