package lotr.common.init;

import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Lifecycle;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;
import lotr.common.LOTRLog;
import lotr.common.dim.LOTRDimensionType;
import lotr.common.dim.MiddleEarthDimensionType;
import lotr.common.fac.Faction;
import lotr.common.util.LOTRUtil;
import lotr.common.world.biome.provider.MiddleEarthBiomeProvider;
import lotr.common.world.gen.MiddleEarthChunkGenerator;
import net.minecraft.block.Blocks;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.MutableRegistry;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.SimpleRegistry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.Dimension;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.DimensionSettings;
import net.minecraft.world.gen.settings.DimensionGeneratorSettings;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.NoiseSettings;
import net.minecraft.world.gen.settings.ScalingSettings;
import net.minecraft.world.gen.settings.SlideSettings;
import net.minecraft.world.server.ServerWorld;

public class LOTRDimensions {
   public static final ResourceLocation MIDDLE_EARTH_ID = new ResourceLocation("lotr", "middle_earth");
   public static final LOTRDimensionType MIDDLE_EARTH_DIMTYPE;
   public static final RegistryKey MIDDLE_EARTH_DIMTYPE_KEY;
   public static final RegistryKey MIDDLE_EARTH_DIM_KEY;
   public static final RegistryKey MIDDLE_EARTH_WORLD_KEY;
   private static Set addedDimensionKeys;
   public static final RegistryKey MIDDLE_EARTH_DIMSETTINGS_KEY;
   public static final DimensionSettings MIDDLE_EARTH_DIMSETTINGS;

   public static LOTRDimensionType dispatchModDimensionType(ResourceLocation dimensionId) {
      if (dimensionId.equals(MIDDLE_EARTH_ID)) {
         return new MiddleEarthDimensionType(MIDDLE_EARTH_ID);
      } else {
         throw new IllegalArgumentException("Dimension ID " + dimensionId + " is not a known LOTR mod dimension and cannot be dispatched to a LOTRDimensionType!");
      }
   }

   private static RegistryKey createDimensionKey(ResourceLocation res) {
      RegistryKey key = RegistryKey.func_240903_a_(Registry.field_239700_af_, res);
      if (addedDimensionKeys == null) {
         addedDimensionKeys = new HashSet();
      }

      addedDimensionKeys.add(key);
      return key;
   }

   public static boolean isAddedDimension(RegistryKey key) {
      return addedDimensionKeys.contains(key);
   }

   public static Set viewAddedDimensions() {
      return ImmutableSet.copyOf(addedDimensionKeys);
   }

   public static void registerDimensionTypes(MutableRegistry dimTypeReg) {
      dimTypeReg.func_218381_a(MIDDLE_EARTH_DIMTYPE_KEY, MIDDLE_EARTH_DIMTYPE, Lifecycle.stable());
   }

   public static void registerWorldDimensions(SimpleRegistry dimReg, Registry dimTypeReg, Registry biomeReg, Registry dimSettingsReg, long seed) {
      Iterator var6 = viewAddedDimensions().iterator();

      while(var6.hasNext()) {
         RegistryKey modDimension = (RegistryKey)var6.next();
         addSpecificDimensionToWorldRegistry(modDimension, dimReg, dimTypeReg, biomeReg, dimSettingsReg, seed);
      }

   }

   public static void addSpecificDimensionToWorldRegistry(RegistryKey dimKey, SimpleRegistry dimReg, Registry dimTypeReg, Registry biomeReg, Registry dimSettingsReg, long seed) {
      if (dimKey.equals(MIDDLE_EARTH_DIM_KEY)) {
         dimReg.func_218381_a(dimKey, new Dimension(() -> {
            return (DimensionType)dimTypeReg.func_243576_d(MIDDLE_EARTH_DIMTYPE_KEY);
         }, createMiddleEarthChunkGenerator(biomeReg, dimSettingsReg, seed)), Lifecycle.stable());
      } else {
         throw new IllegalArgumentException("Coding error! LOTR mod somehow tried to add an unknown dimension (" + dimKey.func_240901_a_() + ") to the world registry - it isn't one of ours!");
      }
   }

   private static ChunkGenerator createMiddleEarthChunkGenerator(Registry biomeReg, Registry dimSettingsReg, long seed) {
      boolean classicBiomes = false;
      return new MiddleEarthChunkGenerator(new MiddleEarthBiomeProvider(seed, classicBiomes, biomeReg), seed, () -> {
         return (DimensionSettings)dimSettingsReg.func_243576_d(MIDDLE_EARTH_DIMSETTINGS_KEY);
      }, Optional.empty());
   }

