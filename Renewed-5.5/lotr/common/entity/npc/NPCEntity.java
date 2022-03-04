package lotr.common.entity.npc;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;
import lotr.common.LOTRLog;
import lotr.common.LOTRMod;
import lotr.common.config.LOTRConfig;
import lotr.common.data.DataUtil;
import lotr.common.data.LOTRLevelData;
import lotr.common.entity.CanHaveShieldDisabled;
import lotr.common.entity.LOTREntityDataSerializers;
import lotr.common.entity.npc.ai.AttackGoalsHolder;
import lotr.common.entity.npc.ai.AttackMode;
import lotr.common.entity.npc.ai.NPCCombatUpdater;
import lotr.common.entity.npc.ai.NPCTalkAnimations;
import lotr.common.entity.npc.ai.NPCTargetSelector;
import lotr.common.entity.npc.ai.goal.NPCHurtByTargetGoal;
import lotr.common.entity.npc.ai.goal.NPCNearestAttackableTargetGoal;
import lotr.common.entity.npc.ai.goal.StargazingGoal;
import lotr.common.entity.npc.ai.goal.WatchSunriseSunsetGoal;
import lotr.common.entity.npc.data.NPCEntitySettingsManager;
import lotr.common.entity.npc.data.NPCGenderProvider;
import lotr.common.entity.npc.data.NPCPersonalInfo;
import lotr.common.entity.npc.data.name.NPCNameGenerator;
import lotr.common.entity.npc.data.name.NPCNameGenerators;
import lotr.common.entity.npc.inv.NPCItemsInventory;
import lotr.common.fac.AlignmentPredicates;
import lotr.common.fac.Faction;
import lotr.common.fac.FactionPointer;
import lotr.common.init.LOTRAttributes;
import lotr.common.init.LOTRBiomes;
import lotr.common.item.ItemOwnership;
import lotr.common.item.PouchItem;
import lotr.common.speech.NPCSpeechSender;
import lotr.common.speech.SpecialSpeechbanks;
import lotr.common.stat.LOTRStats;
import lotr.common.util.CalendarUtil;
import lotr.common.util.LOTRUtil;
import lotr.common.world.spawning.NPCSpawnSettingsManager;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.AttributeModifierMap.MutableAttribute;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ForgeMod;

public abstract class NPCEntity extends CreatureEntity implements IRangedAttackMob, CanHaveShieldDisabled {
   protected final NPCPersonalInfo personalInfo = new NPCPersonalInfo(this);
   protected final NPCItemsInventory npcItemsInv;
   private final NPCCombatUpdater combatUpdater;
   private final AttackGoalsHolder attackGoalsHolder;
   private int attackGoalIndex = -1;
   private boolean addedTargetingGoals = false;
   private boolean isTargetSeeker = false;
   private UUID prevAttackTargetUuid;
   private static final DataParameter FACTION_OVERRIDE;
   private boolean loggedMissingFactionOverride = false;
   private int speechCooldown;
   private LivingEntity talkingTo;
   private int talkingToTime;
   private float talkingToInitialDistance;
   private final NPCTalkAnimations talkAnimations;
   private Optional speechbankOverride = Optional.empty();
   protected boolean spawnRequiresDarkness = false;
   protected boolean spawnRequiresSurfaceBlock = false;
   public boolean spawnRidingHorse = false;
   private List capturedLootForEnpouching = null;

   protected NPCEntity(EntityType type, World w) {
      super(type, w);
      this.setupNPCInfo();
      this.recalculateReachDistance();
      this.attackGoalsHolder = new AttackGoalsHolder(this);
      this.addNPCAI();
      this.npcItemsInv = new NPCItemsInventory(this);
      this.combatUpdater = new NPCCombatUpdater(this);
      this.talkAnimations = new NPCTalkAnimations(this);
   }

   private void setupNPCInfo() {
      if (!this.field_70170_p.field_72995_K) {
         this.personalInfo.setMale(this.getGenderProvider().isMale(this.field_70146_Z));
         this.personalInfo.setName(this.getNameGenerator().generateName(this.field_70146_Z, this.personalInfo.isMale()));
         this.personalInfo.assumeRandomPersonalityTraits(this.field_70146_Z);
      }

   }

