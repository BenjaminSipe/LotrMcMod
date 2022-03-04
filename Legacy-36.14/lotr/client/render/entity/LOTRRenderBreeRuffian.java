package lotr.client.render.entity;

import lotr.common.entity.npc.LOTREntityBreeRuffian;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderBreeRuffian extends LOTRRenderBreeMan {
   private static LOTRRandomSkins skinsRuffian;
   private static LOTRRandomSkins hoodsRuffian;

   public LOTRRenderBreeRuffian() {
      skinsRuffian = LOTRRandomSkins.loadSkinsList("lotr:mob/bree/ruffian");
      hoodsRuffian = LOTRRandomSkins.loadSkinsList("lotr:mob/bree/ruffian_hood");
   }

   public ResourceLocation func_110775_a(Entity entity) {
      LOTREntityBreeRuffian ruffian = (LOTREntityBreeRuffian)entity;
      return skinsRuffian.getRandomSkin(ruffian);
   }

   public int func_77032_a(EntityLiving entity, int pass, float f) {
      LOTREntityBreeRuffian ruffian = (LOTREntityBreeRuffian)entity;
      if (pass == 0 && ruffian.func_71124_b(4) == null && LOTRRandomSkins.nextInt(ruffian, 3) == 0) {
         this.func_77042_a(this.outfitModel);
         this.func_110776_a(hoodsRuffian.getRandomSkin(ruffian));
         return 1;
      } else {
         return super.func_77032_a(ruffian, pass, f);
      }
   }
}
