package lotr.common;

import net.minecraft.event.HoverEvent.Action;
import net.minecraftforge.common.util.EnumHelper;

public class LOTRChatEvents {
   private static Class[][] hoverParams;
   public static Action SHOW_LOTR_ACHIEVEMENT;

   public static void createEvents() {
      SHOW_LOTR_ACHIEVEMENT = (Action)EnumHelper.addEnum(hoverParams, Action.class, "SHOW_LOTR_ACHIEVEMENT", new Object[]{"show_lotr_achievement", true});
      LOTRReflection.getHoverEventMappings().put(SHOW_LOTR_ACHIEVEMENT.func_150685_b(), SHOW_LOTR_ACHIEVEMENT);
   }

   static {
      hoverParams = new Class[][]{{Action.class, String.class, Boolean.TYPE}};
   }
}
