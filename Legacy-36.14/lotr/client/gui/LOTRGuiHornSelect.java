package lotr.client.gui;

import lotr.common.LOTRMod;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.LOTRPacketHornSelect;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

public class LOTRGuiHornSelect extends LOTRGuiScreenBase {
   private static final ResourceLocation guiTexture = new ResourceLocation("lotr:gui/horn_select.png");
   private static final RenderItem itemRenderer = new RenderItem();
   private int xSize = 176;
   private int ySize = 256;
   private int guiLeft;
   private int guiTop;

   public void func_73866_w_() {
      this.guiLeft = (this.field_146294_l - this.xSize) / 2;
      this.guiTop = (this.field_146295_m - this.ySize) / 2;
      this.field_146292_n.add(new GuiButton(1, this.guiLeft + 40, this.guiTop + 40, 120, 20, StatCollector.func_74838_a("lotr.gui.hornSelect.haltReady")));
      this.field_146292_n.add(new GuiButton(3, this.guiLeft + 40, this.guiTop + 75, 120, 20, StatCollector.func_74838_a("lotr.gui.hornSelect.summon")));
   }

   protected void func_146284_a(GuiButton button) {
      if (button.field_146124_l) {
         LOTRPacketHornSelect packet = new LOTRPacketHornSelect(button.field_146127_k);
         LOTRPacketHandler.networkWrapper.sendToServer(packet);
         this.field_146297_k.field_71439_g.func_71053_j();
      }

   }

   public void func_73863_a(int i, int j, float f) {
      this.func_146276_q_();
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.field_146297_k.func_110434_K().func_110577_a(guiTexture);
      this.func_73729_b(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
      String s = StatCollector.func_74838_a("lotr.gui.hornSelect.title");
      this.field_146289_q.func_78276_b(s, this.guiLeft + this.xSize / 2 - this.field_146289_q.func_78256_a(s) / 2, this.guiTop + 11, 4210752);
      super.func_73863_a(i, j, f);

      for(int k = 0; k < this.field_146292_n.size(); ++k) {
         GuiButton button = (GuiButton)this.field_146292_n.get(k);
         itemRenderer.func_77015_a(this.field_146289_q, this.field_146297_k.func_110434_K(), new ItemStack(LOTRMod.commandHorn, 1, button.field_146127_k), button.field_146128_h - 22, button.field_146129_i + 2);
      }

   }

   public void func_73876_c() {
      super.func_73876_c();
      ItemStack itemstack = this.field_146297_k.field_71439_g.field_71071_by.func_70448_g();
      if (itemstack == null || itemstack.func_77973_b() != LOTRMod.commandHorn || itemstack.func_77960_j() != 0) {
         this.field_146297_k.field_71439_g.func_71053_j();
      }

   }
}
