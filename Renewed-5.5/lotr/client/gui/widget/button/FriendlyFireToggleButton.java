package lotr.client.gui.widget.button;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lotr.client.gui.MiddleEarthFactionsScreen;
import lotr.common.data.LOTRLevelData;
import lotr.common.data.LOTRPlayerData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.button.Button.IPressable;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class FriendlyFireToggleButton extends Button {
   private static final List TOOLTIP_ENABLED = generateTooltip("enabled");
   private static final List TOOLTIP_DISABLED = generateTooltip("disabled");

   private static List generateTooltip(String state) {
      return (List)IntStream.of(new int[]{1, 2}).mapToObj((i) -> {
         return String.format("gui.lotr.factions.friendlyFire.%s.%d", state, i);
      }).map(TranslationTextComponent::new).collect(Collectors.toList());
   }

   public FriendlyFireToggleButton(int xIn, int yIn, IPressable onPress) {
      super(xIn, yIn, 16, 16, StringTextComponent.field_240750_d_, onPress);
   }

   private static LOTRPlayerData getClientPlayerData() {
      Minecraft mc = Minecraft.func_71410_x();
      return LOTRLevelData.clientInstance().getData(mc.field_71439_g);
   }

   public static void sendToggleToServer(Button button) {
      getClientPlayerData().getAlignmentData().toggleFriendlyFireEnabledAndSendToServer();
   }

   public void func_230431_b_(MatrixStack matStack, int mouseX, int mouseY, float f) {
      Minecraft mc = Minecraft.func_71410_x();
      mc.func_110434_K().func_110577_a(MiddleEarthFactionsScreen.FACTIONS_TEXTURE);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, this.field_230695_q_);
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      RenderSystem.enableDepthTest();
      int yOffset = this.func_230989_a_(this.func_230449_g_());
      this.func_238474_b_(matStack, this.field_230690_l_, this.field_230691_m_, 84, 142 + yOffset * this.field_230689_k_, this.field_230688_j_, this.field_230689_k_);
      this.func_230441_a_(matStack, mc, mouseX, mouseY);
   }

   protected int func_230989_a_(boolean hovered) {
      return isFriendlyFireEnabled() ? 1 : 0;
   }

   public static boolean isFriendlyFireEnabled() {
      return getClientPlayerData().getAlignmentData().isFriendlyFireEnabled();
   }

   public static List getTooltipLines() {
      return isFriendlyFireEnabled() ? TOOLTIP_ENABLED : TOOLTIP_DISABLED;
   }
}
