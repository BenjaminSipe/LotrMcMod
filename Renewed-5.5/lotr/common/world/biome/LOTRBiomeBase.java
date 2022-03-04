package lotr.common.world.biome;

import com.google.common.collect.ImmutableList;
import java.awt.Color;
import java.util.List;
import java.util.Random;
import lotr.common.LOTRLog;
import lotr.common.LOTRMod;
import lotr.common.init.LOTRBiomes;
import lotr.common.init.LOTREntities;
import lotr.common.util.CalendarUtil;
import lotr.common.world.biome.surface.MiddleEarthSurfaceConfig;
import lotr.common.world.gen.feature.WeightedRandomFeatureConfig;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeMaker;
import net.minecraft.world.biome.MoodSoundAmbience;
import net.minecraft.world.biome.Biome.Builder;
import net.minecraft.world.biome.Biome.RainType;
import net.minecraft.world.biome.MobSpawnInfo.Spawners;
import net.minecraft.world.gen.PerlinNoiseGenerator;
import net.minecraft.world.gen.feature.BlockClusterFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

public abstract class LOTRBiomeBase implements LOTRBiomeWrapper {
   private ResourceLocation biomeName;
   private Biome actualBiome;
   private Builder unbuiltBiome;
   private net.minecraft.world.biome.BiomeAmbience.Builder unbuiltBiomeAmbience;
   private final boolean isMajorBiome;
   protected LOTRBiomeBase.CustomBiomeColors biomeColors;
   protected static final int STANDARD_FOG_COLOR = 12638463;
   protected static final int STANDARD_WATER_FOG_COLOR = 329011;
   protected static final PerlinNoiseGenerator SNOW_VARIETY_NOISE = makeSingleLayerPerlinNoise(2490309256000602L);
   private float treeDensityForPodzol;
   private int maxPodzolHeight;
   private WeightedRandomFeatureConfig grassBonemealGenerator;

   protected LOTRBiomeBase(Builder builder, int waterFogColor, boolean major) {
      this.maxPodzolHeight = Integer.MAX_VALUE;
      this.unbuiltBiome = builder;
      this.unbuiltBiomeAmbience = (new net.minecraft.world.biome.BiomeAmbience.Builder()).func_235246_b_(16777215).func_235248_c_(waterFogColor);
      this.isMajorBiome = major;
      this.biomeColors = new LOTRBiomeBase.CustomBiomeColors(builder);
   }

   protected LOTRBiomeBase(Builder builder, boolean major) {
      this(builder, 329011, major);
   }

   public LOTRBiomeBase setBiomeName(ResourceLocation name) {
      if (this.biomeName != null) {
         throw new IllegalStateException("Cannot set biomeName for LOTRBiome " + this.biomeName + " - already set!");
      } else {
         this.biomeName = name;
         return this;
      }
   }

   public Biome initialiseActualBiome() {
      if (this.actualBiome != null) {
         throw new IllegalStateException("LOTRBiome object for " + this.biomeName + " is already initialised!");
      } else {
         this.setupBiomeAmbience(this.unbuiltBiomeAmbience);
         this.unbuiltBiome.func_235097_a_(this.unbuiltBiomeAmbience.func_235238_a_());
         this.unbuiltBiomeAmbience = null;
         net.minecraft.world.biome.BiomeGenerationSettings.Builder generationBuilder = new net.minecraft.world.biome.BiomeGenerationSettings.Builder();
         MiddleEarthSurfaceConfig surfaceBuilderConfig = MiddleEarthSurfaceConfig.createDefault();
         this.setupSurface(surfaceBuilderConfig);
         this.addFeatures(generationBuilder);
         surfaceBuilderConfig.setTreeDensityForPodzol(this.treeDensityForPodzol).setMaxPodzolHeight(this.maxPodzolHeight);
         generationBuilder.func_242517_a(LOTRBiomes.MIDDLE_EARTH_SURFACE.func_242929_a(surfaceBuilderConfig));
         this.unbuiltBiome.func_242457_a(generationBuilder.func_242508_a());
         net.minecraft.world.biome.MobSpawnInfo.Builder entitySpawnBuilder = new net.minecraft.world.biome.MobSpawnInfo.Builder();
         this.addAnimals(entitySpawnBuilder);
         this.unbuiltBiome.func_242458_a(entitySpawnBuilder.func_242577_b());
         this.actualBiome = this.unbuiltBiome.func_242455_a();
         this.unbuiltBiome = null;
         return this.actualBiome;
      }
   }

