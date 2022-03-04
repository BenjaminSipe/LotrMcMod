package lotr.common.network;

import java.util.function.Supplier;
import lotr.common.LOTRLog;
import lotr.common.LOTRMod;
import lotr.common.fac.AlignmentBonus;
import lotr.common.fac.AlignmentBonusMap;
import lotr.common.fac.Faction;
import lotr.common.fac.FactionSettings;
import lotr.common.fac.FactionSettingsManager;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class SPacketAlignmentBonus {
   public final int entityId;
   public final AlignmentBonus source;
   public final Faction mainFaction;
   public final float prevMainAlignment;
   public final AlignmentBonusMap factionBonusMap;
   public final float conquestBonus;
   public final Vector3d pos;

   public SPacketAlignmentBonus(int entityId, AlignmentBonus source, Faction mainFaction, float prevMainAlignment, AlignmentBonusMap factionBonusMap, float conquestBonus, Vector3d pos) {
      this.entityId = entityId;
      this.source = source;
      this.mainFaction = mainFaction;
      this.prevMainAlignment = prevMainAlignment;
      this.factionBonusMap = factionBonusMap;
      this.conquestBonus = conquestBonus;
      this.pos = pos;
   }

   public static void encode(SPacketAlignmentBonus packet, PacketBuffer buf) {
      buf.writeInt(packet.entityId);
      packet.source.write(buf);
      buf.func_150787_b(packet.mainFaction.getAssignedId());
      buf.writeFloat(packet.prevMainAlignment);
      packet.factionBonusMap.write(buf);
      buf.writeFloat(packet.conquestBonus);
      buf.writeDouble(packet.pos.field_72450_a);
      buf.writeDouble(packet.pos.field_72448_b);
      buf.writeDouble(packet.pos.field_72449_c);
   }

   public static SPacketAlignmentBonus decode(PacketBuffer buf) {
      FactionSettings facSettings = FactionSettingsManager.clientInstance().getCurrentLoadedFactions();
      int entityId = buf.readInt();
      AlignmentBonus bonus = AlignmentBonus.read(buf);
      int mainFactionId = buf.func_150792_a();
      Faction mainFaction = facSettings.getFactionByID(mainFactionId);
      if (mainFaction == null) {
         LOTRLog.warn("Received nonexistent faction ID %d in alignment bonus packet from server", mainFactionId);
      }

      float prevMainAlignment = buf.readFloat();
      AlignmentBonusMap factionBonusMap = AlignmentBonusMap.read(buf, facSettings);
      float conquestBonus = buf.readFloat();
      Vector3d pos = new Vector3d(buf.readDouble(), buf.readDouble(), buf.readDouble());
      return new SPacketAlignmentBonus(entityId, bonus, mainFaction, prevMainAlignment, factionBonusMap, conquestBonus, pos);
   }

   public static void handle(SPacketAlignmentBonus packet, Supplier context) {
      LOTRMod.PROXY.spawnAlignmentBonus(packet);
      ((Context)context.get()).setPacketHandled(true);
   }
}