   public final NPCPersonalInfo getPersonalInfo() {
      return this.personalInfo;
   }

   protected NPCGenderProvider getGenderProvider() {
      return NPCGenderProvider.MALE_OR_FEMALE;
   }

   protected NPCNameGenerator getNameGenerator() {
      return NPCNameGenerators.NAMELESS_THING;
   }

   protected void func_70088_a() {
      super.func_70088_a();
      this.field_70180_af.func_187214_a(FACTION_OVERRIDE, Optional.empty());
   }

   private Optional getFactionOverride() {
      return (Optional)this.field_70180_af.func_187225_a(FACTION_OVERRIDE);
   }

   private void setFactionOverride(Optional factionOverride) {
      this.field_70180_af.func_187227_b(FACTION_OVERRIDE, factionOverride);
   }

   protected static MutableAttribute registerBaseNPCAttributes() {
      return MobEntity.func_233666_p_().func_233815_a_(Attributes.field_233823_f_, 1.0D).func_233814_a_(Attributes.field_233825_h_).func_233815_a_((Attribute)ForgeMod.REACH_DISTANCE.get(), 0.0D).func_233814_a_((Attribute)LOTRAttributes.NPC_RANGED_INACCURACY.get()).func_233814_a_((Attribute)LOTRAttributes.NPC_MOUNT_ATTACK_SPEED.get()).func_233814_a_((Attribute)LOTRAttributes.NPC_CONVERSATION_RANGE.get());
   }

   public void func_213323_x_() {
      super.func_213323_x_();
      this.recalculateReachDistance();
   }

   private void recalculateReachDistance() {
      this.func_110148_a((Attribute)ForgeMod.REACH_DISTANCE.get()).func_111128_a(this.calculateDefaultNPCReach());
   }

   protected double calculateDefaultNPCReach() {
      return (double)(0.5F * this.func_213311_cf() + 1.5F);
   }

   protected void addNPCAI() {
      ((GroundPathNavigator)this.func_70661_as()).func_179688_b(true);
      this.func_70661_as().func_212239_d(true);
      this.func_184644_a(PathNodeType.DANGER_FIRE, 16.0F);
      this.func_184644_a(PathNodeType.DAMAGE_FIRE, -1.0F);
      this.initialiseAttackGoals(this.attackGoalsHolder);
      this.addNPCTargetingAI();
   }

   protected void initialiseAttackGoals(AttackGoalsHolder holder) {
   }

   public final AttackGoalsHolder getAttackGoalsHolder() {
      return this.attackGoalsHolder;
   }

   protected void addAttackGoal(int i) {
      this.attackGoalIndex = i;
      this.field_70714_bg.func_75776_a(i, this.attackGoalsHolder.getInitialAttackGoal());
   }

   public final int getAttackGoalIndex() {
      return this.attackGoalIndex;
   }

   protected void addNPCTargetingAI() {
   }

   protected final int addAggressiveTargetingGoals() {
      return this.addTargetingGoals(true);
   }

   protected final int addNonAggressiveTargetingGoals() {
      return this.addTargetingGoals(false);
   }

   private int addTargetingGoals(boolean seekTargets) {
      if (this.addedTargetingGoals) {
         throw new IllegalStateException("Mod development error - NPC addTargetingGoals can only be called once!");
      } else {
         this.addedTargetingGoals = true;
         int i = 1;
         int i = i + 1;
         this.field_70715_bh.func_75776_a(i, new NPCHurtByTargetGoal(this));
         if (seekTargets) {
            this.field_70715_bh.func_75776_a(i++, new NPCNearestAttackableTargetGoal(this, PlayerEntity.class, true));
            this.field_70715_bh.func_75776_a(i++, new NPCNearestAttackableTargetGoal(this, MobEntity.class, true, new NPCTargetSelector(this)));
         }

         this.isTargetSeeker = seekTargets;
         return i;
      }
   }

   public void onAttackModeChange(AttackMode newMode, boolean newRiding) {
   }

   public NPCItemsInventory getNPCItemsInv() {
      return this.npcItemsInv;
   }

   public NPCCombatUpdater getNPCCombatUpdater() {
      return this.combatUpdater;
   }

