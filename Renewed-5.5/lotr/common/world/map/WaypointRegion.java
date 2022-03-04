package lotr.common.world.map;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;
import lotr.common.LOTRLog;
import lotr.common.data.DataUtil;
import lotr.common.init.LOTRBiomes;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

public class WaypointRegion {
   private final ResourceLocation resourceName;
   private final int assignedId;
   private final List biomeNames;

   private WaypointRegion(ResourceLocation resourceName, int assignedId, List biomeNames) {
      this.resourceName = resourceName;
      this.assignedId = assignedId;
      this.biomeNames = biomeNames;
   }

   public static WaypointRegion readCombined(MapSettings map, ResourceLocation resourceName, List jsonVersions, int assignedId) {
      List biomeNames = new ArrayList();
      Iterator var5 = jsonVersions.iterator();

      while(var5.hasNext()) {
         JsonObject json = (JsonObject)var5.next();
         boolean replacePrevious = json.get("replace").getAsBoolean();
         if (replacePrevious) {
            biomeNames.clear();
         }

         JsonArray biomeArray = json.get("biomes").getAsJsonArray();
         Iterator var9 = biomeArray.iterator();

         while(var9.hasNext()) {
            JsonElement elem = (JsonElement)var9.next();
            ResourceLocation biomeName = new ResourceLocation(elem.getAsString());
            biomeNames.add(biomeName);
         }
      }

      if (biomeNames.isEmpty()) {
         LOTRLog.warn("Waypoint region %s does not declare any biomes - this will make its waypoints impossible to unlock ingame!", resourceName);
      }

      return new WaypointRegion(resourceName, assignedId, biomeNames);
   }

   public static WaypointRegion read(PacketBuffer buf) {
      ResourceLocation resourceName = buf.func_192575_l();
      int assignedID = buf.func_150792_a();
      Supplier var10001 = ArrayList::new;
      buf.getClass();
      List biomeNames = (List)DataUtil.readNewCollectionFromBuffer(buf, var10001, buf::func_192575_l);
      return new WaypointRegion(resourceName, assignedID, biomeNames);
   }

   public void write(PacketBuffer buf) {
      buf.func_192572_a(this.resourceName);
      buf.func_150787_b(this.assignedId);
      DataUtil.writeCollectionToBuffer(buf, this.biomeNames, buf::func_192572_a);
   }

   public ResourceLocation getName() {
      return this.resourceName;
   }

   public int getAssignedId() {
      return this.assignedId;
   }

   public List getBiomeNames() {
      return this.biomeNames;
   }

   protected void postLoadValidateBiomes(World world) {
      this.biomeNames.forEach((biomeName) -> {
         Biome foundBiome = LOTRBiomes.getBiomeByRegistryName(biomeName, world);
         if (foundBiome == null) {
            LOTRLog.warn("WaypointRegion %s specifies a biome '%s' which does not exist in the biome registry!", this.resourceName, biomeName);
         }

      });
   }
}
