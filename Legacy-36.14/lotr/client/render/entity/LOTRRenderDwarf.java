package lotr.client.render.entity;

import lotr.client.model.LOTRModelDwarf;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityBlueDwarf;
import lotr.common.entity.npc.LOTREntityDwarf;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class LOTRRenderDwarf extends LOTRRenderBiped {
   private static LOTRRandomSkins dwarfSkinsMale;
   private static LOTRRandomSkins dwarfSkinsFemale;
   private static LOTRRandomSkins blueDwarfSkinsMale;
   private static LOTRRandomSkins blueDwarfSkinsFemale;
   private static ResourceLocation ringTexture = new ResourceLocation("lotr:mob/dwarf/ring.png");
   protected ModelBiped standardRenderPassModel = new LOTRModelDwarf(0.5F, 64, 64);

   public LOTRRenderDwarf() {
      super(new LOTRModelDwarf(), 0.5F);
      this.func_77042_a(this.standardRenderPassModel);
      dwarfSkinsMale = LOTRRandomSkins.loadSkinsList("lotr:mob/dwarf/dwarf_male");
      dwarfSkinsFemale = LOTRRandomSkins.loadSkinsList("lotr:mob/dwarf/dwarf_female");
      blueDwarfSkinsMale = LOTRRandomSkins.loadSkinsList("lotr:mob/dwarf/blueMountains_male");
      blueDwarfSkinsFemale = LOTRRandomSkins.loadSkinsList("lotr:mob/dwarf/blueMountains_female");
   }

   protected void func_82421_b() {
      this.field_82423_g = new LOTRModelDwarf(1.0F);
      this.field_82425_h = new LOTRModelDwarf(0.5F);
   }

   public ResourceLocation func_110775_a(Entity entity) {
      LOTREntityDwarf dwarf = (LOTREntityDwarf)entity;
      if (dwarf instanceof LOTREntityBlueDwarf) {
         return dwarf.familyInfo.isMale() ? blueDwarfSkinsMale.getRandomSkin(dwarf) : blueDwarfSkinsFemale.getRandomSkin(dwarf);
      } else {
         return dwarf.familyInfo.isMale() ? dwarfSkinsMale.getRandomSkin(dwarf) : dwarfSkinsFemale.getRandomSkin(dwarf);
      }
   }

   public int func_77032_a(EntityLiving entity, int pass, float f) {
      LOTREntityDwarf dwarf = (LOTREntityDwarf)entity;
      if (pass == 1 && dwarf.getClass() == dwarf.familyInfo.marriageEntityClass && dwarf.func_71124_b(4) != null && dwarf.func_71124_b(4).func_77973_b() == dwarf.familyInfo.marriageRing) {
         this.func_110776_a(ringTexture);
         this.func_77042_a(this.standardRenderPassModel);
         ((ModelBiped)this.field_77046_h).field_78112_f.field_78806_j = false;
         return 1;
      } else {
         return super.func_77032_a(entity, pass, f);
      }
   }

   protected void func_77041_b(EntityLivingBase entity, float f) {
      super.func_77041_b(entity, f);
      GL11.glScalef(0.8125F, 0.8125F, 0.8125F);
      if (LOTRMod.isAprilFools()) {
         GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
      }

   }

   public float getHeldItemYTranslation() {
      return 0.125F;
   }
}
