package lotr.client.render.world;

import java.awt.Color;
import java.util.Iterator;
import java.util.Map.Entry;
import lotr.common.LOTRLog;
import lotr.common.init.LOTRBiomes;
import lotr.common.world.map.BothWaterLatitudeSettings;
import lotr.common.world.map.MapSettings;
import lotr.common.world.map.MapSettingsManager;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.profiler.IProfiler;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.MutableRegistry;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

public class GeographicalWaterColors {
   private static final Color WATER_COLOR_COLD = new Color(602979);
   private static final Color WATER_COLOR_WARM = new Color(4445678);
   private static BlockPos lastViewerPos;
   private static int viewerPosWaterColor;

   public static void updateGeographicalWaterColorInBiomes(Minecraft mc) {
      IProfiler profiler = mc.func_213239_aq();
      profiler.func_76320_a("lotrGeographicalWaterColors");
      Entity viewer = mc.field_175622_Z;
      BlockPos newViewerPos = null;
      if (viewer != null) {
         newViewerPos = viewer.func_233580_cy_();
      }

      if (newViewerPos != null && (lastViewerPos == null || !lastViewerPos.func_218141_a(newViewerPos, 16.0D))) {
         lastViewerPos = newViewerPos;
         profiler.func_76320_a("calculate");
         viewerPosWaterColor = calcGeographicalWaterColor(mc, newViewerPos);
         profiler.func_219895_b("iterateBiomes");
         World world = mc.field_71441_e;
         MutableRegistry biomeRegistry = world.func_241828_r().func_243612_b(Registry.field_239720_u_);
         int i = 0;

         for(Iterator var7 = biomeRegistry.func_239659_c_().iterator(); var7.hasNext(); ++i) {
            Entry e = (Entry)var7.next();
            Biome biome = (Biome)e.getValue();
            LOTRBiomes.getWrapperFor(biome, world).onGeographicalWaterColorUpdate(viewerPosWaterColor, biome);
         }

         profiler.func_76319_b();
      }

      profiler.func_76319_b();
   }

   private static int calcGeographicalWaterColor(Minecraft mc, BlockPos pos) {
      MapSettings loadedMapSettings = MapSettingsManager.clientInstance().getLoadedMapOrLoadDefault(mc.func_195551_G());
      if (loadedMapSettings == null) {
         LOTRLog.error("No MapSettings instance is loaded on the client! This should not happen and is very bad!");
      }

      BothWaterLatitudeSettings waterLatitudes = loadedMapSettings.getWaterLatitudes();
      float latitude = waterLatitudes.getWaterTemperatureForLatitude(pos.func_177952_p());
      float[] coldColors = WATER_COLOR_COLD.getColorComponents((float[])null);
      float[] warmColors = WATER_COLOR_WARM.getColorComponents((float[])null);
      float r = MathHelper.func_219799_g(latitude, coldColors[0], warmColors[0]);
      float g = MathHelper.func_219799_g(latitude, coldColors[1], warmColors[1]);
      float b = MathHelper.func_219799_g(latitude, coldColors[2], warmColors[2]);
      Color water = new Color(r, g, b);
      int waterRGB = water.getRGB();
      return waterRGB;
   }

   public static void resetInMenu() {
      lastViewerPos = null;
   }
}
