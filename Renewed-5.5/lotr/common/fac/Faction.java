package lotr.common.fac;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lotr.common.LOTRLog;
import lotr.common.data.DataUtil;
import lotr.common.data.LOTRLevelData;
import lotr.common.data.LOTRPlayerData;
import lotr.common.init.LOTRBiomes;
import lotr.common.world.map.MapSettings;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

public class Faction {
   private final FactionSettings factionSettings;
   private final ResourceLocation resourceName;
   private final int assignedId;
   private final String name;
   private final boolean translateName;
   private final String subtitle;
   private final boolean translateSubtitle;
   private final FactionRegion region;
   private final int ordering;
   private final int color;
   private final float[] colorComponents;
   private final MapSquare mapSquare;
   private final boolean isPlayableAlignmentFaction;
   private final Set types;
   private final boolean approvesCivilianKills;
   private List ranks = new ArrayList();
   private Map ranksById = new HashMap();
   private Map ranksByName = new HashMap();
   private List trueRanksSortedAscending = new ArrayList();
   private List trueRanksSortedDescending = new ArrayList();
   private Optional pledgeRank;
   private AreasOfInfluence areasOfInfluence;
   private List speechbankHomeBiomes;

   public Faction(FactionSettings facSettings, ResourceLocation res, int id, String name, boolean translateName, String subtitle, boolean translateSubtitle, FactionRegion region, int ordering, int color, MapSquare mapSquare, boolean isPlayableAlignmentFaction, Set types, boolean civilianKills) {
      this.factionSettings = facSettings;
      this.resourceName = res;
      this.assignedId = id;
      this.name = name;
      this.translateName = translateName;
      this.subtitle = subtitle;
      this.translateSubtitle = translateSubtitle;
      this.region = region;
      this.ordering = ordering;
      this.color = color;
      this.colorComponents = (new Color(color)).getColorComponents((float[])null);
      this.mapSquare = mapSquare;
      this.isPlayableAlignmentFaction = isPlayableAlignmentFaction;
      this.types = types;
      this.approvesCivilianKills = civilianKills;
   }

