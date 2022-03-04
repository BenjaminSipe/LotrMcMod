package lotr.common.command;

import java.util.List;
import lotr.common.fac.LOTRFaction;
import lotr.common.fac.LOTRFactionRelations;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;

public class LOTRCommandFactionRelations extends CommandBase {
   public String func_71517_b() {
      return "facRelations";
   }

   public int func_82362_a() {
      return 2;
   }

   public String func_71518_a(ICommandSender sender) {
      return "commands.lotr.facRelations.usage";
   }

   public void func_71515_b(ICommandSender sender, String[] args) {
      if (args.length >= 1) {
         String function = args[0];
         if (function.equals("set")) {
            if (args.length >= 4) {
               LOTRFaction fac1 = LOTRFaction.forName(args[1]);
               if (fac1 == null) {
                  throw new WrongUsageException("commands.lotr.alignment.noFaction", new Object[]{args[1]});
               }

               LOTRFaction fac2 = LOTRFaction.forName(args[2]);
               if (fac2 == null) {
                  throw new WrongUsageException("commands.lotr.alignment.noFaction", new Object[]{args[2]});
               }

               LOTRFactionRelations.Relation relation = LOTRFactionRelations.Relation.forName(args[3]);
               if (relation == null) {
                  throw new WrongUsageException("commands.lotr.facRelations.noRelation", new Object[]{args[3]});
               }

               try {
                  LOTRFactionRelations.overrideRelations(fac1, fac2, relation);
                  func_152373_a(sender, this, "commands.lotr.facRelations.set", new Object[]{fac1.factionName(), fac2.factionName(), relation.getDisplayName()});
                  return;
               } catch (IllegalArgumentException var8) {
                  throw new WrongUsageException("commands.lotr.facRelations.error", new Object[]{var8.getMessage()});
               }
            }
         } else if (function.equals("reset")) {
            LOTRFactionRelations.resetAllRelations();
            func_152373_a(sender, this, "commands.lotr.facRelations.reset", new Object[0]);
            return;
         }
      }

      throw new WrongUsageException(this.func_71518_a(sender), new Object[0]);
   }

   public List func_71516_a(ICommandSender sender, String[] args) {
      if (args.length == 1) {
         return func_71530_a(args, new String[]{"set", "reset"});
      } else {
         List list;
         if (args.length != 2 && args.length != 3) {
            if (args.length == 4) {
               list = LOTRFactionRelations.Relation.listRelationNames();
               return func_71530_a(args, (String[])list.toArray(new String[0]));
            } else {
               return null;
            }
         } else {
            list = LOTRFaction.getPlayableAlignmentFactionNames();
            return func_71530_a(args, (String[])list.toArray(new String[0]));
         }
      }
   }

   public boolean func_82358_a(String[] args, int i) {
      return false;
   }
}
