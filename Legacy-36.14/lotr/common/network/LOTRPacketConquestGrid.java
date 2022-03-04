package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import lotr.common.LOTRMod;
import lotr.common.fac.LOTRFaction;
import lotr.common.world.map.LOTRConquestZone;
import net.minecraft.world.World;

public class LOTRPacketConquestGrid implements IMessage {
   private LOTRFaction conqFac;
   private List allZones;
   private long worldTime;
   private static final short END_OF_PACKET = -15000;

   public LOTRPacketConquestGrid() {
   }

   public LOTRPacketConquestGrid(LOTRFaction fac, Collection grid, World world) {
      this.conqFac = fac;
      this.allZones = new ArrayList(grid);
      this.worldTime = world.func_82737_E();
   }

   public void toBytes(ByteBuf data) {
      int facID = this.conqFac.ordinal();
      data.writeByte(facID);
      Iterator var3 = this.allZones.iterator();

      while(var3.hasNext()) {
         LOTRConquestZone zone = (LOTRConquestZone)var3.next();
         float str = zone.getConquestStrength(this.conqFac, this.worldTime);
         if (str > 0.0F) {
            float strRaw = zone.getConquestStrengthRaw(this.conqFac);
            data.writeShort(zone.gridX);
            data.writeShort(zone.gridZ);
            data.writeLong(zone.getLastChangeTime());
            data.writeFloat(strRaw);
         }
      }

      data.writeShort(-15000);
   }

   public void fromBytes(ByteBuf data) {
      int facID = data.readByte();
      this.conqFac = LOTRFaction.forID(facID);
      this.allZones = new ArrayList();
      int gridX = false;
      int gridZ = false;
      float str = 0.0F;

      short gridX;
      while((gridX = data.readShort()) != -15000) {
         int gridZ = data.readShort();
         long time = data.readLong();
         str = data.readFloat();
         LOTRConquestZone zone = new LOTRConquestZone(gridX, gridZ);
         zone.setClientSide();
         zone.setLastChangeTime(time);
         zone.setConquestStrengthRaw(this.conqFac, str);
         this.allZones.add(zone);
      }

   }

   public static class Handler implements IMessageHandler {
      public IMessage onMessage(LOTRPacketConquestGrid packet, MessageContext context) {
         LOTRMod.proxy.receiveConquestGrid(packet.conqFac, packet.allZones);
         return null;
      }
   }
}
