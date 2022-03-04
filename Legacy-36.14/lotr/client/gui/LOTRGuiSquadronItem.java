package lotr.client.gui;

import lotr.common.LOTRSquadrons;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.LOTRPacketItemSquadron;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.util.StringUtils;
import org.lwjgl.opengl.GL11;

public class LOTRGuiSquadronItem extends LOTRGuiScreenBase {
   private static final ResourceLocation guiTexture = new ResourceLocation("lotr:gui/squadronItem.png");
   private static final RenderItem itemRenderer = new RenderItem();
   private int xSize = 200;
   private int ySize = 120;
   private int guiLeft;
   private int guiTop;
   private GuiButton buttonDone;
   private GuiTextField squadronNameField;
   private ItemStack theItem;
   private boolean sendSquadronUpdate = false;

   public void func_73866_w_() {
      this.guiLeft = (this.field_146294_l - this.xSize) / 2;
      this.guiTop = (this.field_146295_m - this.ySize) / 2;
      this.field_146292_n.add(this.buttonDone = new GuiButton(1, this.guiLeft + this.xSize / 2 - 40, this.guiTop + 85, 80, 20, StatCollector.func_74838_a("lotr.gui.squadronItem.done")));
      ItemStack itemstack = this.field_146297_k.field_71439_g.field_71071_by.func_70448_g();
      if (itemstack != null && itemstack.func_77973_b() instanceof LOTRSquadrons.SquadronItem) {
         this.theItem = itemstack;
         this.squadronNameField = new GuiTextField(this.field_146289_q, this.guiLeft + this.xSize / 2 - 80, this.guiTop + 50, 160, 20);
         this.squadronNameField.func_146203_f(LOTRSquadrons.SQUADRON_LENGTH_MAX);
         String squadron = LOTRSquadrons.getSquadron(this.theItem);
         if (!StringUtils.func_151246_b(squadron)) {
            this.squadronNameField.func_146180_a(squadron);
         }
      }

      if (this.theItem == null) {
         this.field_146297_k.field_71439_g.func_71053_j();
      }

   }

   public void func_73863_a(int i, int j, float f) {
      this.func_146276_q_();
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.field_146297_k.func_110434_K().func_110577_a(guiTexture);
      this.func_73729_b(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
      String s = this.theItem.func_82833_r();
      this.field_146289_q.func_78276_b(s, this.guiLeft + this.xSize / 2 - this.field_146289_q.func_78256_a(s) / 2, this.guiTop + 11, 4210752);
      s = StatCollector.func_74838_a("lotr.gui.squadronItem.squadron");
      this.field_146289_q.func_78276_b(s, this.squadronNameField.field_146209_f, this.squadronNameField.field_146210_g - this.field_146289_q.field_78288_b - 3, 4210752);
      boolean noSquadron = StringUtils.func_151246_b(this.squadronNameField.func_146179_b()) && !this.squadronNameField.func_146206_l();
      if (noSquadron) {
         String squadronMessage = StatCollector.func_74838_a("lotr.gui.squadronItem.none");
         this.squadronNameField.func_146180_a(EnumChatFormatting.DARK_GRAY + squadronMessage);
      }

      this.squadronNameField.func_146194_f();
      if (noSquadron) {
         this.squadronNameField.func_146180_a("");
      }

      super.func_73863_a(i, j, f);
   }

   public void func_73876_c() {
      super.func_73876_c();
      this.squadronNameField.func_146178_a();
      ItemStack itemstack = this.field_146297_k.field_71439_g.func_71045_bC();
      if (itemstack != null && itemstack.func_77973_b() instanceof LOTRSquadrons.SquadronItem) {
         this.theItem = itemstack;
      } else {
         this.field_146297_k.field_71439_g.func_71053_j();
      }

   }

   protected void func_146284_a(GuiButton button) {
      if (button == this.buttonDone) {
         this.field_146297_k.field_71439_g.func_71053_j();
      }

   }

   protected void func_73869_a(char c, int i) {
      if (!this.squadronNameField.func_146176_q() || !this.squadronNameField.func_146201_a(c, i)) {
         super.func_73869_a(c, i);
      }
   }

   protected void func_73864_a(int i, int j, int k) {
      super.func_73864_a(i, j, k);
      this.squadronNameField.func_146192_a(i, j, k);
   }

   public void func_146281_b() {
      super.func_146281_b();
      String squadron = this.squadronNameField.func_146179_b();
      LOTRPacketItemSquadron packet = new LOTRPacketItemSquadron(squadron);
      LOTRPacketHandler.networkWrapper.sendToServer(packet);
   }
}
