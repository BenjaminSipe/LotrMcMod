package lotr.client.gui;

import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.entity.npc.LOTRTradeEntries;
import lotr.common.entity.npc.LOTRTradeEntry;
import lotr.common.entity.npc.LOTRTradeSellResult;
import lotr.common.entity.npc.LOTRTradeable;
import lotr.common.inventory.LOTRContainerTrade;
import lotr.common.inventory.LOTRSlotTrade;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.LOTRPacketSell;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class LOTRGuiTrade extends GuiContainer {
   public static final ResourceLocation guiTexture = new ResourceLocation("lotr:gui/npc/trade.png");
   private static int lockedTradeColor = -1610612736;
   public LOTREntityNPC theEntity;
   private LOTRContainerTrade containerTrade;
   private GuiButton buttonSell;
   private static int sellQueryX;
   private static int sellQueryY;
   private static int sellQueryWidth = 12;

   public LOTRGuiTrade(InventoryPlayer inv, LOTRTradeable trader, World world) {
      super(new LOTRContainerTrade(inv, trader, world));
      this.containerTrade = (LOTRContainerTrade)this.field_147002_h;
      this.theEntity = (LOTREntityNPC)trader;
      this.field_147000_g = 270;
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      this.buttonSell = new LOTRGuiTradeButton(0, this.field_147003_i + 79, this.field_147009_r + 164);
      this.buttonSell.field_146124_l = false;
      this.field_146292_n.add(this.buttonSell);
   }

   protected void func_146979_b(int i, int j) {
      this.drawCenteredString(this.theEntity.getNPCName(), 89, 11, 4210752);
      this.field_146289_q.func_78276_b(StatCollector.func_74838_a("container.lotr.trade.buy"), 8, 28, 4210752);
      this.field_146289_q.func_78276_b(StatCollector.func_74838_a("container.lotr.trade.sell"), 8, 79, 4210752);
      this.field_146289_q.func_78276_b(StatCollector.func_74838_a("container.lotr.trade.sellOffer"), 8, 129, 4210752);
      this.field_146289_q.func_78276_b(StatCollector.func_74838_a("container.inventory"), 8, 176, 4210752);

      int totalSellPrice;
      LOTRSlotTrade slotSell;
      for(totalSellPrice = 0; totalSellPrice < this.containerTrade.tradeInvBuy.func_70302_i_(); ++totalSellPrice) {
         slotSell = (LOTRSlotTrade)this.containerTrade.func_75147_a(this.containerTrade.tradeInvBuy, totalSellPrice);
         this.renderTradeSlot(slotSell);
      }

      for(totalSellPrice = 0; totalSellPrice < this.containerTrade.tradeInvSell.func_70302_i_(); ++totalSellPrice) {
         slotSell = (LOTRSlotTrade)this.containerTrade.func_75147_a(this.containerTrade.tradeInvSell, totalSellPrice);
         this.renderTradeSlot(slotSell);
      }

      totalSellPrice = 0;

      for(int l = 0; l < this.containerTrade.tradeInvSellOffer.func_70302_i_(); ++l) {
         Slot slotSell = this.containerTrade.func_75147_a(this.containerTrade.tradeInvSellOffer, l);
         ItemStack item = slotSell.func_75211_c();
         if (item != null) {
            LOTRTradeSellResult sellResult = LOTRTradeEntries.getItemSellResult(item, this.theEntity);
            if (sellResult != null) {
               totalSellPrice += sellResult.totalSellValue;
            }
         }
      }

      if (totalSellPrice > 0) {
         this.field_146289_q.func_78276_b(StatCollector.func_74837_a("container.lotr.trade.sellPrice", new Object[]{totalSellPrice}), 100, 169, 4210752);
      }

      this.buttonSell.field_146124_l = totalSellPrice > 0;
   }

   private void renderTradeSlot(LOTRSlotTrade slot) {
      LOTRTradeEntry trade = slot.getTrade();
      if (trade != null) {
         int lockedPixels = false;
         boolean inFront = false;
         int lockedPixels;
         if (!trade.isAvailable()) {
            lockedPixels = 16;
            inFront = true;
         } else {
            lockedPixels = trade.getLockedProgressForSlot();
            inFront = false;
         }

         int cost;
         if (lockedPixels > 0) {
            GL11.glPushMatrix();
            if (inFront) {
               GL11.glTranslatef(0.0F, 0.0F, 200.0F);
            }

            cost = slot.field_75223_e;
            int y = slot.field_75221_f;
            func_73734_a(cost, y, cost + lockedPixels, y + 16, lockedTradeColor);
            GL11.glPopMatrix();
         }

         if (trade.isAvailable()) {
            cost = slot.cost();
            if (cost > 0) {
               this.renderCost(Integer.toString(cost), slot.field_75223_e + 8, slot.field_75221_f + 22);
            }
         } else {
            this.drawCenteredString(StatCollector.func_74838_a("container.lotr.trade.locked"), slot.field_75223_e + 8, slot.field_75221_f + 22, 4210752);
         }
      }

   }

   private void renderCost(String s, int x, int y) {
      int l = this.field_146289_q.func_78256_a(s);
      boolean halfSize = l > 15;
      if (halfSize) {
         GL11.glPushMatrix();
         GL11.glScalef(0.5F, 0.5F, 1.0F);
         x *= 2;
         y *= 2;
         y += this.field_146289_q.field_78288_b / 2;
      }

      this.drawCenteredString(s, x, y, 4210752);
      if (halfSize) {
         GL11.glPopMatrix();
      }

   }

   protected void func_146976_a(float f, int i, int j) {
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.field_146297_k.func_110434_K().func_110577_a(guiTexture);
      func_146110_a(this.field_147003_i, this.field_147009_r, 0.0F, 0.0F, this.field_146999_f, this.field_147000_g, 512.0F, 512.0F);
   }

   protected void func_146284_a(GuiButton button) {
      if (button.field_146124_l && button == this.buttonSell) {
         LOTRPacketSell packet = new LOTRPacketSell();
         LOTRPacketHandler.networkWrapper.sendToServer(packet);
      }

   }

   private void drawCenteredString(String s, int i, int j, int k) {
      this.field_146289_q.func_78276_b(s, i - this.field_146289_q.func_78256_a(s) / 2, j, k);
   }
}
