package lotr.common;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.UnmodifiableIterator;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLInterModComms.IMCMessage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;

public class LOTRInterModComms {
   public static void update() {
      ImmutableList messages = FMLInterModComms.fetchRuntimeMessages(LOTRMod.instance);
      if (!messages.isEmpty()) {
         UnmodifiableIterator var1 = messages.iterator();

         while(var1.hasNext()) {
            IMCMessage message = (IMCMessage)var1.next();
            if (message.key.equals("SIEGE_ACTIVE")) {
               String playerName = message.getStringValue();
               EntityPlayer entityplayer = MinecraftServer.func_71276_C().func_71203_ab().func_152612_a(playerName);
               if (entityplayer != null) {
                  int duration = 20;
                  LOTRLevelData.getData((EntityPlayer)entityplayer).setSiegeActive(duration);
               }
            }
         }
      }

   }
}
