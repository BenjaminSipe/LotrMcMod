package lotr.client.gui;

import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.inventory.LOTRContainerCoinExchange;
import lotr.common.network.LOTRPacketCoinExchange;
import lotr.common.network.LOTRPacketHandler;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

public class LOTRGuiCoinExchange extends GuiContainer {
   public static ResourceLocation guiTexture = new ResourceLocation("lotr:gui/coin_exchange.png");
   private LOTRContainerCoinExchange theContainer;
   private GuiButton buttonLeft;
   private GuiButton buttonRight;

   public LOTRGuiCoinExchange(EntityPlayer entityplayer, LOTREntityNPC npc) {
      super(new LOTRContainerCoinExchange(entityplayer, npc));
      this.theContainer = (LOTRContainerCoinExchange)this.field_147002_h;
      this.field_147000_g = 188;
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      int i = this.field_147003_i + this.field_146999_f / 2;
      int j = 28;
      int k = 16;
      this.field_146292_n.add(this.buttonLeft = new LOTRGuiButtonCoinExchange(0, i - j - k, this.field_147009_r + 45));
      this.field_146292_n.add(this.buttonRight = new LOTRGuiButtonCoinExchange(1, i + j - k, this.field_147009_r + 45));
   }

   public void func_73863_a(int i, int j, float f) {
      this.buttonLeft.field_146124_l = !this.theContainer.exchanged && this.theContainer.exchangeInv.func_70301_a(0) != null;
      this.buttonRight.field_146124_l = !this.theContainer.exchanged && this.theContainer.exchangeInv.func_70301_a(1) != null;
      super.func_73863_a(i, j, f);
   }

   protected void func_146979_b(int i, int j) {
      this.drawCenteredString(StatCollector.func_74838_a("container.lotr.coinExchange"), 89, 11, 4210752);
      this.field_146289_q.func_78276_b(StatCollector.func_74838_a("container.inventory"), 8, 94, 4210752);
   }

   protected void func_146976_a(float f, int i, int j) {
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.field_146297_k.func_110434_K().func_110577_a(guiTexture);
      this.func_73729_b(this.field_147003_i, this.field_147009_r, 0, 0, this.field_146999_f, this.field_147000_g);
      if (this.theContainer.exchanged) {
         for(int l = 0; l < this.theContainer.exchangeInv.func_70302_i_(); ++l) {
            Slot slot = this.theContainer.func_75147_a(this.theContainer.exchangeInv, l);
            if (slot.func_75216_d()) {
               this.func_73729_b(this.field_147003_i + slot.field_75223_e - 5, this.field_147009_r + slot.field_75221_f - 5, 176, 51, 26, 26);
            }
         }
      }

   }

   private void drawCenteredString(String s, int i, int j, int k) {
      this.field_146289_q.func_78276_b(s, i - this.field_146289_q.func_78256_a(s) / 2, j, k);
   }

   protected void func_146284_a(GuiButton button) {
      if (button.field_146124_l) {
         if (button != this.buttonLeft && button != this.buttonRight) {
            super.func_146284_a(button);
         } else {
            LOTRPacketCoinExchange packet = new LOTRPacketCoinExchange(button.field_146127_k);
            LOTRPacketHandler.networkWrapper.sendToServer(packet);
         }
      }

   }
}
