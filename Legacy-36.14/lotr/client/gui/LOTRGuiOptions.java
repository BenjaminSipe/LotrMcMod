package lotr.client.gui;

import lotr.common.LOTRLevelData;
import lotr.common.LOTRPlayerData;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.LOTRPacketSetOption;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

public class LOTRGuiOptions extends LOTRGuiMenuBase {
   private LOTRGuiButtonOptions buttonFriendlyFire;
   private LOTRGuiButtonOptions buttonHiredDeathMessages;
   private LOTRGuiButtonOptions buttonAlignment;
   private LOTRGuiButtonOptions buttonMapLocation;
   private LOTRGuiButtonOptions buttonConquest;
   private LOTRGuiButtonOptions buttonFeminineRank;

   public void func_73866_w_() {
      super.func_73866_w_();
      int buttonX = this.guiLeft + this.xSize / 2 - 100;
      int buttonY = this.guiTop + 40;
      this.field_146292_n.add(this.buttonFriendlyFire = new LOTRGuiButtonOptions(0, buttonX, buttonY, 200, 20, "lotr.gui.options.friendlyFire"));
      this.field_146292_n.add(this.buttonHiredDeathMessages = new LOTRGuiButtonOptions(1, buttonX, buttonY + 24, 200, 20, "lotr.gui.options.hiredDeathMessages"));
      this.field_146292_n.add(this.buttonAlignment = new LOTRGuiButtonOptions(2, buttonX, buttonY + 48, 200, 20, "lotr.gui.options.showAlignment"));
      this.field_146292_n.add(this.buttonMapLocation = new LOTRGuiButtonOptions(3, buttonX, buttonY + 72, 200, 20, "lotr.gui.options.showMapLocation"));
      this.field_146292_n.add(this.buttonConquest = new LOTRGuiButtonOptions(5, buttonX, buttonY + 96, 200, 20, "lotr.gui.options.conquest"));
      this.field_146292_n.add(this.buttonFeminineRank = new LOTRGuiButtonOptions(4, buttonX, buttonY + 120, 200, 20, "lotr.gui.options.femRank"));
   }

   public void func_73863_a(int i, int j, float f) {
      this.func_146276_q_();
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      String s = StatCollector.func_74838_a("lotr.gui.options.title");
      this.field_146289_q.func_78276_b(s, this.guiLeft + 100 - this.field_146289_q.func_78256_a(s) / 2, this.guiTop - 30, 16777215);
      s = StatCollector.func_74838_a("lotr.gui.options.worldSettings");
      this.field_146289_q.func_78276_b(s, this.guiLeft + 100 - this.field_146289_q.func_78256_a(s) / 2, this.guiTop + 10, 16777215);
      LOTRPlayerData pd = LOTRLevelData.getData((EntityPlayer)this.field_146297_k.field_71439_g);
      this.buttonFriendlyFire.setState(pd.getFriendlyFire());
      this.buttonHiredDeathMessages.setState(pd.getEnableHiredDeathMessages());
      this.buttonAlignment.setState(!pd.getHideAlignment());
      this.buttonMapLocation.setState(!pd.getHideMapLocation());
      this.buttonConquest.setState(pd.getEnableConquestKills());
      this.buttonFeminineRank.setState(pd.getFemRankOverride());
      super.func_73863_a(i, j, f);

      for(int k = 0; k < this.field_146292_n.size(); ++k) {
         GuiButton button = (GuiButton)this.field_146292_n.get(k);
         if (button instanceof LOTRGuiButtonOptions) {
            ((LOTRGuiButtonOptions)button).drawTooltip(this.field_146297_k, i, j);
         }
      }

   }

   protected void func_146284_a(GuiButton button) {
      if (button.field_146124_l) {
         if (button instanceof LOTRGuiButtonOptions) {
            LOTRPacketSetOption packet = new LOTRPacketSetOption(button.field_146127_k);
            LOTRPacketHandler.networkWrapper.sendToServer(packet);
         } else {
            super.func_146284_a(button);
         }
      }

   }
}
