package lotr.common.entity.ai;

import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.fac.LOTRFaction;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.player.EntityPlayer;

public class LOTREntityAINearestAttackableTargetOrc extends LOTREntityAINearestAttackableTargetBasic {
   public LOTREntityAINearestAttackableTargetOrc(EntityCreature entity, Class targetClass, int chance, boolean flag) {
      super(entity, targetClass, chance, flag);
   }

   public LOTREntityAINearestAttackableTargetOrc(EntityCreature entity, Class targetClass, int chance, boolean flag, IEntitySelector selector) {
      super(entity, targetClass, chance, flag, selector);
   }

   protected boolean isPlayerSuitableAlignmentTarget(EntityPlayer entityplayer) {
      LOTRFaction faction = LOTRMod.getNPCFaction(this.field_75299_d);
      if (faction == LOTRFaction.MORDOR) {
         float alignment = LOTRLevelData.getData(entityplayer).getAlignment(faction);
         if (alignment >= 100.0F) {
            return false;
         } else if (alignment >= 0.0F) {
            int chance = Math.round(alignment * 5.0F);
            chance = Math.max(chance, 1);
            return this.field_75299_d.func_70681_au().nextInt(chance) == 0;
         } else {
            return super.isPlayerSuitableAlignmentTarget(entityplayer);
         }
      } else {
         return super.isPlayerSuitableAlignmentTarget(entityplayer);
      }
   }
}