   private static DimensionSettings createMiddleEarthDimensionSettings() {
      DimensionStructuresSettings dimStrSettings = new DimensionStructuresSettings(false);
      boolean isAmplified = false;
      double d0 = 0.9999999814507745D;
      return new DimensionSettings(dimStrSettings, new NoiseSettings(256, new ScalingSettings(d0, d0, 80.0D, 160.0D), new SlideSettings(-10, 3, 0), new SlideSettings(-30, 0, 0), 1, 2, 1.0D, -0.46875D, true, true, false, isAmplified), Blocks.field_150348_b.func_176223_P(), Blocks.field_150355_j.func_176223_P(), -10, 0, 63, false);
   }

   public static void registerAssociated() {
      replaceDimensionCodecToForceStability();
      Registry.func_218322_a(Registry.field_239690_aB_, new ResourceLocation("lotr", "middle_earth"), MiddleEarthChunkGenerator.CODEC);
      Registry.func_218322_a(Registry.field_239689_aA_, new ResourceLocation("lotr", "middle_earth"), MiddleEarthBiomeProvider.CODEC);
      WorldGenRegistries.func_243664_a(WorldGenRegistries.field_243658_j, MIDDLE_EARTH_DIMSETTINGS_KEY.func_240901_a_(), MIDDLE_EARTH_DIMSETTINGS);
   }

   public static boolean isDimension(World world, RegistryKey dimension) {
      return world.func_234923_W_().equals(dimension);
   }

   public static boolean isDimension(Faction fac, RegistryKey dimension) {
      return fac.getDimension().equals(dimension);
   }

   public static boolean isModDimension(World world) {
      return world.func_230315_m_() instanceof LOTRDimensionType;
   }

   public static RegistryKey getCurrentLOTRDimensionOrFallback(World world) {
      DimensionType dimension = world.func_230315_m_();
      return dimension instanceof LOTRDimensionType ? world.func_234923_W_() : MIDDLE_EARTH_WORLD_KEY;
   }

   public static ITextComponent getDisplayName(RegistryKey dimensionWorldKey) {
      ResourceLocation dimensionName = dimensionWorldKey.func_240901_a_();
      String key = String.format("dimension.%s.%s", dimensionName.func_110624_b(), dimensionName.func_110623_a());
      return new TranslationTextComponent(key);
   }

   public static BlockPos getDimensionSpawnPoint(ServerWorld world) {
      DimensionType dimension = world.func_230315_m_();
      return dimension instanceof MiddleEarthDimensionType ? ((MiddleEarthDimensionType)dimension).getSpawnCoordinate(world) : world.func_241135_u_();
   }

   public static void replaceDimensionCodecToForceStability() {
      try {
         final Codec codec = DimensionGeneratorSettings.field_236201_a_;
         Codec stableCodec = new Codec() {
            public DataResult encode(DimensionGeneratorSettings input, DynamicOps ops, Object prefix) {
               return codec.encode(input, ops, prefix);
            }

            public DataResult decode(DynamicOps ops, Object input) {
               DataResult result = codec.decode(ops, input);
               return DataResult.success(result.result().orElseThrow(() -> {
                  return new IllegalStateException("Failed to change lifecycle to stable");
               }), Lifecycle.stable());
            }
         };
         Field f_codec = (Field)Stream.of(DimensionGeneratorSettings.class.getDeclaredFields()).filter((field) -> {
            return (field.getModifiers() & 8) != 0;
         }).filter((field) -> {
            return field.getType() == Codec.class;
         }).findFirst().orElseThrow(() -> {
            return new IllegalStateException("Failed to find codec field in DimensionGeneratorSettings");
         });
         LOTRUtil.unlockFinalField(f_codec);
         f_codec.set((Object)null, stableCodec);
      } catch (Exception var3) {
         var3.printStackTrace();
         LOTRLog.error("Failed to set dimension generator settings codec to stable");
      }

   }

   static {
      MIDDLE_EARTH_DIMTYPE = dispatchModDimensionType(MIDDLE_EARTH_ID);
      MIDDLE_EARTH_DIMTYPE_KEY = RegistryKey.func_240903_a_(Registry.field_239698_ad_, MIDDLE_EARTH_ID);
      MIDDLE_EARTH_DIM_KEY = createDimensionKey(MIDDLE_EARTH_ID);
      MIDDLE_EARTH_WORLD_KEY = RegistryKey.func_240903_a_(Registry.field_239699_ae_, MIDDLE_EARTH_DIM_KEY.func_240901_a_());
      MIDDLE_EARTH_DIMSETTINGS_KEY = RegistryKey.func_240903_a_(Registry.field_243549_ar, new ResourceLocation("lotr", "middle_earth"));
      MIDDLE_EARTH_DIMSETTINGS = createMiddleEarthDimensionSettings();
   }
}
