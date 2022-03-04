package lotr.common.entity.npc;

import java.util.function.Predicate;
import lotr.common.entity.DisableShieldHelper;
import lotr.common.entity.ai.goal.NonRiddenTargetGoal;
import lotr.common.entity.ai.goal.PanicIfBurningGoal;
import lotr.common.entity.ai.goal.RandomWalkingEvenWhenRiddenGoal;
import lotr.common.entity.npc.ai.AttackGoalsHolder;
import lotr.common.entity.npc.ai.goal.NPCMeleeAttackGoal;
import lotr.common.entity.npc.ai.goal.WargLeapAndDisableShieldGoal;
import lotr.common.init.LOTRSoundEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.AttributeModifierMap.MutableAttribute;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;

public abstract class WargEntity extends NPCEntity {
   private static final DataParameter WARG_TYPE;
   private static final DataParameter IS_LEAPING;
   private int leapingTick = 0;
   private int leapingProgress;
   private int prevLeapingProgress = 0;
   private static final int leapingProgressMax = 5;
   private static final Predicate ANIMAL_TARGETS;

   protected WargEntity(EntityType type, World w) {
      super(type, w);
      this.spawnRequiresDarkness = true;
   }

   protected void func_70088_a() {
      super.func_70088_a();
      this.field_70180_af.func_187214_a(WARG_TYPE, 0);
      this.field_70180_af.func_187214_a(IS_LEAPING, false);
   }

   public WargType getWargType() {
      return WargType.forId((Integer)this.field_70180_af.func_187225_a(WARG_TYPE));
   }

   public void setWargType(WargType type) {
      this.field_70180_af.func_187227_b(WARG_TYPE, type.getId());
   }

   public boolean getIsLeaping() {
      return (Boolean)this.field_70180_af.func_187225_a(IS_LEAPING);
   }

   private void setIsLeaping(boolean flag) {
      this.field_70180_af.func_187227_b(IS_LEAPING, flag);
   }

   public float getLeapingProgress(float f) {
      return MathHelper.func_219799_g(f, (float)this.prevLeapingProgress, (float)this.leapingProgress) / 5.0F;
   }

   public static MutableAttribute regAttrs() {
      return NPCEntity.registerBaseNPCAttributes().func_233815_a_(Attributes.field_233819_b_, 32.0D).func_233815_a_(Attributes.field_233821_d_, 0.22D).func_233815_a_(Attributes.field_233820_c_, 0.5D);
   }

   private void applyRandomisedWargAttributes() {
      this.func_110148_a(Attributes.field_233818_a_).func_111128_a((double)MathHelper.func_76136_a(this.field_70146_Z, 24, 40));
      this.func_110148_a(Attributes.field_233823_f_).func_111128_a((double)MathHelper.func_76136_a(this.field_70146_Z, 3, 5));
   }

   protected void addNPCAI() {
      super.addNPCAI();
      this.field_70714_bg.func_75776_a(0, new SwimGoal(this));
      this.field_70714_bg.func_75776_a(1, new PanicIfBurningGoal(this, 1.6D));
      this.field_70714_bg.func_75776_a(3, new WargLeapAndDisableShieldGoal(this, 0.45F));
      this.addAttackGoal(4);
      this.field_70714_bg.func_75776_a(7, new RandomWalkingEvenWhenRiddenGoal(this, 1.0D));
      this.field_70714_bg.func_75776_a(8, new LookAtGoal(this, PlayerEntity.class, 12.0F, 0.02F));
      this.field_70714_bg.func_75776_a(8, new LookAtGoal(this, NPCEntity.class, 8.0F, 0.02F));
      this.field_70714_bg.func_75776_a(9, new LookAtGoal(this, LivingEntity.class, 12.0F, 0.02F));
      this.field_70714_bg.func_75776_a(10, new LookRandomlyGoal(this));
   }

   protected void initialiseAttackGoals(AttackGoalsHolder holder) {
      holder.setMeleeAttackGoal(this.createWargAttackGoal());
   }

