package lotr.client.gui.widget.button;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import lotr.client.gui.MiddleEarthMasterMenuScreen;
import lotr.client.gui.MiddleEarthMenuScreen;
import lotr.client.gui.map.MiddleEarthMapScreen;
import lotr.common.LOTRLog;
import lotr.common.init.LOTRWorldTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.ITextComponent;

public class MiddleEarthMenuButton extends Button {
   private final Class menuScreenClass;
   private final int verticalIconNumber;
   public final int menuKeyCode;

   public MiddleEarthMenuButton(int xIn, int yIn, Class cls, ITextComponent text, int vertNumber, int key) {
      super(xIn, yIn, 32, 32, text, (button) -> {
         ((MiddleEarthMenuButton)button).openMenuScreen();
      });
      this.menuScreenClass = cls;
      this.verticalIconNumber = vertNumber;
      this.menuKeyCode = key;
   }

   public void openMenuScreen() {
      if (this.menuScreenClass != null && this.canDisplayMenu()) {
         try {
            MiddleEarthMenuScreen screen = (MiddleEarthMenuScreen)this.menuScreenClass.newInstance();
            Minecraft.func_71410_x().func_147108_a(screen);
            MiddleEarthMasterMenuScreen.lastMenuScreen = screen.getClass();
         } catch (Exception var2) {
            LOTRLog.error("Error opening menu button screen");
            var2.printStackTrace();
         }
      }

   }

   public boolean canDisplayMenu() {
      return this.menuScreenClass == MiddleEarthMapScreen.class ? LOTRWorldTypes.hasMapFeaturesClientside() : true;
   }

   public void func_230431_b_(MatrixStack matStack, int mouseX, int mouseY, float f) {
      Minecraft minecraft = Minecraft.func_71410_x();
      FontRenderer fr = minecraft.field_71466_p;
      minecraft.func_110434_K().func_110577_a(MiddleEarthMasterMenuScreen.MENU_ICONS);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, this.field_230695_q_);
      this.func_238474_b_(matStack, this.field_230690_l_, this.field_230691_m_, 0 + (this.field_230693_o_ ? 0 : this.field_230688_j_ * 2) + (this.func_230449_g_() ? this.field_230688_j_ : 0), this.verticalIconNumber * this.field_230689_k_, this.field_230688_j_, this.field_230689_k_);
   }
}
