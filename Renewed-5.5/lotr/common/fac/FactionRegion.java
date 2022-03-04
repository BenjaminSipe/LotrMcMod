package lotr.common.fac;

import com.google.gson.JsonObject;
import lotr.common.LOTRLog;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class FactionRegion {
   private final ResourceLocation resourceName;
   private final int assignedId;
   private final String name;
   private final boolean translateName;
   private final RegistryKey dimension;
   private final int ordering;

   public FactionRegion(ResourceLocation res, int id, String name, boolean translateName, ResourceLocation dimName, int order) {
      this.resourceName = res;
      this.assignedId = id;
      this.name = name;
      this.translateName = translateName;
      this.dimension = RegistryKey.func_240903_a_(Registry.field_239699_ae_, dimName);
      this.ordering = order;
   }

   public static FactionRegion read(ResourceLocation resourceName, JsonObject json, int assignedId) {
      if (json.size() == 0) {
         LOTRLog.info("Faction region %s has an empty file - not loading it in this world", resourceName);
         return null;
      } else {
         JsonObject nameObj = json.get("name").getAsJsonObject();
         String name = nameObj.get("text").getAsString();
         boolean translateName = nameObj.get("translate").getAsBoolean();
         String dimensionName = json.get("dimension").getAsString();
         ResourceLocation dimensionNameRes = new ResourceLocation(dimensionName);
         int ordering = json.get("ordering").getAsInt();
         return new FactionRegion(resourceName, assignedId, name, translateName, dimensionNameRes, ordering);
      }
   }

   public static FactionRegion read(PacketBuffer buf) {
      ResourceLocation resourceName = buf.func_192575_l();
      int assignedId = buf.func_150792_a();
      String name = buf.func_218666_n();
      boolean translateName = buf.readBoolean();
      ResourceLocation dimensionName = buf.func_192575_l();
      int ordering = buf.readInt();
      return new FactionRegion(resourceName, assignedId, name, translateName, dimensionName, ordering);
   }

   public void write(PacketBuffer buf) {
      buf.func_192572_a(this.resourceName);
      buf.func_150787_b(this.assignedId);
      buf.func_180714_a(this.name);
      buf.writeBoolean(this.translateName);
      buf.func_192572_a(this.dimension.func_240901_a_());
      buf.writeInt(this.ordering);
   }

   public ResourceLocation getName() {
      return this.resourceName;
   }

   public int getAssignedId() {
      return this.assignedId;
   }

   public ITextComponent getDisplayName() {
      return (ITextComponent)(this.translateName ? new TranslationTextComponent(this.name) : new StringTextComponent(this.name));
   }

   public ResourceLocation getDimensionName() {
      return this.dimension.func_240901_a_();
   }

   public RegistryKey getDimension() {
      return this.dimension;
   }

   public int getOrdering() {
      return this.ordering;
   }
}
