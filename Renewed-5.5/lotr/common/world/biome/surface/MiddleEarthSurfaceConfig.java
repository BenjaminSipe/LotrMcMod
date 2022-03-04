package lotr.common.world.biome.surface;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import lotr.common.world.biome.LOTRBiomeBase;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.gen.PerlinNoiseGenerator;
import net.minecraft.world.gen.surfacebuilders.ISurfaceBuilderConfig;
import org.apache.commons.lang3.ArrayUtils;

public class MiddleEarthSurfaceConfig implements ISurfaceBuilderConfig {
   public static final Codec CODEC = RecordCodecBuilder.create((instance) -> {
      return instance.group(BlockState.field_235877_b_.fieldOf("top_material").forGetter((config) -> {
         return config.topMaterial;
      }), BlockState.field_235877_b_.fieldOf("under_material").forGetter((config) -> {
         return config.fillerMaterial;
      }), Codec.DOUBLE.fieldOf("under_depth").orElse(5.0D).forGetter((config) -> {
         return config.fillerDepth;
      }), BlockState.field_235877_b_.fieldOf("underwater_material").forGetter((config) -> {
         return config.underwaterMaterial;
      }), MiddleEarthSurfaceConfig.SubSoilLayer.SUB_SOIL_LAYER_CODEC.listOf().fieldOf("sub_soil_layers").forGetter((config) -> {
         return config.subSoilLayers;
      }), Codec.BOOL.fieldOf("rocky").orElse(true).forGetter((config) -> {
         return config.rocky;
      }), Codec.BOOL.fieldOf("podzol").orElse(true).forGetter((config) -> {
         return config.podzol;
      }), Codec.FLOAT.fieldOf("tree_density_for_podzol").orElse(0.0F).forGetter((config) -> {
         return config.treeDensityForPodzol;
      }), Codec.INT.fieldOf("max_podzol_height").orElse(Integer.MAX_VALUE).forGetter((config) -> {
         return config.maxPodzolHeight;
      }), Codec.BOOL.fieldOf("marsh").orElse(false).forGetter((config) -> {
         return config.marsh;
      }), SurfaceNoiseMixer.CODEC.fieldOf("surface_noise_mixer").orElse(SurfaceNoiseMixer.NONE).forGetter((config) -> {
         return config.surfaceNoiseMixer;
      }), Codec.BOOL.fieldOf("surface_noise_paths").orElse(false).forGetter((config) -> {
         return config.hasSurfaceNoisePaths;
      }), UnderwaterNoiseMixer.CODEC.fieldOf("underwater_noise_mixer").orElse(UnderwaterNoiseMixer.NONE).forGetter((config) -> {
         return config.underwaterNoiseMixer;
      }), MountainTerrainProvider.CODEC.fieldOf("mountain_terrain_provider").orElse(MountainTerrainProvider.NONE).forGetter((config) -> {
         return config.mountainTerrainProvider;
      })).apply(instance, MiddleEarthSurfaceConfig::new);
   });
   private BlockState topMaterial;
   private BlockState fillerMaterial;
   private double fillerDepth;
   private static final double DEFAULT_FILLER_DEPTH = 5.0D;
   private BlockState underwaterMaterial;
   private List subSoilLayers;
   private boolean rocky;
   private boolean podzol;
   private float treeDensityForPodzol;
   private int maxPodzolHeight;
   private boolean marsh;
   protected static final PerlinNoiseGenerator MARSH_NOISE = new PerlinNoiseGenerator(new SharedSeedRandom(444L), ImmutableList.of(0));
   private SurfaceNoiseMixer surfaceNoiseMixer;
   private boolean hasSurfaceNoisePaths;
   private UnderwaterNoiseMixer underwaterNoiseMixer;
   private static final PerlinNoiseGenerator noiseGen1 = LOTRBiomeBase.makeSingleLayerPerlinNoise(1954L);
   private static final PerlinNoiseGenerator noiseGen2 = LOTRBiomeBase.makeSingleLayerPerlinNoise(10420914965337148L);
   private static final PerlinNoiseGenerator noiseGen3 = LOTRBiomeBase.makeSingleLayerPerlinNoise(2274201084179107L);
   private static final PerlinNoiseGenerator noiseGen4 = LOTRBiomeBase.makeSingleLayerPerlinNoise(259632637571778808L);
   private MountainTerrainProvider mountainTerrainProvider;

   public MiddleEarthSurfaceConfig(BlockState top, BlockState filler, BlockState underwater) {
      this(top, filler, 5.0D, underwater, new ArrayList(), true, true, 0.0F, Integer.MAX_VALUE, false, SurfaceNoiseMixer.NONE, false, UnderwaterNoiseMixer.NONE, MountainTerrainProvider.NONE);
   }

