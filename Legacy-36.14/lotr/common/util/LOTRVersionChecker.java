package lotr.common.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

public class LOTRVersionChecker {
   private static String versionURL = "https://dl.dropboxusercontent.com/s/sidxw1dicl2nsev/version.txt";
   private static boolean checkedUpdate = false;

   public static void checkForUpdates() {
      if (!checkedUpdate) {
         Thread checkThread = new Thread("LOTR Update Checker") {
            public void run() {
               try {
                  URL url = new URL(LOTRVersionChecker.versionURL);
                  BufferedReader updateReader = new BufferedReader(new InputStreamReader(url.openStream()));

                  String updateVersion;
                  String line;
                  for(updateVersion = ""; (line = updateReader.readLine()) != null; updateVersion = updateVersion.concat(line)) {
                  }

                  updateReader.close();
                  updateVersion = updateVersion.trim();
                  String currentVersion = "Update v36.14 for Minecraft 1.7.10";
                  if (!updateVersion.equals(currentVersion)) {
                     IChatComponent component = new ChatComponentText("The Lord of the Rings Mod:");
                     component.func_150256_b().func_150238_a(EnumChatFormatting.YELLOW);
                     EntityPlayer entityplayer = Minecraft.func_71410_x().field_71439_g;
                     if (entityplayer != null) {
                        entityplayer.func_145747_a(new ChatComponentTranslation("chat.lotr.update", new Object[]{component, updateVersion}));
                     }
                  }
               } catch (Exception var8) {
                  LOTRLog.logger.warn("LOTR: Version check failed");
                  var8.printStackTrace();
               }

            }
         };
         checkedUpdate = true;
         checkThread.setDaemon(true);
         checkThread.start();
      }

   }
}
