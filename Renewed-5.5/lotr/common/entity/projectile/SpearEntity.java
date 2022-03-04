package lotr.common.entity.projectile;

import java.util.Collection;
import java.util.Optional;
import lotr.common.init.LOTRDamageSources;
import lotr.common.init.LOTREntities;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity.PickupStatus;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.world.World;

public class SpearEntity extends LOTRAbstractArrowEntity {
   private static final DataParameter SPEAR_ITEM;

   public SpearEntity(EntityType type, World w) {
      super(type, w);
   }

   public SpearEntity(World w, LivingEntity thrower, ItemStack stack) {
      super((EntityType)LOTREntities.SPEAR.get(), thrower, w);
      this.setSpearItem(stack.func_77946_l());
   }

   public SpearEntity(World w, double x, double y, double z, ItemStack stack) {
      super((EntityType)LOTREntities.SPEAR.get(), x, y, z, w);
      this.setSpearItem(stack.func_77946_l());
   }

   protected void func_70088_a() {
      super.func_70088_a();
      this.field_70180_af.func_187214_a(SPEAR_ITEM, ItemStack.field_190927_a);
   }

   public ItemStack getSpearItem() {
      return (ItemStack)this.field_70180_af.func_187225_a(SPEAR_ITEM);
   }

   private void setSpearItem(ItemStack stack) {
      this.field_70180_af.func_187227_b(SPEAR_ITEM, stack);
   }

   protected ItemStack func_184550_j() {
      return this.getSpearItem().func_77946_l();
   }

   protected void func_213868_a(EntityRayTraceResult result) {
      Entity hitEntity = result.func_216348_a();
      int damage = this.calculateImpactDamageIncludingCritical(this.getBaseImpactDamage(hitEntity));
      Entity shooter = this.func_234616_v_();
      DamageSource damageSource = LOTRDamageSources.causeThrownSpearDamage(this, (Entity)Optional.ofNullable(shooter).orElse(this));
      if (hitEntity.func_70097_a(damageSource, (float)damage)) {
         if (this.func_70027_ad()) {
            hitEntity.func_70015_d(5);
         }

         if (hitEntity instanceof LivingEntity) {
            LivingEntity hitLivingEntity = (LivingEntity)hitEntity;
            if (shooter instanceof LivingEntity) {
               EnchantmentHelper.func_151384_a(hitLivingEntity, shooter);
               EnchantmentHelper.func_151385_b((LivingEntity)shooter, hitLivingEntity);
            }

            this.func_184548_a(hitLivingEntity);
         }
      }

      this.func_213317_d(this.func_213322_ci().func_216372_d(-0.01D, -0.1D, -0.01D));
      this.func_184185_a(SoundEvents.field_203268_ij, 1.0F, 1.0F);
   }

   protected SoundEvent func_213867_k() {
      return SoundEvents.field_203269_ik;
   }

   private float getBaseImpactDamage(Entity hitEntity) {
      double speed = this.func_213322_ci().func_72433_c();
      ItemStack spearItem = this.getSpearItem();
      double damageMultiplier = this.getSpearItemAttackDamage(spearItem);
      damageMultiplier += (double)EnchantmentHelper.func_152377_a(spearItem, hitEntity instanceof LivingEntity ? ((LivingEntity)hitEntity).func_70668_bt() : CreatureAttribute.field_223222_a_);
      damageMultiplier *= 0.7D;
      return (float)(speed * damageMultiplier);
   }

   private double getSpearItemAttackDamage(ItemStack stack) {
      Attribute attr = Attributes.field_233823_f_;
      ModifiableAttributeInstance attrInst = new ModifiableAttributeInstance(attr, (mai) -> {
      });
      attrInst.func_111128_a(1.0D);
      Collection mainhandAttackModifiers = stack.func_111283_C(EquipmentSlotType.MAINHAND).get(attr);
      mainhandAttackModifiers.forEach(attrInst::func_233767_b_);
      return attrInst.func_111126_e();
   }

   protected int getLifespanTicksInGround() {
      return this.field_70251_a == PickupStatus.DISALLOWED ? 1200 : 6000;
   }

   public void func_70100_b_(PlayerEntity player) {
      if (!this.field_70170_p.field_72995_K && (this.field_70254_i || this.func_203047_q()) && this.field_70249_b <= 0) {
         boolean canPickUp = this.field_70251_a == PickupStatus.ALLOWED || this.field_70251_a == PickupStatus.CREATIVE_ONLY && player.field_71075_bZ.field_75098_d || this.func_203047_q() && this.func_234616_v_().func_110124_au() == player.func_110124_au();
         if (canPickUp) {
            if (this.field_70251_a == PickupStatus.CREATIVE_ONLY) {
               this.doPickUpAnimationAndRemove(player);
            } else {
               ItemStack pickupStack = this.func_184550_j().func_77946_l();
               pickupStack.func_222118_a(1, player, (p) -> {
               });
               if (pickupStack.func_190926_b()) {
                  this.playBreakSoundAndRemove();
               } else if (player.field_71071_by.func_70441_a(pickupStack)) {
                  this.doPickUpAnimationAndRemove(player);
               }
            }
         }
      }

   }

   private void doPickUpAnimationAndRemove(PlayerEntity player) {
      player.func_71001_a(this, 1);
      this.func_70106_y();
   }

   private void playBreakSoundAndRemove() {
      this.func_184185_a(SoundEvents.field_187635_cQ, 0.8F, 0.8F + this.field_70170_p.field_73012_v.nextFloat() * 0.4F);
      this.func_70106_y();
   }

   public void func_70037_a(CompoundNBT nbt) {
      super.func_70037_a(nbt);
      if (nbt.func_150297_b("SpearItem", 10)) {
         this.setSpearItem(ItemStack.func_199557_a(nbt.func_74775_l("SpearItem")));
      }

   }

   public void func_213281_b(CompoundNBT nbt) {
      super.func_213281_b(nbt);
      nbt.func_218657_a("SpearItem", this.getSpearItem().func_77955_b(new CompoundNBT()));
   }

   protected float func_203044_p() {
      return 0.99F;
   }

   static {
      SPEAR_ITEM = EntityDataManager.func_187226_a(SpearEntity.class, DataSerializers.field_187196_f);
   }
}