   public static Faction read(FactionSettings factionSettings, ResourceLocation resourceName, JsonObject json, int assignedId, MapSettings mapSettings) {
      if (json.size() == 0) {
         LOTRLog.info("Faction %s has an empty file - not loading it in this world", resourceName);
         return null;
      } else {
         JsonObject nameObj = json.get("name").getAsJsonObject();
         String name = nameObj.get("text").getAsString();
         boolean translateName = nameObj.get("translate").getAsBoolean();
         JsonObject subtitleObj = json.get("subtitle").getAsJsonObject();
         String subtitle = subtitleObj.get("text").getAsString();
         boolean translateSubtitle = subtitleObj.get("translate").getAsBoolean();
         String regionName = json.get("region").getAsString();
         FactionRegion region = factionSettings.getRegionByName(new ResourceLocation(regionName));
         if (region == null) {
            LOTRLog.warn("Faction %s has invalid region name %s - no such region exists", resourceName, regionName);
            return null;
         } else {
            int ordering = json.get("ordering").getAsInt();
            String hexColor = json.get("color").getAsString();
            int color = 0;

            try {
               color = Integer.parseInt(hexColor, 16);
            } catch (NumberFormatException var33) {
               LOTRLog.warn("Faction %s has invalid color code %s - must be in hex color format (e.g. FFAA33)", resourceName, hexColor);
            }

            JsonObject mapSquareObj = json.get("map_square").getAsJsonObject();
            MapSquare mapSquare = MapSquare.read(mapSquareObj);
            boolean isPlayableAlignmentFaction = true;
            JsonArray typesArray = json.get("types").getAsJsonArray();
            Set types = new HashSet();
            Iterator var21 = typesArray.iterator();

            while(var21.hasNext()) {
               JsonElement typeElement = (JsonElement)var21.next();
               String typeName = typeElement.getAsString();
               FactionType type = FactionType.forName(new ResourceLocation(typeName));
               if (type != null) {
                  types.add(type);
               } else {
                  LOTRLog.warn("Faction %s includes invalid faction type name %s - no such type exists", resourceName, typeName);
               }
            }

            boolean approvesCivilianKills = json.get("approves_civilian_kills").getAsBoolean();
            Faction faction = new Faction(factionSettings, resourceName, assignedId, name, translateName, subtitle, translateSubtitle, region, ordering, color, mapSquare, isPlayableAlignmentFaction, types, approvesCivilianKills);
            List ranks = new ArrayList();
            int nextRankId = 0;
            int nextRankId = DummyFactionRanks.registerCommonRanks(faction, ranks, nextRankId);
            if (json.has("ranks")) {
               JsonArray ranksArray = json.get("ranks").getAsJsonArray();
               Iterator var26 = ranksArray.iterator();

               while(var26.hasNext()) {
                  JsonElement rankElement = (JsonElement)var26.next();

                  try {
                     JsonObject rankObj = rankElement.getAsJsonObject();
                     FactionRank rank = FactionRank.read(faction, rankObj, nextRankId);
                     if (rank != null) {
                        ranks.add(rank);
                     }

                     ++nextRankId;
                  } catch (Exception var32) {
                     LOTRLog.warn("Failed to load a rank in faction %s from file", faction.getName());
                     var32.printStackTrace();
                  }
               }

               if (ranks.stream().filter(FactionRank::isPledgeRank).count() > 1L) {
                  LOTRLog.warn("Faction %s declares more than one pledge rank (%s) - only one is allowed. Ranks will not be loaded until this is fixed", faction.getName(), String.join(",", (Iterable)ranks.stream().filter(FactionRank::isPledgeRank).map(FactionRank::getBaseName).collect(Collectors.toList())));
               } else {
                  faction.setRanks(ranks);
               }
            }

            JsonObject aoiObj = json.get("areas_of_influence").getAsJsonObject();
            AreasOfInfluence areasOfInfluence = AreasOfInfluence.read(faction, aoiObj, mapSettings);
            faction.setAreasOfInfluence(areasOfInfluence);
            List speechbankHomeBiomes = new ArrayList();
            if (json.has("speechbank_home_biomes")) {
               JsonArray biomeArray = json.get("speechbank_home_biomes").getAsJsonArray();
               Iterator var43 = biomeArray.iterator();

               while(var43.hasNext()) {
                  JsonElement elem = (JsonElement)var43.next();
                  ResourceLocation biomeName = new ResourceLocation(elem.getAsString());
                  speechbankHomeBiomes.add(biomeName);
               }
            }

            faction.setSpeechbankHomeBiomes(speechbankHomeBiomes);
            return faction;
         }
      }
   }

