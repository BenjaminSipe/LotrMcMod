package lotr.common.item;

import com.google.common.collect.ImmutableMultimap.Builder;
import lotr.common.dispenser.DispenseSpear;
import lotr.common.entity.projectile.SpearEntity;
import lotr.common.init.LOTRMaterial;
import net.minecraft.block.DispenserBlock;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity.PickupStatus;
import net.minecraft.item.BowItem;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class SpearItem extends LOTRSwordItem {
   public SpearItem(IItemTier tier) {
      super(tier, 2, -2.667F);
      DispenserBlock.func_199774_a(this, new DispenseSpear());
   }

   public SpearItem(LOTRMaterial material) {
      this((IItemTier)material.asTool());
   }

   protected void setupExtendedMeleeAttributes(Builder builder) {
      this.addReachModifier(builder, 1.0D);
   }

   public int func_77626_a(ItemStack stack) {
      return 72000;
   }

   public UseAction func_77661_b(ItemStack stack) {
      return UseAction.BOW;
   }

   public ActionResult func_77659_a(World world, PlayerEntity player, Hand hand) {
      ItemStack heldItem = player.func_184586_b(hand);
      if (canPlayerUseSpearAction(player, hand)) {
         player.func_184598_c(hand);
         return ActionResult.func_226248_a_(heldItem);
      } else {
         return ActionResult.func_226250_c_(heldItem);
      }
   }

   private static boolean canPlayerUseSpearAction(PlayerEntity player, Hand hand) {
      if (hand == Hand.MAIN_HAND) {
         ItemStack offhandItem = player.func_184592_cb();
         if (offhandItem.func_77975_n() == UseAction.BLOCK) {
            return player.func_225608_bj_();
         }
      }

      return true;
   }

   public void func_77615_a(ItemStack stack, World world, LivingEntity shooter, int timeLeft) {
      int useTime = this.func_77626_a(stack) - timeLeft;
      float charge = BowItem.func_185059_b(useTime);
      if ((double)charge > 0.1D) {
         if (!world.field_72995_K) {
            SpearEntity spear = new SpearEntity(world, shooter, stack);
            spear.func_234612_a_(shooter, shooter.field_70125_A, shooter.field_70177_z, 0.0F, charge * 3.0F, 1.0F);
            if (charge == 1.0F) {
               spear.func_70243_d(true);
            }

            applyStandardEnchantmentsFromBow(stack, spear);
            spear.field_70251_a = this.getPickupStatusForShooter(shooter);
            world.func_217376_c(spear);
         }

         world.func_184148_a((PlayerEntity)null, shooter.func_226277_ct_(), shooter.func_226278_cu_(), shooter.func_226281_cx_(), SoundEvents.field_203274_ip, SoundCategory.PLAYERS, 1.0F, 1.0F / (field_77697_d.nextFloat() * 0.4F + 1.2F) + charge * 0.5F);
         if (shooter instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity)shooter;
            if (!player.field_71075_bZ.field_75098_d) {
               stack.func_190918_g(1);
               if (stack.func_190926_b()) {
                  player.field_71071_by.func_184437_d(stack);
               }
            }

            player.func_71029_a(Stats.field_75929_E.func_199076_b(this));
         }
      }

   }

   private PickupStatus getPickupStatusForShooter(LivingEntity shooter) {
      if (shooter instanceof PlayerEntity) {
         return ((PlayerEntity)shooter).field_71075_bZ.field_75098_d ? PickupStatus.CREATIVE_ONLY : PickupStatus.ALLOWED;
      } else {
         return PickupStatus.DISALLOWED;
      }
   }

   public static void applyStandardEnchantmentsFromBow(ItemStack stack, AbstractArrowEntity projectile) {
      int power = EnchantmentHelper.func_77506_a(Enchantments.field_185309_u, stack);
      if (power > 0) {
         projectile.func_70239_b(projectile.func_70242_d() + (double)power * 0.5D + 0.5D);
      }

      int punch = EnchantmentHelper.func_77506_a(Enchantments.field_185310_v, stack);
      if (punch > 0) {
         projectile.func_70240_a(punch);
      }

      if (EnchantmentHelper.func_77506_a(Enchantments.field_185311_w, stack) > 0) {
         projectile.func_70015_d(100);
      }

   }
}
