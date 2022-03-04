package lotr.client.render.entity;

import lotr.client.LOTRTickHandlerClient;
import lotr.client.model.LOTRModelBarrowWight;
import lotr.common.entity.npc.LOTREntityBarrowWight;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class LOTRRenderBarrowWight extends LOTRRenderBiped {
   private static LOTRRandomSkins wightSkins;

   public LOTRRenderBarrowWight() {
      super(new LOTRModelBarrowWight(), 0.0F);
      wightSkins = LOTRRandomSkins.loadSkinsList("lotr:mob/barrowWight/wight");
   }

   public ResourceLocation func_110775_a(Entity entity) {
      LOTREntityBarrowWight wight = (LOTREntityBarrowWight)entity;
      return wightSkins.getRandomSkin(wight);
   }

   public void func_76986_a(Entity entity, double d, double d1, double d2, float f, float f1) {
      super.func_76986_a(entity, d, d1, d2, f, f1);
      LOTREntityBarrowWight wight = (LOTREntityBarrowWight)entity;
      if (wight.field_70175_ag) {
         Entity viewer = Minecraft.func_71410_x().field_71451_h;
         if (viewer != null && wight.getTargetEntityID() == viewer.func_145782_y()) {
            LOTRTickHandlerClient.anyWightsViewed = true;
         }
      }

   }

   protected void func_77041_b(EntityLivingBase entity, float f) {
      super.func_77041_b(entity, f);
      float hover = MathHelper.func_76126_a(((float)entity.field_70173_aa + f) * 0.05F) * 0.2F;
      GL11.glTranslatef(0.0F, hover, 0.0F);
      if (entity.field_70725_aQ > 0) {
         float death = ((float)entity.field_70725_aQ + f - 1.0F) / 20.0F;
         death = Math.max(0.0F, death);
         death = Math.min(1.0F, death);
         float scale = 1.0F + death * 1.0F;
         GL11.glScalef(scale, scale, scale);
         GL11.glEnable(3042);
         GL11.glBlendFunc(770, 771);
         GL11.glEnable(3008);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F - death);
      }

   }

   protected float func_77037_a(EntityLivingBase entity) {
      return 0.0F;
   }
}