   public static Faction read(FactionSettings factionSettings, MapSettings mapSettings, PacketBuffer buf) {
      ResourceLocation resourceName = buf.func_192575_l();
      int assignedId = buf.func_150792_a();
      String name = buf.func_218666_n();
      boolean translateName = buf.readBoolean();
      String subtitle = buf.func_218666_n();
      boolean translateSubtitle = buf.readBoolean();
      FactionRegion region = (FactionRegion)DataUtil.readNullableFromBuffer(buf, () -> {
         int regionId = buf.func_150792_a();
         FactionRegion readRegion = factionSettings.getRegionByID(regionId);
         if (readRegion == null) {
            LOTRLog.warn("Received faction %s from server with a nonexistent region ID (%d) - faction will not be loaded correctly clientside", resourceName, regionId);
         }

         return readRegion;
      });
      int ordering = buf.func_150792_a();
      int color = buf.readInt();
      MapSquare mapSquare = (MapSquare)DataUtil.readNullableFromBuffer(buf, () -> {
         return MapSquare.read(buf);
      });
      boolean isPlayableAlignmentFaction = buf.readBoolean();
      Set types = (Set)DataUtil.readNewCollectionFromBuffer(buf, HashSet::new, () -> {
         int typeId = buf.func_150792_a();
         FactionType type = FactionType.forNetworkID(typeId);
         if (type == null) {
            LOTRLog.warn("Received faction %s from server with a nonexistent faction type ID (%d)", resourceName, typeId);
         }

         return type;
      });
      boolean approvesCivilianKills = buf.readBoolean();
      Faction faction = new Faction(factionSettings, resourceName, assignedId, name, translateName, subtitle, translateSubtitle, region, ordering, color, mapSquare, isPlayableAlignmentFaction, types, approvesCivilianKills);
      List ranks = (List)DataUtil.readNewCollectionFromBuffer(buf, ArrayList::new, () -> {
         try {
            return FactionRank.read(faction, buf);
         } catch (Exception var4) {
            LOTRLog.warn("Error loading a rank in faction %s from server", resourceName);
            var4.printStackTrace();
            return null;
         }
      });
      faction.setRanks(ranks);
      AreasOfInfluence areasOfInfluence = AreasOfInfluence.read(faction, buf, mapSettings);
      faction.setAreasOfInfluence(areasOfInfluence);
      Supplier var10002 = ArrayList::new;
      buf.getClass();
      faction.setSpeechbankHomeBiomes((List)DataUtil.readNewCollectionFromBuffer(buf, var10002, buf::func_192575_l));
      return faction;
   }

   public void write(PacketBuffer buf) {
      buf.func_192572_a(this.resourceName);
      buf.func_150787_b(this.assignedId);
      buf.func_180714_a(this.name);
      buf.writeBoolean(this.translateName);
      buf.func_180714_a(this.subtitle);
      buf.writeBoolean(this.translateSubtitle);
      DataUtil.writeNullableToBuffer(buf, this.region, (Runnable)(() -> {
         buf.func_150787_b(this.region.getAssignedId());
      }));
      buf.func_150787_b(this.ordering);
      buf.writeInt(this.color);
      DataUtil.writeNullableToBuffer(buf, this.mapSquare, (BiConsumer)(MapSquare::write));
      buf.writeBoolean(this.isPlayableAlignmentFaction);
      DataUtil.writeCollectionToBuffer(buf, this.types, (type) -> {
         buf.func_150787_b(type.networkID);
      });
      buf.writeBoolean(this.approvesCivilianKills);
      DataUtil.writeCollectionToBuffer(buf, this.ranks, (rank) -> {
         rank.write(buf);
      });
      this.areasOfInfluence.write(buf);
      DataUtil.writeCollectionToBuffer(buf, this.speechbankHomeBiomes, buf::func_192572_a);
   }

   public ResourceLocation getName() {
      return this.resourceName;
   }

   public int getAssignedId() {
      return this.assignedId;
   }

   public IFormattableTextComponent getDisplayName() {
      return (IFormattableTextComponent)(this.translateName ? new TranslationTextComponent(this.name) : new StringTextComponent(this.name));
   }

   public IFormattableTextComponent getColoredDisplayName() {
      IFormattableTextComponent text = this.getDisplayName();
      text.func_240703_c_(Style.field_240709_b_.func_240718_a_(net.minecraft.util.text.Color.func_240743_a_(this.getColor())));
      return text;
   }

   public ITextComponent getDisplaySubtitle() {
      return (ITextComponent)(this.translateSubtitle ? new TranslationTextComponent(this.subtitle) : new StringTextComponent(this.subtitle));
   }

   public static ITextComponent getFactionOrUnknownDisplayName(Faction faction) {
      return (ITextComponent)(faction != null ? faction.getDisplayName() : new TranslationTextComponent("faction.lotr.unknown"));
   }

   public FactionRegion getRegion() {
      return this.region;
   }

   public int getNullableRegionOrdering() {
      return (Integer)Optional.ofNullable(this.region).map(FactionRegion::getOrdering).orElse(-1);
   }

   public RegistryKey getDimension() {
      return this.region != null ? this.region.getDimension() : null;
   }

