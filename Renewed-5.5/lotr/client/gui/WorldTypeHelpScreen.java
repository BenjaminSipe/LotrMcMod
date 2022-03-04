package lotr.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lotr.client.util.LOTRClientUtil;
import lotr.common.LOTRLog;
import lotr.common.config.LOTRConfig;
import lotr.common.init.LOTRItems;
import lotr.common.init.LOTRWorldTypes;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.BiomeGeneratorTypeScreens;
import net.minecraft.client.gui.screen.CreateWorldScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.WorldOptionsScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.gen.settings.DimensionGeneratorSettings;
import net.minecraftforge.client.ForgeWorldTypeScreens;
import net.minecraftforge.common.world.ForgeWorldType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

public class WorldTypeHelpScreen extends Screen {
   private static final int DISPLAY_WIDTH = 320;
   private static final int DISPLAY_HEIGHT = 200;
   private static final int WORLD_TYPE_BUTTON_Y = 70;
   private final CreateWorldScreen parentScreen;
   private Button buttonNormalWorld;
   private Button buttonMiddleEarthWorld;
   private Button buttonDismiss;
   private BiomeGeneratorTypeScreens selectedWorldType = null;
   private int selectionCloseScreenTimer;
   private static final int SELECTION_CLOSE_SCREEN_TIME = 100;
   private static final int SELECTION_CLOSE_SCREEN_FADEOUT_TIME = 20;

   public WorldTypeHelpScreen(CreateWorldScreen parent) {
      super(new StringTextComponent("WORLD_TYPE_HELP"));
      this.parentScreen = parent;
   }

   public void func_231160_c_() {
      super.func_231160_c_();
      int xMid = this.field_230708_k_ / 2;
      int yMid = this.field_230709_l_ / 2;
      int yTop = yMid - 100;
      int yBottom = yMid + 100;
      int buttonW = 150;
      int buttonH = 20;
      int buttonY = yTop + 70;
      this.buttonNormalWorld = (Button)this.func_230480_a_(new Button(xMid - 155, buttonY, buttonW, buttonH, this.getSelectWorldTypeDisplayText(BiomeGeneratorTypeScreens.field_239066_a_), (b) -> {
         this.selectWorldType(b, BiomeGeneratorTypeScreens.field_239066_a_);
      }));
      this.buttonMiddleEarthWorld = (Button)this.func_230480_a_(new Button(xMid + 5, buttonY, buttonW, buttonH, this.getSelectWorldTypeDisplayText(getGeneratorFromForgeWorldType(LOTRWorldTypes.MIDDLE_EARTH)), (b) -> {
         this.selectWorldType(b, getGeneratorFromForgeWorldType(LOTRWorldTypes.MIDDLE_EARTH));
      }));
      this.buttonDismiss = (Button)this.func_230480_a_(new Button(xMid - 75, yBottom - 10, buttonW, buttonH, new TranslationTextComponent("gui.lotr.worldTypeHelp.dismiss"), (b) -> {
         this.func_231175_as__();
      }));
   }

   private static BiomeGeneratorTypeScreens getGeneratorFromForgeWorldType(RegistryObject worldType) {
      return getGeneratorFromForgeWorldType((ForgeWorldType)worldType.get());
   }

   private static BiomeGeneratorTypeScreens getGeneratorFromForgeWorldType(ForgeWorldType worldType) {
      try {
         Map generators = (Map)ObfuscationReflectionHelper.getPrivateValue(ForgeWorldTypeScreens.class, (Object)null, "GENERATORS");
         return (BiomeGeneratorTypeScreens)generators.get(worldType);
      } catch (Exception var2) {
         LOTRLog.error("Reflection tricks to lookup the generator for the ForgeWorldType %s failed!", worldType.getRegistryName());
         var2.printStackTrace();
         return null;
      }
   }

   private ITextComponent getSelectWorldTypeDisplayText(BiomeGeneratorTypeScreens worldType) {
      ITextComponent baseMsg = new TranslationTextComponent("selectWorld.mapType");
      return baseMsg.func_230532_e_().func_240702_b_(" ").func_230529_a_(this.getWorldTypeDisplayName(worldType));
   }

