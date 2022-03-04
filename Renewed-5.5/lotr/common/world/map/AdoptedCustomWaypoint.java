package lotr.common.world.map;

import java.util.UUID;
import lotr.common.LOTRLog;
import lotr.common.data.LOTRLevelData;
import lotr.common.data.LOTRPlayerData;
import lotr.common.util.UsernameHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IWorldReader;

public class AdoptedCustomWaypoint extends AbstractCustomWaypoint {
   private String ownerUsername;

   public AdoptedCustomWaypoint(MapSettings map, UUID createdPlayer, int id, String name, String lore, BlockPos worldPos) {
      super(map, createdPlayer, id, name, lore, worldPos);
   }

   public static AdoptedCustomWaypoint adopt(MapSettings map, CustomWaypoint waypoint) {
      return new AdoptedCustomWaypoint(map, waypoint.getCreatedPlayer(), waypoint.getCustomId(), waypoint.getRawName(), waypoint.getRawLore(), waypoint.getPosition());
   }

   public AdoptedCustomWaypointKey getAdoptedKey() {
      return AdoptedCustomWaypointKey.keyFor(this);
   }

   public ITextComponent getDisplayOwnership() {
      String ownerNameOrId = this.ownerUsername != null ? this.ownerUsername : this.getCreatedPlayer().toString();
      return new TranslationTextComponent("gui.lotr.map.waypoint.adopted.owner", new Object[]{ownerNameOrId});
   }

   public WaypointNetworkType getNetworkType() {
      return WaypointNetworkType.ADOPTED_CUSTOM;
   }

   public static void writeIdentification(PacketBuffer buf, AdoptedCustomWaypoint wp) {
      wp.getAdoptedKey().write(buf);
   }

   public static AdoptedCustomWaypoint readFromIdentification(PacketBuffer buf, LOTRPlayerData pd) {
      AdoptedCustomWaypointKey key = AdoptedCustomWaypointKey.read(buf);
      AdoptedCustomWaypoint wp = pd.getFastTravelData().getAdoptedCustomWaypointByKey(key);
      if (wp == null) {
         LOTRLog.warn("Received nonexistent adopted custom waypoint (creator %s, ID %d) from %s", key.getCreatedPlayer(), key.getWaypointId(), pd.getLogicalSide());
      }

      return wp;
   }

   public void updateFromOriginal(CustomWaypoint originalWaypoint) {
      this.updateFromOriginal(originalWaypoint.getRawName(), originalWaypoint.getRawLore());
   }

   public void updateFromOriginal(String newName, String newLore) {
      this.setName(newName);
      this.setLore(newLore);
   }

   public static AdoptedCustomWaypoint load(MapSettings map, CompoundNBT nbt) {
      AdoptedCustomWaypoint waypoint = (AdoptedCustomWaypoint)baseLoad(map, nbt, AdoptedCustomWaypoint::new);
      return waypoint;
   }

   public void save(CompoundNBT nbt) {
      super.save(nbt);
   }

   public static AdoptedCustomWaypoint read(MapSettings map, PacketBuffer buf) {
      AdoptedCustomWaypoint waypoint = (AdoptedCustomWaypoint)baseRead(map, buf, AdoptedCustomWaypoint::new);
      waypoint.ownerUsername = buf.func_218666_n();
      return waypoint;
   }

   public void write(PacketBuffer buf) {
      super.write(buf);
      String username = UsernameHelper.getLastKnownUsernameOrFallback(this.getCreatedPlayer());
      buf.func_180714_a(username);
   }

   protected void removeFromPlayerData(PlayerEntity player) {
      LOTRLevelData.sidedInstance((IWorldReader)player.field_70170_p).getData(player).getFastTravelData().removeAdoptedCustomWaypoint(player.field_70170_p, this);
   }
}
