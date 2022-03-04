package lotr.common.world.map;

import java.util.Objects;
import java.util.UUID;
import lotr.common.data.DataUtil;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;

public class AdoptedCustomWaypointKey {
   private final UUID createdPlayer;
   private final int waypointId;

   private AdoptedCustomWaypointKey(UUID createdPlayer, int waypointId) {
      this.createdPlayer = createdPlayer;
      this.waypointId = waypointId;
   }

   public static AdoptedCustomWaypointKey of(UUID createdPlayer, int waypointId) {
      return new AdoptedCustomWaypointKey(createdPlayer, waypointId);
   }

   public static AdoptedCustomWaypointKey keyFor(AbstractCustomWaypoint waypoint) {
      return new AdoptedCustomWaypointKey(waypoint.getCreatedPlayer(), waypoint.getCustomId());
   }

   public UUID getCreatedPlayer() {
      return this.createdPlayer;
   }

   public int getWaypointId() {
      return this.waypointId;
   }

   public static AdoptedCustomWaypointKey load(CompoundNBT nbt) {
      UUID createdPlayer = DataUtil.getUniqueIdBackCompat(nbt, "CreatedPlayer");
      int waypointId = nbt.func_74762_e("WaypointID");
      return of(createdPlayer, waypointId);
   }

   public void save(CompoundNBT nbt) {
      nbt.func_186854_a("CreatedPlayer", this.createdPlayer);
      nbt.func_74768_a("WaypointID", this.waypointId);
   }

   public static AdoptedCustomWaypointKey read(PacketBuffer buf) {
      UUID createdPlayer = buf.func_179253_g();
      int waypointId = buf.func_150792_a();
      return of(createdPlayer, waypointId);
   }

   public void write(PacketBuffer buf) {
      buf.func_179252_a(this.createdPlayer);
      buf.func_150787_b(this.waypointId);
   }

   public boolean equals(Object obj) {
      if (obj == this) {
         return true;
      } else if (obj instanceof AdoptedCustomWaypointKey && obj.getClass() == this.getClass()) {
         AdoptedCustomWaypointKey otherKey = (AdoptedCustomWaypointKey)obj;
         return otherKey.createdPlayer.equals(this.createdPlayer) && otherKey.waypointId == this.waypointId;
      } else {
         return false;
      }
   }

   public int hashCode() {
      return Objects.hash(new Object[]{this.createdPlayer, this.waypointId});
   }
}
