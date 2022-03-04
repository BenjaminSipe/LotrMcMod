package lotr.client.render.entity;

import lotr.client.model.LOTRModelHobbit;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityHobbit;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class LOTRRenderHobbit extends LOTRRenderBiped {
   private static LOTRRandomSkins hobbitSkinsMale;
   private static LOTRRandomSkins hobbitSkinsFemale;
   private static LOTRRandomSkins hobbitSkinsMaleChild;
   private static LOTRRandomSkins hobbitSkinsFemaleChild;
   private static ResourceLocation ringTexture = new ResourceLocation("lotr:mob/hobbit/ring.png");
   protected ModelBiped outfitModel = new LOTRModelHobbit(0.5F, 64, 64);

   public LOTRRenderHobbit() {
      super(new LOTRModelHobbit(), 0.5F);
      this.func_77042_a(this.outfitModel);
      hobbitSkinsMale = LOTRRandomSkins.loadSkinsList("lotr:mob/hobbit/hobbit_male");
      hobbitSkinsFemale = LOTRRandomSkins.loadSkinsList("lotr:mob/hobbit/hobbit_female");
      hobbitSkinsMaleChild = LOTRRandomSkins.loadSkinsList("lotr:mob/hobbit/child_male");
      hobbitSkinsFemaleChild = LOTRRandomSkins.loadSkinsList("lotr:mob/hobbit/child_female");
   }

   protected void func_82421_b() {
      this.field_82423_g = new LOTRModelHobbit(1.0F);
      this.field_82425_h = new LOTRModelHobbit(0.5F);
   }

   public ResourceLocation func_110775_a(Entity entity) {
      LOTREntityHobbit hobbit = (LOTREntityHobbit)entity;
      boolean child = hobbit.func_70631_g_();
      if (hobbit.familyInfo.isMale()) {
         return child ? hobbitSkinsMaleChild.getRandomSkin(hobbit) : hobbitSkinsMale.getRandomSkin(hobbit);
      } else {
         return child ? hobbitSkinsFemaleChild.getRandomSkin(hobbit) : hobbitSkinsFemale.getRandomSkin(hobbit);
      }
   }

   public int func_77032_a(EntityLiving entity, int pass, float f) {
      LOTREntityHobbit hobbit = (LOTREntityHobbit)entity;
      this.outfitModel.field_78112_f.field_78806_j = true;
      if (pass == 1 && hobbit.getClass() == hobbit.familyInfo.marriageEntityClass && hobbit.func_71124_b(4) != null && hobbit.func_71124_b(4).func_77973_b() == hobbit.familyInfo.marriageRing) {
         this.func_110776_a(ringTexture);
         this.outfitModel.field_78112_f.field_78806_j = false;
         this.func_77042_a(this.outfitModel);
         return 1;
      } else {
         return super.func_77032_a(entity, pass, f);
      }
   }

   protected void func_77041_b(EntityLivingBase entity, float f) {
      super.func_77041_b(entity, f);
      if (LOTRMod.isAprilFools()) {
         GL11.glScalef(2.0F, 2.0F, 2.0F);
      } else {
         GL11.glScalef(0.75F, 0.75F, 0.75F);
      }

   }

   public float getHeldItemYTranslation() {
      return 0.075F;
   }
}
