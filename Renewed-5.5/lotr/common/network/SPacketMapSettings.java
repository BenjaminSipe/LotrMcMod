package lotr.common.network;

import java.util.function.Supplier;
import lotr.common.world.map.MapSettings;
import lotr.common.world.map.MapSettingsManager;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class SPacketMapSettings {
   private final MapSettings mapSettings;

   public SPacketMapSettings(MapSettings map) {
      this.mapSettings = map;
   }

   public static void encode(SPacketMapSettings packet, PacketBuffer buf) {
      packet.mapSettings.write(buf);
   }

   public static SPacketMapSettings decode(PacketBuffer buf) {
      MapSettings mapSettings = MapSettings.read(MapSettingsManager.clientInstance(), buf);
      return new SPacketMapSettings(mapSettings);
   }

   public static void handle(SPacketMapSettings packet, Supplier context) {
      MapSettingsManager.clientInstance().loadClientMapFromServer(Minecraft.func_71410_x().func_195551_G(), packet.mapSettings);
      ((Context)context.get()).setPacketHandled(true);
   }
}
