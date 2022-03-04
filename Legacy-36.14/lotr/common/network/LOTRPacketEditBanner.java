package lotr.common.network;

import com.google.common.base.Charsets;
import com.mojang.authlib.GameProfile;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import java.util.List;
import lotr.common.entity.item.LOTRBannerWhitelistEntry;
import lotr.common.entity.item.LOTREntityBanner;
import lotr.common.fellowship.LOTRFellowship;
import lotr.common.fellowship.LOTRFellowshipProfile;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.StringUtils;
import net.minecraft.world.World;

public class LOTRPacketEditBanner implements IMessage {
   private int bannerID;
   public boolean playerSpecificProtection;
   public boolean selfProtection;
   public float alignmentProtection;
   public int whitelistLength;
   public String[] whitelistSlots;
   public int[] whitelistPerms;
   public int defaultPerms;

   public LOTRPacketEditBanner() {
   }

   public LOTRPacketEditBanner(LOTREntityBanner banner) {
      this.bannerID = banner.func_145782_y();
   }

   public void toBytes(ByteBuf data) {
      data.writeInt(this.bannerID);
      data.writeBoolean(this.playerSpecificProtection);
      data.writeBoolean(this.selfProtection);
      data.writeFloat(this.alignmentProtection);
      data.writeShort(this.whitelistLength);
      boolean sendWhitelist = this.whitelistSlots != null;
      data.writeBoolean(sendWhitelist);
      if (sendWhitelist) {
         data.writeShort(this.whitelistSlots.length);

         for(int index = 0; index < this.whitelistSlots.length; ++index) {
            data.writeShort(index);
            String username = this.whitelistSlots[index];
            if (StringUtils.func_151246_b(username)) {
               data.writeByte(-1);
            } else {
               byte[] usernameBytes = username.getBytes(Charsets.UTF_8);
               data.writeByte(usernameBytes.length);
               data.writeBytes(usernameBytes);
               data.writeShort(this.whitelistPerms[index]);
            }
         }

         data.writeShort(-1);
      }

      data.writeShort(this.defaultPerms);
   }

   public void fromBytes(ByteBuf data) {
      this.bannerID = data.readInt();
      this.playerSpecificProtection = data.readBoolean();
      this.selfProtection = data.readBoolean();
      this.alignmentProtection = data.readFloat();
      this.whitelistLength = data.readShort();
      boolean sendWhitelist = data.readBoolean();
      if (sendWhitelist) {
         this.whitelistSlots = new String[data.readShort()];
         this.whitelistPerms = new int[this.whitelistSlots.length];
         boolean var3 = false;

         short index;
         while((index = data.readShort()) >= 0) {
            int length = data.readByte();
            if (length == -1) {
               this.whitelistSlots[index] = null;
            } else {
               ByteBuf usernameBytes = data.readBytes(length);
               String name = usernameBytes.toString(Charsets.UTF_8);
               this.whitelistSlots[index] = name;
               this.whitelistPerms[index] = data.readShort();
            }
         }
      }

      this.defaultPerms = data.readShort();
   }

   public static class Handler implements IMessageHandler {
      public IMessage onMessage(LOTRPacketEditBanner packet, MessageContext context) {
         EntityPlayerMP entityplayer = context.getServerHandler().field_147369_b;
         World world = entityplayer.field_70170_p;
         Entity bEntity = world.func_73045_a(packet.bannerID);
         if (bEntity instanceof LOTREntityBanner) {
            LOTREntityBanner banner = (LOTREntityBanner)bEntity;
            if (banner.canPlayerEditBanner(entityplayer)) {
               banner.setPlayerSpecificProtection(packet.playerSpecificProtection);
               banner.setSelfProtection(packet.selfProtection);
               banner.setAlignmentProtection(packet.alignmentProtection);
               banner.resizeWhitelist(packet.whitelistLength);
               if (packet.whitelistSlots != null) {
                  for(int index = 0; index < packet.whitelistSlots.length; ++index) {
                     if (index != 0) {
                        String username = packet.whitelistSlots[index];
                        int perms = packet.whitelistPerms[index];
                        if (StringUtils.func_151246_b(username)) {
                           banner.whitelistPlayer(index, (GameProfile)null);
                        } else {
                           List decodedPerms = LOTRBannerWhitelistEntry.static_decodePermBitFlags(perms);
                           if (LOTRFellowshipProfile.hasFellowshipCode(username)) {
                              String fsName = LOTRFellowshipProfile.stripFellowshipCode(username);
                              LOTRFellowship fellowship = banner.getPlacersFellowshipByName(fsName);
                              if (fellowship != null) {
                                 banner.whitelistFellowship(index, fellowship, decodedPerms);
                              }
                           } else {
                              GameProfile profile = MinecraftServer.func_71276_C().func_152358_ax().func_152655_a(username);
                              if (profile != null) {
                                 banner.whitelistPlayer(index, profile, decodedPerms);
                              }
                           }
                        }
                     }
                  }
               }

               List defaultPerms = LOTRBannerWhitelistEntry.static_decodePermBitFlags(packet.defaultPerms);
               banner.setDefaultPermissions(defaultPerms);
            }
         }

         return null;
      }
   }
}