   protected Goal createWargAttackGoal() {
      return new NPCMeleeAttackGoal(this, 1.7D);
   }

   protected void addNPCTargetingAI() {
      int target = this.addAggressiveTargetingGoals();
      this.field_70715_bh.func_75776_a(target + 1, new NonRiddenTargetGoal(this, AnimalEntity.class, 500, true, false, ANIMAL_TARGETS));
   }

   public ILivingEntityData func_213386_a(IServerWorld sw, DifficultyInstance diff, SpawnReason reason, ILivingEntityData spawnData, CompoundNBT dataTag) {
      spawnData = super.func_213386_a(sw, diff, reason, spawnData, dataTag);
      this.applyRandomisedWargAttributes();
      this.setWargType(this.chooseWargType());
      this.addWargRider(sw, diff, reason);
      return spawnData;
   }

   protected abstract WargType chooseWargType();

   private void addWargRider(IServerWorld sw, DifficultyInstance diff, SpawnReason reason) {
      if (!this.field_70170_p.field_72995_K && this.canWargBeRidden() && this.field_70146_Z.nextInt(5) == 0) {
         NPCEntity rider = this.createWargRider();
         if (rider != null) {
            rider.func_70012_b(this.func_226277_ct_(), this.func_226278_cu_(), this.func_226281_cx_(), this.field_70177_z, 0.0F);
            rider.func_213386_a(sw, diff, reason, (ILivingEntityData)null, (CompoundNBT)null);
            if (this.func_104002_bU()) {
               rider.func_110163_bv();
            }

            rider.func_184220_m(this);
         }
      }

   }

   protected boolean canWargBeRidden() {
      return true;
   }

   protected abstract NPCEntity createWargRider();

   public double func_70042_X() {
      return (double)(this.func_213302_cg() * 0.63F);
   }

   public void startLeaping() {
      this.leapingTick = 40;
   }

   public void func_70071_h_() {
      super.func_70071_h_();
      if (!this.field_70170_p.field_72995_K) {
         if (this.leapingTick > 0) {
            --this.leapingTick;
         }

         this.setIsLeaping(this.leapingTick > 0 && !this.func_233570_aj_());
      } else {
         this.prevLeapingProgress = this.leapingProgress;
         if (this.getIsLeaping()) {
            this.leapingProgress = Math.min(this.leapingProgress + 1, 5);
         } else {
            this.leapingProgress = Math.max(this.leapingProgress - 1, 0);
         }
      }

   }

   public boolean func_70652_k(Entity target) {
      boolean flag = super.func_70652_k(target);
      if (this.leapingTick > 0) {
         DisableShieldHelper.disableShieldIfEntityShielding(target, true);
         this.leapingTick = 0;
      }

      return flag;
   }

   public void func_213281_b(CompoundNBT nbt) {
      super.func_213281_b(nbt);
      nbt.func_74768_a("WargType", this.getWargType().getId());
   }

   public void func_70037_a(CompoundNBT nbt) {
      super.func_70037_a(nbt);
      this.setWargType(WargType.forId(nbt.func_74762_e("WargType")));
   }

   protected SoundEvent func_184639_G() {
      return LOTRSoundEvents.WARG_AMBIENT;
   }

   protected SoundEvent func_184601_bQ(DamageSource source) {
      return LOTRSoundEvents.WARG_HURT;
   }

   protected SoundEvent func_184615_bR() {
      return LOTRSoundEvents.WARG_DEATH;
   }

   protected SoundEvent getAttackSound() {
      return LOTRSoundEvents.WARG_ATTACK;
   }

   static {
      WARG_TYPE = EntityDataManager.func_187226_a(WargEntity.class, DataSerializers.field_187192_b);
      IS_LEAPING = EntityDataManager.func_187226_a(WargEntity.class, DataSerializers.field_187198_h);
      ANIMAL_TARGETS = (entity) -> {
         EntityType type = entity.func_200600_R();
         return type == EntityType.field_200737_ac || type == EntityType.field_200795_i || type == EntityType.field_200736_ab || type == EntityType.field_220356_B;
      };
   }
}
