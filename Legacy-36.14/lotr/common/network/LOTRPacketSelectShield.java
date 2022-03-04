package lotr.common.network;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRShields;
import net.minecraft.entity.player.EntityPlayer;

public class LOTRPacketSelectShield implements IMessage {
   private LOTRShields shield;

   public LOTRPacketSelectShield() {
   }

   public LOTRPacketSelectShield(LOTRShields s) {
      this.shield = s;
   }

   public void toBytes(ByteBuf data) {
      if (this.shield == null) {
         data.writeByte(-1);
      } else {
         data.writeByte(this.shield.shieldID);
         data.writeByte(this.shield.shieldType.ordinal());
      }

   }

   public void fromBytes(ByteBuf data) {
      int shieldID = data.readByte();
      if (shieldID == -1) {
         this.shield = null;
      } else {
         int shieldTypeID = data.readByte();
         if (shieldTypeID >= 0 && shieldTypeID < LOTRShields.ShieldType.values().length) {
            LOTRShields.ShieldType shieldType = LOTRShields.ShieldType.values()[shieldTypeID];
            if (shieldID >= 0 && shieldID < shieldType.list.size()) {
               this.shield = (LOTRShields)shieldType.list.get(shieldID);
            } else {
               FMLLog.severe("Failed to update LOTR shield on server side: There is no shield with ID " + shieldID + " for shieldtype " + shieldTypeID, new Object[0]);
            }
         } else {
            FMLLog.severe("Failed to update LOTR shield on server side: There is no shieldtype with ID " + shieldTypeID, new Object[0]);
         }
      }

   }

   public static class Handler implements IMessageHandler {
      public IMessage onMessage(LOTRPacketSelectShield packet, MessageContext context) {
         EntityPlayer entityplayer = context.getServerHandler().field_147369_b;
         LOTRShields shield = packet.shield;
         if (shield != null && !shield.canPlayerWear(entityplayer)) {
            FMLLog.severe("Failed to update LOTR shield on server side: Player " + entityplayer.func_70005_c_() + " cannot wear shield " + shield.name(), new Object[0]);
         } else {
            LOTRLevelData.getData((EntityPlayer)entityplayer).setShield(shield);
            LOTRLevelData.sendShieldToAllPlayersInWorld(entityplayer, entityplayer.field_70170_p);
         }

         return null;
      }
   }
}
