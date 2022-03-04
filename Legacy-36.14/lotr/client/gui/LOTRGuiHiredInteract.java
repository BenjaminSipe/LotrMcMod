package lotr.client.gui;

import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.LOTRPacketHiredUnitInteract;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.StatCollector;

public class LOTRGuiHiredInteract extends LOTRGuiNPCInteract {
   public LOTRGuiHiredInteract(LOTREntityNPC entity) {
      super(entity);
   }

   public void func_73866_w_() {
      this.field_146292_n.add(new GuiButton(0, this.field_146294_l / 2 - 65, this.field_146295_m / 5 * 3, 60, 20, StatCollector.func_74838_a("lotr.gui.npc.talk")));
      this.field_146292_n.add(new GuiButton(1, this.field_146294_l / 2 + 5, this.field_146295_m / 5 * 3, 60, 20, StatCollector.func_74838_a("lotr.gui.npc.command")));
      this.field_146292_n.add(new GuiButton(2, this.field_146294_l / 2 - 65, this.field_146295_m / 5 * 3 + 25, 130, 20, StatCollector.func_74838_a("lotr.gui.npc.dismiss")));
      ((GuiButton)this.field_146292_n.get(0)).field_146124_l = this.theEntity.getSpeechBank(this.field_146297_k.field_71439_g) != null;
   }

   protected void func_146284_a(GuiButton button) {
      if (button.field_146124_l) {
         if (button.field_146127_k == 2) {
            this.field_146297_k.func_147108_a(new LOTRGuiHiredDismiss(this.theEntity));
            return;
         }

         LOTRPacketHiredUnitInteract packet = new LOTRPacketHiredUnitInteract(this.theEntity.func_145782_y(), button.field_146127_k);
         LOTRPacketHandler.networkWrapper.sendToServer(packet);
      }

   }
}
