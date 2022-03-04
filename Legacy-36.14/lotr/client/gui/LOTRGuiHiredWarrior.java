package lotr.client.gui;

import lotr.common.LOTRSquadrons;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.entity.npc.LOTRHiredNPCInfo;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.LOTRPacketNPCSquadron;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.util.StringUtils;
import org.lwjgl.opengl.GL11;

public class LOTRGuiHiredWarrior extends LOTRGuiHiredNPC {
   private static String[] pageTitles = new String[]{"overview", "options"};
   public static final int XP_COLOR = 16733440;
   private GuiButton buttonLeft;
   private GuiButton buttonRight;
   private LOTRGuiButtonOptions buttonOpenInv;
   private LOTRGuiButtonOptions buttonTeleport;
   private LOTRGuiButtonOptions buttonGuardMode;
   private LOTRGuiSlider sliderGuardRange;
   private GuiTextField squadronNameField;
   private boolean updatePage;
   private boolean sendSquadronUpdate = false;

   public LOTRGuiHiredWarrior(LOTREntityNPC npc) {
      super(npc);
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      int midX = this.guiLeft + this.xSize / 2;
      if (this.page == 0) {
         this.field_146292_n.add(this.buttonOpenInv = new LOTRGuiButtonOptions(0, midX - 80, this.guiTop + 142, 160, 20, StatCollector.func_74838_a("lotr.gui.warrior.openInv")));
      } else if (this.page == 1) {
         this.field_146292_n.add(this.buttonTeleport = new LOTRGuiButtonOptions(0, midX - 80, this.guiTop + 180, 160, 20, StatCollector.func_74838_a("lotr.gui.warrior.teleport")));
         this.field_146292_n.add(this.buttonGuardMode = new LOTRGuiButtonOptions(1, midX - 80, this.guiTop + 50, 160, 20, StatCollector.func_74838_a("lotr.gui.warrior.guardMode")));
         this.field_146292_n.add(this.sliderGuardRange = new LOTRGuiSlider(2, midX - 80, this.guiTop + 74, 160, 20, StatCollector.func_74838_a("lotr.gui.warrior.guardRange")));
         this.sliderGuardRange.setMinMaxValues(LOTRHiredNPCInfo.GUARD_RANGE_MIN, LOTRHiredNPCInfo.GUARD_RANGE_MAX);
         this.sliderGuardRange.setSliderValue(this.theNPC.hiredNPCInfo.getGuardRange());
         this.squadronNameField = new GuiTextField(this.field_146289_q, midX - 80, this.guiTop + 130, 160, 20);
         this.squadronNameField.func_146203_f(LOTRSquadrons.SQUADRON_LENGTH_MAX);
         String squadron = this.theNPC.hiredNPCInfo.getSquadron();
         if (!StringUtils.func_151246_b(squadron)) {
            this.squadronNameField.func_146180_a(squadron);
         }
      }

      this.buttonLeft = new LOTRGuiButtonLeftRight(1000, true, this.guiLeft - 160, this.guiTop + 50, "");
      this.buttonRight = new LOTRGuiButtonLeftRight(1001, false, this.guiLeft + this.xSize + 40, this.guiTop + 50, "");
      this.field_146292_n.add(this.buttonLeft);
      this.field_146292_n.add(this.buttonRight);
      if (this.page == 0) {
         this.buttonLeft.field_146126_j = pageTitles[pageTitles.length - 1];
      } else {
         this.buttonLeft.field_146126_j = pageTitles[this.page - 1];
      }

      if (this.page == pageTitles.length - 1) {
         this.buttonRight.field_146126_j = pageTitles[0];
      } else {
         this.buttonRight.field_146126_j = pageTitles[this.page + 1];
      }

      this.buttonLeft.field_146126_j = StatCollector.func_74838_a("lotr.gui.warrior." + this.buttonLeft.field_146126_j);
      this.buttonRight.field_146126_j = StatCollector.func_74838_a("lotr.gui.warrior." + this.buttonRight.field_146126_j);
   }

   protected void func_146284_a(GuiButton button) {
      if (!(button instanceof LOTRGuiSlider)) {
         if (button.field_146124_l) {
            if (button instanceof LOTRGuiButtonLeftRight) {
               if (button == this.buttonLeft) {
                  --this.page;
                  if (this.page < 0) {
                     this.page = pageTitles.length - 1;
                  }
               } else if (button == this.buttonRight) {
                  ++this.page;
                  if (this.page >= pageTitles.length) {
                     this.page = 0;
                  }
               }

               this.field_146292_n.clear();
               this.updatePage = true;
            } else {
               this.sendActionPacket(button.field_146127_k);
            }
         }

      }
   }

