package lotr.common.entity.npc.ai.goal;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;
import lotr.common.entity.npc.NPCEntity;
import lotr.common.entity.npc.NPCPredicates;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.Goal.Flag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.pathfinding.Path;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeMod;

public class NPCMeleeAttackGoal extends Goal {
   private final NPCEntity theEntity;
   private final World world;
   private final Random rand;
   private final double speedTowardsTarget;
   private final boolean longMemory;
   private Path initialPath;
   private int rePathDelay;
   private long lastCheckTime;
   private int weaponAccountedSwingCooldown;
   private NPCMeleeAttackGoal.MeleeMode meleeMode;
   private int decideMeleeModeTimer;
   private static final int MELEE_MODE_DECISION_INTERVAL = 5;
   private int timeSinceDifferentMeleeMode;
   private float cachedFriendsToFoesRatio;
   private long lastFriendsToFoesCacheTime;
   private int ongoingShieldingTime;
   private int timeUntilCanShield;
   private static final UUID DEFENDING_CLOSE_HALT_ID = UUID.fromString("c95298e0-93a9-4965-9189-52c9c151fb33");
   private static final AttributeModifier DEFENDING_CLOSE_HALT;

   public NPCMeleeAttackGoal(NPCEntity entity, double speed) {
      this(entity, speed, true);
   }

   private NPCMeleeAttackGoal(NPCEntity entity, double speed, boolean longMem) {
      this.decideMeleeModeTimer = 0;
      this.timeSinceDifferentMeleeMode = 0;
      this.lastFriendsToFoesCacheTime = 0L;
      this.ongoingShieldingTime = 0;
      this.timeUntilCanShield = 0;
      this.theEntity = entity;
      this.world = this.theEntity.field_70170_p;
      this.rand = this.theEntity.func_70681_au();
      this.speedTowardsTarget = speed;
      this.longMemory = longMem;
      this.func_220684_a(EnumSet.of(Flag.MOVE, Flag.LOOK));
   }

   public boolean func_75250_a() {
      long time = this.theEntity.field_70170_p.func_82737_E();
      if (time - this.lastCheckTime < 20L) {
         return false;
      } else {
         this.lastCheckTime = time;
         LivingEntity target = this.theEntity.func_70638_az();
         if (target != null && target.func_70089_S()) {
            this.initialPath = this.theEntity.func_70661_as().func_75494_a(target, 0);
            return this.initialPath != null || this.getAttackReachSq(target) >= this.theEntity.func_70092_e(target.func_226277_ct_(), target.func_226278_cu_(), target.func_226281_cx_());
         } else {
            return false;
         }
      }
   }

   public boolean func_75253_b() {
      LivingEntity target = this.theEntity.func_70638_az();
      if (target != null && target.func_70089_S()) {
         if (!this.longMemory) {
            return !this.theEntity.func_70661_as().func_75500_f();
         } else if (!(target instanceof PlayerEntity)) {
            return true;
         } else {
            PlayerEntity playerTarget = (PlayerEntity)target;
            return !playerTarget.func_175149_v() && !playerTarget.func_184812_l_();
         }
      } else {
         return false;
      }
   }

   public void func_75249_e() {
      this.theEntity.func_70661_as().func_75484_a(this.initialPath, this.speedTowardsTarget);
      this.theEntity.func_213395_q(true);
      this.rePathDelay = 0;
      this.weaponAccountedSwingCooldown = 0;
      this.updateMeleeMode(this.decideMeleeMode());
      this.decideMeleeModeTimer = 5;
      this.ongoingShieldingTime = 0;
      this.timeUntilCanShield = 0;
   }

   private void updateMeleeMode(NPCMeleeAttackGoal.MeleeMode newMode) {
      if (newMode != this.meleeMode) {
         this.meleeMode = newMode;
         this.timeSinceDifferentMeleeMode = 0;
         if (this.meleeMode == NPCMeleeAttackGoal.MeleeMode.DEFENSIVE) {
            Optional var10000 = this.getShieldingHand();
            NPCEntity var10001 = this.theEntity;
            var10000.ifPresent(var10001::func_184598_c);
         } else {
            this.theEntity.func_184597_cx();
         }
      }

   }

   private NPCMeleeAttackGoal.MeleeMode decideMeleeMode() {
      this.world.func_217381_Z().func_76320_a("decideNewMeleeMode");
      NPCMeleeAttackGoal.MeleeMode decision = this.doDecideMeleeMode();
      this.world.func_217381_Z().func_76319_b();
      return decision;
   }

