package lotr.common.command;

import java.util.ArrayList;
import java.util.List;
import lotr.common.LOTRSpawnDamping;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;

public class LOTRCommandSpawnDamping extends CommandBase {
   public String func_71517_b() {
      return "spawnDamping";
   }

   public int func_82362_a() {
      return 2;
   }

   public String func_71518_a(ICommandSender sender) {
      return "commands.lotr.spawnDamping.usage";
   }

   public void func_71515_b(ICommandSender sender, String[] args) {
      if (args.length >= 1) {
         String option = args[0];
         if (option.equals("reset")) {
            LOTRSpawnDamping.resetAll();
            func_152373_a(sender, this, "commands.lotr.spawnDamping.reset", new Object[0]);
            return;
         }

         if (args.length >= 2) {
            String type = args[1];
            if (!type.equals(LOTRSpawnDamping.TYPE_NPC) && EnumCreatureType.valueOf(type) == null) {
               throw new WrongUsageException("commands.lotr.spawnDamping.noType", new Object[]{type});
            }

            if (option.equals("set") && args.length >= 3) {
               float damping = (float)func_110661_a(sender, args[2], 0.0D, 1.0D);
               LOTRSpawnDamping.setSpawnDamping(type, damping);
               func_152373_a(sender, this, "commands.lotr.spawnDamping.set", new Object[]{type, damping});
               return;
            }

            if (option.equals("calc")) {
               World world = sender.func_130014_f_();
               int dim = world.field_73011_w.field_76574_g;
               String dimName = world.field_73011_w.func_80007_l();
               float damping = LOTRSpawnDamping.getSpawnDamping(type);
               int players = world.field_73010_i.size();
               int expectedChunks = 196;
               int baseCap = LOTRSpawnDamping.getBaseSpawnCapForInfo(type, world);
               int cap = LOTRSpawnDamping.getSpawnCap(type, baseCap, players);
               int capXPlayers = cap * players;
               IChatComponent msg = new ChatComponentTranslation("commands.lotr.spawnDamping.calc", new Object[]{dim, dimName, type, damping, players, Integer.valueOf(expectedChunks), cap, baseCap, capXPlayers});
               msg.func_150256_b().func_150238_a(EnumChatFormatting.GREEN);
               sender.func_145747_a(msg);
               return;
            }
         }
      }

      throw new WrongUsageException(this.func_71518_a(sender), new Object[0]);
   }

   public List func_71516_a(ICommandSender sender, String[] args) {
      if (args.length == 1) {
         return func_71530_a(args, new String[]{"set", "calc", "reset"});
      } else if (args.length != 2 || !args[0].equals("set") && !args[0].equals("calc")) {
         return null;
      } else {
         List types = new ArrayList();
         EnumCreatureType[] var4 = EnumCreatureType.values();
         int var5 = var4.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            EnumCreatureType type = var4[var6];
            types.add(type.name());
         }

         types.add(LOTRSpawnDamping.TYPE_NPC);
         return func_71530_a(args, (String[])types.toArray(new String[0]));
      }
   }

   public boolean func_82358_a(String[] args, int i) {
      return false;
   }
}
