package lotr.client.render.entity;

import java.util.HashMap;
import java.util.Map;
import lotr.client.model.LOTRModelHuman;
import lotr.common.entity.npc.LOTREntityHaradSlave;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderHaradSlave extends LOTRRenderBiped {
   private static Map slaveSkinsMale = new HashMap();
   private static Map slaveSkinsFemale = new HashMap();

   public LOTRRenderHaradSlave() {
      super(new LOTRModelHuman(), 0.5F);
   }

   public ResourceLocation func_110775_a(Entity entity) {
      LOTREntityHaradSlave slave = (LOTREntityHaradSlave)entity;
      boolean isMale = slave.familyInfo.isMale();
      LOTREntityHaradSlave.SlaveType type = slave.getSlaveType();
      String skinDir = "lotr:mob/nearHarad/slave/" + type.skinDir + "_" + (isMale ? "male" : "female");
      LOTRRandomSkins skins = LOTRRandomSkins.loadSkinsList(skinDir);
      return skins.getRandomSkin(slave);
   }
}
