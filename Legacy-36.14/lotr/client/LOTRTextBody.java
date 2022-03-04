package lotr.client;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.MathHelper;

public class LOTRTextBody {
   private List list = new ArrayList();
   private int defaultColor;
   private int textWidth;
   private static final String LINEBREAK = "<BR>";

   public LOTRTextBody(int c) {
      this.defaultColor = c;
      this.textWidth = 100;
   }

   public void add(String s) {
      this.add(s, this.defaultColor);
   }

   public void add(String s, int c) {
      this.list.add(new LOTRTextBody.TextColor(s, c));
   }

   public void addLinebreak() {
      this.add("<BR>");
   }

   public void set(int i, String s) {
      ((LOTRTextBody.TextColor)this.list.get(i)).text = s;
   }

   public void set(int i, String s, int c) {
      ((LOTRTextBody.TextColor)this.list.get(i)).text = s;
      ((LOTRTextBody.TextColor)this.list.get(i)).color = c;
   }

   public String getText(int i) {
      return ((LOTRTextBody.TextColor)this.list.get(i)).text;
   }

   public int getColor(int i) {
      return ((LOTRTextBody.TextColor)this.list.get(i)).color;
   }

   public int size() {
      return this.list.size();
   }

   public void setTextWidth(int w) {
      this.textWidth = w;
   }

   public void render(FontRenderer fr, int x, int y) {
      this.renderAndReturnScroll(fr, x, y, Integer.MAX_VALUE, Float.MAX_VALUE);
   }

   public float renderAndReturnScroll(FontRenderer fr, int x, int yTop, int yBottom, float scroll) {
      int ySize = yBottom - yTop;
      int numLines = this.getTotalLines(fr);
      int lineHeight = fr.field_78288_b;
      scroll = Math.max(scroll, 0.0F);
      scroll = Math.min(scroll, (float)(numLines - MathHelper.func_76128_c((double)((float)ySize / (float)lineHeight))));
      int d1 = Math.round(scroll);
      int y = yTop + ySize / lineHeight * lineHeight;
      y -= lineHeight;
      int maxLines = ySize / lineHeight;
      if (numLines < maxLines) {
         y -= (maxLines - numLines) * lineHeight;
      }

      for(int i = this.size() - 1; i >= 0; --i) {
         String part = this.getText(i);
         int color = this.getColor(i);
         List lineList = fr.func_78271_c(part, this.textWidth);

         for(int l = lineList.size() - 1; l >= 0; --l) {
            String line = (String)lineList.get(l);
            if (d1 > 0) {
               --d1;
            } else {
               if (y < yTop) {
                  return scroll;
               }

               if (line.equals("<BR>")) {
                  line = "";

                  for(char br = '-'; fr.func_78256_a(line + br) < this.textWidth; line = line + br) {
                  }
               }

               fr.func_78276_b(line, x, y, color);
               y -= lineHeight;
            }
         }
      }

      return scroll;
   }

   public int getTotalLines(FontRenderer fr) {
      int lines = 0;

      for(int i = 0; i < this.size(); ++i) {
         String part = this.getText(i);
         List lineList = fr.func_78271_c(part, this.textWidth);

         for(Iterator var6 = lineList.iterator(); var6.hasNext(); ++lines) {
            String line = (String)var6.next();
         }
      }

      return lines;
   }

   private static class TextColor {
      public String text;
      public int color;

      public TextColor(String s, int c) {
         this.text = s;
         this.color = c;
      }
   }
}
