package lotr.common.command;

import java.util.List;
import lotr.common.LOTRLevelData;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentTranslation;

public class LOTRCommandBanStructures extends CommandBase {
   public String func_71517_b() {
      return "banStructures";
   }

   public int func_82362_a() {
      return 3;
   }

   public String func_71518_a(ICommandSender sender) {
      return "commands.lotr.banStructures.usage";
   }

   public void func_71515_b(ICommandSender sender, String[] args) {
      if (args.length == 0) {
         if (LOTRLevelData.structuresBanned()) {
            throw new WrongUsageException("commands.lotr.banStructures.alreadyBanned", new Object[0]);
         }

         LOTRLevelData.setStructuresBanned(true);
         func_152373_a(sender, this, "commands.lotr.banStructures.ban", new Object[0]);
      } else {
         LOTRLevelData.setPlayerBannedForStructures(args[0], true);
         func_152373_a(sender, this, "commands.lotr.banStructures.banPlayer", new Object[]{args[0]});
         EntityPlayerMP entityplayer = func_82359_c(sender, args[0]);
         if (entityplayer != null) {
            entityplayer.func_145747_a(new ChatComponentTranslation("chat.lotr.banStructures", new Object[0]));
         }
      }

   }

   public List func_71516_a(ICommandSender sender, String[] args) {
      return args.length == 1 ? func_71530_a(args, MinecraftServer.func_71276_C().func_71213_z()) : null;
   }
}
