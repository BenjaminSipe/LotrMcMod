package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import java.util.UUID;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRPlayerData;
import lotr.common.fellowship.LOTRFellowship;
import lotr.common.fellowship.LOTRFellowshipClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

public class LOTRPacketFellowshipDoPlayer extends LOTRPacketFellowshipDo {
   private UUID subjectUuid;
   private LOTRPacketFellowshipDoPlayer.PlayerFunction function;

   public LOTRPacketFellowshipDoPlayer() {
   }

   public LOTRPacketFellowshipDoPlayer(LOTRFellowshipClient fs, UUID subject, LOTRPacketFellowshipDoPlayer.PlayerFunction f) {
      super(fs);
      this.subjectUuid = subject;
      this.function = f;
   }

   public void toBytes(ByteBuf data) {
      super.toBytes(data);
      data.writeLong(this.subjectUuid.getMostSignificantBits());
      data.writeLong(this.subjectUuid.getLeastSignificantBits());
      data.writeByte(this.function.ordinal());
   }

   public void fromBytes(ByteBuf data) {
      super.fromBytes(data);
      this.subjectUuid = new UUID(data.readLong(), data.readLong());
      this.function = LOTRPacketFellowshipDoPlayer.PlayerFunction.values()[data.readByte()];
   }

   public static class Handler implements IMessageHandler {
      public IMessage onMessage(LOTRPacketFellowshipDoPlayer packet, MessageContext context) {
         EntityPlayerMP entityplayer = context.getServerHandler().field_147369_b;
         String playerName = entityplayer.func_70005_c_();
         LOTRFellowship fellowship = packet.getActiveFellowship();
         UUID subjectPlayer = packet.subjectUuid;
         if (fellowship != null && subjectPlayer != null) {
            LOTRPlayerData playerData = LOTRLevelData.getData((EntityPlayer)entityplayer);
            if (packet.function == LOTRPacketFellowshipDoPlayer.PlayerFunction.REMOVE) {
               playerData.removePlayerFromFellowship(fellowship, subjectPlayer, playerName);
            } else if (packet.function == LOTRPacketFellowshipDoPlayer.PlayerFunction.TRANSFER) {
               playerData.transferFellowship(fellowship, subjectPlayer, playerName);
            } else if (packet.function == LOTRPacketFellowshipDoPlayer.PlayerFunction.OP) {
               playerData.setFellowshipAdmin(fellowship, subjectPlayer, true, playerName);
            } else if (packet.function == LOTRPacketFellowshipDoPlayer.PlayerFunction.DEOP) {
               playerData.setFellowshipAdmin(fellowship, subjectPlayer, false, playerName);
            }
         }

         return null;
      }
   }

   public static enum PlayerFunction {
      REMOVE,
      TRANSFER,
      OP,
      DEOP;
   }
}
