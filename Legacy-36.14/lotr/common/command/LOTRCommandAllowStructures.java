package lotr.common.command;

import java.util.ArrayList;
import java.util.List;
import lotr.common.LOTRLevelData;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentTranslation;

public class LOTRCommandAllowStructures extends CommandBase {
   public String func_71517_b() {
      return "allowStructures";
   }

   public int func_82362_a() {
      return 3;
   }

   public String func_71518_a(ICommandSender sender) {
      return "commands.lotr.allowStructures.usage";
   }

   public void func_71515_b(ICommandSender sender, String[] args) {
      if (args.length == 0) {
         if (!LOTRLevelData.structuresBanned()) {
            throw new WrongUsageException("commands.lotr.allowStructures.alreadyAllowed", new Object[0]);
         }

         LOTRLevelData.setStructuresBanned(false);
         func_152373_a(sender, this, "commands.lotr.allowStructures.allow", new Object[0]);
      } else {
         LOTRLevelData.setPlayerBannedForStructures(args[0], false);
         func_152373_a(sender, this, "commands.lotr.allowStructures.allowPlayer", new Object[]{args[0]});
         EntityPlayerMP entityplayer = func_82359_c(sender, args[0]);
         if (entityplayer != null) {
            entityplayer.func_145747_a(new ChatComponentTranslation("chat.lotr.allowStructures", new Object[0]));
         }
      }

   }

   public List func_71516_a(ICommandSender sender, String[] args) {
      if (args.length == 1) {
         List bannedNames = new ArrayList();
         bannedNames.addAll(LOTRLevelData.getBannedStructurePlayersUsernames());
         return func_71530_a(args, (String[])bannedNames.toArray(new String[0]));
      } else {
         return null;
      }
   }
}