   public NPCTalkAnimations getTalkAnimations() {
      return this.talkAnimations;
   }

   protected final ITextComponent func_225513_by_() {
      ITextComponent typeName = this.getEntityTypeName();
      ITextComponent npcName = this.getNPCName();
      if (CalendarUtil.isAprilFools()) {
         npcName = new StringTextComponent("Gandalf");
      }

      return this.formatNPCName((ITextComponent)npcName, typeName);
   }

   protected ITextComponent formatNPCName(ITextComponent npcName, ITextComponent typeName) {
      return this.formatGenericNPCName(npcName, typeName);
   }

   protected final ITextComponent formatGenericNPCName(ITextComponent npcName, ITextComponent typeName) {
      return (ITextComponent)(npcName != null && !npcName.func_150261_e().equals(typeName.func_150261_e()) ? new TranslationTextComponent("entityname.lotr.generic", new Object[]{npcName, typeName}) : typeName);
   }

   protected final ITextComponent getEntityTypeName() {
      return super.func_225513_by_();
   }

   protected final ITextComponent getNPCName() {
      return (ITextComponent)Optional.ofNullable(this.personalInfo.getName()).map(StringTextComponent::new).orElse((Object)null);
   }

   public final Faction getFaction() {
      if (this.getFactionOverride().isPresent()) {
         FactionPointer overridePointer = (FactionPointer)this.getFactionOverride().get();
         Optional override = overridePointer.resolveFaction((IWorldReader)this.field_70170_p);
         if (override.isPresent()) {
            return (Faction)override.get();
         }

         if (!this.loggedMissingFactionOverride) {
            LOTRLog.debug("NPC '%s' with a factionOverride '%s' could not resolve faction reference", this.func_200200_C_().getString(), overridePointer.getName());
            this.loggedMissingFactionOverride = true;
         }
      }

      return NPCEntitySettingsManager.getEntityTypeFaction(this);
   }

   public boolean generatesLocalAreaOfInfluence() {
      return true;
   }

   public boolean canBeFreelyTargetedBy(LivingEntity attacker) {
      return true;
   }

   public boolean isCivilianNPC() {
      return !this.isTargetSeeker;
   }

   public ILivingEntityData func_213386_a(IServerWorld sw, DifficultyInstance diff, SpawnReason reason, ILivingEntityData spawnData, CompoundNBT dataTag) {
      spawnData = super.func_213386_a(sw, diff, reason, spawnData, dataTag);
      if (reason == SpawnReason.SPAWN_EGG) {
         this.func_110163_bv();
      }

      return spawnData;
   }

   protected int getNPCSpeakToInterval() {
      return 40;
   }

   protected final boolean canSpeakToNPC() {
      if (this.func_70089_S() && !LOTRMod.PROXY.getSidedAttackTarget(this).isPresent()) {
         if (!this.field_70170_p.field_72995_K) {
            return this.speechCooldown >= this.getNPCSpeakToInterval();
         } else {
            return true;
         }
      } else {
         return false;
      }
   }

   protected final void markNPCSpoken() {
      this.speechCooldown = 0;
   }

   public final void func_70624_b(LivingEntity target) {
      boolean speak = target != null && this.func_70635_at().func_75522_a(target) && this.field_70146_Z.nextInt(3) == 0;
      this.npcSetAttackTarget(target, speak);
   }

   protected void npcSetAttackTarget(LivingEntity target, boolean speak) {
      LivingEntity prevTarget = this.func_70638_az();
      super.func_70624_b(target);
      if (target != null && !target.func_110124_au().equals(this.prevAttackTargetUuid)) {
         this.prevAttackTargetUuid = target.func_110124_au();
         if (!this.field_70170_p.field_72995_K) {
            if (this.getAttackSound() != null) {
               this.func_184185_a(this.getAttackSound(), this.func_70599_aP(), this.func_70647_i());
            }

            if (target instanceof PlayerEntity && speak) {
               PlayerEntity player = (PlayerEntity)target;
               double range = 16.0D;
               List nearbyNPCsAttackingSamePlayer = this.field_70170_p.func_175674_a(this, this.func_174813_aQ().func_186662_g(16.0D), (e) -> {
                  if (!(e instanceof NPCEntity)) {
                     return false;
                  } else {
                     NPCEntity otherNPC = (NPCEntity)e;
                     return otherNPC.func_70089_S() && otherNPC.func_70638_az() == player;
                  }
               });
               if (nearbyNPCsAttackingSamePlayer.size() <= 5) {
                  this.sendNormalSpeechTo((ServerPlayerEntity)player);
               }
            }
         }
      }

   }

