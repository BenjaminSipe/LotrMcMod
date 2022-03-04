package lotr.common.data;

import java.util.List;
import lotr.common.network.CPacketToggle;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.SPacketCreateMapMarker;
import lotr.common.network.SPacketDeleteMapMarker;
import lotr.common.network.SPacketToggle;
import lotr.common.network.SPacketUpdateMapMarker;
import lotr.common.network.SidedTogglePacket;
import lotr.common.util.LookupList;
import lotr.common.world.map.MapMarker;
import lotr.common.world.map.MapMarkerIcon;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;

public class MapMarkerDataModule extends PlayerDataModule {
   private LookupList markers = new LookupList(MapMarker::getId);
   private int nextMarkerId = 0;
   private boolean showMarkers = true;
   public static final int MAX_MARKERS = 64;

   protected MapMarkerDataModule(LOTRPlayerData pd) {
      super(pd);
   }

   public void save(CompoundNBT playerNBT) {
      if (!this.markers.isEmpty()) {
         playerNBT.func_218657_a("MapMarkers", DataUtil.saveCollectionAsCompoundListNBT(this.markers, (nbt, marker) -> {
            marker.save(nbt);
         }));
      }

      playerNBT.func_74768_a("NextMapMarkerId", this.nextMarkerId);
      playerNBT.func_74757_a("ShowMapMarkers", this.showMarkers);
   }

   public void load(CompoundNBT playerNBT) {
      DataUtil.loadCollectionFromCompoundListNBT(this.markers, playerNBT.func_150295_c("MapMarkers", 10), (nbt) -> {
         return MapMarker.load(this.currentMapSettings(), nbt);
      });
      this.nextMarkerId = (Integer)DataUtil.getIfNBTContains(this.nextMarkerId, playerNBT, "NextMapMarkerId", CompoundNBT::func_74762_e);
      this.showMarkers = (Boolean)DataUtil.getIfNBTContains(this.showMarkers, playerNBT, "ShowMapMarkers", CompoundNBT::func_74767_n);
   }

   protected void sendLoginData(PacketBuffer buf) {
      DataUtil.writeCollectionToBuffer(buf, this.markers, (marker) -> {
         marker.write(buf);
      });
      buf.func_150787_b(this.nextMarkerId);
      buf.writeBoolean(this.showMarkers);
   }

   protected void receiveLoginData(PacketBuffer buf) {
      DataUtil.fillCollectionFromBuffer(buf, this.markers, () -> {
         return MapMarker.read(this.currentMapSettings(), buf);
      });
      this.nextMarkerId = buf.func_150792_a();
      this.showMarkers = buf.readBoolean();
   }

   public List getMarkers() {
      return this.markers;
   }

   public MapMarker getMarkerById(int id) {
      return (MapMarker)this.markers.lookup(id);
   }

   public boolean canCreateNewMarker() {
      return this.markers.size() < 64;
   }

   public void createNewMarker(int worldX, int worldZ, String name) {
      MapMarker marker = new MapMarker(this.currentMapSettings(), this.nextMarkerId, worldX, worldZ, name, MapMarkerIcon.CROSS);
      ++this.nextMarkerId;
      this.markers.add(marker);
      this.markDirty();
      this.sendPacketToClient(new SPacketCreateMapMarker(marker));
   }

   public void addCreatedMarkerFromServer(MapMarker marker) {
      this.markers.add(marker);
   }

   public void updateMarker(MapMarker marker, String name, MapMarkerIcon icon) {
      marker.update(name, icon);
      this.markDirty();
      this.sendPacketToClient(new SPacketUpdateMapMarker(marker));
   }

   public void removeMarker(MapMarker marker) {
      this.markers.remove(marker);
      this.markDirty();
      this.sendPacketToClient(new SPacketDeleteMapMarker(marker));
   }

   public boolean getShowMarkers() {
      return this.showMarkers;
   }

   public void setShowMarkers(boolean flag) {
      if (this.showMarkers != flag) {
         this.showMarkers = flag;
         this.markDirty();
         this.sendShowMarkersToClient();
      }

   }

   private void sendShowMarkersToClient() {
      this.sendPacketToClient(new SPacketToggle(SidedTogglePacket.ToggleType.SHOW_MAP_MARKERS, this.showMarkers));
   }

   public void toggleShowMarkersAndSendToServer() {
      this.showMarkers = !this.showMarkers;
      this.sendShowMarkersToServer();
   }

   private void sendShowMarkersToServer() {
      LOTRPacketHandler.sendToServer(new CPacketToggle(SidedTogglePacket.ToggleType.SHOW_MAP_MARKERS, this.showMarkers));
   }
}
