package lotr.client.gui;

import lotr.common.inventory.LOTRContainerBarrel;
import lotr.common.network.LOTRPacketBrewingButton;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.tileentity.LOTRTileEntityBarrel;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

public class LOTRGuiBarrel extends GuiContainer {
   private static ResourceLocation guiTexture = new ResourceLocation("lotr:gui/barrel/barrel.png");
   private static ResourceLocation brewingTexture = new ResourceLocation("lotr:gui/barrel/brewing.png");
   private LOTRTileEntityBarrel theBarrel;
   private GuiButton brewingButton;
   private float prevBrewAnim = -1.0F;
   private float brewAnim = -1.0F;

   public LOTRGuiBarrel(InventoryPlayer inv, LOTRTileEntityBarrel barrel) {
      super(new LOTRContainerBarrel(inv, barrel));
      this.theBarrel = barrel;
      this.field_146999_f = 210;
      this.field_147000_g = 221;
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      this.field_146292_n.add(this.brewingButton = new GuiButton(0, this.field_147003_i + 25, this.field_147009_r + 97, 100, 20, StatCollector.func_74838_a("container.lotr.barrel.startBrewing")));
   }

   public void func_73863_a(int i, int j, float f) {
      if (this.theBarrel.barrelMode == 0) {
         this.brewingButton.field_146124_l = this.theBarrel.func_70301_a(9) != null;
         this.brewingButton.field_146126_j = StatCollector.func_74838_a("container.lotr.barrel.startBrewing");
      }

      if (this.theBarrel.barrelMode == 1) {
         this.brewingButton.field_146124_l = this.theBarrel.func_70301_a(9) != null && this.theBarrel.func_70301_a(9).func_77960_j() > 0;
         this.brewingButton.field_146126_j = StatCollector.func_74838_a("container.lotr.barrel.stopBrewing");
      }

      if (this.theBarrel.barrelMode == 2) {
         this.brewingButton.field_146124_l = false;
         this.brewingButton.field_146126_j = StatCollector.func_74838_a("container.lotr.barrel.startBrewing");
      }

      super.func_73863_a(i, j, f);
   }

   protected void func_146284_a(GuiButton button) {
      if (button.field_146124_l && button.field_146127_k == 0) {
         LOTRPacketBrewingButton packet = new LOTRPacketBrewingButton();
         LOTRPacketHandler.networkWrapper.sendToServer(packet);
      }

   }

   protected void func_146979_b(int i, int j) {
      String s = this.theBarrel.func_145825_b();
      String s1 = this.theBarrel.getInvSubtitle();
      this.field_146289_q.func_78276_b(s, this.field_146999_f / 2 - this.field_146289_q.func_78256_a(s) / 2, 6, 4210752);
      this.field_146289_q.func_78276_b(s1, this.field_146999_f / 2 - this.field_146289_q.func_78256_a(s1) / 2, 17, 4210752);
      this.field_146289_q.func_78276_b(StatCollector.func_74838_a("container.inventory"), 25, 127, 4210752);
   }

   protected void func_146976_a(float f, int i, int j) {
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.field_146297_k.func_110434_K().func_110577_a(guiTexture);
      this.func_73729_b(this.field_147003_i, this.field_147009_r, 0, 0, this.field_146999_f, this.field_147000_g);
      int brewMode = this.theBarrel.barrelMode;
      int fullAmount = this.theBarrel.getBarrelFullAmountScaled(96);
      if (brewMode == 1) {
         fullAmount = this.theBarrel.getBrewProgressScaled(96);
      }

      this.prevBrewAnim = this.brewAnim;
      this.brewAnim = this.theBarrel.getBrewAnimationProgressScaledF(97, f);
      float brewAnimF = this.prevBrewAnim + (this.brewAnim - this.prevBrewAnim) * f;
      float brewAnimPc = this.theBarrel.getBrewAnimationProgressScaledF(1, f);
      if (brewMode == 1 || brewMode == 2) {
         int x0 = this.field_147003_i + 148;
         int x1 = this.field_147003_i + 196;
         int y0 = this.field_147009_r + 34;
         int y1 = this.field_147009_r + 130;
         int yFull = y1 - fullAmount;
         float yAnim = (float)y1 - brewAnimF;
         ItemStack itemstack = this.theBarrel.func_70301_a(9);
         if (itemstack != null) {
            IIcon liquidIcon = itemstack.func_77973_b().func_77617_a(-1);
            if (liquidIcon != null) {
               this.field_146297_k.func_110434_K().func_110577_a(TextureMap.field_110576_c);
               float minU = liquidIcon.func_94214_a(7.0D);
               float maxU = liquidIcon.func_94214_a(8.0D);
               float minV = liquidIcon.func_94207_b(7.0D);
               float maxV = liquidIcon.func_94207_b(8.0D);
               Tessellator tessellator = Tessellator.field_78398_a;
               tessellator.func_78382_b();
               tessellator.func_78374_a((double)x0, (double)y1, (double)this.field_73735_i, (double)minU, (double)maxV);
               tessellator.func_78374_a((double)x1, (double)y1, (double)this.field_73735_i, (double)maxU, (double)maxV);
               tessellator.func_78374_a((double)x1, (double)yFull, (double)this.field_73735_i, (double)maxU, (double)minV);
               tessellator.func_78374_a((double)x0, (double)yFull, (double)this.field_73735_i, (double)minU, (double)minV);
               tessellator.func_78381_a();
               int fullColor = 2167561;
               this.func_73733_a(x0, yFull, x1, y1, 0, -16777216 | fullColor);
            }
         }

         if (brewMode == 1) {
            this.field_146297_k.func_110434_K().func_110577_a(brewingTexture);
            GL11.glEnable(3042);
            OpenGlHelper.func_148821_a(770, 771, 1, 0);
            GL11.glDisable(3008);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, brewAnimPc);
            LOTRGuiScreenBase.drawTexturedModalRectFloat((double)((float)x0), (double)yAnim, 51.0D, 0.0D, (double)(x1 - x0), (double)((float)y1 - yAnim), 256, this.field_73735_i);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glEnable(3008);
            GL11.glDisable(3042);
         }

         this.field_146297_k.func_110434_K().func_110577_a(brewingTexture);
         this.func_73729_b(x0, y0, 1, 0, x1 - x0, y1 - y0);
      }

   }
}
