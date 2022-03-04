package lotr.common.entity.npc.ai;

import java.util.UUID;
import lotr.common.entity.npc.NPCEntity;
import lotr.common.item.SpearItem;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.SPacketNPCState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.ForgeMod;

public class NPCCombatUpdater {
   private final NPCEntity theEntity;
   private boolean ridingMount;
   private AttackMode currentAttackMode;
   private boolean firstUpdatedAttackMode;
   private boolean refreshAttackModeNextTick;
   private AttackModeUpdater attackModeUpdater;
   private int attackModeUpdateCooldown;
   private static final int ATTACK_MODE_UPDATE_INTERVAL = 10;
   private int combatCooldown;
   private static final int IN_COMBAT_COOLDOWN = 40;
   private boolean combatStance;
   private boolean prevCombatStance;
   private int ticksSinceAttacked;
   private boolean wasShielding;
   private static final UUID SHIELDING_SLOWDOWN_ID = UUID.fromString("02dcfa3a-6713-4651-93e0-adaa8a55bf9c");
   private static final AttributeModifier SHIELDING_SLOWDOWN;
   private int shieldDisableCooldown;
   private static final int SHIELD_DISABLE_TIME = 100;
   private static final UUID MOUNTED_FOLLOW_RANGE_BOOST_ID;
   private static final AttributeModifier MOUNTED_FOLLOW_RANGE_BOOST;
   private static final double MOUNTED_MELEE_RANGE_BOOST = 1.5D;

   public NPCCombatUpdater(NPCEntity entity) {
      this.currentAttackMode = AttackMode.IDLE;
      this.firstUpdatedAttackMode = false;
      this.refreshAttackModeNextTick = false;
      this.attackModeUpdater = StandardAttackModeUpdaters.meleeOnly();
      this.attackModeUpdateCooldown = 0;
      this.ticksSinceAttacked = Integer.MAX_VALUE;
      this.wasShielding = false;
      this.theEntity = entity;
   }

   public void setRidingHorse(boolean flag) {
      this.ridingMount = flag;
      ModifiableAttributeInstance attrib = this.theEntity.func_110148_a(Attributes.field_233819_b_);
      attrib.func_233770_c_(MOUNTED_FOLLOW_RANGE_BOOST_ID);
      if (this.ridingMount) {
         attrib.func_233769_c_(MOUNTED_FOLLOW_RANGE_BOOST);
      }

   }

   public void refreshCurrentAttackMode() {
      this.refreshAttackModeNextTick = true;
   }

   private void onAttackModeChange(AttackMode newMode, boolean newRiding) {
      this.attackModeUpdater.onAttackModeChange(this.theEntity, newMode, newRiding);
   }

   public void setAttackModeUpdater(AttackModeUpdater updater) {
      this.attackModeUpdater = updater;
   }

   public boolean isCombatStance() {
      return this.combatStance;
   }

   private double getMeleeRange() {
      double d = this.theEntity.func_233637_b_((Attribute)ForgeMod.REACH_DISTANCE.get()) + 2.0D;
      if (this.ridingMount) {
         d *= 1.5D;
      }

      return d;
   }

   private double getMeleeRangeSq() {
      double d = this.getMeleeRange();
      return d * d;
   }

   private double getMaxCombatRange() {
      double d = this.theEntity.func_233637_b_(Attributes.field_233819_b_);
      return d * 0.95D;
   }

   public double getMaxCombatRangeSq() {
      double d = this.getMaxCombatRange();
      return d * d;
   }

   public void onAttacked() {
      this.ticksSinceAttacked = 0;
   }

   public int getTicksSinceAttacked() {
      return this.ticksSinceAttacked;
   }

   public boolean isShieldDisabled() {
      return this.shieldDisableCooldown > 0;
   }

   public void temporarilyDisableShield() {
      this.shieldDisableCooldown = 100;
   }

   public void updateCombat() {
      this.theEntity.field_70170_p.func_217381_Z().func_76320_a("NPCCombatUpdater#updateCombat");
      if (!this.theEntity.field_70170_p.field_72995_K) {
         this.checkAttackTarget();
         this.updateCombatCooldown();
         if (this.ticksSinceAttacked < Integer.MAX_VALUE) {
            ++this.ticksSinceAttacked;
         }

         if (this.theEntity.func_70089_S()) {
            this.updateAttackMode();
         }

         this.updateCombatStance();
         this.updateShielding();
      }

      this.theEntity.field_70170_p.func_217381_Z().func_76319_b();
   }

   private void checkAttackTarget() {
      LivingEntity target = this.theEntity.func_70638_az();
      if (target != null && (!target.func_70089_S() || target instanceof PlayerEntity && ((PlayerEntity)target).field_71075_bZ.field_75098_d)) {
         this.theEntity.func_70624_b((LivingEntity)null);
      }

   }

   private void updateCombatCooldown() {
      if (this.theEntity.func_70638_az() != null) {
         this.combatCooldown = 40;
      } else if (this.combatCooldown > 0) {
         --this.combatCooldown;
      }

   }

