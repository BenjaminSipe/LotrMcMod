package lotr.common.network;

import java.util.function.Supplier;
import lotr.common.LOTRMod;
import lotr.common.config.ClientsideCurrentServerConfigSettings;
import lotr.common.data.LOTRLevelData;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class SPacketLoginLOTR {
   private BlockPos middleEarthPortalPos;
   private int wpCooldownMax;
   private int wpCooldownMin;
   private boolean areasOfInfluence;
   private boolean smallerBees;
   private boolean hasMapFeatures;
   private int forceFogOfWar;

   public void setMiddleEarthPortalPos(int x, int y, int z) {
      this.middleEarthPortalPos = new BlockPos(x, y, z);
   }

   public void setWaypointCooldownMaxMin(int max, int min) {
      this.wpCooldownMax = max;
      this.wpCooldownMin = min;
   }

   public void setAreasOfInfluence(boolean flag) {
      this.areasOfInfluence = flag;
   }

   public void setSmallerBees(boolean flag) {
      this.smallerBees = flag;
   }

   public void setHasMapFeatures(boolean flag) {
      this.hasMapFeatures = flag;
   }

   public void setForceFogOfWar(int i) {
      this.forceFogOfWar = i;
   }

   public static void encode(SPacketLoginLOTR packet, PacketBuffer buf) {
      buf.func_179255_a(packet.middleEarthPortalPos);
      buf.writeInt(packet.wpCooldownMax);
      buf.writeInt(packet.wpCooldownMin);
      buf.writeBoolean(packet.areasOfInfluence);
      buf.writeBoolean(packet.smallerBees);
      buf.writeBoolean(packet.hasMapFeatures);
      buf.func_150787_b(packet.forceFogOfWar);
   }

   public static SPacketLoginLOTR decode(PacketBuffer buf) {
      SPacketLoginLOTR packet = new SPacketLoginLOTR();
      packet.middleEarthPortalPos = buf.func_179259_c();
      packet.wpCooldownMax = buf.readInt();
      packet.wpCooldownMin = buf.readInt();
      packet.areasOfInfluence = buf.readBoolean();
      packet.smallerBees = buf.readBoolean();
      packet.hasMapFeatures = buf.readBoolean();
      packet.forceFogOfWar = buf.func_150792_a();
      return packet;
   }

   public static void handle(SPacketLoginLOTR packet, Supplier context) {
      World clientWorld = LOTRMod.PROXY.getClientWorld();
      LOTRLevelData levelData = LOTRLevelData.clientInstance();
      levelData.markMiddleEarthPortalLocation(clientWorld, packet.middleEarthPortalPos);
      levelData.setWaypointCooldown(clientWorld, packet.wpCooldownMax, packet.wpCooldownMin);
      ClientsideCurrentServerConfigSettings ccsConfig = ClientsideCurrentServerConfigSettings.INSTANCE;
      ccsConfig.areasOfInfluence = packet.areasOfInfluence;
      ccsConfig.smallerBees = packet.smallerBees;
      ccsConfig.hasMapFeatures = packet.hasMapFeatures;
      ccsConfig.forceFogOfWar = packet.forceFogOfWar;
      ((Context)context.get()).setPacketHandled(true);
   }
}
