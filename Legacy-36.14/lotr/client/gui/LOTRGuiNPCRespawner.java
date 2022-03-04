package lotr.client.gui;

import lotr.common.entity.LOTREntities;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.network.LOTRPacketEditNPCRespawner;
import lotr.common.network.LOTRPacketHandler;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.StatCollector;
import net.minecraft.util.StringUtils;

public class LOTRGuiNPCRespawner extends LOTRGuiScreenBase {
   private int xSize = 256;
   private int ySize = 280;
   private int guiLeft;
   private int guiTop;
   private LOTREntityNPCRespawner theSpawner;
   private GuiTextField textSpawnClass1;
   private GuiTextField textSpawnClass2;
   private LOTRGuiSlider sliderCheckHorizontal;
   private LOTRGuiSlider sliderCheckVerticalMin;
   private LOTRGuiSlider sliderCheckVerticalMax;
   private LOTRGuiSlider sliderSpawnCap;
   private LOTRGuiSlider sliderBlockEnemy;
   private LOTRGuiSlider sliderSpawnHorizontal;
   private LOTRGuiSlider sliderSpawnVerticalMin;
   private LOTRGuiSlider sliderSpawnVerticalMax;
   private LOTRGuiSlider sliderHomeRange;
   private LOTRGuiButtonOptions buttonMounts;
   private LOTRGuiSlider sliderSpawnIntervalM;
   private LOTRGuiSlider sliderSpawnIntervalS;
   private LOTRGuiSlider sliderNoPlayerRange;
   private GuiButton buttonDestroy;
   private boolean destroySpawner = false;

   public LOTRGuiNPCRespawner(LOTREntityNPCRespawner entity) {
      this.theSpawner = entity;
   }

