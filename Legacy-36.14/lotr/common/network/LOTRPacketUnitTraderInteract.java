package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.entity.npc.LOTRHireableBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;

public class LOTRPacketUnitTraderInteract implements IMessage {
   private int traderID;
   private int traderAction;

   public LOTRPacketUnitTraderInteract() {
   }

   public LOTRPacketUnitTraderInteract(int idt, int a) {
      this.traderID = idt;
      this.traderAction = a;
   }

   public void toBytes(ByteBuf data) {
      data.writeInt(this.traderID);
      data.writeByte(this.traderAction);
   }

   public void fromBytes(ByteBuf data) {
      this.traderID = data.readInt();
      this.traderAction = data.readByte();
   }

   protected void openTradeGUI(EntityPlayer entityplayer, LOTREntityNPC trader) {
      entityplayer.openGui(LOTRMod.instance, 7, entityplayer.field_70170_p, trader.func_145782_y(), 0, 0);
   }

   public static class Handler implements IMessageHandler {
      public IMessage onMessage(LOTRPacketUnitTraderInteract packet, MessageContext context) {
         EntityPlayerMP entityplayer = context.getServerHandler().field_147369_b;
         World world = entityplayer.field_70170_p;
         Entity trader = world.func_73045_a(packet.traderID);
         if (trader != null && trader instanceof LOTRHireableBase) {
            LOTRHireableBase tradeableTrader = (LOTRHireableBase)trader;
            LOTREntityNPC livingTrader = (LOTREntityNPC)trader;
            int action = packet.traderAction;
            boolean closeScreen = false;
            if (action == 0) {
               livingTrader.npcTalkTick = livingTrader.getNPCTalkInterval();
               closeScreen = livingTrader.func_130002_c(entityplayer);
            } else if (action == 1 && tradeableTrader.canTradeWith(entityplayer)) {
               packet.openTradeGUI(entityplayer, livingTrader);
            }

            if (closeScreen) {
               entityplayer.func_71053_j();
            }
         }

         return null;
      }
   }
}
