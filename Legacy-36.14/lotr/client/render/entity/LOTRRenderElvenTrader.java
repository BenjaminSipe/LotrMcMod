package lotr.client.render.entity;

import lotr.client.model.LOTRModelElf;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderElvenTrader extends LOTRRenderElf {
   private ResourceLocation outfitTexture;
   private ModelBiped outfitModel = new LOTRModelElf(0.5F);

   public LOTRRenderElvenTrader(String s) {
      this.func_77042_a(this.outfitModel);
      this.outfitTexture = new ResourceLocation("lotr:mob/elf/" + s + ".png");
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
