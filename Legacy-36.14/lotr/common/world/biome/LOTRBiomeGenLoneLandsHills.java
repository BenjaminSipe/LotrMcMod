package lotr.common.world.biome;

import lotr.common.world.biome.variant.LOTRBiomeVariant;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.world.biome.BiomeGenBase.SpawnListEntry;

public class LOTRBiomeGenLoneLandsHills extends LOTRBiomeGenLoneLands {
   public LOTRBiomeGenLoneLandsHills(int i, boolean major) {
      super(i, major);
      this.field_76762_K.add(new SpawnListEntry(EntityWolf.class, 10, 4, 8));
      this.clearBiomeVariants();
      this.addBiomeVariantSet(LOTRBiomeVariant.SET_MOUNTAINS);
      this.decorator.treesPerChunk = 1;
   }
}
