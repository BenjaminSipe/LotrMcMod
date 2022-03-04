package lotr.common.block.trees;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Stream;
import lotr.common.world.biome.LOTRBiomeFeatures;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.server.ServerWorld;

public class VanillaSaplingPartyTrees {
   private static final Map partyTrees = new HashMap();

   private static void putPartyTree(Block block, PartyTreeLogic.PartyTreeProvider tree) {
      if (block.getRegistryName().func_110624_b().equals("lotr")) {
         throw new IllegalArgumentException("Saplings from the mod itself should be handled directly with PartyTreeLogic in their own Tree instances! This workaround is for vanilla saplings.");
      } else {
         partyTrees.put(block.getRegistryName(), new PartyTreeLogic(tree));
      }
   }

   public static boolean attemptGrowPartyTree(BlockState sapling, ServerWorld sWorld, ChunkGenerator chunkGen, BlockPos pos, Random rand) {
      Stream var10000 = partyTrees.keySet().stream();
      ResourceLocation var10001 = sapling.func_177230_c().getRegistryName();
      var10001.getClass();
      Optional matchingBlockName = var10000.filter(var10001::equals).findFirst();
      if (matchingBlockName.isPresent()) {
         PartyTreeLogic partyTree = (PartyTreeLogic)partyTrees.get(matchingBlockName.get());
         if (partyTree.attemptGrowPartyTree(sWorld, chunkGen, pos, sapling, rand)) {
            return true;
         }
      }

      return false;
   }

   static {
      putPartyTree(Blocks.field_196674_t, (rand) -> {
         return LOTRBiomeFeatures.oakParty();
      });
      putPartyTree(Blocks.field_196676_v, (rand) -> {
         return LOTRBiomeFeatures.birchParty();
      });
      putPartyTree(Blocks.field_196680_y, (rand) -> {
         return LOTRBiomeFeatures.darkOakParty();
      });
   }
}
