package lotr.common.util;

import net.minecraft.world.GameRules;
import net.minecraft.world.World;

public class GameRuleUtil {
   public static boolean canDropLoot(World world) {
      return world.func_82736_K().func_223586_b(GameRules.field_223602_e);
   }
}
