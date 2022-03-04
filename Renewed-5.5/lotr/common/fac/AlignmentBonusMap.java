package lotr.common.fac;

import java.util.HashMap;
import java.util.Set;
import java.util.stream.Collectors;
import lotr.common.LOTRLog;
import lotr.common.data.DataUtil;
import net.minecraft.network.PacketBuffer;
import org.apache.commons.lang3.tuple.Pair;

public class AlignmentBonusMap extends HashMap {
   public Set getChangedFactions() {
      return (Set)this.keySet().stream().filter((f) -> {
         return (Float)this.get(f) != 0.0F;
      }).collect(Collectors.toSet());
   }

   public void write(PacketBuffer buf) {
      DataUtil.writeMapToBuffer(buf, this, (fac, align) -> {
         buf.func_150787_b(fac.getAssignedId());
         buf.writeFloat(align);
      });
   }

   public static AlignmentBonusMap read(PacketBuffer buf, FactionSettings currentLoadedFactions) {
      return (AlignmentBonusMap)DataUtil.readNewMapFromBuffer(buf, AlignmentBonusMap::new, () -> {
         int facId = buf.func_150792_a();
         float align = buf.readFloat();
         Faction fac = currentLoadedFactions.getFactionByID(facId);
         if (fac != null) {
            return Pair.of(fac, align);
         } else {
            LOTRLog.warn("Received nonexistent faction ID %d in alignment bonus packet", facId);
            return null;
         }
      });
   }
}
