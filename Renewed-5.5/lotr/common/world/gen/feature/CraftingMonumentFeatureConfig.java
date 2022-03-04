package lotr.common.world.gen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.world.gen.blockstateprovider.BlockStateProvider;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class CraftingMonumentFeatureConfig implements IFeatureConfig {
   public static final Codec CODEC = RecordCodecBuilder.create((instance) -> {
      return instance.group(BlockState.field_235877_b_.fieldOf("table_state").forGetter((config) -> {
         return config.craftingTable;
      }), BlockStateProvider.field_236796_a_.fieldOf("base_provider").forGetter((config) -> {
         return config.baseBlockProvider;
      }), BlockStateProvider.field_236796_a_.fieldOf("post_provider").forGetter((config) -> {
         return config.postProvider;
      }), BlockStateProvider.field_236796_a_.fieldOf("torch_provider").forGetter((config) -> {
         return config.torchProvider;
      })).apply(instance, CraftingMonumentFeatureConfig::new);
   });
   public final BlockState craftingTable;
   public final BlockStateProvider baseBlockProvider;
   public final BlockStateProvider postProvider;
   public final BlockStateProvider torchProvider;

   public CraftingMonumentFeatureConfig(BlockState table, BlockStateProvider base, BlockStateProvider post, BlockStateProvider torch) {
      this.craftingTable = table;
      this.baseBlockProvider = base;
      this.postProvider = post;
      this.torchProvider = torch;
   }
}
