package lotr.client.gui;

import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.LOTRPacketHiredUnitDismiss;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.Entity;
import net.minecraft.util.StatCollector;

public class LOTRGuiHiredDismiss extends LOTRGuiNPCInteract {
   public LOTRGuiHiredDismiss(LOTREntityNPC entity) {
      super(entity);
   }

   public void func_73866_w_() {
      this.field_146292_n.add(new GuiButton(0, this.field_146294_l / 2 - 65, this.field_146295_m / 5 * 3 + 40, 60, 20, StatCollector.func_74838_a("lotr.gui.dismiss.dismiss")));
      this.field_146292_n.add(new GuiButton(1, this.field_146294_l / 2 + 5, this.field_146295_m / 5 * 3 + 40, 60, 20, StatCollector.func_74838_a("lotr.gui.dismiss.cancel")));
   }

   public void func_73863_a(int i, int j, float f) {
      super.func_73863_a(i, j, f);
      String s = StatCollector.func_74838_a("lotr.gui.dismiss.warning1");
      int y = this.field_146295_m / 5 * 3;
      this.field_146289_q.func_78276_b(s, (this.field_146294_l - this.field_146289_q.func_78256_a(s)) / 2, y, 16777215);
      y += this.field_146289_q.field_78288_b;
      s = StatCollector.func_74838_a("lotr.gui.dismiss.warning2");
      this.field_146289_q.func_78276_b(s, (this.field_146294_l - this.field_146289_q.func_78256_a(s)) / 2, y, 16777215);
      y += this.field_146289_q.field_78288_b;
      Entity mount = this.theEntity.field_70154_o;
      Entity rider = this.theEntity.field_70153_n;
      boolean hasMount = mount instanceof LOTREntityNPC && ((LOTREntityNPC)mount).hiredNPCInfo.getHiringPlayer() == this.field_146297_k.field_71439_g;
      boolean hasRider = rider instanceof LOTREntityNPC && ((LOTREntityNPC)rider).hiredNPCInfo.getHiringPlayer() == this.field_146297_k.field_71439_g;
      if (hasMount) {
         s = StatCollector.func_74838_a("lotr.gui.dismiss.mount");
         this.field_146289_q.func_78276_b(s, (this.field_146294_l - this.field_146289_q.func_78256_a(s)) / 2, y, 11184810);
         y += this.field_146289_q.field_78288_b;
      }

      if (hasRider) {
         s = StatCollector.func_74838_a("lotr.gui.dismiss.rider");
         this.field_146289_q.func_78276_b(s, (this.field_146294_l - this.field_146289_q.func_78256_a(s)) / 2, y, 11184810);
         int var10000 = y + this.field_146289_q.field_78288_b;
      }

   }

   protected void func_146284_a(GuiButton button) {
      if (button.field_146124_l) {
         if (button.field_146127_k == 1) {
            this.field_146297_k.func_147108_a(new LOTRGuiHiredInteract(this.theEntity));
            return;
         }

         LOTRPacketHiredUnitDismiss packet = new LOTRPacketHiredUnitDismiss(this.theEntity.func_145782_y(), button.field_146127_k);
         LOTRPacketHandler.networkWrapper.sendToServer(packet);
      }

   }
}
