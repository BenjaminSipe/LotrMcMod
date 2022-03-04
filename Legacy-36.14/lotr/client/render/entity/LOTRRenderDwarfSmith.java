package lotr.client.render.entity;

import lotr.common.entity.npc.LOTREntityDwarf;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderDwarfSmith extends LOTRRenderDwarf {
   private static ResourceLocation apronTexture = new ResourceLocation("lotr:mob/dwarf/blacksmith_apron.png");

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
