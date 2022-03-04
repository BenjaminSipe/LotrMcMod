package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;
import java.util.UUID;
import lotr.common.fellowship.LOTRFellowship;
import lotr.common.fellowship.LOTRFellowshipClient;
import lotr.common.fellowship.LOTRFellowshipData;

public abstract class LOTRPacketFellowshipDo implements IMessage {
   private UUID fellowshipID;

   public LOTRPacketFellowshipDo() {
   }

   public LOTRPacketFellowshipDo(LOTRFellowshipClient fsClient) {
      this.fellowshipID = fsClient.getFellowshipID();
   }

   public void toBytes(ByteBuf data) {
      data.writeLong(this.fellowshipID.getMostSignificantBits());
      data.writeLong(this.fellowshipID.getLeastSignificantBits());
   }

   public void fromBytes(ByteBuf data) {
      this.fellowshipID = new UUID(data.readLong(), data.readLong());
   }

   protected LOTRFellowship getActiveFellowship() {
      return LOTRFellowshipData.getActiveFellowship(this.fellowshipID);
   }

   protected LOTRFellowship getActiveOrDisbandedFellowship() {
      return LOTRFellowshipData.getFellowship(this.fellowshipID);
   }
}
