package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.entity.player.EntityPlayer;

public class LOTRPacketMercenaryInteract extends LOTRPacketUnitTraderInteract {
   public LOTRPacketMercenaryInteract() {
   }

   public LOTRPacketMercenaryInteract(int idt, int a) {
      super(idt, a);
   }

   protected void openTradeGUI(EntityPlayer entityplayer, LOTREntityNPC trader) {
      entityplayer.openGui(LOTRMod.instance, 59, entityplayer.field_70170_p, trader.func_145782_y(), 0, 0);
   }

   public static class Handler implements IMessageHandler {
      public IMessage onMessage(LOTRPacketMercenaryInteract message, MessageContext ctx) {
         return (new LOTRPacketUnitTraderInteract.Handler()).onMessage((LOTRPacketUnitTraderInteract)message, ctx);
      }
   }
}