   public void func_73866_w_() {
      this.guiLeft = (this.field_146294_l - this.xSize) / 2;
      this.guiTop = (this.field_146295_m - this.ySize) / 2;
      this.textSpawnClass1 = new GuiTextField(this.field_146289_q, this.guiLeft + this.xSize / 2 - 190, this.guiTop + 35, 180, 20);
      if (this.theSpawner.spawnClass1 != null) {
         this.textSpawnClass1.func_146180_a(LOTREntities.getStringFromClass(this.theSpawner.spawnClass1));
      }

      this.textSpawnClass2 = new GuiTextField(this.field_146289_q, this.guiLeft + this.xSize / 2 + 10, this.guiTop + 35, 180, 20);
      if (this.theSpawner.spawnClass2 != null) {
         this.textSpawnClass2.func_146180_a(LOTREntities.getStringFromClass(this.theSpawner.spawnClass2));
      }

      this.field_146292_n.add(this.sliderCheckHorizontal = new LOTRGuiSlider(0, this.guiLeft + this.xSize / 2 - 180, this.guiTop + 70, 160, 20, StatCollector.func_74838_a("lotr.gui.npcRespawner.checkHorizontal")));
      this.sliderCheckHorizontal.setMinMaxValues(0, 64);
      this.sliderCheckHorizontal.setSliderValue(this.theSpawner.checkHorizontalRange);
      this.field_146292_n.add(this.sliderCheckVerticalMin = new LOTRGuiSlider(0, this.guiLeft + this.xSize / 2 - 180, this.guiTop + 95, 160, 20, StatCollector.func_74838_a("lotr.gui.npcRespawner.checkVerticalMin")));
      this.sliderCheckVerticalMin.setMinMaxValues(-64, 64);
      this.sliderCheckVerticalMin.setSliderValue(this.theSpawner.checkVerticalMin);
      this.field_146292_n.add(this.sliderCheckVerticalMax = new LOTRGuiSlider(0, this.guiLeft + this.xSize / 2 - 180, this.guiTop + 120, 160, 20, StatCollector.func_74838_a("lotr.gui.npcRespawner.checkVerticalMax")));
      this.sliderCheckVerticalMax.setMinMaxValues(-64, 64);
      this.sliderCheckVerticalMax.setSliderValue(this.theSpawner.checkVerticalMax);
      this.field_146292_n.add(this.sliderSpawnCap = new LOTRGuiSlider(0, this.guiLeft + this.xSize / 2 - 180, this.guiTop + 145, 160, 20, StatCollector.func_74838_a("lotr.gui.npcRespawner.spawnCap")));
      this.sliderSpawnCap.setMinMaxValues(0, 64);
      this.sliderSpawnCap.setSliderValue(this.theSpawner.spawnCap);
      this.field_146292_n.add(this.sliderBlockEnemy = new LOTRGuiSlider(0, this.guiLeft + this.xSize / 2 - 180, this.guiTop + 170, 160, 20, StatCollector.func_74838_a("lotr.gui.npcRespawner.blockEnemy")));
      LOTREntityNPCRespawner var10002 = this.theSpawner;
      this.sliderBlockEnemy.setMinMaxValues(0, 64);
      this.sliderBlockEnemy.setSliderValue(this.theSpawner.blockEnemySpawns);
      this.field_146292_n.add(this.sliderSpawnHorizontal = new LOTRGuiSlider(0, this.guiLeft + this.xSize / 2 + 20, this.guiTop + 70, 160, 20, StatCollector.func_74838_a("lotr.gui.npcRespawner.spawnHorizontal")));
      this.sliderSpawnHorizontal.setMinMaxValues(0, 64);
      this.sliderSpawnHorizontal.setSliderValue(this.theSpawner.spawnHorizontalRange);
      this.field_146292_n.add(this.sliderSpawnVerticalMin = new LOTRGuiSlider(0, this.guiLeft + this.xSize / 2 + 20, this.guiTop + 95, 160, 20, StatCollector.func_74838_a("lotr.gui.npcRespawner.spawnVerticalMin")));
      this.sliderSpawnVerticalMin.setMinMaxValues(-64, 64);
      this.sliderSpawnVerticalMin.setSliderValue(this.theSpawner.spawnVerticalMin);
      this.field_146292_n.add(this.sliderSpawnVerticalMax = new LOTRGuiSlider(0, this.guiLeft + this.xSize / 2 + 20, this.guiTop + 120, 160, 20, StatCollector.func_74838_a("lotr.gui.npcRespawner.spawnVerticalMax")));
      this.sliderSpawnVerticalMax.setMinMaxValues(-64, 64);
      this.sliderSpawnVerticalMax.setSliderValue(this.theSpawner.spawnVerticalMax);
      this.field_146292_n.add(this.sliderHomeRange = new LOTRGuiSlider(0, this.guiLeft + this.xSize / 2 + 20, this.guiTop + 145, 160, 20, StatCollector.func_74838_a("lotr.gui.npcRespawner.homeRange")));
      this.sliderHomeRange.setMinMaxValues(-1, 64);
      this.sliderHomeRange.setSliderValue(this.theSpawner.homeRange);
      this.field_146292_n.add(this.buttonMounts = new LOTRGuiButtonOptions(0, this.guiLeft + this.xSize / 2 + 20, this.guiTop + 170, 160, 20, StatCollector.func_74838_a("lotr.gui.npcRespawner.mounts")));
      this.field_146292_n.add(this.sliderSpawnIntervalM = new LOTRGuiSlider(0, this.guiLeft + this.xSize / 2 - 100 - 5, this.guiTop + 195, 100, 20, StatCollector.func_74838_a("lotr.gui.npcRespawner.spawnIntervalM")));
      this.sliderSpawnIntervalM.setMinMaxValues(0, 60);
      this.sliderSpawnIntervalM.setValueOnly();
      this.sliderSpawnIntervalM.setSliderValue(this.theSpawner.spawnInterval / 20 / 60);
      this.field_146292_n.add(this.sliderSpawnIntervalS = new LOTRGuiSlider(0, this.guiLeft + this.xSize / 2 + 5, this.guiTop + 195, 100, 20, StatCollector.func_74838_a("lotr.gui.npcRespawner.spawnIntervalS")));
      this.sliderSpawnIntervalS.setMinMaxValues(0, 59);
      this.sliderSpawnIntervalS.setValueOnly();
      this.sliderSpawnIntervalS.setNumberDigits(2);
      this.sliderSpawnIntervalS.setSliderValue(this.theSpawner.spawnInterval / 20 % 60);
      this.field_146292_n.add(this.sliderNoPlayerRange = new LOTRGuiSlider(0, this.guiLeft + this.xSize / 2 - 80, this.guiTop + 220, 160, 20, StatCollector.func_74838_a("lotr.gui.npcRespawner.noPlayerRange")));
      this.sliderNoPlayerRange.setMinMaxValues(0, 64);
      this.sliderNoPlayerRange.setSliderValue(this.theSpawner.noPlayerRange);
      this.field_146292_n.add(this.buttonDestroy = new GuiButton(0, this.guiLeft + this.xSize / 2 - 50, this.guiTop + 255, 100, 20, StatCollector.func_74838_a("lotr.gui.npcRespawner.destroy")));
   }

