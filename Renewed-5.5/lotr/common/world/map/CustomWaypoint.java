package lotr.common.world.map;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import javax.annotation.Nullable;
import lotr.common.LOTRLog;
import lotr.common.data.DataUtil;
import lotr.common.data.LOTRLevelData;
import lotr.common.data.LOTRPlayerData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class CustomWaypoint extends AbstractCustomWaypoint {
   private boolean isPublic;
   private List adoptedPlayers;
   private int adoptedCount;

   private CustomWaypoint(MapSettings map, UUID createdPlayer, int id, String name, String lore, BlockPos worldPos) {
      super(map, createdPlayer, id, name, lore, worldPos);
      this.adoptedPlayers = new ArrayList();
   }

   public CustomWaypoint(MapSettings map, UUID createdPlayer, int id, String name, String lore, BlockPos worldPos, boolean isPublic) {
      this(map, createdPlayer, id, name, lore, worldPos);
      this.isPublic = isPublic;
   }

   @Nullable
   public ITextComponent getDisplayOwnership() {
      return null;
   }

   public boolean isPublic() {
      return this.isPublic;
   }

   public void update(World world, String newName, String newLore, boolean newIsPublic) {
      this.setName(newName);
      this.setLore(newLore);
      if (!this.isPublic) {
         this.isPublic = newIsPublic;
      }

      Iterator var5 = this.adoptedPlayers.iterator();

      while(var5.hasNext()) {
         UUID adoptedPlayer = (UUID)var5.next();
         LOTRLevelData.sidedInstance((IWorldReader)world).getData(world, adoptedPlayer).getFastTravelData().updateAdoptedCustomWaypointFromOriginal(this);
      }

   }

   public void onAdoptedBy(UUID playerUUID, World world) {
      if (!this.adoptedPlayers.contains(playerUUID)) {
         this.adoptedPlayers.add(playerUUID);
         this.updateInPlayerDataAfterAdoption(world);
      }

   }

   public void onForsakenBy(UUID playerUUID, World world) {
      if (this.adoptedPlayers.contains(playerUUID)) {
         this.adoptedPlayers.remove(playerUUID);
         this.updateInPlayerDataAfterAdoption(world);
      }

   }

   private void updateInPlayerDataAfterAdoption(World world) {
      LOTRLevelData.sidedInstance((IWorldReader)world).getData(world, this.getCreatedPlayer()).getFastTravelData().updateCustomWaypointAdoptedCount(this, this.adoptedPlayers.size());
   }

   public int getAdoptedCountForDisplay() {
      return this.adoptedCount;
   }

   public void receiveAdoptedCountFromServer(int numAdopted) {
      this.adoptedCount = numAdopted;
   }

   public WaypointNetworkType getNetworkType() {
      return WaypointNetworkType.CUSTOM;
   }

   public static void writeIdentification(PacketBuffer buf, CustomWaypoint wp) {
      buf.func_150787_b(wp.getCustomId());
   }

   public static CustomWaypoint readFromIdentification(PacketBuffer buf, LOTRPlayerData pd) {
      int wpId = buf.func_150792_a();
      CustomWaypoint wp = pd.getFastTravelData().getCustomWaypointById(wpId);
      if (wp == null) {
         LOTRLog.warn("Received nonexistent custom waypoint ID %d from %s", wpId, pd.getLogicalSide());
      }

      return wp;
   }

   public static CustomWaypoint load(MapSettings map, CompoundNBT nbt) {
      CustomWaypoint waypoint = (CustomWaypoint)baseLoad(map, nbt, CustomWaypoint::new);
      waypoint.isPublic = nbt.func_74767_n("Public");
      DataUtil.loadCollectionFromPrimitiveListNBT(waypoint.adoptedPlayers, nbt.func_150295_c("AdoptedPlayers", 8), ListNBT::func_150307_f, UUID::fromString);
      return waypoint;
   }

   public void save(CompoundNBT nbt) {
      super.save(nbt);
      nbt.func_74757_a("Public", this.isPublic);
      if (!this.adoptedPlayers.isEmpty()) {
         nbt.func_218657_a("AdoptedPlayers", DataUtil.saveCollectionAsPrimitiveListNBT(this.adoptedPlayers, (playerUuid) -> {
            return StringNBT.func_229705_a_(playerUuid.toString());
         }));
      }

   }

   public static CustomWaypoint read(MapSettings map, PacketBuffer buf) {
      CustomWaypoint waypoint = (CustomWaypoint)baseRead(map, buf, CustomWaypoint::new);
      waypoint.isPublic = buf.readBoolean();
      waypoint.adoptedCount = buf.func_150792_a();
      return waypoint;
   }

   public void write(PacketBuffer buf) {
      super.write(buf);
      buf.writeBoolean(this.isPublic);
      buf.func_150787_b(this.adoptedCount);
   }

   protected void removeFromPlayerData(PlayerEntity player) {
      LOTRLevelData.sidedInstance((IWorldReader)player.field_70170_p).getData(player).getFastTravelData().removeCustomWaypoint(player.field_70170_p, this);
   }

   public void removeFromAllAdoptedPlayersWhenDestroyed(World world) {
      List copyOfAdoptedPlayers = new ArrayList(this.adoptedPlayers);
      Iterator var3 = copyOfAdoptedPlayers.iterator();

      while(var3.hasNext()) {
         UUID adoptedPlayer = (UUID)var3.next();
         LOTRLevelData.sidedInstance((IWorldReader)world).getData(world, adoptedPlayer).getFastTravelData().removeAdoptedCustomWaypointWhenOriginalDestroyed(world, this);
      }

   }
}
