package lotr.common.data;

import lotr.common.LOTRLog;
import lotr.common.fac.RankGender;
import lotr.common.network.CPacketChoosePreferredRankGender;
import lotr.common.network.CPacketToggle;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.SPacketPreferredRankGender;
import lotr.common.network.SPacketToggle;
import lotr.common.network.SidedTogglePacket;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.server.ServerWorld;

public class MiscDataModule extends PlayerDataModule {
   private boolean initialSpawnedIntoME;
   private int alcoholTolerance;
   private RankGender preferredRankGender;
   private boolean showMapLocation;

   protected MiscDataModule(LOTRPlayerData pd) {
      super(pd);
      this.preferredRankGender = RankGender.MASCULINE;
      this.showMapLocation = true;
   }

   public void save(CompoundNBT playerNBT) {
      playerNBT.func_74757_a("InitialSpawnedIntoME", this.initialSpawnedIntoME);
      playerNBT.func_74768_a("Alcohol", this.alcoholTolerance);
      playerNBT.func_74778_a("RankGender", this.preferredRankGender.getSaveName());
      playerNBT.func_74757_a("ShowMapLocation", this.showMapLocation);
   }

   public void load(CompoundNBT playerNBT) {
      this.initialSpawnedIntoME = playerNBT.func_74767_n("InitialSpawnedIntoME");
      this.alcoholTolerance = playerNBT.func_74762_e("Alcohol");
      if (playerNBT.func_74764_b("RankGender")) {
         String genderName = playerNBT.func_74779_i("RankGender");
         RankGender loadedGender = RankGender.forSaveName(genderName);
         if (loadedGender != null) {
            this.preferredRankGender = loadedGender;
         } else {
            this.playerData.logPlayerError("No preferred rank gender by name %s", genderName);
         }
      }

      this.showMapLocation = (Boolean)DataUtil.getIfNBTContains(this.showMapLocation, playerNBT, "ShowMapLocation", CompoundNBT::func_74767_n);
   }

   protected void sendLoginData(PacketBuffer buf) {
      buf.func_150787_b(this.preferredRankGender.networkID);
      buf.writeBoolean(this.showMapLocation);
   }

   protected void receiveLoginData(PacketBuffer buf) {
      int genderId = buf.func_150792_a();
      RankGender gender = RankGender.forNetworkID(genderId);
      if (gender != null) {
         this.preferredRankGender = gender;
      } else {
         LOTRLog.warn("Received nonexistent preferred rank gender ID %d from server", genderId);
      }

      this.showMapLocation = buf.readBoolean();
   }

   protected void onUpdate(ServerPlayerEntity player, ServerWorld world, int tick) {
      if (tick % 24000 == 0 && this.alcoholTolerance > 0) {
         --this.alcoholTolerance;
         this.setAlcoholTolerance(this.alcoholTolerance);
      }

   }

   public boolean getInitialSpawnedIntoME() {
      return this.initialSpawnedIntoME;
   }

   public void setInitialSpawnedIntoME(boolean flag) {
      this.initialSpawnedIntoME = flag;
      this.markDirty();
   }

   public int getAlcoholTolerance() {
      return this.alcoholTolerance;
   }

   public void setAlcoholTolerance(int i) {
      this.alcoholTolerance = i;
      this.markDirty();
      if (this.alcoholTolerance >= 250) {
      }

   }

   public RankGender getPreferredRankGender() {
      return this.preferredRankGender;
   }

   public void setPreferredRankGender(RankGender gender) {
      if (gender != null) {
         this.preferredRankGender = gender;
         this.markDirty();
         this.sendPacketToClient(new SPacketPreferredRankGender(this.preferredRankGender));
      }

   }

   public void setPreferredRankGenderAndSendToServer(RankGender gender) {
      if (gender != null) {
         this.setPreferredRankGender(gender);
         LOTRPacketHandler.sendToServer(new CPacketChoosePreferredRankGender(this.preferredRankGender));
      }

   }

   public boolean getShowMapLocation() {
      return this.showMapLocation;
   }

   public void setShowMapLocation(boolean flag) {
      if (this.showMapLocation != flag) {
         this.showMapLocation = flag;
         this.markDirty();
         this.sendPacketToClient(new SPacketToggle(SidedTogglePacket.ToggleType.SHOW_MAP_LOCATION, this.showMapLocation));
      }

   }

   public void toggleShowMapLocationAndSendToServer() {
      this.showMapLocation = !this.showMapLocation;
      LOTRPacketHandler.sendToServer(new CPacketToggle(SidedTogglePacket.ToggleType.SHOW_MAP_LOCATION, this.showMapLocation));
   }
}
