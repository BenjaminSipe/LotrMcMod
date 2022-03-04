package lotr.common.network;

import com.mojang.authlib.GameProfile;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;
import lotr.common.LOTRMod;
import lotr.common.world.map.MapPlayerLocation;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class SPacketMapPlayerLocations {
   private final List playerLocations;

   public SPacketMapPlayerLocations(List players) {
      this.playerLocations = players;
   }

   public static void encode(SPacketMapPlayerLocations packet, PacketBuffer buf) {
      List playerLocations = packet.playerLocations;
      int players = playerLocations.size();
      buf.writeInt(players);
      Iterator var4 = playerLocations.iterator();

      while(var4.hasNext()) {
         MapPlayerLocation loc = (MapPlayerLocation)var4.next();
         buf.func_179252_a(loc.profile.getId());
         buf.writeDouble(loc.posX);
         buf.writeDouble(loc.posZ);
      }

   }

   public static SPacketMapPlayerLocations decode(PacketBuffer buf) {
      List playerLocations = new ArrayList();
      int players = buf.readInt();

      for(int i = 0; i < players; ++i) {
         UUID playerID = buf.func_179253_g();
         double posX = buf.readDouble();
         double posZ = buf.readDouble();
         playerLocations.add(new MapPlayerLocation(new GameProfile(playerID, (String)null), posX, posZ));
      }

      return new SPacketMapPlayerLocations(playerLocations);
   }

   public static void handle(SPacketMapPlayerLocations packet, Supplier context) {
      LOTRMod.PROXY.mapHandlePlayerLocations(packet.playerLocations);
      ((Context)context.get()).setPacketHandled(true);
   }
}
