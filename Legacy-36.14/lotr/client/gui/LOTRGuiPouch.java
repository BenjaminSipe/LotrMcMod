package lotr.client.gui;

import lotr.common.inventory.LOTRContainerPouch;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.LOTRPacketRenamePouch;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

public class LOTRGuiPouch extends GuiContainer {
   public static ResourceLocation texture = new ResourceLocation("lotr:gui/pouch.png");
   private LOTRContainerPouch thePouch;
   private int pouchRows;
   private GuiTextField theGuiTextField;

   public LOTRGuiPouch(EntityPlayer entityplayer, int slot) {
      super(new LOTRContainerPouch(entityplayer, slot));
      this.thePouch = (LOTRContainerPouch)this.field_147002_h;
      this.pouchRows = this.thePouch.capacity / 9;
      this.field_147000_g = 180;
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      this.theGuiTextField = new GuiTextField(this.field_146289_q, this.field_147003_i + this.field_146999_f / 2 - 80, this.field_147009_r + 7, 160, 20);
      this.theGuiTextField.func_146180_a(this.thePouch.getDisplayName());
   }

   protected void func_146979_b(int i, int j) {
      this.field_146289_q.func_78276_b(StatCollector.func_74838_a("container.inventory"), 8, this.field_147000_g - 96 + 2, 4210752);
   }

   protected void func_146976_a(float f, int i, int j) {
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.field_146297_k.func_110434_K().func_110577_a(texture);
      this.func_73729_b(this.field_147003_i, this.field_147009_r, 0, 0, this.field_146999_f, this.field_147000_g);

      for(int l = 0; l < this.pouchRows; ++l) {
         this.func_73729_b(this.field_147003_i + 7, this.field_147009_r + 29 + l * 18, 0, 180, 162, 18);
      }

      GL11.glDisable(2896);
      this.theGuiTextField.func_146194_f();
      GL11.glEnable(2896);
   }

   public void func_73876_c() {
      super.func_73876_c();
      this.theGuiTextField.func_146178_a();
   }

   protected void func_73869_a(char c, int i) {
      if (this.theGuiTextField.func_146201_a(c, i)) {
         this.renamePouch();
      } else {
         super.func_73869_a(c, i);
      }

   }

   protected void func_73864_a(int i, int j, int k) {
      super.func_73864_a(i, j, k);
      this.theGuiTextField.func_146192_a(i, j, k);
   }

   private void renamePouch() {
      String name = this.theGuiTextField.func_146179_b();
      this.thePouch.renamePouch(name);
      LOTRPacketRenamePouch packet = new LOTRPacketRenamePouch(name);
      LOTRPacketHandler.networkWrapper.sendToServer(packet);
   }
}