   public MiddleEarthSurfaceConfig(BlockState top, BlockState filler, double fillerDepth, BlockState underwater, List subSoilLayers, boolean rocky, boolean podzol, float treeDensityForPodzol, int maxPodzolHeight, boolean marsh, SurfaceNoiseMixer surfaceNoiseMixer, boolean hasSurfaceNoisePaths, UnderwaterNoiseMixer underwaterNoiseMixer, MountainTerrainProvider mountainTerrainProvider) {
      this.fillerDepth = 5.0D;
      this.subSoilLayers = new ArrayList();
      this.rocky = true;
      this.podzol = true;
      this.treeDensityForPodzol = 0.0F;
      this.maxPodzolHeight = Integer.MAX_VALUE;
      this.marsh = false;
      this.surfaceNoiseMixer = SurfaceNoiseMixer.NONE;
      this.hasSurfaceNoisePaths = false;
      this.underwaterNoiseMixer = UnderwaterNoiseMixer.NONE;
      this.mountainTerrainProvider = MountainTerrainProvider.NONE;
      this.topMaterial = top;
      this.fillerMaterial = filler;
      this.fillerDepth = fillerDepth;
      this.underwaterMaterial = underwater;
      this.subSoilLayers = subSoilLayers;
      this.rocky = rocky;
      this.podzol = podzol;
      this.treeDensityForPodzol = treeDensityForPodzol;
      this.maxPodzolHeight = maxPodzolHeight;
      this.marsh = marsh;
      this.surfaceNoiseMixer = surfaceNoiseMixer;
      this.hasSurfaceNoisePaths = hasSurfaceNoisePaths;
      this.underwaterNoiseMixer = underwaterNoiseMixer;
      this.mountainTerrainProvider = mountainTerrainProvider;
   }

   public static MiddleEarthSurfaceConfig createDefault() {
      return new MiddleEarthSurfaceConfig(Blocks.field_196658_i.func_176223_P(), Blocks.field_150346_d.func_176223_P(), Blocks.field_150351_n.func_176223_P());
   }

   public BlockState func_204108_a() {
      return this.topMaterial;
   }

   public void setTop(BlockState state) {
      this.topMaterial = state;
   }

   public BlockState func_204109_b() {
      return this.fillerMaterial;
   }

   public void setFiller(BlockState state) {
      this.fillerMaterial = state;
   }

   public double getFillerDepth() {
      return this.fillerDepth;
   }

   public void setFillerDepth(double d) {
      this.fillerDepth = d;
   }

   public BlockState getUnderwaterMaterial() {
      return this.underwaterMaterial;
   }

   public void setUnderwater(BlockState state) {
      this.underwaterMaterial = state;
   }

   public Iterator getSubSoilLayers() {
      return this.subSoilLayers.iterator();
   }

   public void addSubSoilLayer(BlockState state, int depth) {
      this.addSubSoilLayer(state, depth, depth);
   }

   public void addSubSoilLayer(BlockState state, int min, int max) {
      this.subSoilLayers.add(new MiddleEarthSurfaceConfig.SubSoilLayer(state, min, max));
   }

   public void resetFillerDepthAndSubSoilLayers() {
      this.setFillerDepth(5.0D);
      this.subSoilLayers.clear();
   }

   public boolean hasRockyTerrain() {
      return this.rocky;
   }

   public MiddleEarthSurfaceConfig setRockyTerrain(boolean flag) {
      this.rocky = flag;
      return this;
   }

   public boolean hasPodzol() {
      return this.podzol;
   }

   public MiddleEarthSurfaceConfig setPodzol(boolean flag) {
      this.podzol = flag;
      return this;
   }

   public float getTreeDensityForPodzol() {
      return this.treeDensityForPodzol;
   }

   public MiddleEarthSurfaceConfig setTreeDensityForPodzol(float f) {
      this.treeDensityForPodzol = f;
      return this;
   }

   public int getMaxPodzolHeight() {
      return this.maxPodzolHeight;
   }

   public MiddleEarthSurfaceConfig setMaxPodzolHeight(int h) {
      this.maxPodzolHeight = h;
      return this;
   }

   public boolean isMarsh() {
      return this.marsh;
   }

   public MiddleEarthSurfaceConfig setMarsh(boolean flag) {
      this.marsh = flag;
      return this;
   }

   public void setSurfaceNoiseMixer(SurfaceNoiseMixer mixer) {
      this.surfaceNoiseMixer = mixer;
   }

   public void setSurfaceNoisePaths(boolean flag) {
      this.hasSurfaceNoisePaths = flag;
   }

