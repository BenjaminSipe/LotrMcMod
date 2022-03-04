package lotr.client.gui;

import java.util.UUID;
import lotr.common.LOTRLevelData;
import lotr.common.fellowship.LOTRFellowshipClient;
import lotr.common.network.LOTRPacketBeaconEdit;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.tileentity.LOTRTileEntityBeacon;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.apache.commons.lang3.StringUtils;
import org.lwjgl.opengl.GL11;

public class LOTRGuiBeacon extends LOTRGuiScreenBase {
   private static ResourceLocation guiTexture = new ResourceLocation("lotr:gui/beacon.png");
   private int xSize = 200;
   private int ySize = 160;
   private int guiLeft;
   private int guiTop;
   private int beaconX;
   private int beaconY;
   private int beaconZ;
   private UUID initFellowshipID;
   private LOTRFellowshipClient initFellowship;
   private String initBeaconName;
   private String currentBeaconName;
   private GuiButton buttonDone;
   private GuiTextField fellowshipNameField;
   private GuiTextField beaconNameField;

   public LOTRGuiBeacon(LOTRTileEntityBeacon beacon) {
      this.beaconX = beacon.field_145851_c;
      this.beaconY = beacon.field_145848_d;
      this.beaconZ = beacon.field_145849_e;
      this.initFellowshipID = beacon.getFellowshipID();
      this.initBeaconName = beacon.getBeaconName();
   }

   public void func_73866_w_() {
      this.guiLeft = (this.field_146294_l - this.xSize) / 2;
      this.guiTop = (this.field_146295_m - this.ySize) / 2;
      this.initFellowship = LOTRLevelData.getData((EntityPlayer)this.field_146297_k.field_71439_g).getClientFellowshipByID(this.initFellowshipID);
      this.field_146292_n.add(this.buttonDone = new GuiButton(0, this.guiLeft + this.xSize / 2 - 40, this.guiTop + 130, 80, 20, StatCollector.func_74838_a("container.lotr.beacon.done")));
      this.fellowshipNameField = new GuiTextField(this.field_146289_q, this.guiLeft + this.xSize / 2 - 80, this.guiTop + 45, 160, 20);
      this.fellowshipNameField.func_146203_f(40);
      if (this.initFellowship != null) {
         this.fellowshipNameField.func_146180_a(this.initFellowship.getName());
      }

      this.beaconNameField = new GuiTextField(this.field_146289_q, this.guiLeft + this.xSize / 2 - 80, this.guiTop + 100, 160, 20);
      this.beaconNameField.func_146203_f(40);
      if (!StringUtils.isBlank(this.initBeaconName)) {
         this.beaconNameField.func_146180_a(this.initBeaconName);
      }

   }

   public void func_73863_a(int i, int j, float f) {
      this.func_146276_q_();
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.field_146297_k.func_110434_K().func_110577_a(guiTexture);
      this.func_73729_b(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
      TileEntity te = this.field_146297_k.field_71441_e.func_147438_o(this.beaconX, this.beaconY, this.beaconZ);
      String s = (new ItemStack(te.func_145838_q(), 1, te.func_145832_p())).func_82833_r();
      this.field_146289_q.func_78276_b(s, this.guiLeft + this.xSize / 2 - this.field_146289_q.func_78256_a(s) / 2, this.guiTop + 11, 4210752);
      this.fellowshipNameField.func_146194_f();
      s = StatCollector.func_74838_a("container.lotr.beacon.nameFellowship");
      this.field_146289_q.func_78276_b(s, this.fellowshipNameField.field_146209_f + 4, this.fellowshipNameField.field_146210_g - 4 - this.field_146289_q.field_78288_b, 4210752);
      this.currentBeaconName = this.beaconNameField.func_146179_b();
      this.beaconNameField.func_146184_c(true);
      if (this.beaconNameField.func_146206_l()) {
         this.beaconNameField.func_146194_f();
      } else {
         String beaconNameEff = this.currentBeaconName;
         if (StringUtils.isBlank(beaconNameEff)) {
            beaconNameEff = this.fellowshipNameField.func_146179_b();
            this.beaconNameField.func_146184_c(false);
         }

         this.beaconNameField.func_146180_a(beaconNameEff);
         this.beaconNameField.func_146194_f();
         this.beaconNameField.func_146180_a(this.currentBeaconName);
      }

      s = StatCollector.func_74838_a("container.lotr.beacon.nameBeacon");
      this.field_146289_q.func_78276_b(s, this.beaconNameField.field_146209_f + 4, this.beaconNameField.field_146210_g - 4 - this.field_146289_q.field_78288_b * 2, 4210752);
      s = StatCollector.func_74838_a("container.lotr.beacon.namePrefix");
      this.field_146289_q.func_78276_b(s, this.beaconNameField.field_146209_f + 4, this.beaconNameField.field_146210_g - 4 - this.field_146289_q.field_78288_b, 4210752);
      super.func_73863_a(i, j, f);
   }

   public void func_73876_c() {
      super.func_73876_c();
      this.fellowshipNameField.func_146178_a();
      this.beaconNameField.func_146178_a();
      double dSq = this.field_146297_k.field_71439_g.func_70092_e((double)this.beaconX + 0.5D, (double)this.beaconY + 0.5D, (double)this.beaconZ + 0.5D);
      if (dSq > 64.0D) {
         this.field_146297_k.field_71439_g.func_71053_j();
      } else {
         TileEntity tileentity = this.field_146297_k.field_71441_e.func_147438_o(this.beaconX, this.beaconY, this.beaconZ);
         if (!(tileentity instanceof LOTRTileEntityBeacon)) {
            this.field_146297_k.field_71439_g.func_71053_j();
         }
      }

   }

   protected void func_73869_a(char c, int i) {
      if (!this.fellowshipNameField.func_146176_q() || !this.fellowshipNameField.func_146201_a(c, i)) {
         if (!this.beaconNameField.func_146176_q() || !this.beaconNameField.func_146201_a(c, i)) {
            super.func_73869_a(c, i);
         }
      }
   }

   protected void func_73864_a(int i, int j, int k) {
      super.func_73864_a(i, j, k);
      this.fellowshipNameField.func_146192_a(i, j, k);
      this.beaconNameField.func_146192_a(i, j, k);
   }

   protected void func_146284_a(GuiButton button) {
      if (button.field_146124_l && button == this.buttonDone) {
         this.field_146297_k.field_71439_g.func_71053_j();
      }

   }

   private void sendBeaconEditPacket(boolean closed) {
      UUID fsID = null;
      String fsName = this.fellowshipNameField.func_146179_b();
      if (!StringUtils.isBlank(fsName)) {
         LOTRFellowshipClient fs = LOTRLevelData.getData((EntityPlayer)this.field_146297_k.field_71439_g).getClientFellowshipByName(fsName);
         if (fs != null) {
            fsID = fs.getFellowshipID();
         }
      }

      String beaconName = this.currentBeaconName;
      LOTRPacketBeaconEdit packet = new LOTRPacketBeaconEdit(this.beaconX, this.beaconY, this.beaconZ, fsID, beaconName, true);
      LOTRPacketHandler.networkWrapper.sendToServer(packet);
   }

   public void func_146281_b() {
      this.sendBeaconEditPacket(true);
   }
}
