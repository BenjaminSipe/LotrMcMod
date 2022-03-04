package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRPlayerData;
import lotr.common.fellowship.LOTRFellowship;
import lotr.common.fellowship.LOTRFellowshipClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

public class LOTRPacketFellowshipToggle extends LOTRPacketFellowshipDo {
   private LOTRPacketFellowshipToggle.ToggleFunction function;

   public LOTRPacketFellowshipToggle() {
   }

   public LOTRPacketFellowshipToggle(LOTRFellowshipClient fs, LOTRPacketFellowshipToggle.ToggleFunction f) {
      super(fs);
      this.function = f;
   }

   public void toBytes(ByteBuf data) {
      super.toBytes(data);
      data.writeByte(this.function.ordinal());
   }

   public void fromBytes(ByteBuf data) {
      super.fromBytes(data);
      this.function = LOTRPacketFellowshipToggle.ToggleFunction.values()[data.readByte()];
   }

   public static class Handler implements IMessageHandler {
      public IMessage onMessage(LOTRPacketFellowshipToggle packet, MessageContext context) {
         EntityPlayerMP entityplayer = context.getServerHandler().field_147369_b;
         LOTRFellowship fellowship = packet.getActiveFellowship();
         if (fellowship != null) {
            LOTRPlayerData playerData = LOTRLevelData.getData((EntityPlayer)entityplayer);
            boolean current;
            if (packet.function == LOTRPacketFellowshipToggle.ToggleFunction.PVP) {
               current = fellowship.getPreventPVP();
               playerData.setFellowshipPreventPVP(fellowship, !current);
            } else if (packet.function == LOTRPacketFellowshipToggle.ToggleFunction.HIRED_FF) {
               current = fellowship.getPreventHiredFriendlyFire();
               playerData.setFellowshipPreventHiredFF(fellowship, !current);
            } else if (packet.function == LOTRPacketFellowshipToggle.ToggleFunction.MAP_SHOW) {
               current = fellowship.getShowMapLocations();
               playerData.setFellowshipShowMapLocations(fellowship, !current);
            }
         }

         return null;
      }
   }

   public static enum ToggleFunction {
      PVP,
      HIRED_FF,
      MAP_SHOW;
   }
}
