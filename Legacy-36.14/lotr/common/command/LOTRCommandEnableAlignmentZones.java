package lotr.common.command;

import java.util.List;
import lotr.common.LOTRLevelData;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;

public class LOTRCommandEnableAlignmentZones extends CommandBase {
   public String func_71517_b() {
      return "alignmentZones";
   }

   public int func_82362_a() {
      return 2;
   }

   public String func_71518_a(ICommandSender sender) {
      return "commands.lotr.alignmentZones.usage";
   }

   public void func_71515_b(ICommandSender sender, String[] args) {
      if (args.length >= 1) {
         String flag = args[0];
         if (flag.equals("enable")) {
            LOTRLevelData.setEnableAlignmentZones(true);
            func_152373_a(sender, this, "commands.lotr.alignmentZones.enable", new Object[0]);
            return;
         }

         if (flag.equals("disable")) {
            LOTRLevelData.setEnableAlignmentZones(false);
            func_152373_a(sender, this, "commands.lotr.alignmentZones.disable", new Object[0]);
            return;
         }
      }

      throw new WrongUsageException(this.func_71518_a(sender), new Object[0]);
   }

   public List func_71516_a(ICommandSender sender, String[] args) {
      return args.length == 1 ? func_71530_a(args, new String[]{"enable", "disable"}) : null;
   }

   public boolean func_82358_a(String[] args, int i) {
      return false;
   }
}
