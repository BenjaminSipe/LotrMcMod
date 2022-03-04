package lotr.common.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import java.util.UUID;
import lotr.common.init.LOTRItemGroups;
import lotr.common.init.LOTRMaterial;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IItemTier;
import net.minecraft.item.SwordItem;
import net.minecraft.item.Item.Properties;
import net.minecraftforge.common.ForgeMod;

public class LOTRSwordItem extends SwordItem {
   private Multimap extendedAttributeModifiers;
   protected static final UUID ATTACK_REACH_MODIFIER = UUID.fromString("8e3d7974-9a16-47d5-a6b1-02f248a5aa32");

   public LOTRSwordItem(IItemTier tier) {
      this(tier, 3, -2.4F);
   }

   public LOTRSwordItem(IItemTier tier, int atk, float speed) {
      super(tier, atk, speed, (new Properties()).func_200916_a(LOTRItemGroups.COMBAT));
   }

   public LOTRSwordItem(LOTRMaterial material) {
      this((IItemTier)material.asTool());
   }

   public LOTRSwordItem(LOTRMaterial material, int extraAtk) {
      this(material.asTool(), 3 + extraAtk, -2.4F);
   }

   protected void setupExtendedMeleeAttributes(Builder builder) {
      this.addReachModifier(builder, 0.0D);
   }

   protected final void addReachModifier(Builder builder, double reach) {
      builder.put(ForgeMod.REACH_DISTANCE.get(), new AttributeModifier(ATTACK_REACH_MODIFIER, "Weapon modifier", reach, Operation.ADDITION));
   }

   public Multimap func_111205_h(EquipmentSlotType slot) {
      if (this.extendedAttributeModifiers == null) {
         this.extendedAttributeModifiers = this.buildExtendedAttributeModifiers();
      }

      return slot == EquipmentSlotType.MAINHAND ? this.extendedAttributeModifiers : super.func_111205_h(slot);
   }

   private Multimap buildExtendedAttributeModifiers() {
      Builder builder = ImmutableMultimap.builder();
      Multimap baseSwordModifiers = super.func_111205_h(EquipmentSlotType.MAINHAND);
      builder.putAll(baseSwordModifiers);
      this.setupExtendedMeleeAttributes(builder);
      return builder.build();
   }
}
