package lotr.common.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRPlayerData;
import lotr.common.fellowship.LOTRFellowship;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

public class LOTRCommandFellowshipMessage extends CommandBase {
   public String func_71517_b() {
      return "fmsg";
   }

   public List func_71514_a() {
      return Arrays.asList("fchat");
   }

   public int func_82362_a() {
      return 0;
   }

   public boolean func_71519_b(ICommandSender sender) {
      return sender instanceof EntityPlayer ? true : super.func_71519_b(sender);
   }

   public String func_71518_a(ICommandSender sender) {
      return "commands.lotr.fmsg.usage";
   }

   public void func_71515_b(ICommandSender sender, String[] args) {
      EntityPlayerMP entityplayer = func_71521_c(sender);
      LOTRPlayerData playerData = LOTRLevelData.getData((EntityPlayer)entityplayer);
      if (args.length >= 1) {
         if (args[0].equals("bind") && args.length >= 2) {
            args = LOTRCommandFellowship.fixArgsForFellowship(args, 1, false);
            String fsName = args[1];
            LOTRFellowship fellowship = playerData.getFellowshipByName(fsName);
            if (fellowship != null && !fellowship.isDisbanded() && fellowship.containsPlayer(entityplayer.func_110124_au())) {
               playerData.setChatBoundFellowship(fellowship);
               IChatComponent notif = new ChatComponentTranslation("commands.lotr.fmsg.bind", new Object[]{fellowship.getName()});
               notif.func_150256_b().func_150238_a(EnumChatFormatting.GRAY);
               notif.func_150256_b().func_150217_b(true);
               sender.func_145747_a(notif);
               return;
            }

            throw new WrongUsageException("commands.lotr.fmsg.notFound", new Object[]{fsName});
         }

         LOTRFellowship fellowship;
         if (args[0].equals("unbind")) {
            fellowship = playerData.getChatBoundFellowship();
            playerData.setChatBoundFellowshipID((UUID)null);
            if (fellowship != null && fellowship.containsPlayer(entityplayer.func_110124_au())) {
            }

            IChatComponent notif = new ChatComponentTranslation("commands.lotr.fmsg.unbind", new Object[]{fellowship.getName()});
            notif.func_150256_b().func_150238_a(EnumChatFormatting.GRAY);
            notif.func_150256_b().func_150217_b(true);
            sender.func_145747_a(notif);
            return;
         }

         fellowship = null;
         int msgStartIndex = 0;
         if (args[0].startsWith("\"")) {
            args = LOTRCommandFellowship.fixArgsForFellowship(args, 0, false);
            String fsName = args[0];
            fellowship = playerData.getFellowshipByName(fsName);
            if (fellowship == null) {
               throw new WrongUsageException("commands.lotr.fmsg.notFound", new Object[]{fsName});
            }

            msgStartIndex = 1;
         }

         if (fellowship == null) {
            fellowship = playerData.getChatBoundFellowship();
            if (fellowship == null) {
               throw new WrongUsageException("commands.lotr.fmsg.boundNone", new Object[0]);
            }

            if (fellowship.isDisbanded() || !fellowship.containsPlayer(entityplayer.func_110124_au())) {
               throw new WrongUsageException("commands.lotr.fmsg.boundNotMember", new Object[]{fellowship.getName()});
            }
         }

         if (fellowship != null) {
            IChatComponent message = func_147176_a(sender, args, msgStartIndex, false);
            fellowship.sendFellowshipMessage(entityplayer, message.func_150260_c());
            return;
         }
      }

      throw new WrongUsageException(this.func_71518_a(sender), new Object[0]);
   }

   public List func_71516_a(ICommandSender sender, String[] args) {
      LOTRPlayerData playerData = LOTRLevelData.getData((EntityPlayer)func_71521_c(sender));
      String[] argsOriginal = (String[])Arrays.copyOf(args, args.length);
      if (args.length >= 2 && args[0].equals("bind")) {
         args = LOTRCommandFellowship.fixArgsForFellowship(args, 1, true);
         return LOTRCommandFellowship.listFellowshipsMatchingLastWord(args, argsOriginal, 1, playerData, false);
      } else if (args.length >= 1) {
         args = LOTRCommandFellowship.fixArgsForFellowship(args, 0, true);
         List matches = new ArrayList();
         if (args.length == 1 && !argsOriginal[0].startsWith("\"")) {
            matches.addAll(func_71530_a(args, new String[]{"bind", "unbind"}));
         }

         matches.addAll(LOTRCommandFellowship.listFellowshipsMatchingLastWord(args, argsOriginal, 0, playerData, false));
         return matches;
      } else {
         return null;
      }
   }

   public boolean func_82358_a(String[] args, int i) {
      return false;
   }
}
