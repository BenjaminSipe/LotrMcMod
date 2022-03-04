package lotr.common.entity.ai;

import lotr.common.entity.npc.IBandit;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public class LOTREntityAINearestAttackableTargetBandit extends LOTREntityAINearestAttackableTargetBasic {
   private final IBandit taskOwnerAsBandit;

   public LOTREntityAINearestAttackableTargetBandit(EntityCreature entity, Class targetClass, int chance, boolean flag) {
      super(entity, targetClass, chance, flag);
      this.taskOwnerAsBandit = (IBandit)entity;
   }

   public LOTREntityAINearestAttackableTargetBandit(EntityCreature entity, Class targetClass, int chance, boolean flag, IEntitySelector selector) {
      super(entity, targetClass, chance, flag, selector);
      this.taskOwnerAsBandit = (IBandit)entity;
   }

   public boolean func_75250_a() {
      return !this.taskOwnerAsBandit.getBanditInventory().isEmpty() ? false : super.func_75250_a();
   }

   protected boolean func_75296_a(EntityLivingBase entity, boolean flag) {
      return entity instanceof EntityPlayer && super.func_75296_a(entity, flag);
   }

   protected boolean isPlayerSuitableTarget(EntityPlayer entityplayer) {
      return IBandit.Helper.canStealFromPlayerInv(this.taskOwnerAsBandit, entityplayer) ? false : super.isPlayerSuitableTarget(entityplayer);
   }
}
