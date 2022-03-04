package lotr.common.item;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import lotr.common.init.LOTRItemGroups;
import lotr.common.init.LOTRMaterial;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.HoeItem;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item.Properties;

public class LOTRHoeItem extends HoeItem {
   public LOTRHoeItem(IItemTier tier) {
      this(tier, -1.0F);
   }

   public LOTRHoeItem(IItemTier tier, float speed) {
      super(tier, 0, speed, (new Properties()).func_200916_a(LOTRItemGroups.TOOLS));
   }

   public LOTRHoeItem(LOTRMaterial material) {
      this((IItemTier)material.asTool());
   }

   public Multimap func_111205_h(EquipmentSlotType equipmentSlot) {
      Multimap mapCopy = HashMultimap.create(super.func_111205_h(equipmentSlot));
      if (mapCopy.containsKey(Attributes.field_233823_f_)) {
         Builder builder = ImmutableMultimap.builder();
         builder.put(Attributes.field_233823_f_, new AttributeModifier(field_111210_e, "Tool modifier", 0.0D, Operation.ADDITION));
         mapCopy.removeAll(Attributes.field_233823_f_);
         builder.putAll(mapCopy);
         return builder.build();
      } else {
         return mapCopy;
      }
   }
}