   public boolean isSameDimension(Faction other) {
      RegistryKey dim = this.getDimension();
      if (dim != null) {
         return dim.equals(other.getDimension());
      } else {
         return other.getRegion() == null;
      }
   }

   public int getOrdering() {
      return this.ordering;
   }

   public int getColor() {
      return this.color;
   }

   public float[] getColorComponents() {
      return this.colorComponents;
   }

   public MapSquare getMapSquare() {
      return this.mapSquare;
   }

   public Set getTypes() {
      return this.types;
   }

   public boolean isOfAnyType(FactionType... checkTypes) {
      Stream var10000 = Stream.of(checkTypes);
      Set var10001 = this.types;
      var10001.getClass();
      return var10000.anyMatch(var10001::contains);
   }

   public boolean isPlayableAlignmentFaction() {
      return this.isPlayableAlignmentFaction;
   }

   public List getRanks() {
      return this.ranks;
   }

   public void setRanks(List ranksToSet) {
      if (this.ranks != null && !this.ranks.isEmpty()) {
         throw new IllegalArgumentException("Cannot set " + this.name + " ranks - already set!");
      } else {
         this.ranks = ranksToSet;
         this.ranksById = (Map)this.ranks.stream().collect(Collectors.toMap(FactionRank::getAssignedId, UnaryOperator.identity()));
         this.ranksByName = (Map)this.ranks.stream().collect(Collectors.toMap(FactionRank::getBaseName, UnaryOperator.identity()));
         List trueRanks = (List)this.ranks.stream().filter((rank) -> {
            return !rank.isDummyRank();
         }).collect(Collectors.toList());
         this.trueRanksSortedAscending = new ArrayList(trueRanks);
         this.trueRanksSortedDescending = new ArrayList(trueRanks);
         Collections.sort(this.trueRanksSortedAscending);
         Collections.sort(this.trueRanksSortedDescending, Comparator.reverseOrder());
         this.pledgeRank = trueRanks.stream().filter(FactionRank::isPledgeRank).findFirst();
      }
   }

   public FactionRank getRankByID(int id) {
      return (FactionRank)this.ranksById.get(id);
   }

   public FactionRank getRankByName(String name) {
      return (FactionRank)this.ranksByName.get(name);
   }

   public FactionRank getNeutralRank() {
      return this.getRankByName("neutral");
   }

   public FactionRank getEnemyRank() {
      return this.getRankByName("enemy");
   }

   public FactionRank getRankFor(PlayerEntity player) {
      return this.getRankFor(LOTRLevelData.sidedInstance((IWorldReader)player.field_70170_p).getData(player));
   }

   public FactionRank getRankFor(LOTRPlayerData playerData) {
      return this.getRankFor(playerData.getAlignmentData().getAlignment(this));
   }

   public FactionRank getRankFor(float alignment) {
      return (FactionRank)this.trueRanksSortedDescending.stream().filter((rank) -> {
         return !rank.isDummyRank() && alignment >= rank.getAlignment();
      }).findFirst().orElse(alignment >= 0.0F ? this.getNeutralRank() : this.getEnemyRank());
   }

   public FactionRank getRankAbove(FactionRank curRank) {
      return this.getRankNAbove(curRank, 1);
   }

   public FactionRank getRankBelow(FactionRank curRank) {
      return this.getRankNBelow(curRank, 1);
   }

   public FactionRank getRankNAbove(FactionRank curRank, int n) {
      if (!this.trueRanksSortedDescending.isEmpty() && curRank != null) {
         int index = -1;
         if (curRank.isDummyRank()) {
            index = this.trueRanksSortedDescending.size();
         } else if (this.trueRanksSortedDescending.contains(curRank)) {
            index = this.trueRanksSortedDescending.indexOf(curRank);
         }

         if (index >= 0) {
            index -= n;
            if (index < 0) {
               return (FactionRank)this.trueRanksSortedDescending.get(0);
            } else {
               return index > this.trueRanksSortedDescending.size() - 1 ? this.getNeutralRank() : (FactionRank)this.trueRanksSortedDescending.get(index);
            }
         } else {
            return this.getNeutralRank();
         }
      } else {
         return this.getNeutralRank();
      }
   }

