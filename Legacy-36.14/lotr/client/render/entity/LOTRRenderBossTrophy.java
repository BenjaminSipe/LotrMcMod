package lotr.client.render.entity;

import java.util.HashMap;
import java.util.Map;
import lotr.client.model.LOTRModelEnt;
import lotr.client.model.LOTRModelTroll;
import lotr.common.entity.item.LOTREntityBossTrophy;
import lotr.common.item.LOTRItemBossTrophy;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class LOTRRenderBossTrophy extends Render {
   private static Map trophyTextures = new HashMap();
   private static LOTRModelTroll trollModel = new LOTRModelTroll();
   private static LOTRModelEnt entModel = new LOTRModelEnt();

   protected ResourceLocation func_110775_a(Entity entity) {
      LOTREntityBossTrophy trophy = (LOTREntityBossTrophy)entity;
      LOTRItemBossTrophy.TrophyType type = trophy.getTrophyType();
      ResourceLocation r = (ResourceLocation)trophyTextures.get(type);
      if (r == null) {
         r = new ResourceLocation("lotr:item/bossTrophy/" + type.trophyName + ".png");
         trophyTextures.put(type, r);
      }

      return r;
   }

   public void func_76986_a(Entity entity, double d, double d1, double d2, float f, float f1) {
      LOTREntityBossTrophy trophy = (LOTREntityBossTrophy)entity;
      LOTRItemBossTrophy.TrophyType type = trophy.getTrophyType();
      float modelscale = 0.0625F;
      GL11.glPushMatrix();
      GL11.glDisable(2884);
      GL11.glTranslatef((float)d, (float)d1, (float)d2);
      GL11.glScalef(-1.0F, -1.0F, 1.0F);
      float rotation = 0.0F;
      if (trophy.isTrophyHanging()) {
         rotation = 180.0F + (float)trophy.getTrophyFacing() * 90.0F;
      } else {
         rotation = 180.0F - entity.field_70177_z;
      }

      GL11.glRotatef(rotation, 0.0F, 1.0F, 0.0F);
      this.func_110777_b(entity);
      ModelRenderer trunk;
      if (type == LOTRItemBossTrophy.TrophyType.MOUNTAIN_TROLL_CHIEFTAIN) {
         trunk = trollModel.head;
         trunk.func_78793_a(0.0F, -6.0F, 6.0F);
         GL11.glTranslatef(0.0F, -0.05F, 0.1F);
         GL11.glPushMatrix();
         GL11.glTranslatef(-0.25F, 0.0F, 0.0F);
         GL11.glRotatef(-10.0F, 0.0F, 0.0F, 1.0F);
         GL11.glRotatef(15.0F, 0.0F, 1.0F, 0.0F);
         trunk.func_78785_a(modelscale);
         GL11.glPopMatrix();
         GL11.glPushMatrix();
         GL11.glTranslatef(0.25F, 0.0F, 0.0F);
         GL11.glRotatef(10.0F, 0.0F, 0.0F, 1.0F);
         GL11.glRotatef(-15.0F, 0.0F, 1.0F, 0.0F);
         trunk.func_78785_a(modelscale);
         GL11.glPopMatrix();
      }

      if (type == LOTRItemBossTrophy.TrophyType.MALLORN_ENT) {
         trunk = entModel.trunk;
         entModel.rightArm.field_78806_j = false;
         entModel.leftArm.field_78806_j = false;
         entModel.trophyBottomPanel.field_78806_j = true;
         float scale = 0.6F;
         GL11.glTranslatef(0.0F, 34.0F * modelscale * scale, 0.0F);
         if (trophy.isTrophyHanging()) {
            GL11.glTranslatef(0.0F, 0.0F, 3.0F * modelscale / scale);
         }

         GL11.glScalef(scale, scale, scale);
         trunk.func_78785_a(modelscale);
      }

      GL11.glEnable(2884);
      GL11.glPopMatrix();
   }
}
