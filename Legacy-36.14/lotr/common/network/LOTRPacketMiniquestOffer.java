package lotr.common.network;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import java.io.IOException;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.LOTRPlayerData;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.quest.LOTRMiniQuest;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;

public class LOTRPacketMiniquestOffer implements IMessage {
   private int entityID;
   private NBTTagCompound miniquestData;

   public LOTRPacketMiniquestOffer() {
   }

   public LOTRPacketMiniquestOffer(int id, NBTTagCompound nbt) {
      this.entityID = id;
      this.miniquestData = nbt;
   }

   public void toBytes(ByteBuf data) {
      data.writeInt(this.entityID);

      try {
         (new PacketBuffer(data)).func_150786_a(this.miniquestData);
      } catch (IOException var3) {
         FMLLog.severe("Error writing miniquest data", new Object[0]);
         var3.printStackTrace();
      }

   }

   public void fromBytes(ByteBuf data) {
      this.entityID = data.readInt();

      try {
         this.miniquestData = (new PacketBuffer(data)).func_150793_b();
      } catch (IOException var3) {
         FMLLog.severe("Error reading miniquest data", new Object[0]);
         var3.printStackTrace();
      }

   }

   public static void sendClosePacket(EntityPlayer entityplayer, LOTREntityNPC npc, boolean accept) {
      if (entityplayer == null) {
         FMLLog.warning("LOTR Warning: Tried to send miniquest offer close packet, but player == null", new Object[0]);
      } else {
         LOTRPacketMiniquestOfferClose packet = new LOTRPacketMiniquestOfferClose(npc.func_145782_y(), accept);
         LOTRPacketHandler.networkWrapper.sendToServer(packet);
      }
   }

   public static class Handler implements IMessageHandler {
      public IMessage onMessage(LOTRPacketMiniquestOffer packet, MessageContext context) {
         EntityPlayer entityplayer = LOTRMod.proxy.getClientPlayer();
         LOTRPlayerData pd = LOTRLevelData.getData(entityplayer);
         World world = LOTRMod.proxy.getClientWorld();
         Entity entity = world.func_73045_a(packet.entityID);
         if (entity instanceof LOTREntityNPC) {
            LOTREntityNPC npc = (LOTREntityNPC)entity;
            LOTRMiniQuest quest = LOTRMiniQuest.loadQuestFromNBT(packet.miniquestData, pd);
            if (quest != null) {
               LOTRMod.proxy.displayMiniquestOffer(quest, npc);
            } else {
               LOTRPacketMiniquestOffer.sendClosePacket(entityplayer, npc, false);
            }
         }

         return null;
      }
   }
}
