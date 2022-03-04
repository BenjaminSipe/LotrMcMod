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

public class LOTRCommandPledgeCooldown extends CommandBase {
   public String func_71517_b() {
      return "pledgeCooldown";
   }

   public int func_82362_a() {
      return 2;
   }

   public String func_71518_a(ICommandSender sender) {
      return "commands.lotr.pledgeCooldown.usage";
   }

   public void func_71515_b(ICommandSender sender, String[] args) {
      if (args.length >= 1) {
         int cd = func_71532_a(sender, args[0], 0, 10000000);
         EntityPlayerMP entityplayer;
         if (args.length >= 2) {
            entityplayer = func_82359_c(sender, args[1]);
         } else {
            entityplayer = func_71521_c(sender);
            if (entityplayer == null) {
               throw new PlayerNotFoundException();
            }
         }

         LOTRLevelData.getData((EntityPlayer)entityplayer).setPledgeBreakCooldown(cd);
         func_152373_a(sender, this, "commands.lotr.pledgeCooldown.set", new Object[]{entityplayer.func_70005_c_(), cd, LOTRLevelData.getHMSTime_Ticks(cd)});
      } else {
         throw new WrongUsageException(this.func_71518_a(sender), new Object[0]);
      }
   }

   public List func_71516_a(ICommandSender sender, String[] args) {
      return args.length == 2 ? func_71530_a(args, MinecraftServer.func_71276_C().func_71213_z()) : null;
   }

   public boolean func_82358_a(String[] args, int i) {
      return i == 1;
   }
}
