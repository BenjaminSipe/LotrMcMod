package lotr.common.network;

import com.google.common.base.Charsets;
import com.mojang.authlib.GameProfile;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.LOTRPlayerData;
import lotr.common.LOTRTitle;
import lotr.common.fellowship.LOTRFellowship;
import lotr.common.fellowship.LOTRFellowshipClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.UsernameCache;
import org.apache.commons.lang3.StringUtils;

public class LOTRPacketFellowship implements IMessage {
   private UUID fellowshipID;
   private boolean isInvite;
   private String fellowshipName;
   private ItemStack fellowshipIcon;
   private boolean isOwned;
   private boolean isAdminned;
   private GameProfile owner;
   private List members = new ArrayList();
   private Map titleMap = new HashMap();
   private Set adminUuids = new HashSet();
   private boolean preventPVP;
   private boolean preventHiredFF;
   private boolean showMapLocations;

   public LOTRPacketFellowship() {
   }

   public LOTRPacketFellowship(LOTRPlayerData playerData, LOTRFellowship fs, boolean invite) {
      this.fellowshipID = fs.getFellowshipID();
      this.isInvite = invite;
      this.fellowshipName = fs.getName();
      this.fellowshipIcon = fs.getIcon();
      UUID thisPlayer = playerData.getPlayerUUID();
      this.isOwned = fs.isOwner(thisPlayer);
      this.isAdminned = fs.isAdmin(thisPlayer);
      List playerIDs = fs.getAllPlayerUUIDs();
      Iterator var6 = playerIDs.iterator();

      while(var6.hasNext()) {
         UUID player = (UUID)var6.next();
         GameProfile profile = getPlayerProfileWithUsername(player);
         if (fs.isOwner(player)) {
            this.owner = profile;
         } else {
            this.members.add(profile);
         }

         LOTRTitle.PlayerTitle title = LOTRLevelData.getPlayerTitleWithOfflineCache(player);
         if (title != null) {
            this.titleMap.put(player, title);
         }

         if (fs.isAdmin(player)) {
            this.adminUuids.add(player);
         }
      }

      this.preventPVP = fs.getPreventPVP();
      this.preventHiredFF = fs.getPreventHiredFriendlyFire();
      this.showMapLocations = fs.getShowMapLocations();
   }

   public static GameProfile getPlayerProfileWithUsername(UUID player) {
      GameProfile profile = MinecraftServer.func_71276_C().func_152358_ax().func_152652_a(player);
      if (profile == null || StringUtils.isBlank(profile.getName())) {
         String name = UsernameCache.getLastKnownUsername(player);
         if (name != null) {
            profile = new GameProfile(player, name);
         } else {
            profile = new GameProfile(player, "");
            MinecraftServer.func_71276_C().func_147130_as().fillProfileProperties(profile, true);
         }
      }

      return profile;
   }

   public void toBytes(ByteBuf data) {
      data.writeLong(this.fellowshipID.getMostSignificantBits());
      data.writeLong(this.fellowshipID.getLeastSignificantBits());
      data.writeBoolean(this.isInvite);
      byte[] fsNameBytes = this.fellowshipName.getBytes(Charsets.UTF_8);
      data.writeByte(fsNameBytes.length);
      data.writeBytes(fsNameBytes);
      NBTTagCompound iconData = new NBTTagCompound();
      if (this.fellowshipIcon != null) {
         this.fellowshipIcon.func_77955_b(iconData);
      }

      try {
         (new PacketBuffer(data)).func_150786_a(iconData);
      } catch (IOException var9) {
         FMLLog.severe("LOTR: Error writing fellowship icon data", new Object[0]);
         var9.printStackTrace();
      }

      data.writeBoolean(this.isOwned);
      data.writeBoolean(this.isAdminned);
      writePlayerUuidAndUsername(data, this.owner);
      LOTRTitle.PlayerTitle.writeNullableTitle(data, (LOTRTitle.PlayerTitle)this.titleMap.get(this.owner.getId()));
      data.writeInt(this.members.size());
      Iterator var4 = this.members.iterator();

      while(var4.hasNext()) {
         GameProfile member = (GameProfile)var4.next();
         UUID memberUuid = member.getId();
         LOTRTitle.PlayerTitle title = (LOTRTitle.PlayerTitle)this.titleMap.get(memberUuid);
         boolean admin = this.adminUuids.contains(memberUuid);
         writePlayerUuidAndUsername(data, member);
         LOTRTitle.PlayerTitle.writeNullableTitle(data, title);
         data.writeBoolean(admin);
      }

      data.writeBoolean(this.preventPVP);
      data.writeBoolean(this.preventHiredFF);
      data.writeBoolean(this.showMapLocations);
   }

