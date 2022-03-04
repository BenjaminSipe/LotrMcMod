package lotr.common.network;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import lotr.common.LOTRLog;
import lotr.common.data.AlignmentDataModule;
import lotr.common.data.LOTRLevelData;
import lotr.common.data.LOTRPlayerData;
import lotr.common.fac.Faction;
import lotr.common.fac.FactionRegion;
import lotr.common.fac.FactionSettings;
import lotr.common.fac.FactionSettingsManager;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class CPacketViewedFactions {
   private final Faction currentViewedFaction;
   private final Map regionLastViewedFactions;

   public CPacketViewedFactions(Faction currentViewed, Map regionLastViewed) {
      this.currentViewedFaction = currentViewed;
      this.regionLastViewedFactions = regionLastViewed;
   }

   public static void encode(CPacketViewedFactions packet, PacketBuffer buf) {
      Faction currentViewedFaction = packet.currentViewedFaction;
      if (currentViewedFaction != null) {
         buf.func_150787_b(currentViewedFaction.getAssignedId());
      } else {
         buf.func_150787_b(-1);
      }

      buf.func_150787_b(packet.regionLastViewedFactions.size());
      packet.regionLastViewedFactions.forEach((region, faction) -> {
         buf.func_150787_b(region.getAssignedId());
         buf.func_150787_b(faction.getAssignedId());
      });
   }

   public static CPacketViewedFactions decode(PacketBuffer buf) {
      FactionSettings facSettings = FactionSettingsManager.serverInstance().getCurrentLoadedFactions();
      int currentFacId = buf.func_150792_a();
      Faction currentFac = null;
      if (currentFacId >= 0) {
         currentFac = facSettings.getFactionByID(currentFacId);
         if (currentFac == null) {
            LOTRLog.warn("No faction for ID %d exists on the server!", currentFacId);
         }
      }

      Map regionMap = new HashMap();
      int regionMapSize = buf.func_150792_a();

      for(int i = 0; i < regionMapSize; ++i) {
         int regionId = buf.func_150792_a();
         int regionFacId = buf.func_150792_a();
         FactionRegion region = facSettings.getRegionByID(regionId);
         Faction regionFac = facSettings.getFactionByID(regionFacId);
         if (region == null) {
            LOTRLog.warn("No faction region for ID %d exists on the server!", regionId);
         } else if (regionFac == null) {
            LOTRLog.warn("No faction for ID %d exists on the server!", regionFacId);
         } else {
            regionMap.put(region, regionFac);
         }
      }

      return new CPacketViewedFactions(currentFac, regionMap);
   }

   public static void handle(CPacketViewedFactions packet, Supplier context) {
      ServerPlayerEntity player = ((Context)context.get()).getSender();
      LOTRPlayerData playerData = LOTRLevelData.serverInstance().getData(player);
      AlignmentDataModule alignData = playerData.getAlignmentData();
      if (packet.currentViewedFaction != null) {
         alignData.setCurrentViewedFaction(packet.currentViewedFaction);
      }

      packet.regionLastViewedFactions.forEach((region, faction) -> {
         alignData.setRegionLastViewedFaction(region, faction);
      });
      ((Context)context.get()).setPacketHandled(true);
   }
}
