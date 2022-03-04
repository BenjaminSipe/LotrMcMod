package lotr.common.item;

import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import lotr.common.LOTRMod;
import lotr.common.data.LOTRLevelData;
import lotr.common.data.MiscDataModule;
import lotr.common.init.LOTRItemGroups;
import lotr.common.util.CalendarUtil;
import lotr.common.util.LOTRUtil;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.Items;
import net.minecraft.item.UseAction;
import net.minecraft.item.Item.Properties;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.potion.PotionUtils;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class VesselDrinkItem extends Item {
   public final float drinkAlcoholicity;
   private final int drinkFoodRestore;
   private final float drinkSaturation;
   public final boolean hasPotencies;
   private final float drinkDamage;
   private final List drinkEffects;

   protected VesselDrinkItem(float alc, int food, float sat, boolean hasPots, float dmg, List effs) {
      super((new Properties()).func_200917_a(1).func_200916_a(LOTRItemGroups.FOOD));
      this.drinkAlcoholicity = alc;
      this.drinkFoodRestore = food;
      this.drinkSaturation = sat;
      this.hasPotencies = hasPots;
      this.drinkDamage = dmg;
      this.drinkEffects = effs;
   }

   public static VesselDrinkItem newAlcohol(float alc, int food, float sat) {
      return new VesselDrinkItem(alc, food, sat, true, 0.0F, ImmutableList.of());
   }

   public static VesselDrinkItem newBasic(int food, float sat) {
      return new VesselDrinkItem(0.0F, food, sat, false, 0.0F, ImmutableList.of());
   }

   public static VesselDrinkItem newEffects(int food, float sat, EffectInstance... effs) {
      return new VesselDrinkItem(0.0F, food, sat, true, 0.0F, Arrays.asList(effs));
   }

   public static VesselDrinkItem newEffectsDamage(int food, float sat, float dmg, EffectInstance... effs) {
      return new VesselDrinkItem(0.0F, food, sat, true, dmg, Arrays.asList(effs));
   }

   public static VesselDrinkItem newEffectsAlcohol(float alc, int food, float sat, EffectInstance... effs) {
      return new VesselDrinkItem(alc, food, sat, true, 0.0F, Arrays.asList(effs));
   }

   public static VesselDrinkItem.Potency getPotency(ItemStack stack) {
      CompoundNBT nbt = stack.func_179543_a("vessel");
      return nbt != null && nbt.func_150297_b("potency", 8) ? VesselDrinkItem.Potency.forName(nbt.func_74779_i("potency")) : VesselDrinkItem.Potency.MODERATE;
   }

   public static void setPotency(ItemStack stack, VesselDrinkItem.Potency pot) {
      stack.func_190925_c("vessel").func_74778_a("potency", pot.getCodeName());
   }

   public static VesselType getVessel(ItemStack stack) {
      CompoundNBT nbt = stack.func_179543_a("vessel");
      return nbt != null && nbt.func_150297_b("type", 8) ? VesselType.forName(nbt.func_74779_i("type")) : VesselType.WOODEN_MUG;
   }

   public static void setVessel(ItemStack stack, VesselType ves) {
      stack.func_190925_c("vessel").func_74778_a("type", ves.getCodeName());
   }

   public ItemStack getContainerItem(ItemStack stack) {
      return getVessel(stack).createEmpty();
   }

   public boolean hasContainerItem(ItemStack stack) {
      return true;
   }

   public ActionResult func_77659_a(World world, PlayerEntity player, Hand hand) {
      ItemStack heldItem = player.func_184586_b(hand);
      if (this.canBeginDrinking(player, heldItem)) {
         player.func_184598_c(hand);
         return ActionResult.func_226249_b_(heldItem);
      } else {
         return ActionResult.func_226251_d_(heldItem);
      }
   }

   public boolean canBeginDrinking(PlayerEntity player, ItemStack stack) {
      boolean alwaysDrinkable = this.drinkAlcoholicity > 0.0F || !this.drinkEffects.isEmpty();
      return player.func_71043_e(alwaysDrinkable);
   }

   public ItemStack func_77654_b(ItemStack stack, World world, LivingEntity entity) {
      PlayerEntity asPlayer = entity instanceof PlayerEntity ? (PlayerEntity)entity : null;
      if (asPlayer instanceof ServerPlayerEntity) {
         CriteriaTriggers.field_193138_y.func_193148_a((ServerPlayerEntity)asPlayer, stack);
      }

      VesselType vessel = getVessel(stack);
      ItemStack emptyVessel = vessel.createEmpty();
      float benefitEffectiveness = this.getBenefitEffectivenessFor(entity);
      float healF = (float)this.drinkFoodRestore * benefitEffectiveness;
      float sat = this.drinkSaturation * benefitEffectiveness;
      if (this.hasPotencies) {
         VesselDrinkItem.Potency potency = getPotency(stack);
         healF *= potency.foodMultiplier;
         sat *= potency.foodMultiplier;
      }

      int heal = Math.round(healF);
      if (asPlayer != null) {
         asPlayer.func_71024_bL().func_75122_a(heal, sat);
         asPlayer.func_71029_a(Stats.field_75929_E.func_199076_b(this));
      } else {
         entity.func_70691_i((float)heal);
      }

      if (!world.field_72995_K && this.drinkAlcoholicity > 0.0F) {
         float alcStrength = this.getAlcoholicityForStrength(stack);
         Optional playerMiscData = Optional.ofNullable(asPlayer).map((p) -> {
            return LOTRLevelData.sidedInstance((IWorldReader)world).getData(p).getMiscData();
         });
         int tolerance = (Integer)playerMiscData.map(MiscDataModule::getAlcoholTolerance).orElse(0);
         if (tolerance > 0) {
            float f = (float)Math.pow(0.99D, (double)tolerance);
            alcStrength *= f;
         }

         if (world.field_73012_v.nextFloat() < alcStrength) {
            int duration = (int)(60.0F * (1.0F + world.field_73012_v.nextFloat() * 0.5F) * alcStrength);
            if (duration >= 1) {
               int durationTicks = duration * 20;
               entity.func_195064_c(new EffectInstance(Effects.field_76431_k, durationTicks));
               int toleranceAdd = Math.round((float)duration / 20.0F);
               int newTolerance = tolerance + toleranceAdd;
               playerMiscData.ifPresent((miscData) -> {
                  miscData.setAlcoholTolerance(newTolerance);
               });
            }
         }
      }

      if (!world.field_72995_K && !this.drinkEffects.isEmpty() && this.shouldApplyPotionEffects(stack, entity)) {
         List effects = this.convertPotionEffectsForStrengthAndEntity(stack, entity);
         Iterator var20 = effects.iterator();

         while(var20.hasNext()) {
            EffectInstance effect = (EffectInstance)var20.next();
            if (effect.func_188419_a().func_76403_b()) {
               effect.func_188419_a().func_180793_a(asPlayer, asPlayer, entity, effect.func_76458_c(), 1.0D);
            } else {
               entity.func_195064_c(new EffectInstance(effect));
            }
         }
      }

      if (this.drinkDamage > 0.0F) {
         entity.func_70097_a(DamageSource.field_76376_m, this.getDrinkDamageForStrength(stack));
      }

      if (asPlayer == null || !asPlayer.field_71075_bZ.field_75098_d) {
         stack.func_190918_g(1);
         if (stack.func_190926_b()) {
            return emptyVessel;
         }

         if (asPlayer != null) {
            asPlayer.field_71071_by.func_70441_a(emptyVessel);
         }
      }

      return stack;
   }

   protected float getBenefitEffectivenessFor(LivingEntity entity) {
      return 1.0F;
   }

   public UseAction func_77661_b(ItemStack stack) {
      return UseAction.DRINK;
   }

   public int func_77626_a(ItemStack stack) {
      return 32;
   }

   private float getAlcoholicityForStrength(ItemStack stack) {
      return this.drinkAlcoholicity * getPotency(stack).alcMultiplier;
   }

   protected boolean shouldApplyPotionEffects(ItemStack stack, LivingEntity entity) {
      return this.getBenefitEffectivenessFor(entity) > 0.0F;
   }

   private List convertPotionEffectsForStrengthAndEntity(ItemStack stack, LivingEntity entity) {
      float strength = 1.0F;
      if (this.hasPotencies) {
         strength = getPotency(stack).effectsMultiplier;
      }

      float benefitEffectiveness = entity != null ? this.getBenefitEffectivenessFor(entity) : 1.0F;
      List effects = new ArrayList();
      Iterator var6 = this.drinkEffects.iterator();

      while(var6.hasNext()) {
         EffectInstance base = (EffectInstance)var6.next();
         float effectDurationFactor = strength;
         if (base.func_188419_a().func_188408_i()) {
            effectDurationFactor = strength * benefitEffectiveness;
         }

         int duration = (int)((float)base.func_76459_b() * effectDurationFactor);
         EffectInstance modified = new EffectInstance(base.func_188419_a(), duration, base.func_76458_c(), base.func_82720_e(), base.func_188418_e());
         effects.add(modified);
      }

      return effects;
   }

   private float getDrinkDamageForStrength(ItemStack stack) {
      float dmg = this.drinkDamage;
      if (this.hasPotencies) {
         dmg *= getPotency(stack).damageMultiplier;
      }

      return dmg;
   }

   public ActionResultType func_195939_a(ItemUseContext context) {
      ItemStack drinkItem = context.func_195996_i();
      VesselType vessel = getVessel(drinkItem);
      Item emptyVesselItem = vessel.createEmpty().func_77973_b();
      if (emptyVesselItem instanceof IEmptyVesselItem) {
         ActionResultType vesselPlaceResult = ((IEmptyVesselItem)emptyVesselItem).tryToPlaceVesselBlock(context);
         if (vesselPlaceResult.func_226246_a_()) {
            return vesselPlaceResult;
         }
      }

      return super.func_195939_a(context);
   }

   public ITextComponent func_200295_i(ItemStack stack) {
      return (ITextComponent)(CalendarUtil.isAprilFools() ? new StringTextComponent("Hooch") : super.func_200295_i(stack));
   }

   @OnlyIn(Dist.CLIENT)
   public void func_77624_a(ItemStack stack, World world, List tooltip, ITooltipFlag flag) {
      LivingEntity relevantEntity = LOTRMod.PROXY.getClientPlayer();
      IFormattableTextComponent displayDmg;
      if (this.hasPotencies) {
         VesselDrinkItem.Potency potency = getPotency(stack);
         displayDmg = potency.getDisplayName().func_240699_a_(TextFormatting.GRAY);
         tooltip.add(displayDmg);
         if (this.drinkAlcoholicity > 0.0F) {
            float alc = this.getAlcoholicityForStrength(stack) * 10.0F;
            TextFormatting color = TextFormatting.GREEN;
            if (alc < 2.0F) {
               color = TextFormatting.GREEN;
            } else if (alc < 5.0F) {
               color = TextFormatting.YELLOW;
            } else if (alc < 10.0F) {
               color = TextFormatting.GOLD;
            } else if (alc < 20.0F) {
               color = TextFormatting.RED;
            } else {
               color = TextFormatting.DARK_RED;
            }

            ITextComponent displayAlc = (new TranslationTextComponent("item.lotr.drink.alcoholicity", new Object[]{alc})).func_240699_a_(color);
            tooltip.add(displayAlc);
         }
      }

      if (this.drinkDamage > 0.0F) {
         float dmg = this.getDrinkDamageForStrength(stack);
         displayDmg = (new TranslationTextComponent("item.lotr.drink.damage", new Object[]{dmg})).func_240699_a_(TextFormatting.RED);
         tooltip.add(displayDmg);
      }

      this.addPreEffectsTooltip(stack, world, tooltip, flag);
      addPotionEffectsToTooltip(stack, tooltip, flag, this.convertPotionEffectsForStrengthAndEntity(stack, relevantEntity));
   }

   @OnlyIn(Dist.CLIENT)
   protected void addPreEffectsTooltip(ItemStack stack, World world, List tooltip, ITooltipFlag flag) {
   }

   public static void addPotionEffectsToTooltip(ItemStack stack, List tooltip, ITooltipFlag flag, List itemEffects) {
      if (!itemEffects.isEmpty()) {
         ItemStack potionEquivalent = new ItemStack(Items.field_151068_bn);
         PotionUtils.func_185184_a(potionEquivalent, itemEffects);
         List effectTooltips = new ArrayList();
         PotionUtils.func_185182_a(potionEquivalent, effectTooltips, 1.0F);
         tooltip.addAll(effectTooltips);
      }

   }

   public void func_150895_a(ItemGroup group, NonNullList items) {
      if (this.func_194125_a(group)) {
         VesselType[] displayVessels = new VesselType[]{VesselType.WOODEN_MUG};
         if (group == null || group.hasSearchBar()) {
            displayVessels = VesselType.values();
         }

         VesselType[] var4 = displayVessels;
         int var5 = displayVessels.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            VesselType ves = var4[var6];
            if (this.hasPotencies) {
               VesselDrinkItem.Potency[] var13 = VesselDrinkItem.Potency.values();
               int var9 = var13.length;

               for(int var10 = 0; var10 < var9; ++var10) {
                  VesselDrinkItem.Potency pot = var13[var10];
                  ItemStack stack = new ItemStack(this);
                  setPotency(stack, pot);
                  setVessel(stack, ves);
                  items.add(stack);
               }
            } else {
               ItemStack stack = new ItemStack(this);
               setVessel(stack, ves);
               items.add(stack);
            }
         }
      }

   }

   public static enum Potency {
      WEAK(0, "weak", 0.25F, 0.5F),
      LIGHT(1, "light", 0.5F, 0.75F),
      MODERATE(2, "moderate", 1.0F, 1.0F),
      STRONG(3, "strong", 2.0F, 1.25F),
      POTENT(4, "potent", 3.0F, 1.5F);

      private static final Map LEVEL_LOOKUP = LOTRUtil.createKeyedEnumMap(values(), (potency) -> {
         return potency.level;
      });
      private static final Map NAME_LOOKUP = LOTRUtil.createKeyedEnumMap(values(), VesselDrinkItem.Potency::getCodeName);
      public final int level;
      private static int minLevel;
      private static int maxLevel;
      public final String name;
      public final float alcMultiplier;
      public final float effectsMultiplier;
      public final float damageMultiplier;
      public final float foodMultiplier;

      private Potency(int i, String s, float alc, float food) {
         this.level = i;
         this.name = s;
         this.alcMultiplier = alc;
         this.effectsMultiplier = alc;
         this.damageMultiplier = alc;
         this.foodMultiplier = food;
         this.recache();
      }

      private void recache() {
         minLevel = Math.min(minLevel, this.level);
         maxLevel = Math.max(maxLevel, this.level);
      }

      public boolean isMax() {
         return this.level == maxLevel;
      }

      public boolean isMin() {
         return this.level == minLevel;
      }

      public static VesselDrinkItem.Potency getMax() {
         return forLevel(maxLevel);
      }

      public static VesselDrinkItem.Potency getMin() {
         return forLevel(minLevel);
      }

      public VesselDrinkItem.Potency getNext() {
         return this.isMax() ? this : forLevel(this.level + 1);
      }

      public VesselDrinkItem.Potency getPrev() {
         return this.isMin() ? this : forLevel(this.level - 1);
      }

      public String getCodeName() {
         return this.name;
      }

      public IFormattableTextComponent getDisplayName() {
         return new TranslationTextComponent("item.lotr.drink." + this.name);
      }

      public static VesselDrinkItem.Potency forLevel(int level) {
         return (VesselDrinkItem.Potency)LEVEL_LOOKUP.getOrDefault(level, MODERATE);
      }

      public static VesselDrinkItem.Potency forName(String name) {
         return (VesselDrinkItem.Potency)NAME_LOOKUP.getOrDefault(name, MODERATE);
      }

      public static VesselDrinkItem.Potency randomForNPC(Random rand) {
         int i = rand.nextInt(3);
         if (i == 0) {
            return LIGHT;
         } else {
            return i == 1 ? MODERATE : STRONG;
         }
      }
   }
}
