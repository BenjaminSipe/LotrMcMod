package lotr.client.render;

import lotr.common.entity.npc.NPCEntity;
import net.minecraft.util.ResourceLocation;

public class GenderedRandomTextureVariants {
   private final RandomTextureVariants maleSkins;
   private final RandomTextureVariants femaleSkins;

   public GenderedRandomTextureVariants(ResourceLocation res) {
      this(res.func_110624_b(), res.func_110623_a());
   }

   public GenderedRandomTextureVariants(String namespace, String path) {
      this.maleSkins = RandomTextureVariants.loadSkinsList(namespace, path + "_male");
      this.femaleSkins = RandomTextureVariants.loadSkinsList(namespace, path + "_female");
   }

   public ResourceLocation getRandomSkin(NPCEntity npc) {
      return npc.getPersonalInfo().isMale() ? this.maleSkins.getRandomSkin(npc) : this.femaleSkins.getRandomSkin(npc);
   }
}
