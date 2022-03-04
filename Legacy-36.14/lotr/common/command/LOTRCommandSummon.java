package lotr.common.command;

import lotr.common.entity.LOTREntities;
import net.minecraft.command.server.CommandSummon;

public class LOTRCommandSummon extends CommandSummon {
   public String func_71517_b() {
      return "lotr_summon";
   }

   protected String[] func_147182_d() {
      return (String[])((String[])LOTREntities.getAllEntityNames().toArray(new String[0]));
   }
}
