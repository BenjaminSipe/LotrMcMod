package lotr.client.render.entity;

import lotr.client.model.LOTRModelElf;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderDorwinionElfVintner extends LOTRRenderElf {
   private ResourceLocation outfitTexture = new ResourceLocation("lotr:mob/elf/dorwinionVintner_cloak.png");
   private ResourceLocation capeTexture = new ResourceLocation("lotr:mob/elf/dorwinionVintner_cape.png");
   private ModelBiped outfitModel = new LOTRModelElf(0.5F);

   public LOTRRenderDorwinionElfVintner() {
      this.func_77042_a(this.outfitModel);
   }

   public void func_76986_a(EntityLiving entity, double d, double d1, double d2, float f, float f1) {
      ((LOTREntityNPC)entity).npcCape = this.capeTexture;
      super.func_76986_a(entity, d, d1, d2, f, f1);
   }

   public int func_77032_a(EntityLiving entity, int pass, float f) {
      if (pass == 0) {
         this.func_77042_a(this.outfitModel);
         this.func_110776_a(this.outfitTexture);
         return 1;
      } else {
         return super.func_77032_a(entity, pass, f);
      }
   }
}