   private NPCMeleeAttackGoal.MeleeMode doDecideMeleeMode() {
      if (this.ongoingShieldingTime > 80 + this.rand.nextInt(50)) {
         this.ongoingShieldingTime = 0;
         this.timeUntilCanShield = 80 + this.rand.nextInt(60);
         return NPCMeleeAttackGoal.MeleeMode.AGGRESSIVE;
      } else if (this.timeUntilCanShield > 0) {
         return NPCMeleeAttackGoal.MeleeMode.AGGRESSIVE;
      } else if (this.canUseShield()) {
         LivingEntity target = this.theEntity.func_70638_az();
         float attackReadiness = 1.0F;
         float lowHealthPriority;
         if (!this.isInRangeToAttack(target)) {
            int ticksSinceAttacked = this.theEntity.getNPCCombatUpdater().getTicksSinceAttacked();
            lowHealthPriority = 1.0F - (float)ticksSinceAttacked / 100.0F;
            lowHealthPriority = MathHelper.func_76131_a(lowHealthPriority, 0.0F, 1.0F);
            attackReadiness -= lowHealthPriority * 2.0F;
         } else {
            attackReadiness += 0.5F;
         }

         float health = this.theEntity.func_110143_aJ() / Math.max(this.theEntity.func_110138_aP(), 0.001F);
         lowHealthPriority = 1.0F - health / 0.4F;
         lowHealthPriority = MathHelper.func_76131_a(lowHealthPriority, 0.0F, 1.0F);
         attackReadiness -= lowHealthPriority * 0.33F;
         this.world.func_217381_Z().func_76320_a("getNearbyFoesToFriendsRatio");
         float foesFriendsRatio = this.getNearbyFoesToFriendsRatio();
         this.world.func_217381_Z().func_76319_b();
         if (foesFriendsRatio > 1.0F) {
            attackReadiness -= Math.min(foesFriendsRatio, 2.0F) * 0.33F;
         } else {
            attackReadiness += 1.0F / foesFriendsRatio * 0.25F;
         }

         if (target.func_184585_cz()) {
            attackReadiness -= 0.3F;
         }

         if (attackReadiness >= 0.9F) {
            return NPCMeleeAttackGoal.MeleeMode.AGGRESSIVE;
         } else if (attackReadiness <= 0.1F) {
            return NPCMeleeAttackGoal.MeleeMode.DEFENSIVE;
         } else if (this.meleeMode != null && this.timeSinceDifferentMeleeMode < 40) {
            return this.meleeMode;
         } else {
            return this.rand.nextFloat() < attackReadiness ? NPCMeleeAttackGoal.MeleeMode.AGGRESSIVE : NPCMeleeAttackGoal.MeleeMode.DEFENSIVE;
         }
      } else {
         return NPCMeleeAttackGoal.MeleeMode.AGGRESSIVE;
      }
   }

   private Optional getShieldingHand() {
      return Stream.of(Hand.values()).filter((hand) -> {
         return this.theEntity.func_184586_b(hand).isShield(this.theEntity);
      }).findFirst();
   }

   private boolean canUseShield() {
      return this.getShieldingHand().isPresent() && !this.theEntity.getNPCCombatUpdater().isShieldDisabled();
   }

   private float getNearbyFoesToFriendsRatio() {
      long gameTime = this.world.func_82737_E();
      if (gameTime - this.lastFriendsToFoesCacheTime > 10L) {
         Set friends = new HashSet();
         Set foes = new HashSet();
         friends.add(this.theEntity);
         foes.add(this.theEntity.func_70638_az());
         double nearbyRange = 8.0D;
         AxisAlignedBB checkBox = this.theEntity.func_174813_aQ().func_186662_g(nearbyRange);
         friends.addAll(this.theEntity.field_70170_p.func_225316_b(LivingEntity.class, checkBox, NPCPredicates.selectFriends(this.theEntity)));
         foes.addAll(this.theEntity.field_70170_p.func_225316_b(LivingEntity.class, checkBox, NPCPredicates.selectFoes(this.theEntity)));
         friends.removeAll(foes);
         this.cachedFriendsToFoesRatio = (float)foes.size() / (float)friends.size();
         this.lastFriendsToFoesCacheTime = gameTime;
      }

      return this.cachedFriendsToFoesRatio;
   }

   public void func_75246_d() {
      if (this.meleeMode == NPCMeleeAttackGoal.MeleeMode.DEFENSIVE && !this.canUseShield()) {
         this.updateMeleeMode(NPCMeleeAttackGoal.MeleeMode.AGGRESSIVE);
      }

      if (this.theEntity.func_184585_cz()) {
         ++this.ongoingShieldingTime;
      } else {
         this.ongoingShieldingTime = 0;
      }

      this.timeUntilCanShield = Math.max(this.timeUntilCanShield - 1, 0);
      ++this.timeSinceDifferentMeleeMode;
      if (this.decideMeleeModeTimer > 0) {
         --this.decideMeleeModeTimer;
      } else {
         this.updateMeleeMode(this.decideMeleeMode());
      }

      this.baseMeleeTick();
      this.updateHaltIfDefendingClose();
   }

