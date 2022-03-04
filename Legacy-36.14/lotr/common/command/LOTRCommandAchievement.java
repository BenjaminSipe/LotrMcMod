package lotr.common.command;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRPlayerData;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

public class LOTRCommandAchievement extends CommandBase {
   public String func_71517_b() {
      return "lotrAchievement";
   }

   public int func_82362_a() {
      return 2;
   }

   public String func_71518_a(ICommandSender sender) {
      return "commands.lotr.lotrAchievement.usage";
   }

   public void func_71515_b(ICommandSender sender, String[] args) {
      if (args.length >= 2) {
         String achName = args[1];
         EntityPlayerMP entityplayer;
         if (args.length >= 3) {
            entityplayer = func_82359_c(sender, args[2]);
         } else {
            entityplayer = func_71521_c(sender);
         }

         LOTRPlayerData playerData = LOTRLevelData.getData((EntityPlayer)entityplayer);
         LOTRAchievement ach;
         if (args[0].equalsIgnoreCase("give")) {
            ach = this.findAchievementByName(achName);
            if (playerData.hasAchievement(ach)) {
               throw new WrongUsageException("commands.lotr.lotrAchievement.give.fail", new Object[]{entityplayer.func_70005_c_(), ach.getTitle(entityplayer)});
            }

            playerData.addAchievement(ach);
            func_152373_a(sender, this, "commands.lotr.lotrAchievement.give", new Object[]{entityplayer.func_70005_c_(), ach.getTitle(entityplayer)});
            return;
         }

         if (args[0].equalsIgnoreCase("remove")) {
            if (!achName.equalsIgnoreCase("all")) {
               ach = this.findAchievementByName(achName);
               if (!playerData.hasAchievement(ach)) {
                  throw new WrongUsageException("commands.lotr.lotrAchievement.remove.fail", new Object[]{entityplayer.func_70005_c_(), ach.getTitle(entityplayer)});
               }

               playerData.removeAchievement(ach);
               func_152373_a(sender, this, "commands.lotr.lotrAchievement.remove", new Object[]{entityplayer.func_70005_c_(), ach.getTitle(entityplayer)});
               return;
            }

            List allAchievements = new ArrayList(playerData.getAchievements());
            Iterator var7 = allAchievements.iterator();

            while(var7.hasNext()) {
               LOTRAchievement ach = (LOTRAchievement)var7.next();
               playerData.removeAchievement(ach);
            }

            func_152373_a(sender, this, "commands.lotr.lotrAchievement.removeAll", new Object[]{entityplayer.func_70005_c_()});
            return;
         }
      }

      throw new WrongUsageException(this.func_71518_a(sender), new Object[0]);
   }

   private LOTRAchievement findAchievementByName(String name) {
      LOTRAchievement ach = LOTRAchievement.findByName(name);
      if (ach == null) {
         throw new CommandException("commands.lotr.lotrAchievement.unknown", new Object[]{name});
      } else {
         return ach;
      }
   }

   public List func_71516_a(ICommandSender sender, String[] args) {
      if (args.length == 1) {
         return func_71530_a(args, new String[]{"give", "remove"});
      } else if (args.length != 2) {
         return args.length == 3 ? func_71530_a(args, MinecraftServer.func_71276_C().func_71213_z()) : null;
      } else {
         List achievements = LOTRAchievement.getAllAchievements();
         List names = new ArrayList();
         Iterator var5 = achievements.iterator();

         while(var5.hasNext()) {
            LOTRAchievement a = (LOTRAchievement)var5.next();
            names.add(a.getCodeName());
         }

         if (args[0].equals("remove")) {
            names.add("all");
         }

         return func_71530_a(args, (String[])names.toArray(new String[0]));
      }
   }

   public boolean func_82358_a(String[] args, int i) {
      return i == 2;
   }
}
