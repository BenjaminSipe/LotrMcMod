package lotr.common.world.map;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import lotr.common.LOTRLog;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class Road {
   private final MapSettings mapSettings;
   private final ResourceLocation resourceName;
   private final String name;
   private final boolean translateName;
   private final List controlPoints;
   private final List sections = new ArrayList();

   public Road(MapSettings map, ResourceLocation res, String name, boolean translateName, List controlPoints) {
      this.mapSettings = map;
      this.resourceName = res;
      this.name = name;
      this.translateName = translateName;
      this.controlPoints = controlPoints;
   }

   public Road generateCurves() {
      if (!this.sections.isEmpty()) {
         throw new IllegalStateException("Road " + this.resourceName + " curves were already generated");
      } else {
         this.sections.addAll(RoadCurveGenerator.generateSplines(this.mapSettings, this, this.controlPoints, this.mapSettings.getRoadPointCache()));
         return this;
      }
   }

   public static Road read(MapSettings map, ResourceLocation resourceName, JsonObject json) {
      if (json.size() == 0) {
         LOTRLog.info("Road %s has an empty file - not loading it in this world", resourceName);
         return null;
      } else {
         JsonObject nameObj = json.get("name").getAsJsonObject();
         String name = nameObj.get("text").getAsString();
         boolean translateName = nameObj.get("translate").getAsBoolean();
         List controlPoints = new ArrayList();
         JsonArray route = json.get("route").getAsJsonArray();
         Iterator var8 = route.iterator();

         while(var8.hasNext()) {
            JsonElement routeElement = (JsonElement)var8.next();
            JsonObject pointObj = routeElement.getAsJsonObject();
            RouteRoadPoint controlPoint = RouteRoadPoint.read(map, resourceName, pointObj);
            if (controlPoint != null) {
               controlPoints.add(controlPoint);
            }
         }

         return new Road(map, resourceName, name, translateName, controlPoints);
      }
   }

   public static Road read(MapSettings map, PacketBuffer buf) {
      ResourceLocation resourceName = buf.func_192575_l();
      String name = buf.func_218666_n();
      boolean translateName = buf.readBoolean();
      List controlPoints = new ArrayList();
      int routeSize = buf.func_150792_a();

      for(int i = 0; i < routeSize; ++i) {
         RouteRoadPoint controlPoint = RouteRoadPoint.read(map, resourceName, buf);
         if (controlPoint != null) {
            controlPoints.add(controlPoint);
         }
      }

      return new Road(map, resourceName, name, translateName, controlPoints);
   }

   public void write(PacketBuffer buf) {
      buf.func_192572_a(this.resourceName);
      buf.func_180714_a(this.name);
      buf.writeBoolean(this.translateName);
      buf.func_150787_b(this.controlPoints.size());
      Iterator var2 = this.controlPoints.iterator();

      while(var2.hasNext()) {
         RouteRoadPoint point = (RouteRoadPoint)var2.next();
         point.write(buf);
      }

   }

   public ResourceLocation getName() {
      return this.resourceName;
   }

   public ITextComponent getDisplayName() {
      return (ITextComponent)(this.translateName ? new TranslationTextComponent(this.name) : new StringTextComponent(this.name));
   }

   public boolean hasSameDisplayNameAs(Road other) {
      return this.getDisplayName().getString().equals(other.getDisplayName().getString());
   }

   public List getSections() {
      return this.sections;
   }
}
