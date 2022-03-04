package lotr.client.gui;

import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.inventory.LOTRContainerHiredFarmerInventory;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

public class LOTRGuiHiredFarmerInventory extends GuiContainer {
   private static ResourceLocation guiTexture = new ResourceLocation("lotr:gui/npc/hiredFarmer.png");
   private LOTREntityNPC theNPC;

   public LOTRGuiHiredFarmerInventory(InventoryPlayer inv, LOTREntityNPC entity) {
      super(new LOTRContainerHiredFarmerInventory(inv, entity));
      this.theNPC = entity;
      this.field_147000_g = 161;
   }

   protected void func_146979_b(int i, int j) {
      String s = this.theNPC.getNPCName();
      this.field_146289_q.func_78276_b(s, this.field_146999_f / 2 - this.field_146289_q.func_78256_a(s) / 2, 6, 4210752);
      this.field_146289_q.func_78276_b(StatCollector.func_74838_a("container.inventory"), 8, 67, 4210752);
      ItemStack seeds = this.field_147002_h.func_75139_a(0).func_75211_c();
      if (seeds != null && seeds.field_77994_a == 1) {
         s = StatCollector.func_74838_a("lotr.gui.farmer.oneSeed");
         s = EnumChatFormatting.RED + s;
         this.field_146289_q.func_78279_b(s, this.field_146999_f + 10, 20, 120, 16777215);
      }

   }

   protected void func_146976_a(float f, int i, int j) {
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.field_146297_k.func_110434_K().func_110577_a(guiTexture);
      this.func_73729_b(this.field_147003_i, this.field_147009_r, 0, 0, this.field_146999_f, this.field_147000_g);
      ItemStack seeds = this.field_147002_h.func_75139_a(0).func_75211_c();
      if (seeds == null) {
         this.func_73729_b(this.field_147003_i + 80, this.field_147009_r + 21, 176, 0, 16, 16);
      }

      ItemStack bonemeal = this.field_147002_h.func_75139_a(3).func_75211_c();
      if (bonemeal == null) {
         this.func_73729_b(this.field_147003_i + 123, this.field_147009_r + 34, 176, 16, 16, 16);
      }

   }
}
