package lotr.common.command;

import java.util.List;
import lotr.common.entity.LOTREntityInvasionSpawner;
import lotr.common.world.spawning.LOTRInvasions;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class LOTRCommandInvasion extends CommandBase {
   public String func_71517_b() {
      return "invasion";
   }

   public int func_82362_a() {
      return 2;
   }

   public String func_71518_a(ICommandSender sender) {
      return "commands.lotr.invasion.usage";
   }

   public void func_71515_b(ICommandSender sender, String[] args) {
      EntityPlayer player = sender instanceof EntityPlayer ? (EntityPlayer)sender : null;
      World world = sender.func_130014_f_();
      if (args.length >= 1) {
         String typeName = args[0];
         LOTRInvasions type = LOTRInvasions.forName(typeName);
         if (type != null) {
            double posX = (double)sender.func_82114_b().field_71574_a + 0.5D;
            double posY = (double)sender.func_82114_b().field_71572_b;
            double posZ = (double)sender.func_82114_b().field_71573_c + 0.5D;
            if (args.length >= 4) {
               posX = func_110666_a(sender, posX, args[1]);
               posY = func_110666_a(sender, posY, args[2]);
               posZ = func_110666_a(sender, posZ, args[3]);
            } else {
               posY += 3.0D;
            }

            int size = -1;
            if (args.length >= 5) {
               size = func_71532_a(sender, args[4], 0, 10000);
            }

            LOTREntityInvasionSpawner invasion = new LOTREntityInvasionSpawner(world);
            invasion.setInvasionType(type);
            invasion.func_70012_b(posX, posY, posZ, 0.0F, 0.0F);
            world.func_72838_d(invasion);
            invasion.selectAppropriateBonusFactions();
            invasion.startInvasion(player, size);
            func_152373_a(sender, this, "commands.lotr.invasion.start", new Object[]{type.invasionName(), invasion.getInvasionSize(), posX, posY, posZ});
         } else {
            throw new WrongUsageException("commands.lotr.invasion.noType", new Object[]{typeName});
         }
      } else {
         throw new WrongUsageException(this.func_71518_a(sender), new Object[0]);
      }
   }

   public List func_71516_a(ICommandSender sender, String[] args) {
      return args.length == 1 ? func_71530_a(args, LOTRInvasions.listInvasionNames()) : null;
   }

   public boolean func_82358_a(String[] args, int i) {
      return false;
   }
}
