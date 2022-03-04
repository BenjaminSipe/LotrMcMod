package lotr.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;

public class LOTRModelPortal extends ModelBase {
   private static final int NUM_PARTS = 60;
   private boolean isScript;
   private ModelRenderer[] ringParts = new ModelRenderer[60];
   private Vec3[][] scriptParts = new Vec3[60][4];

   public LOTRModelPortal(boolean flag) {
      this.isScript = flag;
      if (!this.isScript) {
         for(int i = 0; i < 60; ++i) {
            ModelRenderer part = (new ModelRenderer(this, 0, 0)).func_78787_b(64, 32);
            part.func_78789_a(-2.0F, -3.5F, -38.0F, 4, 7, 3);
            part.func_78793_a(0.0F, 0.0F, 0.0F);
            part.field_78796_g = (float)i / 60.0F * 3.1415927F * 2.0F;
            this.ringParts[i] = part;
         }
      } else {
         double depth = 38.0D;
         double halfX = 2.0D;
         double halfY = 2.5D;
         Vec3[] parts = new Vec3[]{Vec3.func_72443_a(halfX, -halfY, -depth), Vec3.func_72443_a(-halfX, -halfY, -depth), Vec3.func_72443_a(-halfX, halfY, -depth), Vec3.func_72443_a(halfX, halfY, -depth)};

         for(int i = 0; i < 60; ++i) {
            float rotate = (float)i / 60.0F * 3.1415927F * 2.0F;

            for(int j = 0; j < parts.length; ++j) {
               Vec3 srcPart = parts[j];
               Vec3 rotatedPart = Vec3.func_72443_a(srcPart.field_72450_a, srcPart.field_72448_b, srcPart.field_72449_c);
               rotatedPart.func_72442_b(-rotate);
               this.scriptParts[i][j] = rotatedPart;
            }
         }
      }

   }

   public void func_78088_a(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      if (!this.isScript) {
         for(int i = 0; i < this.ringParts.length; ++i) {
            this.ringParts[i].func_78785_a(f5);
         }
      } else {
         GL11.glPushMatrix();
         GL11.glScalef(-1.0F, 1.0F, 1.0F);
         Tessellator tess = Tessellator.field_78398_a;

         for(int i = 0; i < 60; ++i) {
            Vec3[] parts = this.scriptParts[i];
            float uMin = (float)i / 60.0F;
            float uMax = (float)(i + 1) / 60.0F;
            float vMin = 0.0F;
            float vMax = 1.0F;
            tess.func_78382_b();
            tess.func_78374_a(parts[0].field_72450_a * (double)f5, parts[0].field_72448_b * (double)f5, parts[0].field_72449_c * (double)f5, (double)uMax, (double)vMin);
            tess.func_78374_a(parts[1].field_72450_a * (double)f5, parts[1].field_72448_b * (double)f5, parts[1].field_72449_c * (double)f5, (double)uMin, (double)vMin);
            tess.func_78374_a(parts[2].field_72450_a * (double)f5, parts[2].field_72448_b * (double)f5, parts[2].field_72449_c * (double)f5, (double)uMin, (double)vMax);
            tess.func_78374_a(parts[3].field_72450_a * (double)f5, parts[3].field_72448_b * (double)f5, parts[3].field_72449_c * (double)f5, (double)uMax, (double)vMax);
            tess.func_78381_a();
         }

         GL11.glPopMatrix();
      }

   }
}
