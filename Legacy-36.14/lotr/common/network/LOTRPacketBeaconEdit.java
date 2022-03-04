package lotr.common.network;

import com.google.common.base.Charsets;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import java.util.UUID;
import lotr.common.fellowship.LOTRFellowship;
import lotr.common.fellowship.LOTRFellowshipData;
import lotr.common.tileentity.LOTRTileEntityBeacon;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

public class LOTRPacketBeaconEdit implements IMessage {
   private int beaconX;
   private int beaconY;
   private int beaconZ;
   private UUID fellowshipID;
   private String beaconName;
   private boolean releasePlayer;

   public LOTRPacketBeaconEdit() {
   }

   public LOTRPacketBeaconEdit(int i, int j, int k, UUID fsID, String name, boolean release) {
      this.beaconX = i;
      this.beaconY = j;
      this.beaconZ = k;
      this.fellowshipID = fsID;
      this.beaconName = name;
      this.releasePlayer = release;
   }

   public void toBytes(ByteBuf data) {
      data.writeInt(this.beaconX);
      data.writeInt(this.beaconY);
      data.writeInt(this.beaconZ);
      boolean hasFs = this.fellowshipID != null;
      data.writeBoolean(hasFs);
      if (hasFs) {
         data.writeLong(this.fellowshipID.getMostSignificantBits());
         data.writeLong(this.fellowshipID.getLeastSignificantBits());
      }

      boolean hasName = this.beaconName != null;
      data.writeBoolean(hasName);
      if (hasName) {
         byte[] nameBytes = this.beaconName.getBytes(Charsets.UTF_8);
         data.writeShort(nameBytes.length);
         data.writeBytes(nameBytes);
      }

      data.writeBoolean(this.releasePlayer);
   }

   public void fromBytes(ByteBuf data) {
      this.beaconX = data.readInt();
      this.beaconY = data.readInt();
      this.beaconZ = data.readInt();
      if (data.readBoolean()) {
         this.fellowshipID = new UUID(data.readLong(), data.readLong());
      }

      if (data.readBoolean()) {
         int length = data.readShort();
         ByteBuf nameBytes = data.readBytes(length);
         this.beaconName = nameBytes.toString(Charsets.UTF_8);
      }

      this.releasePlayer = data.readBoolean();
   }

   public static class Handler implements IMessageHandler {
      public IMessage onMessage(LOTRPacketBeaconEdit packet, MessageContext context) {
         EntityPlayer entityplayer = context.getServerHandler().field_147369_b;
         TileEntity te = entityplayer.field_70170_p.func_147438_o(packet.beaconX, packet.beaconY, packet.beaconZ);
         if (te instanceof LOTRTileEntityBeacon) {
            LOTRTileEntityBeacon beacon = (LOTRTileEntityBeacon)te;
            if (beacon.isPlayerEditing(entityplayer)) {
               LOTRFellowship fellowship = null;
               if (packet.fellowshipID != null) {
                  fellowship = LOTRFellowshipData.getActiveFellowship(packet.fellowshipID);
               }

               if (fellowship != null && fellowship.containsPlayer(entityplayer.func_110124_au())) {
                  beacon.setFellowship(fellowship);
               } else {
                  beacon.setFellowship((LOTRFellowship)null);
               }

               if (packet.beaconName != null) {
                  beacon.setBeaconName(packet.beaconName);
               } else {
                  beacon.setBeaconName((String)null);
               }

               if (packet.releasePlayer) {
                  beacon.releaseEditingPlayer(entityplayer);
               }
            }
         }

         return null;
      }
   }
}
