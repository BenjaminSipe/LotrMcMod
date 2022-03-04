package lotr.common.init;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.IItemTier;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.LazyValue;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.Tags.Items;

public enum LOTRMaterial {
   BRONZE("bronze", 2, 230, 5.0F, 1.5F, 10, 0.5F, SoundEvents.field_187725_r, 0.0F, () -> {
      return Ingredient.func_199805_a(LOTRTags.Items.INGOTS_BRONZE);
   }, new LOTRMaterial.Specials[0]),
   MITHRIL("mithril", 4, 2400, 9.0F, 5.0F, 10, 0.8F, SoundEvents.field_187716_o, 3.0F, () -> {
      return Ingredient.func_199804_a(new IItemProvider[]{(IItemProvider)LOTRItems.MITHRIL_INGOT.get()});
   }, () -> {
      return Ingredient.func_199804_a(new IItemProvider[]{(IItemProvider)LOTRItems.MITHRIL_MAIL.get()});
   }, new LOTRMaterial.Specials[0]),
   FUR("fur", 0, 180, 0.0F, 0.0F, 8, 0.4F, SoundEvents.field_187728_s, 0.0F, () -> {
      return Ingredient.func_199804_a(new IItemProvider[]{(IItemProvider)LOTRItems.FUR.get()});
   }, new LOTRMaterial.Specials[0]),
   BONE("bone", 0, 150, 0.0F, 0.0F, 10, 0.3F, SoundEvents.field_187719_p, 0.0F, () -> {
      return Ingredient.func_199805_a(Items.BONES);
   }, new LOTRMaterial.Specials[0]),
   GONDOR("gondor", 2, 450, 6.0F, 2.5F, 10, 0.6F, SoundEvents.field_187725_r, 0.0F, () -> {
      return Ingredient.func_199805_a(Items.INGOTS_IRON);
   }, new LOTRMaterial.Specials[0]),
   DOL_AMROTH("dol_amroth", 2, 500, 6.0F, 3.0F, 10, 0.6F, SoundEvents.field_187725_r, 0.0F, () -> {
      return Ingredient.func_199805_a(Items.INGOTS_IRON);
   }, new LOTRMaterial.Specials[0]),
   ROHAN("rohan", 2, 300, 6.0F, 2.5F, 10, 0.5F, SoundEvents.field_187713_n, 0.0F, () -> {
      return Ingredient.func_199805_a(Items.INGOTS_IRON);
   }, new LOTRMaterial.Specials[0]),
   ROHAN_MARSHAL("rohan_marshal", 2, 400, 6.0F, 3.0F, 10, 0.6F, SoundEvents.field_187713_n, 0.0F, () -> {
      return Ingredient.func_199805_a(Items.INGOTS_IRON);
   }, new LOTRMaterial.Specials[0]),
   DUNLENDING("dunlending", 2, 250, 6.0F, 2.0F, 8, 0.5F, SoundEvents.field_187713_n, 0.0F, () -> {
      return Ingredient.func_199805_a(Items.INGOTS_IRON);
   }, new LOTRMaterial.Specials[0]),
   DALE("dale", 2, 300, 6.0F, 2.5F, 10, 0.6F, SoundEvents.field_187713_n, 0.0F, () -> {
      return Ingredient.func_199805_a(Items.INGOTS_IRON);
   }, new LOTRMaterial.Specials[0]),
   RANGER_NORTH("ranger_north", 2, 350, 6.0F, 2.5F, 12, 0.48F, SoundEvents.field_187728_s, 0.0F, () -> {
      return Ingredient.func_199805_a(Items.INGOTS_IRON);
   }, () -> {
      return Ingredient.func_199805_a(Items.LEATHER);
   }, new LOTRMaterial.Specials[0]),
   RANGER_ITHILIEN("ranger_ithilien", 2, 350, 6.0F, 2.5F, 12, 0.48F, SoundEvents.field_187728_s, 0.0F, () -> {
      return Ingredient.func_199805_a(Items.INGOTS_IRON);
   }, () -> {
      return Ingredient.func_199805_a(Items.LEATHER);
   }, new LOTRMaterial.Specials[0]),
   ARNOR("arnor", 2, 500, 6.0F, 3.0F, 10, 0.6F, SoundEvents.field_187725_r, 0.0F, () -> {
      return Ingredient.func_199805_a(Items.INGOTS_IRON);
   }, new LOTRMaterial.Specials[0]),
   DORWINION("dorwinion", 2, 400, 6.0F, 2.5F, 10, 0.5F, SoundEvents.field_187725_r, 0.0F, () -> {
      return Ingredient.func_199805_a(Items.INGOTS_IRON);
   }, new LOTRMaterial.Specials[0]),
   HARAD("harad", 2, 300, 6.0F, 2.5F, 10, 0.5F, SoundEvents.field_187713_n, 0.0F, () -> {
      return Ingredient.func_199805_a(LOTRTags.Items.INGOTS_BRONZE);
   }, new LOTRMaterial.Specials[0]),
   UMBAR("umbar", 2, 450, 6.0F, 2.5F, 10, 0.6F, SoundEvents.field_187725_r, 0.0F, () -> {
      return Ingredient.func_199805_a(Items.INGOTS_IRON);
   }, new LOTRMaterial.Specials[0]),
   HARNENNOR("harnennor", 2, 250, 6.0F, 2.0F, 8, 0.5F, SoundEvents.field_187713_n, 0.0F, () -> {
      return Ingredient.func_199805_a(LOTRTags.Items.INGOTS_BRONZE);
   }, new LOTRMaterial.Specials[0]),
   LINDON("lindon", 2, 700, 8.0F, 3.0F, 15, 0.6F, SoundEvents.field_187725_r, 0.0F, () -> {
      return Ingredient.func_199804_a(new IItemProvider[]{(IItemProvider)LOTRItems.ELVEN_STEEL_INGOT.get()});
   }, new LOTRMaterial.Specials[0]),
   RIVENDELL("rivendell", 2, 700, 8.0F, 3.0F, 15, 0.6F, SoundEvents.field_187725_r, 0.0F, () -> {
      return Ingredient.func_199804_a(new IItemProvider[]{(IItemProvider)LOTRItems.ELVEN_STEEL_INGOT.get()});
   }, new LOTRMaterial.Specials[0]),
   GALADHRIM("galadhrim", 2, 600, 7.0F, 3.0F, 15, 0.6F, SoundEvents.field_187725_r, 0.0F, () -> {
      return Ingredient.func_199804_a(new IItemProvider[]{(IItemProvider)LOTRItems.ELVEN_STEEL_INGOT.get()});
   }, new LOTRMaterial.Specials[0]),
   MALLORN("mallorn", 1, 200, 4.0F, 1.5F, 15, 0.0F, SoundEvents.field_187719_p, 0.0F, () -> {
      return Ingredient.func_199804_a(new IItemProvider[]{(IItemProvider)LOTRItems.MALLORN_PLANKS.get()});
   }, new LOTRMaterial.Specials[0]),
   WOOD_ELVEN("wood_elven", 2, 500, 9.0F, 3.0F, 15, 0.6F, SoundEvents.field_187725_r, 0.0F, () -> {
      return Ingredient.func_199804_a(new IItemProvider[]{(IItemProvider)LOTRItems.ELVEN_STEEL_INGOT.get()});
   }, new LOTRMaterial.Specials[0]),
   DORWINION_ELVEN("dorwinion_elven", 2, 500, 7.0F, 3.0F, 15, 0.6F, SoundEvents.field_187725_r, 0.0F, () -> {
      return Ingredient.func_199804_a(new IItemProvider[]{(IItemProvider)LOTRItems.ELVEN_STEEL_INGOT.get()});
   }, new LOTRMaterial.Specials[0]),
   DWARVEN("dwarven", 3, 700, 7.0F, 3.0F, 10, 0.7F, SoundEvents.field_187725_r, 0.0F, () -> {
      return Ingredient.func_199804_a(new IItemProvider[]{(IItemProvider)LOTRItems.DWARVEN_STEEL_INGOT.get()});
   }, new LOTRMaterial.Specials[0]),
   BLUE_DWARVEN("blue_dwarven", 3, 650, 7.0F, 3.0F, 12, 0.7F, SoundEvents.field_187725_r, 0.0F, () -> {
      return Ingredient.func_199804_a(new IItemProvider[]{(IItemProvider)LOTRItems.DWARVEN_STEEL_INGOT.get()});
   }, new LOTRMaterial.Specials[0]),
   MORDOR("mordor", 2, 400, 6.0F, 2.5F, 7, 0.6F, SoundEvents.field_187713_n, 0.0F, () -> {
      return Ingredient.func_199804_a(new IItemProvider[]{(IItemProvider)LOTRItems.ORC_STEEL_INGOT.get()});
   }, new LOTRMaterial.Specials[]{LOTRMaterial.Specials.MAN_FLESH}),
   URUK("uruk", 2, 550, 6.0F, 3.0F, 5, 0.7F, SoundEvents.field_187725_r, 0.0F, () -> {
      return Ingredient.func_199804_a(new IItemProvider[]{(IItemProvider)LOTRItems.URUK_STEEL_INGOT.get()});
   }, new LOTRMaterial.Specials[]{LOTRMaterial.Specials.MAN_FLESH}),
   COSMETIC("cosmetic", 0, 0, 0.0F, 0.0F, 0, 0.0F, SoundEvents.field_187719_p, 0.0F, () -> {
      return Ingredient.field_193370_a;
   }, new LOTRMaterial.Specials[]{LOTRMaterial.Specials.UNDAMAGEABLE});

