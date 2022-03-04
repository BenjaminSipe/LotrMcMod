package lotr.client.render.entity;

import lotr.common.entity.npc.LOTREntityGaladhrimWarden;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import org.lwjgl.opengl.GL11;

public class LOTRRenderGaladhrimWarden extends LOTRRenderElf {
   private void doElfInvisibility() {
      GL11.glDepthMask(false);
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glAlphaFunc(516, 0.001F);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.05F);
   }

   private void undoElfInvisibility() {
      GL11.glDepthMask(true);
      GL11.glDisable(3042);
      GL11.glAlphaFunc(516, 0.1F);
   }

   protected void func_77041_b(EntityLivingBase entity, float f) {
      super.func_77041_b(entity, f);
      if (((LOTREntityGaladhrimWarden)entity).isElfSneaking()) {
         this.doElfInvisibility();
      }

   }

   public int func_77032_a(EntityLiving entity, int pass, float f) {
      int j = super.func_77032_a(entity, pass, f);
      if (j > 0 && ((LOTREntityGaladhrimWarden)entity).isElfSneaking()) {
         this.doElfInvisibility();
      }

      return j;
   }

   protected void func_77029_c(EntityLivingBase entity, float f) {
      if (((LOTREntityGaladhrimWarden)entity).isElfSneaking()) {
         this.doElfInvisibility();
      } else {
         super.func_77029_c(entity, f);
         if (((LOTREntityGaladhrimWarden)entity).isElfSneaking()) {
            this.undoElfInvisibility();
         }

      }
   }

   protected void renderNPCCape(LOTREntityNPC entity) {
      if (((LOTREntityGaladhrimWarden)entity).isElfSneaking()) {
         this.doElfInvisibility();
      }

      super.renderNPCCape(entity);
      if (((LOTREntityGaladhrimWarden)entity).isElfSneaking()) {
         this.undoElfInvisibility();
      }

   }
}
