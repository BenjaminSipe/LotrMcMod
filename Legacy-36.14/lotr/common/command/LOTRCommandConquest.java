package lotr.common.command;

import java.util.List;
import lotr.common.LOTRLevelData;
import lotr.common.fac.LOTRFaction;
import lotr.common.world.map.LOTRConquestGrid;
import lotr.common.world.map.LOTRConquestZone;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.World;

public class LOTRCommandConquest extends CommandBase {
   public String func_71517_b() {
      return "conquest";
   }

   public int func_82362_a() {
      return 2;
   }

   public String func_71518_a(ICommandSender sender) {
      return "commands.lotr.conquest.usage";
   }

   public void func_71515_b(ICommandSender sender, String[] args) {
      World world = sender.func_130014_f_();
      if (!LOTRConquestGrid.conquestEnabled(world)) {
         throw new WrongUsageException("commands.lotr.conquest.notEnabled", new Object[0]);
      } else {
         if (args.length >= 1) {
            String function = args[0];
            if (function.equals("clear")) {
               Object[] obj = this.parseCoordsAndZone(sender, args, 1);
               int posX = (Integer)obj[0];
               int posZ = (Integer)obj[1];
               LOTRConquestZone zone = (LOTRConquestZone)obj[2];
               zone.clearAllFactions(world);
               func_152373_a(sender, this, "commands.lotr.conquest.clear", new Object[]{posX, posZ});
               return;
            }

            if (function.equals("rate")) {
               if (args.length >= 2) {
                  double rate = func_110661_a(sender, args[1], 0.0D, 100.0D);
                  LOTRLevelData.setConquestRate((float)rate);
                  func_152373_a(sender, this, "commands.lotr.conquest.rateSet", new Object[]{rate});
                  return;
               }

               float currentRate = LOTRLevelData.getConquestRate();
               sender.func_145747_a(new ChatComponentTranslation("commands.lotr.conquest.rateGet", new Object[]{currentRate}));
               return;
            }

            if (args.length >= 3 && (function.equals("set") || function.equals("add") || function.equals("radial"))) {
               LOTRFaction fac = LOTRFaction.forName(args[1]);
               if (fac == null) {
                  throw new WrongUsageException("commands.lotr.conquest.noFaction", new Object[]{args[1]});
               }

               float amount = (float)func_82363_b(sender, args[2]);
               Object[] obj = this.parseCoordsAndZone(sender, args, 3);
               int posX = (Integer)obj[0];
               int posZ = (Integer)obj[1];
               LOTRConquestZone zone = (LOTRConquestZone)obj[2];
               if (function.equals("set")) {
                  if (amount < 0.0F) {
                     throw new WrongUsageException("commands.lotr.conquest.tooLow", new Object[]{0.0F});
                  }

                  if (amount > 100000.0F) {
                     throw new WrongUsageException("commands.lotr.conquest.tooHigh", new Object[]{100000.0F});
                  }

                  zone.setConquestStrength(fac, amount, world);
                  func_152373_a(sender, this, "commands.lotr.conquest.set", new Object[]{fac.factionName(), amount, posX, posZ});
                  return;
               }

               float centralStr;
               if (function.equals("add")) {
                  centralStr = zone.getConquestStrength(fac, world);
                  float newStr = centralStr + amount;
                  if (newStr < 0.0F) {
                     throw new WrongUsageException("commands.lotr.conquest.tooLow", new Object[]{0.0F});
                  }

                  if (newStr > 100000.0F) {
                     throw new WrongUsageException("commands.lotr.conquest.tooHigh", new Object[]{100000.0F});
                  }

                  zone.addConquestStrength(fac, amount, world);
                  func_152373_a(sender, this, "commands.lotr.conquest.add", new Object[]{fac.factionName(), amount, posX, posZ});
                  return;
               }

               if (function.equals("radial")) {
                  centralStr = zone.getConquestStrength(fac, world);
                  if (centralStr + amount > 100000.0F) {
                     throw new WrongUsageException("commands.lotr.conquest.tooHigh", new Object[]{100000.0F});
                  }

                  EntityPlayerMP senderIfPlayer = sender instanceof EntityPlayerMP ? (EntityPlayerMP)sender : null;
                  if (amount < 0.0F) {
                     LOTRConquestGrid.doRadialConquest(world, zone, senderIfPlayer, (LOTRFaction)null, fac, -amount, -amount);
                  } else {
                     LOTRConquestGrid.doRadialConquest(world, zone, senderIfPlayer, fac, (LOTRFaction)null, amount, amount);
                  }

                  func_152373_a(sender, this, "commands.lotr.conquest.radial", new Object[]{fac.factionName(), amount, posX, posZ});
                  return;
               }
            }
         }

         throw new WrongUsageException(this.func_71518_a(sender), new Object[0]);
      }
   }

   private Object[] parseCoordsAndZone(ICommandSender sender, String[] args, int specifyIndex) {
      int posX = sender.func_82114_b().field_71574_a;
      int posZ = sender.func_82114_b().field_71573_c;
      if (args.length >= specifyIndex + 2) {
         posX = func_71526_a(sender, args[specifyIndex]);
         posZ = func_71526_a(sender, args[specifyIndex + 1]);
      }

      LOTRConquestZone zone = LOTRConquestGrid.getZoneByWorldCoords(posX, posZ);
      if (zone.isDummyZone) {
         throw new WrongUsageException("commands.lotr.conquest.outOfBounds", new Object[]{posX, posZ});
      } else {
         return new Object[]{posX, posZ, zone};
      }
   }

   public List func_71516_a(ICommandSender sender, String[] args) {
      if (args.length == 1) {
         return func_71530_a(args, new String[]{"set", "add", "radial", "clear", "rate"});
      } else if (args.length != 2 || !args[0].equals("set") && !args[0].equals("add") && !args[0].equals("radial")) {
         return null;
      } else {
         List list = LOTRFaction.getPlayableAlignmentFactionNames();
         return func_71530_a(args, (String[])list.toArray(new String[0]));
      }
   }

   public boolean func_82358_a(String[] args, int i) {
      return false;
   }
}
