package lotr.common.speech.condition;

import io.netty.buffer.ByteBuf;
import lotr.curuquesta.util.StringSerializer;
import net.minecraft.util.ResourceLocation;

public class BiomeWithTags {
   private final ResourceLocation biomeName;
   private final boolean isFactionHomeBiome;

   public BiomeWithTags(ResourceLocation biomeName, boolean isFactionHomeBiome) {
      if (biomeName == null) {
         throw new IllegalArgumentException("BiomeWithTags: biomeName cannot be null!");
      } else {
         this.biomeName = biomeName;
         this.isFactionHomeBiome = isFactionHomeBiome;
      }
   }

   public ResourceLocation getBiomeName() {
      return this.biomeName;
   }

   public boolean isHomeBiome() {
      return this.isFactionHomeBiome;
   }

   public boolean isForeignBiome() {
      return !this.isHomeBiome();
   }

   public void write(ByteBuf buf) {
      StringSerializer.write(this.biomeName.toString(), buf);
      buf.writeBoolean(this.isFactionHomeBiome);
   }

   public static BiomeWithTags read(ByteBuf buf) {
      String biomeString = StringSerializer.read(buf);
      ResourceLocation biomeName = new ResourceLocation(biomeString);
      boolean isFactionHomeBiome = buf.readBoolean();
      return new BiomeWithTags(biomeName, isFactionHomeBiome);
   }

   public String toString() {
      return String.format("BiomeWithTags[biomeName=%s, isFactionHomeBiome=%s]", this.biomeName, this.isFactionHomeBiome);
   }
}
