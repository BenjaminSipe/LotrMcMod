package lotr.client.gui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.entity.npc.LOTRUnitTradeEntry;
import lotr.common.fac.LOTRAlignmentValues;
import lotr.common.fac.LOTRFaction;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.LOTRPacketHiredUnitCommand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

public abstract class LOTRGuiHiredNPC extends LOTRGuiScreenBase {
   private static ResourceLocation guiTexture = new ResourceLocation("lotr:gui/npc/hired.png");
   public int xSize = 200;
   public int ySize = 220;
   public int guiLeft;
   public int guiTop;
   public LOTREntityNPC theNPC;
   public int page;

   public LOTRGuiHiredNPC(LOTREntityNPC npc) {
      this.theNPC = npc;
   }

   public void func_73866_w_() {
      this.guiLeft = (this.field_146294_l - this.xSize) / 2;
      this.guiTop = (this.field_146295_m - this.ySize) / 2;
   }

   public void func_73863_a(int i, int j, float f) {
      this.func_146276_q_();
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.field_146297_k.func_110434_K().func_110577_a(guiTexture);
      this.func_73729_b(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
      String s = this.theNPC.getNPCName();
      this.field_146289_q.func_78276_b(s, this.guiLeft + this.xSize / 2 - this.field_146289_q.func_78256_a(s) / 2, this.guiTop + 11, 3618615);
      s = this.theNPC.getEntityClassName();
      this.field_146289_q.func_78276_b(s, this.guiLeft + this.xSize / 2 - this.field_146289_q.func_78256_a(s) / 2, this.guiTop + 26, 3618615);
      if (this.page == 0 && this.theNPC.hiredNPCInfo.hasHiringRequirements()) {
         int xBorder = true;
         int x = this.guiLeft + 6;
         int y = this.guiTop + 170;
         s = StatCollector.func_74838_a("lotr.hiredNPC.commandReq");
         this.field_146289_q.func_78276_b(s, x, y, 3618615);
         y += this.field_146289_q.field_78288_b;
         int xInset = true;
         x += 4;
         List requirementLines = new ArrayList();
         int maxWidth = this.xSize - 12 - 4;
         LOTRFaction fac = this.theNPC.getHiringFaction();
         String alignS = LOTRAlignmentValues.formatAlignForDisplay(this.theNPC.hiredNPCInfo.alignmentRequiredToCommand);
         String alignReq = StatCollector.func_74837_a("lotr.hiredNPC.commandReq.align", new Object[]{alignS, fac.factionName()});
         requirementLines.addAll(this.field_146289_q.func_78271_c(alignReq, maxWidth));
         LOTRUnitTradeEntry.PledgeType pledge = this.theNPC.hiredNPCInfo.pledgeType;
         String pledgeReq = pledge.getCommandReqText(fac);
         if (pledgeReq != null) {
            requirementLines.addAll(this.field_146289_q.func_78271_c(pledgeReq, maxWidth));
         }

         for(Iterator var16 = requirementLines.iterator(); var16.hasNext(); y += this.field_146289_q.field_78288_b) {
            Object obj = var16.next();
            String line = (String)obj;
            this.field_146289_q.func_78276_b(line, x, y, 3618615);
         }
      }

      super.func_73863_a(i, j, f);
   }

   public void func_73876_c() {
      super.func_73876_c();
      if (!this.theNPC.func_70089_S() || this.theNPC.hiredNPCInfo.getHiringPlayer() != this.field_146297_k.field_71439_g || this.theNPC.func_70068_e(this.field_146297_k.field_71439_g) > 64.0D) {
         this.field_146297_k.field_71439_g.func_71053_j();
      }

   }

   public void func_146281_b() {
      super.func_146281_b();
      this.sendActionPacket(-1);
   }

   public void sendActionPacket(int action) {
      this.sendActionPacket(action, 0);
   }

   public void sendActionPacket(int action, int value) {
      LOTRPacketHiredUnitCommand packet = new LOTRPacketHiredUnitCommand(this.theNPC.func_145782_y(), this.page, action, value);
      LOTRPacketHandler.networkWrapper.sendToServer(packet);
   }
}
