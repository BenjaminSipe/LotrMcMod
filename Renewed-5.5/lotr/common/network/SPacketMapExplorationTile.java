package lotr.common.network;

import java.util.BitSet;
import java.util.function.Supplier;
import lotr.common.LOTRMod;
import lotr.common.data.FogDataModule;
import lotr.common.data.LOTRLevelData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class SPacketMapExplorationTile {
   private final int mapX;
   private final int mapZ;
   private final BitSet tileBits;

   public SPacketMapExplorationTile(int mapX, int mapZ, BitSet tileBits) {
      this.mapX = mapX;
      this.mapZ = mapZ;
      this.tileBits = tileBits;
   }

   public static void encode(SPacketMapExplorationTile packet, PacketBuffer buf) {
      buf.func_150787_b(packet.mapX);
      buf.func_150787_b(packet.mapZ);
      buf.func_179250_a(packet.tileBits.toByteArray());
   }

   public static SPacketMapExplorationTile decode(PacketBuffer buf) {
      int mapX = buf.func_150792_a();
      int mapZ = buf.func_150792_a();
      BitSet tileBits = BitSet.valueOf(buf.func_179251_a());
      return new SPacketMapExplorationTile(mapX, mapZ, tileBits);
   }

   public static void handle(SPacketMapExplorationTile packet, Supplier context) {
      PlayerEntity player = LOTRMod.PROXY.getClientPlayer();
      FogDataModule fogData = LOTRLevelData.clientInstance().getData(player).getFogData();
      fogData.receiveSingleTileUpdateFromServer(packet.mapX, packet.mapZ, packet.tileBits);
      ((Context)context.get()).setPacketHandled(true);
   }
}