   private ITextComponent getWorldTypeDisplayName(BiomeGeneratorTypeScreens worldType) {
      return worldType.func_239077_a_();
   }

   private void selectWorldType(Button selectedButton, BiomeGeneratorTypeScreens worldType) {
      if (this.selectedWorldType == null) {
         this.selectedWorldType = worldType;

         try {
            WorldOptionsScreen wos = this.parentScreen.field_238934_c_;
            ObfuscationReflectionHelper.setPrivateValue(WorldOptionsScreen.class, wos, Optional.of(worldType), "field_239040_n_");
            Field f_dimensionGeneratorSettings = ObfuscationReflectionHelper.findField(WorldOptionsScreen.class, "field_239039_m_");
            DimensionGeneratorSettings prevDimGenSettings = (DimensionGeneratorSettings)f_dimensionGeneratorSettings.get(wos);
            f_dimensionGeneratorSettings.set(wos, worldType.func_241220_a_(wos.func_239055_b_(), prevDimGenSettings.func_236221_b_(), prevDimGenSettings.func_236222_c_(), prevDimGenSettings.func_236223_d_()));
         } catch (Exception var6) {
            LOTRLog.error("Error setting world type in world creation screen");
            var6.printStackTrace();
         }

         this.selectionCloseScreenTimer = 100;
         selectedButton.field_230690_l_ = this.field_230708_k_ / 2 - selectedButton.func_230998_h_() / 2;
         this.deactivateButtonIfNotSelected(this.buttonNormalWorld, selectedButton);
         this.deactivateButtonIfNotSelected(this.buttonMiddleEarthWorld, selectedButton);
      }

   }

   private void deactivateButtonIfNotSelected(Button button, Button selectedButton) {
      if (button != selectedButton) {
         button.field_230694_p_ = button.field_230693_o_ = false;
      }

   }

   public void func_231175_as__() {
      LOTRConfig.CLIENT.showWorldTypeHelp.setAndSave(false);
      this.field_230706_i_.func_147108_a(this.parentScreen);
   }

   public void func_231023_e_() {
      super.func_231023_e_();
      if (this.selectionCloseScreenTimer > 0) {
         --this.selectionCloseScreenTimer;
         if (this.selectionCloseScreenTimer <= 0) {
            this.func_231175_as__();
         }
      }

   }

   private float getFadeoutAlpha() {
      if (this.selectedWorldType == null) {
         return 1.0F;
      } else {
         float f = (float)this.selectionCloseScreenTimer / 20.0F;
         return MathHelper.func_76131_a(f, 0.0F, 1.0F);
      }
   }