   public final ResourceLocation getBiomeRegistryName() {
      return this.biomeName;
   }

   public final Biome getActualBiome() {
      if (this.actualBiome != null) {
         return this.actualBiome;
      } else {
         throw new IllegalStateException("Cannot fetch Biome object for LOTRBiome " + this.biomeName + " - has not yet been initialised!");
      }
   }

   public final boolean isMajorBiome() {
      return this.isMajorBiome;
   }

   public static PerlinNoiseGenerator makeSingleLayerPerlinNoise(long seed) {
      return new PerlinNoiseGenerator(new SharedSeedRandom(seed), ImmutableList.of(0));
   }

   protected void setupBiomeAmbience(net.minecraft.world.biome.BiomeAmbience.Builder builder) {
      if (this.biomeColors.hasGrass()) {
         this.unbuiltBiomeAmbience.func_242541_f(this.biomeColors.getGrass());
      }

      if (this.biomeColors.hasFoliage()) {
         this.unbuiltBiomeAmbience.func_242540_e(this.biomeColors.getFoliage());
      }

      this.unbuiltBiomeAmbience.func_242539_d(this.biomeColors.getSky());
      this.unbuiltBiomeAmbience.func_235239_a_(this.biomeColors.getFog());
      if (this.hasCustomWaterColor()) {
         this.unbuiltBiomeAmbience.func_235246_b_(this.getCustomWaterColor());
      }

      builder.func_235243_a_(MoodSoundAmbience.field_235027_b_);
   }

   protected void setupSurface(MiddleEarthSurfaceConfig config) {
   }

   protected void addFeatures(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      this.addCarvers(builder);
      this.addLakes(builder);
      this.addDirtGravel(builder);
      this.addStoneVariants(builder);
      this.addOres(builder);
      this.addDripstones(builder);
      this.addCobwebs(builder);
      this.addSedimentDisks(builder);
      this.addBoulders(builder);
      this.addVegetation(builder);
      LOTRBiomeFeatures.addMushrooms(builder);
      this.addReeds(builder);
      this.addPumpkins(builder);
      this.addLiquidSprings(builder);
      LOTRBiomeFeatures.addFreezeTopLayer(builder);
      this.addStructures(builder);
   }

   protected void addCarvers(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addCarvers(builder);
   }

   protected void addLakes(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addLakes(builder);
   }

   protected void addDirtGravel(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addDirtGravel(builder);
   }

   protected void addStoneVariants(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addGranite(builder);
   }

   protected void addOres(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addOres(builder);
   }

   protected void addDripstones(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addDripstones(builder);
   }

   protected void addCobwebs(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addCobwebs(builder);
   }

   protected void addSedimentDisks(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      this.addBiomeSandSediments(builder);
      LOTRBiomeFeatures.addClayGravelSediments(builder);
   }

   protected void addBiomeSandSediments(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addSandSediments(builder);
   }

   protected void addBoulders(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
   }

   protected void addVegetation(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
   }

   public void setGrassBonemealGenerator(WeightedRandomFeatureConfig config) {
      this.grassBonemealGenerator = config;
   }

   public BlockState getGrassForBonemeal(Random rand, BlockPos pos) {
      if (this.grassBonemealGenerator != null) {
         ConfiguredFeature feature = this.grassBonemealGenerator.getRandomFeature(rand);
         if (feature.field_222738_b instanceof BlockClusterFeatureConfig) {
            return ((BlockClusterFeatureConfig)feature.field_222738_b).field_227289_a_.func_225574_a_(rand, pos);
         }

         LOTRLog.warn("DEVELOPMENT ERROR: Biome (%s) grass bonemeal generator contains a FeatureConfig of invalid type - should be BlockClusterFeatureConfig, but is %s", this.biomeName, feature.field_222738_b.getClass().getName());
      }

      return Blocks.field_150349_c.func_176223_P();
   }

