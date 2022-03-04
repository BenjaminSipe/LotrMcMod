package lotr.common;

import java.io.File;
import java.util.List;
import java.util.Optional;
import lotr.common.data.PlayerMessageType;
import lotr.common.entity.item.RingPortalEntity;
import lotr.common.network.SPacketAlignmentBonus;
import lotr.common.network.SPacketNotifyAlignRequirement;
import lotr.common.network.SPacketOpenScreen;
import lotr.common.network.SPacketSetAttackTarget;
import lotr.common.network.SPacketSpeechbank;
import lotr.common.world.map.AdoptedCustomWaypoint;
import lotr.common.world.map.CustomWaypoint;
import lotr.common.world.map.Waypoint;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public interface LOTRProxy {
   boolean isClient();

   boolean isSingleplayer();

   File getGameRootDirectory();

   World getClientWorld();

   PlayerEntity getClientPlayer();

   Optional getSidedAttackTarget(MobEntity var1);

   void receiveClientAttackTarget(SPacketSetAttackTarget var1);

   default float getCurrentSandstormFogStrength() {
      return 0.0F;
   }

   void setInRingPortal(Entity var1, RingPortalEntity var2);

   default void mapHandleIsOp(boolean isOp) {
   }

   default void mapHandlePlayerLocations(List playerLocations) {
   }

   default void displayNewDate() {
   }

   default void spawnAlignmentBonus(SPacketAlignmentBonus packet) {
   }

   default void displayAlignmentDrain(int numFactions) {
   }

   default void displayMessageType(PlayerMessageType messageType, boolean isCommandSent, String customText) {
   }

   default void displayPacketOpenScreen(SPacketOpenScreen.Type type) {
   }

   default void displayFastTravelScreen(Waypoint waypoint, int startX, int startZ) {
   }

   default void displayUpdateCustomWaypointScreen(CustomWaypoint waypoint) {
   }

   default void displayAdoptCustomWaypointScreen(CustomWaypoint waypoint, String createdPlayerName) {
   }

   default void displayViewAdoptedCustomWaypointScreen(AdoptedCustomWaypoint waypoint, String createdPlayerName) {
   }

   default void receiveSpeechbankPacket(SPacketSpeechbank packet) {
   }

   default void receiveNotifyAlignRequirementPacket(SPacketNotifyAlignRequirement packet) {
   }
}
