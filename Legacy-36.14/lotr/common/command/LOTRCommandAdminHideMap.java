package lotr.common.command;

import java.util.List;
import lotr.common.LOTRLevelData;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentTranslation;

public class LOTRCommandAdminHideMap extends CommandBase {
   public String func_71517_b() {
      return "opHideMap";
   }

   public int func_82362_a() {
      return 2;
   }

   public String func_71518_a(ICommandSender sender) {
      return "commands.lotr.opHideMap.usage";
   }

   public void func_71515_b(ICommandSender sender, String[] args) {
      if (sender instanceof EntityPlayer) {
         EntityPlayer player = (EntityPlayer)sender;
         if (MinecraftServer.func_71276_C().func_71203_ab().func_152596_g(player.func_146103_bH())) {
            if (player.field_71075_bZ.field_75098_d) {
               LOTRLevelData.getData(player).setAdminHideMap(true);
               func_152373_a(sender, this, "commands.lotr.opHideMap.hiding", new Object[0]);
            } else {
               throw new WrongUsageException("commands.lotr.opHideMap.notCreative", new Object[0]);
            }
         } else {
            throw new WrongUsageException("commands.lotr.opHideMap.notOp", new Object[0]);
         }
      } else {
         throw new WrongUsageException("commands.lotr.opHideMap.notOp", new Object[0]);
      }
   }

   public static void notifyUnhidden(EntityPlayer entityplayer) {
      entityplayer.func_145747_a(new ChatComponentTranslation("commands.lotr.opHideMap.unhide", new Object[0]));
   }

   public List func_71516_a(ICommandSender sender, String[] args) {
      return null;
   }

   public boolean func_82358_a(String[] args, int i) {
      return false;
   }
}
