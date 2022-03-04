package lotr.common.entity.npc;

import lotr.common.config.LOTRConfig;
import lotr.common.entity.npc.ai.AttackGoalsHolder;
import lotr.common.entity.npc.ai.goal.FriendlyNPCConversationGoal;
import lotr.common.entity.npc.ai.goal.NPCDrinkGoal;
import lotr.common.entity.npc.ai.goal.NPCEatGoal;
import lotr.common.entity.npc.ai.goal.NPCMeleeAttackGoal;
import lotr.common.entity.npc.ai.goal.TalkToCurrentGoal;
import lotr.common.entity.npc.ai.goal.WatchSunriseSunsetGoal;
import lotr.common.entity.npc.data.NPCFoodPool;
import lotr.common.entity.npc.data.NPCFoodPools;
import lotr.common.entity.npc.data.NPCGenderProvider;
import lotr.common.entity.npc.data.name.NPCNameGenerator;
import lotr.common.entity.npc.data.name.NPCNameGenerators;
import lotr.common.init.LOTRAttributes;
import lotr.common.init.LOTRItems;
import lotr.common.init.LOTRSoundEvents;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
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
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class DwarfEntity extends NPCEntity {
   private static final SpawnEquipmentTable WEAPONS;

   public DwarfEntity(EntityType type, World w) {
      super(type, w);
   }

   protected NPCGenderProvider getGenderProvider() {
      return NPCGenderProvider.DWARF;
   }

   protected NPCNameGenerator getNameGenerator() {
      return NPCNameGenerators.DWARF;
   }

   public static MutableAttribute regAttrs() {
      return NPCEntity.registerBaseNPCAttributes().func_233815_a_(Attributes.field_233818_a_, 26.0D).func_233815_a_(Attributes.field_233821_d_, 0.2D).func_233815_a_((Attribute)LOTRAttributes.NPC_CONVERSATION_RANGE.get(), 6.0D).func_233815_a_(Attributes.field_233820_c_, 0.4D);
   }

   protected void addNPCAI() {
      super.addNPCAI();
      this.field_70714_bg.func_75776_a(0, new SwimGoal(this));
      this.addAttackGoal(3);
      this.field_70714_bg.func_75776_a(9, new OpenDoorGoal(this, true));
      this.field_70714_bg.func_75776_a(10, new TalkToCurrentGoal(this));
      this.field_70714_bg.func_75776_a(11, new FriendlyNPCConversationGoal(this, 5.0E-4F));
      this.field_70714_bg.func_75776_a(12, new WatchSunriseSunsetGoal(this, 0.001F));
      this.field_70714_bg.func_75776_a(13, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
      this.field_70714_bg.func_75776_a(14, new NPCEatGoal(this, this.getEatPool(), 6000));
      this.field_70714_bg.func_75776_a(14, new NPCDrinkGoal(this, this.getDrinkPool(), 6000));
      this.field_70714_bg.func_75776_a(15, new LookAtGoal(this, PlayerEntity.class, 8.0F, 0.02F));
      this.field_70714_bg.func_75776_a(15, new LookAtGoal(this, NPCEntity.class, 5.0F, 0.02F));
      this.field_70714_bg.func_75776_a(16, new LookAtGoal(this, LivingEntity.class, 8.0F, 0.02F));
      this.field_70714_bg.func_75776_a(17, new LookRandomlyGoal(this));
   }

   protected void initialiseAttackGoals(AttackGoalsHolder holder) {
      holder.setMeleeAttackGoal(this.createDwarfAttackGoal());
   }

   protected Goal createDwarfAttackGoal() {
      return new NPCMeleeAttackGoal(this, 1.3D);
   }

   protected void addNPCTargetingAI() {
      this.addAggressiveTargetingGoals();
   }

   protected NPCFoodPool getEatPool() {
      return NPCFoodPools.DWARF;
   }

   protected NPCFoodPool getDrinkPool() {
      return NPCFoodPools.DWARF_DRINK;
   }

   public ILivingEntityData func_213386_a(IServerWorld sw, DifficultyInstance diff, SpawnReason reason, ILivingEntityData spawnData, CompoundNBT dataTag) {
      spawnData = super.func_213386_a(sw, diff, reason, spawnData, dataTag);
      this.npcItemsInv.setMeleeWeapon(WEAPONS.getRandomItem(this.field_70146_Z));
      this.npcItemsInv.clearIdleItem();
      return spawnData;
   }

   protected SoundEvent func_184601_bQ(DamageSource source) {
      return LOTRSoundEvents.DWARF_HURT;
   }

   protected SoundEvent func_184615_bR() {
      return LOTRSoundEvents.DWARF_DEATH;
   }

   protected SoundEvent getAttackSound() {
      return LOTRSoundEvents.DWARF_ATTACK;
   }

   protected SoundEvent getKillSound() {
      return LOTRSoundEvents.DWARF_KILL;
   }

   protected float func_70647_i() {
      float f = super.func_70647_i();
      if (this.personalInfo.isFemale()) {
         f *= 1.2F;
      }

      return f;
   }

   public boolean func_213380_a(IWorld iworld, SpawnReason reason) {
      return super.func_213380_a(iworld, reason) && (reason != SpawnReason.NATURAL || this.canDwarfSpawnHere());
   }

   protected boolean canDwarfSpawnHere() {
      if (this.field_70146_Z.nextInt(20) == 0) {
         return this.canDwarfSpawnAboveGround();
      } else {
         BlockPos pos = this.func_233580_cy_();
         return this.field_70170_p.func_180495_p(pos.func_177977_b()).func_185904_a() == Material.field_151576_e && !this.field_70170_p.func_175710_j(pos);
      }
   }

   protected boolean canDwarfSpawnAboveGround() {
      return true;
   }

   public boolean useSmallArmsModel() {
      return (Boolean)LOTRConfig.CLIENT.dwarfWomenUseAlexModelStyle.get() && this.personalInfo.isFemale();
   }

   static {
      WEAPONS = SpawnEquipmentTable.of(Items.field_151036_c, Items.field_151035_b, LOTRItems.IRON_DAGGER, LOTRItems.DWARVEN_AXE, LOTRItems.DWARVEN_PICKAXE, LOTRItems.DWARVEN_DAGGER);
   }
}
