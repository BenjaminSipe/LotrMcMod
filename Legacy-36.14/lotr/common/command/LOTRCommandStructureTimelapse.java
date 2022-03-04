package lotr.common.command;

import java.util.List;
import lotr.common.LOTRConfig;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;

public class LOTRCommandStructureTimelapse extends CommandBase {
   public String func_71517_b() {
      return "strTimelapse";
   }

   public int func_82362_a() {
      return 2;
   }

   public String func_71518_a(ICommandSender sender) {
      return "commands.lotr.strTimelapse.usage";
   }

   public void func_71515_b(ICommandSender sender, String[] args) {
      if (args.length == 1) {
         if (args[0].equals("on")) {
            LOTRConfig.setStructureTimelapse(true);
            func_152373_a(sender, this, "commands.lotr.strTimelapse.on", new Object[0]);
            func_152373_a(sender, this, "commands.lotr.strTimelapse.warn", new Object[0]);
         } else if (args[0].equals("off")) {
            LOTRConfig.setStructureTimelapse(false);
            func_152373_a(sender, this, "commands.lotr.strTimelapse.off", new Object[0]);
         } else {
            int interval = func_71528_a(sender, args[0], 0);
            LOTRConfig.setStructureTimelapseInterval(interval);
            func_152373_a(sender, this, "commands.lotr.strTimelapse.interval", new Object[]{interval});
         }
      } else {
         throw new WrongUsageException(this.func_71518_a(sender), new Object[0]);
      }
   }

   public List func_71516_a(ICommandSender sender, String[] args) {
      return args.length == 1 ? func_71530_a(args, new String[]{"on", "off"}) : null;
   }
}
