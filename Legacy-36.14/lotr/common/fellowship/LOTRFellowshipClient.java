package lotr.common.fellowship;

import com.mojang.authlib.GameProfile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import lotr.common.LOTRTitle;
import net.minecraft.item.ItemStack;

public class LOTRFellowshipClient {
   private UUID fellowshipID;
   private String fellowshipName;
   private ItemStack fellowshipIcon;
   private boolean isOwned;
   private boolean isAdminned;
   private UUID ownerUUID;
   private List memberUUIDs = new ArrayList();
   private Map usernameMap = new HashMap();
   private Map titleMap = new HashMap();
   private Set adminUUIDs = new HashSet();
   private boolean preventPVP;
   private boolean preventHiredFF;
   private boolean showMapLocations;

   public LOTRFellowshipClient(UUID id, String name, boolean owned, boolean admin, GameProfile owner, List members) {
      this.fellowshipID = id;
      this.fellowshipName = name;
      this.isOwned = owned;
      this.isAdminned = admin;
      this.ownerUUID = owner.getId();
      this.usernameMap.put(this.ownerUUID, owner.getName());
      Iterator var7 = members.iterator();

      while(var7.hasNext()) {
         GameProfile member = (GameProfile)var7.next();
         this.memberUUIDs.add(member.getId());
         this.usernameMap.put(member.getId(), member.getName());
      }

   }

   public void setOwner(GameProfile newOwner, boolean owned) {
      UUID prevOwnerUuid = this.ownerUUID;
      UUID newOwnerUuid = newOwner.getId();
      if (!prevOwnerUuid.equals(newOwnerUuid)) {
         if (!this.memberUUIDs.contains(prevOwnerUuid)) {
            this.memberUUIDs.add(0, prevOwnerUuid);
         }

         this.ownerUUID = newOwnerUuid;
         this.usernameMap.put(this.ownerUUID, newOwner.getName());
         if (this.memberUUIDs.contains(newOwnerUuid)) {
            this.memberUUIDs.remove(newOwnerUuid);
         }

         if (this.adminUUIDs.contains(newOwnerUuid)) {
            this.adminUUIDs.remove(newOwnerUuid);
         }

         this.isOwned = owned;
         if (this.isOwned) {
            this.isAdminned = false;
         }
      }

   }

   public void addMember(GameProfile member, LOTRTitle.PlayerTitle title) {
      UUID memberUuid = member.getId();
      if (!this.memberUUIDs.contains(memberUuid)) {
         this.memberUUIDs.add(memberUuid);
         this.usernameMap.put(memberUuid, member.getName());
         this.titleMap.put(memberUuid, title);
      }

   }

   public void removeMember(GameProfile member) {
      UUID memberUuid = member.getId();
      if (this.memberUUIDs.contains(memberUuid)) {
         this.memberUUIDs.remove(memberUuid);
         this.usernameMap.remove(memberUuid);
         if (this.adminUUIDs.contains(memberUuid)) {
            this.adminUUIDs.remove(memberUuid);
         }

         this.titleMap.remove(memberUuid);
      }

   }

   public void setTitles(Map titles) {
      this.titleMap = titles;
   }

   public void updatePlayerTitle(UUID playerUuid, LOTRTitle.PlayerTitle title) {
      if (title == null) {
         this.titleMap.remove(playerUuid);
      } else {
         this.titleMap.put(playerUuid, title);
      }

   }

   public void setAdmins(Set admins) {
      this.adminUUIDs = admins;
   }

   public void setAdmin(UUID playerUuid, boolean adminned) {
      if (!this.adminUUIDs.contains(playerUuid)) {
         this.adminUUIDs.add(playerUuid);
         this.isAdminned = adminned;
      }

   }

   public void removeAdmin(UUID playerUuid, boolean adminned) {
      if (this.adminUUIDs.contains(playerUuid)) {
         this.adminUUIDs.remove(playerUuid);
         this.isAdminned = adminned;
      }

   }

   public void setName(String name) {
      this.fellowshipName = name;
   }

   public void setIcon(ItemStack itemstack) {
      this.fellowshipIcon = itemstack;
   }

   public void setPreventPVP(boolean flag) {
      this.preventPVP = flag;
   }

   public void setPreventHiredFriendlyFire(boolean flag) {
      this.preventHiredFF = flag;
   }

   public void setShowMapLocations(boolean flag) {
      this.showMapLocations = flag;
   }

   public UUID getFellowshipID() {
      return this.fellowshipID;
   }

   public String getName() {
      return this.fellowshipName;
   }

   public boolean isOwned() {
      return this.isOwned;
   }

   public boolean isAdminned() {
      return this.isAdminned;
   }

   public UUID getOwnerUuid() {
      return this.ownerUUID;
   }

   public List getMemberUuids() {
      return this.memberUUIDs;
   }

   public List getAllPlayerUuids() {
      List allPlayers = new ArrayList();
      allPlayers.add(this.ownerUUID);
      allPlayers.addAll(this.memberUUIDs);
      return allPlayers;
   }

   private GameProfile getProfileFor(UUID playerUuid) {
      return new GameProfile(playerUuid, this.getUsernameFor(playerUuid));
   }

   private List getProfilesFor(List playerUuids) {
      List list = new ArrayList();
      Iterator var3 = playerUuids.iterator();

      while(var3.hasNext()) {
         UUID playerUuid = (UUID)var3.next();
         list.add(this.getProfileFor(playerUuid));
      }

      return list;
   }

   public GameProfile getOwnerProfile() {
      return this.getProfileFor(this.ownerUUID);
   }

   public List getMemberProfiles() {
      return this.getProfilesFor(this.memberUUIDs);
   }

   public List getAllPlayerProfiles() {
      return this.getProfilesFor(this.getAllPlayerUuids());
   }

   public String getUsernameFor(UUID playerUuid) {
      return (String)this.usernameMap.get(playerUuid);
   }

   public boolean containsPlayer(UUID playerUuid) {
      return this.ownerUUID.equals(playerUuid) || this.memberUUIDs.contains(playerUuid);
   }

   public boolean containsPlayerUsername(String username) {
      return this.usernameMap.values().contains(username);
   }

   public int getPlayerCount() {
      return this.memberUUIDs.size() + 1;
   }

   public LOTRTitle.PlayerTitle getTitleFor(UUID playerUuid) {
      return (LOTRTitle.PlayerTitle)this.titleMap.get(playerUuid);
   }

   public boolean isAdmin(UUID playerUuid) {
      return this.adminUUIDs.contains(playerUuid);
   }

   public ItemStack getIcon() {
      return this.fellowshipIcon;
   }

   public boolean getPreventPVP() {
      return this.preventPVP;
   }

   public boolean getPreventHiredFriendlyFire() {
      return this.preventHiredFF;
   }

   public boolean getShowMapLocations() {
      return this.showMapLocations;
   }

   public void updateDataFrom(LOTRFellowshipClient other) {
      this.fellowshipName = other.fellowshipName;
      this.fellowshipIcon = other.fellowshipIcon;
      this.isOwned = other.isOwned;
      this.isAdminned = other.isAdminned;
      this.ownerUUID = other.ownerUUID;
      this.memberUUIDs = other.memberUUIDs;
      this.usernameMap = other.usernameMap;
      this.titleMap = other.titleMap;
      this.adminUUIDs = other.adminUUIDs;
      this.preventPVP = other.preventPVP;
      this.preventHiredFF = other.preventHiredFF;
      this.showMapLocations = other.showMapLocations;
   }
}
