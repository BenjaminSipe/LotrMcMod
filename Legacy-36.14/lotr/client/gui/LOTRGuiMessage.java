package lotr.client.gui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;
import lotr.client.LOTRTickHandlerClient;
import lotr.common.LOTRGuiMessageTypes;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

public class LOTRGuiMessage extends LOTRGuiScreenBase {
   private static ResourceLocation guiTexture = new ResourceLocation("lotr:gui/message.png");
   private LOTRGuiMessageTypes type;
   public int xSize = 240;
   public int ySize = 160;
   private int border = 10;
   private int guiLeft;
   private int guiTop;
   private GuiButton buttonDismiss;
   private int buttonTimer = 60;

   public LOTRGuiMessage(LOTRGuiMessageTypes t) {
      this.type = t;
   }

   public void func_73866_w_() {
      this.guiLeft = (this.field_146294_l - this.xSize) / 2;
      this.guiTop = (this.field_146295_m - this.ySize) / 2;
      this.field_146292_n.add(this.buttonDismiss = new LOTRGuiButtonRedBook(0, this.guiLeft + this.xSize / 2 - 40, this.guiTop + this.ySize + 20, 80, 20, StatCollector.func_74838_a("lotr.gui.message.dismiss")));
   }

   public void func_73863_a(int i, int j, float f) {
      this.func_146276_q_();
      this.field_146297_k.func_110434_K().func_110577_a(guiTexture);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.func_73729_b(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
      String msg = this.type.getMessage();
      int pageWidth = this.xSize - this.border * 2;
      String[] splitNewline = msg.split(Pattern.quote("\\n"));
      List msgLines = new ArrayList();
      String[] var8 = splitNewline;
      int y = splitNewline.length;

      String line;
      for(int var10 = 0; var10 < y; ++var10) {
         line = var8[var10];
         msgLines.addAll(this.field_146289_q.func_78271_c(line, pageWidth));
      }

      int x = this.guiLeft + this.border;
      y = this.guiTop + this.border;

      for(Iterator var18 = msgLines.iterator(); var18.hasNext(); y += this.field_146289_q.field_78288_b) {
         line = (String)var18.next();
         this.field_146289_q.func_78276_b(line, x, y, 8019267);
      }

      String s = StatCollector.func_74838_a("lotr.gui.message.notDisplayedAgain");
      this.drawCenteredString(s, this.guiLeft + this.xSize / 2, this.guiTop + this.ySize - this.border / 2 - this.field_146289_q.field_78288_b, 9666921);
      if (this.type == LOTRGuiMessageTypes.ALIGN_DRAIN) {
         int numIcons = 3;
         int iconGap = 40;

         for(int l = 0; l < numIcons; ++l) {
            int iconX = this.guiLeft + this.xSize / 2;
            iconX -= (numIcons - 1) * iconGap / 2;
            iconX += l * iconGap - 8;
            int iconY = this.guiTop + this.border + 14;
            int num = l + 1;
            LOTRTickHandlerClient.renderAlignmentDrain(this.field_146297_k, iconX, iconY, num);
         }
      }

      if (this.buttonTimer > 0) {
         --this.buttonTimer;
      }

      this.buttonDismiss.field_146124_l = this.buttonTimer == 0;
      super.func_73863_a(i, j, f);
   }

   protected void func_146284_a(GuiButton button) {
      if (button.field_146124_l && button == this.buttonDismiss) {
         this.field_146297_k.field_71439_g.func_71053_j();
      }

   }

   protected void func_73869_a(char c, int i) {
   }
}
