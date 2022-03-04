package lotr.common.world.gen.carver;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSet.Builder;
import java.util.Set;
import lotr.common.init.LOTRBlocks;
import lotr.common.init.RegistryOrderHelper;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.carver.WorldCarver;
import net.minecraft.world.gen.feature.ProbabilityConfig;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class LOTRWorldCarvers {
   public static final DeferredRegister WORLD_CARVERS;
   public static final WorldCarver CAVE;
   public static final WorldCarver CANYON;
   public static final WorldCarver UNDERWATER_CAVE;
   public static final WorldCarver UNDERWATER_CANYON;

   public static void register() {
      IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
      WORLD_CARVERS.register(bus);
   }

   private static WorldCarver preRegCarver(String name, WorldCarver carver) {
      return (WorldCarver)RegistryOrderHelper.preRegObject(WORLD_CARVERS, name, carver);
   }

   public static Set listCarvableBlocks() {
      return ImmutableSet.of(Blocks.field_150348_b, Blocks.field_196650_c, Blocks.field_196654_e, Blocks.field_196656_g, LOTRBlocks.MORDOR_ROCK.get(), LOTRBlocks.GONDOR_ROCK.get(), new Block[]{(Block)LOTRBlocks.ROHAN_ROCK.get(), (Block)LOTRBlocks.BLUE_ROCK.get(), (Block)LOTRBlocks.RED_ROCK.get(), (Block)LOTRBlocks.CHALK.get(), (Block)LOTRBlocks.DIRTY_CHALK.get(), (Block)LOTRBlocks.CHALK_PATH.get(), Blocks.field_150346_d, Blocks.field_196660_k, Blocks.field_196661_l, Blocks.field_196658_i, Blocks.field_185774_da, (Block)LOTRBlocks.MORDOR_DIRT.get(), (Block)LOTRBlocks.MORDOR_DIRT_PATH.get(), Blocks.field_150322_A, Blocks.field_180395_cM, (Block)LOTRBlocks.WHITE_SANDSTONE.get(), Blocks.field_150391_bh, Blocks.field_150433_aE, Blocks.field_196604_cC, Blocks.field_150403_cj, (Block)LOTRBlocks.SNOW_PATH.get()});
   }

   public static Set listLandOnlyCarvableBlocks() {
      return ImmutableSet.of(Blocks.field_150354_m, Blocks.field_196611_F, LOTRBlocks.WHITE_SAND.get(), Blocks.field_150351_n, LOTRBlocks.MORDOR_GRAVEL.get());
   }

   public static Set listUnderwaterCarvableBlocks() {
      return (new Builder()).addAll(listCarvableBlocks()).addAll(listLandOnlyCarvableBlocks()).add(new Object[]{Blocks.field_150355_j, Blocks.field_150353_l, Blocks.field_150343_Z, Blocks.field_150350_a, Blocks.field_201941_jj}).build();
   }

   static {
      WORLD_CARVERS = DeferredRegister.create(ForgeRegistries.WORLD_CARVERS, "lotr");
      CAVE = preRegCarver("cave", new MiddleEarthCaveCarver(ProbabilityConfig.field_236576_b_, 256));
      CANYON = preRegCarver("canyon", new MiddleEarthCanyonCarver(ProbabilityConfig.field_236576_b_));
      UNDERWATER_CAVE = preRegCarver("underwater_cave", new MiddleEarthUnderwaterCaveCarver(ProbabilityConfig.field_236576_b_));
      UNDERWATER_CANYON = preRegCarver("underwater_canyon", new MiddleEarthUnderwaterCanyonCarver(ProbabilityConfig.field_236576_b_));
   }
}
