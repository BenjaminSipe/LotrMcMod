package lotr.common.world.map;

import com.google.common.io.Files;
import cpw.mods.fml.common.FMLLog;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import lotr.common.LOTRConfig;
import lotr.common.fellowship.LOTRFellowship;
import lotr.common.fellowship.LOTRFellowshipData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.DimensionManager;

public class LOTRCustomWaypointLogger {
   private static final Charset CHARSET = Charset.forName("UTF-8");
   private static final DateFormat DATE_FORMAT = new SimpleDateFormat("YYYY-MM");
   private static final DateFormat MONTH_DATE_FORMAT = new SimpleDateFormat("MM-dd");
   private static final DateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm:ss");

   public static void logCreate(EntityPlayer entityplayer, LOTRCustomWaypoint cwp) {
      log("CREATE", entityplayer, cwp);
   }

   public static void logTravel(EntityPlayer entityplayer, LOTRCustomWaypoint cwp) {
      log("TRAVEL", entityplayer, cwp);
   }

   public static void logRename(EntityPlayer entityplayer, LOTRCustomWaypoint cwp) {
      log("RENAME", entityplayer, cwp);
   }

   public static void logDelete(EntityPlayer entityplayer, LOTRCustomWaypoint cwp) {
      log("DELETE", entityplayer, cwp);
   }

   private static void log(String function, EntityPlayer entityplayer, LOTRCustomWaypoint cwp) {
      if (LOTRConfig.cwpLog) {
         try {
            Date date = Calendar.getInstance().getTime();
            String logLine = String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s", MONTH_DATE_FORMAT.format(date), TIME_FORMAT.format(date), function, entityplayer.func_70005_c_(), entityplayer.getPersistentID(), cwp.getCodeName(), cwp.getXCoord(), cwp.getYCoordSaved(), cwp.getZCoord(), cwp.isShared(), cwp.isShared() ? cwp.getSharingPlayerName() : "N/A", cwp.isShared() ? cwp.getSharingPlayerID() : "N/A");
            if (cwp.isShared()) {
               List fsIDs = cwp.getSharedFellowshipIDs();
               Iterator var6 = fsIDs.iterator();

               while(var6.hasNext()) {
                  UUID id = (UUID)var6.next();
                  LOTRFellowship fellowship = LOTRFellowshipData.getActiveFellowship(id);
                  if (fellowship != null && fellowship.containsPlayer(entityplayer.func_110124_au())) {
                     logLine = logLine + ",";
                     logLine = logLine + fellowship.getName();
                  }
               }
            }

            File dupeLogDir = new File(DimensionManager.getCurrentSaveRootDirectory(), "lotr_cwp_logs");
            if (!dupeLogDir.exists()) {
               dupeLogDir.mkdirs();
            }

            File logFile = new File(dupeLogDir, DATE_FORMAT.format(date) + ".csv");
            if (!logFile.exists()) {
               Files.append("date,time,function,username,UUID,wp_name,x,y,z,shared,sharer_name,sharer_UUID,common_fellowships" + System.getProperty("line.separator"), logFile, CHARSET);
            }

            Files.append(logLine + System.getProperty("line.separator"), logFile, CHARSET);
         } catch (IOException var9) {
            FMLLog.warning("Error logging custom waypoint activities", new Object[0]);
            var9.printStackTrace();
         }

      }
   }
}
