package lotr.common.network;

import com.google.common.base.Charsets;
import com.mojang.authlib.GameProfile;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import java.util.List;
import java.util.UUID;
import lotr.common.LOTRMod;
import lotr.common.entity.item.LOTRBannerWhitelistEntry;
import lotr.common.entity.item.LOTREntityBanner;
import lotr.common.fellowship.LOTRFellowshipProfile;
import net.minecraft.entity.Entity;
import net.minecraft.util.StringUtils;
import net.minecraft.world.World;

public class LOTRPacketBannerData implements IMessage {
   private int entityID;
   private boolean openGui;
   public boolean playerSpecificProtection;
   public boolean selfProtection;
   public boolean structureProtection;
   public int customRange;
   public float alignmentProtection;
   public int whitelistLength;
   public String[] whitelistSlots;
   public int[] whitelistPerms;
   public int defaultPerms;
   public boolean thisPlayerHasPermission;

   public LOTRPacketBannerData() {
   }

   public LOTRPacketBannerData(int id, boolean flag) {
      this.entityID = id;
      this.openGui = flag;
   }

   public void toBytes(ByteBuf data) {
      data.writeInt(this.entityID);
      data.writeBoolean(this.openGui);
      data.writeBoolean(this.playerSpecificProtection);
      data.writeBoolean(this.selfProtection);
      data.writeBoolean(this.structureProtection);
      data.writeShort(this.customRange);
      data.writeFloat(this.alignmentProtection);
      data.writeShort(this.whitelistLength);
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
      data.writeShort(this.defaultPerms);
      data.writeBoolean(this.thisPlayerHasPermission);
   }

   public void fromBytes(ByteBuf data) {
      this.entityID = data.readInt();
      this.openGui = data.readBoolean();
      this.playerSpecificProtection = data.readBoolean();
      this.selfProtection = data.readBoolean();
      this.structureProtection = data.readBoolean();
      this.customRange = data.readShort();
      this.alignmentProtection = data.readFloat();
      this.whitelistLength = data.readShort();
      this.whitelistSlots = new String[data.readShort()];
      this.whitelistPerms = new int[this.whitelistSlots.length];
      boolean var2 = false;

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

      this.defaultPerms = data.readShort();
      this.thisPlayerHasPermission = data.readBoolean();
   }

   public static class Handler implements IMessageHandler {
      public IMessage onMessage(LOTRPacketBannerData packet, MessageContext context) {
         World world = LOTRMod.proxy.getClientWorld();
         Entity entity = world.func_73045_a(packet.entityID);
         if (entity instanceof LOTREntityBanner) {
            LOTREntityBanner banner = (LOTREntityBanner)entity;
            banner.setPlayerSpecificProtection(packet.playerSpecificProtection);
            banner.setSelfProtection(packet.selfProtection);
            banner.setStructureProtection(packet.structureProtection);
            banner.setCustomRange(packet.customRange);
            banner.setAlignmentProtection(packet.alignmentProtection);
            banner.resizeWhitelist(packet.whitelistLength);

            for(int index = 0; index < packet.whitelistSlots.length; ++index) {
               String username = packet.whitelistSlots[index];
               if (StringUtils.func_151246_b(username)) {
                  banner.whitelistPlayer(index, (GameProfile)null);
               } else if (LOTRFellowshipProfile.hasFellowshipCode(username)) {
                  String fsName = LOTRFellowshipProfile.stripFellowshipCode(username);
                  LOTRFellowshipProfile profile = new LOTRFellowshipProfile(banner, (UUID)null, fsName);
                  banner.whitelistPlayer(index, profile);
               } else {
                  GameProfile profile = new GameProfile((UUID)null, username);
                  banner.whitelistPlayer(index, profile);
               }

               LOTRBannerWhitelistEntry entry = banner.getWhitelistEntry(index);
               if (entry != null) {
                  entry.decodePermBitFlags(packet.whitelistPerms[index]);
               }
            }

            List defaultPerms = LOTRBannerWhitelistEntry.static_decodePermBitFlags(packet.defaultPerms);
            banner.setDefaultPermissions(defaultPerms);
            banner.setClientside_playerHasPermissionInSurvival(packet.thisPlayerHasPermission);
            if (packet.openGui) {
               LOTRMod.proxy.displayBannerGui(banner);
            }
         }

         return null;
      }
   }
}
