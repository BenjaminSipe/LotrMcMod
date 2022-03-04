package lotr.common.entity.ai;

import java.util.Random;
import lotr.common.LOTRFoods;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.item.ItemStack;

public abstract class LOTREntityAIConsumeBase extends EntityAIBase {
   protected LOTREntityNPC theEntity;
   protected Random rand;
   protected LOTRFoods foodPool;
   private int chanceToConsume;
   private int consumeTick;

   public LOTREntityAIConsumeBase(LOTREntityNPC entity, LOTRFoods foods, int chance) {
      this.theEntity = entity;
      this.rand = this.theEntity.func_70681_au();
      this.foodPool = foods;
      this.chanceToConsume = chance;
      this.func_75248_a(3);
   }

   public boolean func_75250_a() {
      if (this.theEntity.func_70631_g_()) {
         return false;
      } else if (this.theEntity.func_70638_az() != null) {
         return false;
      } else {
         return this.theEntity.npcItemsInv.getIsEating() ? false : this.shouldConsume();
      }
   }

   protected boolean shouldConsume() {
      boolean needsHeal = this.theEntity.func_110143_aJ() < this.theEntity.func_110138_aP();
      return needsHeal && this.rand.nextInt(this.chanceToConsume / 4) == 0 || this.rand.nextInt(this.chanceToConsume) == 0;
   }

   public void func_75249_e() {
      this.theEntity.npcItemsInv.setEatingBackup(this.theEntity.func_70694_bm());
      this.theEntity.npcItemsInv.setIsEating(true);
      this.theEntity.func_70062_b(0, this.createConsumable());
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

   public boolean func_75253_b() {
      return this.consumeTick > 0 && this.theEntity.func_70694_bm() != null && this.theEntity.func_70638_az() == null;
   }

   public void func_75251_c() {
      this.theEntity.func_70062_b(0, this.theEntity.npcItemsInv.getEatingBackup());
      this.theEntity.npcItemsInv.setEatingBackup((ItemStack)null);
      this.theEntity.npcItemsInv.setIsEating(false);
      this.theEntity.refreshCurrentAttackMode();
      this.consumeTick = 0;
   }
}
