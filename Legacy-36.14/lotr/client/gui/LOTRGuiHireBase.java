package lotr.client.gui;

import java.util.List;
import lotr.client.LOTRClientProxy;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.LOTRSquadrons;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.entity.npc.LOTRHireableBase;
import lotr.common.entity.npc.LOTRUnitTradeEntries;
import lotr.common.entity.npc.LOTRUnitTradeEntry;
import lotr.common.fac.LOTRAlignmentValues;
import lotr.common.fac.LOTRFaction;
import lotr.common.inventory.LOTRContainerUnitTrade;
import lotr.common.inventory.LOTRSlotAlignmentReward;
import lotr.common.network.LOTRPacketBuyUnit;
import lotr.common.network.LOTRPacketHandler;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.util.StringUtils;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public abstract class LOTRGuiHireBase extends GuiContainer {
   private static ResourceLocation guiTexture = new ResourceLocation("lotr:gui/npc/unit_trade.png");
   private LOTRHireableBase theUnitTrader;
   private LOTRFaction traderFaction;
   private LOTRUnitTradeEntries trades;
   private int currentTradeEntryIndex;
   private LOTREntityNPC currentDisplayedMob;
   private EntityLiving currentDisplayedMount;
   private float screenXSize;
   private float screenYSize;
   private LOTRGuiUnitTradeButton buttonHire;
   private LOTRGuiUnitTradeButton buttonLeftUnit;
   private LOTRGuiUnitTradeButton buttonRightUnit;
   private GuiTextField squadronNameField;
   private static final int extraInfoX = 49;
   private static final int extraInfoY = 106;
   private static final int extraInfoWidth = 9;
   private static final int extraInfoHeight = 7;

   public LOTRGuiHireBase(EntityPlayer entityplayer, LOTRHireableBase trader, World world) {
      super(new LOTRContainerUnitTrade(entityplayer, trader, world));
      this.field_146999_f = 220;
      this.field_147000_g = 256;
      this.theUnitTrader = trader;
      this.traderFaction = trader.getFaction();
   }

   protected void setTrades(LOTRUnitTradeEntries t) {
      this.trades = t;
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      this.field_146292_n.add(this.buttonLeftUnit = new LOTRGuiUnitTradeButton(0, this.field_147003_i + 90, this.field_147009_r + 144, 12, 19));
      this.buttonLeftUnit.field_146124_l = false;
      this.field_146292_n.add(this.buttonHire = new LOTRGuiUnitTradeButton(1, this.field_147003_i + 102, this.field_147009_r + 144, 16, 19));
      this.field_146292_n.add(this.buttonRightUnit = new LOTRGuiUnitTradeButton(2, this.field_147003_i + 118, this.field_147009_r + 144, 12, 19));
      this.squadronNameField = new GuiTextField(this.field_146289_q, this.field_147003_i + this.field_146999_f / 2 - 80, this.field_147009_r + 120, 160, 20);
      this.squadronNameField.func_146203_f(LOTRSquadrons.SQUADRON_LENGTH_MAX);
   }

   private LOTRUnitTradeEntry currentTrade() {
      return this.trades.tradeEntries[this.currentTradeEntryIndex];
   }

   public void func_73863_a(int i, int j, float f) {
      this.buttonLeftUnit.field_146124_l = this.currentTradeEntryIndex > 0;
      this.buttonHire.field_146124_l = this.currentTrade().hasRequiredCostAndAlignment(this.field_146297_k.field_71439_g, this.theUnitTrader);
      this.buttonRightUnit.field_146124_l = this.currentTradeEntryIndex < this.trades.tradeEntries.length - 1;
      super.func_73863_a(i, j, f);
      this.screenXSize = (float)i;
      this.screenYSize = (float)j;
   }

   public void func_73876_c() {
      super.func_73876_c();
      this.squadronNameField.func_146178_a();
   }

   protected void func_146979_b(int i, int j) {
      LOTRUnitTradeEntry curTrade = this.currentTrade();
      this.drawCenteredString(this.theUnitTrader.getNPCName(), 110, 11, 4210752);
      this.field_146289_q.func_78276_b(StatCollector.func_74838_a("container.inventory"), 30, 162, 4210752);
      this.drawCenteredString(curTrade.getUnitTradeName(), 138, 50, 4210752);
      int reqX = 64;
      int reqXText = reqX + 19;
      int reqY = 65;
      int reqYTextBelow = 4;
      int reqGap = 18;
      GL11.glEnable(2896);
      GL11.glEnable(2884);
      field_146296_j.func_82406_b(this.field_146289_q, this.field_146297_k.func_110434_K(), new ItemStack(LOTRMod.silverCoin), reqX, reqY);
      GL11.glDisable(2896);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      int cost = curTrade.getCost(this.field_146297_k.field_71439_g, this.theUnitTrader);
      this.field_146289_q.func_78276_b(String.valueOf(cost), reqXText, reqY + reqYTextBelow, 4210752);
      int reqY = reqY + reqGap;
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.field_146297_k.func_110434_K().func_110577_a(LOTRClientProxy.alignmentTexture);
      this.func_73729_b(reqX, reqY, 0, 36, 16, 16);
      float alignment = curTrade.alignmentRequired;
      String alignS = LOTRAlignmentValues.formatAlignForDisplay(alignment);
      this.field_146289_q.func_78276_b(alignS, reqXText, reqY + reqYTextBelow, 4210752);
      String extraInfo;
      if (curTrade.getPledgeType() != LOTRUnitTradeEntry.PledgeType.NONE) {
         reqY += reqGap;
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         this.field_146297_k.func_110434_K().func_110577_a(LOTRClientProxy.alignmentTexture);
         this.func_73729_b(reqX, reqY, 0, 212, 16, 16);
         extraInfo = StatCollector.func_74838_a("container.lotr.unitTrade.pledge");
         this.field_146289_q.func_78276_b(extraInfo, reqXText, reqY + reqYTextBelow, 4210752);
         int i2 = i - this.field_147003_i - reqX;
         int j2 = j - this.field_147009_r - reqY;
         if (i2 >= 0 && i2 < 16 && j2 >= 0 && j2 < 16) {
            String pledgeDesc = curTrade.getPledgeType().getCommandReqText(this.traderFaction);
            this.func_146279_a(pledgeDesc, i - this.field_147003_i, j - this.field_147009_r);
            GL11.glDisable(2896);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         }
      }

      boolean mouseover;
      if (((LOTRContainerUnitTrade)this.field_147002_h).alignmentRewardSlots > 0) {
         Slot slot = this.field_147002_h.func_75139_a(0);
         mouseover = slot.func_75216_d();
         if (mouseover) {
            GL11.glEnable(2896);
            GL11.glEnable(2884);
            field_146296_j.func_82406_b(this.field_146289_q, this.field_146297_k.func_110434_K(), new ItemStack(LOTRMod.silverCoin), 160, 100);
            GL11.glDisable(2896);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            cost = LOTRSlotAlignmentReward.REWARD_COST;
            this.field_146289_q.func_78276_b(String.valueOf(cost), 179, 104, 4210752);
         } else if (!slot.func_75216_d() && LOTRLevelData.getData((EntityPlayer)this.field_146297_k.field_71439_g).getAlignment(this.traderFaction) < 1500.0F && this.func_146978_c(slot.field_75223_e, slot.field_75221_f, 16, 16, i, j)) {
            this.func_146279_a(StatCollector.func_74837_a("container.lotr.unitTrade.requiresAlignment", new Object[]{1500.0F}), i - this.field_147003_i, j - this.field_147009_r);
            GL11.glDisable(2896);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         }
      }

      if (curTrade.hasExtraInfo()) {
         extraInfo = curTrade.getFormattedExtraInfo();
         mouseover = i >= this.field_147003_i + 49 && i < this.field_147003_i + 49 + 9 && j >= this.field_147009_r + 106 && j < this.field_147009_r + 106 + 7;
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         this.field_146297_k.func_110434_K().func_110577_a(guiTexture);
         this.func_73729_b(49, 106, 220, 38 + (mouseover ? 1 : 0) * 7, 9, 7);
         if (mouseover) {
            float z = this.field_73735_i;
            int stringWidth = 200;
            List desc = this.field_146289_q.func_78271_c(extraInfo, stringWidth);
            this.func_146283_a(desc, i - this.field_147003_i, j - this.field_147009_r);
            GL11.glDisable(2896);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.field_73735_i = z;
         }
      }

   }

   protected void func_146976_a(float f, int i, int j) {
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.field_146297_k.func_110434_K().func_110577_a(guiTexture);
      this.func_73729_b(this.field_147003_i, this.field_147009_r, 0, 0, this.field_146999_f, this.field_147000_g);
      if (((LOTRContainerUnitTrade)this.field_147002_h).alignmentRewardSlots > 0) {
         Slot slot = this.field_147002_h.func_75139_a(0);
         this.func_73729_b(this.field_147003_i + slot.field_75223_e - 3, this.field_147009_r + slot.field_75221_f - 3, this.field_146999_f, 16, 22, 22);
         if (!slot.func_75216_d() && LOTRLevelData.getData((EntityPlayer)this.field_146297_k.field_71439_g).getAlignment(this.traderFaction) < 1500.0F) {
            this.func_73729_b(this.field_147003_i + slot.field_75223_e, this.field_147009_r + slot.field_75221_f, this.field_146999_f, 0, 16, 16);
         }
      }

      this.drawMobOnGui(this.field_147003_i + 32, this.field_147009_r + 109, (float)(this.field_147003_i + 32) - this.screenXSize, (float)(this.field_147009_r + 109 - 50) - this.screenYSize);
      boolean squadronPrompt = StringUtils.func_151246_b(this.squadronNameField.func_146179_b()) && !this.squadronNameField.func_146206_l();
      if (squadronPrompt) {
         String squadronMessage = StatCollector.func_74838_a("container.lotr.unitTrade.squadronBox");
         this.squadronNameField.func_146180_a(EnumChatFormatting.DARK_GRAY + squadronMessage);
      }

      this.squadronNameField.func_146194_f();
      if (squadronPrompt) {
         this.squadronNameField.func_146180_a("");
      }

   }

   private void drawMobOnGui(int i, int j, float f, float f1) {
      Class entityClass = this.currentTrade().entityClass;
      Class mountClass = this.currentTrade().mountClass;
      if (this.currentDisplayedMob == null || this.currentDisplayedMob.getClass() != entityClass || mountClass == null && this.currentDisplayedMount != null || mountClass != null && (this.currentDisplayedMount == null || this.currentDisplayedMount.getClass() != mountClass)) {
         this.currentDisplayedMob = this.currentTrade().getOrCreateHiredNPC(this.field_146297_k.field_71441_e);
         if (mountClass != null) {
            EntityLiving mount = this.currentTrade().createHiredMount(this.field_146297_k.field_71441_e);
            this.currentDisplayedMount = mount;
            this.currentDisplayedMob.func_70078_a(this.currentDisplayedMount);
         } else {
            this.currentDisplayedMount = null;
         }
      }

      float size = this.currentDisplayedMob.field_70130_N * this.currentDisplayedMob.field_70131_O * this.currentDisplayedMob.field_70130_N;
      if (this.currentDisplayedMount != null) {
         size += this.currentDisplayedMount.field_70130_N * this.currentDisplayedMount.field_70131_O * this.currentDisplayedMount.field_70130_N * 0.5F;
      }

      float scale = MathHelper.func_76129_c(MathHelper.func_76129_c(1.0F / size)) * 30.0F;
      GL11.glEnable(2903);
      GL11.glPushMatrix();
      GL11.glTranslatef((float)i, (float)j, 50.0F);
      GL11.glScalef(-scale, scale, scale);
      GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
      GL11.glRotatef(135.0F, 0.0F, 1.0F, 0.0F);
      RenderHelper.func_74519_b();
      GL11.glRotatef(-135.0F, 0.0F, 1.0F, 0.0F);
      GL11.glRotatef(-((float)Math.atan((double)(f1 / 40.0F))) * 20.0F, 1.0F, 0.0F, 0.0F);
      this.currentDisplayedMob.field_70761_aq = (float)Math.atan((double)(f / 40.0F)) * 20.0F;
      this.currentDisplayedMob.field_70177_z = (float)Math.atan((double)(f / 40.0F)) * 40.0F;
      this.currentDisplayedMob.field_70125_A = -((float)Math.atan((double)(f1 / 40.0F))) * 20.0F;
      this.currentDisplayedMob.field_70759_as = this.currentDisplayedMob.field_70177_z;
      GL11.glTranslatef(0.0F, this.currentDisplayedMob.field_70129_M, 0.0F);
      if (this.currentDisplayedMount != null) {
         GL11.glTranslatef(0.0F, (float)this.currentDisplayedMount.func_70042_X(), 0.0F);
      }

      RenderManager.field_78727_a.field_78735_i = 180.0F;
      RenderManager.field_78727_a.func_147940_a(this.currentDisplayedMob, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
      GL11.glPopMatrix();
      RenderHelper.func_74518_a();
      GL11.glDisable(32826);
      OpenGlHelper.func_77473_a(OpenGlHelper.field_77476_b);
      GL11.glDisable(3553);
      OpenGlHelper.func_77473_a(OpenGlHelper.field_77478_a);
      if (this.currentDisplayedMount != null) {
         GL11.glEnable(2903);
         GL11.glPushMatrix();
         GL11.glTranslatef((float)i, (float)j, 50.0F);
         GL11.glScalef(-scale, scale, scale);
         GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
         GL11.glRotatef(135.0F, 0.0F, 1.0F, 0.0F);
         RenderHelper.func_74519_b();
         GL11.glRotatef(-135.0F, 0.0F, 1.0F, 0.0F);
         GL11.glRotatef(-((float)Math.atan((double)(f1 / 40.0F))) * 20.0F, 1.0F, 0.0F, 0.0F);
         this.currentDisplayedMount.field_70761_aq = (float)Math.atan((double)(f / 40.0F)) * 20.0F;
         this.currentDisplayedMount.field_70177_z = (float)Math.atan((double)(f / 40.0F)) * 40.0F;
         this.currentDisplayedMount.field_70125_A = -((float)Math.atan((double)(f1 / 40.0F))) * 20.0F;
         this.currentDisplayedMount.field_70759_as = this.currentDisplayedMount.field_70177_z;
         GL11.glTranslatef(0.0F, this.currentDisplayedMount.field_70129_M, 0.0F);
         RenderManager.field_78727_a.field_78735_i = 180.0F;
         RenderManager.field_78727_a.func_147940_a(this.currentDisplayedMount, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
         GL11.glPopMatrix();
         RenderHelper.func_74518_a();
         GL11.glDisable(32826);
         OpenGlHelper.func_77473_a(OpenGlHelper.field_77476_b);
         GL11.glDisable(3553);
         OpenGlHelper.func_77473_a(OpenGlHelper.field_77478_a);
      }

   }

   protected void func_73869_a(char c, int i) {
      if (!this.squadronNameField.func_146176_q() || !this.squadronNameField.func_146201_a(c, i)) {
         super.func_73869_a(c, i);
      }
   }

   protected void func_73864_a(int i, int j, int k) {
      super.func_73864_a(i, j, k);
      this.squadronNameField.func_146192_a(i, j, k);
   }

   protected void func_146284_a(GuiButton button) {
      if (button.field_146124_l) {
         if (button == this.buttonLeftUnit) {
            if (this.currentTradeEntryIndex > 0) {
               --this.currentTradeEntryIndex;
            }
         } else if (button == this.buttonHire) {
            String squadron = this.squadronNameField.func_146179_b();
            LOTRPacketBuyUnit packet = new LOTRPacketBuyUnit(this.currentTradeEntryIndex, squadron);
            LOTRPacketHandler.networkWrapper.sendToServer(packet);
         } else if (button == this.buttonRightUnit && this.currentTradeEntryIndex < this.trades.tradeEntries.length - 1) {
            ++this.currentTradeEntryIndex;
         }
      }

   }

   private void drawCenteredString(String s, int i, int j, int k) {
      this.field_146289_q.func_78276_b(s, i - this.field_146289_q.func_78256_a(s) / 2, j, k);
   }
}
