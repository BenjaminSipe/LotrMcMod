package lotr.client.gui;

import lotr.common.inventory.LOTRContainerDaleCracker;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

public class LOTRGuiDaleCracker extends GuiContainer {
   private static ResourceLocation texture = new ResourceLocation("lotr:gui/daleCracker.png");
   private LOTRContainerDaleCracker theCracker;
   private GuiButton buttonSeal;

   public LOTRGuiDaleCracker(EntityPlayer entityplayer) {
      super(new LOTRContainerDaleCracker(entityplayer));
      this.theCracker = (LOTRContainerDaleCracker)this.field_147002_h;
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      this.field_146292_n.add(this.buttonSeal = new GuiButton(0, this.field_147003_i + this.field_146999_f / 2 - 40, this.field_147009_r + 48, 80, 20, StatCollector.func_74838_a("lotr.gui.daleCracker.seal")));
      this.buttonSeal.field_146124_l = false;
   }

   protected void func_146979_b(int i, int j) {
      String s = StatCollector.func_74838_a("lotr.gui.daleCracker");
      this.field_146289_q.func_78276_b(s, this.field_146999_f / 2 - this.field_146289_q.func_78256_a(s) / 2, 6, 4210752);
      this.field_146289_q.func_78276_b(StatCollector.func_74838_a("container.inventory"), 8, this.field_147000_g - 96 + 2, 4210752);
   }

   protected void func_146976_a(float f, int i, int j) {
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.field_146297_k.func_110434_K().func_110577_a(texture);
      this.func_73729_b(this.field_147003_i, this.field_147009_r, 0, 0, this.field_146999_f, this.field_147000_g);
   }

   public void func_73876_c() {
      super.func_73876_c();
      this.buttonSeal.field_146124_l = !this.theCracker.isCrackerInvEmpty();
   }

   protected boolean func_146983_a(int i) {
      return false;
   }

   protected void func_146284_a(GuiButton button) {
      if (button.field_146124_l && button == this.buttonSeal && !this.theCracker.isCrackerInvEmpty()) {
         this.theCracker.sendSealingPacket(this.field_146297_k.field_71439_g);
         this.field_146297_k.func_147108_a((GuiScreen)null);
      }

   }
}