   public FactionRank getRankNBelow(FactionRank curRank, int n) {
      return this.getRankNAbove(curRank, -n);
   }

   public FactionRank getFirstRank() {
      return !this.trueRanksSortedAscending.isEmpty() ? (FactionRank)this.trueRanksSortedAscending.get(0) : this.getNeutralRank();
   }

   public Optional getPledgeRank() {
      return this.pledgeRank;
   }

   public float getPledgeAlignment() {
      return (Float)this.pledgeRank.map(FactionRank::getAlignment).orElse(0.0F);
   }

   public boolean approvesCivilianKills() {
      return this.approvesCivilianKills;
   }

   public FactionRelation getRelation(Faction other) {
      return this.factionSettings.getRelations().getRelation(this, other);
   }

   public boolean isGoodRelation(Faction other) {
      FactionRelation relation = this.getRelation(other);
      return relation == FactionRelation.ALLY || relation == FactionRelation.FRIEND;
   }

   public boolean isAlly(Faction other) {
      return this.getRelation(other) == FactionRelation.ALLY;
   }

   public boolean isNeutral(Faction other) {
      return this.getRelation(other) == FactionRelation.NEUTRAL;
   }

   public boolean isBadRelation(Faction other) {
      FactionRelation relation = this.getRelation(other);
      return relation == FactionRelation.ENEMY || relation == FactionRelation.MORTAL_ENEMY;
   }

   public boolean isMortalEnemy(Faction other) {
      return this.getRelation(other) == FactionRelation.MORTAL_ENEMY;
   }

   public List getOthersOfRelation(FactionRelation relation) {
      return (List)this.factionSettings.streamFactionsExcept(this).filter(Faction::isPlayableAlignmentFaction).filter((faction) -> {
         return this.getRelation(faction) == relation;
      }).collect(Collectors.toList());
   }

   public List getBonusesForKilling() {
      return (List)this.factionSettings.streamFactionsExcept(this).filter(this::isBadRelation).collect(Collectors.toList());
   }

   public List getPenaltiesForKilling() {
      return (List)this.factionSettings.streamFactions().filter((f) -> {
         return f == this || this.isGoodRelation(f);
      }).collect(Collectors.toList());
   }

   public List getConquestBoostRelations() {
      return (List)this.factionSettings.streamFactionsExcept(this).filter(Faction::isPlayableAlignmentFaction).filter(this::isAlly).collect(Collectors.toList());
   }

   public AreasOfInfluence getAreasOfInfluence() {
      return this.areasOfInfluence;
   }

   public void setAreasOfInfluence(AreasOfInfluence aoi) {
      if (this.areasOfInfluence != null) {
         throw new IllegalArgumentException("Cannot set " + this.name + " areas of influence - already set!");
      } else {
         this.areasOfInfluence = aoi;
      }
   }

   public List getSpeechbankHomeBiomes() {
      return this.speechbankHomeBiomes;
   }

   public void setSpeechbankHomeBiomes(List biomes) {
      if (this.speechbankHomeBiomes != null) {
         throw new IllegalArgumentException("Cannot set " + this.name + " speechbank home biomes - already set!");
      } else {
         this.speechbankHomeBiomes = biomes;
      }
   }

   public boolean isSpeechbankHomeBiome(ResourceLocation biomeName) {
      return this.speechbankHomeBiomes.contains(biomeName);
   }

   protected void postLoadValidateBiomes(World world) {
      this.speechbankHomeBiomes.forEach((biomeName) -> {
         Biome foundBiome = LOTRBiomes.getBiomeByRegistryName(biomeName, world);
         if (foundBiome == null) {
            LOTRLog.warn("Faction %s specifies a biome '%s' in speechbank_home_biomes which does not exist in the biome registry!", this.resourceName, biomeName);
         }

      });
   }
}
