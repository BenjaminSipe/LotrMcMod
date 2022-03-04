package lotr.client.gui;

import lotr.common.network.LOTRPacketEditSign;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.tileentity.LOTRTileEntitySign;
import lotr.common.tileentity.LOTRTileEntitySignCarved;
import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.Direction;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class LOTRGuiEditSign extends GuiScreen {
   private LOTRTileEntitySign tileSign;
   private int updateCounter;
   private int editLine;
   private GuiButton buttonDone;
   private static RenderItem itemRenderer = new RenderItem();

   public LOTRGuiEditSign(LOTRTileEntitySign sign) {
      this.tileSign = sign;
   }

   public void func_73866_w_() {
      this.field_146292_n.clear();
      Keyboard.enableRepeatEvents(true);
      this.field_146292_n.add(this.buttonDone = new GuiButton(0, this.field_146294_l / 2 - 100, this.field_146295_m / 4 + 120, StatCollector.func_74838_a("gui.done")));
      this.tileSign.setEditable(false);
   }

   public void func_146281_b() {
      Keyboard.enableRepeatEvents(false);
      LOTRPacketEditSign packet = new LOTRPacketEditSign(this.tileSign);
      LOTRPacketHandler.networkWrapper.sendToServer(packet);
      this.tileSign.setEditable(true);
   }

   public void func_73876_c() {
      ++this.updateCounter;
   }

   protected void func_146284_a(GuiButton button) {
      if (button.field_146124_l) {
         if (button == this.buttonDone) {
         }

         this.tileSign.func_70296_d();
         this.field_146297_k.func_147108_a((GuiScreen)null);
      }

   }

   protected void func_73869_a(char c, int i) {
      if (i == 200) {
         --this.editLine;
      }

      if (i == 208 || i == 28 || i == 156) {
         ++this.editLine;
      }

      this.editLine &= this.tileSign.getNumLines() - 1;
      if (i == 14 && this.tileSign.signText[this.editLine].length() > 0) {
         String s = this.tileSign.signText[this.editLine];
         this.tileSign.signText[this.editLine] = s.substring(0, s.length() - 1);
      }

      if (ChatAllowedCharacters.func_71566_a(c)) {
         int var10000 = this.tileSign.signText[this.editLine].length();
         LOTRTileEntitySign var10001 = this.tileSign;
         if (var10000 < 15) {
            StringBuilder var4 = new StringBuilder();
            String[] var10002 = this.tileSign.signText;
            int var10004 = this.editLine;
            var10002[var10004] = var4.append(var10002[var10004]).append(c).toString();
         }
      }

      if (i == 1) {
         this.func_146284_a(this.buttonDone);
      }

   }

   public void func_73863_a(int i, int j, float f) {
      Block block = this.tileSign.func_145838_q();
      int meta = this.tileSign.func_145832_p();
      float rotation = (float)Direction.field_71579_d[meta] * 90.0F;
      IIcon onIcon = ((LOTRTileEntitySignCarved)this.tileSign).getOnBlockIcon();
      this.func_146276_q_();
      this.func_73732_a(this.field_146289_q, StatCollector.func_74838_a("sign.edit"), this.field_146294_l / 2, 40, 16777215);
      GL11.glPushMatrix();
      GL11.glTranslatef((float)(this.field_146294_l / 2), 0.0F, 50.0F);
      float f1 = 93.75F;
      GL11.glScalef(-f1, -f1, -f1);
      GL11.glTranslatef(0.0F, -1.0625F, 0.0F);
      GL11.glDisable(2929);
      GL11.glPushMatrix();
      float iconScale = 0.5F;
      GL11.glScalef(-iconScale, -iconScale, iconScale);
      GL11.glTranslatef(0.0F, 0.5F, 0.0F);
      this.field_146297_k.func_110434_K().func_110577_a(TextureMap.field_110575_b);
      itemRenderer.func_94149_a(-1, -1, onIcon, 2, 2);
      GL11.glPopMatrix();
      GL11.glEnable(2929);
      if (this.updateCounter / 6 % 2 == 0) {
         this.tileSign.lineBeingEdited = this.editLine;
      }

      GL11.glRotatef(rotation + 180.0F, 0.0F, 1.0F, 0.0F);
      TileEntityRendererDispatcher.field_147556_a.func_147549_a(this.tileSign, -0.5D, -0.75D, -0.5D, 0.0F);
      GL11.glDisable(2896);
      this.tileSign.lineBeingEdited = -1;
      GL11.glPopMatrix();
      super.func_73863_a(i, j, f);
   }
}
