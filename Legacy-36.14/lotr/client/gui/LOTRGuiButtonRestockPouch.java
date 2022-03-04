package lotr.client.gui;

import lotr.client.LOTRReflectionClient;
import lotr.common.LOTRMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class LOTRGuiButtonRestockPouch extends GuiButton {
   private static final ResourceLocation texture = new ResourceLocation("lotr:gui/widgets.png");
   private final GuiContainer parentGUI;

   public LOTRGuiButtonRestockPouch(GuiContainer parent, int i, int j, int k) {
      super(i, j, k, 10, 10, "");
      this.parentGUI = parent;
   }

   public void func_146112_a(Minecraft mc, int i, int j) {
      this.checkPouchRestockEnabled(mc);
      if (this.field_146125_m) {
         FontRenderer fontrenderer = mc.field_71466_p;
         mc.func_110434_K().func_110577_a(texture);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         this.field_146123_n = i >= this.field_146128_h && j >= this.field_146129_i && i < this.field_146128_h + this.field_146120_f && j < this.field_146129_i + this.field_146121_g;
         int k = this.func_146114_a(this.field_146123_n);
         this.func_73729_b(this.field_146128_h, this.field_146129_i, 0, 128 + k * 10, this.field_146120_f, this.field_146121_g);
         this.func_146119_b(mc, i, j);
      }

   }

   private void checkPouchRestockEnabled(Minecraft mc) {
      InventoryPlayer inv = mc.field_71439_g.field_71071_by;
      this.field_146124_l = this.field_146125_m = inv.func_146028_b(LOTRMod.pouch);
      if (this.parentGUI instanceof GuiContainerCreative) {
         int creativeTabIndex = LOTRReflectionClient.getCreativeTabIndex((GuiContainerCreative)this.parentGUI);
         if (creativeTabIndex != CreativeTabs.field_78036_m.func_78021_a()) {
            this.field_146124_l = this.field_146125_m = false;
         }
      }

   }
}
