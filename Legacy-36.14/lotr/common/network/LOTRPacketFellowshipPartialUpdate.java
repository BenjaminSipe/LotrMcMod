package lotr.common.network;

import com.google.common.base.Charsets;
import com.mojang.authlib.GameProfile;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import java.io.IOException;
import java.util.UUID;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.LOTRPlayerData;
import lotr.common.LOTRTitle;
import lotr.common.fellowship.LOTRFellowship;
import lotr.common.fellowship.LOTRFellowshipClient;
import lotr.common.util.LOTRLog;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;

public abstract class LOTRPacketFellowshipPartialUpdate implements IMessage {
   protected UUID fellowshipID;

   protected LOTRPacketFellowshipPartialUpdate() {
   }

   protected LOTRPacketFellowshipPartialUpdate(LOTRFellowship fs) {
      this.fellowshipID = fs.getFellowshipID();
   }

   public void toBytes(ByteBuf data) {
      data.writeLong(this.fellowshipID.getMostSignificantBits());
      data.writeLong(this.fellowshipID.getLeastSignificantBits());
   }

   public void fromBytes(ByteBuf data) {
      this.fellowshipID = new UUID(data.readLong(), data.readLong());
   }

   public abstract void updateClient(LOTRFellowshipClient var1);

   public static class ToggleShowMap extends LOTRPacketFellowshipPartialUpdate {
      private boolean showMapLocations;

      public ToggleShowMap() {
      }

      public ToggleShowMap(LOTRFellowship fs) {
         super(fs);
         this.showMapLocations = fs.getShowMapLocations();
      }

      public void toBytes(ByteBuf data) {
         super.toBytes(data);
         data.writeBoolean(this.showMapLocations);
      }

      public void fromBytes(ByteBuf data) {
         super.fromBytes(data);
         this.showMapLocations = data.readBoolean();
      }

      public void updateClient(LOTRFellowshipClient fellowship) {
         fellowship.setShowMapLocations(this.showMapLocations);
      }

      public static class Handler extends LOTRPacketFellowshipPartialUpdate.Handler {
      }
   }

   public static class ToggleHiredFriendlyFire extends LOTRPacketFellowshipPartialUpdate {
      private boolean preventHiredFF;

      public ToggleHiredFriendlyFire() {
      }

      public ToggleHiredFriendlyFire(LOTRFellowship fs) {
         super(fs);
         this.preventHiredFF = fs.getPreventHiredFriendlyFire();
      }

      public void toBytes(ByteBuf data) {
         super.toBytes(data);
         data.writeBoolean(this.preventHiredFF);
      }

      public void fromBytes(ByteBuf data) {
         super.fromBytes(data);
         this.preventHiredFF = data.readBoolean();
      }

      public void updateClient(LOTRFellowshipClient fellowship) {
         fellowship.setPreventHiredFriendlyFire(this.preventHiredFF);
      }

      public static class Handler extends LOTRPacketFellowshipPartialUpdate.Handler {
      }
   }

   public static class TogglePvp extends LOTRPacketFellowshipPartialUpdate {
      private boolean preventPVP;

      public TogglePvp() {
      }

      public TogglePvp(LOTRFellowship fs) {
         super(fs);
         this.preventPVP = fs.getPreventPVP();
      }

      public void toBytes(ByteBuf data) {
         super.toBytes(data);
         data.writeBoolean(this.preventPVP);
      }

      public void fromBytes(ByteBuf data) {
         super.fromBytes(data);
         this.preventPVP = data.readBoolean();
      }

      public void updateClient(LOTRFellowshipClient fellowship) {
         fellowship.setPreventPVP(this.preventPVP);
      }

      public static class Handler extends LOTRPacketFellowshipPartialUpdate.Handler {
      }
   }

   public static class ChangeIcon extends LOTRPacketFellowshipPartialUpdate {
      private ItemStack fellowshipIcon;

      public ChangeIcon() {
      }

      public ChangeIcon(LOTRFellowship fs) {
         super(fs);
         this.fellowshipIcon = fs.getIcon();
      }

