package lotr.client.event;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import lotr.common.item.ItemOwnership;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;

public class ItemTooltipFeatures {
   private static final Minecraft MC = Minecraft.func_71410_x();

   public static void handleTooltipEvent(ItemTooltipEvent event) {
      ItemStack itemstack = event.getItemStack();
      List tooltip = event.getToolTip();
      PlayerEntity player = event.getPlayer();
      FontRenderer fontRenderer = MC.field_71466_p;
      addItemOwnership(itemstack, tooltip, fontRenderer);
   }

   private static void addItemOwnership(ItemStack itemstack, List tooltip, FontRenderer fontRenderer) {
      ITextComponent currentOwner = ItemOwnership.getCurrentOwner(itemstack);
      if (currentOwner != null) {
         tooltip.add(StringTextComponent.field_240750_d_);
         ITextComponent ownerText = (new TranslationTextComponent("item.lotr.generic.currentOwner", new Object[]{currentOwner})).func_240699_a_(TextFormatting.GRAY);
         tooltip.add(ownerText);
      }

      List previousOwners = ItemOwnership.getPreviousOwners(itemstack);
      if (!previousOwners.isEmpty()) {
         tooltip.add(StringTextComponent.field_240750_d_);
         List ownerLines = new ArrayList();
         IFormattableTextComponent beginList;
         if (previousOwners.size() == 1) {
            beginList = (new TranslationTextComponent("item.lotr.generic.previousOwner", new Object[]{previousOwners.get(0)})).func_240699_a_(TextFormatting.ITALIC).func_240699_a_(TextFormatting.GRAY);
            ownerLines.add(beginList);
         } else {
            beginList = (new TranslationTextComponent("item.lotr.generic.previousOwnerList")).func_240699_a_(TextFormatting.ITALIC).func_240699_a_(TextFormatting.GRAY);
            ownerLines.add(beginList);
            Iterator var7 = previousOwners.iterator();

            while(var7.hasNext()) {
               ITextComponent previousOwner = (ITextComponent)var7.next();
               ITextComponent previousOwnerText = (new TranslationTextComponent("%s", new Object[]{previousOwner})).func_240699_a_(TextFormatting.ITALIC).func_240699_a_(TextFormatting.GRAY);
               ownerLines.add(previousOwnerText);
            }
         }

         tooltip.addAll(ownerLines);
      }

   }
}
