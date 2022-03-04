package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import lotr.common.LOTRDimension;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRPlayerData;
import lotr.common.fac.LOTRFaction;
import lotr.common.quest.LOTRMiniQuestEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

public class LOTRPacketClientInfo implements IMessage {
   private LOTRFaction viewingFaction;
   private Map changedRegionMap;
   private boolean showWP;
   private boolean showCWP;
   private boolean showHiddenSWP;

   public LOTRPacketClientInfo() {
   }

   public LOTRPacketClientInfo(LOTRFaction f, Map crMap, boolean w, boolean cw, boolean h) {
      this.viewingFaction = f;
      this.changedRegionMap = crMap;
      this.showWP = w;
      this.showCWP = cw;
      this.showHiddenSWP = h;
   }

   public void toBytes(ByteBuf data) {
      if (this.viewingFaction == null) {
         data.writeByte(-1);
      } else {
         data.writeByte(this.viewingFaction.ordinal());
      }

      int changedRegionsSize = this.changedRegionMap != null ? this.changedRegionMap.size() : 0;
      data.writeByte(changedRegionsSize);
      if (changedRegionsSize > 0) {
         Iterator var3 = this.changedRegionMap.entrySet().iterator();

         while(var3.hasNext()) {
            Entry e = (Entry)var3.next();
            LOTRDimension.DimensionRegion reg = (LOTRDimension.DimensionRegion)e.getKey();
            LOTRFaction fac = (LOTRFaction)e.getValue();
            data.writeByte(reg.ordinal());
            data.writeByte(fac.ordinal());
         }
      }

      data.writeBoolean(this.showWP);
      data.writeBoolean(this.showCWP);
      data.writeBoolean(this.showHiddenSWP);
   }

   public void fromBytes(ByteBuf data) {
      int factionID = data.readByte();
      if (factionID >= 0) {
         this.viewingFaction = LOTRFaction.forID(factionID);
      }

      int changedRegionsSize = data.readByte();
      if (changedRegionsSize > 0) {
         this.changedRegionMap = new HashMap();

         for(int l = 0; l < changedRegionsSize; ++l) {
            LOTRDimension.DimensionRegion reg = LOTRDimension.DimensionRegion.forID(data.readByte());
            LOTRFaction fac = LOTRFaction.forID(data.readByte());
            this.changedRegionMap.put(reg, fac);
         }
      }

      this.showWP = data.readBoolean();
      this.showCWP = data.readBoolean();
      this.showHiddenSWP = data.readBoolean();
   }

   public static class Handler implements IMessageHandler {
      public IMessage onMessage(LOTRPacketClientInfo packet, MessageContext context) {
         EntityPlayerMP entityplayer = context.getServerHandler().field_147369_b;
         LOTRPlayerData pd = LOTRLevelData.getData((EntityPlayer)entityplayer);
         if (packet.viewingFaction != null) {
            LOTRFaction prevFac = pd.getViewingFaction();
            LOTRFaction newFac = packet.viewingFaction;
            pd.setViewingFaction(newFac);
            if (prevFac != newFac && prevFac.factionRegion == newFac.factionRegion) {
               pd.distributeMQEvent(new LOTRMiniQuestEvent.CycleAlignment());
            }

            if (prevFac.factionRegion != newFac.factionRegion) {
               pd.distributeMQEvent(new LOTRMiniQuestEvent.CycleAlignmentRegion());
            }
         }

         Map changedRegionMap = packet.changedRegionMap;
         if (changedRegionMap != null) {
            Iterator var10 = changedRegionMap.keySet().iterator();

            while(var10.hasNext()) {
               LOTRDimension.DimensionRegion reg = (LOTRDimension.DimensionRegion)var10.next();
               LOTRFaction fac = (LOTRFaction)changedRegionMap.get(reg);
               pd.setRegionLastViewedFaction(reg, fac);
            }
         }

         pd.setShowWaypoints(packet.showWP);
         pd.setShowCustomWaypoints(packet.showCWP);
         pd.setShowHiddenSharedWaypoints(packet.showHiddenSWP);
         return null;
      }
   }
}
