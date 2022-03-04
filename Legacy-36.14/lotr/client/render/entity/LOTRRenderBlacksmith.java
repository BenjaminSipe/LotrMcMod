package lotr.client.render.entity;

import lotr.client.model.LOTRModelHuman;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderBlacksmith extends LOTRRenderBiped {
   private LOTRRandomSkins skins;
   private ResourceLocation apron;
   private ModelBiped standardRenderPassModel = new LOTRModelHuman(0.5F, false);

   public LOTRRenderBlacksmith(String s, String s1) {
      super(new LOTRModelHuman(), 0.5F);
      this.skins = LOTRRandomSkins.loadSkinsList("lotr:mob/" + s);
      this.apron = new ResourceLocation("lotr:mob/" + s1 + ".png");
      this.func_77042_a(this.standardRenderPassModel);
   }

   public ResourceLocation func_110775_a(Entity entity) {
      return this.skins.getRandomSkin((LOTREntityNPC)entity);
   }

   public int func_77032_a(EntityLiving entity, int pass, float f) {
      if (pass == 1) {
         this.func_77042_a(this.standardRenderPassModel);
         this.func_110776_a(this.apron);
         return 1;
      } else {
         return super.func_77032_a(entity, pass, f);
      }
   }
}
