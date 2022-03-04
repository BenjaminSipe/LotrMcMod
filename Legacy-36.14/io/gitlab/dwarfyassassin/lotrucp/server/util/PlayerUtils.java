package io.gitlab.dwarfyassassin.lotrucp.server.util;

import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.UsernameCache;

public class PlayerUtils {
   public static UUID getLastKownUUIDFromUsername(String username) {
      EntityPlayerMP player = MinecraftServer.func_71276_C().func_71203_ab().func_152612_a(username);
      if (player != null) {
         return player.func_146103_bH().getId();
      } else {
         Map userNameCache = UsernameCache.getMap();
         Iterator var3 = userNameCache.entrySet().iterator();

         Entry entry;
         do {
            if (!var3.hasNext()) {
               return null;
            }

            entry = (Entry)var3.next();
         } while(!((String)entry.getValue()).equalsIgnoreCase(username));

         return (UUID)entry.getKey();
      }
   }
}
