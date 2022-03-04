package lotr.common.network;

import com.google.common.base.Charsets;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import lotr.common.tileentity.LOTRTileEntitySign;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.world.World;

public class LOTRPacketEditSign implements IMessage {
   private int posX;
   private int posY;
   private int posZ;
   private String[] signText;

   public LOTRPacketEditSign() {
   }

   public LOTRPacketEditSign(LOTRTileEntitySign sign) {
      this.posX = sign.field_145851_c;
      this.posY = sign.field_145848_d;
      this.posZ = sign.field_145849_e;
      this.signText = sign.signText;
   }

   public void toBytes(ByteBuf data) {
      data.writeInt(this.posX);
      data.writeInt(this.posY);
      data.writeInt(this.posZ);
      data.writeByte(this.signText.length);

      for(int i = 0; i < this.signText.length; ++i) {
         String line = this.signText[i];
         if (line == null) {
            data.writeShort(-1);
         } else {
            byte[] lineBytes = line.getBytes(Charsets.UTF_8);
            data.writeShort(lineBytes.length);
            data.writeBytes(lineBytes);
         }
      }

   }

   public void fromBytes(ByteBuf data) {
      this.posX = data.readInt();
      this.posY = data.readInt();
      this.posZ = data.readInt();
      int lines = data.readByte();
      this.signText = new String[lines];

      for(int i = 0; i < this.signText.length; ++i) {
         int length = data.readShort();
         if (length > -1) {
            String line = data.readBytes(length).toString(Charsets.UTF_8);
            this.signText[i] = line;
         } else {
            this.signText[i] = "";
         }
      }

   }

   public static class Handler implements IMessageHandler {
      public IMessage onMessage(LOTRPacketEditSign packet, MessageContext context) {
         EntityPlayerMP entityplayer = context.getServerHandler().field_147369_b;
         entityplayer.func_143004_u();
         World world = entityplayer.field_70170_p;
         int i = packet.posX;
         int j = packet.posY;
         int k = packet.posZ;
         String[] newText = packet.signText;
         if (world.func_72899_e(i, j, k)) {
            TileEntity te = world.func_147438_o(i, j, k);
            if (te instanceof LOTRTileEntitySign) {
               LOTRTileEntitySign sign = (LOTRTileEntitySign)te;
               if (!sign.isEditable() || sign.getEditingPlayer() != entityplayer) {
                  MinecraftServer.func_71276_C().func_71236_h("Player " + entityplayer.func_70005_c_() + " just tried to change non-editable LOTR sign");
                  return null;
               }

               for(int l = 0; l < sign.getNumLines(); ++l) {
                  String line = newText[l];
                  boolean valid = true;
                  if (line.length() > 15) {
                     valid = false;
                  } else {
                     for(int c = 0; c < line.length(); ++c) {
                        if (!ChatAllowedCharacters.func_71566_a(line.charAt(c))) {
                           valid = false;
                        }
                     }
                  }

                  if (!valid) {
                     newText[l] = "!?";
                  }
               }

               System.arraycopy(newText, 0, sign.signText, 0, sign.getNumLines());
               sign.func_70296_d();
               world.func_147471_g(i, j, k);
            }
         }

         return null;
      }
   }
}
