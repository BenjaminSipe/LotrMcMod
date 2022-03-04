package lotr.common.event;

import lotr.common.LOTRLog;
import lotr.common.config.ClientsideCurrentServerConfigSettings;
import lotr.common.config.LOTRConfig;
import lotr.common.init.LOTRDimensions;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityEvent.Size;

public class BeeAdjustments {
   public static final float DOWNSCALE = 0.35F;

   public static boolean shouldApply(Entity entity, World world) {
      if (world == null) {
         return false;
      } else {
         return isEnabledThroughConfig(world) && entity instanceof BeeEntity && LOTRDimensions.isModDimension(world);
      }
   }

   private static boolean isEnabledThroughConfig(World world) {
      return !world.field_72995_K ? (Boolean)LOTRConfig.COMMON.smallerBees.get() : ClientsideCurrentServerConfigSettings.INSTANCE.smallerBees;
   }

   public static void adjustSize(Size event) {
      event.setNewSize(event.getNewSize().func_220313_a(0.35F), true);
      BeeEntity bee = (BeeEntity)event.getEntity();
      float newWidth = event.getNewSize().field_220315_a;
      float defaultWidth = EntityType.field_226289_e_.func_220333_h();
      if ((bee.func_70631_g_() || newWidth != defaultWidth * 0.35F) && (!bee.func_70631_g_() || newWidth != defaultWidth * 0.35F * 0.5F)) {
         LOTRLog.warn("Bee size is not as expected - %s [is child %s], size %s", bee.toString(), String.valueOf(bee.func_70631_g_()), event.getNewSize());
         Thread.dumpStack();
      }

   }
}
