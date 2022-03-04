package lotr.common.world.gen.layer;

import lotr.common.init.LOTRBiomes;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.area.IArea;
import net.minecraft.world.gen.layer.traits.IAreaTransformer2;
import net.minecraft.world.gen.layer.traits.IDimOffset0Transformer;

public enum MEAddRiversLayer implements IAreaTransformer2, IDimOffset0Transformer {
   INSTANCE;

   public int func_215723_a(INoiseRandom context, IArea biomeArea, IArea riverArea, int x, int z) {
      int biomeID = biomeArea.func_202678_a(this.func_215721_a(x), this.func_215722_b(z));
      if (MiddleEarthWorldLayers.getActiveMapSettings().getProceduralRivers()) {
         int hasRiver = riverArea.func_202678_a(this.func_215721_a(x), this.func_215722_b(z));
         if (hasRiver >= 1) {
            IWorld world = LOTRBiomes.getServerBiomeContextWorld();
            Biome river = LOTRBiomes.getWrapperFor(LOTRBiomes.getBiomeByID(biomeID, world), world).getRiver(world);
            if (river != null) {
               return LOTRBiomes.getBiomeID((Biome)river, world);
            }
         }
      }

      return biomeID;
   }
}
