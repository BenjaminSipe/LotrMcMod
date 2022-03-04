package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.command.server.CommandTeleport;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;

public class LOTRPacketMapTp implements IMessage {
   private int xCoord;
   private int zCoord;

   public LOTRPacketMapTp() {
   }

   public LOTRPacketMapTp(int x, int z) {
      this.xCoord = x;
      this.zCoord = z;
   }

   public void toBytes(ByteBuf data) {
      data.writeInt(this.xCoord);
      data.writeInt(this.zCoord);
   }

   public void fromBytes(ByteBuf data) {
      this.xCoord = data.readInt();
      this.zCoord = data.readInt();
   }

   public static class Handler implements IMessageHandler {
      public IMessage onMessage(LOTRPacketMapTp packet, MessageContext context) {
         EntityPlayerMP entityplayer = context.getServerHandler().field_147369_b;
         World world = entityplayer.field_70170_p;
         boolean isOp = MinecraftServer.func_71276_C().func_71203_ab().func_152596_g(entityplayer.func_146103_bH());
         if (isOp) {
            int i = packet.xCoord;
            int k = packet.zCoord;
            int j = world.func_72825_h(i, k);
            String[] args = new String[]{Integer.toString(i), Integer.toString(j), Integer.toString(k)};
            (new CommandTeleport()).func_71515_b(entityplayer, args);
         }

         return null;
      }
   }
}
