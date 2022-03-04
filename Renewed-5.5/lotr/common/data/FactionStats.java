package lotr.common.data;

import lotr.common.fac.Faction;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;

public class FactionStats {
   private final FactionStatsDataModule dataModule;
   private final Faction faction;
   private int membersKilled;
   private int enemiesKilled;
   private int tradeCount;
   private int hireCount;
   private int miniQuestsCompleted;
   private float conquestEarned;
   private boolean hasConquestHorn;

   public FactionStats(FactionStatsDataModule data, Faction fac) {
      this.dataModule = data;
      this.faction = fac;
   }

   public void save(CompoundNBT nbt) {
      nbt.func_74768_a("MemberKill", this.membersKilled);
      nbt.func_74768_a("EnemyKill", this.enemiesKilled);
      nbt.func_74768_a("Trades", this.tradeCount);
      nbt.func_74768_a("Hired", this.hireCount);
      nbt.func_74768_a("MiniQuests", this.miniQuestsCompleted);
      if (this.conquestEarned != 0.0F) {
         nbt.func_74776_a("Conquest", this.conquestEarned);
      }

      nbt.func_74757_a("ConquestHorn", this.hasConquestHorn);
   }

   public void load(CompoundNBT nbt) {
      this.membersKilled = nbt.func_74762_e("MemberKill");
      this.enemiesKilled = nbt.func_74762_e("EnemyKill");
      this.tradeCount = nbt.func_74762_e("Trades");
      this.hireCount = nbt.func_74762_e("Hired");
      this.miniQuestsCompleted = nbt.func_74762_e("MiniQuests");
      this.conquestEarned = nbt.func_74760_g("Conquest");
      this.hasConquestHorn = nbt.func_74767_n("ConquestHorn");
   }

   public void write(PacketBuffer buf) {
      buf.func_150787_b(this.membersKilled);
      buf.func_150787_b(this.enemiesKilled);
      buf.func_150787_b(this.tradeCount);
      buf.func_150787_b(this.hireCount);
      buf.func_150787_b(this.miniQuestsCompleted);
      buf.writeFloat(this.conquestEarned);
      buf.writeBoolean(this.hasConquestHorn);
   }

   public void read(PacketBuffer buf) {
      this.membersKilled = buf.func_150792_a();
      this.enemiesKilled = buf.func_150792_a();
      this.tradeCount = buf.func_150792_a();
      this.hireCount = buf.func_150792_a();
      this.miniQuestsCompleted = buf.func_150792_a();
      this.conquestEarned = buf.readFloat();
      this.hasConquestHorn = buf.readBoolean();
   }

   private void updateFactionData() {
      this.dataModule.updateFactionData(this.faction);
   }

   public int getMembersKilled() {
      return this.membersKilled;
   }

   public void addMemberKill() {
      ++this.membersKilled;
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
