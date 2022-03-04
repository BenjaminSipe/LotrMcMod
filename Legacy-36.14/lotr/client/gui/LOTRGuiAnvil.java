package lotr.client.gui;

import java.util.Iterator;
import java.util.List;
import lotr.common.enchant.LOTREnchantmentHelper;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.inventory.LOTRContainerAnvil;
import lotr.common.network.LOTRPacketAnvilEngraveOwner;
import lotr.common.network.LOTRPacketAnvilReforge;
import lotr.common.network.LOTRPacketAnvilRename;
import lotr.common.network.LOTRPacketHandler;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class LOTRGuiAnvil extends GuiContainer {
   public static final ResourceLocation anvilTexture = new ResourceLocation("lotr:gui/anvil.png");
   private LOTRContainerAnvil theAnvil;
   private ItemStack prevItemStack;
   private GuiButton buttonReforge;
   private GuiButton buttonEngraveOwner;
   private GuiTextField textFieldRename;
   private static int[] colorCodes = new int[16];

   public LOTRGuiAnvil(EntityPlayer entityplayer, int i, int j, int k) {
      super(new LOTRContainerAnvil(entityplayer, i, j, k));
      this.theAnvil = (LOTRContainerAnvil)this.field_147002_h;
      this.field_146999_f = 176;
      this.field_147000_g = 198;
   }

   public LOTRGuiAnvil(EntityPlayer entityplayer, LOTREntityNPC npc) {
      super(new LOTRContainerAnvil(entityplayer, npc));
      this.theAnvil = (LOTRContainerAnvil)this.field_147002_h;
      this.field_146999_f = 176;
      this.field_147000_g = 198;
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      this.buttonReforge = new LOTRGuiButtonReforge(0, this.field_147003_i + 25, this.field_147009_r + 78, 176, 39);
      this.buttonEngraveOwner = new LOTRGuiButtonReforge(1, this.field_147003_i + 5, this.field_147009_r + 78, 176, 59);
      this.field_146292_n.add(this.buttonReforge);
      this.field_146292_n.add(this.buttonEngraveOwner);
      Keyboard.enableRepeatEvents(true);
      this.textFieldRename = new GuiTextField(this.field_146289_q, this.field_147003_i + 62, this.field_147009_r + 24, 103, 12);
      this.textFieldRename.func_146193_g(-1);
      this.textFieldRename.func_146204_h(-1);
      this.textFieldRename.func_146185_a(false);
      this.textFieldRename.func_146203_f(40);
      this.prevItemStack = null;
   }

   public void func_146281_b() {
      super.func_146281_b();
      Keyboard.enableRepeatEvents(false);
   }

   public void func_73876_c() {
      super.func_73876_c();
      if (this.theAnvil.clientReforgeTime > 0) {
         --this.theAnvil.clientReforgeTime;
      }

      ItemStack itemstack = this.theAnvil.invInput.func_70301_a(0);
      if (itemstack != this.prevItemStack) {
         this.prevItemStack = itemstack;
         String var4;
         if (itemstack == null) {
            var4 = "";
         } else {
            LOTRContainerAnvil var10000 = this.theAnvil;
            var4 = LOTRContainerAnvil.stripFormattingCodes(itemstack.func_82833_r());
         }

         String textFieldText = var4;
         boolean textFieldEnabled = itemstack != null;
         this.textFieldRename.func_146180_a(textFieldText);
         this.textFieldRename.func_146184_c(textFieldEnabled);
         if (itemstack != null) {
            this.renameItem(textFieldText);
         }
      }

   }

   public void func_73863_a(int i, int j, float f) {
      ItemStack inputItem = this.theAnvil.invInput.func_70301_a(0);
      boolean canReforge = inputItem != null && LOTREnchantmentHelper.isReforgeable(inputItem) && this.theAnvil.reforgeCost > 0;
      boolean canEngrave = inputItem != null && LOTREnchantmentHelper.isReforgeable(inputItem) && this.theAnvil.engraveOwnerCost > 0;
      this.buttonReforge.field_146125_m = this.buttonReforge.field_146124_l = canReforge;
      this.buttonEngraveOwner.field_146125_m = this.buttonEngraveOwner.field_146124_l = canEngrave && this.theAnvil.canEngraveNewOwner(inputItem, this.field_146297_k.field_71439_g);
      super.func_73863_a(i, j, f);
      float z;
      String tooltip;
      if (this.buttonReforge.field_146125_m && this.buttonReforge.func_146115_a()) {
         z = this.field_73735_i;
         tooltip = StatCollector.func_74838_a("container.lotr.anvil.reforge");
         this.func_146279_a(tooltip, i - 12, j + 24);
         GL11.glDisable(2896);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         this.field_73735_i = z;
      }

      if (this.buttonEngraveOwner.field_146125_m && this.buttonEngraveOwner.func_146115_a()) {
         z = this.field_73735_i;
         tooltip = StatCollector.func_74838_a("container.lotr.anvil.engraveOwner");
         this.func_146279_a(tooltip, i - this.field_146289_q.func_78256_a(tooltip), j + 24);
         GL11.glDisable(2896);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         this.field_73735_i = z;
      }

      GL11.glDisable(2896);
      GL11.glDisable(3042);
      List itemNameFormatting = this.theAnvil.getActiveItemNameFormatting();
      Iterator var13 = itemNameFormatting.iterator();

      while(var13.hasNext()) {
         EnumChatFormatting formatting = (EnumChatFormatting)var13.next();
         int formattingID = formatting.ordinal();
         if (formatting.func_96302_c() && formattingID < colorCodes.length) {
            int color = colorCodes[formattingID];
            this.textFieldRename.func_146193_g(color);
         }
      }

      this.textFieldRename.func_146194_f();
      this.textFieldRename.func_146193_g(-1);
   }

   protected void func_146976_a(float f, int i, int j) {
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.field_146297_k.func_110434_K().func_110577_a(anvilTexture);
      this.func_73729_b(this.field_147003_i, this.field_147009_r, 0, 0, this.field_146999_f, this.field_147000_g);
      if (this.theAnvil.isTrader) {
         this.func_73729_b(this.field_147003_i + 75, this.field_147009_r + 69, 176, 21, 18, 18);
      }

      this.func_73729_b(this.field_147003_i + 59, this.field_147009_r + 20, 0, this.field_147000_g + (this.theAnvil.invInput.func_70301_a(0) != null ? 0 : 16), 110, 16);
      if (this.theAnvil.invOutput.func_70301_a(0) == null) {
         boolean flag = false;

         for(int l = 0; l < this.theAnvil.invInput.func_70302_i_(); ++l) {
            if (this.theAnvil.invInput.func_70301_a(l) != null) {
               flag = true;
               break;
            }
         }

         if (flag) {
            this.func_73729_b(this.field_147003_i + 99, this.field_147009_r + 56, this.field_146999_f, 0, 28, 21);
         }
      }

      if (this.buttonReforge.field_146125_m && this.buttonEngraveOwner.field_146125_m) {
         this.func_73729_b(this.field_147003_i + 5, this.field_147009_r + 78, 176, 99, 40, 20);
      } else if (this.buttonReforge.field_146125_m) {
         this.func_73729_b(this.field_147003_i + 25, this.field_147009_r + 78, 176, 79, 20, 20);
      }

   }

   protected void func_146979_b(int i, int j) {
      GL11.glDisable(2896);
      GL11.glDisable(3042);
      String s = this.theAnvil.isTrader ? StatCollector.func_74838_a("container.lotr.smith") : StatCollector.func_74838_a("container.lotr.anvil");
      this.field_146289_q.func_78276_b(s, 60, 6, 4210752);
      boolean reforge = this.buttonReforge.field_146124_l && this.buttonReforge.func_146115_a();
      boolean engraveOwner = this.buttonEngraveOwner.field_146124_l && this.buttonEngraveOwner.func_146115_a();
      String costText = null;
      int color = 8453920;
      ItemStack inputItem = this.theAnvil.invInput.func_70301_a(0);
      ItemStack outputItem = this.theAnvil.invOutput.func_70301_a(0);
      if (inputItem != null) {
         if (reforge && this.theAnvil.reforgeCost > 0) {
            costText = StatCollector.func_74837_a("container.lotr.anvil.reforgeCost", new Object[]{this.theAnvil.reforgeCost});
            if (!this.theAnvil.hasMaterialOrCoinAmount(this.theAnvil.reforgeCost)) {
               color = 16736352;
            }
         } else if (engraveOwner && this.theAnvil.engraveOwnerCost > 0) {
            costText = StatCollector.func_74837_a("container.lotr.anvil.engraveOwnerCost", new Object[]{this.theAnvil.engraveOwnerCost});
            if (!this.theAnvil.hasMaterialOrCoinAmount(this.theAnvil.engraveOwnerCost)) {
               color = 16736352;
            }
         } else if (this.theAnvil.materialCost > 0 && outputItem != null) {
            if (this.theAnvil.isTrader) {
               costText = StatCollector.func_74837_a("container.lotr.smith.cost", new Object[]{this.theAnvil.materialCost});
            } else {
               costText = StatCollector.func_74837_a("container.lotr.anvil.cost", new Object[]{this.theAnvil.materialCost});
            }

            if (!this.theAnvil.func_75147_a(this.theAnvil.invOutput, 0).func_82869_a(this.field_146297_k.field_71439_g)) {
               color = 16736352;
            }
         }
      }

      int x;
      if (costText != null) {
         int colorF = -16777216 | (color & 16579836) >> 2 | color & -16777216;
         x = this.field_146999_f - 8 - this.field_146289_q.func_78256_a(costText);
         int y = 94;
         if (this.field_146289_q.func_82883_a()) {
            func_73734_a(x - 3, y - 2, this.field_146999_f - 7, y + 10, -16777216);
            func_73734_a(x - 2, y - 1, this.field_146999_f - 8, y + 9, -12895429);
         } else {
            this.field_146289_q.func_78276_b(costText, x, y + 1, colorF);
            this.field_146289_q.func_78276_b(costText, x + 1, y, colorF);
            this.field_146289_q.func_78276_b(costText, x + 1, y + 1, colorF);
         }

         this.field_146289_q.func_78276_b(costText, x, y, color);
      }

      GL11.glEnable(2896);
      if (this.theAnvil.clientReforgeTime > 0) {
         float var10000 = (float)this.theAnvil.clientReforgeTime;
         LOTRContainerAnvil var10001 = this.theAnvil;
         float f = var10000 / 40.0F;
         x = (int)(f * 255.0F);
         x = MathHelper.func_76125_a(x, 0, 255);
         int overlayColor = 16777215 | x << 24;
         Slot slot = this.theAnvil.func_75147_a(this.theAnvil.invInput, 0);
         func_73734_a(slot.field_75223_e, slot.field_75221_f, slot.field_75223_e + 16, slot.field_75221_f + 16, overlayColor);
      }

   }

   protected void func_73869_a(char c, int i) {
      if (this.textFieldRename.func_146201_a(c, i)) {
         this.renameItem(this.textFieldRename.func_146179_b());
      } else {
         super.func_73869_a(c, i);
      }

   }

   private void renameItem(String rename) {
      ItemStack itemstack = this.theAnvil.invInput.func_70301_a(0);
      if (itemstack != null && !itemstack.func_82837_s()) {
         String displayNameStripped = LOTRContainerAnvil.stripFormattingCodes(itemstack.func_82833_r());
         String renameStripped = LOTRContainerAnvil.stripFormattingCodes(rename);
         if (renameStripped.equals(displayNameStripped)) {
            rename = "";
         }
      }

      this.theAnvil.updateItemName(rename);
      LOTRPacketAnvilRename packet = new LOTRPacketAnvilRename(rename);
      LOTRPacketHandler.networkWrapper.sendToServer(packet);
   }

   protected void func_73864_a(int i, int j, int k) {
      super.func_73864_a(i, j, k);
      this.textFieldRename.func_146192_a(i, j, k);
   }

   protected void func_146284_a(GuiButton button) {
      if (button.field_146124_l) {
         ItemStack inputItem;
         if (button == this.buttonReforge) {
            inputItem = this.theAnvil.invInput.func_70301_a(0);
            if (inputItem != null && this.theAnvil.reforgeCost > 0 && this.theAnvil.hasMaterialOrCoinAmount(this.theAnvil.reforgeCost)) {
               LOTRPacketAnvilReforge packet = new LOTRPacketAnvilReforge();
               LOTRPacketHandler.networkWrapper.sendToServer(packet);
            }
         } else if (button == this.buttonEngraveOwner) {
            inputItem = this.theAnvil.invInput.func_70301_a(0);
            if (inputItem != null && this.theAnvil.engraveOwnerCost > 0 && this.theAnvil.hasMaterialOrCoinAmount(this.theAnvil.engraveOwnerCost)) {
               LOTRPacketAnvilEngraveOwner packet = new LOTRPacketAnvilEngraveOwner();
               LOTRPacketHandler.networkWrapper.sendToServer(packet);
            }
         }
      }

   }

   static {
      for(int i = 0; i < 16; ++i) {
         int baseBrightness = (i >> 3 & 1) * 85;
         int r = (i >> 2 & 1) * 170 + baseBrightness;
         int g = (i >> 1 & 1) * 170 + baseBrightness;
         int b = (i >> 0 & 1) * 170 + baseBrightness;
         if (i == 6) {
            r += 85;
         }

         colorCodes[i] = (r & 255) << 16 | (g & 255) << 8 | b & 255;
      }

   }
}
