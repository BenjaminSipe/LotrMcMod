package lotr.common.item;

import java.util.List;
import lotr.common.entity.projectile.SmokeRingEntity;
import lotr.common.init.LOTRItemGroups;
import lotr.common.init.LOTRItems;
import lotr.common.init.LOTRSoundEvents;
import lotr.common.util.LOTRUtil;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.item.Item.Properties;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SmokingPipeItem extends Item {
   private final int smokeFood;
   private final float smokeSaturation;

   public SmokingPipeItem(Properties properties) {
      super(properties);
      this.smokeFood = 2;
      this.smokeSaturation = 0.3F;
   }

   public SmokingPipeItem() {
      this((new Properties()).func_200918_c(300).func_200916_a(LOTRItemGroups.MISC));
   }

   public int func_77626_a(ItemStack stack) {
      return 40;
   }

   public UseAction func_77661_b(ItemStack stack) {
      return UseAction.BOW;
   }

   public ActionResult func_77659_a(World world, PlayerEntity player, Hand hand) {
      ItemStack heldItem = player.func_184586_b(hand);
      if (this.canSmoke(player)) {
         player.func_184598_c(hand);
         return ActionResult.func_226249_b_(heldItem);
      } else {
         return ActionResult.func_226251_d_(heldItem);
      }
   }

   private boolean canSmoke(LivingEntity entity) {
      if (entity instanceof PlayerEntity) {
         PlayerEntity player = (PlayerEntity)entity;
         if (player.field_71075_bZ.field_75098_d) {
            return true;
         } else {
            ItemStack smokedItem = LOTRUtil.findHeldOrInventoryItem(player, this::isSmokable);
            return !smokedItem.func_190926_b();
         }
      } else {
         return true;
      }
   }

   private boolean isSmokable(ItemStack stack) {
      return stack.func_77973_b() == LOTRItems.PIPEWEED.get();
   }

   public ItemStack func_77654_b(ItemStack stack, World world, LivingEntity entity) {
      if (this.canSmoke(entity)) {
         stack.func_222118_a(1, entity, (e) -> {
            e.func_213334_d(e.func_184600_cs());
         });
         if (entity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity)entity;
            ItemStack smokedItem = LOTRUtil.findHeldOrInventoryItem(player, this::isSmokable);
            if (!smokedItem.func_190926_b()) {
               LOTRUtil.consumeOneInventoryItem(player, smokedItem);
            }

            if (player.func_71043_e(false)) {
               player.func_71024_bL().func_75122_a(2, 0.3F);
            }

            player.func_71029_a(Stats.field_75929_E.func_199076_b(this));
         } else {
            entity.func_70691_i(2.0F);
         }

         if (!world.field_72995_K) {
            SmokeRingEntity smoke = new SmokeRingEntity(world, entity);
            DyeColor color = getSmokeColor(stack);
            boolean magic = isMagicSmoke(stack);
            smoke.setSmokeColor(color);
            smoke.setMagicSmoke(magic);
            float speed = 0.1F;
            smoke.func_234612_a_(entity, entity.field_70125_A, entity.field_70177_z, 0.0F, speed, 0.0F);
            world.func_217376_c(smoke);
         }

         entity.func_184185_a(LOTRSoundEvents.SMOKE_PUFF, 1.0F, (field_77697_d.nextFloat() - field_77697_d.nextFloat()) * 0.2F + 1.0F);
      }

      return stack;
   }

   public static void setSmokeColor(ItemStack stack, DyeColor color) {
      stack.func_190925_c("pipe").func_74778_a("color", color.func_176610_l());
   }

   public static void clearSmokeColor(ItemStack stack) {
      setSmokeColor(stack, DyeColor.WHITE);
   }

   public static DyeColor getSmokeColor(ItemStack stack) {
      DyeColor color = getSavedSmokeColor(stack);
      return color != null ? color : DyeColor.WHITE;
   }

   private static DyeColor getSavedSmokeColor(ItemStack stack) {
      CompoundNBT nbt = stack.func_179543_a("pipe");
      return nbt != null && nbt.func_150297_b("color", 8) ? DyeColor.func_204271_a(nbt.func_74779_i("color"), DyeColor.WHITE) : null;
   }

   public static boolean isSmokeDyed(ItemStack stack) {
      DyeColor color = getSavedSmokeColor(stack);
      return color != null && color != DyeColor.WHITE;
   }

   public static void setMagicSmoke(ItemStack stack, boolean flag) {
      stack.func_190925_c("pipe").func_74757_a("magic", flag);
   }

   public static boolean isMagicSmoke(ItemStack stack) {
      CompoundNBT nbt = stack.func_179543_a("pipe");
      return nbt != null ? nbt.func_74767_n("magic") : false;
   }

   @OnlyIn(Dist.CLIENT)
   public void func_77624_a(ItemStack stack, World world, List tooltip, ITooltipFlag flag) {
      DyeColor color = getSmokeColor(stack);
      tooltip.add((new TranslationTextComponent(String.format("%s.%s", this.func_195935_o(), color.func_176762_d()))).func_240699_a_(TextFormatting.GRAY));
      if (isMagicSmoke(stack)) {
         tooltip.add((new TranslationTextComponent(String.format("%s.%s", this.func_195935_o(), "magic"))).func_240699_a_(TextFormatting.GRAY));
      }

   }

   public void func_150895_a(ItemGroup group, NonNullList items) {
      if (this.func_194125_a(group)) {
         DyeColor[] var3 = DyeColor.values();
         int var4 = var3.length;

         int var5;
         DyeColor color;
         ItemStack pipe;
         for(var5 = 0; var5 < var4; ++var5) {
            color = var3[var5];
            pipe = new ItemStack(this);
            setSmokeColor(pipe, color);
            items.add(pipe);
         }

         var3 = DyeColor.values();
         var4 = var3.length;

         for(var5 = 0; var5 < var4; ++var5) {
            color = var3[var5];
            pipe = new ItemStack(this);
            setSmokeColor(pipe, color);
            setMagicSmoke(pipe, true);
            items.add(pipe);
         }
      }

   }
}
