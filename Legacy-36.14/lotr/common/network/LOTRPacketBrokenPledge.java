package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.LOTRPlayerData;
import lotr.common.fac.LOTRFaction;
import net.minecraft.entity.player.EntityPlayer;

public class LOTRPacketBrokenPledge implements IMessage {
   private int cooldown;
   private int cooldownStart;
   private LOTRFaction brokenFac;

   public LOTRPacketBrokenPledge() {
   }

   public LOTRPacketBrokenPledge(int cd, int start, LOTRFaction f) {
      this.cooldown = cd;
      this.cooldownStart = start;
      this.brokenFac = f;
   }

   public void toBytes(ByteBuf data) {
      data.writeInt(this.cooldown);
      data.writeInt(this.cooldownStart);
      if (this.brokenFac == null) {
         data.writeByte(-1);
      } else {
         data.writeByte(this.brokenFac.ordinal());
      }

   }

   public void fromBytes(ByteBuf data) {
      this.cooldown = data.readInt();
      this.cooldownStart = data.readInt();
      int facID = data.readByte();
      if (facID >= 0) {
         this.brokenFac = LOTRFaction.forID(facID);
      }

   }

   public static class Handler implements IMessageHandler {
      public IMessage onMessage(LOTRPacketBrokenPledge packet, MessageContext context) {
         EntityPlayer entityplayer = LOTRMod.proxy.getClientPlayer();
         LOTRPlayerData pd = LOTRLevelData.getData(entityplayer);
         pd.setPledgeBreakCooldown(packet.cooldown);
         pd.setPledgeBreakCooldownStart(packet.cooldownStart);
         pd.setBrokenPledgeFaction(packet.brokenFac);
         return null;
      }
   }
}
