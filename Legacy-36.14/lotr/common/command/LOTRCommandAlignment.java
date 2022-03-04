package lotr.common.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import lotr.common.LOTRLevelData;
import lotr.common.fac.LOTRFaction;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

public class LOTRCommandAlignment extends CommandBase {
   public String func_71517_b() {
      return "alignment";
   }

   public int func_82362_a() {
      return 2;
   }

   public String func_71518_a(ICommandSender sender) {
      return "commands.lotr.alignment.usage";
   }

   public void func_71515_b(ICommandSender sender, String[] args) {
      if (args.length >= 2) {
         List factions = new ArrayList();
         if (args[1].equalsIgnoreCase("all")) {
            factions = LOTRFaction.getPlayableAlignmentFactions();
         } else {
            LOTRFaction faction = LOTRFaction.forName(args[1]);
            if (faction == null) {
               throw new WrongUsageException("commands.lotr.alignment.noFaction", new Object[]{args[1]});
            }

            ((List)factions).add(faction);
         }

         EntityPlayerMP entityplayer;
         float alignment;
         if (args[0].equals("set")) {
            alignment = (float)func_110661_a(sender, args[2], -10000.0D, 10000.0D);
            if (args.length >= 4) {
               entityplayer = func_82359_c(sender, args[3]);
            } else {
               entityplayer = func_71521_c(sender);
               if (entityplayer == null) {
                  throw new PlayerNotFoundException();
               }
            }

            Iterator var11 = ((List)factions).iterator();

            while(var11.hasNext()) {
               LOTRFaction f = (LOTRFaction)var11.next();
               LOTRLevelData.getData((EntityPlayer)entityplayer).setAlignmentFromCommand(f, alignment);
               func_152373_a(sender, this, "commands.lotr.alignment.set", new Object[]{entityplayer.func_70005_c_(), f.factionName(), alignment});
            }

            return;
         }

         if (args[0].equals("add")) {
            alignment = (float)func_82363_b(sender, args[2]);
            if (args.length >= 4) {
               entityplayer = func_82359_c(sender, args[3]);
            } else {
               entityplayer = func_71521_c(sender);
               if (entityplayer == null) {
                  throw new PlayerNotFoundException();
               }
            }

            Map newAlignments = new HashMap();
            Iterator var7 = ((List)factions).iterator();

            LOTRFaction f;
            float newAlignment;
            while(var7.hasNext()) {
               f = (LOTRFaction)var7.next();
               newAlignment = LOTRLevelData.getData((EntityPlayer)entityplayer).getAlignment(f) + alignment;
               if (newAlignment < -10000.0F) {
                  throw new WrongUsageException("commands.lotr.alignment.tooLow", new Object[]{-10000.0F});
               }

               if (newAlignment > 10000.0F) {
                  throw new WrongUsageException("commands.lotr.alignment.tooHigh", new Object[]{10000.0F});
               }

               newAlignments.put(f, newAlignment);
            }

            var7 = ((List)factions).iterator();

            while(var7.hasNext()) {
               f = (LOTRFaction)var7.next();
               newAlignment = (Float)newAlignments.get(f);
               LOTRLevelData.getData((EntityPlayer)entityplayer).addAlignmentFromCommand(f, alignment);
               func_152373_a(sender, this, "commands.lotr.alignment.add", new Object[]{alignment, entityplayer.func_70005_c_(), f.factionName()});
            }

            return;
         }
      }

      throw new WrongUsageException(this.func_71518_a(sender), new Object[0]);
   }

   public List func_71516_a(ICommandSender sender, String[] args) {
      if (args.length == 1) {
         return func_71530_a(args, new String[]{"set", "add"});
      } else if (args.length == 2) {
         List list = LOTRFaction.getPlayableAlignmentFactionNames();
         list.add("all");
         return func_71530_a(args, (String[])list.toArray(new String[0]));
      } else {
         return args.length == 4 ? func_71530_a(args, MinecraftServer.func_71276_C().func_71213_z()) : null;
      }
   }

   public boolean func_82358_a(String[] args, int i) {
      return i == 3;
   }
}
