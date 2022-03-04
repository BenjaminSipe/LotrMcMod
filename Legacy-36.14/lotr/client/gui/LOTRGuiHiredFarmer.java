package lotr.client.gui;

import lotr.common.LOTRSquadrons;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.entity.npc.LOTRHiredNPCInfo;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.LOTRPacketNPCSquadron;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.StatCollector;
import net.minecraft.util.StringUtils;

public class LOTRGuiHiredFarmer extends LOTRGuiHiredNPC {
   private LOTRGuiButtonOptions buttonGuardMode;
   private LOTRGuiSlider sliderGuardRange;
   private GuiTextField squadronNameField;
   private boolean sendSquadronUpdate = false;

   public LOTRGuiHiredFarmer(LOTREntityNPC npc) {
      super(npc);
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      int midX = this.guiLeft + this.xSize / 2;
      this.field_146292_n.add(this.buttonGuardMode = new LOTRGuiButtonOptions(0, midX - 80, this.guiTop + 60, 160, 20, StatCollector.func_74838_a("lotr.gui.farmer.mode")));
      this.buttonGuardMode.setState(this.theNPC.hiredNPCInfo.isGuardMode());
      this.field_146292_n.add(this.sliderGuardRange = new LOTRGuiSlider(1, midX - 80, this.guiTop + 84, 160, 20, StatCollector.func_74838_a("lotr.gui.farmer.range")));
      this.sliderGuardRange.setMinMaxValues(LOTRHiredNPCInfo.GUARD_RANGE_MIN, LOTRHiredNPCInfo.GUARD_RANGE_MAX);
      this.sliderGuardRange.setSliderValue(this.theNPC.hiredNPCInfo.getGuardRange());
      this.sliderGuardRange.field_146125_m = this.theNPC.hiredNPCInfo.isGuardMode();
      this.squadronNameField = new GuiTextField(this.field_146289_q, midX - 80, this.guiTop + 120, 160, 20);
      this.squadronNameField.func_146203_f(LOTRSquadrons.SQUADRON_LENGTH_MAX);
      String squadron = this.theNPC.hiredNPCInfo.getSquadron();
      if (!StringUtils.func_151246_b(squadron)) {
         this.squadronNameField.func_146180_a(squadron);
      }

      this.field_146292_n.add(new LOTRGuiButtonOptions(2, midX - 80, this.guiTop + 144, 160, 20, StatCollector.func_74838_a("lotr.gui.farmer.openInv")));
   }

   protected void func_146284_a(GuiButton button) {
      if (!(button instanceof LOTRGuiSlider)) {
         if (button.field_146124_l) {
            this.sendActionPacket(button.field_146127_k);
         }

      }
   }

   public void func_73863_a(int i, int j, float f) {
      super.func_73863_a(i, j, f);
      String s = this.theNPC.hiredNPCInfo.getStatusString();
      this.field_146289_q.func_78276_b(s, this.guiLeft + this.xSize / 2 - this.field_146289_q.func_78256_a(s) / 2, this.guiTop + 48, 4210752);
      s = StatCollector.func_74838_a("lotr.gui.farmer.squadron");
      this.field_146289_q.func_78276_b(s, this.squadronNameField.field_146209_f, this.squadronNameField.field_146210_g - this.field_146289_q.field_78288_b - 3, 4210752);
      this.squadronNameField.func_146194_f();
   }

   public void func_73876_c() {
      super.func_73876_c();
      this.buttonGuardMode.setState(this.theNPC.hiredNPCInfo.isGuardMode());
      this.sliderGuardRange.field_146125_m = this.theNPC.hiredNPCInfo.isGuardMode();
      if (this.sliderGuardRange.dragging) {
         int i = this.sliderGuardRange.getSliderValue();
         this.theNPC.hiredNPCInfo.setGuardRange(i);
         this.sendActionPacket(this.sliderGuardRange.field_146127_k, i);
      }

      this.squadronNameField.func_146178_a();
   }

   protected void func_73869_a(char c, int i) {
      if (this.squadronNameField != null && this.squadronNameField.func_146176_q() && this.squadronNameField.func_146201_a(c, i)) {
         this.theNPC.hiredNPCInfo.setSquadron(this.squadronNameField.func_146179_b());
         this.sendSquadronUpdate = true;
      } else {
         super.func_73869_a(c, i);
      }
   }

   protected void func_73864_a(int i, int j, int k) {
      super.func_73864_a(i, j, k);
      if (this.squadronNameField != null) {
         this.squadronNameField.func_146192_a(i, j, k);
      }

   }

   public void func_146281_b() {
      super.func_146281_b();
      if (this.sendSquadronUpdate) {
         String squadron = this.theNPC.hiredNPCInfo.getSquadron();
         LOTRPacketNPCSquadron packet = new LOTRPacketNPCSquadron(this.theNPC, squadron);
         LOTRPacketHandler.networkWrapper.sendToServer(packet);
      }

   }
}
