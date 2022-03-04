package lotr.common.enchant;

import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class LOTREnchantmentLooting extends LOTREnchantment {
   public final int lootLevel;

   public LOTREnchantmentLooting(String s, int level) {
      super(s, new LOTREnchantmentType[]{LOTREnchantmentType.TOOL, LOTREnchantmentType.MELEE});
      this.lootLevel = level;
      this.setValueModifier(1.0F + (float)this.lootLevel);
   }

   public String getDescription(ItemStack itemstack) {
      return StatCollector.func_74837_a("lotr.enchant.looting.desc", new Object[]{this.formatAdditiveInt(this.lootLevel)});
   }

   public boolean isBeneficial() {
      return true;
   }

   public boolean isCompatibleWith(LOTREnchantment other) {
      return super.isCompatibleWith(other) && !(other instanceof LOTREnchantmentSilkTouch);
   }
}