   protected void addReeds(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addReeds(builder);
   }

   protected void addPumpkins(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addPumpkins(builder);
   }

   protected void addLiquidSprings(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addWaterLavaSprings(builder);
   }

   public boolean isSurfaceBlockForNPCSpawn(BlockState state) {
      MiddleEarthSurfaceConfig sc = (MiddleEarthSurfaceConfig)this.getActualBiome().func_242440_e().func_242502_e();
      return sc.isSurfaceBlockForNPCSpawning(state);
   }

   public List getSpawnsAtLocation(EntityClassification creatureType, BlockPos pos) {
      return this.actualBiome.func_242433_b().func_242559_a(creatureType);
   }

   protected void addAnimals(net.minecraft.world.biome.MobSpawnInfo.Builder builder) {
      this.addLandCreatures(builder);
      this.addAmbientCreatures(builder);
   }

   protected void addLandCreatures(net.minecraft.world.biome.MobSpawnInfo.Builder builder) {
      builder.func_242575_a(EntityClassification.CREATURE, new Spawners(EntityType.field_200737_ac, 24, 4, 4));
      builder.func_242575_a(EntityClassification.CREATURE, new Spawners(EntityType.field_200784_X, 20, 4, 4));
      builder.func_242575_a(EntityClassification.CREATURE, new Spawners(EntityType.field_200795_i, 20, 4, 4));
      builder.func_242575_a(EntityClassification.CREATURE, new Spawners(EntityType.field_200796_j, 16, 4, 4));
      this.addDeer(builder);
   }

   protected void addAmbientCreatures(net.minecraft.world.biome.MobSpawnInfo.Builder builder) {
      builder.func_242575_a(EntityClassification.AMBIENT, new Spawners(EntityType.field_200791_e, 10, 8, 8));
   }

   protected void addExtraSheep(net.minecraft.world.biome.MobSpawnInfo.Builder builder, int mul) {
      builder.func_242575_a(EntityClassification.CREATURE, new Spawners(EntityType.field_200737_ac, 24 * mul, 4, 4));
   }

   protected void addHorsesDonkeys(net.minecraft.world.biome.MobSpawnInfo.Builder builder) {
      this.addHorsesDonkeys(builder, 1);
   }

   protected void addHorsesDonkeys(net.minecraft.world.biome.MobSpawnInfo.Builder builder, int mul) {
      builder.func_242575_a(EntityClassification.CREATURE, new Spawners(EntityType.field_200762_B, 10 * mul, 2, 6));
      builder.func_242575_a(EntityClassification.CREATURE, new Spawners(EntityType.field_200798_l, 1 * mul, 1, 3));
   }

   protected void addWolves(net.minecraft.world.biome.MobSpawnInfo.Builder builder) {
      this.addWolves(builder, 1);
   }

   protected void addWolves(net.minecraft.world.biome.MobSpawnInfo.Builder builder, int mul) {
      builder.func_242575_a(EntityClassification.CREATURE, new Spawners(EntityType.field_200724_aC, 10 * mul, 4, 8));
   }

   protected void addFoxes(net.minecraft.world.biome.MobSpawnInfo.Builder builder) {
      this.addFoxes(builder, 1);
   }

   protected void addFoxes(net.minecraft.world.biome.MobSpawnInfo.Builder builder, int mul) {
      builder.func_242575_a(EntityClassification.CREATURE, new Spawners(EntityType.field_220356_B, 16 * mul, 2, 4));
   }

   protected void addCaracals(net.minecraft.world.biome.MobSpawnInfo.Builder builder) {
      this.addCaracals(builder, 1);
   }

