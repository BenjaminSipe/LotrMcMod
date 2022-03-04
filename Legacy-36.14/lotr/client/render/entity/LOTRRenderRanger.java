package lotr.client.render.entity;

import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.entity.npc.LOTREntityRanger;
import lotr.common.entity.npc.LOTREntityRangerIthilien;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class LOTRRenderRanger extends LOTRRenderDunedain {
   private static LOTRRandomSkins ithilienSkins;

   public LOTRRenderRanger() {
      ithilienSkins = LOTRRandomSkins.loadSkinsList("lotr:mob/gondor/ranger");
   }

   public ResourceLocation func_110775_a(Entity entity) {
      LOTREntityRanger ranger = (LOTREntityRanger)entity;
      return ranger instanceof LOTREntityRangerIthilien ? ithilienSkins.getRandomSkin(ranger) : super.func_110775_a(entity);
   }

   private void doRangerInvisibility() {
      GL11.glDepthMask(false);
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glAlphaFunc(516, 0.001F);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.15F);
   }

   private void undoRangerInvisibility() {
      GL11.glDepthMask(true);
      GL11.glDisable(3042);
      GL11.glAlphaFunc(516, 0.1F);
   }

   protected void func_77041_b(EntityLivingBase entity, float f) {
      super.func_77041_b(entity, f);
      if (((LOTREntityRanger)entity).isRangerSneaking()) {
         this.doRangerInvisibility();
      }

   }

   public int func_77032_a(EntityLiving entity, int pass, float f) {
      int i = super.func_77032_a(entity, pass, f);
      if (i > 0 && ((LOTREntityRanger)entity).isRangerSneaking()) {
         this.doRangerInvisibility();
      }

      return i;
   }

   protected void func_77029_c(EntityLivingBase entity, float f) {
      if (((LOTREntityRanger)entity).isRangerSneaking()) {
         this.doRangerInvisibility();
      }

      super.func_77029_c(entity, f);
      if (((LOTREntityRanger)entity).isRangerSneaking()) {
         this.undoRangerInvisibility();
      }

   }

   protected void renderNPCCape(LOTREntityNPC entity) {
      if (((LOTREntityRanger)entity).isRangerSneaking()) {
         this.doRangerInvisibility();
      }

      super.renderNPCCape(entity);
      if (((LOTREntityRanger)entity).isRangerSneaking()) {
         this.undoRangerInvisibility();
      }

   }
}