   private String materialName;
   private LOTRMaterial.AsTool asTool;
   private final int harvestLevel;
   private final int maxUses;
   private final float efficiency;
   private final float attackDamage;
   private final int enchantability;
   private final LazyValue toolRepairMaterial;
   private LOTRMaterial.AsArmor asArmor;
   private static final int[] ARMOR_DURABILITY_ARRAY = new int[]{13, 15, 16, 11};
   private final int armorDurabilityFactor;
   private final int[] armorProtectionArray;
   private final SoundEvent armorSoundEvent;
   private final float toughness;
   private final LazyValue armorRepairMaterial;
   private final Set specialProperties;

   private LOTRMaterial(String name, int lvl, int uses, float eff, float atk, int ench, float pr, SoundEvent sound, float tough, Supplier repair, LOTRMaterial.Specials... specs) {
      this(name, lvl, uses, eff, atk, ench, pr, sound, tough, repair, repair, specs);
   }

   private LOTRMaterial(String name, int lvl, int uses, float eff, float atk, int ench, float pr, SoundEvent sound, float tough, Supplier repair, Supplier armorRepair, LOTRMaterial.Specials... specs) {
      this.specialProperties = new HashSet();
      this.materialName = "lotr:" + name;
      this.harvestLevel = lvl;
      this.maxUses = uses;
      this.efficiency = eff;
      this.attackDamage = atk;
      this.enchantability = ench;
      this.toolRepairMaterial = new LazyValue(repair);
      this.armorDurabilityFactor = Math.round((float)this.maxUses * 0.06F);
      this.armorProtectionArray = LOTRMaterial.ArmorHelper.getArmorProtectionArray(pr);
      this.armorSoundEvent = sound;
      this.toughness = tough;
      this.armorRepairMaterial = new LazyValue(armorRepair);
      LOTRMaterial.Specials[] var15 = specs;
      int var16 = specs.length;

      for(int var17 = 0; var17 < var16; ++var17) {
         LOTRMaterial.Specials s = var15[var17];
         this.specialProperties.add(s);
      }

   }

