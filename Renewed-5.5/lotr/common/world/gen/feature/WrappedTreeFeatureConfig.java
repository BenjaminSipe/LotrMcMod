package lotr.common.world.gen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.function.Predicate;
import lotr.common.init.LOTRTags;
import net.minecraft.block.BlockState;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraftforge.common.Tags.Blocks;

public class WrappedTreeFeatureConfig implements IFeatureConfig {
   public static final Codec CODEC = RecordCodecBuilder.create((instance) -> {
      return instance.group(BaseTreeFeatureConfig.field_236676_a_.fieldOf("tree_config").forGetter((config) -> {
         return config.treeConfig;
      }), WrappedTreeFeatureConfig.AlternativeTreeSoil.CODEC.fieldOf("alternative_soil_type").forGetter((config) -> {
         return config.alternativeSoilType;
      })).apply(instance, WrappedTreeFeatureConfig::new);
   });
   public final BaseTreeFeatureConfig treeConfig;
   public final WrappedTreeFeatureConfig.AlternativeTreeSoil alternativeSoilType;

   public WrappedTreeFeatureConfig(BaseTreeFeatureConfig tree, WrappedTreeFeatureConfig.AlternativeTreeSoil soil) {
      this.treeConfig = tree;
      this.alternativeSoilType = soil;
   }

   public static enum AlternativeTreeSoil implements IStringSerializable {
      DESERT("desert", (state) -> {
         return state.func_235714_a_(BlockTags.field_203436_u) || state.func_235714_a_(Blocks.SANDSTONE) || state.func_177230_c() == net.minecraft.block.Blocks.field_150348_b;
      }),
      CHARRED("mordor", (state) -> {
         return state.func_235714_a_(LOTRTags.Blocks.MORDOR_PLANT_SURFACES) || state.func_177230_c() == net.minecraft.block.Blocks.field_150348_b;
      }),
      SNOWY("snowy", (state) -> {
         return state.func_177230_c() == net.minecraft.block.Blocks.field_196604_cC || state.func_177230_c() == net.minecraft.block.Blocks.field_150348_b;
      });

      public static final Codec CODEC = IStringSerializable.func_233023_a_(WrappedTreeFeatureConfig.AlternativeTreeSoil::values, WrappedTreeFeatureConfig.AlternativeTreeSoil::forCode);
      private final String code;
      private final Predicate blockStateTest;

      private AlternativeTreeSoil(String s, Predicate test) {
         this.code = s;
         this.blockStateTest = test;
      }

      public String func_176610_l() {
         return this.code;
      }

      public boolean testTerrain(BlockState state) {
         return this.blockStateTest.test(state);
      }

      public static WrappedTreeFeatureConfig.AlternativeTreeSoil forCode(String code) {
         WrappedTreeFeatureConfig.AlternativeTreeSoil[] var1 = values();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            WrappedTreeFeatureConfig.AlternativeTreeSoil type = var1[var3];
            if (type.func_176610_l().equals(code)) {
               return type;
            }
         }

         return null;
      }
   }
}
