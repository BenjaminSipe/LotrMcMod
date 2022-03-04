package lotr.client.gui;

import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.entity.npc.LOTRTradeable;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.LOTRPacketTraderInteract;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.StatCollector;

public class LOTRGuiTradeInteract extends LOTRGuiNPCInteract {
   private GuiButton buttonTalk;
   private GuiButton buttonTrade;
   private GuiButton buttonExchange;
   private GuiButton buttonSmith;

   public LOTRGuiTradeInteract(LOTREntityNPC entity) {
      super(entity);
   }

   public void func_73866_w_() {
      this.buttonTalk = new GuiButton(0, this.field_146294_l / 2 - 65, this.field_146295_m / 5 * 3, 60, 20, StatCollector.func_74838_a("lotr.gui.npc.talk"));
      this.buttonTrade = new GuiButton(1, this.field_146294_l / 2 + 5, this.field_146295_m / 5 * 3, 60, 20, StatCollector.func_74838_a("lotr.gui.npc.trade"));
      this.buttonExchange = new GuiButton(2, this.field_146294_l / 2 - 65, this.field_146295_m / 5 * 3 + 25, 130, 20, StatCollector.func_74838_a("lotr.gui.npc.exchange"));
      this.field_146292_n.add(this.buttonTalk);
      this.field_146292_n.add(this.buttonTrade);
      this.field_146292_n.add(this.buttonExchange);
      if (this.theEntity instanceof LOTRTradeable.Smith) {
         GuiButton var10000 = this.buttonTalk;
         var10000.field_146128_h -= 35;
         var10000 = this.buttonTrade;
         var10000.field_146128_h -= 35;
         this.buttonSmith = new GuiButton(3, this.field_146294_l / 2 + 40, this.field_146295_m / 5 * 3, 60, 20, StatCollector.func_74838_a("lotr.gui.npc.smith"));
         this.field_146292_n.add(this.buttonSmith);
      }

   }

   protected void func_146284_a(GuiButton button) {
      if (button.field_146124_l) {
         LOTRPacketTraderInteract packet = new LOTRPacketTraderInteract(this.theEntity.func_145782_y(), button.field_146127_k);
         LOTRPacketHandler.networkWrapper.sendToServer(packet);
      }

   }
}