      public void toBytes(ByteBuf data) {
         super.toBytes(data);
         NBTTagCompound iconData = new NBTTagCompound();
         if (this.fellowshipIcon != null) {
            this.fellowshipIcon.func_77955_b(iconData);
         }

         try {
            (new PacketBuffer(data)).func_150786_a(iconData);
         } catch (IOException var4) {
            FMLLog.severe("LOTR: Error writing fellowship icon data", new Object[0]);
            var4.printStackTrace();
         }

      }

      public void fromBytes(ByteBuf data) {
         super.fromBytes(data);
         NBTTagCompound iconData = new NBTTagCompound();

         try {
            iconData = (new PacketBuffer(data)).func_150793_b();
         } catch (IOException var4) {
            FMLLog.severe("LOTR: Error reading fellowship icon data", new Object[0]);
            var4.printStackTrace();
         }

         this.fellowshipIcon = ItemStack.func_77949_a(iconData);
      }

      public void updateClient(LOTRFellowshipClient fellowship) {
         fellowship.setIcon(this.fellowshipIcon);
      }

      public static class Handler extends LOTRPacketFellowshipPartialUpdate.Handler {
      }
   }

   public static class Rename extends LOTRPacketFellowshipPartialUpdate {
      private String fellowshipName;

      public Rename() {
      }

      public Rename(LOTRFellowship fs) {
         super(fs);
         this.fellowshipName = fs.getName();
      }

      public void toBytes(ByteBuf data) {
         super.toBytes(data);
         byte[] fsNameBytes = this.fellowshipName.getBytes(Charsets.UTF_8);
         data.writeByte(fsNameBytes.length);
         data.writeBytes(fsNameBytes);
      }

      public void fromBytes(ByteBuf data) {
         super.fromBytes(data);
         int fsNameLength = data.readByte();
         ByteBuf fsNameBytes = data.readBytes(fsNameLength);
         this.fellowshipName = fsNameBytes.toString(Charsets.UTF_8);
      }

      public void updateClient(LOTRFellowshipClient fellowship) {
         fellowship.setName(this.fellowshipName);
      }

      public static class Handler extends LOTRPacketFellowshipPartialUpdate.Handler {
      }
   }

   public static class UpdatePlayerTitle extends LOTRPacketFellowshipPartialUpdate.OnePlayerUpdate {
      private LOTRTitle.PlayerTitle playerTitle;

      public UpdatePlayerTitle() {
      }

      public UpdatePlayerTitle(LOTRFellowship fs, UUID player, LOTRTitle.PlayerTitle title) {
         super(fs, player);
         this.playerTitle = title;
      }

      public void toBytes(ByteBuf data) {
         super.toBytes(data);
         LOTRTitle.PlayerTitle.writeNullableTitle(data, this.playerTitle);
      }

      public void fromBytes(ByteBuf data) {
         super.fromBytes(data);
         this.playerTitle = LOTRTitle.PlayerTitle.readNullableTitle(data);
      }

      public void updateClient(LOTRFellowshipClient fellowship) {
         fellowship.updatePlayerTitle(this.playerProfile.getId(), this.playerTitle);
      }

      public static class Handler extends LOTRPacketFellowshipPartialUpdate.Handler {
      }
   }

   public static class RemoveAdmin extends LOTRPacketFellowshipPartialUpdate.OnePlayerUpdate {
      private boolean isAdminned;

      public RemoveAdmin() {
      }

      public RemoveAdmin(LOTRFellowship fs, UUID admin, boolean adminned) {
         super(fs, admin);
         this.isAdminned = adminned;
      }

      public void toBytes(ByteBuf data) {
         super.toBytes(data);
         data.writeBoolean(this.isAdminned);
      }

      public void fromBytes(ByteBuf data) {
         super.fromBytes(data);
         this.isAdminned = data.readBoolean();
      }

      public void updateClient(LOTRFellowshipClient fellowship) {
         fellowship.removeAdmin(this.playerProfile.getId(), this.isAdminned);
      }

      public static class Handler extends LOTRPacketFellowshipPartialUpdate.Handler {
      }
   }

   public static class SetAdmin extends LOTRPacketFellowshipPartialUpdate.OnePlayerUpdate {
      private boolean isAdminned;

      public SetAdmin() {
      }

      public SetAdmin(LOTRFellowship fs, UUID admin, boolean adminned) {
         super(fs, admin);
         this.isAdminned = adminned;
      }

