package lotr.common.world.gen.tree;

import com.mojang.serialization.Codec;
import net.minecraft.block.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.TreeFeature;
import net.minecraft.world.gen.trunkplacer.TrunkPlacerType;

public class LOTRTrunkPlacers {
   public static TrunkPlacerType DEAD_TRUNK_PLACER;
   public static TrunkPlacerType DESERT_TRUNK_PLACER;
   public static TrunkPlacerType BOUGHS_TRUNK_PLACER;
   public static TrunkPlacerType CEDAR_TRUNK_PLACER;
   public static TrunkPlacerType MIRK_OAK_TRUNK_PLACER;
   public static TrunkPlacerType FANGORN_TRUNK_PLACER;
   public static TrunkPlacerType PARTY_TRUNK_PLACER;

   public static void register() {
      DEAD_TRUNK_PLACER = registerModded("dead_trunk_placer", DeadTrunkPlacer.CODEC);
      DESERT_TRUNK_PLACER = registerModded("desert_trunk_placer", DesertTrunkPlacer.CODEC);
      BOUGHS_TRUNK_PLACER = registerModded("boughs_trunk_placer", BoughsTrunkPlacer.CODEC);
      CEDAR_TRUNK_PLACER = registerModded("cedar_trunk_placer", CedarTrunkPlacer.CODEC);
      MIRK_OAK_TRUNK_PLACER = registerModded("mirk_oak_trunk_placer", MirkOakTrunkPlacer.CODEC);
      FANGORN_TRUNK_PLACER = registerModded("fangorn_trunk_placer", FangornTrunkPlacer.CODEC);
      PARTY_TRUNK_PLACER = registerModded("party_trunk_placer", PartyTrunkPlacer.CODEC);
   }

   private static TrunkPlacerType registerModded(String name, Codec codec) {
      TrunkPlacerType type = new TrunkPlacerType(codec);
      return (TrunkPlacerType)Registry.func_218322_a(Registry.field_239701_aw_, new ResourceLocation("lotr", name), type);
   }

   public static void setGrassToDirt(IWorldGenerationReader world, BlockPos groundPos) {
      if (world.func_217375_a(groundPos, (state) -> {
         return state.func_203425_a(Blocks.field_196658_i) || state.func_203425_a(Blocks.field_150391_bh);
      })) {
         TreeFeature.func_236408_b_(world, groundPos, Blocks.field_150346_d.func_176223_P());
      }

   }
}
