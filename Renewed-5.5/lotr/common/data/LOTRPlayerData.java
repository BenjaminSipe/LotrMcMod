package lotr.common.data;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;
import lotr.common.LOTRLog;
import lotr.common.LOTRMod;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.SPacketLoginPlayerDataModule;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

public class LOTRPlayerData {
   private final LOTRLevelData levelData;
   private final UUID playerUUID;
   private final List modules = new ArrayList();
   private final BiMap modulesByName = HashBiMap.create();
   private final FastTravelDataModule fastTravelData;
   private final MapMarkerDataModule mapMarkerData;
   private final AlignmentDataModule alignmentData;
   private final FactionStatsDataModule factionStatsData;
   private final MessageDataModule messageData;
   private final FogDataModule fogData;
   private final MiscDataModule miscData;
   private boolean needsSave = false;
   private int updateTick;

   public LOTRPlayerData(LOTRLevelData level, UUID player) {
      this.levelData = level;
      this.playerUUID = player;
      this.fastTravelData = (FastTravelDataModule)this.addModule("FastTravel", FastTravelDataModule::new);
      this.mapMarkerData = (MapMarkerDataModule)this.addModule("MapMarkers", MapMarkerDataModule::new);
      this.alignmentData = (AlignmentDataModule)this.addModule("Alignment", AlignmentDataModule::new);
      this.factionStatsData = (FactionStatsDataModule)this.addModule("FactionStats", FactionStatsDataModule::new);
      this.messageData = (MessageDataModule)this.addModule("Messages", MessageDataModule::new);
      this.fogData = (FogDataModule)this.addModule("Fog", FogDataModule::new);
      this.miscData = (MiscDataModule)this.addModule("Misc", MiscDataModule::new);
   }

   private PlayerDataModule addModule(String code, Function moduleConstructor) {
      PlayerDataModule module = (PlayerDataModule)moduleConstructor.apply(this);
      this.modules.add(module);
      this.modulesByName.put(code, module);
      return module;
   }

   private String getModuleCode(PlayerDataModule module) {
      return (String)this.modulesByName.inverse().get(module);
   }

   public LOTRLevelData getParentLevelData() {
      return this.levelData;
   }

   public LogicalSide getLogicalSide() {
      return this.levelData.getLogicalSide();
   }

   public UUID getPlayerUUID() {
      return this.playerUUID;
   }

   protected void markDirty() {
      this.needsSave = true;
   }

   public boolean needsSave() {
      return this.needsSave;
   }

   public void save(CompoundNBT playerNBT) {
      Iterator var2 = this.modules.iterator();

      while(var2.hasNext()) {
         PlayerDataModule module = (PlayerDataModule)var2.next();

         try {
            module.save(playerNBT);
         } catch (Exception var5) {
            LOTRLog.error("Error saving player data module %s for player %s", this.getModuleCode(module), this.playerUUID.toString());
            var5.printStackTrace();
         }
      }

      this.needsSave = false;
   }

   public void load(CompoundNBT playerNBT) {
      Iterator var2 = this.modules.iterator();

      while(var2.hasNext()) {
         PlayerDataModule module = (PlayerDataModule)var2.next();

         try {
            module.load(playerNBT);
         } catch (Exception var5) {
            LOTRLog.error("Error loading player data module %s for player %s", this.getModuleCode(module), this.playerUUID.toString());
            var5.printStackTrace();
         }
      }

   }

   public void handleLoginAndSendLoginData(ServerPlayerEntity player) {
      Iterator var2 = this.modules.iterator();

      while(var2.hasNext()) {
         PlayerDataModule module = (PlayerDataModule)var2.next();
         module.handleLogin(player);
         PacketBuffer buf = new PacketBuffer(Unpooled.buffer());
         module.sendLoginData(buf);
         if (buf.writerIndex() > 0) {
            String moduleCode = this.getModuleCode(module);
            SPacketLoginPlayerDataModule packet = new SPacketLoginPlayerDataModule(moduleCode, buf);
            LOTRPacketHandler.sendTo(packet, player);
         }
      }

   }

   public void receiveLoginData(String moduleCode, ByteBuf moduleData) {
      PlayerDataModule module = (PlayerDataModule)this.modulesByName.get(moduleCode);
      if (module != null) {
         module.receiveLoginData(new PacketBuffer(moduleData));
      } else {
         LOTRLog.error("Received login playerdata for nonexistent data module %s", moduleCode);
      }

   }

   public void sendPacketToClient(Object packet) {
      boolean executed = this.executeIfPlayerExistsServerside((player) -> {
         LOTRPacketHandler.sendTo(packet, player);
      });
      if (!executed && this.getParentLevelData().getLogicalSide() == LogicalSide.SERVER) {
         LOTRLog.error("Server tried to send a playerdata packet (%s) to %s, but didn't find the player online!", packet.getClass().getSimpleName(), this.playerUUID);
      }

   }

   public boolean executeIfPlayerExistsServerside(Consumer action) {
      PlayerEntity player = this.findPlayer();
      if (player != null && player instanceof ServerPlayerEntity) {
         action.accept((ServerPlayerEntity)player);
         return true;
      } else {
         return false;
      }
   }

   private PlayerEntity findPlayer() {
      if (LOTRMod.PROXY.isClient()) {
         return LOTRMod.PROXY.getClientPlayer();
      } else {
         MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
         return server.func_184103_al().func_177451_a(this.playerUUID);
      }
   }

   protected void logPlayerError(String msg, Object... args) {
      LOTRLog.error("playerdata %s: %s", this.playerUUID.toString(), String.format(msg, args));
   }

   public FastTravelDataModule getFastTravelData() {
      return this.fastTravelData;
   }

   public MapMarkerDataModule getMapMarkerData() {
      return this.mapMarkerData;
   }

   public AlignmentDataModule getAlignmentData() {
      return this.alignmentData;
   }

   public FactionStatsDataModule getFactionStatsData() {
      return this.factionStatsData;
   }

   public MessageDataModule getMessageData() {
      return this.messageData;
   }

   public FogDataModule getFogData() {
      return this.fogData;
   }

   public MiscDataModule getMiscData() {
      return this.miscData;
   }

   public void onUpdate(ServerPlayerEntity player, ServerWorld world) {
      ++this.updateTick;
      this.modules.forEach((module) -> {
         module.onUpdate(player, world, this.updateTick);
      });
   }

   public boolean getHideMapLocation() {
      return !this.miscData.getShowMapLocation();
   }

   public boolean getAdminHideMap() {
      return false;
   }
}
