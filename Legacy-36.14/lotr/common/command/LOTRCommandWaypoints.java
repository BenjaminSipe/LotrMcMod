package lotr.common.command;

import java.util.ArrayList;
import java.util.List;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRPlayerData;
import lotr.common.world.map.LOTRWaypoint;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

public class LOTRCommandWaypoints extends CommandBase {
   public String func_71517_b() {
      return "lotrWaypoints";
   }

   public int func_82362_a() {
      return 2;
   }

   public String func_71518_a(ICommandSender sender) {
      return "commands.lotr.lotrWaypoints.usage";
   }

   public void func_71515_b(ICommandSender sender, String[] args) {
      if (args.length >= 2) {
         String regionName = args[1];
         EntityPlayerMP entityplayer;
         if (args.length >= 3) {
            entityplayer = func_82359_c(sender, args[2]);
         } else {
            entityplayer = func_71521_c(sender);
         }

         LOTRPlayerData playerData = LOTRLevelData.getData((EntityPlayer)entityplayer);
         LOTRWaypoint.Region[] var6;
         int var7;
         int var8;
         LOTRWaypoint.Region region;
         LOTRWaypoint.Region region;
         if (args[0].equalsIgnoreCase("unlock")) {
            if (!regionName.equalsIgnoreCase("all")) {
               region = this.findRegionByName(regionName);
               if (playerData.isFTRegionUnlocked(region)) {
                  throw new WrongUsageException("commands.lotr.lotrWaypoints.unlock.fail", new Object[]{entityplayer.func_70005_c_(), region.name()});
               }

               playerData.unlockFTRegion(region);
               func_152373_a(sender, this, "commands.lotr.lotrWaypoints.unlock", new Object[]{entityplayer.func_70005_c_(), region.name()});
               return;
            }

            var6 = LOTRWaypoint.Region.values();
            var7 = var6.length;

            for(var8 = 0; var8 < var7; ++var8) {
               region = var6[var8];
               playerData.unlockFTRegion(region);
            }

            func_152373_a(sender, this, "commands.lotr.lotrWaypoints.unlockAll", new Object[]{entityplayer.func_70005_c_()});
            return;
         }

         if (args[0].equalsIgnoreCase("lock")) {
            if (!regionName.equalsIgnoreCase("all")) {
               region = this.findRegionByName(regionName);
               if (!playerData.isFTRegionUnlocked(region)) {
                  throw new WrongUsageException("commands.lotr.lotrWaypoints.lock.fail", new Object[]{entityplayer.func_70005_c_(), region.name()});
               }

               playerData.lockFTRegion(region);
               func_152373_a(sender, this, "commands.lotr.lotrWaypoints.lock", new Object[]{entityplayer.func_70005_c_(), region.name()});
               return;
            }

            var6 = LOTRWaypoint.Region.values();
            var7 = var6.length;

            for(var8 = 0; var8 < var7; ++var8) {
               region = var6[var8];
               playerData.lockFTRegion(region);
            }

            func_152373_a(sender, this, "commands.lotr.lotrWaypoints.lockAll", new Object[]{entityplayer.func_70005_c_()});
            return;
         }
      }

      throw new WrongUsageException(this.func_71518_a(sender), new Object[0]);
   }

   private LOTRWaypoint.Region findRegionByName(String name) {
      LOTRWaypoint.Region region = LOTRWaypoint.regionForName(name);
      if (region == null) {
         throw new CommandException("commands.lotr.lotrWaypoints.unknown", new Object[]{name});
      } else {
         return region;
      }
   }

   public List func_71516_a(ICommandSender sender, String[] args) {
      if (args.length == 1) {
         return func_71530_a(args, new String[]{"unlock", "lock"});
      } else if (args.length != 2) {
         return args.length == 3 ? func_71530_a(args, MinecraftServer.func_71276_C().func_71213_z()) : null;
      } else {
         List names = new ArrayList();
         LOTRWaypoint.Region[] var4 = LOTRWaypoint.Region.values();
         int var5 = var4.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            LOTRWaypoint.Region r = var4[var6];
            names.add(r.name());
         }

         names.add("all");
         return func_71530_a(args, (String[])names.toArray(new String[0]));
      }
   }

   public boolean func_82358_a(String[] args, int i) {
      return i == 2;
   }
}
