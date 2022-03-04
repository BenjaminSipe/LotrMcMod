package lotr.common.init;

import java.util.Optional;
import lotr.common.config.ClientsideCurrentServerConfigSettings;
import lotr.common.world.gen.MiddleEarthChunkGenerator;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.SimpleRegistry;
import net.minecraft.world.Dimension;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.settings.DimensionGeneratorSettings;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.world.ForgeWorldType;
import net.minecraftforge.common.world.ForgeWorldType.IChunkGeneratorFactory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class LOTRWorldTypes {
   public static final DeferredRegister WORLD_TYPES;
   public static final RegistryObject MIDDLE_EARTH;
   public static final RegistryObject MIDDLE_EARTH_CLASSIC;

   public static void register() {
      IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
      WORLD_TYPES.register(bus);
   }

   private static IChunkGeneratorFactory createMiddleEarthWorldType(final boolean classicBiomes) {
      return new IChunkGeneratorFactory() {
         public ChunkGenerator createChunkGenerator(Registry biomeRegistry, Registry dimensionSettingsRegistry, long seed, String generatorSettings) {
            return LOTRWorldTypes.createDefaultOverworldChunkGenerator(biomeRegistry, dimensionSettingsRegistry, seed);
         }

         public DimensionGeneratorSettings createSettings(DynamicRegistries dynamicRegistries, long seed, boolean generateStructures, boolean bonusChest, String generatorSettings) {
            IChunkGeneratorFactory defaultMethodProxy = this::createChunkGenerator;
            DimensionGeneratorSettings settings = defaultMethodProxy.createSettings(dynamicRegistries, seed, generateStructures, bonusChest, generatorSettings);
            MiddleEarthChunkGenerator meChunkgen = (MiddleEarthChunkGenerator)LOTRWorldTypes.findMiddleEarthChunkGeneratorFromSettings(settings).orElseThrow(() -> {
               return new IllegalStateException("Expected to find a MiddleEarthChunkGenerator in new Middle-earth worldgen settings - this is a development error.");
            });
            meChunkgen.hackySetWorldTypeInstantMiddleEarth(true);
            meChunkgen.hackySetWorldTypeClassicBiomes(classicBiomes);
            return settings;
         }
      };
   }

   private static ChunkGenerator createDefaultOverworldChunkGenerator(Registry biomeReg, Registry dimSettingsReg, long seed) {
      return DimensionGeneratorSettings.func_242750_a(biomeReg, dimSettingsReg, seed);
   }

   public static boolean hasMapFeatures(ServerWorld world) {
      DimensionGeneratorSettings dimGenSettings = world.func_73046_m().func_240793_aU_().func_230418_z_();
      Optional meChunkgen = findMiddleEarthChunkGeneratorFromSettings(dimGenSettings);
      return (Boolean)meChunkgen.map((cg) -> {
         return !cg.isClassicBiomes();
      }).orElse(true);
   }

   public static boolean hasMapFeaturesClientside() {
      return ClientsideCurrentServerConfigSettings.INSTANCE.hasMapFeatures;
   }

   public static boolean isInstantME(ServerWorld world) {
      DimensionGeneratorSettings dimGenSettings = world.func_73046_m().func_240793_aU_().func_230418_z_();
      Optional meChunkgen = findMiddleEarthChunkGeneratorFromSettings(dimGenSettings);
      return (Boolean)meChunkgen.map(MiddleEarthChunkGenerator::isInstantMiddleEarth).orElse(false);
   }

   private static Optional findMiddleEarthChunkGeneratorFromSettings(DimensionGeneratorSettings dimGenSettings) {
      SimpleRegistry dimRegistry = dimGenSettings.func_236224_e_();
      Dimension middleEarth = (Dimension)dimRegistry.func_230516_a_(LOTRDimensions.MIDDLE_EARTH_DIM_KEY);
      if (middleEarth != null) {
         ChunkGenerator chunkGen = middleEarth.func_236064_c_();
         if (chunkGen instanceof MiddleEarthChunkGenerator) {
            return Optional.of((MiddleEarthChunkGenerator)chunkGen);
         }
      }

      return Optional.empty();
   }

   static {
      WORLD_TYPES = DeferredRegister.create(ForgeRegistries.WORLD_TYPES, "lotr");
      MIDDLE_EARTH = WORLD_TYPES.register("me", () -> {
         return new ForgeWorldType(createMiddleEarthWorldType(false));
      });
      MIDDLE_EARTH_CLASSIC = WORLD_TYPES.register("me_classic", () -> {
         return new ForgeWorldType(createMiddleEarthWorldType(true));
      });
   }
}
