package lotr.client.render.entity;

import lotr.client.model.LOTRModelHuman;
import lotr.common.LOTRMod;
import lotr.common.entity.item.LOTREntityOrcBomb;
import lotr.common.entity.npc.LOTREntityDunlending;
import lotr.common.entity.npc.LOTREntityDunlendingBerserker;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class LOTRRenderDunlendingBase extends LOTRRenderBiped {
   private static LOTRRandomSkins dunlendingSkinsMale;
   private static LOTRRandomSkins dunlendingSkinsFemale;
   private static LOTRRandomSkins dunlendingSkinsBerserker;

   public LOTRRenderDunlendingBase() {
      super(new LOTRModelHuman(), 0.5F);
      dunlendingSkinsMale = LOTRRandomSkins.loadSkinsList("lotr:mob/dunland/dunlending_male");
      dunlendingSkinsFemale = LOTRRandomSkins.loadSkinsList("lotr:mob/dunland/dunlending_female");
      dunlendingSkinsBerserker = LOTRRandomSkins.loadSkinsList("lotr:mob/dunland/berserker");
   }

   public ResourceLocation func_110775_a(Entity entity) {
      LOTREntityDunlending dunlending = (LOTREntityDunlending)entity;
      if (dunlending.familyInfo.isMale()) {
         return dunlending instanceof LOTREntityDunlendingBerserker ? dunlendingSkinsBerserker.getRandomSkin(dunlending) : dunlendingSkinsMale.getRandomSkin(dunlending);
      } else {
         return dunlendingSkinsFemale.getRandomSkin(dunlending);
      }
   }

   public void func_76986_a(EntityLiving entity, double d, double d1, double d2, float f, float f1) {
      LOTREntityDunlending dunlending = (LOTREntityDunlending)entity;
      ItemStack helmet = dunlending.func_71124_b(4);
      if (helmet != null && helmet.func_77973_b() == Item.func_150898_a(LOTRMod.orcBomb)) {
         d1 += 0.5D;
         GL11.glEnable(32826);
         GL11.glPushMatrix();
         GL11.glTranslatef((float)d, (float)d1 + 2.5F, (float)d2);
         GL11.glRotatef(-f, 0.0F, 1.0F, 0.0F);
         int i = entity.func_70070_b(f1);
         int j = i % 65536;
         int k = i / 65536;
         OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, (float)j / 1.0F, (float)k / 1.0F);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         LOTRRenderOrcBomb bombRenderer = (LOTRRenderOrcBomb)RenderManager.field_78727_a.func_78715_a(LOTREntityOrcBomb.class);
         bombRenderer.renderBomb(entity, 0.0D, 0.0D, 0.0D, f1, 5, 0, 0.75F, 1.0F);
         GL11.glPopMatrix();
         GL11.glDisable(32826);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      }

      super.func_76986_a(entity, d, d1, d2, f, f1);
   }
}
