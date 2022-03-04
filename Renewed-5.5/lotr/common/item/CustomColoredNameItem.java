package lotr.common.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item.Properties;
import net.minecraft.util.text.Color;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TranslationTextComponent;

public class CustomColoredNameItem extends Item {
   private final int nameColor;

   public CustomColoredNameItem(Properties properties, int nameColor) {
      super(properties);
      this.nameColor = nameColor;
   }

   public ITextComponent func_200295_i(ItemStack stack) {
      IFormattableTextComponent name = new TranslationTextComponent(this.func_77667_c(stack));
      name.func_240703_c_(Style.field_240709_b_.func_240718_a_(Color.func_240743_a_(this.nameColor)));
      return name;
   }
}
