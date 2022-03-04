package lotr.common.world.biome.provider;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import lotr.common.init.LOTRBiomes;
import lotr.common.world.gen.MiddleEarthBiomeGenSettings;
import lotr.common.world.gen.layer.LayerWithDataDrivenBiomes;
import lotr.common.world.gen.layer.MiddleEarthWorldLayers;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryLookupCodec;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class MiddleEarthBiomeProvider extends BiomeProvider {
   public static final Codec CODEC = RecordCodecBuilder.create((instance) -> {
      return instance.group(Codec.LONG.fieldOf("seed").stable().forGetter((provider) -> {
         return provider.seed;
      }), Codec.BOOL.fieldOf("classic_biomes").forGetter((provider) -> {
         return provider.classicBiomes;
      }), RegistryLookupCodec.func_244331_a(Registry.field_239720_u_).forGetter((provider) -> {
         return provider.lookupRegistry;
      })).apply(instance, MiddleEarthBiomeProvider::new);
   });
   private final long seed;
   private final Registry lookupRegistry;
   private boolean classicBiomes;
   private boolean alreadySetWorldTypeClassicBiomes = false;
   private LayerWithDataDrivenBiomes genBiomes;

   public MiddleEarthBiomeProvider(long seed, boolean classicBiomes, Registry lookupRegistry) {
      super(LOTRBiomes.listAllBiomesForProvider(lookupRegistry));
      this.seed = seed;
      this.lookupRegistry = lookupRegistry;
      this.classicBiomes = classicBiomes;
      this.genBiomes = this.createOrRecreateBiomeGenLayers();
   }

   private LayerWithDataDrivenBiomes createOrRecreateBiomeGenLayers() {
      return MiddleEarthWorldLayers.create(this.seed, this.classicBiomes, new MiddleEarthBiomeGenSettings());
   }

   public void hackySetWorldTypeClassicBiomes(boolean flag) {
      if (this.alreadySetWorldTypeClassicBiomes) {
         throw new IllegalStateException("Already set the world type value of classicBiomes to " + this.classicBiomes + "!");
      } else {
         if (flag != this.classicBiomes) {
            this.alreadySetWorldTypeClassicBiomes = true;
            this.classicBiomes = flag;
            this.genBiomes = this.createOrRecreateBiomeGenLayers();
         }

      }
   }

   public boolean isClassicBiomes() {
      return this.classicBiomes;
   }

   public Biome func_225526_b_(int x, int y, int z) {
      return this.genBiomes.getLayerBiome(this.lookupRegistry, x, z);
   }

   protected Codec func_230319_a_() {
      return CODEC;
   }

   @OnlyIn(Dist.CLIENT)
   public BiomeProvider func_230320_a_(long seed) {
      return new MiddleEarthBiomeProvider(seed, this.classicBiomes, this.lookupRegistry);
   }
}
