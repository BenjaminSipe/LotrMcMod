package lotr.common.network;

import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.common.network.internal.EntitySpawnHandler;
import cpw.mods.fml.common.network.internal.FMLRuntimeCodec;
import cpw.mods.fml.common.network.internal.FMLMessage.EntityMessage;
import cpw.mods.fml.common.network.internal.FMLMessage.EntitySpawnMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lotr.common.util.LOTRLog;

public class LOTRPacketSpawnMob implements IMessage {
   private ByteBuf spawnData;

   public LOTRPacketSpawnMob() {
   }

   public LOTRPacketSpawnMob(ByteBuf data) {
      this.spawnData = data.copy();
   }

   public void toBytes(ByteBuf data) {
      data.writeInt(this.spawnData.readableBytes());
      data.writeBytes(this.spawnData);
   }

   public void fromBytes(ByteBuf data) {
      int len = data.readInt();
      this.spawnData = data.readBytes(len);
   }

   private static class AdhocEntitySpawnHandler extends EntitySpawnHandler {
      private AdhocEntitySpawnHandler() {
      }

      public void channelRead0(ChannelHandlerContext ctx, EntityMessage msg) throws Exception {
         super.channelRead0(ctx, msg);
      }

      // $FF: synthetic method
      AdhocEntitySpawnHandler(Object x0) {
         this();
      }
   }

   public static class Handler implements IMessageHandler {
      public IMessage onMessage(LOTRPacketSpawnMob packet, MessageContext context) {
         EntitySpawnMessage msg = new EntitySpawnMessage();
         (new FMLRuntimeCodec()).decodeInto((ChannelHandlerContext)null, packet.spawnData, msg);
         int modEntityID = 999999999;
         double x = 999.0D;
         double y = 999.0D;
         double z = 999.0D;

         try {
            modEntityID = (Integer)ObfuscationReflectionHelper.getPrivateValue(EntitySpawnMessage.class, msg, new String[]{"modEntityTypeId"});
            x = (Double)ObfuscationReflectionHelper.getPrivateValue(EntitySpawnMessage.class, msg, new String[]{"scaledX"});
            y = (Double)ObfuscationReflectionHelper.getPrivateValue(EntitySpawnMessage.class, msg, new String[]{"scaledY"});
            z = (Double)ObfuscationReflectionHelper.getPrivateValue(EntitySpawnMessage.class, msg, new String[]{"scaledZ"});
         } catch (Exception var13) {
         }

         LOTRLog.logger.info("LOTR: Received mob spawn packet: " + modEntityID + "[" + x + ", " + y + ", " + z + "]");

         try {
            (new LOTRPacketSpawnMob.AdhocEntitySpawnHandler()).channelRead0((ChannelHandlerContext)null, (EntityMessage)msg);
         } catch (Exception var12) {
            LOTRLog.logger.error("LOTR: FATAL ERROR spawning entity!!!");
            var12.printStackTrace();
         }

         return null;
      }
   }
}
