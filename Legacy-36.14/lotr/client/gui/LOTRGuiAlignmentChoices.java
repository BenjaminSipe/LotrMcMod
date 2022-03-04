package lotr.client.gui;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import lotr.client.LOTRClientProxy;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRPlayerData;
import lotr.common.fac.LOTRAlignmentValues;
import lotr.common.fac.LOTRFaction;
import lotr.common.network.LOTRPacketAlignmentChoices;
import lotr.common.network.LOTRPacketHandler;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.opengl.GL11;

public class LOTRGuiAlignmentChoices extends LOTRGuiScreenBase {
   private int xSize = 430;
   private int ySize = 250;
   private int guiLeft;
   private int guiTop;
   private int page = 0;
   private GuiButton buttonConfirm;
   private Map facButtons = new HashMap();
   private Map buttonFacs = new HashMap();
   private Set setZeroFacs = new HashSet();
   private static final int colorConflict = -62464;
   private static final int colorSelected = -1;

   public void func_73866_w_() {
      super.func_73866_w_();
      this.guiLeft = (this.field_146294_l - this.xSize) / 2;
      this.guiTop = (this.field_146295_m - this.ySize) / 2;
      this.field_146292_n.add(this.buttonConfirm = new LOTRGuiButtonRedBook(0, this.guiLeft + this.xSize / 2 - 100, this.guiTop + this.ySize - 30, 200, 20, "BUTTON"));

      LOTRGuiButtonRedBook button;
      for(Iterator var1 = LOTRFaction.getPlayableAlignmentFactions().iterator(); var1.hasNext(); button.field_146125_m = button.field_146124_l = false) {
         LOTRFaction fac = (LOTRFaction)var1.next();
         button = new LOTRGuiButtonRedBook(0, 0, 0, 80, 20, "");
         this.facButtons.put(fac, button);
         this.buttonFacs.put(button, fac);
         this.field_146292_n.add(button);
      }

   }