   public LivingEntity getTalkingToEntity() {
      return this.talkingTo;
   }

   public boolean isTalking() {
      return this.talkingTo != null;
   }

   public float getTalkingToInitialDistance() {
      return this.talkingToInitialDistance;
   }

   public void setTalkingToEntity(LivingEntity e, int time) {
      this.talkingTo = e;
      this.talkingToTime = time;
      this.talkingToInitialDistance = this.talkingTo == null ? 0.0F : this.func_70032_d(this.talkingTo);
   }

   public void clearTalkingToEntity() {
      this.setTalkingToEntity((LivingEntity)null, 0);
   }

   public void onPlayerStartTrackingNPC(ServerPlayerEntity player) {
      this.personalInfo.sendData(player);
      this.npcItemsInv.sendIsEating(player);
      this.combatUpdater.sendCombatStance(player);
      this.talkAnimations.sendData(player);
   }

   public void func_70071_h_() {
      super.func_70071_h_();
      this.combatUpdater.updateCombat();
      this.personalInfo.tick();
      this.func_82168_bl();
      ++this.speechCooldown;
      if (this.talkingToTime > 0) {
         --this.talkingToTime;
         if (this.talkingToTime <= 0) {
            this.clearTalkingToEntity();
         }
      }

      this.talkAnimations.updateAnimation();
      this.pathBackToHome();
   }

   private void pathBackToHome() {
      if (!this.field_70170_p.field_72995_K && this.hasHomePos() && !this.func_213383_dH()) {
         BlockPos homePos = this.func_213384_dI();
         int homeRange = (int)this.func_213391_dJ();
         double maxDist = (double)homeRange + 128.0D;
         double distToHomeSq = this.func_195048_a(Vector3d.func_237492_c_(homePos));
         if (distToHomeSq > maxDist * maxDist) {
            this.clearHomePos();
         } else if (this.func_70638_az() == null && this.func_70661_as().func_75500_f()) {
            this.clearHomePos();
            boolean goDirectlyHome = false;
            if (this.field_70170_p.func_175667_e(homePos)) {
            }

            double homeSpeed = 1.3D;
            if (goDirectlyHome) {
               this.func_70661_as().func_75492_a((double)homePos.func_177958_n() + 0.5D, (double)homePos.func_177956_o() + 0.5D, (double)homePos.func_177952_p() + 0.5D, homeSpeed);
            } else {
               Vector3d path = null;

               for(int l = 0; l < 16 && path == null; ++l) {
                  path = RandomPositionGenerator.func_75464_a(this, 8, 7, Vector3d.func_237492_c_(homePos));
               }

               if (path != null) {
                  this.func_70661_as().func_75492_a(path.field_72450_a, path.field_72448_b, path.field_72449_c, homeSpeed);
               }
            }

            this.func_213390_a(homePos, homeRange);
         }
      }

   }

   public MovementController func_70605_aq() {
      return this.isRidingOtherNPCMount() ? this.field_70765_h : super.func_70605_aq();
   }

   public PathNavigator func_70661_as() {
      return this.isRidingOtherNPCMount() ? this.field_70699_by : super.func_70661_as();
   }

   private boolean isRidingOtherNPCMount() {
      return this.func_184218_aH() && this.func_184187_bx() instanceof NPCEntity;
   }

   public void refreshCurrentAttackMode() {
      this.combatUpdater.refreshCurrentAttackMode();
   }

   public boolean func_70652_k(Entity target) {
      if (super.func_70652_k(target)) {
         ItemStack weapon = this.func_184614_ca();
         if (!weapon.func_190926_b() && target instanceof LivingEntity) {
            int weaponItemDamage = weapon.func_77952_i();
            weapon.func_77973_b().func_77644_a(weapon, (LivingEntity)target, this);
            weapon.func_196085_b(weaponItemDamage);
         }

         return true;
      } else {
         return false;
      }
   }

