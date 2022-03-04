package lotr.common.entity.ai;

import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityWoodElf;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.player.EntityPlayer;

public class LOTREntityAINearestAttackableTargetWoodElf extends LOTREntityAINearestAttackableTargetBasic {
   public LOTREntityAINearestAttackableTargetWoodElf(EntityCreature entity, Class targetClass, int chance, boolean flag) {
      super(entity, targetClass, chance, flag);
   }

   public LOTREntityAINearestAttackableTargetWoodElf(EntityCreature entity, Class targetClass, int chance, boolean flag, IEntitySelector selector) {
      super(entity, targetClass, chance, flag, selector);
   }

   protected boolean isPlayerSuitableAlignmentTarget(EntityPlayer entityplayer) {
      float alignment = LOTRLevelData.getData(entityplayer).getAlignment(LOTRMod.getNPCFaction(this.field_75299_d));
      if (alignment >= LOTREntityWoodElf.getWoodlandTrustLevel()) {
         return false;
      } else if (alignment >= 0.0F) {
         int chance = Math.round(alignment * 20.0F);
         chance = Math.max(chance, 1);
         return this.field_75299_d.func_70681_au().nextInt(chance) == 0;
      } else {
         return super.isPlayerSuitableAlignmentTarget(entityplayer);
      }
   }
}
