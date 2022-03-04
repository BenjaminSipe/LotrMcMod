package lotr.common.data;

import java.util.UUID;
import java.util.function.Consumer;
import lotr.common.LOTRLog;
import lotr.common.fac.Faction;
import lotr.common.fac.FactionSettings;
import lotr.common.fac.FactionSettingsManager;
import lotr.common.world.map.MapSettings;
import lotr.common.world.map.MapSettingsManager;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

public abstract class PlayerDataModule {
   protected final LOTRPlayerData playerData;

   protected PlayerDataModule(LOTRPlayerData pd) {
      this.playerData = pd;
   }

   public abstract void save(CompoundNBT var1);

   public abstract void load(CompoundNBT var1);

   protected final void markDirty() {
      this.playerData.markDirty();
   }

   protected void sendLoginData(PacketBuffer buf) {
   }

   protected void receiveLoginData(PacketBuffer buf) {
   }

   protected void handleLogin(ServerPlayerEntity player) {
   }

   protected void onUpdate(ServerPlayerEntity player, ServerWorld world, int tick) {
   }

   protected static boolean isTimerAutosaveTick() {
      MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
      return server != null && server.func_71259_af() % 200 == 0;
   }

   protected final void sendPacketToClient(Object message) {
      this.playerData.sendPacketToClient(message);
   }

   protected final void executeIfPlayerExistsServerside(Consumer action) {
      this.playerData.executeIfPlayerExistsServerside(action);
   }

   protected final LOTRLevelData getLevelData() {
      return this.playerData.getParentLevelData();
   }

   protected final UUID getPlayerUUID() {
      return this.playerData.getPlayerUUID();
   }

   protected final MapSettings currentMapSettings() {
      return MapSettingsManager.sidedInstance(this.getLevelData().getLogicalSide()).getCurrentLoadedMap();
   }

   protected final FactionSettings currentFactionSettings() {
      return FactionSettingsManager.sidedInstance(this.getLevelData().getLogicalSide()).getCurrentLoadedFactions();
   }

   protected final void writeFactionToNBT(CompoundNBT nbt, String key, Faction faction) {
      if (faction != null) {
         nbt.func_74778_a(key, faction.getName().toString());
      }

   }

   protected final Faction loadFactionFromNBT(CompoundNBT nbt, String key, String messageIfNotExists) {
      if (nbt.func_74764_b(key)) {
         String facName = nbt.func_74779_i(key);
         Faction faction = this.currentFactionSettings().getFactionByName(new ResourceLocation(facName));
         if (faction == null) {
            this.playerData.logPlayerError(messageIfNotExists, facName);
         }

         return faction;
      } else {
         return null;
      }
   }

   protected final void writeFactionToBuffer(PacketBuffer buf, Faction faction) {
      buf.func_150787_b(faction != null ? faction.getAssignedId() : -1);
   }

   protected final Faction readFactionFromBuffer(PacketBuffer buf, String messageIfNotExists) {
      int facId = buf.func_150792_a();
      if (facId >= 0) {
         Faction faction = this.currentFactionSettings().getFactionByID(facId);
         if (faction == null) {
            LOTRLog.warn(messageIfNotExists, facId);
         }

         return faction;
      } else {
         return null;
      }
   }
}