   public void func_73863_a(int i, int j, float f) {
      this.func_146276_q_();
      func_73734_a(this.guiLeft, this.guiTop, this.guiLeft + this.xSize, this.guiTop + this.ySize, -5756117);
      func_73734_a(this.guiLeft + 2, this.guiTop + 2, this.guiLeft + this.xSize - 2, this.guiTop + this.ySize - 2, -1847889);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.field_146297_k.func_110434_K().func_110577_a(LOTRClientProxy.alignmentTexture);
      int warnIconSize = 32;
      this.func_73729_b(this.guiLeft - warnIconSize, this.guiTop, 16, 128, warnIconSize, warnIconSize);
      this.func_73729_b(this.guiLeft + this.xSize, this.guiTop, 16, 128, warnIconSize, warnIconSize);
      LOTRPlayerData pd = LOTRLevelData.getData((EntityPlayer)this.field_146297_k.field_71439_g);
      int textColor = 8019267;
      int border = 7;
      int lineWidth = this.xSize - border * 2;
      int x = this.guiLeft + border;
      int y = this.guiTop + border;
      String s;
      if (this.page == 0) {
         s = "Hello! You are reading this because you earned alignment before Update 35.";
         this.field_146289_q.func_78279_b(s, x, y, lineWidth, textColor);
         y += this.field_146289_q.field_78288_b * this.field_146289_q.func_78271_c(s, lineWidth).size();
         y += this.field_146289_q.field_78288_b;
         s = "This update introduces 'Enemy Alignment Draining'. If you have + alignment with two Mortal Enemy factions (more severe than Enemy), both alignments will slowly drain over time until one reaches 0.";
         this.field_146289_q.func_78279_b(s, x, y, lineWidth, textColor);
         y += this.field_146289_q.field_78288_b * this.field_146289_q.func_78271_c(s, lineWidth).size();
         y += this.field_146289_q.field_78288_b;
         s = "You can still hold + alignment with Mortal Enemies in the short term if you work quickly. But long-term public friendship with Gondor and Mordor together is not in the spirit of Tolkien's Middle-earth.";
         this.field_146289_q.func_78279_b(s, x, y, lineWidth, textColor);
         y += this.field_146289_q.field_78288_b * this.field_146289_q.func_78271_c(s, lineWidth).size();
         y += this.field_146289_q.field_78288_b;
         s = "Because you have played before, you have the option to set any unwanted alignments to zero immediately, to prevent draining high alignment from factions you care about. This will also help if you want to Pledge to a faction.";
         this.field_146289_q.func_78279_b(s, x, y, lineWidth, textColor);
         y += this.field_146289_q.field_78288_b * this.field_146289_q.func_78271_c(s, lineWidth).size();
         y += this.field_146289_q.field_78288_b;
         s = "Note that if you are a server admin or playing in singleplayer you can toggle this feature in the LOTR mod config. However, players who wish to Pledge will still need to reduce Mortal Enemy alignments to zero.";
         this.field_146289_q.func_78279_b(EnumChatFormatting.ITALIC + s, x, y, lineWidth, textColor);
         int var10000 = y + this.field_146289_q.field_78288_b * this.field_146289_q.func_78271_c(s, lineWidth).size();
         this.buttonConfirm.field_146126_j = "View your alignments";
      } else if (this.page == 1) {
         s = "Choose which alignments to set to zero. You can choose as many or as few as you like, but you can only choose once. Alignments which will drain due to a conflict are in RED - this will update as you select unwanted factions.";
         this.field_146289_q.func_78279_b(s, x, y, lineWidth, textColor);
         y += this.field_146289_q.field_78288_b * this.field_146289_q.func_78271_c(s, lineWidth).size();
         y += this.field_146289_q.field_78288_b;
         s = "If you are hoping to Pledge to a faction, you will need to have 0 or - alignment with all of its Mortal Enemies.";
         this.field_146289_q.func_78279_b(s, x, y, lineWidth, textColor);
         y += this.field_146289_q.field_78288_b * this.field_146289_q.func_78271_c(s, lineWidth).size();
         y += this.field_146289_q.field_78288_b;
         int buttonX = this.guiLeft + border;
         int buttonY = y;
         Iterator var14 = LOTRFaction.getPlayableAlignmentFactions().iterator();

         label72:
         while(true) {
            LOTRFaction fac;
            LOTRGuiButtonRedBook button;
            do {
               float align;
               do {
                  do {
                     do {
                        if (!var14.hasNext()) {
                           s = "If you do not want to choose now you can close this screen with '" + GameSettings.func_74298_c(this.field_146297_k.field_71474_y.field_151445_Q.func_151463_i()) + "' and it will appear again when you log in. Remember - you can only choose once.";
                           y = this.buttonConfirm.field_146129_i - this.field_146289_q.field_78288_b * (this.field_146289_q.func_78271_c(s, lineWidth).size() + 1);
                           this.field_146289_q.func_78279_b(s, x, y, lineWidth, textColor);
                           this.buttonConfirm.field_146126_j = "CONFIRM - set " + this.setZeroFacs.size() + " alignments to zero";
                           break label72;
                        }

                        fac = (LOTRFaction)var14.next();
                        button = (LOTRGuiButtonRedBook)this.facButtons.get(fac);
                        button.field_146125_m = true;
                        button.field_146124_l = false;
                        button.field_146126_j = "";
                        button.field_146128_h = buttonX;
                        button.field_146129_i = buttonY;
                        buttonX += button.field_146120_f + 4;
                        if (buttonX >= this.guiLeft + this.xSize - border) {
                           buttonX = this.guiLeft + border;
                           buttonY += 24;
                        }

                        align = pd.getAlignment(fac);
                        String facName = fac.factionName();
                        String alignS = LOTRAlignmentValues.formatAlignForDisplay(align);
                        String status = "Not draining";
                        button.field_146124_l = false;
                        if (align > 0.0F) {
                           boolean isDraining = this.isFactionConflicting(pd, fac, false);
                           boolean willDrain = this.isFactionConflicting(pd, fac, true);
                           if (isDraining) {
                              if (this.setZeroFacs.contains(fac)) {
                                 status = "Setting to zero";
                                 button.field_146124_l = true;
                                 func_73734_a(button.field_146128_h - 1, button.field_146129_i - 1, button.field_146128_h + button.field_146120_f + 1, button.field_146129_i + button.field_146121_g + 1, -1);
                              } else if (willDrain) {
                                 status = "Draining";
                                 button.field_146124_l = true;
                                 func_73734_a(button.field_146128_h - 1, button.field_146129_i - 1, button.field_146128_h + button.field_146120_f + 1, button.field_146129_i + button.field_146121_g + 1, -62464);
                              } else {
                                 status = "Will not drain after CONFIRM";
                                 button.field_146124_l = false;
                              }
                           }
                        }

                        float buttonTextScale = 0.5F;
                        GL11.glPushMatrix();
                        GL11.glTranslatef(0.0F, 0.0F, 100.0F);
                        GL11.glScalef(buttonTextScale, buttonTextScale, 1.0F);
                        int buttonTextX = (int)((float)(button.field_146128_h + button.field_146120_f / 2) / buttonTextScale);
                        int buttonTextY = (int)((float)button.field_146129_i / buttonTextScale) + 4;
                        this.drawCenteredString(facName, buttonTextX, buttonTextY, textColor);
                        buttonTextY += this.field_146289_q.field_78288_b;
                        this.drawCenteredString(alignS, buttonTextX, buttonTextY, textColor);
                        buttonTextY += this.field_146289_q.field_78288_b;
                        this.drawCenteredString(status, buttonTextX, buttonTextY, textColor);
                        GL11.glPopMatrix();
                     } while(!button.func_146115_a());
                  } while(align <= 0.0F);
               } while(this.setZeroFacs.contains(fac));
            } while(!this.isFactionConflicting(pd, fac, true));

            GL11.glPushMatrix();
            GL11.glTranslatef(0.0F, 0.0F, 100.0F);
            Iterator var24 = LOTRFaction.getPlayableAlignmentFactions().iterator();

            while(var24.hasNext()) {
               LOTRFaction otherFac = (LOTRFaction)var24.next();
               if (fac != otherFac && !this.setZeroFacs.contains(otherFac) && pd.doFactionsDrain(fac, otherFac) && pd.getAlignment(otherFac) > 0.0F) {
                  LOTRGuiButtonRedBook otherButton = (LOTRGuiButtonRedBook)this.facButtons.get(otherFac);
                  int x1 = button.field_146128_h + button.field_146120_f / 2;
                  int x2 = otherButton.field_146128_h + otherButton.field_146120_f / 2;
                  int y1 = button.field_146129_i + button.field_146121_g / 2;
                  int y2 = otherButton.field_146129_i + otherButton.field_146121_g / 2;
                  GL11.glDisable(3553);
                  Tessellator tess = Tessellator.field_78398_a;
                  tess.func_78371_b(1);
                  GL11.glPushAttrib(2849);
                  GL11.glLineWidth(4.0F);
                  tess.func_78378_d(-62464);
                  tess.func_78377_a((double)x1, (double)y1, 0.0D);
                  tess.func_78377_a((double)x2, (double)y2, 0.0D);
                  tess.func_78381_a();
                  GL11.glPopAttrib();
                  GL11.glEnable(3553);
               }
            }

            GL11.glPopMatrix();
         }
      }

      super.func_73863_a(i, j, f);
   }

