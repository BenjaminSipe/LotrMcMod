package lotr.client.gui.inv;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.ArrayList;
import java.util.List;
import lotr.client.gui.widget.button.KegBrewButton;
import lotr.client.render.model.vessel.VesselDrinkModel;
import lotr.client.util.LOTRClientUtil;
import lotr.common.inv.KegContainer;
import lotr.common.network.CPacketKegBrewButton;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.tileentity.KegTileEntity;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

public class KegScreen extends ContainerScreen {
   public static final ResourceLocation KEG_SCREEN = new ResourceLocation("lotr", "textures/gui/keg/keg.png");
   private static final ResourceLocation KEG_BREWING = new ResourceLocation("lotr", "textures/gui/keg/brewing.png");
   private KegBrewButton brewButton;
   private int brewAnim;
   private int brewAnimPrev;
   private static final int BREW_ANIMATION_TIME = 32;
   private static final int TITLE_BOX_CENTRE_X = 106;

   public KegScreen(KegContainer cont, PlayerInventory inv, ITextComponent title) {
      super(cont, inv, title);
      this.field_146999_f = 210;
      this.field_147000_g = 221;
   }

   public void func_231160_c_() {
      super.func_231160_c_();
      this.brewButton = (KegBrewButton)this.func_230480_a_(new KegBrewButton(this.field_147003_i + 87, this.field_147009_r + 92, (b) -> {
         LOTRPacketHandler.sendToServer(new CPacketKegBrewButton());
      }, this::renderBrewButtonTooltip));
   }

   public void func_231023_e_() {
      super.func_231023_e_();
      this.brewAnimPrev = this.brewAnim;
      if (((KegContainer)this.field_147002_h).getKegMode() == KegTileEntity.KegMode.BREWING) {
         ++this.brewAnim;
         if (this.brewAnim >= 32) {
            this.brewAnim = 0;
            this.brewAnimPrev = this.brewAnim;
         }
      } else {
         this.brewAnim = 0;
         this.brewAnimPrev = this.brewAnim;
      }

   }

   public void func_230430_a_(MatrixStack matStack, int x, int y, float f) {
      this.func_230446_a_(matStack);
      if (((KegContainer)this.field_147002_h).getKegMode() == KegTileEntity.KegMode.EMPTY) {
         this.brewButton.field_230693_o_ = ((KegContainer)this.field_147002_h).hasBrewingResult();
      } else if (((KegContainer)this.field_147002_h).getKegMode() == KegTileEntity.KegMode.BREWING) {
         this.brewButton.field_230693_o_ = ((KegContainer)this.field_147002_h).canFinishBrewingNow();
      } else if (((KegContainer)this.field_147002_h).getKegMode() == KegTileEntity.KegMode.FULL) {
         this.brewButton.field_230693_o_ = false;
      }

      super.func_230430_a_(matStack, x, y, f);
      this.func_230459_a_(matStack, x, y);
   }

   private void renderBrewButtonTooltip(Button b, MatrixStack matStack, int mouseX, int mouseY) {
      List tooltipLines = new ArrayList();
      if (((KegContainer)this.field_147002_h).getKegMode() == KegTileEntity.KegMode.EMPTY) {
         tooltipLines.add(new TranslationTextComponent("container.lotr.keg.start_brewing"));
      } else if (((KegContainer)this.field_147002_h).getKegMode() == KegTileEntity.KegMode.BREWING) {
         tooltipLines.add(new TranslationTextComponent("container.lotr.keg.finish_brewing"));
         if (((KegContainer)this.field_147002_h).canFinishBrewingNow()) {
            tooltipLines.add((new TranslationTextComponent("container.lotr.keg.finish_brewing.tooltip.allowed", new Object[]{((KegContainer)this.field_147002_h).getInterruptBrewingPotency().getDisplayName()})).func_240701_a_(new TextFormatting[]{TextFormatting.GRAY, TextFormatting.ITALIC}));
         } else {
            tooltipLines.add((new TranslationTextComponent("container.lotr.keg.finish_brewing.tooltip.not_allowed", new Object[]{((KegContainer)this.field_147002_h).getMinimumPotency().getDisplayName()})).func_240701_a_(new TextFormatting[]{TextFormatting.GRAY, TextFormatting.ITALIC}));
         }
      } else if (((KegContainer)this.field_147002_h).getKegMode() == KegTileEntity.KegMode.FULL) {
         tooltipLines.add(new TranslationTextComponent("container.lotr.keg.start_brewing"));
      }

      if (!tooltipLines.isEmpty()) {
         this.func_243308_b(matStack, tooltipLines, mouseX, mouseY);
      }

   }

