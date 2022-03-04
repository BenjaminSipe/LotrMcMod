package lotr.client.render.entity;

import java.util.HashMap;
import lotr.client.LOTRTextures;
import lotr.client.model.LOTRModelEnt;
import lotr.common.entity.npc.LOTREntityEnt;
import lotr.common.entity.npc.LOTREntityTree;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderEnt extends RenderLiving {
   private static HashMap entTextures = new HashMap();
   private LOTRModelEnt eyesModel = new LOTRModelEnt(0.05F);

   public LOTRRenderEnt() {
      super(new LOTRModelEnt(), 0.5F);
   }

   protected ResourceLocation func_110775_a(Entity entity) {
      int treeType = ((LOTREntityEnt)entity).getTreeType();
      String s = "lotr:mob/ent/" + LOTREntityTree.TYPES[treeType] + ".png";
      ResourceLocation r = (ResourceLocation)entTextures.get(treeType);
      if (r == null) {
         r = new ResourceLocation(s);
         entTextures.put(treeType, r);
      }

      return r;
   }

   protected void func_77036_a(EntityLivingBase entity, float f, float f1, float f2, float f3, float f4, float f5) {
      super.func_77036_a(entity, f, f1, f2, f3, f4, f5);
      ResourceLocation eyes = LOTRTextures.getEyesTexture(this.func_110775_a(entity), new int[][]{{15, 23}, {22, 23}}, 3, 2);
      LOTRGlowingEyes.renderGlowingEyes(entity, eyes, this.eyesModel, f, f1, f2, f3, f4, f5);
   }
}
