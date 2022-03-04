package lotr.common.entity.npc.ai.goal;

import java.util.EnumSet;
import java.util.Random;
import lotr.common.entity.npc.NPCEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.Goal.Flag;
import net.minecraft.item.ItemStack;

public abstract class NPCConsumeGoal extends Goal {
   protected NPCEntity theEntity;
   protected Random rand;
   private int chanceToConsume;
   private int consumeTick;

   public NPCConsumeGoal(NPCEntity entity, int chance) {
      this.theEntity = entity;
      this.rand = this.theEntity.func_70681_au();
      this.chanceToConsume = chance;
      this.func_220684_a(EnumSet.of(Flag.MOVE, Flag.LOOK));
   }

   public boolean func_75250_a() {
      if (this.theEntity.func_70631_g_()) {
         return false;
      } else if (this.theEntity.func_70638_az() != null) {
         return false;
      } else {
         return this.theEntity.getNPCItemsInv().getIsEating() ? false : this.shouldConsume();
      }
   }

   protected boolean shouldConsume() {
      boolean needsHeal = this.theEntity.func_110143_aJ() < this.theEntity.func_110138_aP();
      return needsHeal && this.rand.nextInt(this.chanceToConsume / 4) == 0 || this.rand.nextInt(this.chanceToConsume) == 0;
   }

   public void func_75249_e() {
      this.theEntity.getNPCItemsInv().backupHeldAndStartEating(this.createConsumable());
      this.consumeTick = this.getConsumeTime();
   }

   protected int getConsumeTime() {
      return 32;
   }

   public void func_75246_d() {
      --this.consumeTick;
      this.updateConsumeTick(this.consumeTick);
      if (this.consumeTick == 0) {
         this.consume();
      }

   }

   protected abstract ItemStack createConsumable();

   protected abstract void updateConsumeTick(int var1);

   protected abstract void consume();

   protected final ItemStack getHeldConsumingItem() {
      return this.theEntity.func_184614_ca();
   }

   public boolean func_75253_b() {
      return this.consumeTick > 0 && !this.theEntity.func_184614_ca().func_190926_b() && this.theEntity.func_70638_az() == null;
   }

   public void func_75251_c() {
      this.theEntity.getNPCItemsInv().stopEatingAndRestoreHeld();
      this.theEntity.refreshCurrentAttackMode();
      this.consumeTick = 0;
   }
}
