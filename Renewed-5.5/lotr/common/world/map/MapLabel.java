package lotr.common.world.map;

import com.google.gson.JsonObject;
import lotr.common.LOTRLog;
import lotr.common.init.LOTRBiomes;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;

public class MapLabel {
   private final MapSettings mapSettings;
   private final ResourceLocation resourceName;
   private final RegistryKey biomeName;
   private final String textName;
   private final boolean translateName;
   private final int mapX;
   private final int mapZ;
   private final float scale;
   private final float angle;
   private final float minZoom;
   private final float maxZoom;

   private MapLabel(MapSettings map, ResourceLocation res, RegistryKey biome, String text, boolean translate, int x, int z, float scale, float angle, float minZoom, float maxZoom) {
      this.mapSettings = map;
      this.resourceName = res;
      this.biomeName = biome;
      this.textName = text;
      this.translateName = translate;
      this.mapX = x;
      this.mapZ = z;
      this.scale = scale;
      this.angle = angle;
      this.minZoom = minZoom;
      this.maxZoom = maxZoom;
   }

   public static MapLabel makeBiomeLabel(MapSettings map, ResourceLocation res, RegistryKey biome, int x, int z, float scale, float angle, float minZoom, float maxZoom) {
      return new MapLabel(map, res, biome, (String)null, false, x, z, scale, angle, minZoom, maxZoom);
   }

   public static MapLabel makeTextLabel(MapSettings map, ResourceLocation res, String text, boolean translate, int x, int z, float scale, float angle, float minZoom, float maxZoom) {
      return new MapLabel(map, res, (RegistryKey)null, text, translate, x, z, scale, angle, minZoom, maxZoom);
   }

   public static MapLabel read(MapSettings map, ResourceLocation resourceName, JsonObject json) {
      if (json.size() == 0) {
         LOTRLog.info("Map label %s has an empty file - not loading it in this world", resourceName);
         return null;
      } else {
         int mapX = json.get("x").getAsInt();
         int mapZ = json.get("z").getAsInt();
         float scale = json.get("scale").getAsFloat();
         float angle = json.get("angle").getAsFloat();
         float minZoom = json.get("min_zoom").getAsFloat();
         float maxZoom = json.get("max_zoom").getAsFloat();
         JsonObject nameObj = json.get("name").getAsJsonObject();
         String nameType = nameObj.get("type").getAsString();
         if (nameType.equals("biome")) {
            ResourceLocation biomeName = new ResourceLocation(nameObj.get("biome").getAsString());
            RegistryKey biomeKey = RegistryKey.func_240903_a_(Registry.field_239720_u_, biomeName);
            return makeBiomeLabel(map, resourceName, biomeKey, mapX, mapZ, scale, angle, minZoom, maxZoom);
         } else if (nameType.equals("text")) {
            String text = nameObj.get("text").getAsString();
            boolean translateText = nameObj.get("translate").getAsBoolean();
            return makeTextLabel(map, resourceName, text, translateText, mapX, mapZ, scale, angle, minZoom, maxZoom);
         } else {
            LOTRLog.error("Error loading map label %s - name type %s is not recognised", resourceName, nameType);
            return null;
         }
      }
   }

   public static MapLabel read(MapSettings map, PacketBuffer buf) {
      ResourceLocation resourceName = buf.func_192575_l();
      int mapX = buf.readInt();
      int mapZ = buf.readInt();
      float scale = buf.readFloat();
      float angle = buf.readFloat();
      float minZoom = buf.readFloat();
      float maxZoom = buf.readFloat();
      boolean isBiomeName = buf.readBoolean();
      if (isBiomeName) {
         ResourceLocation biomeName = buf.func_192575_l();
         RegistryKey biomeKey = RegistryKey.func_240903_a_(Registry.field_239720_u_, biomeName);
         return makeBiomeLabel(map, resourceName, biomeKey, mapX, mapZ, scale, angle, minZoom, maxZoom);
      } else {
         String textName = buf.func_218666_n();
         boolean translateName = buf.readBoolean();
         return makeTextLabel(map, resourceName, textName, translateName, mapX, mapZ, scale, angle, minZoom, maxZoom);
      }
   }

   public void write(PacketBuffer buf) {
      buf.func_192572_a(this.resourceName);
      buf.writeInt(this.mapX);
      buf.writeInt(this.mapZ);
      buf.writeFloat(this.scale);
      buf.writeFloat(this.angle);
      buf.writeFloat(this.minZoom);
      buf.writeFloat(this.maxZoom);
      boolean isBiomeName = this.biomeName != null;
      buf.writeBoolean(isBiomeName);
      if (isBiomeName) {
         buf.func_192572_a(this.biomeName.func_240901_a_());
      } else {
         buf.func_180714_a(this.textName);
         buf.writeBoolean(this.translateName);
      }

   }

   public ResourceLocation getName() {
      return this.resourceName;
   }

   public ITextComponent getDisplayName(IWorld world) {
      if (this.biomeName != null) {
         Biome biome = (Biome)world.func_241828_r().func_243612_b(Registry.field_239720_u_).func_230516_a_(this.biomeName);
         return (ITextComponent)(biome != null ? LOTRBiomes.getBiomeDisplayName(biome, world) : new StringTextComponent(String.format("Unknown biome '%s'", this.biomeName.func_240901_a_())));
      } else {
         return (ITextComponent)(this.translateName ? new TranslationTextComponent(this.textName) : new StringTextComponent(this.textName));
      }
   }

   public int getMapX() {
      return this.mapX;
   }

   public int getMapZ() {
      return this.mapZ;
   }

   public float getScale() {
      return this.scale;
   }

   public float getAngle() {
      return this.angle;
   }

   public float getMinZoom() {
      return this.minZoom;
   }

   public float getMaxZoom() {
      return this.maxZoom;
   }
}
