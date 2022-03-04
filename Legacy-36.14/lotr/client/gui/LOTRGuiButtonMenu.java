package lotr.client.gui;

import lotr.common.LOTRMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class LOTRGuiButtonMenu extends GuiButton {
   private LOTRGuiMenu parentGUI;
   private Class menuScreenClass;
   public final int menuKeyCode;

   public LOTRGuiButtonMenu(LOTRGuiMenu gui, int i, int x, int y, Class cls, String s, int key) {
      super(i, x, y, 32, 32, s);
      this.parentGUI = gui;
      this.menuScreenClass = cls;
      this.menuKeyCode = key;
   }

   public LOTRGuiMenuBase openMenu() {
      try {
         return (LOTRGuiMenuBase)this.menuScreenClass.newInstance();
      } catch (Exception var2) {
         var2.printStackTrace();
         return null;
      }
   }

   public boolean canDisplayMenu() {
      if (this.menuScreenClass != LOTRGuiMap.class) {
         return true;
      } else {
         World world = Minecraft.func_71410_x().field_71441_e;
         return world != null && world.func_72912_H().func_76067_t() != LOTRMod.worldTypeMiddleEarthClassic;
      }
   }

   public void func_146112_a(Minecraft mc, int i, int j) {
      if (this.field_146125_m) {
         FontRenderer fontrenderer = mc.field_71466_p;
         mc.func_110434_K().func_110577_a(LOTRGuiMenu.menuIconsTexture);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         this.field_146123_n = i >= this.field_146128_h && j >= this.field_146129_i && i < this.field_146128_h + this.field_146120_f && j < this.field_146129_i + this.field_146121_g;
         this.func_73729_b(this.field_146128_h, this.field_146129_i, 0 + (this.field_146124_l ? 0 : this.field_146120_f * 2) + (this.field_146123_n ? this.field_146120_f : 0), this.field_146127_k * this.field_146121_g, this.field_146120_f, this.field_146121_g);
         this.func_146119_b(mc, i, j);
      }

   }
}
