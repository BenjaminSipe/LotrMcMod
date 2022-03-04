package lotr.client.gui.map;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import lotr.client.MapImageTextures;
import lotr.common.world.map.Waypoint;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class WaypointTooltipRenderer extends MapTooltipRenderer {
   private List sections = new ArrayList();

   public void render(MatrixStack matStack, Waypoint waypoint, boolean selected, int mouseX, int mouseY, float tick) {
      float expandProgress = this.getSelectionExpandProgress();
      float textAlpha = this.getExpandingTextAlpha();
      ITextComponent name = this.getNameText(waypoint);
      ITextComponent coords = this.getCoordsText(waypoint);
      ITextComponent distanceText = this.getDistanceText(waypoint);
      ITextComponent loreText = this.getLoreText(waypoint);
      ITextComponent ownershipText = this.getOwnershipText(waypoint);
      ITextComponent numTravelsText = this.getNumTravelsText(waypoint, selected);
      float guiScale = (float)this.mc.func_228018_at_().func_198100_s();
      float loreScale = guiScale - 1.0F;
      if (guiScale <= 2.0F) {
         loreScale = guiScale;
      }

      float loreScaleRel = loreScale / guiScale;
      float loreScaleRelInv = 1.0F / loreScaleRel;
      this.font.getClass();
      int loreFontHeight = MathHelper.func_76123_f(9.0F * loreScaleRel);
      double[] pos = this.mapScreen.transformMapCoords(waypoint.getMapX(), waypoint.getMapZ(), tick);
      int rectX = (int)Math.round(pos[0]);
      int rectY = (int)Math.round(pos[1]);
      rectY += 5;
      int border = 3;
      this.font.getClass();
      int fontHeight = 9;
      int innerRectWidth = this.font.func_238414_a_(name);
      int innerRectWidthCompletelyExpanded = innerRectWidth;
      int rectWidth;
      if (selected) {
         rectWidth = Math.max(innerRectWidth, this.font.func_238414_a_(coords));
         if (loreText != null) {
            rectWidth += 50;
            rectWidth = Math.round((float)rectWidth * (loreScaleRel / 0.66667F));
         }

         innerRectWidth = (int)MathHelper.func_219799_g(expandProgress, (float)innerRectWidth, (float)rectWidth);
         innerRectWidthCompletelyExpanded = rectWidth;
      }

      rectWidth = innerRectWidth + border * 2;
      rectX -= rectWidth / 2;
      this.sections.clear();
      this.sections.add(new WaypointTooltipRenderer.TooltipSection(fontHeight + border * 2, (midX, y, highlight) -> {
         this.mapScreen.drawCenteredStringNoShadow(matStack, this.font, name, midX, y + border, this.getTextColor(highlight, 1.0F));
      }));
      int rectHeight = this.calculateSectionsTotalHeight();
      int ownershipTextHeight;
      int expandedRectHeight;
      if (selected) {
         int coordsAndTravelsHeight = fontHeight + border;
         if (numTravelsText != null) {
            coordsAndTravelsHeight += loreFontHeight + border;
         }

         this.sections.add(new WaypointTooltipRenderer.TooltipSection(coordsAndTravelsHeight, (midX, y, highlight) -> {
            if (textAlpha > 0.0F) {
               this.mapScreen.drawCenteredStringNoShadow(matStack, this.font, coords, midX, y, this.getTextColor(highlight, textAlpha));
               if (numTravelsText != null) {
                  y += fontHeight + border;
                  int iconSize = 8;
                  int iconPlusTextWidth = iconSize + border + (int)((float)this.font.func_238414_a_(numTravelsText) * loreScaleRel);
                  this.mc.func_110434_K().func_110577_a(MapImageTextures.MAP_ICONS);
                  float brightness = highlight ? 1.0F : 0.82F;
                  RenderSystem.color4f(brightness, brightness, brightness, textAlpha);
                  RenderSystem.enableBlend();
                  RenderSystem.defaultBlendFunc();
                  this.mapScreen.func_238474_b_(matStack, midX - iconPlusTextWidth / 2, y, 0, 216, iconSize, iconSize);
                  RenderSystem.disableBlend();
                  RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
                  matStack.func_227860_a_();
                  matStack.func_227862_a_(loreScaleRel, loreScaleRel, 1.0F);
                  this.font.func_243248_b(matStack, numTravelsText, (float)((int)((float)(midX - iconPlusTextWidth / 2 + iconSize + border) * loreScaleRelInv)), (float)((int)((float)(y + (fontHeight - loreFontHeight) / 2) * loreScaleRelInv)), this.getTextColor(highlight, textAlpha));
                  matStack.func_227865_b_();
               }
            }

         }));
         if (distanceText != null) {
            this.sections.add(new WaypointTooltipRenderer.TooltipSection(fontHeight + border, (midX, y, highlight) -> {
               if (textAlpha > 0.0F) {
                  this.mapScreen.drawCenteredStringNoShadow(matStack, this.font, distanceText, midX, y, this.getTextColor(highlight, textAlpha));
               }

            }));
         }

         List ownershipLines;
         if (loreText != null) {
            ownershipLines = this.font.func_238425_b_(loreText, (int)((float)innerRectWidthCompletelyExpanded * loreScaleRelInv));
            ownershipTextHeight = ownershipLines.size() * loreFontHeight;
            this.sections.add(new WaypointTooltipRenderer.TooltipSection(ownershipTextHeight + border * 2, (midX, y, highlight) -> {
               if (textAlpha > 0.0F) {
                  y += border;
                  matStack.func_227860_a_();
                  matStack.func_227862_a_(loreScaleRel, loreScaleRel, 1.0F);

                  for(Iterator var11 = ownershipLines.iterator(); var11.hasNext(); y += loreFontHeight) {
                     IReorderingProcessor line = (IReorderingProcessor)var11.next();
                     this.mapScreen.drawCenteredStringNoShadow(matStack, this.font, line, (int)((float)midX * loreScaleRelInv), (int)((float)y * loreScaleRelInv), this.getTextColor(highlight, textAlpha));
                  }

                  matStack.func_227865_b_();
               }

            }));
         }

         if (ownershipText != null) {
            ownershipLines = this.font.func_238425_b_(ownershipText, (int)((float)innerRectWidthCompletelyExpanded * loreScaleRelInv));
            ownershipTextHeight = ownershipLines.size() * loreFontHeight;
            this.sections.add(new WaypointTooltipRenderer.TooltipSection(ownershipTextHeight + border, (midX, y, highlight) -> {
               if (textAlpha > 0.0F) {
                  matStack.func_227860_a_();
                  matStack.func_227862_a_(loreScaleRel, loreScaleRel, 1.0F);

                  for(Iterator var10 = ownershipLines.iterator(); var10.hasNext(); y += loreFontHeight) {
                     IReorderingProcessor line = (IReorderingProcessor)var10.next();
                     this.mapScreen.drawCenteredStringNoShadow(matStack, this.font, line, (int)((float)midX * loreScaleRelInv), (int)((float)y * loreScaleRelInv), this.getTextColor(highlight, textAlpha));
                  }

                  matStack.func_227865_b_();
               }

            }));
         }

         expandedRectHeight = this.calculateSectionsTotalHeight();
         rectHeight = (int)MathHelper.func_219799_g(expandProgress, (float)rectHeight, (float)expandedRectHeight);
      }

      int mapBorder2 = 2;
      rectX = Math.max(rectX, this.mapXMin + mapBorder2);
      rectX = Math.min(rectX, this.mapXMax - mapBorder2 - rectWidth);
      rectY = Math.max(rectY, this.mapYMin + mapBorder2);
      rectY = Math.min(rectY, this.mapYMax - mapBorder2 - rectHeight);
      boolean mouseWithinTooltip = mouseX >= rectX && mouseX <= rectX + rectWidth && mouseY >= rectY && mouseY <= rectY + rectHeight;
      matStack.func_227860_a_();
      matStack.func_227861_a_(0.0D, 0.0D, 300.0D);
      this.mapScreen.drawFancyRect(matStack, rectX, rectY, rectX + rectWidth, rectY + rectHeight);
      expandedRectHeight = rectX + rectWidth / 2;
      ownershipTextHeight = rectY;
      WaypointTooltipRenderer.TooltipSection highlightedSection = null;
      Iterator var34;
      WaypointTooltipRenderer.TooltipSection section;
      if (mouseWithinTooltip) {
         for(var34 = this.sections.iterator(); var34.hasNext(); ownershipTextHeight += section.height) {
            section = (WaypointTooltipRenderer.TooltipSection)var34.next();
            if (mouseY >= ownershipTextHeight && mouseY < ownershipTextHeight + section.height) {
               highlightedSection = section;
               break;
            }
         }
      }

      if (highlightedSection == null && !this.sections.isEmpty()) {
         highlightedSection = (WaypointTooltipRenderer.TooltipSection)this.sections.get(0);
      }

      ownershipTextHeight = rectY;

      for(var34 = this.sections.iterator(); var34.hasNext(); ownershipTextHeight += section.height) {
         section = (WaypointTooltipRenderer.TooltipSection)var34.next();
         section.renderer.render(expandedRectHeight, ownershipTextHeight, section == highlightedSection);
      }

      matStack.func_227865_b_();
   }

   private ITextComponent getNameText(Waypoint waypoint) {
      return waypoint.getDisplayName();
   }

   private ITextComponent getCoordsText(Waypoint waypoint) {
      return waypoint.getCoordsText();
   }

   private ITextComponent getDistanceText(Waypoint waypoint) {
      if (this.mc.field_71439_g != null) {
         double dist = waypoint.getDistanceFromPlayer(this.mc.field_71439_g);
         return WaypointDistanceDisplay.getDistanceText(dist);
      } else {
         return null;
      }
   }

   private ITextComponent getLoreText(Waypoint waypoint) {
      return waypoint.getDisplayLore();
   }

   private ITextComponent getOwnershipText(Waypoint waypoint) {
      return waypoint.getDisplayOwnership();
   }

   private ITextComponent getNumTravelsText(Waypoint waypoint, boolean selected) {
      if (selected && this.mc.field_71439_g != null) {
         int numTravels = this.mapScreen.getClientPlayerData().getFastTravelData().getWPUseCount(waypoint);
         if (numTravels > 0 && waypoint.hasPlayerUnlocked(this.mc.field_71439_g)) {
            return new StringTextComponent(String.valueOf(numTravels));
         }
      }

      return null;
   }

   private int calculateSectionsTotalHeight() {
      return (Integer)this.sections.stream().collect(Collectors.summingInt((section) -> {
         return section.height;
      }));
   }

   @FunctionalInterface
   private interface SectionRenderer {
      void render(int var1, int var2, boolean var3);
   }

   private static class TooltipSection {
      private final int height;
      private final WaypointTooltipRenderer.SectionRenderer renderer;

      public TooltipSection(int height, WaypointTooltipRenderer.SectionRenderer renderer) {
         this.height = height;
         this.renderer = renderer;
      }
   }
}
