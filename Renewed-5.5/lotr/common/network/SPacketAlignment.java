package lotr.common.network;

import com.google.common.collect.ImmutableMap;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import lotr.common.LOTRLog;
import lotr.common.LOTRMod;
import lotr.common.data.AlignmentDataModule;
import lotr.common.data.DataUtil;
import lotr.common.data.LOTRLevelData;
import lotr.common.data.LOTRPlayerData;
import lotr.common.fac.Faction;
import lotr.common.fac.FactionSettings;
import lotr.common.fac.FactionSettingsManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent.Context;
import org.apache.commons.lang3.tuple.Pair;

public class SPacketAlignment {
   private final Map alignmentMap;
   private final UUID otherPlayerId;

   public SPacketAlignment(Faction faction, float alignment) {
      this(ImmutableMap.of(faction, alignment), (UUID)((UUID)null));
   }

   public SPacketAlignment(Faction faction, float alignment, PlayerEntity otherPlayer) {
      this(ImmutableMap.of(faction, alignment), (PlayerEntity)otherPlayer);
   }

   public SPacketAlignment(Map alignmentMap, PlayerEntity otherPlayer) {
      this(alignmentMap, otherPlayer.func_110124_au());
   }

   private SPacketAlignment(Map alignmentMap, UUID otherPlayerId) {
      this.alignmentMap = alignmentMap;
      this.otherPlayerId = otherPlayerId;
   }

   public static void encode(SPacketAlignment packet, PacketBuffer buf) {
      DataUtil.writeMapToBuffer(buf, packet.alignmentMap, (faction, alignment) -> {
         buf.func_150787_b(faction.getAssignedId());
         buf.writeFloat(alignment);
      });
      DataUtil.writeNullableToBuffer(buf, packet.otherPlayerId, (BiFunction)(PacketBuffer::func_179252_a));
   }

   public static SPacketAlignment decode(PacketBuffer buf) {
      FactionSettings facSettings = FactionSettingsManager.clientInstance().getCurrentLoadedFactions();
      Map alignmentMap = DataUtil.readNewMapFromBuffer(buf, HashMap::new, () -> {
         int factionId = buf.func_150792_a();
         Faction faction = facSettings.getFactionByID(factionId);
         float alignment = buf.readFloat();
         if (faction == null) {
            LOTRLog.warn("Alignment update packet received nonexistent faction ID %d from server", factionId);
            return null;
         } else {
            return Pair.of(faction, alignment);
         }
      });
      UUID otherPlayerId = (UUID)DataUtil.readNullableFromBuffer(buf, PacketBuffer::func_179253_g);
      return new SPacketAlignment(alignmentMap, otherPlayerId);
   }

   public static void handle(SPacketAlignment packet, Supplier context) {
      LOTRPlayerData pd;
      if (packet.otherPlayerId != null) {
         pd = LOTRLevelData.clientInstance().getData(LOTRMod.PROXY.getClientWorld(), packet.otherPlayerId);
      } else {
         PlayerEntity player = LOTRMod.PROXY.getClientPlayer();
         pd = LOTRLevelData.clientInstance().getData(player);
      }

      AlignmentDataModule alignData = pd.getAlignmentData();
      packet.alignmentMap.forEach(alignData::setAlignment);
      ((Context)context.get()).setPacketHandled(true);
   }
}
