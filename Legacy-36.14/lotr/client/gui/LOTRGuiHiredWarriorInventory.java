package lotr.client.gui;

import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.entity.npc.LOTREntityOrc;
import lotr.common.entity.npc.LOTRInventoryHiredReplacedItems;
import lotr.common.inventory.LOTRContainerHiredWarriorInventory;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

public class LOTRGuiHiredWarriorInventory extends GuiContainer {
   private static ResourceLocation guiTexture = new ResourceLocation("lotr:gui/npc/hiredWarrior.png");
   private LOTREntityNPC theNPC;
   private LOTRContainerHiredWarriorInventory containerInv;

   public LOTRGuiHiredWarriorInventory(InventoryPlayer inv, LOTREntityNPC entity) {
      super(new LOTRContainerHiredWarriorInventory(inv, entity));
      this.theNPC = entity;
      this.containerInv = (LOTRContainerHiredWarriorInventory)this.field_147002_h;
      this.field_147000_g = 188;
   }

   protected void func_146979_b(int i, int j) {
      String s = StatCollector.func_74838_a("lotr.gui.warrior.openInv");
      this.field_146289_q.func_78276_b(s, this.field_146999_f / 2 - this.field_146289_q.func_78256_a(s) / 2, 6, 4210752);
      this.field_146289_q.func_78276_b(StatCollector.func_74838_a("container.inventory"), 8, 95, 4210752);
   }

   protected void func_146976_a(float f, int i, int j) {
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.field_146297_k.func_110434_K().func_110577_a(guiTexture);
      this.func_73729_b(this.field_147003_i, this.field_147009_r, 0, 0, this.field_146999_f, this.field_147000_g);
      if (this.theNPC instanceof LOTREntityOrc && ((LOTREntityOrc)this.theNPC).isOrcBombardier()) {
         LOTRInventoryHiredReplacedItems var10002 = this.containerInv.npcInv;
         Slot slotBomb = this.containerInv.func_75147_a(this.containerInv.proxyInv, 5);
         var10002 = this.containerInv.npcInv;
         Slot slotMelee = this.containerInv.func_75147_a(this.containerInv.proxyInv, 4);
         this.func_73729_b(this.field_147003_i + slotBomb.field_75223_e - 1, this.field_147009_r + slotBomb.field_75221_f - 1, slotMelee.field_75223_e - 1, slotMelee.field_75221_f - 1, 18, 18);
      }

   }
}
