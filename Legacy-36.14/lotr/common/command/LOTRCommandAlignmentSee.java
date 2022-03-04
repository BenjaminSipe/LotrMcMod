package lotr.common.command;

import com.mojang.authlib.GameProfile;
import java.util.List;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRPlayerData;
import lotr.common.network.LOTRPacketAlignmentSee;
import lotr.common.network.LOTRPacketHandler;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

public class LOTRCommandAlignmentSee extends CommandBase {
   public String func_71517_b() {
      return "alignmentsee";
   }

   public int func_82362_a() {
      return 2;
   }

   public String func_71518_a(ICommandSender sender) {
      return "commands.lotr.alignmentsee.usage";
   }

   public void func_71515_b(ICommandSender sender, String[] args) {
      if (args.length >= 1) {
         String username = args[0];
         GameProfile profile = null;
         EntityPlayerMP entityplayer = MinecraftServer.func_71276_C().func_71203_ab().func_152612_a(username);
         if (entityplayer != null) {
            profile = entityplayer.func_146103_bH();
         } else {
            profile = MinecraftServer.func_71276_C().func_152358_ax().func_152655_a(username);
         }

         if (profile == null || profile.getId() == null) {
            throw new PlayerNotFoundException("commands.lotr.alignmentsee.noPlayer", new Object[]{username});
         }

         if (sender instanceof EntityPlayerMP) {
            LOTRPlayerData playerData = LOTRLevelData.getData(profile.getId());
            LOTRPacketAlignmentSee packet = new LOTRPacketAlignmentSee(username, playerData);
            LOTRPacketHandler.networkWrapper.sendTo(packet, (EntityPlayerMP)sender);
            return;
         }
      }

      throw new WrongUsageException(this.func_71518_a(sender), new Object[0]);
   }

   public List func_71516_a(ICommandSender sender, String[] args) {
      return args.length == 1 ? func_71530_a(args, MinecraftServer.func_71276_C().func_71213_z()) : null;
   }

   public boolean func_82358_a(String[] args, int i) {
      return i == 0;
   }
}
