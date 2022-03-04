package lotr.client.model;

import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.item.LOTRItemHaradRobes;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

public class LOTRModelHaradRobes extends LOTRModelHuman {
   protected ItemStack robeItem;

   public LOTRModelHaradRobes() {
      this(0.0F);
   }

   public LOTRModelHaradRobes(float f) {
      super(f, true);
   }

   public void setRobeItem(ItemStack itemstack) {
      this.robeItem = itemstack;
   }

   public void func_78088_a(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      int robeColor = LOTRItemHaradRobes.getRobesColor(this.robeItem);
      float r = (float)(robeColor >> 16 & 255) / 255.0F;
      float g = (float)(robeColor >> 8 & 255) / 255.0F;
      float b = (float)(robeColor & 255) / 255.0F;
      GL11.glColor3f(r, g, b);
      this.bipedChest.field_78806_j = entity instanceof LOTREntityNPC && ((LOTREntityNPC)entity).shouldRenderNPCChest();
      this.field_78116_c.func_78785_a(f5);
      this.field_78114_d.func_78785_a(f5);
      this.field_78115_e.func_78785_a(f5);
      this.field_78112_f.func_78785_a(f5);
      this.field_78113_g.func_78785_a(f5);
      this.field_78123_h.func_78785_a(f5);
      this.field_78124_i.func_78785_a(f5);
      GL11.glColor3f(1.0F, 1.0F, 1.0F);
   }
}
