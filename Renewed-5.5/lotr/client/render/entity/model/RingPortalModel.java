package lotr.client.render.entity.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.matrix.MatrixStack.Entry;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import lotr.common.entity.item.RingPortalEntity;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.vector.Matrix3f;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.math.vector.Vector4f;

public class RingPortalModel extends EntityModel {
   private static final int NUM_PARTS = 60;
   private boolean isScript;
   private ModelRenderer[] ringParts = new ModelRenderer[60];
   private Vector3f[][] scriptParts = new Vector3f[60][4];

   public RingPortalModel(boolean flag) {
      this.isScript = flag;
      if (!this.isScript) {
         for(int i = 0; i < 60; ++i) {
            ModelRenderer part = (new ModelRenderer(this, 0, 0)).func_78787_b(64, 32);
            part.func_228300_a_(-2.0F, -3.5F, -38.0F, 4.0F, 7.0F, 3.0F);
            part.func_78793_a(0.0F, 0.0F, 0.0F);
            part.field_78796_g = (float)i / 60.0F * 3.1415927F * 2.0F;
            this.ringParts[i] = part;
         }
      } else {
         float depth = 38.0F;
         float halfX = 2.0F;
         float halfY = 2.5F;
         Vector3f[] parts = new Vector3f[]{new Vector3f(halfX, -halfY, -depth), new Vector3f(-halfX, -halfY, -depth), new Vector3f(-halfX, halfY, -depth), new Vector3f(halfX, halfY, -depth)};

         for(int i = 0; i < 60; ++i) {
            float rotate = (float)i / 60.0F * 3.1415927F * 2.0F;

            for(int j = 0; j < parts.length; ++j) {
               Vector3f srcPart = parts[j];
               Vector3f rotatedPart = new Vector3f(srcPart.func_195899_a(), srcPart.func_195900_b(), srcPart.func_195902_c());
               rotatedPart.func_214905_a(new Quaternion(Vector3f.field_229181_d_, -rotate, false));
               this.scriptParts[i][j] = rotatedPart;
            }
         }
      }

   }

   public void setRotationAngles(RingPortalEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
   }

   public void func_225598_a_(MatrixStack mat, IVertexBuilder buf, int light, int overlay, float r, float g, float b, float a) {
      if (!this.isScript) {
         for(int i = 0; i < this.ringParts.length; ++i) {
            this.ringParts[i].func_228309_a_(mat, buf, light, overlay, r, g, b, a);
         }
      } else {
         mat.func_227860_a_();
         Entry mEntry = mat.func_227866_c_();
         Matrix4f matrix = mEntry.func_227870_a_();
         Matrix3f matrixNormal = mEntry.func_227872_b_();
         Vector3f normal = Vector3f.field_229180_c_.func_229195_e_();
         normal.func_229188_a_(matrixNormal);
         float nx = normal.func_195899_a();
         float ny = normal.func_195900_b();
         float nz = normal.func_195902_c();

         for(int i = 0; i < 60; ++i) {
            Vector3f[] parts = this.scriptParts[i];
            float uMin = (float)i / 60.0F;
            float uMax = (float)(i + 1) / 60.0F;
            float vMin = 0.0F;
            float vMax = 1.0F;
            float f5 = 0.0625F;
            this.addVertexUV(buf, parts[0].func_195899_a() * f5, parts[0].func_195900_b() * f5, parts[0].func_195902_c() * f5, uMax, vMin, matrix, r, g, b, a, light, overlay, normal);
            this.addVertexUV(buf, parts[1].func_195899_a() * f5, parts[1].func_195900_b() * f5, parts[1].func_195902_c() * f5, uMin, vMin, matrix, r, g, b, a, light, overlay, normal);
            this.addVertexUV(buf, parts[2].func_195899_a() * f5, parts[2].func_195900_b() * f5, parts[2].func_195902_c() * f5, uMin, vMax, matrix, r, g, b, a, light, overlay, normal);
            this.addVertexUV(buf, parts[3].func_195899_a() * f5, parts[3].func_195900_b() * f5, parts[3].func_195902_c() * f5, uMax, vMax, matrix, r, g, b, a, light, overlay, normal);
         }

         mat.func_227865_b_();
      }

   }

   private void addVertexUV(IVertexBuilder buf, float x, float y, float z, float u, float v, Matrix4f matrix, float r, float g, float b, float a, int light, int overlay, Vector3f normal) {
      Vector4f v4 = new Vector4f(x, y, z, 1.0F);
      v4.func_229372_a_(matrix);
      buf.func_225588_a_(v4.func_195910_a(), v4.func_195913_b(), v4.func_195914_c(), r, g, b, a, u, v, overlay, light, normal.func_195899_a(), normal.func_195900_b(), normal.func_195902_c());
   }
}
