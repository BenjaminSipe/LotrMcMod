package lotr.common.data;

import java.util.HashMap;
import java.util.Map;
import lotr.common.LOTRLog;
import lotr.common.fac.Faction;
import lotr.common.network.SPacketFactionStats;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.lang3.tuple.Pair;

public class FactionStatsDataModule extends PlayerDataModule {
   private final Map factionStatsMap = new HashMap();

   protected FactionStatsDataModule(LOTRPlayerData pd) {
      super(pd);
   }

   public void save(CompoundNBT playerNBT) {
      playerNBT.func_218657_a("FactionStats", DataUtil.saveMapAsListNBT(this.factionStatsMap, (nbt, fac, stats) -> {
         nbt.func_74778_a("Faction", fac.getName().toString());
         stats.save(nbt);
      }));
   }

   public void load(CompoundNBT playerNBT) {
      DataUtil.loadMapFromListNBT(this.factionStatsMap, playerNBT.func_150295_c("FactionStats", 10), (nbt) -> {
         ResourceLocation facName = new ResourceLocation(nbt.func_74779_i("Faction"));
         Faction fac = this.currentFactionSettings().getFactionByName(facName);
         if (fac != null) {
            FactionStats stats = new FactionStats(this, fac);
            stats.load(nbt);
            return Pair.of(fac, stats);
         } else {
            this.playerData.logPlayerError("Loaded faction stats for nonexistent faction %s", facName);
            return null;
         }
      });
   }

   protected void sendLoginData(PacketBuffer buf) {
      DataUtil.writeMapToBuffer(buf, this.factionStatsMap, (faction, stats) -> {
         buf.func_150787_b(faction.getAssignedId());
         stats.write(buf);
      });
   }

   protected void receiveLoginData(PacketBuffer buf) {
      DataUtil.fillMapFromBuffer(buf, this.factionStatsMap, () -> {
         int factionId = buf.func_150792_a();
         Faction faction = this.currentFactionSettings().getFactionByID(factionId);
         if (faction == null) {
            LOTRLog.warn("Received faction stats for nonexistent faction ID %d from server", factionId);
            return null;
         } else {
            FactionStats stats = new FactionStats(this, faction);
            stats.read(buf);
            return Pair.of(faction, stats);
         }
      });
   }

   public FactionStats getFactionStats(Faction faction) {
      return (FactionStats)this.factionStatsMap.computeIfAbsent(faction, (f) -> {
         return new FactionStats(this, f);
      });
   }

   protected void updateFactionData(Faction faction) {
      this.markDirty();
      this.sendPacketToClient(new SPacketFactionStats(faction, this.getFactionStats(faction)));
   }
}
