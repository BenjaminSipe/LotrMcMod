package lotr.common.fac;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import java.util.List;
import java.util.Set;
import lotr.common.world.map.MapSettings;
import net.minecraft.util.ResourceLocation;

public class TechnicalFactions {
   public static int registerTechnicalFactions(FactionSettings facSettings, MapSettings mapSettings, List factions, int nextFactionId) {
      factions.add(createTechnicalFaction(facSettings, mapSettings, FactionPointers.UNALIGNED, nextFactionId++));
      factions.add(createTechnicalFaction(facSettings, mapSettings, FactionPointers.HOSTILE, nextFactionId++));
      return nextFactionId;
   }

   private static Faction createTechnicalFaction(FactionSettings facSettings, MapSettings mapSettings, FactionPointer facPointer, int id) {
      ResourceLocation res = facPointer.getName();
      String name = String.format("faction.%s.%s", res.func_110624_b(), res.func_110623_a());
      boolean translateName = false;
      String subtitle = "";
      boolean translateSubtitle = false;
      FactionRegion region = null;
      int ordering = 0;
      int color = 0;
      MapSquare mapSquare = null;
      boolean isPlayableAlignmentFaction = false;
      Set types = ImmutableSet.of();
      boolean civilianKills = false;
      Faction faction = new Faction(facSettings, res, id, name, translateName, subtitle, translateSubtitle, (FactionRegion)region, ordering, color, (MapSquare)mapSquare, isPlayableAlignmentFaction, types, civilianKills);
      faction.setAreasOfInfluence(AreasOfInfluence.makeEmptyAreas(mapSettings, faction));
      faction.setSpeechbankHomeBiomes(ImmutableList.of());
      return faction;
   }
}