   public void func_73863_a(int i, int j, float f) {
      this.func_146276_q_();
      String s = StatCollector.func_74838_a("lotr.gui.npcRespawner.title");
      this.field_146289_q.func_78276_b(s, this.guiLeft + this.xSize / 2 - this.field_146289_q.func_78256_a(s) / 2, this.guiTop, 16777215);
      this.textSpawnClass1.func_146194_f();
      this.textSpawnClass2.func_146194_f();
      s = StatCollector.func_74838_a("lotr.gui.npcRespawner.spawnClass1");
      this.field_146289_q.func_78276_b(s, this.textSpawnClass1.field_146209_f + 3, this.textSpawnClass1.field_146210_g - this.field_146289_q.field_78288_b - 3, 13421772);
      s = StatCollector.func_74838_a("lotr.gui.npcRespawner.spawnClass2");
      this.field_146289_q.func_78276_b(s, this.textSpawnClass2.field_146209_f + 3, this.textSpawnClass2.field_146210_g - this.field_146289_q.field_78288_b - 3, 13421772);
      if (this.theSpawner.mountSetting == 0) {
         this.buttonMounts.setState(StatCollector.func_74838_a("lotr.gui.npcRespawner.mounts.0"));
      } else if (this.theSpawner.mountSetting == 1) {
         this.buttonMounts.setState(StatCollector.func_74838_a("lotr.gui.npcRespawner.mounts.1"));
      } else {
         this.buttonMounts.setState(StatCollector.func_74838_a("lotr.gui.npcRespawner.mounts.2"));
      }

      if (!this.theSpawner.blockEnemySpawns()) {
         this.sliderBlockEnemy.setOverrideStateString(StatCollector.func_74838_a("lotr.gui.npcRespawner.blockEnemy.off"));
      } else {
         this.sliderBlockEnemy.setOverrideStateString((String)null);
      }

      if (!this.theSpawner.hasHomeRange()) {
         this.sliderHomeRange.setOverrideStateString(StatCollector.func_74838_a("lotr.gui.npcRespawner.homeRange.off"));
      } else {
         this.sliderHomeRange.setOverrideStateString((String)null);
      }

      String timepre = StatCollector.func_74838_a("lotr.gui.npcRespawner.spawnInterval");
      int timepreX = this.sliderSpawnIntervalM.field_146128_h - 5 - this.field_146289_q.func_78256_a(timepre);
      int timepreY = this.sliderSpawnIntervalM.field_146129_i + this.sliderSpawnIntervalM.field_146121_g / 2 - this.field_146289_q.field_78288_b / 2;
      this.field_146289_q.func_78276_b(timepre, timepreX, timepreY, 16777215);
      String timesplit = ":";
      int timesplitX = (this.sliderSpawnIntervalM.field_146128_h + this.sliderSpawnIntervalM.field_146120_f + this.sliderSpawnIntervalS.field_146128_h) / 2 - this.field_146289_q.func_78256_a(timesplit) / 2;
      int timesplitY = this.sliderSpawnIntervalM.field_146129_i + this.sliderSpawnIntervalM.field_146121_g / 2 - this.field_146289_q.field_78288_b / 2;
      this.field_146289_q.func_78276_b(timesplit, timesplitX, timesplitY, 16777215);
      super.func_73863_a(i, j, f);
      this.updateSliders();
      if (this.sliderBlockEnemy.field_146124_l && this.sliderBlockEnemy.func_146115_a() && !this.sliderBlockEnemy.dragging) {
         String tooltip = StatCollector.func_74838_a("lotr.gui.npcRespawner.blockEnemy.tooltip");
         int border = 3;
         int stringWidth = this.field_146297_k.field_71466_p.func_78256_a(tooltip);
         int stringHeight = this.field_146297_k.field_71466_p.field_78288_b;
         int offset = 10;
         i += offset;
         j += offset;
         func_73734_a(i, j, i + stringWidth + border * 2, j + stringHeight + border * 2, -1073741824);
         this.field_146297_k.field_71466_p.func_78276_b(tooltip, i + border, j + border, 16777215);
      }

   }

   protected void func_146284_a(GuiButton button) {
      if (!(button instanceof LOTRGuiSlider)) {
         if (button.field_146124_l) {
            if (button == this.buttonMounts) {
               this.theSpawner.toggleMountSetting();
            }

            if (button == this.buttonDestroy) {
               this.destroySpawner = true;
               this.field_146297_k.field_71439_g.func_71053_j();
            }
         }

      }
   }

