package lotr.common.entity.npc;

import lotr.common.config.LOTRConfig;
import lotr.common.entity.npc.ai.AttackGoalsHolder;
import lotr.common.entity.npc.ai.goal.FriendlyNPCConversationGoal;
import lotr.common.entity.npc.ai.goal.NPCDrinkGoal;
import lotr.common.entity.npc.ai.goal.NPCEatGoal;
import lotr.common.entity.npc.ai.goal.TalkToCurrentGoal;
import lotr.common.entity.npc.ai.goal.WatchSunriseSunsetGoal;
import lotr.common.entity.npc.data.NPCFoodPool;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.OpenDoorGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public abstract class ManEntity extends AbstractMannishEntity {
   public ManEntity(EntityType type, World w) {
      super(type, w);
   }

   protected void addNPCAI() {
      super.addNPCAI();
      this.field_70714_bg.func_75776_a(0, new SwimGoal(this));
      this.addAttackGoal(2);
      this.field_70714_bg.func_75776_a(4, new OpenDoorGoal(this, true));
      this.field_70714_bg.func_75776_a(5, new TalkToCurrentGoal(this));
      this.field_70714_bg.func_75776_a(6, new FriendlyNPCConversationGoal(this, 5.0E-4F));
      this.field_70714_bg.func_75776_a(7, new WatchSunriseSunsetGoal(this, 0.005F));
      this.field_70714_bg.func_75776_a(8, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
      this.addConsumingGoals(9);
      this.field_70714_bg.func_75776_a(10, new LookAtGoal(this, PlayerEntity.class, 8.0F, 0.02F));
      this.field_70714_bg.func_75776_a(10, new LookAtGoal(this, NPCEntity.class, 5.0F, 0.02F));
      this.field_70714_bg.func_75776_a(11, new LookAtGoal(this, LivingEntity.class, 8.0F, 0.02F));
      this.field_70714_bg.func_75776_a(12, new LookRandomlyGoal(this));
   }

   protected void initialiseAttackGoals(AttackGoalsHolder holder) {
      holder.setMeleeAttackGoal(this.createAttackGoal());
   }

   protected abstract Goal createAttackGoal();

   protected void addConsumingGoals(int prio) {
      this.field_70714_bg.func_75776_a(prio, new NPCEatGoal(this, this.getEatPool(), 8000));
      this.field_70714_bg.func_75776_a(prio, new NPCDrinkGoal(this, this.getDrinkPool(), 8000));
   }

   protected abstract NPCFoodPool getEatPool();

   protected abstract NPCFoodPool getDrinkPool();

   public boolean useSmallArmsModel() {
      return (Boolean)LOTRConfig.CLIENT.mannishWomenUseAlexModelStyle.get() && this.personalInfo.isFemale();
   }
}
