package lotr.common.network;

import com.google.common.base.Charsets;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import lotr.common.item.LOTRItemBrandingIron;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.StringUtils;

public class LOTRPacketBrandingIron implements IMessage {
   private String brandName;

   public LOTRPacketBrandingIron() {
   }

   public LOTRPacketBrandingIron(String s) {
      this.brandName = s;
   }

   public void toBytes(ByteBuf data) {
      if (StringUtils.isBlank(this.brandName)) {
         data.writeInt(-1);
      } else {
         byte[] brandBytes = this.brandName.getBytes(Charsets.UTF_8);
         data.writeInt(brandBytes.length);
         data.writeBytes(brandBytes);
      }

   }

   public void fromBytes(ByteBuf data) {
      int length = data.readInt();
      if (length > -1) {
         this.brandName = data.readBytes(length).toString(Charsets.UTF_8);
      }

   }

   public static class Handler implements IMessageHandler {
      public IMessage onMessage(LOTRPacketBrandingIron packet, MessageContext context) {
         EntityPlayerMP entityplayer = context.getServerHandler().field_147369_b;
         ItemStack itemstack = entityplayer.func_71045_bC();
         if (itemstack != null && itemstack.func_77973_b() instanceof LOTRItemBrandingIron) {
            String brandName = packet.brandName;
            brandName = LOTRItemBrandingIron.trimAcceptableBrandName(brandName);
            if (!StringUtils.isBlank(brandName) && !LOTRItemBrandingIron.hasBrandName(itemstack)) {
               LOTRItemBrandingIron.setBrandName(itemstack, brandName);
            }
         }

         return null;
      }
   }
}
