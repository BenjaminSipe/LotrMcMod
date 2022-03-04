package lotr.client.gui;

import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.LOTRPacketUnitTraderInteract;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.StatCollector;

public class LOTRGuiTradeUnitTradeInteract extends LOTRGuiTradeInteract {
   private GuiButton buttonHire;

   public LOTRGuiTradeUnitTradeInteract(LOTREntityNPC entity) {
      super(entity);
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      this.buttonHire = new GuiButton(-1, this.field_146294_l / 2 - 65, this.field_146295_m / 5 * 3 + 50, 130, 20, StatCollector.func_74838_a("lotr.gui.npc.hire"));
      this.field_146292_n.add(this.buttonHire);
   }

   protected void func_146284_a(GuiButton button) {
      if (button.field_146124_l) {
         if (button == this.buttonHire) {
            LOTRPacketUnitTraderInteract packet = new LOTRPacketUnitTraderInteract(this.theEntity.func_145782_y(), 1);
            LOTRPacketHandler.networkWrapper.sendToServer(packet);
         } else {
            super.func_146284_a(button);
         }
      }

   }
}