   protected void addCaracals(net.minecraft.world.biome.MobSpawnInfo.Builder builder, int mul) {
      builder.func_242575_a(EntityClassification.CREATURE, new Spawners((EntityType)LOTREntities.CARACAL.get(), 12 * mul, 1, 4));
   }

   protected void addDeer(net.minecraft.world.biome.MobSpawnInfo.Builder builder) {
      this.addDeer(builder, 1);
   }

   protected void addDeer(net.minecraft.world.biome.MobSpawnInfo.Builder builder, int mul) {
   }

   protected void addBoars(net.minecraft.world.biome.MobSpawnInfo.Builder builder) {
      this.addBoars(builder, 1);
   }

   protected void addBoars(net.minecraft.world.biome.MobSpawnInfo.Builder builder, int mul) {
   }

   protected void addBears(net.minecraft.world.biome.MobSpawnInfo.Builder builder) {
      this.addBears(builder, 1);
   }

   protected void addBears(net.minecraft.world.biome.MobSpawnInfo.Builder builder, int mul) {
   }

   protected void addElk(net.minecraft.world.biome.MobSpawnInfo.Builder builder) {
      this.addElk(builder, 1);
   }

   protected void addElk(net.minecraft.world.biome.MobSpawnInfo.Builder builder, int mul) {
   }

   protected void addStructures(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
   }

   public final void updateBiomePodzolVariables(float treeDensity, int maxHeight) {
      this.treeDensityForPodzol = Math.max(this.treeDensityForPodzol, treeDensity);
      this.maxPodzolHeight = Math.min(this.maxPodzolHeight, maxHeight);
   }

   public final Vector3d alterCloudColor(Vector3d clouds) {
      if (this.biomeColors.hasClouds()) {
         float[] colors = this.biomeColors.getCloudsRGB();
         clouds = clouds.func_216372_d((double)colors[0], (double)colors[1], (double)colors[2]);
      }

      return clouds;
   }

   public final float getCloudCoverage() {
      return this.biomeColors.getCloudCoverage();
   }

   public final boolean isFoggy() {
      return this.biomeColors.isFoggy();
   }

   public final RainType getPrecipitationVisually() {
      return isChristmasSnowInVisualContext() ? RainType.SNOW : this.actualBiome.func_201851_b();
   }

   private static boolean isChristmasSnowOverride() {
      return CalendarUtil.isChristmas();
   }

   private static boolean isChristmasSnowInVisualContext() {
      return isChristmasSnowOverride() && LOTRMod.PROXY.isClient();
   }

   public static boolean isSnowingVisually(LOTRBiomeWrapper biomeWrapper, IWorld world, BlockPos pos) {
      float temp = biomeWrapper.getTemperatureForSnowWeatherRendering(world, pos);
      boolean isChristmas = isChristmasSnowOverride();
      return isChristmas || isTemperatureSuitableForSnow(temp);
   }

   public static boolean isTemperatureSuitableForSnow(float temp) {
      return temp < 0.15F;
   }

   public float getTemperatureForSnowWeatherRendering(IWorld world, BlockPos pos) {
      return this.actualBiome.func_225486_c(pos);
   }

   public final ExtendedWeatherType getExtendedWeatherVisually() {
      return isChristmasSnowInVisualContext() ? ExtendedWeatherType.NONE : this.getBiomeExtendedWeather();
   }

   protected ExtendedWeatherType getBiomeExtendedWeather() {
      return ExtendedWeatherType.NONE;
   }

   public final boolean hasCustomWaterColor() {
      return this.biomeColors.hasWater();
   }

   public final int getCustomWaterColor() {
      return this.biomeColors.getWater();
   }

   public void onGeographicalWaterColorUpdate(int waterColor, Biome biomeObjectInClientRegistry) {
      if (!this.hasCustomWaterColor()) {
         biomeObjectInClientRegistry.func_235089_q_().field_235206_c_ = waterColor;
      }

   }

   public boolean isRiver() {
      return false;
   }

   public Biome getRiver(IWorld world) {
      return this.getNormalRiver(world);
   }

