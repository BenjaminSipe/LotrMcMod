package lotr.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public class LOTRModelGiraffeRug extends ModelBase {
   private LOTRModelGiraffe giraffeModel = new LOTRModelGiraffe(0.0F);

   public void func_78088_a(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      this.giraffeModel.setRiddenHeadNeckRotation(f, f1, f2, f3, f4, f5);
      this.setRotationAngles();
      GL11.glTranslatef(0.0F, 0.1F, 0.0F);
      GL11.glPushMatrix();
      GL11.glScalef(1.5F, 0.4F, 1.0F);
      this.giraffeModel.body.func_78785_a(f5);
      this.giraffeModel.tail.func_78785_a(f5);
      GL11.glPopMatrix();
      GL11.glPushMatrix();
      GL11.glTranslatef(0.0F, 0.6F, -0.2F);
      this.giraffeModel.head.func_78785_a(f5);
      this.giraffeModel.neck.func_78785_a(f5);
      GL11.glPopMatrix();
      GL11.glTranslatef(0.0F, 0.0F, 0.0F);
      GL11.glPushMatrix();
      GL11.glTranslatef(-0.25F, 0.0F, 0.0F);
      this.giraffeModel.leg1.func_78785_a(f5);
      this.giraffeModel.leg3.func_78785_a(f5);
      GL11.glPopMatrix();
      GL11.glPushMatrix();
      GL11.glTranslatef(0.25F, 0.0F, 0.0F);
      this.giraffeModel.leg2.func_78785_a(f5);
      this.giraffeModel.leg4.func_78785_a(f5);
      GL11.glPopMatrix();
   }

   private void setRotationAngles() {
      this.giraffeModel.leg1.field_78795_f = (float)Math.toRadians(30.0D);
      this.giraffeModel.leg1.field_78808_h = (float)Math.toRadians(90.0D);
      this.giraffeModel.leg2.field_78795_f = (float)Math.toRadians(30.0D);
      this.giraffeModel.leg2.field_78808_h = (float)Math.toRadians(-90.0D);
      this.giraffeModel.leg3.field_78795_f = (float)Math.toRadians(-20.0D);
      this.giraffeModel.leg3.field_78808_h = (float)Math.toRadians(90.0D);
      this.giraffeModel.leg4.field_78795_f = (float)Math.toRadians(-20.0D);
      this.giraffeModel.leg4.field_78808_h = (float)Math.toRadians(-90.0D);
   }
}
