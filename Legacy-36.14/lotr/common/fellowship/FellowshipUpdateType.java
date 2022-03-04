package lotr.common.fellowship;

import com.google.common.collect.ImmutableList;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import java.util.List;
import java.util.UUID;
import lotr.common.LOTRPlayerData;
import lotr.common.LOTRTitle;
import lotr.common.network.LOTRPacketFellowship;
import lotr.common.network.LOTRPacketFellowshipPartialUpdate;

public interface FellowshipUpdateType {
   IMessage createUpdatePacket(LOTRPlayerData var1, LOTRFellowship var2);

   List getPlayersToCheckSharedWaypointsFrom(LOTRFellowship var1);

   public static class ToggleShowMapLocations implements FellowshipUpdateType {
      public IMessage createUpdatePacket(LOTRPlayerData pd, LOTRFellowship fs) {
         return new LOTRPacketFellowshipPartialUpdate.ToggleShowMap(fs);
      }

      public List getPlayersToCheckSharedWaypointsFrom(LOTRFellowship fs) {
         return null;
      }
   }

   public static class ToggleHiredFriendlyFire implements FellowshipUpdateType {
      public IMessage createUpdatePacket(LOTRPlayerData pd, LOTRFellowship fs) {
         return new LOTRPacketFellowshipPartialUpdate.ToggleHiredFriendlyFire(fs);
      }

      public List getPlayersToCheckSharedWaypointsFrom(LOTRFellowship fs) {
         return null;
      }
   }

   public static class TogglePvp implements FellowshipUpdateType {
      public IMessage createUpdatePacket(LOTRPlayerData pd, LOTRFellowship fs) {
         return new LOTRPacketFellowshipPartialUpdate.TogglePvp(fs);
      }

      public List getPlayersToCheckSharedWaypointsFrom(LOTRFellowship fs) {
         return null;
      }
   }

   public static class ChangeIcon implements FellowshipUpdateType {
      public IMessage createUpdatePacket(LOTRPlayerData pd, LOTRFellowship fs) {
         return new LOTRPacketFellowshipPartialUpdate.ChangeIcon(fs);
      }

      public List getPlayersToCheckSharedWaypointsFrom(LOTRFellowship fs) {
         return null;
      }
   }

   public static class Rename implements FellowshipUpdateType {
      public IMessage createUpdatePacket(LOTRPlayerData pd, LOTRFellowship fs) {
         return new LOTRPacketFellowshipPartialUpdate.Rename(fs);
      }

      public List getPlayersToCheckSharedWaypointsFrom(LOTRFellowship fs) {
         return null;
      }
   }

   public static class UpdatePlayerTitle implements FellowshipUpdateType {
      private final UUID playerID;
      private final LOTRTitle.PlayerTitle playerTitle;

      public UpdatePlayerTitle(UUID player, LOTRTitle.PlayerTitle title) {
         this.playerID = player;
         this.playerTitle = title;
      }

      public IMessage createUpdatePacket(LOTRPlayerData pd, LOTRFellowship fs) {
         return new LOTRPacketFellowshipPartialUpdate.UpdatePlayerTitle(fs, this.playerID, this.playerTitle);
      }

      public List getPlayersToCheckSharedWaypointsFrom(LOTRFellowship fs) {
         return null;
      }
   }

   public static class RemoveAdmin implements FellowshipUpdateType {
      private final UUID adminID;

      public RemoveAdmin(UUID admin) {
         this.adminID = admin;
      }

      public IMessage createUpdatePacket(LOTRPlayerData pd, LOTRFellowship fs) {
         return new LOTRPacketFellowshipPartialUpdate.RemoveAdmin(fs, this.adminID, fs.isAdmin(pd.getPlayerUUID()));
      }

      public List getPlayersToCheckSharedWaypointsFrom(LOTRFellowship fs) {
         return null;
      }
   }

   public static class SetAdmin implements FellowshipUpdateType {
      private final UUID adminID;

      public SetAdmin(UUID admin) {
         this.adminID = admin;
      }

      public IMessage createUpdatePacket(LOTRPlayerData pd, LOTRFellowship fs) {
         return new LOTRPacketFellowshipPartialUpdate.SetAdmin(fs, this.adminID, fs.isAdmin(pd.getPlayerUUID()));
      }

      public List getPlayersToCheckSharedWaypointsFrom(LOTRFellowship fs) {
         return null;
      }
   }

   public static class RemoveMember implements FellowshipUpdateType {
      private final UUID memberID;

      public RemoveMember(UUID member) {
         this.memberID = member;
      }

      public IMessage createUpdatePacket(LOTRPlayerData pd, LOTRFellowship fs) {
         return new LOTRPacketFellowshipPartialUpdate.RemoveMember(fs, this.memberID);
      }

      public List getPlayersToCheckSharedWaypointsFrom(LOTRFellowship fs) {
         return ImmutableList.of(this.memberID);
      }
   }

   public static class AddMember implements FellowshipUpdateType {
      private final UUID memberID;

      public AddMember(UUID member) {
         this.memberID = member;
      }

      public IMessage createUpdatePacket(LOTRPlayerData pd, LOTRFellowship fs) {
         return new LOTRPacketFellowshipPartialUpdate.AddMember(fs, this.memberID);
      }

      public List getPlayersToCheckSharedWaypointsFrom(LOTRFellowship fs) {
         return ImmutableList.of(this.memberID);
      }
   }

   public static class SetOwner implements FellowshipUpdateType {
      private final UUID ownerID;

      public SetOwner(UUID owner) {
         this.ownerID = owner;
      }

      public IMessage createUpdatePacket(LOTRPlayerData pd, LOTRFellowship fs) {
         return new LOTRPacketFellowshipPartialUpdate.SetOwner(fs, this.ownerID, fs.isOwner(pd.getPlayerUUID()));
      }

      public List getPlayersToCheckSharedWaypointsFrom(LOTRFellowship fs) {
         return ImmutableList.of(this.ownerID);
      }
   }

   public static class Full implements FellowshipUpdateType {
      public IMessage createUpdatePacket(LOTRPlayerData pd, LOTRFellowship fs) {
         return new LOTRPacketFellowship(pd, fs, false);
      }

      public List getPlayersToCheckSharedWaypointsFrom(LOTRFellowship fs) {
         return fs.getAllPlayerUUIDs();
      }
   }
}