   public static void writePlayerUuidAndUsername(ByteBuf data, GameProfile profile) {
      UUID uuid = profile.getId();
      String username = profile.getName();
      data.writeLong(uuid.getMostSignificantBits());
      data.writeLong(uuid.getLeastSignificantBits());
      byte[] usernameBytes = username.getBytes(Charsets.UTF_8);
      data.writeByte(usernameBytes.length);
      data.writeBytes(usernameBytes);
   }

   public void fromBytes(ByteBuf data) {
      this.fellowshipID = new UUID(data.readLong(), data.readLong());
      this.isInvite = data.readBoolean();
      int fsNameLength = data.readByte();
      ByteBuf fsNameBytes = data.readBytes(fsNameLength);
      this.fellowshipName = fsNameBytes.toString(Charsets.UTF_8);
      NBTTagCompound iconData = new NBTTagCompound();

      try {
         iconData = (new PacketBuffer(data)).func_150793_b();
      } catch (IOException var10) {
         FMLLog.severe("LOTR: Error reading fellowship icon data", new Object[0]);
         var10.printStackTrace();
      }

      this.fellowshipIcon = ItemStack.func_77949_a(iconData);
      this.isOwned = data.readBoolean();
      this.isAdminned = data.readBoolean();
      this.owner = readPlayerUuidAndUsername(data);
      this.readTitleForPlayer(data, this.owner.getId());
      int numMembers = data.readInt();

      for(int i = 0; i < numMembers; ++i) {
         GameProfile member = readPlayerUuidAndUsername(data);
         if (member != null) {
            this.members.add(member);
            UUID memberUuid = member.getId();
            this.readTitleForPlayer(data, memberUuid);
            boolean admin = data.readBoolean();
            if (admin) {
               this.adminUuids.add(memberUuid);
            }
         }
      }

      this.preventPVP = data.readBoolean();
      this.preventHiredFF = data.readBoolean();
      this.showMapLocations = data.readBoolean();
   }

   public static GameProfile readPlayerUuidAndUsername(ByteBuf data) {
      UUID uuid = new UUID(data.readLong(), data.readLong());
      int nameLength = data.readByte();
      if (nameLength >= 0) {
         ByteBuf nameBytes = data.readBytes(nameLength);
         String username = nameBytes.toString(Charsets.UTF_8);
         return new GameProfile(uuid, username);
      } else {
         return null;
      }
   }

   private void readTitleForPlayer(ByteBuf data, UUID playerUuid) {
      LOTRTitle.PlayerTitle playerTitle = LOTRTitle.PlayerTitle.readNullableTitle(data);
      if (playerTitle != null) {
         this.titleMap.put(playerUuid, playerTitle);
      }

   }

   public static class Handler implements IMessageHandler {
      public IMessage onMessage(LOTRPacketFellowship packet, MessageContext context) {
         LOTRFellowshipClient fellowship = new LOTRFellowshipClient(packet.fellowshipID, packet.fellowshipName, packet.isOwned, packet.isAdminned, packet.owner, packet.members);
         fellowship.setTitles(packet.titleMap);
         fellowship.setAdmins(packet.adminUuids);
         fellowship.setIcon(packet.fellowshipIcon);
         fellowship.setPreventPVP(packet.preventPVP);
         fellowship.setPreventHiredFriendlyFire(packet.preventHiredFF);
         fellowship.setShowMapLocations(packet.showMapLocations);
         EntityPlayer entityplayer = LOTRMod.proxy.getClientPlayer();
         if (packet.isInvite) {
            LOTRLevelData.getData(entityplayer).addOrUpdateClientFellowshipInvite(fellowship);
         } else {
            LOTRLevelData.getData(entityplayer).addOrUpdateClientFellowship(fellowship);
         }

         return null;
      }
   }
}
