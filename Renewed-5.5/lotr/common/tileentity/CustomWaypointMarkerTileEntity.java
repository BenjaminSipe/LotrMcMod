package lotr.common.tileentity;

import java.util.UUID;
import lotr.common.block.CustomWaypointMarkerBlock;
import lotr.common.data.DataUtil;
import lotr.common.init.LOTRTileEntities;
import lotr.common.world.map.AbstractCustomWaypoint;
import lotr.common.world.map.CustomWaypoint;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemFrameEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;

public class CustomWaypointMarkerTileEntity extends TileEntity {
   private UUID playerUuid;
   private int waypointId;
   private String savedWaypointName;
   private boolean savedWaypointPublic;
   private CompoundNBT itemFrameNBT;

   public CustomWaypointMarkerTileEntity() {
      super((TileEntityType)LOTRTileEntities.CUSTOM_WAYPOINT_MARKER.get());
   }

   public void setWaypointReference(CustomWaypoint waypoint) {
      this.playerUuid = waypoint.getCreatedPlayer();
      this.waypointId = waypoint.getCustomId();
      this.savedWaypointName = waypoint.getRawName();
      this.savedWaypointPublic = waypoint.isPublic();
   }

   public void updateWaypointReference(CustomWaypoint waypoint) {
      this.savedWaypointName = waypoint.getRawName();
      this.savedWaypointPublic = waypoint.isPublic();
      this.func_145831_w().func_184138_a(this.func_174877_v(), this.func_195044_w(), this.func_195044_w(), 3);
   }

   public boolean matchesWaypointReference(AbstractCustomWaypoint waypoint) {
      return waypoint.getCreatedPlayer().equals(this.playerUuid) && waypoint.getCustomId() == this.waypointId;
   }

   public UUID getWaypointPlayer() {
      return this.playerUuid;
   }

   public int getWaypointId() {
      return this.waypointId;
   }

   public String getWaypointName() {
      return this.savedWaypointName;
   }

   public boolean isWaypointPublic() {
      return this.savedWaypointPublic;
   }

   public Direction getFacingDirection() {
      return (Direction)this.func_195044_w().func_177229_b(CustomWaypointMarkerBlock.FACING);
   }

   public void absorbItemFrame(ItemFrameEntity itemFrame) {
      this.itemFrameNBT = itemFrame.func_189511_e(new CompoundNBT());
      itemFrame.func_70106_y();
   }

   public void recreateAndDropItemFrame(BlockState oldState) {
      Direction facing = (Direction)oldState.func_177229_b(CustomWaypointMarkerBlock.FACING);
      ItemFrameEntity itemFrame = new ItemFrameEntity(this.field_145850_b, this.field_174879_c, facing);
      itemFrame.func_70020_e(this.itemFrameNBT);
      itemFrame.func_110128_b((Entity)null);
      this.itemFrameNBT = null;
   }

   public CompoundNBT func_189515_b(CompoundNBT nbt) {
      super.func_189515_b(nbt);
      if (this.playerUuid != null) {
         nbt.func_186854_a("WaypointCreator", this.playerUuid);
         nbt.func_74768_a("WaypointID", this.waypointId);
      }

      if (this.savedWaypointName != null) {
         nbt.func_74778_a("WaypointNameSaved", this.savedWaypointName);
      }

      nbt.func_74757_a("WaypointPublicSaved", this.savedWaypointPublic);
      if (this.itemFrameNBT != null) {
         nbt.func_218657_a("ItemFrameData", this.itemFrameNBT.func_74737_b());
      }

      return nbt;
   }

   public void func_230337_a_(BlockState state, CompoundNBT nbt) {
      super.func_230337_a_(state, nbt);
      if (DataUtil.hasUniqueIdBackCompat(nbt, "WaypointCreator")) {
         this.playerUuid = DataUtil.getUniqueIdBackCompat(nbt, "WaypointCreator");
         this.waypointId = nbt.func_74762_e("WaypointID");
      }

      if (nbt.func_74764_b("WaypointNameSaved")) {
         this.savedWaypointName = nbt.func_74779_i("WaypointNameSaved");
      }

      this.savedWaypointPublic = nbt.func_74767_n("WaypointPublicSaved");
      this.itemFrameNBT = nbt.func_74775_l("ItemFrameData");
   }

   public CompoundNBT func_189517_E_() {
      CompoundNBT nbt = super.func_189517_E_();
      this.writeWaypointNameForClient(nbt);
      return nbt;
   }

   public SUpdateTileEntityPacket func_189518_D_() {
      CompoundNBT nbt = new CompoundNBT();
      this.writeWaypointNameForClient(nbt);
      return new SUpdateTileEntityPacket(this.field_174879_c, 0, nbt);
   }

   private void writeWaypointNameForClient(CompoundNBT nbt) {
      if (this.savedWaypointName != null) {
         nbt.func_74778_a("WaypointNameSaved", this.savedWaypointName);
      }

   }

   public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
      CompoundNBT nbt = pkt.func_148857_g();
      if (nbt.func_74764_b("WaypointNameSaved")) {
         this.savedWaypointName = nbt.func_74779_i("WaypointNameSaved");
      } else {
         this.savedWaypointName = null;
      }

   }
}
