package lotr.common.command;

import java.util.List;
import lotr.common.LOTRDate;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;

public class LOTRCommandDate extends CommandBase {
   public String func_71517_b() {
      return "lotrDate";
   }

   public int func_82362_a() {
      return 2;
   }

   public String func_71518_a(ICommandSender sender) {
      return "commands.lotr.lotrDate.usage";
   }

   public void func_71515_b(ICommandSender sender, String[] args) {
      int newDate;
      String dateName;
      if (args.length >= 1 && args[0].equals("get")) {
         newDate = LOTRDate.ShireReckoning.currentDay;
         dateName = LOTRDate.ShireReckoning.getShireDate().getDateName(false);
         IChatComponent message = new ChatComponentTranslation("commands.lotr.lotrDate.get", new Object[]{newDate, dateName});
         sender.func_145747_a(message);
      } else if (args.length >= 2) {
         newDate = LOTRDate.ShireReckoning.currentDay;
         if (args[0].equals("set")) {
            newDate = func_71526_a(sender, args[1]);
         } else if (args[0].equals("add")) {
            int date = func_71526_a(sender, args[1]);
            newDate += date;
         }

         if (Math.abs(newDate) > 1000000) {
            throw new WrongUsageException("commands.lotr.lotrDate.outOfBounds", new Object[0]);
         } else {
            LOTRDate.setDate(newDate);
            dateName = LOTRDate.ShireReckoning.getShireDate().getDateName(false);
            func_152373_a(sender, this, "commands.lotr.lotrDate.set", new Object[]{newDate, dateName});
         }
      } else {
         throw new WrongUsageException(this.func_71518_a(sender), new Object[0]);
      }
   }

   public List func_71516_a(ICommandSender sender, String[] args) {
      return args.length == 1 ? func_71530_a(args, new String[]{"get", "set", "add"}) : null;
   }

   public boolean func_82358_a(String[] args, int i) {
      return false;
   }
}
