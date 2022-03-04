package lotr.common.data;

import java.util.BitSet;
import java.util.stream.Stream;
import lotr.common.config.ClientsideCurrentServerConfigSettings;
import lotr.common.config.LOTRConfig;
import lotr.common.util.LOTRUtil;
import lotr.common.world.map.MapExploration;
import lotr.common.world.map.MapSettings;
import lotr.common.world.map.MapSettingsManager;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.profiler.IProfiler;
import net.minecraft.world.server.ServerWorld;

public class FogDataModule extends PlayerDataModule {
   private final MapExploration mapExploration = new MapExploration();
   private static final int EXPLORATION_UPDATE_INTERVAL = LOTRUtil.secondsToTicks(5);

   protected FogDataModule(LOTRPlayerData pd) {
      super(pd);
   }

   public void save(CompoundNBT playerNBT) {
      playerNBT.func_218657_a("MapExploration", this.mapExploration.save(new CompoundNBT()));
   }

   public void load(CompoundNBT playerNBT) {
      this.mapExploration.load(playerNBT.func_74775_l("MapExploration"), this.getPlayerUUID());
   }

   protected void sendLoginData(PacketBuffer buf) {
      this.mapExploration.write(buf);
   }

   protected void receiveLoginData(PacketBuffer buf) {
      this.mapExploration.read(buf);
   }

   public static boolean isFogOfWarEnabledClientside() {
      int forcedFromServer = ClientsideCurrentServerConfigSettings.INSTANCE.forceFogOfWar;
      if (forcedFromServer == 1) {
         return true;
      } else {
         return forcedFromServer == 2 ? false : (Boolean)LOTRConfig.CLIENT.fogOfWar.get();
      }
   }

   public boolean isFogged(int mapX, int mapZ) {
      if (!isFogOfWarEnabledClientside()) {
         return false;
      } else {
         return !this.mapExploration.isExplored(mapX, mapZ);
      }
   }

   protected void onUpdate(ServerPlayerEntity player, ServerWorld world, int tick) {
      MapSettings currentMap = MapSettingsManager.serverInstance().getCurrentLoadedMap();
      if (currentMap != null) {
         if (this.mapExploration.initialiseIfEmptyOrChanged(player, currentMap)) {
            this.markDirty();
         }

         if (tick % EXPLORATION_UPDATE_INTERVAL == 0 && this.mapExploration.onUpdate(player, currentMap)) {
            this.markDirty();
         }
      }

   }

   public void receiveFullGridFromServer(PacketBuffer buf) {
      this.mapExploration.read(buf);
   }

   public void receiveSingleTileUpdateFromServer(int mapX, int mapZ, BitSet tileBits) {
      this.mapExploration.receiveSingleTileUpdateFromServer(mapX, mapZ, tileBits);
   }

   public Stream streamTilesForRendering(double mapXMin, double mapXMax, double mapZMin, double mapZMax, IProfiler profiler) {
      return this.mapExploration.streamTilesForRendering(mapXMin, mapXMax, mapZMin, mapZMax, profiler);
   }
}
