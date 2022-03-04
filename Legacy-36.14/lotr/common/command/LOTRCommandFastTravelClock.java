package lotr.common.command;

import java.util.List;
import lotr.common.LOTRLevelData;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

public class LOTRCommandFastTravelClock extends CommandBase {
   public String func_71517_b() {
      return "fastTravelClock";
   }

   public int func_82362_a() {
      return 2;
   }

   public String func_71518_a(ICommandSender sender) {
      return "commands.lotr.fastTravelClock.usage";
   }

   public void func_71515_b(ICommandSender sender, String[] args) {
      if (args.length >= 1) {
         String argSeconds = args[0];
         int seconds;
         if (argSeconds.equals("max")) {
            seconds = 1000000;
         } else {
            seconds = func_71528_a(sender, args[0], 0);
         }

         EntityPlayerMP entityplayer;
         if (args.length >= 2) {
            entityplayer = func_82359_c(sender, args[1]);
         } else {
            entityplayer = func_71521_c(sender);
            if (entityplayer == null) {
               throw new PlayerNotFoundException();
            }
         }

         int ticks = seconds * 20;
         LOTRLevelData.getData((EntityPlayer)entityplayer).setTimeSinceFTWithUpdate(ticks);
         func_152373_a(sender, this, "commands.lotr.fastTravelClock.set", new Object[]{entityplayer.func_70005_c_(), seconds, LOTRLevelData.getHMSTime_Seconds(seconds)});
      } else {
         throw new WrongUsageException(this.func_71518_a(sender), new Object[0]);
      }
   }

   public List func_71516_a(ICommandSender sender, String[] args) {
      if (args.length == 1) {
         return func_71530_a(args, new String[]{"0", "max"});
      } else {
         return args.length == 2 ? func_71530_a(args, MinecraftServer.func_71276_C().func_71213_z()) : null;
      }
   }

   public boolean func_82358_a(String[] args, int i) {
      return i == 1;
   }
}
