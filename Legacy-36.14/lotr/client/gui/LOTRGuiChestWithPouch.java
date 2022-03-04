package lotr.client.gui;

import lotr.common.inventory.LOTRContainerChestWithPouch;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

public class LOTRGuiChestWithPouch extends GuiContainer {
   private static final ResourceLocation guiTexture = new ResourceLocation("lotr:gui/pouch_with_chest.png");
   private IInventory pouchInv;
   private IInventory chestInv;
   private int chestRows;
   private int pouchRows;

   public LOTRGuiChestWithPouch(EntityPlayer entityplayer, int slot, IInventory chest) {
      super(new LOTRContainerChestWithPouch(entityplayer, slot, chest));
      this.pouchInv = ((LOTRContainerChestWithPouch)this.field_147002_h).pouchContainer.pouchInventory;
      this.chestInv = chest;
      this.field_146291_p = false;
      this.chestRows = chest.func_70302_i_() / 9;
      this.pouchRows = this.pouchInv.func_70302_i_() / 9;
      this.field_147000_g = 180 + this.chestRows * 18;
   }

   protected void func_146979_b(int i, int j) {
      this.field_146289_q.func_78276_b(this.chestInv.func_145818_k_() ? this.chestInv.func_145825_b() : StatCollector.func_74838_a(this.chestInv.func_145825_b()), 8, 6, 4210752);
      this.field_146289_q.func_78276_b(this.pouchInv.func_145818_k_() ? this.pouchInv.func_145825_b() : StatCollector.func_74838_a(this.pouchInv.func_145825_b()), 8, this.field_147000_g - 160, 4210752);
      this.field_146289_q.func_78276_b(StatCollector.func_74838_a("container.inventory"), 8, this.field_147000_g - 93, 4210752);
   }

   protected void func_146976_a(float f, int i, int j) {
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.field_146297_k.func_110434_K().func_110577_a(guiTexture);
      this.func_73729_b(this.field_147003_i, this.field_147009_r, 0, 0, this.field_146999_f, 17 + this.chestRows * 18);
      this.func_73729_b(this.field_147003_i, this.field_147009_r + 17 + this.chestRows * 18, 0, 125, this.field_146999_f, 13);

      int l;
      for(l = 0; l < 3; ++l) {
         this.func_73729_b(this.field_147003_i, this.field_147009_r + 17 + this.chestRows * 18 + 13 + l * 18, 0, 138, this.field_146999_f, 18);
      }

      this.func_73729_b(this.field_147003_i, this.field_147009_r + 17 + this.chestRows * 18 + 67, 0, 156, this.field_146999_f, 96);
      this.field_146297_k.func_110434_K().func_110577_a(LOTRGuiPouch.texture);

      for(l = 0; l < this.pouchRows; ++l) {
         this.func_73729_b(this.field_147003_i + 7, this.field_147009_r + 17 + this.chestRows * 18 + 13 + l * 18, 0, 180, 162, 18);
      }

   }
}
