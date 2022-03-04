package lotr.client.gui.map;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import lotr.client.MapImageTextures;
import lotr.client.util.LOTRClientUtil;
import lotr.common.world.map.MapMarker;
import lotr.common.world.map.MapMarkerIcon;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.util.StringUtils;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;

public class MarkerTooltipRenderer extends MapTooltipRenderer {
   private static final int ICON_SIZE = 10;
   private static final int ICON_SPACED_BORDER = 1;
   private static final int ICON_SPACED_SIZE = 12;
   private static final int NUM_ICONS = MapMarkerIcon.values().length;
   private TextFieldWidget nameField;
   private MapMarker selectedMarker;
   private MapMarkerIcon mouseOverIcon;

   public void init(MiddleEarthMapScreen mapScreen, Minecraft mc, FontRenderer font) {
      super.init(mapScreen, mc, font);
      font.getClass();
      this.nameField = new TextFieldWidget(font, 0, 0, 200, 9, this.nameField, (ITextComponent)null);
      this.nameField.func_146203_f(32);
      this.nameField.func_146185_a(false);
      this.nameField.func_146193_g(16777215);
   }

   public void onSelect(MapMarker marker) {
      if (this.selectedMarker != null) {
         String rename = this.nameField.func_146179_b().trim();
         if (!StringUtils.func_151246_b(rename) && !rename.equals(this.selectedMarker.getName())) {
            this.selectedMarker.renameAndSendToServer(rename);
            MiddleEarthMapScreen.playMarkerUpdateSound();
         }
      }

      this.selectedMarker = marker;
      if (marker != null) {
         this.nameField.func_146180_a(marker.getName());
         this.nameField.func_146195_b(true);
      } else {
         this.nameField.func_146180_a("");
         this.nameField.func_146195_b(false);
      }

   }

   public void tick() {
      this.nameField.func_146178_a();
   }

   public void render(MatrixStack matStack, MapMarker marker, boolean selected, int mouseX, int mouseY, float tick) {
      this.mouseOverIcon = null;
      float expandProgress = this.getSelectionExpandProgress();
      float expandAlpha = this.getExpandingTextAlpha();
      String name = marker.getName();
      double[] pos = this.mapScreen.transformMapCoords(marker.getMapX(), marker.getMapZ(), tick);
      int rectX = (int)Math.round(pos[0]);
      int rectY = (int)Math.round(pos[1]);
      int strWidth = this.font.func_78256_a(name);
      this.font.getClass();
      int strHeight = 9;
      rectY += 7;
      int border = 3;
      int innerRectWidth = strWidth;
      int innerRectHeight = strHeight;
      int rectWidth;
      int rectHeight;
      if (selected) {
         rectWidth = MapMarkerIcon.values().length;
         rectHeight = Math.max(strWidth, NUM_ICONS * 12);
         rectHeight = Math.max(rectHeight, this.nameField.func_230998_h_());
         int innerRectHeightExpanded = strHeight + border + 12;
         innerRectWidth = (int)MathHelper.func_219799_g(expandProgress, (float)strWidth, (float)rectHeight);
         innerRectHeight = (int)MathHelper.func_219799_g(expandProgress, (float)strHeight, (float)innerRectHeightExpanded);
      }

      rectWidth = innerRectWidth + border * 2;
      rectX -= rectWidth / 2;
      rectHeight = innerRectHeight + border * 2;
      int mapBorder2 = 2;
      rectX = Math.max(rectX, this.mapXMin + mapBorder2);
      rectX = Math.min(rectX, this.mapXMax - mapBorder2 - rectWidth);
      rectY = Math.max(rectY, this.mapYMin + mapBorder2);
      rectY = Math.min(rectY, this.mapYMax - mapBorder2 - rectHeight);
      matStack.func_227860_a_();
      matStack.func_227861_a_(0.0D, 0.0D, 300.0D);
      this.mapScreen.drawFancyRect(matStack, rectX, rectY, rectX + rectWidth, rectY + rectHeight);
      int midX = rectX + rectWidth / 2;
      if (selected && expandProgress == 1.0F) {
         this.nameField.field_230690_l_ = midX - this.font.func_78256_a(this.nameField.func_146179_b()) / 2;
         this.nameField.field_230691_m_ = rectY + border;
         this.nameField.func_230430_a_(matStack, mouseX, mouseY, tick);
      } else {
         this.mapScreen.drawCenteredStringNoShadow(matStack, this.font, name, midX, rectY + border, 16777215);
      }

      if (selected && expandAlpha > 0.0F) {
         int iconBarWidth = NUM_ICONS * 12;
         int iconLeftX = midX - iconBarWidth / 2;
         int iconY = rectY + border + strHeight + border;
         boolean mouseInBox = mouseY >= iconY && mouseY < iconY + 12;
         MiddleEarthMapScreen var10000 = this.mapScreen;
         MiddleEarthMapScreen.func_238467_a_(matStack, iconLeftX, iconY, iconLeftX + iconBarWidth, iconY + 12, LOTRClientUtil.getRGBA(14399895, expandAlpha));
         RenderSystem.enableBlend();
         RenderSystem.defaultBlendFunc();
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, expandAlpha);
         this.mc.func_110434_K().func_110577_a(MapImageTextures.MAP_ICONS);

         for(int i = 0; i < NUM_ICONS; ++i) {
            MapMarkerIcon icon = MapMarkerIcon.values()[i];
            int iconX = iconLeftX + i * 12;
            boolean highlight = icon == marker.getIcon();
            if (mouseInBox) {
               highlight = mouseX >= iconX && mouseX < iconX + 12;
               if (highlight) {
                  this.mouseOverIcon = icon;
               }
            }

            this.mapScreen.func_238474_b_(matStack, iconX + 1, iconY + 1, icon.getU(highlight), icon.getV(highlight), 10, 10);
         }

         RenderSystem.disableBlend();
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      }

      matStack.func_227865_b_();
   }

   public boolean mouseClicked(double x, double y, int code) {
      if (this.selectedMarker != null && this.mouseOverIcon != null && code == 0) {
         this.selectedMarker.changeIconAndSendToServer(this.mouseOverIcon);
         MiddleEarthMapScreen.playMarkerUpdateSound();
         return true;
      } else {
         return this.selectedMarker != null && this.nameField.func_231044_a_(x, y, code) ? true : super.mouseClicked(x, y, code);
      }
   }

   public boolean keyPressed(int key, int scan, int param3) {
      return this.selectedMarker != null && this.nameField.func_231046_a_(key, scan, param3);
   }

   public boolean charTyped(char c, int modifiers) {
      return this.selectedMarker != null && this.nameField.func_231042_a_(c, modifiers);
   }

   public boolean isTextFieldFocused() {
      return this.selectedMarker != null && this.nameField.func_212955_f();
   }
}