   private void baseMeleeTick() {
      LivingEntity target = this.theEntity.func_70638_az();
      this.theEntity.func_70671_ap().func_75651_a(target, 30.0F, 30.0F);
      double dSq = this.theEntity.func_70092_e(target.func_226277_ct_(), target.func_226278_cu_(), target.func_226281_cx_());
      this.rePathDelay = Math.max(this.rePathDelay - 1, 0);
      if ((this.longMemory || this.theEntity.func_70635_at().func_75522_a(target)) && this.rePathDelay <= 0) {
         this.rePathDelay = 4 + this.rand.nextInt(7);
         if (dSq > 1024.0D) {
            this.rePathDelay += 10;
         } else if (dSq > 256.0D) {
            this.rePathDelay += 5;
         }

         if (this.theEntity.func_70661_as().func_75500_f() && !this.isInRangeToAttack(target)) {
            this.theEntity.func_70661_as().func_75484_a(this.theEntity.func_70661_as().func_75494_a(target, 0), this.speedTowardsTarget);
         } else if (!this.theEntity.func_70661_as().func_75497_a(target, this.speedTowardsTarget)) {
            this.rePathDelay += 15;
         }
      }

      this.weaponAccountedSwingCooldown = Math.max(this.weaponAccountedSwingCooldown - 1, 0);
      this.checkAndPerformAttack(target, dSq);
   }

   private void updateHaltIfDefendingClose() {
      ModifiableAttributeInstance attrib = this.theEntity.func_110148_a(Attributes.field_233821_d_);
      attrib.func_188479_b(DEFENDING_CLOSE_HALT_ID);
      if (this.meleeMode == NPCMeleeAttackGoal.MeleeMode.DEFENSIVE) {
         double dSq = this.theEntity.func_70068_e(this.theEntity.func_70638_az());
         double defendingHaltRange = 2.0D;
         if (dSq < defendingHaltRange * defendingHaltRange) {
            attrib.func_233767_b_(DEFENDING_CLOSE_HALT);
         }
      }

   }

   protected void checkAndPerformAttack(LivingEntity target, double distToEnemySq) {
      if (this.meleeMode == NPCMeleeAttackGoal.MeleeMode.AGGRESSIVE && this.isInRangeToAttack(target, distToEnemySq) && this.weaponAccountedSwingCooldown <= 0) {
         this.resetSwingCooldown();
         this.theEntity.func_184609_a(Hand.MAIN_HAND);
         this.theEntity.func_70652_k(target);
      }

   }

   private boolean isInRangeToAttack(LivingEntity target, double distToEnemySq) {
      double reachSq = this.getAttackReachSq(target);
      return distToEnemySq <= reachSq;
   }

   protected double getAttackReachSq(LivingEntity target) {
      double fullReach = this.theEntity.func_233637_b_((Attribute)ForgeMod.REACH_DISTANCE.get());
      return fullReach * fullReach;
   }

   private boolean isInRangeToAttack(LivingEntity target) {
      return this.isInRangeToAttack(target, this.theEntity.func_70068_e(target));
   }

   protected void resetSwingCooldown() {
      this.weaponAccountedSwingCooldown = this.getWeaponMeleeCooldown();
   }

   private int getWeaponMeleeCooldown() {
      return this.theEntity.getAttackCooldownTicks();
   }

   public void func_75251_c() {
      LivingEntity target = this.theEntity.func_70638_az();
      if (!EntityPredicates.field_188444_d.test(target)) {
         this.theEntity.func_70624_b((LivingEntity)null);
      }

      this.theEntity.func_213395_q(false);
      this.theEntity.func_70661_as().func_75499_g();
      this.updateMeleeMode((NPCMeleeAttackGoal.MeleeMode)null);
      this.decideMeleeModeTimer = 0;
      this.updateHaltIfDefendingClose();
      this.ongoingShieldingTime = 0;
      this.timeUntilCanShield = 0;
   }

   static {
      DEFENDING_CLOSE_HALT = new AttributeModifier(DEFENDING_CLOSE_HALT_ID, "Halt when defending close", -1.0D, Operation.MULTIPLY_TOTAL);
   }

   public static enum MeleeMode {
      AGGRESSIVE,
      DEFENSIVE;
   }
}
