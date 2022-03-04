package lotr.client.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import lotr.client.render.entity.LOTRHuornTextures;
import lotr.common.entity.npc.LOTREntityHuornBase;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public class LOTRModelHuorn extends ModelBase {
   private List woodBlocks = new ArrayList();
   private List leafBlocks = new ArrayList();
   private ModelRenderer face;
   private int baseX = 2;
   private int baseY = 0;
   private int baseZ = 2;
   private Random rand = new Random();

   public LOTRModelHuorn() {
      this.rand.setSeed(100L);
      int height = 6;
      byte leafStart = 3;
      byte leafRangeMin = 0;

      int j;
      for(j = this.baseY - leafStart + height; j <= this.baseY + height; ++j) {
         int j1 = j - (this.baseY + height);
         int leafRange = leafRangeMin + 1 - j1 / 2;

         for(int i = this.baseX - leafRange; i <= this.baseX + leafRange; ++i) {
            int i1 = i - this.baseX;

            for(int k = this.baseZ - leafRange; k <= this.baseZ + leafRange; ++k) {
               int k1 = k - this.baseZ;
               if (Math.abs(i1) != leafRange || Math.abs(k1) != leafRange || this.rand.nextInt(2) != 0 && j1 != 0) {
                  ModelRenderer block = new ModelRenderer(this, 0, 0);
                  block.func_78789_a(-8.0F, -8.0F, -8.0F, 16, 16, 16);
                  block.func_78793_a((float)i * 16.0F, 16.0F - (float)j * 16.0F, (float)k * 16.0F);
                  this.leafBlocks.add(block);
               }
            }
         }
      }

      for(j = 0; j < height; ++j) {
         ModelRenderer block = new ModelRenderer(this, 0, 0);
         block.func_78789_a(-8.0F, -8.0F, -8.0F, 16, 16, 16);
         block.func_78793_a((float)this.baseX * 16.0F, 16.0F - (float)j * 16.0F, (float)this.baseZ * 16.0F);
         this.woodBlocks.add(block);
      }

      this.face = new ModelRenderer(this, 0, 0);
      this.face.func_78790_a(-8.0F, -8.0F, -8.0F, 16, 16, 16, 0.5F);
      this.face.func_78793_a(0.0F, 0.0F, 0.0F);
   }

   public void func_78088_a(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      LOTREntityHuornBase huorn = (LOTREntityHuornBase)entity;
      if (huorn.isHuornActive()) {
         this.face.func_78785_a(f5);
      }

      GL11.glPushMatrix();
      GL11.glEnable(2884);
      GL11.glTranslatef(-((float)this.baseX), -((float)this.baseY), -((float)this.baseZ));

      int i;
      ModelRenderer block;
      for(i = 0; i < this.woodBlocks.size(); ++i) {
         if (i == 0) {
            LOTRHuornTextures.INSTANCE.bindWoodTexture(huorn);
         }

         block = (ModelRenderer)this.woodBlocks.get(i);
         block.func_78785_a(f5);
      }

      for(i = 0; i < this.leafBlocks.size(); ++i) {
         if (i == 0) {
            LOTRHuornTextures.INSTANCE.bindLeafTexture(huorn);
         }

         block = (ModelRenderer)this.leafBlocks.get(i);
         block.func_78785_a(f5);
      }

      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glDisable(2884);
      GL11.glPopMatrix();
   }
}