   public void func_73863_a(int i, int j, float f) {
      super.func_73863_a(i, j, f);
      if (this.page == 0) {
         int midX = this.guiLeft + this.xSize / 2;
         String s = StatCollector.func_74837_a("lotr.gui.warrior.health", new Object[]{Math.round(this.theNPC.func_110143_aJ()), Math.round(this.theNPC.func_110138_aP())});
         this.field_146289_q.func_78276_b(s, midX - this.field_146289_q.func_78256_a(s) / 2, this.guiTop + 50, 4210752);
         s = this.theNPC.hiredNPCInfo.getStatusString();
         this.field_146289_q.func_78276_b(s, midX - this.field_146289_q.func_78256_a(s) / 2, this.guiTop + 62, 4210752);
         s = StatCollector.func_74837_a("lotr.gui.warrior.level", new Object[]{this.theNPC.hiredNPCInfo.xpLevel});
         this.field_146289_q.func_78276_b(s, midX - this.field_146289_q.func_78256_a(s) / 2, this.guiTop + 80, 4210752);
         float lvlProgress = this.theNPC.hiredNPCInfo.getProgressToNextLevel();
         String curLevel = EnumChatFormatting.BOLD + String.valueOf(this.theNPC.hiredNPCInfo.xpLevel);
         String nextLevel = EnumChatFormatting.BOLD + String.valueOf(this.theNPC.hiredNPCInfo.xpLevel + 1);
         LOTRHiredNPCInfo var10000 = this.theNPC.hiredNPCInfo;
         String xpCurLevel = String.valueOf(LOTRHiredNPCInfo.totalXPForLevel(this.theNPC.hiredNPCInfo.xpLevel));
         var10000 = this.theNPC.hiredNPCInfo;
         String xpNextLevel = String.valueOf(LOTRHiredNPCInfo.totalXPForLevel(this.theNPC.hiredNPCInfo.xpLevel + 1));
         func_73734_a(midX - 36, this.guiTop + 96, midX + 36, this.guiTop + 102, -16777216);
         func_73734_a(midX - 35, this.guiTop + 97, midX + 35, this.guiTop + 101, -10658467);
         func_73734_a(midX - 35, this.guiTop + 97, midX - 35 + (int)(lvlProgress * 70.0F), this.guiTop + 101, -43776);
         GL11.glPushMatrix();
         float scale = 0.67F;
         GL11.glScalef(scale, scale, 1.0F);
         this.field_146289_q.func_78276_b(curLevel, Math.round(((float)(midX - 38) - (float)this.field_146289_q.func_78256_a(curLevel) * scale) / scale), (int)((float)(this.guiTop + 94) / scale), 4210752);
         this.field_146289_q.func_78276_b(nextLevel, Math.round((float)(midX + 38) / scale), (int)((float)(this.guiTop + 94) / scale), 4210752);
         this.field_146289_q.func_78276_b(xpCurLevel, Math.round(((float)(midX - 38) - (float)this.field_146289_q.func_78256_a(xpCurLevel) * scale) / scale), (int)((float)(this.guiTop + 101) / scale), 4210752);
         this.field_146289_q.func_78276_b(xpNextLevel, Math.round((float)(midX + 38) / scale), (int)((float)(this.guiTop + 101) / scale), 4210752);
         GL11.glPopMatrix();
         s = StatCollector.func_74837_a("lotr.gui.warrior.xp", new Object[]{this.theNPC.hiredNPCInfo.xp});
         this.field_146289_q.func_78276_b(s, midX - this.field_146289_q.func_78256_a(s) / 2, this.guiTop + 110, 4210752);
         s = StatCollector.func_74837_a("lotr.gui.warrior.kills", new Object[]{this.theNPC.hiredNPCInfo.mobKills});
         this.field_146289_q.func_78276_b(s, midX - this.field_146289_q.func_78256_a(s) / 2, this.guiTop + 122, 4210752);
      }

      if (this.page == 1) {
         String s = StatCollector.func_74838_a("lotr.gui.warrior.squadron");
         this.field_146289_q.func_78276_b(s, this.squadronNameField.field_146209_f, this.squadronNameField.field_146210_g - this.field_146289_q.field_78288_b - 3, 4210752);
         this.squadronNameField.func_146194_f();
      }

   }

   public void func_73876_c() {
      if (this.updatePage) {
         this.func_73866_w_();
         this.updatePage = false;
      }

      super.func_73876_c();
      if (this.page == 1) {
         this.buttonTeleport.setState(this.theNPC.hiredNPCInfo.teleportAutomatically);
         this.buttonTeleport.field_146124_l = !this.theNPC.hiredNPCInfo.isGuardMode();
         this.buttonGuardMode.setState(this.theNPC.hiredNPCInfo.isGuardMode());
         this.sliderGuardRange.field_146125_m = this.theNPC.hiredNPCInfo.isGuardMode();
         if (this.sliderGuardRange.dragging) {
            int i = this.sliderGuardRange.getSliderValue();
            this.theNPC.hiredNPCInfo.setGuardRange(i);
            this.sendActionPacket(this.sliderGuardRange.field_146127_k, i);
         }

         this.squadronNameField.func_146178_a();
      }

   }

   protected void func_73869_a(char c, int i) {
      if (this.page == 1 && this.squadronNameField != null && this.squadronNameField.func_146176_q() && this.squadronNameField.func_146201_a(c, i)) {
         this.theNPC.hiredNPCInfo.setSquadron(this.squadronNameField.func_146179_b());
         this.sendSquadronUpdate = true;
      } else {
         super.func_73869_a(c, i);
      }
   }

   protected void func_73864_a(int i, int j, int k) {
      super.func_73864_a(i, j, k);
      if (this.page == 1 && this.squadronNameField != null) {
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
