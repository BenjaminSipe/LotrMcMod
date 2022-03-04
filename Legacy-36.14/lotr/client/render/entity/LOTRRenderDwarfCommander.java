package lotr.client.render.entity;

import lotr.client.model.LOTRModelDwarf;
import lotr.common.entity.npc.LOTREntityBlueDwarf;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderDwarfCommander extends LOTRRenderDwarf {
   private static ResourceLocation cloak = new ResourceLocation("lotr:mob/dwarf/commander_cloak.png");
   private static ResourceLocation blueCloak = new ResourceLocation("lotr:mob/dwarf/blueMountains_commander_cloak.png");
   private LOTRModelDwarf cloakModel = new LOTRModelDwarf(1.5F);

   protected ResourceLocation getCloakTexture(EntityLivingBase entity) {
      return entity instanceof LOTREntityBlueDwarf ? blueCloak : cloak;
   }

   public int func_77032_a(EntityLiving entity, int pass, float f) {
      if (pass == 0) {
         this.func_110776_a(this.getCloakTexture(entity));
         this.cloakModel.field_78116_c.field_78806_j = false;
         this.cloakModel.field_78114_d.field_78806_j = false;
         this.cloakModel.field_78115_e.field_78806_j = true;
         this.cloakModel.field_78112_f.field_78806_j = true;
         this.cloakModel.field_78113_g.field_78806_j = true;
         this.cloakModel.field_78123_h.field_78806_j = false;
         this.cloakModel.field_78124_i.field_78806_j = false;
         this.func_77042_a(this.cloakModel);
         this.cloakModel.field_78095_p = this.field_77045_g.field_78095_p;
         this.cloakModel.field_78093_q = this.field_77045_g.field_78093_q;
         this.cloakModel.field_78091_s = this.field_77045_g.field_78091_s;
         this.cloakModel.field_78120_m = this.field_77071_a.field_78120_m;
         return 1;
      } else {
         return super.func_77032_a(entity, pass, f);
      }
   }
}
