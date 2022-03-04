package lotr.client.gui.map;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lotr.common.LOTRLog;
import lotr.common.world.map.MapPlayerLocation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.play.ClientPlayNetHandler;
import net.minecraft.client.network.play.NetworkPlayerInfo;

public class MapPlayerLocationHolder {
   private static final Map locations = new HashMap();

   public static Map getPlayerLocations() {
      return locations;
   }

   public static void refreshPlayerLocations(List newPlayerLocations) {
      clearPlayerLocations();
      ClientPlayNetHandler nph = Minecraft.func_71410_x().func_147114_u();
      Iterator var2 = newPlayerLocations.iterator();

      while(var2.hasNext()) {
         MapPlayerLocation loc = (MapPlayerLocation)var2.next();
         UUID playerID = loc.profile.getId();
         if (playerID != null) {
            NetworkPlayerInfo player = nph.func_175102_a(playerID);
            locations.put(playerID, loc.withFullProfile(player.func_178845_a()));
         } else {
            LOTRLog.warn("Received map player location from server with null UUID");
         }
      }

   }

   public static void clearPlayerLocations() {
      locations.clear();
   }
}