   protected void func_230451_b_(MatrixStack matStack, int x, int y) {
      ITextComponent kegTitle = ((KegContainer)this.field_147002_h).getKegTitle();
      ITextComponent kegSubtitle = ((KegContainer)this.field_147002_h).getKegSubtitle();
      this.field_230712_o_.func_243248_b(matStack, kegTitle, (float)(106 - this.field_230712_o_.func_238414_a_(kegTitle) / 2), 6.0F, 4210752);
      this.field_230712_o_.func_243248_b(matStack, kegSubtitle, (float)(106 - this.field_230712_o_.func_238414_a_(kegSubtitle) / 2), 17.0F, 4210752);
      this.field_230712_o_.func_243248_b(matStack, this.field_213127_e.func_145748_c_(), 25.0F, 127.0F, 4210752);
   }

   protected void func_230450_a_(MatrixStack matStack, float partialTicks, int mouseX, int mouseY) {
      partialTicks = this.field_230706_i_.func_184121_ak();
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.field_230706_i_.func_110434_K().func_110577_a(KEG_SCREEN);
      int left = this.field_147003_i;
      int top = this.field_147009_r;
      this.func_238474_b_(matStack, left, top, 0, 0, this.field_146999_f, this.field_147000_g);
      KegTileEntity.KegMode mode = ((KegContainer)this.field_147002_h).getKegMode();
      int fullAmount = ((KegContainer)this.field_147002_h).getBarrelFullAmountScaled(96);
      float fullAmount16 = (float)((KegContainer)this.field_147002_h).getBarrelFullAmountScaled(16000) / 1000.0F;
      if (mode == KegTileEntity.KegMode.BREWING) {
         fullAmount = ((KegContainer)this.field_147002_h).getBrewProgressScaled(96);
         fullAmount16 = (float)((KegContainer)this.field_147002_h).getBrewProgressScaled(16000) / 1000.0F;
      }

      float brewAnimF = (float)this.brewAnimPrev + (float)(this.brewAnim - this.brewAnimPrev) * partialTicks;
      brewAnimF /= 32.0F;
      float brewAnimScaled = brewAnimF * 97.0F;
      if (mode == KegTileEntity.KegMode.BREWING || mode == KegTileEntity.KegMode.FULL) {
         int x0 = this.field_147003_i + 148;
         int x1 = this.field_147003_i + 196;
         int y0 = this.field_147009_r + 34;
         int y1 = this.field_147009_r + 130;
         int yFull = y1 - fullAmount;
         float yAnim = (float)y1 - brewAnimScaled;
         ItemStack result = ((KegContainer)this.field_147002_h).getBrewingResult();
         if (!result.func_190926_b()) {
            TextureAtlasSprite icon = VesselDrinkModel.getLiquidIconFor(result);
            this.field_230706_i_.func_110434_K().func_110577_a(icon.func_229241_m_().func_229223_g_());
            float minU = icon.func_94214_a(8.0D);
            float maxU = icon.func_94214_a(16.0D);
            float minV = icon.func_94207_b((double)(16.0F - fullAmount16));
            float maxV = icon.func_94207_b(16.0D);
            Tessellator tess = Tessellator.func_178181_a();
            BufferBuilder buf = tess.func_178180_c();
            buf.func_181668_a(7, DefaultVertexFormats.field_181707_g);
            int z = this.func_230927_p_();
            buf.func_225582_a_((double)x0, (double)y1, (double)z).func_225583_a_(minU, maxV).func_181675_d();
            buf.func_225582_a_((double)x1, (double)y1, (double)z).func_225583_a_(maxU, maxV).func_181675_d();
            buf.func_225582_a_((double)x1, (double)yFull, (double)z).func_225583_a_(maxU, minV).func_181675_d();
            buf.func_225582_a_((double)x0, (double)yFull, (double)z).func_225583_a_(minU, minV).func_181675_d();
            tess.func_78381_a();
            int fullColor = 2167561;
            this.func_238468_a_(matStack, x0, yFull, x1, y1, 0, -16777216 | fullColor);
         }

         if (mode == KegTileEntity.KegMode.BREWING) {
            this.field_230706_i_.func_110434_K().func_110577_a(KEG_BREWING);
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.disableAlphaTest();
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, brewAnimF * 0.75F);
            LOTRClientUtil.blitFloat(this, matStack, (float)x0, yAnim, 51.0F, 0.0F, (float)(x1 - x0), (float)y1 - yAnim);
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.enableAlphaTest();
            RenderSystem.disableBlend();
         }

         this.field_230706_i_.func_110434_K().func_110577_a(KEG_BREWING);
         this.func_238474_b_(matStack, x0, y0, 1, 0, x1 - x0, y1 - y0);
      }

   }
}
