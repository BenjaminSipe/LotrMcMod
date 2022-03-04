package lotr.common.item;

import java.util.List;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item.Properties;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SubtitledItem extends Item {
   public SubtitledItem(Properties properties) {
      super(properties);
   }

   @OnlyIn(Dist.CLIENT)
   public void func_77624_a(ItemStack stack, World world, List tooltip, ITooltipFlag flag) {
      tooltip.add((new TranslationTextComponent(this.func_77658_a() + ".subtitle")).func_240699_a_(TextFormatting.GRAY));
   }
}
