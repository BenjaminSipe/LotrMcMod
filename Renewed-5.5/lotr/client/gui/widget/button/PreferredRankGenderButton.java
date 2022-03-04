package lotr.client.gui.widget.button;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import lotr.client.gui.MiddleEarthFactionsScreen;
import lotr.common.data.LOTRLevelData;
import lotr.common.data.LOTRPlayerData;
import lotr.common.fac.RankGender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.button.Button.IPressable;
import net.minecraft.util.text.StringTextComponent;

public class PreferredRankGenderButton extends Button {
   private final RankGender rankGender;

   public PreferredRankGenderButton(int xIn, int yIn, RankGender gender, IPressable onPress) {
      super(xIn, yIn, 12, 12, StringTextComponent.field_240750_d_, onPress);
      this.rankGender = gender;
   }

   private static LOTRPlayerData getClientPlayerData() {
      Minecraft mc = Minecraft.func_71410_x();
      return LOTRLevelData.clientInstance().getData(mc.field_71439_g);
   }

   public static void sendPreferenceToServer(Button button) {
      PreferredRankGenderButton thisButton = (PreferredRankGenderButton)button;
      getClientPlayerData().getMiscData().setPreferredRankGenderAndSendToServer(thisButton.rankGender);
   }

   public void func_230431_b_(MatrixStack matStack, int mouseX, int mouseY, float f) {
      Minecraft mc = Minecraft.func_71410_x();
      mc.func_110434_K().func_110577_a(MiddleEarthFactionsScreen.FACTIONS_TEXTURE);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, this.field_230695_q_);
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      RenderSystem.enableDepthTest();
      int uMin = 60 + this.rankGender.ordinal() * this.field_230688_j_;
      int yOffset = this.func_230989_a_(this.func_230449_g_());
      this.func_238474_b_(matStack, this.field_230690_l_, this.field_230691_m_, uMin, 142 + yOffset * this.field_230689_k_, this.field_230688_j_, this.field_230689_k_);
      this.func_230441_a_(matStack, mc, mouseX, mouseY);
   }

   protected int func_230989_a_(boolean hovered) {
      return hovered ? 2 : (this.isCurrentlyPreferredGender() ? 1 : 0);
   }

   private boolean isCurrentlyPreferredGender() {
      return getClientPlayerData().getMiscData().getPreferredRankGender() == this.rankGender;
   }
}
