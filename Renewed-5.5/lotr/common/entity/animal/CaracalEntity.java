package lotr.common.entity.animal;

import java.util.EnumSet;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import lotr.common.entity.ai.goal.CaracalRaidChestGoal;
import lotr.common.init.LOTREntities;
import lotr.common.util.LOTRUtil;
import net.minecraft.block.BedBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.AttributeModifierMap.MutableAttribute;
import net.minecraft.entity.ai.controller.LookController;
import net.minecraft.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.entity.ai.goal.BreedGoal;
import net.minecraft.entity.ai.goal.CatLieOnBedGoal;
import net.minecraft.entity.ai.goal.CatSitOnBlockGoal;
import net.minecraft.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.LeapAtTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.OcelotAttackGoal;
import net.minecraft.entity.ai.goal.SitGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.ai.goal.Goal.Flag;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.passive.RabbitEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.LootContext.Builder;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.Effects;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.Hand;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class CaracalEntity extends CatEntity {
   private static final Ingredient BREEDING_ITEMS;
   public static final Predicate WANTS_TO_EAT;
   private static final DataParameter IS_RAIDING_CHEST;
   private static final DataParameter ARE_EARS_ALERT;
   private static final DataParameter IS_FLOPPING;
   private TemptGoal temptGoal;
   private CaracalEntity.CaracalAvoidPlayerGoal avoidPlayerGoal;
   private int eatTick;
   private static final int EATING_ITEM_STATUS_UPDATE = 45;
   private float yawWhenSat;
   private boolean wasSitting = false;
   private int earsAlertTimer = 0;
   private int floppingTimer = 0;

   public CaracalEntity(EntityType type, World worldIn) {
      super(type, worldIn);
      this.func_98053_h(true);
      this.field_70749_g = new CaracalEntity.CaracalLookController(this);
   }

   protected void func_70088_a() {
      super.func_70088_a();
      this.field_70180_af.func_187214_a(IS_RAIDING_CHEST, false);
      this.field_70180_af.func_187214_a(ARE_EARS_ALERT, false);
      this.field_70180_af.func_187214_a(IS_FLOPPING, false);
   }

   protected void func_184651_r() {
      this.temptGoal = new CaracalEntity.CaracalTemptGoal(this, 0.6D, BREEDING_ITEMS, true);
      this.field_70714_bg.func_75776_a(1, new SwimGoal(this));
      this.field_70714_bg.func_75776_a(2, new SitGoal(this));
      this.field_70714_bg.func_75776_a(3, new CaracalEntity.CaracalMorningGiftGoal(this));
      this.field_70714_bg.func_75776_a(4, this.temptGoal);
      this.field_70714_bg.func_75776_a(6, new CatLieOnBedGoal(this, 1.1D, 8));
      this.field_70714_bg.func_75776_a(7, new CaracalRaidChestGoal(this, 1.2D));
      this.field_70714_bg.func_75776_a(8, new FollowOwnerGoal(this, 1.0D, 10.0F, 5.0F, false));
      this.field_70714_bg.func_75776_a(9, new CatSitOnBlockGoal(this, 0.8D));
      this.field_70714_bg.func_75776_a(10, new LeapAtTargetGoal(this, 0.3F));
      this.field_70714_bg.func_75776_a(11, new OcelotAttackGoal(this));
      this.field_70714_bg.func_75776_a(12, new BreedGoal(this, 0.8D));
      this.field_70714_bg.func_75776_a(13, new WaterAvoidingRandomWalkingGoal(this, 0.8D, 1.0E-5F));
      this.field_70714_bg.func_75776_a(13, new CaracalEntity.CaracalFindItemsGoal(this, 1.2D));
      this.field_70714_bg.func_75776_a(14, new LookAtGoal(this, PlayerEntity.class, 10.0F));
      this.field_70715_bh.func_75776_a(1, new NearestAttackableTargetGoal(this, RabbitEntity.class, 10, false, false, (Predicate)null));
      this.field_70715_bh.func_75776_a(1, new NearestAttackableTargetGoal(this, ChickenEntity.class, 10, false, false, (Predicate)null));
   }

   protected void func_175544_ck() {
      if (this.avoidPlayerGoal == null) {
         this.avoidPlayerGoal = new CaracalEntity.CaracalAvoidPlayerGoal(this, PlayerEntity.class, 16.0F, 0.8D, 1.33D);
      }

      this.field_70714_bg.func_85156_a(this.avoidPlayerGoal);
      if (!this.func_70909_n()) {
         this.field_70714_bg.func_75776_a(5, this.avoidPlayerGoal);
      }

   }

   public static MutableAttribute regAttrs() {
      return CatEntity.func_234184_eY_().func_233815_a_(Attributes.field_233818_a_, 15.0D).func_233815_a_(Attributes.field_233823_f_, 4.0D);
   }

   public boolean isRaidingChest() {
      return (Boolean)this.field_70180_af.func_187225_a(IS_RAIDING_CHEST);
   }

   public void setIsRaidingChest(boolean flag) {
      this.field_70180_af.func_187227_b(IS_RAIDING_CHEST, flag);
   }

   public boolean areEarsAlert() {
      return (Boolean)this.field_70180_af.func_187225_a(ARE_EARS_ALERT);
   }

   private void setAreEarsAlert(boolean flag) {
      this.field_70180_af.func_187227_b(ARE_EARS_ALERT, flag);
   }

   public boolean isFlopping() {
      return (Boolean)this.field_70180_af.func_187225_a(IS_FLOPPING);
   }

   private void setIsFlopping(boolean flag) {
      this.field_70180_af.func_187227_b(IS_FLOPPING, flag);
   }

   public void func_70619_bc() {
      super.func_70619_bc();
      if (this.func_70605_aq().func_75640_a()) {
         double speed = this.func_70605_aq().func_75638_b();
         if (speed <= 0.6D) {
            this.func_213301_b(Pose.CROUCHING);
            this.func_70031_b(false);
         } else if (speed >= 1.33D) {
            this.func_213301_b(Pose.STANDING);
            this.func_70031_b(true);
         } else {
            this.func_213301_b(Pose.STANDING);
            this.func_70031_b(false);
         }
      } else {
         this.func_213301_b(Pose.STANDING);
         this.func_70031_b(false);
      }

      if (this.isRaidingChest()) {
         this.func_213301_b(Pose.CROUCHING);
         this.func_70031_b(true);
      }

   }

   public void func_70071_h_() {
      super.func_70071_h_();
      if (this.func_233684_eK_()) {
         if (!this.wasSitting) {
            this.yawWhenSat = this.field_70177_z;
         }

         this.field_70761_aq = this.field_70177_z = this.yawWhenSat;
      }

      this.wasSitting = this.func_233684_eK_();
      if (this.temptGoal != null && this.temptGoal.func_75277_f() && !this.func_70909_n() && this.field_70173_aa % 100 == 0) {
         this.func_184185_a(SoundEvents.field_219608_aI, 1.0F, 1.0F);
      }

      if (!this.field_70170_p.field_72995_K) {
         if (this.earsAlertTimer < 0) {
            ++this.earsAlertTimer;
         } else if (this.areEarsAlert()) {
            --this.earsAlertTimer;
            if (this.earsAlertTimer <= 0) {
               this.setAreEarsAlert(false);
               this.earsAlertTimer = -LOTRUtil.secondsToTicks(10 + this.field_70146_Z.nextInt(15));
            }
         }

         if (this.floppingTimer < 0) {
            ++this.floppingTimer;
         } else if (!this.isFlopping()) {
            if (this.field_70146_Z.nextInt(600) == 0) {
               this.setIsFlopping(true);
               int maxFlopSeconds = this.isFloppa() ? 10 : 6;
               this.floppingTimer = LOTRUtil.secondsToTicks(MathHelper.func_76136_a(this.field_70146_Z, 1, maxFlopSeconds));
            }
         } else {
            --this.floppingTimer;
            if (this.floppingTimer <= 0) {
               this.setIsFlopping(false);
               this.floppingTimer = -LOTRUtil.secondsToTicks(30 + this.field_70146_Z.nextInt(200));
               if (this.isFloppa()) {
                  this.floppingTimer /= 2;
               }
            }
         }
      }

   }

   public ActionResultType func_230254_b_(PlayerEntity player, Hand hand) {
      if (this.func_70909_n() && this.func_233685_eM_() && player.func_225608_bj_() && player.func_184586_b(hand).func_190926_b()) {
         if (!this.field_70170_p.field_72995_K) {
            this.func_184185_a(SoundEvents.field_187645_R, 0.6F + 0.4F * (this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()), 1.0F);
         }

         return ActionResultType.SUCCESS;
      } else {
         return super.func_230254_b_(player, hand);
      }
   }

   public boolean isFloppa() {
      return this.func_70909_n() && this.func_145818_k_() && nameContainsAny(this.func_200201_e().getString(), "floppa", "gregory", "gosha");
   }

   private static boolean nameContainsAny(String name, String... matches) {
      Stream var10000 = Stream.of(matches).map(String::toLowerCase);
      String var10001 = name.toLowerCase();
      var10001.getClass();
      return var10000.anyMatch(var10001::contains);
   }

   public CaracalEntity createChild(ServerWorld world, AgeableEntity mate) {
      CaracalEntity caracal = (CaracalEntity)((EntityType)LOTREntities.CARACAL.get()).func_200721_a(world);
      CatEntity superChild = super.func_241840_a(world, mate);
      caracal.func_184754_b(superChild.func_184753_b());
      caracal.func_70903_f(superChild.func_70909_n());
      caracal.func_213417_a(superChild.func_213414_ei());
      return caracal;
   }

   public boolean func_70877_b(ItemStack stack) {
      return BREEDING_ITEMS.test(stack);
   }

   protected float func_213348_b(Pose pose, EntitySize size) {
      return size.field_220316_b * 0.85F;
   }

   public boolean func_70097_a(DamageSource source, float amount) {
      boolean flag = super.func_70097_a(source, amount);
      if (flag && this.func_233684_eK_()) {
         this.func_233686_v_(false);
      }

      return flag;
   }

   protected float func_70647_i() {
      return super.func_70647_i() * 0.65F;
   }

   public int func_70627_aG() {
      return 200;
   }

   public SoundEvent func_213353_d(ItemStack stack) {
      return SoundEvents.field_219607_aG;
   }

   public boolean hasItemInMouth() {
      return !this.func_184582_a(EquipmentSlotType.MAINHAND).func_190926_b();
   }

   public ItemStack getItemInMouth() {
      return this.func_184582_a(EquipmentSlotType.MAINHAND);
   }

   public void setItemInMouth(ItemStack stack) {
      this.func_184201_a(EquipmentSlotType.MAINHAND, stack);
   }

   public void func_70636_d() {
      super.func_70636_d();
      if (!this.field_70170_p.field_72995_K && this.func_70089_S() && this.func_70613_aW()) {
         ++this.eatTick;
         if (this.eatTick >= 560) {
            ItemStack heldItem = this.getItemInMouth();
            if (this.canEatItem(heldItem)) {
               if (this.canEatItemNow(heldItem)) {
                  if (this.eatTick > 600) {
                     ItemStack eatResult = heldItem.func_77950_b(this.field_70170_p, this);
                     this.func_195063_d(Effects.field_76438_s);
                     if (!eatResult.func_190926_b()) {
                        if (this.canEatItem(eatResult)) {
                           this.setItemInMouth(eatResult);
                        } else {
                           this.func_199701_a_(eatResult);
                           this.setItemInMouth(ItemStack.field_190927_a);
                        }
                     }

                     this.eatTick = 0;
                  } else if (this.eatTick > 560 && this.field_70146_Z.nextFloat() < 0.1F) {
                     this.func_184185_a(this.func_213353_d(heldItem), 1.0F, 1.0F);
                     this.field_70170_p.func_72960_a(this, (byte)45);
                  }
               }
            } else {
               this.func_199701_a_(heldItem);
               this.setItemInMouth(ItemStack.field_190927_a);
            }
         }
      }

   }

   public ItemStack func_213357_a(World world, ItemStack stack) {
      int healAmount = stack.func_222117_E() ? stack.func_77973_b().func_219967_s().func_221466_a() : 0;
      ItemStack result = super.func_213357_a(world, stack);
      if (!world.field_72995_K && healAmount > 0) {
         this.func_70691_i((float)healAmount);
      }

      return result;
   }

   public boolean canEatItem(ItemStack stack) {
      return WANTS_TO_EAT.test(stack);
   }

   private boolean canEatItemNow(ItemStack stack) {
      return this.canEatItem(stack) && this.func_70638_az() == null && this.field_70122_E && !this.func_70608_bn();
   }

   @OnlyIn(Dist.CLIENT)
   public void func_70103_a(byte id) {
      if (id == 45) {
         ItemStack heldItem = this.getItemInMouth();
         if (!heldItem.func_190926_b()) {
            for(int i = 0; i < 8; ++i) {
               Vector3d crumbMotion = (new Vector3d(((double)this.field_70146_Z.nextFloat() - 0.5D) * 0.1D, Math.random() * 0.1D + 0.1D, 0.0D)).func_178789_a((float)Math.toRadians((double)(-this.field_70125_A))).func_178785_b((float)Math.toRadians((double)(-this.field_70177_z)));
               this.field_70170_p.func_195594_a(new ItemParticleData(ParticleTypes.field_197591_B, heldItem), this.func_226277_ct_() + this.func_70040_Z().field_72450_a / 2.0D, this.func_226278_cu_(), this.func_226281_cx_() + this.func_70040_Z().field_72449_c / 2.0D, crumbMotion.field_72450_a, crumbMotion.field_72448_b + 0.05D, crumbMotion.field_72449_c);
            }
         }
      } else {
         super.func_70103_a(id);
      }

   }

   public boolean func_175448_a(ItemStack stack) {
      ItemStack currentHeldItem = this.getItemInMouth();
      return this.canEatItem(stack) && (currentHeldItem.func_190926_b() || this.eatTick > 0 && !this.canEatItem(currentHeldItem));
   }

   public boolean func_213365_e(ItemStack stack) {
      EquipmentSlotType slotType = MobEntity.func_184640_d(stack);
      if (!this.func_184582_a(slotType).func_190926_b()) {
         return false;
      } else {
         return slotType == EquipmentSlotType.MAINHAND && super.func_213365_e(stack);
      }
   }

   protected void func_175445_a(ItemEntity itemEntity) {
      ItemStack stack = itemEntity.func_92059_d();
      if (this.func_175448_a(stack)) {
         int count = stack.func_190916_E();
         if (count > 1) {
            this.dropItemBack(stack.func_77979_a(count - 1));
         }

         this.spitOutCurrentItemInMouth();
         this.func_233630_a_(itemEntity);
         this.setItemInMouth(stack.func_77979_a(1));
         this.field_82174_bp[EquipmentSlotType.MAINHAND.func_188454_b()] = 2.0F;
         this.func_71001_a(itemEntity, stack.func_190916_E());
         itemEntity.func_70106_y();
         this.eatTick = 0;
      }

   }

   private void dropItemBack(ItemStack stack) {
      ItemEntity itementity = new ItemEntity(this.field_70170_p, this.func_226277_ct_(), this.func_226278_cu_(), this.func_226281_cx_(), stack);
      this.field_70170_p.func_217376_c(itementity);
   }

   private void spitOutCurrentItemInMouth() {
      ItemStack stack = this.getItemInMouth();
      if (!stack.func_190926_b() && !this.field_70170_p.field_72995_K) {
         ItemEntity drop = new ItemEntity(this.field_70170_p, this.func_226277_ct_() + this.func_70040_Z().field_72450_a, this.func_226278_cu_() + 1.0D, this.func_226281_cx_() + this.func_70040_Z().field_72449_c, stack);
         drop.func_174867_a(40);
         drop.func_200216_c(this.func_110124_au());
         this.func_184185_a(SoundEvents.field_219629_dC, 1.0F, 1.0F);
         this.field_70170_p.func_217376_c(drop);
      }

   }

   protected void func_213345_d(DamageSource source) {
      ItemStack heldItem = this.getItemInMouth();
      if (!heldItem.func_190926_b()) {
         this.func_199701_a_(heldItem);
         this.setItemInMouth(ItemStack.field_190927_a);
      }

      super.func_213345_d(source);
   }

   static {
      BREEDING_ITEMS = Ingredient.func_199804_a(new IItemProvider[]{Items.field_196086_aW, Items.field_196087_aX, Items.field_151076_bf, Items.field_179558_bo});
      WANTS_TO_EAT = BREEDING_ITEMS.or((stack) -> {
         return stack.func_77973_b().func_219971_r() && stack.func_77973_b().func_219967_s().func_221467_c();
      });
      IS_RAIDING_CHEST = EntityDataManager.func_187226_a(CaracalEntity.class, DataSerializers.field_187198_h);
      ARE_EARS_ALERT = EntityDataManager.func_187226_a(CaracalEntity.class, DataSerializers.field_187198_h);
      IS_FLOPPING = EntityDataManager.func_187226_a(CaracalEntity.class, DataSerializers.field_187198_h);
   }

   static class CaracalFindItemsGoal extends Goal {
      private static final Predicate ITEM_SELECTOR = (itemEntity) -> {
         return !itemEntity.func_174874_s() && itemEntity.func_70089_S() && CaracalEntity.WANTS_TO_EAT.test(itemEntity.func_92059_d());
      };
      private final CaracalEntity caracal;
      private final double speed;

      public CaracalFindItemsGoal(CaracalEntity caracal, double speed) {
         this.caracal = caracal;
         this.speed = speed;
         this.func_220684_a(EnumSet.of(Flag.MOVE));
      }

      public boolean func_75250_a() {
         return this.caracal.func_70681_au().nextInt(10) == 0 && this.func_75253_b();
      }

      public boolean func_75253_b() {
         if (this.caracal.hasItemInMouth()) {
            return false;
         } else if (!this.caracal.func_233685_eM_() && this.caracal.func_70638_az() == null && this.caracal.func_70643_av() == null) {
            return this.findDroppedItem() != null;
         } else {
            return false;
         }
      }

      private ItemEntity findDroppedItem() {
         List items = this.caracal.field_70170_p.func_175647_a(ItemEntity.class, this.caracal.func_174813_aQ().func_72314_b(8.0D, 8.0D, 8.0D), ITEM_SELECTOR);
         return !items.isEmpty() ? (ItemEntity)items.get(0) : null;
      }

      public void func_75249_e() {
         ItemEntity droppedItem = this.findDroppedItem();
         if (droppedItem != null) {
            this.caracal.func_70661_as().func_75497_a(droppedItem, this.speed);
         }

      }

      public void func_75246_d() {
         if (!this.caracal.hasItemInMouth()) {
            ItemEntity droppedItem = this.findDroppedItem();
            if (droppedItem != null) {
               this.caracal.func_70661_as().func_75497_a(droppedItem, this.speed);
            }
         }

      }
   }

   static class CaracalTemptGoal extends TemptGoal {
      @Nullable
      private PlayerEntity temptingPlayer;
      private final CaracalEntity caracal;

      public CaracalTemptGoal(CaracalEntity caracal, double speed, Ingredient temptItems, boolean scaredByPlayerMovement) {
         super(caracal, speed, temptItems, scaredByPlayerMovement);
         this.caracal = caracal;
      }

      public void func_75246_d() {
         super.func_75246_d();
         if (this.temptingPlayer == null && this.caracal.func_70681_au().nextInt(600) == 0) {
            this.temptingPlayer = this.field_75289_h;
         } else if (this.caracal.func_70681_au().nextInt(500) == 0) {
            this.temptingPlayer = null;
         }

      }

      protected boolean func_220761_g() {
         return this.temptingPlayer != null && this.temptingPlayer.equals(this.field_75289_h) ? false : super.func_220761_g();
      }

      public boolean func_75250_a() {
         return super.func_75250_a() && !this.caracal.func_70909_n();
      }
   }

   static class CaracalMorningGiftGoal extends Goal {
      private final CaracalEntity caracal;
      private PlayerEntity theOwner;
      private BlockPos bedPos;
      private int tickCounter;

      public CaracalMorningGiftGoal(CaracalEntity caracal) {
         this.caracal = caracal;
      }

      public boolean func_75250_a() {
         if (!this.caracal.func_70909_n()) {
            return false;
         } else if (this.caracal.func_233685_eM_()) {
            return false;
         } else {
            LivingEntity owner = this.caracal.func_70902_q();
            if (owner instanceof PlayerEntity) {
               this.theOwner = (PlayerEntity)owner;
               if (!this.theOwner.func_70608_bn()) {
                  return false;
               }

               if (this.caracal.func_70068_e(this.theOwner) > 100.0D) {
                  return false;
               }

               BlockPos ownerPos = this.theOwner.func_233580_cy_();
               BlockState state = this.caracal.field_70170_p.func_180495_p(ownerPos);
               if (state.func_177230_c().func_203417_a(BlockTags.field_219747_F)) {
                  this.bedPos = (BlockPos)state.func_235903_d_(BedBlock.field_185512_D).map((dir) -> {
                     return ownerPos.func_177972_a(dir.func_176734_d());
                  }).orElseGet(() -> {
                     return new BlockPos(ownerPos);
                  });
                  return !this.anyOtherCaracalsGivingGifts();
               }
            }

            return false;
         }
      }

      private boolean anyOtherCaracalsGivingGifts() {
         return this.caracal.field_70170_p.func_217357_a(CaracalEntity.class, (new AxisAlignedBB(this.bedPos)).func_186662_g(2.0D)).stream().filter((otherCaracal) -> {
            return otherCaracal != this.caracal;
         }).anyMatch((otherCaracal) -> {
            return otherCaracal.func_213416_eg() || otherCaracal.func_213409_eh();
         });
      }

      public boolean func_75253_b() {
         return this.caracal.func_70909_n() && !this.caracal.func_233685_eM_() && this.theOwner != null && this.theOwner.func_70608_bn() && this.bedPos != null && !this.anyOtherCaracalsGivingGifts();
      }

      public void func_75249_e() {
         if (this.bedPos != null) {
            this.caracal.func_233686_v_(false);
            this.caracal.func_70661_as().func_75492_a((double)this.bedPos.func_177958_n(), (double)this.bedPos.func_177956_o(), (double)this.bedPos.func_177952_p(), 1.100000023841858D);
         }

      }

      public void func_75251_c() {
         this.caracal.func_213419_u(false);
         float skyAngle = this.caracal.field_70170_p.func_242415_f(1.0F);
         if (this.theOwner.func_71060_bI() >= 100 && skyAngle > 0.77F && skyAngle < 0.8F && this.caracal.field_70170_p.func_201674_k().nextFloat() < 0.7F) {
            this.spawnGiftItem();
         }

         this.tickCounter = 0;
         this.caracal.func_213415_v(false);
         this.caracal.func_70661_as().func_75499_g();
      }

      private void spawnGiftItem() {
         Random rand = this.caracal.func_70681_au();
         Mutable movingPos = new Mutable();
         movingPos.func_189533_g(this.caracal.func_233580_cy_());
         this.caracal.func_213373_a((double)(movingPos.func_177958_n() + MathHelper.func_76136_a(rand, -5, 5)), (double)(movingPos.func_177956_o() + MathHelper.func_76136_a(rand, -2, 2)), (double)(movingPos.func_177952_p() + MathHelper.func_76136_a(rand, -5, 5)), false);
         movingPos.func_189533_g(this.caracal.func_233580_cy_());
         LootTable lootTable = this.caracal.field_70170_p.func_73046_m().func_200249_aQ().func_186521_a(LootTables.field_215797_af);
         Builder builder = (new Builder((ServerWorld)this.caracal.field_70170_p)).func_216015_a(LootParameters.field_237457_g_, this.caracal.func_213303_ch()).func_216015_a(LootParameters.field_216281_a, this.caracal).func_216023_a(rand);
         float yawRadians = (float)Math.toRadians((double)this.caracal.field_70761_aq);
         lootTable.func_216113_a(builder.func_216022_a(LootParameterSets.field_216264_e)).forEach((lootStack) -> {
            this.caracal.field_70170_p.func_217376_c(new ItemEntity(this.caracal.field_70170_p, (double)((float)movingPos.func_177958_n() - MathHelper.func_76126_a(yawRadians)), (double)movingPos.func_177956_o(), (double)((float)movingPos.func_177952_p() + MathHelper.func_76134_b(yawRadians)), lootStack));
         });
      }

      public void func_75246_d() {
         if (this.theOwner != null && this.bedPos != null) {
            this.caracal.func_233686_v_(false);
            this.caracal.func_70661_as().func_75492_a((double)this.bedPos.func_177958_n(), (double)this.bedPos.func_177956_o(), (double)this.bedPos.func_177952_p(), 1.100000023841858D);
            if (this.caracal.func_70068_e(this.theOwner) < 2.5D) {
               ++this.tickCounter;
               if (this.tickCounter > 16) {
                  this.caracal.func_213419_u(true);
                  this.caracal.func_213415_v(false);
               } else {
                  this.caracal.func_70625_a(this.theOwner, 45.0F, 45.0F);
                  this.caracal.func_213415_v(true);
               }
            } else {
               this.caracal.func_213419_u(false);
            }
         }

      }
   }

   static class CaracalAvoidPlayerGoal extends AvoidEntityGoal {
      private final CaracalEntity caracal;

      public CaracalAvoidPlayerGoal(CaracalEntity caracal, Class entityClassToAvoid, float avoidDistance, double farSpeed, double nearSpeed) {
         Predicate var10006 = EntityPredicates.field_188444_d;
         super(caracal, entityClassToAvoid, avoidDistance, farSpeed, nearSpeed, var10006::test);
         this.caracal = caracal;
      }

      public boolean func_75250_a() {
         return !this.caracal.func_70909_n() && super.func_75250_a();
      }

      public boolean func_75253_b() {
         return !this.caracal.func_70909_n() && super.func_75253_b();
      }
   }

   public static class CaracalLookController extends LookController {
      private final CaracalEntity theCaracal;

      public CaracalLookController(CaracalEntity caracal) {
         super(caracal);
         this.theCaracal = caracal;
      }

      public void func_75649_a() {
         if (this.field_75655_d && !this.theCaracal.areEarsAlert() && this.theCaracal.earsAlertTimer >= 0 && this.theCaracal.field_70146_Z.nextInt(60) == 0) {
            this.theCaracal.setAreEarsAlert(true);
            this.theCaracal.earsAlertTimer = LOTRUtil.secondsToTicks(1 + this.theCaracal.field_70146_Z.nextInt(2));
         }

         super.func_75649_a();
      }
   }
}
