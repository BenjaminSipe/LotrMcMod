package lotr.common.world.biome;

import java.util.List;
import lotr.common.init.LOTRBiomes;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityClassification;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.biome.Biome.RainType;

public class VanillaPlaceholderLOTRBiome implements LOTRBiomeWrapper {
   private final ResourceLocation biomeName;
   private final Biome biome;

   private VanillaPlaceholderLOTRBiome(ResourceLocation name, Biome b) {
      this.biomeName = name;
      this.biome = b;
   }

   public static VanillaPlaceholderLOTRBiome makeWrapperFor(IWorld world, Biome biome) {
      ResourceLocation biomeName = LOTRBiomes.getBiomeRegistryName(biome, world);
      if (LOTRBiomes.isDefaultLOTRBiome(biomeName)) {
         throw new IllegalArgumentException(String.format("Cannot wrap a default LOTR mod biome (%s) in a vanilla placeholder wrapper!", biomeName));
      } else {
         return new VanillaPlaceholderLOTRBiome(biomeName, biome);
      }
   }

   public final ResourceLocation getBiomeRegistryName() {
      return this.biomeName;
   }

   public final Biome getActualBiome() {
      return this.biome;
   }

   public List getSpawnsAtLocation(EntityClassification creatureType, BlockPos pos) {
      return this.biome.func_242433_b().func_242559_a(creatureType);
   }

   public RainType getPrecipitationVisually() {
      return this.biome.func_201851_b();
   }

   public float getTemperatureForSnowWeatherRendering(IWorld world, BlockPos pos) {
      return this.biome.func_242445_k();
   }

   public boolean isRiver() {
      return this.biome.func_201856_r() == Category.RIVER;
   }

   public Biome getRiver(IWorld world) {
      return LOTRBiomes.getBiomeByRegistryName(Biomes.field_76781_i.func_240901_a_(), world);
   }

   public LOTRBiomeBase getShore() {
      return LOTRBiomes.BEACH.getInitialisedBiomeWrapper();
   }

   public boolean isSurfaceBlockForNPCSpawn(BlockState state) {
      return state.func_177230_c() == this.biome.func_242440_e().func_242502_e().func_204108_a().func_177230_c();
   }
}
