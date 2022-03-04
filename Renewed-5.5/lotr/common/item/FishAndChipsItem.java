package lotr.common.item;

import java.util.Random;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item.Properties;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class FishAndChipsItem extends HeavyFoodSubtitledItem {
   private static final Random RAND = new Random();

   public FishAndChipsItem(Properties properties) {
      super(properties);
   }

   public ITextComponent func_200295_i(ItemStack stack) {
      ITextComponent name = super.func_200295_i(stack);
      if (name.getString().equalsIgnoreCase("Fish and Chips")) {
         long l = System.currentTimeMillis() / 10000L;
         RAND.setSeed(709247283937L * (l + 31L) + 17L);
         if (RAND.nextInt(360) == 0) {
            return (new StringTextComponent("Chips and Fish")).func_240703_c_(name.func_150256_b());
         }
      }

      return name;
   }
}
