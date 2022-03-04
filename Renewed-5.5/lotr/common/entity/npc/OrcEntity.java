package lotr.common.entity.npc;

import lotr.common.config.LOTRConfig;
import lotr.common.entity.npc.ai.AttackGoalsHolder;
import lotr.common.entity.npc.ai.StandardAttackModeUpdaters;
import lotr.common.entity.npc.ai.goal.FriendlyNPCConversationGoal;
import lotr.common.entity.npc.ai.goal.NPCDrinkGoal;
import lotr.common.entity.npc.ai.goal.NPCEatGoal;
import lotr.common.entity.npc.ai.goal.TalkToCurrentGoal;
import lotr.common.entity.npc.data.NPCFoodPools;
import lotr.common.entity.npc.data.NPCGenderProvider;
import lotr.common.entity.npc.data.name.NPCNameGenerator;
import lotr.common.entity.npc.data.name.NPCNameGenerators;
import lotr.common.init.LOTRSoundEvents;
import lotr.common.world.spawning.NPCSpawnSettingsManager;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.AttributeModifierMap.MutableAttribute;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.OpenDoorGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

public abstract class OrcEntity extends NPCEntity {
   protected boolean isOrcWeakInSun = true;

   public OrcEntity(EntityType type, World w) {
      super(type, w);
      this.getNPCCombatUpdater().setAttackModeUpdater(StandardAttackModeUpdaters.meleeOnlyOrcWithBomb());
      this.spawnRequiresDarkness = true;
   }

   protected NPCGenderProvider getGenderProvider() {
      return NPCGenderProvider.MALE;
   }

   protected NPCNameGenerator getNameGenerator() {
      return NPCNameGenerators.ORC;
   }

   public static MutableAttribute regAttrs() {
      return NPCEntity.registerBaseNPCAttributes().func_233815_a_(Attributes.field_233818_a_, 18.0D).func_233815_a_(Attributes.field_233821_d_, 0.2D);
   }

   protected void addNPCAI() {
      super.addNPCAI();
      this.field_70714_bg.func_75776_a(0, new SwimGoal(this));
      this.addAttackGoal(4);
      this.field_70714_bg.func_75776_a(6, new OpenDoorGoal(this, true));
      this.field_70714_bg.func_75776_a(7, new TalkToCurrentGoal(this));
      this.field_70714_bg.func_75776_a(8, new FriendlyNPCConversationGoal(this, 2.0E-4F));
      this.field_70714_bg.func_75776_a(9, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
      this.field_70714_bg.func_75776_a(10, new NPCEatGoal(this, NPCFoodPools.ORC, 6000));
      this.field_70714_bg.func_75776_a(10, new NPCDrinkGoal(this, NPCFoodPools.ORC_DRINK, 6000));
      this.field_70714_bg.func_75776_a(11, new LookAtGoal(this, PlayerEntity.class, 8.0F, 0.05F));
      this.field_70714_bg.func_75776_a(11, new LookAtGoal(this, NPCEntity.class, 5.0F, 0.05F));
      this.field_70714_bg.func_75776_a(12, new LookAtGoal(this, LivingEntity.class, 8.0F, 0.02F));
      this.field_70714_bg.func_75776_a(13, new LookRandomlyGoal(this));
   }

   protected void initialiseAttackGoals(AttackGoalsHolder holder) {
      holder.setMeleeAttackGoal(this.createOrcAttackGoal());
   }

   protected abstract Goal createOrcAttackGoal();

   protected void addNPCTargetingAI() {
      int target = this.addAggressiveTargetingGoals();
   }

   public void func_70071_h_() {
      super.func_70071_h_();
      if (!this.field_70170_p.field_72995_K && this.isOrcWeakInSun && this.isOrcExposedToSun() && this.field_70173_aa % 20 == 0) {
         this.func_195064_c(new EffectInstance(Effects.field_76429_m, 200, -2));
         this.func_195064_c(new EffectInstance(Effects.field_76421_d, 200));
      }

   }

   private boolean isOrcExposedToSun() {
      BlockPos pos = this.func_233580_cy_();
      Biome biome = this.field_70170_p.func_226691_t_(pos);
      return this.field_70170_p.func_72935_r() && this.field_70170_p.func_175710_j(pos) && !NPCSpawnSettingsManager.getSpawnsForBiome(biome, this.field_70170_p).allowsDarknessSpawnsInDaytime();
   }

   protected SoundEvent func_184639_G() {
      return LOTRSoundEvents.ORC_AEUGH;
   }

   protected SoundEvent func_184601_bQ(DamageSource source) {
      return LOTRSoundEvents.ORC_HURT;
   }

   protected SoundEvent func_184615_bR() {
      return LOTRSoundEvents.ORC_DEATH;
   }

   public boolean useSmallArmsModel() {
      return (Boolean)LOTRConfig.CLIENT.orcWomenUseAlexModelStyle.get() && this.personalInfo.isFemale();
   }
}
