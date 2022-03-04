package lotr.common.entity.npc;

import lotr.common.LOTRAchievement;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import lotr.common.quest.LOTRMiniQuest;
import lotr.common.quest.LOTRMiniQuestFactory;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.world.World;

public class LOTREntityRuffianBrute extends LOTREntityBreeRuffian {
   public LOTREntityRuffianBrute(World world) {
      super(world);
   }

   protected int addBreeAttackAI(int prio) {
      this.field_70714_bg.func_75776_a(prio, new LOTREntityAIAttackOnCollide(this, 1.5D, false));
      return prio;
   }

   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(30.0D);
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
      return data;
   }

   protected LOTRAchievement getKillAchievement() {
      return LOTRAchievement.killRuffianBrute;
   }

   public LOTRMiniQuest createMiniQuest() {
      return LOTRMiniQuestFactory.RUFFIAN_BRUTE.createQuest(this);
   }

   public LOTRMiniQuestFactory getBountyHelpSpeechDir() {
      return LOTRMiniQuestFactory.RUFFIAN_BRUTE;
   }
}
