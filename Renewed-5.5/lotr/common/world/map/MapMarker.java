package lotr.common.world.map;

import lotr.common.network.CPacketUpdateMapMarker;
import lotr.common.network.LOTRPacketHandler;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class MapMarker implements SelectableMapObject {
   public static final int MAX_NAME_LENGTH = 32;
   public static final int ICON_SIZE = 10;
   private final MapSettings mapSettings;
   private final int id;
   private final int worldX;
   private final int worldZ;
   private final double mapX;
   private final double mapZ;
   private String name;
   private MapMarkerIcon icon;

   public MapMarker(MapSettings map, int id, int worldX, int worldZ, String name, MapMarkerIcon icon) {
      this.mapSettings = map;
      this.id = id;
      this.worldX = worldX;
      this.worldZ = worldZ;
      this.mapX = this.mapSettings.worldToMapX_frac((double)worldX);
      this.mapZ = this.mapSettings.worldToMapZ_frac((double)worldZ);
      this.name = name;
      this.icon = icon;
   }

   public int getId() {
      return this.id;
   }

   public int getWorldX() {
      return this.worldX;
   }

   public int getWorldZ() {
      return this.worldZ;
   }

   public double getMapX() {
      return this.mapX;
   }

   public double getMapZ() {
      return this.mapZ;
   }

   public String getName() {
      return this.name;
   }

   public MapMarkerIcon getIcon() {
      return this.icon;
   }

   public void update(String newName, MapMarkerIcon newIcon) {
      this.name = newName;
      this.icon = newIcon;
   }

   public void renameAndSendToServer(String newName) {
      this.name = newName;
      LOTRPacketHandler.sendToServer(new CPacketUpdateMapMarker(this));
   }

   public void changeIconAndSendToServer(MapMarkerIcon newIcon) {
      this.icon = newIcon;
      LOTRPacketHandler.sendToServer(new CPacketUpdateMapMarker(this));
   }

   public int getMapIconWidth() {
      return 10;
   }

   public static MapMarker load(MapSettings map, CompoundNBT nbt) {
      int id = nbt.func_74762_e("ID");
      int worldX = nbt.func_74762_e("X");
      int worldZ = nbt.func_74762_e("Z");
      String name = nbt.func_74779_i("Name");
      MapMarkerIcon icon = MapMarkerIcon.forNameOrDefault(nbt.func_74779_i("Icon"));
      return new MapMarker(map, id, worldX, worldZ, name, icon);
   }

   public void save(CompoundNBT nbt) {
      nbt.func_74768_a("ID", this.id);
      nbt.func_74768_a("X", this.worldX);
      nbt.func_74768_a("Z", this.worldZ);
      nbt.func_74778_a("Name", this.name);
      nbt.func_74778_a("Icon", this.icon.name);
   }

   public static MapMarker read(MapSettings map, PacketBuffer buf) {
      int id = buf.func_150792_a();
      int worldX = buf.readInt();
      int worldZ = buf.readInt();
      String name = buf.func_218666_n();
      MapMarkerIcon icon = MapMarkerIcon.forNetworkIdOrDefault(buf.func_150792_a());
      return new MapMarker(map, id, worldX, worldZ, name, icon);
   }

   public void write(PacketBuffer buf) {
      buf.func_150787_b(this.id);
      buf.writeInt(this.worldX);
      buf.writeInt(this.worldZ);
      buf.func_180714_a(this.name);
      buf.func_150787_b(this.icon.networkId);
   }

   public static boolean isValidMapMarkerPosition(MapSettings currentMap, int worldX, int worldZ) {
      double mapX = currentMap.worldToMapX_frac((double)worldX);
      double mapZ = currentMap.worldToMapZ_frac((double)worldZ);
      return mapX >= 0.0D && mapX < (double)currentMap.getWidth() && mapZ >= 0.0D && mapZ < (double)currentMap.getHeight();
   }

   public static boolean isValidMapMarkerPosition(World world, int worldX, int worldZ) {
      MapSettings currentMap = MapSettingsManager.sidedInstance((IWorldReader)world).getCurrentLoadedMap();
      return isValidMapMarkerPosition(currentMap, worldX, worldZ);
   }
}
