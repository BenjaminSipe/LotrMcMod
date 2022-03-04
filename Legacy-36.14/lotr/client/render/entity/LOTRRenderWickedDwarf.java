package lotr.client.render.entity;

import lotr.common.entity.npc.LOTREntityDwarf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderWickedDwarf extends LOTRRenderDwarf {
   private static LOTRRandomSkins wickedSkinsMale;
   private static final ResourceLocation apronTexture = new ResourceLocation("lotr:mob/dwarf/wicked_apron.png");

   public LOTRRenderWickedDwarf() {
      wickedSkinsMale = LOTRRandomSkins.loadSkinsList("lotr:mob/dwarf/wicked_male");
   }

   public ResourceLocation func_110775_a(Entity entity) {
      LOTREntityDwarf dwarf = (LOTREntityDwarf)entity;
      return wickedSkinsMale.getRandomSkin(dwarf);
   }

   public int func_77032_a(EntityLiving entity, int pass, float f) {
      LOTREntityDwarf dwarf = (LOTREntityDwarf)entity;
      if (pass == 1 && dwarf.func_71124_b(3) == null) {
         this.func_77042_a(this.standardRenderPassModel);
         this.func_110776_a(apronTexture);
         return 1;
      } else {
         return super.func_77032_a(entity, pass, f);
      }
   }
}
