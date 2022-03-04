package lotr.common.fac;

import com.google.gson.JsonObject;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;

public class FactionRank implements Comparable {
   private final Faction faction;
   private final String name;
   private final int assignedId;
   private final boolean isDummyRank;
   private final float alignment;
   private final boolean isPledgeRank;

   public FactionRank(Faction faction, String name, int assignedId, boolean isDummyRank, float alignment, boolean isPledgeRank) {
      this.faction = faction;
      this.name = name;
      this.assignedId = assignedId;
      this.isDummyRank = isDummyRank;
      this.alignment = alignment;
      this.isPledgeRank = isPledgeRank;
      if (!isDummyRank && alignment <= 0.0F) {
         throw new IllegalArgumentException(String.format("Faction rank %s.%s is invalid - alignment must be greater than 0 for a non-dummy rank", faction.getName(), name));
      }
   }

   public static FactionRank read(Faction faction, JsonObject json, int assignedId) {
      String name = json.get("name").getAsString();
      float alignment = json.get("alignment").getAsFloat();
      boolean isPledgeRank = json.has("is_pledge_rank") && json.get("is_pledge_rank").getAsBoolean();
      return new FactionRank(faction, name, assignedId, false, alignment, isPledgeRank);
   }

   public static FactionRank read(Faction faction, PacketBuffer buf) {
      String name = buf.func_218666_n();
      int assignedId = buf.func_150792_a();
      boolean isDummyRank = buf.readBoolean();
      float alignment = buf.readFloat();
      boolean isPledgeRank = buf.readBoolean();
      return new FactionRank(faction, name, assignedId, isDummyRank, alignment, isPledgeRank);
   }

   public void write(PacketBuffer buf) {
      buf.func_180714_a(this.name);
      buf.func_150787_b(this.assignedId);
      buf.writeBoolean(this.isDummyRank);
      buf.writeFloat(this.alignment);
      buf.writeBoolean(this.isPledgeRank);
   }

   public Faction getFaction() {
      return this.faction;
   }

   public String getBaseName() {
      return this.name;
   }

   public String toString() {
      if (this.isDummyRank) {
         return String.format("%s.rank.%s", "lotr", this.getBaseName());
      } else {
         ResourceLocation facName = this.faction.getName();
         return String.format("%s.%s.rank.%s", facName.func_110624_b(), facName.func_110623_a(), this.getBaseName());
      }
   }

   public String getTranslationNameKey() {
      return String.format("faction.%s", this.toString());
   }

   private String getTranslatedName() {
      return (new TranslationTextComponent(this.getTranslationNameKey())).getString();
   }

   public String getDisplayShortName(RankGender gender) {
      return FactionRankNameDecomposer.actOn(this.getTranslatedName()).getShortName(gender);
   }

   public String getDisplayFullName(RankGender gender) {
      return FactionRankNameDecomposer.actOn(this.getTranslatedName()).getFullName(gender);
   }

   public boolean isNameEqual(String rankName) {
      return this.getBaseName().equals(rankName);
   }

   public int getAssignedId() {
      return this.assignedId;
   }

   public boolean isDummyRank() {
      return this.isDummyRank;
   }

   public float getAlignment() {
      return this.alignment;
   }

   public boolean isPledgeRank() {
      return this.isPledgeRank;
   }

   public boolean isAbovePledgeRank() {
      return this.alignment > this.faction.getPledgeAlignment();
   }

   public int compareTo(FactionRank other) {
      if (this.faction != other.faction) {
         throw new IllegalArgumentException(String.format("Cannot compare two ranks from different factions! %s, %s", this.getTranslationNameKey(), other.getTranslationNameKey()));
      } else {
         float align1 = this.alignment;
         float align2 = other.alignment;
         if (align1 == align2 && this != other) {
            throw new IllegalArgumentException(String.format("Two ranks cannot have the same alignment value! %s (= %f), %s (= %f)", this.getTranslationNameKey(), align1, other.getTranslationNameKey(), align2));
         } else {
            return Float.compare(align1, align2);
         }
      }
   }
}