      public void toBytes(ByteBuf data) {
         super.toBytes(data);
         data.writeBoolean(this.isAdminned);
      }

      public void fromBytes(ByteBuf data) {
         super.fromBytes(data);
         this.isAdminned = data.readBoolean();
      }

      public void updateClient(LOTRFellowshipClient fellowship) {
         fellowship.setAdmin(this.playerProfile.getId(), this.isAdminned);
      }

      public static class Handler extends LOTRPacketFellowshipPartialUpdate.Handler {
      }
   }

   public static class RemoveMember extends LOTRPacketFellowshipPartialUpdate.OnePlayerUpdate {
      public RemoveMember() {
      }

      public RemoveMember(LOTRFellowship fs, UUID member) {
         super(fs, member);
      }

      public void updateClient(LOTRFellowshipClient fellowship) {
         fellowship.removeMember(this.playerProfile);
      }

      public static class Handler extends LOTRPacketFellowshipPartialUpdate.Handler {
      }
   }

   public static class AddMember extends LOTRPacketFellowshipPartialUpdate.OnePlayerUpdate {
      private LOTRTitle.PlayerTitle playerTitle;

      public AddMember() {
      }

      public AddMember(LOTRFellowship fs, UUID member) {
         super(fs, member);
         this.playerTitle = LOTRLevelData.getData(member).getPlayerTitle();
      }

      public void toBytes(ByteBuf data) {
         super.toBytes(data);
         LOTRTitle.PlayerTitle.writeNullableTitle(data, this.playerTitle);
      }

      public void fromBytes(ByteBuf data) {
         super.fromBytes(data);
         this.playerTitle = LOTRTitle.PlayerTitle.readNullableTitle(data);
      }

      public void updateClient(LOTRFellowshipClient fellowship) {
         fellowship.addMember(this.playerProfile, this.playerTitle);
      }

      public static class Handler extends LOTRPacketFellowshipPartialUpdate.Handler {
      }
   }

   public static class SetOwner extends LOTRPacketFellowshipPartialUpdate.OnePlayerUpdate {
      private boolean isOwned;

      public SetOwner() {
      }

      public SetOwner(LOTRFellowship fs, UUID owner, boolean owned) {
         super(fs, owner);
         this.isOwned = owned;
      }

      public void toBytes(ByteBuf data) {
         super.toBytes(data);
         data.writeBoolean(this.isOwned);
      }

      public void fromBytes(ByteBuf data) {
         super.fromBytes(data);
         this.isOwned = data.readBoolean();
      }

      public void updateClient(LOTRFellowshipClient fellowship) {
         fellowship.setOwner(this.playerProfile, this.isOwned);
      }

      public static class Handler extends LOTRPacketFellowshipPartialUpdate.Handler {
      }
   }

   public abstract static class OnePlayerUpdate extends LOTRPacketFellowshipPartialUpdate {
      protected GameProfile playerProfile;

      public OnePlayerUpdate() {
      }

      public OnePlayerUpdate(LOTRFellowship fs, UUID player) {
         super(fs);
         this.playerProfile = LOTRPacketFellowship.getPlayerProfileWithUsername(player);
      }

      public void toBytes(ByteBuf data) {
         super.toBytes(data);
         LOTRPacketFellowship.writePlayerUuidAndUsername(data, this.playerProfile);
      }

      public void fromBytes(ByteBuf data) {
         super.fromBytes(data);
         this.playerProfile = LOTRPacketFellowship.readPlayerUuidAndUsername(data);
      }
   }

   public abstract static class Handler implements IMessageHandler {
      public IMessage onMessage(LOTRPacketFellowshipPartialUpdate packet, MessageContext context) {
         EntityPlayer entityplayer = LOTRMod.proxy.getClientPlayer();
         LOTRPlayerData pd = LOTRLevelData.getData(entityplayer);
         LOTRFellowshipClient fellowship = pd.getClientFellowshipByID(packet.fellowshipID);
         if (fellowship != null) {
            packet.updateClient(fellowship);
         } else {
            LOTRLog.logger.warn("Client couldn't find fellowship for ID " + packet.fellowshipID);
         }

         return null;
      }
   }
}
