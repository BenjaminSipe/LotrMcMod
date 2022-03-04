package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRPlayerData;
import lotr.common.fac.LOTRFaction;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

public class LOTRPacketAlignmentChoices implements IMessage {
   private Set setZeroFacs = new HashSet();

   public LOTRPacketAlignmentChoices() {
   }

   public LOTRPacketAlignmentChoices(Set facs) {
      this.setZeroFacs = facs;
   }

   public void toBytes(ByteBuf data) {
      Iterator var2 = this.setZeroFacs.iterator();

      while(var2.hasNext()) {
         LOTRFaction fac = (LOTRFaction)var2.next();
         data.writeByte(fac.ordinal());
      }

      data.writeByte(-1);
   }

   public void fromBytes(ByteBuf data) {
      boolean var2 = false;

      byte facID;
      while((facID = data.readByte()) >= 0) {
         LOTRFaction fac = LOTRFaction.forID(facID);
         if (fac != null) {
            this.setZeroFacs.add(fac);
         }
      }

   }

   public static class Handler implements IMessageHandler {
      public IMessage onMessage(LOTRPacketAlignmentChoices packet, MessageContext context) {
         EntityPlayerMP entityplayer = context.getServerHandler().field_147369_b;
         LOTRPlayerData playerData = LOTRLevelData.getData((EntityPlayer)entityplayer);
         playerData.chooseUnwantedAlignments(entityplayer, packet.setZeroFacs);
         return null;
      }
   }
}