   private void updateAttackMode() {
      if (this.attackModeUpdateCooldown > 0) {
         --this.attackModeUpdateCooldown;
      }

      boolean changedMounted = this.checkForMountedChange();
      boolean changedAttackMode = this.checkForAttackModeChange();
      if (!this.firstUpdatedAttackMode) {
         this.firstUpdatedAttackMode = true;
         changedAttackMode = true;
      }

      if (this.refreshAttackModeNextTick) {
         this.refreshAttackModeNextTick = false;
         changedAttackMode = true;
      }

      if (changedAttackMode || changedMounted) {
         this.onAttackModeChange(this.currentAttackMode, this.ridingMount);
         if (changedAttackMode) {
            this.attackModeUpdateCooldown = 10;
         }
      }

   }

   private boolean checkForMountedChange() {
      Entity mount = this.theEntity.func_184187_bx();
      boolean isRidingMountNow = mount instanceof LivingEntity && mount.func_70089_S() && !(mount instanceof NPCEntity);
      if (this.ridingMount != isRidingMountNow) {
         this.setRidingHorse(isRidingMountNow);
         return true;
      } else {
         return false;
      }
   }

   private boolean checkForAttackModeChange() {
      if (!this.theEntity.func_70631_g_()) {
         LivingEntity target = this.theEntity.func_70638_az();
         if (target != null) {
            if (this.attackModeUpdateCooldown <= 0) {
               double dSq = this.theEntity.func_70068_e(target);
               if (dSq >= this.getMeleeRangeSq() && !this.isCarryingSpearWithBackup()) {
                  if (dSq < this.getMaxCombatRangeSq() && this.currentAttackMode != AttackMode.RANGED) {
                     this.currentAttackMode = AttackMode.RANGED;
                     return true;
                  }
               } else if (this.currentAttackMode != AttackMode.MELEE) {
                  this.currentAttackMode = AttackMode.MELEE;
                  return true;
               }
            }
         } else if (this.currentAttackMode != AttackMode.IDLE && this.combatCooldown <= 0) {
            this.currentAttackMode = AttackMode.IDLE;
            return true;
         }
      }

      return false;
   }

   private boolean isCarryingSpearWithBackup() {
      return this.theEntity.func_184614_ca().func_77973_b() instanceof SpearItem && !this.theEntity.getNPCItemsInv().getSpearBackup().func_190926_b();
   }

   private void updateCombatStance() {
      this.prevCombatStance = this.combatStance;
      this.combatStance = this.combatCooldown > 0;
      if (this.combatStance != this.prevCombatStance) {
         this.sendCombatStanceToAllWatchers();
      }

   }

   private void updateShielding() {
      boolean isShielding = this.theEntity.func_184585_cz();
      if (isShielding != this.wasShielding) {
         this.wasShielding = isShielding;
         ModifiableAttributeInstance attrib = this.theEntity.func_110148_a(Attributes.field_233821_d_);
         attrib.func_188479_b(SHIELDING_SLOWDOWN_ID);
         if (isShielding) {
            attrib.func_233767_b_(SHIELDING_SLOWDOWN);
         }
      }

      if (this.shieldDisableCooldown > 0) {
         --this.shieldDisableCooldown;
      }

   }

   public void write(CompoundNBT nbt) {
      nbt.func_74757_a("RidingHorse", this.ridingMount);
   }

   public void read(CompoundNBT nbt) {
      this.ridingMount = nbt.func_74767_n("RidingHorse");
   }

   public void sendCombatStance(ServerPlayerEntity player) {
      LOTRPacketHandler.sendTo(this.createCombatStancePacket(), player);
   }

   private void sendCombatStanceToAllWatchers() {
      LOTRPacketHandler.sendToAllTrackingEntity(this.createCombatStancePacket(), this.theEntity);
   }

   private SPacketNPCState createCombatStancePacket() {
      return new SPacketNPCState(this.theEntity, SPacketNPCState.Type.COMBAT_STANCE, this.combatStance);
   }

   public void receiveClientCombatStance(boolean state) {
      if (!this.theEntity.field_70170_p.field_72995_K) {
         throw new IllegalStateException("This method should only be called on the clientside");
      } else {
         this.combatStance = state;
      }
   }

   static {
      SHIELDING_SLOWDOWN = new AttributeModifier(SHIELDING_SLOWDOWN_ID, "Shielding slowdown", -0.3D, Operation.MULTIPLY_TOTAL);
      MOUNTED_FOLLOW_RANGE_BOOST_ID = UUID.fromString("72a017fc-767b-49fd-bc66-c91daa7b7b12");
      MOUNTED_FOLLOW_RANGE_BOOST = new AttributeModifier(MOUNTED_FOLLOW_RANGE_BOOST_ID, "Mounted follow range boost", 1.5D, Operation.MULTIPLY_BASE);
   }
}