   private void updateSliders() {
      if (this.sliderCheckHorizontal.dragging) {
         this.theSpawner.checkHorizontalRange = this.sliderCheckHorizontal.getSliderValue();
      }

      if (this.sliderCheckVerticalMin.dragging) {
         this.theSpawner.checkVerticalMin = this.sliderCheckVerticalMin.getSliderValue();
         if (this.theSpawner.checkVerticalMax < this.theSpawner.checkVerticalMin) {
            this.theSpawner.checkVerticalMax = this.theSpawner.checkVerticalMin;
            this.sliderCheckVerticalMax.setSliderValue(this.theSpawner.checkVerticalMax);
         }
      }

      if (this.sliderCheckVerticalMax.dragging) {
         this.theSpawner.checkVerticalMax = this.sliderCheckVerticalMax.getSliderValue();
         if (this.theSpawner.checkVerticalMin > this.theSpawner.checkVerticalMax) {
            this.theSpawner.checkVerticalMin = this.theSpawner.checkVerticalMax;
            this.sliderCheckVerticalMin.setSliderValue(this.theSpawner.checkVerticalMin);
         }
      }

      if (this.sliderSpawnCap.dragging) {
         this.theSpawner.spawnCap = this.sliderSpawnCap.getSliderValue();
      }

      if (this.sliderBlockEnemy.dragging) {
         this.theSpawner.blockEnemySpawns = this.sliderBlockEnemy.getSliderValue();
      }

      if (this.sliderSpawnHorizontal.dragging) {
         this.theSpawner.spawnHorizontalRange = this.sliderSpawnHorizontal.getSliderValue();
      }

      if (this.sliderSpawnVerticalMin.dragging) {
         this.theSpawner.spawnVerticalMin = this.sliderSpawnVerticalMin.getSliderValue();
         if (this.theSpawner.spawnVerticalMax < this.theSpawner.spawnVerticalMin) {
            this.theSpawner.spawnVerticalMax = this.theSpawner.spawnVerticalMin;
            this.sliderSpawnVerticalMax.setSliderValue(this.theSpawner.spawnVerticalMax);
         }
      }

      if (this.sliderSpawnVerticalMax.dragging) {
         this.theSpawner.spawnVerticalMax = this.sliderSpawnVerticalMax.getSliderValue();
         if (this.theSpawner.spawnVerticalMin > this.theSpawner.spawnVerticalMax) {
            this.theSpawner.spawnVerticalMin = this.theSpawner.spawnVerticalMax;
            this.sliderSpawnVerticalMin.setSliderValue(this.theSpawner.spawnVerticalMin);
         }
      }

      if (this.sliderHomeRange.dragging) {
         this.theSpawner.homeRange = this.sliderHomeRange.getSliderValue();
      }

      if (this.sliderSpawnIntervalM.dragging || this.sliderSpawnIntervalS.dragging) {
         if (this.sliderSpawnIntervalM.getSliderValue() == 0) {
            int s = this.sliderSpawnIntervalS.getSliderValue();
            s = Math.max(s, 1);
            this.sliderSpawnIntervalS.setSliderValue(s);
         }

         this.theSpawner.spawnInterval = (this.sliderSpawnIntervalM.getSliderValue() * 60 + this.sliderSpawnIntervalS.getSliderValue()) * 20;
      }

      if (this.sliderNoPlayerRange.dragging) {
         this.theSpawner.noPlayerRange = this.sliderNoPlayerRange.getSliderValue();
      }

   }

   public void func_73876_c() {
      super.func_73876_c();
      this.textSpawnClass1.func_146178_a();
      this.textSpawnClass2.func_146178_a();
   }

   protected void func_73869_a(char c, int i) {
      if (!this.textSpawnClass1.func_146176_q() || !this.textSpawnClass1.func_146201_a(c, i)) {
         if (!this.textSpawnClass2.func_146176_q() || !this.textSpawnClass2.func_146201_a(c, i)) {
            super.func_73869_a(c, i);
         }
      }
   }

   protected void func_73864_a(int i, int j, int k) {
      super.func_73864_a(i, j, k);
      this.textSpawnClass1.func_146192_a(i, j, k);
      this.textSpawnClass2.func_146192_a(i, j, k);
   }

   public void func_146281_b() {
      super.func_146281_b();
      this.sendSpawnerData();
   }

   private void sendSpawnerData() {
      String s1 = this.textSpawnClass1.func_146179_b();
      String s2 = this.textSpawnClass2.func_146179_b();
      Class entityClass;
      if (!StringUtils.func_151246_b(s1)) {
         entityClass = LOTREntities.getClassFromString(s1);
         this.theSpawner.spawnClass1 = entityClass;
      }

      if (!StringUtils.func_151246_b(s2)) {
         entityClass = LOTREntities.getClassFromString(s2);
         this.theSpawner.spawnClass2 = entityClass;
      }

      LOTRPacketEditNPCRespawner packet = new LOTRPacketEditNPCRespawner(this.theSpawner);
      packet.destroy = this.destroySpawner;
      LOTRPacketHandler.networkWrapper.sendToServer(packet);
   }
}
