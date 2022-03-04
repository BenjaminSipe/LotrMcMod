package lotr.client.gui;

import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.LOTRPacketMercenaryInteract;
import net.minecraft.client.gui.GuiButton;

public class LOTRGuiMercenaryInteract extends LOTRGuiUnitTradeInteract {
   public LOTRGuiMercenaryInteract(LOTREntityNPC entity) {
      super(entity);
   }

   protected void func_146284_a(GuiButton button) {
      if (button.field_146124_l) {
         LOTRPacketMercenaryInteract packet = new LOTRPacketMercenaryInteract(this.theEntity.func_145782_y(), button.field_146127_k);
         LOTRPacketHandler.networkWrapper.sendToServer(packet);
      }

   }
}
