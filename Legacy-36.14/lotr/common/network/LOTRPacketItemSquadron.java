package lotr.common.network;

import com.google.common.base.Charsets;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRSquadrons;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StringUtils;

public class LOTRPacketItemSquadron implements IMessage {
   private String squadron;

   public LOTRPacketItemSquadron() {
   }

   public LOTRPacketItemSquadron(String s) {
      this.squadron = s;
   }

   public void toBytes(ByteBuf data) {
      if (StringUtils.func_151246_b(this.squadron)) {
         data.writeInt(-1);
      } else {
         byte[] sqBytes = this.squadron.getBytes(Charsets.UTF_8);
         data.writeInt(sqBytes.length);
         data.writeBytes(sqBytes);
      }

   }

   public void fromBytes(ByteBuf data) {
      int length = data.readInt();
      if (length > -1) {
         this.squadron = data.readBytes(length).toString(Charsets.UTF_8);
      }

   }

   public static class Handler implements IMessageHandler {
      public IMessage onMessage(LOTRPacketItemSquadron packet, MessageContext context) {
         EntityPlayerMP entityplayer = context.getServerHandler().field_147369_b;
         ItemStack itemstack = entityplayer.func_71045_bC();
         if (itemstack != null && itemstack.func_77973_b() instanceof LOTRSquadrons.SquadronItem) {
            String squadron = packet.squadron;
            if (!StringUtils.func_151246_b(squadron)) {
               squadron = LOTRSquadrons.checkAcceptableLength(squadron);
               LOTRSquadrons.setSquadron(itemstack, squadron);
            } else {
               LOTRSquadrons.setSquadron(itemstack, "");
            }
         }

         return null;
      }
   }
}
