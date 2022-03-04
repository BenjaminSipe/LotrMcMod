package lotr.common.command;

import lotr.common.LOTRConfig;
import net.minecraft.command.server.CommandMessage;

public class LOTRCommandMessageFixed extends CommandMessage {
   public boolean func_82358_a(String[] args, int i) {
      return LOTRConfig.preventMessageExploit ? false : super.func_82358_a(args, i);
   }
}
