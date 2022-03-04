package lotr.common.entity.npc;

import lotr.common.config.LOTRConfig;
import lotr.common.entity.npc.ai.AttackGoalsHolder;
import lotr.common.entity.npc.ai.StandardAttackModeUpdaters;
import lotr.common.entity.npc.ai.goal.FriendlyNPCConversationGoal;
import lotr.common.entity.npc.ai.goal.NPCDrinkGoal;
import lotr.common.entity.npc.ai.goal.NPCEatGoal;
import lotr.common.entity.npc.ai.goal.NPCMeleeAttackGoal;
import lotr.common.entity.npc.ai.goal.NPCRangedAttackGoal;
import lotr.common.entity.npc.ai.goal.StargazingGoal;
import lotr.common.entity.npc.ai.goal.TalkToCurrentGoal;
import lotr.common.entity.npc.ai.goal.WatchSunriseSunsetGoal;
import lotr.common.entity.npc.data.NPCFoodPool;
import lotr.common.entity.npc.data.NPCFoodPools;
import lotr.common.init.LOTRAttributes;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.AttributeModifierMap.MutableAttribute;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.OpenDoorGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public abstract class ElfEntity extends NPCEntity {
   public ElfEntity(EntityType type, World w) {
      super(type, w);
      this.getNPCCombatUpdater().setAttackModeUpdater(StandardAttackModeUpdaters.meleeRangedSwitching());
      this.spawnRequiresSurfaceBlock = true;
   }

   public static MutableAttribute regAttrs() {
      return NPCEntity.registerBaseNPCAttributes().func_233815_a_(Attributes.field_233818_a_, 30.0D).func_233815_a_(Attributes.field_233821_d_, 0.2D).func_233815_a_((Attribute)LOTRAttributes.NPC_RANGED_INACCURACY.get(), 0.5D);
   }

   protected void addNPCAI() {
      super.addNPCAI();
      this.field_70714_bg.func_75776_a(0, new SwimGoal(this));
      this.addAttackGoal(2);
      this.field_70714_bg.func_75776_a(4, new OpenDoorGoal(this, true));
      this.field_70714_bg.func_75776_a(5, new TalkToCurrentGoal(this));
      this.field_70714_bg.func_75776_a(6, new FriendlyNPCConversationGoal(this, 0.001F));
      this.field_70714_bg.func_75776_a(7, new WatchSunriseSunsetGoal(this, 0.01F));
      this.field_70714_bg.func_75776_a(8, new StargazingGoal(this, 4.0E-4F));
      this.field_70714_bg.func_75776_a(9, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
      this.field_70714_bg.func_75776_a(10, new NPCEatGoal(this, this.getEatPool(), 12000));
      this.field_70714_bg.func_75776_a(10, new NPCDrinkGoal(this, this.getDrinkPool(), 8000));
      this.field_70714_bg.func_75776_a(11, new LookAtGoal(this, PlayerEntity.class, 8.0F, 0.02F));
      this.field_70714_bg.func_75776_a(11, new LookAtGoal(this, NPCEntity.class, 5.0F, 0.02F));
      this.field_70714_bg.func_75776_a(12, new LookAtGoal(this, LivingEntity.class, 8.0F, 0.02F));
      this.field_70714_bg.func_75776_a(13, new LookRandomlyGoal(this));
   }

   protected void initialiseAttackGoals(AttackGoalsHolder holder) {
      holder.setMeleeAttackGoal(this.createElfMeleeAttackGoal());
      holder.setRangedAttackGoal(this.createElfRangedAttackGoal());
   }

   protected Goal createElfRangedAttackGoal() {
      return new NPCRangedAttackGoal(this, 1.25D, 20, 16.0F);
   }

   protected Goal createElfMeleeAttackGoal() {
      return new NPCMeleeAttackGoal(this, 1.4D);
   }

   protected void addNPCTargetingAI() {
      this.addAggressiveTargetingGoals();
   }

   protected NPCFoodPool getEatPool() {
      return NPCFoodPools.ELF;
   }

   protected NPCFoodPool getDrinkPool() {
      return NPCFoodPools.ELF_DRINK;
   }

   public boolean useSmallArmsModel() {
      return (Boolean)LOTRConfig.CLIENT.elfWomenUseAlexModelStyle.get() && this.personalInfo.isFemale();
   }
}
