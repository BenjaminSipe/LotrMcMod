package lotr.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public class LOTRModelLionRug extends ModelBase {
   private LOTRModelLion lionModel = new LOTRModelLion();

   public void func_78088_a(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      this.setRotationAngles();
      GL11.glTranslatef(0.0F, -0.4F, 0.0F);
      GL11.glPushMatrix();
      GL11.glScalef(1.5F, 0.4F, 1.0F);
      this.lionModel.body.func_78785_a(f5);
      GL11.glPopMatrix();
      GL11.glPushMatrix();
      GL11.glTranslatef(0.0F, 0.0F, 0.0F);
      this.lionModel.tail.func_78785_a(f5);
      GL11.glPopMatrix();
      GL11.glPushMatrix();
      GL11.glTranslatef(0.0F, -0.1F, 0.1F);
      this.lionModel.head.func_78785_a(f5);
      this.lionModel.mane.func_78785_a(f5);
      GL11.glPopMatrix();
      GL11.glTranslatef(0.0F, 0.15F, 0.0F);
      GL11.glPushMatrix();
      GL11.glTranslatef(-0.4F, 0.0F, 0.0F);
      this.lionModel.leg1.func_78785_a(f5);
      this.lionModel.leg3.func_78785_a(f5);
      GL11.glPopMatrix();
      GL11.glPushMatrix();
      GL11.glTranslatef(0.4F, 0.0F, 0.0F);
      this.lionModel.leg2.func_78785_a(f5);
      this.lionModel.leg4.func_78785_a(f5);
      GL11.glPopMatrix();
   }

   private void setRotationAngles() {
      this.lionModel.leg1.field_78795_f = (float)Math.toRadians(30.0D);
      this.lionModel.leg1.field_78808_h = (float)Math.toRadians(90.0D);
      this.lionModel.leg2.field_78795_f = (float)Math.toRadians(30.0D);
      this.lionModel.leg2.field_78808_h = (float)Math.toRadians(-90.0D);
      this.lionModel.leg3.field_78795_f = (float)Math.toRadians(-20.0D);
      this.lionModel.leg3.field_78808_h = (float)Math.toRadians(90.0D);
      this.lionModel.leg4.field_78795_f = (float)Math.toRadians(-20.0D);
      this.lionModel.leg4.field_78808_h = (float)Math.toRadians(-90.0D);
   }
}
