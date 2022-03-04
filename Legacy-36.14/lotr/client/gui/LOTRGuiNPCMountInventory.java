package lotr.client.gui;

import lotr.common.entity.npc.LOTREntityNPCRideable;
import lotr.common.inventory.LOTRContainerNPCMountInventory;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class LOTRGuiNPCMountInventory extends GuiContainer {
   private static final ResourceLocation guiTexture = new ResourceLocation("textures/gui/container/horse.png");
   private IInventory thePlayerInv;
   private IInventory theMountInv;
   private LOTREntityNPCRideable theMount;
   private float mouseX;
   private float mouseY;

   public LOTRGuiNPCMountInventory(IInventory playerInv, IInventory mountInv, LOTREntityNPCRideable mount) {
      super(new LOTRContainerNPCMountInventory(playerInv, mountInv, mount));
      this.thePlayerInv = playerInv;
      this.theMountInv = mountInv;
      this.theMount = mount;
      this.field_146291_p = false;
   }

   protected void func_146979_b(int i, int j) {
      this.field_146289_q.func_78276_b(this.theMountInv.func_145818_k_() ? this.theMountInv.func_145825_b() : I18n.func_135052_a(this.theMountInv.func_145825_b(), new Object[0]), 8, 6, 4210752);
      this.field_146289_q.func_78276_b(this.thePlayerInv.func_145818_k_() ? this.thePlayerInv.func_145825_b() : I18n.func_135052_a(this.thePlayerInv.func_145825_b(), new Object[0]), 8, this.field_147000_g - 96 + 2, 4210752);
   }

   protected void func_146976_a(float f, int i, int j) {
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.field_146297_k.func_110434_K().func_110577_a(guiTexture);
      int k = (this.field_146294_l - this.field_146999_f) / 2;
      int l = (this.field_146295_m - this.field_147000_g) / 2;
      this.func_73729_b(k, l, 0, 0, this.field_146999_f, this.field_147000_g);
      this.func_73729_b(k + 7, l + 35, 0, this.field_147000_g + 54, 18, 18);
      GuiInventory.func_147046_a(k + 51, l + 60, 17, (float)(k + 51) - this.mouseX, (float)(l + 75 - 50) - this.mouseY, this.theMount);
   }

   public void func_73863_a(int i, int j, float f) {
      this.mouseX = (float)i;
      this.mouseY = (float)j;
      super.func_73863_a(i, j, f);
   }
}
