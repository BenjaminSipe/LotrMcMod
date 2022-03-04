package lotr.common.init;

import java.util.function.Supplier;
import lotr.common.world.biome.LOTRBiomeBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.common.util.NonNullSupplier;

public class PreRegisteredLOTRBiome {
   private final String name;
   private final ResourceLocation fullRegistryName;
   private final LazyOptional biomeWrapper;
   private boolean initialisedWrapper;

   public PreRegisteredLOTRBiome(String name, NonNullSupplier biomeWrapperSupplier) {
      this.name = name;
      this.fullRegistryName = new ResourceLocation("lotr", name);
      this.biomeWrapper = LazyOptional.of(biomeWrapperSupplier);
      this.initialisedWrapper = false;
   }

   public String getName() {
      return this.name;
   }

   public ResourceLocation getRegistryName() {
      return this.fullRegistryName;
   }

   private LOTRBiomeBase getOrCreateBiomeWrapper() {
      this.initialisedWrapper = true;
      return (LOTRBiomeBase)this.biomeWrapper.orElseThrow(() -> {
         return new IllegalStateException("Could not supply LOTR biome " + this.name);
      });
   }

   public LOTRBiomeBase initialiseAndReturnBiomeWrapper(ResourceLocation biomeName) {
      if (this.initialisedWrapper) {
         throw new IllegalStateException("LOTR biome " + this.name + " is already initialised!");
      } else {
         return this.getOrCreateBiomeWrapper().setBiomeName(biomeName);
      }
   }

   public LOTRBiomeBase getInitialisedBiomeWrapper() {
      if (!this.initialisedWrapper) {
         throw new IllegalStateException("LOTR biome " + this.name + " is not yet initialised!");
      } else {
         return this.getOrCreateBiomeWrapper();
      }
   }

   public Supplier supplyBiomeInitialiser() {
      return () -> {
         return this.getInitialisedBiomeWrapper().initialiseActualBiome();
      };
   }

   public Biome getInitialisedBiome() {
      return this.getInitialisedBiomeWrapper().getActualBiome();
   }

   public Supplier supplyInitialisedBiome() {
      return () -> {
         return this.getInitialisedBiome();
      };
   }
}
