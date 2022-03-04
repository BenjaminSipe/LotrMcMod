package lotr.common.network;

import com.google.common.base.Charsets;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRSquadrons;
import lotr.common.entity.npc.LOTRHireableBase;
import lotr.common.entity.npc.LOTRMercenary;
import lotr.common.entity.npc.LOTRMercenaryTradeEntry;
import lotr.common.entity.npc.LOTRUnitTradeEntry;
import lotr.common.entity.npc.LOTRUnitTradeable;
import lotr.common.inventory.LOTRContainerUnitTrade;
import lotr.common.util.LOTRLog;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.StringUtils;
import net.minecraft.world.World;

public class LOTRPacketBuyUnit implements IMessage {
   private int tradeIndex;
   private String squadron;

   public LOTRPacketBuyUnit() {
   }

   public LOTRPacketBuyUnit(int tr, String s) {
      this.tradeIndex = tr;
      this.squadron = s;
   }

   public void toBytes(ByteBuf data) {
      data.writeByte(this.tradeIndex);
      if (!StringUtils.func_151246_b(this.squadron)) {
         byte[] squadronBytes = this.squadron.getBytes(Charsets.UTF_8);
         data.writeInt(squadronBytes.length);
         data.writeBytes(squadronBytes);
      } else {
         data.writeInt(-1);
      }

   }

   public void fromBytes(ByteBuf data) {
      this.tradeIndex = data.readByte();
      int squadronLength = data.readInt();
      if (squadronLength > -1) {
         this.squadron = data.readBytes(squadronLength).toString(Charsets.UTF_8);
      }

   }

   public static class Handler implements IMessageHandler {
      public IMessage onMessage(LOTRPacketBuyUnit packet, MessageContext context) {
         EntityPlayer entityplayer = context.getServerHandler().field_147369_b;
         World world = entityplayer.field_70170_p;
         Container container = entityplayer.field_71070_bA;
         if (container != null && container instanceof LOTRContainerUnitTrade) {
            LOTRContainerUnitTrade tradeContainer = (LOTRContainerUnitTrade)container;
            LOTRHireableBase unitTrader = tradeContainer.theUnitTrader;
            int tradeIndex = packet.tradeIndex;
            LOTRUnitTradeEntry trade = null;
            if (unitTrader instanceof LOTRUnitTradeable) {
               LOTRUnitTradeEntry[] tradeList = ((LOTRUnitTradeable)unitTrader).getUnits().tradeEntries;
               if (tradeIndex >= 0 && tradeIndex < tradeList.length) {
                  trade = tradeList[tradeIndex];
               }
            } else if (unitTrader instanceof LOTRMercenary) {
               trade = LOTRMercenaryTradeEntry.createFor((LOTRMercenary)unitTrader);
            }

            String squadron = packet.squadron;
            squadron = LOTRSquadrons.checkAcceptableLength(squadron);
            if (trade != null) {
               ((LOTRUnitTradeEntry)trade).hireUnit(entityplayer, unitTrader, squadron);
               if (unitTrader instanceof LOTRMercenary) {
                  entityplayer.func_71053_j();
               }
            } else {
               LOTRLog.logger.error("LOTR: Error player " + entityplayer.func_70005_c_() + " trying to hire unit from " + unitTrader.getNPCName() + " - trade is null or bad index!");
            }
         }

         return null;
      }
   }
}
