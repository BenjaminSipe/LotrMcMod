package lotr.common.fac;

import java.util.Collections;
import java.util.List;
import lotr.common.data.LOTRLevelData;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class FoodAlignmentHelper {
   public static final FactionType[] EVIL_CREATURE_FACTION_TYPES;

   public static boolean hasAnyPositiveAlignment(LivingEntity entity, FactionType... alignedTypes) {
      List factions = getFactionsOfTypes(entity.field_70170_p, alignedTypes);
      if (entity instanceof PlayerEntity) {
         PlayerEntity player = (PlayerEntity)entity;
         return LOTRLevelData.getSidedData(player).getAlignmentData().hasAlignmentWithAny(factions, AlignmentPredicates.POSITIVE);
      } else {
         return isNonPlayerEntityAlignedToAny(entity, factions);
      }
   }

   private static List getFactionsOfTypes(World world, FactionType... alignedTypes) {
      FactionSettings facSettings = FactionSettingsManager.sidedInstance((IWorldReader)world).getCurrentLoadedFactions();
      return facSettings != null ? facSettings.getFactionsOfTypes(alignedTypes) : Collections.emptyList();
   }

   private static boolean isNonPlayerEntityAlignedToAny(LivingEntity entity, List factions) {
      return factions.contains(EntityFactionHelper.getFaction(entity));
   }

   public static boolean isPledgedOrEntityAlignedToAny(LivingEntity entity, FactionType[] alignedTypes) {
      List factions = getFactionsOfTypes(entity.field_70170_p, alignedTypes);
      if (!(entity instanceof PlayerEntity)) {
         return isNonPlayerEntityAlignedToAny(entity, factions);
      } else {
         PlayerEntity player = (PlayerEntity)entity;
         Faction pledged = LOTRLevelData.getSidedData(player).getAlignmentData().getPledgeFaction();
         return pledged != null && factions.contains(pledged);
      }
   }

   public static float getHighestAlignmentProportion(LivingEntity entity, float fullAlignment, FactionType... alignedTypes) {
      List factions = getFactionsOfTypes(entity.field_70170_p, alignedTypes);
      if (entity instanceof PlayerEntity) {
         PlayerEntity player = (PlayerEntity)entity;
         float highestAlign = LOTRLevelData.getSidedData(player).getAlignmentData().getHighestAlignmentAmong(factions);
         float prop = highestAlign / fullAlignment;
         return MathHelper.func_76131_a(prop, 0.0F, 1.0F);
      } else {
         return isNonPlayerEntityAlignedToAny(entity, factions) ? 1.0F : 0.0F;
      }
   }

   public static ItemStack onFoodEatenWithoutRestore(ItemStack stack, World world, LivingEntity entity) {
      world.func_184148_a((PlayerEntity)null, entity.func_226277_ct_(), entity.func_226278_cu_(), entity.func_226281_cx_(), entity.func_213353_d(stack), SoundCategory.NEUTRAL, 1.0F, 1.0F + (world.field_73012_v.nextFloat() - world.field_73012_v.nextFloat()) * 0.4F);
      if (!(entity instanceof PlayerEntity) || !((PlayerEntity)entity).field_71075_bZ.field_75098_d) {
         stack.func_190918_g(1);
      }

      if (entity instanceof PlayerEntity) {
         PlayerEntity player = (PlayerEntity)entity;
         player.func_71029_a(Stats.field_75929_E.func_199076_b(stack.func_77973_b()));
         world.func_184148_a((PlayerEntity)null, player.func_226277_ct_(), player.func_226278_cu_(), player.func_226281_cx_(), SoundEvents.field_187739_dZ, SoundCategory.PLAYERS, 0.5F, world.field_73012_v.nextFloat() * 0.1F + 0.9F);
         if (player instanceof ServerPlayerEntity) {
            CriteriaTriggers.field_193138_y.func_193148_a((ServerPlayerEntity)player, stack);
         }
      }

      return stack;
   }

   static {
      EVIL_CREATURE_FACTION_TYPES = new FactionType[]{FactionType.ORC, FactionType.TROLL};
   }
}
