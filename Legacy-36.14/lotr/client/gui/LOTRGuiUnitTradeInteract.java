package lotr.client.gui;

import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.LOTRPacketUnitTraderInteract;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.StatCollector;

public class LOTRGuiUnitTradeInteract extends LOTRGuiNPCInteract {
   private GuiButton buttonTalk;
   private GuiButton buttonHire;

   public LOTRGuiUnitTradeInteract(LOTREntityNPC entity) {
      super(entity);
   }

   public void func_73866_w_() {
      this.buttonTalk = new GuiButton(0, this.field_146294_l / 2 - 65, this.field_146295_m / 5 * 3, 60, 20, StatCollector.func_74838_a("lotr.gui.npc.talk"));
      this.buttonHire = new GuiButton(1, this.field_146294_l / 2 + 5, this.field_146295_m / 5 * 3, 60, 20, StatCollector.func_74838_a("lotr.gui.npc.hire"));
      this.field_146292_n.add(this.buttonTalk);
      this.field_146292_n.add(this.buttonHire);
   }

   protected void func_146284_a(GuiButton button) {
      if (button.field_146124_l) {
         LOTRPacketUnitTraderInteract packet = new LOTRPacketUnitTraderInteract(this.theEntity.func_145782_y(), button.field_146127_k);
         LOTRPacketHandler.networkWrapper.sendToServer(packet);
      }

   }
}
