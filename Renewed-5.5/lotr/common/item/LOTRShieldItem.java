package lotr.common.item;

import lotr.common.init.LOTRItemGroups;
import lotr.common.init.LOTRMaterial;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ShieldItem;
import net.minecraft.item.Item.Properties;

public class LOTRShieldItem extends ShieldItem {
   public LOTRShieldItem(Properties properties) {
      super(properties);
   }

   public LOTRShieldItem(LOTRMaterial material) {
      this((new Properties()).func_200915_b(calculateShieldDurability(material)).func_200916_a(LOTRItemGroups.COMBAT));
   }

   private static int calculateShieldDurability(LOTRMaterial material) {
      int standardShieldDura = (new ItemStack(Items.field_185159_cQ)).func_77958_k();
      int materialDura = material.asTool().func_200926_a();
      return Math.max(materialDura, standardShieldDura);
   }

   public boolean isShield(ItemStack stack, LivingEntity entity) {
      return true;
   }
}
