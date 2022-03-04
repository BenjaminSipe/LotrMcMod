package lotr.client.render.entity;

import lotr.client.model.LOTRModelTroll;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.entity.npc.LOTREntityTroll;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class LOTRRenderTroll extends RenderLiving {
   private static LOTRRandomSkins trollSkins;
   public static ResourceLocation[] trollOutfits = new ResourceLocation[]{new ResourceLocation("lotr:mob/troll/outfit_0.png"), new ResourceLocation("lotr:mob/troll/outfit_1.png"), new ResourceLocation("lotr:mob/troll/outfit_2.png")};
   private static ResourceLocation weaponsTexture = new ResourceLocation("lotr:mob/troll/weapons.png");
   private LOTRModelTroll shirtModel = new LOTRModelTroll(1.0F, 0);
   private LOTRModelTroll trousersModel = new LOTRModelTroll(0.75F, 1);

   public LOTRRenderTroll() {
      super(new LOTRModelTroll(), 0.5F);
      trollSkins = LOTRRandomSkins.loadSkinsList("lotr:mob/troll/troll");
   }

   protected ResourceLocation func_110775_a(Entity entity) {
      return trollSkins.getRandomSkin((LOTREntityTroll)entity);
   }

   public void func_76986_a(EntityLiving entity, double d, double d1, double d2, float f, float f1) {
      super.func_76986_a(entity, d, d1, d2, f, f1);
      if (Minecraft.func_71382_s() && ((LOTREntityNPC)entity).hiredNPCInfo.getHiringPlayer() == this.field_76990_c.field_78734_h) {
         LOTRNPCRendering.renderHiredIcon(entity, d, d1 + 1.0D, d2);
         LOTRNPCRendering.renderNPCHealthBar(entity, d, d1 + 1.0D, d2);
      }

   }

   protected void func_77041_b(EntityLivingBase entity, float f) {
      LOTREntityTroll troll = (LOTREntityTroll)entity;
      this.scaleTroll(troll, false);
      if (!LOTRMod.isAprilFools() && !troll.familyInfo.getName().toLowerCase().equals("shrek")) {
         if (troll.familyInfo.getName().toLowerCase().equals("drek")) {
            GL11.glColor3f(0.2F, 0.4F, 1.0F);
         }
      } else {
         GL11.glColor3f(0.0F, 1.0F, 0.0F);
      }

   }

   protected void scaleTroll(LOTREntityTroll troll, boolean inverse) {
      float scale = troll.getTrollScale();
      if (inverse) {
         scale = 1.0F / scale;
      }

      GL11.glScalef(scale, scale, scale);
   }

   protected void func_77029_c(EntityLivingBase entity, float f) {
      GL11.glColor3f(1.0F, 1.0F, 1.0F);
      super.func_77029_c(entity, f);
      GL11.glPushMatrix();
      this.func_110776_a(weaponsTexture);
      this.renderTrollWeapon(entity, f);
      GL11.glPopMatrix();
   }

   protected void renderTrollWeapon(EntityLivingBase entity, float f) {
      ((LOTRModelTroll)this.field_77045_g).renderWoodenClub(0.0625F);
   }

   protected int func_77032_a(EntityLivingBase entity, int pass, float f) {
      this.bindTrollOutfitTexture(entity);
      if (pass == 0) {
         this.shirtModel.field_78095_p = this.field_77045_g.field_78095_p;
         this.func_77042_a(this.shirtModel);
         return 1;
      } else if (pass == 1) {
         this.trousersModel.field_78095_p = this.trousersModel.field_78095_p;
         this.func_77042_a(this.trousersModel);
         return 1;
      } else {
         return super.func_77032_a(entity, pass, f);
      }
   }

   protected void bindTrollOutfitTexture(EntityLivingBase entity) {
      int j = ((LOTREntityTroll)entity).getTrollOutfit();
      if (j < 0 || j >= trollOutfits.length) {
         j = 0;
      }

      this.func_110776_a(trollOutfits[j]);
   }

   protected void func_77043_a(EntityLivingBase entity, float f, float f1, float f2) {
      if (((LOTREntityTroll)entity).getSneezingTime() > 0) {
         f1 += (float)(Math.cos((double)entity.field_70173_aa * 3.25D) * 3.141592653589793D);
      }

      super.func_77043_a(entity, f, f1, f2);
   }
}
