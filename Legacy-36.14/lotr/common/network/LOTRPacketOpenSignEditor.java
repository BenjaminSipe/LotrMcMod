package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRMod;
import lotr.common.tileentity.LOTRTileEntitySign;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class LOTRPacketOpenSignEditor implements IMessage {
   private int posX;
   private int posY;
   private int posZ;
   private Block blockType;
   private int blockMeta;

   public LOTRPacketOpenSignEditor() {
   }

   public LOTRPacketOpenSignEditor(LOTRTileEntitySign sign) {
      this.posX = sign.field_145851_c;
      this.posY = sign.field_145848_d;
      this.posZ = sign.field_145849_e;
      this.blockType = sign.func_145838_q();
      this.blockMeta = sign.func_145832_p();
   }

   public void toBytes(ByteBuf data) {
      data.writeInt(this.posX);
      data.writeInt(this.posY);
      data.writeInt(this.posZ);
      data.writeShort(Block.func_149682_b(this.blockType));
      data.writeByte(this.blockMeta);
   }

   public void fromBytes(ByteBuf data) {
      this.posX = data.readInt();
      this.posY = data.readInt();
      this.posZ = data.readInt();
      this.blockType = Block.func_149729_e(data.readShort());
      this.blockMeta = data.readByte();
   }

   public static class Handler implements IMessageHandler {
      public IMessage onMessage(LOTRPacketOpenSignEditor packet, MessageContext context) {
         EntityPlayer entityplayer = LOTRMod.proxy.getClientPlayer();
         World world = LOTRMod.proxy.getClientWorld();
         world.func_147465_d(packet.posX, packet.posY, packet.posZ, packet.blockType, packet.blockMeta, 3);
         entityplayer.openGui(LOTRMod.instance, 47, world, packet.posX, packet.posY, packet.posZ);
         return null;
      }
   }
}
