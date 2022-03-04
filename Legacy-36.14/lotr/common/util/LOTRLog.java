package lotr.common.util;

import cpw.mods.fml.common.FMLLog;
import java.lang.reflect.Field;
import lotr.common.LOTRReflection;
import net.minecraft.server.MinecraftServer;
import org.apache.logging.log4j.Logger;

public class LOTRLog {
   public static Logger logger;

   public static void findLogger() {
      try {
         Field[] fields = MinecraftServer.class.getDeclaredFields();
         Field[] var1 = fields;
         int var2 = fields.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            Field f = var1[var3];
            LOTRReflection.unlockFinalField(f);
            Object obj = f.get((Object)null);
            if (obj instanceof Logger) {
               logger = (Logger)obj;
               logger.info("LOTR: Found logger");
               break;
            }
         }
      } catch (Exception var6) {
         FMLLog.warning("LOTR: Failed to find logger!", new Object[0]);
         var6.printStackTrace();
      }

   }
}