   public final int getAttackCooldownTicks() {
      return Math.max(MathHelper.func_76123_f(this.getAttackCooldownTicksF()), 1);
   }

   protected float getAttackCooldownTicksF() {
      return (float)(1.0D / this.func_233637_b_(Attributes.field_233825_h_) * 20.0D);
   }

   public boolean func_70097_a(DamageSource source, float amount) {
      boolean willBeBlockedByShield = amount > 0.0F && this.func_184583_d(source);
      Vector3d preMotion = this.func_213322_ci();
      boolean flag = super.func_70097_a(source, amount);
      if (flag) {
         this.combatUpdater.onAttacked();
      }

      if (willBeBlockedByShield) {
         this.func_213317_d(preMotion);
      }

      return flag;
   }

   protected void func_190629_c(LivingEntity attacker) {
      super.func_190629_c(attacker);
      if (attacker.func_184614_ca().canDisableShield(this.field_184627_bm, this, attacker)) {
         this.disableShield(true);
      }

   }

   protected void func_184590_k(float damage) {
      super.func_184590_k(damage);
      this.func_184185_a(SoundEvents.field_187767_eL, 1.0F, 0.8F + this.field_70146_Z.nextFloat() * 0.4F);
   }

   public void disableShield(boolean flag) {
      float f = 0.25F + (float)EnchantmentHelper.func_185293_e(this) * 0.05F;
      if (flag) {
         f += 0.75F;
      }

      if (this.field_70146_Z.nextFloat() < f) {
         this.combatUpdater.temporarilyDisableShield();
         this.func_184602_cy();
         this.func_184185_a(SoundEvents.field_187769_eM, 0.8F, 0.8F + this.field_70170_p.field_73012_v.nextFloat() * 0.4F);
      }

   }

   public void func_82196_d(LivingEntity target, float charge) {
      this.npcArrowAttack(target, charge);
   }

   protected final void npcArrowAttack(LivingEntity target, float charge) {
      ItemStack heldItem = this.func_184586_b(ProjectileHelper.getWeaponHoldingHand(this, (item) -> {
         return item instanceof BowItem;
      }));
      ItemStack ammoItem = this.func_213356_f(heldItem);
      AbstractArrowEntity arrow = ProjectileHelper.func_221272_a(this, ammoItem, charge);
      if (heldItem.func_77973_b() instanceof BowItem) {
         arrow = ((BowItem)heldItem.func_77973_b()).customArrow(arrow);
      }

      double dx = target.func_226277_ct_() - this.func_226277_ct_();
      double dy = target.func_226283_e_(0.3333333333333333D) - arrow.func_226278_cu_();
      double dz = target.func_226281_cx_() - this.func_226281_cx_();
      double dxzSq = (double)MathHelper.func_76133_a(dx * dx + dz * dz);
      arrow.func_70186_c(dx, dy + dxzSq * 0.20000000298023224D, dz, 1.6F, (float)this.func_233637_b_((Attribute)LOTRAttributes.NPC_RANGED_INACCURACY.get()));
      this.func_184185_a(SoundEvents.field_187866_fi, 1.0F, 1.0F / (this.field_70146_Z.nextFloat() * 0.4F + 0.8F));
      this.field_70170_p.func_217376_c(arrow);
   }

   public void func_241847_a(ServerWorld sWorld, LivingEntity killedEntity) {
      super.func_241847_a(sWorld, killedEntity);
      if (this.getKillSound() != null) {
         this.func_184185_a(this.getKillSound(), this.func_70599_aP(), this.func_70647_i());
      }

   }

   public final double getSpawnCountWeight() {
      return this.func_104002_bU() ? 0.0D : 1.0D;
   }

