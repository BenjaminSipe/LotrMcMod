package lotr.client.event;

import java.lang.reflect.Field;
import lotr.common.LOTRLog;
import lotr.common.dim.LOTRDimensionType;
import lotr.common.init.LOTRDimensions;
import lotr.common.util.LOTRUtil;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

public class ClientWorldDimensionTypeHelper {
   public static void fixDimensionType(ClientWorld world) {
      DimensionType type = world.func_230315_m_();
      if (type.func_242725_p().func_110624_b().equals("lotr") && !(type instanceof LOTRDimensionType)) {
         ResourceLocation dimensionId = world.func_234923_W_().func_240901_a_();
         LOTRDimensionType newType = LOTRDimensions.dispatchModDimensionType(dimensionId);

         try {
            Field f_dimensionType = ObfuscationReflectionHelper.findField(World.class, "field_234921_x_");
            LOTRUtil.unlockFinalField(f_dimensionType);
            f_dimensionType.set(world, newType);
            LOTRLog.debug("Corrected the DimensionType of client world %s to %s", dimensionId, newType);
         } catch (Exception var5) {
            LOTRLog.error("Failed to correct the DimensionType of client world %s", dimensionId);
            var5.printStackTrace();
         }
      }

   }
}
