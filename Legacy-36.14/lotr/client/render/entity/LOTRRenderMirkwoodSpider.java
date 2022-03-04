package lotr.client.render.entity;

import lotr.common.entity.npc.LOTREntityMirkwoodSpider;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderMirkwoodSpider extends LOTRRenderSpiderBase {
   private static ResourceLocation[] spiderSkins = new ResourceLocation[]{new ResourceLocation("lotr:mob/spider/spider_mirkwood.png"), new ResourceLocation("lotr:mob/spider/spider_mirkwoodSlowness.png"), new ResourceLocation("lotr:mob/spider/spider_mirkwoodPoison.png")};

   protected ResourceLocation func_110775_a(Entity entity) {
      LOTREntityMirkwoodSpider spider = (LOTREntityMirkwoodSpider)entity;
      return spiderSkins[spider.getSpiderType()];
   }
}