   public boolean func_213380_a(IWorld iworld, SpawnReason reason) {
      if (super.func_213380_a(iworld, reason)) {
         if (reason == SpawnReason.NATURAL) {
            if (this.spawnRequiresDarkness && !this.isValidLightLevelForDarkSpawn(iworld)) {
               return false;
            }

            if (this.spawnRequiresSurfaceBlock && !this.checkSpawningOnSurfaceBlock(iworld, reason)) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   private boolean checkSpawningOnSurfaceBlock(IWorld iworld, SpawnReason reason) {
      if (reason == SpawnReason.NATURAL) {
         BlockPos pos = this.func_233580_cy_();
         if (pos.func_177956_o() >= iworld.func_181545_F()) {
            Biome biome = iworld.func_226691_t_(pos);
            BlockState belowState = iworld.func_180495_p(pos.func_177977_b());
            return LOTRBiomes.getWrapperFor(biome, iworld).isSurfaceBlockForNPCSpawn(belowState);
         } else {
            return false;
         }
      } else {
         return true;
      }
   }

   private boolean isValidLightLevelForDarkSpawn(IWorld iworld) {
      BlockPos pos = this.func_233580_cy_();
      if (this.spawnRequiresDarkness) {
         Biome biome = iworld.func_226691_t_(pos);
         if (NPCSpawnSettingsManager.getSpawnsForBiome(biome, iworld).allowsDarknessSpawnsInDaytime()) {
            return true;
         }
      }

      World thisWorld = this.func_130014_f_();
      if (thisWorld instanceof ServerWorld) {
         return MonsterEntity.func_223323_a((ServerWorld)thisWorld, pos, this.field_70146_Z);
      } else {
         LOTRLog.warn("Something is trying to check NPC spawning light levels on the client side! This should never happen!");
         return false;
      }
   }

   public float func_205022_a(BlockPos pos, IWorldReader reader) {
      if (this.spawnRequiresDarkness) {
         Biome biome = this.field_70170_p.func_226691_t_(pos);
         return NPCSpawnSettingsManager.getSpawnsForBiome(biome, this.func_130014_f_()).allowsDarknessSpawnsInDaytime() ? 1.0F : 0.5F - reader.func_205052_D(pos);
      } else {
         return 0.0F;
      }
   }

   public ActionResultType func_230254_b_(PlayerEntity player, Hand hand) {
      if (this.canSpeakToNPC()) {
         if (this.field_70170_p.field_72995_K) {
            return ActionResultType.SUCCESS;
         }

         if (this.sendNormalSpeechTo((ServerPlayerEntity)player)) {
            int talkingToTime = LOTRUtil.secondsToTicks(LOTRConfig.COMMON.getRandomNPCTalkToPlayerDuration(this.field_70146_Z));
            this.setTalkingToEntity(player, talkingToTime);
            player.func_195066_a(LOTRStats.TALK_TO_NPC);
            return ActionResultType.SUCCESS;
         }
      }

      return super.func_230254_b_(player, hand);
   }

   public boolean sendNormalSpeechTo(ServerPlayerEntity player) {
      Optional optSpeechbank = this.getSpeechbank();
      if (optSpeechbank.isPresent()) {
         this.sendSpeechTo(player, (ResourceLocation)optSpeechbank.get());
         return true;
      } else {
         return false;
      }
   }

   private Optional getSpeechbank() {
      Optional specialSpeech = SpecialSpeechbanks.getSpecialSpeechbank(this.field_70146_Z);
      if (specialSpeech.isPresent()) {
         return specialSpeech;
      } else {
         return this.speechbankOverride.isPresent() ? this.speechbankOverride : NPCEntitySettingsManager.getEntityTypeSettings(this).getSpeechbank();
      }
   }

   public void sendSpeechTo(ServerPlayerEntity player, ResourceLocation speechbank) {
      NPCSpeechSender.sendMessageInContext(player, this, speechbank);
      this.markNPCSpoken();
   }

   public boolean isFriendlyAndAligned(PlayerEntity player) {
      return this.isNotFighting(player) && LOTRLevelData.getSidedData(player).getAlignmentData().hasAlignment(this.getFaction(), AlignmentPredicates.POSITIVE_OR_ZERO);
   }

   public final boolean isNotFighting(PlayerEntity player) {
      return this.func_70638_az() != player && this.field_70717_bb != player;
   }

   public boolean canTrade(PlayerEntity player) {
      return this.isFriendlyAndAligned(player);
   }

   public boolean isStargazing() {
      return this.field_70714_bg.func_220888_c().anyMatch((prioritizedGoal) -> {
         return prioritizedGoal.func_220772_j() instanceof StargazingGoal;
      });
   }

   public boolean isWatchingSunriseOrSunset() {
      return this.field_70714_bg.func_220888_c().anyMatch((prioritizedGoal) -> {
         return prioritizedGoal.func_220772_j() instanceof WatchSunriseSunsetGoal;
      });
   }

   public boolean isFleeing() {
      return this.field_70714_bg.func_220888_c().anyMatch((prioritizedGoal) -> {
         return prioritizedGoal.func_220772_j() instanceof AvoidEntityGoal;
      });
   }

   public void func_213281_b(CompoundNBT nbt) {
      super.func_213281_b(nbt);
      this.personalInfo.write(nbt);
      this.npcItemsInv.writeToEntityNBT(nbt);
      this.combatUpdater.write(nbt);
      DataUtil.writeOptionalFactionPointerToNBT(nbt, "FactionOverride", this.getFactionOverride());
      DataUtil.writeOptionalToNBT(nbt, "SpeechbankOverride", this.speechbankOverride, DataUtil::putResourceLocation);
      nbt.func_74768_a("NPCHomeX", this.func_213384_dI().func_177958_n());
      nbt.func_74768_a("NPCHomeY", this.func_213384_dI().func_177956_o());
      nbt.func_74768_a("NPCHomeZ", this.func_213384_dI().func_177952_p());
      nbt.func_74768_a("NPCHomeRadius", (int)this.func_213391_dJ());
   }

   public void func_70037_a(CompoundNBT nbt) {
      super.func_70037_a(nbt);
      this.personalInfo.read(nbt);
      this.npcItemsInv.readFromEntityNBT(nbt);
      this.combatUpdater.read(nbt);
      this.setFactionOverride(DataUtil.readOptionalFactionPointerFromNBT(nbt, "FactionOverride"));
      this.speechbankOverride = DataUtil.readOptionalFromNBT(nbt, "SpeechbankOverride", DataUtil::getResourceLocation);
      if (nbt.func_74764_b("NPCHomeRadius")) {
         int x = nbt.func_74762_e("NPCHomeX");
         int y = nbt.func_74762_e("NPCHomeY");
         int z = nbt.func_74762_e("NPCHomeZ");
         int r = nbt.func_74762_e("NPCHomeRadius");
         this.func_213390_a(new BlockPos(x, y, z), r);
      }

   }

   protected void func_213354_a(DamageSource source, boolean attackedRecently) {
      this.capturedLootForEnpouching = new ArrayList();
      super.func_213354_a(source, attackedRecently);
      List enpouchedLoot = this.sortCapturedLootIntoPouches(this.capturedLootForEnpouching);
      this.capturedLootForEnpouching = null;
      enpouchedLoot.forEach((item) -> {
         this.npcDropItem(item, 0.0F, false, false);
      });
   }

   private List sortCapturedLootIntoPouches(List capturedLoot) {
      List pouches = new ArrayList();
      List otherLoot = new ArrayList();
      Iterator var4 = capturedLoot.iterator();

      while(var4.hasNext()) {
         ItemStack stack = (ItemStack)var4.next();
         if (stack.func_77973_b() instanceof PouchItem) {
            pouches.add(stack);
         } else {
            otherLoot.add(stack);
         }
      }

      List notPouchedLoot = new ArrayList();
      Iterator var11 = otherLoot.iterator();

      while(var11.hasNext()) {
         ItemStack stack = (ItemStack)var11.next();
         Iterator var7 = pouches.iterator();

         while(var7.hasNext()) {
            ItemStack pouch = (ItemStack)var7.next();
            PouchItem.AddItemResult result = PouchItem.tryAddItemToPouch(pouch, stack, false);
            if (result == PouchItem.AddItemResult.FULLY_ADDED) {
               stack = ItemStack.field_190927_a;
               break;
            }
         }

         if (!stack.func_190926_b()) {
            notPouchedLoot.add(stack);
         }
      }

      pouches.addAll(notPouchedLoot);
      return pouches;
   }

   public final ItemEntity func_70099_a(ItemStack item, float offsetY) {
      return this.npcDropItem(item, offsetY, true, true);
   }

   protected final ItemEntity npcDropItem(ItemStack item, float offsetY, boolean captureIfEnpouching, boolean applyOwnership) {
      if (applyOwnership && item != null && !item.func_190926_b() && item.func_77976_d() == 1) {
         ItemOwnership.addPreviousOwner(item, this.func_200200_C_());
      }

      if (captureIfEnpouching && this.capturedLootForEnpouching != null && item != null) {
         this.capturedLootForEnpouching.add(item);
         return null;
      } else {
         return super.func_70099_a(item, offsetY);
      }
   }

   protected void func_213333_a(DamageSource source, int looting, boolean playerHit) {
      int equipmentCount = (int)Stream.of(EquipmentSlotType.values()).filter((slotx) -> {
         return !this.func_184582_a(slotx).func_190926_b();
      }).count();
      if (equipmentCount > 0) {
         EquipmentSlotType[] var5 = EquipmentSlotType.values();
         int var6 = var5.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            EquipmentSlotType slot = var5[var7];
            ItemStack equipmentDrop = this.func_184582_a(slot);
            if (!equipmentDrop.func_190926_b()) {
               float dropChance = this.func_205712_c(slot);
               boolean dropUndamaged = dropChance > 1.0F;
               boolean dropGuaranteedVanilla = dropChance >= 1.0F;
               if ((dropGuaranteedVanilla || playerHit) && !EnchantmentHelper.func_190939_c(equipmentDrop)) {
                  boolean doDrop = true;
                  if (!dropGuaranteedVanilla) {
                     int chance = 20 * equipmentCount - looting * 4 * equipmentCount;
                     chance = Math.max(chance, 1);
                     if (this.field_70146_Z.nextInt(chance) != 0) {
                        doDrop = false;
                     }
                  }

                  if (doDrop) {
                     if (!dropUndamaged && equipmentDrop.func_77984_f()) {
                        float dropDamageF = MathHelper.func_151240_a(this.field_70146_Z, 0.5F, 0.75F);
                        if (this.field_70146_Z.nextInt(12) == 0) {
                           dropDamageF = MathHelper.func_151240_a(this.field_70146_Z, 0.0F, 0.5F);
                        }

                        int dropDamage = MathHelper.func_76141_d((float)equipmentDrop.func_77958_k() * dropDamageF);
                        equipmentDrop.func_196085_b(dropDamage);
                     }

                     this.func_199701_a_(equipmentDrop);
                     this.func_184201_a(slot, ItemStack.field_190927_a);
                  }
               }
            }
         }
      }

   }

   public int func_70627_aG() {
      return 200;
   }

   protected SoundEvent getAttackSound() {
      return null;
   }

   protected SoundEvent getKillSound() {
      return null;
   }

   public SoundEvent getNPCDrinkSound(ItemStack itemstack) {
      return this.func_213351_c(itemstack);
   }

   public final void clearHomePos() {
      this.func_213390_a(BlockPos.field_177992_a, -1);
   }

   public final boolean hasHomePos() {
      return this.func_213394_dL();
   }

   public boolean func_184652_a(PlayerEntity player) {
      return false;
   }

   public boolean func_70631_g_() {
      return this.personalInfo.isChild();
   }

   public final boolean isDrunk() {
      return this.personalInfo.isDrunk();
   }

   public float getDrunkenSpeechFactor() {
      return this.field_70146_Z.nextInt(3) == 0 ? MathHelper.func_151240_a(this.field_70146_Z, 0.0F, 0.2F) : 0.0F;
   }

   public boolean shouldRenderNPCHair() {
      return true;
   }

   public boolean shouldRenderNPCChest() {
      return this.personalInfo.isFemale() && !this.func_70631_g_() && this.func_184582_a(EquipmentSlotType.CHEST).func_190926_b();
   }

   public boolean useSmallArmsModel() {
      return false;
   }

   static {
      FACTION_OVERRIDE = EntityDataManager.func_187226_a(NPCEntity.class, LOTREntityDataSerializers.OPTIONAL_FACTION_POINTER);
   }
}
