package lotr.common.command;

import java.util.List;
import lotr.common.LOTRLevelData;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;

public class LOTRCommandWaypointCooldown extends CommandBase {
   public static int MAX_COOLDOWN = 86400;

   public String func_71517_b() {
      return "wpCooldown";
   }

   public int func_82362_a() {
      return 2;
   }

   public String func_71518_a(ICommandSender sender) {
      return "commands.lotr.wpCooldown.usage";
   }

   public void func_71515_b(ICommandSender sender, String[] args) {
      String function = null;
      int cooldown = -1;
      if (args.length == 1) {
         function = "max";
         cooldown = func_71532_a(sender, args[0], 0, MAX_COOLDOWN);
      } else if (args.length >= 2) {
         function = args[0];
         cooldown = func_71532_a(sender, args[1], 0, MAX_COOLDOWN);
      }

      if (function != null && cooldown >= 0) {
         int max = LOTRLevelData.getWaypointCooldownMax();
         int min = LOTRLevelData.getWaypointCooldownMin();
         boolean updatedMax;
         if (function.equals("max")) {
            updatedMax = false;
            if (cooldown < min) {
               min = cooldown;
               updatedMax = true;
            }

            LOTRLevelData.setWaypointCooldown(cooldown, min);
            func_152373_a(sender, this, "commands.lotr.wpCooldown.setMax", new Object[]{cooldown, LOTRLevelData.getHMSTime_Seconds(cooldown)});
            if (updatedMax) {
               func_152373_a(sender, this, "commands.lotr.wpCooldown.updateMin", new Object[]{min});
            }

            return;
         }

         if (function.equals("min")) {
            updatedMax = false;
            if (cooldown > max) {
               max = cooldown;
               updatedMax = true;
            }

            LOTRLevelData.setWaypointCooldown(max, cooldown);
            func_152373_a(sender, this, "commands.lotr.wpCooldown.setMin", new Object[]{cooldown, LOTRLevelData.getHMSTime_Seconds(cooldown)});
            if (updatedMax) {
               func_152373_a(sender, this, "commands.lotr.wpCooldown.updateMax", new Object[]{max});
            }

            return;
         }
      }

      throw new WrongUsageException(this.func_71518_a(sender), new Object[0]);
   }

   public List func_71516_a(ICommandSender sender, String[] args) {
      return args.length == 1 ? func_71530_a(args, new String[]{"max", "min"}) : null;
   }

   public boolean func_82358_a(String[] args, int i) {
      return false;
   }
}
