package lotr.common.entity.ai;

import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.player.EntityPlayer;

public class LOTREntityAINearestAttackableTargetTroll extends LOTREntityAINearestAttackableTargetBasic {
   public LOTREntityAINearestAttackableTargetTroll(EntityCreature entity, Class targetClass, int chance, boolean flag) {
      super(entity, targetClass, chance, flag);
   }

   public LOTREntityAINearestAttackableTargetTroll(EntityCreature entity, Class targetClass, int chance, boolean flag, IEntitySelector selector) {
      super(entity, targetClass, chance, flag, selector);
   }

   protected boolean isPlayerSuitableAlignmentTarget(EntityPlayer entityplayer) {
      float alignment = LOTRLevelData.getData(entityplayer).getAlignment(LOTRMod.getNPCFaction(this.field_75299_d));
      if (alignment >= 100.0F) {
         return false;
      } else if (alignment >= 0.0F) {
         int chance = Math.round(alignment * 10.0F);
         chance = Math.max(chance, 1);
         return this.field_75299_d.func_70681_au().nextInt(chance) == 0;
      } else {
         return super.isPlayerSuitableAlignmentTarget(entityplayer);
      }
   }
}