   public void func_230430_a_(MatrixStack matStack, int mouseX, int mouseY, float f) {
      int xMid = this.field_230708_k_ / 2;
      int yMid = this.field_230709_l_ / 2;
      int x0 = xMid - 160;
      int x1 = xMid + 160;
      int y0 = yMid - 100;
      int y1 = yMid + 100;
      float alpha = this.getFadeoutAlpha();
      matStack.func_227860_a_();
      matStack.func_227861_a_(0.0D, 0.0D, -100.0D);
      this.parentScreen.func_230430_a_(matStack, -1000, -1000, f);
      matStack.func_227865_b_();
      func_238467_a_(matStack, 0, 0, this.field_230708_k_, this.field_230709_l_, LOTRClientUtil.getRGBA(1052688, alpha * 0.75F));
      int border = 1;
      func_238467_a_(matStack, x0, y0, x1, y1, LOTRClientUtil.getRGBA(0, alpha));
      this.func_238465_a_(matStack, x0 - border, x1 + border, y0 - border, LOTRClientUtil.getRGBA(16777215, alpha));
      this.func_238465_a_(matStack, x0 - border, x1 + border, y1 + border, LOTRClientUtil.getRGBA(16777215, alpha));
      this.func_238473_b_(matStack, x0 - border, y0 - border, y1 + border, LOTRClientUtil.getRGBA(16777215, alpha));
      this.func_238473_b_(matStack, x1 + border, y0 - border, y1 + border, LOTRClientUtil.getRGBA(16777215, alpha));
      int fontBorder = 6;
      int maxFontWidth = 320 - fontBorder * 2;
      ITextComponent title = new TranslationTextComponent("gui.lotr.worldTypeHelp.title");
      List titleLines = this.field_230712_o_.func_238425_b_(title, maxFontWidth);
      int y = y0 + fontBorder;

      for(Iterator var18 = titleLines.iterator(); var18.hasNext(); y += 9) {
         IReorderingProcessor line = (IReorderingProcessor)var18.next();
         this.field_230712_o_.func_238422_b_(matStack, line, (float)(xMid - this.field_230712_o_.func_243245_a(line) / 2), (float)y, LOTRClientUtil.getRGBAForFontRendering(16777215, alpha));
         this.field_230712_o_.getClass();
      }

      if (this.selectedWorldType == null) {
         this.renderTextBelowButton(matStack, this.buttonNormalWorld, new TranslationTextComponent("gui.lotr.worldTypeHelp.normal", new Object[]{this.getWorldTypeDisplayName(BiomeGeneratorTypeScreens.field_239066_a_)}), alpha);
         this.renderTextBelowButton(matStack, this.buttonMiddleEarthWorld, new TranslationTextComponent("gui.lotr.worldTypeHelp.me", new Object[]{this.getWorldTypeDisplayName(getGeneratorFromForgeWorldType(LOTRWorldTypes.MIDDLE_EARTH))}), alpha);
      } else {
         int var10000 = y0 + 70 + 20;
         this.field_230712_o_.getClass();
         y = var10000 + 9;
         ITextComponent line1 = new TranslationTextComponent("gui.lotr.worldTypeHelp.selected.1", new Object[]{this.getWorldTypeDisplayName(this.selectedWorldType)});
         ITextComponent line2 = new TranslationTextComponent("gui.lotr.worldTypeHelp.selected.2", new Object[]{new TranslationTextComponent("selectWorld.moreWorldOptions")});
         this.field_230712_o_.func_243248_b(matStack, line1, (float)(xMid - this.field_230712_o_.func_238414_a_(line1) / 2), (float)y, LOTRClientUtil.getRGBAForFontRendering(16777215, alpha));
         FontRenderer var20 = this.field_230712_o_;
         float var10003 = (float)(xMid - this.field_230712_o_.func_238414_a_(line2) / 2);
         this.field_230712_o_.getClass();
         var20.func_243248_b(matStack, line2, var10003, (float)(y + 9 * 2), LOTRClientUtil.getRGBAForFontRendering(16777215, alpha));
      }

      this.renderItemIconAboveButton(this.buttonNormalWorld, new ItemStack((IItemProvider)LOTRItems.GOLD_RING.get()), -10, 0, alpha);
      this.renderItemIconAboveButton(this.buttonNormalWorld, new ItemStack(Items.field_151033_d), 10, 0, alpha);
      this.renderItemIconAboveButton(this.buttonMiddleEarthWorld, new ItemStack((IItemProvider)LOTRItems.RED_BOOK.get()), 0, 0, alpha);
      this.field_230710_m_.forEach((button) -> {
         button.func_230986_a_(alpha);
      });
      super.func_230430_a_(matStack, mouseX, mouseY, f);
   }

   private void renderTextBelowButton(MatrixStack matStack, Button button, ITextComponent text, float alpha) {
      int buttonBorder = 4;
      if (button.field_230694_p_) {
         List belowLines = this.field_230712_o_.func_238425_b_(text, button.func_230998_h_());
         int y = button.field_230691_m_ + button.func_238483_d_() + buttonBorder;

         for(Iterator var8 = belowLines.iterator(); var8.hasNext(); y += 9) {
            IReorderingProcessor line = (IReorderingProcessor)var8.next();
            this.field_230712_o_.func_238422_b_(matStack, line, (float)button.field_230690_l_, (float)y, LOTRClientUtil.getRGBAForFontRendering(16777215, alpha));
            this.field_230712_o_.getClass();
         }
      }

   }

   private void renderItemIconAboveButton(Button button, ItemStack icon, int xOffset, int yOffset, float alpha) {
      if (button.field_230694_p_ && alpha >= 1.0F) {
         int x = button.field_230690_l_ + button.func_230998_h_() / 2 + xOffset;
         int y = button.field_230691_m_ - 19 + yOffset;
         x -= 8;
         this.field_230707_j_.func_175042_a(icon, x, y);
      }

   }
}
