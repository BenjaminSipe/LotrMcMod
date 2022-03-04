package lotr.client.render.entity;

import lotr.client.model.LOTRModelElf;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderElvenSmith extends LOTRRenderElf {
   private ResourceLocation outfitTexture;
   private ResourceLocation capeTexture;
   private ModelBiped outfitModel = new LOTRModelElf(0.5F);

   public LOTRRenderElvenSmith(String s, String s1) {
      this.outfitTexture = new ResourceLocation("lotr:mob/elf/" + s + ".png");
      this.capeTexture = new ResourceLocation("lotr:mob/elf/" + s1 + ".png");
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