   private boolean isFactionConflicting(LOTRPlayerData pd, LOTRFaction fac, boolean accountForSelection) {
      Iterator var4 = LOTRFaction.getPlayableAlignmentFactions().iterator();

      LOTRFaction otherFac;
      do {
         do {
            do {
               if (!var4.hasNext()) {
                  return false;
               }

               otherFac = (LOTRFaction)var4.next();
            } while(fac == otherFac);
         } while(accountForSelection && this.setZeroFacs.contains(otherFac));
      } while(!pd.doFactionsDrain(fac, otherFac) || pd.getAlignment(otherFac) <= 0.0F);

      return true;
   }

   protected void func_146284_a(GuiButton button) {
      if (button.field_146124_l) {
         if (button == this.buttonConfirm) {
            if (this.page == 0) {
               this.page = 1;
            } else if (this.page == 1) {
               LOTRPacketAlignmentChoices packet = new LOTRPacketAlignmentChoices(this.setZeroFacs);
               LOTRPacketHandler.networkWrapper.sendToServer(packet);
               this.field_146297_k.field_71439_g.func_71053_j();
            }
         } else if (this.buttonFacs.containsKey(button)) {
            LOTRFaction fac = (LOTRFaction)this.buttonFacs.get(button);
            if (this.isFactionConflicting(LOTRLevelData.getData((EntityPlayer)this.field_146297_k.field_71439_g), fac, false)) {
               if (this.setZeroFacs.contains(fac)) {
                  this.setZeroFacs.remove(fac);
               } else {
                  this.setZeroFacs.add(fac);
               }
            }
         }
      }

      super.func_146284_a(button);
   }
}
