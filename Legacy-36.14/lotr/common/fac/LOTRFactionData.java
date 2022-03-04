package lotr.common.fac;

import lotr.common.LOTRPlayerData;
import net.minecraft.nbt.NBTTagCompound;

public class LOTRFactionData {
   private LOTRPlayerData playerData;
   private LOTRFaction theFaction;
   private int npcsKilled;
   private int enemiesKilled;
   private int tradeCount;
   private int hireCount;
   private int miniQuestsCompleted;
   private float conquestEarned;
   private boolean hasConquestHorn;

   public LOTRFactionData(LOTRPlayerData data, LOTRFaction faction) {
      this.playerData = data;
      this.theFaction = faction;
   }

   public void save(NBTTagCompound nbt) {
      nbt.func_74768_a("NPCKill", this.npcsKilled);
      nbt.func_74768_a("EnemyKill", this.enemiesKilled);
      nbt.func_74768_a("Trades", this.tradeCount);
      nbt.func_74768_a("Hired", this.hireCount);
      nbt.func_74768_a("MiniQuests", this.miniQuestsCompleted);
      if (this.conquestEarned != 0.0F) {
         nbt.func_74776_a("Conquest", this.conquestEarned);
      }

      nbt.func_74757_a("ConquestHorn", this.hasConquestHorn);
   }

   public void load(NBTTagCompound nbt) {
      this.npcsKilled = nbt.func_74762_e("NPCKill");
      this.enemiesKilled = nbt.func_74762_e("EnemyKill");
      this.tradeCount = nbt.func_74762_e("Trades");
      this.hireCount = nbt.func_74762_e("Hired");
      this.miniQuestsCompleted = nbt.func_74762_e("MiniQuests");
      this.conquestEarned = nbt.func_74760_g("Conquest");
      this.hasConquestHorn = nbt.func_74767_n("ConquestHorn");
   }

   private void updateFactionData() {
      this.playerData.updateFactionData(this.theFaction, this);
   }

   public int getNPCsKilled() {
      return this.npcsKilled;
   }

   public void addNPCKill() {
      ++this.npcsKilled;
      this.updateFactionData();
   }

   public int getEnemiesKilled() {
      return this.enemiesKilled;
   }

   public void addEnemyKill() {
      ++this.enemiesKilled;
      this.updateFactionData();
   }

   public int getTradeCount() {
      return this.tradeCount;
   }

   public void addTrade() {
      ++this.tradeCount;
      this.updateFactionData();
   }

   public int getHireCount() {
      return this.hireCount;
   }

   public void addHire() {
      ++this.hireCount;
      this.updateFactionData();
   }

   public int getMiniQuestsCompleted() {
      return this.miniQuestsCompleted;
   }

   public void completeMiniQuest() {
      ++this.miniQuestsCompleted;
      this.updateFactionData();
   }

   public float getConquestEarned() {
      return this.conquestEarned;
   }

   public void addConquest(float f) {
      this.conquestEarned += f;
      this.updateFactionData();
   }

   public boolean hasConquestHorn() {
      return this.hasConquestHorn;
   }

   public void takeConquestHorn() {
      this.hasConquestHorn = true;
      this.updateFactionData();
   }
}