   public BlockState getSurfaceNoiseReplacement(int x, int z, BlockState in, boolean top, Random rand) {
      BlockState state = this.surfaceNoiseMixer.getReplacement(x, z, in, top, rand);
      if (this.hasSurfaceNoisePaths) {
         state = SurfaceNoisePaths.getReplacement(x, z, state, top, rand);
      }

      return state;
   }

   public void setUnderwaterNoiseMixer(UnderwaterNoiseMixer mixer) {
      this.underwaterNoiseMixer = mixer;
   }

   public BlockState getUnderwaterNoiseReplacement(int x, int z, BlockState in, Random rand) {
      return this.underwaterNoiseMixer.getReplacement(x, z, in, rand);
   }

   private static double getIteratedNoise(PerlinNoiseGenerator noiseGen, int x, int z, double[] scales, double[] xScales, double[] zScales, int[] weights) {
      if (ArrayUtils.isEmpty(xScales)) {
         xScales = (double[])Util.func_200696_a(new double[scales.length], (arr) -> {
            Arrays.fill(arr, 1.0D);
         });
      }

      if (ArrayUtils.isEmpty(zScales)) {
         zScales = (double[])Util.func_200696_a(new double[scales.length], (arr) -> {
            Arrays.fill(arr, 1.0D);
         });
      }

      if (ArrayUtils.isEmpty(weights)) {
         weights = (int[])Util.func_200696_a(new int[scales.length], (arr) -> {
            Arrays.fill(arr, 1);
         });
      }

      double noise = 0.0D;
      int totalWeight = 0;

      for(int i = 0; i < scales.length; ++i) {
         double coordScale = scales[i];
         int weight = weights[i];
         noise += noiseGen.func_215464_a((double)x * xScales[i] * coordScale, (double)z * zScales[i] * coordScale, false) * (double)weight;
         totalWeight += weight;
      }

      return noise / (double)totalWeight;
   }

   public static double getNoise1(int x, int z, double[] scales, double[] xScales, double[] zScales, int[] weights) {
      return getIteratedNoise(noiseGen1, x, z, scales, xScales, zScales, weights);
   }

   public static double getNoise1(int x, int z, double... scales) {
      return getNoise1(x, z, scales, (double[])null, (double[])null, (int[])null);
   }

   public static double getNoise2(int x, int z, double[] scales, double[] xScales, double[] zScales, int[] weights) {
      return getIteratedNoise(noiseGen2, x, z, scales, xScales, zScales, weights);
   }

   public static double getNoise2(int x, int z, double... scales) {
      return getNoise2(x, z, scales, (double[])null, (double[])null, (int[])null);
   }

   public static double getNoise3(int x, int z, double[] scales, double[] xScales, double[] zScales, int[] weights) {
      return getIteratedNoise(noiseGen3, x, z, scales, xScales, zScales, weights);
   }

   public static double getNoise4(int x, int z, double[] scales, double[] xScales, double[] zScales, int[] weights) {
      return getIteratedNoise(noiseGen4, x, z, scales, xScales, zScales, weights);
   }

   public void setMountainTerrain(MountainTerrainProvider provider) {
      this.mountainTerrainProvider = provider;
   }

   public boolean hasMountainTerrain() {
      return this.mountainTerrainProvider != MountainTerrainProvider.NONE;
   }

   public BlockState getMountainTerrain(int x, int z, int y, BlockState in, BlockState stone, boolean top, int stoneNoiseDepth) {
      return this.mountainTerrainProvider.getReplacement(x, z, y, in, stone, top, stoneNoiseDepth);
   }

   public boolean isSurfaceBlockForNPCSpawning(BlockState state) {
      return state.func_177230_c() == this.func_204108_a().func_177230_c() || state.func_177230_c() == Blocks.field_196661_l && this.hasPodzol() || this.surfaceNoiseMixer.isSurfaceBlock(state);
   }

   public static class SubSoilLayer {
      public static final Codec SUB_SOIL_LAYER_CODEC = RecordCodecBuilder.create((instance) -> {
         return instance.group(BlockState.field_235877_b_.fieldOf("material").forGetter((config) -> {
            return config.material;
         }), Codec.INT.fieldOf("min_depth").orElse(0).forGetter((config) -> {
            return config.minDepth;
         }), Codec.INT.fieldOf("max_depth").orElse(0).forGetter((config) -> {
            return config.maxDepth;
         })).apply(instance, MiddleEarthSurfaceConfig.SubSoilLayer::new);
      });
      private final BlockState material;
      private final int minDepth;
      private final int maxDepth;

      public SubSoilLayer(BlockState state, int min, int max) {
         this.material = state;
         this.minDepth = min;
         this.maxDepth = max;
      }

      public BlockState getMaterial() {
         return this.material;
      }

      public int getDepth(Random rand) {
         return MathHelper.func_76136_a(rand, this.minDepth, this.maxDepth);
      }
   }
}
