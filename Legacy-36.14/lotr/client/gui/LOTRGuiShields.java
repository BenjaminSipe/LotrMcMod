package lotr.client.gui;

import java.util.List;
import lotr.client.render.LOTRRenderShield;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRShields;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.LOTRPacketSelectShield;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class LOTRGuiShields extends LOTRGuiMenuBase {
   private static ModelBiped playerModel = new ModelBiped();
   private int modelX;
   private int modelY;
   private float modelRotation = -140.0F;
   private float modelRotationPrev;
   private int isMouseDown;
   private int mouseX;
   private int mouseY;
   private int prevMouseX;
   private LOTRShields.ShieldType currentShieldType;
   private static int currentShieldTypeID;
   private LOTRShields currentShield;
   private static int currentShieldID;
   private GuiButton shieldLeft;
   private GuiButton shieldRight;
   private GuiButton shieldSelect;
   private GuiButton shieldRemove;
   private GuiButton changeCategory;

   public LOTRGuiShields() {
      this.modelRotationPrev = this.modelRotation;
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      this.modelX = this.guiLeft + this.xSize / 2;
      this.modelY = this.guiTop + 40;
      this.field_146292_n.add(this.shieldLeft = new LOTRGuiButtonShieldsArrows(0, true, this.guiLeft + this.xSize / 2 - 64, this.guiTop + 207));
      this.field_146292_n.add(this.shieldSelect = new GuiButton(1, this.guiLeft + this.xSize / 2 - 40, this.guiTop + 195, 80, 20, StatCollector.func_74838_a("lotr.gui.shields.select")));
      this.field_146292_n.add(this.shieldRight = new LOTRGuiButtonShieldsArrows(2, false, this.guiLeft + this.xSize / 2 + 44, this.guiTop + 207));
      this.field_146292_n.add(this.shieldRemove = new GuiButton(3, this.guiLeft + this.xSize / 2 - 40, this.guiTop + 219, 80, 20, StatCollector.func_74838_a("lotr.gui.shields.remove")));
      this.field_146292_n.add(this.changeCategory = new GuiButton(4, this.guiLeft + this.xSize / 2 - 80, this.guiTop + 250, 160, 20, ""));
      LOTRShields equippedShield = this.getPlayerEquippedShield();
      if (equippedShield != null) {
         currentShieldTypeID = equippedShield.shieldType.ordinal();
         currentShieldID = equippedShield.shieldID;
      }

      this.updateCurrentShield(0, 0);
   }

   private void updateCurrentShield(int shield, int type) {
      if (shield != 0) {
         currentShieldID += shield;
         currentShieldID = Math.max(currentShieldID, 0);
         currentShieldID = Math.min(currentShieldID, this.currentShieldType.list.size() - 1);
      }

      if (type != 0) {
         currentShieldTypeID += type;
         if (currentShieldTypeID > LOTRShields.ShieldType.values().length - 1) {
            currentShieldTypeID = 0;
         }

         if (currentShieldTypeID < 0) {
            currentShieldTypeID = LOTRShields.ShieldType.values().length - 1;
         }

         currentShieldID = 0;
      }

      this.currentShieldType = LOTRShields.ShieldType.values()[currentShieldTypeID];
      this.currentShield = (LOTRShields)this.currentShieldType.list.get(currentShieldID);

      while(true) {
         while(!this.currentShield.canDisplay(this.field_146297_k.field_71439_g)) {
            if ((shield < 0 || type != 0) && this.canGoLeft()) {
               this.updateCurrentShield(-1, 0);
            } else if ((shield > 0 || type != 0) && this.canGoRight()) {
               this.updateCurrentShield(1, 0);
            } else {
               this.updateCurrentShield(0, 1);
            }
         }

         return;
      }
   }

   private boolean canGoLeft() {
      for(int i = 0; i <= currentShieldID - 1; ++i) {
         LOTRShields shield = (LOTRShields)this.currentShieldType.list.get(i);
         if (shield.canDisplay(this.field_146297_k.field_71439_g)) {
            return true;
         }
      }

      return false;
   }

   private boolean canGoRight() {
      for(int i = currentShieldID + 1; i <= this.currentShieldType.list.size() - 1; ++i) {
         LOTRShields shield = (LOTRShields)this.currentShieldType.list.get(i);
         if (shield.canDisplay(this.field_146297_k.field_71439_g)) {
            return true;
         }
      }

      return false;
   }

   private LOTRShields getPlayerEquippedShield() {
      return LOTRLevelData.getData((EntityPlayer)this.field_146297_k.field_71439_g).getShield();
   }

   public void func_73876_c() {
      super.func_73876_c();
      this.modelRotationPrev = this.modelRotation;
      this.modelRotationPrev = MathHelper.func_76142_g(this.modelRotationPrev);
      this.modelRotation = MathHelper.func_76142_g(this.modelRotation);
      boolean mouseWithinModel = Math.abs(this.mouseX - this.modelX) <= 60 && Math.abs(this.mouseY - this.modelY) <= 80;
      if (Mouse.isButtonDown(0)) {
         if (this.isMouseDown == 0 || this.isMouseDown == 1) {
            if (this.isMouseDown == 0) {
               if (mouseWithinModel) {
                  this.isMouseDown = 1;
               }
            } else if (this.mouseX != this.prevMouseX) {
               float move = (float)(-(this.mouseX - this.prevMouseX)) * 1.0F;
               this.modelRotation += move;
            }

            this.prevMouseX = this.mouseX;
         }
      } else {
         this.isMouseDown = 0;
      }

   }

   public void func_73863_a(int i, int j, float f) {
      this.mouseX = i;
      this.mouseY = j;
      this.func_146276_q_();
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      String s = StatCollector.func_74838_a("lotr.gui.shields.title");
      this.drawCenteredString(s, this.guiLeft + this.xSize / 2, this.guiTop - 30, 16777215);
      GL11.glEnable(2903);
      RenderHelper.func_74519_b();
      GL11.glPushMatrix();
      GL11.glDisable(2884);
      GL11.glEnable(32826);
      GL11.glEnable(3008);
      GL11.glTranslatef((float)this.modelX, (float)this.modelY, 50.0F);
      float scale = 55.0F;
      GL11.glScalef(-scale, scale, scale);
      GL11.glRotatef(-30.0F, 1.0F, 0.0F, 0.0F);
      GL11.glRotatef(this.modelRotationPrev + (this.modelRotation - this.modelRotationPrev) * f, 0.0F, 1.0F, 0.0F);
      this.field_146297_k.func_110434_K().func_110577_a(this.field_146297_k.field_71439_g.func_110306_p());
      playerModel.func_78088_a((Entity)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
      LOTRRenderShield.renderShield(this.currentShield, (EntityLivingBase)null, playerModel);
      GL11.glDisable(32826);
      GL11.glEnable(2884);
      GL11.glPopMatrix();
      RenderHelper.func_74518_a();
      OpenGlHelper.func_77473_a(OpenGlHelper.field_77476_b);
      GL11.glDisable(3553);
      OpenGlHelper.func_77473_a(OpenGlHelper.field_77478_a);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      int x = this.guiLeft + this.xSize / 2;
      int y = this.guiTop + 145;
      s = this.currentShield.getShieldName();
      this.drawCenteredString(s, x, y, 16777215);
      y += this.field_146289_q.field_78288_b * 2;
      List desc = this.field_146289_q.func_78271_c(this.currentShield.getShieldDesc(), 220);

      for(int l = 0; l < desc.size(); ++l) {
         s = (String)desc.get(l);
         this.drawCenteredString(s, x, y, 16777215);
         y += this.field_146289_q.field_78288_b;
      }

      this.shieldLeft.field_146124_l = this.canGoLeft();
      this.shieldSelect.field_146124_l = this.currentShield.canPlayerWear(this.field_146297_k.field_71439_g);
      this.shieldSelect.field_146126_j = this.getPlayerEquippedShield() == this.currentShield ? StatCollector.func_74838_a("lotr.gui.shields.selected") : StatCollector.func_74838_a("lotr.gui.shields.select");
      this.shieldRight.field_146124_l = this.canGoRight();
      this.shieldRemove.field_146124_l = this.getPlayerEquippedShield() != null && this.getPlayerEquippedShield() == this.currentShield;
      this.changeCategory.field_146126_j = this.currentShieldType.getDisplayName();
      super.func_73863_a(i, j, f);
   }

   protected void func_146284_a(GuiButton button) {
      if (button.field_146124_l) {
         if (button == this.shieldLeft) {
            this.updateCurrentShield(-1, 0);
         } else {
            LOTRPacketSelectShield packet;
            if (button == this.shieldSelect) {
               this.updateCurrentShield(0, 0);
               packet = new LOTRPacketSelectShield(this.currentShield);
               LOTRPacketHandler.networkWrapper.sendToServer(packet);
            } else if (button == this.shieldRight) {
               this.updateCurrentShield(1, 0);
            } else if (button == this.shieldRemove) {
               this.updateCurrentShield(0, 0);
               packet = new LOTRPacketSelectShield((LOTRShields)null);
               LOTRPacketHandler.networkWrapper.sendToServer(packet);
            } else if (button == this.changeCategory) {
               this.updateCurrentShield(0, 1);
            } else {
               super.func_146284_a(button);
            }
         }
      }

   }

   static {
      playerModel.field_78091_s = false;
   }
}
