package lotr.common.command;

import lotr.common.world.LOTRWorldProvider;
import net.minecraft.command.CommandTime;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;

public class LOTRCommandTimeVanilla extends CommandTime {
   protected void func_71552_a(ICommandSender sender, int time) {
      WorldServer[] var3 = MinecraftServer.func_71276_C().field_71305_c;
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         WorldServer world = var3[var5];
         if (!(world.field_73011_w instanceof LOTRWorldProvider)) {
            world.func_72877_b((long)time);
         }
      }

   }

   protected void func_71553_b(ICommandSender sender, int time) {
      WorldServer[] var3 = MinecraftServer.func_71276_C().field_71305_c;
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         WorldServer world = var3[var5];
         if (!(world.field_73011_w instanceof LOTRWorldProvider)) {
            world.func_72877_b(world.func_72820_D() + (long)time);
         }
      }

   }
}
