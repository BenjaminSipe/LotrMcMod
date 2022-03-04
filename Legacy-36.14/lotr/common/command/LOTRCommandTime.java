package lotr.common.command;

import java.util.List;
import lotr.common.LOTRTime;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;

public class LOTRCommandTime extends CommandBase {
   public String func_71517_b() {
      return "lotr_time";
   }

   public int func_82362_a() {
      return 2;
   }

   public String func_71518_a(ICommandSender sender) {
      return "commands.lotr.time.usage";
   }

   public void func_71515_b(ICommandSender sender, String[] args) {
      if (args.length >= 2) {
         if (args[0].equals("set")) {
            long time = 0L;
            if (args[1].equals("day")) {
               time = Math.round((double)LOTRTime.DAY_LENGTH * 0.03D);
            } else if (args[1].equals("night")) {
               time = Math.round((double)LOTRTime.DAY_LENGTH * 0.6D);
            } else {
               time = (long)func_71528_a(sender, args[1], 0);
            }

            LOTRTime.setWorldTime(time);
            func_152373_a(sender, this, "commands.lotr.time.set", new Object[]{time});
            return;
         }

         if (args[0].equals("add")) {
            int time = func_71528_a(sender, args[1], 0);
            LOTRTime.addWorldTime((long)time);
            func_152373_a(sender, this, "commands.lotr.time.add", new Object[]{time});
            return;
         }
      }

      throw new WrongUsageException(this.func_71518_a(sender), new Object[0]);
   }

   public List func_71516_a(ICommandSender sender, String[] args) {
      if (args.length == 1) {
         return func_71530_a(args, new String[]{"set", "add"});
      } else {
         return args.length == 2 && args[0].equals("set") ? func_71530_a(args, new String[]{"day", "night"}) : null;
      }
   }
}
