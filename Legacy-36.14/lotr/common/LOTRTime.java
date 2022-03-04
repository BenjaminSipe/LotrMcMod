package lotr.common;

import cpw.mods.fml.common.FMLLog;
import java.io.File;
import java.io.FileOutputStream;
import lotr.common.world.LOTRWorldInfo;
import lotr.common.world.LOTRWorldProvider;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;

public class LOTRTime {
   public static int DAY_LENGTH = 48000;
   private static long totalTime;
   private static long worldTime;
   public static boolean needsLoad = true;

   private static File getTimeDat() {
      return new File(LOTRLevelData.getOrCreateLOTRDir(), "LOTRTime.dat");
   }

   public static void save() {
      try {
         File time_dat = getTimeDat();
         if (!time_dat.exists()) {
            CompressedStreamTools.func_74799_a(new NBTTagCompound(), new FileOutputStream(time_dat));
         }

         NBTTagCompound timeData = new NBTTagCompound();
         timeData.func_74772_a("LOTRTotalTime", totalTime);
         timeData.func_74772_a("LOTRWorldTime", worldTime);
         LOTRLevelData.saveNBTToFile(time_dat, timeData);
      } catch (Exception var2) {
         FMLLog.severe("Error saving LOTR time data", new Object[0]);
         var2.printStackTrace();
      }

   }

   public static void load() {
      try {
         NBTTagCompound timeData = LOTRLevelData.loadNBTFromFile(getTimeDat());
         totalTime = timeData.func_74763_f("LOTRTotalTime");
         worldTime = timeData.func_74763_f("LOTRWorldTime");
         needsLoad = false;
         save();
      } catch (Exception var1) {
         FMLLog.severe("Error loading LOTR time data", new Object[0]);
         var1.printStackTrace();
      }

   }

   public static void setWorldTime(long time) {
      worldTime = time;
   }

   public static void addWorldTime(long time) {
      worldTime += time;
   }

   public static void advanceToMorning() {
      long l = worldTime + (long)DAY_LENGTH;
      setWorldTime(l - l % (long)DAY_LENGTH);
   }

   public static void update() {
      MinecraftServer server = MinecraftServer.func_71276_C();
      WorldServer overworld = server.func_71218_a(0);
      if (LOTRMod.doDayCycle(overworld)) {
         ++worldTime;
      }

      ++totalTime;
      WorldServer[] var2 = server.field_71305_c;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         WorldServer world = var2[var4];
         if (world.field_73011_w instanceof LOTRWorldProvider) {
            LOTRWorldInfo worldinfo = (LOTRWorldInfo)world.func_72912_H();
            worldinfo.lotr_setTotalTime(totalTime);
            worldinfo.lotr_setWorldTime(worldTime);
         }
      }

   }
}