   protected final Biome getNormalRiver(IWorld world) {
      return LOTRBiomes.getBiomeByRegistryName(LOTRBiomes.RIVER.getRegistryName(), world);
   }

   public LOTRBiomeBase getShore() {
      return this.actualBiome.func_185355_j() < 0.0F ? this : LOTRBiomes.BEACH.getInitialisedBiomeWrapper();
   }

   public static class CustomBiomeColors {
      private int grass = -1;
      private int foliage = -1;
      private int sky;
      private final int defaultBiomeSky;
      private int clouds = -1;
      private float[] cloudsRGB = new float[3];
      private float cloudCoverage = 1.0F;
      private int fog;
      private float[] fogRGB = new float[3];
      private boolean foggy = false;
      private int water = -1;

      public CustomBiomeColors(Builder builder) {
         float biomeTemp = (Float)ObfuscationReflectionHelper.getPrivateValue(Builder.class, builder, "field_205427_f");
         this.defaultBiomeSky = BiomeMaker.func_244206_a(biomeTemp);
         this.setSky(this.defaultBiomeSky);
         this.setFog(12638463);
      }

      public boolean hasGrass() {
         return this.grass >= 0;
      }

      public int getGrass() {
         return this.grass;
      }

      public LOTRBiomeBase.CustomBiomeColors setGrass(int i) {
         this.grass = i;
         return this;
      }

      public LOTRBiomeBase.CustomBiomeColors resetGrass() {
         return this.setGrass(-1);
      }

      public boolean hasFoliage() {
         return this.foliage >= 0;
      }

      public int getFoliage() {
         return this.foliage;
      }

      public LOTRBiomeBase.CustomBiomeColors setFoliage(int i) {
         this.foliage = i;
         return this;
      }

      public LOTRBiomeBase.CustomBiomeColors resetFoliage() {
         return this.setFoliage(-1);
      }

      public int getSky() {
         return this.sky;
      }

      public LOTRBiomeBase.CustomBiomeColors setSky(int i) {
         this.sky = i;
         return this;
      }

      public LOTRBiomeBase.CustomBiomeColors resetSky() {
         return this.setSky(this.defaultBiomeSky);
      }

      public boolean hasClouds() {
         return this.clouds >= 0;
      }

      public int getClouds() {
         return this.clouds;
      }

      public float[] getCloudsRGB() {
         return this.cloudsRGB;
      }

      public LOTRBiomeBase.CustomBiomeColors setClouds(int i) {
         this.clouds = i;
         this.cloudsRGB = (new Color(this.clouds)).getColorComponents(this.cloudsRGB);
         return this;
      }

      public float getCloudCoverage() {
         return this.cloudCoverage;
      }

      public LOTRBiomeBase.CustomBiomeColors setCloudCoverage(float f) {
         this.cloudCoverage = f;
         return this;
      }

      public LOTRBiomeBase.CustomBiomeColors resetClouds() {
         this.clouds = -1;
         this.cloudsRGB = null;
         this.cloudCoverage = 1.0F;
         return this;
      }

      public int getFog() {
         return this.fog;
      }

      public float[] getFogRGB() {
         return this.fogRGB;
      }

      public LOTRBiomeBase.CustomBiomeColors setFog(int i) {
         this.fog = i;
         this.fogRGB = (new Color(this.fog)).getColorComponents(this.fogRGB);
         return this;
      }

      public boolean isFoggy() {
         return this.foggy;
      }

      public LOTRBiomeBase.CustomBiomeColors setFoggy(boolean flag) {
         this.foggy = flag;
         return this;
      }

      public LOTRBiomeBase.CustomBiomeColors resetFog() {
         this.setFog(12638463);
         this.fogRGB = null;
         this.foggy = false;
         return this;
      }

      public boolean hasWater() {
         return this.water >= 0;
      }

      public int getWater() {
         return this.water;
      }

      public LOTRBiomeBase.CustomBiomeColors setWater(int i) {
         this.water = i;
         return this;
      }

      public LOTRBiomeBase.CustomBiomeColors resetWater(int i) {
         return this.setWater(-1);
      }
   }
}