   public LOTRMaterial.AsTool asTool() {
      if (this.asTool == null) {
         this.asTool = new LOTRMaterial.AsTool(this);
      }

      return this.asTool;
   }

   public LOTRMaterial.AsArmor asArmor() {
      if (this.asArmor == null) {
         this.asArmor = new LOTRMaterial.AsArmor(this);
      }

      return this.asArmor;
   }

   public static Optional ifLOTRToolMaterial(IItemTier material) {
      return material instanceof LOTRMaterial.AsTool ? Optional.of((LOTRMaterial.AsTool)material) : Optional.empty();
   }

   public static Optional ifLOTRArmorMaterial(IArmorMaterial material) {
      return material instanceof LOTRMaterial.AsArmor ? Optional.of((LOTRMaterial.AsArmor)material) : Optional.empty();
   }

   public static class AsArmor implements IArmorMaterial {
      private final LOTRMaterial materialReference;

      public AsArmor(LOTRMaterial m) {
         this.materialReference = m;
      }

      public int func_200896_a(EquipmentSlotType slot) {
         return LOTRMaterial.ARMOR_DURABILITY_ARRAY[slot.func_188454_b()] * this.materialReference.armorDurabilityFactor;
      }

      public int func_200902_b(EquipmentSlotType slot) {
         return this.materialReference.armorProtectionArray[slot.func_188454_b()];
      }

      public int func_200900_a() {
         return this.materialReference.enchantability;
      }

      public SoundEvent func_200899_b() {
         return this.materialReference.armorSoundEvent;
      }

      public Ingredient func_200898_c() {
         return (Ingredient)this.materialReference.armorRepairMaterial.func_179281_c();
      }

      @OnlyIn(Dist.CLIENT)
      public String func_200897_d() {
         return this.materialReference.materialName;
      }

      public float func_200901_e() {
         return this.materialReference.toughness;
      }

      public boolean isUndamageable() {
         return this.materialReference.specialProperties.contains(LOTRMaterial.Specials.UNDAMAGEABLE);
      }

      public float func_230304_f_() {
         return 0.0F;
      }
   }

   public static class AsTool implements IItemTier {
      private final LOTRMaterial materialReference;

      public AsTool(LOTRMaterial m) {
         this.materialReference = m;
      }

      public int func_200926_a() {
         return this.materialReference.maxUses;
      }

      public float func_200928_b() {
         return this.materialReference.efficiency;
      }

      public float func_200929_c() {
         return this.materialReference.attackDamage;
      }

      public int func_200925_d() {
         return this.materialReference.harvestLevel;
      }

      public int func_200927_e() {
         return this.materialReference.enchantability;
      }

      public Ingredient func_200924_f() {
         return (Ingredient)this.materialReference.toolRepairMaterial.func_179281_c();
      }

      public boolean canHarvestManFlesh() {
         return this.materialReference.specialProperties.contains(LOTRMaterial.Specials.MAN_FLESH);
      }
   }

   private static class ArmorHelper {
      private static final float[] ARMOR_PART_WEIGHTING = new float[]{0.14F, 0.32F, 0.4F, 0.14F};
      private static final float FULL_ARMOR_PROTECTION = 25.0F;

      public static int[] getArmorProtectionArray(float protection) {
         int[] armorArray = new int[ARMOR_PART_WEIGHTING.length];

         for(int i = 0; i < armorArray.length; ++i) {
            armorArray[i] = Math.round(ARMOR_PART_WEIGHTING[i] * protection * 25.0F);
         }

         return armorArray;
      }
   }

   public static enum Specials {
      MAN_FLESH,
      UNDAMAGEABLE;
   }
}
