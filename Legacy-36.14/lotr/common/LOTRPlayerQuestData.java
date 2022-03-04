package lotr.common;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.WorldServer;

public class LOTRPlayerQuestData {
   private LOTRPlayerData playerData;
   private boolean givenFirstPouches = false;

   public LOTRPlayerQuestData(LOTRPlayerData pd) {
      this.playerData = pd;
   }

   public void save(NBTTagCompound questData) {
      questData.func_74757_a("Pouches", this.givenFirstPouches);
   }

   public void load(NBTTagCompound questData) {
      this.givenFirstPouches = questData.func_74767_n("Pouches");
   }

   public void onUpdate(EntityPlayerMP entityplayer, WorldServer world) {
   }

   private void markDirty() {
      this.playerData.markDirty();
   }

   public boolean getGivenFirstPouches() {
      return this.givenFirstPouches;
   }

   public void setGivenFirstPouches(boolean flag) {
      this.givenFirstPouches = flag;
      this.markDirty();
   }
}
